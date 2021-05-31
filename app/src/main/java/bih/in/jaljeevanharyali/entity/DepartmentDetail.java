package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

public class DepartmentDetail implements KvmSerializable {

    public static Class<DepartmentDetail> DepartmentDetail_CLASS=DepartmentDetail.class;

    private String id;
    private String departmentName;
    private String departmentNameHnd;

    public DepartmentDetail(SoapObject sobj) {
        this.id=sobj.getProperty("_id").toString();
        this.departmentName=sobj.getProperty("_DepatmentName").toString();
        this.departmentNameHnd=sobj.getProperty("_DepatmentHNd").toString();

    }

    public static Class<DepartmentDetail> getDepartmentDetail_CLASS() {
        return DepartmentDetail_CLASS;
    }

    public static void setDepartmentDetail_CLASS(Class<DepartmentDetail> departmentDetail_CLASS) {
        DepartmentDetail_CLASS = departmentDetail_CLASS;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getDepartmentNameHnd() {
        return departmentNameHnd;
    }

    public void setDepartmentNameHnd(String departmentNameHnd) {
        this.departmentNameHnd = departmentNameHnd;
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
