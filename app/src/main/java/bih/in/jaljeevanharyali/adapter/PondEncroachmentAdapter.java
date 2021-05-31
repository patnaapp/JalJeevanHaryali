package bih.in.jaljeevanharyali.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import bih.in.jaljeevanharyali.activity.PondInspectionActivity;
import bih.in.jaljeevanharyali.activity.PondListActivity;
import bih.in.jaljeevanharyali.activity.PondListEditActivity;
import bih.in.jaljeevanharyali.activity.userCo.MarkRemoveEncrhmntActivity;
import bih.in.jaljeevanharyali.activity.userCo.PondEncroachmentListActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class PondEncroachmentAdapter extends BaseAdapter {

    Activity activity;
    LayoutInflater mInflater;

    ArrayList<PondEncroachmentEntity> ThrList=new ArrayList<>();
    String panchayatCode,panchayatName="";
    String version;
    String structureType;

    public PondEncroachmentAdapter(PondEncroachmentListActivity listViewshowedit, ArrayList<PondEncroachmentEntity> rlist, String panchayatCode, String panchayatName, String structureType) {
        this.activity=listViewshowedit;
        this.ThrList=rlist;
        mInflater = (LayoutInflater)activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        this.panchayatCode = panchayatCode;
        this.panchayatName = panchayatName;
        this.structureType = structureType;

        Log.e("pond", String.valueOf(rlist.size()));
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

        convertView = mInflater.inflate(R.layout.adaptor_pondwell_encrhmnt, null);

        holder = new ViewHolder();
        holder.insId=(TextView)convertView.findViewById(R.id.ins_Id);
        holder.rajswaThanaNo=(TextView)convertView.findViewById(R.id.rajswaThanaNo);
        holder.tv_district=(TextView)convertView.findViewById(R.id.tv_district);
        holder.block=(TextView)convertView.findViewById(R.id.block);
        holder.tv_village=(TextView)convertView.findViewById(R.id.tv_village);
        holder.tv_latitude=(TextView)convertView.findViewById(R.id.tv_latitude);
        holder.tv_longitude=(TextView)convertView.findViewById(R.id.tv_longitude);
        holder.inspection_status=(TextView)convertView.findViewById(R.id.inspection_status);
        holder.encrhmnt_status=(TextView)convertView.findViewById(R.id.encrhmnt_status);

        holder.btn_map=(Button)convertView.findViewById(R.id.btn_map);
        holder.btn_inspect=(Button)convertView.findViewById(R.id.btn_inspect);
        convertView.setTag(holder);

        holder.insId.setText(ThrList.get(position).getInspectionID());
        holder.rajswaThanaNo.setText(ThrList.get(position).getRajswaThanaNumber());
        holder.tv_district.setText(ThrList.get(position).getDistName());
        holder.block.setText(ThrList.get(position).getBlockName());
        holder.tv_village.setText(ThrList.get(position).getVILLNAME());
        holder.tv_latitude.setText(ThrList.get(position).getLatitude());
        holder.tv_longitude.setText( ThrList.get(position).getLongitude());

        String inspectStatus = ThrList.get(position).getIsInspected();

        //sif(inspectStatus.equals("Y")){

            if(ThrList.get(position).getStatus_Of_Encroachment().equals("Y")){
                holder.inspection_status.setText("पूर्ण");
                if(ThrList.get(position).getType_Of_Encroachment().equals("K")){
                    holder.encrhmnt_status.setText("कच्चा");
                }else{
                    holder.encrhmnt_status.setText("पक्का");
                }
            }else if(ThrList.get(position).getStatus_Of_Encroachment().equals("N")){
                holder.encrhmnt_status.setText("नहीं है");
                holder.inspection_status.setText("पूर्ण");
            }else{
                holder.encrhmnt_status.setText("अचिह्नित");
                holder.inspection_status.setText("अपूर्ण");
            }
//        }else{
//
//            holder.encrhmnt_status.setText("अचिह्नित");
//        }

        //if(inspectStatus.equals("Y")){
            if(ThrList.get(position).getStatus_Of_Encroachment().equals("Y")){
                holder.btn_inspect.setText("अतिक्रमण हटाए");
            }else if(ThrList.get(position).getStatus_Of_Encroachment().equals("N")){
                holder.btn_inspect.setVisibility(View.GONE);
            }else{
                holder.btn_inspect.setText("चिह्नित करे");
            }
//        }else{
//            holder.btn_inspect.setText("चिह्नित करे");
//        }

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

            }
        });

        holder.btn_inspect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encrhmntType = "";
                    if(ThrList.get(position).getStatus_Of_Encroachment().equals("Y")){
                        encrhmntType = "remove";
                    }else if(ThrList.get(position).getStatus_Of_Encroachment().equals("NA")){
                        encrhmntType = "mark";
                    }
                Intent i = new Intent(activity, MarkRemoveEncrhmntActivity.class);
                i.putExtra("id", ThrList.get(position).getId());
                i.putExtra("panchayatCode", panchayatCode);
                i.putExtra("panchayatName", panchayatName);
                i.putExtra("encrhmntType", encrhmntType);
                i.putExtra("structureType", structureType);
                i.putExtra("edit", false);
                activity.startActivity(i);
                //activity.finish();
            }
        });
        return convertView;
    }


    private class ViewHolder {
        TextView insId,rajswaThanaNo,tv_district,block,tv_village,tv_latitude,tv_longitude,inspection_status,encrhmnt_status;
        Button btn_map,btn_inspect;

    }
}
