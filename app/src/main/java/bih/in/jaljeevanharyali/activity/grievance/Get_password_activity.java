package bih.in.jaljeevanharyali.activity.grievance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class Get_password_activity extends Activity {

    EditText et_mob_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password_activity);

        et_mob_number = (EditText)findViewById(R.id.et_mob_number);
    }


    public void onGet_Password(View view){

        if(validateData())
        {
            String mobile = et_mob_number.getText().toString();

            new GetPassword().execute();
        }else{
            Toast.makeText(this,"Enter Valid Data", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean validateData()
    {
        View focusView = null;
        boolean validate = true;

        if (et_mob_number.getText().toString().length() < 10)
        {
            et_mob_number.setError("अमान्य मोबाइल नंबर");
            focusView = et_mob_number;
            validate = false;
        }


        if (!validate) focusView.requestFocus();

        return validate;
    }

    private class GetPassword extends AsyncTask<UserDetails, Void, String>
    {

        private final ProgressDialog dialog = new ProgressDialog(Get_password_activity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(Get_password_activity.this).create();

        @Override
        protected void onPreExecute()
        {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Processing...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(UserDetails... param)
        {
            return WebServiceHelper.ResetPassword(et_mob_number.getText().toString());
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
                    AlertDialog.Builder builder = new AlertDialog.Builder(Get_password_activity.this);

                    builder.setMessage("पासवर्ड पंजीकृत मोबाइल नंबर पे भेजा गया है ");
                    builder.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.dismiss();
                            finish();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();

                }
                else if(result.contains("2"))
                {
                    Toast.makeText(Get_password_activity.this,"मोबाइल संख्या रजिस्टर्ड नहीं है", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    //unknown return type
                    Toast.makeText(Get_password_activity.this,"Failed", Toast.LENGTH_SHORT).show();
                }
            } else {

                Toast.makeText(Get_password_activity.this,"Result Null ", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
