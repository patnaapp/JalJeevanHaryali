package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;


public class MapActivity extends Activity implements GoogleMap.OnMarkerClickListener, GoogleMap.OnMyLocationChangeListener, GoogleMap.OnInfoWindowClickListener,GoogleMap.OnMapClickListener,GoogleMap.OnMapLongClickListener, GoogleMap.OnMarkerDragListener{
    GoogleMap map;
    GPSTracker gps;
    LatLng latlon;
    private Marker usrloc_marker, usrloc_marker1;
    ArrayList<LatLng> array;
    int i = 0;
    public ArrayList<PondEntity> ret_booths=new ArrayList<>();
    private static String NAMESPACE = "http://gis.bih.nic.in/";
    // private static String METHOD_NAME = "getHealthDetails";
    private static String METHOD_NAME = "getBoothList";
    //  private static String URL = "http://gis.bih.nic.in/healthwebservices.asmx";
    private static String URL = "http://gis.bih.nic.in/BoothTrackingWebService.asmx";
    ProgressDialog progressDialog;
    String SearchText="";
    /* private static String NAMESPACE = "http://mobapp.bih.nic.in/";
     //private static String NAMESPACE = "http://onlineapp.bih.nic.in/";
     private static String METHOD_NAME = "getThanaDetail";
     //private static String URL = "http://onlineapp.bih.nic.in/thanalocationWebService.asmx";
     private static String URL = "http://mobapp.bih.nic.in/thanalocationWebService.asmx";
     private static SoapObject request;*/
    private List<String> uradd=new ArrayList<String>();
    private String _country="n/a";
    private String _state="n/a";
    private String _city="n/a";
    private String _zip="n/a";
    Address address=null;
    private String _address="Location not found";
    private String Address1 = "", City = "", State = "", Country = "", PIN = "";

    TextView tvDistanceDuration;
    Polyline line;

    boolean markerClicked;
    final int RQS_GooglePlayServices = 1;
    ImageButton imageButtonReg,imageButtonManual;
    TextView btnMapSatellite;

    DataBaseHelper localDBHelper;
    ArrayList<String> districtNameArray;
    Spinner SpnHealthF_ID;
    String distId="";
    String AcId="";
    String PcId="";
    String distName="",pcName="",acName="";
    ImageView img_search;
    EditText edt_search;
    RelativeLayout relativeLayout;
    TextView detail;
   String pachId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/
          try {
              pachId=getIntent().getStringExtra("panchayatCode");
          }catch (Exception e){}
        progressDialog=new ProgressDialog(MapActivity.this);
        localDBHelper=new DataBaseHelper(MapActivity.this);
        tvDistanceDuration = (TextView) findViewById(R.id.tv_distance_time);

        imageButtonManual = (ImageButton) findViewById(R.id.button_manual);
        btnMapSatellite = (TextView) findViewById(R.id.btn_map_satellite);
        SpnHealthF_ID=(Spinner)findViewById(R.id.spn_healthf_id);
        edt_search=(EditText)findViewById(R.id.edt_search);
        img_search=(ImageView)findViewById(R.id.img_search);
        detail=(TextView)findViewById(R.id.detail);
        relativeLayout=(RelativeLayout)findViewById(R.id.rel23);


