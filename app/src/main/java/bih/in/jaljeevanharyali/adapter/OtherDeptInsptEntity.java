package bih.in.jaljeevanharyali.adapter;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.util.Hashtable;

import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;

public class OtherDeptInsptEntity implements KvmSerializable {

    public static Class<OtherDeptInsptEntity> OtherDeptDetail_CLASS = OtherDeptInsptEntity.class;

    private int id;
    private String Execution_Dept_ID;
    private String Sub_Exect_Dept_ID;
    private String Sub_Sub_Exect_Dept_ID;
    private String Sub_Exect_Value;
    private String District_Name;
    private String District_Id;
    private String Block_ID;
    private String Block_Name;
    private String Panchayat_Id;
    private String Urban_Area_Id;
    private String Urban_AreaName;
    private String Ward_Id;
    private String Ward_Name;
    private String Village_Id;
    private String Village_Name;
    private String Latitude;
    private String Longitude;
    private String Work_Structure_Type;
    private String Work_Structure_Type_Name;
    private String Work_Structure_Type_Other_Name;
    private String Measurement_Of_Structure;
    private String Estimated_Amount;
    private String Approval_Date;
    private String Administrative_Approval_Date;
    private String Scheme_Code;
    private String Remarks;
    private String CreatedBy;
    private String CreatedDate;
    private String App_Version;
    private String PanchayatName;
    private String ExectDeptName;
    private String SubDivName;
    private String Sub_Sub_Exec_DeptName;
    private String Photo1;
    private String Photo2;
    private String SahariNikayeId;
    private String Other_Name;
    private String YojnaType="0";

    private String isUpdated;

    private String Types_OfSarchnaId;
    private String Types_OfSarchnaName;

    private String IsPhase1Inspected;
    private String IsPhase1InspBy;
    private String IsPhase1InspDate;
    private String IsPhase1InspPhoto1;
    private String IsPhase1InspPhoto2;
    private String IsPhase1InspLatitude;
    private String IsPhase1InspLongitude;
    private String IsPhase1InspRemarks;
    private String IsPhase2Inspected;
    private String IsPhase2InspBy;
    private String IsPhase2InspDate;
    private String IsPhase2InspPhoto1;
    private String IsPhase2InspPhoto2;
    private String IsPhase2InspLatitude;
    private String IsPhase2InspLongitude;
    private String IsPhase2InspRemarks;
    private String IsPhase3Inspected;
    private String IsPhase3InspBy;
    private String IsPhase3InspDate;
    private String IsPhase3InspPhoto1;
    private String IsPhase3InspPhoto2;
    private String IsPhase3InspLatitude;
    private String IsPhase3InspLongitude;
    private String IsPhase3InspRemarks;
    private String IsFinalInspected;
    private String AwayabId;

    private String Khaata_Kheshara_Number;
    private String Rakba;
    private String LandOwnerMob;
    private String LandOwnerName;
    private String FencingLatitude1;
    private String FencingLatitude2;
    private String FencingLatitude3;
    private String FencingLatitude4;
    private String FencingLongitude1;
    private String FencingLongitude2;
    private String FencingLongitude3;
    private String FencingLongitude4;
    private String IsLandEnterDt;

    private String ConsumerNo;
    private String ConsumrBill;
    private String TreeType;
    private String TreeTypeName;

