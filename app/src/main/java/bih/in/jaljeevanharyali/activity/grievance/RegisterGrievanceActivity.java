package bih.in.jaljeevanharyali.activity.grievance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.LoginActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.GrievanceEntryDetail;
import bih.in.jaljeevanharyali.entity.UploadComplainEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.CommonPref;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class RegisterGrievanceActivity extends Activity {

    TextView tv_district,tv_block,tv_panchayat,tv_village,tv_structure,tv_related,tv_name,tv_griv_type;
    Spinner spinnerward;
    ArrayList<ward> WardList = new ArrayList<ward>();
    ArrayAdapter<String> wardadapter;
    String _varwardName="",_varwardID="",_varwardSetID="";
    DataBaseHelper localdbhelper;
    ProgressDialog pd;
    String mobileno="";
    EditText txt_complain;
    Button btn_proceed;
    ProgressDialog dialog;

    GrievanceEntryDetail entryDetail;
    String complain="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_grievance);

        initialization();

        extractDataFromIntent();

        spinnerward.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub
                if (arg2 > 0) {
                    ward wrd = WardList.get(arg2 - 1);
                    _varwardID = wrd.getWardCode();
                    Log.e("_varwardID",_varwardID);
                }
                else if(arg2==0)
                {

                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        btn_proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String isvalid = validateRecordBeforeSaving();
                if (isvalid.equalsIgnoreCase("yes")) {

//                    UploadComplainEntity complain = new UploadComplainEntity();
//                    complain.setMobileNo(userInfo.getMobileNo());
//                    complain.setDistrictCode(userInfo.getDistrictCode());
//                    complain.setBlockCode(userInfo.getBlockCode());
//                    complain.setPanchayatCode(userInfo.getPanchaayatCode());
//                    complain.setWardCode(_varwardID);
//                    complain.setComplainRemarks(txt_complain.getText().toString());
                    complain=txt_complain.getText().toString();
                    Intent i = new Intent(RegisterGrievanceActivity.this, GrievanceMultiplePhotoActivity.class);
                    // i.putExtra("EDIT", wantToEdit);
                    i.putExtra("remarks", complain);

                    i.putExtra("data", entryDetail);
                    startActivity(i);
                }
            }

        });
    }

    public void initialization(){
        pd = new ProgressDialog(this);
        pd.setCanceledOnTouchOutside(false);

        localdbhelper= new DataBaseHelper(this);

        tv_district=(TextView)findViewById(R.id.tv_district);
        tv_block=(TextView)findViewById(R.id.tv_block);
        tv_panchayat=(TextView)findViewById(R.id.tv_panchayat);
        tv_village=(TextView)findViewById(R.id.tv_village);
        tv_structure=(TextView)findViewById(R.id.tv_structure);

        tv_related=(TextView)findViewById(R.id.tv_related);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_griv_type=(TextView)findViewById(R.id.tv_griv_type);

        spinnerward=(Spinner) findViewById(R.id.spinnerward);
        txt_complain=(EditText) findViewById(R.id.txt_complain);

        btn_proceed=(Button) findViewById(R.id.btn_proceed);

        dialog = new ProgressDialog(this);
    }

    public void extractDataFromIntent(){
        entryDetail = (GrievanceEntryDetail)getIntent().getSerializableExtra("data");

        tv_district.setText(entryDetail.getDistName());
        tv_block.setText(entryDetail.getBlockName());
        tv_panchayat.setText(entryDetail.getPanchayatName());
        tv_village.setText(entryDetail.getVillageName());
        tv_structure.setText(entryDetail.getWork_StructureName());

        if (entryDetail.getType().equals("sch")){
            tv_related.setText("SCHEME");
        }
        else if (entryDetail.getType().equals("str")){
            tv_related.setText("STRUCTURE");
        }else {
            tv_related.setText(entryDetail.getType().toUpperCase());
            tv_griv_type.setText(entryDetail.getType().equals(AppConstant.BUILDING) ? "भवन का नाम" : "पौधशाला का नाम");
        }


        tv_name.setText(entryDetail.getWork_StructureName());
        Log.e("type", entryDetail.getType());
    }



    public void loadLocalWardINSpinnerdata() {
        localdbhelper = new DataBaseHelper(this);
        //WardList=localdbhelper.getWardList(userInfo.getPanchaayatCode());

        if(WardList.size()>0 ){
            loadWardSpinnerData(WardList);
        }else {
            //new SyncWardData().execute("");
        }
    }

    private void loadWardSpinnerData(ArrayList<ward> pList) {
        ArrayList<String> StringList=new ArrayList<String>();
        StringList.add("-चुनें-");
        String setWID="0";
        for(int i=0;i<pList.size();i++)
        {
            StringList.add(pList.get(i).getWardname());
            if(_varwardID.equalsIgnoreCase(pList.get(i).getWardCode()))
            {
                setWID=""+ (i+1);
            }
        }
        //wardadapter=new ArrayAdapter(this,R.layout.dropdownlist,StringList);
        wardadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, StringList);
        wardadapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerward.setAdapter(wardadapter);
    }

    private String validateRecordBeforeSaving() {
        String isvalid = "yes";

//        if((spinnerward != null && spinnerward.getSelectedItem() !=null )) {
//            if ((String) spinnerward.getSelectedItem() != "-चुनें-") {
//                isvalid = "yes";
//            } else {
//                Toast.makeText(this, "कृपया वार्ड का चयन करें..", Toast.LENGTH_LONG).show();
//
//                spinnerward.requestFocus();
//                return "no";
//            }
//        }

        if (txt_complain.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "कृपया प्रतिक्रिया दर्ज करें.", Toast.LENGTH_LONG).show();
            txt_complain.requestFocus();
            return "no";
        }

        return isvalid;
    }

//    private class SyncWardData extends AsyncTask<String, Void, ArrayList<ward>> {
//        //private final ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
//
//        @Override
//        protected void onPreExecute() {
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setMessage("वार्ड लोड हो रहा है...");
//            dialog.show();
//        }
//
//        @Override
//        protected ArrayList<ward> doInBackground(String...arg) {
//
//
//            return WebServiceHelper.getWardListData(userInfo.getPanchaayatCode());
//
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<ward> result) {
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
//
//            if (result.size() > 0){
//                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
//
//                long i= helper.setWardDataToLocal(result);
//
//                if(i>0)
//                {
//                    Toast.makeText(getApplicationContext(), "Ward Data Synced Successfully",Toast.LENGTH_SHORT).show();
//                }
//                else
//                {
//                    Toast.makeText(getApplicationContext(), "Failed to update ward",Toast.LENGTH_SHORT).show();
//                }
//
//                //WardList = helper.getWardList(userInfo.getPanchaayatCode());
//                loadWardSpinnerData(WardList);
//            }else{
//                showAlertDialog();
//            }
//
//
//
//        }
//    }

    public void showAlertDialog(){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo1)
                .setTitle("वार्ड नहीं मिला!!")
                .setMessage("इस पंचायत का वार्ड जोड़ने के लिए कृपया तकनीकी सहायता टीम से संपर्क करें")
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        RegisterGrievanceActivity.this.finish();
                    }
                })
                .show();
    }



}
