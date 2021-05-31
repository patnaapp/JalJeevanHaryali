package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondInspectionActivity;
import bih.in.jaljeevanharyali.activity.WellInspectionActivity;
import bih.in.jaljeevanharyali.activity.WellListActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEntity;

public class WellAdaptor extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<PondEntity> ThrList=new ArrayList<>();
    String panchayatCode,panchayatName="",structureId;

    public WellAdaptor(WellListActivity listViewshowedit, ArrayList<PondEntity> rlist, String panchayatCode, String panchayatName, String structureId) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.panchayatCode = panchayatCode;
        this.panchayatName = panchayatName;
        this.structureId = structureId;
    }

    @Override
    public int getCount() {
        return ThrList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        WellAdaptor.ViewHolder holder = null;
        //if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adaptor_well, null);

            holder = new WellAdaptor.ViewHolder();
            //holder.insId=(TextView)convertView.findViewById(R.id.ins_Id);
            holder.rajswaThanaNo=(TextView)convertView.findViewById(R.id.rajswaThanaNo);
            holder.tv_district=(TextView)convertView.findViewById(R.id.tv_district);
            holder.block=(TextView)convertView.findViewById(R.id.block);
            holder.tv_village=(TextView)convertView.findViewById(R.id.tv_village);
            holder.tv_latitude=(TextView)convertView.findViewById(R.id.tv_latitude);
            holder.tv_longitude=(TextView)convertView.findViewById(R.id.tv_longitude);
            holder.ll_type=(LinearLayout) convertView.findViewById(R.id.ll_type);

            holder.btn_map=(Button)convertView.findViewById(R.id.btn_map);
            holder.btn_inspect=(Button)convertView.findViewById(R.id.btn_inspect);
            convertView.setTag(holder);

            ///holder.insId.setText(ThrList.get(position).getInspectionID());
            holder.rajswaThanaNo.setText(ThrList.get(position).getRajswaThanaNumber());
            holder.tv_district.setText(ThrList.get(position).getDistName());
            holder.block.setText(ThrList.get(position).getBlockName());
            holder.tv_village.setText(ThrList.get(position).getVillageName());
            holder.tv_latitude.setText("Lat: "+ThrList.get(position).getLatitude());
            holder.tv_longitude.setText("Long: "+ThrList.get(position).getLongitude());

            holder.ll_type.setVisibility(View.GONE);

        holder.btn_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri mapUri = Uri.parse("geo:0,0?q="+ThrList.get(position).getLatitude()+","+ThrList.get(position).getLongitude()+"("+ThrList.get(position).getInspectionID()+")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                //activity.startActivity(mapIntent);
                if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivity(mapIntent);
                }else{
                    Toast.makeText(activity, "कृपया इस सुविधा का उपयोग करने के लिए Google Map install करें", Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.btn_inspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(activity, WellInspectionActivity.class);
                i.putExtra("id", ThrList.get(position).getId());
                i.putExtra("panchayatCode", panchayatCode);
                i.putExtra("panchayatName", panchayatName);
                i.putExtra("structureId", structureId);
                //listid=data.get(position).getSlno();
                activity.startActivity(i);
                activity.finish();
            }
        });
//        }
//        else {
//            holder = (WellAdaptor.ViewHolder) convertView.getTag();
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView insId,rajswaThanaNo,tv_district,block,tv_village,tv_latitude,tv_longitude;
        LinearLayout ll_type;
        Button btn_map,btn_inspect;
    }
}
