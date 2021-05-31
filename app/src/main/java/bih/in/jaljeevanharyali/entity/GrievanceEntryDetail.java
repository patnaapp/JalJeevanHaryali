package bih.in.jaljeevanharyali.entity;

import java.io.Serializable;

public class GrievanceEntryDetail implements Serializable {

    public static Class<GrievanceEntryDetail> GRIEVANCE_CLASS = GrievanceEntryDetail.class;

    private String DistName;
    private String DistCode;
    private String BlockName;
    private String BlockCode;
    private String PanchayatName;
    private String PanchayatCode;
    private String Ward;
    private String VillageCode;
    private String VillageName;

    private String Approval_Date;
    private String MIS_Scheme_Code;
    private String SchemeId;
    private String Work_StructureName;
    private String AbyabId;
    private String AbyabName;
    private String Type;
    private String StructureId;
    private String StructureName;

    private String YojnaTypeCode = "";
    private String ComplainRemarks = "";
    private String photo1 = "";
    private String photo2 = "";
    private String photo3 = "";
    private String photo4 = "";
    private String latitude = "";
    private String longitude = "";
    private String latitudeComp = "";
    private String longitudeComp = "";

    private String status = "";
    private String ComplainId1 = "";
    private String ComplainDate = "";

    private String MobileNo = "";
    private String NischaeyTypeCode = "";

    private String DeptId = "";
    private String DeptName = "";
    private String PraklitRashi = "";
    private String nidhiKaSrot = "";
    private String WorkCompDate = "";
    private String MBAmount = "";

    public GrievanceEntryDetail() {
    }

//    public GrievanceEntryDetail(String distName, String distCode, String blockName, String blockCode, String approval_Date, String MIS_Scheme_Code, String schemeId, String work_StructureName, String abyabId, String abyabName,String type, String panchayatName,String villageName, String panchayatCode,String ward) {
//        DistName = distName;
//        DistCode = distCode;
//        BlockName = blockName;
//        BlockCode = blockCode;
//        Approval_Date = approval_Date;
//        this.MIS_Scheme_Code = MIS_Scheme_Code;
//        SchemeId = schemeId;
//        Work_StructureName = work_StructureName;
//        AbyabId = abyabId;
//        AbyabName = abyabName;
//        Type = type;
//        PanchayatName = panchayatName;
//        VillageName = villageName;
//        PanchayatCode = panchayatCode;
//        Ward = ward;
//    }


    public GrievanceEntryDetail(String distName, String distCode, String blockName, String blockCode, String panchayatName, String panchayatCode, String ward, String villageCode, String villageName, String abyabId, String abyabName, String type, String structureId, String structureName, String latitude, String longitude) {
        DistName = distName;
        DistCode = distCode;
        BlockName = blockName;
        BlockCode = blockCode;
        PanchayatName = panchayatName;
        PanchayatCode = panchayatCode;
        Ward = ward;
        VillageCode = villageCode;
        VillageName = villageName;
        AbyabId = abyabId;
        AbyabName = abyabName;
        Type = type;
        StructureId = structureId;
        StructureName = structureName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitudeComp() {
        return latitudeComp;
    }

    public String getLongitudeComp() {
        return longitudeComp;
    }

    public void setLongitudeComp(String longitudeComp) {
        this.longitudeComp = longitudeComp;
    }

    public void setLatitudeComp(String latitudeComp) {
        this.latitudeComp = latitudeComp;
    }

    public String getVillageCode() {
        return VillageCode;
    }

    public void setVillageCode(String villageCode) {
        VillageCode = villageCode;
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

    public String getPraklitRashi() {
        return PraklitRashi;
    }

    public void setPraklitRashi(String praklitRashi) {
        PraklitRashi = praklitRashi;
    }

    public String getNidhiKaSrot() {
        return nidhiKaSrot;
    }

    public void setNidhiKaSrot(String nidhiKaSrot) {
        this.nidhiKaSrot = nidhiKaSrot;
    }

    public String getWorkCompDate() {
        return WorkCompDate;
    }

    public void setWorkCompDate(String workCompDate) {
        WorkCompDate = workCompDate;
    }

    public String getMBAmount() {
        return MBAmount;
    }

    public void setMBAmount(String MBAmount) {
        this.MBAmount = MBAmount;
    }

    public String getStructureId() {
        return StructureId;
    }

    public void setStructureId(String structureId) {
        StructureId = structureId;
    }

    public String getStructureName() {
        return StructureName;
    }

    public void setStructureName(String structureName) {
        StructureName = structureName;
    }

    public String getPanchayatCode() {
        return PanchayatCode;
    }

    public void setPanchayatCode(String panchayatCode) {
        PanchayatCode = panchayatCode;
    }

    public String getWard() {
        return Ward;
    }

    public void setWard(String ward) {
        Ward = ward;
    }

    public String getPanchayatName() {
        return PanchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        PanchayatName = panchayatName;
    }

    public String getVillageName() {
        return VillageName;
    }

    public void setVillageName(String villageName) {
        VillageName = villageName;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        this.Type = type;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getDistCode() {
        return DistCode;
    }

    public void setDistCode(String distCode) {
        DistCode = distCode;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getBlockCode() {
        return BlockCode;
    }

    public void setBlockCode(String blockCode) {
        BlockCode = blockCode;
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

    public String getSchemeId() {
        return SchemeId;
    }

    public void setSchemeId(String schemeId) {
        SchemeId = schemeId;
    }

    public String getWork_StructureName() {
        return Work_StructureName;
    }

    public void setWork_StructureName(String work_StructureName) {
        Work_StructureName = work_StructureName;
    }

    public String getAbyabId() {
        return AbyabId;
    }

    public void setAbyabId(String abyabId) {
        AbyabId = abyabId;
    }

    public String getAbyabName() {
        return AbyabName;
    }

    public void setAbyabName(String abyabName) {
        AbyabName = abyabName;
    }


    public String getYojnaTypeCode() {
        return YojnaTypeCode;
    }

    public void setYojnaTypeCode(String yojnaTypeCode) {
        YojnaTypeCode = yojnaTypeCode;
    }

    public String getComplainRemarks() {
        return ComplainRemarks;
    }

    public void setComplainRemarks(String complainRemarks) {
        ComplainRemarks = complainRemarks;
    }

    public String getPhoto1() {
        return photo1;
    }

    public void setPhoto1(String photo1) {
        this.photo1 = photo1;
    }

    public String getPhoto2() {
        return photo2;
    }

    public void setPhoto2(String photo2) {
        this.photo2 = photo2;
    }

    public String getPhoto3() {
        return photo3;
    }

    public void setPhoto3(String photo3) {
        this.photo3 = photo3;
    }

    public String getPhoto4() {
        return photo4;
    }

    public void setPhoto4(String photo4) {
        this.photo4 = photo4;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getComplainId1() {
        return ComplainId1;
    }

    public void setComplainId1(String complainId1) {
        ComplainId1 = complainId1;
    }

    public String getComplainDate() {
        return ComplainDate;
    }

    public void setComplainDate(String complainDate) {
        ComplainDate = complainDate;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public void setMobileNo(String mobileNo) {
        MobileNo = mobileNo;
    }

    public String getNischaeyTypeCode() {
        return NischaeyTypeCode;
    }

    public void setNischaeyTypeCode(String nischaeyTypeCode) {
        NischaeyTypeCode = nischaeyTypeCode;
    }
}
