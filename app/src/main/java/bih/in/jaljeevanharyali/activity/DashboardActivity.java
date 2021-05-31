package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.nursery.NurseryReportActivity;
import bih.in.jaljeevanharyali.activity.nursery.NursuryListActivity;
import bih.in.jaljeevanharyali.activity.nursery.NursuryListEditActivity;
import bih.in.jaljeevanharyali.activity.remarkUpdate.RemarkUpdateEditActivity;
import bih.in.jaljeevanharyali.activity.remarkUpdate.RemarkUpdateListActivity;
import bih.in.jaljeevanharyali.adapter.OtherDeptInsptEntity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.DepartmentEntity;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaDataEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaTypeEntity;
import bih.in.jaljeevanharyali.entity.StructureRemarkEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

import static android.view.View.GONE;

public class DashboardActivity extends Activity implements View.OnClickListener {

    LinearLayout pondLayout, wellLayout,linearlayout_eidt_pond,linearlayout_edit_well,ll_upload_pond_data,ll_uplaod_well_data;
    LinearLayout ll_manrega_add,ll_manrega_edit,ll_manrega_upload,ll_remark_add,ll_remark_edit,ll_remark_upload;
    //LinearLayout ll_prs,ll_mwrd_dst;
    LinearLayout ll_rdd,ll_Plantatin_NewEntry,ll_building_entry,ll_Plantatin_Edit,ll_building_edit,ll_nursery_upload,ll_building_upload;
    LinearLayout ll_building_card,ll_nursery_card,ll_scheme;

    //Menu
    LinearLayout ll_pond_menu,ll_well_menu,ll_manreag_menu,ll_other_menu,ll_sanrachna_menu,ll_building_menu,ll_plantation_menu,ll_remark_menu;
    LinearLayout ll_pond_click,ll_well_click,ll_manrega_click,ll_other_click,ll_sanrachna_click,ll_building_click,ll_plantation_click,ll_remark_click;
    ImageView iv_pond_drpdwn,iv_well_drpdwn,iv_manrega_drpdwn,iv_other_drpdwn,iv_sanrachna_drpdwn,iv_building_drpdwn,iv_plantation_drpdwn,iv_remark_drpdwn;

    RelativeLayout rl_sync_data;

    TextView tv_pond_count_mwr,tv_nursury_count,tv_appver,pending_remark;
    TextView tv_userName,pending_sudhar_pond,pending_sudhar_well,pending_upload_well,pending_upload_pond,tv_district,tv_block,pending_manrega,pending_upload_manrega,pending_other,pending_upload_other,pending_sanrachna,tv_sync_title;
    TextView tv_building_count;
    Button btn_logout;

    MaterialCardView mcv_structure,mcv_well,mcv_scheme,mcv_remark_update;

    DataBaseHelper dataBaseHelper;
    Boolean isShowPond = false, isShowWell = false,isShowManrega = false, isShowOther = false, isShowRemark = false, isShowBuilding = false, isShowNursury = false;

    UserDetails userInfo;

    ArrayList<DepartmentEntity> deptList = new ArrayList<DepartmentEntity>();
    DepartmentEntity deptInfo;

    ArrayList<String> syncDataList = new ArrayList<>();
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        dialog = new ProgressDialog(this);

        pondLayout =  findViewById(R.id.Pond_List_Layout);
        wellLayout =  findViewById(R.id.WellList_Layout);
        linearlayout_eidt_pond =  findViewById(R.id.linearlayout_eidt_pond);
        linearlayout_edit_well =  findViewById(R.id.linearlayout_edit_well);
        ll_upload_pond_data =  findViewById(R.id.ll_upload_pond_data);
        ll_uplaod_well_data =  findViewById(R.id.ll_uplaod_well_data);
        //ll_prs =  findViewById(R.id.ll_prs);
        ll_rdd =  findViewById(R.id.ll_rdd);
        ll_building_card =  findViewById(R.id.ll_building_card);
        ll_nursery_card =  findViewById(R.id.ll_nursery_card);
        ll_scheme =  findViewById(R.id.ll_scheme);

        ll_manrega_add =  findViewById(R.id.ll_manrega_add);
        ll_manrega_edit =  findViewById(R.id.ll_manrega_edit);
        ll_manrega_upload =  findViewById(R.id.ll_manrega_upload);

        ll_remark_add =  findViewById(R.id.ll_remark_add);
        ll_remark_edit =  findViewById(R.id.ll_remark_edit);
        ll_remark_upload =  findViewById(R.id.ll_remark_upload);
//
//        ll_sanrachna_add =  findViewById(R.id.ll_sanrachna_add);
//        ll_sanrachna_edit =  findViewById(R.id.ll_sanrachna_edit);

        rl_sync_data =  findViewById(R.id.rl_sync_data);

        //Menu
        ll_pond_menu =  findViewById(R.id.ll_pond_menu);
        ll_well_menu =  findViewById(R.id.ll_well_menu);
        ll_manreag_menu =  findViewById(R.id.ll_manreag_menu);
        ll_remark_menu =  findViewById(R.id.ll_remark_menu);
//        ll_sanrachna_menu =  findViewById(R.id.ll_sanrachna_menu);
        ll_building_menu =  findViewById(R.id.ll_building_menu);
        ll_plantation_menu = findViewById(R.id.ll_plantation_menu);

