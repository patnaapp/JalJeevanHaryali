package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.DepartmentEntity;
import bih.in.jaljeevanharyali.entity.GetLocation;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;
import bih.in.jaljeevanharyali.entity.PondLakeDepartmentEntity;
import bih.in.jaljeevanharyali.entity.Sub_abyb;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class  PondInspectionActivity extends Activity implements AdapterView.OnItemSelectedListener, LocationListener {

    GetLocation gps;
    DataBaseHelper dataBaseHelper;
    LinearLayout ll_comercial,ll_encroachment_type,ll_avbl_type,ll_rqmnt_machine,ll_pond_owner_dept_other_name,ll_pond_cat_other_name;
    TextView tv_gram,tv_pond_avbl,rajaswa_thana,block_name,district_name,tv_panchayat,tv_pond_type,tv_pine,tv_water_avbl,tv_encrh_status,tv_encrh_type,tv_renovation,tv_machine,tv_village_name,tv_pond_category,tv_owner_dept_name,tv_functional;
    EditText et_khaata,et_area_by_govt_record,et_owner_name,et_remark,et_khesra,et_pondCatOtherName,et_pondOwnerDeptOtherName;
    Spinner spin_gram,spin_panchayat,spin_pond_type,spin_pine,spin_water_availibility,spin_encroachment_status,spin_encroachment_type,spin_rqrmnt_renovation,spin_rqrmnt_of_machine,spin_owner_dept,spin_pond_avbl,spin_pond_category,spin_awayab_type,spin_functional;
    Button btn_reg;

    Spinner spin_cal_area_type;
    EditText et_cal_area;
    TextView tv_calculated_area;
    RelativeLayout rl_calculator;
    //String calculateAreaType[] = {"Select", "Hectare", "Katha"};
    String calculateAreaType[] = {"Select", "Hectare"};
    String calAreaType = "", calculatedArea="";

    String inspectionID,blockID,blockName,rajswaThanaNumber,villageID="",villageName,distID,distName,latitude,longitude,panchayatCode,panchayatName="", isUpdated = "0", mob_latitude, mob_longitude;
    String pondType,pondTypeValue="",pondLakeDeptName, pondAvblValue="", pondAvblType, pondCatType,pondCatValue;
    String pineType, pineValue,waterAvbl,waterAvblValue,encrhmntStatus,encrhmntStatusValue = "",encrhmntType,encrhmntTypeValue,renovation,renovationValue = "",machine,machineValue="";

    ArrayList<DepartmentEntity> deptList = new ArrayList<DepartmentEntity>();
    //ArrayList<PondLakeDepartmentEntity> deptList = new ArrayList<PondLakeDepartmentEntity>();
    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<VillageListEntity> villageList = new ArrayList<VillageListEntity>();
    ArrayList<String> villageNameArray, deptNameArray, pineArray, waterArray, statusOfEncroachmentArray, renovationArray, machineArray, pondAvblArray,functionalArray;

    String pondTypeOption[] = {"-चयन करे-","सार्वजनिक", "निजी"};
    String encroachmentTypeOption[] = {"-चयन करे-","कच्चा", "पक्का"};

    //String pondCategoryOption[] = {"-चयन करे-","तालाब", "नहर", "अाहर", "पईन", "छोटी-छोटी नदिया", "छोटी-छोटी नाला", "पहाड़ी क्षेत्रों में जल संग्रहण", "भवनों में छत वर्षा जल संचयन", "पौधशाला सृजन एवं सघन वृक्षारोपण", "जैविक खेती एवं टपकन सिंचाई", "सौर उर्जा उपयोग को प्रोत्साहन एवं उर्जा की बचत", "अन्य"};
    String pondCategoryOption[] = {"-चयन करे-","तालाब", "नहर", "अाहर", "पईन", "अन्य"};

    int id;

    ArrayAdapter<String> deptAdapter;
    ArrayAdapter pondTypeAdaptor;
    ArrayAdapter encroachmentTypeAdaptor;
    ArrayAdapter pondCategoryAdaptor;
    ArrayAdapter villageAdapter;

    LocationManager mlocManager = null;
    ProgressBar pbar;
    private final int UPDATE_LATLNG = 2;
    static Location LastLocation = null;
    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

    LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    private String s1_data = "";
    boolean isNetwork = false;
    boolean canGetLocation = true;

    //private String s1_data="";

    String blockCode,abyabId="", abyabName="",functionalStatus;

    ArrayList<Abyab> abyabList = new ArrayList<Abyab>();
    ArrayList<String> abyabNameArray;
    ArrayAdapter<String>abyabadapter;

    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond_inspection);
        dataBaseHelper = new DataBaseHelper(getApplicationContext());

        Initialization();

        loadSpinnerYesNo();

        setSpinner();

        loadabyabSpinner();

        extractDataFromIntent();

        loadSpinnerPondLakeDept();

        rl_calculator.setVisibility(View.GONE);

        ll_pond_owner_dept_other_name.setVisibility(View.GONE);
        ll_pond_cat_other_name.setVisibility(View.GONE);



        btn_reg.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (validateData()) {
                            UpdateInspectionDetail();
                        }else{
                            Toast.makeText(PondInspectionActivity.this, "Please filled all required field", Toast.LENGTH_SHORT).show();
                        }
                    }

                });

    }

    public void setSpinner(){
        pondTypeAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pondTypeOption);
        pondTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_pond_type.setAdapter(pondTypeAdaptor);

        encroachmentTypeAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, encroachmentTypeOption);
        encroachmentTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_encroachment_type.setAdapter(encroachmentTypeAdaptor);

        pondCategoryAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, pondCategoryOption);
        pondCategoryAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_pond_category.setAdapter(pondCategoryAdaptor);
    }

    public void onShowAreaCalculator(View view){
        rl_calculator.setVisibility(View.VISIBLE);
    }

    public void onCancelCalculator(View view){
        et_cal_area.setText("");
        spin_cal_area_type.setSelection(0);
        tv_calculated_area.setText("परिकलित क्षेत्रफल: ");
        rl_calculator.setVisibility(View.GONE);
    }

    public void onUseCalculator(View view){
        et_area_by_govt_record.setText(calculatedArea);
        et_cal_area.setText("");
        spin_cal_area_type.setSelection(0);
        tv_calculated_area.setText("परिकलित क्षेत्रफल: ");
        rl_calculator.setVisibility(View.GONE);
        Utiilties.dismissKeyboard(this, et_area_by_govt_record);
    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                      //  mob_latitude = String.valueOf(loc.getLatitude());
                      //  mob_longitude =  String.valueOf(loc.getLongitude());
                        Log.d("m_lat_long", "" + mob_latitude + "*" + mob_longitude);
                        //if (loc != null)
                            //updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, (LocationListener) this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        mob_latitude = String.valueOf(loc.getLatitude());
                        mob_longitude =  String.valueOf(loc.getLongitude());
                        Log.d("m_lat_long", "" + mob_latitude + "*" + mob_longitude);
                        //if (loc != null)
                            //updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    //updateUI(loc);
                    mob_latitude = String.valueOf(loc.getLatitude());
                    mob_longitude =  String.valueOf(loc.getLongitude());
                    Log.d("m_lat_long", "" + mob_latitude + "*" + mob_longitude);
                }
            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }

    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }

    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    private void Initialization()
    {
        rajaswa_thana = (TextView) findViewById(R.id.rajaswa_thana);
        block_name = (TextView) findViewById(R.id.block_name);
        district_name = (TextView) findViewById(R.id.district_name);
        tv_pond_avbl = (TextView) findViewById(R.id.tv_pond_avbl);
        tv_village_name = (TextView) findViewById(R.id.tv_village_name);
        tv_pond_category = (TextView) findViewById(R.id.tv_pond_category);
        tv_owner_dept_name = (TextView) findViewById(R.id.tv_owner_dept_name);
        tv_gram = (TextView) findViewById(R.id.tv_gram);
        tv_functional = (TextView) findViewById(R.id.tv_functional);

        ll_comercial = (LinearLayout) findViewById(R.id.ll_comercial);
        ll_encroachment_type = (LinearLayout) findViewById(R.id.ll_encroachment_type);
        ll_avbl_type = (LinearLayout) findViewById(R.id.ll_avbl_type);
        ll_rqmnt_machine = (LinearLayout) findViewById(R.id.ll_rqmnt_machine);
        ll_pond_owner_dept_other_name = (LinearLayout) findViewById(R.id.ll_pond_owner_dept_other_name);
        ll_pond_cat_other_name = (LinearLayout) findViewById(R.id.ll_pond_cat_other_name);

        et_khaata = (EditText)findViewById(R.id.et_khaata);
        et_khesra = (EditText)findViewById(R.id.et_khesra);
        et_area_by_govt_record =(EditText)findViewById(R.id.et_area_by_govt_record);
        et_owner_name = (EditText)findViewById(R.id.et_owner_name);
        et_remark = (EditText)findViewById(R.id.et_remark);
        et_pondCatOtherName = (EditText)findViewById(R.id.et_pondCatOtherName);
        et_pondOwnerDeptOtherName = (EditText)findViewById(R.id.et_pondOwnerDeptOtherName);

        //spin_panchayat = (Spinner) findViewById(R.id.spin_panchayat);
        spin_pond_type = (Spinner)findViewById(R.id.spin_pond_type);
        spin_pine =(Spinner)findViewById(R.id.spin_pine);
        spin_water_availibility = (Spinner)findViewById(R.id.spin_water_availibility);
        spin_encroachment_status = (Spinner)findViewById(R.id.spin_encroachment_status);
        spin_encroachment_type = (Spinner)findViewById(R.id.spin_encroachment_type);
        spin_rqrmnt_renovation = (Spinner)findViewById(R.id.spin_rqrmnt_renovation);
        spin_rqrmnt_of_machine = (Spinner)findViewById(R.id.spin_rqrmnt_of_machine);
        spin_owner_dept = (Spinner)findViewById(R.id.spin_owner_dept);
        spin_pond_avbl = (Spinner)findViewById(R.id.spin_pond_avbl);
        spin_pond_category = (Spinner)findViewById(R.id.spin_pond_category);
        spin_gram = (Spinner)findViewById(R.id.spin_gram);
        spin_awayab_type = (Spinner)findViewById(R.id.spin_awayab_type);
        spin_functional = (Spinner)findViewById(R.id.spin_functional);

        spin_cal_area_type = findViewById(R.id.spin_cal_area_type);
        et_cal_area = findViewById(R.id.et_cal_area);
        tv_calculated_area = findViewById(R.id.tv_calculated_area);
        rl_calculator = findViewById(R.id.rl_calculator);
        et_cal_area.addTextChangedListener(inputTextWatcher );

       //spin_panchayat.setOnItemSelectedListener(this);
        spin_pond_type.setOnItemSelectedListener(this);
        spin_pine.setOnItemSelectedListener(this);
        spin_water_availibility.setOnItemSelectedListener(this);
        spin_encroachment_status.setOnItemSelectedListener(this);
        spin_encroachment_type.setOnItemSelectedListener(this);
        spin_rqrmnt_renovation.setOnItemSelectedListener(this);
        spin_rqrmnt_of_machine.setOnItemSelectedListener(this);
        spin_owner_dept.setOnItemSelectedListener(this);
        spin_pond_avbl.setOnItemSelectedListener(this);
        spin_pond_category.setOnItemSelectedListener(this);
        spin_gram.setOnItemSelectedListener(this);
        spin_awayab_type .setOnItemSelectedListener(this);
        spin_functional.setOnItemSelectedListener(this);

        btn_reg = (Button) findViewById(R.id.btn_reg);


        tv_panchayat = (TextView) findViewById(R.id.tv_panchayat);
        tv_pond_type = (TextView) findViewById(R.id.tv_pond_type);
        tv_pine = (TextView) findViewById(R.id.tv_pine);
        tv_water_avbl = (TextView) findViewById(R.id.tv_water_avbl);
        tv_encrh_status = (TextView) findViewById(R.id.tv_encrh_status);
        tv_encrh_type = (TextView) findViewById(R.id.tv_encrh_type);
        tv_renovation = (TextView) findViewById(R.id.tv_renovation);
        tv_machine = (TextView) findViewById(R.id.tv_machine);

        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, calculateAreaType);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_cal_area_type.setAdapter(adaptor);
        spin_cal_area_type.setOnItemSelectedListener(this);
    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;
        if (pondAvblValue.equals("")) {
            tv_pond_avbl.setError(getString(R.string.fieldRequired));
            focusView = tv_pond_avbl;
            validate = false;
            //chk_msg_save("Please select");

        }

        if (et_remark.getText().toString().equals("")) {
            et_remark.setError(getString(R.string.fieldRequired));
            focusView = et_remark;
            validate = false;
        }

        if(pondAvblValue.contains("Y")){

            if (pondCatValue == null) {
                tv_pond_category.setError(getString(R.string.fieldRequired));
                focusView = tv_pond_category;
                validate = false;
            }

            if (pondTypeValue.equals("")) {
                tv_pond_type.setError(getString(R.string.fieldRequired));
                focusView = tv_pond_type;
                validate = false;
            }

            if (pondTypeValue.equals("C")){
                if (et_khaata.getText().toString().equals("")) {
                    et_khaata.setError(getString(R.string.fieldRequired));
                    focusView = et_khaata;
                    validate = false;
                }

                if (et_khesra.getText().toString().equals("")) {
                    et_khesra.setError(getString(R.string.fieldRequired));
                    focusView = et_khesra;
                    validate = false;
                }
                try{
                    if (et_area_by_govt_record.getText().toString().equals("")) {
                        et_area_by_govt_record.setError(getString(R.string.fieldRequired));
                        focusView = et_area_by_govt_record;
                        validate = false;
                    }else if(Double.parseDouble(et_area_by_govt_record.getText().toString()) <= 0.0){
                        et_area_by_govt_record.setError("कृपया सही क्षेत्रफल डालें");
                        focusView = et_area_by_govt_record;
                        validate = false;
                    }
                }catch (Exception e){
                    //Toast.makeText(this, e.printStackTrace(), Toast.LENGTH_SHORT).show();
                }

                if (pondLakeDeptName == null) {
                    tv_owner_dept_name.setError(getString(R.string.fieldRequired));
                    focusView = tv_owner_dept_name;
                    validate = false;
                }
                if (pineType == null) {
                    tv_pine.setError(getString(R.string.fieldRequired));
                    focusView = tv_pine;
                    validate = false;
                }
                if (waterAvbl == null) {
                    tv_water_avbl.setError(getString(R.string.fieldRequired));
                    focusView = tv_water_avbl;
                    validate = false;
                }
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
                if (renovation == null) {
                    tv_renovation.setError(getString(R.string.fieldRequired));
                    focusView = tv_renovation;
                    validate = false;
                }

                if(renovationValue.contains("Y")){
                    if (machine == null) {
                        tv_machine.setError(getString(R.string.fieldRequired));
                        focusView = tv_machine;
                        validate = false;
                    }
                }

                if (functionalStatus == null || functionalStatus.equals("")) {
                    tv_functional.setError(getString(R.string.fieldRequired));
                    focusView = tv_functional;
                    validate = false;
                }

            }
        }

        if (!validate) focusView.requestFocus();

        return validate;
    }

    public void setValue(){

    }

    private void UpdateInspectionDetail(){
        long result = 0;
        String mob_latitude = "", mob_longitude = "";
            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String khataKhesraNo = et_khaata.getText().toString().trim()+"/"+et_khesra.getText().toString().trim();

            PondInspectionEntity pondInspectionEntity=new PondInspectionEntity();

            pondInspectionEntity.setInspectionID(inspectionID);
            pondInspectionEntity.setId(id);
            pondInspectionEntity.setDistID(distID);
            pondInspectionEntity.setDistName(distName);
            pondInspectionEntity.setBlockID(blockID);
            pondInspectionEntity.setBlockName(blockName);
            pondInspectionEntity.setRajswaThanaNumber(rajswaThanaNumber);
            pondInspectionEntity.setVillageID(villageID);
            pondInspectionEntity.setVillageName(villageName);
            pondInspectionEntity.setName_of_undertaken(pondLakeDeptName);
            pondInspectionEntity.setVerified_Date(currentDate);
            pondInspectionEntity.setPondAvblValue(pondAvblValue);
            pondInspectionEntity.setPondCatValue(pondCatValue);

            pondInspectionEntity.setPanchayat_Code(panchayatCode);

            pondInspectionEntity.setPanchayat_Name(panchayatName);
            pondInspectionEntity.setKhata_Khesra_No(khataKhesraNo);
            pondInspectionEntity.setPrivate_or_Public(pondTypeValue);
            pondInspectionEntity.setArea_by_Govt_Record(et_area_by_govt_record.getText().toString());
            pondInspectionEntity.setPondCatName(et_pondCatOtherName.getText().toString());
            pondInspectionEntity.setPondOwnerOtherDeptName(et_pondOwnerDeptOtherName.getText().toString());
            pondInspectionEntity.setConnectedWithPine(pineValue);
            pondInspectionEntity.setAvailability_Of_Water(waterAvblValue);
            pondInspectionEntity.setStatus_of_Encroachment(encrhmntStatusValue);
            pondInspectionEntity.setTypes_of_Encroachment(encrhmntTypeValue);
            pondInspectionEntity.setRequirement_of_Renovation(renovationValue);
            pondInspectionEntity.setRecommended_Execution_Dept("");
            pondInspectionEntity.setRemarks(et_remark.getText().toString());
            pondInspectionEntity.setIsUpdated("1");
            pondInspectionEntity.setRequirement_of_machine(machineValue);
            pondInspectionEntity.setAbyabID(abyabId);
            pondInspectionEntity.setAbyabName(abyabName);
            pondInspectionEntity.setDeptId(userInfo.getDeptId());
            pondInspectionEntity.setFunctionalStatus(functionalStatus);


        result = new DataBaseHelper(this).UpdatePondInspectionDetail(pondInspectionEntity);

            String type = "new";

            if (isUpdated != null && isUpdated.equalsIgnoreCase("1")){
                type = "edit";
            }

            if (result > 0) {
                if(id == 0){
                    id = (int) result;
                    type = "new";
                }
                Toast.makeText(getApplicationContext(),"डाटा सफलतापूर्वक सेव हो गया",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(this, MultiplePhotoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                Log.d("ggeuhshd",""+id);
                intent.putExtra("KEY_PID", id);
                intent.putExtra("pupose", type);
                intent.putExtra("isOpen", s1_data);
                startActivity(intent);
                  finish();
            }else {
                Toast.makeText(getApplicationContext(),"डाटा सेव नहीं हुआ", Toast.LENGTH_LONG).show();
            }

    }

    public void extractDataFromIntent(){
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);

        panchayatName = intent.getStringExtra("panchayatName");
        panchayatCode = intent.getStringExtra("panchayatCode");

        blockCode = intent.getStringExtra("blockCode");
        blockName = intent.getStringExtra("blockName");

        userInfo = getUserInfo();

        //if(userInfo.getUserrole().equals("PRDDST")){

       // }

       if (id != 0){
           PondInspectionDetail info = dataBaseHelper.getPondInspectionDetails(String.valueOf(id));

//           if(info.getDeptId() != null && userInfo == null){
//               userInfo = new UserDetails();
//               userInfo.setDeptId(info.getDeptId());
//           }

           inspectionID = info.getInspectionID();
           distID = info.getDistrictCode();
           blockID = info.getBlockCode();
           blockName = info.getBlockName();
           rajswaThanaNumber = info.getRajswaThanaNo();
           villageID = info.getVillage();
           villageName = info.getVillageName();
           distID = info.getDistrictCode();
           distName = info.getDistName();
           latitude = info.getLatitude();
           longitude = info.getLongitude();
           pondLakeDeptName = info.getOwnerName();
           panchayatName = info.getPanchayatName();
           panchayatCode = info.getPanchayatCode();
           abyabId = info.getAbyabID();
           abyabName = info.getAbyabName();
           isUpdated = info.getIsUpdated();

           try{
               if(info.getAreaByGovtRecord() != null){
                   et_area_by_govt_record.setText(info.getAreaByGovtRecord());
               }

               if(info.getPondCatValue() != null){
                   spin_pond_category.setSelection(Arrays.asList(pondCategoryOption).indexOf(info.getPondCatValue()));
               }
           }catch (Exception e){
               Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
           }

           loadVillageData(panchayatCode);
           spin_gram.setEnabled(false);

           if(info.getKhataKhesraNo() != null && info.getKhataKhesraNo().contains("/")){
               String[] kkArray = info.getKhataKhesraNo().split("/");
               if(kkArray.length > 0){
                   String khata = kkArray[0];
                   if(khata != null && !khata.contains("NA") && !khata.toLowerCase().contains("anytype")){
                       et_khaata.setText(khata);
                   }
               }

               if(kkArray.length > 1){
                   String khesraNo = kkArray[1];
                   if(khesraNo != null && !khesraNo.contains("NA") && !khesraNo.toLowerCase().contains("anytype")){
                       et_khesra.setText(khesraNo);
                   }
               }

           }
           rajaswa_thana.setText(panchayatName);
           block_name.setText(blockName);
           district_name.setText(distName);
           tv_village_name.setText(villageName);

           if (isUpdated != null && isUpdated.contains("1")){
               //et_khaata.setText(info.getKhataKhesraNo());
               et_area_by_govt_record.setText(info.getAreaByGovtRecord());
               et_owner_name.setText(info.getOwnerName());
               et_remark.setText(info.getRemarks());
               if(info.getPondOwnerOtherDeptName() != null){
                   et_pondOwnerDeptOtherName.setText(info.getPondOwnerOtherDeptName());
               }

               pondAvblValue = info.getPondAvblValue();
               pondAvblType = pondAvblValue == "Y" ? "हाँ" : "नहीं";

               if(pondAvblValue.contains("Y")){
                   pondCatValue = info.getPondCatValue();
                   if (pondCatValue.contains("T")){
                       pondCatType = "तालाब";
                   }else if(pondCatValue.contains("N")){
                       pondCatType = "नहर";
                   }else if(pondCatValue.contains("A")){
                       pondCatType = "अाहर";
                   }else if(pondCatValue.contains("P")){
                       pondCatType = "पईन";
                   }else if(pondCatValue.contains("ND")){
                       pondCatType = "छोटी-छोटी नदिया";
                   }else if(pondCatValue.contains("NL")){
                       pondCatType = "छोटी-छोटी नाला";
                   }else if(pondCatValue.contains("JS")){
                       pondCatType = "पहाड़ी क्षेत्रों में जल संग्रहण";
                   }else if(pondCatValue.contains("BCJ")){
                       pondCatType = "भवनों में छत वर्षा जल संचयन";
                   }else if(pondCatValue.contains("PSSV")){
                       pondCatType = "पौधशाला सृजन एवं सघन वृक्षारोपण";
                   }else if(pondCatValue.contains("JKTS")){
                       pondCatType = "जैविक खेती एवं टपकन सिंचाई";
                   }else if(pondCatValue.contains("SUUP")){
                       pondCatType = "सौर उर्जा उपयोग को प्रोत्साहन एवं उर्जा की बचत";
                   }else if(pondCatValue.contains("O")){
                       pondCatType = "अन्य";
//                       if(info.getPondCatName() != null){
//                           et_pondCatOtherName.setText(info.getPondCatName());
//                       }
                   }

                   pondTypeValue = info.getPrivateOrPublic();
                   pondType = pondTypeValue == "C" ? "सार्वजनिक" : "निजी";

                   if(pondTypeValue.contains("C")){
                       pineValue = info.getConnectedWithPine();
                       pineType = pineValue == "Y" ? "हाँ" : "नहीं";

                       waterAvblValue = info.getAvailiablityOfWater();
                       waterAvbl = waterAvblValue == "Y" ? "हाँ" : "नहीं";

                       encrhmntStatusValue = info.getStatusOfEncroachment();
                       encrhmntStatus = encrhmntStatusValue == "Y" ? "हाँ" : "नहीं";

                       if (encrhmntStatusValue.contains("Y")){
                           encrhmntTypeValue = info.getTypesOfEncroachment();
                           encrhmntType = encrhmntTypeValue == "K" ? "कच्चा" : "पक्का";
                       }
                       renovationValue = info.getRequirementOfRenovation();
                       renovation = renovationValue == "Y" ? "हाँ" : "नहीं";

                       machineValue = info.getRequirementOfMachine();
                       machine = machineValue == "Y" ? "हाँ" : "नहीं";

                       functionalStatus = info.getFunctional();
                       spin_functional.setSelection(functionalStatus.contains("Y") ? 1 : 2);
                   }
               }


               spin_awayab_type.setSelection((((ArrayAdapter<String>) spin_awayab_type.getAdapter()).getPosition(abyabName)));

               spin_pond_avbl.setSelection(pondAvblValue.contains("Y") ? 1 : 2);
               if(pondAvblValue != null && pondAvblValue.contains("Y")){
                   if (pondCatValue.contains("T")){
                       spin_pond_category.setSelection(1);
                   }else if(pondCatValue.contains("N")){
                       spin_pond_category.setSelection(2);
                   }else if(pondCatValue.contains("A")){
                       spin_pond_category.setSelection(3);
                   }else if(pondCatValue.contains("P")){
                       spin_pond_category.setSelection(4);
                   }else if(pondCatValue.contains("O")){
                       spin_pond_category.setSelection(5);
                       if(info.getPondOwnerOtherDeptName() != null){
                           et_pondCatOtherName.setText(info.getPondCatName());
                       }
                   }

                   if (pondTypeValue != null){
                       int pondIndex = pondTypeValue.contains("C") ? 1 : 2;
                       //Toast.makeText(this, String.valueOf(pondIndex), Toast.LENGTH_SHORT).show();

                       spin_pond_type.setSelection(pondIndex);
                       String P_Type=info.getPrivateOrPublic();
                       if(P_Type.equalsIgnoreCase("C")){
                           spin_owner_dept.setSelection(Integer.parseInt(pondLakeDeptName));
                           spin_pine.setSelection(pineValue.contains("Y") ? 1 : 2);
                           spin_water_availibility.setSelection(waterAvblValue.contains("Y") ? 1 : 2);
                           spin_encroachment_status.setSelection(encrhmntStatusValue.contains("Y") ? 1 : 2);

                           if(encrhmntStatusValue.contains("Y")){
                               spin_encroachment_type.setSelection(encrhmntTypeValue.contains("K") ? 1 : 2);
                           }
                           spin_rqrmnt_renovation.setSelection(renovationValue.contains("Y") ? 1 : 2);
                           spin_rqrmnt_of_machine.setSelection(machineValue.contains("Y") ? 1 : 2);
                       }
                   }
               }

           }
       }else{
           inspectionID = "";
           blockID = blockCode;
           rajswaThanaNumber = "";
           villageID = "";
           villageName = "";
           distID = userInfo.getDistrictCode();
           distName = userInfo.getDistName();
           latitude = "";
           longitude = "";
            Log.e("DeptId: ", userInfo.getDeptId());

//           if(userInfo.getUserrole().equals("PRDDST")){
//               blockID = intent.getStringExtra("blockCode");
//               blockName = intent.getStringExtra("blockName");
//           }

           rajaswa_thana.setText(panchayatName);
           block_name.setText(blockName);
           district_name.setText(distName);
           tv_village_name.setText(villageName);

           spin_pond_avbl.setSelection(1);
           spin_pond_avbl.setEnabled(false);
           loadVillageData(panchayatCode);

       }

    }

    public UserDetails getUserInfo(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        return dataBaseHelper.getUserDetails(username, password);
    }


    public void loadSpinnerPondLakeDept() {
        deptList = dataBaseHelper.getExecDepartmentList();
        //deptList = dataBaseHelper.getPondLakeDeptList();
        if(deptList.size()>0){
            setDeptSpinner();
        }else{
            new SyncDepartmentData().execute();
        }
    }

    public void setDeptSpinner(){
        deptNameArray = new ArrayList<String>();
        deptNameArray.add("-चयन करे-");
        int i = 0;
        for (DepartmentEntity info : deptList) {
            deptNameArray.add(info.getDeptNameHn());
            if(info.getDeptId().equals(userInfo.getDeptId())){
                i = deptList.indexOf(info);
            }
        }
        deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deptNameArray);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_owner_dept.setAdapter(deptAdapter);

        //if(userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
            spin_owner_dept.setSelection(i+1);
            spin_owner_dept.setEnabled(false);
        //}
    }

    public void loadVillageData(String pan_Code) {

        villageList = dataBaseHelper.getVillageList(pan_Code);
        if (villageList.size() > 0){
            setVillageData();
        }else{
            new SyncVillageData().execute(pan_Code);
        }
    }

    public void setVillageData(){
        villageNameArray = new ArrayList<String>();
        villageNameArray.add("-गाँव का चयन करे-");
        int i = 0, setId= 0;
        for (VillageListEntity dept_list : villageList) {
            villageNameArray.add(dept_list.getVillName());
            if(villageID.equals(dept_list.getVillCode())){
                setId= i;
            }
            i++;
        }
        villageAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, villageNameArray);
        villageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_gram.setAdapter(villageAdapter);

        if(id > 0){
            spin_gram.setSelection(setId+1);
        }
    }

    public ArrayList<String> loadSpinnerYesNo() {
        //DataBaseHelper helper = new DataBaseHelper(getApplicationContext());
        ArrayList<String> list = new ArrayList<String>();
        list.add(getResources().getString(R.string.yes));
        list.add(getResources().getString(R.string.no));

        ArrayList<String> sList = new ArrayList<String>();


        if (list.size() > 0) {
            String hints = "-- " + getResources().getString(R.string.select_spinner_hint) + "--";
            sList.add(hints);
        }
        for (int i = 0; i < list.size(); i++) {
            sList.add(list.get(i));
        }

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, sList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_pond_avbl.setAdapter(spinnerAdapter);
        pondAvblArray = sList;

        spin_pine.setAdapter(spinnerAdapter);
        pineArray = sList;
        spin_water_availibility.setAdapter(spinnerAdapter);
        waterArray = sList;
        spin_encroachment_status.setAdapter(spinnerAdapter);
        statusOfEncroachmentArray = sList;
        spin_rqrmnt_renovation.setAdapter(spinnerAdapter);
        renovationArray = sList;
        spin_rqrmnt_of_machine.setAdapter(spinnerAdapter);
        machineArray = sList;

        spin_functional.setAdapter(spinnerAdapter);
        functionalArray = sList;

        return list;
    }

    public void loadabyabSpinner() {
        abyabList = dataBaseHelper.getAbyab("");
        abyabNameArray = new ArrayList<String>();
        abyabNameArray.add("-चयन करे-");
        int i = 0, setId= 0;
        for (Abyab sub_abyb : abyabList) {
            abyabNameArray.add(sub_abyb.getAbyab_name());
            if(abyabId.equals(sub_abyb.getAbyab_Code())){
                setId= i;
            }
            i++;
        }
        abyabadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, abyabNameArray);
        abyabadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_awayab_type.setAdapter(abyabadapter);

        if(id > 0){
            spin_awayab_type.setSelection(setId+1);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_awayab_type:
                if (position > 0) {
                    Abyab abayab = abyabList.get(position - 1);
                    abyabId = abayab.getAbyab_Code();
                    abyabName = abayab.getAbyab_name();
                    //Toast.makeText(this, abyabId, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.spin_gram:
                if (position > 0) {
                    VillageListEntity village = villageList.get(position - 1);
                    villageID = village.getVillCode();
                    villageName = village.getVillName();
                    //Toast.makeText(this, villageID, Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.spin_owner_dept:
                int pos = position;
                if (pos > 0) {
                    tv_owner_dept_name.setError(null);
                    pondLakeDeptName = String.valueOf(deptList.get(pos - 1).getDeptId());
                    if (pos == 4){
                        ll_pond_owner_dept_other_name.setVisibility(View.VISIBLE);
                    }else{
                        ll_pond_owner_dept_other_name.setVisibility(View.GONE);
                    }
                    //panchayatName = panchayatList.get(pos - 1).getPname();
                }
                break;
            case R.id.spin_pond_type:
                if (position > 0) {
                    tv_pond_type.setError(null);
                    pondType=pondTypeOption[position].toString();

                    if(pondType.equals("सार्वजनिक")){
                        pondTypeValue="C";
                        ll_comercial.setVisibility(View.VISIBLE);
                    }
                    else if(pondType.equals("निजी")){
                        pondTypeValue="P";
                        ll_comercial.setVisibility(View.GONE);
                    }
                    //Toast.makeText(PondInspectionActivity.this, pondType, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.spin_pond_category:
                if (position > 0) {
                    tv_pond_category.setError(null);
                    pondCatType=pondCategoryOption[position].toString();

                    if(pondCatType.equals("तालाब")){
                        pondCatValue="T";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("नहर")){
                        pondCatValue="N";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("अाहर")){
                        pondCatValue="A";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("पईन")){
                        pondCatValue="P";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("छोटी-छोटी नदिया")){
                        pondCatValue="ND";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("छोटी-छोटी नाला")){
                        pondCatValue="NL";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("पहाड़ी क्षेत्रों में जल संग्रहण")){
                        pondCatValue="JS";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("भवनों में छत वर्षा जल संचयन")){
                        pondCatValue="BCJ";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("पौधशाला सृजन एवं सघन वृक्षारोपण")){
                        pondCatValue="PSSV";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("जैविक खेती एवं टपकन सिंचाई")){
                        pondCatValue="JKTS";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("सौर उर्जा उपयोग को प्रोत्साहन एवं उर्जा की बचत")){
                        pondCatValue="SUUP";
                        ll_pond_cat_other_name.setVisibility(View.GONE);
                    }
                    else if(pondCatType.equals("अन्य")){
                        pondCatValue="O";
                        ll_pond_cat_other_name.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case R.id.spin_pond_avbl:
                if (position > 0) {
                    tv_pond_avbl.setError(null);
                    pondAvblType=pondAvblArray.get(position).toString();

                    if(pondAvblType.equals("हाँ")){
                        pondAvblValue="Y";
                        ll_avbl_type.setVisibility(View.VISIBLE);
                    }
                    else if(pondAvblType.equals("नहीं")){
                        pondAvblValue="N";
                        ll_avbl_type.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.spin_pine:
                if (position > 0) {
                    tv_pine.setError(null);
                    pineType=pineArray.get(position).toString();

                    if(pineType.equals("हाँ")){
                        pineValue="Y";
                    }
                    else if(pineType.equals("नहीं")){
                        pineValue="N";
                    }

                }
                break;

            case R.id.spin_water_availibility:
                if (position > 0) {
                    tv_water_avbl.setError(null);
                    waterAvbl=waterArray.get(position).toString();

                    if(waterAvbl.equals("हाँ")){
                        waterAvblValue="Y";
                    }
                    else if(waterAvbl.equals("नहीं")){
                        waterAvblValue="N";
                    }
                }
                break;

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

            case R.id.spin_rqrmnt_renovation:
                if (position > 0) {
                    tv_renovation.setError(null);
                    renovation=renovationArray.get(position).toString();

                    if(renovation.equals("हाँ")){
                        renovationValue="Y";
                        ll_rqmnt_machine.setVisibility(View.VISIBLE);
                    }
                    else if(renovation.equals("नहीं")){
                        renovationValue="N";
                        ll_rqmnt_machine.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.spin_rqrmnt_of_machine:
                if (position > 0) {
                    tv_machine.setError(null);
                    machine=machineArray.get(position).toString();

                    if(machine.equals("हाँ")){
                        machineValue="Y";
                    }
                    else if(machine.equals("नहीं")){
                        machineValue="N";
                    }
                }
                break;

            case R.id.spin_cal_area_type:
                if (position > 0) {
                    calAreaType = calculateAreaType[position];
                }
                break;

            case R.id.spin_functional:
                if (position > 0) {
                    tv_machine.setError(null);
                    String machine=functionalArray.get(position).toString();

                    if(machine.equals("हाँ")){
                        functionalStatus="Y";
                    }

                    else if(machine.equals("नहीं")){
                        functionalStatus="N";
                    }

                    tv_functional.setError(null);
                }else{
                    functionalStatus = "";
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        //updateUI(location);
        mob_latitude = String.valueOf(loc.getLatitude());
        mob_longitude =  String.valueOf(loc.getLongitude());
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public void chk_msg_save(String msg) {
        // final String wantToUpdate;
        android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(this);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.logo1);
        ab.setTitle("Data Saved");
        ab.setMessage(msg);
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();

            }
        });

        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }

    private TextWatcher inputTextWatcher = new TextWatcher()
    {

        public void beforeTextChanged(CharSequence s, int start, int count,int after)
        {

        }

        public void onTextChanged(CharSequence s, int start, int before, int count)
        {
            if (et_cal_area.getText().length() >0)
            {
                if(calAreaType.isEmpty()){
                    Toast.makeText(PondInspectionActivity.this, "कृपया क्षेत्र प्रकार चुनें", Toast.LENGTH_SHORT).show();
                }else{
                    calculateAreaUnitWise();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {}
    };

    public void calculateAreaUnitWise(){
        try{
            switch (calAreaType){
                case "Hectare":
                    calculatedArea = String.format("%.4f",Double.parseDouble(et_cal_area.getText().toString()) * 2.4711);
                    tv_calculated_area.setText("परिकलित क्षेत्रफल: "+calculatedArea);
                    break;

                case "Katha":
                    calculatedArea = String.format("%.4f",Double.parseDouble(et_cal_area.getText().toString()) * 0.0338);
                    tv_calculated_area.setText("परिकलित क्षेत्रफल: "+calculatedArea);
                    break;
            }
        }catch (Exception e){
            Toast.makeText(this, "क्षेत्रफल बदलने में विफल, ठीक से क्षेत्रफल दर्ज करें", Toast.LENGTH_SHORT).show();
        }

    }

    private class SyncVillageData extends AsyncTask<String, Void, ArrayList<VillageListEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(PondInspectionActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("ग्राम लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<VillageListEntity> doInBackground(String...arg) {
            return WebServiceHelper.getVillageListData(blockID);
        }

        @Override
        protected void onPostExecute(ArrayList<VillageListEntity> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
                long i= helper.setVillageDataToLocal(result);

                if(i>0)
                {
                    Toast.makeText(getApplicationContext(), "Village Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to update village",Toast.LENGTH_SHORT).show();
                }

                villageList = getPanchayatWiseVillage(result);
                setVillageData();
            }else{
                Toast.makeText(getApplicationContext(), "Village list not found!!",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SyncDepartmentData extends AsyncTask<String, Void, ArrayList<DepartmentEntity>> {

        public SyncDepartmentData() {
        }

        private final ProgressDialog dialog = new ProgressDialog(PondInspectionActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setMessage("विभाग लोड हो रहा है...");
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
                //DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

                long i= dataBaseHelper.setExecDeptDataToLocal(result);
                deptList = result;
                if(i>0)
                {
                    setDeptSpinner();
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

    public ArrayList<VillageListEntity> getPanchayatWiseVillage(ArrayList<VillageListEntity> list){
        ArrayList<VillageListEntity> array = new ArrayList<>();
        for (VillageListEntity info : list){
            if(info.getPanchayatCode().equals(panchayatCode)){
                array.add(info);
            }
        }

        return  array;
    }
}