    public OtherDeptInsptEntity(SoapObject res1) {


        this.District_Id=res1.getProperty("DistCode").toString();
        this.District_Name=res1.getProperty("DistName").toString();
        this.Block_ID=res1.getProperty("BlockCode").toString();
        this.Block_Name=res1.getProperty("BlockName").toString();
        this.Panchayat_Id=res1.getProperty("PanchayatCode").toString();
        this.PanchayatName=res1.getProperty("PanchayatName").toString();
        this.Village_Id=res1.getProperty("VILLCODE").toString();
        this.Village_Name=res1.getProperty("VILLNAME").toString();
        this.id=Integer.parseInt(res1.getProperty("id").toString());
        this.CreatedBy=res1.getProperty("Verified_By").toString();
        this.Execution_Dept_ID=res1.getProperty("Execution_DeptID").toString();
        this.ExectDeptName=res1.getProperty("Execution_DeptName").toString();
        this.Sub_Sub_Exect_Dept_ID=res1.getProperty("SubSubExectDept_Id").toString();
        this.SahariNikayeId=res1.getProperty("Urban_AreaID").toString();
        this.Estimated_Amount=res1.getProperty("Estimated_Amount").toString();
        this.Measurement_Of_Structure=res1.getProperty("Measurement_Of_Structure").toString();
        this.Scheme_Code=res1.getProperty("Scheme_Code").toString();
        this.Administrative_Approval_Date=res1.getProperty("Administrative_Approval_Date").toString();
        this.CreatedDate=res1.getProperty("CreatedDate").toString();
        this.Types_OfSarchnaId=res1.getProperty("Types_OfSarchnaId").toString();
        this.Types_OfSarchnaName=res1.getProperty("Types_OfSarchnaName").toString();
        this.Sub_Exect_Dept_ID=res1.getProperty("Sub_Execution_DeptID").toString();
        this.SubDivName=res1.getProperty("Sub_Execution_DepartmentName").toString();
        //this.Sub_Sub_Exec_DeptName=res1.getProperty("SubSubExectDept_Name").toString();

        this.IsPhase1Inspected=res1.getProperty("IsPhase1Inspected").toString();
        this.IsPhase1InspBy=res1.getProperty("IsPhase1InspBy").toString();
        this.IsPhase1InspDate=res1.getProperty("IsPhase1InspDate").toString();
        this.IsPhase1InspPhoto1=res1.getProperty("IsPhase1InspPhoto1").toString();
        this.IsPhase1InspPhoto2=res1.getProperty("IsPhase1InspPhoto2").toString();
        this.IsPhase1InspLatitude=res1.getProperty("IsPhase1InspLatitude").toString();
        this.IsPhase1InspLongitude=res1.getProperty("IsPhase1InspLongitude").toString();
        this.IsPhase1InspRemarks=res1.getProperty("IsPhase1InspRemarks").toString();
        this.IsPhase2Inspected=res1.getProperty("IsPhase2Inspected").toString();
        this.IsPhase2InspBy=res1.getProperty("IsPhase2InspBy").toString();
        this.IsPhase2InspDate=res1.getProperty("IsPhase2InspDate").toString();
        this.IsPhase2InspPhoto1=res1.getProperty("IsPhase2InspPhoto1").toString();
        this.IsPhase2InspPhoto2=res1.getProperty("IsPhase2InspPhoto2").toString();
        this.IsPhase2InspLatitude=res1.getProperty("IsPhase2InspLatitude").toString();
        this.IsPhase2InspLongitude=res1.getProperty("IsPhase2InspLongitude").toString();
        this.IsPhase2InspRemarks=res1.getProperty("IsPhase2InspRemarks").toString();
        this.IsPhase3Inspected=res1.getProperty("IsPhase3Inspected").toString();
        this.IsPhase3InspBy=res1.getProperty("IsPhase3InspBy").toString();
        this.IsPhase3InspDate=res1.getProperty("IsPhase3InspDate").toString();
        this.IsPhase3InspPhoto1=res1.getProperty("IsPhase3InspPhoto1").toString();
        this.IsPhase3InspPhoto2=res1.getProperty("IsPhase3InspPhoto2").toString();
        this.IsPhase3InspLatitude=res1.getProperty("IsPhase3InspLatitude").toString();
        this.IsPhase3InspLongitude=res1.getProperty("IsPhase3InspLongitude").toString();
        this.IsPhase3InspRemarks=res1.getProperty("IsPhase3InspRemarks").toString();
        this.IsFinalInspected=res1.getProperty("IsFinalInspected").toString();
        this.YojnaType=res1.getProperty("IsUSType").toString();
        this.AwayabId=res1.getProperty("AwayabId").toString();

        this.Khaata_Kheshara_Number=res1.getProperty("Khaata_Kheshara_Number").toString();
        this.Rakba=res1.getProperty("Rakba").toString();
        this.LandOwnerMob=res1.getProperty("LandOwnerMob").toString();
        this.LandOwnerName=res1.getProperty("LandOwnerName").toString();
        this.FencingLatitude1=res1.getProperty("FencingLatitude1").toString();
        this.FencingLatitude2=res1.getProperty("FencingLatitude2").toString();
        this.FencingLatitude3=res1.getProperty("FencingLatitude3").toString();
        this.FencingLatitude4=res1.getProperty("FencingLatitude4").toString();
        this.FencingLongitude1=res1.getProperty("FencingLongitude1").toString();
        this.FencingLongitude2=res1.getProperty("FencingLongitude2").toString();
        this.FencingLongitude3=res1.getProperty("FencingLongitude3").toString();
        this.FencingLongitude4=res1.getProperty("FencingLongitude4").toString();

        this.IsLandEnterDt=res1.getProperty("IsLandEnterDt").toString();
        this.ConsumerNo=res1.getProperty("ConsumerNo").toString();
        this.ConsumrBill=res1.getProperty("ConsumrBill").toString();
        this.TreeType=res1.getProperty("TreeType").toString();
    }

