package bih.in.jaljeevanharyali.activity.remarkUpdate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.CameraActivity;
import bih.in.jaljeevanharyali.activity.DashboardActivity;
import bih.in.jaljeevanharyali.activity.nursery.NurseryEntryActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.DepartmentEntity;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.StructureRemarkEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class RemarkUpdateInspectionActivity extends Activity implements AdapterView.OnItemSelectedListener {

    TextView tv_block,tv_panchayat,tv_village,tv_inspection_id,tv_rajswa_thana,tv_khata_khesra,tv_structure,tv_remark;
    ImageView iv_show_location;

    TextView tv_pond_avbl,tv_functional,tv_photo;
    Spinner spin_pond_avbl,spin_functional;
    EditText et_remark;
    ImageView img1;
    LinearLayout ll_photo,ll_functional;

    UserDetails userInfo;
    StructureRemarkEntity structureInfo;
    DataBaseHelper dataBaseHelper;

    private final static int CAMERA_PIC = 99;
    byte[] img;
    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_update_inspection);

        initialize();
        loadSpinnerYesNo();
        getDataFromIntent();
    }

    public void initialize(){
        dataBaseHelper=new DataBaseHelper(this);

        tv_block = findViewById(R.id.tv_block);
        tv_panchayat = findViewById(R.id.tv_panchayat);
        tv_village = findViewById(R.id.tv_village);
        tv_inspection_id = findViewById(R.id.tv_inspection_id);
        tv_rajswa_thana = findViewById(R.id.tv_rajswa_thana);
        tv_khata_khesra = findViewById(R.id.tv_khata_khesra);
        tv_structure = findViewById(R.id.tv_structure);
        tv_remark = findViewById(R.id.tv_remark);
        tv_photo = findViewById(R.id.tv_photo);
        img1 = findViewById(R.id.img1);
        tv_pond_avbl = findViewById(R.id.tv_pond_avbl);
        tv_functional = findViewById(R.id.tv_functional);

        iv_show_location = findViewById(R.id.iv_show_location);

        spin_pond_avbl = findViewById(R.id.spin_pond_avbl);
        spin_functional = findViewById(R.id.spin_functional);
        et_remark = findViewById(R.id.et_remark);
        ll_photo = findViewById(R.id.ll_image);
        ll_functional = findViewById(R.id.ll_functional);
    }

    public void getDataFromIntent(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");

        userInfo = dataBaseHelper.getUserDetails(username, password);

        structureInfo = (StructureRemarkEntity) getIntent().getSerializableExtra("data");

        tv_block.setText(structureInfo.getBlock_Name());
        tv_panchayat.setText(structureInfo.getPanchayat_Name());
        tv_village.setText(structureInfo.getVillage_Name());
        tv_inspection_id.setText(structureInfo.getInspectionID());
        tv_rajswa_thana.setText(structureInfo.getRajswaThanaNumber());
        tv_khata_khesra.setText(structureInfo.getKhaata_Kheshara_Number());
        tv_structure.setText(structureInfo.getTypesOfSarchna());
        tv_remark.setText(structureInfo.getRemark());

        if(structureInfo.getStrType().equals("5")){
            ll_photo.setVisibility(View.VISIBLE);
        }else{
            ll_photo.setVisibility(View.GONE);
        }

        if(structureInfo.getIsUpdate().equals("1")){

            if(structureInfo.getIsStrAvailable().equals("Y")){
                spin_pond_avbl.setSelection(1);
            }else if(structureInfo.getIsStrAvailable().equals("N")){
                spin_pond_avbl.setSelection(2);
            }

            if(structureInfo.getIsWorking().equals("Y")){
                spin_functional.setSelection(1);
            }else if(structureInfo.getIsWorking().equals("N")){
                spin_functional.setSelection(2);
            }

            et_remark.setText(structureInfo.getNewRemark());
            if(structureInfo.getImg() != null){
                img = structureInfo.getImg();
                Bitmap bmp = BitmapFactory.decodeByteArray(img, 0,img.length);
                img1.setImageBitmap(bmp);
            }
        }
    }

    public void onSaveData(View view) {
        if (validateData()) {

            structureInfo.setImg(img);
            structureInfo.setEntryDate(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date()));
            structureInfo.setEntryBy(userInfo.getUserID());
            //structureInfo.setDeptId(userInfo.getDeptId());
            structureInfo.setAppVersion(Utiilties.getAppVersion(this));
            structureInfo.setIsUpdate("1");
            structureInfo.setNewRemark(et_remark.getText().toString());
            InsertIntoLocal();
        }
    }

    private void InsertIntoLocal(){

        long id = dataBaseHelper.InsertUpdateRemarkData(structureInfo);

        if (id > 0) {
            Toast.makeText(this,"Data Saved Successfully,",Toast.LENGTH_SHORT).show();
            Intent iUserHome = new Intent(getApplicationContext(), DashboardActivity.class);
            iUserHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(iUserHome);
            finish();
        }else {
            Toast.makeText(this,"Data Not Saved", Toast.LENGTH_SHORT).show();
        }
    }

    public void loadSpinnerYesNo() {
        ArrayList<String> list = new ArrayList<String>();
        list.add("-- " + getResources().getString(R.string.select_spinner_hint) + "--");
        list.add(getResources().getString(R.string.yes));
        list.add(getResources().getString(R.string.no));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin_pond_avbl.setAdapter(spinnerAdapter);
        spin_functional.setAdapter(spinnerAdapter);

        spin_pond_avbl.setOnItemSelectedListener(this);
        spin_functional.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {

            case R.id.spin_pond_avbl:
                if (position > 0) {
                    if(position == 1){
                        structureInfo.setIsStrAvailable("Y");
                        ll_functional.setVisibility(View.VISIBLE);
                    }else if (position == 2){
                        structureInfo.setIsStrAvailable("N");
                        ll_functional.setVisibility(View.GONE);
                        structureInfo.setIsWorking("");
                    }

                    tv_pond_avbl.setError(null);
                }else{
                    structureInfo.setIsStrAvailable("");
                }
                break;
            case R.id.spin_functional:
                if (position > 0) {
                    if(position == 1){
                        structureInfo.setIsWorking("Y");
                    }else if (position == 2){
                        structureInfo.setIsWorking("N");
                    }

                    tv_functional.setError(null);
                }else{
                    structureInfo.setIsWorking("");
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private boolean validateData() {
        View focusView = null;
        boolean validate = true;

        if (structureInfo.getIsStrAvailable() == null || structureInfo.getIsStrAvailable().equals("")) {
            tv_pond_avbl.setError(getString(R.string.fieldRequired));
            focusView = tv_pond_avbl;
            validate = false;
        }else if(structureInfo.getIsStrAvailable().equals("Y")){
            if (structureInfo.getIsWorking() == null || structureInfo.getIsWorking().equals("")) {
                tv_functional.setError(getString(R.string.fieldRequired));
                focusView = tv_functional;
                validate = false;
            }
        }

        if (et_remark.getText().toString().equals("")) {
            et_remark.setError(getString(R.string.fieldRequired));
            focusView = et_remark;
            validate = false;
        }

        if(structureInfo.getStrType().equals("5") && img == null){
            tv_photo.setError(getString(R.string.fieldRequired));
            focusView = tv_photo;
            validate = false;
            Toast.makeText(this, "कृपया फोटो ले", Toast.LENGTH_SHORT).show();
        }

        if (!validate) focusView.requestFocus();

        return validate;
    }

    public void onClick_ViewImg(View view) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.viewimage, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("  फोटो  ");


        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        ImageView imgview = (ImageView) dialogView.findViewById(R.id.imgview);
        if (img != null) {
            imgview.setImageBitmap(BitmapFactory.decodeByteArray(img, 0, img.length));
        }

        dialogBuilder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    public void onClickTakePhoto(View view) {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {


            buildAlertMessageNoGps();
        }
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) == true) {

            Intent iCamera = new Intent(getApplicationContext(), CameraActivity.class);
            //if (view.equals(img1))
            iCamera.putExtra("KEY_PIC", "1");
            startActivityForResult(iCamera, CAMERA_PIC);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        switch (requestCode) {
            case CAMERA_PIC:
                if (resultCode == RESULT_OK) {
                    byte[] imgData = data.getByteArrayExtra("CapturedImage");
                    DataBaseHelper placeData = new DataBaseHelper(getApplicationContext());

                    switch (data.getIntExtra("KEY_PIC", 0)) {
                        case 1:
                            img = imgData;
                            Bitmap bmpImg = BitmapFactory.decodeByteArray(imgData, 0, imgData.length);
                            bmp = bmpImg;
                            img1.setImageBitmap(bmpImg);

                            structureInfo.setLatitude(String.valueOf(data.getStringExtra("Lat")));
                            structureInfo.setLongitude(String.valueOf(data.getStringExtra("Lng")));
                            tv_photo.setError(null);
                            break;
                    }
                }
        }
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.logo);
        builder.setTitle("GPS ?");
        builder.setMessage("(GPS)जीपीएस बंद है\nस्थान के अक्षांश और देशांतर प्राप्त करने के लिए कृपया जीपीएस चालू करें")
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

    public void onShowOnMap(View view) {
        Uri mapUri = Uri.parse("geo:0,0?q="+structureInfo.getLatitude()+","+structureInfo.getLongitude()+"("+structureInfo.getId()+")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        }else{
            Toast.makeText(this, "कृपया इस सुविधा का उपयोग करने के लिए Google मानचित्र स्थापित करें", Toast.LENGTH_SHORT).show();
        }
    }
}
