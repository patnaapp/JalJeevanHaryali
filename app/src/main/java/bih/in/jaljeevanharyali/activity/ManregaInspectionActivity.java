package bih.in.jaljeevanharyali.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.TreesDetail;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class ManregaInspectionActivity extends Activity {

    DataBaseHelper dataBaseHelper;
    TextView tv_district,tv_block,tv_panchayat,tv_village,tv_kriwayan_vibhag,tv_abya_name,tv_sanrachna_type,tv_sanrachna_measurement,tv_estimated_amount,tv_anumodan_tithi,tv_prashasnik_date,tv_yojna_code,tv_title_phase1,tv_title_phase2,tv_title_phase3,tv_scheme_type;
    EditText et_remark_phase1,et_remark_phase2,et_remark_phase3;
    //EditText et_khaata,et_khesra,et_rakba,et_owner_name,et_mobile,et_consumer_no,et_consumer_bill,et_total_tree,et_live_tree;
    LinearLayout ll_required_field,ll_tree_type;
    Button btn_phase1,btn_phase2,btn_phase3;
    LinearLayout ll_phase1,ll_phase2,ll_phase3,ll_entry_phase1,ll_entry_phase2,ll_entry_phase3;
    //LinearLayout ll_electricity,ll_land_detail,ll_tree_count;
    ImageView img_phase1,img_phase2,img_phase3;
    RelativeLayout rl_phase1,rl_phase2,rl_phase3;

    TextView tv_tree_type;
    Spinner spin_tree_type;

    int id;
    private String s1_data = "";
    ManregaSchemeDetail info;

    ArrayList<TreesDetail> treeList = new ArrayList<TreesDetail>();
    ArrayList<String> treeNameArray;
    ArrayAdapter<String>treeAdapter;

    String treeTypeName="", treeTypeId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manrega_inspection);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Initialization();

        extractDataFromIntent();

        btn_phase1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    UpdateManregaDetail(1);
                }else{
                    Toast.makeText(ManregaInspectionActivity.this, "Please filled all required field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_phase2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    UpdateManregaDetail(2);
                }else{
                    Toast.makeText(ManregaInspectionActivity.this, "Please filled all required field", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn_phase3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    UpdateManregaDetail(3);
                }else{
                    Toast.makeText(ManregaInspectionActivity.this, "Please filled all required field", Toast.LENGTH_SHORT).show();
                }
            }
        });

