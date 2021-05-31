package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

import bih.in.jaljeevanharyali.utility.AppConstant;

public class NurseryReportEntity implements KvmSerializable, Serializable {
    public static Class<NurseryEntity> NurseryEntity_CLASS = NurseryEntity.class;
    private String sl_no;
    private String Id;
    private String DistCode;
    private String DistName;
    private String BlockCode;
    private String BlockName;
    private String PanchayatCode;
    private String PanchayatName;
    private String VILLNAME;
    private String VILLCODE;
    private String WARDCODE;
    private String WARDNAME;
    private String Urban_AreaID;
    private String NurseryName;
    private String NurseryID;
    private String TotalArea;
    private String TotalTree;
    private String ContactMobile;
    private String Latitude;
    private String Longitude;
    private String IsInspectedBy;
    private String BuildingName;
    private String BuildingType;
    private String Execution_DeptID;
    private String OtherDept;
    private String ConsumerNo;
    private String BCode;
    private String PreAmount;
    private String IsInspected;
    private String InspectedDate;


    public NurseryReportEntity(SoapObject res1, String type) {

        if(type.equals(AppConstant.NURSURY)){
            this.DistCode=res1.getProperty("DistCode").toString();
            this.DistName=res1.getProperty("DistName").toString();
            this.BlockCode=res1.getProperty("BlockCode").toString();
            this.BlockName=res1.getProperty("BlockName").toString();
            this.PanchayatCode=res1.getProperty("PanchayatCode").toString();
            this.PanchayatName=res1.getProperty("PanchayatName").toString();
            this.VILLNAME=res1.getProperty("VILLNAME").toString();
            this.VILLCODE=res1.getProperty("VILLCODE").toString();
            //this.WARDCODE=res1.getProperty("WARDCODE").toString();
           // this.WARDNAME=res1.getProperty("WARDNAME").toString();
            //this.Urban_AreaID=res1.getProperty("Urban_AreaID").toString();
            //this.Area_Name = evaluatAreaName();
            this.NurseryName=res1.getProperty("NurseryName").toString();
            this.Id=res1.getProperty("NurseryID").toString();
            //this.TotalArea=res1.getProperty("TotalArea").toString();
            //this.TotalTree=res1.getProperty("TotalTree").toString();
            this.ContactMobile=res1.getProperty("ContactMobile").toString();
            this.Latitude=res1.getProperty("Latitude").toString();
            this.Longitude=res1.getProperty("Longitude").toString();
            this.IsInspectedBy=res1.getProperty("IsInspectedBy").toString();
            this.InspectedDate=res1.getProperty("InspectedDate").toString();


        }else if(type.equals(AppConstant.BUILDING)){
            this.DistCode=res1.getProperty("DistrictCode").toString();
            this.DistName=res1.getProperty("DistName").toString();
            this.BlockCode=res1.getProperty("BlockCode").toString();
            this.BlockName=res1.getProperty("BlockName").toString();
            this.PanchayatCode=res1.getProperty("PanchayatCode").toString();
            this.PanchayatName=res1.getProperty("PanchayatName").toString();
            this.WARDCODE=res1.getProperty("WARDCODE").toString();
            this.WARDNAME=res1.getProperty("WARDNAME").toString();
            this.BuildingType=res1.getProperty("BuildingType").toString();
            this.BuildingName=res1.getProperty("BuildingName").toString();
            this.BuildingName=res1.getProperty("BuildingName").toString();
            //this.Area_Name = evaluatAreaName();
            this.Urban_AreaID=res1.getProperty("Urban_AreaID").toString();
            this.Execution_DeptID=res1.getProperty("Execution_DeptID").toString();
            this.OtherDept=res1.getProperty("OtherDept").toString();
            this.TotalArea=res1.getProperty("TotalArea").toString();
            this.ConsumerNo=res1.getProperty("ConsumerNo").toString();
            this.Id=res1.getProperty("BID").toString();
            this.PreAmount=res1.getProperty("PreAmount").toString();
            this.Latitude=res1.getProperty("Latitude").toString();
            this.Longitude=res1.getProperty("Longitude").toString();
            this.IsInspected=res1.getProperty("IsInspected").toString();
            this.IsInspectedBy=res1.getProperty("IsInspectedBy").toString();
            this.InspectedDate=res1.getProperty("InspectedDate").toString();
        }
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

    public String getSl_no() {
        return sl_no;
    }

    public void setSl_no(String sl_no) {
        this.sl_no = sl_no;
    }

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getPanchayatCode() {
        return PanchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        PanchayatCode = panchayatCode;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public String getVILLNAME() {
        return VILLNAME;
    }

    public void setVILLNAME(String VILLNAME) {
        this.VILLNAME = VILLNAME;
    }

    public String getVILLCODE() {
        return VILLCODE;
    }

    public void setVILLCODE(String VILLCODE) {
        this.VILLCODE = VILLCODE;
    }

    public String getWARDCODE() {
        return WARDCODE;
    }

    public void setWARDCODE(String WARDCODE) {
        this.WARDCODE = WARDCODE;
    }

    public String getWARDNAME() {
        return WARDNAME;
    }

    public void setWARDNAME(String WARDNAME) {
        this.WARDNAME = WARDNAME;
    }

    public String getUrban_AreaID() {
        return Urban_AreaID;
    }

    public void setUrban_AreaID(String urban_AreaID) {
        Urban_AreaID = urban_AreaID;
    }

    public String getNurseryName() {
        return NurseryName;
    }

    public void setNurseryName(String nurseryName) {
        NurseryName = nurseryName;
    }

    public String getNurseryID() {
        return NurseryID;
    }

    public void setNurseryID(String nurseryID) {
        NurseryID = nurseryID;
    }

    public String getTotalArea() {
        return TotalArea;
    }

    public void setTotalArea(String totalArea) {
        TotalArea = totalArea;
    }

    public String getTotalTree() {
        return TotalTree;
    }

    public void setTotalTree(String totalTree) {
        TotalTree = totalTree;
    }

    public String getContactMobile() {
        return ContactMobile;
    }

    public void setContactMobile(String contactMobile) {
        ContactMobile = contactMobile;
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

    public String getIsInspectedBy() {
        return IsInspectedBy;
    }

    public void setIsInspectedBy(String isInspectedBy) {
        IsInspectedBy = isInspectedBy;
    }

    public String getBuildingName() {
        return BuildingName;
    }

    public void setBuildingName(String buildingName) {
        BuildingName = buildingName;
    }

    public String getBuildingType() {
        return BuildingType;
    }

    public void setBuildingType(String buildingType) {
        BuildingType = buildingType;
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

    public String getIsInspected() {
        return IsInspected;
    }

    public void setIsInspected(String isInspected) {
        IsInspected = isInspected;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getInspectedDate() {
        return InspectedDate;
    }

    public void setInspectedDate(String inspectedDate) {
        InspectedDate = inspectedDate;
    }
}
