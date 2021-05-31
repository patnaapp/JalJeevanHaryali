package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondInspectionActivity;
import bih.in.jaljeevanharyali.activity.PondListEditActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class PondEditAdaptor extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    Activity activity;
    LayoutInflater mInflater;

    ArrayList<PondEntity> ThrList=new ArrayList<>();
    String version;

    public PondEditAdaptor(PondListEditActivity listViewshowedit, ArrayList<PondEntity> rlist, String version) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.version = version;
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
        PondEditAdaptor.ViewHolder holder = null;
        //if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adaptor_pond_edit, null);

            holder = new PondEditAdaptor.ViewHolder();
            holder.insId=(TextView)convertView.findViewById(R.id.ins_Id);
            holder.rajswaThanaNo=(TextView)convertView.findViewById(R.id.rajswaThanaNo);
            holder.village=(TextView)convertView.findViewById(R.id.tv_village);
            holder.block=(TextView)convertView.findViewById(R.id.block);
            holder.district=(TextView)convertView.findViewById(R.id.tv_district);

//            holder.btn_remove=(Button) convertView.findViewById(R.id.btn_remove);
//            holder.btn_edit=(Button)convertView.findViewById(R.id.btn_edit);
//            holder.btn_upload=(Button)convertView.findViewById(R.id.btn_upload);
        holder.tv_remove = convertView.findViewById(R.id.tv_remove);
        holder.tv_edit = convertView.findViewById(R.id.tv_edit);
        holder.tv_upload = convertView.findViewById(R.id.tv_upload);

            holder.ll_struct=(LinearLayout) convertView.findViewById(R.id.ll_struct);

            convertView.setTag(holder);

            holder.insId.setText(ThrList.get(position).getInspectionID());
            holder.rajswaThanaNo.setText(ThrList.get(position).getRajswaThanaNumber());
            holder.village.setText(ThrList.get(position).getVillageName());
            holder.block.setText(ThrList.get(position).getBlockName());
            holder.district.setText(ThrList.get(position).getDistName());

            holder.ll_struct.setVisibility(View.GONE);

            holder.tv_remove.setOnClickListener(new View.OnClickListener() {
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
                                    long c = dataBaseHelper.resetPondInspectionUpdatedData(inspectionid);

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

            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(activity, "Edit", Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(activity, PondInspectionActivity.class);
                    i.putExtra("id", ThrList.get(position).getId());
                    i.putExtra("panchayatCode", "");
                    i.putExtra("panchayatName", "");
                    activity.startActivity(i);
                }
            });

            holder.tv_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(activity, String.valueOf(ThrList.get(position).getId()), Toast.LENGTH_SHORT).show();

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
                                        PondInspectionDetail info = dataBaseHelper.getPondInspectionDetails(pid);
                                        info.setId(Integer.parseInt(pid));
                                        new UploadPondLakeDetail(info, position, version).execute();
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
        TextView insId,rajswaThanaNo,village,block,district;
        //Button btn_remove,btn_edit,btn_upload;
        TextView tv_remove,tv_edit,tv_upload;
        LinearLayout ll_struct;

    }

    private class UploadPondLakeDetail extends AsyncTask<String, Void, String> {
        PondInspectionDetail data;
        String version;
        int position;
        private final ProgressDialog dialog = new ProgressDialog(activity);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
        String username = prefs.getString("uid", "user");
        String password = prefs.getString("pass", "password");
        UserDetails userInfo = dataBaseHelper.getUserDetails(username, password);
        // private final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(UserHome.this).create();


        UploadPondLakeDetail(PondInspectionDetail data, int position, String version) {
            this.data = data;
            this.position = position;
            this.version = version;
        }

        @Override
        protected void onPreExecute() {

            this.dialog.setCanceledOnTouchOutside(false);
            this.dialog.setMessage("अपलोड हो राहा है...");
            this.dialog.show();
        }

        @Override
        protected String doInBackground(String... param) {

            String res = WebServiceHelper.uploadPondLakeDate(this.data, userInfo, version);
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
                    long isDel = localDBHelper.resetPondInspectionUpdatedData(String.valueOf(this.data.getId()));
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
                    Toast.makeText(activity, "अपलोड विफल, कृपया बाद में पुनः प्रयास करें!", Toast.LENGTH_SHORT).show();
                }

            }
            else {

                Toast.makeText(activity, "null record", Toast.LENGTH_SHORT).show();
            }



        }
    }

    public void refresh(ArrayList<PondEntity> events) {
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

        // ab.create().getWindow().getAttributes().windowAnimations = R.style.alert_animation;
        ab.show();
    }

}