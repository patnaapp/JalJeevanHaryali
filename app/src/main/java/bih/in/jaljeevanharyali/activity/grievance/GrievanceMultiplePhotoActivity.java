package bih.in.jaljeevanharyali.activity.grievance;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.CameraActivity;
import bih.in.jaljeevanharyali.entity.GrievanceEntryDetail;
import bih.in.jaljeevanharyali.entity.UploadComplainEntity;
import bih.in.jaljeevanharyali.utility.CommonPref;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class GrievanceMultiplePhotoActivity extends Activity implements OnClickListener {
    private final static int CAMERA_PIC = 99;
    ImageView img1, img2, img3, img4;
    Button btnHome;
    int ThumbnailSize = 0;
    String id = "0";
    String WorkStatus = "";
    String AREA = "";

    LinearLayout lntxt12,lntxt34;
    String isIMGOneExist,isIMGTwoExist,isIMGThreeExist,isIMGFourExist;
    String  tableName = "ComplainData";
    String tableName1,_SchemeCode,mobile;
    String mobilenum;

    // UploadComplainEntity complainInfo;
    GrievanceEntryDetail complainInfo;
    String complain="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_photo3);
        //complainInfo = (UploadComplainEntity)getIntent().getSerializableExtra("data");
        complainInfo = (GrievanceEntryDetail)getIntent().getSerializableExtra("data");
        complain=getIntent().getStringExtra("remarks");

        //mobilenum= complainInfo.getMobileNo();
        mobilenum= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");


        isIMGOneExist=isIMGTwoExist=isIMGThreeExist=isIMGFourExist="N";

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        img1 = (ImageView) findViewById(R.id.imageButton1);
        img2 = (ImageView) findViewById(R.id.imageButton2);
        img3 = (ImageView) findViewById(R.id.imageButton3);
        img4 = (ImageView) findViewById(R.id.imageButton4);
        lntxt12 = findViewById(R.id.lntxt12);
        lntxt34 = findViewById(R.id.lntxt34);
        btnHome = (Button) findViewById(R.id.btnOk);
        img3.setOnClickListener(this);
        img4.setOnClickListener(this);
        btnHome.setOnClickListener(this);

//        if(getIntent().hasExtra("VIEW"))
//        {
//            btnHome.setText("होम स्क्रीन पर जायें");
//            if (_SchemeCode.equalsIgnoreCase("1")) {
//                tableName = "ReportPayJaltbl";
//            }
//            if (_SchemeCode.equalsIgnoreCase("2")) {
//                tableName = "ReportGaliNali";
//                lntxt12.setVisibility(View.GONE);
//                lntxt34.setVisibility(View.GONE);
//            }
//        }
//        else {
//            if (_SchemeCode.equalsIgnoreCase("1")) {
//                tableName = "PayJaltbl";
//            }
//            if (_SchemeCode.equalsIgnoreCase("2")) {
//                tableName = "GaliNali";
//                lntxt12.setVisibility(View.GONE);
//                lntxt34.setVisibility(View.GONE);
//            }
//        }
        id = getIntent().getStringExtra("ID");
        // mobile= getIntent().getStringExtra("mobilenoumner");
        mobile= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("uid", "");

        if (id == null) {
            id = "0";
        }

        if (displaymetrics.widthPixels < displaymetrics.heightPixels)
        {
            ThumbnailSize = displaymetrics.widthPixels / 2;
            img1.getLayoutParams().height = ThumbnailSize;
            img3.getLayoutParams().height = ThumbnailSize;

        }
        else
        {
            ThumbnailSize = displaymetrics.widthPixels / 4;
            img1.getLayoutParams().height = img2.getLayoutParams().height = img3
                    .getLayoutParams().height = img4.getLayoutParams().height = ThumbnailSize;

        }

//        DataBaseHelper placeData = new DataBaseHelper(this);
//        SQLiteDatabase db = placeData.getReadableDatabase();
//        // int HIDID = getIntent().getIntExtra("KEY_HIDID", 0);
//        Cursor cursor=null;
//        cursor = db
//                .rawQuery(
//                        "SELECT Photofirst, PhotoSecond, PhotoThird,PhotoForth  FROM " + tableName + " where id=?",
//                        new String[]{String.valueOf(id)});
//
//        if (cursor.moveToNext()) {
//            if(getIntent().hasExtra("VIEW"))
//            {
//                //userphoto=userDetails.getUserPhoto();
//                if(!cursor.isNull(0)){
//                    if(!cursor.getString(0).equalsIgnoreCase("N")) {
//                        byte[] decodedString = Base64.decode(cursor.getString(0), Base64.DEFAULT);
//
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                        img1.setImageBitmap(decodedByte);
//                    }
//                }
//                if(!cursor.isNull(1)){
//
//                    if(!cursor.getString(1).equalsIgnoreCase("N")) {
//                        byte[] decodedString = Base64.decode(cursor.getString(1), Base64.DEFAULT);
//
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                        img2.setImageBitmap(decodedByte);
//                    }
//                }
//                if(!cursor.isNull(2)){
//
//                    if(!cursor.getString(2).equalsIgnoreCase("N")) {
//                        byte[] decodedString = Base64.decode(cursor.getString(2), Base64.DEFAULT);
//
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                        img3.setImageBitmap(decodedByte);
//                    }
//
//                }
//                if(!cursor.isNull(3)){
//
//
//                    if(!cursor.getString(3).equalsIgnoreCase("N")) {
//                        byte[] decodedString = Base64.decode(cursor.getString(3), Base64.DEFAULT);
//
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//                        img4.setImageBitmap(decodedByte);
//                    }
//                }
//            }
//            else
//            {
//                if (!cursor.isNull(0)) {
//
//                    byte[] imgData = cursor.getBlob(0);
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
//                            imgData.length);
//                    img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp,ThumbnailSize, ThumbnailSize));
//                    img1.setOnClickListener(null);
//                    isIMGOneExist="Y";
//                } else {
//                    img1.setOnClickListener(this);
//                    img2.setEnabled(false);
//                    img3.setEnabled(false);
//                    img4.setEnabled(false);
//                }
//
//                if (!cursor.isNull(1)) {
//                    byte[] imgData = cursor.getBlob(1);
//
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
//                            imgData.length);
//                    img2.setImageBitmap(Utiilties.GenerateThumbnail(bmp,ThumbnailSize, ThumbnailSize));
//                    img2.setOnClickListener(null);
//                    isIMGTwoExist="Y";
//
//                } else {
//                    img2.setOnClickListener(this);
//                    img3.setEnabled(false);
//                    img4.setEnabled(false);
//                }
//
//                if (!cursor.isNull(2)) {
//                    byte[] imgData = cursor.getBlob(2);
//
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
//                            imgData.length);
//                    img3.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
//                            ThumbnailSize, ThumbnailSize));
//                    img3.setOnClickListener(null);
//                    isIMGThreeExist="Y";
//
//                } else {
//                    img3.setOnClickListener(this);
//                    img4.setEnabled(false);
//                }
//
//                if (!cursor.isNull(3)) {
//                    byte[] imgData = cursor.getBlob(3);
//
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,
//                            imgData.length);
//                    img4.setImageBitmap(Utiilties.GenerateThumbnail(bmp,
//                            ThumbnailSize, ThumbnailSize));
//                    img4.setOnClickListener(null);
//                    isIMGFourExist="Y";
//
//                } else
//                    img4.setOnClickListener(this);
//            }
//        }
//        cursor.close();
//        db.close();
//        if(getIntent().hasExtra("VIEW"))
//        {
//            img1.setOnClickListener(null);
//            img2.setOnClickListener(null);
//            img3.setOnClickListener(null);
//            img4.setOnClickListener(null);
        //}

//		if(getIntent().hasExtra("EDIT"))
//		{
//			if(getIntent().getStringExtra("EDIT").equalsIgnoreCase("yes"))
//			{
//				img1.setOnClickListener(this);
//				img2.setOnClickListener(this);
//				img3.setOnClickListener(this);
//				img4.setOnClickListener(this);
//
//				img1.setEnabled(true);
//				img2.setEnabled(true);
//				img3.setEnabled(true);
//				img4.setEnabled(true);
//			}
//		}


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.activity_multiple_photo, menu);
        return true;
    }

    @Override
    public void onClick(View view) {

        if (view.equals(btnHome)) {
            if(getIntent().hasExtra("VIEW"))
            {
                finish();
            }
            else {
                if (isIMGOneExist.equalsIgnoreCase("Y")) {

                    if (Utiilties.isOnline(GrievanceMultiplePhotoActivity.this)) {

                        AlertDialog.Builder ab = new AlertDialog.Builder(
                                GrievanceMultiplePhotoActivity.this);
                        ab.setTitle("अपलोड  !");
                        ab.setMessage("क्या आप रिकॉर्ड अपलोड करना चाहते हैं  ?");
                        ab.setPositiveButton("[ हाँ ]", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();

                                getcontracterlabphoto();
                            }
                        });

                        ab.setNegativeButton("[ नहीं ]", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();

                            }
                        });

                        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
                        ab.show();

                    } else

                    {
                        Toast.makeText(GrievanceMultiplePhotoActivity.this,
                                " No Internet connection ! \n Please check your internet connectivity.",
                                Toast.LENGTH_LONG).show();
                    }