//        spin_tree_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                if(position > 0){
//                    TreesDetail info = treeList.get(position - 1);
//                    treeTypeId = info.getTreeId();
//                    treeTypeName = info.getTreeHnd();
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
    }

    private void Initialization() {
        tv_district =  findViewById(R.id.tv_district);
        tv_block =  findViewById(R.id.tv_block);
        tv_panchayat =  findViewById(R.id.tv_panchayat);
        tv_village =  findViewById(R.id.tv_village);
        tv_kriwayan_vibhag =  findViewById(R.id.tv_kriwayan_vibhag);
        tv_abya_name =  findViewById(R.id.tv_abya_name);
        tv_sanrachna_type =  findViewById(R.id.tv_sanrachna_type);
        tv_sanrachna_measurement =  findViewById(R.id.tv_sanrachna_measurement);
        tv_estimated_amount =  findViewById(R.id.tv_estimated_amount);
        tv_anumodan_tithi =  findViewById(R.id.tv_anumodan_tithi);
        tv_prashasnik_date =  findViewById(R.id.tv_prashasnik_date);
        tv_yojna_code =  findViewById(R.id.tv_yojna_code);
        tv_title_phase1 =  findViewById(R.id.tv_title_phase1);
        tv_title_phase2 =  findViewById(R.id.tv_title_phase2);
        tv_title_phase3 =  findViewById(R.id.tv_title_phase3);
        tv_scheme_type =  findViewById(R.id.tv_scheme_type);

        tv_tree_type =  findViewById(R.id.tv_tree_type);

        ll_phase1 = findViewById(R.id.ll_phase1);
        ll_phase2 = findViewById(R.id.ll_phase2);
        ll_phase3 = findViewById(R.id.ll_phase3);
        ll_entry_phase1 = findViewById(R.id.ll_entry_phase1);
        ll_entry_phase2 = findViewById(R.id.ll_entry_phase2);
        ll_entry_phase3 = findViewById(R.id.ll_entry_phase3);

        ll_tree_type = findViewById(R.id.ll_tree_type);
//        ll_electricity = findViewById(R.id.ll_electricity);
//        ll_land_detail = findViewById(R.id.ll_land_detail);

        spin_tree_type = (Spinner) findViewById(R.id.spin_tree_type);

        ll_required_field = findViewById(R.id.ll_required_field);
        //ll_tree_count = findViewById(R.id.ll_tree_count);

        rl_phase1 = findViewById(R.id.rl_phase1);
        rl_phase2 = findViewById(R.id.rl_phase2);
        rl_phase3 = findViewById(R.id.rl_phase3);

        et_remark_phase1 = findViewById(R.id.et_remark_phase1);
        et_remark_phase2 = findViewById(R.id.et_remark_phase2);
        et_remark_phase3 =findViewById(R.id.et_remark_phase3);

//        et_khaata =findViewById(R.id.et_khaata);
//        et_khesra =findViewById(R.id.et_khesra);
//        et_rakba =findViewById(R.id.et_rakba);
//        et_owner_name =findViewById(R.id.et_owner_name);
//        et_mobile =findViewById(R.id.et_mobile);
//        et_consumer_no =findViewById(R.id.et_consumer_no);
//        et_consumer_bill =findViewById(R.id.et_consumer_bill);
//        et_total_tree =findViewById(R.id.et_total_tree);
//        et_live_tree =findViewById(R.id.et_live_tree);

        img_phase1 = findViewById(R.id.img_phase1);
        img_phase2 =findViewById(R.id.img_phase2);
        img_phase3 =findViewById(R.id.img_phase3);

        btn_phase1 = findViewById(R.id.btn_phase1);
        btn_phase2 = findViewById(R.id.btn_phase2);
        btn_phase3 = findViewById(R.id.btn_phase3);

    }

    public void extractDataFromIntent(){
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 0);
        Log.e("ManregId", ""+id);

        info = dataBaseHelper.getManregaDetail(String.valueOf(id));

        setData(info);
    }

    public void setData(ManregaSchemeDetail info){

        tv_district.setText(info.getDistrict_Name());
        tv_block.setText(info.getBlock_Name());
        tv_panchayat.setText(info.getPanchayatName());
        tv_village.setText(info.getWard_Name());

        tv_kriwayan_vibhag.setText(info.getExectDeptName());
        tv_abya_name.setText(info.getSubDivName());
        tv_sanrachna_type.setText(info.getTypes_OfSarchnaName());
        tv_sanrachna_measurement.setText(info.getMeasurement_Of_Structure());
        tv_estimated_amount.setText(info.getEstimated_Amount());
        tv_anumodan_tithi.setText(info.getApproval_Date());
        tv_prashasnik_date.setText(info.getAdministrative_Approval_Date());
        tv_yojna_code.setText(info.getScheme_Code());

        String yojnaType = "NA";
        if(info.getYojnaType() != null){
            yojnaType = info.getYojnaType().equals("U") ? "उद्घाटन" : "शिलान्यास";
        }
        tv_scheme_type.setText(yojnaType);

        //setLandDetailView();

        if(info.getIsUpdated() != null && info.getIsUpdated().equals("1")){
            //et_live_tree.setText("");
//            if(info.getYojnaType() != null && info.getYojnaType().equals("U")){
//                ll_phase1.setVisibility(View.GONE);
//                ll_phase2.setVisibility(View.GONE);
//                ll_phase3.setVisibility(View.VISIBLE);
//                ll_entry_phase3.setVisibility(View.VISIBLE);
//                img_phase3.setVisibility(View.GONE);
//                tv_title_phase3.setText("Stage 3 (कार्य के उद्घाटन के दौरान)");
//                et_remark_phase3.setText(info.getIsPhase3InspRemarks());
//            }else{
                if(info.getIsPhase3Inspected() != null && info.getIsPhase3Inspected().equals("Y")){
                    ll_phase3.setVisibility(View.VISIBLE);
                    ll_entry_phase3.setVisibility(View.VISIBLE);
                    img_phase3.setVisibility(View.GONE);

                    rl_phase3.setBackgroundColor(getResources().getColor(R.color.color_orange));
                    et_remark_phase3.setText(info.getIsPhase3InspRemarks());

                    rl_phase1.setBackgroundColor(getResources().getColor(R.color.color_green));
                    rl_phase2.setBackgroundColor(getResources().getColor(R.color.color_green));

                    img_phase1.setVisibility(View.VISIBLE);
                    img_phase2.setVisibility(View.VISIBLE);

                    ll_phase1.setVisibility(View.VISIBLE);
                    ll_phase2.setVisibility(View.VISIBLE);
                    ll_entry_phase1.setVisibility(View.GONE);
                    ll_entry_phase2.setVisibility(View.GONE);
                }else{
                    ll_phase3.setVisibility(View.GONE);
                    if(info.getIsPhase2Inspected() != null && info.getIsPhase2Inspected().equals("Y")){
                        ll_phase2.setVisibility(View.VISIBLE);
                        ll_entry_phase2.setVisibility(View.VISIBLE);
                        img_phase2.setVisibility(View.GONE);

                        rl_phase2.setBackgroundColor(getResources().getColor(R.color.color_orange));
                        et_remark_phase2.setText(info.getIsPhase2InspRemarks());

                        rl_phase1.setBackgroundColor(getResources().getColor(R.color.color_green));
                        img_phase1.setVisibility(View.VISIBLE);
                        ll_phase1.setVisibility(View.VISIBLE);
                        ll_entry_phase1.setVisibility(View.GONE);

                    }else{
                        ll_phase2.setVisibility(View.GONE);
                        ll_phase3.setVisibility(View.GONE);

                        rl_phase1.setBackgroundColor(getResources().getColor(R.color.color_orange));
                        et_remark_phase1.setText(info.getIsPhase1InspRemarks());
                        img_phase1.setVisibility(View.GONE);
                    }
                }
           // }
        }else{

//            if(info.getYojnaType() != null && info.getYojnaType().equals("U")){
//                ll_phase1.setVisibility(View.GONE);
//                ll_phase2.setVisibility(View.GONE);
//                ll_phase3.setVisibility(View.VISIBLE);
//                ll_entry_phase3.setVisibility(View.VISIBLE);
//                img_phase3.setVisibility(View.GONE);
//                tv_title_phase3.setText("Stage 3 (कार्य के उद्घाटन के दौरान)");
//            }else{
                if(info.getIsPhase1Inspected() != null && info.getIsPhase1Inspected().equals("Y")){
                    ll_phase1.setVisibility(View.VISIBLE);
                    ll_entry_phase1.setVisibility(View.GONE);
                    rl_phase1.setBackgroundColor(getResources().getColor(R.color.color_green));
                    img_phase1.setVisibility(View.VISIBLE);

                    if(info.getIsPhase2Inspected() != null && info.getIsPhase2Inspected().equals("Y")){
                        ll_phase2.setVisibility(View.VISIBLE);
                        ll_entry_phase2.setVisibility(View.GONE);
                        rl_phase2.setBackgroundColor(getResources().getColor(R.color.color_green));
                        img_phase2.setVisibility(View.VISIBLE);

                        if(info.getIsPhase3Inspected() != null && info.getIsPhase3Inspected().equals("Y")){
                            ll_phase3.setVisibility(View.VISIBLE);
                            ll_entry_phase3.setVisibility(View.GONE);
                            rl_phase3.setBackgroundColor(getResources().getColor(R.color.color_green));
                            img_phase3.setVisibility(View.VISIBLE);
                        }else{
                            ll_phase3.setVisibility(View.VISIBLE);
                            ll_entry_phase3.setVisibility(View.VISIBLE);
                            img_phase3.setVisibility(View.GONE);

//                            long daycount = Utiilties.getDateDifferenceFromCurrentDate(info.getIsPhase2InspDate());
//                            Log.e("DayCount", String.valueOf(daycount));
//                            if(daycount > 1){
//                                btn_phase3.setEnabled(true);
//                                btn_phase3.setBackground(getResources().getDrawable(R.drawable.greenbuttonshape));
//                            }else {
//                                btn_phase3.setEnabled(false);
//                                btn_phase3.setBackground(getResources().getDrawable(R.drawable.greybuttonshape));
//                            }
                        }
                    }else{
                        ll_phase2.setVisibility(View.VISIBLE);
                        ll_entry_phase2.setVisibility(View.VISIBLE);
                        img_phase2.setVisibility(View.GONE);
                        ll_phase3.setVisibility(View.GONE);

//                        long daycount = Utiilties.getDateDifferenceFromCurrentDate(info.getIsPhase1InspDate());
//                        Log.e("DayCount", String.valueOf(daycount));
//                        if(daycount > 1){
//                            btn_phase2.setEnabled(true);
//                            btn_phase2.setBackground(getResources().getDrawable(R.drawable.greenbuttonshape));
//                        }else {
//                            btn_phase2.setEnabled(false);
//                            btn_phase2.setBackground(getResources().getDrawable(R.drawable.greybuttonshape));
//                        }
                    }
                }else{
                    ll_phase1.setVisibility(View.VISIBLE);
                    ll_entry_phase1.setVisibility(View.VISIBLE);
                    img_phase1.setVisibility(View.GONE);
                    ll_phase2.setVisibility(View.GONE);
                    ll_phase3.setVisibility(View.GONE);
                }
          //  }
        }
    }

    private void setLandDetailView(){

//        if(info.getAwayabId().equals("5")){
//            ll_tree_count.setVisibility(View.VISIBLE);
//        }else{
//            ll_tree_count.setVisibility(View.GONE);
//        }

        if(info.getIsLandEnterDt() != null && info.getIsLandEnterDt().equals("Y")){
            //ll_required_field.setVisibility(View.GONE);

        }else{
            //ll_required_field.setVisibility(View.VISIBLE);

//            if(info.getAwayabId().equals("8") || info.getAwayabId().equals("5")){
//                ll_land_detail.setVisibility(View.VISIBLE);
//                ll_electricity.setVisibility(View.GONE);
//
//                if(info.getAwayabId().equals("5")){
//                    ll_tree_type.setVisibility(View.VISIBLE);
//                    loadTreeTypeSpinner();
//                }else{
//                    ll_tree_type.setVisibility(View.GONE);
//                }
//            }
//            else if (info.getAwayabId().equals("9")){
//                ll_land_detail.setVisibility(View.GONE);
//                ll_electricity.setVisibility(View.VISIBLE);
//            }else{
//                ll_required_field.setVisibility(View.GONE);
//            }

            if(info.getIsUpdated() != null && info.getIsUpdated().equals("1")){

//                if(info.getKhaata_Kheshara_Number() != null && info.getKhaata_Kheshara_Number().contains("/")){
//                    String[] kkArray = info.getKhaata_Kheshara_Number().split("/");
//                    if(kkArray.length > 0){ 
//                        String khata = kkArray[0];
//                        if(khata != null && !khata.contains("NA") && !khata.toLowerCase().contains("anytype")){
//                            et_khaata.setText(khata);
//                        }
//                    }
//
//                    if(kkArray.length > 1){
//                        String khesraNo = kkArray[1];
//                        if(khesraNo != null && !khesraNo.contains("NA") && !khesraNo.toLowerCase().contains("anytype")){
//
//                            et_khesra.setText(khesraNo);
//                        }
//                    }
//                }
//
//                et_rakba.setText(info.getRakba());
//                et_owner_name.setText(info.getLandOwnerName());
//                et_mobile.setText(info.getLandOwnerMob());
//                et_total_tree.setText(info.getTotalTree());
//                et_live_tree.setText(info.getTreeTypeName());

//                if(info.getAwayabId().equals("5") && (info.getTreeTypeName() != null && !info.getTreeTypeName().equals(""))){
//                    spin_tree_type.setSelection(treeNameArray.indexOf(info.getTreeTypeName()));
//                }

//                et_consumer_bill.setText(info.getConsumrBill());
//                et_consumer_no.setText(info.getConsumerNo());
            }
        }

    }

    public void loadTreeTypeSpinner() {
        treeList = dataBaseHelper.getTreelist();
        treeNameArray = new ArrayList<String>();
        treeNameArray.add("-चयन करे-");
        int i = 0, setId= 0;
        for (TreesDetail info : treeList) {
            treeNameArray.add(info.getTreeHnd());
        }
        treeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, treeNameArray);
        treeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_tree_type.setAdapter(treeAdapter);
    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;


