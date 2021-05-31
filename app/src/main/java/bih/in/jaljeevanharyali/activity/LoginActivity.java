package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.kobjects.util.Csv;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.grievance.GrievanceLoginActivity;
import bih.in.jaljeevanharyali.activity.userCo.DashboardCoActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.DepartmentDetail;
import bih.in.jaljeevanharyali.entity.TreesDetail;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.CommonPref;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class LoginActivity extends Activity {

    ConnectivityManager cm;
    public static String UserPhoto;
    String version;
    TelephonyManager tm;
    private static String imei;
    //TODO setup Database
    //DatabaseHelper1 localDBHelper;
    Context context;
    String uid = "";
    String pass = "";
    EditText userName;
    EditText userPass;
    String[] param;
    DataBaseHelper localDBHelper;

    UserDetails userInfo;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button loginBtn = (Button) findViewById(R.id.email_sign_in_button);
        TextView signUpBtn = (TextView) findViewById(R.id.signUp);

        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setCanceledOnTouchOutside(false);

        localDBHelper = new DataBaseHelper(this);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = (EditText) findViewById(R.id.email);
                userPass = (EditText) findViewById(R.id.password);
                param = new String[2];
                param[0] = userName.getText().toString();
                param[1] = userPass.getText().toString();

                if (param[0].length() < 1){
                    Toast.makeText(LoginActivity.this, "Enter Valid User Id", Toast.LENGTH_SHORT).show();
                }else if (param[1].length() < 1){
                    Toast.makeText(LoginActivity.this, "Enter Valid Password", Toast.LENGTH_SHORT).show();
                }else{
                    inititateLogin();
                }

            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent singUpInt = new Intent(LoginActivity.this, SignUpActivity.class);
                LoginActivity.this.startActivity(singUpInt);
            }
        });

        version = Utiilties.getAppVersion(this);
        TextView tv = (TextView) findViewById(R.id.txtVersion);
        tv.setText(getResources().getString(R.string.app_version) + version);
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = localDBHelper.getUserDetails(username.toLowerCase(), password);

        //Toast.makeText(this, userInfo.getBlockCode(), Toast.LENGTH_SHORT).show();
    }

    public void onClickGrievance(View view){
        Intent intent = new Intent(this, GrievanceLoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //getIMEI();

    }

    public void inititateLogin() {

        userName = (EditText) findViewById(R.id.email);
        userPass = (EditText) findViewById(R.id.password);
        param = new String[2];
        param[0] = userName.getText().toString();
        param[1] = userPass.getText().toString();
        //Toast.makeText(this, (CharSequence) userName, Toast.LENGTH_SHORT).show();
        new LoginTask().execute(param);

    }

    private class LoginTask extends AsyncTask<String, Void, UserDetails> {

        private final ProgressDialog dialog = new ProgressDialog(
                LoginActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(
                LoginActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage(getResources().getString(R.string.authenticating));
            this.dialog.show();
        }

        @Override
        protected UserDetails doInBackground(String... param) {

            if (!Utiilties.isOnline(LoginActivity.this)) {
                UserDetails userDetails = new UserDetails();
                userDetails.setAuthenticated(true);
                return userDetails;
            } else {
                return WebServiceHelper.Login(param[0], param[1]);
            }

        }

        @Override
        protected void onPostExecute(final UserDetails result) {

            if (this.dialog.isShowing()) this.dialog.dismiss();

            if ((result == null)) {
                AlertDialog.Builder ab = new AlertDialog.Builder(context);
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

            }else if (result != null && result.isAuthenticated() == false) {

                alertDialog.setTitle(getResources().getString(R.string.failed));
                alertDialog.setMessage(getResources().getString(R.string.authentication_failed));
                alertDialog.show();

            }else if (result.getUserrole().equals("MOB") || result.getUserrole().equals("Insp")) {

                //-----------------------------------------Online-------------------------------------
                if (Utiilties.isOnline(LoginActivity.this)) {


                    uid = param[0];
                    pass = param[1];

                    if (result != null && result.isAuthenticated() == true) {
                        uid=result.getUserID();
                        pass = param[1];

                        try {

                            GlobalVariables.LoggedUser = result;
                            GlobalVariables.LoggedUser.setUserID(userName
                                    .getText().toString().trim().toLowerCase());

                            GlobalVariables.LoggedUser.setPassword(userPass
                                    .getText().toString().trim());


                            CommonPref.setUserDetails(getApplicationContext(),
                                    GlobalVariables.LoggedUser);


                            long c = setLoginStatus(GlobalVariables.LoggedUser);

                            if (c > 0) {
                                start();
                            }
                            else {
                                Toast.makeText(LoginActivity.this, getResources().getString(R.string.authentication_failed),
                                        Toast.LENGTH_SHORT).show();
                            }


                        }
                        catch (Exception ex) {
                            ex.printStackTrace();
                            Toast.makeText(LoginActivity.this, getResources().getString(R.string.authentication_failed),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }

                    // offline -------------------------------------------------------------------------

                } else {

                    if (localDBHelper.getUserCount() > 0) {

                        GlobalVariables.LoggedUser = localDBHelper
                                .getUserDetails(userName.getText()
                                                .toString().trim().toLowerCase(),
                                        userPass.getText().toString());

                        if (GlobalVariables.LoggedUser != null) {

                            CommonPref.setUserDetails(
                                    getApplicationContext(),
                                    GlobalVariables.LoggedUser);

                            userInfo = GlobalVariables.LoggedUser;
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

            } else {
                alertDialog.setTitle("अनधिकृत(Unautharized) लॉगिन");
                alertDialog.setMessage("यह आईडी और पासवर्ड मोबाइल एप्लिकेशन में लॉगिन के लिए अनुमति नहीं है, कृपया सही आईडी पासवर्ड दर्ज करें या इसके बारे में जल जीवन हरियाली टीम से संपर्क करें ");
                alertDialog.show();
            }

        }
    }

    private long setLoginStatus(UserDetails details) {

        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putBoolean("username", true);
        editor.putBoolean("password", true);
        editor.putString("uid", uid);
        editor.putString("pass", pass);
        editor.putString("role", details.getUserrole());
        editor.putString("userType", "inspection");
        editor.commit();
        //PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("USER_ID", uid).commit();
        PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("uid", uid).commit();
        localDBHelper = new DataBaseHelper(getApplicationContext());
        long c = localDBHelper.insertUserDetails(details);

        return c;
    }

    public void start() {
        getUserDetail();
        new SyncPanchayatData().execute("");
    }

    private class SyncPanchayatData extends AsyncTask<String, Void, ArrayList<PanchayatEntity>> {

        @Override
        protected void onPreExecute() {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("पंचायत लोड हो रहा है...");
            dialog.show();
        }

        @Override
        protected ArrayList<PanchayatEntity> doInBackground(String...arg) {
            return WebServiceHelper.getPanchayatList(userInfo.getDistrictCode(), userInfo.getBlockCode());
        }

        @Override
        protected void onPostExecute(ArrayList<PanchayatEntity> result) {
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            //new SyncWardData().execute("");
            new SyncVillageData().execute("");
            long i= helper.setPanchayatDataToLocal(userInfo, result);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Panchayat Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update panchayat",Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class SyncWardData extends AsyncTask<String, Void, ArrayList<ward>> {
        //private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            //this.dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("वार्ड लोड हो रहा है...");
            //this.dialog.show();
        }

        @Override
        protected ArrayList<ward> doInBackground(String...arg) {
            return WebServiceHelper.getWardListData(userInfo.getBlockCode());
        }

        @Override
        protected void onPostExecute(ArrayList<ward> result) {
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            //new SyncVillageData().execute("");
            long i= helper.setWardDataToLocal(result);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Ward Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update ward",Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class SyncVillageData extends AsyncTask<String, Void, ArrayList<VillageListEntity>> {
        //private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            //dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("ग्राम लोड हो रहा है...");
            //this.dialog.show();
        }

        @Override
        protected ArrayList<VillageListEntity> doInBackground(String...arg) {


            return WebServiceHelper.getVillageListData(userInfo.getBlockCode());

        }

        @Override
        protected void onPostExecute(ArrayList<VillageListEntity> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


            long i= helper.setVillageDataToLocal(result);
            new SyncDepartmentListData().execute("");


            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Village Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update village",Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class SyncDepartmentListData extends AsyncTask<String, Void, ArrayList<DepartmentDetail>> {

        @Override
        protected void onPreExecute() {
            dialog.setMessage("विभाग लोड हो रहा है...");
            //dialog.show();
        }

        @Override
        protected ArrayList<DepartmentDetail> doInBackground(String...arg) {
            return WebServiceHelper.getDepartmentListData();
        }

        @Override
        protected void onPostExecute(ArrayList<DepartmentDetail> result) {

            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            long i= helper.setDepartmentDataToLocal(result);
            new SyncTreeTypeListData().execute("");

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Department Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update department",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class SyncTreeTypeListData extends AsyncTask<String, Void, ArrayList<TreesDetail>> {

        @Override
        protected void onPreExecute() {
            dialog.setMessage("पेड़ का प्रकार लोड हो रहा है...");
            //dialog.show();
        }

        @Override
        protected ArrayList<TreesDetail> doInBackground(String...arg) {
            return WebServiceHelper.getTreeDetailListData();
        }

        @Override
        protected void onPostExecute(ArrayList<TreesDetail> result) {

            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            long i= helper.setTreeDetailDataToLocal(result);
            new SyncAwayabListData().execute("");

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Department Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update department",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class SyncAwayabListData extends AsyncTask<String, Void, ArrayList<Abyab>> {
        //private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);

        @Override
        protected void onPreExecute() {
            //dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("अवयव लोड हो रहा है...");
            //this.dialog.show();
        }

        @Override
        protected ArrayList<Abyab> doInBackground(String...arg) {


            return WebServiceHelper.getAbyabListData();

        }

        @Override
        protected void onPostExecute(ArrayList<Abyab> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


            long i= helper.setAbyabDataToLocal(result);

            if(userInfo.getUserrole().toLowerCase().contains("co")){
                Intent iUserHome = new Intent(getApplicationContext(), DashboardCoActivity.class);
                startActivity(iUserHome);
                finish();
            }else{
                Intent iUserHome = new Intent(getApplicationContext(), DashboardActivity.class);
                startActivity(iUserHome);
                finish();
            }

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Awayab Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update Awayab",Toast.LENGTH_SHORT).show();

            }


        }
    }

}
