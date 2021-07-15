package bih.in.jaljeevanharyali.web_services;

import android.content.ContentProvider;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import bih.in.jaljeevanharyali.activity.PanchayatEntity;
import bih.in.jaljeevanharyali.adapter.OtherDeptInsptEntity;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.DashboardEntity;
import bih.in.jaljeevanharyali.entity.DepartmentDetail;
import bih.in.jaljeevanharyali.entity.DepartmentEntity;
import bih.in.jaljeevanharyali.entity.GrievanceEntryDetail;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.entity.NurseryReportEntity;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;
import bih.in.jaljeevanharyali.entity.PondWellReportEntity;
import bih.in.jaljeevanharyali.entity.PublicNurseryBuildingEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaDataEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaTypeEntity;
import bih.in.jaljeevanharyali.entity.SchemeDetailPublic;
import bih.in.jaljeevanharyali.entity.SignUp;
import bih.in.jaljeevanharyali.entity.StructureDetailPublic;
import bih.in.jaljeevanharyali.entity.StructureRemarkEntity;
import bih.in.jaljeevanharyali.entity.StructureType;
import bih.in.jaljeevanharyali.entity.TreesDetail;
import bih.in.jaljeevanharyali.entity.UploadComplainEntity;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.Versioninfo;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.WellInspectionEntity;
import bih.in.jaljeevanharyali.entity.YojnaReportEntity;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.AppConstant;
import bih.in.jaljeevanharyali.utility.Utiilties;

public class WebServiceHelper{

    public static final String SERVICENAMESPACE = "http://jaljeevanhariyali.bih.nic.in/";
   // public static final String SERVICEURL1 = "https://jaljeevanhariyali.bih.nic.in/RuralDevelopmentPondLakeWebService.asmx";
    public static final String SERVICEURL1 = "https://jaljeevanhariyali.bih.nic.in/RuralDevelopmentPondLakeWebService.asmx";

//    public static final String SERVICENAMESPACE = "http://10.133.20.159/";
//    public static final String SERVICEURL1 = "http://10.133.20.159/TestService/RuralDevelopmentPondLakeWebService.asmx";

    public static final String APPVERSION_METHOD = "getAppLatest";
    public static final String AUTHENTICATE_METHOD = "Authenticate";
    public static final String AUTHENTICATE_PUBLIC = "AuthenticatePublic";
    private static final String REGISTRATION_METHOD = "RegisterUserMobile";
    public static final String BASIC_DETAILS_UPLOAD="InsertComplainUsersDt";

    private static final String REGISTER_USER = "RegisterUser";
    private static final String UPDATEPONDLAKEDATA = "UpdateData_INTO_Phy_Ver_Of_PondLakeNew";
    private static final String UPDATEREMARKDETAIL = "UpdateStructureRemarks";
    private static final String UPDATEDATATASKCOMPLETE = "UpdateDataTaskCompletedPond";
    private static final String MARKENCRHMNTPOND = "MarkEncrochmentPond";
    private static final String MARKENCRHMNTWELL = "MarkEncrochmentWell";
    private static final String REMOVEENCRHMNTPOND = "RemoveEncrochmentPond";
    private static final String REMOVEENCRHMNTWELL = "RemoveEncrochmentWell";
    private static final String UPDATEMANREGADATANEW = "SchemeInspection";
    private static final String UPLOADOTHERSCHEMEADATA = "UpdateOther_DeptOfRuralDeptNew";
    private static final String UPDATEWELLDATA = "UpdateData_INTO_Phy_Ver_Of_WellNew";
    private static final String INSERTNURSURY="InsertNursery";
    private static final String INSERTBUILDING="InsertBuilding";

    private static final String PONDLAKEDATA = "getInitialDetailsPondLakeData";
    private static final String PONDLAKEDATADISTRICTWISE = "getInitialDetailsPondLakeDataDistrictWise";
    private static final String PONDLAKEENCRCHMNTDATA = "getInitialDetailsPondLakeDataCoVerified";
    private static final String WELLNCRCHMNTDATA = "getInitialDetailsWellDataCoVerified";
    private static final String PONDINSPECTIONLIST = "getPondInspectionList";
    private static final String GETMARKENCROACHMENTPOND = "getMarkEnchrochmentPond";
    private static final String WELLINSPECTIONLIST = "getWellInspectionList";
    private static final String WELLDATA = "getInitialDetailsWellData";
    private static final String WELLDATADISTRCITWISE = "getInitialDetailsWellDataDistrictWise";
    private static final String PONDDATAMWRD = "getInitialDetailsPondMwr";
    private static final String GETVILLAGELIST = "getVillageList";
    private static final String GETEXECDEPTLIST = "getDepartmentList";
    private static final String GETDEPARTMENTLIST = "getSwitawDepList";
    private static final String GETTRESSTYPELIST = "getTypesOfTreeList";
    private static final String GETAWAYABLIST = "getAwayList";
    private static final String GETSANRACHNATYPELIST = "getTypesOfSanrchnaList";
    private static final String GETSSCHEMELIST = "getSchemeDetails";
    private static final String GETSSCHEMELISTLOCATIONWISE = "getSchemeDistanceWise";
    private static final String GETSTRUCTURELIST = "getSchemeStructureWise";
    private static final String GETSTRUCTURELISTLOCATIONWISE = "getSchemeStructureWiseDistance";
    private static final String GETBUILDINGLISTLOCATIONWISE = "GetBuildingDetailDistanceWise";
    private static final String GETNURSERYLISTLOCATIONWISE = "GetNursuryDetailDistanceWise";
    private static final String GETWORKSTRUCTURETYPELIST = "getWorkStructureTypeList";
    private static final String GETMANREGADEPTINSPLIST = "getSchemeInspectionList";
    private static final String GETREMARKUPDATELIST = "getInitialDetailsForRemarks";
    private static final String GETOTHERDEPTINSPLIST = "getOtherDeptinspectionList";
    private static final String GETPONDINSPECTCTLIST = "getPondInspeCtlist";
    private static final String GETNURSURYLIST = "getNursery";
    private static final String GETBUILDINGLIST = "getBuilding";
    private static final String GETWARDLIST = "getWardList";
    private static final String GETPANCHAYATLIST = "getPanchayatList";

    public static final String REGISTRATIONOTP = "VerifyComplainuser";
    public static final String GETCOMPLAINDETAIL = "getComplainDetails";
    public static final String GETDashboardData = "getGirvanceDasBoard";
    public static final String UploadGrievance = "getComplainDetails";


    static String rest;

    public static Versioninfo CheckVersion(String version) {
        Versioninfo versioninfo;
        SoapObject res1;
        try {

            res1=getServerData(APPVERSION_METHOD, Versioninfo.Versioninfo_CLASS,"IMEI","Ver","0",version);
            SoapObject final_object = (SoapObject) res1.getProperty(0);

            versioninfo = new Versioninfo(final_object);

        } catch (Exception e) {

            return null;
        }
        return versioninfo;
    }


