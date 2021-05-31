package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

import bih.in.jaljeevanharyali.utility.AppConstant;

public class PublicNurseryBuildingEntity implements KvmSerializable,Serializable {

    public static Class<PublicNurseryBuildingEntity> PublicNurseryBuildingEntity_CLASS = PublicNurseryBuildingEntity.class;
    private String sl_no;
    private int id;

    private String InspectionID="";

    private String Dist_Code="";
    private String Dist_Name;
    private String Block_Code="";
    private String Block_Name;
    private String Panchayat_Code="";
    private String Panchayat_Name;
    private String Village_Code="";
    private String Village_Name;
    private String Ward_Code;
    private String Ward_Name;
    private String Area_Code;
    private String Area_Name;

    private String Nursury_Name;
    private String NursuryId;
    private String Tree;
    private String Area;
    private String Mobile;

    private String BuildingType;
    private String BuildingName;
    private String Execution_DeptID;
    private String Execution_DeptName;
    private String OtherDept;
    private String ConsumerNo;
    private String BCode;
    private String PreAmount;

    private String Photo;
    private String Latitude;
    private String Longitude;
    private String Entry_By;
    private String Image;
    private String AppVersion;
    private String StrType;
    private String EntryDate;
    private String IsUpdate;
    private String Remark;
    private String DeptId;
    private String Latitude_mst;
    private String Longitude_mst;
    private String Contact_Mobile;


    byte[] img;

    public PublicNurseryBuildingEntity(SoapObject res1, String type) {

        if(type.equals(AppConstant.NURSURY)){
           // this.Dist_Code=res1.getProperty("DistrictCode").toString();
            this.Dist_Name=res1.getProperty("DistName").toString();
            //this.Block_Code=res1.getProperty("BlockCode").toString();
            this.Block_Name=res1.getProperty("BlockName").toString();
            //this.Panchayat_Code=res1.getProperty("PanchayatCode").toString();
            this.Panchayat_Name=res1.getProperty("PanchayatName").toString();
            this.Village_Name=res1.getProperty("VILLNAME").toString();
//            this.Village_Code=res1.getProperty("VILLCODE").toString();
//            this.Ward_Code=res1.getProperty("WARDCODE").toString();
//            this.Ward_Name=res1.getProperty("WARDNAME").toString();
//            this.Area_Code=res1.getProperty("Urban_AreaID").toString();
//            this.Area_Name = evaluatAreaName();
            this.Nursury_Name=res1.getProperty("NurseryName").toString();
            //this.InspectionID=res1.getProperty("NurseryID").toString();
            this.Area=res1.getProperty("TotalArea").toString();
            this.Tree=res1.getProperty("TotalTree").toString();
            this.Contact_Mobile=res1.getProperty("ContactMobile").toString();
            this.Latitude_mst=res1.getProperty("Latitude").toString();
            this.Longitude_mst=res1.getProperty("Longitude").toString();

        }else if(type.equals(AppConstant.BUILDING)){
            //this.Dist_Code=res1.getProperty("DistrictCode").toString();
            this.Dist_Name=res1.getProperty("DistName").toString();
            //this.Block_Code=res1.getProperty("BlockCode").toString();
            this.Block_Name=res1.getProperty("BlockName").toString();
            //this.Panchayat_Code=res1.getProperty("PanchayatCode").toString();
            this.Panchayat_Name=res1.getProperty("PanchayatName").toString();
//            this.Ward_Code=res1.getProperty("WARDCODE").toString();
//            this.Ward_Name=res1.getProperty("WARDNAME").toString();
//            this.BuildingType=res1.getProperty("BuildingType").toString();
            this.BuildingName=res1.getProperty("BuildingName").toString();
//            this.Area_Code=res1.getProperty("Urban_AreaID").toString();
//            this.Area_Name = evaluatAreaName();
//            this.Execution_DeptID=res1.getProperty("Execution_DeptID").toString();
//            this.OtherDept=res1.getProperty("OtherDept").toString();
            this.Area=res1.getProperty("TotalArea").toString();
//            this.ConsumerNo=res1.getProperty("ConsumerNo").toString();
//            this.BCode=res1.getProperty("BCode").toString();
//            this.PreAmount=res1.getProperty("PreAmount").toString();
//            this.InspectionID=res1.getProperty("BID").toString();
            this.Latitude_mst=res1.getProperty("Latitude").toString();
            this.Longitude_mst=res1.getProperty("Longitude").toString();
        }
    }

