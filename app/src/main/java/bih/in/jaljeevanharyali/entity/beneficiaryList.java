package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

/**
 * Created by nicsi on 3/20/2018.
 */
public class beneficiaryList implements Serializable {
    private String auto_id;
    private String scheme_code;
    private String disaser_id;
    private String beneficiary_id;
    private String beneficiery_name;
    private String beneficiery_f_name;
    private String IFCCode;
    private String bank_accountno;
    private String adhar_no;
    private String bank_address;
    private String wardcode;
   // private String villagecode;
    private String panchayt_code;
    private String panchayat_name;
    private String village_name;
    private String ward_name;
    private String RasanCardNo;
    private String AadharName;
    private String MobileNO;
    private String Depid;
    private String InspTypeId;
    private String DistCode;
    private String BlockCode;
    private String Gender;
    private String Category;
    private String SanctionNo;
    private String SactionDate;
    private String DistName;
    private String BlockName;
    private String fYearId;
    private String fYearName;

    private String SectionalLelId;
    private String SectionalLelName;
    private String VillageCode;
//    private String VillageName;

    private String FirstInst;
    private String FirstInstDate;
    private String SecInst;
    private String SecInstDate;
    private String ThirdInst;
    private String ThirdInstDate;
    private String FourthInst;
    private String FourthInstDate;
    private String SchemeId;
    public static Class<beneficiaryList> getdata = beneficiaryList.class;



    public beneficiaryList(){

    }

    public beneficiaryList(SoapObject soapObject) {
        this.beneficiary_id = soapObject.getProperty("BenId").toString();
        this.beneficiery_name = soapObject.getProperty("BenName").toString();
        this.beneficiery_f_name = soapObject.getProperty("FHName").toString();
        this.Depid = soapObject.getProperty("DeptId").toString();
        this.InspTypeId = soapObject.getProperty("InspTypeId").toString();
        this.DistCode = soapObject.getProperty("DistCode").toString();
        this.panchayt_code=soapObject.getProperty("PanchayatCode").toString();
        this.BlockCode=soapObject.getProperty("BlockCode").toString();
        this.Gender=soapObject.getProperty("Gender").toString();
        this.Category=soapObject.getProperty("Category").toString();
        this.BlockName=soapObject.getProperty("BlockName").toString();
        this.panchayat_name=soapObject.getProperty("PanchayatName").toString();
        this.DistName=soapObject.getProperty("DistName").toString();
        this.SanctionNo=soapObject.getProperty("SanctionNo").toString();
        this.SactionDate=soapObject.getProperty("SanctionDate").toString();
        this.fYearId=soapObject.getProperty("FYID").toString();
        this.fYearName=soapObject.getProperty("FY").toString();

        this.adhar_no = soapObject.getProperty("AadharNo").toString();
        this.SectionalLelId = soapObject.getProperty("SanctionedLevelID").toString();
        this.SectionalLelName = soapObject.getProperty("SanctionedLevelName").toString();

        this.VillageCode = soapObject.getProperty("VillageCode").toString();
        this.village_name = soapObject.getProperty("VILLNAME").toString();

        this.FirstInst = soapObject.getProperty("FirstInstallment").toString();
        this.FirstInstDate = soapObject.getProperty("FirstInstallmentDate").toString();
        this.SecInst = soapObject.getProperty("SecondInstallment").toString();
        this.SecInstDate = soapObject.getProperty("SecondInstallmentDate").toString();
        this.ThirdInst = soapObject.getProperty("ThirdInstallment").toString();
        this.ThirdInstDate = soapObject.getProperty("ThirdInstallmentDate").toString();
        this.FourthInst = soapObject.getProperty("FourthInstallment").toString();
        this.FourthInstDate = soapObject.getProperty("FourthInstallmentDate").toString();
        this.SchemeId = soapObject.getProperty("SchemeId").toString();

    }
    public String getAuto_id() {
        return auto_id;
    }

    public void setAuto_id(String auto_id) {
        this.auto_id = auto_id;
    }

    public String getScheme_code() {
        return scheme_code;
    }

    public void setScheme_code(String scheme_code) {
        this.scheme_code = scheme_code;
    }

    public String getDisaser_id() {
        return disaser_id;
    }

    public void setDisaser_id(String disaser_id) {
        this.disaser_id = disaser_id;
    }

    public String getBeneficiery_name() {
        return beneficiery_name;
    }

    public void setBeneficiery_name(String beneficiery_name) {
        this.beneficiery_name = beneficiery_name;
    }

    public String getBeneficiery_f_name() {
        return beneficiery_f_name;
    }

    public void setBeneficiery_f_name(String beneficiery_f_name) {
        this.beneficiery_f_name = beneficiery_f_name;
    }

    public String getIFCCode() {
        return IFCCode;
    }

    public void setIFCCode(String IFCCode) {
        this.IFCCode = IFCCode;
    }

    public String getBank_accountno() {
        return bank_accountno;
    }

    public void setBank_accountno(String bank_accountno) {
        this.bank_accountno = bank_accountno;
    }

