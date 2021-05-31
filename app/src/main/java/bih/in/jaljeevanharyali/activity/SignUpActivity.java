package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import bih.in.jaljeevanharyali.Manifest;
import bih.in.jaljeevanharyali.MarshmallowPermission;
import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.District;
import bih.in.jaljeevanharyali.entity.SignUp;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

//import android.support.v7.app.AppCompatActivity;


public class SignUpActivity extends Activity {

    DataBaseHelper dataBaseHelper;

    EditText et_name,et_address,et_fname,et_mobile,et_password,et_confirm_password,et_designation;
    Spinner spn_dist,spn_block;
    Button btn_signUp,btn_cancel;
    String st_et_name,st_et_address,st_et_fname,st_et_mobile,st_et_password,st_et_confirm_password,st_Desig;
    String st_spn_dist,st_spn_dist_code="",st_spn_block,st_spn_block_code="";
    TelephonyManager tm;
    public Context context;


    ArrayList<District> distList = new ArrayList<District>();
    ArrayList<Block> BlkList = new ArrayList<Block>();

    ArrayList<String> districtNameArray;
    ArrayList<String> blkNameArray;

    static ArrayList<String> blockstlist;
    private static String imei;
    String version="";

    ArrayAdapter<String> districtadapter;
    ArrayAdapter<String> blockadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Initialization();
//        Utiilties.setActionBarBackground(this);
//        Utiilties.setStatusBarColor(this);

        dataBaseHelper = new DataBaseHelper(SignUpActivity.this);
        dataBaseHelper = new DataBaseHelper(this);


        try {
            dataBaseHelper.createDataBase();

        } catch (IOException ioe) {

            throw new Error("Unable to create database");

        }

        try {

            dataBaseHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }



        loadDistrictSpinner();

        spn_dist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_dist_code = distList.get(pos - 1).get_DistCode();
                    st_spn_dist = distList.get(pos - 1).get_DistName();
                    BlkList=dataBaseHelper.getBlock(st_spn_dist_code);
                    if(BlkList.size()<=0)
                    {
                        //new loadBlockDatanew().execute();
                    }
                    else
                    {
                        setBlockData();
                    }

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });
        spn_block.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                int pos = position;
                if (pos > 0) {
                    st_spn_block_code = BlkList.get(pos - 1).getBlockCode();
                    st_spn_block = BlkList.get(pos - 1).getBlockName();

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }

        });

        btn_signUp.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //Toast.makeText(SignUpActivity.this, "Signup", Toast.LENGTH_SHORT).show();
                        SignUpEntry();
                    }

                });

        btn_cancel.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
