package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.grievance.GrievanceDetailActivity;
import bih.in.jaljeevanharyali.activity.grievance.TrackGrievanceActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.UploadComplainEntity;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class TrackAdapter extends BaseAdapter {

    DataBaseHelper dataBaseHelper;
    Activity activity;
    LayoutInflater mInflater;
    ArrayList<UploadComplainEntity> List=new ArrayList<>();
    String singlerowid;

    public TrackAdapter(TrackGrievanceActivity editTHRList, ArrayList<UploadComplainEntity> chayansevika) {
        this.activity=editTHRList;
        this.List=chayansevika;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount()
    {
        return List.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
       // if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adaptor_track_grievance, null);

            holder = new ViewHolder();
            holder.tv_complain_id= convertView.findViewById(R.id.tv_complain_id);
            holder.tv_complain_date= convertView.findViewById(R.id.tv_complain_date);
            holder.tv_complain_status= convertView.findViewById(R.id.tv_complain_status);
            holder.tv_application_detail= convertView.findViewById(R.id.tv_application_detail);
            holder.tv_view_detail= convertView.findViewById(R.id.tv_view_detail);
            holder.tv_complain_related= convertView.findViewById(R.id.tv_complain_related);


            holder.tv_complain_id.setText(List.get(position).getComplainId1());
            holder.tv_complain_date.setText(Utiilties.convertDateStringFormet("dd/MM/yyyy HH:mm:ss", "dd MMM yyyy",List.get(position).getComplainDate()));
            holder.tv_complain_status.setText(List.get(position).getStatus());
            holder.tv_application_detail.setText(List.get(position).getComplainRemarks());
            holder.tv_complain_related.setText(getComplainType(List.get(position).getGrievanceRelated()));

            holder.tv_view_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(activity, GrievanceDetailActivity.class);
                    i.putExtra("data", List.get(position));
                    activity.startActivity(i);
                }
            });

            convertView.setTag(holder);
//        }
//        else {
//            holder = (ViewHolder) convertView.getTag();
//        }
        return convertView;
    }

    public String getComplainType(String type){
        if(type.equals("sch"))
            return "Scheme";
        else if(type.equals("str"))
            return "Structure";
        else
            return "Other";
    }


    private class ViewHolder {
        TextView tv_complain_id,tv_complain_date,tv_complain_status,tv_application_detail,tv_view_detail,tv_complain_related;
        ImageView img_upload;

    }
}
