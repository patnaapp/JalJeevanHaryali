package bih.in.jaljeevanharyali.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;


@SuppressLint("NewApi")
public class OtherSchemePhotoActivity extends Activity implements OnClickListener {
    private final static int CAMERA_PIC = 99;
    ImageView img1, img2, img3, img4,img5;
    Button btnOk;

    int ThumbnailSize = 0,phaseType;
    int exitCount = 1;
    String PID = "0";
    String AREA = "";
    //ArrayList<Intent> imageData;
    Intent imageData1,imageData2,imageData3,imageData4,imageData5;
    String isOpen = "",purpose="",remark;
    TextView text_p1;
    LinearLayout linearLayout2;
    RelativeLayout re_p2;
    Boolean hasImg1=false, hasImg2=false;

    DataBaseHelper dataBaseHelper;
    UserDetails userInfo;
    String consumerNo,consumerBill;
    String columnInspected,columnInspectedBy,columnInspectedDate,columnPhoto1,columnPhoto2,columnLat,columnLong,columnremark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_multiple_photo);

        dataBaseHelper=new DataBaseHelper(this);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dataBaseHelper.getUserDetails(username, password);

        PID = String.valueOf(getIntent().getIntExtra("KEY_PID", 0));
        phaseType = getIntent().getIntExtra("PhaseType", 0);
        isOpen = getIntent().getStringExtra("isOpen");
        remark = getIntent().getStringExtra("remark");
        purpose = getIntent().getStringExtra("pupose");

        consumerNo = getIntent().getStringExtra("consumerNo");
        consumerBill = getIntent().getStringExtra("consumerBill");

        if (PID == null) {
            PID = "0";
        }

        switch (phaseType){
            case 1:
                columnInspected="IsPhase1Inspected";
                columnInspectedBy="IsPhase1InspBy";
                columnInspectedDate="IsPhase1InspDate";
                columnPhoto1="IsPhase1InspPhoto1";
                columnPhoto2="IsPhase1InspPhoto2";
                columnLat="IsPhase1InspLatitude";
                columnLong="IsPhase1InspLongitude";
                columnremark="IsPhase1InspRemarks";
                break;
            case 2:
                columnInspected="IsPhase2Inspected";
                columnInspectedBy="IsPhase2InspBy";
                columnInspectedDate="IsPhase2InspDate";
                columnPhoto1="IsPhase2InspPhoto1";
                columnPhoto2="IsPhase2InspPhoto2";
                columnLat="IsPhase2InspLatitude";
                columnLong="IsPhase2InspLongitude";
                columnremark="IsPhase2InspRemarks";
                break;
            case 3:
                columnInspected="IsPhase3Inspected";
                columnInspectedBy="IsPhase3InspBy";
                columnInspectedDate="IsPhase3InspDate";
                columnPhoto1="IsPhase3InspPhoto1";
                columnPhoto2="IsPhase3InspPhoto2";
                columnLat="IsPhase3InspLatitude";
                columnLong="IsPhase3InspLongitude";
                columnremark="IsPhase3InspRemarks";
                break;
        }

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        img1 = (ImageView) findViewById(R.id.imageButton1);
        img2 = (ImageView) findViewById(R.id.imageButton2);
        img3 = (ImageView) findViewById(R.id.imageButton3);
        img4 = (ImageView) findViewById(R.id.imageButton4);
        img5 = (ImageView) findViewById(R.id.imageButton5);
        linearLayout2 = (LinearLayout) findViewById(R.id.linearLayout2);
        re_p2 = (RelativeLayout) findViewById(R.id.re_p2);
        text_p1 = (TextView) findViewById(R.id.text_p1);

        btnOk = (Button) findViewById(R.id.btnOk);

        btnOk.setOnClickListener(this);
        if (!purpose.equalsIgnoreCase("edit"))
        {
            btnOk.setEnabled(true);
            btnOk.setBackgroundResource(R.drawable.buttonbackshape1);
        }

        if (displaymetrics.widthPixels < displaymetrics.heightPixels) {
            ThumbnailSize = displaymetrics.widthPixels / 2;
            //img1.getLayoutParams().height = ThumbnailSize;
            //img3.getLayoutParams().height = ThumbnailSize;


        } else {

            ThumbnailSize = displaymetrics.widthPixels / 4;
            img1.getLayoutParams().height = img2.getLayoutParams().height = img3
                    .getLayoutParams().height = img4.getLayoutParams().height = img5.getLayoutParams().height = ThumbnailSize;

        }
        if (isOpen.equals("2")) {
            text_p1.setText("बंद केंद्र की फोटो");
            linearLayout2.setVisibility(View.GONE);
            re_p2.setVisibility(View.GONE);
        }

        DataBaseHelper placeData = new DataBaseHelper(this);
        SQLiteDatabase db = placeData.getReadableDatabase();
        //int HIDID = getIntent().getIntExtra("KEY_HIDID", 0);

        Cursor cursor = db
                .rawQuery(
                        "SELECT "+columnPhoto1+","+columnPhoto2+",Photo3,Photo4 FROM OtherDept_Of_Rural_Dev_Dept where id =?",
                        new String[]{String.valueOf(PID)});

        Log.e("PID  ", PID);


        if (cursor.moveToNext()) {

            if (!cursor.isNull(0)) {

                byte[] imgData = cursor.getBlob(cursor.getColumnIndex(columnPhoto1));
                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                        ThumbnailSize, ThumbnailSize));
                img1.setOnClickListener(this);
                hasImg1 = true;
            } else {
                img1.setOnClickListener(this);
                img2.setEnabled(false);
                img3.setEnabled(false);
                img4.setEnabled(false);
                img5.setEnabled(false);

            }

            if (!cursor.isNull(1)) {
                byte[] imgData = cursor.getBlob(cursor.getColumnIndex(columnPhoto2));

                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                        imgData.length);
                img2.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                        ThumbnailSize, ThumbnailSize));
                img2.setOnClickListener(this);
                hasImg2 = true;
                btnOk.setEnabled(true);
                btnOk.setBackgroundResource(R.drawable.buttonbackshape);
            } else {
                img2.setOnClickListener(this);
                img5.setOnClickListener(this);
                img3.setEnabled(false);
                img4.setEnabled(false);

            }