//                    Intent iUserHome = new Intent(getApplicationContext(), ComplainActivity.class);
//                    iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    finish();
//                    startActivity(iUserHome);
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    //   builder.setIcon(R.drawable.front_camera);
                    builder.setTitle(" फोटो ?");
                    builder.setMessage("कृपया कम से कम एक फोटो ले ")
                            .setCancelable(false)
                            .setPositiveButton("[ ठीक ]", new DialogInterface.OnClickListener() {
                                public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                                    dialog.dismiss();
                                    dialog.cancel();
                                }
                            });
                    final AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        } else {

            final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


                buildAlertMessageNoGps();
            }
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

                Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
                // iCamera.putExtra("KEY_WORKID",
                // getIntent().getStringExtra("KEY_WORKID"));
                if (view.equals(img1))
                    iCamera.putExtra("KEY_PIC", "1");

                else if (view.equals(img2))
                    iCamera.putExtra("KEY_PIC", "2");
                else if (view.equals(img3))
                    iCamera.putExtra("KEY_PIC", "3");
                else if (view.equals(img4))
                    iCamera.putExtra("KEY_PIC", "4");

                startActivityForResult(iCamera, CAMERA_PIC);

            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)

    {

        switch (requestCode) {

            case CAMERA_PIC:

                if (resultCode == RESULT_OK) {

                    byte[] imgData = data.getByteArrayExtra("CapturedImage");

                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            complainInfo.setPhoto1(encodedImg1);
                            complainInfo.setLatitude(String.valueOf(data.getStringExtra("Lat")));
                            complainInfo.setLongitude(String.valueOf(data.getStringExtra("Lng")));

                            img1.setImageBitmap(Utiilties.GenerateThumbnail(bmp, ThumbnailSize, ThumbnailSize));
                            img1.setOnClickListener(null);
                            img2.setEnabled(true);
                            isIMGOneExist = "Y";
                            break;
                        case 2:
                            complainInfo.setPhoto2(encodedImg1);

                            img2.setImageBitmap(Utiilties.GenerateThumbnail(bmp, ThumbnailSize,ThumbnailSize));
                            img2.setOnClickListener(null);
                            img3.setEnabled(true);
                            isIMGTwoExist="Y";
                            break;
                        case 3:
                            complainInfo.setPhoto3(encodedImg1);

                            img3.setImageBitmap(Utiilties.GenerateThumbnail(bmp, ThumbnailSize,ThumbnailSize));
                            img3.setOnClickListener(null);
                            img4.setEnabled(true);
                            isIMGThreeExist="Y";
                            break;
                        case 4:
                            complainInfo.setPhoto4(encodedImg1);

                            img4.setImageBitmap(Utiilties.GenerateThumbnail(bmp, ThumbnailSize,ThumbnailSize));
                            img4.setOnClickListener(null);
                            isIMGFourExist="Y";
                            break;
                    }
                    Toast.makeText(getApplicationContext(), "इमेजिस सफलतापूर्वक सहेजी गईं", Toast.LENGTH_LONG).show();
                }

        }

    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //builder.setIcon(R.drawable.bihgov);
        builder.setTitle("GPS ?");
        builder.setMessage("(GPS)जीपीएस बंद है\nस्थान के अक्षांश और देशांतर प्राप्त करने के लिए कृपया जीपीएस चालू करें")
