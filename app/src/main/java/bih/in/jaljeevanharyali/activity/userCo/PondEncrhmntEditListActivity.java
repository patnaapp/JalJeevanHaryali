package bih.in.jaljeevanharyali.activity.userCo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondListEditActivity;
import bih.in.jaljeevanharyali.adapter.PondEditAdaptor;
import bih.in.jaljeevanharyali.adapter.PondEncrhmntEditAdaptor;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.PondEntity;

public class PondEncrhmntEditListActivity extends Activity {

    ListView listView;
    PondEncrhmntEditAdaptor adaptor_showedit_listDetail;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<PondEncroachmentEntity> data;
    String listid, version="";
    TextView tv_Norecord,tv_title;

    String structureType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pond_encrhmnt_edit_list);

        Intent intent = getIntent();
        structureType = intent.getStringExtra("structureType");

        initialization();

        tv_title.setText(structureType.equals("pond") ? "जल संरचनाओं का अतिक्रमण जोड़े/हटाए" : "कुंआ का अतिक्रमण जोड़े/हटाए");
    }

    private void initialization(){
        listView=(ListView)findViewById(R.id.listviewshow);
        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_title=(TextView) findViewById(R.id.tv_title);

        dataBaseHelper = new DataBaseHelper(this);
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
            adaptor_showedit_listDetail = new PondEncrhmntEditAdaptor(PondEncrhmntEditListActivity.this, data, structureType);
            adaptor_showedit_listDetail.dataBaseHelper = dataBaseHelper;
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    private void setReportListViewData()
    {
        data=dataBaseHelper.getPondsEncrhmntUpdatedDetail(structureType);
        adaptor_showedit_listDetail =new PondEncrhmntEditAdaptor(PondEncrhmntEditListActivity.this,data, structureType);
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
            adaptor_showedit_listDetail = new PondEncrhmntEditAdaptor(PondEncrhmntEditListActivity.this, data, structureType);
            listView.setAdapter(adaptor_showedit_listDetail);
        }else{
            listView.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}
