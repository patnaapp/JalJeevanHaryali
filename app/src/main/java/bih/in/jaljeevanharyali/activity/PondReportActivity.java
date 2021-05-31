package bih.in.jaljeevanharyali.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.PondAdapter;
import bih.in.jaljeevanharyali.adapter.PondReportAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondWellReportEntity;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class PondReportActivity extends Activity implements AdapterView.OnItemSelectedListener {

    ListView listView;
    PondReportAdapter adaptor_showedit_listDetail;
    Spinner spin_panchayat;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondWellReportEntity> data;
    String listid;
    TextView tv_Norecord,tv_header;
    Button btn_add_pond,btn_view_pond_map;
    LinearLayout ll_btn;

    //ArrayList<PondEntity> reportList=new ArrayList<>();
    //UserDetails userInfo;

    String blockCode,DistrictCode,panchayatCode,panchayatName="", userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond_report);
        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_header=(TextView) findViewById(R.id.tv_header);

        dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        blockCode = intent.getStringExtra("blockCode");
        DistrictCode = intent.getStringExtra("DistrictCode");
        userId = intent.getStringExtra("userId");

        tv_Norecord.setVisibility(View.GONE);

        if(Utiilties.isOnline(this)){
            new FetchPondLakeReport(blockCode,userId).execute("");
        }else{
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            //chk_msg_save("");
        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub
//                Intent i=new Intent(getApplicationContext(),PondInspectionActivity.class);
//                i.putExtra("id", data.get(position).getId());
//                i.putExtra("panchayatCode", panchayatCode);
//                i.putExtra("panchayatName", panchayatName);
//                //listid=data.get(position).getSlno();
//                startActivity(i);
//                finish();

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

    public void chk_msg_save(String msg) {
        // final String wantToUpdate;
        android.app.AlertDialog.Builder ab = new android.app.AlertDialog.Builder(this);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.logo1);
        ab.setTitle("Data Saved");
        ab.setMessage(R.string.no_internet_connection);
        Dialog dialog = new Dialog(this);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();

            }
        });

        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }

    public void populateLocalData(){


        if(data.size()> 0){
            tv_header.setText("जल संरचनाओं का सत्यापित रिपोर्ट  ("+data.size()+")");
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new PondReportAdapter(PondReportActivity.this, data);
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(data != null){
            if(data.size()> 0){
                tv_Norecord.setVisibility(View.GONE);
                listView.setVisibility(View.VISIBLE);
                listView.invalidate();
                //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
                adaptor_showedit_listDetail = new PondReportAdapter(PondReportActivity.this, data);
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
//                if (pos > 0) {
//                    panchayatCode = panchayatList.get(pos - 1).getPcode();
//                    panchayatName = panchayatList.get(pos - 1).getPname();
//                    populateLocalData();
//                    ll_btn.setVisibility(View.VISIBLE);
//                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class FetchPondLakeReport extends AsyncTask<String, Void, ArrayList<PondWellReportEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(PondReportActivity.this);
        String blockId;
        String userId;

        FetchPondLakeReport(String blockId, String userId){
            this.blockId = blockId;
            this.userId = userId;
        }
        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("जल संरचनाओं का रिपोर्ट लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PondWellReportEntity> doInBackground(String...arg) {
            //String blockId = userInfo.getBlockCode();

            return WebServiceHelper.getPondLakeWellReport("1", "pond", userId);

        }

        @Override
        protected void onPostExecute(ArrayList<PondWellReportEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            if(result.size() > 0){
                data = result;
                populateLocalData();
            }else{
                Toast.makeText(getApplicationContext(), "कोई रिकॉर्ड अपलोड नहीं हुआ है",Toast.LENGTH_SHORT).show();
                listView.setVisibility(View.GONE);
                tv_Norecord.setVisibility(View.VISIBLE);
            }
        }
    }
}
