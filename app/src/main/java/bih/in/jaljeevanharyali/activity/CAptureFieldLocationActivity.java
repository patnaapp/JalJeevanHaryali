package bih.in.jaljeevanharyali.activity;

import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class CAptureFieldLocationActivity extends Activity implements LocationListener {

    Button btn_location1,btn_location2,btn_location3,btn_location4,btn_location5,btn_submit,btn_location_lnr_1,btn_location_lnr_2;
    Spinner spin_land_type;
    LinearLayout ll_location_form,ll_buttons_linear,ll_buttons;
    RelativeLayout rl_linear,rl_rectangle;
    ImageView iv_mark1,iv_mark2,iv_mark3,iv_mark4,iv_mark5,iv_mark6;

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
    String Lat1="",Long1="",Lat2="",Long2="",Lat3="",Long3="",Lat4="",Long4="",Lat5="",Long5="";
    String locationpoint="";
    TextView tv_lat1,tv_lat2,tv_lat3,tv_lat4,tv_lat5,tv_long1,tv_long2,tv_long3,tv_long4,tv_long5,tv_lat_lnr1,tv_long_lnr1,tv_lat_lnr2,tv_long_lnr2;

    static Location LastLocation = null;
    private final int UPDATE_LATLNG = 2;
    private final int UPDATE_ADDRESS = 1;
    private ProgressDialog dialog;

    String landTypeOption[] = {"-चयन करे-","आयताकार/Rectangular", "रैखिक/Linear"};
    String landType="", landTypeValue="", khataKhesra,rakba,ownerMob,ownerName,totalTree;
    ArrayAdapter landTypeAdaptor;

    int phaseType;
    String PID = "0";
    String isOpen = "",purpose="",remark,awyabId;
    String treeTypeName="", treeTypeId="";
    String inspType="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_field_location_aaa);

        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);

        btn_location1=findViewById(R.id.btn_location1);
        btn_location2=findViewById(R.id.btn_location2);
        btn_location3=findViewById(R.id.btn_location3);
        btn_location4=findViewById(R.id.btn_location4);
        btn_location5=findViewById(R.id.btn_location5);
        btn_submit=findViewById(R.id.btn_submit);
        btn_location_lnr_1=findViewById(R.id.btn_location_lnr_1);
        btn_location_lnr_2=findViewById(R.id.btn_location_lnr_2);

        tv_lat1=findViewById(R.id.tv_lat1);
        tv_lat2=findViewById(R.id.tv_lat2);
        tv_lat3=findViewById(R.id.tv_lat3);
        tv_lat4=findViewById(R.id.tv_lat4);
        tv_lat5=findViewById(R.id.tv_lat5);

        tv_long1=findViewById(R.id.tv_long1);
        tv_long2=findViewById(R.id.tv_long2);
        tv_long3=findViewById(R.id.tv_long3);
        tv_long4=findViewById(R.id.tv_long4);
        tv_long5=findViewById(R.id.tv_long5);

        tv_lat_lnr1=findViewById(R.id.tv_lat_lnr1);
        tv_long_lnr1=findViewById(R.id.tv_long_lnr1);
        tv_lat_lnr2=findViewById(R.id.tv_lat_lnr2);
        tv_long_lnr2=findViewById(R.id.tv_long_lnr2);

        spin_land_type=findViewById(R.id.spin_land_type);

        iv_mark1=findViewById(R.id.iv_mark1);
        iv_mark2=findViewById(R.id.iv_mark2);
        iv_mark3=findViewById(R.id.iv_mark3);
        iv_mark4=findViewById(R.id.iv_mark4);
        iv_mark5=findViewById(R.id.iv_mark5);
        iv_mark6=findViewById(R.id.iv_mark6);

        iv_mark2.setVisibility(View.GONE);
        iv_mark3.setVisibility(View.GONE);
        iv_mark4.setVisibility(View.GONE);
        iv_mark6.setVisibility(View.GONE);

        ll_location_form=findViewById(R.id.ll_location_form);

        ll_buttons_linear=findViewById(R.id.ll_buttons_linear);
        ll_buttons=findViewById(R.id.ll_buttons);

        rl_linear=findViewById(R.id.rl_linear);
        rl_rectangle=findViewById(R.id.rl_rectangle);

        rl_linear.setVisibility(View.GONE);
        rl_rectangle.setVisibility(View.GONE);
        ll_buttons_linear.setVisibility(View.GONE);
        ll_buttons.setVisibility(View.GONE);

        ll_location_form.setVisibility(View.GONE);

        btn_submit.setVisibility(View.GONE);

        btn_location1.setEnabled(true);
        btn_location2.setEnabled(false);
        btn_location3.setEnabled(false);
        btn_location4.setEnabled(false);
        btn_location5.setEnabled(false);

        btn_location_lnr_1.setEnabled(true);
        btn_location_lnr_2.setEnabled(false);

        PID = String.valueOf(getIntent().getIntExtra("KEY_PID", 0));
        phaseType = getIntent().getIntExtra("PhaseType", 0);
        isOpen = getIntent().getStringExtra("isOpen");
        remark = getIntent().getStringExtra("remark");
        purpose = getIntent().getStringExtra("pupose");
        awyabId = getIntent().getStringExtra("awyabId");
