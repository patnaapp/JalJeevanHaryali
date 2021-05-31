package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
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
import bih.in.jaljeevanharyali.activity.ManregaActivity;
import bih.in.jaljeevanharyali.activity.ManregaListActivity;
import bih.in.jaljeevanharyali.activity.OtherDeptInspectionActivity;
import bih.in.jaljeevanharyali.activity.OtherSchemeActivity;
import bih.in.jaljeevanharyali.activity.OtherSchemeListActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class OtherSchemeEditAdaptor  extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    UserDetails userInfo;

    Activity activity;
    LayoutInflater mInflater;

    ArrayList<ManregaSchemeDetail> ThrList=new ArrayList<>();

    public OtherSchemeEditAdaptor(OtherSchemeListActivity listViewshowedit, ArrayList<ManregaSchemeDetail> rlist, UserDetails userInfo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.userInfo = userInfo;

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
        OtherSchemeEditAdaptor.ViewHolder holder = null;
        //if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adaptor_marega_list, null);

            holder = new OtherSchemeEditAdaptor.ViewHolder();
            holder.tv_panchayat=(TextView)convertView.findViewById(R.id.tv_panchayat);
            holder.tv_kriwayan_vibhag=(TextView)convertView.findViewById(R.id.tv_kriwayan_vibhag);
            holder.tv_abya_name=(TextView)convertView.findViewById(R.id.tv_abya_name);
            holder.tv_prashasnik_date=(TextView)convertView.findViewById(R.id.tv_prashasnik_date);
            holder.tv_scheme_type=(TextView)convertView.findViewById(R.id.tv_scheme_type);
            holder.tv_yojna_code=(TextView)convertView.findViewById(R.id.tv_yojna_code);

            holder.btn_remove=(Button) convertView.findViewById(R.id.btn_remove);
            holder.btn_edit=(Button)convertView.findViewById(R.id.btn_edit);
            holder.btn_upload=(Button)convertView.findViewById(R.id.btn_upload);

            convertView.setTag(holder);

            holder.tv_panchayat.setText(ThrList.get(position).getPanchayatName());
            holder.tv_kriwayan_vibhag.setText(ThrList.get(position).getExectDeptName());
            holder.tv_abya_name.setText(ThrList.get(position).getSubDivName());
            holder.tv_prashasnik_date.setText(ThrList.get(position).getAdministrative_Approval_Date());
            holder.tv_yojna_code.setText(ThrList.get(position).getScheme_Code());

            String yojnaType = "NA";
            if(ThrList.get(position).getYojnaType() != null){
                yojnaType = ThrList.get(position).getYojnaType().equals("U") ? "उद्घाटन" : "शिलान्यास";
            }

            holder.tv_scheme_type.setText(yojnaType);

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
                                    int type=0;
                                    if(ThrList.get(position).getIsPhase3Inspected() != null && ThrList.get(position).getIsPhase3Inspected().equals("Y")){
                                        type = 3;
                                    }else{
                                        if(ThrList.get(position).getIsPhase2Inspected() != null && ThrList.get(position).getIsPhase2Inspected().equals("Y")){
                                            type = 2;
                                        }else{
                                            type = 1;
                                        }
                                    }
                                    long c = dataBaseHelper.deleteOtherSchemeData(inspectionid, type);

                                    if(c>0)
                                    {
//
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
                    //Toast.makeText(activity, "Edit", Toast.LENGTH_SHORT).show();

                    Intent i=new Intent(activity, OtherDeptInspectionActivity.class);
//                    i.putExtra("blockCode", userInfo.getBlockCode());
//                    i.putExtra("blockName", userInfo.getBlockName());
//                    i.putExtra("DistrictCode", userInfo.getDistrictCode());
//                    i.putExtra("DistrictName", userInfo.getDistName());
//                    i.putExtra("userId", userInfo.getUserID());
                    i.putExtra("id", ThrList.get(position).getId());
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
                                        int type=0;
                                        if(ThrList.get(position).getIsPhase3Inspected() != null && ThrList.get(position).getIsPhase3Inspected().equals("Y")){
                                            type = 3;
                                        }else{
                                            if(ThrList.get(position).getIsPhase2Inspected() != null && ThrList.get(position).getIsPhase2Inspected().equals("Y")){
                                                type = 2;
                                            }else{
                                                type = 1;
                                            }
                                        }
                                        new OtherSchemeEditAdaptor.UploadOtherSchemeDetail(ThrList.get(position), position, type).execute();
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
//        }
//        else {
//            holder = (OtherSchemeEditAdaptor.ViewHolder) convertView.getTag();
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_panchayat,tv_kriwayan_vibhag,tv_abya_name,tv_prashasnik_date,tv_scheme_type,tv_yojna_code;
        Button btn_remove,btn_edit,btn_upload;

    }

    private class UploadOtherSchemeDetail extends AsyncTask<String, Void, String> {
        ManregaSchemeDetail data;
        int position,type;
        private final ProgressDialog dialog = new ProgressDialog(activity);

        UploadOtherSchemeDetail(ManregaSchemeDetail data, int position, int type) {
            this.data = data;
            this.position = position;
            this.type = type;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("अपलोड हो राहा है...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {


            String res = WebServiceHelper.uploadOtherdata(this.data, type);
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
                    long isDel = localDBHelper.resetOtherDeptData(String.valueOf(this.data.getId()), type);
                    if (isDel > 0) {
                        Log.e("messagdelete", "Data deleted !!");
                    } else {
                        Log.e("message", "data is uploaded but not deleted !!");
                    }

                    Toast.makeText(activity, "प्रेषण हो गया", Toast.LENGTH_SHORT).show();
                    chk_msg("डेटा अपलोड हो गया", "डेटा अपलोड हो गया");
                    ThrList.remove(position);
                    notifyDataSetChanged();
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

        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }

}
