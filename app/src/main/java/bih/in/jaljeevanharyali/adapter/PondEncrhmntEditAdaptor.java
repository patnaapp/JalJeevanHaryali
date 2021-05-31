package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondInspectionActivity;
import bih.in.jaljeevanharyali.activity.PondListEditActivity;
import bih.in.jaljeevanharyali.activity.userCo.MarkRemoveEncrhmntActivity;
import bih.in.jaljeevanharyali.activity.userCo.PondEncrhmntEditListActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class PondEncrhmntEditAdaptor extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    Activity activity;
    LayoutInflater mInflater;

    ArrayList<PondEncroachmentEntity> ThrList=new ArrayList<>();
    String structureType;

    public PondEncrhmntEditAdaptor(PondEncrhmntEditListActivity listViewshowedit, ArrayList<PondEncroachmentEntity> rlist, String structureType) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        this.structureType = structureType;
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
        convertView = mInflater.inflate(R.layout.adaptor_pond_encrhmnt_edit, null);

        holder = new ViewHolder();
        holder.rajswaThanaNo=(TextView)convertView.findViewById(R.id.rajswaThanaNo);
        holder.village=(TextView)convertView.findViewById(R.id.tv_village);
        holder.block=(TextView)convertView.findViewById(R.id.block);
        holder.tv_inspc_status=(TextView)convertView.findViewById(R.id.tv_inspc_status);
        holder.tv_encrh_status=(TextView)convertView.findViewById(R.id.tv_encrh_status);

        holder.btn_remove=(Button) convertView.findViewById(R.id.btn_remove);
        holder.btn_edit=(Button)convertView.findViewById(R.id.btn_edit);
        holder.btn_upload=(Button)convertView.findViewById(R.id.btn_upload);

        convertView.setTag(holder);

        holder.rajswaThanaNo.setText(ThrList.get(position).getRajswaThanaNumber());
        holder.village.setText(ThrList.get(position).getVILLNAME());
        holder.block.setText(ThrList.get(position).getBlockName());

        String inspectStatus = ThrList.get(position).getIsInspected();

       // if(inspectStatus.equals("Y")){

            if(ThrList.get(position).getStatus_Of_Encroachment().equals("Y")){
                holder.tv_inspc_status.setText("पूर्ण");
                if(ThrList.get(position).getType_Of_Encroachment().equals("K")){
                    holder.tv_encrh_status.setText("कच्चा");
                }else{
                    holder.tv_encrh_status.setText("पक्का");
                }
            }else if(ThrList.get(position).getStatus_Of_Encroachment().equals("N")){
                holder.tv_encrh_status.setText("नहीं है");
                holder.tv_inspc_status.setText("अपूर्ण");
            }else{
                holder.tv_encrh_status.setText("अचिह्नित");
                holder.tv_inspc_status.setText("अपूर्ण");
            }
//        }else{
//            holder.tv_inspc_status.setText("अपूर्ण");
//            holder.tv_encrh_status.setText("अचिह्नित");
//        }

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(activity)
                        .setIcon(R.drawable.logo1)
                        .setTitle(R.string.confirmation)
                        .setMessage("क्या आप डाटा हटाना चाहते है?")
                        .setCancelable(false)
                        .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dataBaseHelper = new DataBaseHelper(activity);
                                String inspectionid = String.valueOf(ThrList.get(position).getId());
                                long c = dataBaseHelper.resetEncrhmntUpdatedData(inspectionid, structureType);

                                if(c>0)
                                {
                                    Toast.makeText(activity, "Deleted Successfully",Toast.LENGTH_SHORT).show();
                                    ThrList.remove(position);
                                    notifyDataSetChanged();
                                }
                                else
                                {
                                    Toast.makeText(activity, "Failed to delete",Toast.LENGTH_SHORT).show();

                                }
                            }
                        })
                        .setNegativeButton("नहीं", null)
                        .show();


            }
        });

        holder.btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encrhmntType = ThrList.get(position).getUploadType().equals("R") ? "remove" : "mark";
