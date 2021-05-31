package bih.in.jaljeevanharyali.activity.userCo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondReportActivity;
import bih.in.jaljeevanharyali.adapter.EncroachmentReportAdapter;
import bih.in.jaljeevanharyali.adapter.PondEncroachmentAdapter;
import bih.in.jaljeevanharyali.adapter.PondReportAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.PondWellReportEntity;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class EncroachmentReportActivity extends Activity {

    ListView listView;
    EncroachmentReportAdapter adaptor_showedit_listDetail;
    Spinner spin_panchayat;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondEncroachmentEntity> data;
    String listid;
    TextView tv_Norecord,tv_header;
    Button btn_add_pond,btn_view_pond_map;
    LinearLayout ll_btn;

    String blockCode,DistrictCode,panchayatCode,panchayatName="", userId, structureType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encroachment_report);

        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_header=(TextView) findViewById(R.id.tv_header);

        dataBaseHelper = new DataBaseHelper(this);

        Intent intent = getIntent();
        blockCode = intent.getStringExtra("blockCode");
        DistrictCode = intent.getStringExtra("DistrictCode");
        userId = intent.getStringExtra("userId");
        structureType = intent.getStringExtra("structureType");

        tv_Norecord.setVisibility(View.GONE);

        if(Utiilties.isOnline(this)){
            new EncroachmentReportActivity.FetchEncroachmentReport(blockCode,userId).execute("");
        }else{
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show();
            //chk_msg_save("");
        }
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
            String title = structureType.equals("pond") ? "जल संरचनाओं का अतिक्रमण रिपोर्ट" : "कुंआ का अतिक्रमण रिपोर्ट";
            title += "  ("+data.size()+")";
            tv_header.setText(title);
            tv_Norecord.setVisibility(View.GONE);
            listView.setVisibility(View.VISIBLE);
            listView.invalidate();
            //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
            adaptor_showedit_listDetail = new EncroachmentReportAdapter(EncroachmentReportActivity.this, data);
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
                adaptor_showedit_listDetail = new EncroachmentReportAdapter(EncroachmentReportActivity.this, data);
                listView.setAdapter(adaptor_showedit_listDetail);
            }else{
                listView.setVisibility(View.GONE);
                tv_Norecord.setVisibility(View.VISIBLE);
            }
        }

    }

    private class FetchEncroachmentReport extends AsyncTask<String, Void, ArrayList<PondEncroachmentEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(EncroachmentReportActivity.this);
        String blockId;
        String userId;

        FetchEncroachmentReport(String blockId, String userId){
            this.blockId = blockId;
            this.userId = userId;
        }
        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("रिपोर्ट लोड हो रहा है...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<PondEncroachmentEntity> doInBackground(String...arg) {
            //String blockId = userInfo.getBlockCode();

            return WebServiceHelper.getEncroachmentReport(blockId, structureType, userId);

        }

        @Override
        protected void onPostExecute(ArrayList<PondEncroachmentEntity> result) {
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
