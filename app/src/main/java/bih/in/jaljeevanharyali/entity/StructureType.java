package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class StructureType implements KvmSerializable, Serializable {

    public static Class<StructureType> StructureType_CLASS = StructureType.class;

    private String StrID;
    private String StrName;
    private String StrCode;

    public StructureType(SoapObject res1) {

        this.StrID=res1.getProperty("StrID").toString();
        this.StrName=res1.getProperty("StructureTypeNameHn").toString();
        this.StrCode=res1.getProperty("StructureCode").toString();
    }

    public StructureType() {

    }

    public String getStrID() {
        return StrID;
    }

    public void setStrID(String strID) {
        StrID = strID;
    }

    public String getStrName() {
        return StrName;
    }

    public void setStrName(String strName) {
        StrName = strName;
    }

    public String getStrCode() {
        return StrCode;
    }

    public void setStrCode(String strCode) {
        StrCode = strCode;
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
}
