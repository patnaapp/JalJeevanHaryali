package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.PondEditAdaptor;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEntity;

public class PondListEditActivity extends Activity {

    ListView listView;
    LinearLayout ll_panchayat;
    PondEditAdaptor adaptor_showedit_listDetail;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondEntity> data;
    String listid, version="";
    TextView tv_Norecord,tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond_list_edit);

        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_title=(TextView) findViewById(R.id.tv_title);

        dataBaseHelper = new DataBaseHelper(this);
        version = getAppVersion();
        dialog=new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading...");
        setReportListViewData();
        if(data.size()> 0){
            //updateTitle();
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new PondEditAdaptor(PondListEditActivity.this, data, version);
            adaptor_showedit_listDetail.dataBaseHelper = dataBaseHelper;
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
                Intent i=new Intent(getApplicationContext(),PondInspectionActivity.class);
                i.putExtra("id", data.get(position).getId());
                i.putExtra("panchayatCode", "");
                i.putExtra("panchayatName", "");
//                i.putExtra("BlockID", data.get(position).getBlockID());
//                i.putExtra("BlockName", data.get(position).getBlockName());
//                i.putExtra("RajswaThanaNumber", data.get(position).getRajswaThanaNumber());
//                i.putExtra("VillageID", data.get(position).getVillageID());
//                i.putExtra("VillageName", data.get(position).getVillageName());
//                i.putExtra("DistID", data.get(position).getDistID());
//                i.putExtra("Latitude", data.get(position).getLatitude());
//                i.putExtra("Longitude", data.get(position).getLongitude());
                listid=data.get(position).getSlno();
                //Toast.makeText(ShowEdit.this, "RID:" + data.get(position).getReferenceID(), Toast.LENGTH_SHORT).show();
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

    }

    public String getAppVersion(){
        try {

            String version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
            return version;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    private void updateTitle(){
        tv_title.setText("जल संरचनाओं का सत्यापन/सर्वेक्षण  ("+data.size()+")");
    }

    private void removeFromAdapter(String position){
        data.remove(position);
        adaptor_showedit_listDetail.notifyDataSetChanged();

    }

    private void setReportListViewData()
    {
        data=dataBaseHelper.getPondsInspectionUpdatedDetail();
        adaptor_showedit_listDetail =new PondEditAdaptor(PondListEditActivity.this,data, version);
        listView.setAdapter(adaptor_showedit_listDetail);
    }
    @Override
    protected void onResume() {
        super.onResume();
        if(data.size()> 0){
            //tv_title.setText("जल संरचनाओं का सत्यापन/सर्वेक्षण  ("+data.size()+")");
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new PondEditAdaptor(PondListEditActivity.this, data, version);
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    public void deletePondData(){

    }
}