    public static String completeSignupOld(SignUp data) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,
                    REGISTER_USER);

            request.addProperty("Name",data.getName());
            request.addProperty("DistrictCode",data.getDist_code());
            request.addProperty("BlockCode",data.getBlock_code());
            request.addProperty("MobileNo",data.getMobile());
            request.addProperty("Degignation",data.getDesignation());
            //request.addProperty("CreatedBy",data.getUpload_by());
            request.addProperty("IMEI","123456789");
            request.addProperty("Appversion","1.0");
            request.addProperty("Pwd","abc");

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    SignUp.SIGNUP_CLASS.getSimpleName(),
                    SignUp.SIGNUP_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTER_USER,
                    envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                // Log.d("", result.toString());

                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String registration_otp(UserDetails user) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,REGISTRATIONOTP);
            request.addProperty("_otp",user.getOtp());
            request.addProperty("_Name",user.getName());
            request.addProperty("_Mobile",user.getMobileNo());
            request.addProperty("_FatherName",user.getFatherName());
            request.addProperty("_Address",user.getAddress());
            request.addProperty("_Pwd",user.getPassword());
            request.addProperty("_DistCode",user.getDistrictCode());
            request.addProperty("_panchayatCode",user.getPanchaayatCode());
            request.addProperty("_BlockCode",user.getBlockCode());
            request.addProperty("_EntryDate",user.getEntrydate());
            //request.addProperty("_DOB",user.getEntrydate());
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    UserDetails.getUserClass().getSimpleName(),
                    UserDetails.getUserClass());
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTRATIONOTP,
                    envelope);

            Object result = envelope.getResponse();

            if (result != null) {
                // Log.d("", result.toString());

                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String UploadBasicDetails(GrievanceEntryDetail data,String mobile,String reamrks) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,BASIC_DETAILS_UPLOAD);

            request.addProperty("_mobile",mobile);
            request.addProperty("_DistCode",data.getDistCode());
            request.addProperty("_BlockCode",data.getBlockCode());
            request.addProperty("_panchayatCode", data.getPanchayatCode());
            request.addProperty("_WardCode", data.getWard().equals("NA") ? "" : data.getWard());
            request.addProperty("_Villagecode", data.getVillageCode());

            request.addProperty("_ComplainRemark",reamrks);
            request.addProperty("_Latlong", data.getLatitude());
            request.addProperty("_Longitude", data.getLongitude());
            request.addProperty("_EntryDate ",(Utiilties.getCurrentDateDMY()));
            request.addProperty("_GrievanceRelated",data.getType());
            request.addProperty("_AwayId",data.getAbyabId());
            request.addProperty("_SchemeStrid",data.getSchemeId());
            request.addProperty("_SchemeName",data.getWork_StructureName());
            request.addProperty("_MisSChemeCode",data.getMIS_Scheme_Code());
            request.addProperty("_Strid",data.getStructureId());

            request.addProperty("_DepId",data.getDeptId());
            request.addProperty("_DeptName",data.getDeptName());
            request.addProperty("_PraklitRashi",data.getPraklitRashi());
            //request.addProperty("_AnumodanDate",Utiilties.convertDateStringFormet("dd/MM/yyyy HH:mm:ss","dd-MM-yyyy",data.getApproval_Date()));
            request.addProperty("_Nidhikasrot",data.getNidhiKaSrot());
            request.addProperty("_WorkCompletedDate",data.getWorkCompDate().equals("NA") ? "" : data.getWorkCompDate());
            request.addProperty("_MbAmount",data.getMBAmount());
            request.addProperty("_StructureName",data.getStructureName());
            request.addProperty("_AwyabName",data.getAbyabName());
            request.addProperty("_LatlongComp",data.getLatitudeComp());
            request.addProperty("_LongitudeComp",data.getLongitudeComp());

            request.addProperty("_FeedbackPhoto1",data.getPhoto1());
            request.addProperty("_FeedbackPhoto2", data.getPhoto2());
            request.addProperty("_FeedbackPhoto3", data.getPhoto3());
            request.addProperty("_FeedbackPhoto4", data.getPhoto4());

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,GrievanceEntryDetail.GRIEVANCE_CLASS.getSimpleName(), GrievanceEntryDetail.GRIEVANCE_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + BASIC_DETAILS_UPLOAD, envelope);
            Object result = envelope.getResponse();
            if (result != null) {
                // Log.d("", result.toString());
                return String.valueOf(result);
            } else
                return null;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
        //return null;
    }

    public static ArrayList<UploadComplainEntity> getGrievanceList(String benId) {

        SoapObject request = new SoapObject(SERVICENAMESPACE,GETCOMPLAINDETAIL);

        request.addProperty("_UserId", benId);

        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            envelope.addMapping(SERVICENAMESPACE,UploadComplainEntity.Basicdetail.getSimpleName(), UploadComplainEntity.Basicdetail);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + GETCOMPLAINDETAIL,
                    envelope);

            res1 = (SoapObject) envelope.getResponse();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        int TotalProperty = res1.getPropertyCount();

        ArrayList<UploadComplainEntity> pvmArrayList = new ArrayList<UploadComplainEntity>();

        for (int ii = 0; ii < TotalProperty; ii++) {
            if (res1.getProperty(ii) != null) {
                Object property = res1.getProperty(ii);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    UploadComplainEntity block = new UploadComplainEntity(final_object);
                    pvmArrayList.add(block);
                }
            } else
                return pvmArrayList;
        }
        return pvmArrayList;
    }

    public static String Registration(String mob) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,REGISTRATION_METHOD);


            request.addProperty("_Mobile",mob);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    UserDetails.getUserClass().getSimpleName(),
                    UserDetails.getUserClass());
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTRATION_METHOD,
                    envelope);

            Object result = envelope.getResponse();

            //return "1";
            if (result != null) {
                // Log.d("", result.toString());
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String completeSignup(SignUp data,String imei,String version) {
        SoapObject request = new SoapObject(SERVICENAMESPACE, REGISTER_USER);
        request.addProperty("Name",data.getName());
        request.addProperty("DistrictCode",data.getDist_code());
        request.addProperty("BlockCode",data.getBlock_code());
        request.addProperty("MobileNo",data.getMobile());
        request.addProperty("Degignation",data.getDesignation());
        //request.addProperty("CreatedBy",data.getUpload_by());
        request.addProperty("IMEI",imei);
        request.addProperty("Appversion",version);
        request.addProperty("Pwd","abc");
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + REGISTER_USER,
                    envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return rest;
    }

    public static String uploadWellData(WellInspectionEntity data, UserDetails userInfo, String version) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATEWELLDATA);

        String entryStatus = (data.getInspectionID() == null || data.getInspectionID() == "") ? "Y" : "N";
        if(entryStatus.equals("Y")){
            data.setLatitude(data.getLatitude_Mob());
            data.setLongitude(data.getLongitude_Mob());
        }

        request.addProperty("_InspectionID",data.getInspectionID());
        request.addProperty("_DistrictID",data.getDistID());
        request.addProperty("_BlockID",data.getBlockID());
        request.addProperty("_PanchayatID",data.getPanchayat_Code().trim());
        request.addProperty("_VillageID",data.getVillageID());
        request.addProperty("_Khaata_Kheshara_Number",data.getKhata_Khesra_No());
        request.addProperty("_Commercial_Public",data.getPrivate_or_Public());
        request.addProperty("_Out_Side_Diameter", data.getOutside_Diamter());
        request.addProperty("_Name_Of_Under_Taken_Dept", data.getName_of_undertaken());
        request.addProperty("_Requirement_Of_Flyer", data.getRequirement_Of_Flyer());
        request.addProperty("_Status_Of_Encroachment", data.getStatus_of_Encroachment());
        request.addProperty("_Type_Of_Encroachment", data.getTypes_of_Encroachment());
        request.addProperty("_Requirement_Of_Renovation", data.getRequirement_of_Renovation());

        request.addProperty("_Remarks", data.getRemarks());
        request.addProperty("_Verified_By", userInfo.getUserID());
        request.addProperty("_Verified_Date", data.getVerified_Date());
        request.addProperty("_AppVersion", version);

        //request.addProperty("_MachineAwaskta", data.getRequirement_of_machine());
        //request.addProperty("_Swamitw_Dep", data.getRequirementOfRenovation());

        request.addProperty("_Latitude", data.getLatitude());
        request.addProperty("_Longitude", data.getLongitude());
        request.addProperty("_Latitude_Mob", data.getLatitude_Mob());
        request.addProperty("_Longitude_Mob", data.getLongitude_Mob());
        request.addProperty("_JalSarchna_HaiYaNahi", data.getWellAvblValue());
        request.addProperty("_SwamitwDep_Name", data.getWellOwnerOtherDeptName());
        request.addProperty("_StrID", data.getStructureId());
        request.addProperty("_TypesOfSarchna", data.getStructureId().equals("2") ? "W" : "C");
        request.addProperty("_Functional", data.getFunctional());
        request.addProperty("_DepId", userInfo.getDeptId());

        request.addProperty("_Photo", data.getPhoto1());
        request.addProperty("_Photo1", data.getPhoto2());
        request.addProperty("_Photo2", data.getPhoto3());
        request.addProperty("_Photo3", data.getPhoto4());

        //request.addProperty("_IsNewEntryWell", entryStatus);

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UPDATEWELLDATA,
                    envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return rest;
    }

    public static String resizeBase64Image(String base64image){
        byte [] encodeByte=Base64.decode(base64image.getBytes(), Base64.DEFAULT);
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inPurgeable = true;
        Bitmap image = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length,options);


        if(image.getHeight() <= 200 && image.getWidth() <= 200){
            return base64image;
        }
        image = Bitmap.createScaledBitmap(image, 100, 100, false);

        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG,100, baos);

        byte [] b=baos.toByteArray();
        System.gc();
        return Base64.encodeToString(b, Base64.NO_WRAP);

    }

    public static String uploadPondEnchrmntDate(PondEncroachmentEntity data, String type) {
        String method;
        if(type.equals("pond")){
            method = data.getUploadType().equals("M") ? MARKENCRHMNTPOND : REMOVEENCRHMNTPOND;
        }else{
            method = data.getUploadType().equals("M") ? MARKENCRHMNTWELL : REMOVEENCRHMNTWELL;
        }


        SoapObject request = new SoapObject(SERVICENAMESPACE, method);

        request.addProperty("_InspectionID",data.getInspectionID());

        if(data.getUploadType().equals("M")){
            request.addProperty("_Status_Of_Encroachment",data.getStatus_Of_Encroachment());
            request.addProperty("_Type_Of_Encroachment", data.getType_Of_Encroachment());
            request.addProperty("_Verified_By", data.getVerified_By());
            request.addProperty("_Verified_Date", data.getVerified_Date());
        }else{
            request.addProperty("_EnchrochmentRemovetBy", data.getVerified_By());
            request.addProperty("_EnchrochmentRemovetDate", data.getVerified_Date());
            request.addProperty("_EnchrochmentStartDate",data.getEnchrochmentStartDate());
            request.addProperty("_EnchrochmentEndDate", data.getEnchrochmentEndDate());
            request.addProperty("_NoticeDate", data.getNoticeDate());
            request.addProperty("_NoticeNo", data.getNoticeNo());
        }

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + method,
                    envelope);
            rest = envelope.getResponse().toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }


        return rest;
    }

    public static String uploadSanrachanDetail(SanrachnaDataEntity data) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATEDATATASKCOMPLETE);

        String entryStatus = (data.getInspectionID() == null || data.getInspectionID() == "") ? "Y" : "N";

        request.addProperty("_InspectionID",data.getInspectionID());
        request.addProperty("_TaskCompRemark",data.getRemarksCompl());
        request.addProperty("_TaskPhoto",data.getPhoto1());
        request.addProperty("_TaskPhoto1",data.getPhoto2());
        request.addProperty("_TaskCompLatitude",data.getLatitude_Mob());
        request.addProperty("_TaskCompLongitude",data.getLongitude_Mob());
        request.addProperty("_TaskCompdate",data.getVerified_Date());
        request.addProperty("_TaskCompby",data.getVerified_By());

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UPDATEDATATASKCOMPLETE,
                    envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return rest;
    }

    public static String uploadNursuryDate(NurseryEntity data) {

        String methodName = data.getStrType().equals(AppConstant.NURSURY) ? INSERTNURSURY : INSERTBUILDING;

        SoapObject request = new SoapObject(SERVICENAMESPACE, methodName);

        String entryStatus = (data.getInspectionID() == null || data.getInspectionID() == "") ? "" : data.getInspectionID();


        request.addProperty("_DistrictID",data.getDist_Code());
        request.addProperty("_BlockID",data.getBlock_Code());
        request.addProperty("_PanchayatID",data.getPanchayat_Code());
        request.addProperty("_Urban_AreaID",data.getArea_Code().trim());
        request.addProperty("_WardID",data.getWard_Code());

        if(data.getStrType().equals(AppConstant.NURSURY)){
            request.addProperty("_VillageCode",data.getVillage_Code());
            request.addProperty("_NurseryID",entryStatus);
            request.addProperty("_NurseryName",data.getNursury_Name());
            request.addProperty("_TotalArea",data.getArea());
            request.addProperty("_TotalTree", data.getTree());
            request.addProperty("_ContactMobile", data.getMobile());

        }else if(data.getStrType().equals(AppConstant.BUILDING)){
            request.addProperty("_BID",data.getInspectionID());
            request.addProperty("_bulldingType",data.getBuildingType());
            request.addProperty("_BuildingName",data.getBuildingName());
            request.addProperty("_BCode",data.getBCode());
            request.addProperty("_TotalArea",data.getArea());
            request.addProperty("_Execution_DeptID", data.getExecution_DeptID());
            request.addProperty("_OtherDept", data.getOtherDept());
            request.addProperty("_ConsumerNo", data.getConsumerNo());
        }

        request.addProperty("_Latitude", data.getLatitude());
        request.addProperty("_Longitude", data.getLongitude());
        request.addProperty("_CreatedBy", data.getEntry_By());
        request.addProperty("_CreatedOn", data.getEntryDate());
        request.addProperty("_Appversion", data.getAppVersion());
        request.addProperty("_IsInspectedBy", data.getEntry_By());
        request.addProperty("_InspectedDate", data.getEntryDate());
        request.addProperty("_Remarks", data.getRemark());
        request.addProperty("_InspectedDptId", data.getDeptId());

        request.addProperty("_Photo1", data.getPhoto());

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName, envelope);
            rest = envelope.getResponse().toString();

        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

        return rest;
    }
    public static String uploadRemarkDetail(StructureRemarkEntity data) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATEREMARKDETAIL);

        request.addProperty("_InspectionID",data.getInspectionID());
        request.addProperty("_Remarks",data.getNewRemark());
        request.addProperty("_Verified_By",data.getEntryBy());
        request.addProperty("_Verified_Date",data.getEntryDate().trim());
        request.addProperty("_AppVersion",data.getAppVersion());
        request.addProperty("_Latitude_Mob",data.getLatitude());
        request.addProperty("_Longitude_Mob",data.getLongitude());
        request.addProperty("_JalSarchna_HaiYaNahi", data.getIsStrAvailable());
        request.addProperty("_StrID", data.getStrType());
        request.addProperty("_Functional", data.getIsWorking());

        request.addProperty("_Photo", data.getPhoto());

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UPDATEREMARKDETAIL, envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

        return rest;
    }

    public static String uploadPondLakeDate(PondInspectionDetail data, UserDetails userInfo, String version) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATEPONDLAKEDATA);

        //String entryStatus = (data.getInspectionID() == null || data.getInspectionID() == "") ? "Y" : "N";
        String entryStatus = "N";
        if (data.getInspectionID() == null || data.getInspectionID() == ""){
            entryStatus = "Y";
            request.addProperty("_Latitude", data.getLatitude_Mob());
            request.addProperty("_Longitude", data.getLongitude_Mob());
        }else{
            entryStatus = "N";
            request.addProperty("_Latitude", data.getLatitude());
            request.addProperty("_Longitude", data.getLongitude());
        }

        request.addProperty("_InspectionID",data.getInspectionID());
        request.addProperty("_DistrictID",data.getDistrictCode());
        request.addProperty("_BlockID",data.getBlockCode());
        request.addProperty("_PanchayatID",data.getPanchayatCode().trim());
        request.addProperty("_VillageID",data.getVillage());
        request.addProperty("_Khaata_Kheshara_Number",data.getKhataKhesraNo());
        request.addProperty("_Commercial_Public",data.getPrivateOrPublic());
        request.addProperty("_Area_By_Govt_Record",data.getAreaByGovtRecord());
        request.addProperty("_Connected_With_Pine", data.getConnectedWithPine());
        request.addProperty("_Availabilty_Of_Water", data.getAvailiablityOfWater());
        request.addProperty("_Status_Of_Encroachment", data.getStatusOfEncroachment());
        request.addProperty("_Type_Of_Encroachment", data.getTypesOfEncroachment());
        request.addProperty("_Requirement_Of_Renovation", data.getRequirementOfRenovation());

        request.addProperty("_Remarks", data.getRemarks());
        request.addProperty("_Verified_By", userInfo.getUserID());
        request.addProperty("_Verified_Date", data.getVerified_Date());
        request.addProperty("_AppVersion",version);

        request.addProperty("_MachineAwaskta", data.getRequirementOfMachine());
        request.addProperty("_Swamitw_Dep", data.getOwnerName());
        request.addProperty("_Latitude_Mob", data.getLatitude_Mob());
        request.addProperty("_Longitude_Mob", data.getLongitude_Mob());

        request.addProperty("_JalSarchna_HaiYaNahi", data.getPondAvblValue());
        request.addProperty("_TypesOfSarchna", data.getPondCatValue());
        request.addProperty("_TypesOfSanchnaName", data.getPondCatName());
        request.addProperty("_SwamitwDep_Name", data.getPondOwnerOtherDeptName());
        request.addProperty("_AwayId", data.getAbyabID());
        request.addProperty("_DepId", data.getDeptId());
        request.addProperty("_Functional", data.getFunctional());

        request.addProperty("_Photo", data.getPhoto1());
        request.addProperty("_Photo1", data.getPhoto2());
        request.addProperty("_Photo2", data.getPhoto3());
        request.addProperty("_Photo3", data.getPhoto4());

        //request.addProperty("_IsNewEntryPond", entryStatus);

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UPDATEPONDLAKEDATA, envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }

        return rest;
    }

    public static String uploadManregadata(ManregaSchemeDetail data, int phaseType) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPDATEMANREGADATANEW);

        request.addProperty("_PhaseType", phaseType);
        request.addProperty("_AwayabID", data.getAwayabId());
        request.addProperty("_Inspectionid", data.getId());
        request.addProperty("_App_Version", data.getApp_Version());

