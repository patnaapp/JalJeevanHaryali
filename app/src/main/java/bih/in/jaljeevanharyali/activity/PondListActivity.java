package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.PondAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.BlockName;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;

public class PondListActivity extends Activity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    PondAdapter adaptor_showedit_listDetail;
    Spinner spin_panchayat,spin_block;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondInspectionEntity> data;
    String listid;
    TextView tv_Norecord,tv_header;
    Button btn_add_pond,btn_view_pond_map;
    LinearLayout ll_btn,ll_block;

    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<Block> BlockList = new ArrayList<Block>();
    UserDetails userInfo;

    String blockCode,blockName,DistrictCode,panchayatCode,panchayatName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond_list);

        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_header=(TextView) findViewById(R.id.tv_header);

        btn_add_pond=(Button) findViewById(R.id.btn_add_pond);
        btn_view_pond_map=(Button) findViewById(R.id.btn_view_pond_map);
        ll_btn=(LinearLayout) findViewById(R.id.ll_btn);
        ll_block=(LinearLayout) findViewById(R.id.ll_block);
        ll_btn.setVisibility(View.GONE);
        spin_panchayat=(Spinner) findViewById(R.id.spin_panchayat);
        spin_block=(Spinner) findViewById(R.id.spin_block);
        spin_panchayat.setOnItemSelectedListener(this);
        spin_block.setOnItemSelectedListener(this);

        dataBaseHelper = new DataBaseHelper(this);
        Intent intent = getIntent();
        userInfo = (UserDetails)intent.getSerializableExtra("user");

        DistrictCode = userInfo.getDistrictCode();
        blockCode = userInfo.getBlockCode();
        blockName = userInfo.getBlockName();

        if(userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
            ll_block.setVisibility(View.VISIBLE);
            //btn_add_pond.setVisibility(View.GONE);
            loadBlockSpinner();
        }else{
            ll_block.setVisibility(View.GONE);
            loadPanchayatSpinner();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                // TODO Auto-generated method stub
                Intent i=new Intent(getApplicationContext(),PondInspectionActivity.class);
                i.putExtra("id", data.get(position).getId());
                i.putExtra("panchayatCode", panchayatCode);
                i.putExtra("panchayatName", panchayatName);
                i.putExtra("blockCode", blockCode);
                i.putExtra("blockName", blockName);
                //listid=data.get(position).getSlno();
                startActivity(i);
                finish();
            }


        });

        btn_add_pond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),PondInspectionActivity.class);
                i.putExtra("id", 0);
                i.putExtra("panchayatCode", panchayatCode);
                i.putExtra("panchayatName", panchayatName);
                //i.putExtra("user", userInfo);
                i.putExtra("blockCode", blockCode);
                i.putExtra("blockName", blockName);
                startActivity(i);
                finish();
            }
        });

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

//        btn_view_pond_map.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i=new Intent(getApplicationContext(),MapActivity.class);
//                i.putExtra("type", "pond");
//                i.putExtra("panchayatCode", panchayatCode);
//                startActivity(i);
//            }
//        });
    }

    public void populateLocalData(){
        data=dataBaseHelper.getPondsInspectionDetail(panchayatCode);
        tv_header.setText("जल संरचनाओं का सर्वेक्षण ("+ data.size() +")");

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            //listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new PondAdapter(PondListActivity.this, data, panchayatCode, panchayatName, userInfo);
            listView.setAdapter(adaptor_showedit_listDetail);
            adaptor_showedit_listDetail.notifyDataSetChanged();
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    public void loadBlockSpinner(){
        BlockList=dataBaseHelper.getBlock(DistrictCode);

        ArrayList<String> blockStrArray =new ArrayList<>();
        blockStrArray.add("-प्रखंड चुनें-");

        for(Block item: BlockList){
            blockStrArray.add(item.getBlockName());
        }

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,blockStrArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_block.setAdapter(adapter);
    }

    public void loadPanchayatSpinner(){
        panchayatList=dataBaseHelper.getPanchayt(blockCode);

        ArrayList<String> StrArray =new ArrayList<>();
        StrArray.add("-पंचायत चुनें-");

        for(PanchayatData item: panchayatList){
            StrArray.add(item.getPname());
        }

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,StrArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_panchayat.setAdapter(adapter);
    }



    private void setReportListViewData()
    {
        data=dataBaseHelper.getPondsInspectionDetail(panchayatCode);
        //Toast.makeText(this, String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
        adaptor_showedit_listDetail =new PondAdapter(PondListActivity.this,data, panchayatCode, panchayatName, userInfo);
        listView.setAdapter(adaptor_showedit_listDetail);
        adaptor_showedit_listDetail.notifyDataSetChanged();
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(panchayatCode != null){
            if(data.size()> 0){
                tv_Norecord.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.invalidate();
                //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
                adaptor_showedit_listDetail = new PondAdapter(PondListActivity.this, data, panchayatCode, panchayatName, userInfo);
                listView.setAdapter(adaptor_showedit_listDetail);
            }else{
                listView.setVisibility(View.GONE);
                tv_Norecord.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_block:
                if (position > 0) {
                    blockCode = BlockList.get(position - 1).getBlockCode().trim();
                    blockName = BlockList.get(position - 1).getBlockName().trim();
                    Log.d("pond", "Block Code: "+blockCode);
                    loadPanchayatSpinner();
                }
                break;
            case R.id.spin_panchayat:
                int pos = position;
                if (pos > 0) {
                    panchayatCode = panchayatList.get(pos - 1).getPcode().trim();
                    panchayatName = panchayatList.get(pos - 1).getPname().trim();
                    Log.d("pond", "Panchayat Code: "+panchayatCode);
                    populateLocalData();
                    ll_btn.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