//		builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("[(GPS) जीपीएस चालू करे ]", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("[ बंद करें ]", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }
    public void btn_complainUpload(View view){

        if (Utiilties.isOnline(GrievanceMultiplePhotoActivity.this)) {

            AlertDialog.Builder ab = new AlertDialog.Builder(
                    GrievanceMultiplePhotoActivity.this);
            ab.setTitle("अपलोड  !");
            ab.setMessage("क्या आप रिकॉर्ड अपलोड करना चाहते हैं  ?");
            ab.setPositiveButton("नहीं ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                }
            });

            ab.setNegativeButton("हाँ ", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    dialog.dismiss();
                    getcontracterlabphoto();

                }
            });

            // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();

        } else

        {
            Toast.makeText(
                    GrievanceMultiplePhotoActivity.this,
                    " No Internet connection ! \n Please check your internet connectivity.",
                    Toast.LENGTH_LONG).show();
        }
    }


    public void getcontracterlabphoto()
    {

        new Uploadcontracterlabphoto(complainInfo,mobile,complain).execute();

//        DataBaseHelper place = new DataBaseHelper(MultiplePhotoActivity.this);
//
//        ArrayList<UploadComplainEntity> dataProgress = place.getAllEntryDetail_New(mobile);
//        if (dataProgress.size() > 0) {
//            for (UploadComplainEntity data : dataProgress) {
//
//                new Uploadcontracterlabphoto(data).execute();
//            }
//
//        }
//        else {
//            Toast.makeText(getApplicationContext(), "No Data to Upload", Toast.LENGTH_LONG).show();
//        }

    }


    private class Uploadcontracterlabphoto extends AsyncTask<String, Void, String> {
        GrievanceEntryDetail data;
        String mob;
        String remarks;
        private final ProgressDialog dialog = new ProgressDialog(GrievanceMultiplePhotoActivity.this);

        private final AlertDialog alertDialog = new AlertDialog.Builder(GrievanceMultiplePhotoActivity.this).create();


        Uploadcontracterlabphoto(GrievanceEntryDetail data, String mobile,String complain) {
            this.data = data;
            this.mob = mobile;
            this.remarks = complain;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("रिकॉर्ड अपलोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {


            String res = WebServiceHelper.UploadBasicDetails(this.data,mob,remarks);
            return res;

        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue",""+result);

            if (result != null) {
                if (result.equalsIgnoreCase("1")) {
//                    DataBaseHelper  placeData = new DataBaseHelper(MultiplePhotoActivity.this);
//                    // long c = placeData.deleterowconLab(userid);
//                    boolean isDel = placeData.deleterowconLab(Integer.parseInt(data.get_slno()));
//                    if (isDel) {
//
//                    } else {
//                        Log.e("message", "data is uploaded but not deleted !!");
//                    }

                    Toast.makeText(GrievanceMultiplePhotoActivity.this, "आपकी शिकायत रजिस्टर हो गई है ! आपको शिकायत नंबर आपके द्वारा दिए गए मोबाइल नंबर पे SMS द्वारा प्राप्त हो जाएगी !", Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(GrievanceMultiplePhotoActivity.this,GrievanceHomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
                else  if (result.equalsIgnoreCase("0")) {
                    Toast.makeText(GrievanceMultiplePhotoActivity.this, "अपलोडिंग फेल, रिकॉर्ड सर्वर पर सहेजा नहीं गया", Toast.LENGTH_LONG).show();
                }
            }
            else {
                Toast.makeText(getApplicationContext(), "Null record", Toast.LENGTH_LONG).show();
            }
        }
    }
}