        ll_pond_click =  findViewById(R.id.ll_pond_click);
        ll_well_click =  findViewById(R.id.ll_well_click);
        ll_manrega_click =  findViewById(R.id.ll_manrega_click);
        ll_remark_click =  findViewById(R.id.ll_remark_click);
//        ll_sanrachna_click =  findViewById(R.id.ll_sanrachna_click);
        ll_plantation_click =  findViewById(R.id.ll_plantation_click);
        ll_building_click =  findViewById(R.id.ll_building_click);

        ll_Plantatin_NewEntry =  findViewById(R.id.ll_Plantatin_NewEntry);
        ll_nursery_upload =  findViewById(R.id.ll_nursery_upload);
        ll_building_upload =  findViewById(R.id.ll_building_upload);
        ll_building_entry =  findViewById(R.id.ll_building_entry);

        ll_Plantatin_Edit =  findViewById(R.id.ll_Plantatin_Edit);
        ll_building_edit =  findViewById(R.id.ll_building_edit);

        iv_pond_drpdwn =  findViewById(R.id.iv_pond_drpdwn);
        iv_well_drpdwn =  findViewById(R.id.iv_well_drpdwn);
        iv_manrega_drpdwn =  findViewById(R.id.iv_manrega_drpdwn);
        iv_remark_drpdwn =  findViewById(R.id.iv_remark_drpdwn);
//        iv_sanrachna_drpdwn =  findViewById(R.id.iv_sanrachna_drpdwn);
        iv_building_drpdwn =  findViewById(R.id.iv_building_drpdwn);
        iv_plantation_drpdwn =  findViewById(R.id.iv_plantation_drpdwn);

        btn_logout =  findViewById(R.id.btn_logout);

        tv_userName =  findViewById(R.id.tv_userName);
        tv_appver =  findViewById(R.id.tv_appver);
        pending_sudhar_pond =  findViewById(R.id.pending_sudhar_pond);
        pending_sudhar_well =  findViewById(R.id.pending_sudhar_well);
        pending_upload_well =  findViewById(R.id.pending_upload_well);
        pending_upload_pond =  findViewById(R.id.pending_upload_pond);
        tv_sync_title =  findViewById(R.id.tv_sync_title);

        pending_manrega =  findViewById(R.id.pending_manrega);
        pending_upload_manrega =  findViewById(R.id.pending_upload_manrega);

        pending_remark =  findViewById(R.id.pending_remark);
//        pending_upload_other =  findViewById(R.id.pending_upload_other);
//        pending_sanrachna =  findViewById(R.id.pending_sanrachna);

        tv_district =  findViewById(R.id.tv_district);
        tv_block =  findViewById(R.id.tv_block);
        tv_nursury_count =  findViewById(R.id.tv_nursury_count);
        tv_building_count =  findViewById(R.id.tv_building_count);

        mcv_structure = findViewById(R.id.mcv_structure);
        mcv_well = findViewById(R.id.mcv_well);
        mcv_scheme = findViewById(R.id.mcv_scheme);
        mcv_remark_update = findViewById(R.id.mcv_remark_update);

        pondLayout.setOnClickListener(this);
        wellLayout.setOnClickListener(this);
        linearlayout_eidt_pond.setOnClickListener(this);
        linearlayout_edit_well.setOnClickListener(this);
        ll_upload_pond_data.setOnClickListener(this);
        ll_uplaod_well_data.setOnClickListener(this);

        rl_sync_data.setOnClickListener(this);

        ll_manrega_add.setOnClickListener(this);
        ll_manrega_edit.setOnClickListener(this);
        ll_manrega_upload.setOnClickListener(this);

        ll_remark_add.setOnClickListener(this);
        ll_remark_edit.setOnClickListener(this);
        ll_remark_upload.setOnClickListener(this);
//
//        ll_sanrachna_add.setOnClickListener(this);
//        ll_sanrachna_edit.setOnClickListener(this);

        ll_pond_click.setOnClickListener(this);
        ll_well_click.setOnClickListener(this);
        ll_manrega_click.setOnClickListener(this);
        ll_remark_click.setOnClickListener(this);
//        ll_sanrachna_click.setOnClickListener(this);
        ll_building_click.setOnClickListener(this);
        ll_plantation_click.setOnClickListener(this);

        ll_building_entry.setOnClickListener(this);
        ll_Plantatin_NewEntry.setOnClickListener(this);
        ll_nursery_upload.setOnClickListener(this);
        ll_building_upload.setOnClickListener(this);
        ll_Plantatin_Edit.setOnClickListener(this);
        ll_building_edit.setOnClickListener(this);

        dataBaseHelper=new DataBaseHelper(this);

        mcv_structure.setVisibility(GONE);
        mcv_well.setVisibility(GONE);
        mcv_scheme.setVisibility(GONE);
        ll_nursery_card.setVisibility(GONE);
        ll_building_card.setVisibility(GONE);

        getUserDetail();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        showPending();
        handleDropdownMenu(isShowPond, ll_pond_menu, iv_pond_drpdwn);
        handleDropdownMenu(isShowWell, ll_well_menu, iv_well_drpdwn);
        handleDropdownMenu(isShowManrega, ll_manreag_menu, iv_manrega_drpdwn);
        handleDropdownMenu(isShowRemark, ll_remark_menu, iv_remark_drpdwn);
        //handleDropdownMenu(isShowSanrachna, ll_sanrachna_menu, iv_sanrachna_drpdwn);
        handleDropdownMenu(isShowBuilding, ll_building_menu, iv_building_drpdwn);
        handleDropdownMenu(isShowNursury, ll_plantation_menu, iv_plantation_drpdwn);
        //Toast.makeText(this, getApplicationContext().getPackageName(), Toast.LENGTH_SHORT).show();