//                if(ThrList.get(position).getIsInspected().equals("Y")){
//                    if(ThrList.get(position).getStatus_Of_Encroachment().equals("Y")){
//                        encrhmntType = "remove";
//                    }else if(ThrList.get(position).getStatus_Of_Encroachment().equals("NA")){
//                        encrhmntType = "mark";
//                    }
//                }else{
//                    encrhmntType = "mark";
//                }
                //Toast.makeText(activity, "Edit", Toast.LENGTH_SHORT).show();
                Intent i=new Intent(activity, MarkRemoveEncrhmntActivity.class);
                i.putExtra("id", ThrList.get(position).getId());
                i.putExtra("panchayatCode", "");
                i.putExtra("panchayatName", "");
                i.putExtra("encrhmntType", encrhmntType);
                i.putExtra("structureType", structureType);
                i.putExtra("edit", true);
                activity.startActivity(i);
            }
        });

        holder.btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(activity)
                        .setIcon(R.drawable.logo1)
                        .setTitle(R.string.confirmation)
                        .setMessage("क्या आप डाटा अपलोड करना चाहते है?")
                        .setCancelable(false)
                        .setPositiveButton("हाँ", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if (Utiilties.isOnline(activity)) {
                                    String pid = String.valueOf(ThrList.get(position).getId());
                                    dataBaseHelper = new DataBaseHelper(activity);
                                    PondEncroachmentEntity info = dataBaseHelper.getPondEncrhmntDetails(pid, structureType);
                                    info.setId(Integer.parseInt(pid));
                                    new PondEncrhmntEditAdaptor.UploadEncrhmntDetail(info, position).execute();
                                }
                                else {

                                    Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("नहीं", null)
                        .show();
            }
        });
        return convertView;
    }

    private class ViewHolder {
        TextView rajswaThanaNo,village,block,tv_inspc_status,tv_encrh_status;
        Button btn_remove,btn_edit,btn_upload;

    }

    private class UploadEncrhmntDetail extends AsyncTask<String, Void, String> {
        PondEncroachmentEntity data;
        int position;
        private final ProgressDialog dialog = new ProgressDialog(activity);

        UploadEncrhmntDetail(PondEncroachmentEntity data, int position) {
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


            String res = WebServiceHelper.uploadPondEnchrmntDate(this.data, structureType);
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
                    DataBaseHelper localDBHelper = new DataBaseHelper(activity);
                    // long c = placeData.deleterowconLab(userid);
                    long isDel = localDBHelper.resetEncrhmntUpdatedData(String.valueOf(this.data.getId()), structureType);
                    if (isDel > 0) {
                        Log.e("messagdelete", "Data deleted !!");
                        ThrList.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Log.e("message", "data is uploaded but not deleted !!");
                    }

                    Toast.makeText(activity, "प्रेषण हो गया", Toast.LENGTH_SHORT).show();
                    chk_msg("डेटा अपलोड हो गया", "डेटा अपलोड हो गया");
                    Log.d("Uploaded", "uploaded Id: "+String.valueOf(this.data.getId()));
                }
                else  if (result.equalsIgnoreCase("0")) {
                    Toast.makeText(activity, "प्रेषण फेल !", Toast.LENGTH_SHORT).show();
                }

            }
            else {

                Toast.makeText(activity, "null record", Toast.LENGTH_SHORT).show();
            }



        }
    }

    public void refresh(ArrayList<PondEncroachmentEntity> events) {
        this.ThrList.clear();
        this.ThrList.addAll(events);
        notifyDataSetChanged();
    }

    public void chk_msg(String title, String msg) {
        // final String wantToUpdate;
        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
        ab.setCancelable(false);
        ab.setIcon(R.drawable.logo1);
        ab.setTitle(title);
        //ab.setMessage(msg);
        Dialog dialog = new Dialog(activity);
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
