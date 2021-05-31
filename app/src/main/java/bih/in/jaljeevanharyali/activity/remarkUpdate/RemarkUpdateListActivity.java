package bih.in.jaljeevanharyali.activity.remarkUpdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.UpdateRemarkAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.StructureRemarkEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;

public class RemarkUpdateListActivity extends Activity implements AdapterView.OnItemSelectedListener {

    RecyclerView rv_data;
    Spinner spin_panchayat,spin_block;
    TextView tv_Norecord,tv_header;
    RadioButton rb_well,rb_chapakal,rb_structure;
    LinearLayout ll_block;

    DataBaseHelper dataBaseHelper;
    ArrayList<StructureRemarkEntity> data;

    ArrayList<PanchayatData> panchayatList = new ArrayList();
    ArrayList<Block> blockList = new ArrayList();

    String blockCode,blockName,DistrictCode,panchayatCode,panchayatName="",structureId="1";
    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_update_list);

        initialize();

        userInfo = (UserDetails)getIntent().getSerializableExtra("user");
        DistrictCode = userInfo.getDistrictCode();
        blockCode = userInfo.getBlockCode();
        blockName = userInfo.getBlockName();

        if(userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
            ll_block.setVisibility(View.VISIBLE);
            loadBlockSpinner();
        }else{
            ll_block.setVisibility(View.GONE);
            loadPanchayatSpinner();
        }

        rb_well.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    structureId = "2";
                    //loadPanchayatSpinner();
                    populateLocalData();
                }
            }
        });

        rb_chapakal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    structureId = "5";
                    //loadPanchayatSpinner();
                    populateLocalData();
                }
            }
        });

        rb_structure.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    structureId = "1";
                    //loadPanchayatSpinner();
                    populateLocalData();
                }
            }
        });
    }

    public void initialize(){

        dataBaseHelper = new DataBaseHelper(this);

        rv_data=findViewById(R.id.rv_data);

        tv_Norecord= findViewById(R.id.tv_Norecord);
        tv_header= findViewById(R.id.tv_header);


        spin_block= findViewById(R.id.spin_block);
        spin_panchayat= findViewById(R.id.spin_panchayat);

        rb_well= findViewById(R.id.rb_well);
        rb_chapakal= findViewById(R.id.rb_chapakal);
        rb_structure= findViewById(R.id.rb_structure);

        ll_block= findViewById(R.id.ll_block);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void populateLocalData(){
        data = dataBaseHelper.getStructureRemarkDetail(panchayatCode,structureId);
        tv_header.setText(AppConstant.UPDATEREMARK+" सूची ("+data.size()+")");

        if(data != null && data.size()> 0){
            rv_data.setLayoutManager(new LinearLayoutManager(this));
            UpdateRemarkAdapter adapter = new UpdateRemarkAdapter(this, data, tv_header, false);
            rv_data.setAdapter(adapter);
            tv_Norecord.setVisibility(View.GONE);
            rv_data.setVisibility(View.VISIBLE);
        }else{
            rv_data.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    public void loadBlockSpinner(){
        blockList=dataBaseHelper.getBlock(DistrictCode);

        ArrayList<String> blockStrArray =new ArrayList<>();
        blockStrArray.add("-प्रखंड चुनें-");

        for(Block item: blockList){
            blockStrArray.add(item.getBlockName());
        }

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,blockStrArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_block.setAdapter(adapter);
        spin_block.setOnItemSelectedListener(this);
    }

    public void loadPanchayatSpinner() {
        panchayatList = dataBaseHelper.getPanchayt(blockCode);
        ArrayList<String> panchayatNameArray = new ArrayList<String>();
        panchayatNameArray.add("-Select-");

        for (PanchayatData panchayat_list : panchayatList) {
            panchayatNameArray.add(panchayat_list.getPname());
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panchayatNameArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_panchayat.setAdapter(adapter);
        spin_panchayat.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spin_block:
                if (position > 0) {
                    blockCode = blockList.get(position - 1).getBlockCode().trim();
                    blockName = blockList.get(position - 1).getBlockName().trim();
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
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
