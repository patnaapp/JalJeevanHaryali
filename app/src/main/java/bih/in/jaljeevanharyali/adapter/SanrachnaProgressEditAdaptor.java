package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondInspectionActivity;
import bih.in.jaljeevanharyali.activity.sanrachnaProgress.SanrachnaProgressConfirmActivity;
import bih.in.jaljeevanharyali.activity.sanrachnaProgress.SanrachnaProgressEditActivity;
import bih.in.jaljeevanharyali.activity.sanrachnaProgress.SanrachnaProgressListActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.SanrachnaDataEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class SanrachnaProgressEditAdaptor extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    UserDetails userInfo;

    Activity activity;
    LayoutInflater mInflater;

    ArrayList<SanrachnaDataEntity> ThrList=new ArrayList<>();

    public SanrachnaProgressEditAdaptor(SanrachnaProgressEditActivity listViewshowedit, ArrayList<SanrachnaDataEntity> rlist) {
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
        SanrachnaProgressEditAdaptor.ViewHolder holder = null;
        //if (convertView == null) {
        convertView = mInflater.inflate(R.layout.adaptor_sanrachna_edit, null);

        holder = new ViewHolder();
        holder.tv_sanrachna=(TextView)convertView.findViewById(R.id.tv_sanrachna);
        holder.tv_vibhag=(TextView)convertView.findViewById(R.id.tv_vibhag);
        holder.tv_area=(TextView)convertView.findViewById(R.id.tv_area);
        holder.tv_gram=(TextView)convertView.findViewById(R.id.tv_gram);
        holder.tv_rajaswa_thana=(TextView)convertView.findViewById(R.id.tv_rajaswa_thana);
        holder.tv_panchayat=(TextView)convertView.findViewById(R.id.tv_panchayat);

        holder.btn_remove=(Button) convertView.findViewById(R.id.btn_remove);
        holder.btn_edit=(Button)convertView.findViewById(R.id.btn_edit);
        holder.btn_upload=(Button)convertView.findViewById(R.id.btn_upload);

        convertView.setTag(holder);

        holder.tv_sanrachna.setText(Utiilties.getTypeOfSanrachna(ThrList.get(position).getTypesOfSarchna()));
        holder.tv_vibhag.setText(ThrList.get(position).getSwamitw_Dep().equals("4") ? ThrList.get(position).getSwamitwDep_Name() : ThrList.get(position).getDepatmentName());
        holder.tv_area.setText(ThrList.get(position).getArea_By_Govt_Record());
        holder.tv_gram.setText(ThrList.get(position).getVILLNAME());
        holder.tv_rajaswa_thana.setText(ThrList.get(position).getRajswaThanaNumber());
        holder.tv_panchayat.setText(ThrList.get(position).getPanchayatName());

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
                                long c = dataBaseHelper.resetSanrachnaUpdatedData(inspectionid);

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
                Intent i = new Intent(activity, SanrachnaProgressConfirmActivity.class);
                SanrachnaDataEntity info = ThrList.get(position);
                i.putExtra("data", info);
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
                                    SanrachnaDataEntity info = ThrList.get(position);

                                    new UploadSanrachnaUpdatedDetail(info, position).execute();
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
//            holder = (PondEditAdaptor.ViewHolder) convertView.getTag();
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_sanrachna,tv_vibhag,tv_area,tv_gram,tv_rajaswa_thana,tv_panchayat;
        Button btn_remove,btn_edit,btn_upload;

    }

    private class UploadSanrachnaUpdatedDetail extends AsyncTask<String, Void, String> {
        SanrachnaDataEntity data;
        Integer position;
        private final ProgressDialog dialog = new ProgressDialog(activity);

        UploadSanrachnaUpdatedDetail(SanrachnaDataEntity data, Integer position) {
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


            String res = WebServiceHelper.uploadSanrachanDetail(this.data);
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
                    long isDel = localDBHelper.resetSanrachnaUpdatedData(String.valueOf(this.data.getId()));
                    if (isDel > 0) {
                        Log.e("messagdelete", "Data deleted !!");
                        ThrList.remove(position);
                        notifyDataSetChanged();
                    } else {
                        Log.e("message", "data is uploaded but not deleted !!");
                    }

                    Toast.makeText(activity, "प्रेषण हो गया", Toast.LENGTH_SHORT).show();
                    chk_msg("डेटा अपलोड हो गया", "डेटा अपलोड हो गया");
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

        ab.show();
    }

}
