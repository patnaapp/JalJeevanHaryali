package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.nursery.NurseryReportActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.NurseryReportEntity;
import bih.in.jaljeevanharyali.utility.AppConstant;

public class NurseryBuildingAdapter extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<NurseryReportEntity> ThrList=new ArrayList<>();
    String Type;

    public NurseryBuildingAdapter(NurseryReportActivity listViewshowedit, ArrayList<NurseryReportEntity> rlist, String type) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        this.Type=type;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
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
        ViewHolder holder = null;
        //if (convertView == null) {
        convertView = mInflater.inflate(R.layout.adaptor_nursery_building, null);

        holder = new ViewHolder();
        //holder.insId=(TextView)convertView.findViewById(R.id.ins_Id);
        holder.building_name=(TextView)convertView.findViewById(R.id.building_name);
        holder.tv_district=(TextView)convertView.findViewById(R.id.tv_district);
        holder.block=(TextView)convertView.findViewById(R.id.block);
        holder.tv_panchayat=(TextView)convertView.findViewById(R.id.tv_panchayat);
        holder.tv_latitude=(TextView)convertView.findViewById(R.id.tv_latitude);
        holder.tv_longitude=(TextView)convertView.findViewById(R.id.tv_longitude);
        holder.tv_inspection_id=(TextView)convertView.findViewById(R.id.tv_inspection_id);
        holder.tv_inspection_date=(TextView)convertView.findViewById(R.id.tv_inspection_date);
        holder.electricty_consumer=(TextView)convertView.findViewById(R.id.electricty_consumer);
        holder.plantation_name=(TextView)convertView.findViewById(R.id.plantation_name);
        holder.mobilenumber=(TextView)convertView.findViewById(R.id.mobilenumber);
        holder.ll_building=(LinearLayout)convertView.findViewById(R.id.ll_building);
        holder.ll_consumer=(LinearLayout)convertView.findViewById(R.id.ll_consumer);
        holder.ll_nursery=(LinearLayout)convertView.findViewById(R.id.ll_nursery);
        holder.ll_mobile=(LinearLayout)convertView.findViewById(R.id.ll_mobile);
        holder.iv_show_location=(ImageView) convertView.findViewById(R.id.iv_show_location);

        holder.ll_map=(LinearLayout) convertView.findViewById(R.id.ll_map);
        holder.ll_btn=(LinearLayout) convertView.findViewById(R.id.ll_btn);
        holder.ll_type=(LinearLayout) convertView.findViewById(R.id.ll_type);
        convertView.setTag(holder);

        holder.ll_btn.setVisibility(View.GONE);
        holder.tv_inspection_id.setText(ThrList.get(position).getId());
        holder.building_name.setText(ThrList.get(position).getBuildingName());
        holder.tv_district.setText(ThrList.get(position).getDistName());
        holder.block.setText(ThrList.get(position).getBlockName());
        holder.tv_panchayat.setText(ThrList.get(position).getPanchayatName());
        holder.tv_latitude.setText(ThrList.get(position).getLatitude());
        holder.tv_longitude.setText(ThrList.get(position).getLongitude());

        if(ThrList.get(position).getInspectedDate().equals("")||ThrList.get(position).getInspectedDate().equals("NA")||ThrList.get(position).getInspectedDate().equals("anyType{}")){
            holder.tv_inspection_date.setText(ThrList.get(position).getInspectedDate());
        }else{
            String dateformat="";
            dateformat=ThrList.get(position).getInspectedDate();
            String s=dateformat.substring(0,10);
            holder.tv_inspection_date.setText(s);
        }

        holder.electricty_consumer.setText(ThrList.get(position).getConsumerNo());
        holder.plantation_name.setText(ThrList.get(position).getNurseryName());
        holder.mobilenumber.setText(ThrList.get(position).getContactMobile());

        holder.ll_type.setVisibility(View.GONE);
        if(Type.equals(AppConstant.NURSURY)){
            holder.ll_nursery.setVisibility(View.VISIBLE);
            holder.ll_mobile.setVisibility(View.VISIBLE);

            holder.ll_consumer.setVisibility(View.GONE);
            holder.ll_building.setVisibility(View.GONE);

        }else{
            holder.ll_consumer.setVisibility(View.VISIBLE);
            holder.ll_building.setVisibility(View.VISIBLE);

            holder.ll_nursery.setVisibility(View.GONE);
            holder.ll_mobile.setVisibility(View.GONE);
        }

        holder.iv_show_location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri mapUri = Uri.parse("geo:0,0?q="+ThrList.get(position).getLatitude()+","+ThrList.get(position).getLongitude()+"("+ThrList.get(position).getId()+")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
                    activity.startActivity(mapIntent);
                }else{
                    Toast.makeText(activity, "कृपया इस सुविधा का उपयोग करने के लिए Google मानचित्र स्थापित करें", Toast.LENGTH_SHORT).show();
                }
                //act
            }
        });
//        }
//        else {
//            holder = (WellReportAdapter.ViewHolder) convertView.getTag();
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_inspection_date,building_name,tv_district,block,tv_panchayat,tv_latitude,tv_longitude,tv_inspection_id,electricty_consumer,plantation_name,mobilenumber;
        LinearLayout ll_map,ll_btn,ll_type,ll_building,ll_consumer,ll_nursery,ll_mobile;
        ImageView iv_show_location;

    }
}



