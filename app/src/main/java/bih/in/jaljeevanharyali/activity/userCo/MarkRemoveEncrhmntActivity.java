package bih.in.jaljeevanharyali.activity.userCo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.DashboardActivity;
import bih.in.jaljeevanharyali.activity.PondInspectionActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;

public class MarkRemoveEncrhmntActivity extends Activity implements AdapterView.OnItemSelectedListener {

    TextView tv_rajaswa_thana, tv_district_name, tv_block_name, tv_village_name,tv_encrhmnt_start_date, tv_encrhmnt_end_date, tv_notice_date,tv_encrh_status,tv_encrh_type;
    TextView tv_start_date_title, tv_end_date_title, tv_notice_date_title, tv_notice_no_title;
    Spinner spin_encroachment_status, spin_encroachment_type;
    EditText et_notice_no;
    Button btn_mark_encrhmnt, btn_rmv_encrhmnt;
    LinearLayout ll_mark_encrhmnt, ll_remove_encrhmnt,ll_encroachment_type;

    String encroachmentTypeOption[] = {"-चयन करे-","कच्चा", "पक्का"};
    ArrayList<String> statusOfEncroachmentArray;

    ArrayAdapter encroachmentTypeAdaptor;
    DataBaseHelper dataBaseHelper;

    int id,dateNo;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;

    UserDetails userInfo;

