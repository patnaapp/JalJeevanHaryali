package bih.in.jaljeevanharyali.activity.remarkUpdate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.adapter.UpdateRemarkAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.StructureRemarkEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;

public class RemarkUpdateEditActivity extends Activity {

    RecyclerView rv_data;
    TextView tv_Norecord,tv_header;

    DataBaseHelper dataBaseHelper;
    ArrayList<StructureRemarkEntity> data;

    UserDetails userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_update_edit);

        initialize();

        populateLocalData();
    }

    public void initialize(){

        dataBaseHelper = new DataBaseHelper(this);

        rv_data=findViewById(R.id.rv_data);

        tv_Norecord= findViewById(R.id.tv_Norecord);
        tv_header= findViewById(R.id.tv_header);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void populateLocalData(){
        data = dataBaseHelper.getStructureRemarkDetail("0","0");
        tv_header.setText(AppConstant.UPDATEREMARK+" सूची ("+data.size()+")");

        if(data != null && data.size()> 0){
            rv_data.setLayoutManager(new LinearLayoutManager(this));
            UpdateRemarkAdapter adapter = new UpdateRemarkAdapter(this, data, tv_header, true);
            rv_data.setAdapter(adapter);
            tv_Norecord.setVisibility(View.GONE);
            rv_data.setVisibility(View.VISIBLE);
        }else{
            rv_data.setVisibility(View.GONE);
            tv_Norecord.setVisibility(View.VISIBLE);
        }
    }
}
