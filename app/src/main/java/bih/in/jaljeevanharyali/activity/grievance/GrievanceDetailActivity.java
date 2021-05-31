package bih.in.jaljeevanharyali.activity.grievance;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.entity.UploadComplainEntity;
import bih.in.jaljeevanharyali.utility.Utiilties;


public class GrievanceDetailActivity extends Activity {

    TextView tv_district,tv_block,tv_panchayat,tv_ward,tv_mobile,tv_complain_no,tv_complain_date,tv_remark,tv_complain_status,tv_reply;
    TextView tv_grev_type,tv_complain_name,tv_complain_title,tv_mis_code,tv_sch_str,tv_awayab;
    ImageView iv_complain,iv_show_location;
    LinearLayout ll_schem_code,ll_awayab;
    UploadComplainEntity complainInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grievance_detail);

        initialisation();

        extractDataFromIntent();

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void initialisation(){
        tv_district = (TextView)findViewById(R.id.tv_district);
        tv_block = (TextView)findViewById(R.id.tv_block);
        tv_panchayat = (TextView)findViewById(R.id.tv_panchayat);
        tv_ward = (TextView)findViewById(R.id.tv_ward);
        tv_mobile = (TextView)findViewById(R.id.tv_mobile);
        tv_complain_no = (TextView)findViewById(R.id.tv_complain_no);
        tv_complain_date = (TextView)findViewById(R.id.tv_complain_date);
        tv_remark = (TextView)findViewById(R.id.tv_remark);
        tv_complain_status = (TextView)findViewById(R.id.tv_complain_status);
        tv_reply = (TextView)findViewById(R.id.tv_reply);

        tv_grev_type = (TextView)findViewById(R.id.tv_grev_type);
        tv_complain_name = (TextView)findViewById(R.id.tv_complain_name);
        tv_complain_title = (TextView)findViewById(R.id.tv_complain_title);

        tv_mis_code = findViewById(R.id.tv_mis_code);
        tv_sch_str = findViewById(R.id.tv_sch_str);
        ll_schem_code = findViewById(R.id.ll_schem_code);
        tv_awayab = findViewById(R.id.tv_awayab);
        ll_awayab = findViewById(R.id.ll_awayab);

        iv_complain = (ImageView) findViewById(R.id.iv_complain);
        iv_show_location = (ImageView) findViewById(R.id.iv_show_location);

        ll_schem_code.setVisibility(View.GONE);
        iv_show_location.setVisibility(View.GONE);
        ll_awayab.setVisibility(View.GONE);
    }

    public void extractDataFromIntent(){
        complainInfo = (UploadComplainEntity) getIntent().getSerializableExtra("data");

        tv_district.setText(complainInfo.getDistrictName());
        tv_block.setText(complainInfo.getBlockName());
        tv_panchayat.setText(complainInfo.getPanchayatName());
        tv_ward.setText(complainInfo.getWARDNAME());
        tv_mobile.setText(complainInfo.getMobileNo());
        tv_complain_no.setText(complainInfo.getComplainId1());
        tv_complain_date.setText(Utiilties.convertDateStringFormet("dd/MM/yyyy HH:mm:ss", "dd MMM yyyy",complainInfo.getComplainDate()));
        tv_remark.setText(complainInfo.getComplainRemarks());
        tv_complain_status.setText(complainInfo.getStatus());
        tv_reply.setText(complainInfo.getRemarks());

        Log.e("imgPath", complainInfo.getPhoto1().replace("~",""));
        Picasso.with(this).load("http://jaljeevanhariyali.bih.nic.in"+complainInfo.getPhoto1().replace("~","")).into(iv_complain);

        if(complainInfo.getGrievanceRelated().equals("oth")){
            tv_grev_type.setText("Other");
            tv_complain_title.setText("अवयब");
            tv_complain_name.setText(complainInfo.getSub_Execution_DeptName());
        }else if(complainInfo.getGrievanceRelated().equals("str")){
            tv_grev_type.setText("Structure");
            tv_complain_title.setText("संरचना");
            tv_complain_name.setText(complainInfo.getStructureTypeNameHn());
            iv_show_location.setVisibility(View.VISIBLE);
        }else if(complainInfo.getGrievanceRelated().equals("sch")){
            tv_grev_type.setText("Scheme");
            tv_complain_title.setText("योजना");
            tv_complain_name.setText(handleNullValue(complainInfo.getWork_StructureName()));
            tv_mis_code.setText(complainInfo.getMIS_Scheme_Code());
            tv_sch_str.setText(handleNullValue(complainInfo.getStructureTypeNameHn()));
            tv_awayab.setText(complainInfo.getSub_Execution_DeptName());
            ll_schem_code.setVisibility(View.VISIBLE);
            ll_awayab.setVisibility(View.VISIBLE);

        }
    }

    public void onShowOnMap(View view) {
        Uri mapUri = Uri.parse("geo:0,0?q="+complainInfo.getLatlongComp()+","+complainInfo.getLongitudeComp()+"("+complainInfo.getWork_StructureName()+")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(this.getPackageManager()) != null) {
            startActivity(mapIntent);
        }else{
            Toast.makeText(this, "कृपया इस सुविधा का उपयोग करने के लिए Google Map install करें", Toast.LENGTH_SHORT).show();
        }
    }

    public String handleNullValue(String name){
        if(name.equals("anyType{}"))
            return "NA";
        else
            return name;
    }

    //public void getGrevTypeName()
}
