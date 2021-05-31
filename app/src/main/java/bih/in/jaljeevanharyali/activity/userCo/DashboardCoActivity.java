package bih.in.jaljeevanharyali.activity.userCo;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.DashboardActivity;
import bih.in.jaljeevanharyali.activity.LoginActivity;
import bih.in.jaljeevanharyali.activity.PanchayatEntity;
import bih.in.jaljeevanharyali.activity.PondListActivity;
import bih.in.jaljeevanharyali.activity.PondListEditActivity;
import bih.in.jaljeevanharyali.activity.PondReportActivity;
import bih.in.jaljeevanharyali.activity.PreLoginActivity;
import bih.in.jaljeevanharyali.activity.SplashActivity;
import bih.in.jaljeevanharyali.activity.WellListActivity;
import bih.in.jaljeevanharyali.activity.WellListEditActivity;
import bih.in.jaljeevanharyali.activity.WellReportActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaTypeEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class DashboardCoActivity extends Activity implements View.OnClickListener {

    LinearLayout pondLayout, wellLayout,linearlayout_eidt_pond,linearlayout_edit_well,ll_upload_pond_data,ll_uplaod_well_data;
    TextView tv_userName,pending_sudhar_pond,pending_sudhar_well,pending_upload_well,pending_upload_pond,tv_district,tv_block;
    Button btn_logout,btn_sync_pond,btn_sync_well,btn_sync_panchayat;

    //Menu
    LinearLayout ll_pond_menu,ll_well_menu;
    LinearLayout ll_pond_click,ll_well_click;
    ImageView iv_pond_drpdwn,iv_well_drpdwn;

    RelativeLayout rl_sync_data;

    DataBaseHelper dataBaseHelper;
    Boolean isShowPond = false, isShowWell = false;
    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_co);

        setupUi();

        dataBaseHelper=new DataBaseHelper(this);
        getUserDetail();

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        btn_sync_pond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DashboardCoActivity.SyncPondEncrhmntdata().execute("");
            }
        });

        btn_sync_well.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DashboardCoActivity.SyncWelldata().execute("");
            }
        });

        btn_sync_panchayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DashboardCoActivity.SyncPanchayatData().execute("");
            }
        });


        showPending();
        handlePondMenu();
        handleWellMenu();
    }

    private void setupUi(){
        pondLayout = (LinearLayout) findViewById(R.id.Pond_List_Layout);
        wellLayout = (LinearLayout) findViewById(R.id.WellList_Layout);
        linearlayout_eidt_pond = (LinearLayout) findViewById(R.id.linearlayout_eidt_pond);
        linearlayout_edit_well = (LinearLayout) findViewById(R.id.linearlayout_edit_well);
        ll_upload_pond_data = (LinearLayout) findViewById(R.id.ll_upload_pond_data);
        ll_uplaod_well_data = (LinearLayout) findViewById(R.id.ll_uplaod_well_data);

        ll_pond_menu = (LinearLayout) findViewById(R.id.ll_pond_menu);
        ll_well_menu = (LinearLayout) findViewById(R.id.ll_well_menu);

        ll_pond_click = (LinearLayout) findViewById(R.id.ll_pond_click);
        ll_well_click = (LinearLayout) findViewById(R.id.ll_well_click);

        iv_pond_drpdwn = (ImageView) findViewById(R.id.iv_pond_drpdwn);
        iv_well_drpdwn = (ImageView) findViewById(R.id.iv_well_drpdwn);

        rl_sync_data = (RelativeLayout) findViewById(R.id.rl_sync_data);

        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_sync_pond = (Button) findViewById(R.id.btn_sync_pond);
        btn_sync_well = (Button) findViewById(R.id.btn_sync_well);
        btn_sync_panchayat = (Button) findViewById(R.id.btn_sync_panchayat);

        tv_userName = (TextView) findViewById(R.id.tv_userName);
        pending_sudhar_pond = (TextView) findViewById(R.id.pending_sudhar_pond);
        pending_sudhar_well = (TextView) findViewById(R.id.pending_sudhar_well);
        pending_upload_well = (TextView) findViewById(R.id.pending_upload_well);
        pending_upload_pond = (TextView) findViewById(R.id.pending_upload_pond);

        tv_district = (TextView) findViewById(R.id.tv_district);
        tv_block = (TextView) findViewById(R.id.tv_block);
    
        pondLayout.setOnClickListener(this);
        wellLayout.setOnClickListener(this);
        linearlayout_eidt_pond.setOnClickListener(this);
        linearlayout_edit_well.setOnClickListener(this);
        ll_upload_pond_data.setOnClickListener(this);
        ll_uplaod_well_data.setOnClickListener(this);

        rl_sync_data.setOnClickListener(this);

        ll_pond_click.setOnClickListener(this);
        ll_well_click.setOnClickListener(this);
    }

    private void showPending(){
        String pondCount = dataBaseHelper.getPondEncrhmntUpdatedDataCount();
        String wellCount = dataBaseHelper.getWellEncrhmntUpdatedDataCount();

        pending_sudhar_pond.setText(pondCount);
        pending_upload_pond.setText(pondCount);

        pending_sudhar_well.setText(wellCount);
        pending_upload_well.setText(wellCount);
    }

    private void confirmLogout(){
        SplashActivity.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putBoolean("username", false);
        editor.putBoolean("password", false);
        editor.commit();

        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
        dataBaseHelper.deleteAllCoInspectionRecord();
        Intent intent = new Intent(this,
                PreLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirmation)
                .setIcon(R.drawable.logo1)
                .setMessage("क्या आप लॉग आउट करना चाहते हैं? \n ")
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
        userInfo = dataBaseHelper.getUserDetails(username.toLowerCase(), password);
        tv_userName.setText("Welcome "+userInfo.getName());
        tv_district.setText("जिला : "+userInfo.getDistName());
        tv_block.setText("प्रखंड : "+userInfo.getBlockName());

        Log.e("District", userInfo.getDistrictCode());
        Log.e("Block", userInfo.getBlockCode());
    }

    private String capitalizeFirstCharacter(String name){
        String[] nameList = name.trim().split(" ");
        String orderName = "";

        for(String item: nameList){
            item = item.toLowerCase();
            //item = item.toUpperCase()
        }

        return orderName;
    }

    @Override
    public void onClick(View view){
        int id = view.getId();

        switch (id){
            case R.id.Pond_List_Layout:
                Intent i = new Intent(this, PondEncroachmentListActivity.class);
                i.putExtra("blockCode", userInfo.getBlockCode());
                i.putExtra("DistrictCode", userInfo.getDistrictCode());
                i.putExtra("structureType", "pond");
                startActivity(i);
                break;
            case R.id.WellList_Layout:
                Intent iwell = new Intent(this, PondEncroachmentListActivity.class);
                iwell.putExtra("blockCode", userInfo.getBlockCode());
                iwell.putExtra("DistrictCode", userInfo.getDistrictCode());
                iwell.putExtra("structureType", "well");
                startActivity(iwell);
                break;
            case R.id.linearlayout_eidt_pond:
                Intent iPondEdit = new Intent(this, PondEncrhmntEditListActivity.class);
                iPondEdit.putExtra("structureType", "pond");
                startActivity(iPondEdit);
                break;
            case R.id.linearlayout_edit_well:
                Intent iWellEdit = new Intent(this, PondEncrhmntEditListActivity.class);
                iWellEdit.putExtra("structureType", "well");
                startActivity(iWellEdit);
                break;
            case R.id.ll_upload_pond_data:
                Intent iReport = new Intent(this, EncroachmentReportActivity.class);
                iReport.putExtra("blockCode", userInfo.getBlockCode());
                iReport.putExtra("DistrictCode", userInfo.getDistrictCode());
                iReport.putExtra("userId", userInfo.getUserID());
                iReport.putExtra("structureType", "pond");
                startActivity(iReport);
                break;
            case R.id.ll_uplaod_well_data:
                Intent iReportWell = new Intent(this, EncroachmentReportActivity.class);
                iReportWell.putExtra("blockCode", userInfo.getBlockCode());
                iReportWell.putExtra("DistrictCode", userInfo.getDistrictCode());
                iReportWell.putExtra("userId", userInfo.getUserID());
                iReportWell.putExtra("structureType", "well");
                startActivity(iReportWell);
                break;

            case R.id.ll_pond_click:
                isShowPond = !isShowPond;
                handlePondMenu();
                break;
            case R.id.ll_well_click:
                isShowWell = !isShowWell;
                handleWellMenu();
                break;

            case R.id.rl_sync_data:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setIcon(R.drawable.logo1);
                builder.setTitle("अपडेट करें");
                String[] syncList = {"जल संरचना","कुँआ","पंचायत"};
                builder.setItems(syncList, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                new SyncPondEncrhmntdata().execute("");
                                break;
                            case 1:
                                new SyncWelldata().execute("");
                                break;
                            case 2:
                                new SyncPanchayatData().execute("");
                                break;
                        }
                    }
                });

                builder.create().show();
        }
    }

    private void handlePondMenu(){
        if(isShowPond){
            iv_pond_drpdwn.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
            ll_pond_menu.setVisibility(View.VISIBLE);
        }else{
            iv_pond_drpdwn.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
            ll_pond_menu.setVisibility(View.GONE);
        }
    }

    private void handleWellMenu(){
        if(isShowWell){
            iv_well_drpdwn.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_up_black_24dp));
            ll_well_menu.setVisibility(View.VISIBLE);
        }else{
            iv_well_drpdwn.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_drop_down_black_24dp));
            ll_well_menu.setVisibility(View.GONE);
        }
    }

    private class SyncPondEncrhmntdata extends AsyncTask<String, Void, ArrayList<PondEncroachmentEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardCoActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PondEncroachmentEntity> doInBackground(String...arg) {
            String blockId = userInfo.getBlockCode();

            return WebServiceHelper.getPondLakeWellEncrhmntData(blockId, "pond");

        }

        @Override
        protected void onPostExecute(ArrayList<PondEncroachmentEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


            long i= helper.setPondLakeWellEncrhmntDataToLocal(result, "pond");

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Pond Lake Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class SyncWelldata extends AsyncTask<String, Void, ArrayList<PondEncroachmentEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardCoActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PondEncroachmentEntity> doInBackground(String...arg) {


            return WebServiceHelper.getPondLakeWellEncrhmntData(userInfo.getBlockCode(), "Well");

        }

        @Override
        protected void onPostExecute(ArrayList<PondEncroachmentEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


            long i= helper.setPondLakeWellEncrhmntDataToLocal(result, "well");

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Well Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.loading_fail),Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class SyncPanchayatData extends AsyncTask<String, Void, ArrayList<PanchayatEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(DashboardCoActivity.this);

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

            new DashboardCoActivity.SyncWardData().execute("");
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
        private final ProgressDialog dialog = new ProgressDialog(DashboardCoActivity.this);

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

            new DashboardCoActivity.SyncVillageData().execute("");
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
        private final ProgressDialog dialog = new ProgressDialog(DashboardCoActivity.this);

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

            new DashboardCoActivity.SyncSanrachnaTypeData().execute("");
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
        private final ProgressDialog dialog = new ProgressDialog(DashboardCoActivity.this);

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

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(R.drawable.logo1)
                .setTitle(R.string.appTitle)
                .setMessage("क्या आप एैप से बाहर निकलना चाहते हैं?")
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DashboardCoActivity.this.finish();
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

}
