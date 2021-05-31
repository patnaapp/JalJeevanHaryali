package bih.in.jaljeevanharyali.activity.grievance;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.TrackAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.UploadComplainEntity;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class TrackGrievanceActivity extends Activity {

    ListView listView;
    TrackAdapter adaptor_showedit_listDetail;

    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<UploadComplainEntity> data;
    String listid;
    TextView tv_Norecord,tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_grievance);

        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_title=(TextView) findViewById(R.id.tv_title);

        dataBaseHelper = new DataBaseHelper(this);

        dialog=new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Loading Grievances...");

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (!GlobalVariables.isOffline && !Utiilties.isOnline(this)) {

            AlertDialog.Builder ab = new AlertDialog.Builder(this);
            ab.setMessage(Html.fromHtml(
                    "<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
            ab.show();

        }else{
            new GrievanceDataTask().execute();
        }
    }

    public void setReportListViewData()
    {
        //data=dataBaseHelper.getAllEntryBenMemberDetail();
        adaptor_showedit_listDetail =new TrackAdapter(this,data);
        listView.setAdapter(adaptor_showedit_listDetail);
    }
    @Override
    protected void onResume() {
        super.onResume();


    }

    private class GrievanceDataTask extends AsyncTask<String, Void, ArrayList<UploadComplainEntity>> {

        private final ProgressDialog dialog = new ProgressDialog(TrackGrievanceActivity.this);

        private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(TrackGrievanceActivity.this).create();

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading Grievances...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<UploadComplainEntity> doInBackground(String... param) {
            return WebServiceHelper.getGrievanceList(PreferenceManager.getDefaultSharedPreferences(TrackGrievanceActivity.this).getString("uid", ""));
        }

        @Override
        protected void onPostExecute(ArrayList<UploadComplainEntity> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            if (result != null) {

                if(result.size()> 0){
                    data = result;
                    tv_title.setText("पंजीकृत शिकायत सूची ("+result.size()+") ");
                    setReportListViewData();
                    if(data.size()> 0){
                        tv_Norecord.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        listView.invalidate();
                        //((DraftAdapter)dataListView.getAdapter()).notifyDataSetChanged();
                        adaptor_showedit_listDetail = new TrackAdapter(TrackGrievanceActivity.this, data);
                        listView.setAdapter(adaptor_showedit_listDetail);
                    }else{
                        listView.setVisibility(View.GONE);
                        tv_Norecord.setVisibility(View.VISIBLE);
                    }
                }

            }

        }
    }
}
