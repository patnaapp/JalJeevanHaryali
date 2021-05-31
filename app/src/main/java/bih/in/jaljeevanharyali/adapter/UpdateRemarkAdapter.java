package bih.in.jaljeevanharyali.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.remarkUpdate.RemarkUpdateInspectionActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.StructureRemarkEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class UpdateRemarkAdapter extends RecyclerView.Adapter<UpdateRemarkAdapter.ViewHolder> {

    private ArrayList<StructureRemarkEntity> mData;
    private LayoutInflater mInflater;
    Context context;
    TextView tv_title;
    Boolean isUpdate;

    public UpdateRemarkAdapter(Context context, ArrayList<StructureRemarkEntity> data, TextView tv_title, Boolean isUpdate) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = context;
        this.tv_title = tv_title;
        this.isUpdate = isUpdate;
    }


    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.adaptor_update_remark, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        final StructureRemarkEntity info = mData.get(position);

        holder.tv_count.setText(String.valueOf(position+1)+".");
        holder.tv_block.setText(info.getBlock_Name());
        holder.tv_panchayat.setText(info.getPanchayat_Name());
        holder.tv_village.setText(info.getVillage_Name());
        holder.tv_inspection_id.setText(info.getInspectionID());
        holder.tv_rajswa_thana.setText(info.getRajswaThanaNumber());
        holder.tv_khata_khesra.setText(info.getKhaata_Kheshara_Number());
        holder.tv_structure.setText(info.getTypesOfSarchna());


        if(isUpdate){
            holder.tv_remark.setText(info.getNewRemark());
            holder.ll_btn.setVisibility(View.VISIBLE);
        }else{
            holder.tv_remark.setText(info.getRemark());
            holder.ll_btn.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isUpdate){
                    Intent i = new Intent(context, RemarkUpdateInspectionActivity.class);
                    i.putExtra("data", info);
                    context.startActivity(i);
                }
            }
        });

        holder.iv_show_location.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Uri mapUri = Uri.parse("geo:0,0?q="+info.getLatitude()+","+info.getLongitude()+"("+info.getId()+")");
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

        holder.btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setIcon(R.drawable.logo)
                        .setTitle("निरीक्षण हटाएं!!")
                        .setMessage("क्या आप वाकई इस संरचना के निरीक्षण को हटाना चाहते हैं??")
                        .setCancelable(false)
                        .setPositiveButton("हटाएं", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               updateList(info.getInspectionID(), position);
                            }
                        })
                        .setNegativeButton("रद्द करें", null)
                        .show();


            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, RemarkUpdateInspectionActivity.class);
                i.putExtra("data", info);
                context.startActivity(i);
            }
        });

        holder.btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utiilties.isOnline(context)) {
                    new AlertDialog.Builder(context)
                            .setIcon(R.drawable.logo)
                            .setTitle(R.string.confirmation)
                            .setMessage("क्या आप डाटा अपलोड करना चाहते है?")
                            .setCancelable(false)
                            .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    new UploadRemarkDetail(info, position).execute();
                                }
                            })
                            .setNegativeButton("नहीं", null)
                            .show();

                } else {
                    Utiilties.showInternentAlert(context);
                }
            }
        });
    }

    public void updateList(String id, int position){
        DataBaseHelper dataBaseHelper = new DataBaseHelper(context);
        long c = dataBaseHelper.deleteRemarkUpdatedData(id);
        mData.remove(position);
        notifyDataSetChanged();
        tv_title.setText(AppConstant.UPDATEREMARK+" सूची ("+mData.size()+")");
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_block,tv_panchayat,tv_count,tv_village,tv_inspection_id,tv_rajswa_thana,tv_khata_khesra,tv_structure,tv_remark;
        Button btn_delete,btn_edit,btn_upload;
        ImageView iv_show_location;
        LinearLayout ll_btn;

        ViewHolder(View itemView) {
            super(itemView);
            tv_block = itemView.findViewById(R.id.tv_block);
            tv_panchayat = itemView.findViewById(R.id.tv_panchayat);
            tv_count = itemView.findViewById(R.id.tv_count);
            tv_village = itemView.findViewById(R.id.tv_village);
            tv_inspection_id = itemView.findViewById(R.id.tv_inspection_id);
            tv_rajswa_thana = itemView.findViewById(R.id.tv_rajswa_thana);
            tv_khata_khesra = itemView.findViewById(R.id.tv_khata_khesra);
            tv_structure = itemView.findViewById(R.id.tv_structure);
            tv_remark = itemView.findViewById(R.id.tv_remark);

            iv_show_location = itemView.findViewById(R.id.iv_show_location);
            ll_btn = itemView.findViewById(R.id.ll_btn);

            btn_delete = itemView.findViewById(R.id.btn_delete);
            btn_edit = itemView.findViewById(R.id.btn_edit);
            btn_upload = itemView.findViewById(R.id.btn_upload);
        }

        @Override
        public void onClick(View view) {
            //if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    StructureRemarkEntity getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {

    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    private class UploadRemarkDetail extends AsyncTask<String, Void, String> {
        StructureRemarkEntity data;
        int position;

        private final ProgressDialog dialog = new ProgressDialog(context);

        UploadRemarkDetail(StructureRemarkEntity data, int position) {
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

            String res = WebServiceHelper.uploadRemarkDetail(this.data);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            if (this.dialog.isShowing()) {
                this.dialog.dismiss();
            }
            Log.d("Responsevalue",""+result);

            if (result != null) {
                if (result.equalsIgnoreCase("1")) {
                    updateList(data.getInspectionID(), position);
                }
                else  if (result.equalsIgnoreCase("0")) {
                    Toast.makeText(context, "अपलोड विफल, कृपया बाद में पुनः प्रयास करें!", Toast.LENGTH_SHORT).show();
                }
            }
            else {

                Toast.makeText(context, "null record", Toast.LENGTH_SHORT).show();
            }
        }
    }

}




