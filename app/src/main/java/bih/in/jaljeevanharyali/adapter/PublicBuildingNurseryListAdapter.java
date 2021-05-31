package bih.in.jaljeevanharyali.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
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
import bih.in.jaljeevanharyali.activity.grievance.RegisterGrievanceActivity;
import bih.in.jaljeevanharyali.entity.GrievanceEntryDetail;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.entity.PublicNurseryBuildingEntity;
import bih.in.jaljeevanharyali.entity.StructureDetailPublic;
import bih.in.jaljeevanharyali.utility.AppConstant;


public class PublicBuildingNurseryListAdapter extends RecyclerView.Adapter<PublicBuildingNurseryListAdapter.ViewHolder> {

    private ArrayList<PublicNurseryBuildingEntity> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private String type;

    public PublicBuildingNurseryListAdapter(Context context, ArrayList<PublicNurseryBuildingEntity> data, String type) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.type = type;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_public_building_nurseylist, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        final PublicNurseryBuildingEntity info = mData.get(position);
        final String name = type.equals(AppConstant.BUILDING)? info.getBuildingName():info.getNursury_Name();

//        if(type.equals(AppConstant.BUILDING)){
//            holder.tv_name.setText(String.valueOf(position+1)+")  " +info.getBuildingName());
//        }else{
//            holder.tv_name.setText(String.valueOf(position+1)+")  " +info.getNursury_Name());
//        }

        holder.tv_name.setText(String.valueOf(position+1)+")  " +name);
        holder.tv_district.setText(info.getDist_Name());
        holder.tv_panchayat.setText(info.getPanchayat_Name());
        holder.tv_block.setText(info.getBlock_Name());
        holder.tv_area.setText(info.getArea());

        holder.tv_view_detail.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                GrievanceEntryDetail scheme = new GrievanceEntryDetail(info.getDist_Name(), info.getDist_Code(),info.getBlock_Name(), info.getBlock_Code(), info.getPanchayat_Name(),info.getPanchayat_Code(),"",info.getVillage_Code(),info.getVillage_Name(),"","", type, info.getInspectionID(),name,info.getLatitude_mst(), info.getLongitude_mst());
                scheme.setSchemeId(info.getInspectionID());
                scheme.setLatitudeComp(info.getLatitude_mst());
                scheme.setLongitudeComp(info.getLongitude_mst());
                scheme.setWork_StructureName(name);
                Intent i = new Intent(context, RegisterGrievanceActivity.class);
                i.putExtra("data", scheme);
                context.startActivity(i);
            }
        });

        holder.iv_show_location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = type.equals(AppConstant.BUILDING) ? info.getBuildingName() : info.getNursury_Name();
                //Log.e("Location: ", "Lat:"+info.getLatitude()+", Long:"+info.getLongitude());
                Uri mapUri = Uri.parse("geo:0,0?q="+info.getLatitude_mst()+","+info.getLongitude_mst()+"("+name+")");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                mapIntent.setPackage("com.google.android.apps.maps");

                if (mapIntent.resolveActivity(context.getPackageManager()) != null) {
                    context.startActivity(mapIntent);
                }else{
                    Toast.makeText(context, "कृपया इस सुविधा का उपयोग करने के लिए Google मानचित्र स्थापित करें", Toast.LENGTH_SHORT).show();
                }
                //act
            }
        });
    }

    // total number of rows
    @Override
    public int getItemCount()
    {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView tv_name,tv_area,tv_district,tv_block,tv_panchayat,tv_view_detail;
        LinearLayout ll_structure;
        ImageView iv_show_location;

        ViewHolder(View itemView)

        {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_area = itemView.findViewById(R.id.tv_area);
            tv_district = itemView.findViewById(R.id.tv_district);
            tv_block = itemView.findViewById(R.id.tv_block);
            tv_panchayat = itemView.findViewById(R.id.tv_panchayat);
            tv_view_detail = itemView.findViewById(R.id.tv_view_detail);

            ll_structure = itemView.findViewById(R.id.ll_structure);
            iv_show_location = itemView.findViewById(R.id.iv_show_location);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    PublicNurseryBuildingEntity getItem(int id) {
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
