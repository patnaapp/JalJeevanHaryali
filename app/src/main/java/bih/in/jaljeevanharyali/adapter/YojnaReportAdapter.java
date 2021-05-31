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
import bih.in.jaljeevanharyali.activity.ManregaReportActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.YojnaReportEntity;

public class YojnaReportAdapter extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<YojnaReportEntity> ThrList=new ArrayList<>();

    public YojnaReportAdapter(ManregaReportActivity listViewshowedit, ArrayList<YojnaReportEntity> rlist) {
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
        ViewHolder holder = null;
        //if (convertView == null) {
        convertView = mInflater.inflate(R.layout.adaptor_yojna, null);

        holder = new ViewHolder();
        //holder.insId=(TextView)convertView.findViewById(R.id.ins_Id);
        holder.inspectiondate=(TextView)convertView.findViewById(R.id.inspectiondate);
        holder.tv_district=(TextView)convertView.findViewById(R.id.tv_district);
        holder.block=(TextView)convertView.findViewById(R.id.block);
        holder.schemecode=(TextView)convertView.findViewById(R.id.schemecode);
        holder.tv_village=(TextView)convertView.findViewById(R.id.tv_village);
        holder.tv_latitude=(TextView)convertView.findViewById(R.id.tv_latitude);
        holder.tv_longitude=(TextView)convertView.findViewById(R.id.tv_longitude);
        holder.tv_inspection_id=(TextView)convertView.findViewById(R.id.tv_inspection_id);
        holder.tv_yojna=(TextView)convertView.findViewById(R.id.tv_yojna);
        holder.tv_abyab=(TextView)convertView.findViewById(R.id.tv_abyab);
        holder.tv_karya=(TextView)convertView.findViewById(R.id.tv_karya);
        holder.tv_vivag=(TextView)convertView.findViewById(R.id.tv_vivag);
        holder.iv_show_location=(ImageView) convertView.findViewById(R.id.iv_show_location);

        holder.ll_map=(LinearLayout) convertView.findViewById(R.id.ll_map);
        holder.ll_btn=(LinearLayout) convertView.findViewById(R.id.ll_btn);
        holder.ll_type=(LinearLayout) convertView.findViewById(R.id.ll_type);
        convertView.setTag(holder);



        //holder.ll_map.setVisibility(View.GONE);
        holder.ll_btn.setVisibility(View.GONE);
        // holder.insId.setText(ThrList.get(position).getId());
        holder.tv_inspection_id.setText(ThrList.get(position).getId());
        //holder.inspectiondate.setText(ThrList.get(position).getIsPhase1InspDate());
        if(ThrList.get(position).getIsPhase3InspDate().equals("")||ThrList.get(position).getIsPhase3InspDate().equals("NA")||ThrList.get(position).getIsPhase3InspDate().equals("anyType{}")){
            holder.inspectiondate.setText(ThrList.get(position).getIsPhase3InspDate());
        }else{
            String dateformat="";
            dateformat=ThrList.get(position).getIsPhase3InspDate();
            String s=dateformat.substring(0,10);
            holder.inspectiondate.setText(s);
        }
        holder.tv_district.setText(ThrList.get(position).getDistName());
        holder.block.setText(ThrList.get(position).getBlockName());
        holder.schemecode.setText(ThrList.get(position).getMIS_Scheme_Code());
        //holder.tv_village.setText(ThrList.get(position).getVILLNAME());
        holder.tv_latitude.setText(ThrList.get(position).getIsPhase1InspLatitude());
        holder.tv_longitude.setText(ThrList.get(position).getIsPhase1InspLongitude());
        holder.tv_abyab.setText(ThrList.get(position).getSub_Execution_DepartmentName());
        holder.tv_karya.setText(ThrList.get(position).getSubSubExectDept_Name());
        holder.tv_vivag.setText(ThrList.get(position).getExecution_DeptName());



        holder.iv_show_location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri mapUri = Uri.parse("geo:0,0?q="+ThrList.get(position).getIsPhase1InspLatitude()+","+ThrList.get(position).getIsPhase1InspLongitude()+"("+ThrList.get(position).getId()+")");
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
        TextView schemecode,inspectiondate,tv_district,block,tv_village,tv_latitude,tv_longitude,tv_inspection_id,tv_yojna,tv_abyab,tv_karya,tv_vivag;
        LinearLayout ll_map,ll_btn,ll_type;
        ImageView iv_show_location;

    }
}

