package bih.in.jaljeevanharyali.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.SQLException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.Krinawayan;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondLakeDepartmentEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaTypeEntity;
import bih.in.jaljeevanharyali.entity.Sub_abyb;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.WardList;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.CommonPref;

public class ManregaActivity extends Activity implements AdapterView.OnItemSelectedListener{
    Spinner spn_panchayat,spn_ward,spn_Kriwaran,spn_abyb,spn_Sub_abyb,spn_sanrachna_type,spin_village;
    EditText et_sanrachna,et_prakalit,et_mis_yojna,et_remark,et_bhawan,et_Kriwaran,et_sanrachna_name,et_other;
    LinearLayout ll_bhawan_name,ll_sub_abyb,ll_sanrachna_type,ll_sanrachna_name,ll_village,ll_other;
    RelativeLayout rl_swikriti_tithi,rl_anumodan_tithi;
    TextView tv_panchayat,tv_ward,tv_awaya_name,district_name,block_name,tv_sanrachna_name,tv_village;
    TextView et_anumodan_date,et_prashinik_date,tv_other;
    Button btn_next;
    ImageView img_cal11,img_cal1;
    ArrayList<ward> wardList = new ArrayList<ward>();
    ArrayList<SanrachnaTypeEntity> sanrachnaTypeList = new ArrayList<SanrachnaTypeEntity>();

    DataBaseHelper dataBaseHelper;
    ArrayList<String> villageNameArray;
    ArrayAdapter<String> villageAdapter;
    ArrayList<String> wardNameArray;
    ArrayList<String> SanrachnaNameArray;
    ArrayAdapter<String> wardAdapter;
    ArrayAdapter<String> sanrachnaAdapter;
    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<Krinawayan> krinawayanList = new ArrayList<Krinawayan>();
    ArrayList<Abyab> abyabList = new ArrayList<Abyab>();
    ArrayList<Sub_abyb> sub_abyabList = new ArrayList<Sub_abyb>();
    ArrayList<VillageListEntity> villageList = new ArrayList<VillageListEntity>();

    ArrayList<String> panchayatNameArray;
    ArrayList<String> krinawayanNameArray;
    ArrayList<String> abyabNameArray;
    ArrayList<String> SubabyabNameArray;
    ArrayAdapter<String> panchayatadapter;
    ArrayAdapter<String> krinawayanadapter;
    ArrayAdapter<String>abyabadapter;
    ArrayAdapter<String>Subabyabadapter;
    String Pan_Id="",Pan_Name="",Ward_Id="",Ward_Name="",Village_Id="",Village_Name="",Kriwaran_Id="1",Kriwaran_Name="ग्रामीण विकास विभाग",abyb_Id="",abyb_Name="",Sub_abyb_Id="",Sub_abyb_Name="", userId =
            "", sanrachnaTypeId = "", sanrachnaTypeName;
    int dateNo,id;

    private String s1_data = "";

    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;

