package bih.in.jaljeevanharyali.entity;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.StringTokenizer;

import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class NoReportListAdapter extends ArrayAdapter<ReportClass> {

		Context context;
		int layoutResourceId;
		String fileName;
		// For Image loading via cache memory
		//public ImageLoader imageLoader;


		ArrayList<ReportClass> data = new ArrayList<ReportClass>();

		public NoReportListAdapter(Context context, int layoutResourceId,
                                   ArrayList<ReportClass> data) {
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

//				holder.officeName= (TextView) row.findViewById(R.id.officename);
//
//
//
//				holder.date = (TextView) row.findViewById(R.id.dateText);
//
//				holder.oc = (TextView) row.findViewById(R.id.oc);
//				holder.ms = (TextView) row.findViewById(R.id.ms);
//				holder.p = (TextView) row.findViewById(R.id.p);
//				holder.level = (TextView) row.findViewById(R.id.level);
//				holder.projectName=(TextView)row.findViewById(R.id.projectname);
//				holder.projectNamelayout=(LinearLayout)row.findViewById(R.id.projectNamelayout);

				row.setTag(holder);
			} else {
				holder = (ImageHolder) row.getTag();
			}

			ReportClass report = data.get(position);

			if(GlobalVariables.rtype==2) holder.projectNamelayout.setVisibility(View.GONE);

			holder.officeName.setText(report.getPanchayatName());
			holder.projectName.setText(report.getBlockName());

			StringTokenizer st=new StringTokenizer(report.getEntryDate()," ");
			String edate="";
			if(st.hasMoreTokens()) edate=st.nextToken();
			edate= Utiilties.parseDate(edate);

			holder.date.setText(edate);

              String oc="";
			if(report.getF2().equals("1")) oc=context.getString(R.string.open);
			else oc=context.getString(R.string.close);

			holder.oc.setText(oc);
			holder.ms.setText(getYN(report.getF3()));
			holder.p.setText(report.getF8());

			String level="";


			String[] levelList={"बहुत अच्छा ","अच्छा ","संतोषप्रद ","असंतोषप्रद "};

			if(report.getF4().equals("1")) level=levelList[0];
			else if(report.getF4().equals("2")) level=levelList[1];
			else if(report.getF4().equals("3")) level=levelList[2];
			else if(report.getF4().equals("4")) level=levelList[3];

			holder.level.setText(level);

			holder = (ImageHolder) row.getTag();
			return row;
		}
		
		
	public String getYN(String code)
	{
		if(code.equals("1")) return context.getString(R.string.yes);
		else return context.getString(R.string.no);
	}

		static class ImageHolder {
			TextView officeName,date,oc,ms,p,level,projectName;
			LinearLayout projectNamelayout;
		}

	}




