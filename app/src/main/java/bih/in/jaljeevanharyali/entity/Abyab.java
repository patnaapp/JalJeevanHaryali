package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Abyab implements KvmSerializable {

    String Abyab_Code="";
    String Abyab_name="";
    String Krinawayan_Code="";

    public Abyab() {

    }
    public static Class<Krinawayan> kriwayan_CLASS=Krinawayan.class;
    public Abyab(SoapObject sobj)
    {

        this.Abyab_Code=sobj.getProperty("AwayabId").toString();
        this.Abyab_name=sobj.getProperty("Sub_Execution_DeptName").toString();
    }

    @Override
    public Object getProperty(int i) {
        return null;
    }

    @Override
    public int getPropertyCount() {
        return 0;
    }

    @Override
    public void setProperty(int i, Object o) {

    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {

    }

    public String getAbyab_Code() {
        return Abyab_Code;
    }

    public void setAbyab_Code(String abyab_Code) {
        Abyab_Code = abyab_Code;
    }

    public String getAbyab_name() {
        return Abyab_name;
    }

    public void setAbyab_name(String abyab_name) {
        Abyab_name = abyab_name;
    }

    public String getKrinawayan_Code() {
        return Krinawayan_Code;
    }

    public void setKrinawayan_Code(String krinawayan_Code) {
        Krinawayan_Code = krinawayan_Code;
    }
}