//            if (!cursor.isNull(2)) {
//                byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo3"));
//
//                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
//                        imgData.length);
//                img3.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
//                        ThumbnailSize, ThumbnailSize));
//                img3.setOnClickListener(this);
//            } else {
//                img3.setOnClickListener(this);
//                img4.setEnabled(false);
//            }
//            if (!cursor.isNull(3)) {
//                byte[] imgData = cursor.getBlob(cursor.getColumnIndex("Photo4"));
//
//                Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
//                        imgData.length);
//                img4.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
//                        ThumbnailSize, ThumbnailSize));
//                img4.setOnClickListener(this);
//            } else {
//                img4.setOnClickListener(this);
//
//            }

        }
        cursor.close();
        db.close();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_multiple_photo, menu);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.equals(btnOk)) {
            if (!hasImg1 || !hasImg2 /*&& imageData3==null&& imageData4==null&& imageData5==null&& purpose.equals("edit")*/){
                Toast.makeText(this, "कृपया दोनों फोटो लें", Toast.LENGTH_SHORT).show();
                //finish();
            }else {
                saveImage();
                Intent iUserHome = new Intent(getApplicationContext(),
                        DashboardActivity.class);
                iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(iUserHome);
            }
        } else {
            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                buildAlertMessageNoGps();
            }
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

                Intent iCamera = new Intent(getApplicationContext(),
                        CameraActivity.class);
                if (view.equals(img1))
                    iCamera.putExtra("KEY_PIC", "1");
                else if (view.equals(img2))
                    iCamera.putExtra("KEY_PIC", "2");
                else if (view.equals(img3))
                    iCamera.putExtra("KEY_PIC", "3");
                else if (view.equals(img4))
                    iCamera.putExtra("KEY_PIC", "4");
                else if (view.equals(img5))
                    iCamera.putExtra("KEY_PIC", "5");
                startActivityForResult(iCamera, CAMERA_PIC);
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");


                    //imageData.add(data);
                    if (isOpen.equals("2")) btnOk.setEnabled(true);

                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            imageData1=data;
                            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
                                    imgData.length);
                            img1.setScaleType(ScaleType.FIT_XY);
                            img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
                                    500, 500));
                            //img1.setOnClickListener(null);
                            hasImg1 = true;