    String districtName, districtId, blockName, blockId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manrega);

        dataBaseHelper=new DataBaseHelper(ManregaActivity.this);

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

        Initialization();

        loadPanchayatSpinner(CommonPref.getUserDetails(ManregaActivity.this).getBlockCode());
        //loadKrinawayanSpinner();
        loadabyabSpinner(Kriwaran_Id);

        extractDataFromIntent();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    UpdateManreagSchemeDetail();
                }else{
                    Toast.makeText(ManregaActivity.this, "कृपया सभी अनिवार्य फ़ील्ड भरें", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void  extractDataFromIntent(){
        districtName = getIntent().getStringExtra("DistrictName");
        districtId = getIntent().getStringExtra("DistrictCode");
        blockId = getIntent().getStringExtra("blockCode");
        blockName = getIntent().getStringExtra("blockName");
        userId = getIntent().getStringExtra("userId");
        id = getIntent().getIntExtra("id", 0);

        district_name.setText(districtName);
        block_name.setText(blockName);

        if(id >0){
            ManregaSchemeDetail info = dataBaseHelper.getManregaDetail(String.valueOf(id));

            Pan_Id= info.getPanchayat_Id();
            Pan_Name= info.getPanchayatName();
            Ward_Id= info.getWard_Id();
            Ward_Name=info.getWard_Name();
            Kriwaran_Id= info.getExecution_Dept_ID();
            Kriwaran_Name="ग्रामीण विकास विभाग";
            abyb_Id= info.getSub_Exect_Dept_ID();
            abyb_Name= info.getSubDivName();
            Sub_abyb_Id= info.getSub_Sub_Exect_Dept_ID();
            sanrachnaTypeId = info.getWork_Structure_Type();
            Village_Id = info.getVillage_Id();

            if (info.getWork_Structure_Type_Name() != null && info.getWork_Structure_Type_Name().equals("अन्य")){
                ll_sanrachna_name.setVisibility(View.VISIBLE);
                et_sanrachna_name.setText(info.getWork_Structure_Type_Other_Name());
            }else{
                ll_sanrachna_name.setVisibility(View.GONE);
            }
            //et_krya.setText(info.getWork_Structure_Type());
            et_sanrachna.setText(info.getMeasurement_Of_Structure());
            et_prakalit.setText(info.getEstimated_Amount());
            et_mis_yojna.setText(info.getScheme_Code());
            et_remark.setText(info.getRemarks());
            et_anumodan_date.setText(info.getApproval_Date());
            et_prashinik_date.setText(info.getAdministrative_Approval_Date());

            loadWardData(Pan_Id);

            if (abyb_Id.equals("4")){
                et_other.setText(info.getOther_Name());
            }

            if (abyb_Id.equals("5")){
                et_bhawan.setText(info.getSub_Exect_Value());
            }

            spn_panchayat.setSelection((((ArrayAdapter<String>) spn_panchayat.getAdapter()).getPosition(Pan_Name)));


            spn_ward.setSelection((((ArrayAdapter<String>) spn_ward.getAdapter()).getPosition(Ward_Name)));
            spn_abyb.setSelection((((ArrayAdapter<String>) spn_abyb.getAdapter()).getPosition(abyb_Name)));

            if(abyb_Id.equals("6")){
                loadSubabyabSpinner(abyb_Id);
                spn_Sub_abyb.setSelection((((ArrayAdapter<String>) spn_Sub_abyb.getAdapter()).getPosition(Sub_abyb_Id)));

            }
        }

    }

    public  void  Initialization(){
        spn_panchayat=(Spinner)findViewById(R.id.spn_panchayat);
        spn_ward=(Spinner)findViewById(R.id.spn_ward);
        spn_Kriwaran=(Spinner)findViewById(R.id.spn_Kriwaran);
        spn_abyb=(Spinner)findViewById(R.id.spn_abyb);
        spn_Sub_abyb=(Spinner)findViewById(R.id.spn_Sub_abyb);
        spn_sanrachna_type=(Spinner)findViewById(R.id.spn_sanrachna_type);
        spin_village=findViewById(R.id.spin_village);

        //et_krya=(EditText) findViewById(R.id.et_krya);
        et_sanrachna=(EditText) findViewById(R.id.et_sanrachna);
        et_prakalit=(EditText) findViewById(R.id.et_prakalit);
        et_mis_yojna=(EditText) findViewById(R.id.et_mis_yojna);
        et_remark=(EditText) findViewById(R.id.et_remark);
        et_bhawan=(EditText) findViewById(R.id.et_bhawan);
        et_Kriwaran=(EditText) findViewById(R.id.et_Kriwaran);
        et_sanrachna_name=(EditText) findViewById(R.id.et_sanrachna_name);
        et_other=(EditText) findViewById(R.id.et_other);

        et_Kriwaran.setEnabled(false);

        et_anumodan_date=(TextView) findViewById(R.id.et_anumodan_date);
        et_prashinik_date=(TextView) findViewById(R.id.et_prashinik_date);

        tv_village=findViewById(R.id.tv_village);
        tv_panchayat=(TextView) findViewById(R.id.tv_panchayat);
        tv_ward=(TextView) findViewById(R.id.tv_ward);
        tv_awaya_name=(TextView) findViewById(R.id.tv_awaya_name);
        tv_sanrachna_name=(TextView) findViewById(R.id.tv_sanrachna_name);
        tv_other=(TextView) findViewById(R.id.tv_other);

        district_name=(TextView) findViewById(R.id.district_name);
        block_name=(TextView) findViewById(R.id.block_name);

        btn_next=(Button) findViewById(R.id.btn_next);

        img_cal1=(ImageView) findViewById(R.id.img_cal1);
        img_cal11=(ImageView) findViewById(R.id.img_cal11);

        ll_bhawan_name = (LinearLayout) findViewById(R.id.ll_bhawan_name);
        ll_sub_abyb = (LinearLayout) findViewById(R.id.ll_sub_abyb);
        ll_sanrachna_type = (LinearLayout) findViewById(R.id.ll_sanrachna_type);
        ll_sanrachna_name = (LinearLayout) findViewById(R.id.ll_sanrachna_name);
        ll_village=findViewById(R.id.ll_village);
        ll_other=findViewById(R.id.ll_other);

        ll_bhawan_name.setVisibility(View.GONE);
        ll_sub_abyb.setVisibility(View.GONE);
        ll_sanrachna_name.setVisibility(View.GONE);

        spin_village.setOnItemSelectedListener(this);
        spn_panchayat.setOnItemSelectedListener(this);
        spn_ward.setOnItemSelectedListener(this);
        spn_Kriwaran.setOnItemSelectedListener(this);
        spn_abyb.setOnItemSelectedListener(this);
        spn_Sub_abyb.setOnItemSelectedListener(this);
        spn_sanrachna_type.setOnItemSelectedListener(this);
    }


    private void UpdateManreagSchemeDetail(){
        long result = 0;

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ManregaSchemeDetail manregaSchemeDetail=new ManregaSchemeDetail();

        manregaSchemeDetail.setId(id);
        manregaSchemeDetail.setExecution_Dept_ID(Kriwaran_Id);
        manregaSchemeDetail.setExectDeptName(Kriwaran_Name);
        manregaSchemeDetail.setSub_Exect_Dept_ID(abyb_Id);
        manregaSchemeDetail.setSubDivName(abyb_Name);
        manregaSchemeDetail.setSub_Sub_Exect_Dept_ID(Sub_abyb_Id);
        manregaSchemeDetail.setSub_Sub_Exec_DeptName(Sub_abyb_Name);
        manregaSchemeDetail.setDistrict_Name(districtName);
        manregaSchemeDetail.setDistrict_Id(districtId);
        manregaSchemeDetail.setBlock_ID(blockId);
        manregaSchemeDetail.setBlock_Name(blockName);
        manregaSchemeDetail.setPanchayat_Id(Pan_Id);
        manregaSchemeDetail.setPanchayatName(Pan_Name);
        manregaSchemeDetail.setUrban_Area_Id("");
        manregaSchemeDetail.setWard_Id(Ward_Id);
        manregaSchemeDetail.setWard_Name(Ward_Name);
        manregaSchemeDetail.setVillage_Id(Village_Id);
        manregaSchemeDetail.setVillage_Name(Village_Name);

        manregaSchemeDetail.setWork_Structure_Type(sanrachnaTypeId);
        manregaSchemeDetail.setWork_Structure_Type_Name(sanrachnaTypeName);
        manregaSchemeDetail.setWork_Structure_Type_Other_Name(et_sanrachna_name.getText().toString());
        manregaSchemeDetail.setOther_Name(et_other.getText().toString());
        manregaSchemeDetail.setMeasurement_Of_Structure(et_sanrachna.getText().toString());
        manregaSchemeDetail.setEstimated_Amount(et_prakalit.getText().toString());
        manregaSchemeDetail.setApproval_Date(et_anumodan_date.getText().toString());
        manregaSchemeDetail.setAdministrative_Approval_Date(et_prashinik_date.getText().toString());
        manregaSchemeDetail.setScheme_Code(et_mis_yojna.getText().toString());
        manregaSchemeDetail.setRemarks(et_remark.getText().toString());
        manregaSchemeDetail.setSub_Exect_Value(et_bhawan.getText().toString());
        manregaSchemeDetail.setCreatedBy(userId);
        manregaSchemeDetail.setCreatedDate(currentDate);
        manregaSchemeDetail.setApp_Version(getAppVersion());


        result = new DataBaseHelper(this).InsertManreagaSchemeDetail(manregaSchemeDetail);
        String type = "new";

        if(id > 0){
            type = "edit";
        }

        if (result > 0) {

            Toast.makeText(getApplicationContext(),"डाटा सफलतापूर्वक सेव हो गया",Toast.LENGTH_LONG).show();

            Intent intent = new Intent(this, ManregaPhotoActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d("ggeuhshd",""+result);
            intent.putExtra("KEY_PID", id > 0 ? id : (int) result);
            intent.putExtra("pupose", type);
            intent.putExtra("isOpen", s1_data);
            startActivity(intent);
            finish();
        }else {
            Toast.makeText(getApplicationContext(),"डाटा सेव नहीं हुआ", Toast.LENGTH_LONG).show();
        }

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spn_panchayat:
                int pos = position;
                if (pos > 0) {
                    PanchayatData panchayatEntity = panchayatList.get(pos - 1);

                    Pan_Id = panchayatEntity.getPcode();
                    Pan_Name = panchayatEntity.getPname();
                    loadVillageData(Pan_Id);
                    //loadWardData(Pan_Id);
                }
                break;

            case R.id.spn_ward:
                if (position > 0) {
                    ward ward = wardList.get(position - 1);
                    Ward_Id = ward.getWardCode();
                    Ward_Name = ward.getWardname();


                    //Toast.makeText(PondInspectionActivity.this, pondType, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.spin_village:
                if (position > 0) {
                    VillageListEntity village = villageList.get(position - 1);
                    Village_Id = village.getVillCode();
                    Village_Name = village.getVillName();


                    //Toast.makeText(this, ward.getPanchayatCode(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.spn_Kriwaran:
                if (position > 0) {
                    Krinawayan krinawayan = krinawayanList.get(position - 1);
                    Kriwaran_Id = krinawayan.getKrinawayan_Code();
                    Kriwaran_Name = krinawayan.getKrinawayan_name();

//                    loadabyabSpinner(Kriwaran_Id);

                }
                break;

            case R.id.spn_abyb:
                if (position > 0) {
                    Abyab abyab = abyabList.get(position - 1);
                    abyb_Id = abyab.getAbyab_Code();
                    abyb_Name = abyab.getAbyab_name();
                    //Toast.makeText(this, abyb_Id, Toast.LENGTH_SHORT).show();

                    if (abyb_Id.equals("4")){
                        ll_other.setVisibility(View.VISIBLE);
                    }else{
                        ll_other.setVisibility(View.GONE);
                    }

                    if (abyb_Id.equals("5")){
                        ll_bhawan_name.setVisibility(View.VISIBLE);
                    }else{
                        ll_bhawan_name.setVisibility(View.GONE);
                    }

                    if(abyb_Id.equals("6")){
                        ll_sub_abyb.setVisibility(View.VISIBLE);
                        loadSubabyabSpinner(abyb_Id);
                    }else{
                        ll_sub_abyb.setVisibility(View.GONE);
                    }

                    loadSanrachnaTypeData(abyb_Id);

                }
                break;

            case R.id.spn_sanrachna_type:
                if (position > 0) {
                    SanrachnaTypeEntity type = sanrachnaTypeList.get(position - 1);
                    sanrachnaTypeId = type.getSancrachnaId();
                    sanrachnaTypeName = type.getSancrachnaName();
                    String subExecId = type.getSub_Execution_DeptID();
                    if (type.getSancrachnaName().equals("अन्य")){
                        ll_sanrachna_name.setVisibility(View.VISIBLE);
                    }else {
                        ll_sanrachna_name.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.spn_Sub_abyb:
                if (position > 0) {
                    Sub_abyb abyab = sub_abyabList.get(position - 1);
                    Sub_abyb_Id = abyab.getSub_abyb_Code();
                    Sub_abyb_Name = abyab.getSub_abyb_name();
                }
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void loadSanrachnaTypeData(String subExecID) {

        sanrachnaTypeList = dataBaseHelper.getSanrachnaTypeList(subExecID);
        if(sanrachnaTypeList.size() > 0){
            ll_sanrachna_type.setVisibility(View.VISIBLE);
            SanrachnaNameArray = new ArrayList<String>();
            SanrachnaNameArray.add("-वार्ड का चयन करे-");
            int i = 0, setId= 0;
            for (SanrachnaTypeEntity dept_list : sanrachnaTypeList) {
                SanrachnaNameArray.add(dept_list.getSancrachnaName());
                if(sanrachnaTypeId.equals(dept_list.getSancrachnaId())){
                    Log.e("ID: ", dept_list.getSancrachnaId());
                    setId= i;
                }
                i++;
            }
            sanrachnaAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SanrachnaNameArray);
            sanrachnaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spn_sanrachna_type.setAdapter(sanrachnaAdapter);

            if(id > 0){
                spn_sanrachna_type.setSelection(setId+1);
                //Toast.makeText(this, String.valueOf(setId), Toast.LENGTH_SHORT).show();
            }
        }else{
            ll_sanrachna_type.setVisibility(View.GONE);
            ll_sanrachna_name.setVisibility(View.GONE);
        }

    }

    public void loadWardData(String pan_Code) {

        wardList = dataBaseHelper.getWardList(pan_Code);
        wardNameArray = new ArrayList<String>();
        wardNameArray.add("-वार्ड का चयन करे-");
        int i = 0, setId= 0;
        for (ward dept_list : wardList) {
            wardNameArray.add(dept_list.getWardname());
            if(Ward_Id.equals(dept_list.getWardCode())){
                setId= i;
            }
            i++;
        }
        wardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wardNameArray);
        wardAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_ward.setAdapter(wardAdapter);

        if(id > 0){
            spn_ward.setSelection(setId+1);
        }
    }

    public void loadVillageData(String pan_Code) {

        villageList = dataBaseHelper.getVillageList(pan_Code);
        villageNameArray = new ArrayList<String>();
        villageNameArray.add("-गाँव का चयन करे-");
        int i = 0, setId= 0;
        for (VillageListEntity dept_list : villageList) {
            villageNameArray.add(dept_list.getVillName());
            if(Village_Id.equals(dept_list.getVillCode())){
                setId= i;
            }
            i++;
        }
        villageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, villageNameArray);
        villageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_village.setAdapter(villageAdapter);

        if(id > 0){
            spin_village.setSelection(setId+1);
        }
    }

    public void loadPanchayatSpinner(String blockCode) {


        panchayatList = dataBaseHelper.getPanchayt(blockCode);
        panchayatNameArray = new ArrayList<String>();
        panchayatNameArray.add("-select-");
        int i = 0;
        for (PanchayatData panchayat_list : panchayatList) {
            panchayatNameArray.add(panchayat_list.getPname());
            i++;
        }
        panchayatadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panchayatNameArray);
        panchayatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_panchayat.setAdapter(panchayatadapter);


    }
    public void loadKrinawayanSpinner() {


        krinawayanList = dataBaseHelper.getKriwayan();
        krinawayanNameArray = new ArrayList<String>();
        krinawayanNameArray.add("-select-");
        int i = 0;
        for (Krinawayan krinawayan : krinawayanList) {
            krinawayanNameArray.add(krinawayan.getKrinawayan_name());
            i++;
        }
        krinawayanadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, krinawayanNameArray);
        krinawayanadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Kriwaran.setAdapter(krinawayanadapter);


    }
    public void loadabyabSpinner(String Krinawayan_code) {


        abyabList = dataBaseHelper.getAbyab(Krinawayan_code);
        abyabNameArray = new ArrayList<String>();
        abyabNameArray.add("-select-");
        int i = 0;
        for (Abyab abyab : abyabList) {
            abyabNameArray.add(abyab.getAbyab_name());
            i++;
        }
        abyabadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, abyabNameArray);
        abyabadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_abyb.setAdapter(abyabadapter);


    }


    public void loadSubabyabSpinner(String Krinawayan_code) {


        sub_abyabList = dataBaseHelper.getSubAbyab(Krinawayan_code);
        SubabyabNameArray = new ArrayList<String>();
        SubabyabNameArray.add("-select-");
        int i = 0;
        for (Sub_abyb sub_abyb : sub_abyabList) {
            SubabyabNameArray.add(sub_abyb.getSub_abyb_name());
            i++;
        }
        Subabyabadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SubabyabNameArray);
        Subabyabadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_Sub_abyb.setAdapter(Subabyabadapter);


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
                    et_anumodan_date.setText(smMonth + "-" + smDay + "-" + mYear);
                }else if(dateNo == 2){
                    et_prashinik_date.setText(smMonth + "-" + smDay + "-" + mYear);
                }

                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                // _ed_dob = mYear + smMonth + smDay;

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    public void click_prashinik_date(View v){
        dateNo = 2;
        ShowDialog();
    }

    public void click_anumodan_date(View v){
        dateNo = 1;
        ShowDialog();
    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if (Pan_Id.equals("")) {
            tv_panchayat.setError(getString(R.string.fieldRequired));
            focusView = tv_panchayat;
            validate = false;
        }
        if (abyb_Id.equals("")) {
            tv_awaya_name.setError(getString(R.string.fieldRequired));
            focusView = tv_awaya_name;
            validate = false;
        }


        if (!validate) focusView.requestFocus();

        return validate;
    }
}
