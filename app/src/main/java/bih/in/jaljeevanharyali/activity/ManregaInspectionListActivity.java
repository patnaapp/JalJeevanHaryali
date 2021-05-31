package bih.in.jaljeevanharyali.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.ManregaEditAdaptor;
import bih.in.jaljeevanharyali.adapter.ManregaInspectionListAdaptor;
import bih.in.jaljeevanharyali.adapter.PondAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.BlockName;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;

public class ManregaInspectionListActivity extends Activity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    LinearLayout ll_panchayat,ll_block;
    ManregaInspectionListAdaptor adaptor_showedit_listDetail;
    Spinner spin_panchayat,spin_awayab_type,spin_block;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<ManregaSchemeDetail> data;
    String listid;
    TextView tv_Norecord,tv_title;

    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<Block> blockList = new ArrayList<Block>();

    ArrayList<String> panchayatNameArray;
    ArrayAdapter<String> panchayatadapter;

    ArrayList<Abyab> abyabList = new ArrayList<Abyab>();
    ArrayList<String> abyabNameArray;
    ArrayAdapter<String>abyabadapter;

    String blockCode,DistrictCode,panchayatCode,panchayatName="";
    String abyabId = "";
    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_manrega_inspection_list);

        initialize();

        getUserDetail();

//        Intent intent = getIntent();
//        blockCode = intent.getStringExtra("blockCode");
//        DistrictCode = intent.getStringExtra("DistrictCode");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getApplicationContext(),ManregaInspectionActivity.class);
                i.putExtra("id", data.get(position).getId());
                startActivity(i);
                //finish();
            }


        });

    }

    public void initialize(){
        dataBaseHelper = new DataBaseHelper(this);

        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_title=(TextView) findViewById(R.id.tv_title);

        ll_block= findViewById(R.id.ll_block);

        spin_panchayat=(Spinner) findViewById(R.id.spin_panchayat);
        spin_block=(Spinner) findViewById(R.id.spin_block);

        spin_panchayat.setOnItemSelectedListener(this);
        spin_block.setOnItemSelectedListener(this);

        spin_awayab_type=(Spinner) findViewById(R.id.spin_awayab_type);
        spin_awayab_type.setOnItemSelectedListener(this);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void populateLocalData(){

        data=dataBaseHelper.getManregaDetailListPanchayatWise(panchayatCode, abyabId);
        //Toast.makeText(this, String.valueOf(data.size()), Toast.LENGTH_SHORT).show();
        tv_title.setText("योजना विवरण सूची ("+data.size()+")");

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);

            adaptor_showedit_listDetail = new ManregaInspectionListAdaptor(ManregaInspectionListActivity.this, data, userInfo);
            listView.setAdapter(adaptor_showedit_listDetail);
            adaptor_showedit_listDetail.notifyDataSetChanged();
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    public void loadPanchayatSpinner() {
        panchayatList = dataBaseHelper.getPanchayt(blockCode);
        panchayatNameArray = new ArrayList<String>();
        panchayatNameArray.add("-चयन करे-");
        int i = 0;
        for (PanchayatData panchayat_list : panchayatList) {
            panchayatNameArray.add(panchayat_list.getPname());
            i++;
        }
        panchayatadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panchayatNameArray);
        panchayatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_panchayat.setAdapter(panchayatadapter);

    }

    public void loadBlockSpinner() {
        blockList = dataBaseHelper.getBlock(userInfo.getDistrictCode());
        ArrayList<String> block = new ArrayList();
        block.add("-चयन करे-");
        int i = 0;
        for (Block info : blockList) {
            block.add(info.getBlockName());
            i++;
        }
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, block);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_block.setAdapter(adapter);
    }

    public void loadabyabSpinner() {
        abyabList = dataBaseHelper.getAbyab("");
        abyabNameArray = new ArrayList<String>();
        abyabNameArray.add("-चयन करे-");
        int i = 0, setId= 0;
        for (Abyab sub_abyb : abyabList) {
            abyabNameArray.add(sub_abyb.getAbyab_name());
        }
        abyabadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, abyabNameArray);
        abyabadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_awayab_type.setAdapter(abyabadapter);
    }

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dataBaseHelper.getUserDetails(username, password);

        if(userInfo.getUserrole().equals(AppConstant.DEPARTMENT)){
            loadBlockSpinner();
            ll_block.setVisibility(View.VISIBLE);
        }else{
            blockCode = userInfo.getBlockCode();
            loadPanchayatSpinner();
            loadabyabSpinner();
            ll_block.setVisibility(View.GONE);
        }

    }

    private void removeFromAdapter(String position){
        data.remove(position);
        adaptor_showedit_listDetail.notifyDataSetChanged();
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(panchayatCode != null) {
            if(data.size()> 0){
                tv_Norecord.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.invalidate();
                adaptor_showedit_listDetail = new ManregaInspectionListAdaptor(ManregaInspectionListActivity.this, data, userInfo);
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
                    blockCode = blockList.get(position - 1).getBlockCode().trim();
                    //BlockName = blockList.get(pos - 1).getBlockName().trim();
                    abyabId = "";
                    panchayatCode = "";

                    loadPanchayatSpinner();
                    loadabyabSpinner();
                }
                break;
            case R.id.spin_panchayat:
                if (position > 0) {
                    panchayatCode = panchayatList.get(position - 1).getPcode().trim();
                    panchayatName = panchayatList.get(position - 1).getPname().trim();
                    abyabId = "";
                    Log.d("manrega", "Panchayat Code: "+panchayatCode);
                    spin_awayab_type.setSelection(0);
                    populateLocalData();
                }
                break;
            case R.id.spin_awayab_type:
                if (position > 0) {
                    Abyab abayab = abyabList.get(position - 1);
                    abyabId = abayab.getAbyab_Code();
                    Log.d("manrega", "Abyab Name: "+abayab.getAbyab_name());
                    Log.d("manrega", "Abyab Code: "+abayab.getAbyab_Code());
                    populateLocalData();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
