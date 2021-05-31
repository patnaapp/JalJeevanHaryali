package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondListActivity;
import bih.in.jaljeevanharyali.activity.PondReportActivity;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondWellReportEntity;

public class PondReportAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<PondWellReportEntity> ThrList=new ArrayList<>();

    public PondReportAdapter(PondReportActivity listViewshowedit, ArrayList<PondWellReportEntity> rlist) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
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
        PondReportAdapter.ViewHolder holder = null;
        //if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adaptor_well, null);

            holder = new PondReportAdapter.ViewHolder();
            //holder.insId=(TextView)convertView.findViewById(R.id.ins_Id);
            holder.rajswaThanaNo=(TextView)convertView.findViewById(R.id.rajswaThanaNo);
            holder.tv_district=(TextView)convertView.findViewById(R.id.tv_district);
            holder.block=(TextView)convertView.findViewById(R.id.block);
            holder.tv_village=(TextView)convertView.findViewById(R.id.tv_village);
            holder.tv_latitude=(TextView)convertView.findViewById(R.id.tv_latitude);
            holder.tv_longitude=(TextView)convertView.findViewById(R.id.tv_longitude);

            holder.ll_map=(LinearLayout)convertView.findViewById(R.id.ll_map);
            holder.ll_btn=(LinearLayout)convertView.findViewById(R.id.ll_btn);
            holder.ll_type=(LinearLayout)convertView.findViewById(R.id.ll_type);
            holder.iv_show_location=convertView.findViewById(R.id.iv_show_location);
            convertView.setTag(holder);

            //holder.ll_map.setVisibility(View.GONE);
            holder.ll_btn.setVisibility(View.GONE);
            //holder.insId.setText(ThrList.get(position).getId());
            holder.rajswaThanaNo.setText(ThrList.get(position).getRajswaThanaNumber());
            holder.tv_district.setText(ThrList.get(position).getDistName());
            holder.block.setText(ThrList.get(position).getBlockName());
            holder.tv_village.setText(ThrList.get(position).getVILLNAME());
            holder.tv_latitude.setText(ThrList.get(position).getLatitude());
            holder.tv_longitude.setText(ThrList.get(position).getLongitude());

            holder.ll_type.setVisibility(View.GONE);

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
//            holder = (PondReportAdapter.ViewHolder) convertView.getTag();
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView insId,rajswaThanaNo,tv_district,block,tv_village,tv_latitude,tv_longitude;
        LinearLayout ll_map,ll_btn,ll_type;
        ImageView iv_show_location;


    }



}
