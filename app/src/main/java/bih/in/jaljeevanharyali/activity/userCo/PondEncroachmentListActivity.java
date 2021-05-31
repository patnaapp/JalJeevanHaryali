package bih.in.jaljeevanharyali.activity.userCo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import bih.in.jaljeevanharyali.adapter.PondEncroachmentAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.PondEntity;

public class PondEncroachmentListActivity extends Activity implements AdapterView.OnItemSelectedListener{

    ListView listView;
    PondEncroachmentAdapter adaptor_showedit_listDetail;
    Spinner spin_panchayat;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondEncroachmentEntity> data;
    String listid;
    TextView tv_Norecord, tv_heading;
    Button btn_add_pond,btn_view_pond_map;
    LinearLayout ll_btn;

    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<String> panchayatNameArray;
    ArrayAdapter<String> panchayatadapter;

    String blockCode,DistrictCode,panchayatCode,panchayatName="";
    String structureType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond_encroachment_list);

        setupUi();

        loadPanchayatSpinner();

        Intent intent = getIntent();
        structureType = intent.getStringExtra("structureType");
        tv_heading.setText(structureType.equals("pond") ? "जल संरचनाओं का अतिक्रमण जोड़े/हटाए" : "कुंआ का अतिक्रमण जोड़े/हटाए");
    }

    private void setupUi(){
        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_heading=(TextView) findViewById(R.id.tv_heading);

        btn_add_pond=(Button) findViewById(R.id.btn_add_pond);
        btn_view_pond_map=(Button) findViewById(R.id.btn_view_pond_map);

        spin_panchayat=(Spinner) findViewById(R.id.spin_panchayat);
        spin_panchayat.setOnItemSelectedListener(this);

        dataBaseHelper = new DataBaseHelper(this);
        Intent intent = getIntent();
        blockCode = intent.getStringExtra("blockCode");
        DistrictCode = intent.getStringExtra("DistrictCode");
    }

    public void populateLocalData(){

        data=dataBaseHelper.getPondsEncrhmntDetail(panchayatCode, structureType);

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            adaptor_showedit_listDetail = new PondEncroachmentAdapter(PondEncroachmentListActivity.this, data, panchayatCode, panchayatName, structureType);
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
        panchayatNameArray.add("-select-");
        int i = 0;
        for (PanchayatData panchayat_list : panchayatList) {
            panchayatNameArray.add(panchayat_list.getPname());
            i++;
        }
        panchayatadapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, panchayatNameArray);
        panchayatadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin_panchayat.setAdapter(panchayatadapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
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

    @Override
    protected void onResume() {
        super.onResume();
        if(panchayatCode != null){
            if(data.size()> 0){
                tv_Norecord.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.invalidate();
                //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
                adaptor_showedit_listDetail = new PondEncroachmentAdapter(PondEncroachmentListActivity.this, data, panchayatCode, panchayatName, structureType);
                listView.setAdapter(adaptor_showedit_listDetail);
            }else{
                listView.setVisibility(View.GONE);
                tv_Norecord.setVisibility(View.VISIBLE);
            }
        }

    }
}