        tv_appver.setText("ऐप वर्ज़न "+Utiilties.getAppVersion(this));
    }

    private void showPending(){
        String pondCount = dataBaseHelper.getPondLakeUpdatedDataCount();
        String wellCount = dataBaseHelper.getWellUpdatedDataCount();
        String manreagCount = dataBaseHelper.getManregadDataCount();
        String otherCount = dataBaseHelper.getOtherSchemeDataCount();
        String nursury = dataBaseHelper.getNursuryBuildingDataCount(AppConstant.NURSURY);
        String building = dataBaseHelper.getNursuryBuildingDataCount(AppConstant.BUILDING);
        String remark = dataBaseHelper.getUpdateRemarkDataCount();

        pending_sudhar_pond.setText(pondCount);
        pending_upload_pond.setText(pondCount);

        pending_sudhar_well.setText(wellCount);
        pending_upload_well.setText(wellCount);

        pending_manrega.setText(manreagCount);
        pending_upload_manrega.setText(manreagCount);

        pending_remark.setText(remark);
//        pending_upload_other.setText(otherCount);

        tv_nursury_count.setText(nursury);
        tv_building_count.setText(building);

    }

    private void confirmLogout(){
        SplashActivity.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putBoolean("username", false);
        editor.putBoolean("password", false);
        editor.commit();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.deleteAllInspectionRecord();
        Intent intent = new Intent(this, PreLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirmation)
                .setIcon(R.drawable.logo1)
                .setMessage("कृपया लॉगआउट करने से पहले सभी निरीक्षण रिकॉर्ड अपलोड कर लें, अन्यथा आप उन रिकॉर्ड को खो सकते हैं! \n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        confirmLogout();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dataBaseHelper.getUserDetails(username, password);

        if(userInfo != null){
            tv_userName.setText("Welcome "+userInfo.getName());
            tv_district.setText("जिला : "+userInfo.getDistName());

            if (userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
                mcv_remark_update.setVisibility(GONE);
//                deptList = dataBaseHelper.getExecDepartmentList();
//                if(deptList.size()>0){
//                    tv_block.setText("विभाग : "+getDeptName().trim());
//                    setupViewAccordingToDept();
//                }
//                else{
                    new SyncDepartmentData().execute();
               // }

            }else if(userInfo.getUserrole().equals(AppConstant.PRS)){
                tv_block.setText("प्रखंड : "+userInfo.getBlockName());
                tv_block.setVisibility(View.VISIBLE);

                ll_rdd.setVisibility(View.VISIBLE);

                mcv_structure.setVisibility(View.VISIBLE);
                mcv_well.setVisibility(View.VISIBLE);
                mcv_scheme.setVisibility(View.VISIBLE);

                tv_sync_title.setText("सर्वर से डेटा लोड करें");

            }else{
                tv_block.setVisibility(GONE);
                ll_rdd.setVisibility(GONE);
                ll_nursery_card.setVisibility(GONE);
                ll_building_card.setVisibility(GONE);
                rl_sync_data.setVisibility(GONE);
            }

            Log.e("District", userInfo.getDistrictCode());
            Log.e("Block", userInfo.getBlockCode());
        }else{
            Toast.makeText(this, "User Detail Not Found!!", Toast.LENGTH_SHORT).show();
            confirmLogout();
        }
    }

    public void alertForDeptLoad(){
        new AlertDialog.Builder(this)
                .setTitle("विभाग नहीं मिला")
                .setIcon(R.drawable.logo1)
                .setMessage("विभाग का विवरण नहीं मिला\nकृपया फिर से लॉगिन करें")
                .setCancelable(false)
                .setPositiveButton("ओके", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SplashActivity.prefs = PreferenceManager.getDefaultSharedPreferences(DashboardActivity.this);
                        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
                        editor.putBoolean("username", false);
                        editor.putBoolean("password", false);
                        editor.commit();

                        Intent intent = new Intent(DashboardActivity.this, PreLoginActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .show();
    }

    public void setupViewAccordingToDept(){
        syncDataList.clear();
        if(deptInfo != null){

            if(deptInfo.getStructure().equals("Y")){
                mcv_structure.setVisibility(View.VISIBLE);
                if(deptInfo.getDeptId().equals("2")){
                    syncDataList.add("अहर/पाइन 1 एकड़ से अधिक");
                    syncDataList.add("तालाब 5 एकड़ से अधिक");
                }else{
                    syncDataList.add("जल संरचना");
                }
            }

            if(deptInfo.getWellChapakal().equals("Y")){
                mcv_well.setVisibility(View.VISIBLE);
                syncDataList.add("कुँआ");
                syncDataList.add("चापाकल");
            }

            if(deptInfo.getScheme().equals("Y")){
                mcv_scheme.setVisibility(View.VISIBLE);
                syncDataList.add("योजना");
            }

            if(deptInfo.getBuilding().equals("Y")){
                ll_building_card.setVisibility(View.VISIBLE);
                syncDataList.add("भवन");
            }

            if(deptInfo.getNursery().equals("Y")){
                ll_nursery_card.setVisibility(View.VISIBLE);
                syncDataList.add("पौधशाला");
            }

        }else{
            alertForDeptLoad();
        }
    }

//    public void setupViewAccordingToDept(){
//
//        switch (userInfo.getDeptId()){
//            case "1":
//                ll_nursery_card.setVisibility(View.VISIBLE);
//                ll_building_card.setVisibility(View.VISIBLE);
//                ll_scheme.setVisibility(View.GONE);
//                break;
//
//            case "2":
//                tv_sync_title.setText("संरचना का डेटा लोड करें");
//                mcv_structure.setVisibility(View.VISIBLE);
//                ll_nursery_card.setVisibility(View.GONE);
//                ll_building_card.setVisibility(View.GONE);
//                break;
//
//            case "3":
//            case "9":
//            case "10":
//            case "11":
//            case "12":
//            case "13":
//                //tv_sync_title.setText("भवन का डेटा लोड करें");
//                tv_sync_title.setText("सर्वर से डेटा लोड करें");
//                ll_building_card.setVisibility(View.VISIBLE);
//                ll_nursery_card.setVisibility(View.GONE);
//                isShowBuilding = true;
//                break;
//
//            case "5":
//                tv_sync_title.setText("सर्वर से डेटा लोड करें");
//                //tv_sync_title.setText("पौधशाला का डेटा लोड करें");
//                ll_nursery_card.setVisibility(View.VISIBLE);
//                ll_building_card.setVisibility(View.GONE);
//                break;
//
//            case "4":
//            case "6":
//            case "7":
//            case "8":
//                tv_sync_title.setText("योजना का डेटा लोड करें");
//                ll_nursery_card.setVisibility(View.GONE);
//                ll_building_card.setVisibility(View.GONE);
//                //ll_prs.setVisibility(View.GONE);
//                mcv_structure.setVisibility(View.GONE);
//                break;
//        }
//    }

    @Override
    public void onClick(View view){
        int id = view.getId();

        switch (id){
            case R.id.Pond_List_Layout:
                Intent i = new Intent(this, PondListActivity.class);
                i.putExtra("user", userInfo);
                //i.putExtra("DistrictCode", userInfo.getDistrictCode());
                startActivity(i);
                break;
            case R.id.WellList_Layout:
                Intent iwell = new Intent(this, WellListActivity.class);
                iwell.putExtra("user", userInfo);
//                iwell.putExtra("blockCode", userInfo.getBlockCode());
//                iwell.putExtra("DistrictCode", userInfo.getDistrictCode());
                startActivity(iwell);
                break;
            case R.id.linearlayout_eidt_pond:
                Intent iPondEdit = new Intent(this, PondListEditActivity.class);
                startActivity(iPondEdit);
                break;
            case R.id.linearlayout_edit_well:
                Intent iWellEdit = new Intent(this, WellListEditActivity.class);
                startActivity(iWellEdit);
                break;
            case R.id.ll_manrega_add:
                Intent iManregaInsp = new Intent(this, ManregaInspectionListActivity.class);
//                iManregaInsp.putExtra("blockCode", userInfo.getBlockCode());
//                iManregaInsp.putExtra("DistrictCode", userInfo.getDistrictCode());
               // iManregaInsp.putExtra(AppConstant.USER, userInfo);
                startActivity(iManregaInsp);
                break;

            case R.id.ll_remark_add:
                Intent remark = new Intent(this, RemarkUpdateListActivity.class);
                remark.putExtra("user", userInfo);
                startActivity(remark);
                break;
            case R.id.ll_remark_edit:
                Intent iRemark = new Intent(this, RemarkUpdateEditActivity.class);
                iRemark.putExtra("user", userInfo);
                startActivity(iRemark);
                break;

            case R.id.ll_manrega_edit:
                Intent iManregaEdit = new Intent(this, ManregaListActivity.class);
                startActivity(iManregaEdit);
                break;
//            case R.id.ll_other_edit:
//                Intent iOtherEdit = new Intent(this, OtherSchemeListActivity.class);
//                startActivity(iOtherEdit);
//                break;
            case R.id.ll_upload_pond_data:
                Intent iReport = new Intent(this, PondReportActivity.class);
                iReport.putExtra("blockCode", userInfo.getBlockCode());
                iReport.putExtra("DistrictCode", userInfo.getDistrictCode());
                iReport.putExtra("userId", userInfo.getUserID());
                startActivity(iReport);
                break;
            case R.id.ll_manrega_upload:
                Intent iReportmanrega = new Intent(this, ManregaReportActivity.class);
                iReportmanrega.putExtra("blockCode", userInfo.getBlockCode());
                iReportmanrega.putExtra("DistrictCode", userInfo.getDistrictCode());
                iReportmanrega.putExtra("userId", userInfo.getUserID());
                startActivity(iReportmanrega);
                break;

            case R.id.ll_uplaod_well_data:
                Intent iReportWell = new Intent(this, WellReportActivity.class);
                iReportWell.putExtra("blockCode", userInfo.getBlockCode());
                iReportWell.putExtra("DistrictCode", userInfo.getDistrictCode());
                iReportWell.putExtra("userId", userInfo.getUserID());
                startActivity(iReportWell);
                break;

//            case R.id.ll_sanrachna_add:
//                Intent iSanrachnaAdd = new Intent(this, SanrachnaProgressListActivity.class);
//                iSanrachnaAdd.putExtra("blockCode", userInfo.getBlockCode());
//                iSanrachnaAdd.putExtra("DistrictCode", userInfo.getDistrictCode());
//                startActivity(iSanrachnaAdd);
//                break;
//            case R.id.ll_sanrachna_edit:
//                Intent iSanrachnaEdit = new Intent(this, SanrachnaProgressEditActivity.class);
//                startActivity(iSanrachnaEdit);
//                break;
            case R.id.ll_Plantatin_NewEntry:
                Intent nursery = new Intent(this, NursuryListActivity.class);
                nursery.putExtra("type", AppConstant.NURSURY);
                startActivity(nursery);
                break;
            case R.id.ll_nursery_upload:
                Intent nursery1 = new Intent(this, NurseryReportActivity.class);
                nursery1.putExtra("type", AppConstant.NURSURY);
                nursery1.putExtra("blockCode", userInfo.getBlockCode());
                nursery1.putExtra("DistrictCode", userInfo.getDistrictCode());
                nursery1.putExtra("userId", userInfo.getUserID());
                startActivity(nursery1);
                break;

            case R.id.ll_building_upload:
                Intent building_upload = new Intent(this, NurseryReportActivity.class);
                building_upload.putExtra("type", AppConstant.BUILDING);
                building_upload.putExtra("blockCode", userInfo.getBlockCode());
                building_upload.putExtra("DistrictCode", userInfo.getDistrictCode());
                building_upload.putExtra("userId", userInfo.getUserID());
                startActivity(building_upload);
                break;


            case R.id.ll_Plantatin_Edit:
                Intent nurseryedit = new Intent(this, NursuryListEditActivity.class);
                nurseryedit.putExtra("type", AppConstant.NURSURY);
                startActivity(nurseryedit);
                break;
            case R.id.ll_building_entry:
                //Toast.makeText(this, "Will be available soon", Toast.LENGTH_SHORT).show();
                Intent building = new Intent(this, NursuryListActivity.class);
                building.putExtra("type", AppConstant.BUILDING);
                startActivity(building);
                break;
            case R.id.ll_building_edit:
                Intent bedit = new Intent(this, NursuryListEditActivity.class);
                bedit.putExtra("type", AppConstant.BUILDING);
                startActivity(bedit);
                break;

//            case R.id.ll_remark_edit:
//                Intent remark = new Intent(this, NursuryListEditActivity.class);
//                startActivity(remark);
//                break;
//            case R.id.ll_inspect_pond_mwr:
//                Intent inspectMwr = new Intent(this, DistrictWisePondListActivity.class);
//                inspectMwr.putExtra("DistrictCode", userInfo.getDistrictCode());
//                inspectMwr.putExtra("userId", userInfo.getUserID());
//                startActivity(inspectMwr);
//                break;
//
//            case R.id.ll_upload_pond_mwr:
//                Intent uploadMwr = new Intent(this, UploadPondPineInspectionActivity.class);
//                uploadMwr.putExtra("DistrictCode", userInfo.getDistrictCode());
//                uploadMwr.putExtra("userId", userInfo.getUserID());
//                startActivity(uploadMwr);
//                break;

            case R.id.ll_pond_click:
                isShowPond = !isShowPond;
                handleDropdownMenu(isShowPond, ll_pond_menu, iv_pond_drpdwn);
                break;
            case R.id.ll_well_click:
                isShowWell = !isShowWell;
                handleDropdownMenu(isShowWell, ll_well_menu, iv_well_drpdwn);
                break;
            case R.id.ll_manrega_click:
                isShowManrega = !isShowManrega;
                handleDropdownMenu(isShowManrega, ll_manreag_menu, iv_manrega_drpdwn);
                break;
            case R.id.ll_remark_click:
                isShowRemark = !isShowRemark;
                handleDropdownMenu(isShowRemark, ll_remark_menu, iv_remark_drpdwn);
                break;
//            case R.id.ll_sanrachna_click:
//                isShowSanrachna = !isShowSanrachna;
//                handleDropdownMenu(isShowSanrachna, ll_sanrachna_menu, iv_sanrachna_drpdwn);
//                break;
            case R.id.ll_building_click:
                isShowBuilding = !isShowBuilding;
                handleDropdownMenu(isShowBuilding, ll_building_menu, iv_building_drpdwn);
                break;
            case R.id.ll_plantation_click:
                isShowNursury = !isShowNursury;
                handleDropdownMenu(isShowNursury, ll_plantation_menu, iv_plantation_drpdwn);
                break;
            case R.id.rl_sync_data:
                if (userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
                    handleUpdteButtonForDept();
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.logo1);
                    builder.setTitle("सर्वर से डेटा लोड करें");
                    //String[] syncList = {"जल संरचना","कुँआ","पंचायत","मनरेगा","अन्य विभाग", "कार्य प्रगती जल संरचना"};
                    String[] syncList = {"जल संरचना","कुँआ", "चापाकल","योजना","पंचायत", AppConstant.UPDATEREMARK};
                    builder.setItems(syncList, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which){
                                case 0:
                                    new SyncPondLakedata().execute("");
                                    break;
                                case 1:
                                    new SyncWelldata("2").execute("");
                                    break;
                                case 2:
                                    new SyncWelldata("5").execute("");
                                    break;
                                case 3:
                                    new SyncManregaData().execute("");
                                    break;
                                case 4:
                                    new SyncPanchayatData().execute("");
                                    break;
                                case 5:
                                    syncRemarkData("1");
                                    break;
                            }
                        }
                    });
                    builder.create().show();
                }
        }
    }

    public void handleUpdteButtonForDept(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo1);

