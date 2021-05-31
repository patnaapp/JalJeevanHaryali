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
import bih.in.jaljeevanharyali.entity.SanrachnaTypeEntity;
import bih.in.jaljeevanharyali.entity.Sub_abyb;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.CommonPref;

public class OtherSchemeActivity extends Activity implements AdapterView.OnItemSelectedListener{

    TextView district_name,block_name,rajaswa_thana,tv_village;
    Spinner spin_panchayat,spin_ward,spin_dept_name,spin_sub_division,spin_sub_division_part,spin_urban_body_area,spin_village,spn_sanrachna_type;
    EditText et_sub_div_part1,et_work_type,et_structure_area,et_remarks,et_yojana_code,et_estimated_amount,et_sanrachna_name;
    TextView et_approval_date,tv_panchayat,tv_kriwan,tv_sub_division,tv_sanrachna_name;
    Button btn_next;
    ImageView img_cal_personal;
    private int mYear, mMonth, mDay;
    DatePickerDialog datedialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<ward> wardList = new ArrayList<ward>();
    ArrayList<VillageListEntity> villageList = new ArrayList<VillageListEntity>();
    ArrayList<SanrachnaTypeEntity> sanrachnaTypeList = new ArrayList<SanrachnaTypeEntity>();

    ArrayList<String> wardNameArray;
    ArrayList<String> SanrachnaNameArray;
    ArrayList<String> villageNameArray;
    ArrayAdapter<String> wardAdapter;
    ArrayAdapter<String> villageAdapter;
    ArrayAdapter<String> sanrachnaAdapter;

    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<Krinawayan> krinawayanList = new ArrayList<Krinawayan>();
    ArrayList<Abyab> abyabList = new ArrayList<Abyab>();
    ArrayList<Sub_abyb> sub_abyabList = new ArrayList<Sub_abyb>();

    ArrayList<String> panchayatNameArray;
    ArrayList<String> krinawayanNameArray;
    ArrayList<String> SubabyabNameArray;
    ArrayList<String> abyabNameArray;

    ArrayAdapter<String> panchayatadapter;
    ArrayAdapter<String> krinawayanadapter;
    ArrayAdapter<String>abyabadapter;
    ArrayAdapter<String>Subabyabadapter;
    ArrayAdapter SahariNikayeAdaptor;
    String Pan_Id="",Pan_Name="",Ward_Id="",Ward_Name="",Village_Id="",Village_Name="",Kriwaran_Id="",Kriwaran_Name="",abyb_Id="",abyb_Name="",Sub_abyb_Id="",Sub_abyb_Name="", sahariNiakayeId = "",sahariNiakayeValue="", userId="", sanrachnaTypeId = "", sanrachnaTypeName;

    LinearLayout ll_sub_div_part1_spin,ll_sub_div_part1_edt,ll_panchayat,ll_village,ll_sanrachna_type,ll_sanrachna_name;

    String districtName, districtId, blockName, blockId;
    int dateNo,id;
    private String s1_data = "";

    String SahariNikayeOption[] = {"-चयन करे-","ग्रामीण", "शहरी"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_scheme);

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

        Initialization();
        //loadPanchayatSpinner(CommonPref.getUserDetails(OtherSchemeActivity.this).getBlockCode());
        loadKrinawayanSpinner();

        SahariNikayeAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, SahariNikayeOption);
        SahariNikayeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_urban_body_area.setAdapter(SahariNikayeAdaptor);

        extractDataFromIntent();


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateData()){
                    UpdateOtherSchemeDetail();
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
            ManregaSchemeDetail info = dataBaseHelper.getOtherSchemeDetail(String.valueOf(id));
            Pan_Id= info.getPanchayat_Id();
            Pan_Name= info.getPanchayatName();
            Ward_Id= info.getWard_Id();
            Ward_Name=info.getWard_Name();
            Kriwaran_Id= info.getExecution_Dept_ID();
            Kriwaran_Name= info.getExectDeptName();
            abyb_Id= info.getSub_Exect_Dept_ID();
            abyb_Name= info.getSub_Exect_Value();
            Sub_abyb_Id= info.getSub_Sub_Exect_Dept_ID();
            sahariNiakayeId = info.getSahariNikayeId();
            sanrachnaTypeId = info.getWork_Structure_Type();

            loadPanchayatSpinner(blockId, sahariNiakayeId);
            loadabyabSpinner(Kriwaran_Id);

            if (info.getWork_Structure_Type_Name() != null && info.getWork_Structure_Type_Name().equals("अन्य")){
                ll_sanrachna_name.setVisibility(View.VISIBLE);
                et_sanrachna_name.setText(info.getWork_Structure_Type_Other_Name());
            }else{
                ll_sanrachna_name.setVisibility(View.GONE);
            }

            //et_work_type.setText(info.getWork_Structure_Type());
            et_structure_area.setText(info.getMeasurement_Of_Structure());
            et_estimated_amount.setText(info.getEstimated_Amount());
            et_yojana_code.setText(info.getScheme_Code());
            et_remarks.setText(info.getRemarks());
            et_approval_date.setText(info.getAdministrative_Approval_Date());

            if(abyb_Id.equals("29")||abyb_Id.equals("31")||abyb_Id.equals("32")){
                //Toast.makeText(this, info.getSub_Exect_Value(), Toast.LENGTH_SHORT).show();
                et_sub_div_part1.setText(info.getSub_Exect_Value());
            }

            spin_urban_body_area.setSelection(sahariNiakayeId.contains("R") ? 1 : 2);
            spin_dept_name.setSelection((((ArrayAdapter<String>) spin_dept_name.getAdapter()).getPosition(Kriwaran_Name)));
        }

    }

    private void Initialization()
    {
        district_name=findViewById(R.id.district_name);
        block_name=findViewById(R.id.block_name);
        rajaswa_thana=findViewById(R.id.rajaswa_thana);
        tv_village=findViewById(R.id.tv_village);
        tv_sanrachna_name=(TextView) findViewById(R.id.tv_sanrachna_name);

        spin_panchayat=findViewById(R.id.spin_panchayat);
        spin_ward=findViewById(R.id.spin_ward);
        spin_dept_name=findViewById(R.id.spin_dept_name);
        spin_sub_division=findViewById(R.id.spin_sub_division);
        spin_sub_division_part=findViewById(R.id.spin_sub_division_part);
        spin_urban_body_area=findViewById(R.id.spin_urban_body_area);
        spin_village=findViewById(R.id.spin_village);
        spn_sanrachna_type=(Spinner)findViewById(R.id.spn_sanrachna_type);

        et_sub_div_part1=findViewById(R.id.et_sub_div_part1);
        //et_work_type=findViewById(R.id.et_work_type);
        et_structure_area=findViewById(R.id.et_structure_area);
        et_approval_date=findViewById(R.id.et_approval_date);
        et_remarks=findViewById(R.id.et_remarks);
        et_yojana_code=findViewById(R.id.et_yojana_code);
        et_estimated_amount=findViewById(R.id.et_estimated_amount);
        et_sanrachna_name=(EditText) findViewById(R.id.et_sanrachna_name);

        ll_sub_div_part1_spin=findViewById(R.id.ll_sub_div_part1_spin);
        ll_sub_div_part1_edt=findViewById(R.id.ll_sub_div_part1_edt);
        ll_panchayat=findViewById(R.id.ll_panchayat);
        ll_village=findViewById(R.id.ll_village);
        ll_sanrachna_type = (LinearLayout) findViewById(R.id.ll_sanrachna_type);
        ll_sanrachna_name = (LinearLayout) findViewById(R.id.ll_sanrachna_name);

        spin_panchayat.setOnItemSelectedListener(this);
        spin_ward.setOnItemSelectedListener(this);
        spin_dept_name.setOnItemSelectedListener(this);
        spin_sub_division.setOnItemSelectedListener(this);
        spin_sub_division_part.setOnItemSelectedListener(this);
        spin_urban_body_area.setOnItemSelectedListener(this);
        spin_village.setOnItemSelectedListener(this);
        spn_sanrachna_type.setOnItemSelectedListener(this);

        ll_sub_div_part1_spin.setVisibility(View.GONE);
        ll_sub_div_part1_edt.setVisibility(View.GONE);
        //ll_panchayat.setVisibility(View.GONE);
        ll_village.setVisibility(View.GONE);
        ll_sanrachna_name.setVisibility(View.GONE);

        btn_next=(Button) findViewById(R.id.btn_next);

        district_name=(TextView) findViewById(R.id.district_name);
        block_name=(TextView) findViewById(R.id.block_name);
        tv_panchayat=(TextView) findViewById(R.id.tv_panchayat);
    }

    private void UpdateOtherSchemeDetail(){
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
        manregaSchemeDetail.setUrban_Area_Id(sahariNiakayeId);
        manregaSchemeDetail.setWard_Id(Ward_Id);
        manregaSchemeDetail.setWard_Name(Ward_Name);
        manregaSchemeDetail.setVillage_Id(Village_Id);
        manregaSchemeDetail.setVillage_Name(Village_Name);

        manregaSchemeDetail.setWork_Structure_Type(sanrachnaTypeId);
        manregaSchemeDetail.setWork_Structure_Type_Name(sanrachnaTypeName);
        manregaSchemeDetail.setWork_Structure_Type_Other_Name(et_sanrachna_name.getText().toString());
        manregaSchemeDetail.setMeasurement_Of_Structure(et_structure_area.getText().toString());
        manregaSchemeDetail.setEstimated_Amount(et_estimated_amount.getText().toString());
        manregaSchemeDetail.setSub_Exect_Value(et_sub_div_part1.getText().toString());
        //manregaSchemeDetail.setApproval_Date(et_approval_date.getText().toString());
        manregaSchemeDetail.setAdministrative_Approval_Date(et_approval_date.getText().toString());
        manregaSchemeDetail.setScheme_Code(et_yojana_code.getText().toString());
        manregaSchemeDetail.setRemarks(et_remarks.getText().toString());
        manregaSchemeDetail.setCreatedBy(userId);
        manregaSchemeDetail.setSahariNikayeId(sahariNiakayeId);
        manregaSchemeDetail.setCreatedDate(currentDate);
        manregaSchemeDetail.setApp_Version(getAppVersion());


        result = new DataBaseHelper(this).InsertOtherSchemeDetail(manregaSchemeDetail);

        if (result > 0) {

            Toast.makeText(getApplicationContext(),"डाटा सफलतापूर्वक सेव हो गया",Toast.LENGTH_LONG).show();

            Intent intentOther = new Intent(this, OtherSchemePhotoActivity.class);
            intentOther.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            Log.d("ggeuhshd",""+result);
            intentOther.putExtra("KEY_PID", id > 0 ? id : (int) result);
            intentOther.putExtra("pupose", "new");
            intentOther.putExtra("isOpen", s1_data);
            startActivity(intentOther);
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
            case R.id.spin_panchayat:
                int pos = position;
                if (pos > 0) {
                    PanchayatData panchayatEntity = panchayatList.get(pos - 1);

                    Pan_Id = panchayatEntity.getPcode();
                    Pan_Name = panchayatEntity.getPname();
                    loadWardData(Pan_Id);
                    if(sahariNiakayeId.equals("R")){
                        loadVillageData(Pan_Id);
                    }
                    //Toast.makeText(this, panchayatEntity.getPcode(), Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.spin_ward:
                if (position > 0) {
                    ward ward = wardList.get(position - 1);
                    Ward_Id = ward.getWardCode();
                    Ward_Name = ward.getWardname();


                    //Toast.makeText(this, ward.getPanchayatCode(), Toast.LENGTH_SHORT).show();
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

            case R.id.spin_dept_name:
                if (position > 0) {
                    Krinawayan krinawayan = krinawayanList.get(position - 1);
                    Kriwaran_Id = krinawayan.getKrinawayan_Code();
                    Kriwaran_Name = krinawayan.getKrinawayan_name();

                    loadabyabSpinner(Kriwaran_Id);
                }
                break;

            case R.id.spin_sub_division:
                if (position > 0) {
                    Abyab abyab = abyabList.get(position - 1);
                    abyb_Id = abyab.getAbyab_Code();
                    abyb_Name = abyab.getAbyab_name();
                    ll_sub_div_part1_spin.setVisibility(View.GONE);
                    ll_sub_div_part1_edt.setVisibility(View.GONE);


                    if(abyb_Id.equals("28")){
                        ll_sub_div_part1_spin.setVisibility(View.VISIBLE);
                        loadSubabyabSpinner(abyb_Id);
                    }


                    if(abyb_Id.equals("29")||abyb_Id.equals("31")||abyb_Id.equals("32")){
                        ll_sub_div_part1_edt.setVisibility(View.VISIBLE);

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
            case R.id.spin_urban_body_area:
                if (position > 0) {
                    sahariNiakayeValue=SahariNikayeOption[position].toString();

                    if(sahariNiakayeValue.equals("ग्रामीण")){
                        sahariNiakayeId="R";
                        tv_panchayat.setText("पंचायत *");
                        ll_village.setVisibility(View.VISIBLE);
                        //loadPanchayatSpinner(CommonPref.getUserDetails(OtherSchemeActivity.this).getBlockCode(), "R");
                    }
                    else if(sahariNiakayeValue.equals("शहरी")){
                        sahariNiakayeId="U";
                        tv_panchayat.setText("शहरी निकाय क्षेत्र *");
                        ll_village.setVisibility(View.GONE);

                    }

                    loadPanchayatSpinner(CommonPref.getUserDetails(OtherSchemeActivity.this).getBlockCode(), sahariNiakayeId);
                    //Toast.makeText(PondInspectionActivity.this, pondType, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.spin_sub_division_part:
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

    public void click_prashinik_other_date(View v){
        ShowDialog();
    }


    public void ShowDialog() {


        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        datedialog = new DatePickerDialog(OtherSchemeActivity.this,
                mDateSetListener, mYear, mMonth, mDay);

        //datedialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // datedialog.getDatePicker().setMinDate(min.getTime());
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


                et_approval_date.setText(smMonth + "-" + smDay + "-" + mYear);
                //_DOB = mYear + "-" + smMonth + "-" + smDay + " " + newString;
                // _ed_dob = mYear + smMonth + smDay;

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    };

    public void loadPanchayatSpinner(String blockCode, String areaType) {


        panchayatList = dataBaseHelper.getPanchaytAreawise(blockCode, areaType);
        panchayatNameArray = new ArrayList<String>();
        panchayatNameArray.add("-चयन करे-");
        int i = 0, setId= 0;
        for (PanchayatData panchayat_list : panchayatList) {
            panchayatNameArray.add(panchayat_list.getPname());
            if(Pan_Id.equals(panchayat_list.getPcode())){
                setId= i;
            }
            i++;
        }
        panchayatadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panchayatNameArray);
        panchayatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_panchayat.setAdapter(panchayatadapter);

        if(id > 0){
            spin_panchayat.setSelection(setId+1);
        }
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
        spin_ward.setAdapter(wardAdapter);

        if(id > 0){
            spin_ward.setSelection(setId+1);
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


    public void loadKrinawayanSpinner() {


        krinawayanList = dataBaseHelper.getKriwayan();
        krinawayanList.remove(0);
        krinawayanNameArray = new ArrayList<String>();
        krinawayanNameArray.add("-चयन करे-");
        int i = 0;
        for (Krinawayan krinawayan : krinawayanList) {
            krinawayanNameArray.add(krinawayan.getKrinawayan_name());
            i++;
        }
        krinawayanadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, krinawayanNameArray);
        krinawayanadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_dept_name.setAdapter(krinawayanadapter);


    }
    public void loadabyabSpinner(String Krinawayan_code) {


        abyabList = dataBaseHelper.getAbyab(Krinawayan_code);
        abyabNameArray = new ArrayList<String>();
        abyabNameArray.add("-चयन करे-");
        int i = 0, setId= 0;
        for (Abyab abyab : abyabList) {
            abyabNameArray.add(abyab.getAbyab_name());
            if(abyb_Id.equals(abyab.getAbyab_Code())){
                setId= i;
            }
            i++;
        }
        abyabadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, abyabNameArray);
        abyabadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_sub_division.setAdapter(abyabadapter);

        if(id > 0){
            spin_sub_division.setSelection(setId+1);
        }
    }

    public void loadSubabyabSpinner(String Krinawayan_code) {


        sub_abyabList = dataBaseHelper.getSubAbyab(Krinawayan_code);
        SubabyabNameArray = new ArrayList<String>();
        SubabyabNameArray.add("-चयन करे-");
        int i = 0, setId= 0;
        for (Sub_abyb sub_abyb : sub_abyabList) {
            SubabyabNameArray.add(sub_abyb.getSub_abyb_name());
            if(Sub_abyb_Id.equals(sub_abyb.getSub_abyb_Code())){
                setId= i;
            }
            i++;
        }
        Subabyabadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, SubabyabNameArray);
        Subabyabadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_sub_division_part.setAdapter(Subabyabadapter);

        if(id > 0){
            spin_sub_division_part.setSelection(setId+1);
        }
    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if (Pan_Id.equals("")) {
            tv_panchayat.setError(getString(R.string.fieldRequired));
            focusView = tv_panchayat;
            validate = false;
        }
        if (Kriwaran_Id.equals("")) {
            tv_kriwan.setError(getString(R.string.fieldRequired));
            focusView = tv_kriwan;
            validate = false;
        }
        if (abyb_Id.equals("")) {
            tv_sub_division.setError(getString(R.string.fieldRequired));
            focusView = tv_sub_division;
            validate = false;
        }


        if (!validate) focusView.requestFocus();

        return validate;
    }

}
