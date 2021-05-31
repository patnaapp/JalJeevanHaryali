package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.SoapObject;

public class DistrictData {

	

	private String code;
	private String value;

	
	public static Class<DistrictData> DistrictData_CLASS= DistrictData.class;
	
	public DistrictData(SoapObject sobj)
	{
		
		this.code=sobj.getProperty(0).toString();
		this.value=sobj.getProperty(1).toString();



	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
