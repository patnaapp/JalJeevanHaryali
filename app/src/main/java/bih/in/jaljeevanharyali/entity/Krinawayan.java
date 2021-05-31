package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class Krinawayan implements KvmSerializable {

    String Krinawayan_Code="";
    String Krinawayan_name="";

    public Krinawayan() {

    }
    public static Class<Krinawayan> kriwayan_CLASS=Krinawayan.class;
    public Krinawayan(SoapObject sobj)
    {

        this.Krinawayan_Code=sobj.getProperty("Krinawayan_Code").toString();
        this.Krinawayan_name=sobj.getProperty("Krinawayan_name").toString();
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

    public String getKrinawayan_Code() {
        return Krinawayan_Code;
    }

    public void setKrinawayan_Code(String krinawayan_Code) {
        Krinawayan_Code = krinawayan_Code;
    }

    public String getKrinawayan_name() {
        return Krinawayan_name;
    }

    public void setKrinawayan_name(String krinawayan_name) {
        Krinawayan_name = krinawayan_name;
    }
}