//        khataKhesra = getIntent().getStringExtra("khataKhesra");
//        rakba = getIntent().getStringExtra("rakba");
//        totalTree = getIntent().getStringExtra("totalTree");
//        ownerName = getIntent().getStringExtra("name");
//        ownerMob = getIntent().getStringExtra("mobile");
//
//        treeTypeId = getIntent().getStringExtra("treeId");
//        treeTypeName = getIntent().getStringExtra("treeName");
        inspType = getIntent().getStringExtra("inspType");

//        pbar = (ProgressBar) findViewById(R.id.spinner);
//        pbar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#80DAEB"),
//                android.graphics.PorterDuff.Mode.MULTIPLY);
//        pbar.setVisibility(View.VISIBLE);

        landTypeAdaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, landTypeOption);
        landTypeAdaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_land_type.setAdapter(landTypeAdaptor);

        if(awyabId.equals("8")){
            spin_land_type.setSelection(1);
            spin_land_type.setEnabled(false);
        }

        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);

        GlobalVariables.glocation = null;

        btn_location1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationpoint="1";
                locationManager();
                //getLocation();
            }
        });
        btn_location2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.glocation = null;
                locationpoint="2";
                locationManager();


            }
        });
        btn_location3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.glocation = null;
                locationpoint="3";
                locationManager();


            }
        });
        btn_location4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.glocation = null;
                locationpoint="4";
                locationManager();


            }
        });
        btn_location5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.glocation = null;
                locationpoint = "5";
                locationManager();
            }
        });

        btn_location_lnr_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.glocation = null;
                locationpoint = "6";
                locationManager();
            }
        });

        btn_location_lnr_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.glocation = null;
                locationpoint = "7";
                locationManager();
            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = 0;
                DataBaseHelper placeData = new DataBaseHelper(CAptureFieldLocationActivity.this);
                SQLiteDatabase db = placeData.getWritableDatabase();
                ContentValues values = new ContentValues();
                String[] whereArgs;

//                values.put("Khaata_Kheshara_Number", khataKhesra);
//                values.put("Rakba", rakba);
//                values.put("TotalTree", totalTree);
//                values.put("LandOwnerMob", ownerMob);
//                values.put("LandOwnerName", ownerName);
                values.put("FencingLatitude1", Lat1);
                values.put("FencingLatitude2", Lat2);
                values.put("FencingLatitude3", Lat3);
                values.put("FencingLatitude4", Lat4);
                values.put("FencingLongitude1", Long1);
                values.put("FencingLongitude2", Long2);
                values.put("FencingLongitude3", Long3);
                values.put("FencingLongitude4", Long4);

