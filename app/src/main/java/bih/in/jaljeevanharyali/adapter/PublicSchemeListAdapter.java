package bih.in.jaljeevanharyali.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.grievance.RegisterGrievanceActivity;
import bih.in.jaljeevanharyali.entity.GrievanceEntryDetail;
import bih.in.jaljeevanharyali.entity.SchemeDetailPublic;
import bih.in.jaljeevanharyali.utility.Utiilties;


public class PublicSchemeListAdapter extends RecyclerView.Adapter<PublicSchemeListAdapter.ViewHolder> {

    private ArrayList<SchemeDetailPublic> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public PublicSchemeListAdapter(Context context, ArrayList<SchemeDetailPublic> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_schemelist, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final SchemeDetailPublic info = mData.get(position);
        holder.tv_scheme_id.setText(info.getMIS_Scheme_Code());
        holder.tv_scheme_name.setText(getNameWithSerial(info.getWork_StructureName(), position));
        holder.tv_est_amnt.setText(Utiilties.getNumberFormatString(info.getEstimated_Amount()));
        holder.tv_panchayat.setText(info.getPanchayatName());
        holder.tv_structure.setText(info.getStructureTypeNameHn());
        holder.tv_dept.setText(info.getExecution_DeptName());
        holder.tv_scheme_status.setText(info.getWorkStatus().equals("C") ? "Completed" : "Ongoing");
        holder.tv_anumodan_tithi.setText(Utiilties.convertDateStringFormet("dd/MM/yyyy HH:mm:ss", "dd MMM yyyy",info.getApproval_Date()));
        holder.tv_nidhi_srot.setText(info.getSourceOfFund());
        holder.tv_work_comp_date.setText(Utiilties.convertDateStringFormet("dd/MM/yyyy HH:mm:ss", "dd MMM yyyy",info.getWorkCompDate()));
        holder.tv_mbr_amount.setText(Utiilties.getNumberFormatString(info.getFinalAmount()));

        if(info.getLatitude().equals("NA")){
            holder.tv_insp_status.setText("Not Yet Inspected");
            holder.iv_show_location.setVisibility(View.GONE);
        }else{
            holder.tv_insp_status.setVisibility(View.GONE);
        }

        holder.sblist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GrievanceEntryDetail scheme = new GrievanceEntryDetail(info.getDistName(), info.getDistCode(),info.getBlockName(), info.getBlockCode(), info.getApproval_Date(),info.getMIS_Scheme_Code(),info.getSchemeCode(), info.getWork_StructureName(), info.getAwayabId(), info.getSub_Execution_DeptName(), "sch", info.getPanchayatName(), info.getVILLNAME(), info.getPanchayatCode(), "");
                GrievanceEntryDetail scheme = new GrievanceEntryDetail(info.getDistName(), info.getDistCode(),info.getBlockName(), info.getBlockCode(), info.getPanchayatName(), info.getPanchayatCode(),"",info.getVillageCode(), info.getVILLNAME(), info.getAwayabId(), info.getSub_Execution_DeptName(), "sch", info.getStructureId(), info.getStructureTypeNameHn(), info.getLatitude(), info.getLongitude());
                scheme.setDeptId(info.getExecution_DeptId());
                scheme.setDeptName(info.getExecution_DeptName());
                scheme.setApproval_Date(info.getApproval_Date());
                scheme.setMIS_Scheme_Code(info.getMIS_Scheme_Code());
                scheme.setSchemeId(info.getSchemeCode());
                scheme.setWork_StructureName(info.getWork_StructureName());
                scheme.setPraklitRashi(info.getEstimated_Amount());
                scheme.setNidhiKaSrot(info.getSourceOfFund());
                scheme.setWorkCompDate(info.getWorkCompDate());
                scheme.setMBAmount(info.getFinalAmount());
                scheme.setLatitudeComp(info.getLatitude());
                scheme.setLongitudeComp(info.getLongitude());
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
                Uri mapUri = Uri.parse("geo:0,0?q="+info.getLatitude()+","+info.getLongitude()+"("+info.getWork_StructureName()+")");
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

    public String getNameWithSerial(String name, int position){
        if(name.equals("anyType{}")){
            return String.valueOf(position+1)+")  NA";
        }else{
            return String.valueOf(position+1)+")  " +name;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_scheme_id,tv_scheme_name,tv_est_amnt,tv_panchayat,tv_structure,tv_dept,tv_scheme_status,tv_anumodan_tithi,tv_nidhi_srot,tv_work_comp_date,tv_mbr_amount,tv_insp_status;
        LinearLayout sblist;
        ImageView iv_show_location;

        ViewHolder(View itemView) {
            super(itemView);
            tv_scheme_id = itemView.findViewById(R.id.tv_scheme_id);
            tv_scheme_name = itemView.findViewById(R.id.tv_scheme_name);
            tv_est_amnt = itemView.findViewById(R.id.tv_est_amnt);
            tv_panchayat = itemView.findViewById(R.id.tv_panchayat);
            sblist = itemView.findViewById(R.id.sblist);
            tv_structure = itemView.findViewById(R.id.tv_structure);
            tv_dept = itemView.findViewById(R.id.tv_dept);
            tv_anumodan_tithi = itemView.findViewById(R.id.tv_anumodan_tithi);
            tv_nidhi_srot = itemView.findViewById(R.id.tv_nidhi_srot);
            tv_work_comp_date = itemView.findViewById(R.id.tv_work_comp_date);
            tv_mbr_amount = itemView.findViewById(R.id.tv_mbr_amount);
            tv_insp_status = itemView.findViewById(R.id.tv_insp_status);

            tv_scheme_status = itemView.findViewById(R.id.tv_scheme_status);
            iv_show_location = itemView.findViewById(R.id.iv_show_location);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    SchemeDetailPublic getItem(int id) {
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