//        request.addProperty("_Khaata_Kheshara_Number", data.getKhaata_Kheshara_Number());
//        request.addProperty("_Rakba", data.getRakba());
//        request.addProperty("_LandOwnerMob", data.getLandOwnerMob());
//        request.addProperty("_LandOwnerName", data.getLandOwnerName());
        if(data.getIsLandEnterDt().equals("Y")){
            request.addProperty("_FencingLatitude1", "");
            request.addProperty("_FencingLatitude2", "");
            request.addProperty("_FencingLatitude3", "");
            request.addProperty("_FencingLatitude4", "");
            request.addProperty("_FencingLongitude1", "");
            request.addProperty("_FencingLongitude2", "");
            request.addProperty("_FencingLongitude3", "");
            request.addProperty("_FencingLongitude4", "");
        }else{
            request.addProperty("_FencingLatitude1", data.getFencingLatitude1());
            request.addProperty("_FencingLatitude2", data.getFencingLatitude2());
            request.addProperty("_FencingLatitude3", data.getFencingLatitude3());
            request.addProperty("_FencingLatitude4", data.getFencingLatitude4());
            request.addProperty("_FencingLongitude1", data.getFencingLongitude1());
            request.addProperty("_FencingLongitude2", data.getFencingLongitude2());
            request.addProperty("_FencingLongitude3", data.getFencingLongitude3());
            request.addProperty("_FencingLongitude4", data.getFencingLongitude4());
        }

        request.addProperty("_IsLandEnterDt", data.getIsLandEnterDt());

        if(data.getIsPhase3Inspected() != null && data.getIsPhase3Inspected().equals("Y")){
            request.addProperty("_IsFinalInspected", "Y");
        }
