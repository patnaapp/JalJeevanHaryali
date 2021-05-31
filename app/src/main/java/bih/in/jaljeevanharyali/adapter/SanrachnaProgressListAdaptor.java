package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
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
import bih.in.jaljeevanharyali.activity.sanrachnaProgress.SanrachnaProgressConfirmActivity;
import bih.in.jaljeevanharyali.activity.sanrachnaProgress.SanrachnaProgressListActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.SanrachnaDataEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class SanrachnaProgressListAdaptor extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    UserDetails userInfo;

    Activity activity;
    LayoutInflater mInflater;

    ArrayList<SanrachnaDataEntity> ThrList=new ArrayList<>();

    public SanrachnaProgressListAdaptor(SanrachnaProgressListActivity listViewshowedit, ArrayList<SanrachnaDataEntity> rlist, UserDetails userInfo) {
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
        SanrachnaProgressListAdaptor.ViewHolder holder = null;
        //if (convertView == null) {
        convertView = mInflater.inflate(R.layout.adaptor_sanrachna_progress, null);

        holder = new ViewHolder();

        holder.tv_sanrachna=(TextView)convertView.findViewById(R.id.tv_sanrachna);
        holder.tv_vibhag=(TextView)convertView.findViewById(R.id.tv_vibhag);
        holder.tv_area=(TextView)convertView.findViewById(R.id.tv_area);
        holder.tv_gram=(TextView)convertView.findViewById(R.id.tv_gram);
        holder.tv_rajaswa_thana=(TextView)convertView.findViewById(R.id.tv_rajaswa_thana);
        holder.btn_map=(Button)convertView.findViewById(R.id.btn_map);
        holder.btn_inspect=(Button)convertView.findViewById(R.id.btn_inspect);
        convertView.setTag(holder);

        holder.tv_sanrachna.setText(Utiilties.getTypeOfSanrachna(ThrList.get(position).getTypesOfSarchna()));
        holder.tv_vibhag.setText(ThrList.get(position).getSwamitw_Dep().equals("4") ? ThrList.get(position).getSwamitwDep_Name() : ThrList.get(position).getDepatmentName());
        holder.tv_area.setText(ThrList.get(position).getArea_By_Govt_Record());
        holder.tv_gram.setText(ThrList.get(position).getVILLNAME());
        holder.tv_rajaswa_thana.setText(ThrList.get(position).getRajswaThanaNumber());
        //Log.e("pond", ThrList.get(position).getInspectionID() +" "+ThrList.get(position).getLatitude());

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
                Intent i = new Intent(activity, SanrachnaProgressConfirmActivity.class);
                SanrachnaDataEntity info = ThrList.get(position);
                i.putExtra("data", info);
                activity.startActivity(i);
                //activity.finish();
            }
        });
//        }
//        else {
//            holder = (OtherDeptInspectionListAdaptor.ViewHolder) convertView.getTag();
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_sanrachna,tv_vibhag,tv_area,tv_gram,tv_rajaswa_thana;
        Button btn_map,btn_inspect;

    }

}
