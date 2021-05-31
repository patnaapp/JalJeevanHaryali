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
import bih.in.jaljeevanharyali.activity.grievance.RegisterGrievanceActivity;
import bih.in.jaljeevanharyali.entity.GrievanceEntryDetail;
import bih.in.jaljeevanharyali.entity.SchemeDetailPublic;
import bih.in.jaljeevanharyali.entity.StructureDetailPublic;


public class PublicStructureListAdapter extends RecyclerView.Adapter<PublicStructureListAdapter.ViewHolder> {

    private ArrayList<StructureDetailPublic> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;
    private String strucName;

    public PublicStructureListAdapter(Context context, ArrayList<StructureDetailPublic> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = mInflater.inflate(R.layout.adaptor_structurelist, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        final StructureDetailPublic info = mData.get(position);
        //holder.tv_scheme_id.setText(info.getMIS_Scheme_Code());
        holder.tv_scheme_name.setText(String.valueOf(position+1)+")  " +info.getCommercial_Public());
        holder.tv_village.setText(info.getVILLNAME());
        holder.tv_panchayat.setText(info.getPanchayatName());
        holder.tv_functional.setText(info.getFunctional());
        if(info.getStrName().equals("NA") || info.getStrName().equals("")){
            holder.ll_structure.setVisibility(View.GONE);
        }else{
            holder.tv_structure.setText(info.getStrName());
            holder.ll_structure.setVisibility(View.VISIBLE);
        }


        //holder.tv_date.setText(Utiilties.convertDateStringFormet("MM/dd/yyyy HH:mm:ss a", "dd MMM yyyy",info.getINSPECTION_DATE()));

        holder.sblist.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //GrievanceEntryDetail scheme = new GrievanceEntryDetail(info.getDistName(), info.getDistCode(),info.getBlockName(), info.getBlockCode(), "","",info.getInspId(), info.getStrName(), "", "", "str", info.getPanchayatName(), info.getVILLNAME(), info.getPanchayatCode(), "");
                GrievanceEntryDetail scheme = new GrievanceEntryDetail(info.getDistName(), info.getDistCode(),info.getBlockName(), info.getBlockCode(), info.getPanchayatName(),info.getPanchayatCode(),"",info.getVILLCODE(),info.getVILLNAME(),"","", "str", info.getStrid(),info.getStrName(),info.getLatitude(), info.getLongitude());
                scheme.setSchemeId(info.getInspId());
                scheme.setLatitudeComp(info.getLatitude());
                scheme.setLongitudeComp(info.getLongitude());
                scheme.setWork_StructureName(info.getStrName());
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
                Uri mapUri = Uri.parse("geo:0,0?q="+info.getLatitude()+","+info.getLongitude()+"("+info.getInspId()+")");
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
        TextView tv_scheme_id,tv_scheme_name,tv_est_amnt,tv_panchayat,tv_village,tv_structure,tv_functional;
        LinearLayout sblist,ll_structure;
        ImageView iv_show_location;

        ViewHolder(View itemView)

        {
            super(itemView);
            tv_scheme_id = itemView.findViewById(R.id.tv_scheme_id);
            tv_scheme_name = itemView.findViewById(R.id.tv_scheme_name);
            //tv_est_amnt = itemView.findViewById(R.id.tv_est_amnt);
            tv_panchayat = itemView.findViewById(R.id.tv_panchayat);
            tv_village = itemView.findViewById(R.id.tv_village);
            sblist = itemView.findViewById(R.id.sblist);
            tv_structure = itemView.findViewById(R.id.tv_structure);
            ll_structure = itemView.findViewById(R.id.ll_structure);
            iv_show_location = itemView.findViewById(R.id.iv_show_location);
            tv_functional = itemView.findViewById(R.id.tv_functional);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    StructureDetailPublic getItem(int id) {
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
