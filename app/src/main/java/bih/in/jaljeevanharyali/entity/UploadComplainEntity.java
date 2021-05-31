package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

/**
 *
 */
public class UploadComplainEntity implements KvmSerializable, Serializable {
	public static Class<UploadComplainEntity> Basicdetail = UploadComplainEntity.class;
	private String _slno = "";
	private String DistrictCode = "";
	private String BlockCode = "";
	private String PanchayatCode = "";
	private String MobileNo = "";
	private String NischaeyTypeCode = "";
	private String WardCode = "";
	private String YojnaTypeCode = "";
	private String ComplainRemarks = "";
	private String photo1 = "";
	private String photo2 = "";
	private String photo3 = "";
	private String photo4 = "";
	private String latitude = "";
	private String longitude = "";

	private String DistrictName = "";
	private String BlockName = "";
	private String PanchayatName = "";
	private String WARDNAME = "";
	private String status = "";
	private String ComplainId1 = "";
	private String ComplainDate = "";

	private String GrievanceRelated = "";
	private String Work_StructureName = "";
	private String StructureTypeNameHn = "";
	private String SchemeCode = "";
	private String Sub_Execution_DeptName = "";
	private String AwayId = "";
	private String schemeStructure = "";
	private String MIS_Scheme_Code = "";
	private String VILLNAME = "";

	private String LatlongComp = "";
	private String LongitudeComp = "";
	private String villagecode = "";
	private String Remarks = "";
	private String ResolveBy = "";
	private String Publicremarks = "";
	private String Strid = "";
	private String DepId = "";
	private String PraklitRashi = "";
	private String AnumodankiRashi = "";
	private String Nidhikasrot = "";
	private String WorkCompletedDate = "";
	private String MbAmount = "";

	public UploadComplainEntity(){

	}

