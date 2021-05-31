package bih.in.jaljeevanharyali.activity.grievance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PanchayatEntity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.District;
import bih.in.jaljeevanharyali.entity.GrievanceEntryDetail;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.StructureType;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class OtherTypeGrievActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spn_griev_type,spn_district,spn_block,spn_awyab,spn_panchayat;
    LinearLayout ll_other_entry;
    Button btn_proceed;
    EditText et_oth_Name,txt_complain;

    DataBaseHelper localdbhelper;
    ArrayList<District> DistrictList;
    ArrayList<Block> blockList;
    ArrayList<Abyab> abyabList;

    ArrayList<PanchayatData> PanchayatList = new ArrayList<>();

    String grievStatusArray[] = {"-चयन करे-","योजनाओं से सम्बंधित", "संरचनाओं से सम्बंधित", "अन्य"};
    String distCode,distName,blockCode,blockName,grievType,awyabId,awyabName,panchayatCode,panchayatName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_type_griev);

        setup();
        loadDistrictSpinnerdata();

        abyabList = localdbhelper.getAbyabList();
        if(abyabList.size() > 0){
            loadAwayabSpinnerdata();
        }else{
            new SyncAwayabListData().execute();
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, grievStatusArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_griev_type.setAdapter(adapter);

        ImageView img_back = findViewById(R.id.img_back);
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setup(){
        localdbhelper=new DataBaseHelper(this);

        spn_district = findViewById(R.id.spn_district);
        spn_block = findViewById(R.id.spn_block);
        spn_griev_type = findViewById(R.id.spn_griev_type);
        spn_awyab = findViewById(R.id.spn_awyab);
        spn_panchayat = findViewById(R.id.spn_panchayat);

        et_oth_Name = findViewById(R.id.et_oth_Name);
        txt_complain = findViewById(R.id.txt_complain);

        ll_other_entry = findViewById(R.id.ll_other_entry);

        btn_proceed = findViewById(R.id.btn_proceed);


        spn_district.setOnItemSelectedListener(this);
        spn_block.setOnItemSelectedListener(this);
        spn_griev_type.setOnItemSelectedListener(this);
        spn_awyab.setOnItemSelectedListener(this);
        spn_panchayat.setOnItemSelectedListener(this);

        ll_other_entry.setVisibility(View.GONE);
    }

    public void loadAwayabSpinnerdata() {
        ArrayList<String> array = new ArrayList<>();
        array.add("-चयन करे-");
        for (Abyab abyab : abyabList) {
            array.add(abyab.getAbyab_name());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.dropdownlist, array);
        adapter.setDropDownViewResource(R.layout.dropdownlist);
        spn_awyab.setAdapter(adapter);
    }

    public void loadDistrictSpinnerdata() {
        DistrictList = localdbhelper.getDistrict();
        ArrayList<String> array = new ArrayList<>();
        array.add("-चयन करे-");
        int i = 1;
        for (District dist : DistrictList) {
            array.add(dist.get_DistName());
            i++;
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_district.setAdapter(adapter);
        distCode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("distcode", "");
        int setID=0;
        for ( i = 0; i < DistrictList.size(); i++) {

            if (DistrictList.get(i).get_DistCode().equalsIgnoreCase(distCode)) {
                setID = i;
            }
            if(setID!=0) {
                spn_district.setSelection(setID+1);
                //spn_district.setEnabled(false);
            }
        }
    }

    public void loadBlockSpinnerdata() {
        blockList = localdbhelper.getBlock(distCode);
        ArrayList<String> array = new ArrayList<>();
        array.add("-चयन करे-");
        for (Block block : blockList) {
            array.add(block.getBlockName());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_block.setAdapter(adapter);
    }

    public void setPanchayatSpinnerData()
    {
        DataBaseHelper placeData = new DataBaseHelper(this);
        PanchayatList=placeData.getPanchayatLocal(blockCode);
        String isfound="n";

        if(PanchayatList.size()>0) {
            loadPanchayatSpinnerData(PanchayatList);
        }else{
            new SyncPanchayatData().execute();
        }

    }
    private void loadPanchayatSpinnerData(ArrayList<PanchayatData> pList)
    {
        ArrayList<String> PanchayatStringList=new ArrayList<String>();
        PanchayatStringList.add("-चयन करे-");
        int setID=0;
        for(int i=0;i<pList.size();i++)
        {
            PanchayatStringList.add(pList.get(i).getPname());
        }

        ArrayAdapter adapter=new ArrayAdapter(this,android.R.layout.simple_spinner_item,PanchayatStringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_panchayat.setAdapter(adapter);
        //`spinnerPanchayat.setSelection(setID);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spn_district:
                if (position > 0) {
                    District dist = DistrictList.get(position - 1);
                    distCode = dist.get_DistCode();
                    loadBlockSpinnerdata();
                }
                break;
            case R.id.spn_block:
                if (position > 0) {
                    Block block = blockList.get(position - 1);
                    blockCode = block.getBlockCode();

//                    if(!isTypeLoaded)
//                        loadSchemeStructureType();
//
//                    syncSchemeList();
                    setPanchayatSpinnerData();
                }
                break;

            case R.id.spn_panchayat:
                if (position > 0) {
                    PanchayatData panchayat = PanchayatList.get(position - 1);
                    panchayatCode = panchayat.getPcode();
                    panchayatName = panchayat.getPname();

                }
                break;
            case R.id.spn_griev_type:
                if (position > 0) {
                    if(position == 1)
                        grievType = "sch";
                    else if(position == 2)
                        grievType = "str";
                    else
                        grievType = "oth";

                    showHideOtherEntry();
                    btn_proceed.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.spn_awyab:
                if (position > 0) {
                    Abyab data = abyabList.get(position - 1);
                    awyabId = data.getAbyab_Code();
                    awyabName = data.getAbyab_name();
                    Log.e("awyabCode", data.getAbyab_Code());
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void showHideOtherEntry(){
        if(grievType.equals("oth")){
            ll_other_entry.setVisibility(View.VISIBLE);
        }else{
            ll_other_entry.setVisibility(View.GONE);
        }
    }

    public void onClickProceed(View view) {
        if(grievType.equals("sch")){
            Intent intent = new Intent(this, SearchSchemeActivity.class);
            intent.putExtra("type", "scheme");
            startActivity(intent);
            finish();
        }else if(grievType.equals("str")){
            Intent intent = new Intent(this, SearchSchemeActivity.class);
            intent.putExtra("type", "structure");
            startActivity(intent);
            finish();
        }else{
            if(validateData()){
                GrievanceEntryDetail entry = new GrievanceEntryDetail(distName, distCode,blockName, blockCode, panchayatName,panchayatCode,"NA", "","NA", awyabId, awyabName, "oth", "", "NA", "NA","NA");
                Intent i = new Intent(this, GrievanceMultiplePhotoActivity.class);
                // i.putExtra("EDIT", wantToEdit);
                i.putExtra("remarks", txt_complain.getText().toString());

                i.putExtra("data", entry);
                startActivity(i);
            }
        }
    }

    private Boolean validateData() {
        Boolean isvalid = true;

        if(blockCode == null){
            Toast.makeText(this, "कृपया प्रखंड चयन करें.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(panchayatCode == null){
            Toast.makeText(this, "कृपया पंचायत चयन करें.", Toast.LENGTH_LONG).show();
            return false;
        }

        if(awyabId == null){
                Toast.makeText(this, "कृपया अवयब चयन करें.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (txt_complain.getText().toString().trim().length() <= 0) {
            Toast.makeText(this, "कृपया कम्प्लेन दर्ज करें.", Toast.LENGTH_LONG).show();
            txt_complain.requestFocus();
            return false;
        }

        return isvalid;
    }

    private class SyncAwayabListData extends AsyncTask<String, Void, ArrayList<Abyab>> {
        private final ProgressDialog dialog = new ProgressDialog(OtherTypeGrievActivity.this);

        @Override
        protected void onPreExecute() {
            //dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("अवयव लोड हो रहा है...");
            //this.dialog.show();
        }

        @Override
        protected ArrayList<Abyab> doInBackground(String...arg) {


            return WebServiceHelper.getAbyabListData();

        }

        @Override
        protected void onPostExecute(ArrayList<Abyab> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


            long i= helper.setAbyabPublicDataToLocal(result);
            abyabList = result;
            loadAwayabSpinnerdata();

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Awayab Data Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update Awayab",Toast.LENGTH_SHORT).show();

            }


        }
    }

    private class SyncPanchayatData extends AsyncTask<String, Void, ArrayList<PanchayatEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(OtherTypeGrievActivity.this);
        @Override
        protected void onPreExecute() {
            dialog.setCanceledOnTouchOutside(false);
            dialog.setMessage("पंचायत लोड हो रहा है...");
            dialog.show();
        }

        @Override
        protected ArrayList<PanchayatEntity> doInBackground(String...arg) {


            return WebServiceHelper.getPanchayatList(distCode, blockCode);

        }

        @Override
        protected void onPostExecute(ArrayList<PanchayatEntity> result) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());
            UserDetails info  = new UserDetails();
            info.setBlockCode(blockCode);
            info.setBlockName(blockName);
            info.setDistrictCode(distCode);
            info.setDistName(distName);

            long i= helper.setPanchayatDataToLocal(info, result);

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Panchayat Data Synced Successfully",Toast.LENGTH_SHORT).show();
                //sDataBaseHelper placeData = new DataBaseHelper(this);
                PanchayatList=helper.getPanchayt(blockCode);
                loadPanchayatSpinnerData(PanchayatList);
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update panchayat",Toast.LENGTH_SHORT).show();

            }


        }
    }
}