//                            btnOk.setEnabled(true);
//                            btnOk.setBackgroundResource(R.drawable.buttonbackshape);
                            if (isOpen.equals("2")) {
                                btnOk.setEnabled(true);
                                btnOk.setBackgroundResource(R.drawable.buttonbackshape);
                            } else img2.setEnabled(true);
                            break;

                        case 2:
                            imageData2=data;
                            img2.setScaleType(ScaleType.FIT_XY);
                            img2.setImageBitmap(Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            //img2.setOnClickListener(null);
                            img3.setEnabled(true);
                            hasImg2 = true;
                            btnOk.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonbackshape);
                            break;

                        case 3:
                            imageData3=data;
                            img3.setScaleType(ScaleType.FIT_XY);
                            img3.setImageBitmap(Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            //img3.setOnClickListener(null);
                            img4.setEnabled(true);
                            btnOk.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonbackshape);
                            break;

                        case 4:
                            imageData4=data;
                            img4.setScaleType(ScaleType.FIT_XY);
                            img4.setImageBitmap(Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500,
                                    500));
                            img5.setEnabled(true);
                            btnOk.setEnabled(true);
                            btnOk.setBackgroundResource(R.drawable.buttonbackshape);
                            break;
                        case 5:
                            imageData5=data;
                            img5.setScaleType(ScaleType.FIT_XY);
                            img5.setImageBitmap(Utiilties.GenerateThumbnail(
                                    BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500,
                                    500));
                            //img4.setOnClickListener(null);
                            break;

                    }


                }

        }

    }

    public String getAppVersion(){
        try {

            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            return version;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }


    private void saveImage() {

        int i = 0;
        DataBaseHelper placeData = new DataBaseHelper(
                OtherSchemePhotoActivity.this);
        SQLiteDatabase db = placeData.getWritableDatabase();
        for (i = 0; i < 5; i++) {
            ContentValues values = new ContentValues();
            String[] whereArgs;
            byte[] imgData;
            switch (i) {
                case 0:
                    //GPSTime
                    if (imageData1!=null) {
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
                        imgData = imageData1.getByteArrayExtra("CapturedImage");
                        values.put(columnInspected, "Y");
                        values.put("isUpdated", "1");
                        values.put(columnInspectedBy, userInfo.getUserID());
                        values.put(columnInspectedDate, currentDate);
                        values.put(columnremark, remark);
                        values.put("App_Version", getAppVersion());
                        values.put(columnPhoto1, imgData);
                        values.put(columnLat,String.valueOf(imageData1.getStringExtra("Lat")));
                        values.put(columnLong,String.valueOf(imageData1.getStringExtra("Lng")));

                        values.put("ConsumerNo", consumerNo);
                        values.put("ConsumrBill", consumerBill);

                        Log.e("lat",String.valueOf(imageData1.getStringExtra("Lat")));
                        Log.e("LNG",String.valueOf(imageData1.getStringExtra("Lng")));
                      /*  values.put("UploadDate",
                                String.valueOf(imageData1.getStringExtra("GPSTime")));*/
                        whereArgs = new String[]{String.valueOf(PID)};
                     long c= db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=?", whereArgs);
                        btnOk.setEnabled(true);
                     if(c>0)
                     {
                         Log.e("IMAGEE","Updated");
                     }
                     else
                     {
                         Log.e("IMAGEE","not updttad");
                     }
                    }else{
                        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

                        values.put(columnInspected, "Y");
                        values.put("isUpdated", "1");
                        values.put(columnInspectedBy, userInfo.getUserID());
                        values.put(columnInspectedDate, currentDate);
                        values.put(columnremark, remark);
                        values.put("App_Version", getAppVersion());

                        whereArgs = new String[]{String.valueOf(PID)};
                        long c= db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=?", whereArgs);
                        btnOk.setEnabled(true);
                        if(c>0)
                        {
                            Log.e("Data Withoud Image","Updated");
                        }
                        else
                        {
                            Log.e("Data Withoud Image","not updttad");
                        }
                        //Log.e("Failed","not updttad");
                    }
                    break;
                case 1:
                    if (imageData2!=null) {
                        imgData = imageData2.getByteArrayExtra("CapturedImage");

                        values.put(columnPhoto2, imgData);
                        whereArgs = new String[]{String.valueOf(PID)};
                        db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=?", whereArgs);
                    }
                    break;
                case 2:
                    if (imageData3!=null) {
                        imgData = imageData3.getByteArrayExtra("CapturedImage");
                        values.put("Photo3", imgData);
                        whereArgs = new String[]{String.valueOf(PID)};
                        long c1= db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=?", whereArgs);
                        if(c1>0)
                        {
                            Log.e("IMAGEE","Updated");
                        }
                        else
                        {
                            Log.e("IMAGEE","not updttad");
                        }
                    }
                    break;
                case 3:
                    if (imageData4!=null) {
                        imgData = imageData4.getByteArrayExtra("CapturedImage");

                        values.put("Photo4", imgData);
                        whereArgs = new String[]{String.valueOf(PID)};
                        db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=?", whereArgs);
                    }
                    break;


            }
        }

        db.close();
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("GPS");
        builder.setMessage("GPS is turned OFF...\nDo U Want Turn On GPS..")
//		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
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

    public void onBackPressed() {
        if (purpose.equalsIgnoreCase("new")){
            if (exitCount < 4){
                Toast.makeText(this, "कृपया दोनों फोटो लें अन्यथा स्थल का विवरण पूर्ण नहीं होगा ("+exitCount+")", Toast.LENGTH_SHORT).show();
                exitCount++;
            }else{
                finish();
            }
        }else{
            finish();
        }
    }
}