	public UploadComplainEntity(SoapObject res1) {
		this.DistrictName=res1.getProperty("DistrictCode").toString();
		this.BlockName=res1.getProperty("BlockName").toString();
		this.PanchayatName=res1.getProperty("PanchayatName").toString();
		this.WARDNAME=res1.getProperty("WARDNAME").toString();
		this.photo1=res1.getProperty("FeedbackPhoto1").toString();
		this.ComplainRemarks=res1.getProperty("ComplainRemark").toString();
		this.MobileNo=res1.getProperty("mobileNo").toString();
		this.status=res1.getProperty("staust").toString();
		this.ComplainId1=res1.getProperty("ComplainId1").toString();
		this.ComplainDate=res1.getProperty("ComplainDate").toString();

		this.LatlongComp=res1.getProperty("LatlongComp").toString();
		this.LongitudeComp=res1.getProperty("LongitudeComp").toString();
		this.villagecode=res1.getProperty("villagecode").toString();
		this.Remarks=res1.getProperty("Remarks").toString();
		this.ResolveBy=res1.getProperty("ResolveBy").toString();
		this.Publicremarks=res1.getProperty("Publicremarks").toString();
		this.Strid=res1.getProperty("Strid").toString();
		this.DepId=res1.getProperty("DepId").toString();
		this.PraklitRashi=res1.getProperty("PraklitRashi").toString();
		this.AnumodankiRashi=res1.getProperty("AnumodankiRashi").toString();
		this.Nidhikasrot=res1.getProperty("Nidhikasrot").toString();
		this.WorkCompletedDate=res1.getProperty("WorkCompletedDate").toString();
		this.MbAmount=res1.getProperty("MbAmount").toString();
		this.ComplainDate=res1.getProperty("ComplainDate").toString();
		this.ComplainDate=res1.getProperty("ComplainDate").toString();

		this.GrievanceRelated=res1.getProperty("GrievanceRelated").toString();
		this.Work_StructureName=res1.getProperty("SchemeName").toString();
		this.StructureTypeNameHn=res1.getProperty("StructureName").toString();
		this.SchemeCode=res1.getProperty("schemeInsId").toString();
		this.Sub_Execution_DeptName=res1.getProperty("AwyabName").toString();
		this.AwayId=res1.getProperty("AwayId").toString();
		this.VILLNAME=res1.getProperty("VILLNAME").toString();
		this.schemeStructure=res1.getProperty("schemeInsId").toString();
		this.MIS_Scheme_Code=res1.getProperty("MisSChemeCode").toString();
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

	public String getVILLNAME() {
		return VILLNAME;
	}

	public void setVILLNAME(String VILLNAME) {
		this.VILLNAME = VILLNAME;
	}

	public String getLatlongComp() {
		return LatlongComp;
	}

	public void setLatlongComp(String latlongComp) {
		LatlongComp = latlongComp;
	}

	public String getLongitudeComp() {
		return LongitudeComp;
	}

	public void setLongitudeComp(String longitudeComp) {
		LongitudeComp = longitudeComp;
	}

	public String getVillagecode() {
		return villagecode;
	}

	public void setVillagecode(String villagecode) {
		this.villagecode = villagecode;
	}

	public String getRemarks() {
		return Remarks;
	}

	public void setRemarks(String remarks) {
		Remarks = remarks;
	}

	public String getResolveBy() {
		return ResolveBy;
	}

	public void setResolveBy(String resolveBy) {
		ResolveBy = resolveBy;
	}

	public String getPublicremarks() {
		return Publicremarks;
	}

	public void setPublicremarks(String publicremarks) {
		Publicremarks = publicremarks;
	}

	public String getStrid() {
		return Strid;
	}

	public void setStrid(String strid) {
		Strid = strid;
	}

	public String getDepId() {
		return DepId;
	}

	public void setDepId(String depId) {
		DepId = depId;
	}

	public String getPraklitRashi() {
		return PraklitRashi;
	}

	public void setPraklitRashi(String praklitRashi) {
		PraklitRashi = praklitRashi;
	}

	public String getAnumodankiRashi() {
		return AnumodankiRashi;
	}

	public void setAnumodankiRashi(String anumodankiRashi) {
		AnumodankiRashi = anumodankiRashi;
	}

	public String getNidhikasrot() {
		return Nidhikasrot;
	}

	public void setNidhikasrot(String nidhikasrot) {
		Nidhikasrot = nidhikasrot;
	}

	public String getWorkCompletedDate() {
		return WorkCompletedDate;
	}

	public void setWorkCompletedDate(String workCompletedDate) {
		WorkCompletedDate = workCompletedDate;
	}

	public String getMbAmount() {
		return MbAmount;
	}

	public void setMbAmount(String mbAmount) {
		MbAmount = mbAmount;
	}

	public String getSchemeStructure() {
		return schemeStructure;
	}

	public void setSchemeStructure(String schemeStructure) {
		this.schemeStructure = schemeStructure;
	}

	public String getMIS_Scheme_Code() {
		return MIS_Scheme_Code;
	}

	public void setMIS_Scheme_Code(String MIS_Scheme_Code) {
		this.MIS_Scheme_Code = MIS_Scheme_Code;
	}

	public String getStructureTypeNameHn() {
		return StructureTypeNameHn;
	}

	public void setStructureTypeNameHn(String structureTypeNameHn) {
		StructureTypeNameHn = structureTypeNameHn;
	}

	public String getGrievanceRelated() {
		return GrievanceRelated;
	}

	public void setGrievanceRelated(String grievanceRelated) {
		GrievanceRelated = grievanceRelated;
	}

	public String getWork_StructureName() {
		return Work_StructureName;
	}

	public void setWork_StructureName(String work_StructureName) {
		Work_StructureName = work_StructureName;
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

	public String getAwayId() {
		return AwayId;
	}

	public void setAwayId(String awayId) {
		AwayId = awayId;
	}

	public String getDistrictName() {
		return DistrictName;
	}

	public void setDistrictName(String districtName) {
		DistrictName = districtName;
	}

	public String getBlockName() {
		return BlockName;
	}

	public void setBlockName(String blockName) {
		BlockName = blockName;
	}

	public String getPanchayatName() {
		return PanchayatName;
	}

	public void setPanchayatName(String panchayatName) {
		PanchayatName = panchayatName;
	}

	public String getWARDNAME() {
		return WARDNAME;
	}

	public void setWARDNAME(String WARDNAME) {
		this.WARDNAME = WARDNAME;
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

	public String get_slno() {
		return _slno;
	}

	public void set_slno(String _slno) {
		this._slno = _slno;
	}

	public String getDistrictCode() {
		return DistrictCode;
	}

	public void setDistrictCode(String districtCode) {
		DistrictCode = districtCode;
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

	public String getWardCode() {
		return WardCode;
	}

	public void setWardCode(String wardCode) {
		WardCode = wardCode;
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
}