        img_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setSearchText();
               /* if(!SearchText.equalsIgnoreCase("")){
                    loadmap(gps.getLatitude(),gps.getLongitude());
                }else {
                    Toast.makeText(getApplicationContext(),"Please Enter Booth Number ",Toast.LENGTH_LONG).show();
                }*/
                loadmap(gps.getLatitude(),gps.getLongitude());
            }
        });

        imageButtonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              /*  Intent i = new Intent(getBaseContext(), WebViewActivity.class);
                startActivity(i);*/
                AlertDialog.Builder ab = new AlertDialog.Builder(
                        MapActivity.this);
                ab.setTitle("Refresh Booth");
                ab.setMessage("Do you want to Refresh Booth List ? ");
                ab.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });

                ab.setNegativeButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // dialog.dismiss();
                        //finish();
                        if(Utiilties.isOnline(MapActivity.this)) {

                          //  AsyncPostData task = new AsyncPostData(distId,AcId,PcId);
                          //  task.execute();
                        }else {Toast.makeText(getApplicationContext(),"Please Trun On Internet",Toast.LENGTH_LONG).show();}

                    }
                });
                ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                ab.show();


            }

        });

        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        map.setMapType(map.MAP_TYPE_NORMAL);
        if (isOnline(MapActivity.this) == false) {
            showAlet(MapActivity.this);
        } else {
            getgps();
        }


        map.setMyLocationEnabled(true);
        map.setOnMapClickListener(this);
        map.setOnMapLongClickListener(this);
        map.setOnMarkerDragListener(this);

        map.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {

                i=0;
                Location location = map.getMyLocation();

                loadmap(location.getLatitude(), location.getLongitude());
                return false;
            }
        });

        btnMapSatellite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btnMapSatellite.getText().toString().equals("MAP")){
                    map.setMapType(map.MAP_TYPE_NORMAL);
                    btnMapSatellite.setText("SATELLITE");
                }else {
                    map.setMapType(map.MAP_TYPE_SATELLITE);
                    btnMapSatellite.setText("MAP");
                }

            }
        });
        //loadDistrictSpinnerdata();
    }

    private void setSearchText(){
        SearchText=edt_search.getText().toString();
    }




    //  }


    private void SetZoomlevel(ArrayList<LatLng> myFacilities, final GoogleMap mMap) {

        if (myFacilities != null && myFacilities.size() == 1) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myFacilities.get(0), 10));
        } else if (myFacilities != null && myFacilities.size() > 1) {
            final LatLngBounds.Builder builder = LatLngBounds.builder();
            for (int i = 0; i < myFacilities.size(); i++) {
                builder.include(myFacilities.get(i));
            }

            if (mMap != null) {
                //new CustomToast().Show_Toast(MapsActivity.this, _view, "Please wait displaying facilities found.");
                mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), findViewById(R.id.map)
                        .getWidth(), findViewById(R.id.map).getHeight(), 10));
            }
            //LatLngBounds bounds = builder.build();
            final ViewTreeObserver treeObserver = relativeLayout.getViewTreeObserver();
            treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @SuppressWarnings("deprecation")
                @Override
                public void onGlobalLayout() {
                    if (mMap != null) {
                        //new CustomToast().Show_Toast(MapsActivity.this, _view, "Please wait displaying facilities found.");
                        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), findViewById(R.id.map)
                                .getWidth(), findViewById(R.id.map).getHeight(), 10));
                        relativeLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                    }
                }
            });
        }

        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        //_want_to_draw_path="n";
