package bih.in.jaljeevanharyali.activity.grievance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.LoginActivity;
import bih.in.jaljeevanharyali.activity.PreLoginActivity;
import bih.in.jaljeevanharyali.activity.SplashActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.DashboardEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class GrievanceHomeActivity extends Activity {

    TextView tv_userName,tv_father,tv_district,tv_block;
    Button btn_logout;

    DataBaseHelper dataBaseHelper;
    UserDetails userInfo;
    TextView tv_incomplete_scheme,tv_amount_incmp,tv_complete_scheme,tv_amount_cmp;
    TextView tv_well,tv_pond,tv_chapakal,tv_pyne,tv_aaher,tv_nahar,tv_version;
    TextView tv_total_building,tv_inspected_building,tv_total_nursery,tv_inspected_nursery;

    ImageView menu_inflater_stnew;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance_home);

        dataBaseHelper=new DataBaseHelper(this);

        tv_userName = (TextView) findViewById(R.id.tv_userName);
        tv_father = (TextView) findViewById(R.id.tv_father);
        tv_district = (TextView) findViewById(R.id.tv_district);
        tv_block = (TextView) findViewById(R.id.tv_block);

        tv_incomplete_scheme = (TextView) findViewById(R.id.tv_incomplete_scheme);
        tv_amount_incmp = (TextView) findViewById(R.id.tv_amount_incmp);
        tv_complete_scheme = (TextView) findViewById(R.id.tv_complete_scheme);
        tv_amount_cmp = (TextView) findViewById(R.id.tv_amount_cmp);
        tv_well = (TextView) findViewById(R.id.tv_well);
        tv_pond = (TextView) findViewById(R.id.tv_pond);
        tv_chapakal = (TextView) findViewById(R.id.tv_chapakal);
        tv_pyne = (TextView) findViewById(R.id.tv_pyne);
        tv_aaher = (TextView) findViewById(R.id.tv_aaher);
        tv_nahar = (TextView) findViewById(R.id.tv_nahar);

        tv_version = findViewById(R.id.tv_version);

        tv_total_building = findViewById(R.id.tv_total_building);
        tv_inspected_building = findViewById(R.id.tv_inspected_building);
        tv_total_nursery = findViewById(R.id.tv_total_nursery);
        tv_inspected_nursery = findViewById(R.id.tv_inspected_nursery);

        btn_logout = (Button) findViewById(R.id.btn_logout);

        menu_inflater_stnew = (ImageView) findViewById(R.id.menu_inflater_stnew);

        tv_version.setText("App Version "+Utiilties.getAppVersion(this));

        if (!GlobalVariables.isOffline && !Utiilties.isOnline(GrievanceHomeActivity.this)){

            AlertDialog.Builder ab = new AlertDialog.Builder(GrievanceHomeActivity.this);
            ab.setMessage(Html.fromHtml(
                    "<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();

        }else{
            new FechDashboardData().execute();
        }

        menu_inflater_stnew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(GrievanceHomeActivity.this, menu_inflater_stnew);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        int id = item.getItemId();

                        //noinspection SimplifiableIfStatement
                        if (id == R.id.action_logout_st) {
                            new AlertDialog.Builder(GrievanceHomeActivity.this)
                                    .setTitle(R.string.confirmation)
                                    .setIcon(R.drawable.logo1)
                                    .setMessage("क्या आप वाकई ऐप से लॉग आउट करना चाहते हैं! \n ")
                                    .setCancelable(false)
                                    .setPositiveButton("हाँ", new DialogInterface.OnClickListener()
                                    {
                                        public void onClick(DialogInterface dialog, int id)
                                        {
                                            confirmLogout();
                                        }
                                    })
                                    .setNegativeButton("नहीं", null)
                                    .show();
//
                        }
                        if (id == R.id.action_changepass_st) {

                            Intent i = new Intent(GrievanceHomeActivity.this, ChangePassword_Activity.class);
                            // i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);

                            return true;
//
                        }
                        return true;
                    }
                });

                popup.show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getUserDetail();

        if (!GlobalVariables.isOffline && !Utiilties.isOnline(GrievanceHomeActivity.this)) {

            AlertDialog.Builder ab = new AlertDialog.Builder(GrievanceHomeActivity.this);
            ab.setMessage(Html.fromHtml(
                    "<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();

        }else{
            //new FechDashboardData().execute();

        }
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        Log.e("username", username);
        userInfo = dataBaseHelper.getUserDetails(username, password);

        tv_userName.setText(userInfo.getName());
        tv_father.setText("मोबाईल: "+userInfo.getMobileNo());
        tv_district.setText("जिला : "+userInfo.getDistName());
        tv_block.setText("प्रखंड : "+userInfo.getBlockName());

    }

    public void onRegister(View view)
    {
        Intent intent = new Intent(this, OtherTypeGrievActivity.class);
        intent.putExtra("data", userInfo);
        startActivity(intent);
    }

    public void onTrackGrievance(View view)
    {
        Intent intent = new Intent(this, TrackGrievanceActivity.class);
        intent.putExtra("data", userInfo);
        startActivity(intent);
    }

    private void logout()
    {
        new AlertDialog.Builder(this)
                .setTitle(R.string.confirmation)
                .setIcon(R.drawable.logo1)
                .setMessage("क्या आप वाकई ऐप से लॉगआउट करना चाहते हैं! \n ")
                .setCancelable(false)
                .setPositiveButton("हाँ", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        confirmLogout();
                    }
                })
                .setNegativeButton("नहीं", null)
                .show();
    }

    private void confirmLogout(){
        SplashActivity.prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = SplashActivity.prefs.edit();
        editor.putBoolean("username", false);
        editor.putBoolean("password", false);
        editor.commit();

//        DataBaseHelper dataBaseHelper = new DataBaseHelper(this);
//        dataBaseHelper.deleteAllInspectionRecord();
        Intent intent = new Intent(this, PreLoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public void onViewSchemes(View view) {
        Intent intent = new Intent(this, SearchSchemeActivity.class);
        intent.putExtra("type", "scheme");
        startActivity(intent);
    }

    public void onViewStructures(View view) {
        Intent intent = new Intent(this, SearchSchemeActivity.class);
        intent.putExtra("type", "structure");
        startActivity(intent);
    }

    public void onViewNearbySchemes(View view) {
        Intent intent = new Intent(this, NearbySchemeStructureActivity.class);
        intent.putExtra("type", "scheme");
        startActivity(intent);
    }

    public void onViewNearbyStructures(View view) {
        Intent intent = new Intent(this, NearbySchemeStructureActivity.class);
        intent.putExtra("type", "structure");
        startActivity(intent);
    }

    public void onViewNearbyBuilding(View view) {
        Intent intent = new Intent(this, NearbySchemeStructureActivity.class);
        intent.putExtra("type", AppConstant.BUILDING);
        startActivity(intent);
    }

    public void onViewNearbyNursery(View view) {
        Intent intent = new Intent(this, NearbySchemeStructureActivity.class);
        intent.putExtra("type", AppConstant.NURSURY);
        startActivity(intent);
    }


    private class FechDashboardData extends AsyncTask<String, Void, DashboardEntity> {
        DashboardEntity data;
        String _uid;
        private final ProgressDialog dialog = new ProgressDialog(GrievanceHomeActivity.this);
        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(GrievanceHomeActivity.this).create();



        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("लोड हो रहा है...");
            if (!GrievanceHomeActivity.this.isFinishing()) {
                this.dialog.show();
            }
        }

        @Override
        protected DashboardEntity doInBackground(String... param) {

            return WebServiceHelper.getBen_Details();
        }

        @Override
        protected void onPostExecute(DashboardEntity result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue", "" + result);
            if (result != null) {
                data=result;
                tv_incomplete_scheme.setText(data.getStartedongoing());
                tv_amount_incmp.setText(Utiilties.getNumberFormatString(data.getStartedongoingAmount()));
                tv_complete_scheme.setText(data.getCompleted());
                tv_amount_cmp.setText(Utiilties.getNumberFormatString(data.getCompletedAmount()));
                tv_well.setText(data.getWelltotal());
                tv_pond.setText(data.getPondTotal());
                tv_chapakal.setText(data.getHandPumbTotal());
                tv_pyne.setText(data.getPainTotal());
                tv_aaher.setText(data.getAhartotal());
                tv_nahar.setText(data.getNaharTotal());

                tv_total_building.setText(data.getTotalBuilding());
                tv_inspected_building.setText(data.getTotalBuildingInspected());
                tv_total_nursery.setText(data.getTotalNursery());
                tv_inspected_nursery.setText(data.getTotalNurseryInspected());

//                Intent i =new Intent(MainHomeActivity.this, ProfileActivity.class);
//                i.putExtra("data",DashboardEntity);
//                startActivity(i);
            } else {
                Toast.makeText(getApplicationContext(), "Result Null..Please Try Later", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void onViewComplain_status(View view)
    {
        Intent i=new Intent(GrievanceHomeActivity.this,ViewStatus_Activity.class);
        startActivity(i);
    }
}
