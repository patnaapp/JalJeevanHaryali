package bih.in.jaljeevanharyali.activity.grievance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.LoginActivity;
import bih.in.jaljeevanharyali.activity.PanchayatEntity;
import bih.in.jaljeevanharyali.activity.SplashActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.CommonPref;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class GrievanceLoginActivity extends Activity {

    EditText et_number,et_otp;

    String uid = "";
    String pass = "";
    DataBaseHelper localDBHelper;

    UserDetails userInfo;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance_login);

        et_number = (EditText)findViewById(R.id.et_number);
        et_otp = (EditText)findViewById(R.id.et_otp);

        try {

            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            TextView tv = (TextView) findViewById(R.id.txtVersion);
            tv.setText(getResources().getString(R.string.app_version) + version);

        } catch (PackageManager.NameNotFoundException e) {

        }
    }

    public void onGrievanceLogin(View view){
        if(validateData()){

            String number = et_number.getText().toString();
            String otp = et_otp.getText().toString();

//            Intent intent = new Intent(this, GrievanceHomeActivity.class);
//            startActivity(intent);
            new LoginTask(number, otp).execute("");
        }else{
            Toast.makeText(this,"Enter Valid Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClick_GetPassword(View view)
    {
        Intent i=new Intent(GrievanceLoginActivity.this,Get_password_activity.class);
        startActivity(i);
    }

    public void onClick_GrievanceReg(View view){
        Intent intent = new Intent(this, GrievanceSignupActivity.class);
        startActivity(intent);
    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if (et_number.getText().toString().equals("")) {
            et_number.setError(getString(R.string.fieldRequired));
            focusView = et_number;
            validate = false;
        }else if (et_number.getText().toString().length() < 10) {
            et_number.setError("अमान्य मोबाइल नंबर");
            focusView = et_number;
            validate = false;
        }

        if (et_otp.getText().toString().equals("")) {
            et_otp.setError(getString(R.string.fieldRequired));
            focusView = et_otp;
            validate = false;
        }


        if (!validate) focusView.requestFocus();

        return validate;
    }

    private class LoginTask extends AsyncTask<String, Void, UserDetails> {

        private final ProgressDialog dialog = new ProgressDialog(
                GrievanceLoginActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(
                GrievanceLoginActivity.this).create();

        String number,otp;

        public LoginTask(String number, String otp) {
            this.number = number;
            this.otp = otp;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage(getResources().getString(R.string.authenticating));
            this.dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... param) {

//            if (!Utiilties.isOnline(GrievanceLoginActivity.this)) {
//                UserDetails userDetails = new UserDetails();
//                userDetails.setAuthenticated(true);
//                return userDetails;
//            } else {
                return WebServiceHelper.GrievanceLogin(number, otp);
           // }

        }

        @Override
        protected void onPostExecute(final UserDetails result) {

            if (this.dialog.isShowing()) this.dialog.dismiss();

            if (result != null && result.isAuthenticated() == false) {

                alertDialog.setTitle(getResources().getString(R.string.failed));
                alertDialog.setMessage("मोबाइल नंबर या पासवर्ड गलत है");
                alertDialog.show();

            } else if (!(result != null)) {
                AlertDialog.Builder ab = new AlertDialog.Builder(GrievanceLoginActivity.this);
                ab.setTitle(getResources().getString(R.string.server_down_title));
                ab.setMessage(getResources().getString(R.string.server_down_text));
                ab.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                ab.create().getWindow().getAttributes().windowAnimations = android.R.style.Animation_Dialog;
                ab.show();

            } else {

                //-----------------------------------------Online-------------------------------------
                if (Utiilties.isOnline(GrievanceLoginActivity.this)) {


                    uid = number;
                    pass = otp;

                    if (result != null && result.isAuthenticated() == true) {
                        uid=result.getUserID();
                        pass = otp;

                        try {

                            GlobalVariables.LoggedUser = result;
                            GlobalVariables.LoggedUser.setUserID(et_number.getText().toString().trim().toLowerCase());

                            GlobalVariables.LoggedUser.setPassword(et_otp.getText().toString().trim());

                            CommonPref.setUserDetails(getApplicationContext(),GlobalVariables.LoggedUser);

                            long c = setLoginStatus(GlobalVariables.LoggedUser);

                            if (c > 0) {
                                start();
                            }
                            else {
                                Toast.makeText(GrievanceLoginActivity.this, getResources().getString(R.string.authentication_failed),
                                        Toast.LENGTH_SHORT).show();
                            }


                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(GrievanceLoginActivity.this, getResources().getString(R.string.authentication_failed),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    // offline -------------------------------------------------------------------------

                } else {

                    if (localDBHelper.getUserCount() > 0) {

                        GlobalVariables.LoggedUser = localDBHelper.getUserDetails(et_number.getText().toString().trim().toLowerCase(),
                                        et_otp.getText().toString());

                        if (GlobalVariables.LoggedUser != null) {

                            CommonPref.setUserDetails(
                                    getApplicationContext(),
                                    GlobalVariables.LoggedUser);


                            SharedPreferences.Editor editor = SplashActivity.prefs.edit();
                            editor.putBoolean("username", true);
                            editor.putBoolean("password", true);
                            editor.putString("uid", uid);
                            editor.putString("pass", pass);
                            editor.commit();
                            start();


                        } else {

                            Toast.makeText(
                                    getApplicationContext(),
                                    getResources().getString(R.string.username_password_notmatched),
                                    Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(
                                getApplicationContext(),
                                getResources().getString(R.string.enable_internet_for_firsttime),
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

        }
    }

    private long setLoginStatus(UserDetails details) {

        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putBoolean("username", true);
        editor.putBoolean("password", true);
        editor.putString("uid", uid);
        editor.putString("pass", pass);
        editor.putString("role", "grievance");
        editor.putString("userType", "grievance");
        editor.commit();
        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", uid).commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("uid", uid).commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("distcode", details.getDistrictCode()).commit();
        localDBHelper = new DataBaseHelper(getApplicationContext());
        long c = localDBHelper.insertUserDetails(details);

        return c;
    }

    public void start() {
        getUserDetail();
//        Intent iUserHome = new Intent(getApplicationContext(), GrievanceHomeActivity.class);
//        iUserHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        startActivity(iUserHome);
//        finish();
        new SyncPanchayatData().execute();
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = localDBHelper.getUserDetails(username.toLowerCase(), password);

        //Toast.makeText(this, userInfo.getBlockCode(), Toast.LENGTH_SHORT).show();
    }

    private class SyncPanchayatData extends AsyncTask<String, Void, ArrayList<PanchayatEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(
                GrievanceLoginActivity.this);
        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("पंचायत लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PanchayatEntity> doInBackground(String...arg) {
            return WebServiceHelper.getPanchayatList(userInfo.getDistrictCode(), userInfo.getBlockCode());
        }

        @Override
        protected void onPostExecute(ArrayList<PanchayatEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            long i= helper.setPanchayatDataToLocal(userInfo, result);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Panchayat Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update panchayat",Toast.LENGTH_SHORT).show();

            }

            Intent iUserHome = new Intent(getApplicationContext(), GrievanceHomeActivity.class);
            iUserHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(iUserHome);
        }
    }
}
