package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class SanrachnaDataEntity implements KvmSerializable, Serializable {

    private static Class<SanrachnaDataEntity> SanrachnaDataEntity_CLASS = SanrachnaDataEntity.class;

    private int id;
    String InspectionID;
    private String Distcode;
    private String DistName;
    private String BlockCode;
    private String BlockName;
    private String PanchayatCode;
    private String PanchayatName;
    private String VILLCODE;
    private String VILLNAME;
    private String DepatmentName;
    private String RajswaThanaNumber;
    private String Latitude;
    private String Longitude;
    private String Khaata_Kheshara_Number;
    private String Commercial_Public;
    private String TypesOfSarchna;
    private String Swamitw_Dep;
    private String Remarks;
    private String Verified_Date;
    private String Verified_By;

    private String Longitude_Mob;
    private String Latitude_Mob;
    private String photo1;
    private String photo2;


    private String IsNewEntryPond;
    private String SwamitwDep_Name;
    private String Area_By_Govt_Record;
    private String Connected_With_Pine;
    private String Availability_Of_Water;
    private String Status_of_Encroachment;
    private String Types_of_Encroachment;
    private String Requirement_of_Renovation;

    private String RemarksCompl;
    private String Appversion;
    private String isUpdated;
    private String Requirement_of_machine;

    private byte[] image1;
    private byte[] image2;


    public SanrachnaDataEntity(SoapObject res1) {


        this.InspectionID=res1.getProperty("id").toString();
        this.Distcode=res1.getProperty("Distcode").toString();
        this.DistName=res1.getProperty("DistName").toString();
        this.BlockCode=res1.getProperty("BlockCode").toString();
        this.BlockName=res1.getProperty("BlockName").toString();
        this.PanchayatCode=res1.getProperty("PanchayatCode").toString();
        this.PanchayatName=res1.getProperty("PanchayatName").toString();
        this.VILLCODE=res1.getProperty("VILLCODE").toString();
        this.VILLNAME=res1.getProperty("VILLNAME").toString();
        this.DepatmentName=res1.getProperty("DepatmentName").toString();
        this.RajswaThanaNumber=res1.getProperty("RajswaThanaNumber").toString();
        this.Latitude=res1.getProperty("Latitude").toString();
        this.Longitude=res1.getProperty("Longitude").toString();
        this.Khaata_Kheshara_Number=res1.getProperty("Khaata_Kheshara_Number").toString();
        this.Commercial_Public=res1.getProperty("Commercial_Public").toString();
        this.TypesOfSarchna=res1.getProperty("TypesOfSarchna").toString();
        this.Swamitw_Dep=res1.getProperty("Swamitw_Dep").toString();
        this.Remarks=res1.getProperty("Remarks").toString();
        this.Verified_Date=res1.getProperty("Verified_Date").toString();
        this.Longitude_Mob=res1.getProperty("Longitude_Mob").toString();
        this.Latitude_Mob=res1.getProperty("Latitude_Mob").toString();

        this.IsNewEntryPond=res1.getProperty("IsNewEntryPond").toString();
        this.SwamitwDep_Name=res1.getProperty("SwamitwDep_Name").toString();
        this.Area_By_Govt_Record=res1.getProperty("Area_By_Govt_Record").toString();
        this.Connected_With_Pine=res1.getProperty("Connected_With_Pine").toString();
        this.Availability_Of_Water=res1.getProperty("Availability_Of_Water").toString();
        this.Status_of_Encroachment=res1.getProperty("Status_Of_Encroachment").toString();
        this.Types_of_Encroachment=res1.getProperty("Type_Of_Encroachment").toString();
        this.Requirement_of_Renovation=res1.getProperty("Requirement_Of_Renovation").toString();

    }

    public SanrachnaDataEntity(){

    }

    public static Class<SanrachnaDataEntity> getSanrachnaDataEntity_CLASS() {
        return SanrachnaDataEntity_CLASS;
    }

    public static void setSanrachnaDataEntity_CLASS(Class<SanrachnaDataEntity> sanrachnaDataEntity_CLASS) {
        SanrachnaDataEntity_CLASS = sanrachnaDataEntity_CLASS;
    }

    public String getAppversion() {
        return Appversion;
    }

    public void setAppversion(String appversion) {
        Appversion = appversion;
    }

    public String getVerified_By() {
        return Verified_By;
    }

    public void setVerified_By(String verified_By) {
        Verified_By = verified_By;
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

    public String getDistcode() {
        return Distcode;
    }

    public void setDistcode(String distcode) {
        Distcode = distcode;
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

    public String getVILLCODE() {
        return VILLCODE;
    }

    public void setVILLCODE(String VILLCODE) {
        this.VILLCODE = VILLCODE;
    }

    public String getVILLNAME() {
        return VILLNAME;
    }

    public void setVILLNAME(String VILLNAME) {
        this.VILLNAME = VILLNAME;
    }

    public String getDepatmentName() {
        return DepatmentName;
    }

    public void setDepatmentName(String depatmentName) {
        DepatmentName = depatmentName;
    }

    public String getRajswaThanaNumber() {
        return RajswaThanaNumber;
    }

    public void setRajswaThanaNumber(String rajswaThanaNumber) {
        RajswaThanaNumber = rajswaThanaNumber;
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

    public String getKhaata_Kheshara_Number() {
        return Khaata_Kheshara_Number;
    }

    public void setKhaata_Kheshara_Number(String khaata_Kheshara_Number) {
        Khaata_Kheshara_Number = khaata_Kheshara_Number;
    }

    public String getCommercial_Public() {
        return Commercial_Public;
    }

    public void setCommercial_Public(String commercial_Public) {
        Commercial_Public = commercial_Public;
    }

    public String getTypesOfSarchna() {
        return TypesOfSarchna;
    }

    public void setTypesOfSarchna(String typesOfSarchna) {
        TypesOfSarchna = typesOfSarchna;
    }

    public String getSwamitw_Dep() {
        return Swamitw_Dep;
    }

    public void setSwamitw_Dep(String swamitw_Dep) {
        Swamitw_Dep = swamitw_Dep;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getVerified_Date() {
        return Verified_Date;
    }

    public void setVerified_Date(String verified_Date) {
        Verified_Date = verified_Date;
    }

    public String getLongitude_Mob() {
        return Longitude_Mob;
    }

    public void setLongitude_Mob(String longitude_Mob) {
        Longitude_Mob = longitude_Mob;
    }

    public String getLatitude_Mob() {
        return Latitude_Mob;
    }

    public void setLatitude_Mob(String latitude_Mob) {
        Latitude_Mob = latitude_Mob;
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

    public String getIsNewEntryPond() {
        return IsNewEntryPond;
    }

    public void setIsNewEntryPond(String isNewEntryPond) {
        IsNewEntryPond = isNewEntryPond;
    }

    public String getSwamitwDep_Name() {
        return SwamitwDep_Name;
    }

    public void setSwamitwDep_Name(String swamitwDep_Name) {
        SwamitwDep_Name = swamitwDep_Name;
    }

    public String getArea_By_Govt_Record() {
        return Area_By_Govt_Record;
    }

    public void setArea_By_Govt_Record(String area_By_Govt_Record) {
        Area_By_Govt_Record = area_By_Govt_Record;
    }

    public String getConnected_With_Pine() {
        return Connected_With_Pine;
    }

    public void setConnected_With_Pine(String connected_With_Pine) {
        Connected_With_Pine = connected_With_Pine;
    }

    public String getAvailability_Of_Water() {
        return Availability_Of_Water;
    }

    public void setAvailability_Of_Water(String availability_Of_Water) {
        Availability_Of_Water = availability_Of_Water;
    }

    public String getStatus_of_Encroachment() {
        return Status_of_Encroachment;
    }

    public void setStatus_of_Encroachment(String status_of_Encroachment) {
        Status_of_Encroachment = status_of_Encroachment;
    }

    public String getTypes_of_Encroachment() {
        return Types_of_Encroachment;
    }

    public void setTypes_of_Encroachment(String types_of_Encroachment) {
        Types_of_Encroachment = types_of_Encroachment;
    }

    public String getRequirement_of_Renovation() {
        return Requirement_of_Renovation;
    }

    public void setRequirement_of_Renovation(String requirement_of_Renovation) {
        Requirement_of_Renovation = requirement_of_Renovation;
    }

    public String getRemarksCompl() {
        return RemarksCompl;
    }

    public void setRemarksCompl(String remarksCompl) {
        RemarksCompl = remarksCompl;
    }

    public String getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(String isUpdated) {
        this.isUpdated = isUpdated;
    }

    public String getRequirement_of_machine() {
        return Requirement_of_machine;
    }

    public void setRequirement_of_machine(String requirement_of_machine) {
        Requirement_of_machine = requirement_of_machine;
    }

    public byte[] getImage1() {
        return image1;
    }

    public void setImage1(byte[] image1) {
        this.image1 = image1;
    }

    public byte[] getImage2() {
        return image2;
    }

    public void setImage2(byte[] image2) {
        this.image2 = image2;
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
