package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

import bih.in.jaljeevanharyali.utility.AppConstant;

public class StructureRemarkEntity implements KvmSerializable,Serializable {

    public static Class<StructureRemarkEntity> StructureRemarkEntity_CLASS = StructureRemarkEntity.class;


    private int id;

    private String InspectionID;

    private String Dist_Code;
    private String Dist_Name;
    private String Block_Code;
    private String Block_Name;
    private String Panchayat_Code;
    private String Panchayat_Name;
    private String Village_Code;
    private String Village_Name;
    private String Ward_Code;
    private String Ward_Name;

    private String Photo;
    private String Latitude;
    private String Longitude;

    private String Urban_AreaID;
    private String RajswaThanaNumber;
    private String Khaata_Kheshara_Number;
    private String StrID;
    private String TypesOfSarchna;
    private String Remark;
    private String DeptId;
    private String DeptName;

    private String AppVersion;
    private String EntryDate;
    private String EntryBy;
    private String IsUpdate;
    private String Mobile;
    private String StrType;
    private String IsStrAvailable;
    private String IsWorking;
    private String newRemark;


    byte[] img;

    public StructureRemarkEntity(SoapObject res1) {
        this.InspectionID=res1.getProperty("InspectionID").toString();

        this.Dist_Code=res1.getProperty("DistID").toString().trim();
        this.Dist_Name=res1.getProperty("DistName").toString().trim();
        this.Block_Code=res1.getProperty("BlockID").toString().trim();
        this.Block_Name=res1.getProperty("BlockName").toString().trim();
        this.Panchayat_Code=res1.getProperty("PanchayatID").toString().trim();
        this.Panchayat_Name=res1.getProperty("PanchayatName").toString().trim();
        this.Village_Name=res1.getProperty("VILLNAME").toString().trim();
        this.Village_Code=res1.getProperty("VillageID").toString().trim();
//        this.Ward_Code=res1.getProperty("WARDCODE").toString();
//        this.Ward_Name=res1.getProperty("WARDNAME").toString();
        this.RajswaThanaNumber=res1.getProperty("RajswaThanaNumber").toString().trim();
        this.Khaata_Kheshara_Number=res1.getProperty("Khaata_Kheshara_Number").toString().trim();

        this.Latitude=res1.getProperty("Latitude").toString();
        this.Longitude=res1.getProperty("Longitude").toString();
        this.StrID=res1.getProperty("StrID").toString();
        this.TypesOfSarchna=res1.getProperty("TypesOfSarchna").toString();
        this.Remark=res1.getProperty("Remarks").toString();
    }

//    public String evaluatAreaName(){
//        try{
//            if(Area_Code.equals("R")){
//                return "Rural";
//            }else if(Area_Code.equals("U")){
//                return "Urban";
//            }else{
//                return Area_Code;
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return "NA";
//        }
//    }

    public StructureRemarkEntity() {
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

    public String getIsStrAvailable() {
        return IsStrAvailable;
    }

    public void setIsStrAvailable(String isStrAvailable) {
        IsStrAvailable = isStrAvailable;
    }

    public String getIsWorking() {
        return IsWorking;
    }

    public void setIsWorking(String isWorking) {
        IsWorking = isWorking;
    }

    public String getNewRemark() {
        return newRemark;
    }

    public void setNewRemark(String newRemark) {
        this.newRemark = newRemark;
    }

    public String getStrType() {
        return StrType;
    }

    public void setStrType(String strType) {
        StrType = strType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInspectionID() {
        return InspectionID;
    }

    public void setInspectionID(String inspectionID) {
        InspectionID = inspectionID;
    }

    public String getDist_Code() {
        return Dist_Code;
    }

    public void setDist_Code(String dist_Code) {
        Dist_Code = dist_Code;
    }

    public String getDist_Name() {
        return Dist_Name;
    }

    public void setDist_Name(String dist_Name) {
        Dist_Name = dist_Name;
    }

    public String getBlock_Code() {
        return Block_Code;
    }

    public void setBlock_Code(String block_Code) {
        Block_Code = block_Code;
    }

    public String getBlock_Name() {
        return Block_Name;
    }

    public void setBlock_Name(String block_Name) {
        Block_Name = block_Name;
    }

    public String getPanchayat_Code() {
        return Panchayat_Code;
    }

    public void setPanchayat_Code(String panchayat_Code) {
        Panchayat_Code = panchayat_Code;
    }

    public String getPanchayat_Name() {
        return Panchayat_Name;
    }

    public void setPanchayat_Name(String panchayat_Name) {
        Panchayat_Name = panchayat_Name;
    }

    public String getVillage_Code() {
        return Village_Code;
    }

    public void setVillage_Code(String village_Code) {
        Village_Code = village_Code;
    }

    public String getVillage_Name() {
        return Village_Name;
    }

    public void setVillage_Name(String village_Name) {
        Village_Name = village_Name;
    }

    public String getWard_Code() {
        return Ward_Code;
    }

    public void setWard_Code(String ward_Code) {
        Ward_Code = ward_Code;
    }

    public String getWard_Name() {
        return Ward_Name;
    }

    public void setWard_Name(String ward_Name) {
        Ward_Name = ward_Name;
    }

    public String getPhoto() {
        return Photo;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getLatitude() {
        return Latitude;
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

    public String getUrban_AreaID() {
        return Urban_AreaID;
    }

    public void setUrban_AreaID(String urban_AreaID) {
        Urban_AreaID = urban_AreaID;
    }

    public String getRajswaThanaNumber() {
        return RajswaThanaNumber;
    }

    public void setRajswaThanaNumber(String rajswaThanaNumber) {
        RajswaThanaNumber = rajswaThanaNumber;
    }

    public String getKhaata_Kheshara_Number() {
        return Khaata_Kheshara_Number;
    }

    public void setKhaata_Kheshara_Number(String khaata_Kheshara_Number) {
        Khaata_Kheshara_Number = khaata_Kheshara_Number;
    }

    public String getStrID() {
        return StrID;
    }

    public void setStrID(String strID) {
        StrID = strID;
    }

    public String getTypesOfSarchna() {
        return TypesOfSarchna;
    }

    public void setTypesOfSarchna(String typesOfSarchna) {
        TypesOfSarchna = typesOfSarchna;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String deptId) {
        DeptId = deptId;
    }

    public String getDeptName() {
        return DeptName;
    }

    public void setDeptName(String deptName) {
        DeptName = deptName;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getEntryBy() {
        return EntryBy;
    }

    public void setEntryBy(String entryBy) {
        EntryBy = entryBy;
    }

    public String getIsUpdate() {
        return IsUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        IsUpdate = isUpdate;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }
}
