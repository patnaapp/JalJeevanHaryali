package bih.in.jaljeevanharyali.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.nursery.NurseryEntryActivity;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.utility.AppConstant;


public class NurseryAdapter extends RecyclerView.Adapter<NurseryAdapter.ViewHolder> {

    private ArrayList<NurseryEntity> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public NurseryAdapter(Context context, ArrayList<NurseryEntity> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_nursery, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NurseryEntity info = mData.get(position);

        holder.tv_name.setText(String.valueOf(position+1)+")  " +info.getNursury_Name());
        holder.tv_panchayat.setText(info.getPanchayat_Name());
        holder.tv_village.setText(info.getVillage_Name());
        holder.tv_ward.setText(info.getWard_Name());
        holder.tv_area.setText(info.getArea());
        holder.tv_tree.setText(info.getTree());
        holder.tv_nursury_id.setText(info.getInspectionID());

        holder.tv_build_name.setText(String.valueOf(position+1)+")  " +info.getBuildingName());
        holder.tv_building_id.setText(info.getInspectionID());
        holder.tv_area_build.setText(info.getArea());
        holder.tv_dept_name.setText(info.getExecution_DeptName());
        holder.tv_panchayat_build.setText(info.getPanchayat_Name());
        holder.tv_area_type.setText(info.evaluatAreaName());
        holder.tv_ward_build.setText(info.getWard_Name());

        if(info.getStrType().equals(AppConstant.NURSURY)){
            holder.ll_nursury.setVisibility(View.VISIBLE);
            holder.ll_building.setVisibility(View.GONE);
        }else{
            holder.ll_nursury.setVisibility(View.GONE);
            holder.ll_building.setVisibility(View.VISIBLE);
        }

        holder.tv_view_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NurseryEntryActivity.class);
                intent.putExtra("type", info.getStrType());
                intent.putExtra("data", info);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name,tv_panchayat,tv_village,tv_ward,tv_area,tv_tree,tv_nursury_id;
        TextView tv_view_detail;
        LinearLayout ll_nursury,ll_building;
        TextView tv_build_name,tv_building_id,tv_area_build,tv_dept_name,tv_panchayat_build,tv_area_type,tv_ward_build;

        ViewHolder(View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_panchayat = itemView.findViewById(R.id.tv_panchayat);
            tv_village = itemView.findViewById(R.id.tv_village);
            tv_ward = itemView.findViewById(R.id.tv_ward);
            tv_area = itemView.findViewById(R.id.tv_area);
            tv_tree = itemView.findViewById(R.id.tv_tree);
            tv_nursury_id = itemView.findViewById(R.id.tv_nursury_id);

            tv_view_detail = itemView.findViewById(R.id.tv_view_detail);

            tv_build_name = itemView.findViewById(R.id.tv_build_name);
            tv_building_id = itemView.findViewById(R.id.tv_building_id);
            tv_area_build = itemView.findViewById(R.id.tv_area_build);
            tv_dept_name = itemView.findViewById(R.id.tv_dept_name);
            tv_panchayat_build = itemView.findViewById(R.id.tv_panchayat_build);
            tv_area_type = itemView.findViewById(R.id.tv_area_type);
            tv_ward_build = itemView.findViewById(R.id.tv_ward_build);

            ll_nursury = itemView.findViewById(R.id.ll_nursury);
            ll_building = itemView.findViewById(R.id.ll_building);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    NurseryEntity getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }



}