//                        startActivity(intent);
                        finish();
                    }

                });
    }
    public void Initialization()
    {
        spn_dist = (Spinner)findViewById(R.id.spn_dist);
        spn_block =(Spinner)findViewById(R.id.spn_block);
        et_name = (EditText)findViewById(R.id.et_farmer_name);
        et_fname = (EditText)findViewById(R.id.et_father_husband);
        et_address = (EditText)findViewById(R.id.et_adress);
        et_mobile = (EditText)findViewById(R.id.et_mobile_number);
        et_designation =(EditText)findViewById(R.id.et_designation);
        et_confirm_password = (EditText)findViewById(R.id.et_conform_password);
        btn_signUp = (Button)findViewById(R.id.btn_signUp);
        btn_cancel = (Button)findViewById(R.id.reg_cancel);

    }

    public void loadDistrictSpinner() {
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        distList = dataBaseHelper.getDistrict();
        districtNameArray = new ArrayList<String>();
        districtNameArray.add("-select-");
        int i = 0;
        for (District district_list : distList) {
            districtNameArray.add(district_list.get_DistName());
            i++;
        }
        districtadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtNameArray);
        districtadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_dist.setAdapter(districtadapter);


    }

    private void loadBlockData(ArrayList<Block> pList) {
        blockstlist = new ArrayList<String>();
        blockstlist.add("-Select Block-");
        for (int i = 0; i < pList.size(); i++) {
            blockstlist.add(pList.get(i).getBlockName());
        }
        blockadapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, blockstlist);
        blockadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spn_block.setAdapter(blockadapter);

    }

    public void setBlockData() {
        DataBaseHelper placeData = new DataBaseHelper(SignUpActivity.this);
        BlkList = placeData.getBlock(st_spn_dist_code);
        if (BlkList.size() > 0)
            loadBlockData(BlkList);
    }


    public void SignUpEntry() {
        //long c = 0;
        setValue();
        //DataBaseHelper placeData = new DataBaseHelper(SignUpActivity.this);
        SignUp signUp = new SignUp();
        if (Validate()) {
            signUp.setDist_code(st_spn_dist_code);
            signUp.setBlock_code(st_spn_block_code);
            signUp.setName(st_et_name);
            signUp.setDesignation(st_Desig);
            //signUp.setFname(st_et_fname);
            signUp.setMobile(st_et_mobile);
            //signUp.setPassword(st_et_password);
            //setConfirm_password(st_et_confirm_password);

            new UPLOADDATA(signUp).execute();

        }

    }
    public void setValue()
    {
        st_et_name=et_name.getText().toString();
        //st_et_address=et_address.getText().toString();
        //st_et_fname=et_fname.getText().toString();
        st_et_mobile=et_mobile.getText().toString();
        st_Desig = et_designation.getText().toString();
        //st_et_password=et_password.getText().toString();
        //st_et_confirm_password=et_confirm_password.getText().toString();

    }

    private boolean Validate()
    {
        View focusview=null;
        boolean validate=true;
        if(TextUtils.isEmpty(st_et_name))
        {
            focusview=et_name;
            validate=false;
            et_name.setError("please enter name");

        }
        if(TextUtils.isEmpty(st_et_mobile))
        {
            focusview=et_mobile;
            validate=false;
            et_mobile.setError("please enter mobile number");

        }

        else if (st_et_mobile.length() != 10) {
            et_mobile.setError(getString(R.string.Invalid_Number));
            focusview = et_mobile;
            validate = false;
        }
        if(TextUtils.isEmpty(st_Desig))
        {
            focusview=et_designation;
            validate=false;
            et_designation.setError("please enter password");

        }

        /*else if (st_et_password.length() <= 5) {
            et_password.setError(getString(R.string.invalid_password));
            focusview = et_password;
            validate = false;
        }*/
//        if(TextUtils.isEmpty(st_et_confirm_password))
//        {
//            focusview=et_confirm_password;
//            validate=false;
//            et_confirm_password.setError("please enter confirm password");
//
//        }
//
//        else if (!(st_et_password.equals(st_et_confirm_password))) {
//            et_confirm_password.setError(getString(R.string.password_not_match));
//            focusview = et_confirm_password;
//            validate = false;
//        }

        if(st_spn_dist_code.equals(""))
        {
            focusview=spn_dist;
            validate=false;
            Toast.makeText(getApplicationContext(),"please select district", Toast.LENGTH_LONG).show();
        }
        if(st_spn_block_code.equals(""))
        {
            focusview=spn_block;
            validate=false;
            Toast.makeText(getApplicationContext(),"please select Block", Toast.LENGTH_LONG).show();
        }

        return validate;
    }

    /*   private class DISTRICTDATA extends AsyncTask<String, Void, ArrayList<District_list>> {
 
         private final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
 
         private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(SignUpActivity.this).create();
 
         @Override
         protected void onPreExecute() {
 
             this.dialog.setCanceledOnTouchOutside(false);
             this.dialog.setMessage("Loading...");
             this.dialog.show();
         }
 
         @Override
         protected ArrayList<District_list> doInBackground(String... param) {
 
 
             return WebServiceHelper.getDistrictData();
 
         }
 
         @Override
         protected void onPostExecute(ArrayList<District_list> result) {
             if (this.dialog.isShowing()) {
                 this.dialog.dismiss();
             }
 
             if (result != null) {
                 Log.d("Resultgfg", "" + result);
 
                 DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
 
 
                 long i = helper.setDistrictName(result);
                 if (i > 0) {
 
                     loadDistrictSpinner();
                     // Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
 
                 }
 
                 else
                     {
                     Toast.makeText(getApplicationContext(), "Server is Slow.Please Try later", Toast.LENGTH_SHORT).show();
                 }
 
             }
         }
     }*/
