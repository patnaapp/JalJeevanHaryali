package bih.in.jaljeevanharyali.activity.nursery;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.CameraActivity;
import bih.in.jaljeevanharyali.activity.DashboardActivity;
import bih.in.jaljeevanharyali.activity.LoginActivity;
import bih.in.jaljeevanharyali.activity.PanchayatEntity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.DepartmentDetail;
import bih.in.jaljeevanharyali.entity.DepartmentEntity;
import bih.in.jaljeevanharyali.entity.District;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.SanrachnaTypeEntity;
import bih.in.jaljeevanharyali.entity.Sub_abyb;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.WardList;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.CommonPref;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class NurseryEntryActivity extends Activity implements AdapterView.OnItemSelectedListener {
    Spinner spn_dist,spn_block,spn_area,spn_panchayat,spn_ward,spn_vilage;
    Spinner spn_department,spn_building_type;

    EditText et_plantation_name,et_area,et_tree,et_mobile,et_remark;
    EditText et_building_name,et_building_id,et_chat_area,et_consumer_id,et_dept_name;

    Button btn_save;
    LinearLayout ll_building,ll_nursery,ll_village;
    ImageView img1;
    TextView tv_district,tv_block,tv_photo;
    TextView tv_title_area,tv_titl_panch,tv_title_ward,tv_title_vill,tv_title_build_type,tv_title_dept,tv_header;

    private final static int CAMERA_PIC = 99;
    byte[] img;
    Bitmap bmp;

    DataBaseHelper dataBaseHelper;

    String area_Type[] = {"-चयन करे-","Urban","Rural"};
    String builing_Type[] = {"-चयन करे-","Main Building","Sub Building"};

    ArrayList<District> DistrictList;
    ArrayList<Block> blockList;
    ArrayList<PanchayatData> panchayatList;
    ArrayList<VillageListEntity> VillageList;
    ArrayList<ward> wardList;

    ArrayList<DepartmentEntity> deptList;

    String type;
    UserDetails userInfo;

    NurseryEntity entryInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursery_entry);

        dataBaseHelper=new DataBaseHelper(this);

        initialization();

        ArrayAdapter area_Type_arr = new ArrayAdapter(this, android.R.layout.simple_spinner_item, area_Type);
        area_Type_arr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_area.setAdapter(area_Type_arr);

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, builing_Type);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_building_type.setAdapter(adapter);

        extractData();
    }

    public  void initialization(){
        spn_dist=(Spinner)findViewById(R.id.spn_dist);
        spn_block=(Spinner)findViewById(R.id.spn_block);
        spn_area=(Spinner)findViewById(R.id.spn_area);
        spn_panchayat=(Spinner)findViewById(R.id.spn_panchayat);
        spn_ward=(Spinner)findViewById(R.id.spn_ward);
        spn_vilage=(Spinner)findViewById(R.id.spn_vilage);

        spn_department=(Spinner)findViewById(R.id.spn_department);
        spn_building_type=(Spinner)findViewById(R.id.spn_building_type);

        spn_dist.setOnItemSelectedListener(this);
        spn_block.setOnItemSelectedListener(this);
        spn_area.setOnItemSelectedListener(this);
        spn_panchayat.setOnItemSelectedListener(this);
        spn_ward.setOnItemSelectedListener(this);
        spn_vilage.setOnItemSelectedListener(this);

        spn_department.setOnItemSelectedListener(this);
        spn_building_type.setOnItemSelectedListener(this);

        et_plantation_name=(EditText)findViewById(R.id.et_plantation_name);
        et_area=(EditText)findViewById(R.id.et_area);
        et_tree=(EditText)findViewById(R.id.et_tree);
        et_mobile=(EditText)findViewById(R.id.et_mobile);
        et_remark=(EditText)findViewById(R.id.et_remark);

        et_building_name=(EditText)findViewById(R.id.et_building_name);
        et_building_id=(EditText)findViewById(R.id.et_building_id);
        et_chat_area=(EditText)findViewById(R.id.et_chat_area);
        et_consumer_id=(EditText)findViewById(R.id.et_consumer_id);
        et_dept_name=(EditText)findViewById(R.id.et_dept_name);

        btn_save=(Button) findViewById(R.id.btn_save);

        ll_building = findViewById(R.id.ll_building);
        ll_nursery = findViewById(R.id.ll_nursery);
        ll_village = findViewById(R.id.ll_village);

        tv_district = findViewById(R.id.tv_district);
        tv_block = findViewById(R.id.tv_block);
        tv_header = findViewById(R.id.tv_header);

        img1 = findViewById(R.id.img1);

        tv_title_area = findViewById(R.id.tv_title_area);
        tv_titl_panch = findViewById(R.id.tv_titl_panch);
        tv_title_ward = findViewById(R.id.tv_title_ward);
        tv_title_vill = findViewById(R.id.tv_title_vill);
        tv_photo = findViewById(R.id.tv_photo);

        tv_title_build_type = findViewById(R.id.tv_title_build_type);
        tv_title_dept = findViewById(R.id.tv_title_dept);

        entryInfo = new NurseryEntity();

        et_dept_name.setVisibility(View.GONE);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    InsertIntoLocal();
                }else{
                    Toast.makeText(NurseryEntryActivity.this, "Invalid Data", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void extractData(){

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dataBaseHelper.getUserDetails(username, password);

        type = getIntent().getStringExtra("type");

        if(type.equals(AppConstant.NURSURY)){
            tv_header.setText("पौधशाला सृजन");
        }else{
            tv_header.setText("भवन");
        }

        if((NurseryEntity)getIntent().getSerializableExtra("data") != null){

            entryInfo = (NurseryEntity)getIntent().getSerializableExtra("data");
            setDataForEdit();

        }else{

            entryInfo.setDist_Code(CommonPref.getUserDetails(NurseryEntryActivity.this).getDistrictCode());
            entryInfo.setDist_Name(CommonPref.getUserDetails(NurseryEntryActivity.this).getDistName());
            entryInfo.setEntry_By(userInfo.getUserID());
            entryInfo.setDeptId(userInfo.getDeptId());
            entryInfo.setBlock_Code(getIntent().getStringExtra("blockCode"));
            entryInfo.setBlock_Name(getIntent().getStringExtra("blockName"));
            entryInfo.setStrType(type);
            entryInfo.setId(0);
        }

        setupViewAccordingToType();

        tv_district.setText(entryInfo.getDist_Name());
        tv_block.setText(entryInfo.getBlock_Name());
    }

    public void setDataForEdit(){
        if(entryInfo.getArea_Code().equals("U")){
            spn_area.setSelection(1);
        }else if(entryInfo.getArea_Code().equals("R")){
            spn_area.setSelection(2);
        }

        if(entryInfo.getStrType().equals(AppConstant.NURSURY)){
            et_plantation_name.setText(entryInfo.getNursury_Name());
            et_area.setText(entryInfo.getArea());
            et_tree.setText(entryInfo.getTree());
            et_mobile.setText(entryInfo.getMobile());
        }else if (entryInfo.getStrType().equals(AppConstant.BUILDING)){
            et_building_name.setText(entryInfo.getBuildingName());
            et_building_id.setText(entryInfo.getBCode().equals("NA") ? "" : entryInfo.getBCode());
            et_chat_area.setText(entryInfo.getArea().equals("NA") ? "" : entryInfo.getArea());
            et_consumer_id.setText(entryInfo.getConsumerNo().equals("NA") ? "" : entryInfo.getConsumerNo());

            if(entryInfo.getExecution_DeptID().equals("14")){
                et_dept_name.setText(entryInfo.getOtherDept());
            }

            if(entryInfo.getBuildingType().equals("M"))
                spn_building_type.setSelection(1);
            else if (entryInfo.getBuildingType().equals("S"))
                spn_building_type.setSelection(2);
        }

        et_remark.setText(entryInfo.getRemark());
        if(entryInfo.getImg() != null){
            img = entryInfo.getImg();
            Bitmap bmp = BitmapFactory.decodeByteArray(img, 0,img.length);
            img1.setImageBitmap(bmp);
        }
    }

    public void setupViewAccordingToType(){
        if(type.equals(AppConstant.NURSURY)){
            ll_building.setVisibility(View.GONE);
            ll_village.setVisibility(View.VISIBLE);
        }else{
            ll_nursery.setVisibility(View.GONE);
            ll_village.setVisibility(View.GONE);

            deptList = dataBaseHelper.getExecDepartmentList();
            if(deptList.size() > 0){
                loadDeptListAdapter();
            }else{
                new SyncDepartmentData().execute();
            }
        }
    }

    public void loadBlockSpinnerdata() {
        blockList = dataBaseHelper.getBlock(entryInfo.getDist_Code());
        ArrayList<String> array = new ArrayList<>();
        array.add("-चयन करे-");
        for (Block block : blockList) {
            array.add(block.getBlockName());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_block.setAdapter(adapter);
    }

    public void loadPanchayatSpinnerdata() {
        panchayatList = dataBaseHelper.getPanchaytAreawise(entryInfo.getBlock_Code(),entryInfo.getArea_Code());
        ArrayList<String> array = new ArrayList<>();
        array.add("-चयन करे-");

        int position= -1;
        for (PanchayatData block : panchayatList) {
            array.add(block.getPname());
            if(block.getPcode().equals(entryInfo.getPanchayat_Code())){
                position = panchayatList.indexOf(block);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_panchayat.setAdapter(adapter);

        if(position >= 0){
            spn_panchayat.setSelection(position+1);
        }

    }
    public void loadWardSpinnerdata() {
        wardList = dataBaseHelper.getWardListPanchayatwise(entryInfo.getArea_Code(),entryInfo.getPanchayat_Code());
        ArrayList<String> array = new ArrayList<>();
        array.add("-चयन करे-");

        int position= -1;
        for (ward block : wardList) {
            array.add(block.getWardname());

            if(block.getWardCode().equals(entryInfo.getWard_Code())){
                position = wardList.indexOf(block);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_ward.setAdapter(adapter);

        if(position >= 0){
            spn_ward.setSelection(position+1);
        }
    }

    public void loadVillageSpinnerdata() {
        VillageList = dataBaseHelper.getVillageList(entryInfo.getPanchayat_Code());
        ArrayList<String> array = new ArrayList<>();
        array.add("-चयन करे-");

        int position= -1;
        for (VillageListEntity block : VillageList) {
            array.add(block.getVillName());

            if(block.getVillCode().equals(entryInfo.getVillage_Code())){
                position = VillageList.indexOf(block);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_vilage.setAdapter(adapter);

        if(position >= 0){
            spn_vilage.setSelection(position+1);
        }
    }

    public void loadDeptListAdapter() {
        ArrayList<String> array = new ArrayList<>();
        array.add("-चयन करे-");

        int position= -1;
        for (DepartmentEntity dept : deptList) {
            array.add(dept.getDeptNameHn());

            if(dept.getDeptId().equals(entryInfo.getExecution_DeptID())){
                position = deptList.indexOf(dept);
            }
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_department.setAdapter(adapter);

        if(position >= 0){
            spn_department.setSelection(position+1);
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spn_area:
                if (position > 0) {
                    //Area_Name = area_Type[position].toString();
                    entryInfo.setArea_Name(area_Type[position].toString());
                    if (entryInfo.getArea_Name().equalsIgnoreCase("Urban")) {
                        //Area_Code = "U";
                        entryInfo.setArea_Code("U");
                        //setWardSpinnerData(Area_Code);
                    } else if (entryInfo.getArea_Name().equalsIgnoreCase("Rural")) {
                        //Area_Code = "R";
                        entryInfo.setArea_Code("R");
                    }
                    //loadPanchayatSpinnerdata();
                    setPanchayatSpinnerData(entryInfo.getBlock_Code(),entryInfo.getArea_Code());

                    tv_title_area.setError(null);
                }
                break;
            case R.id.spn_panchayat:
                    if (position > 0) {
                        PanchayatData panchayatEntity = panchayatList.get(position - 1);
//                        Pan_Id = panchayatEntity.getPcode();
//                        Pan_Name = panchayatEntity.getPname();
                        entryInfo.setPanchayat_Code(panchayatEntity.getPcode());
                        entryInfo.setPanchayat_Name(panchayatEntity.getPname());

                        setWardSpinnerData(entryInfo.getArea_Code(),entryInfo.getPanchayat_Code());
                        setVillageSpinnerData(entryInfo.getPanchayat_Code());
                        //loadVillageData(Pan_Id);
                        //loadWardData(Pan_Id);
                        tv_titl_panch.setError(null);
                    }
                break;

            case R.id.spn_ward:
                if (position > 0) {
                    ward abyab = wardList.get(position - 1);
//                    Ward_Code = abyab.getWardCode();
//                    Ward_Name = abyab.getWardname();
                    entryInfo.setWard_Code(abyab.getWardCode());
                    entryInfo.setWard_Name(abyab.getWardname());

                    tv_title_ward.setError(null);
                }
                break;

            case R.id.spn_vilage:
                if (position > 0) {
                    VillageListEntity type = VillageList.get(position - 1);
//                    Vill_Code = type.getVillCode();
//                    Vill_Name = type.getVillName();
                    entryInfo.setVillage_Code(type.getVillCode());
                    entryInfo.setVillage_Name(type.getVillName());

                    tv_title_vill.setError(null);
                }
                break;
            case R.id.spn_department:
                if (position > 0) {
                    DepartmentEntity type = deptList.get(position - 1);
                    entryInfo.setExecution_DeptID(type.getDeptId());
                    entryInfo.setExecution_DeptName(type.getDeptNameHn());
                    tv_title_dept.setError(null);

                    if(type.getDeptId().equals("14")){
                        et_dept_name.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.spn_building_type:
                if (position > 0) {

                    if(position == 1)
                        entryInfo.setBuildingType("M");
                    else if (position == 2)
                        entryInfo.setBuildingType("S");

                    tv_title_build_type.setError(null);
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if (entryInfo.getArea_Code() == null) {
            tv_title_area.setError(getString(R.string.fieldRequired));
            focusView = tv_title_area;
            validate = false;
        }

        if (entryInfo.getPanchayat_Code() == null) {
            tv_titl_panch.setError(getString(R.string.fieldRequired));
            focusView = tv_titl_panch;
            validate = false;
        }

        if (entryInfo.getWard_Code() == null) {
            tv_title_ward.setError(getString(R.string.fieldRequired));
            focusView = tv_title_ward;
            validate = false;
        }

        if(entryInfo.getStrType().equals(AppConstant.NURSURY)){
            if (entryInfo.getVillage_Code() == null) {
                tv_title_vill.setError(getString(R.string.fieldRequired));
                focusView = tv_title_vill;
                validate = false;
            }

            if (et_plantation_name.getText().toString().equals("")) {
                et_plantation_name.setError(getString(R.string.fieldRequired));
                focusView = et_plantation_name;
                validate = false;
            }

            try{
                if (et_area.getText().toString().equals("")) {
                    et_area.setError(getString(R.string.fieldRequired));
                    focusView = et_area;
                    validate = false;
                }else if(Double.parseDouble(et_area.getText().toString()) <= 0.0){
                    et_area.setError("कृपया सही क्षेत्रफल डालें");
                    focusView = et_area;
                    validate = false;
                }
            }catch (Exception e){
            }

            try{
                if (et_tree.getText().toString().equals("")) {
                    et_tree.setError(getString(R.string.fieldRequired));
                    focusView = et_tree;
                    validate = false;
                }else if(Integer.parseInt(et_area.getText().toString()) <= 0){
                    et_tree.setError("कृपया सही पौधा संख्या डालें");
                    focusView = et_tree;
                    validate = false;
                }
            }catch (Exception e){
            }

            try{
                if (et_mobile.getText().toString().equals("")) {
                    et_mobile.setError(getString(R.string.fieldRequired));
                    focusView = et_mobile;
                    validate = false;
                }else if(et_mobile.getText().toString().length() < 10){
                    et_mobile.setError("कृपया सही मोबाइल नंबर डालें");
                    focusView = et_mobile;
                    validate = false;
                }
            }catch (Exception e){
            }
        }

        if(entryInfo.getStrType().equals(AppConstant.BUILDING)){
            if (entryInfo.getExecution_DeptID() == null || entryInfo.getExecution_DeptID().equals("NA")){
                tv_title_dept.setError(getString(R.string.fieldRequired));
                focusView = tv_title_dept;
                validate = false;
            }else if (entryInfo.getExecution_DeptID().equals("14") && et_dept_name.getText().toString().equals("")){
                et_dept_name.setError(getString(R.string.fieldRequired));
                focusView = et_dept_name;
                validate = false;
            }


            if (entryInfo.getBuildingType() == null || entryInfo.getBuildingType().equals("NA")) {
                tv_title_build_type.setError(getString(R.string.fieldRequired));
                focusView = tv_title_build_type;
                validate = false;
            }

            if (et_building_name.getText().toString().equals("")) {
                et_building_name.setError(getString(R.string.fieldRequired));
                focusView = et_building_name;
                validate = false;
            }
        }

        if(img == null){
            tv_photo.setError(getString(R.string.fieldRequired));
            focusView = tv_photo;
            validate = false;
            Toast.makeText(this, "कृपया फोटो ले", Toast.LENGTH_SHORT).show();
        }

        if (!validate) focusView.requestFocus();

        return validate;
    }

    private void InsertIntoLocal(){

        long id = 0;

        setValue();

        id = dataBaseHelper.InsertUpdateNurseryBuildingData(entryInfo);

        if (id > 0) {
            Toast.makeText(NurseryEntryActivity.this,"Data Saved Successfully,",Toast.LENGTH_SHORT).show();
            Intent iUserHome = new Intent(getApplicationContext(),DashboardActivity.class);
            iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(iUserHome);
            finish();
        }else {
            Toast.makeText(NurseryEntryActivity.this,"Data Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    private void setValue() {

        if(entryInfo.getStrType().equals(AppConstant.NURSURY)){
            entryInfo.setNursury_Name(et_plantation_name.getText().toString().trim());
            entryInfo.setArea(et_area.getText().toString().trim());
            entryInfo.setTree(et_tree.getText().toString().trim());
            entryInfo.setMobile(et_mobile.getText().toString().trim());
        }else if (entryInfo.getStrType().equals(AppConstant.BUILDING)){
            entryInfo.setBuildingName(et_building_name.getText().toString().trim());
            entryInfo.setBCode(et_building_id.getText().toString().trim());
            entryInfo.setArea(et_chat_area.getText().toString().trim());
            entryInfo.setConsumerNo(et_consumer_id.getText().toString().trim());
            entryInfo.setOtherDept(et_dept_name.getText().toString().trim());
        }

        entryInfo.setImg(img);
        entryInfo.setEntryDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
        entryInfo.setEntry_By(userInfo.getUserID());
        entryInfo.setDeptId(userInfo.getDeptId());
        entryInfo.setAppVersion(Utiilties.getAppVersion(this));
        entryInfo.setIsUpdate("1");
        entryInfo.setRemark(et_remark.getText().toString().trim());
    }

    public void setPanchayatSpinnerData(String blockCode,String areaCode) {
        DataBaseHelper placeData = new DataBaseHelper(NurseryEntryActivity.this);
        panchayatList = dataBaseHelper.getPanchaytAreawise(entryInfo.getBlock_Code(),entryInfo.getArea_Code());

        if(panchayatList.size()<=0)
        {
            new PanchayatLoader(blockCode,areaCode).execute();
        }
        else {
            loadPanchayatSpinnerdata();//loadSHGSpinnerData(SHGList);
        }
    }

    public void setWardSpinnerData(String areaCode,String Pancode) {
        DataBaseHelper placeData = new DataBaseHelper(NurseryEntryActivity.this);
        wardList = dataBaseHelper.getWardListPanchayatwise(areaCode,Pancode);
        if(wardList.size()<=0)
        {
            new SyncWardData(Pancode).execute();
        }
        else {
            loadWardSpinnerdata();//loadSHGSpinnerData(SHGList);
        }

    }
    public void setVillageSpinnerData(String Pancode) {
        DataBaseHelper placeData = new DataBaseHelper(NurseryEntryActivity.this);
        VillageList = dataBaseHelper.getVillageList(Pancode);
        if(VillageList.size()<=0)
        {
            new SyncVillageData(Pancode).execute();
        }
        else {
            loadVillageSpinnerdata();//loadSHGSpinnerData(SHGList);
        }

    }
    private void loadPanchayatData() {
        ArrayList<String> shgStringList = new ArrayList<String>();
        int pos = 0;
        shgStringList.add("-Select-");
        if(panchayatList != null) {
            for (int i = 0; i < panchayatList.size(); i++) {
                shgStringList.add(panchayatList.get(i).getPname());
            }
        }
        ArrayAdapter<String> panchayatAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, shgStringList);

        if (spn_panchayat != null) {
            spn_panchayat.setAdapter(panchayatAdapter);
            spn_panchayat.setSelection(pos);
        }
//        DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
//        EntryList=helper.getpashuleveleditentry(CommonPref.getUserDetails(PhasuLevelActivity.this).getUserID(),keyid);
//        for (PhasuLevelEntity phasuLevelEntity : EntryList) {
//            spn_shg_Name.setSelection(((ArrayAdapter<String>) spn_shg_Name.getAdapter()).getPosition(phasuLevelEntity.getSHGName()));
//        }
        panchayatAdapter.notifyDataSetChanged();
    }

    public void onClick_ViewImg(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.viewimage, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("  फोटो  ");


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageView imgview = (ImageView) dialogView.findViewById(R.id.imgview);
        if (img != null) {
            imgview.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
        }

        dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void onClickTakePhoto(View view) {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            buildAlertMessageNoGps();
        }
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

            Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
            //if (view.equals(img1))
            iCamera.putExtra("KEY_PIC", "1");
            startActivityForResult(iCamera, CAMERA_PIC);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");
                    DataBaseHelper placeData = new DataBaseHelper(getApplicationContext());

                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            img = imgData;
                            Bitmap bmpImg = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            bmp = bmpImg;
                            img1.setImageBitmap(bmpImg);

                            entryInfo.setLatitude(String.valueOf(data.getStringExtra("Lat")));
                            entryInfo.setLongitude(String.valueOf(data.getStringExtra("Lng")));
                            tv_photo.setError(null);
                            break;
                    }
                }
        }
    }

    public class PanchayatLoader extends AsyncTask<String, Void, ArrayList<PanchayatData>> {
        String blockcode = "",areacode = "";

        public PanchayatLoader(String blockCode,String areaCode) {

            this.blockcode = blockCode;
            this.areacode = areaCode;
        }

        private final ProgressDialog dialog = new ProgressDialog(NurseryEntryActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(NurseryEntryActivity.this).create();

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Panchayat.\nPlease wait...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PanchayatData> doInBackground(String... params) {

            ArrayList<PanchayatData> res1 = WebServiceHelper.getPanchayatList_New(entryInfo.getDist_Code(),entryInfo.getBlock_Code());

            return res1;
        }

        @Override
        protected void onPostExecute(final ArrayList<PanchayatData> result) {

            if (this.dialog.isShowing()) {
                if (result != null) {
                    if (result.size() > 0) {
                        DataBaseHelper placeData = new DataBaseHelper(NurseryEntryActivity.this);
                        long c = placeData.insertPanchayatist(result,entryInfo.getDist_Code(),entryInfo.getBlock_Code());
                        if(c>0){
                            loadPanchayatSpinnerdata();
                        }else{
                            Toast.makeText(NurseryEntryActivity.this, "Failed to save panachayat data", Toast.LENGTH_SHORT).show();
                        }

                    } else {


                    }


                } else {
                    Toast.makeText(NurseryEntryActivity.this, "Null: Failed Load Panchayat", Toast.LENGTH_SHORT).show();
                }

                this.dialog.dismiss();
            }
        }
    }


    private class SyncWardData extends AsyncTask<String, Void, ArrayList<ward>> {

            String PanCode="";
        public SyncWardData(String panCode) {

            this.PanCode = panCode;

        }
        private final ProgressDialog dialog = new ProgressDialog(NurseryEntryActivity.this);

        @Override
        protected void onPreExecute() {
            //this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("वार्ड लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<ward> doInBackground(String...arg) {
            return WebServiceHelper.getWardListData(PanCode);
        }

        @Override
        protected void onPostExecute(ArrayList<ward> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            long i= helper.setWardDataToLocal(result);

            if(i>0)
            {
                loadWardSpinnerdata();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update ward",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class SyncVillageData extends AsyncTask<String, Void, ArrayList<VillageListEntity>> {
        String PanCode="";
        public SyncVillageData(String panCode) {

            this.PanCode = panCode;

        }
        private final ProgressDialog dialog = new ProgressDialog(NurseryEntryActivity.this);

        @Override
        protected void onPreExecute() {
            //dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("ग्राम लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<VillageListEntity> doInBackground(String...arg) {

            return WebServiceHelper.getVillageListData(entryInfo.getBlock_Code());
        }

        @Override
        protected void onPostExecute(ArrayList<VillageListEntity> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            long i= helper.setVillageDataToLocal(result);

            if(i>0)
            {
                loadVillageSpinnerdata();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update village",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SyncDepartmentData extends AsyncTask<String, Void, ArrayList<DepartmentEntity>> {

        public SyncDepartmentData() {
        }

        private final ProgressDialog dialog = new ProgressDialog(NurseryEntryActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("ग्राम लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<DepartmentEntity> doInBackground(String...arg) {

            return WebServiceHelper.getExecDepartmentData();
        }

        @Override
        protected void onPostExecute(ArrayList<DepartmentEntity> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

                long i= helper.setExecDeptDataToLocal(result);
                deptList = result;
                if(i>0)
                {
                    loadDeptListAdapter();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to update department list",Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(getApplicationContext(), "No department list found!!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle("GPS ?");
        builder.setMessage("(GPS)जीपीएस बंद है\nस्थान के अक्षांश और देशांतर प्राप्त करने के लिए कृपया जीपीएस चालू करें")
                .setCancelable(false)
                .setPositiveButton("[(GPS) जीपीएस चालू करे ]", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("[ बंद करें ]", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
}
