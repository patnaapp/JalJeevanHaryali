package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class YojnaReportEntity implements KvmSerializable, Serializable {

    private String Sub_Execution_DepartmentName;
    private String SubSubExectDept_Name;
    private String Measurement_Of_Structure;
    private String Administrative_Approval_Date;
    private String IsPhase2InspRemarks;
    private String IsLandEnterDt;
    private String ConsumerNo;
    private String ConsumrBill;
    private String TreeType;
    private String TotalTree;
    private String DistCode;
    private String DistName;
    private String BlockCode;
    private String BlockName;
    private String PanchayatCode;
    private String PanchayatName;
    private String WARDCODE;
    private String WARDNAME;
    private String id;
    private String Verified_By;
    private String Execution_DeptID;
    private String Execution_DeptName;
    private String Sub_Execution_DeptID;
    private String SubSubExectDept_Id;
    private String Estimated_Amount;
    private String MIS_Scheme_Code;
    private String Approval_Date;
    private String CreatedDate;
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
    private String IsPhase3Inspected;
    private String IsPhase3InspDate;

    public static Class<YojnaReportEntity> YojnaReportEntityCLASS = YojnaReportEntity.class;

    public YojnaReportEntity(SoapObject res1) {


        this.Sub_Execution_DepartmentName=res1.getProperty("Sub_Execution_DepartmentName").toString();
        this.SubSubExectDept_Name=res1.getProperty("SubSubExectDept_Name").toString();
        this.Measurement_Of_Structure=res1.getProperty("Measurement_Of_Structure").toString();
        this.Administrative_Approval_Date=res1.getProperty("Administrative_Approval_Date").toString();
        this.IsPhase2InspRemarks=res1.getProperty("IsPhase2InspRemarks").toString();
        this.IsLandEnterDt=res1.getProperty("IsLandEnterDt").toString();
        this.ConsumerNo=res1.getProperty("ConsumerNo").toString();
        this.ConsumrBill=res1.getProperty("ConsumrBill").toString();
        this.TreeType=res1.getProperty("TreeType").toString();
        this.TotalTree=res1.getProperty("TotalTree").toString();
        this.DistCode=res1.getProperty("DistCode").toString();
        this.DistName=res1.getProperty("DistName").toString();
        this.BlockCode=res1.getProperty("BlockCode").toString();
        this.BlockName=res1.getProperty("BlockName").toString();
        this.PanchayatCode=res1.getProperty("PanchayatCode").toString();
        this.PanchayatName=res1.getProperty("PanchayatName").toString();
        this.WARDCODE=res1.getProperty("WARDCODE").toString();
        this.WARDNAME=res1.getProperty("WARDNAME").toString();
        this.id=res1.getProperty("id").toString();
        this.Verified_By=res1.getProperty("Verified_By").toString();
        this.Execution_DeptID=res1.getProperty("Execution_DeptID").toString();
        this.Execution_DeptName=res1.getProperty("Execution_DeptName").toString();
        this.Sub_Execution_DeptID=res1.getProperty("Sub_Execution_DeptID").toString();
        this.SubSubExectDept_Id=res1.getProperty("SubSubExectDept_Id").toString();
        this.Estimated_Amount=res1.getProperty("Estimated_Amount").toString();
        this.MIS_Scheme_Code=res1.getProperty("MIS_Scheme_Code").toString();
        this.Approval_Date=res1.getProperty("Approval_Date").toString();
        this.CreatedDate=res1.getProperty("CreatedDate").toString();
        this.Types_OfSarchnaId=res1.getProperty("Types_OfSarchnaId").toString();
        this.Types_OfSarchnaName=res1.getProperty("Types_OfSarchnaName").toString();
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
        this.IsPhase3Inspected=res1.getProperty("IsPhase3Inspected").toString();
        this.IsPhase3InspDate=res1.getProperty("IsPhase3InspDate").toString();

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

    public String getSub_Execution_DepartmentName() {
        return Sub_Execution_DepartmentName;
    }

    public void setSub_Execution_DepartmentName(String sub_Execution_DepartmentName) {
        Sub_Execution_DepartmentName = sub_Execution_DepartmentName;
    }

    public String getSubSubExectDept_Name() {
        return SubSubExectDept_Name;
    }

    public void setSubSubExectDept_Name(String subSubExectDept_Name) {
        SubSubExectDept_Name = subSubExectDept_Name;
    }

    public String getMeasurement_Of_Structure() {
        return Measurement_Of_Structure;
    }

    public void setMeasurement_Of_Structure(String measurement_Of_Structure) {
        Measurement_Of_Structure = measurement_Of_Structure;
    }

    public String getAdministrative_Approval_Date() {
        return Administrative_Approval_Date;
    }

    public void setAdministrative_Approval_Date(String administrative_Approval_Date) {
        Administrative_Approval_Date = administrative_Approval_Date;
    }

    public String getIsPhase2InspRemarks() {
        return IsPhase2InspRemarks;
    }

    public void setIsPhase2InspRemarks(String isPhase2InspRemarks) {
        IsPhase2InspRemarks = isPhase2InspRemarks;
    }

    public String getIsLandEnterDt() {
        return IsLandEnterDt;
    }

    public void setIsLandEnterDt(String isLandEnterDt) {
        IsLandEnterDt = isLandEnterDt;
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

    public String getTotalTree() {
        return TotalTree;
    }

    public void setTotalTree(String totalTree) {
        TotalTree = totalTree;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerified_By() {
        return Verified_By;
    }

    public void setVerified_By(String verified_By) {
        Verified_By = verified_By;
    }

    public String getExecution_DeptID() {
        return Execution_DeptID;
    }

    public void setExecution_DeptID(String execution_DeptID) {
        Execution_DeptID = execution_DeptID;
    }

    public String getExecution_DeptName() {
        return Execution_DeptName;
    }

    public void setExecution_DeptName(String execution_DeptName) {
        Execution_DeptName = execution_DeptName;
    }

    public String getSub_Execution_DeptID() {
        return Sub_Execution_DeptID;
    }

    public void setSub_Execution_DeptID(String sub_Execution_DeptID) {
        Sub_Execution_DeptID = sub_Execution_DeptID;
    }

    public String getSubSubExectDept_Id() {
        return SubSubExectDept_Id;
    }

    public void setSubSubExectDept_Id(String subSubExectDept_Id) {
        SubSubExectDept_Id = subSubExectDept_Id;
    }

    public String getEstimated_Amount() {
        return Estimated_Amount;
    }

    public void setEstimated_Amount(String estimated_Amount) {
        Estimated_Amount = estimated_Amount;
    }

    public String getMIS_Scheme_Code() {
        return MIS_Scheme_Code;
    }

    public void setMIS_Scheme_Code(String MIS_Scheme_Code) {
        this.MIS_Scheme_Code = MIS_Scheme_Code;
    }

    public String getApproval_Date() {
        return Approval_Date;
    }

    public void setApproval_Date(String approval_Date) {
        Approval_Date = approval_Date;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
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

    public String getIsPhase3Inspected() {
        return IsPhase3Inspected;
    }

    public void setIsPhase3Inspected(String isPhase3Inspected) {
        IsPhase3Inspected = isPhase3Inspected;
    }

    public String getIsPhase3InspDate() {
        return IsPhase3InspDate;
    }

    public void setIsPhase3InspDate(String isPhase3InspDate) {
        IsPhase3InspDate = isPhase3InspDate;
    }
}