//        if(syncDataList.size() == 1){
//            builder.setTitle("सर्वर से "+syncDataList.get(0)+" लोड करें");
//        }else{
            builder.setTitle("सर्वर से डेटा लोड करें");
       // }

        builder.setItems(syncDataList.toArray(new CharSequence[syncDataList.size()]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (syncDataList.get(which)){
                    case "जल संरचना":
                        new SyncPondLakedataDistrictWise("1").execute();
                        break;
                    case "अहर/पाइन 1 एकड़ से अधिक":
                        new SyncPondDataDistrictMwr("A").execute();
                        break;
                    case "तालाब 5 एकड़ से अधिक":
                        new SyncPondDataDistrictMwr("T").execute();
                        break;
                    case "कुँआ":
                        new SyncPondLakedataDistrictWise("2").execute();
                        break;
                    case "चापाकल":
                        new SyncPondLakedataDistrictWise("5").execute();
                        break;
                    case "योजना":
                        new SyncSchemeInspectionListDeptWiseList().execute("");
                        break;
                    case "भवन":
                        new SyncNursuryData(AppConstant.BUILDING).execute();
                        break;
                    case "पौधशाला":
                        new SyncNursuryData(AppConstant.NURSURY).execute();
                        break;
                }
            }
        });
        builder.create().show();
    }

