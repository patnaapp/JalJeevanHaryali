package bih.in.jaljeevanharyali.activity.districtLogin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondListActivity;
import bih.in.jaljeevanharyali.adapter.PondAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;

public class DistrictWisePondListActivity extends Activity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    Spinner spin_panchayat,spin_block;
    ProgressDialog dialog;
    TextView tv_Norecord;

    PondAdapter adaptor_showedit_listDetail;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondInspectionEntity> data;

    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<Block> BlockList = new ArrayList<Block>();
//    ArrayList<String> panchayatNameArray;
//    ArrayAdapter<String> panchayatadapter;

    String blockCode="",districtCode,panchayatCode="",panchayatName="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_district_wise_pond_list);

        dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        districtCode = intent.getStringExtra("DistrictCode");

        initialise();

        loadBlockSpinner();
    }

    public void initialise(){
        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);

        spin_panchayat=(Spinner) findViewById(R.id.spin_panchayat);
        spin_block=(Spinner) findViewById(R.id.spin_block);

        spin_panchayat.setOnItemSelectedListener(this);
        spin_block.setOnItemSelectedListener(this);
    }

    public void loadBlockSpinner(){
        BlockList=dataBaseHelper.getBlock(districtCode);

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

    public void populateLocalData(){

        data=dataBaseHelper.getPondsInspectionDetail(panchayatCode);

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            //listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
           // adaptor_showedit_listDetail = new PondAdapter(this, data, panchayatCode, panchayatName);
            listView.setAdapter(adaptor_showedit_listDetail);
            adaptor_showedit_listDetail.notifyDataSetChanged();
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_block:
                if (position > 0) {
                    blockCode = BlockList.get(position - 1).getBlockCode().trim();
                    Log.d("pond", "Block Code: "+blockCode);
                    loadPanchayatSpinner();
                }
                break;

            case R.id.spin_panchayat:
                if (position > 0) {
                    panchayatCode = panchayatList.get(position - 1).getPcode().trim();
                    panchayatName = panchayatList.get(position - 1).getPname().trim();
                    Log.d("pond", "Panchayat Code: "+panchayatCode);
                    populateLocalData();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