    public OtherDeptInsptEntity(){

    }

    public static Class<OtherDeptInsptEntity> getOtherDeptDetail_CLASS() {
        return OtherDeptDetail_CLASS;
    }

    public static void setOtherDeptDetail_CLASS(Class<OtherDeptInsptEntity> otherDeptDetail_CLASS) {
        OtherDeptDetail_CLASS = otherDeptDetail_CLASS;
    }

    public String getConsumerNo() {
        return ConsumerNo;
    }

    public void setConsumerNo(String consumerNo) {
        ConsumerNo = consumerNo;
    }

    public String getConsumrBill() {
        return ConsumrBill;
    }

    public void setConsumrBill(String consumrBill) {
        ConsumrBill = consumrBill;
    }

    public String getTreeType() {
        return TreeType;
    }

    public void setTreeType(String treeType) {
        TreeType = treeType;
    }

    public String getTreeTypeName() {
        return TreeTypeName;
    }

    public void setTreeTypeName(String treeTypeName) {
        TreeTypeName = treeTypeName;
    }

    public String getKhaata_Kheshara_Number() {
        return Khaata_Kheshara_Number;
    }

    public void setKhaata_Kheshara_Number(String khaata_Kheshara_Number) {
        Khaata_Kheshara_Number = khaata_Kheshara_Number;
    }

    public String getRakba() {
        return Rakba;
    }

    public void setRakba(String rakba) {
        Rakba = rakba;
    }

    public String getLandOwnerMob() {
        return LandOwnerMob;
    }

    public void setLandOwnerMob(String landOwnerMob) {
        LandOwnerMob = landOwnerMob;
    }

    public String getLandOwnerName() {
        return LandOwnerName;
    }

    public void setLandOwnerName(String landOwnerName) {
        LandOwnerName = landOwnerName;
    }

    public String getFencingLatitude1() {
        return FencingLatitude1;
    }

    public void setFencingLatitude1(String fencingLatitude1) {
        FencingLatitude1 = fencingLatitude1;
    }

    public String getFencingLatitude2() {
        return FencingLatitude2;
    }

    public void setFencingLatitude2(String fencingLatitude2) {
        FencingLatitude2 = fencingLatitude2;
    }

    public String getFencingLatitude3() {
        return FencingLatitude3;
    }

    public void setFencingLatitude3(String fencingLatitude3) {
        FencingLatitude3 = fencingLatitude3;
    }

    public String getFencingLatitude4() {
        return FencingLatitude4;
    }

    public void setFencingLatitude4(String fencingLatitude4) {
        FencingLatitude4 = fencingLatitude4;
    }

    public String getFencingLongitude1() {
        return FencingLongitude1;
    }

    public void setFencingLongitude1(String fencingLongitude1) {
        FencingLongitude1 = fencingLongitude1;
    }

    public String getFencingLongitude2() {
        return FencingLongitude2;
    }

    public void setFencingLongitude2(String fencingLongitude2) {
        FencingLongitude2 = fencingLongitude2;
    }

    public String getFencingLongitude3() {
        return FencingLongitude3;
    }

    public void setFencingLongitude3(String fencingLongitude3) {
        FencingLongitude3 = fencingLongitude3;
    }

    public String getFencingLongitude4() {
        return FencingLongitude4;
    }

    public void setFencingLongitude4(String fencingLongitude4) {
        FencingLongitude4 = fencingLongitude4;
    }

    public String getIsLandEnterDt() {
        return IsLandEnterDt;
    }

    public void setIsLandEnterDt(String isLandEnterDt) {
        IsLandEnterDt = isLandEnterDt;
    }

