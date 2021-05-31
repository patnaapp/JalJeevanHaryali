package bih.in.jaljeevanharyali.activity.nursery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.NurseryAdapter;
import bih.in.jaljeevanharyali.adapter.PublicSchemeListAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.CommonPref;

public class NursuryListActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spin_panchayat,spin_block;
    TextView tv_header,tv_Norecord;
    RecyclerView rv_data;
    Button btn_add_nursery;

    NurseryAdapter adaptor_showedit_listDetail;

    ArrayList<Block> BlockList = new ArrayList<Block>();

    DataBaseHelper dataBaseHelper;
    UserDetails userInfo;

    ArrayList<NurseryEntity> data;

    String DistrictCode="",blockCode="",blockName="";
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursury_list);

        dataBaseHelper=new DataBaseHelper(this);

        setup();

        loadData();

        btn_add_nursery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),NurseryEntryActivity.class);
                i.putExtra("id", 0);
                i.putExtra("blockCode", blockCode);
                i.putExtra("blockName", blockName);
                i.putExtra("type", type);
                startActivity(i);
                //finish();
            }
        });

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loadData(){
        DistrictCode= CommonPref.getUserDetails(NursuryListActivity.this).getDistrictCode();

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        if(type.equals(AppConstant.NURSURY)){
            btn_add_nursery.setText("नया पौधशाला जोड़ें");
            tv_header.setText("पौधशाला का सर्वेक्षण");
        }else{
            btn_add_nursery.setText("नया भवन जोड़ें");
            tv_header.setText("भवन का सर्वेक्षण");
        }

        loadBlockSpinner();
    }

    public void setup(){
        spin_block=(Spinner) findViewById(R.id.spin_block);
        tv_header=(TextView) findViewById(R.id.tv_header);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        rv_data= findViewById(R.id.rv_data);
        btn_add_nursery=(Button) findViewById(R.id.btn_add_nursery);
        spin_block.setOnItemSelectedListener(this);

        btn_add_nursery.setVisibility(View.GONE);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_block:
                if (position > 0) {
                    blockCode = BlockList.get(position - 1).getBlockCode().trim();
                    blockName = BlockList.get(position - 1).getBlockName();
                    Log.d("pond", "Block Code: "+blockCode);
                    populateLocalData();
                    btn_add_nursery.setVisibility(View.VISIBLE);
                    //loadPanchayatSpinner();
                }
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void populateLocalData(){
        data=dataBaseHelper.getNurseryInspectionDetail(blockCode, type, "0");

        if(type.equals(AppConstant.NURSURY)){
            tv_header.setText("पौधशाला का सर्वेक्षण ("+ data.size() +")");
        }else{
            tv_header.setText("भवन का सर्वेक्षण ("+ data.size() +")");
        }

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            rv_data.setVisibility(View.VISIBLE);

            rv_data.setLayoutManager(new LinearLayoutManager(this));
            adaptor_showedit_listDetail = new NurseryAdapter(this, data);
            rv_data.setAdapter(adaptor_showedit_listDetail);

        }else{
            rv_data.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}
