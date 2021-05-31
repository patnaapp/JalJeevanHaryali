package bih.in.jaljeevanharyali.activity.grievance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.CAptureFieldLocationActivity;
import bih.in.jaljeevanharyali.adapter.PublicBuildingNurseryListAdapter;
import bih.in.jaljeevanharyali.adapter.PublicSchemeListAdapter;
import bih.in.jaljeevanharyali.adapter.PublicStructureListAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.entity.PublicNurseryBuildingEntity;
import bih.in.jaljeevanharyali.entity.SchemeDetailPublic;
import bih.in.jaljeevanharyali.entity.StructureDetailPublic;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class NearbySchemeStructureActivity extends Activity {

    TextView tv_title,tv_notfound;
    RecyclerView rv_data;

    ArrayList<SchemeDetailPublic> schemeList;
    PublicSchemeListAdapter schemeAdapter;

    ArrayList<StructureDetailPublic> structureList;
    PublicStructureListAdapter structureAdapter;

    ArrayList<PublicNurseryBuildingEntity> nurseryList;
    ArrayList<PublicNurseryBuildingEntity> buildingList;

    String type;

    final String TAG = "GPS";
    LocationManager locationManager;
    Location loc;
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    private final static int ALL_PERMISSIONS_RESULT = 101;
    String latitude="",longitud="";

    LocationManager mlocManager = null;
    static Location LastLocation = null;

    private final int UPDATE_LATLNG = 2;

    private ProgressDialog dialog;
    Boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nearby_scheme_structure);

        setup();
        setupLocation();

        type = getIntent().getStringExtra("type");
        if(type.equals("scheme")){
            tv_title.setText("योजना सूची विवरण");
        }else if(type.equals("structure")){
            tv_title.setText("संरचना सूची विवरण");
        }else if(type.equals(AppConstant.BUILDING)){
            tv_title.setText("भवन सूची विवरण");
        }else{
            tv_title.setText("पौधशाला सूची विवरण");
        }

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setup(){
        tv_title = findViewById(R.id.tv_title);
        rv_data = findViewById(R.id.rv_data);
        tv_notfound = findViewById(R.id.tv_notfound);

        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
    }

    public void setupLocation(){
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
//
//        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
//        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        GlobalVariables.glocation = null;

        if(mlocManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            locationManager();
        }else{
            buildAlertMessageNoGps();
        }

    }

    public void loadSchemeAdapter(){
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        schemeAdapter = new PublicSchemeListAdapter(this, schemeList);
        rv_data.setAdapter(schemeAdapter);
    }

    public void loadStructureAdapter(){
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        structureAdapter = new PublicStructureListAdapter(this, structureList);
        rv_data.setAdapter(structureAdapter);
    }

    public void loadSBuildingNurseryAdapter(){
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        PublicBuildingNurseryListAdapter adapter = new PublicBuildingNurseryListAdapter(this, type.equals(AppConstant.BUILDING) ? buildingList : nurseryList, type);
        rv_data.setAdapter(adapter);
    }

    private void locationManager() {
        dialog.setMessage("ट्रैकिंग लोकेशन...");
        dialog.show();

        if (GlobalVariables.glocation == null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.01, mlistener);
            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, mlistener);
        } else {

        }
    }

    private final LocationListener mlistener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            if (Utiilties.isGPSEnabled(NearbySchemeStructureActivity.this)) {

                LastLocation = location;
                if (location.getLatitude() > 0.0) {

                    if (location.getAccuracy() > 0 && location.getAccuracy() < 150) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }

                        syncData(location);
                    } else {
                        dialog.setMessage("Wait for gps to become stable");
                        dialog.show();
                    }
                }

            } else {
                Message.obtain(
                        mHandler,
                        UPDATE_LATLNG,
                        new DecimalFormat("#.0000000").format(location.getLatitude())
                                + "-"
                                + new DecimalFormat("#.0000000").format(location
                                .getLongitude()) + "-" + location.getAccuracy() + "-" + location.getTime())
                        .sendToTarget();
            }


        }

        @Override
        public void onProviderDisabled(String provider) {
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

    };

    public void syncData(Location location){
        mlocManager.removeUpdates(mlistener);
        latitude = Double.toString(location.getLatitude());
        longitud = Double.toString(location.getLongitude());
        Log.e("Lat-Long", ""+latitude+"-"+longitud);

        if(!isLoading){
            if(type.equals("scheme")){
                new SyncSchemeList().execute();
            }else if(type.equals("structure")){
                new SyncStructureList().execute();
            }else {
                new SyncBuildingNurseyList(type).execute();
            }
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_LATLNG:
                    String[] LatLon = ((String) msg.obj).split("-");
//
                    Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);


                    break;
            }
        }
    };

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS..")
                .setCancelable(false)
                .setPositiveButton("Turn on GPS", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private class SyncSchemeList extends AsyncTask<String, Void, ArrayList<SchemeDetailPublic>> {

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading Schemes...");
            dialog.show();
            isLoading = true;
        }

        @Override
        protected ArrayList<SchemeDetailPublic> doInBackground(String...arg) {
            return WebServiceHelper.getSchemeListLocationWise(latitude,longitud);
        }

        @Override
        protected void onPostExecute(ArrayList<SchemeDetailPublic> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            isLoading = false;

            if(result.size()> 0){
                schemeList = result;
                loadSchemeAdapter();
                tv_notfound.setVisibility(View.GONE);
            }else{
                rv_data.setVisibility(View.GONE);
                tv_notfound.setVisibility(View.VISIBLE);
            }

        }
    }

    private class SyncStructureList extends AsyncTask<String, Void, ArrayList<StructureDetailPublic>> {

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading...");
            dialog.show();
            isLoading = true;
        }

        @Override
        protected ArrayList<StructureDetailPublic> doInBackground(String...arg) {
            return WebServiceHelper.getStrucutureListLocationWise(latitude, longitud);
        }

        @Override
        protected void onPostExecute(ArrayList<StructureDetailPublic> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            isLoading = false;

            if(result.size()> 0){
                structureList = result;
                loadStructureAdapter();
                tv_notfound.setVisibility(View.GONE);
            }else{
                rv_data.setVisibility(View.GONE);
                tv_notfound.setVisibility(View.VISIBLE);
            }
        }
    }

    private class SyncBuildingNurseyList extends AsyncTask<String, Void, ArrayList<PublicNurseryBuildingEntity>> {

        String type;

        public SyncBuildingNurseyList(String type) {
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading...");
            dialog.show();
            isLoading = true;
        }

        @Override
        protected ArrayList<PublicNurseryBuildingEntity> doInBackground(String...arg) {
            return WebServiceHelper.getNurseryBuildingListLocationWise(latitude, longitud, type);
        }

        @Override
        protected void onPostExecute(ArrayList<PublicNurseryBuildingEntity> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            isLoading = false;

            if(result.size()> 0){
                if(type.equals(AppConstant.BUILDING)){
                    buildingList = result;
                }else{
                    nurseryList = result;
                }

                loadSBuildingNurseryAdapter();
                tv_notfound.setVisibility(View.GONE);
            }else{
                rv_data.setVisibility(View.GONE);
                tv_notfound.setVisibility(View.VISIBLE);
            }
        }
    }
}
