package bih.in.jaljeevanharyali.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.WellEditAdaptor;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEntity;

public class WellListEditActivity extends Activity {

    ListView listView;
    WellEditAdaptor adaptor_showedit_listDetail;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondEntity> data;
    String listid,version="";
    TextView tv_Norecord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_well_list_edit);
        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        version = getAppVersion();
        dataBaseHelper = new DataBaseHelper(this);

        dialog=new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading...");
        setReportListViewData();
        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new WellEditAdaptor(WellListEditActivity.this, data, version);
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
//                i.putExtra("InspectionID", data.get(position).getInspectionID());
//                i.putExtra("BlockID", data.get(position).getBlockID());
//                i.putExtra("BlockName", data.get(position).getBlockName());
//                i.putExtra("RajswaThanaNumber", data.get(position).getRajswaThanaNumber());
//                i.putExtra("VillageID", data.get(position).getVillageID());
//                i.putExtra("VillageName", data.get(position).getVillageName());
//                i.putExtra("DistID", data.get(position).getDistID());
//                i.putExtra("Latitude", data.get(position).getLatitude());
//                i.putExtra("Longitude", data.get(position).getLongitude());
//                listid=data.get(position).getSlno();
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

    private void setReportListViewData()
    {
        data=dataBaseHelper.getWellsInspectionUpdatedDetail();
        adaptor_showedit_listDetail =new WellEditAdaptor(WellListEditActivity.this,data, version);
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
            adaptor_showedit_listDetail = new WellEditAdaptor(WellListEditActivity.this, data, version);
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }


    }
}