//        request.addProperty("_ConsumerNo", data.getConsumerNo());
//        request.addProperty("_ConsumrBill", data.getConsumrBill());
//        request.addProperty("_TreeType", data.getTreeType());
//        request.addProperty("_TotalTree", data.getTotalTree());

        switch (phaseType){
            case 1:
                request.addProperty("_IsPhase1Inspected", data.getIsPhase1Inspected());
                request.addProperty("_IsPhase1InspBy", data.getIsPhase1InspBy());
                request.addProperty("_IsPhase1InspDate", data.getIsPhase1InspDate());
                request.addProperty("_IsPhase1InspLatitude", data.getIsPhase1InspLatitude());
                request.addProperty("_IsPhase1InspLongitude", data.getIsPhase1InspLongitude());
                request.addProperty("_IsPhase1InspRemarks", data.getIsPhase1InspRemarks());
                request.addProperty("_IsPhase1InspPhoto1", data.getIsPhase1InspPhoto1());
                request.addProperty("_IsPhase1InspPhoto2", data.getIsPhase1InspPhoto2());
                break;
            case 2:
                request.addProperty("_IsPhase2Inspected", data.getIsPhase2Inspected());
                request.addProperty("_IsPhase2InspBy", data.getIsPhase2InspBy());
                request.addProperty("_IsPhase2InspDate", data.getIsPhase2InspDate());
                request.addProperty("_IsPhase2InspLatitude", data.getIsPhase2InspLatitude());

                request.addProperty("_IsPhase2InspLongitude", data.getIsPhase2InspLongitude());
                request.addProperty("_IsPhase2InspRemarks", data.getIsPhase2InspRemarks());
                request.addProperty("_IsPhase2InspPhoto1", data.getIsPhase2InspPhoto1());
                request.addProperty("_IsPhase2InspPhoto2", data.getIsPhase2InspPhoto2());
                break;
            case 3:
                request.addProperty("_IsPhase3Inspected", data.getIsPhase3Inspected());
                request.addProperty("_IsPhase3InspBy", data.getIsPhase3InspBy());
                request.addProperty("_IsPhase3InspDate", data.getIsPhase3InspDate());
                request.addProperty("_IsPhase3InspLatitude", data.getIsPhase3InspLatitude());
                request.addProperty("_IsPhase3InspLongitude", data.getIsPhase3InspLongitude());
                request.addProperty("_IsPhase3InspRemarks", data.getIsPhase3InspRemarks());
                request.addProperty("_IsPhase3InspPhoto1", data.getIsPhase3InspPhoto1());
                request.addProperty("_IsPhase3InspPhoto2", data.getIsPhase3InspPhoto2());
                break;
        }

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UPDATEMANREGADATANEW,
                    envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return rest;
    }

    public static String uploadOtherdata(ManregaSchemeDetail data, int phaseType) {

        SoapObject request = new SoapObject(SERVICENAMESPACE, UPLOADOTHERSCHEMEADATA);

        //String entryStatus = (data.getInspectionID() == null || data.getInspectionID() == "") ? "Y" : "N";

//        request.addProperty("_Execution_DeptID",data.getExecution_Dept_ID());
//        request.addProperty("_Sub_Exec_DeptID",data.getSub_Exect_Dept_ID());
//        request.addProperty("_Sub_Sub_Exec_DeptID",data.getSub_Sub_Exect_Dept_ID().trim());
//        request.addProperty("_Sub_Exec_Value",data.getSub_Exect_Value());
//        request.addProperty("_DistrictID",data.getDistrict_Id());
//        request.addProperty("_BlockID",data.getBlock_ID());
//        request.addProperty("_PanchayatID", data.getPanchayat_Id());
//        request.addProperty("_VillageCode", data.getVillage_Id());
//        request.addProperty("_Urban_AreaID", data.getSahariNikayeId());
//        request.addProperty("_WardID", data.getWard_Id());
//        request.addProperty("_Latitude", data.getLatitude());
//        request.addProperty("_Longitude", data.getLongitude());
//        request.addProperty("_Work_Structure_Type", data.getWork_Structure_Type());
//        request.addProperty("_Work_StructureTypeName", data.getWork_Structure_Type_Other_Name());
//
//        request.addProperty("_Measurement_Of_Structure", data.getMeasurement_Of_Structure());
//        request.addProperty("_Estimated_Amount", data.getEstimated_Amount());
//        request.addProperty("_Administrative_Approval_Date", data.getAdministrative_Approval_Date());
//        request.addProperty("_Scheme_Code", data.getScheme_Code());
//
//        request.addProperty("_Remarks", data.getRemarks());
//        request.addProperty("_CreatedBy", data.getCreatedBy());
//        request.addProperty("_CreatedDate", data.getCreatedDate());
//        request.addProperty("_App_Version", data.getApp_Version());
//        request.addProperty("_Photo1", data.getPhoto1());
//        request.addProperty("_Photo2", data.getPhoto2());

        request.addProperty("_PhaseType", phaseType);
        request.addProperty("_Inspectionid", data.getId());
        request.addProperty("_App_Version", data.getApp_Version());

        request.addProperty("_Khaata_Kheshara_Number", data.getKhaata_Kheshara_Number());
        request.addProperty("_Rakba", data.getRakba());
        request.addProperty("_LandOwnerMob", data.getLandOwnerMob());
        request.addProperty("_LandOwnerName", data.getLandOwnerName());
        request.addProperty("_FencingLatitude1", data.getFencingLatitude1());
        request.addProperty("_FencingLatitude2", data.getFencingLatitude2());
        request.addProperty("_FencingLatitude3", data.getFencingLatitude3());
        request.addProperty("_FencingLatitude4", data.getFencingLatitude4());
        request.addProperty("_FencingLongitude1", data.getFencingLongitude1());
        request.addProperty("_FencingLongitude2", data.getFencingLongitude2());
        request.addProperty("_FencingLongitude3", data.getFencingLongitude3());
        request.addProperty("_FencingLongitude4", data.getFencingLongitude4());
        request.addProperty("_IsLandEnterDt", data.getIsLandEnterDt());
        request.addProperty("_ConsumerNo", data.getConsumerNo());
        request.addProperty("_ConsumrBill", data.getConsumrBill());
        request.addProperty("_TreeType", data.getTreeType());
        request.addProperty("_TotalTree", data.getTotalTree());

        switch (phaseType){
            case 1:
                request.addProperty("_IsPhase1Inspected", data.getIsPhase1Inspected());
                request.addProperty("_IsPhase1InspBy", data.getIsPhase1InspBy());
                request.addProperty("_IsPhase1InspDate", data.getIsPhase1InspDate());
                request.addProperty("_IsPhase1InspPhoto1", data.getIsPhase1InspPhoto1());
                request.addProperty("_IsPhase1InspPhoto2", data.getIsPhase1InspPhoto2());
                request.addProperty("_IsPhase1InspLatitude", data.getIsPhase1InspLatitude());
                request.addProperty("_IsPhase1InspLongitude", data.getIsPhase1InspLongitude());
                request.addProperty("_IsPhase1InspRemarks", data.getIsPhase1InspRemarks());
                break;
            case 2:
                request.addProperty("_IsPhase2Inspected", data.getIsPhase2Inspected());
                request.addProperty("_IsPhase2InspBy", data.getIsPhase2InspBy());
                request.addProperty("_IsPhase2InspDate", data.getIsPhase2InspDate());
                request.addProperty("_IsPhase2InspPhoto1", data.getIsPhase2InspPhoto1());
                request.addProperty("_IsPhase2InspPhoto2", data.getIsPhase2InspPhoto2());
                request.addProperty("_IsPhase2InspLatitude", data.getIsPhase2InspLatitude());
                request.addProperty("_IsPhase2InspLongitude", data.getIsPhase2InspLongitude());
                request.addProperty("_IsPhase2InspRemarks", data.getIsPhase2InspRemarks());
                break;
            case 3:
                request.addProperty("_IsPhase3Inspected", data.getIsPhase3Inspected());
                request.addProperty("_IsPhase3InspBy", data.getIsPhase3InspBy());
                request.addProperty("_IsPhase3InspDate", data.getIsPhase3InspDate());
                request.addProperty("_IsPhase3InspPhoto1", data.getIsPhase3InspPhoto1());
                request.addProperty("_IsPhase3InspPhoto2", data.getIsPhase3InspPhoto2());
                request.addProperty("_IsPhase3InspLatitude", data.getIsPhase3InspLatitude());
                request.addProperty("_IsPhase3InspLongitude", data.getIsPhase3InspLongitude());
                request.addProperty("_IsPhase3InspRemarks", data.getIsPhase3InspRemarks());
                break;
        }

        if(data.getIsPhase3Inspected() != null && data.getIsPhase3Inspected().equals("Y")){
            request.addProperty("_IsFinalInspected", "Y");
        }

        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.implicitTypes = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UPLOADOTHERSCHEMEADATA,
                    envelope);
            // res2 = (SoapObject) envelope.getResponse();
            rest = envelope.getResponse().toString();

            // rest=res2.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return rest;
    }

    public static UserDetails Login(String User_ID, String Pwd) {
        try {
            SoapObject res1;
            res1=getServerData(AUTHENTICATE_METHOD, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            if (res1 != null) {
                return new UserDetails(res1);
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static UserDetails GrievanceLogin(String User_ID, String Pwd) {
        try {
            SoapObject res1;
            res1=getServerData(AUTHENTICATE_PUBLIC, UserDetails.getUserClass(),"UserID","Password",User_ID,Pwd);
            if (res1 != null) {
                return new UserDetails(res1,"grievance");
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static ArrayList<PondEncroachmentEntity> getPondLakeWellEncrhmntData(String blockId, String type) {

        SoapObject res1;
        res1=getServerData(type == "pond" ? PONDLAKEENCRCHMNTDATA : WELLNCRCHMNTDATA, PondEncroachmentEntity.PondEncroachmentEntity_CLASS,"blockid",blockId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PondEncroachmentEntity> fieldList = new ArrayList<PondEncroachmentEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PondEncroachmentEntity pondData= new PondEncroachmentEntity(final_object);
                    fieldList.add(pondData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PondInspectionEntity> getPondDataMWRDistrictWise(String districtCode, String type) {

        SoapObject res1;
        res1=getServerData(PONDDATAMWRD, PondInspectionEntity.getPondInspectionInfo_CLASS(),"DistCode","Type", districtCode,type);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PondInspectionEntity> fieldList = new ArrayList<PondInspectionEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PondInspectionEntity pondData= new PondInspectionEntity(final_object);
                    fieldList.add(pondData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PondEntity> getPondLakeWellData(String blockId, String type, String structureId) {

        SoapObject res1;
        if(type.equals("pond")){
            res1=getServerData(PONDLAKEDATA, PondEntity.PondInfo_CLASS,"blockid",blockId);
        }else{
            res1=getServerData(WELLDATA, PondEntity.PondInfo_CLASS,"blockid", "StrId",blockId, structureId);
        }

        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PondEntity> fieldList = new ArrayList<PondEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PondEntity pondData= new PondEntity(final_object);
                    fieldList.add(pondData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PondEntity> getPondLakeWellDataDistrictWise(String districtCode, String type, String structureId) {

        SoapObject res1;
        if(type.equals("pond")){
            res1=getServerData(PONDLAKEDATADISTRICTWISE, PondEntity.PondInfo_CLASS,"DistrictID",districtCode);
        }else{
            res1=getServerData(WELLDATADISTRCITWISE, PondEntity.PondInfo_CLASS,"DistrictID", "StrId",districtCode, structureId);
        }

        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PondEntity> fieldList = new ArrayList<PondEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PondEntity pondData= new PondEntity(final_object);
                    fieldList.add(pondData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PondEncroachmentEntity> getEncroachmentReport(String blockId, String type, String userID) {

        SoapObject res1;
        res1=getServerData(GETMARKENCROACHMENTPOND, PondEncroachmentEntity.PondEncroachmentEntity_CLASS, "userId", "TypePond",userID, type.toUpperCase());
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PondEncroachmentEntity> fieldList = new ArrayList<PondEncroachmentEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PondEncroachmentEntity pondData= new PondEncroachmentEntity(final_object);
                    fieldList.add(pondData);
                }
            } else
                return fieldList;
        }
        return fieldList;
    }

    public static ArrayList<PondWellReportEntity> getPondLakeWellReport(String strId, String type, String userID) {

        SoapObject res1;
        if(type == "pond"){
            res1=getServerData(PONDINSPECTIONLIST, PondWellReportEntity.PondWellReportCLASS, "userid" ,userID);
        }else{
            res1=getServerData(WELLINSPECTIONLIST, PondWellReportEntity.PondWellReportCLASS, "userid", "StrID",userID, strId);
        }

        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PondWellReportEntity> fieldList = new ArrayList<PondWellReportEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PondWellReportEntity pondData= new PondWellReportEntity(final_object);
                    fieldList.add(pondData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<ward> getWardListData(String BlockCode) {


        SoapObject res1;
        res1 = getServerData(GETWARDLIST, ward.ward_CLASS, "PANCHAYATCODE", BlockCode);
        int TotalProperty = 0;
        if (res1 != null) TotalProperty = res1.getPropertyCount();

        ArrayList<ward> fieldList = new ArrayList<ward>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ward wardInfo = new ward(final_object);
                    fieldList.add(wardInfo);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<ManregaSchemeDetail> getManregaInspectionData(String blockId) {

        SoapObject res1;
        res1=getServerData(GETMANREGADEPTINSPLIST, ManregaSchemeDetail.ManregaSchemeDetail_CLASS,"BlockCode",blockId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<ManregaSchemeDetail> fieldList = new ArrayList<ManregaSchemeDetail>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ManregaSchemeDetail info= new ManregaSchemeDetail(final_object);
                    fieldList.add(info);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<StructureRemarkEntity> getStructureRemarkData(String distId, String blockId, String strId) {

        SoapObject res1;
        res1=getServerData(GETREMARKUPDATELIST, StructureRemarkEntity.StructureRemarkEntity_CLASS,"DistrictID","blockid","StrId", distId,blockId, strId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<StructureRemarkEntity> fieldList = new ArrayList();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    StructureRemarkEntity info= new StructureRemarkEntity(final_object);
                    fieldList.add(info);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<NurseryEntity> getNursuryData(String districtCode, String type) {

        SoapObject res1;
        res1=getServerData(type.equals(AppConstant.NURSURY) ? GETNURSURYLIST : GETBUILDINGLIST, SanrachnaDataEntity.getSanrachnaDataEntity_CLASS(),"Distcode",districtCode);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<NurseryEntity> fieldList = new ArrayList<NurseryEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    NurseryEntity info= new NurseryEntity(final_object, type);
                    fieldList.add(info);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<SanrachnaDataEntity> getSanrachnaProgessData(String blockId) {

        SoapObject res1;
        res1=getServerData(GETPONDINSPECTCTLIST, SanrachnaDataEntity.getSanrachnaDataEntity_CLASS(),"BlockCode",blockId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SanrachnaDataEntity> fieldList = new ArrayList<SanrachnaDataEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SanrachnaDataEntity info= new SanrachnaDataEntity(final_object);
                    fieldList.add(info);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<OtherDeptInsptEntity> getOtherDeptInspectionData(String blockId) {

        SoapObject res1;
        res1=getServerData(GETOTHERDEPTINSPLIST, OtherDeptInsptEntity.OtherDeptDetail_CLASS,"BlockCode",blockId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<OtherDeptInsptEntity> fieldList = new ArrayList<OtherDeptInsptEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    OtherDeptInsptEntity info= new OtherDeptInsptEntity(final_object);
                    fieldList.add(info);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<SanrachnaTypeEntity> getSanrachnaTypeData() {

        SoapObject res1;
        res1=getServerData(GETSANRACHNATYPELIST, SanrachnaTypeEntity.SanrachnaType_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SanrachnaTypeEntity> fieldList = new ArrayList<SanrachnaTypeEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SanrachnaTypeEntity villageData= new SanrachnaTypeEntity(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<StructureType> getStructureTypeData() {

        SoapObject res1;
        res1=getServerData(GETWORKSTRUCTURETYPELIST, StructureType.StructureType_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<StructureType> fieldList = new ArrayList<StructureType>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    StructureType villageData= new StructureType(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PublicNurseryBuildingEntity> getNurseryBuildingListLocationWise(String latitude, String longitude, String type) {
        //String lat = "25.9969262", lng = "85.58580858";
        SoapObject res1;
        res1=getServerData(type.equals(AppConstant.BUILDING) ? GETBUILDINGLISTLOCATIONWISE: GETNURSERYLISTLOCATIONWISE, NurseryEntity.NurseryEntity_CLASS, "Latitude","Longitude",latitude, longitude);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PublicNurseryBuildingEntity> fieldList = new ArrayList();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PublicNurseryBuildingEntity villageData= new PublicNurseryBuildingEntity(final_object, type);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<StructureDetailPublic> getStrucutureListLocationWise(String latitude, String longitude) {

        SoapObject res1;
        res1=getServerData(GETSTRUCTURELISTLOCATIONWISE, StructureDetailPublic.StructureDetailPublic_CLASS, "Latitude","Longitude",latitude, longitude);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<StructureDetailPublic> fieldList = new ArrayList<StructureDetailPublic>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    StructureDetailPublic villageData= new StructureDetailPublic(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<StructureDetailPublic> getStrucutureListForPublic(String distCode, String blockCode, String pancode, String strId) {

        SoapObject res1;
        res1=getServerData(GETSTRUCTURELIST, StructureDetailPublic.StructureDetailPublic_CLASS, "Distcode","BlockCode","panchayatcode","StrID",distCode, blockCode,pancode,strId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<StructureDetailPublic> fieldList = new ArrayList<StructureDetailPublic>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    StructureDetailPublic villageData= new StructureDetailPublic(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<SchemeDetailPublic> getSchemeListLocationWise(String latitude, String longitude) {

        SoapObject res1;
        res1=getServerData(GETSSCHEMELISTLOCATIONWISE, SchemeDetailPublic.SchemeDetailPublic_CLASS, "Phase1Lat","Phase1Long",latitude, longitude);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SchemeDetailPublic> fieldList = new ArrayList<SchemeDetailPublic>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SchemeDetailPublic villageData= new SchemeDetailPublic(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<SchemeDetailPublic> getSchemeListForPublic(String distCode, String blockCode, String panCode, String awyabid, String yojnaStatus) {

        SoapObject res1;
        res1=getServerData(GETSSCHEMELIST, SchemeDetailPublic.SchemeDetailPublic_CLASS, "Distcode","Blockcode","panchayatcode","AwayId","YojnaStuts",distCode, blockCode,panCode,awyabid,yojnaStatus);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<SchemeDetailPublic> fieldList = new ArrayList<SchemeDetailPublic>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    SchemeDetailPublic villageData= new SchemeDetailPublic(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<VillageListEntity> getVillageListData(String BlockCode) {

        SoapObject res1;
        res1=getServerData(GETVILLAGELIST, VillageListEntity.VillageList_CLASS,"blockCode",BlockCode);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<VillageListEntity> fieldList = new ArrayList<VillageListEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    VillageListEntity villageData= new VillageListEntity(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<DepartmentEntity> getExecDepartmentData() {

        SoapObject res1;
        res1=getServerData(GETEXECDEPTLIST, DepartmentEntity.DepartmentEntity_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<DepartmentEntity> fieldList = new ArrayList<DepartmentEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    DepartmentEntity villageData= new DepartmentEntity(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<DepartmentDetail> getDepartmentListData() {

        SoapObject res1;
        res1=getServerData(GETDEPARTMENTLIST, DepartmentDetail.DepartmentDetail_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<DepartmentDetail> fieldList = new ArrayList<DepartmentDetail>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    DepartmentDetail data= new DepartmentDetail(final_object);
                    fieldList.add(data);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<TreesDetail> getTreeDetailListData() {

        SoapObject res1;
        res1=getServerData(GETTRESSTYPELIST, TreesDetail.getTreesDetail_CLASS());
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<TreesDetail> fieldList = new ArrayList<TreesDetail>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    TreesDetail data= new TreesDetail(final_object);
                    fieldList.add(data);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<Abyab> getAbyabListData() {

        SoapObject res1;
        res1=getServerData(GETAWAYABLIST, Abyab.kriwayan_CLASS);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<Abyab> fieldList = new ArrayList<Abyab>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    Abyab data= new Abyab(final_object);
                    fieldList.add(data);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<PanchayatEntity> getPanchayatList(String DistCode, String BlockCode) {

        SoapObject res1;
        res1=getServerData(GETPANCHAYATLIST, PanchayatEntity.PanchayatEntity_CLASS,"DistCode", "BlockCode", DistCode, BlockCode);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PanchayatEntity> fieldList = new ArrayList<PanchayatEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PanchayatEntity villageData= new PanchayatEntity(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }
    public static ArrayList<PanchayatData> getPanchayatList_New(String DistCode, String BlockCode) {

        SoapObject res1;
        res1=getServerData(GETPANCHAYATLIST, PanchayatData.PanchayatData_CLASS,"DistCode", "BlockCode", DistCode, BlockCode);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<PanchayatData> fieldList = new ArrayList<PanchayatData>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    PanchayatData villageData= new PanchayatData(final_object);
                    fieldList.add(villageData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }


    public static SoapObject getServerData(String methodName,Class bindClass)
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }

    public static SoapObject getServerData(String methodName,Class bindClass, SoapObject request)
    {
        SoapObject res1;
        try {
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }


    public static SoapObject getServerData(String methodName,Class bindClass,String param,String value )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param,value);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }



    public static SoapObject getServerData(String methodName,Class bindClass,String param1,String param2,String value1,String value2 )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }

    public static SoapObject getServerData(String methodName,Class bindClass,String param1,String param2,String param3,String value1,String value2,String value3 )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            request.addProperty(param3,value3);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }
    public static SoapObject getServerData(String methodName,Class bindClass,String param1,String param2,String param3,String param4,String value1,String value2,String value3,String value4 )
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            request.addProperty(param3,value3);
            request.addProperty(param4,value4);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }
    public static SoapObject getServerData(String methodName, Class bindClass,String param1,String param2,String param3,String param4, String param5,String value1, String value2, String value3, String value4, String value5)
    {
        SoapObject res1;
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,methodName);
            request.addProperty(param1,value1);
            request.addProperty(param2,value2);
            request.addProperty(param3,value3);
            request.addProperty(param4,value4);
            request.addProperty(param5,value5);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,bindClass.getSimpleName(),bindClass);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + methodName,envelope);
            res1 = (SoapObject) envelope.getResponse();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return res1;
    }


    public static DashboardEntity getBen_Details() {

        SoapObject request = new SoapObject(SERVICENAMESPACE, GETDashboardData);


        DashboardEntity userDetails;
        SoapObject res1;
        try {

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE, DashboardEntity.DASHBOARD_CLASS.getSimpleName(), DashboardEntity.DASHBOARD_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + GETDashboardData, envelope);

            res1 = (SoapObject) envelope.getResponse();

            int TotalProperty = res1.getPropertyCount();

            userDetails = new DashboardEntity(res1);

        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return userDetails;

    }

//
//    public static ArrayList<DashboardEntity> getBen_Details() {
//
//
//        SoapObject request = new SoapObject(SERVICENAMESPACE, GETDashboardData);
//
//
//
//        SoapObject res1;
//        try {
//
//            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
//            envelope.dotNet = true;
//            envelope.setOutputSoapObject(request);
//            envelope.addMapping(SERVICENAMESPACE,
//                    DashboardEntity.DASHBOARD_CLASS.getSimpleName(), DashboardEntity.DASHBOARD_CLASS);
//            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1,60000);
//            androidHttpTransport.call(SERVICENAMESPACE + GETDashboardData, envelope);
//
//            res1 = (SoapObject) envelope.getResponse();
//
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//            return null;
//        }
//        int TotalProperty = res1.getPropertyCount();
//        ArrayList<DashboardEntity> pvmArrayList = new ArrayList<DashboardEntity>();
//        if(TotalProperty>0) {
//            for (int ii = 0; ii < TotalProperty; ii++) {
//                if (res1.getProperty(ii) != null) {
//                    Object property = res1.getProperty(ii);
//                    if (property instanceof SoapObject) {
//                        SoapObject final_object = (SoapObject) property;
//                        DashboardEntity state = new DashboardEntity(final_object);
//                        pvmArrayList.add(state);
//                    }
//                } else
//                    return pvmArrayList;
//            }
//        }
//
//
//        return pvmArrayList;
//    }
//


    public static String UploadGrievance(GrievanceEntryDetail data, String userid, String date)
    {
        try
        {
            SoapObject request = new SoapObject(SERVICENAMESPACE,UploadGrievance);

//            request.addProperty("_mobile",data.getm());
//            request.addProperty("_DistCode",data.getDistCode());
//            request.addProperty("_BlockCode",data.getBlockCode());
//            request.addProperty("_panchayatCode","");
//            request.addProperty("_WardCode","");
//            request.addProperty("_FeedbackPhoto1",data.getf());
//            request.addProperty("_FeedbackPhoto2",data.getNameInPass());
//            request.addProperty("_FeedbackPhoto3",data.getAadharNumber());//_FYearID
//            request.addProperty("_FeedbackPhoto4",data.getWard());
//            request.addProperty("_ComplainRemark",data.getBen_Mobile());
//            request.addProperty("_Latlong",data.getYearOfBorth());
//            request.addProperty("_Longitude",data.getPhoto1());
//            request.addProperty("_EntryDate",data.getPhoto2());
//            request.addProperty("_GrievanceRelated",data.getPhoto3());
//            request.addProperty("_GrievanceName",data.getPhoto4());


            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,GrievanceEntryDetail.GRIEVANCE_CLASS.getSimpleName(),GrievanceEntryDetail.GRIEVANCE_CLASS);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + UploadGrievance,envelope);
            Object result = envelope.getResponse();
            if (result != null)
            {
                Log.d("efghegehg", result.toString());

                return result.toString();
            }
            else
                return null;

        }
        catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }

    }

    public static final String Get_Password = "ForgrtPassword";
    public static final String Change_Password = "ChangePassword";


    public static String ResetPassword(String mob) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,Get_Password);


            request.addProperty("_userid",mob);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    UserDetails.getUserClass().getSimpleName(),
                    UserDetails.getUserClass());
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + Get_Password,
                    envelope);

            Object result = envelope.getResponse();

            //return "1";
            if (result != null) {
                // Log.d("", result.toString());
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    public static String ChangePassword(String oldpas,String newpassword,String mobile) {
        try {
            SoapObject request = new SoapObject(SERVICENAMESPACE,Change_Password);


            request.addProperty("_oldpass",oldpas);
            request.addProperty("_newpass",newpassword);
            request.addProperty("_Mobile",mobile);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            envelope.addMapping(SERVICENAMESPACE,
                    UserDetails.getUserClass().getSimpleName(),
                    UserDetails.getUserClass());
            HttpTransportSE androidHttpTransport = new HttpTransportSE(
                    SERVICEURL1);
            androidHttpTransport.call(SERVICENAMESPACE + Change_Password,
                    envelope);

            Object result = envelope.getResponse();

            //return "1";
            if (result != null)
            {
                // Log.d("", result.toString());
                return result.toString();
            } else
                return null;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }


    private static final String GETSCHEMEINSPECTIONLISTDEPTWISE = "getSchemeInspectionListDeptWise";
    private static final String GETNURSURYReportLIST = "getNurseryInspectionListNew";
    private static final String GETBUILDINGReportLIST = "getBulldingInspectionListNew";
    private static final String YojnaINSPECTIONLIST = "getSchemeInspectionListNew";

    public static ArrayList<ManregaSchemeDetail> getSchemeInspectionListDeptWise(String DeptID, String Distcode) {

        SoapObject res1;
        res1=getServerData(GETSCHEMEINSPECTIONLISTDEPTWISE, ManregaSchemeDetail.ManregaSchemeDetail_CLASS,"DeptID","Distcode",DeptID,Distcode);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();
        //if(res1!=null) TotalProperty= res1.getPint TotalProperty=0;ropertyCount();

        ArrayList<ManregaSchemeDetail> fieldList = new ArrayList<ManregaSchemeDetail>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    ManregaSchemeDetail info= new ManregaSchemeDetail(final_object);
                    fieldList.add(info);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<NurseryReportEntity> getNursuryBuildinfReportData(String districtCode, String type, String userid){

        SoapObject res1;
        res1=getServerData(type.equals(AppConstant.NURSURY) ? GETNURSURYReportLIST : GETBUILDINGReportLIST, NurseryEntity.NurseryEntity_CLASS,"userid",userid);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();
        //if(res1!=null) TotalProperty= res1.getPint TotalProperty=0;ropertyCount();

        ArrayList<NurseryReportEntity> fieldList = new ArrayList<NurseryReportEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    NurseryReportEntity info= new NurseryReportEntity(final_object, type);
                    fieldList.add(info);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }

    public static ArrayList<YojnaReportEntity> getSchemeReport(String blockId, String userID) {

        SoapObject res1;
        res1=getServerData(YojnaINSPECTIONLIST, YojnaReportEntity.YojnaReportEntityCLASS, "UserId", "BlockCode",userID, blockId);
        int TotalProperty=0;
        if(res1!=null) TotalProperty= res1.getPropertyCount();

        ArrayList<YojnaReportEntity> fieldList = new ArrayList<YojnaReportEntity>();

        for (int i = 0; i < TotalProperty; i++) {
            if (res1.getProperty(i) != null) {
                Object property = res1.getProperty(i);
                if (property instanceof SoapObject) {
                    SoapObject final_object = (SoapObject) property;
                    YojnaReportEntity pondData= new YojnaReportEntity(final_object);
                    fieldList.add(pondData);
                }
            } else
                return fieldList;
        }

        return fieldList;
    }
}
