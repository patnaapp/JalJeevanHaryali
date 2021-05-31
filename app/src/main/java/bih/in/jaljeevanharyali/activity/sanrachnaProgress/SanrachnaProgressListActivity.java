package bih.in.jaljeevanharyali.activity.sanrachnaProgress;

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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.OtherDeptInspectionActivity;
import bih.in.jaljeevanharyali.activity.OtherDeptInspectionListActivity;
import bih.in.jaljeevanharyali.adapter.OtherDeptInspectionListAdaptor;
import bih.in.jaljeevanharyali.adapter.SanrachnaProgressListAdaptor;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaDataEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;

public class SanrachnaProgressListActivity extends Activity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    LinearLayout ll_panchayat;
    SanrachnaProgressListAdaptor adaptor_showedit_listDetail;
    Spinner spin_panchayat;
    ProgressDialog dialog;

    DataBaseHelper dataBaseHelper;
    ArrayList<SanrachnaDataEntity> data = new ArrayList<SanrachnaDataEntity>();
    String listid;
    TextView tv_Norecord;

    ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
    ArrayList<String> panchayatNameArray;
    ArrayAdapter<String> panchayatadapter;

    String blockCode,DistrictCode,panchayatCode="NA",panchayatName="";
    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanrachna_progress_list);

        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);

        spin_panchayat=(Spinner) findViewById(R.id.spin_panchayat);
        spin_panchayat.setOnItemSelectedListener(this);

        dataBaseHelper = new DataBaseHelper(this);
        getUserDetail();

        Intent intent = getIntent();
        blockCode = intent.getStringExtra("blockCode");
        DistrictCode = intent.getStringExtra("DistrictCode");

        loadPanchayatSpinner();
        //populateLocalData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getApplicationContext(), OtherDeptInspectionActivity.class);
                i.putExtra("id", data.get(position).getId());
                startActivity(i);
                //finish();

            }


        });
    }

    public void populateLocalData(){

        data=dataBaseHelper.getSanrachnaDetail(panchayatCode);
        //Toast.makeText(this, String.valueOf(data.size()), Toast.LENGTH_SHORT).show();

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            //listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new SanrachnaProgressListAdaptor(this, data, userInfo);
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

    private void getUserDetail(){
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        userInfo = dataBaseHelper.getUserDetails(username, password);

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
                adaptor_showedit_listDetail = new SanrachnaProgressListAdaptor(this, data, userInfo);
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
            case R.id.spin_panchayat:
                int pos = position;
                if (pos > 0) {
                    panchayatCode = panchayatList.get(pos - 1).getPcode().trim();
                    panchayatName = panchayatList.get(pos - 1).getPname().trim();
                    Log.d("manrega", "Panchayat Code: "+panchayatCode);
                    populateLocalData();
                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
