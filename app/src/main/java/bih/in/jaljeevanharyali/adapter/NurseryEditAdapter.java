package bih.in.jaljeevanharyali.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.nursery.NurseryEntryActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;


public class NurseryEditAdapter extends RecyclerView.Adapter<NurseryEditAdapter.ViewHolder> {

    private ArrayList<NurseryEntity> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    public NurseryEditAdapter(Context context, ArrayList<NurseryEntity> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_nursery_edit, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NurseryEntity info = mData.get(position);

        setDataToView(info, holder, position);

        holder.tv_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.logo1)
                        .setTitle(R.string.confirmation)
                        .setMessage("क्या आप डाटा हटाना चाहते है?")
                        .setCancelable(false)
                        .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                deleteSaveData(position, "Deleted");
                            }
                        })
                        .setNegativeButton("नहीं", null)
                        .show();
            }
        });

        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NurseryEntity obj = mData.get(position);
                Intent intent = new Intent(context, NurseryEntryActivity.class);
                intent.putExtra("type", obj.getStrType());
                intent.putExtra("data", obj);
                context.startActivity(intent);
            }
        });

        holder.tv_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.logo1)
                        .setTitle(R.string.confirmation)
                        .setMessage("क्या आप डाटा अपलोड करना चाहते है?")
                        .setCancelable(false)
                        .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                proceedUpdate(mData.get(position), position);
                            }
                        })
                        .setNegativeButton("नहीं", null)
                        .show();
            }
        });
    }

    public void setDataToView(NurseryEntity info, ViewHolder holder, int position){
        if(info.getStrType().equals(AppConstant.NURSURY)){
            holder.ll_nursury.setVisibility(View.VISIBLE);
            holder.ll_building.setVisibility(View.GONE);

            holder.tv_name.setText(String.valueOf(position+1)+")  " +info.getNursury_Name());
            holder.tv_panchayat.setText(info.getPanchayat_Name());
            holder.tv_village.setText(info.getVillage_Name());
            holder.tv_ward.setText(info.getWard_Name());
            holder.tv_area.setText(info.getArea());
            holder.tv_tree.setText(info.getTree());
            holder.tv_nursury_id.setText(info.getInspectionID());

        }else{
            holder.ll_nursury.setVisibility(View.GONE);
            holder.ll_building.setVisibility(View.VISIBLE);

            holder.tv_build_name.setText(String.valueOf(position+1)+")  " +info.getBuildingName());
            holder.tv_building_id.setText(info.getInspectionID() == null ? "New Entry" : info.getInspectionID());
            holder.tv_area_build.setText(info.getArea());
            holder.tv_dept_name.setText(info.getExecution_DeptName());
            holder.tv_panchayat_build.setText(info.getPanchayat_Name());
            holder.tv_area_type.setText(info.evaluatAreaName());
            holder.tv_ward_build.setText(info.getWard_Name());
        }
    }

    public void deleteSaveData(int position, String type){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        String id = String.valueOf(mData.get(position).getId());
        long c = dataBaseHelper.resetNursuryBuildingUpdatedData(id);

        if(c>0)
        {
            Toast.makeText(context, type+" Successfully",Toast.LENGTH_SHORT).show();
            mData.remove(position);
            notifyDataSetChanged();
        }
        else
        {
            Toast.makeText(context, "Failed to delete",Toast.LENGTH_SHORT).show();
        }
    }

    public void proceedUpdate(NurseryEntity entity, int position){
        if (Utiilties.isOnline(context)) {
            new UploadDetail(entity, position).execute();
        }
        else {
            Utiilties.showAlet(context);
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_name,tv_panchayat,tv_village,tv_ward,tv_area,tv_tree,tv_nursury_id;
        TextView tv_remove,tv_edit,tv_upload;

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

            tv_remove = itemView.findViewById(R.id.tv_remove);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_upload = itemView.findViewById(R.id.tv_upload);

            tv_build_name = itemView.findViewById(R.id.tv_build_name);
            tv_building_id = itemView.findViewById(R.id.tv_building_id);
            tv_area_build = itemView.findViewById(R.id.tv_area_build);
            tv_dept_name = itemView.findViewById(R.id.tv_dept_name);
            tv_panchayat_build = itemView.findViewById(R.id.tv_panchayat_build);
            tv_area_type = itemView.findViewById(R.id.tv_area_type);
            tv_ward_build = itemView.findViewById(R.id.tv_ward_build);

            ll_nursury = itemView.findViewById(R.id.ll_nursery_edit);
            ll_building = itemView.findViewById(R.id.ll_building_edit);
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


    private class UploadDetail extends AsyncTask<String, Void, String> {
        NurseryEntity data;
        int position;

        private final ProgressDialog dialog = new ProgressDialog(context);

        UploadDetail(NurseryEntity data, int position) {
            this.data = data;
            this.position = position;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("अपलोड हो राहा है...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            return WebServiceHelper.uploadNursuryDate(this.data);
        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }

            onGotResponse(result, position);
        }
    }

    public void onGotResponse(String result, int position){
        Log.d("Responsevalue",""+result);

        if (result != null) {
            if (result.equalsIgnoreCase("1")) {
                deleteSaveData(position, "Uploaded");

                chk_msg("डेटा अपलोड हो गया", "डेटा अपलोड हो गया");
            }
            else  if (result.equalsIgnoreCase("0")) {
                Toast.makeText(context, "अपलोड विफल, कृपया बाद में पुनः प्रयास करें!", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(context, "null record", Toast.LENGTH_SHORT).show();
        }
    }

    public void chk_msg(String title, String msg) {
        AlertDialog.Builder ab = new AlertDialog.Builder(context);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.logo1);
        ab.setTitle(title);
        Dialog dialog = new Dialog(context);
        dialog.setCanceledOnTouchOutside(false);
        ab.setPositiveButton("ओके", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

                dialog.dismiss();
            }
        });

        ab.show();
    }

}
