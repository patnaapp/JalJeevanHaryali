package bih.in.jaljeevanharyali.activity.nursery;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondInspectionActivity;
import bih.in.jaljeevanharyali.adapter.NurseryAdapter;
import bih.in.jaljeevanharyali.adapter.NurseryEditAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.utility.AppConstant;

public class NursuryListEditActivity extends Activity {
    RecyclerView rv_data;
    LinearLayout ll_panchayat;
    NurseryEditAdapter adaptor_showedit_listDetail;
    ProgressDialog dialog;
    DataBaseHelper dataBaseHelper;
    ArrayList<NurseryEntity> data;
    String listid, version="";
    TextView tv_Norecord,tv_title;

    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nursury_list_edit);

        tv_Norecord=(TextView) findViewById(R.id.tv_Norecord);
        tv_title=(TextView) findViewById(R.id.tv_title);

        rv_data= findViewById(R.id.rv_data);

        dataBaseHelper = new DataBaseHelper(this);

        type = getIntent().getStringExtra("type");

        if(type.equals(AppConstant.NURSURY)){
            tv_title.setText("पौधशाला सृजन का सत्यापन/सर्वेक्षण");
        }else{
            tv_title.setText("भवन का सत्यापन/सर्वेक्षण");
        }

        loadAdapter();
    }

    public void loadAdapter(){
        data=dataBaseHelper.getNurseryInspectionDetail("", type, "1");

        if(data.size()> 0){
            tv_Norecord.setVisibility(View.GONE);
            rv_data.setVisibility(View.VISIBLE);

            rv_data.setLayoutManager(new LinearLayoutManager(this));
            adaptor_showedit_listDetail = new NurseryEditAdapter(this, data);
            rv_data.setAdapter(adaptor_showedit_listDetail);

        }else{
            rv_data.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        loadAdapter();
    }
}
