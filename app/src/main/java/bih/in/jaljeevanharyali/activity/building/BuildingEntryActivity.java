package bih.in.jaljeevanharyali.activity.building;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Spinner;

import bih.in.jaljeevanharyali.R;

public class BuildingEntryActivity extends Activity {

    Spinner spn_dist,spn_block,spn_area,spn_panchayat,spn_ward,spn_building_type,spn_department;
    EditText et_building_name,et_building_id,et_consumer_id,et_chat_area;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building_entry);
    }
}
