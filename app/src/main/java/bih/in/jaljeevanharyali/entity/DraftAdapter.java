package bih.in.jaljeevanharyali.entity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class DraftAdapter extends ArrayAdapter<DataProgress> {
	Context context;
	int layoutResourceId;
	String fileName;
	// For Image loading via cache memory
	//public ImageLoader imageLoader;


	ArrayList<DataProgress> data = new ArrayList<DataProgress>();

	public DraftAdapter(Context context, int layoutResourceId,
                        ArrayList<DataProgress> data) {
		super(context, layoutResourceId, data);
		this.layoutResourceId = layoutResourceId;
		this.context = context;
		this.data = data;

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		ImageHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(layoutResourceId, parent, false);
			holder = new ImageHolder();

//			holder.txtId = (TextView) row.findViewById(R.id.draft_id_no);
//			holder.txtDate = (TextView) row.findViewById(R.id.draft_Date);
//			holder.txtName = (TextView) row.findViewById(R.id.draft_Name);
//			holder.txtRemarks = (TextView) row.findViewById(R.id.draft_Remarks);
			//holder.txtDist = (TextView) row.findViewById(R.id.draft_district);
			
			
			row.setTag(holder);
		} else {
			holder = (ImageHolder) row.getTag();
		}

		DataProgress dataprogress = data.get(position);
		holder.txtId.setText(dataprogress.getSlno());
		
		holder.txtDate.setText(dataprogress.getEntry_Date());
		holder.txtName.setText(dataprogress.getPanchayatName());
		holder.txtRemarks.setText(dataprogress.getRemark_Data());
		
		
	


		
		

		holder = (ImageHolder) row.getTag();
		return row;
	}

	static class ImageHolder {
		TextView txtId,txtDate,txtName,txtRemarks;
	}

}