//        if(!info.getIsUpdated().equals("1")){
//            if(info.getIsPhase1Inspected() == null || info.getIsPhase1Inspected().equals("NA")){
//                if (et_remark_phase1.getText().toString().equals("")) {
//                    et_remark_phase1.setError(getString(R.string.fieldRequired));
//                    focusView = et_remark_phase1;
//                    validate = false;
//                }
//            }
//
//            if(info.getIsPhase1Inspected().equals("Y") && (info.getIsPhase2Inspected() == null || info.getIsPhase2Inspected().equals("NA"))){
//                if (et_remark_phase2.getText().toString().equals("")) {
//                    et_remark_phase2.setError(getString(R.string.fieldRequired));
//                    focusView = et_remark_phase2;
//                    validate = false;
//                }
//            }
//
//            if(info.getIsPhase2Inspected().equals("Y") && (info.getIsPhase3Inspected() == null || info.getIsPhase3Inspected().equals("NA"))){
//                if (et_remark_phase3.getText().toString().equals("")) {
//                    et_remark_phase3.setError(getString(R.string.fieldRequired));
//                    focusView = et_remark_phase3;
//                    validate = false;
//                }
//            }
//        }

//        if(info.getAwayabId().equals("5")){
//            if (et_live_tree.getText().toString().equals("")) {
//                et_live_tree.setError(getString(R.string.fieldRequired));
//                focusView = et_live_tree;
//                validate = false;
//            }
//        }