//    private class loadBlockDatanew extends AsyncTask<String, Void, ArrayList<BlockWeb>> {
//
//
//        String distCode = "";
//        ArrayList<BlockWeb> blocklist = new ArrayList<BlockWeb>();
//        private final ProgressDialog dialog = new ProgressDialog(
//                SignUpActivity.this);
//
//
//
//        @Override
//        protected void onPreExecute() {
//
//            this.dialog.setCanceledOnTouchOutside(false);
//            this.dialog.setMessage("Loading...");
//            this.dialog.show();
//        }
//
// //       @Override
////        protected ArrayList<BlockWeb> doInBackground(String... param) {
////
////            this.blocklist = WebServiceHelper.getBlockData(st_spn_dist_code);
////
////            return this.blocklist;
////        }
//
//        @Override
//        protected void onPostExecute(ArrayList<BlockWeb> result) {
//            if (this.dialog.isShowing()) {
//                this.dialog.dismiss();
//
//            }
//
//            if (result != null) {
//                if (result.size() > 0) {
//
//                    DataBaseHelper placeData = new DataBaseHelper(SignUpActivity.this);
//                    long i = placeData.setBlockDataLocal(result, this.distCode);
//                    if (i > 0) {
//                        setBlockData();
//                    }
//                } else {
//                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),
//                            Toast.LENGTH_LONG).show();
//
//                }
//            }
//
//        }
//
//    }



    private class UPLOADDATA extends AsyncTask<String, Void, String> {
        SignUp data;
        String _uid;
        private final ProgressDialog dialog = new ProgressDialog(SignUpActivity.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(SignUpActivity.this).create();


        UPLOADDATA(SignUp data) {
            this.data = data;

        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {


            String res = WebServiceHelper.completeSignup(this.data,imei,version);
            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue",""+result);
            if (result != null)
            {
                String string = result;
                String[] parts = string.split(",");
                String part1 = parts[0]; // 004-


                if(part1.equals("1"))
                {

                    Toast.makeText(getApplicationContext(), "सफलतापूर्वक पंजीकरण हो गया, कृपया अनुमोदन की प्रतीक्षा करें", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(SignUpActivity.this,LoginActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "पंजीकरण विफल हुआ, कृपया दूसरे नंबर से प्रयत्न करें", Toast.LENGTH_SHORT).show();
                }

            }

        }
    }
    private void getIMEI() {
        context = this;
        //Database Opening
        dataBaseHelper = new DataBaseHelper(SignUpActivity.this);
        dataBaseHelper = new DataBaseHelper(this);

        try {
            dataBaseHelper.createDataBase();
        } catch (IOException ioe) {

            throw new Error("Unable to create database");
        }
        try {

            dataBaseHelper.openDataBase();

        } catch (SQLException sqle) {

            throw sqle;

        }

        MarshmallowPermission permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permission.result == -1 || permission.result == 0) {
            try {
                tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

                if (tm != null)
                    imei = tm.getDeviceId();
            } catch (Exception e) {
            }
        } else if (permission.result == 1) {
            tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            if (tm != null) imei = tm.getDeviceId();
                    /* Intent i=new Intent(this,LoginActivity.class);
                     startActivity(i);
           	finish();*/
        }

        try {

            version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;


        } catch (PackageManager.NameNotFoundException e) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getIMEI();

    }

}
