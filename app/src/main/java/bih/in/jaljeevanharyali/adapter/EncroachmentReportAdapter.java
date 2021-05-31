package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondReportActivity;
import bih.in.jaljeevanharyali.activity.userCo.EncroachmentReportActivity;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.PondWellReportEntity;

public class EncroachmentReportAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<PondEncroachmentEntity> ThrList=new ArrayList<>();

    public EncroachmentReportAdapter(EncroachmentReportActivity listViewshowedit, ArrayList<PondEncroachmentEntity> rlist) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;

        convertView = mInflater.inflate(R.layout.adaptor_pondwell_encrhmnt, null);

        holder = new ViewHolder();
        //holder.insId=(TextView)convertView.findViewById(R.id.ins_Id);
        holder.rajswaThanaNo=(TextView)convertView.findViewById(R.id.rajswaThanaNo);
        holder.encrhmnt_status=(TextView)convertView.findViewById(R.id.encrhmnt_status);
        holder.inspection_status=(TextView)convertView.findViewById(R.id.inspection_status);
        holder.block=(TextView)convertView.findViewById(R.id.block);
        holder.tv_village=(TextView)convertView.findViewById(R.id.tv_village);
        holder.ll_map=(LinearLayout)convertView.findViewById(R.id.ll_map);
        holder.ll_btn=(LinearLayout)convertView.findViewById(R.id.ll_btn);
        convertView.setTag(holder);

        holder.ll_map.setVisibility(View.GONE);
        holder.ll_btn.setVisibility(View.GONE);
        //holder.insId.setText(ThrList.get(position).getId());
        holder.rajswaThanaNo.setText(ThrList.get(position).getRajswaThanaNumber());
        holder.block.setText(ThrList.get(position).getBlockName());
        holder.tv_village.setText(ThrList.get(position).getVILLNAME());

        if(ThrList.get(position).getStatus_Of_Encroachment().equals("Y")){
            holder.inspection_status.setText("पूर्ण");
            if(ThrList.get(position).getType_Of_Encroachment().equals("K")){
                holder.encrhmnt_status.setText("कच्चा");
            }else{
                holder.encrhmnt_status.setText("पक्का");
            }
        }else if(ThrList.get(position).getStatus_Of_Encroachment().equals("N")){
            holder.encrhmnt_status.setText("नहीं है");
            holder.inspection_status.setText("पूर्ण");
        }else{
            holder.encrhmnt_status.setText("अचिह्नित");
            holder.inspection_status.setText("अपूर्ण");
        }

        return convertView;
    }

    private class ViewHolder {
        TextView inspection_status,encrhmnt_status,rajswaThanaNo,tv_district,block,tv_village;
        LinearLayout ll_map,ll_btn;


    }

}
