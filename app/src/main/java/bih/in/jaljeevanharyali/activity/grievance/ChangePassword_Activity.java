package bih.in.jaljeevanharyali.activity.grievance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PreLoginActivity;
import bih.in.jaljeevanharyali.activity.SplashActivity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class ChangePassword_Activity extends Activity {

    EditText et_old_pass,et_new_pass,et_confirm_pass;
    Button btn_chng_pass;
    String changePass_old="",change_pass_new="",change_pass_confirm="";
    //ChangePassword change_password;
    String User_Id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_);

        User_Id = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");


        et_old_pass=findViewById(R.id.et_old_pass);
        et_new_pass=findViewById(R.id.et_new_pass);
        et_confirm_pass=findViewById(R.id.et_confirm_pass);
        btn_chng_pass=findViewById(R.id.btn_chng_pass);


        btn_chng_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utiilties.isOnline(ChangePassword_Activity.this))
                {
                    change_pass_new = et_new_pass.getText().toString();
                    change_pass_confirm = et_confirm_pass.getText().toString();
                    if(change_pass_confirm.equals(change_pass_new)){
                        change_password();
                    }
                    else {
                        Toast.makeText(ChangePassword_Activity.this,
                                "Confirm password does not matched", Toast.LENGTH_SHORT)
                                .show();
                    }


                }
                else
                {

                    AlertDialog.Builder alertDialog=new AlertDialog.Builder(ChangePassword_Activity.this);
                    alertDialog.setTitle("Alert!!");
                    alertDialog.setMessage("No Internet Connection...Please turn on your internet connection?");
                    alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                            startActivity(I);
                        }
                    });
                    alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface dialog, int which){
                            dialog.cancel();
                        }
                    });
                    AlertDialog alert=alertDialog.create();
                    alert.show();
                }

            }
        });
    }

    public void change_password(){

        changePass_old = et_old_pass.getText().toString();
        change_pass_new = et_new_pass.getText().toString();
        change_pass_confirm = et_confirm_pass.getText().toString();

        boolean changepassword = false;
        String isValied = "yes";
        View focusView = null;

        if (TextUtils.isEmpty(changePass_old)) {
            et_old_pass.setError("Please enter old password");
            focusView = et_old_pass;
            changepassword = true;
        }

        if (TextUtils.isEmpty(change_pass_new)) {
            et_new_pass.setError("Please enter new password");
            focusView = et_new_pass;
            changepassword = true;
        }
        if (TextUtils.isEmpty(change_pass_confirm)) {
            et_confirm_pass.setError("Please enter confirm password");
            focusView = et_confirm_pass;
            changepassword = true;
        }

        if (!change_pass_confirm.equals(change_pass_new)){
            et_confirm_pass.setError("Confirm Password Doesn't match");
            focusView = et_confirm_pass;
            changepassword = true;
        }

        if (changepassword)
        {
            // error in login
            focusView.requestFocus();
        }
        else
        {
            new CHANGEPASSWORD().execute();

        }
    }

    private class CHANGEPASSWORD extends AsyncTask<UserDetails, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(ChangePassword_Activity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(ChangePassword_Activity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Processing...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(UserDetails... param) {

            //  String loginType= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LOGINTYPE", "");
            // String mobilenum= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MOBILENUM", "");
            return WebServiceHelper.ChangePassword(changePass_old,change_pass_new,User_Id);
        }

        @Override

        protected void onPostExecute(String result)
        {

            if (this.dialog.isShowing())
            {
                this.dialog.dismiss();
            }

            if (result != null)
            {

                if (result.contains("1"))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword_Activity.this);

                    builder.setMessage("पासवर्ड सफलता पूर्वक बदला गया");
                    builder.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SplashActivity.prefs = PreferenceManager.getDefaultSharedPreferences(ChangePassword_Activity.this);
                            SharedPreferences.Editor editor = SplashActivity.prefs.edit();
                            editor.putBoolean("username", false);
                            editor.putBoolean("password", false);
                            editor.commit();

//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
//        dataBaseHelper.deleteAllInspectionRecord();
                            Intent intent = new Intent(ChangePassword_Activity.this, PreLoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();

                }
                else if(result.contains("2")){

                    Toast.makeText(ChangePassword_Activity.this,
                            "पासवर्ड नहीं बदला गया", Toast.LENGTH_SHORT)
                            .show();

                }

                else {

                    //unknown return type
                    Toast.makeText(ChangePassword_Activity.this,"Failed", Toast.LENGTH_SHORT)
                            .show();
                }
            } else {

                Toast.makeText(ChangePassword_Activity.this,
                        "Result Null ", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
}
