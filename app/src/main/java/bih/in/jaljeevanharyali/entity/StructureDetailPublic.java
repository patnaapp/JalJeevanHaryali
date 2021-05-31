package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class StructureDetailPublic implements KvmSerializable, Serializable {

    public static Class<StructureDetailPublic> StructureDetailPublic_CLASS = StructureDetailPublic.class;

    private String DistName;
    private String PanchayatName;
    private String BlockName;
    private String VILLNAME;
    private String VILLCODE;
    private String Latitude;
    private String Longitude;
    private String Commercial_Public;
    private String inspId;
    private String strid;
    private String strName;
    private String DistCode;
    private String BlockCode;
    private String PanchayatCode;
    private String Functional;

    public StructureDetailPublic(SoapObject res1) {

        this.DistName=res1.getProperty("DistName").toString();
        this.PanchayatName=res1.getProperty("PanchayatName").toString();
        this.BlockName=res1.getProperty("BlockName").toString();
        this.VILLNAME=res1.getProperty("VILLNAME").toString();
        this.VILLCODE=res1.getProperty("VILLCODE").toString();
        this.Latitude=res1.getProperty("Latitude").toString();
        this.Longitude=res1.getProperty("Longitude").toString();
        this.Commercial_Public=res1.getProperty("Commercial_Public").toString();
        this.inspId=res1.getProperty("inspId").toString();
        this.strid=res1.getProperty("strid").toString();
        this.DistCode=res1.getProperty("DistrictCode").toString();
        this.BlockCode=res1.getProperty("BlockCode").toString();
        this.PanchayatCode=res1.getProperty("PanchayatCode").toString();
        this.strName=res1.getProperty("StructureTypeName").toString();
        this.Functional=res1.getProperty("Functional").toString();
    }

    public StructureDetailPublic() {

    }

    public String getFunctional() {
        return Functional;
    }

    public void setFunctional(String functional) {
        Functional = functional;
    }

    public String getVILLCODE() {
        return VILLCODE;
    }

    public void setVILLCODE(String VILLCODE) {
        this.VILLCODE = VILLCODE;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getVILLNAME() {
        return VILLNAME;
    }

    public void setVILLNAME(String VILLNAME) {
        this.VILLNAME = VILLNAME;
    }

    public String getLatitude() {
        return Latitude;
    }

    public String getStrName() {
        return strName;
    }

    public void setStrName(String strName) {
        this.strName = strName;
    }

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }

    public String getPanchayatCode() {
        return PanchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        PanchayatCode = panchayatCode;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getCommercial_Public() {
        return Commercial_Public;
    }

    public void setCommercial_Public(String commercial_Public) {
        Commercial_Public = commercial_Public;
    }

    public String getInspId() {
        return inspId;
    }

    public void setInspId(String inspId) {
        this.inspId = inspId;
    }

    public String getStrid() {
        return strid;
    }

    public void setStrid(String strid) {
        this.strid = strid;
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
