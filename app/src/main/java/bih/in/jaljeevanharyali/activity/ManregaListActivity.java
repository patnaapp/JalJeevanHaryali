package bih.in.jaljeevanharyali.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.ManregaEditAdaptor;
import bih.in.jaljeevanharyali.adapter.PondAdapter;
import bih.in.jaljeevanharyali.adapter.PondEditAdaptor;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;

public class ManregaListActivity extends Activity {

    ListView listView;
    LinearLayout ll_panchayat;
    ManregaEditAdaptor adaptor_showedit_listDetail;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<ManregaSchemeDetail> data;
    String listid;
    TextView tv_Norecord,tv_header;

    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manrega_list);
        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_header=(TextView) findViewById(R.id.tv_header);

        dataBaseHelper = new DataBaseHelper(this);

        getUserDetail();

        dialog=new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading...");
        setReportListViewData();

        tv_header.setText("योजना विवरण सूची ("+data.size()+")");

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new ManregaEditAdaptor(ManregaListActivity.this, data, userInfo);
            adaptor_showedit_listDetail.dataBaseHelper = dataBaseHelper;
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

    private void setReportListViewData()
    {
        data=dataBaseHelper.getManregaDetailList();
        adaptor_showedit_listDetail =new ManregaEditAdaptor(ManregaListActivity.this,data, userInfo);
        listView.setAdapter(adaptor_showedit_listDetail);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new ManregaEditAdaptor(ManregaListActivity.this, data, userInfo);
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }


    }

    public void deletePondData(){

    }
}
