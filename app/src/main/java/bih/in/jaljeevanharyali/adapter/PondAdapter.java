package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
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
import java.util.Locale;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.PondInspectionActivity;
import bih.in.jaljeevanharyali.activity.PondListActivity;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;

public class PondAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater mInflater;
    ArrayList<PondInspectionEntity> ThrList=new ArrayList<>();
    String panchayatCode,panchayatName="";
    UserDetails userInfo;

    public PondAdapter(Activity listViewshowedit, ArrayList<PondInspectionEntity> rlist, String panchayatCode, String panchayatName, UserDetails userInfo) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.panchayatCode = panchayatCode;
        this.panchayatName = panchayatName;
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
        ViewHolder holder = null;
        //if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adaptor_well, null);

            holder = new ViewHolder();
            //holder.insId=(TextView)convertView.findViewById(R.id.ins_Id);
            holder.rajswaThanaNo=(TextView)convertView.findViewById(R.id.rajswaThanaNo);
            holder.tv_district=(TextView)convertView.findViewById(R.id.tv_district);
            holder.block=(TextView)convertView.findViewById(R.id.block);
            holder.tv_village=(TextView)convertView.findViewById(R.id.tv_village);
            holder.tv_latitude=(TextView)convertView.findViewById(R.id.tv_latitude);
            holder.tv_longitude=(TextView)convertView.findViewById(R.id.tv_longitude);
            holder.tv_sanrachna_type=(TextView)convertView.findViewById(R.id.tv_sanrachna_type);
            holder.btn_map=(Button)convertView.findViewById(R.id.btn_map);
            holder.btn_inspect=(Button)convertView.findViewById(R.id.btn_inspect);

            holder.ll_type=convertView.findViewById(R.id.ll_type);
            convertView.setTag(holder);

            //holder.insId.setText(ThrList.get(position).getInspectionID());
            holder.rajswaThanaNo.setText(ThrList.get(position).getRajswaThanaNumber());
            holder.tv_sanrachna_type.setText(ThrList.get(position).getPondCatValue());
            holder.tv_district.setText(ThrList.get(position).getDistName());
            holder.block.setText(ThrList.get(position).getBlockName());
            holder.tv_village.setText(ThrList.get(position).getVillageName());
            holder.tv_latitude.setText(ThrList.get(position).getLatitude());
            holder.tv_longitude.setText( ThrList.get(position).getLongitude());
            Log.e("pond", ThrList.get(position).getInspectionID() +" "+ThrList.get(position).getLatitude());

            if(userInfo.getUserrole().equals("PRDDST")){
                holder.ll_type.setVisibility(View.VISIBLE);
            }else{
                holder.ll_type.setVisibility(View.GONE);
            }

            holder.btn_map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri mapUri = Uri.parse("geo:0,0?q="+ThrList.get(position).getLatitude()+","+ThrList.get(position).getLongitude()+"("+ThrList.get(position).getInspectionID()+")");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, mapUri);
                    mapIntent.setPackage("com.google.android.apps.maps");

                    if (mapIntent.resolveActivity(activity.getPackageManager()) != null) {
                        activity.startActivity(mapIntent);
                    }else{
                        Toast.makeText(activity, "कृपया इस सुविधा का उपयोग करने के लिए Google मानचित्र स्थापित करें", Toast.LENGTH_SHORT).show();
                    }
                    //activity.startActivity(mapIntent);

                }
            });

            holder.btn_inspect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, PondInspectionActivity.class);
                    i.putExtra("id", ThrList.get(position).getId());
                    i.putExtra("panchayatCode", panchayatCode);
                    i.putExtra("panchayatName", panchayatName);
                    i.putExtra("user", userInfo);
                    //listid=data.get(position).getSlno();
                    activity.startActivity(i);
                    activity.finish();
                }
            });
//        }
//        else {
//            holder = (ViewHolder) convertView.getTag();
//        }


        return convertView;
    }


    private class ViewHolder {
        TextView insId,rajswaThanaNo,tv_district,block,tv_village,tv_latitude,tv_longitude,tv_sanrachna_type;
        Button btn_map,btn_inspect;
        LinearLayout ll_type;
    }



}
