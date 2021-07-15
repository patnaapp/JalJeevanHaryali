package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import bih.in.jaljeevanharyali.adapter.WellAdaptor;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;

public class WellListActivity extends Activity implements AdapterView.OnItemSelectedListener {
    ListView listView;
    WellAdaptor adaptor_showedit_listDetail;
    Spinner spin_panchayat,spin_struct_type,spin_block;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondEntity> data;
    String listid;
    TextView tv_Norecord,tv_header;
    Button btn_add_pond,btn_view_pond_map;
    LinearLayout ll_btn,ll_block;
    RadioButton rb_well,rb_chapakal;

    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<Block> BlockList = new ArrayList<Block>();

    ArrayList<String> panchayatNameArray;
    ArrayAdapter<String> panchayatadapter;

    String blockCode,blockName,DistrictCode,panchayatCode,panchayatName="",structureId="2",Dpt_Id;

    String strucuteOption[] = {"-चयन करे-","कुँआ", "चापाकल"};
    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_well_list);

        initialize();

        userInfo = (UserDetails)getIntent().getSerializableExtra("user");
        DistrictCode = userInfo.getDistrictCode();
        blockCode = userInfo.getBlockCode();
        blockName = userInfo.getBlockName();
        Dpt_Id = userInfo.getDeptId();
        if(Dpt_Id.equals("10")){
            rb_chapakal.setVisibility(View.GONE);
        }else{
            rb_chapakal.setVisibility(View.VISIBLE);
        }

        if(userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
            ll_block.setVisibility(View.VISIBLE);
            loadBlockSpinner();
        }else{
            ll_block.setVisibility(View.GONE);
            loadPanchayatSpinner();
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getApplicationContext(),WellInspectionActivity.class);
                i.putExtra("id", data.get(position).getId());
                i.putExtra("panchayatCode", panchayatCode);
                i.putExtra("panchayatName", panchayatName);
                i.putExtra("blockCode", blockCode);
                i.putExtra("blockName", blockName);
                listid=data.get(position).getSlno();
                //Toast.makeText(ShowEdit.this, "RID:" + data.get(position).getReferenceID(), Toast.LENGTH_SHORT).show();
                startActivity(i);
                finish();

            }


        });

        btn_add_pond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),WellInspectionActivity.class);
                i.putExtra("id", 0);
                i.putExtra("panchayatCode", panchayatCode);
                i.putExtra("panchayatName", panchayatName);
                i.putExtra("structureId", structureId);
                i.putExtra("blockCode", blockCode);
                i.putExtra("blockName", blockName);
                startActivity(i);
                finish();
            }
        });

        btn_view_pond_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(getApplicationContext(),MapWellActivity.class);
                i.putExtra("type", "well");
                i.putExtra("panchayatCode", panchayatCode);
                startActivity(i);
            }
        });

        rb_well.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    structureId = "2";
                    loadPanchayatSpinner();
                    populateLocalData();
                    setButtonTitle();
                    tv_header.setText("कुँआ का सत्यापन/सर्वेक्षण ("+ data.size() +")");
                }
            }
        });

        rb_chapakal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    structureId = "5";
                    loadPanchayatSpinner();
                    populateLocalData();
                    setButtonTitle();
                    tv_header.setText("चापाकल का सत्यापन/सर्वेक्षण ("+ data.size() +")");
                }
            }
        });

    }

    public void initialize(){
        dataBaseHelper = new DataBaseHelper(this);

        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_header=(TextView) findViewById(R.id.tv_header);

        btn_add_pond=(Button) findViewById(R.id.btn_add_pond);
        btn_view_pond_map=(Button) findViewById(R.id.btn_view_pond_map);
        ll_btn=(LinearLayout) findViewById(R.id.ll_btn);
        ll_btn.setVisibility(View.GONE);

        spin_struct_type=(Spinner) findViewById(R.id.spin_struct_type);
        spin_struct_type.setOnItemSelectedListener(this);

        spin_panchayat=(Spinner) findViewById(R.id.spin_panchayat);
        spin_panchayat.setOnItemSelectedListener(this);

        rb_well= findViewById(R.id.rb_well);
        rb_chapakal= findViewById(R.id.rb_chapakal);

        ll_block= findViewById(R.id.ll_block);
        spin_block= findViewById(R.id.spin_block);
        spin_block.setOnItemSelectedListener(this);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void loadStructureSpinner(){
        ArrayAdapter adaptor = new ArrayAdapter(this, android.R.layout.simple_spinner_item, strucuteOption);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_struct_type.setAdapter(adaptor);
    }

    public void populateLocalData(){
        data=dataBaseHelper.getWellsInspectionDetail(panchayatCode, structureId);
        String str = structureId.equals("2") ? "कुँआ" : "चापाकल";
        tv_header.setText(str+" का सत्यापन/सर्वेक्षण ("+ data.size() +")");

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new WellAdaptor(WellListActivity.this, data, panchayatCode, panchayatName, structureId);
            listView.setAdapter(adaptor_showedit_listDetail);
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

    public void loadPanchayatSpinner() {
        panchayatList = dataBaseHelper.getPanchayt(blockCode);
        panchayatNameArray = new ArrayList<String>();
        panchayatNameArray.add("-Select-");
        int i = 0;
        for (PanchayatData panchayat_list : panchayatList) {
            panchayatNameArray.add(panchayat_list.getPname());
            i++;
        }
        panchayatadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panchayatNameArray);
        panchayatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_panchayat.setAdapter(panchayatadapter);


    }

    private void setReportListViewData()
    {
        data=dataBaseHelper.getWellsInspectionDetail(panchayatCode, structureId);
        adaptor_showedit_listDetail =new WellAdaptor(WellListActivity.this,data, panchayatCode, panchayatName, structureId);
        listView.setAdapter(adaptor_showedit_listDetail);
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
                adaptor_showedit_listDetail = new WellAdaptor(WellListActivity.this, data, panchayatCode, panchayatName, structureId);
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
            case R.id.spin_struct_type:
                if (position > 0) {

                    if(position == 1){
                        structureId = "2";
                    }else if (position == 2){
                        structureId = "5";
                    }
                    loadPanchayatSpinner();
                    populateLocalData();
                    setButtonTitle();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void setButtonTitle(){
        if(structureId.equals("2")){
            btn_add_pond.setText("नया कुँआ जोड़ें");
        }else if(structureId.equals("5")){
            btn_add_pond.setText("नया चापाकल जोड़ें");
        }
    }
}