//                values.put("TreeType", treeTypeId);
//                values.put("TreeTypeName", treeTypeName);

                if(inspType.equals("manrega")){
                    whereArgs = new String[]{String.valueOf(PID)};
                    long c= db.update("Menrega_Rural_Dev_Dept", values, "id=?", whereArgs);

                    if(c>0)
                    {
                        Log.e("Location","Updated");
                        //Toast.makeText(CAptureFieldLocationActivity.this, "Location Sa")
                    }

                    Intent intent = new Intent(CAptureFieldLocationActivity.this, ManregaPhotoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("KEY_PID", Integer.parseInt(PID));
                    intent.putExtra("PhaseType", phaseType);
                    intent.putExtra("pupose", "new");
                    intent.putExtra("isOpen", isOpen);
                    intent.putExtra("remark", remark);
                    startActivity(intent);
                    finish();
                }else{
                    whereArgs = new String[]{String.valueOf(PID)};
                    long c= db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=?", whereArgs);

                    if(c>0)
                    {
                        Log.e("Location","Updated");
                        //Toast.makeText(CAptureFieldLocationActivity.this, "Location Sa")
                    }

                    Intent intent = new Intent(CAptureFieldLocationActivity.this, OtherSchemePhotoActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("KEY_PID", Integer.parseInt(PID));
                    intent.putExtra("PhaseType", phaseType);
                    intent.putExtra("pupose", "new");
                    intent.putExtra("isOpen", isOpen);
                    intent.putExtra("remark", remark);
                    startActivity(intent);
                    finish();
                }


            }
        });

        spin_land_type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    landType = ""+position;
                    landTypeValue = landTypeOption[position].toString();
                    ll_location_form.setVisibility(View.VISIBLE);
                    if(landType.equals("1")){
                        rl_rectangle.setVisibility(View.VISIBLE);
                        ll_buttons.setVisibility(View.VISIBLE);
                        blinkImageView(iv_mark1);

                        rl_linear.setVisibility(View.GONE);
                        ll_buttons_linear.setVisibility(View.GONE);
                    }else if(landType.equals("2")){
                        rl_linear.setVisibility(View.VISIBLE);
                        ll_buttons_linear.setVisibility(View.VISIBLE);
                        blinkImageView(iv_mark5);

                        rl_rectangle.setVisibility(View.GONE);
                        ll_buttons.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public  void blinkImageView(ImageView img){

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(200); //You can manage the time of the blink with this parameter
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        img.startAnimation(anim);
    }

    private void locationManager() {
        dialog.setMessage("ट्रैकिंग लोकेशन...");
        dialog.show();

        if (GlobalVariables.glocation == null) {
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            //btn_location1.setEnabled(false);
            mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, (float) 0.01, mlistener);
            mlocManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, (float) 0.01, mlistener);
        } else {

        }
    }

    private void updateUILocation(Location location) {

        Message.obtain(
                mHandler,
                UPDATE_LATLNG,
                new DecimalFormat("#.0000000").format(location.getLatitude())
                        + "-"
                        + new DecimalFormat("#.0000000").format(location
                        .getLongitude()) + "-" + location.getAccuracy() + "-" + location.getTime())
                .sendToTarget();

    }

    private final LocationListener mlistener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            //A new location update is received. Do something useful with it.
            //Update the UI with
            //the location update.
            if (Utiilties.isGPSEnabled(CAptureFieldLocationActivity.this)) {

                LastLocation = location;
                GlobalVariables.glocation = location;
                updateUILocation(GlobalVariables.glocation);
                //   if (getIntent().getStringExtra("KEY_PIC").equals("1")) {
                if (location.getLatitude() > 0.0) {
                    //dialog = new ProgressDialog(CAptureFieldLocationActivity.this);
                    //long elapsedMillis = SystemClock.elapsedRealtime() - chronometer.getBase();
                    if (location.getAccuracy() > 0 && location.getAccuracy() < 150) {
                        if (dialog.isShowing()) {
                            dialog.dismiss();
                        }
                        //pbar.setVisibility(View.GONE);
                        btn_location1.setEnabled(true);


                        if (locationpoint.equals("1")){
                            Lat1 = Double.toString(location.getLatitude());
                            Long1 = Double.toString(location.getLongitude());

                            tv_lat1.setText("Lat 1: "+Lat1);
                            tv_long1.setText("Long 1: "+Long1);
                            locationManager.removeUpdates(this);
                            btn_location2.setEnabled(true);
                            btn_location1.setEnabled(false);
                            btn_location1.setText("सत्यापित हुआ");

                            iv_mark1.clearAnimation();
                            iv_mark2.setVisibility(View.VISIBLE);
                            blinkImageView(iv_mark2);
                            spin_land_type.setEnabled(false);
                        }
                        else if (locationpoint.equals("2")){
                            Lat2 = Double.toString(location.getLatitude());
                            Long2 = Double.toString(location.getLongitude());
                            tv_lat2.setText("Lat 2: "+Lat2);
                            tv_long2.setText("Long 2: "+Long2);
                            locationManager.removeUpdates(this);
                            btn_location3.setEnabled(true);
                            btn_location2.setEnabled(false);
                            btn_location1.setEnabled(false);
                            btn_location2.setText("सत्यापित हुआ");

                            iv_mark2.clearAnimation();
                            iv_mark3.setVisibility(View.VISIBLE);
                            blinkImageView(iv_mark3);
                        }
                        else if (locationpoint.equals("3")){
                            Lat3 = Double.toString(location.getLatitude());
                            Long3 = Double.toString(location.getLongitude());
                            tv_lat3.setText("Lat 3: "+Lat3);
                            tv_long3.setText("Long 3: "+Long3);
                            locationManager.removeUpdates(this);
                            btn_location4.setEnabled(true);
                            btn_location3.setEnabled(false);
                            btn_location2.setEnabled(false);
                            btn_location1.setEnabled(false);
                            btn_location3.setText("सत्यापित हुआ");

                            iv_mark3.clearAnimation();
                            iv_mark4.setVisibility(View.VISIBLE);
                            blinkImageView(iv_mark4);
                        }
                        else if (locationpoint.equals("4")){
                            Lat4 = Double.toString(location.getLatitude());
                            Long4 = Double.toString(location.getLongitude());
                            tv_lat4.setText("Lat 4: "+Lat4);
                            tv_long4.setText("Long 4: "+Long4);
                            locationManager.removeUpdates(this);
                            btn_location5.setEnabled(true);
                            btn_location4.setEnabled(false);
                            btn_location3.setEnabled(false);
                            btn_location2.setEnabled(false);
                            btn_location1.setEnabled(false);
                            btn_location4.setText("सत्यापित हुआ");

                            iv_mark4.clearAnimation();
                            //iv_mark2.setVisibility(View.VISIBLE);
                            blinkImageView(iv_mark1);
                        }
                        else if (locationpoint.equals("5")){
                            Lat5 = Double.toString(location.getLatitude());
                            Long5 = Double.toString(location.getLongitude());


                            Float preLat = null, preLong = null, crntLat, crntLong;
                            preLat = Float.parseFloat(Lat1);
                            // preLat = Float.parseFloat("25.606220");
                            preLong = Float.parseFloat(Long1);
                            //  preLong = Float.parseFloat("25.606220");
                            Log.e("KEY_PICLat1", "" + preLat);
                            Log.e("KEY_PICLong1", "" + preLong);
                            Float lat2 = Float.parseFloat(Lat5);
                            Float long2 = Float.parseFloat(Long5);

                            Log.e("KEY_PICLat2", "" + lat2);
                            Log.e("KEY_PICLong2", "" + long2);
                            try {
                                float distance = (float) distance(preLat, preLong, lat2, long2) * 1000;
                                Log.e("KEY_PICDist", "" + distance);
                                if (distance <=5) {
                                    tv_lat5.setText("Lat 5: "+Lat5);
                                    tv_long5.setText("Long 5: "+Long5);
                                    locationManager.removeUpdates(this);
                                    btn_location5.setEnabled(false);
                                    btn_location4.setEnabled(false);
                                    btn_location3.setEnabled(false);
                                    btn_location2.setEnabled(false);
                                    btn_location1.setEnabled(false);
                                    btn_location5.setText("सत्यापित हुआ");
                                    iv_mark1.clearAnimation();
                                    btn_submit.setVisibility(View.VISIBLE);
                                } else {

                                    btn_location5.setText("स्थान कैप्चर करने के लिए रेंज में नहीं हैं");
                                }
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }

                        }
                        else if (locationpoint.equals("6")){
                            Lat1 = Double.toString(location.getLatitude());
                            Long1 = Double.toString(location.getLongitude());

                            tv_lat_lnr1.setText("Lat 1: "+Lat1);
                            tv_long_lnr1.setText("Long 1: "+Long1);
                            locationManager.removeUpdates(this);
                            btn_location_lnr_2.setEnabled(true);
                            btn_location_lnr_1.setEnabled(false);
                            btn_location_lnr_1.setText("सत्यापित हुआ");

                            iv_mark5.clearAnimation();
                            iv_mark6.setVisibility(View.VISIBLE);
                            blinkImageView(iv_mark6);
                            spin_land_type.setEnabled(false);
                        }
                        else if (locationpoint.equals("7")){
                            Lat4 = Double.toString(location.getLatitude());
                            Long4 = Double.toString(location.getLongitude());
                            tv_lat_lnr2.setText("Lat 2: "+Lat4);
                            tv_long_lnr2.setText("Long 2: "+Long4);
                            locationManager.removeUpdates(this);
                            btn_location_lnr_1.setEnabled(false);
                            btn_location_lnr_2.setEnabled(false);
                            btn_location_lnr_2.setText("सत्यापित हुआ");

                            iv_mark6.clearAnimation();
                            btn_submit.setVisibility(View.VISIBLE);
                        }

                        latitude = Double.toString(location.getLatitude());
                        longitud = Double.toString(location.getLongitude());
                    } else {
                        dialog.setMessage("Wait for gps to become stable");
                        dialog.show();

                        btn_location1.setEnabled(false);
                        btn_location_lnr_1.setEnabled(false);
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

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case UPDATE_ADDRESS:
                case UPDATE_LATLNG:
                    String[] LatLon = ((String) msg.obj).split("-");
//
                    Log.e("", "Lat-Long" + LatLon[0] + "   " + LatLon[1]);


                    break;
            }
        }
    };

    public double distanceBetweenCoordinatesInMtrs(double lat1, double long1, double lat2, double long2){
        Location startPoint=new Location("locationA");
        startPoint.setLatitude(lat1);
        startPoint.setLongitude(long1);

        Location endPoint=new Location("locationA");
        endPoint.setLatitude(lat2);
        endPoint.setLongitude(long2);

        double distance=startPoint.distanceTo(endPoint);
        return distance;
    }

    private double distance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }
    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