//        if(!info.getIsLandEnterDt().equals("Y")){
//            if (info.getAwayabId().equals("8") || info.getAwayabId().equals("5")){
//
////                if(info.getAwayabId().equals("5")){
////                    if (treeTypeId.equals("")) {
////                        tv_tree_type.setError(getString(R.string.fieldRequired));
////                        focusView = tv_tree_type;
////                        validate = false;
////                    }
////                }
//
//                if (et_khaata.getText().toString().equals("")) {
//                    et_khaata.setError(getString(R.string.fieldRequired));
//                    focusView = et_khaata;
//                    validate = false;
//                }
//
//                if (et_khesra.getText().toString().equals("")) {
//                    et_khesra.setError(getString(R.string.fieldRequired));
//                    focusView = et_khesra;
//                    validate = false;
//                }
//
//                if (et_rakba.getText().toString().equals("")) {
//                    et_rakba.setError(getString(R.string.fieldRequired));
//                    focusView = et_rakba;
//                    validate = false;
//                }
//
//                if (et_total_tree.getText().toString().equals("")) {
//                    et_total_tree.setError(getString(R.string.fieldRequired));
//                    focusView = et_total_tree;
//                    validate = false;
//                }
//
//                if (et_owner_name.getText().toString().equals("")) {
//                    et_owner_name.setError(getString(R.string.fieldRequired));
//                    focusView = et_owner_name;
//                    validate = false;
//                }
//
//                if (et_mobile.getText().toString().equals("")) {
//                    et_mobile.setError(getString(R.string.fieldRequired));
//                    focusView = et_mobile;
//                    validate = false;
//                }else if (et_mobile.getText().toString().length() < 10) {
//                    et_mobile.setError("अमान्य मोबाइल नंबर");
//                    focusView = et_mobile;
//                    validate = false;
//                }
//            }
//        }
//
//        if(!info.getIsLandEnterDt().equals("Y") && info.getAwayabId().equals("9")){
//
//            if (et_consumer_no.getText().toString().equals("")) {
//                et_consumer_no.setError(getString(R.string.fieldRequired));
//                focusView = et_consumer_no;
//                validate = false;
//            }
//
//            if (et_consumer_bill.getText().toString().equals("")) {
//                et_consumer_bill.setError(getString(R.string.fieldRequired));
//                focusView = et_consumer_bill;
//                validate = false;
//            }
//        }
 
        if (!validate) focusView.requestFocus();

        return validate;
    }

    private void UpdateManregaDetail(int type){
        if(info.getIsUpdated().equals("0") && (info.getAwayabId().equals("8") || info.getAwayabId().equals("5")) && (info.getIsLandEnterDt() == null || !info.getIsLandEnterDt().equals("Y"))){
            moveToLocation(type);
        }else{
            moveToCamera(type);
        }
    }

    public void moveToCamera(int type){
        Intent intent = new Intent(this, ManregaPhotoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String remark = "";
        switch (type){
            case 1:
                remark = et_remark_phase1.getText().toString();
                break;
            case 2:
                remark = et_remark_phase2.getText().toString();
                break;
            case 3:
                remark = et_remark_phase3.getText().toString();
                break;
        }
        Log.d("Manrega",""+id);
        intent.putExtra("KEY_PID", id);
        intent.putExtra("PhaseType", type);
        intent.putExtra("pupose", "new");
        intent.putExtra("isOpen", s1_data);
        intent.putExtra("remark", remark);
//        intent.putExtra("consumerNo", et_consumer_no.getText().toString().trim());
//        intent.putExtra("consumerBill", et_consumer_bill.getText().toString().trim());
        startActivity(intent);
        finish();
    }

    public void moveToLocation(int type){
        Intent intent = new Intent(this, CAptureFieldLocationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        String remark = "";
        switch (type){
            case 1:
                remark = et_remark_phase1.getText().toString();
                break;
            case 2:
                remark = et_remark_phase2.getText().toString();
                break;
            case 3:
                remark = et_remark_phase3.getText().toString();
                break;
        }
        Log.d("Manrega",""+id);
//        String khataKhesra = et_khaata.getText().toString().trim()+"/"+et_khesra.getText().toString().trim();
//        intent.putExtra("khataKhesra", khataKhesra);
//        intent.putExtra("rakba", et_rakba.getText().toString().trim());
//        intent.putExtra("name", et_owner_name.getText().toString().trim());
//        intent.putExtra("mobile", et_mobile.getText().toString().trim());
//        intent.putExtra("totalTree", et_total_tree.getText().toString().trim());
//        //intent.putExtra("liveTree", et_live_tree.getText().toString().trim());
        intent.putExtra("awyabId", info.getAwayabId());
//        intent.putExtra("treeId", treeTypeId);
//        intent.putExtra("treeName", et_live_tree.getText().toString().trim());
        intent.putExtra("KEY_PID", id);
        intent.putExtra("PhaseType", type);
        intent.putExtra("pupose", "new");
        intent.putExtra("isOpen", s1_data);
        intent.putExtra("remark", remark);
        intent.putExtra("inspType", "manrega");
        startActivity(intent);
        finish();
    }
}
