package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;


public class FieldInformation implements KvmSerializable, Serializable {

	private String Field="";
	private String Label="";
	private String isActive="";
	private String Type="";
	private String Sequence="";
	private String slNo="";
	public static Class<FieldInformation> FieldInformation_CLASS= FieldInformation.class;
	
	public FieldInformation(SoapObject sobj)
	{
	
		this.Field=sobj.getProperty(0).toString();
		this.Label=sobj.getProperty(1).toString();
		this.isActive=sobj.getProperty(2).toString();
		this.Type=sobj.getProperty(3).toString();
		this.Sequence= String.valueOf(sobj.getProperty(4).toString());
		
				
	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub
		
	}
	
	
	/*public String getslNo()
	{
		return slNo;
	}*/
	
	public String getField()
	{
		return Field;
	}
	
	public String getLabel()
	{
		return Label;
	}
	
	public String getisActive()
	{
		return isActive;
	}
	
	public String getType()
	{
		return Type;
	}
	
	public String getSequence()
	{
		return Sequence;
	}
	
	
}