    public String evaluatAreaName(){
        try{
            if(Area_Code.equals("R")){
                return "Rural";
            }else if(Area_Code.equals("U")){
                return "Urban";
            }else{
                return Area_Code;
            }
        }catch (Exception e){
            e.printStackTrace();
            return "NA";
        }

    }

    public PublicNurseryBuildingEntity() {
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

    public String getDeptId() {
        return DeptId;
    }

    public void setDeptId(String deptId) {
        DeptId = deptId;
    }

    public String getExecution_DeptName() {
        return Execution_DeptName;
    }

    public void setExecution_DeptName(String execution_DeptName) {
        Execution_DeptName = execution_DeptName;
    }

    public String getPhoto() {
        return Photo;
    }

    public byte[] getImg() {
        return img;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public void setPhoto(String photo) {
        Photo = photo;
    }

    public String getAppVersion() {
        return AppVersion;
    }

    public void setAppVersion(String appVersion) {
        AppVersion = appVersion;
    }

    public String getStrType() {
        return StrType;
    }

    public void setStrType(String strType) {
        StrType = strType;
    }

    public String getEntryDate() {
        return EntryDate;
    }

    public void setEntryDate(String entryDate) {
        EntryDate = entryDate;
    }

    public String getIsUpdate() {
        return IsUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        IsUpdate = isUpdate;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getInspectionID() {
        return InspectionID;
    }

    public void setInspectionID(String inspectionID) {
        InspectionID = inspectionID;
    }

    public String getBuildingType() {
        return BuildingType;
    }

    public void setBuildingType(String buildingType) {
        BuildingType = buildingType;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getExecution_DeptID() {
        return Execution_DeptID;
    }

    public void setExecution_DeptID(String execution_DeptID) {
        Execution_DeptID = execution_DeptID;
    }

    public String getOtherDept() {
        return OtherDept;
    }

    public void setOtherDept(String otherDept) {
        OtherDept = otherDept;
    }

    public String getConsumerNo() {
        return ConsumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        ConsumerNo = consumerNo;
    }

    public String getBCode() {
        return BCode;
    }

    public void setBCode(String BCode) {
        this.BCode = BCode;
    }

    public String getPreAmount() {
        return PreAmount;
    }

    public void setPreAmount(String preAmount) {
        PreAmount = preAmount;
    }

    public String getNursury_Name() {
        return Nursury_Name;
    }

    public void setNursury_Name(String nursury_Name) {
        Nursury_Name = nursury_Name;
    }

    public String getNursuryId() {
        return NursuryId;
    }

    public void setNursuryId(String nursuryId) {
        NursuryId = nursuryId;
    }

    public String getSl_no() {
        return sl_no;
    }

    public void setSl_no(String sl_no) {
        this.sl_no = sl_no;
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

    public String getArea_Code() {
        return Area_Code;
    }

    public void setArea_Code(String area_Code) {
        Area_Code = area_Code;
    }

    public String getArea_Name() {
        return Area_Name;
    }

    public void setArea_Name(String area_Name) {
        Area_Name = area_Name;
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


    public String getArea() {
        return Area;
    }

    public void setArea(String area) {
        Area = area;
    }

    public String getTree() {
        return Tree;
    }

    public void setTree(String tree) {
        Tree = tree;
    }

    public String getMobile() {
        return Mobile;
    }

    public void setMobile(String mobile) {
        Mobile = mobile;
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

    public String getEntry_By() {
        return Entry_By;
    }

    public void setEntry_By(String entry_By) {
        Entry_By = entry_By;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude_mst() {
        return Latitude_mst;
    }

    public void setLatitude_mst(String latitude_mst) {
        Latitude_mst = latitude_mst;
    }

    public String getLongitude_mst() {
        return Longitude_mst;
    }

    public void setLongitude_mst(String longitude_mst) {
        Longitude_mst = longitude_mst;
    }

    public String getContact_Mobile() {
        return Contact_Mobile;
    }

    public void setContact_Mobile(String contact_Mobile) {
        Contact_Mobile = contact_Mobile;
    }
}
