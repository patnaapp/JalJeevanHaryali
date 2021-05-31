package bih.in.jaljeevanharyali.activity.sanrachnaProgress;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.CameraActivity;
import bih.in.jaljeevanharyali.activity.DashboardActivity;
import bih.in.jaljeevanharyali.activity.ManregaInspectionActivity;
import bih.in.jaljeevanharyali.activity.ManregaPhotoActivity;
import bih.in.jaljeevanharyali.activity.MultiplePhotoActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaDataEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class
SanrachnaProgressConfirmActivity extends Activity implements View.OnClickListener {

    DataBaseHelper dataBaseHelper;
    //TextView tv_abya_name,tv_estimated_amount,tv_anumodan_tithi,tv_prashasnik_date,tv_yojna_code;
    TextView tv_district,tv_block,tv_panchayat,tv_ward,tv_kriwayan_vibhag,tv_sanrachna_type,tv_sanrachna_measurement,tv_title_phase1,tv_title_phase2,tv_title_phase3,tv_scheme_type;
    TextView tv_gram,tv_rajaswa_thana;
    EditText et_remark_phase1,et_remark_phase2,et_remark_phase3;
    Button btn_phase1,btn_phase2,btn_phase3;
    LinearLayout ll_phase1,ll_phase2,ll_phase3,ll_entry_phase1,ll_entry_phase2,ll_entry_phase3;
    ImageView img_phase1,img_phase2,img_phase3;
    RelativeLayout rl_phase1,rl_phase2,rl_phase3;

    ImageView imageButton1,imageButton2;
    Intent imageData1,imageData2;
    private final static int CAMERA_PIC = 99;

    int id;
    private String s1_data = "", latitude, longitude;
    SanrachnaDataEntity info;
    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanrachna_progress_confirm);

        dataBaseHelper = new DataBaseHelper(getApplicationContext());
        Initialization();
        getUserDetail();
        extractDataFromIntent();

        btn_phase3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateData()) {
                    UpdateSanrachnaProgessDetail();
                }
            }
        });
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dataBaseHelper.getUserDetails(username, password);
    }

    private void Initialization() {
        tv_district = (TextView) findViewById(R.id.tv_district);
        tv_block = (TextView) findViewById(R.id.tv_block);
        tv_panchayat = (TextView) findViewById(R.id.tv_panchayat);
        tv_ward = (TextView) findViewById(R.id.tv_ward);
        tv_kriwayan_vibhag = (TextView) findViewById(R.id.tv_kriwayan_vibhag);
        //tv_abya_name = (TextView) findViewById(R.id.tv_abya_name);
        tv_sanrachna_type = (TextView) findViewById(R.id.tv_sanrachna_type);
        tv_sanrachna_measurement = (TextView) findViewById(R.id.tv_sanrachna_measurement);
//        tv_estimated_amount = (TextView) findViewById(R.id.tv_estimated_amount);
//        tv_anumodan_tithi = (TextView) findViewById(R.id.tv_anumodan_tithi);
//        tv_prashasnik_date = (TextView) findViewById(R.id.tv_prashasnik_date);
//        tv_yojna_code = (TextView) findViewById(R.id.tv_yojna_code);
        tv_title_phase1 = (TextView) findViewById(R.id.tv_title_phase1);
        tv_title_phase2 = (TextView) findViewById(R.id.tv_title_phase2);
        tv_title_phase3 = (TextView) findViewById(R.id.tv_title_phase3);
        tv_scheme_type = (TextView) findViewById(R.id.tv_scheme_type);

        tv_gram = (TextView) findViewById(R.id.tv_gram);
        tv_rajaswa_thana = (TextView) findViewById(R.id.tv_rajaswa_thana);

        ll_phase1 = (LinearLayout) findViewById(R.id.ll_phase1);
        ll_phase2 = (LinearLayout) findViewById(R.id.ll_phase2);
        ll_phase3 = (LinearLayout) findViewById(R.id.ll_phase3);
        ll_entry_phase1 = (LinearLayout) findViewById(R.id.ll_entry_phase1);
        ll_entry_phase2 = (LinearLayout) findViewById(R.id.ll_entry_phase2);
        ll_entry_phase3 = (LinearLayout) findViewById(R.id.ll_entry_phase3);

        rl_phase1 = (RelativeLayout) findViewById(R.id.rl_phase1);
        rl_phase2 = (RelativeLayout) findViewById(R.id.rl_phase2);
        rl_phase3 = (RelativeLayout) findViewById(R.id.rl_phase3);

        et_remark_phase1 = (EditText)findViewById(R.id.et_remark_phase1);
        et_remark_phase2 = (EditText)findViewById(R.id.et_remark_phase2);
        et_remark_phase3 =(EditText)findViewById(R.id.et_remark_phase3);

        img_phase1 =(ImageView) findViewById(R.id.img_phase1);
        img_phase2 =(ImageView)findViewById(R.id.img_phase2);
        img_phase3 =(ImageView)findViewById(R.id.img_phase3);

        imageButton1 =(ImageView)findViewById(R.id.imageButton1);
        imageButton2 =(ImageView)findViewById(R.id.imageButton2);

        btn_phase1 =(Button) findViewById(R.id.btn_phase1);
        btn_phase2 =(Button) findViewById(R.id.btn_phase2);
        btn_phase3 =(Button) findViewById(R.id.btn_phase3);

        imageButton1.setOnClickListener(this);
        imageButton2.setOnClickListener(this);
    }

    public void extractDataFromIntent(){
        Intent intent = getIntent();
        //id = intent.getIntExtra("id", 0);
        info = (SanrachnaDataEntity) intent.getSerializableExtra("data");
        id = info.getId();
        //info = dataBaseHelper.getManregaDetail(String.valueOf(id));

        setData(info);
    }

    public void setData(SanrachnaDataEntity info){

        tv_district.setText(info.getDistName());
        tv_block.setText(info.getBlockName());
        tv_panchayat.setText(info.getPanchayatName());
        tv_ward.setText("NA");
        tv_gram.setText(info.getVILLNAME());
        tv_rajaswa_thana.setText(info.getRajswaThanaNumber());

        tv_kriwayan_vibhag.setText(info.getSwamitw_Dep().equals("4") ? info.getSwamitwDep_Name() : info.getDepatmentName());
        //tv_abya_name.setText(info.getSubDivName());
        tv_sanrachna_type.setText(Utiilties.getTypeOfSanrachna(info.getTypesOfSarchna()));
        tv_sanrachna_measurement.setText(info.getArea_By_Govt_Record());
//        tv_estimated_amount.setText(info.getEstimated_Amount());
//        tv_anumodan_tithi.setText(info.getApproval_Date());
//        tv_prashasnik_date.setText(info.getAdministrative_Approval_Date());
//        tv_yojna_code.setText(info.getScheme_Code());

        String yojnaType = "NA";
//        if(info.getYojnaType() != null){
//            yojnaType = info.getYojnaType().equals("U") ? "उद्घाटन" : "शिलान्यास";
//        }
//        tv_scheme_type.setText(yojnaType);

        if(info.getIsUpdated() != null && info.getIsUpdated().equals("1")){
                ll_phase1.setVisibility(View.GONE);
                ll_phase2.setVisibility(View.GONE);
                ll_phase3.setVisibility(View.VISIBLE);
                ll_entry_phase3.setVisibility(View.VISIBLE);
                img_phase3.setVisibility(View.GONE);
                tv_title_phase3.setText("कार्य पूरा होने के बाद");
                et_remark_phase3.setText(info.getRemarksCompl());

                if(info.getImage1() != null){
                    imageButton1.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageButton1.setImageBitmap(Utiilties.GenerateThumbnail(BitmapFactory.decodeByteArray(info.getImage1(), 0,info.getImage1().length), 500, 500));
                }else{
                    Log.e("Image1", "Not Found");
                }

            if(info.getImage2() != null){
                imageButton2.setScaleType(ImageView.ScaleType.FIT_XY);
                imageButton2.setImageBitmap(Utiilties.GenerateThumbnail(BitmapFactory.decodeByteArray(info.getImage2(), 0,info.getImage2().length), 500, 500));
            }else{
                Log.e("Image2", "Not Found");
            }

        }else{
                ll_phase1.setVisibility(View.GONE);
                ll_phase2.setVisibility(View.GONE);
                ll_phase3.setVisibility(View.VISIBLE);
                ll_entry_phase3.setVisibility(View.VISIBLE);
                img_phase3.setVisibility(View.GONE);
                tv_title_phase3.setText("कार्य पूरा होने के बाद");
        }
    }


    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if (info.getImage1() == null) {
            Toast.makeText(this, "कृपया फोटो लें", Toast.LENGTH_SHORT).show();
            validate = false;
        }

        //if (!validate) focusView.requestFocus();

        return validate;
    }

    private void UpdateSanrachnaProgessDetail(){
        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        info.setRemarksCompl(et_remark_phase3.getText().toString().trim());

        info.setLatitude_Mob(latitude);
        info.setLongitude_Mob(longitude);

        info.setVerified_Date(currentDate);
        info.setVerified_By(userInfo.getUserID());
        info.setAppversion(getAppVersion());
        info.setIsUpdated("1");

        long result = new DataBaseHelper(this).UpdateSanrachnaInspectionDetail(info);

        if (result > 0) {
            Intent iUserHome = new Intent(getApplicationContext(),DashboardActivity.class);
            iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(iUserHome);
        }else {
            Toast.makeText(getApplicationContext(),"डाटा सेव नहीं हुआ", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.imageButton1:
                takePhoto("1");
                break;
            case R.id.imageButton2:
                if(info.getImage1() != null){
                    takePhoto("2");
                }else{
                    Toast.makeText(this, "कृपया पहले फोटो लें", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");


                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            imageData1=data;
                            info.setImage1(imageData1.getByteArrayExtra("CapturedImage"));

                            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
                            latitude = imageData1.getStringExtra("Lat");
                            longitude = imageData1.getStringExtra("Lng");

                            imageButton1.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageButton1.setImageBitmap(Utiilties.GenerateThumbnail(bmp,500, 500));
                            imageButton1.setOnClickListener(null);
                            break;

                        case 2:
                            imageData2=data;
                            info.setImage2(imageData2.getByteArrayExtra("CapturedImage"));
                            imageButton2.setScaleType(ImageView.ScaleType.FIT_XY);
                            imageButton2.setImageBitmap(Utiilties.GenerateThumbnail(BitmapFactory.decodeByteArray(imgData, 0,
                                            imgData.length), 500, 500));
                            imageButton2.setOnClickListener(null);
                            break;
                    }


                }

        }

    }

    public void takePhoto(String index){
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){

            Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
            iCamera.putExtra("KEY_PIC", index);
            startActivityForResult(iCamera, CAMERA_PIC);
        }else{
            buildAlertMessageNoGps();
        }
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

    public String getAppVersion(){
        try {

            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            return version;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }
}