    public String getAwayabId() {
        return AwayabId;
    }

    public void setAwayabId(String awayabId) {
        AwayabId = awayabId;
    }

    public String getYojnaType() {
        return YojnaType;
    }

    public void setYojnaType(String yojnaType) {
        YojnaType = yojnaType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getExecution_Dept_ID() {
        return Execution_Dept_ID;
    }

    public void setExecution_Dept_ID(String execution_Dept_ID) {
        Execution_Dept_ID = execution_Dept_ID;
    }

    public String getSub_Exect_Dept_ID() {
        return Sub_Exect_Dept_ID;
    }

    public void setSub_Exect_Dept_ID(String sub_Exect_Dept_ID) {
        Sub_Exect_Dept_ID = sub_Exect_Dept_ID;
    }

    public String getSub_Sub_Exect_Dept_ID() {
        return Sub_Sub_Exect_Dept_ID;
    }

    public void setSub_Sub_Exect_Dept_ID(String sub_Sub_Exect_Dept_ID) {
        Sub_Sub_Exect_Dept_ID = sub_Sub_Exect_Dept_ID;
    }

    public String getSub_Exect_Value() {
        return Sub_Exect_Value;
    }

    public void setSub_Exect_Value(String sub_Exect_Value) {
        Sub_Exect_Value = sub_Exect_Value;
    }

    public String getDistrict_Name() {
        return District_Name;
    }

    public void setDistrict_Name(String district_Name) {
        District_Name = district_Name;
    }

    public String getDistrict_Id() {
        return District_Id;
    }

    public void setDistrict_Id(String district_Id) {
        District_Id = district_Id;
    }

    public String getBlock_ID() {
        return Block_ID;
    }

    public void setBlock_ID(String block_ID) {
        Block_ID = block_ID;
    }

    public String getBlock_Name() {
        return Block_Name;
    }

    public void setBlock_Name(String block_Name) {
        Block_Name = block_Name;
    }

    public String getPanchayat_Id() {
        return Panchayat_Id;
    }

    public void setPanchayat_Id(String panchayat_Id) {
        Panchayat_Id = panchayat_Id;
    }

    public String getUrban_Area_Id() {
        return Urban_Area_Id;
    }

    public void setUrban_Area_Id(String urban_Area_Id) {
        Urban_Area_Id = urban_Area_Id;
    }

    public String getUrban_AreaName() {
        return Urban_AreaName;
    }

    public void setUrban_AreaName(String urban_AreaName) {
        Urban_AreaName = urban_AreaName;
    }

    public String getWard_Id() {
        return Ward_Id;
    }

    public void setWard_Id(String ward_Id) {
        Ward_Id = ward_Id;
    }

    public String getWard_Name() {
        return Ward_Name;
    }

    public void setWard_Name(String ward_Name) {
        Ward_Name = ward_Name;
    }

    public String getVillage_Id() {
        return Village_Id;
    }

    public void setVillage_Id(String village_Id) {
        Village_Id = village_Id;
    }

    public String getVillage_Name() {
        return Village_Name;
    }

    public void setVillage_Name(String village_Name) {
        Village_Name = village_Name;
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

    public String getWork_Structure_Type() {
        return Work_Structure_Type;
    }

    public void setWork_Structure_Type(String work_Structure_Type) {
        Work_Structure_Type = work_Structure_Type;
    }

    public String getWork_Structure_Type_Name() {
        return Work_Structure_Type_Name;
    }

    public void setWork_Structure_Type_Name(String work_Structure_Type_Name) {
        Work_Structure_Type_Name = work_Structure_Type_Name;
    }

    public String getWork_Structure_Type_Other_Name() {
        return Work_Structure_Type_Other_Name;
    }

    public void setWork_Structure_Type_Other_Name(String work_Structure_Type_Other_Name) {
        Work_Structure_Type_Other_Name = work_Structure_Type_Other_Name;
    }

    public String getMeasurement_Of_Structure() {
        return Measurement_Of_Structure;
    }

    public void setMeasurement_Of_Structure(String measurement_Of_Structure) {
        Measurement_Of_Structure = measurement_Of_Structure;
    }

    public String getEstimated_Amount() {
        return Estimated_Amount;
    }

    public void setEstimated_Amount(String estimated_Amount) {
        Estimated_Amount = estimated_Amount;
    }

    public String getApproval_Date() {
        return Approval_Date;
    }

    public void setApproval_Date(String approval_Date) {
        Approval_Date = approval_Date;
    }

    public String getAdministrative_Approval_Date() {
        return Administrative_Approval_Date;
    }

    public void setAdministrative_Approval_Date(String administrative_Approval_Date) {
        Administrative_Approval_Date = administrative_Approval_Date;
    }

    public String getScheme_Code() {
        return Scheme_Code;
    }

    public void setScheme_Code(String scheme_Code) {
        Scheme_Code = scheme_Code;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getApp_Version() {
        return App_Version;
    }

    public void setApp_Version(String app_Version) {
        App_Version = app_Version;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public String getExectDeptName() {
        return ExectDeptName;
    }

    public void setExectDeptName(String exectDeptName) {
        ExectDeptName = exectDeptName;
    }

    public String getSubDivName() {
        return SubDivName;
    }

    public void setSubDivName(String subDivName) {
        SubDivName = subDivName;
    }

    public String getSub_Sub_Exec_DeptName() {
        return Sub_Sub_Exec_DeptName;
    }

    public void setSub_Sub_Exec_DeptName(String sub_Sub_Exec_DeptName) {
        Sub_Sub_Exec_DeptName = sub_Sub_Exec_DeptName;
    }

    public String getPhoto1() {
        return Photo1;
    }

    public void setPhoto1(String photo1) {
        Photo1 = photo1;
    }

    public String getPhoto2() {
        return Photo2;
    }

    public void setPhoto2(String photo2) {
        Photo2 = photo2;
    }

    public String getSahariNikayeId() {
        return SahariNikayeId;
    }

    public void setSahariNikayeId(String sahariNikayeId) {
        SahariNikayeId = sahariNikayeId;
    }

    public String getOther_Name() {
        return Other_Name;
    }

    public void setOther_Name(String other_Name) {
        Other_Name = other_Name;
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

    public String getTypes_OfSarchnaId() {
        return Types_OfSarchnaId;
    }

    public void setTypes_OfSarchnaId(String types_OfSarchnaId) {
        Types_OfSarchnaId = types_OfSarchnaId;
    }

    public String getTypes_OfSarchnaName() {
        return Types_OfSarchnaName;
    }

    public void setTypes_OfSarchnaName(String types_OfSarchnaName) {
        Types_OfSarchnaName = types_OfSarchnaName;
    }

    public String getIsPhase1Inspected() {
        return IsPhase1Inspected;
    }

    public void setIsPhase1Inspected(String isPhase1Inspected) {
        IsPhase1Inspected = isPhase1Inspected;
    }

    public String getIsPhase1InspBy() {
        return IsPhase1InspBy;
    }

    public void setIsPhase1InspBy(String isPhase1InspBy) {
        IsPhase1InspBy = isPhase1InspBy;
    }

    public String getIsPhase1InspDate() {
        return IsPhase1InspDate;
    }

    public void setIsPhase1InspDate(String isPhase1InspDate) {
        IsPhase1InspDate = isPhase1InspDate;
    }

    public String getIsPhase1InspPhoto1() {
        return IsPhase1InspPhoto1;
    }

    public void setIsPhase1InspPhoto1(String isPhase1InspPhoto1) {
        IsPhase1InspPhoto1 = isPhase1InspPhoto1;
    }

    public String getIsPhase1InspPhoto2() {
        return IsPhase1InspPhoto2;
    }

    public void setIsPhase1InspPhoto2(String isPhase1InspPhoto2) {
        IsPhase1InspPhoto2 = isPhase1InspPhoto2;
    }

    public String getIsPhase1InspLatitude() {
        return IsPhase1InspLatitude;
    }

    public void setIsPhase1InspLatitude(String isPhase1InspLatitude) {
        IsPhase1InspLatitude = isPhase1InspLatitude;
    }

    public String getIsPhase1InspLongitude() {
        return IsPhase1InspLongitude;
    }

    public void setIsPhase1InspLongitude(String isPhase1InspLongitude) {
        IsPhase1InspLongitude = isPhase1InspLongitude;
    }

    public String getIsPhase1InspRemarks() {
        return IsPhase1InspRemarks;
    }

    public void setIsPhase1InspRemarks(String isPhase1InspRemarks) {
        IsPhase1InspRemarks = isPhase1InspRemarks;
    }

    public String getIsPhase2Inspected() {
        return IsPhase2Inspected;
    }

    public void setIsPhase2Inspected(String isPhase2Inspected) {
        IsPhase2Inspected = isPhase2Inspected;
    }

    public String getIsPhase2InspBy() {
        return IsPhase2InspBy;
    }

    public void setIsPhase2InspBy(String isPhase2InspBy) {
        IsPhase2InspBy = isPhase2InspBy;
    }

    public String getIsPhase2InspDate() {
        return IsPhase2InspDate;
    }

    public void setIsPhase2InspDate(String isPhase2InspDate) {
        IsPhase2InspDate = isPhase2InspDate;
    }

    public String getIsPhase2InspPhoto1() {
        return IsPhase2InspPhoto1;
    }

    public void setIsPhase2InspPhoto1(String isPhase2InspPhoto1) {
        IsPhase2InspPhoto1 = isPhase2InspPhoto1;
    }

    public String getIsPhase2InspPhoto2() {
        return IsPhase2InspPhoto2;
    }

    public void setIsPhase2InspPhoto2(String isPhase2InspPhoto2) {
        IsPhase2InspPhoto2 = isPhase2InspPhoto2;
    }

    public String getIsPhase2InspLatitude() {
        return IsPhase2InspLatitude;
    }

    public void setIsPhase2InspLatitude(String isPhase2InspLatitude) {
        IsPhase2InspLatitude = isPhase2InspLatitude;
    }

    public String getIsPhase2InspLongitude() {
        return IsPhase2InspLongitude;
    }

    public void setIsPhase2InspLongitude(String isPhase2InspLongitude) {
        IsPhase2InspLongitude = isPhase2InspLongitude;
    }

    public String getIsPhase2InspRemarks() {
        return IsPhase2InspRemarks;
    }

    public void setIsPhase2InspRemarks(String isPhase2InspRemarks) {
        IsPhase2InspRemarks = isPhase2InspRemarks;
    }

    public String getIsPhase3Inspected() {
        return IsPhase3Inspected;
    }

    public void setIsPhase3Inspected(String isPhase3Inspected) {
        IsPhase3Inspected = isPhase3Inspected;
    }

    public String getIsPhase3InspBy() {
        return IsPhase3InspBy;
    }

    public void setIsPhase3InspBy(String isPhase3InspBy) {
        IsPhase3InspBy = isPhase3InspBy;
    }

    public String getIsPhase3InspDate() {
        return IsPhase3InspDate;
    }

    public void setIsPhase3InspDate(String isPhase3InspDate) {
        IsPhase3InspDate = isPhase3InspDate;
    }

    public String getIsPhase3InspPhoto1() {
        return IsPhase3InspPhoto1;
    }

    public void setIsPhase3InspPhoto1(String isPhase3InspPhoto1) {
        IsPhase3InspPhoto1 = isPhase3InspPhoto1;
    }

    public String getIsPhase3InspPhoto2() {
        return IsPhase3InspPhoto2;
    }

    public void setIsPhase3InspPhoto2(String isPhase3InspPhoto2) {
        IsPhase3InspPhoto2 = isPhase3InspPhoto2;
    }

    public String getIsPhase3InspLatitude() {
        return IsPhase3InspLatitude;
    }

    public void setIsPhase3InspLatitude(String isPhase3InspLatitude) {
        IsPhase3InspLatitude = isPhase3InspLatitude;
    }

    public String getIsPhase3InspLongitude() {
        return IsPhase3InspLongitude;
    }

    public void setIsPhase3InspLongitude(String isPhase3InspLongitude) {
        IsPhase3InspLongitude = isPhase3InspLongitude;
    }

    public String getIsPhase3InspRemarks() {
        return IsPhase3InspRemarks;
    }

    public void setIsPhase3InspRemarks(String isPhase3InspRemarks) {
        IsPhase3InspRemarks = isPhase3InspRemarks;
    }

    public String getIsFinalInspected() {
        return IsFinalInspected;
    }

    public void setIsFinalInspected(String isFinalInspected) {
        IsFinalInspected = isFinalInspected;
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