//        map_list.setAdapter(new dataListAdapter());
    }


    public void loadmap(double latitude, double longitude) {
        array = new ArrayList<LatLng>();
        map.setOnMarkerClickListener(this);


        map.setOnInfoWindowClickListener(this);
        if (latlon != null) {

            if (i == 1) {
                map.clear();
                usrloc_marker1 = map.addMarker(new MarkerOptions().position(new LatLng(latitude, longitude)).draggable(true).title("-1"));

                usrloc_marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.npin));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude, longitude), 13));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))      // Sets the center of the map to location user
                        .zoom(13)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(60)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


                    //ret_booths = localDBHelper.getPondsInspectionDetail(pachId);

                //ret_booths=localDBHelper.getBotthlist("1028");
                // ret_booths=findNearByPlaces(latitude, longitude);

                if(ret_booths.size()==0) {
                    AlertDialog.Builder ab = new AlertDialog.Builder(
                            MapActivity.this);
                    ab.setTitle("Message");
                    ab.setMessage("No Sanrachna found near about Your Current Location");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    ab.create();
                    ab.show();

                }
                else {
                    Toast.makeText(MapActivity.this,"Located "+ret_booths.size()+" Sanrachna",Toast.LENGTH_LONG).show();
                    for (int i=0;i<ret_booths.size();i++) {

                        if (!ret_booths.get(i).getLatitude().equalsIgnoreCase("NA")) {
                            LatLng latLng = new LatLng(Double.parseDouble(ret_booths.get(i).getLatitude()), Double.parseDouble(ret_booths.get(i).getLongitude()));
                            MarkerOptions nearby_marker = new MarkerOptions().position(latLng).title("" + i);

                            // ROSE color icon

                            //nearby_marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                            nearby_marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pollingbooth));
                            usrloc_marker = map.addMarker(nearby_marker);

                        }
                    }
                    if(!SearchText.equalsIgnoreCase("")){
                        ArrayList<LatLng> myFacilities=new ArrayList<>();
                        myFacilities.add(new LatLng(latitude,longitude));
                        SetZoomlevel(myFacilities,map);
                    }
                }

               // usrloc_marker1.showInfoWindow();
            } else {
                map.clear();
                usrloc_marker1 = map.addMarker(new MarkerOptions().position(new LatLng(gps.getLatitude(), gps.getLongitude())).draggable(true).title("-1"));
                usrloc_marker1.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.npin));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gps.getLatitude(), gps.getLongitude()), 13));
                CameraPosition cameraPosition = new CameraPosition.Builder()
                        .target(new LatLng(gps.getLatitude(), gps.getLongitude()))      // Sets the center of the map to location user
                        .zoom(13)                   // Sets the zoom
                        .bearing(0)                // Sets the orientation of the camera to east
                        .tilt(60)                   // Sets the tilt of the camera to 30 degrees
                        .build();                   // Creates a CameraPosition from the builder
                map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
               /* AsyncPostData task = new AsyncPostData(new LatLng(gps.getLatitude(), gps.getLongitude()));
                task.execute();*/
                // ret_booths=localDBHelper.getBotthlist("1028");
                //ret_booths=localDBHelper.getBotthlist(distId,latitude,longitude);
                //ret_booths=findNearByPlaces(latitude, longitude);


                    //ret_booths = localDBHelper.getPondsInspectionDetail(pachId);

                if(ret_booths.size()==0) {
                    // AsyncPostData task = new AsyncPostData(distId);
                    //task.execute();

                    AlertDialog.Builder ab = new AlertDialog.Builder(
                            MapActivity.this);
                    ab.setTitle("Message");
                    ab.setMessage("No Sanrachna found near about Your Current Location");
                    ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int whichButton) {
                            dialog.dismiss();
                        }
                    });

                    ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                    ab.show();

                }else {
                    Toast.makeText(MapActivity.this,"Located "+ret_booths.size()+" Sanrachna",Toast.LENGTH_LONG).show();
                    for (int i = 0; i < ret_booths.size(); i++) {

                        if (!ret_booths.get(i).getLatitude().equalsIgnoreCase("NA")) {
                            LatLng latLng = new LatLng(Double.parseDouble(ret_booths.get(i).getLatitude()), Double.parseDouble(ret_booths.get(i).getLongitude()));

                            //  String[] separated = result.get(i).getThananame().split(",");
                            String status = "Thana";
                   /* if (result.get(i).getThanastatus().equalsIgnoreCase("O")) {
                        status = "OutPost";
                    }*/


                            MarkerOptions nearby_marker = new MarkerOptions().position(latLng).title("" + i);

                            // ROSE color icon

                            if (status.equalsIgnoreCase("OutPost")) {
                                //nearby_marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));
                                nearby_marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pollingbooth));
                                // MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_location));
                            } else {
                                //nearby_marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                                nearby_marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.pollingbooth));
                            }
                            usrloc_marker = map.addMarker(nearby_marker);

                        }
                    }
                    if(!SearchText.equalsIgnoreCase("")){
                        ArrayList<LatLng> myFacilities=new ArrayList<>();
                        myFacilities.add(new LatLng(latitude,longitude));
                        SetZoomlevel(myFacilities,map);
                    }
                }
                usrloc_marker1.showInfoWindow();
            }

        }
    }


   /* private boolean isInRange(double currentLat, double currentlongi,double databaseLat, double databaselongi)
    {

        Double myLat= Double.valueOf(currentLat);
        Double myLang= Double.valueOf(currentlongi);

        Log.e("CLAT",""+currentLat);
        Log.e("CLAN",""+currentlongi);
        Log.e("DLAT",""+databaseLat);
        Log.e("DLAN",""+databaselongi);

        Location loc1 = new Location(LocationManager.GPS_PROVIDER) ;//(Double.parseDouble(String.valueOf(currentLat)),Double.parseDouble(String.valueOf(currentlongi)));
        loc1.setLatitude(currentLat);
        loc1.setLongitude(currentlongi);
        Location loc2 = new Location(LocationManager.GPS_PROVIDER);
        loc2.setLatitude(databaseLat);
        loc2.setLongitude(databaselongi);
        float distanceInMeters = loc1.distanceTo(loc2);
        Log.e("DistanceInMeters",""+distanceInMeters);
        if(distanceInMeters <= 6865335)// your given range
            return true;
        else
            return false;
    }*/

    public void getgps() {
        // create class object
        gps = new GPSTracker(MapActivity.this);
        double latitude = 0.0;
        double longitude = 0.0;

        // check if GPS enabled
        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
                 /* latitude = Double.parseDouble("26.4394400000");
            longitude = Double.parseDouble("85.8752400000");*/
            if (latitude > 0.0 && longitude > 0.0) {
                //Log.e("error", latitude + "," + longitude);
                latlon = new LatLng(latitude, latitude);
                loadmap(latitude, longitude);
                map.setInfoWindowAdapter(new boothInfoAdapter(getLayoutInflater()));
            }
        } else {

            gps.showSettingsAlert();
        }
    }

    /*public static ArrayList<thanaInfo> LoadthanaInfos(String dist,String AcId,String Pccode) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        //request.addProperty("mLatitude", lng);
        request.addProperty("DistCode", dist);
        request.addProperty("ACode", AcId);
        request.addProperty("PCCode", Pccode);
        // request.addProperty("",)
        // request.addProperty("house_post_status_code", "7");
        SoapObject res1;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            // envelope.addMapping(NAMESPACE,
            // BoothInfo.REG_CLASS.getSimpleName(), PlantationInfo.REG_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(NAMESPACE + METHOD_NAME, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<thanaInfo> RangeList = new ArrayList<thanaInfo>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    thanaInfo ri = new thanaInfo(final_object);
                    RangeList.add(ri);
                }
            } else
                return RangeList;
        }

        return RangeList;

    }*/
    /* public static ArrayList<thanaInfo> LoadthanaInfos(String lat, String lng) {

        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

        request.addProperty("mLatitude", lng);
        request.addProperty("mLongitude", lat);
        // request.addProperty("house_post_status_code", "7");
        SoapObject res1;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            // envelope.addMapping(NAMESPACE,
            // BoothInfo.REG_CLASS.getSimpleName(), PlantationInfo.REG_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call(NAMESPACE + METHOD_NAME, envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();
        ArrayList<thanaInfo> RangeList = new ArrayList<thanaInfo>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    thanaInfo ri = new thanaInfo(final_object);
                    RangeList.add(ri);
                }
            } else
                return RangeList;
        }

        return RangeList;

    }*/

    @Override
    public void onMyLocationChange(Location location) {
        //getgps();
        usrloc_marker1.showInfoWindow();
        if(i==0){
            latlon = new LatLng(location.getLatitude(), location.getLongitude());
            loadmap(latlon.latitude, latlon.longitude);
            map.setInfoWindowAdapter(new boothInfoAdapter(getLayoutInflater()));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                String LicenseInfo = GooglePlayServicesUtil.getOpenSourceSoftwareLicenseInfo(
                        getApplicationContext());
                AlertDialog.Builder LicenseDialog = new AlertDialog.Builder(MapActivity.this);
                LicenseDialog.setTitle("Legal Notices");
                LicenseDialog.setMessage(LicenseInfo);
                LicenseDialog.show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {

        super.onResume();

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (resultCode == ConnectionResult.SUCCESS){
            /*Toast.makeText(getApplicationContext(),
                    "isGooglePlayServicesAvailable SUCCESS",
                    Toast.LENGTH_LONG).show();*/
        }else{
            GooglePlayServicesUtil.getErrorDialog(resultCode, this, RQS_GooglePlayServices);
        }

    }

    @Override
    public void onMapClick(LatLng point) {

        //Toast.makeText(getApplicationContext(), point.toString(), Toast.LENGTH_LONG).show();
        map.animateCamera(CameraUpdateFactory.newLatLng(point));

        markerClicked = false;

        usrloc_marker1.showInfoWindow();
        // tvDistanceDuration.setVisibility(View.GONE);
    }

    @Override
    public void onMapLongClick(LatLng point) {

        //Toast.makeText(getApplicationContext(),"New marker added@" + point.toString(),Toast.LENGTH_LONG).show();
        i = 1;
        latlon = new LatLng(point.latitude, point.longitude);
        loadmap(latlon.latitude, latlon.longitude);
        map.setInfoWindowAdapter(new boothInfoAdapter(getLayoutInflater()));

        /*map.addMarker(new MarkerOptions()
                .position(point)
                .draggable(true));*/

        markerClicked = false;

        tvDistanceDuration.setVisibility(View.GONE);
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);



    }

    @Override
    public void onMarkerDragStart(Marker marker) {
        //Toast.makeText(getApplicationContext(),"Marker " + marker.getId() + " DragStart",Toast.LENGTH_LONG).show();
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ncrosspin));
    }

    @Override
    public void onMarkerDrag(Marker marker) {
        //Toast.makeText(getApplicationContext(),"Marker " + marker.getId() + " Drag@" + marker.getPosition(),Toast.LENGTH_LONG).show();
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.ncrosspin));
    }

    @Override
    public void onMarkerDragEnd(Marker marker) {
        //Toast.makeText(getApplicationContext(),"Marker " + marker.getId() + " DragEnd",Toast.LENGTH_LONG).show();
        marker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.npin));
        i = 1;
        latlon = new LatLng(marker.getPosition().latitude, marker.getPosition().longitude);
        loadmap(latlon.latitude, latlon.longitude);
        map.setInfoWindowAdapter(new boothInfoAdapter(getLayoutInflater()));
    }




    class boothInfoAdapter implements GoogleMap.InfoWindowAdapter {

        LayoutInflater mInflater;

        public boothInfoAdapter(LayoutInflater i) {
            mInflater = i;
        }

        // This defines the contents within the info window based on the marker
        @Override
        public View getInfoContents(Marker marker) {
            // Getting view from the layout file
            View v = mInflater.inflate(R.layout.custom_thana_details, null);
            // View v = null;
            // Populate fields
            /* if (!marker.equals(usrloc_marker)) {*/
            // if (ret_booths != null && ret_booths.length > 0) {
            // v = mInflater.inflate(R.layout.custom_booth_info, null);
            if (Integer.valueOf(marker.getTitle()) != -1) {
                v = mInflater.inflate(R.layout.custom_thana_details, null);
                PondEntity booth = ret_booths.get(Integer.valueOf(marker.getTitle()));

                if(!booth.getDistName().equalsIgnoreCase("NA")) {
                    // final String[] separated = booth.getThananame().split(",");


                  /*  String mACName = separated[0];
                    String mBoothLoc = separated[2];
                    String mBLOName = separated[3].toUpperCase();
*/
                    TextView ThanaName = (TextView) v.findViewById(R.id.tvACName);
                    ThanaName.setText("जल संरचना");


                    TextView District = (TextView) v.findViewById(R.id.tvlocation);
                    District.setText(booth.getDistName());



                   /* ImageView Type = (ImageView) v.findViewById(R.id.type);
                    if (booth.getThanastatus().trim().equalsIgnoreCase("O")) {
                        Type.setBackgroundResource(R.drawable.op);
                    }else if (booth.getThanastatus().trim().equalsIgnoreCase("T")){
                        Type.setBackgroundResource(R.drawable.ps);
                    }*/



                    TextView thanaNo = (TextView) v.findViewById(R.id.thanano);
                    if (booth.getBlockID().equalsIgnoreCase("NA") ) {
                        thanaNo.setText("NA");
                    }else {
                        thanaNo.setText(booth.getRajswaThanaNumber());
                    }

                    TextView hqNumber = (TextView) v.findViewById(R.id.district_hq_no);
                    if (booth.getPanchayatID().equalsIgnoreCase("NA") ) {
                        hqNumber.setText("NA");
                    } else {
                        hqNumber.setText(booth.getInspectionID());
                    }



                    Location l1=new Location("One");

                    String address="";

                    if(i==1){
                        l1.setLatitude(latlon.latitude);
                        l1.setLongitude(latlon.longitude);

                        address=GetAddress(String.valueOf(latlon.latitude).trim(),String.valueOf(latlon.longitude).trim());

                    }else {
                        l1.setLatitude(gps.getLatitude());
                        l1.setLongitude(gps.getLongitude());

                        address=GetAddress(""+gps.getLatitude(),""+gps.getLongitude());
                    }

                    Location l2=new Location("Two");
                    l2.setLatitude(Double.valueOf( booth.getLatitude().toString()));
                    l2.setLongitude(Double.valueOf( booth.getLongitude().toString()));


                    TextView userLocation = (TextView) v.findViewById(R.id.user_loc);
                    userLocation.setText(address);

                    //String roadDist = getDistanceOnRoad(l1.getLatitude(),l1.getLongitude(),l2.getLatitude(),l2.getLongitude());

                    Button send = (Button) v.findViewById(R.id.textView10);
                   /* send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Toast.makeText(getApplicationContext(), "Msg Sending" + separated[0], Toast.LENGTH_SHORT).show();
                        }
                    });*/

                    LatLng origin = new LatLng(l1.getLatitude(),l1.getLongitude());
                    LatLng dest = new LatLng(l2.getLatitude(),l2.getLongitude());

                    // Getting URL to the Google Directions API
                    String url = getDirectionsUrl(origin, dest);

                    DownloadTask downloadTask = new DownloadTask();

                    // Start downloading json data from Google Directions API
                    downloadTask.execute(url);
                }

            } else if (Integer.valueOf(marker.getTitle()) == -1) {

                String curaddress="";

                if(i==1){
                    curaddress=GetAddress(String.valueOf(latlon.latitude).trim(),String.valueOf(latlon.longitude).trim());

                }else {
                    curaddress=GetAddress(""+gps.getLatitude(),""+gps.getLongitude());
                }

                v = mInflater.inflate(R.layout.user_location_info, null);
                TextView userloc = (TextView) v.findViewById(R.id.tv_userlocation);
                userloc.setText(curaddress);
            }
            // }

            return v;
        }

        // This changes the frame of the info window; returning null uses the
        // default frame.
        @Override
        public View getInfoWindow(Marker marker) {
            return null;
        }
    }





    public boolean onMarkerClick(Marker marker) {
        // TODO Auto-generated method stub
        if (marker.equals(usrloc_marker)) {
            // handle click here
            Log.e("Map clicked", "GoogleMapActivity Marker Clicked");
            // googleMap.setInfoWindowAdapter(null);

            LatLng urloc = marker.getPosition();
            marker.showInfoWindow();


        }
        if (!marker.equals(usrloc_marker)) {

            marker.showInfoWindow();

        }

        return false;

    }

    public static boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected() == true);
    }

    public static void showAlet(final Context context) {


        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setCancelable(false);
        ab.setMessage(Html
                .fromHtml("<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection..\nTo Turn ON Network Connection Press Yes Button ..</font>"));
        ab.setPositiveButton("Turn On Network Connection",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {
                        GlobalVariables.isOffline = false;
                        Intent I = new Intent(
                                android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                        context.startActivity(I);
                    }
                });
        ab.setNegativeButton("Cancel ",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog,
                                        int whichButton) {

                        /* GlobalVariables.isOffline = true;*/
                    }
                });

        ab.create().getWindow().getAttributes().windowAnimations = R.style.AppTheme_Dark_Dialog;

        ab.show();


    }

    @Override
    public void onInfoWindowClick(Marker marker) {
       /* if (Integer.valueOf(marker.getTitle()) == -1) {

        }  else {


               *//* SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(ret_booths.get(Integer.valueOf(marker.getTitle())).getPsMno(), null, "Help me out. My Latitude = " + gps.getLatitude() + " and Longitude = " + gps.getLongitude(), null, null);
                Toast.makeText(getBaseContext(),
                        "message sending on" + ret_booths.get(Integer.valueOf(marker.getTitle())).getThananame(),
                        Toast.LENGTH_SHORT).show();*//*



            // Create custom dialog object
            final Dialog dialog = new Dialog(MapActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            // Include dialog.xml file
            dialog.setContentView(R.layout.custom_dialog_layout);
            // Set dialog title
            dialog.setTitle("Call To ...");

           // set values for custom dialog components - text, image and button
            final TextView psLLNum = (TextView) dialog.findViewById(R.id.ps_lno);
            final TextView hqLLNum = (TextView) dialog.findViewById(R.id.hq_llno);
            final TextView spNum = (TextView) dialog.findViewById(R.id.sp_no);

            LinearLayout llPsLLNum = (LinearLayout) dialog.findViewById(R.id.ll_ps_landline);
            LinearLayout llHqLLNum = (LinearLayout) dialog.findViewById(R.id.ll_hq_landline);
            LinearLayout llSpNum = (LinearLayout) dialog.findViewById(R.id.ll_sp_no);

           *//* psLLNum.setText(ret_booths.get(Integer.valueOf(marker.getTitle())).getPsLno());
            hqLLNum.setText(ret_booths.get(Integer.valueOf(marker.getTitle())).getCroomlandlineno());
            spNum.setText(ret_booths.get(Integer.valueOf(marker.getTitle())).getSplandlineno());


            if(ret_booths.get(Integer.valueOf(marker.getTitle())).getPsLno().equalsIgnoreCase("NA")){
                llPsLLNum.setVisibility(View.GONE);
            }

            if(ret_booths.get(Integer.valueOf(marker.getTitle())).getCroomlandlineno().equalsIgnoreCase("NA")){
                llHqLLNum.setVisibility(View.GONE);
            }
            if(ret_booths.get(Integer.valueOf(marker.getTitle())).getSplandlineno().equalsIgnoreCase("NA")){
                llSpNum.setVisibility(View.GONE);
            }*//*



         *//*llPsLLNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callTheNumber(psLLNum.getText().toString().trim());
                }
            });


            llHqLLNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callTheNumber(hqLLNum.getText().toString().trim());
                }
            });

            llSpNum.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callTheNumber(spNum.getText().toString().trim());
                }
            });*//*


            dialog.show();


        }*/

    }

    private void callTheNumber(String number) {

        if(isTelephonyEnabled()==true) {

            try {
               /* Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setPackage("com.android.server.telecom");
                callIntent.setData(Uri.parse("tel:" + number));
                startActivity(callIntent);*/
            } catch (Exception e) {
                callTheNumberInLowerVersion(number);
            }
        }

        else
        {
            Toast.makeText(MapActivity.this, "Calling Features are not Supported on Your Device", Toast.LENGTH_LONG).show();
        }
    }

    private void callTheNumberInLowerVersion(String number) {

       /* try {
            Intent callIntent = new Intent(Intent.ACTION_CALL);
            callIntent.setData(Uri.parse("tel:" + number));
            startActivity(callIntent);
        }catch (Exception e) {
            Toast.makeText(MapActivity.this, "Call Service not available", Toast.LENGTH_LONG).show();
        }*/
    }

    private boolean isTelephonyEnabled(){
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        return tm != null && tm.getSimState()==TelephonyManager.SIM_STATE_READY;
    }

    public String GetAddress(String lat,String lng)
    {
        String ret="";
        Geocoder geocoder =new Geocoder(getBaseContext(), Locale.getDefault());

        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(Double.parseDouble(lat),Double.parseDouble(lng), 10);
            if(addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");
                for(int i=0; i<returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append(", ");
                }
                if(addresses.isEmpty())
                {
                    Log.e("addresses.isEmpty()", "ys");
                }
                else
                {
                    Log.e("addresses.isEmpty()", "no");
                    //publishProgress("Address found " + addresses.size());
                }
                ret = strReturnedAddress.toString();
            }
            else{
                ret = "No Address returned!";
            }
        } catch (IOException e1) {
            Log.e("LocationSampleActivity", "IO Exception in getFromLocation()");
            if(uradd.isEmpty())
            {
                //get address from other source
                Log.e("getAddress", "calling this function");
                //getAddress_From_Other_Location(lat,lng);
            }

        } catch (IllegalArgumentException e2) {
            // Error message to post in the log
            String errorString = "Illegal arguments " +lat+", "+lng+ " passed to address service";
            Log.e("LocationSampleActivity", errorString);
        }

        // If the reverse geocode returned an address

        String add="n/a";
        uradd.clear();

        if (addresses != null && addresses.size() > 0) {
            add=addresses.get(0).getAddressLine(0)+","
                    +addresses.get(0).getSubAdminArea()+","
                    +addresses.get(0).getCountryName();

            uradd.add(add);//0

            add+="City: " + addresses.get(0).getLocality();
            uradd.add(addresses.get(0).getLocality());//1
            _city=addresses.get(0).getLocality();

            add+="State: " + addresses.get(0).getAdminArea();
            uradd.add(addresses.get(0).getAdminArea()); //2
            _state=addresses.get(0).getAdminArea();

            uradd.add(addresses.get(0).getCountryName());//3
            _country=addresses.get(0).getCountryName();

            // Get the first address
            for(int i=0 ;i<addresses.size();i++){
                address = addresses.get(i);
                if(address.getPostalCode()!=null){
                    add+="ZIP: " + address.getPostalCode();
                    uradd.add(address.getPostalCode()); //4
                    _zip=address.getPostalCode();
                    break;
                }
            }
        }
        try {
            //  ret=address.getAdminArea()+_city+"-" +_state+"-"+_country+"-"+_zip;
            ret=add;
        }catch (Exception e){

        }

        // Log.e("Addresses", ret);
        //Log.e("InSIde",_city+"-" +_state+"-"+_country+"-"+_zip);
        //call slider activity
        return ret;
    }



    private String getDirectionsUrl(LatLng origin,LatLng dest){

        // Origin of route
        String str_origin = "origin="+origin.latitude+","+origin.longitude;

        // Destination of route
        String str_dest = "destination="+dest.latitude+","+dest.longitude;

        // Sensor enabled
        String sensor = "sensor=true";

        // Building the parameters to the web service
        String parameters = str_origin+"&"+str_dest+"&"+sensor;

        // Output format
        String output = "json";

        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/"+output+"?"+parameters+"&key=AIzaSyAByMdUqH3CBdLJTEg40wcATfJ8LGfNqUI";

        return url;
    }
    /** A method to download json data from url */
    private String downloadUrl(String strUrl) throws IOException{
        String data = "";
        InputStream iStream = null;
        HttpURLConnection urlConnection = null;
        try{
            URL url = new URL(strUrl);

            // Creating an http connection to communicate with url
            urlConnection = (HttpURLConnection) url.openConnection();

            // Connecting to url
            urlConnection.connect();

            // Reading data from url
            iStream = urlConnection.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(iStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
            while( ( line = br.readLine()) != null){
                sb.append(line);
            }

            data = sb.toString();

            br.close();

        }catch(Exception e){
            Log.e("Exception  downloading", e.toString());
        }finally{
            if(iStream!= null) {
                iStream.close();
            }
            if(urlConnection!= null) {
                urlConnection.disconnect();
            }
        }
        return data;
    }

    // Fetches data from url passed
    private class DownloadTask extends AsyncTask<String, Void, String>{

        // Downloading data in non-ui thread
        @Override
        protected String doInBackground(String... url) {

            // For storing data from web service
            String data = "";

            try{
                // Fetching the data from web service
                data = downloadUrl(url[0]);
            }catch(Exception e){
                Log.e("Background Task",e.toString());
            }
            return data;        }

        // Executes in UI thread, after the execution of
        // doInBackground()
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            ParserTask parserTask = new ParserTask();

            // Invokes the thread for parsing the JSON data
            parserTask.execute(result);
        }
    }

    /** A class to parse the Google Places in JSON format */
    private class ParserTask extends AsyncTask<String, Integer, List<List<HashMap<String,String>>> >{

        // Parsing the data in non-ui thread
        @Override
        protected List<List<HashMap<String, String>>> doInBackground(String... jsonData) {

            JSONObject jObject;
            List<List<HashMap<String, String>>> routes = null;

            try{
                jObject = new JSONObject(jsonData[0]);
                DirectionsJSONParser parser = new DirectionsJSONParser();

                // Starts parsing data
                routes = parser.parse(jObject);
            }catch(Exception e){
                Log.e("ParserTask Exception", e.toString());
            }
            return routes;
        }

        // Executes in UI thread, after the parsing process
        @Override
        protected void onPostExecute(List<List<HashMap<String, String>>> result) {
            if (result != null) {

                if (line != null) {
                    line.remove();
                }
                ArrayList<LatLng> points = null;
                PolylineOptions lineOptions = null;
                MarkerOptions markerOptions = new MarkerOptions();
                String distance = "";
                String duration = "";

                if (result.size() < 1) {
                    Toast.makeText(getBaseContext(), "No Points", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Traversing through all the routes
                for (int i = 0; i < result.size(); i++) {
                    points = new ArrayList<LatLng>();
                    lineOptions = new PolylineOptions();

                    // Fetching i-th route
                    List<HashMap<String, String>> path = result.get(i);

                    // Fetching all the points in i-th route
                    for (int j = 0; j < path.size(); j++) {
                        HashMap<String, String> point = path.get(j);

                        if (j == 0) {
                            // Get distance from the list
                            distance = (String) point.get("distance");
                            continue;
                        } else if (j == 1) { // Get duration from the list
                            duration = (String) point.get("duration");
                            continue;
                        }

                        double lat = Double.parseDouble(point.get("lat"));
                        double lng = Double.parseDouble(point.get("lng"));
                        LatLng position = new LatLng(lat, lng);

                        points.add(position);
                    }

                    // Adding all the points in the route to LineOptions
                    lineOptions.addAll(points);
                    lineOptions.width(7);
                    lineOptions.color(getResources().getColor(R.color.back));

                }

                tvDistanceDuration.setText("Distance:" + distance + ", Duration:" + duration);
                tvDistanceDuration.setVisibility(View.VISIBLE);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);


                // Drawing polyline in the Google Map for the i-th route
                line = map.addPolyline(lineOptions);
            }
        }
    }


}

