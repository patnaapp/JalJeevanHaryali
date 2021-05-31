package bih.in.jaljeevanharyali.activity.grievance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.LoginActivity;
import bih.in.jaljeevanharyali.activity.PanchayatEntity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.District;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class GrievanceSignupActivity extends Activity {
    ImageView img_cal;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy ");
    String dateString = "";
    DatePickerDialog datedialog;
    private int mYear, mMonth, mDay;
    String slno = "";
    boolean edit = false;
    String datetime = "";
    public static String dateMul = "";
    DataBaseHelper localdbhelper;
    Button btn_reg , btn_cancel,buttonConfirm_OTP,buttonResend_OTP;
    String _vardistrictName="",_vardistrictID="";
    String _varblockName="",_varblockID="";
    String _varpanchayatName="",_varpanchayatID="";

    ArrayList<District> DistrictList = new ArrayList<District>();
    ArrayAdapter<String> districtadapter;

    ArrayList<Block> BlockList = new ArrayList<Block>();
    ArrayAdapter<String> blockadapter;
    ArrayList<String> BlocktStringList;

    ArrayList<PanchayatData> PanchayatList = new ArrayList<>();
    ArrayAdapter<String> Panchayatadapter;
    TextView viewmobile;
    String currentDAte;
    EditText txt_Name,txt_Mobile,txt_dob,et_OTP,txt_Password,txt_ConfirmPassword,txt_FatherName,txt_Address;
    Spinner spinne_district,spinnerBlock,spinnerPanchayat;
    UserDetails userDetails;
    String str_name,str_mobile,reg_OTP,str_fathername,str_address,str_password,str_confirmpassword;

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance_signup);

        dialog = new ProgressDialog(this);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        txt_Name=(EditText) findViewById(R.id.txt_Name);
        txt_Mobile=(EditText) findViewById(R.id.txt_MObilenumber);
        txt_FatherName=(EditText) findViewById(R.id.txt_FatherName);
        txt_Address=(EditText) findViewById(R.id.txt_Address);
        txt_Password=(EditText) findViewById(R.id.txt_Password);
        txt_ConfirmPassword=(EditText) findViewById(R.id.txt_ConfirmPassword);
        spinne_district=(Spinner) findViewById(R.id.spinne_district);
        spinnerBlock=(Spinner) findViewById(R.id.spinnerBlock);
        spinnerPanchayat=(Spinner) findViewById(R.id.spinnerPanchayat);
        // img_cal=(ImageView) findViewById(R.id.img_cal);
        localdbhelper=new DataBaseHelper(this);

