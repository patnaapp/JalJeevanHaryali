package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class TreesDetail implements KvmSerializable, Serializable {

    private static Class<TreesDetail> TreesDetail_CLASS = TreesDetail.class;

    private String TreeId = "";
    private String TreeEng = "";
    private String TreeHnd = "";

    public TreesDetail() {
    }

    public TreesDetail(SoapObject obj) {
        this.setTreeId(obj.getProperty("_Types_OfTreeId").toString());
        this.setTreeEng(obj.getProperty("_Types_OfTreeNameEngg").toString());
        this.setTreeHnd(obj.getProperty("_Types_OfTreeName").toString());
    }

    public static Class<TreesDetail> getTreesDetail_CLASS() {
        return TreesDetail_CLASS;
    }

    public static void setTreesDetail_CLASS(Class<TreesDetail> treesDetail_CLASS) {
        TreesDetail_CLASS = treesDetail_CLASS;
    }

    public String getTreeId() {
        return TreeId;
    }

    public void setTreeId(String treeId) {
        TreeId = treeId;
    }

    public String getTreeEng() {
        return TreeEng;
    }

    public void setTreeEng(String treeEng) {
        TreeEng = treeEng;
    }

    public String getTreeHnd() {
        return TreeHnd;
    }

    public void setTreeHnd(String treeHnd) {
        TreeHnd = treeHnd;
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