    public String getAdhar_no() {
        return adhar_no;
    }

    public void setAdhar_no(String adhar_no) {
        this.adhar_no = adhar_no;
    }

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }

    public String getWardcode() {
        return wardcode;
    }

    public void setWardcode(String wardcode) {
        this.wardcode = wardcode;
    }

//    public String getVillagecode() {
//        return villagecode;
//    }
//
//    public void setVillagecode(String _villagecode) {
//        this.villagecode = _villagecode;
//    }

    public String getPanchayt_code() {
        return panchayt_code;
    }

    public void setPanchayt_code(String panchayt_code) {
        this.panchayt_code = panchayt_code;
    }

    public String getPanchayat_name() {
        return panchayat_name;
    }

    public void setPanchayat_name(String panchayat_name) {
        this.panchayat_name = panchayat_name;
    }

    public String getVillage_name() {
        return village_name;
    }

    public void setVillage_name(String village_name) {
        this.village_name = village_name;
    }

    public String getWard_name() {
        return ward_name;
    }

    public void setWard_name(String ward_name) {
        this.ward_name = ward_name;
    }

    public String getBeneficiary_id() {
        return beneficiary_id;
    }

    public void setBeneficiary_id(String beneficiary_id) {
        this.beneficiary_id = beneficiary_id;
    }

    public String getRasanCardNo() {
        return RasanCardNo;
    }

    public void setRasanCardNo(String rasanCardNo) {
        RasanCardNo = rasanCardNo;
    }

    public String getAadharName() {
        return AadharName;
    }

    public void setAadharName(String aadharName) {
        AadharName = aadharName;
    }

    public String getMobileNO() {
        return MobileNO;
    }

    public void setMobileNO(String mobileNO) {
        MobileNO = mobileNO;
    }

    public String getDepid() {
        return Depid;
    }

    public void setDepid(String depid) {
        Depid = depid;
    }

    public String getInspTypeId() {
        return InspTypeId;
    }

    public void setInspTypeId(String inspTypeId) {
        InspTypeId = inspTypeId;
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

    public String getGender() {
        return Gender;
    }

    public void setGender(String gender) {
        Gender = gender;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public String getSanctionNo() {
        return SanctionNo;
    }

    public void setSanctionNo(String sanctionNo) {
        SanctionNo = sanctionNo;
    }

    public String getSactionDate() {
        return SactionDate;
    }

    public void setSactionDate(String sactionDate) {
        SactionDate = sactionDate;
    }

    public String getDistName() {
        return DistName;
    }

    public void setDistName(String distName) {
        DistName = distName;
    }

    public String getBlockName() {
        return BlockName;
    }

    public void setBlockName(String blockName) {
        BlockName = blockName;
    }

    public String getfYearId() {
        return fYearId;
    }

    public void setfYearId(String fYearId) {
        this.fYearId = fYearId;
    }

    public String getfYearName() {
        return fYearName;
    }

    public void setfYearName(String fYearName) {
        this.fYearName = fYearName;
    }

    public String getSectionalLelId() {
        return SectionalLelId;
    }

    public void setSectionalLelId(String sectionalLelId) {
        SectionalLelId = sectionalLelId;
    }

    public String getSectionalLelName() {
        return SectionalLelName;
    }

    public void setSectionalLelName(String sectionalLelName) {
        SectionalLelName = sectionalLelName;
    }

    public String getVillageCode() {
        return VillageCode;
    }

    public void setVillageCode(String villageCode) {
        VillageCode = villageCode;
    }

//    public String getVillageName() {
//        return VillageName;
//    }
//
//    public void setVillageName(String villageName) {
//        VillageName = villageName;
//    }

    public String getFirstInst() {
        return FirstInst;
    }

    public void setFirstInst(String firstInst) {
        FirstInst = firstInst;
    }

    public String getFirstInstDate() {
        return FirstInstDate;
    }

    public void setFirstInstDate(String firstInstDate) {
        FirstInstDate = firstInstDate;
    }

    public String getSecInst() {
        return SecInst;
    }

    public void setSecInst(String secInst) {
        SecInst = secInst;
    }

    public String getSecInstDate() {
        return SecInstDate;
    }

    public void setSecInstDate(String secInstDate) {
        SecInstDate = secInstDate;
    }

    public String getThirdInst() {
        return ThirdInst;
    }

    public void setThirdInst(String thirdInst) {
        ThirdInst = thirdInst;
    }

    public String getThirdInstDate() {
        return ThirdInstDate;
    }

    public void setThirdInstDate(String thirdInstDate) {
        ThirdInstDate = thirdInstDate;
    }

    public String getFourthInst() {
        return FourthInst;
    }

    public void setFourthInst(String fourthInst) {
        FourthInst = fourthInst;
    }

    public String getFourthInstDate() {
        return FourthInstDate;
    }

    public void setFourthInstDate(String fourthInstDate) {
        FourthInstDate = fourthInstDate;
    }

    public String getSchemeId() {
        return SchemeId;
    }

    public void setSchemeId(String schemeId) {
        SchemeId = schemeId;
    }
}
