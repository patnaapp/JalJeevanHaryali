package bih.in.jaljeevanharyali.entity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class  MonthReportAdapter extends ArrayAdapter<ReportMonth> {
		Context context;
		int layoutResourceId;
		String fileName;
		// For Image loading via cache memory
		//public ImageLoader imageLoader;

		String[] monthNameList={" ","January","February","March","April","May","June","July","August"
			,"September","October","November","December"};

		ArrayList<ReportMonth> data = new ArrayList<ReportMonth>();

		public MonthReportAdapter(Context context, int layoutResourceId, ArrayList<ReportMonth> data) {
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

				//holder.monthName = (TextView) row.findViewById(R.id.monthName);
				
				//holder.txtDist = (TextView) row.findViewById(R.id.draft_district);
				
				
				row.setTag(holder);
			} else {
				holder = (ImageHolder) row.getTag();
			}

			ReportMonth reportMonth = data.get(position);
			int m= Integer.parseInt(reportMonth.getMonth());
			holder.monthName.setText(monthNameList[m]);
			

			
			
		


			
			

			holder = (ImageHolder) row.getTag();
			return row;
		}

		static class ImageHolder {
			TextView monthName;
		}
		
		
		

	}




