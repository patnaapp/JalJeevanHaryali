package bih.in.jaljeevanharyali.activity.grievance;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PanchayatEntity;
import bih.in.jaljeevanharyali.adapter.PublicSchemeListAdapter;
import bih.in.jaljeevanharyali.adapter.PublicStructureListAdapter;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.District;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.SchemeDetailPublic;
import bih.in.jaljeevanharyali.entity.StructureDetailPublic;
import bih.in.jaljeevanharyali.entity.StructureType;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;


public class SearchSchemeActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Spinner spn_district,spn_awyab,spn_schemestatus,spn_structure_type,spn_block,spn_panchayat;
    LinearLayout ll_scheme,ll_structure,ll_list,ll_panchayat;
    TextView tv_data_title,tv_title;
    RecyclerView rv_data;

    DataBaseHelper localdbhelper;
    ArrayList<District> DistrictList;
    ArrayList<Block> blockList;
    ArrayList<StructureType> sanrachnaList;
    ArrayList<Abyab> abyabList;

    ArrayList<SchemeDetailPublic> schemeList;
    PublicSchemeListAdapter schemeAdapter;

    ArrayList<StructureDetailPublic> structureList;
    PublicStructureListAdapter structureAdapter;

    ArrayList<PanchayatEntity> PanchayatList = new ArrayList<>();

    String schemeStatusArray[] = {"-चयन करे-","All", "Started/Ongoing", "Completed"};

    String distCode,distName,blockCode,blockName,panchayatCode="",panchayatName="",awyabId,awyabName,schemeStatus,structureType;
    String type;

    Boolean isTypeLoaded = false;
    String distcode="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search_scheme);

        setup();
        loadDistrictSpinnerdata();
        extractDataFromIntent();

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, schemeStatusArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_schemestatus.setAdapter(adapter);

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
        spn_awyab = findViewById(R.id.spn_awyab);
        spn_schemestatus = findViewById(R.id.spn_schemestatus);
        spn_structure_type = findViewById(R.id.spn_structure_type);
        spn_panchayat = findViewById(R.id.spn_panchayat);

        tv_title = findViewById(R.id.tv_title);

        ll_scheme = findViewById(R.id.ll_scheme);
        ll_structure = findViewById(R.id.ll_structure);
        ll_list = findViewById(R.id.ll_list);
        ll_panchayat = findViewById(R.id.ll_panchayat);

        tv_data_title = findViewById(R.id.tv_data_title);

        rv_data = findViewById(R.id.rv_data);

        spn_district.setOnItemSelectedListener(this);
        spn_block.setOnItemSelectedListener(this);
        spn_awyab.setOnItemSelectedListener(this);
        spn_schemestatus.setOnItemSelectedListener(this);
        spn_structure_type.setOnItemSelectedListener(this);
        spn_panchayat.setOnItemSelectedListener(this);

        ll_scheme.setVisibility(View.GONE);
        ll_panchayat.setVisibility(View.GONE);
    }

    public void extractDataFromIntent(){
        type = getIntent().getStringExtra("type");

        if(type.equals("scheme")){
            tv_title.setText("योजना सूची विवरण");
        }else{
            tv_title.setText("संरचना सूची विवरण");
        }

    }

    public void loadSchemeStructureType(){
        if(type.equals("scheme")){
            abyabList = localdbhelper.getAbyabList();
            if(abyabList.size() > 0){
                loadAwayabSpinnerdata();
            }else{
                new SyncAwayabListData().execute();
            }

            ll_scheme.setVisibility(View.VISIBLE);
        }else{
            sanrachnaList = localdbhelper.getAllSanrachnaTypeList();
            if(sanrachnaList.size() > 0){
                loadSanrachnaSpinnerdata();
            }else{
                new SyncSanrachnaTypeData().execute();
            }

            ll_structure.setVisibility(View.VISIBLE);
        }

        isTypeLoaded = true;
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
        distcode= PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("distcode", "");

        int setID=0;
        for ( i = 0; i < DistrictList.size(); i++) {

            if (DistrictList.get(i).get_DistCode().equalsIgnoreCase(distcode)) {
                setID = i;
            }
            if(setID!=0) {
                spn_district.setSelection(setID+1);
               // spn_district.setEnabled(false);
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
//        DataBaseHelper placeData = new DataBaseHelper(this);
//        PanchayatList=placeData.getPanchayatLocal(blockCode);
//        String isfound="n";

//        if(PanchayatList.size()>0) {
//            loadPanchayatSpinnerData(PanchayatList);
//        }else{
            new SyncPanchayatData().execute();
       // }

    }
    private void loadPanchayatSpinnerData(ArrayList<PanchayatEntity> pList)
    {
        ArrayList<String> PanchayatStringList=new ArrayList<String>();
        PanchayatStringList.add("All");
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

    public void loadSanrachnaSpinnerdata() {
        ArrayList<String> array = new ArrayList<>();
            array.add("-चयन करे-");
        for (StructureType sanrachna : sanrachnaList) {
            array.add(sanrachna.getStrName());
        }

        ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_structure_type.setAdapter(adapter);
    }

    public void loadSchemeAdapter(){
        if(schemeList.size() > 0){
            tv_data_title.setVisibility(View.VISIBLE);
        }else{
            tv_data_title.setVisibility(View.GONE);
        }
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        schemeAdapter = new PublicSchemeListAdapter(this, schemeList);
        rv_data.setAdapter(schemeAdapter);
    }

    public void loadStructureAdapter(){
        if(structureList.size() > 0){
            tv_data_title.setVisibility(View.VISIBLE);
        }else{
            tv_data_title.setVisibility(View.GONE);
        }
        rv_data.setLayoutManager(new LinearLayoutManager(this));
        structureAdapter = new PublicStructureListAdapter(this, structureList);
        rv_data.setAdapter(structureAdapter);
    }

    public void syncSchemeList(){
        if(distCode != null && blockCode != null && awyabId != null && schemeStatus != null)
            new SyncSchemeList().execute();
    }

    public void syncStructureList(){
        if(distCode != null && blockCode != null && structureType != null)
            new SyncStructureList().execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.spn_district:
                if (position > 0) {
                    District dist = DistrictList.get(position - 1);
                    distCode = dist.get_DistCode();
                    loadBlockSpinnerdata();

                    if(type.equals("scheme") && schemeList != null){
                        schemeList.clear();
                        loadSchemeAdapter();
                    }

                    if (type.equals("structure") && structureList != null){
                        structureList.clear();
                        loadStructureAdapter();
                    }
                }
                break;
            case R.id.spn_block:
                if (position > 0) {
                    Block block = blockList.get(position - 1);
                    Log.e("BlockCode ", block.getBlockCode());
                    blockCode = block.getBlockCode();

                    if(!isTypeLoaded)
                        loadSchemeStructureType();

                    setPanchayatSpinnerData();
                    syncSchemeList();
                    ll_panchayat.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.spn_panchayat:
                if (position > 0) {
                    PanchayatEntity panchayat = PanchayatList.get(position - 1);
                    panchayatCode = panchayat.getPcode();
                    panchayatName = panchayat.getPname();

                    if(type.equals("scheme")){
                        syncSchemeList();
                    }else{
                        syncStructureList();
                    }
                }else{
                    panchayatCode = "";
                    panchayatName = "";
                    if(type.equals("scheme")){
                        syncSchemeList();
                    }else{
                        syncStructureList();
                    }
                }
                break;
            case R.id.spn_awyab:
                if (position > 0) {
                    Abyab data = abyabList.get(position - 1);
                    awyabId = data.getAbyab_Code();
                    awyabName = data.getAbyab_name();
                    syncSchemeList();
                    Log.e("awyabCode", data.getAbyab_Code());
                }
                break;
            case R.id.spn_structure_type:
                if (position > 0) {
                    StructureType data = sanrachnaList.get(position - 1);
                    structureType = data.getStrID();
                    syncStructureList();
                }
                break;
            case R.id.spn_schemestatus:
                if (position > 0) {
                    if(position == 1)
                        schemeStatus = "0";
                    else if (position == 2)
                        schemeStatus = "S";
                    else
                        schemeStatus = "C";

                    syncSchemeList();
                }
                break;
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private class SyncAwayabListData extends AsyncTask<String, Void, ArrayList<Abyab>> {
        private final ProgressDialog dialog = new ProgressDialog(SearchSchemeActivity.this);

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

    private class SyncSanrachnaTypeData extends AsyncTask<String, Void, ArrayList<StructureType>> {
        private final ProgressDialog dialog = new ProgressDialog(SearchSchemeActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<StructureType> doInBackground(String...arg) {

            return WebServiceHelper.getStructureTypeData();

        }

        @Override
        protected void onPostExecute(ArrayList<StructureType> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());


            long i= helper.setStructureTypeDataToLocal(result);
            sanrachnaList = result;
            loadSanrachnaSpinnerdata();

            if(i>0)
            {
                Toast.makeText(getApplicationContext(), "Sanrachna Type Synced Successfully",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(), "Failed to update Sanrachna Type",Toast.LENGTH_SHORT).show();

            }
        }
    }

    private class SyncSchemeList extends AsyncTask<String, Void, ArrayList<SchemeDetailPublic>> {
        private final ProgressDialog dialog = new ProgressDialog(SearchSchemeActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();
        }

        @Override
        protected ArrayList<SchemeDetailPublic> doInBackground(String...arg) {
            return WebServiceHelper.getSchemeListForPublic(distCode,blockCode,panchayatCode,awyabId,schemeStatus);
        }

        @Override
        protected void onPostExecute(ArrayList<SchemeDetailPublic> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            schemeList = result;
            tv_data_title.setText("Total Scheme ("+ result.size()+")");
            tv_data_title.setVisibility(View.VISIBLE);
            loadSchemeAdapter();
        }
    }

    private class SyncStructureList extends AsyncTask<String, Void, ArrayList<StructureDetailPublic>> {
        private final ProgressDialog dialog = new ProgressDialog(SearchSchemeActivity.this);

        @Override
        protected void onPreExecute() {
            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("Loading...");
            this.dialog.show();

            Log.e("Data", "B-"+blockCode+", P-"+panchayatCode);
        }

        @Override
        protected ArrayList<StructureDetailPublic> doInBackground(String...arg) {
            return WebServiceHelper.getStrucutureListForPublic(distCode,blockCode,panchayatCode,structureType);
        }

        @Override
        protected void onPostExecute(ArrayList<StructureDetailPublic> result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            DataBaseHelper helper=new DataBaseHelper(getApplicationContext());

            structureList = result;
            tv_data_title.setText("Total Structure ("+ result.size()+")");
            tv_data_title.setVisibility(View.VISIBLE);
            loadStructureAdapter();
        }
    }

    private class SyncPanchayatData extends AsyncTask<String, Void, ArrayList<PanchayatEntity>> {
        private final ProgressDialog dialog = new ProgressDialog(SearchSchemeActivity.this);
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

            //long i= helper.setPanchayatDataToLocal(info, result);

            PanchayatList=result;
            loadPanchayatSpinnerData(PanchayatList);

//            if(i>0)
//            {
//                Toast.makeText(getApplicationContext(), "Panchayat Data Synced Successfully",Toast.LENGTH_SHORT).show();
//
//
//            }
//            else
//            {
//                Toast.makeText(getApplicationContext(), "Failed to update panchayat",Toast.LENGTH_SHORT).show();
//
//            }
        }
    }
}
