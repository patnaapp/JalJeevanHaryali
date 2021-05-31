package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.SoapObject;

public class FyearWeb {

	private String year;
	private String isCurrent;
	private String fyearId;
	
	public static Class<FyearWeb> FyearWeb_CLASS= FyearWeb.class;
	
	public FyearWeb(SoapObject sobj)
	{
		
		this.year=sobj.getProperty(1).toString();
		this.isCurrent=sobj.getProperty(2).toString();
		this.fyearId=sobj.getProperty(0).toString();
		
	}

	public String getFyearId() {
		return fyearId;
	}

	public void setFyearId(String fyearId) {
		this.fyearId = fyearId;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getIsCurrent() {
		return isCurrent;
	}

	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}
	
	
}