//        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm");
//        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//        String result = String.valueOf(dateFormat.format(millis));

        loadDistrictSpinnerdata();
        spinne_district.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                if (arg2 > 0) {

                    District dist = DistrictList.get(arg2 - 1);
                    _vardistrictID = dist.get_DistCode();
                    _vardistrictName=dist.get_DistName();
                    //CommonPref.setUserDetails();
                    //  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DIST_CODE", _vardistrictID).commit();
                    setBlockSpinnerData();
                    // spinne_district.clearAnimation();
                    // BlinkSpinner(spBlock);

                } else {
                    _vardistrictID = "0";
                    _vardistrictName="NA";
                    // PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("DIST_CODE", "0").commit();
                    spinnerBlock.setSelection(0);
                    spinnerBlock.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerBlock.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    Block blk = BlockList.get(arg2 - 1);
                    _varblockID = blk.getBlockCode();
                    _varblockName=blk.getBlockName();
                    // PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BLOCK_CODE", _varblockID).commit();
                    setPanchayatSpinnerData();
                    //  spinnerBlock.clearAnimation();
                    // BlinkSpinner(spPanchayat);
                }
                else if(arg2==0)
                {
                    _varblockID = "0";
                    _varblockName="0";
                    //   PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("BLOCK_CODE", "0").commit();
                    spinnerPanchayat.setSelection(0);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        spinnerPanchayat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    PanchayatData wrd = PanchayatList.get(arg2 - 1);
                    _varpanchayatID = wrd.getPcode();
                    _varpanchayatName=wrd.getPname();

                    //  pcode=_varpanchayatID;
                    //  PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PAN_CODE", _varpanchayatID).commit();

                    //  setTintColorForDownloadImage();
                    //  spPanchayat.clearAnimation();
                }
                else if(arg2==0)
                {
                    // spWard.setSelection(0);
                    // spVillage.setSelection(0);
                    // PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().putString("PAN_CODE", "0").commit();
                    _varpanchayatID = "0";
                    _varpanchayatName="0";
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }
    public void loadDistrictSpinnerdata() {

        DistrictList = localdbhelper.getDistrict();
        String[] divNameArray = new String[DistrictList.size() + 1];
        divNameArray[0] = "-जिला चुनें-";
        int i = 1;
        int setID=0;
        for (District dist : DistrictList) {

            divNameArray[i] = dist.get_DistName();
            if(_vardistrictID.equalsIgnoreCase(DistrictList.get(i-1).get_DistCode()))
            {
                setID=i;
            }
            i++;
        }
        districtadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, divNameArray);
        districtadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinne_district.setAdapter(districtadapter);

        spinne_district.setSelection(setID);
    }


    public void setBlockSpinnerData()
    {
        DataBaseHelper placeData = new DataBaseHelper(this);
        BlockList=placeData.getBlock(_vardistrictID);

        if(BlockList.size()>0) loadBlockSpinnerData(BlockList);

//		msg = "Please select block";
//		TextToSpeech(msg);
    }

    private void loadBlockSpinnerData(ArrayList<Block> bList)
    {
        BlocktStringList=new ArrayList<String>();

        BlocktStringList.add("-प्रखंड चुनें-");
        int setID=0;
        for(int i=0;i<bList.size();i++)
        {
            BlocktStringList.add(bList.get(i).getBlockName());
            if(_varblockID.equalsIgnoreCase(bList.get(i).getBlockCode()))
            {
                setID=i+1;
            }
        }
        blockadapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,BlocktStringList);
        blockadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBlock.setAdapter(blockadapter);
        spinnerBlock.setSelection(setID);
    }

    public void setPanchayatSpinnerData()
    {
        DataBaseHelper placeData = new DataBaseHelper(this);
        PanchayatList=placeData.getPanchayatLocal(_varblockID);
        String isfound="n";

        if(PanchayatList.size()>0) {
            loadPanchayatSpinnerData(PanchayatList);
        }else{
            new SyncPanchayatData().execute();
        }

    }
    private void loadPanchayatSpinnerData(ArrayList<PanchayatData> pList)
    {
        ArrayList<String> PanchayatStringList=new ArrayList<String>();
        PanchayatStringList.add("-पंचायत चुनें-");
        int setID=0;
        for(int i=0;i<pList.size();i++)
        {
            PanchayatStringList.add(pList.get(i).getPname());
            if(_varpanchayatID.equalsIgnoreCase(pList.get(i).getPcode()))
            {
                setID=i+1;
            }
        }
        Panchayatadapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,PanchayatStringList);
        Panchayatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerPanchayat.setAdapter(Panchayatadapter);
        spinnerPanchayat.setSelection(setID);
    }
    public void onClick_Registration(View view){

        if (Utiilties.isOnline(this)) {
            Registration();

        }
        else{

            Toast.makeText(this, "Check internet connection", Toast.LENGTH_SHORT).show();

            final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle(getResources().getString(R.string.no_internet_connection));
            alertDialog.setMessage("No internet connection. Please turn on internet connection.");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    alertDialog.cancel();
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            alertDialog.show();
        }
    }

    public void Registration() {


        str_name = txt_Name.getText().toString();
        str_mobile = txt_Mobile.getText().toString();
        str_fathername = txt_FatherName.getText().toString();
        str_address = txt_Address.getText().toString();
        str_password = txt_Password.getText().toString();
        str_confirmpassword = txt_ConfirmPassword.getText().toString();

        boolean cancelRegistration = false;
        String isValied = "yes";
        View focusView = null;


        if (TextUtils.isEmpty(str_name)) {
            txt_Name.setError("कृपया नाम डालें ");
            focusView = txt_Name;
            cancelRegistration = true;
        }
        if (TextUtils.isEmpty(str_mobile)) {
            txt_Mobile.setError("कृपया मोबाइल   नंबर डालें ");
            focusView = txt_Mobile;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(str_fathername)) {
            txt_FatherName.setError("कृपया पिता का नाम  डालें ");
            focusView = txt_FatherName;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(str_address)) {
            txt_Address.setError("कृपया पता डालें ");
            focusView = txt_Address;
            cancelRegistration = true;
        }

        if ((spinne_district != null && spinne_district.getSelectedItem() != null)) {
            if ((String) spinne_district.getSelectedItem() != "-जिला चुनें-") {

            } else {
                Toast.makeText(this, "कृपया जिला चुनें", Toast.LENGTH_LONG).show();

                spinne_district.requestFocus();
                return;
            }
        }
        if ((spinnerBlock != null && spinnerBlock.getSelectedItem() != null)) {
            if ((String) spinnerBlock.getSelectedItem() != "-प्रखंड चुनें-") {

            } else {
                Toast.makeText(this, "कृपया ब्लॉक चुनें ", Toast.LENGTH_LONG).show();

                spinnerBlock.requestFocus();
                return;
            }
        }
        if ((spinnerPanchayat != null && spinnerPanchayat.getSelectedItem() != null)) {
            if ((String) spinnerPanchayat.getSelectedItem() != "-पंचायत चुनें-") {

            } else {
                Toast.makeText(this, "कृपया पंचायत का चयन करें", Toast.LENGTH_LONG).show();

                spinnerPanchayat.requestFocus();
                return;
            }
        }
        if (TextUtils.isEmpty(str_password)) {
            txt_Password.setError("कृपया पासवर्ड डालें ");
            focusView = txt_Password;
            cancelRegistration = true;
        }

        if (TextUtils.isEmpty(str_confirmpassword)) {
            txt_ConfirmPassword.setError("कृपया कन्फर्म पासवर्ड डालें ");
            focusView = txt_ConfirmPassword;
            cancelRegistration = true;
        }
        else if (!(str_password.equals(str_confirmpassword))) {
            txt_ConfirmPassword.setError("आपका पासवर्ड मैच नहीं हुआ |");
            focusView = txt_ConfirmPassword;
            cancelRegistration = true;
        }



        if (cancelRegistration) {
            // error in login
            focusView.requestFocus();
        } else {
            userDetails = new UserDetails();
            userDetails.setName(str_name);
            userDetails.setMobileNo(str_mobile);
            userDetails.setFatherName(str_fathername);
            userDetails.setAddress(str_address);
            userDetails.setPassword(str_password);
            userDetails.setDistrictCode(_vardistrictID);
            userDetails.setBlockCode(_varblockID);
            userDetails.setPanchaayatCode(_varpanchayatID);
            userDetails.setEntrydate(Utiilties.getCurrentDate());


            new RegistrationTask().execute(userDetails);
        }
    }
    private class RegistrationTask extends AsyncTask<UserDetails, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(GrievanceSignupActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(GrievanceSignupActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Registration...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(UserDetails... param) {

            //  String loginType= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("LOGINTYPE", "");
            // String mobilenum= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("MOBILENUM", "");
            return WebServiceHelper.Registration(str_mobile);
        }

        @Override

        protected void onPostExecute(String result) {

            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if (result != null) {

                if (result.contains("1")){
                    try {
                        confirmOtp();

                    } catch (Exception ex) {
                        Toast.makeText(GrievanceSignupActivity.this,
                                "ERROR-Exception: Error !" + ex.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }

                }
                else if(result.contains("2")){

                    Toast.makeText(GrievanceSignupActivity.this,
                            "!! URT !! Invalid user", Toast.LENGTH_SHORT)
                            .show();

                }

                else {

                    //unknown return type
                    Toast.makeText(GrievanceSignupActivity.this,"Failed", Toast.LENGTH_SHORT)
                            .show();
                }
            } else {

                Toast.makeText(GrievanceSignupActivity.this,
                        "Result Null ", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
    private void confirmOtp() {
        //Creating a LayoutInflater object for the dialog box
        LayoutInflater li = LayoutInflater.from(this);
        //Creating a view to get the dialog box
        View confirmDialog = li.inflate(R.layout.reg_otponfirm, null);

        //Initizliaing confirm button fo dialog box and edittext of dialog box
        buttonConfirm_OTP = (Button) confirmDialog.findViewById(R.id.buttonConfirmpotp);
        buttonResend_OTP = (Button) confirmDialog.findViewById(R.id.btn_resendotp);
        et_OTP = (EditText) confirmDialog.findViewById(R.id.et_OTP);
        viewmobile = (TextView) confirmDialog.findViewById(R.id.viewmobile);


        //  showmobile = MobileNumber.replaceAll("\\w(?=\\w{4})", "*");

        //  viewmobile.setText(" OTP मोबाइल नंबर: "+showmobile+" पर भेजा गया ");

        //Creating an alertdialog builder
        AlertDialog.Builder alert = new AlertDialog.Builder(this);

        //Adding our dialog box to the view of alert dialog
        alert.setView(confirmDialog);
        alert.setCancelable(false);

        //Creating an alert dialog
        final AlertDialog alertDialog = alert.create();

        //Displaying the alert dialog
        alertDialog.show();
        buttonConfirm_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirm_reg_OTP();

            }
        });
        buttonResend_OTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userDetails.setMobileNo(str_mobile);

                new RegistrationTask().execute(userDetails);
            }
        });

    }

    public void confirm_reg_OTP(){

        reg_OTP = et_OTP.getText().toString();

        boolean changepassword = false;
        String isValied = "yes";
        View focusView = null;

        if (TextUtils.isEmpty(reg_OTP)) {
            et_OTP.setError("Enter OTP");
            focusView = et_OTP;
            changepassword = true;
        }

        if (changepassword) {
            // error in login
            focusView.requestFocus();
        } else {

            userDetails = new UserDetails();
            userDetails.setOtp(reg_OTP);
            userDetails.setName(str_name);
            userDetails.setMobileNo(str_mobile);
            userDetails.setFatherName(str_fathername);
            userDetails.setAddress(str_address);
            userDetails.setPassword(str_password);
            userDetails.setDistrictCode(_vardistrictID);
            userDetails.setBlockCode(_varblockID);
            userDetails.setPanchaayatCode(_varpanchayatID);
            userDetails.setEntrydate(Utiilties.getCurrentDateDMY());

            new reg_user_otp().execute(userDetails);


        }
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


            return WebServiceHelper.getPanchayatList(_vardistrictID, _varblockID);

        }

        @Override
        protected void onPostExecute(ArrayList<PanchayatEntity> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
            UserDetails info  = new UserDetails();
            info.setBlockCode(_varblockID);
            info.setBlockName(_varblockName);
            info.setDistrictCode(_vardistrictID);
            info.setDistName(_vardistrictName);

            long i= helper.setPanchayatDataToLocal(info, result);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Panchayat Data Synced Successfully",Toast.LENGTH_SHORT).show();
                //sDataBaseHelper placeData = new DataBaseHelper(this);
                PanchayatList=helper.getPanchayt(_varblockID);
                loadPanchayatSpinnerData(PanchayatList);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update panchayat",Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class reg_user_otp extends AsyncTask<UserDetails, Void, String> {

        private final ProgressDialog dialog = new ProgressDialog(GrievanceSignupActivity.this);

        private final AlertDialog alertDialog = new android.app.AlertDialog.Builder(GrievanceSignupActivity.this).create();




        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(UserDetails... param) {


            String res = WebServiceHelper.registration_otp(param[0]);
            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responseval", "" + result);
            if (result != null) {
                if (result.contains("1")) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(GrievanceSignupActivity.this);

                    builder.setMessage("Registration Sucessfull");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            dialog.cancel();
                            dialog.dismiss();
                            Intent intent = new Intent(GrievanceSignupActivity.this, GrievanceLoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                   AlertDialog dialog = builder.create();
                    // Display the alert dialog on interface
                    dialog.show();
                    // Toast.makeText(getApplicationContext(), "The Password has been sent to your registerd mobile number", Toast.LENGTH_SHORT).show();

                }

                else if(result.contains("2")) {



                    Toast.makeText(GrievanceSignupActivity.this,
                            "OTP मैच नहीं हुआ ", Toast.LENGTH_SHORT)
                            .show();
                }
                else if(result.contains("0")) {
                    Toast.makeText(GrievanceSignupActivity.this,"मोबाइल नंबर पहले रजिस्टर्ड हो चूका है |", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(GrievanceSignupActivity.this,"पंजीकरण विफल रहा, कृपया पुनः प्रयास करें |", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(GrievanceSignupActivity.this,
                        "Result null ", Toast.LENGTH_LONG)
                        .show();

            }
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            // finish the activity
            onBackPressed();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