    String panchayatName, panchayatCode, encrhmntPurpose, structureType;
    String encrhmntStatus,encrhmntStatusValue = "",encrhmntType,encrhmntTypeValue, encrhmntStartDate, encrhmntEndDate, noticeDate;
    Boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_remove_encrhmnt);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        setupUi();
        getUserDetail();

        btn_mark_encrhmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateMarkedData()){
                    updateMarkedEncroachmentDetail();
                }else{
                    Toast.makeText(MarkRemoveEncrhmntActivity.this, "Please filled all required field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_rmv_encrhmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateRemoveEncrhmntData()){
                    updateRemoveEncroachmentDetail();
                }else{
                    Toast.makeText(MarkRemoveEncrhmntActivity.this, "Please filled all required field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initiliaze()
    {
        tv_rajaswa_thana = (TextView) findViewById(R.id.tv_rajaswa_thana);
        tv_block_name = (TextView) findViewById(R.id.tv_block_name);
        tv_district_name = (TextView) findViewById(R.id.tv_district_name);
        tv_village_name = (TextView) findViewById(R.id.tv_village_name);

        tv_encrhmnt_start_date = (TextView)findViewById(R.id.tv_encrhmnt_start_date);
        tv_encrhmnt_end_date = (TextView)findViewById(R.id.tv_encrhmnt_end_date);
        tv_notice_date = (TextView)findViewById(R.id.tv_notice_date);
        tv_encrh_status = (TextView)findViewById(R.id.tv_encrh_status);
        tv_encrh_type = (TextView)findViewById(R.id.tv_encrh_type);

        tv_start_date_title = (TextView)findViewById(R.id.tv_start_date_title);
        tv_end_date_title = (TextView)findViewById(R.id.tv_end_date_title);
        tv_notice_date_title = (TextView)findViewById(R.id.tv_notice_date_title);
        tv_notice_no_title = (TextView)findViewById(R.id.tv_notice_no_title);

        ll_mark_encrhmnt = (LinearLayout) findViewById(R.id.ll_mark_encrhmnt);
        ll_remove_encrhmnt = (LinearLayout) findViewById(R.id.ll_remove_encrhmnt);
        ll_encroachment_type = (LinearLayout) findViewById(R.id.ll_encroachment_type);

        et_notice_no = (EditText)findViewById(R.id.et_notice_no);

        spin_encroachment_status = (Spinner)findViewById(R.id.spin_encroachment_status);
        spin_encroachment_type = (Spinner)findViewById(R.id.spin_encroachment_type);

        spin_encroachment_status.setOnItemSelectedListener(this);
        spin_encroachment_type.setOnItemSelectedListener(this);

        btn_mark_encrhmnt = (Button) findViewById(R.id.btn_mark_encrhmnt);
        btn_rmv_encrhmnt = (Button) findViewById(R.id.btn_rmv_encrhmnt);
    }

    private void setupUi(){
        initiliaze();

        extractDataFromIntent();

        if(encrhmntPurpose.equals("mark")){
            ll_mark_encrhmnt.setVisibility(View.VISIBLE);
            ll_remove_encrhmnt.setVisibility(View.GONE);

            if(!isEdit){
                loadSpinnerYesNo();

                ll_encroachment_type.setVisibility(View.GONE);

                encroachmentTypeAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, encroachmentTypeOption);
                encroachmentTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_encroachment_type.setAdapter(encroachmentTypeAdaptor);
            }

        }else{
            ll_mark_encrhmnt.setVisibility(View.GONE);
            ll_remove_encrhmnt.setVisibility(View.VISIBLE);
        }

    }

    private void extractDataFromIntent(){
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        panchayatName = intent.getStringExtra("panchayatName");
        panchayatCode = intent.getStringExtra("panchayatCode");
        encrhmntPurpose = intent.getStringExtra("encrhmntType");
        structureType = intent.getStringExtra("structureType");
        isEdit = intent.getBooleanExtra("edit", false);

        PondEncroachmentEntity info = dataBaseHelper.getPondEncrhmntDetails(String.valueOf(id), structureType);

        tv_rajaswa_thana.setText(info.getRajswaThanaNumber());
        tv_district_name.setText(info.getDistName());
        tv_block_name.setText(info.getBlockName());
        tv_village_name.setText(info.getVILLNAME());

        if(isEdit){
            if(info.getUploadType().equals("M")){
                loadSpinnerYesNo();

                encroachmentTypeAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, encroachmentTypeOption);
                encroachmentTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spin_encroachment_type.setAdapter(encroachmentTypeAdaptor);

                encrhmntStatusValue = info.getStatus_Of_Encroachment();
                encrhmntTypeValue = info.getType_Of_Encroachment();

                spin_encroachment_status.setSelection(encrhmntStatusValue.contains("Y") ? 1 : 2);
                if(encrhmntStatusValue.contains("Y")){
                    spin_encroachment_type.setSelection(encrhmntTypeValue.contains("K") ? 1 : 2);
                }
            }else{
                encrhmntStartDate = info.getEnchrochmentStartDate();
                encrhmntEndDate = info.getEnchrochmentEndDate();
                noticeDate = info.getNoticeDate();

                tv_encrhmnt_start_date.setText(encrhmntStartDate);
                tv_encrhmnt_end_date.setText(encrhmntEndDate);
                tv_notice_date.setText(noticeDate);
                et_notice_no.setText(info.getNoticeNo());
            }
        }
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dataBaseHelper.getUserDetails(username.toLowerCase(), password);
    }

    public ArrayList<String> loadSpinnerYesNo() {
        ArrayList<String> list = new ArrayList<String>();
        list.add(getResources().getString(R.string.yes));
        list.add(getResources().getString(R.string.no));

        ArrayList<String> sList = new ArrayList<String>();


        if (list.size() > 0) {
            String hints = "-" + getResources().getString(R.string.select_spinner_hint) + "-";
            sList.add(hints);
        }
        for (int i = 0; i < list.size(); i++) {
            sList.add(list.get(i));
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_encroachment_status.setAdapter(spinnerAdapter);
        statusOfEncroachmentArray = sList;
        return list;
    }

    private boolean validateMarkedData() {
        View focusView = null;
        boolean validate = true;

        if (encrhmntStatus == null) {
            tv_encrh_status.setError(getString(R.string.fieldRequired));
            focusView = tv_encrh_status;
            validate = false;
        }

        if(encrhmntStatusValue.contains("Y")){
            if (encrhmntType == null) {
                tv_encrh_type.setError(getString(R.string.fieldRequired));
                focusView = tv_encrh_type;
                validate = false;
            }
        }

        if (!validate) focusView.requestFocus();

        return validate;
    }

    private boolean validateRemoveEncrhmntData() {
        View focusView = null;
        boolean validate = true;

        if (encrhmntStartDate == null) {
            tv_start_date_title.setError(getString(R.string.fieldRequired));
            focusView = tv_start_date_title;
            validate = false;
        }

        if(encrhmntEndDate == null){
            tv_end_date_title.setError(getString(R.string.fieldRequired));
            focusView = tv_end_date_title;
            validate = false;
        }

        if(noticeDate == null){
            tv_notice_date_title.setError(getString(R.string.fieldRequired));
            focusView = tv_notice_date_title;
            validate = false;
        }

        if(et_notice_no.getText().toString().equals("")){
            et_notice_no.setError(getString(R.string.fieldRequired));
            focusView = et_notice_no;
            validate = false;
    }
        if (!validate) focusView.requestFocus();

        return validate;
    }

    public void ShowDialog() {
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
        datedialog.show();
    }


    DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            mYear = selectedYear;
            mMonth = selectedMonth;
            mDay = selectedDay;
            String ds = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
            ds = ds.replace("/", "-");
            String[] separated = ds.split(" ");

            try {
                // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String currentTimeString = sdf.getTimeInstance().format(new Date());
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String newString = currentTimeString.replace("A.M.", "");

                String smDay = "" + mDay, smMonth = "" + (mMonth + 1);
                if (mDay < 10) {
                    smDay = "0" + mDay;//Integer.parseInt("0" + mDay);
                }
                if ((mMonth + 1) < 10) {
                    smMonth = "0" + (mMonth + 1);
                }

                if (dateNo == 1){
                    encrhmntStartDate = mYear + "-"+ smMonth + "-" + smDay;
                    tv_encrhmnt_start_date.setText(encrhmntStartDate);
                }else if(dateNo == 2){
                    encrhmntEndDate = mYear + "-"+ smMonth + "-" + smDay;
                    tv_encrhmnt_end_date.setText(encrhmntEndDate);
                }else if(dateNo == 3){
                    noticeDate = mYear + "-"+ smMonth + "-" + smDay;
                    tv_notice_date.setText(noticeDate);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    public void onStartEncrhmntDate(View v){
        dateNo = 1;
        ShowDialog();
    }

    public void onEndEncrhmntDate(View v){
        dateNo = 2;
        ShowDialog();
    }

    public void onNoticeDate(View v){
        dateNo = 3;
        ShowDialog();
    }

    public String getAppVersion(){
        try {

            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            return version;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void updateMarkedEncroachmentDetail(){
        long result = 0;

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        PondEncroachmentEntity entity = new PondEncroachmentEntity();

        entity.setId(id);
        entity.setStatus_Of_Encroachment(encrhmntStatusValue);
        entity.setType_Of_Encroachment(encrhmntTypeValue);
        entity.setVerified_Date(currentDate);
        entity.setVerified_By(userInfo.getUserID());
        entity.setAppVersion(getAppVersion());
        entity.setUploadType("M");
        entity.setIsUpdated("1");

        result = new DataBaseHelper(this).UpdateEncrhmntDetail(entity, structureType);

        if(result > 0){
            Toast.makeText(getApplicationContext(),"डाटा सफलतापूर्वक सेव हो गया",Toast.LENGTH_LONG).show();
            Intent iUserHome = new Intent(getApplicationContext(), DashboardCoActivity.class);
            iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(iUserHome);
        }else{
            Toast.makeText(getApplicationContext(),"डाटा सेव नहीं हुआ", Toast.LENGTH_LONG).show();
        }

    }

    private void updateRemoveEncroachmentDetail(){
        long result = 0;

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        PondEncroachmentEntity entity = new PondEncroachmentEntity();

        entity.setId(id);
        entity.setEnchrochmentStartDate(encrhmntStartDate);
        entity.setEnchrochmentEndDate(encrhmntEndDate);
        entity.setNoticeDate(noticeDate);
        entity.setNoticeNo(et_notice_no.getText().toString());
        entity.setVerified_By(userInfo.getUserID());
        entity.setVerified_Date(currentDate);
        entity.setAppVersion(getAppVersion());
        entity.setUploadType("R");
        entity.setIsUpdated("1");

        result = new DataBaseHelper(this).UpdateEncrhmntDetail(entity, structureType);

        if(result > 0){
            Toast.makeText(getApplicationContext(),"डाटा सफलतापूर्वक सेव हो गया",Toast.LENGTH_LONG).show();
            Intent iUserHome = new Intent(getApplicationContext(), DashboardCoActivity.class);
            iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(iUserHome);
        }else{
            Toast.makeText(getApplicationContext(),"डाटा सेव नहीं हुआ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_encroachment_status:
                if (position > 0) {
                    tv_encrh_status.setError(null);
                    encrhmntStatus=statusOfEncroachmentArray.get(position).toString();

                    if(encrhmntStatus.equals("हाँ")){
                        encrhmntStatusValue="Y";
                        ll_encroachment_type.setVisibility(View.VISIBLE);


                    }
                    else if(encrhmntStatus.equals("नहीं")){
                        encrhmntStatusValue="N";
                        ll_encroachment_type.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.spin_encroachment_type:
                if (position > 0) {
                    tv_encrh_type.setError(null);
                    encrhmntType=encroachmentTypeOption[position].toString();

                    if(encrhmntType.equals("कच्चा")){
                        encrhmntTypeValue="K";
                    }
                    else if(encrhmntType.equals("पक्का")){
                        encrhmntTypeValue="P";
                    }
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please select", Toast.LENGTH_SHORT).show();
    }
}
