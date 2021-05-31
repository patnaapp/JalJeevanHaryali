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
import bih.in.jaljeevanharyali.activity.ManregaInspectionListActivity;
import bih.in.jaljeevanharyali.activity.ManregaListActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;

public class ManregaInspectionListAdaptor extends BaseAdapter {

    public DataBaseHelper dataBaseHelper;

    UserDetails userInfo;

    Activity activity;
    LayoutInflater mInflater;

    ArrayList<ManregaSchemeDetail> ThrList=new ArrayList<>();

    public ManregaInspectionListAdaptor(ManregaInspectionListActivity listViewshowedit, ArrayList<ManregaSchemeDetail> rlist, UserDetails userInfo) {
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
        ManregaInspectionListAdaptor.ViewHolder holder = null;
        //if (convertView == null) {
            convertView = mInflater.inflate(R.layout.adaptor_manrega_inspection_list, null);

            holder = new ManregaInspectionListAdaptor.ViewHolder();
            holder.tv_village=(TextView)convertView.findViewById(R.id.tv_village);
            holder.tv_yojna_code=(TextView)convertView.findViewById(R.id.tv_yojna_code);
            holder.tv_kriwayan_vibhag=(TextView)convertView.findViewById(R.id.tv_kriwayan_vibhag);
            holder.tv_abya_name=(TextView)convertView.findViewById(R.id.tv_abya_name);
            holder.tv_prashasnik_date=(TextView)convertView.findViewById(R.id.tv_prashasnik_date);
            holder.tv_scheme_type=(TextView)convertView.findViewById(R.id.tv_scheme_type);

//            holder.btn_remove=(Button) convertView.findViewById(R.id.btn_remove);
//            holder.btn_edit=(Button)convertView.findViewById(R.id.btn_edit);
//            holder.btn_upload=(Button)convertView.findViewById(R.id.btn_upload);

            convertView.setTag(holder);

            holder.tv_village.setText(ThrList.get(position).getWard_Name());
            holder.tv_yojna_code.setText(ThrList.get(position).getScheme_Code());
            holder.tv_kriwayan_vibhag.setText(ThrList.get(position).getExectDeptName());
            holder.tv_abya_name.setText(ThrList.get(position).getSubDivName());
            holder.tv_prashasnik_date.setText(ThrList.get(position).getAdministrative_Approval_Date());

            String yojnaType = "NA";
            if(ThrList.get(position).getYojnaType() != null){
                yojnaType = ThrList.get(position).getYojnaType().equals("U") ? "उद्घाटन" : "शिलान्यास";
            }

            holder.tv_scheme_type.setText(yojnaType);


//        }
//        else {
//            holder = (ManregaInspectionListAdaptor.ViewHolder) convertView.getTag();
//        }
        return convertView;
    }

    private class ViewHolder {
        TextView tv_village,tv_yojna_code,tv_kriwayan_vibhag,tv_abya_name,tv_prashasnik_date,tv_scheme_type;
        //Button btn_remove,btn_edit,btn_upload;

    }

}
