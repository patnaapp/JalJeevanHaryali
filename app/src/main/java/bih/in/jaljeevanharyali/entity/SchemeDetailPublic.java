package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class SchemeDetailPublic implements KvmSerializable, Serializable {

    public static Class<SchemeDetailPublic> SchemeDetailPublic_CLASS = SchemeDetailPublic.class;

    private String DistName;
    private String PanchayatName;
    private String BlockName;
    private String VILLNAME;
    private String VillageCode;
    private String Estimated_Amount;
    private String Approval_Date;
    private String MIS_Scheme_Code;
    private String Work_StructureName;
    private String SchemeCode;
    private String Sub_Execution_DeptName;
    private String StructureTypeNameHn;
    private String StructureId;
    private String Execution_DeptName;
    private String Execution_DeptId;
    private String WorkStatus;
    private String Latitude;
    private String Longitude;
    private String DistCode;
    private String BlockCode;
    private String PanchayatCode;
    private String AwayabId;
    private String WorkCompDate;
    private String FinalAmount;
    private String SourceOfFund="NA";
    private String MBAmount="NA";

    public SchemeDetailPublic(SoapObject res1) {

        this.DistName=res1.getProperty("DistName").toString();
        this.PanchayatName=res1.getProperty("PanchayatName").toString();
        this.BlockName=res1.getProperty("BlockName").toString();
        this.VILLNAME=res1.getProperty("VILLNAME").toString();
        this.VillageCode=res1.getProperty("VillageCode").toString();
        this.Estimated_Amount=res1.getProperty("Estimated_Amount").toString();
        this.Approval_Date=res1.getProperty("Approval_Date").toString();
        this.WorkCompDate=res1.getProperty("WorkCompDate").toString();
        this.FinalAmount=res1.getProperty("FinalAmount").toString();
        this.MIS_Scheme_Code=res1.getProperty("MIS_Scheme_Code").toString();
        this.Work_StructureName=res1.getProperty("Work_StructureName").toString();
        this.SchemeCode=res1.getProperty("SchemeCode").toString();
        this.Sub_Execution_DeptName=res1.getProperty("Sub_Execution_DeptName").toString();
        this.StructureTypeNameHn=res1.getProperty("StructureTypeNameHn").toString();
        this.Execution_DeptName=res1.getProperty("Execution_DeptName").toString();
        this.WorkStatus=res1.getProperty("WorkStatus").toString();
        this.Latitude=res1.getProperty("Phase1Lat").toString();
        this.Longitude=res1.getProperty("Phase1Long").toString();
        this.DistCode=res1.getProperty("DistCode").toString();
        this.BlockCode=res1.getProperty("BlockCode").toString();
        this.PanchayatCode=res1.getProperty("PanchayatCode").toString();
        this.AwayabId=res1.getProperty("AwayabId").toString();
        this.SourceOfFund=res1.getProperty("FundName").toString();
        this.StructureId=res1.getProperty("StrID").toString();
        this.Execution_DeptId=res1.getProperty("Execution_DeptID").toString();
    }

    public SchemeDetailPublic() {

    }

    public String getVillageCode() {
        return VillageCode;
    }

    public void setVillageCode(String villageCode) {
        VillageCode = villageCode;
    }

    public String getExecution_DeptId() {
        return Execution_DeptId;
    }

    public void setExecution_DeptId(String execution_DeptId) {
        Execution_DeptId = execution_DeptId;
    }

    public String getStructureId() {
        return StructureId;
    }

    public void setStructureId(String structureId) {
        StructureId = structureId;
    }

    public String getSourceOfFund() {
        return SourceOfFund;
    }

    public void setSourceOfFund(String sourceOfFund) {
        SourceOfFund = sourceOfFund;
    }

    public String getMBAmount() {
        return MBAmount;
    }

    public void setMBAmount(String MBAmount) {
        this.MBAmount = MBAmount;
    }

    public String getWorkCompDate() {
        return WorkCompDate;
    }

    public void setWorkCompDate(String workCompDate) {
        WorkCompDate = workCompDate;
    }

    public String getFinalAmount() {
        return FinalAmount;
    }

    public void setFinalAmount(String finalAmount) {
        FinalAmount = finalAmount;
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

    public String getAwayabId() {
        return AwayabId;
    }

    public void setAwayabId(String awayabId) {
        AwayabId = awayabId;
    }

    public String getSchemeCode() {
        return SchemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        SchemeCode = schemeCode;
    }

    public String getSub_Execution_DeptName() {
        return Sub_Execution_DeptName;
    }

    public void setSub_Execution_DeptName(String sub_Execution_DeptName) {
        Sub_Execution_DeptName = sub_Execution_DeptName;
    }

    public String getStructureTypeNameHn() {
        return StructureTypeNameHn;
    }

    public void setStructureTypeNameHn(String structureTypeNameHn) {
        StructureTypeNameHn = structureTypeNameHn;
    }

    public String getExecution_DeptName() {
        return Execution_DeptName;
    }

    public void setExecution_DeptName(String execution_DeptName) {
        Execution_DeptName = execution_DeptName;
    }

    public String getWorkStatus() {
        return WorkStatus;
    }

    public void setWorkStatus(String workStatus) {
        WorkStatus = workStatus;
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

    public String getMIS_Scheme_Code() {
        return MIS_Scheme_Code;
    }

    public void setMIS_Scheme_Code(String MIS_Scheme_Code) {
        this.MIS_Scheme_Code = MIS_Scheme_Code;
    }

    public String getWork_StructureName() {
        return Work_StructureName;
    }

    public void setWork_StructureName(String work_StructureName) {
        Work_StructureName = work_StructureName;
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
