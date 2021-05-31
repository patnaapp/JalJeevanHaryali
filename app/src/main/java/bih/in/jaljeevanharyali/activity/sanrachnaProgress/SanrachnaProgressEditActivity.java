package bih.in.jaljeevanharyali.activity.sanrachnaProgress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondListEditActivity;
import bih.in.jaljeevanharyali.adapter.PondEditAdaptor;
import bih.in.jaljeevanharyali.adapter.SanrachnaProgressEditAdaptor;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaDataEntity;

public class SanrachnaProgressEditActivity extends Activity {

    ListView listView;
    LinearLayout ll_panchayat;
    SanrachnaProgressEditAdaptor adaptor_showedit_listDetail;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<SanrachnaDataEntity> data;
    String listid, version="";
    TextView tv_Norecord,tv_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sanrachna_progress_edit);

        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_title=(TextView) findViewById(R.id.tv_title);

        dataBaseHelper = new DataBaseHelper(this);
        //version = getAppVersion();
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
            adaptor_showedit_listDetail = new SanrachnaProgressEditAdaptor(this, data);
            adaptor_showedit_listDetail.dataBaseHelper = dataBaseHelper;
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    private void updateTitle(){
        tv_title.setText("संरचनाओं के कार्यो का विवरण सूची  ("+data.size()+")");
    }

    private void setReportListViewData()
    {
        data=dataBaseHelper.getSanrachnaInspectionUpdatedDetail();
        adaptor_showedit_listDetail =new SanrachnaProgressEditAdaptor(this,data);
        listView.setAdapter(adaptor_showedit_listDetail);
        updateTitle();
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
            adaptor_showedit_listDetail = new SanrachnaProgressEditAdaptor(this, data);
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}
