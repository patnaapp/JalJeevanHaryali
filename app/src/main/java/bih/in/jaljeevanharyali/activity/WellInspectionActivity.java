package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.AlertDialog;
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
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.DepartmentEntity;
import bih.in.jaljeevanharyali.entity.GetLocation;
import bih.in.jaljeevanharyali.entity.PondLakeDepartmentEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.WellInspectionEntity;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.CommonPref;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class WellInspectionActivity extends Activity implements AdapterView.OnItemSelectedListener, LocationListener {

    GetLocation gps;
    DataBaseHelper dataBaseHelper;

    LinearLayout ll_comercial_well,ll_encrh_type,ll_avbl_type,ll_well_owner_dept_other_name,ll_machine_rqmnt,ll_well_abvl;
    TextView tv_gram,rajaswa_thana,block_name,district_name,tv_panchayat,tv_well_type,tv_flier,tv_encrh_status,tv_encrh_type,tv_renovation,tv_machine,tv_well_avbl,tv_village_name,tv_owner_dept_name;
    EditText et_khaata,et_outer_diameter,et_owner_name,et_remark,et_khesra,et_wellOwnerDeptOtherName;
    Spinner spin_gram,spin_panchayat,spin_well_type,spin_flier,spin_encroachment_status,spin_encroachment_type,spin_rqrmnt_renovation,spin_rqrmnt_of_machine,spin_owner_dept,spin_well_avbl,spin_functional;
    Button btn_reg;

    String inspectionID,blockID,blockName,rajswaThanaNumber,villageID,villageName,distID, distName,latitude,longitude,panchayatCode,panchayatName="", isUpdated = "0", mob_latitude, mob_longitude;
    String wellType,wellTypeValue="",wellDeptName;
    String flierType, flierValue ="",waterAvbl,waterAvblValue,encrhmntStatus,encrhmntStatusValue = "",encrhmntType,encrhmntTypeValue,renovation,renovationValue="",machine,machineValue,wellAvblValue ="",wellAvblType;
    String structureId="", functionalStatus;

    ArrayList<DepartmentEntity> deptList = new ArrayList<DepartmentEntity>();
    ArrayList<VillageListEntity> villageList = new ArrayList<VillageListEntity>();
    ArrayList<String> villageNameArray,deptNameArray, flierArray, waterArray, statusOfEncroachmentArray, renovationArray, machineArray,wellAvblArray, functionalArray;

    String wellTypeOption[] = {"-चयन करे-","सार्वजनिक", "निजी"};
    String encroachmentTypeOption[] = {"-चयन करे-","कच्चा", "पक्का"};

    int id;

    ArrayAdapter<String> deptAdapter;
    ArrayAdapter wellTypeAdaptor;
    ArrayAdapter encroachmentTypeAdaptor;
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
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    private String s1_data="";

    String blockCode;
    UserDetails userInfo;

    TextView tv_header,tv_functional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_inspection);
        dataBaseHelper = new DataBaseHelper(this);
        Initialization();

        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        panchayatName = intent.getStringExtra("panchayatName");
        panchayatCode = intent.getStringExtra("panchayatCode");
        structureId = getIntent().getStringExtra("structureId");

        blockCode = intent.getStringExtra("blockCode");
        blockName = intent.getStringExtra("blockName");

        userInfo = getUserInfo();

        updateTitles();
        loadSpinnerPondLakeDept();
        loadSpinnerYesNo();

        ll_well_owner_dept_other_name.setVisibility(View.GONE);

        wellTypeAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, wellTypeOption);
        wellTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_well_type.setAdapter(wellTypeAdaptor);

        encroachmentTypeAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, encroachmentTypeOption);
        encroachmentTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_encroachment_type.setAdapter(encroachmentTypeAdaptor);

        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        extractDataFromIntent();

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    UpdateInspectionDetail();
                }else{
                    Toast.makeText(WellInspectionActivity.this, "Please filled all required field", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void updateTitles(){
        tv_header = findViewById(R.id.tv_header);

        if (structureId.equals("2")){
            tv_header.setText("कुँआ का सत्यापन/सर्वेक्षण");
            tv_well_avbl.setText("कुँआ की उपलब्धता? *");
            tv_well_type.setText("कुँआ के स्वामित्व *");
        }else{
            tv_header.setText("चापाकल का सत्यापन/सर्वेक्षण");
            tv_well_avbl.setText("चापाकल की उपलब्धता? *");
            tv_well_type.setText("चापाकल के स्वामित्व *");
        }
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
                        mob_latitude = String.valueOf(loc.getLatitude());
                        mob_longitude =  String.valueOf(loc.getLongitude());
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
                   // mob_latitude = String.valueOf(loc.getLatitude());
                    //mob_longitude =  String.valueOf(loc.getLongitude());
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

    public void validateDetail(){
        String khata = et_khaata.getText().toString();
    }

    public UserDetails getUserInfo(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        return dataBaseHelper.getUserDetails(username, password);
    }

    public void extractDataFromIntent(){


        if(id != 0){
            WellInspectionEntity info = dataBaseHelper.getWellInspectionDetails(String.valueOf(id));

            inspectionID = String.valueOf(info.getId());
            distID = info.getDistID();
            blockID = info.getBlockID();
            blockName = info.getBlockName();
            rajswaThanaNumber = info.getRajswaThanaNumber();
            villageID = info.getVillageID();
            villageName = info.getVillageName();
            distID = info.getDistID();
            distName = info.getDistName();
            wellDeptName = info.getName_of_undertaken();
            latitude = info.getLatitude();
            longitude = info.getLongitude();
            panchayatCode = info.getPanchayat_Code();
            panchayatName = info.getPanchayat_Name();
            isUpdated = info.getIsUpdated();

            loadVillageData(panchayatCode);
            spin_gram.setEnabled(false);

            if(info.getKhata_Khesra_No().contains("/")){
                String[] kkArray = info.getKhata_Khesra_No().split("/");
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

            rajaswa_thana.setText(rajswaThanaNumber);
            block_name.setText(blockName);
            district_name.setText(distName);
            tv_village_name.setText(villageName);

            if (isUpdated != null && isUpdated.contains("1")){

                //et_khaata.setText(info.getKhata_Khesra_No());
                et_outer_diameter.setText(info.getOutside_Diamter());
                et_owner_name.setText(info.getName_of_undertaken());
                et_remark.setText(info.getRemarks());

                functionalStatus = info.getFunctional();
                spin_functional.setSelection(functionalStatus.contains("Y") ? 1 : 2);

                if(info.getWellOwnerOtherDeptName() != null){
                    et_wellOwnerDeptOtherName.setText(info.getWellOwnerOtherDeptName());
                }

                wellAvblValue = info.getWellAvblValue();
                wellAvblType = wellAvblValue == "Y" ? "हाँ" : "नहीं";

                if(wellAvblValue.contains("Y")){
                    wellTypeValue = info.getPrivate_or_Public();
                    wellType = wellTypeValue == "C" ? "सार्वजनिक" : "निजी";

                    if(wellTypeValue.contains("C")){
                        flierValue = info.getRequirement_Of_Flyer();
                        flierType = flierValue == "Y" ? "हाँ" : "नहीं";

                        encrhmntStatusValue = info.getStatus_of_Encroachment();
                        encrhmntStatus = encrhmntStatusValue == "Y" ? "हाँ" : "नहीं";

                        if (encrhmntStatusValue.contains("Y")) {
                            encrhmntTypeValue = info.getTypes_of_Encroachment();
                            encrhmntType = encrhmntTypeValue == "K" ? "कच्चा" : "पक्का";
                        }

                        renovationValue = info.getRequirement_of_Renovation();
                        renovation = renovationValue == "Y" ? "हाँ" : "नहीं";

                        if(flierValue.contains("Y") || renovationValue.contains("Y")){
                            machineValue = info.getRequirement_of_machine();
                            machine = machineValue == "Y" ? "हाँ" : "नहीं";
                        }
                    }
                }

                spin_well_avbl.setSelection(wellAvblValue.contains("Y") ? 1 : 2);
                if(wellAvblValue != null && wellAvblValue.contains("Y")){
                    if (wellTypeValue != null){
                        int pondIndex = wellTypeValue.contains("C") ? 1 : 2;
                        //Toast.makeText(this, String.valueOf(pondIndex), Toast.LENGTH_SHORT).show();

                        spin_well_type.setSelection(pondIndex);
                        String P_Type=info.getPrivate_or_Public();
                        if(P_Type.equalsIgnoreCase("C")){
                           String _spin_dept_name = dataBaseHelper.getNameFor("DepartmentList", "DeptId", "DeptNameHn", wellDeptName);
                          //  spin_owner_dept.setSelection(Integer.parseInt(wellDeptName));
                            spin_owner_dept.setSelection(((ArrayAdapter<String>) spin_owner_dept.getAdapter()).getPosition(_spin_dept_name));
                            spin_flier.setSelection(flierValue.contains("Y") ? 1 : 2);
                            //spin_water_availibility.setSelection(waterAvblValue.contains("Y") ? 1 : 2);
                            spin_encroachment_status.setSelection(encrhmntStatusValue.contains("Y") ? 1 : 2);
                            if(encrhmntStatusValue.contains("Y")){
                                spin_encroachment_type.setSelection(encrhmntTypeValue.contains("K") ? 1 : 2);
                            }

                            spin_rqrmnt_renovation.setSelection(renovationValue.contains("Y") ? 1 : 2);
                            if((flierValue.contains("Y") || renovationValue.contains("Y")) && machineValue != null){
                                spin_rqrmnt_of_machine.setSelection(machineValue.contains("Y") ? 1 : 2);
                            }

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

            rajaswa_thana.setText(rajswaThanaNumber);
            block_name.setText(blockName);
            district_name.setText(distName);

            loadVillageData(panchayatCode);
            ll_well_abvl.setVisibility(View.GONE);
            wellAvblValue = "Y";
        }
    }

    public void Initialization()
    {

        ll_comercial_well = (LinearLayout) findViewById(R.id.ll_comercial_well);
        ll_encrh_type = (LinearLayout) findViewById(R.id.ll_encrh_type);
        ll_avbl_type = (LinearLayout) findViewById(R.id.ll_avbl_type);
        ll_well_owner_dept_other_name = (LinearLayout) findViewById(R.id.ll_well_owner_dept_other_name);
        ll_machine_rqmnt = (LinearLayout) findViewById(R.id.ll_machine_rqmnt);
        ll_well_abvl = (LinearLayout) findViewById(R.id.ll_well_abvl);

        rajaswa_thana = findViewById(R.id.rajaswa_thana);
        block_name = (TextView) findViewById(R.id.block_name);
        tv_well_avbl = (TextView) findViewById(R.id.tv_well_avbl);
        tv_well_type = (TextView) findViewById(R.id.tv_well_type);
        tv_flier = (TextView) findViewById(R.id.tv_flier);
        tv_village_name = (TextView) findViewById(R.id.tv_village_name);
        tv_owner_dept_name = (TextView) findViewById(R.id.tv_owner_dept_name);
        tv_encrh_status = (TextView) findViewById(R.id.tv_encrh_status);
        tv_encrh_type = (TextView) findViewById(R.id.tv_encrh_type);
        tv_renovation = (TextView) findViewById(R.id.tv_renovation);
        tv_machine = (TextView) findViewById(R.id.tv_machine);
        tv_gram = (TextView) findViewById(R.id.tv_gram);
        tv_functional = (TextView) findViewById(R.id.tv_functional);

        district_name = (TextView) findViewById(R.id.district_name);

        et_khaata = (EditText)findViewById(R.id.et_khaata);
        et_khesra = (EditText)findViewById(R.id.et_khesra);
        et_outer_diameter =(EditText)findViewById(R.id.et_outer_diameter);
        et_owner_name = (EditText)findViewById(R.id.et_owner_name);
        et_remark = (EditText)findViewById(R.id.et_remark);
        et_wellOwnerDeptOtherName = (EditText)findViewById(R.id.et_wellOwnerDeptOtherName);

        spin_panchayat = (Spinner) findViewById(R.id.spin_panchayat);
        spin_well_type = (Spinner)findViewById(R.id.spin_well_type);
        spin_well_avbl =(Spinner)findViewById(R.id.spin_well_avbl);
        spin_flier = (Spinner)findViewById(R.id.spin_flier);
        spin_gram = (Spinner)findViewById(R.id.spin_gram);

        spin_encroachment_status = (Spinner)findViewById(R.id.spin_encroachment_status);
        spin_encroachment_type = (Spinner)findViewById(R.id.spin_encroachment_type);
        spin_rqrmnt_renovation = (Spinner)findViewById(R.id.spin_rqrmnt_renovation);
        spin_rqrmnt_of_machine = (Spinner)findViewById(R.id.spin_rqrmnt_of_machine);
        spin_owner_dept = (Spinner)findViewById(R.id.spin_owner_dept);
        spin_functional = (Spinner)findViewById(R.id.spin_functional);

        btn_reg = (Button) findViewById(R.id.btn_reg);

        spin_panchayat.setOnItemSelectedListener(this);
        spin_well_type.setOnItemSelectedListener(this);
        spin_flier.setOnItemSelectedListener(this);
        //spin_water_availibility.setOnItemSelectedListener(this);
        spin_encroachment_status.setOnItemSelectedListener(this);
        spin_encroachment_type.setOnItemSelectedListener(this);
        spin_rqrmnt_renovation.setOnItemSelectedListener(this);
        spin_rqrmnt_of_machine.setOnItemSelectedListener(this);
        spin_owner_dept.setOnItemSelectedListener(this);
        spin_well_avbl.setOnItemSelectedListener(this);
        spin_gram.setOnItemSelectedListener(this);
        spin_functional.setOnItemSelectedListener(this);

    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if (wellAvblValue.equals("")) {
            tv_well_avbl.setError(getString(R.string.fieldRequired));
            focusView = tv_well_avbl;
            validate = false;
        }

        if (et_remark.getText().toString().equals("")) {
            et_remark.setError(getString(R.string.fieldRequired));
            focusView = et_remark;
            validate = false;
        }

        if(wellAvblValue.contains("Y")){


//            if (wellTypeValue.equals("")) {
//                tv_well_type.setError(getString(R.string.fieldRequired));
//                focusView = tv_well_type;
//                validate = false;
//            }

            if (wellTypeValue.equals("C")){

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

                if (et_outer_diameter.getText().toString().equals("")) {
                    et_outer_diameter.setError(getString(R.string.fieldRequired));
                    focusView = et_outer_diameter;
                    validate = false;
                }
                if (wellDeptName == null) {
                    tv_owner_dept_name.setError(getString(R.string.fieldRequired));
                    focusView = tv_owner_dept_name;
                    validate = false;
                }

//                if(wellDeptName != null && wellDeptName.equals("4")){
//                    if (et_wellOwnerDeptOtherName.getText().toString().equals("")) {
//                        et_wellOwnerDeptOtherName.setError(getString(R.string.fieldRequired));
//                        focusView = et_wellOwnerDeptOtherName;
//                        validate = false;
//                    }
//                }

                if (flierType == null) {
                    tv_flier.setError(getString(R.string.fieldRequired));
                    focusView = tv_flier;
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

                if (functionalStatus == null) {
                    tv_functional.setError(getString(R.string.fieldRequired));
                    focusView = tv_functional;
                    validate = false;
                }
//                if(flierValue.contains("Y") || renovationValue.contains("Y")){
//                    if (machine == null) {
//                        tv_machine.setError(getString(R.string.fieldRequired));
//                        focusView = tv_machine;
//                        validate = false;
//                    }
//                }
            }


        }


        if (!validate) focusView.requestFocus();

        return validate;
    }

    public void setValue(){

    }

    private void UpdateInspectionDetail(){
        long result = 0;

            String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
            String khataKhesraNo = et_khaata.getText().toString()+"/"+et_khesra.getText().toString();

            WellInspectionEntity wellInspectionEntity=new WellInspectionEntity();

            wellInspectionEntity.setInspectionID(inspectionID);
            wellInspectionEntity.setId(id);
            wellInspectionEntity.setDistID(distID);
            wellInspectionEntity.setDistName(distName);
            wellInspectionEntity.setBlockID(blockID);
            wellInspectionEntity.setBlockName(blockName);
            wellInspectionEntity.setRajswaThanaNumber(rajswaThanaNumber);
            wellInspectionEntity.setVillageID(villageID);
            wellInspectionEntity.setVillageName(villageName);

            wellInspectionEntity.setVerified_Date(currentDate);

            wellInspectionEntity.setPanchayat_Code(panchayatCode);
            wellInspectionEntity.setPanchayat_Name(panchayatName);
            wellInspectionEntity.setKhata_Khesra_No(khataKhesraNo);
            wellInspectionEntity.setPrivate_or_Public(wellTypeValue);
            wellInspectionEntity.setOutside_Diamter(et_outer_diameter.getText().toString());
            wellInspectionEntity.setWellOwnerOtherDeptName(et_wellOwnerDeptOtherName.getText().toString());
            wellInspectionEntity.setRequirement_Of_Flyer(flierValue);
            wellInspectionEntity.setWellAvblValue(wellAvblValue);
            wellInspectionEntity.setStatus_of_Encroachment(encrhmntStatusValue);
            wellInspectionEntity.setTypes_of_Encroachment(encrhmntTypeValue);
            wellInspectionEntity.setRequirement_of_Renovation(renovationValue);
            wellInspectionEntity.setName_of_undertaken(wellDeptName);
            wellInspectionEntity.setRemarks(et_remark.getText().toString());
            wellInspectionEntity.setIsUpdated("1");
            wellInspectionEntity.setRequirement_of_machine(machineValue);
            wellInspectionEntity.setStructureId(structureId);
            wellInspectionEntity.setFunctional(functionalStatus);

        result = new DataBaseHelper(this).UpdateWellInspectionDetail(wellInspectionEntity);

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

                Intent intent = new Intent(this, MultipulPhotoActivity2.class);
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


//    public void loadSpinnerPondLakeDept() {
//
//        deptList = dataBaseHelper.getExecDepartmentList();
//        deptNameArray = new ArrayList<String>();
//        deptNameArray.add("-चयन करे-");
//        int i = 0;
//        for (DepartmentEntity info : deptList) {
//            deptNameArray.add(info.getDeptNameHn());
//            if(info.getDeptId().equals(userInfo.getDeptId())){
//                i = deptList.indexOf(info);
//            }
//        }
//        deptAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, deptNameArray);
//        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spin_owner_dept.setAdapter(deptAdapter);
//
//
//        //if(userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
//
//            //spin_owner_dept.setSelection(i + 1);
//
//            spin_owner_dept.setSelection(i+1);
//            spin_owner_dept.setEnabled(false);
//
//            //spin_owner_dept.setEnabled(false);
//
//
//       // }
//    }

    public void loadSpinnerPondLakeDept()
    {

        deptList = dataBaseHelper.getExecDepartmentList();
        String[] typeNameArray = new String[deptList.size() + 1];
        typeNameArray[0] = "-चयन करे-";

        int i = 1;
        for (DepartmentEntity type : deptList)
        {
            typeNameArray[i] = type.getDeptNameHn();
            i++;
        }
        deptAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, typeNameArray);
        deptAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_owner_dept.setAdapter(deptAdapter);
        int setID=0;
        for ( i = 0; i < deptList.size(); i++)
        {
            //if (DivList.get(i).getDivId().equalsIgnoreCase(CommonPref.getUserDetails(NewEntryForm_Activity.this).get_DivisionID())) {
            //if (DistrictList.get(i).get_DistCode().equalsIgnoreCase("213"))
            // if (distList.get(i).get_DistCode().trim().equals(CommonPref.getUserDetails(HomeScreen_Activity.this).getDistrictCode().trim()))
            if (deptList.get(i).getDeptId().trim().equals(userInfo.getDeptId()))
            {
                setID = i+1;
            }
            if(setID!=0)
            {
                spin_owner_dept.setSelection(setID);
                spin_owner_dept.setEnabled(false);
            }
        }

    }
 



    public void loadVillageData(String pan_Code) {
        villageList = dataBaseHelper.getVillageList(pan_Code);
        if(villageList.size()>0){
            setVillageData();
        }else{

            if(Utiilties.isOnline(this)){
                new SyncVillageData().execute();
            }else{
                Utiilties.showInternentAlert(this);
            }
        }

    }

    public void setVillageData(){
        villageNameArray = new ArrayList<>();
        villageNameArray.add("-गाँव का चयन करे-");
        if (villageList.size() > 0){
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

        spin_flier.setAdapter(spinnerAdapter);
        flierArray = sList;
        spin_well_avbl.setAdapter(spinnerAdapter);
        wellAvblArray = sList;
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_gram:
                if (position > 0) {
                    VillageListEntity village = villageList.get(position - 1);
                    villageID = village.getVillCode();
                    villageName = village.getVillName();
                }
                break;
            case R.id.spin_owner_dept:
                int pos = position;
                if (pos > 0) {
                    tv_owner_dept_name.setError(null);
                    wellDeptName = String.valueOf(deptList.get(pos - 1).getDeptId());
//                    if (pos == 4){
//                        ll_well_owner_dept_other_name.setVisibility(View.VISIBLE);
//                    }else{
//                        ll_well_owner_dept_other_name.setVisibility(View.GONE);
//                    }
                }
                break;

            case R.id.spin_well_type:
                if (position > 0) {
                    tv_well_type.setError(null);
                    wellType=wellTypeOption[position].toString();

                    if(wellType.equals("सार्वजनिक")){
                        wellTypeValue="C";
                        ll_comercial_well.setVisibility(View.VISIBLE);
                    }
                    else if(wellType.equals("निजी")){
                        wellTypeValue="P";
                        ll_comercial_well.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.spin_well_avbl:
                if (position > 0) {
                    tv_well_avbl.setError(null);
                    wellAvblType=wellAvblArray.get(position).toString();

                    if(wellAvblType.equals("हाँ")){
                        wellAvblValue="Y";
                        ll_avbl_type.setVisibility(View.VISIBLE);
                    }
                    else if(wellAvblType.equals("नहीं")){
                        wellAvblValue="N";
                        ll_avbl_type.setVisibility(View.GONE);
                    }
                }
                break;
            case R.id.spin_flier:
                if (position > 0) {
                    tv_flier.setError(null);
                    flierType=flierArray.get(position).toString();

                    if(flierType.equals("हाँ")){
                        flierValue="Y";
                    }
                    else if(flierType.equals("नहीं")){
                        flierValue="N";
                    }

                    if(flierValue.contains("Y") || renovationValue.contains("Y")){
                        ll_machine_rqmnt.setVisibility(View.VISIBLE);
                    }else{
                        ll_machine_rqmnt.setVisibility(View.GONE);
                    }
                }
                break;

            case R.id.spin_encroachment_status:
                if (position > 0) {
                    tv_encrh_status.setError(null);
                    encrhmntStatus=statusOfEncroachmentArray.get(position).toString();

                    if(encrhmntStatus.equals("हाँ")){
                        encrhmntStatusValue="Y";
                        ll_encrh_type.setVisibility(View.VISIBLE);
                        encrhmntTypeValue = "";
                    }
                    else if(encrhmntStatus.equals("नहीं")){
                        encrhmntStatusValue="N";
                        ll_encrh_type.setVisibility(View.GONE);
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
                    }
                    else if(renovation.equals("नहीं")){
                        renovationValue="N";
                    }

                    if(flierValue.contains("Y") || renovationValue.contains("Y")){
                        ll_machine_rqmnt.setVisibility(View.VISIBLE);
                    }else{
                        ll_machine_rqmnt.setVisibility(View.GONE);
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


    private class SyncVillageData extends AsyncTask<String, Void, ArrayList<VillageListEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(WellInspectionActivity.this);

        @Override
        protected void onPreExecute() {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("ग्राम लोड हो रहा है...");
            dialog.show();
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
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
            long i= helper.setVillageDataToLocal(result);

            villageList = dataBaseHelper.getVillageList(panchayatCode);
            if(villageList.size()>0){
                setVillageData();
            }else{
                villageList = filterVillagePanchayatWise(result);
                setVillageData();
            }


            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Village Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update village",Toast.LENGTH_SHORT).show();

            }


        }
    }

    public ArrayList<VillageListEntity> filterVillagePanchayatWise(ArrayList<VillageListEntity> list){
        ArrayList<VillageListEntity> villages = new ArrayList<>();

        for(VillageListEntity info: list){
            if(info.getPanchayatCode().equals(panchayatCode)){
                villages.add(info);
            }
        }

        return villages;
    }
}