//    public void handleUpdteButtonForDept(){
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setIcon(R.drawable.logo1);
//        builder.setTitle("सर्वर से डेटा लोड करें");
//        switch (userInfo.getDeptId()){
//            case "1":
//
//                String[] sync = {"भवन","पौधशाला"};
//                builder.setItems(sync, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0:
//                                new SyncNursuryData(AppConstant.BUILDING).execute();
//                                break;
//                            case 1:
//                                new SyncNursuryData(AppConstant.NURSURY).execute();
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//                break;
//
//            case "2":
//                String[] syncList = {"अहर/पाइन 1 एकड़ से अधिक","तालाब 5 एकड़ से अधिक", "योजना"};
//                builder.setItems(syncList, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0:
//                                new SyncPondDataDistrictMwr("A").execute("");
//                                break;
//                            case 1:
//                                new SyncPondDataDistrictMwr("T").execute("");
//                                break;
//                            case 2:
//                                new SyncSchemeInspectionListDeptWiseList().execute("");
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//                break;
//
//            case "3":
//            case "9":
//            case "10":
//            case "11":
//            case "12":
//            case "13":
//                String[] syncList1 = {"भवन", "योजना"};
//                builder.setItems(syncList1, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0:
//                                new SyncNursuryData(AppConstant.BUILDING).execute();
//                                break;
//                            case 1:
//                                new SyncSchemeInspectionListDeptWiseList().execute("");
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//                // new SyncNursuryData(AppConstant.BUILDING).execute();
//                break;
//
//            case "5":
//                String[] syncList2 = {"पौधशाला", "योजना"};
//                builder.setItems(syncList2, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        switch (which){
//                            case 0:
//                                new SyncNursuryData(AppConstant.NURSURY).execute();
//                                break;
//                            case 1:
//                                new SyncSchemeInspectionListDeptWiseList().execute("");
//                                break;
//                        }
//                    }
//                });
//                builder.create().show();
//                //new SyncNursuryData(AppConstant.NURSURY).execute();
//                break;
//
//            case "4":
//            case "6":
//            case "7":
//            case "8":
//                new SyncSchemeInspectionListDeptWiseList().execute("");
//                break;
//        }
//    }

    public void handleDropdownMenu(Boolean flag, LinearLayout layout, ImageView iv){
        if(flag){
            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
            layout.setVisibility(View.VISIBLE);
        }else{
            iv.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
            layout.setVisibility(GONE);
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo1)
                .setTitle(R.string.appTitle)
                .setMessage("क्या आप एैप से बाहर निकलना चाहते हैं?")
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DashboardActivity.this.finish();
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showPending();

    }

    public DepartmentEntity getDeptInfo(){
        if(deptList.size() > 0){
            for (DepartmentEntity item: deptList){
                if(userInfo.getDeptId().equals(item.getDeptId()))
                    return item;
            }
        }

        return null;
    }

    public void showAlert(String message){
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo1)
                .setTitle(R.string.appTitle)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private class SyncPondDataDistrictMwr extends AsyncTask<String, Void, ArrayList<PondInspectionEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        String type;

        public SyncPondDataDistrictMwr(String type) {
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PondInspectionEntity> doInBackground(String...arg) {
            String blockId = userInfo.getBlockCode();

            return WebServiceHelper.getPondDataMWRDistrictWise(userInfo.getDistrictCode(), type);
        }


        @Override
        protected void onPostExecute(ArrayList<PondInspectionEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
                long i= helper.setPondMWRDataToLocal(result, "pond");

                if(i>0)
                {
                    Toast.makeText(getApplicationContext(), "Pond Lake Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to store to local database",Toast.LENGTH_SHORT).show();
                }
            }else {
                Toast.makeText(getApplicationContext(), "No Data Found!!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class SyncPondLakedata extends AsyncTask<String, Void, ArrayList<PondEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PondEntity> doInBackground(String...arg) {
            String blockId = userInfo.getBlockCode();

            return WebServiceHelper.getPondLakeWellData(blockId, "pond", "");
        }

        @Override
        protected void onPostExecute(ArrayList<PondEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
                long i= helper.setPondLakeWellDataToLocal(result, "pond", "0");

                if(i>0)
                {
                    Toast.makeText(getApplicationContext(), "Pond Lake Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to store to local database",Toast.LENGTH_SHORT).show();
                }
            }else {
                //Toast.makeText(getApplicationContext(), "निरीक्षण हेतु कोई भी संरचना उपलब्ध नहीं हैं",Toast.LENGTH_SHORT).show();
                showAlert("निरीक्षण हेतु "+userInfo.getBlockName()+" में कोई भी संरचना उपलब्ध नहीं हैं");
            }

        }
    }

    private class SyncPondLakedataDistrictWise extends AsyncTask<String, Void, ArrayList<PondEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        String strId;

        public SyncPondLakedataDistrictWise(String strId) {
            this.strId = strId;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PondEntity> doInBackground(String...arg) {

            return WebServiceHelper.getPondLakeWellDataDistrictWise(userInfo.getDistrictCode(), "pond", strId);
        }

        @Override
        protected void onPostExecute(ArrayList<PondEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
                long i= helper.setPondLakeWellDataToLocal(result, "pond", strId);

                if(i>0)
                {
                    Toast.makeText(getApplicationContext(), "Pond Lake Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to store to local database",Toast.LENGTH_SHORT).show();
                }
            }else {
                //Toast.makeText(getApplicationContext(), "निरीक्षण हेतु कोई भी संरचना उपलब्ध नहीं हैं",Toast.LENGTH_SHORT).show();
                if(userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
                    showAlert("निरीक्षण हेतु "+userInfo.getDistName()+" में कोई भी संरचना उपलब्ध नहीं हैं");
                }else{
                    showAlert("निरीक्षण हेतु "+userInfo.getBlockName()+" में कोई भी संरचना उपलब्ध नहीं हैं");
                }

            }

        }
    }

    private class SyncWelldata extends AsyncTask<String, Void, ArrayList<PondEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);
        String structureId;

        public SyncWelldata(String structureId) {
            this.structureId = structureId;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PondEntity> doInBackground(String...arg) {

            return WebServiceHelper.getPondLakeWellData(userInfo.getBlockCode(), "Well", structureId);
        }

        @Override
        protected void onPostExecute(ArrayList<PondEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            long i= helper.setPondLakeWellDataToLocal(result, "well", structureId);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Well Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                String strName = structureId.equals("2") ? "कुंआ" : "चापाकल";
                //Toast.makeText(getApplicationContext(), "निरीक्षण हेतु कोई भी "+strName+" उपलब्ध नहीं हैं",Toast.LENGTH_SHORT).show();
                showAlert("निरीक्षण हेतु "+userInfo.getBlockName()+" में कोई भी "+strName+" उपलब्ध नहीं हैं");
            }
        }
    }

    public void syncRemarkData(String strType){
        new SyncRemarkUpdateData(strType).execute();
    }

    private class SyncPanchayatData extends AsyncTask<String, Void, ArrayList<PanchayatEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PanchayatEntity> doInBackground(String...arg) {


            return WebServiceHelper.getPanchayatList(userInfo.getDistrictCode(), userInfo.getBlockCode());

        }

        @Override
        protected void onPostExecute(ArrayList<PanchayatEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            new SyncWardData().execute("");
            long i= helper.setPanchayatDataToLocal(userInfo,result);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Panchayat Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update panchayat",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SyncWardData extends AsyncTask<String, Void, ArrayList<ward>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<ward> doInBackground(String...arg) {


            return WebServiceHelper.getWardListData(userInfo.getBlockCode());

        }

        @Override
        protected void onPostExecute(ArrayList<ward> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            new SyncVillageData().execute("");
            long i= helper.setWardDataToLocal(result);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Ward Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update ward",Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class SyncVillageData extends AsyncTask<String, Void, ArrayList<VillageListEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<VillageListEntity> doInBackground(String...arg) {

            return WebServiceHelper.getVillageListData(userInfo.getBlockCode());
        }

        @Override
        protected void onPostExecute(ArrayList<VillageListEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            new SyncSanrachnaTypeData().execute("");
            long i= helper.setVillageDataToLocal(result);

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

    private class SyncSanrachnaTypeData extends AsyncTask<String, Void, ArrayList<SanrachnaTypeEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<SanrachnaTypeEntity> doInBackground(String...arg) {

            return WebServiceHelper.getSanrachnaTypeData();
        }

        @Override
        protected void onPostExecute(ArrayList<SanrachnaTypeEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


            long i= helper.setSanrachnaTypeDataToLocal(result);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Sanrachna Type Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update Sanrachna Type",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class SyncManregaData extends AsyncTask<String, Void, ArrayList<ManregaSchemeDetail>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<ManregaSchemeDetail> doInBackground(String...arg) {


            return WebServiceHelper.getManregaInspectionData(userInfo.getBlockCode());

        }

        @Override
        protected void onPostExecute(ArrayList<ManregaSchemeDetail> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


                long i= helper.setManregaInspDataToLocal(result);

                if(i>0)
                {
                    Toast.makeText(getApplicationContext(), "Scheme Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to Update Manrega Data",Toast.LENGTH_SHORT).show();

                }
            }else{
                showAlert("निरीक्षण हेतु "+userInfo.getBlockName()+" में कोई भी योजना उपलब्ध नहीं हैं");
            }

        }
    }

    private class SyncOtherDeptData extends AsyncTask<String, Void, ArrayList<OtherDeptInsptEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<OtherDeptInsptEntity> doInBackground(String...arg) {
            return WebServiceHelper.getOtherDeptInspectionData(userInfo.getBlockCode());
        }

        @Override
        protected void onPostExecute(ArrayList<OtherDeptInsptEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


                long i= helper.setOtherInspDataToLocal(result);

                if(i>0)
                {
                    Toast.makeText(getApplicationContext(), "Other Dept Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to Update Other Dept Data",Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(getApplicationContext(), "निरीक्षण हेतु कोई भी योजना उपलब्ध नहीं हैं",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class SyncSanrachnaProgessData extends AsyncTask<String, Void, ArrayList<SanrachnaDataEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<SanrachnaDataEntity> doInBackground(String...arg) {


            return WebServiceHelper.getSanrachnaProgessData(userInfo.getBlockCode());

        }

        @Override
        protected void onPostExecute(ArrayList<SanrachnaDataEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


                long i= helper.setSanrachnaProgressDataToLocal(result);

                if(i>0)
                {
                    Toast.makeText(getApplicationContext(), "Sanrachna Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to Update Sanrachna Data",Toast.LENGTH_SHORT).show();

                }
            }else{
                Toast.makeText(getApplicationContext(), "No Data Found!",Toast.LENGTH_SHORT).show();
            }

        }
    }

    private class SyncNursuryData extends AsyncTask<String, Void, ArrayList<NurseryEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);
        String type;

        public SyncNursuryData(String type) {
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<NurseryEntity> doInBackground(String...arg) {

            return WebServiceHelper.getNursuryData(userInfo.getDistrictCode(), type);
        }

        @Override
        protected void onPostExecute(ArrayList<NurseryEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                new SaveNursuryBuildingDataToLocal(result, type).execute("");
            }else{
                Toast.makeText(getApplicationContext(), "निरीक्षण हेतु सत्यापन/सर्वेक्षण का बटन दबाये",Toast.LENGTH_SHORT).show();
            }


        }
    }

    private class SaveNursuryBuildingDataToLocal extends AsyncTask<String, Void, Long>{
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);
        ArrayList<NurseryEntity> data;
        String type;

        public SaveNursuryBuildingDataToLocal(ArrayList<NurseryEntity> data, String type) {
            this.data = data;
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("Saving Data...");
            dialog.show();
        }

        @Override
        protected Long doInBackground(String... strings) {

            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            long i= helper.insertNursuryBuildingDataToLocal(data,type);

            return i;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if(aLong>0)
            {
                Toast.makeText(getApplicationContext(), "Data Saved Successfully",Toast.LENGTH_SHORT).show();
                //new SyncWardData().execute("");
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to Update "+type+" Data",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class SyncDepartmentData extends AsyncTask<String, Void, ArrayList<DepartmentEntity>> {

        public SyncDepartmentData() {
        }

        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

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
                    deptInfo = getDeptInfo();
                    tv_block.setText("विभाग : "+deptInfo.getDeptNameHn());
                    setupViewAccordingToDept();
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

    private class SyncSchemeInspectionListDeptWiseList extends AsyncTask<String, Void, ArrayList<ManregaSchemeDetail>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<ManregaSchemeDetail> doInBackground(String...arg) {


            return WebServiceHelper.getSchemeInspectionListDeptWise(userInfo.getDeptId(),userInfo.getDistrictCode());

        }

        @Override
        protected void onPostExecute(ArrayList<ManregaSchemeDetail> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


                long i= helper.setManregaInspDataToLocal(result);

                if(i>0)
                {
                    Toast.makeText(getApplicationContext(), "Scheme Data Synced Successfully",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Failed to Update Manrega Data",Toast.LENGTH_SHORT).show();

                }
            }else{
                showAlert("निरीक्षण हेतु "+userInfo.getBlockName()+" में कोई भी योजना उपलब्ध नहीं हैं");
            }

        }
    }

    private class SyncRemarkUpdateData extends AsyncTask<String, Void, Long> {
        //private final ProgressDialog dialog = new ProgressDialog(DashboardActivity.this);

        String type;

        public SyncRemarkUpdateData(String type) {
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            dialog.setCanceledOnTouchOutside(false);

            if(type.equals("1")){
                dialog.setMessage("जल संरचना लोड हो रहा...");
            }else if(type.equals("2")){
                dialog.setMessage("कुँआ लोड हो रहा...");
            }else if(type.equals("5")){
                dialog.setMessage("चापाकल लोड हो रहा...");
            }else{
                dialog.setMessage("डेटा लोड हो रहा...");
            }

            dialog.show();
        }

        @Override
        protected Long doInBackground(String...arg) {

            ArrayList<StructureRemarkEntity> response = WebServiceHelper.getStructureRemarkData(userInfo.getDistrictCode(),userInfo.getBlockCode(), type);

            if(response.size()>0){

//                if(type.equals("1")){
//                    dialog.setMessage("जल संरचना सेव हो रहा...");
//                }else if(type.equals("2")){
//                    dialog.setMessage("कुँआ सेव हो रहा...");
//                }if(type.equals("5")){
//                    dialog.setMessage("चापाकल सेव हो रहा...");
//                }else{
//                    dialog.setMessage("डेटा सेव हो रहा...");
//                }

                long i= dataBaseHelper.setRemarkDataToLocal(response, type);

                return i;
            }else{
                return Long.parseLong("0");
            }


        }

        @Override
        protected void onPostExecute(Long result) {

            if(type.equals("1")){
                syncRemarkData("2");
            }else if(type.equals("2")){
                syncRemarkData("5");
            }else{
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            if(result == 0){
                showAlert("निरीक्षण हेतु कोई भी डेटा उपलब्ध नहीं हैं");
            }if(result == -1){
                showAlert("निरीक्षण हेतु डेटा सेव नहीं हुआ");
            }if(result == -2){
                showAlert("Error: Caught error while saving data");
            }else{
                if(type.equals("1")){
                    Toast.makeText(DashboardActivity.this, "Water Bodies Remark Data Synced Successfully", Toast.LENGTH_SHORT).show();
                }else if(type.equals("2")){
                    Toast.makeText(DashboardActivity.this, "Well Remark Data Synced Successfully", Toast.LENGTH_SHORT).show();
                }else if(type.equals("5")){
                    Toast.makeText(DashboardActivity.this, "Chapakal Remark Data Synced Successfully", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(DashboardActivity.this, "Remark Data Synced Successfully", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
