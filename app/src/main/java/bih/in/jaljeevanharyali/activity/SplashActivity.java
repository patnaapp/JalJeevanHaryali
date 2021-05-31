package bih.in.jaljeevanharyali.activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import bih.in.jaljeevanharyali.MarshmallowPermission;
import bih.in.jaljeevanharyali.R;
import bih.in.jaljeevanharyali.activity.grievance.GrievanceHomeActivity;
import bih.in.jaljeevanharyali.activity.userCo.DashboardCoActivity;
import bih.in.jaljeevanharyali.database.DataBaseHelper;
import bih.in.jaljeevanharyali.entity.Versioninfo;
import bih.in.jaljeevanharyali.utility.CommonPref;
import bih.in.jaljeevanharyali.utility.GlobalVariables;
import bih.in.jaljeevanharyali.utility.Utiilties;
import bih.in.jaljeevanharyali.web_services.WebServiceHelper;


public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    DataBaseHelper databaseHelper;
    MarshmallowPermission permission;
    public static SharedPreferences prefs;
    String imei = "", version = null;
    String username = "";
    String password = "";
    //DataBaseHelper dataBaseHelper;
    Context ctx;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ctx = this;

        //disableSSLCertificateChecking();

        //Database Opening
        databaseHelper = new DataBaseHelper(SplashActivity.this);
        try {
            databaseHelper.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            databaseHelper.openDataBase();
            createTable();
            modifyTable();
        } catch (SQLException sqle) {
            throw sqle;
        }

    }

    public void createTable(){
        db = databaseHelper.getReadableDatabase();
        try{
            String pondTable = "CREATE TABLE IF NOT EXISTS CoPondEncroachmentReport (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, InspectionID TEXT, DistID TEXT, DistName TEXT, " +
                    "BlockID TEXT, BlockName TEXT, PanchayatID TEXT, PanchayatName TEXT, RajswaThanaNumber TEXT, " +
                    "Khaata_Kheshara_Number TEXT, VillageID TEXT, VILLNAME TEXT, Latitude TEXT, Longitude TEXT, " +
                    "Status_Of_Encroachment TEXT, Type_Of_Encroachment TEXT, Verified_By TEXT, IsInspected TEXT, " +
                    "Verified_Date TEXT, Ench_Verified_By TEXT, EStatus TEXT, isUpdated TEXT, EnchrochmentStartDate TEXT, EnchrochmentEndDate TEXT, NoticeDate TEXT, NoticeNo TEXT, AppVersion TEXT, UploadType Text)";
            db.execSQL(pondTable);
            Log.e("Table Created", "tableName -CoPondEncroachmentReport");
        }catch (Exception e){
            Log.e("Creation Failed","CoPondEncroachmentReport");
        }


        try{
            String wellTable = "CREATE TABLE IF NOT EXISTS CoWellEncroachmentReport (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, InspectionID TEXT, DistID TEXT, DistName TEXT, " +
                    "BlockID TEXT, BlockName TEXT, PanchayatID TEXT, PanchayatName TEXT, RajswaThanaNumber TEXT, " +
                    "Khaata_Kheshara_Number TEXT, VillageID TEXT, VILLNAME TEXT, Latitude TEXT, Longitude TEXT, " +
                    "Status_Of_Encroachment TEXT, Type_Of_Encroachment TEXT, Verified_By TEXT, IsInspected TEXT," +
                    " Verified_Date TEXT, Ench_Verified_By TEXT, EStatus TEXT, isUpdated TEXT, EnchrochmentStartDate TEXT, EnchrochmentEndDate TEXT, NoticeDate TEXT, NoticeNo TEXT, AppVersion TEXT, UploadType Text)";
            db.execSQL(wellTable);
            Log.e("Table Created", "tableName -CoWellEncroachmentReport");
        }catch (Exception e){
            Log.e("Creation Failed","CoWellEncroachmentReport");
        }

        try{
            String wellTable = "CREATE TABLE IF NOT EXISTS TreesDetail (id INTEGER PRIMARY KEY AUTOINCREMENT, TreeId TEXT, TreeNameEng TEXT, TreeNameHnd TEXT)";
            db.execSQL(wellTable);
            Log.e("Table Created", "tableName - TreesDetail");
        }catch (Exception e){
            Log.e("Creation Failed","TreesDetail");
        }

        try{
            String wellTable = "CREATE TABLE IF NOT EXISTS StructureType (id INTEGER PRIMARY KEY AUTOINCREMENT, StrID TEXT, StrName TEXT, StrCode TEXT)";
            db.execSQL(wellTable);
            Log.e("Table Created", "tableName - StructureType");
        }catch (Exception e){
            Log.e("Creation Failed","StructureType");
        }

        try{
            String wellTable = "CREATE TABLE IF NOT EXISTS AbyabPublic (id INTEGER PRIMARY KEY AUTOINCREMENT, abyabId TEXT, abyabName TEXT)";
            db.execSQL(wellTable);
            Log.e("Table Created", "tableName - AbyabPublic");
        }catch (Exception e){
            Log.e("Creation Failed","AbyabPublic");
        }

        try{
            String wellTable = "CREATE TABLE IF NOT EXISTS DepartmentList (id INTEGER PRIMARY KEY AUTOINCREMENT, DeptId TEXT, DeptName TEXT, DeptNameHn TEXT, isActive TEXT)";
            db.execSQL(wellTable);
            Log.e("Table Created", "tableName - AbyabPublic");
        }catch (Exception e){
            Log.e("Creation Failed","AbyabPublic");
        }

        try{
            String wellTable = "CREATE TABLE IF NOT EXISTS NursuryBuildingDetail (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, InspectionID TEXT, DistrictCode TEXT, DistName TEXT, " +
                    "BlockCode TEXT, BlockName TEXT, PanchayatCode TEXT, PanchayatName TEXT, VILLNAME TEXT, " +
                    "VILLCODE TEXT, WARDCODE TEXT, WARDNAME TEXT, Latitude TEXT, Longitude TEXT, Photo BLOB, " +
                    "Urban_AreaID TEXT, NurseryName TEXT, TotalArea TEXT," +
                    " TotalTree TEXT, BuildingType TEXT, BuildingName TEXT, Execution_DeptID TEXT, Execution_DeptName TEXT, OtherDept TEXT,"+
                    " ConsumerNo TEXT, BCode TEXT, PreAmount TEXT, AppVersion TEXT, StrType Text, EntryDate TEXT, EntryBy Text, IsUpdate TEXT, Remark Text, Mobile Text, DeptId Text)";

            db.execSQL(wellTable);
            Log.e("Table Created", "tableName -NursuryBuildingDetail");
        }catch (Exception e){
            Log.e("Creation Failed","NursuryBuildingDetail");
        }

        try{
            String wellTable = "CREATE TABLE IF NOT EXISTS StructureRemarkUpdateDetail (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, InspectionID TEXT, DistrictCode TEXT, DistName TEXT, " +
                    "BlockCode TEXT, BlockName TEXT, PanchayatCode TEXT, PanchayatName TEXT, VILLNAME TEXT, " +
                    "VILLCODE TEXT, WARDCODE TEXT, WARDNAME TEXT, Latitude TEXT, Longitude TEXT, Photo BLOB, " +
                    "Urban_AreaID TEXT, RajswaThanaNumber TEXT, Khaata_Kheshara_Number TEXT," +
                    " StrID TEXT, TypesOfSarchna TEXT, Remarks TEXT, DeptId TEXT, DeptName TEXT, IsStrAvailable TEXT, IsWorking TEXT, newRemark TEXT,"+
                    " AppVersion TEXT, EntryDate TEXT, EntryBy TEXT, IsUpdate TEXT, Mobile TEXT, StrType TEXT)";

            db.execSQL(wellTable);
            Log.e("Table Created", "tableName -NursuryBuildingDetail");
        }catch (Exception e){
            Log.e("Creation Failed","NursuryBuildingDetail");
        }
    }

    public void modifyTable(){

        if(isColumnExists("Ward", "AreaType") == false){
            AlterTable("Ward", "AreaType");
        }

        if(isColumnExists("PondInspectionDetail", "AbyabId") == false){
            AlterTable("PondInspectionDetail", "AbyabId");
        }

        if(isColumnExists("PondInspectionDetail", "AbyabName") == false){
            AlterTable("PondInspectionDetail", "AbyabName");
        }

        if(isColumnExists("PondInspectionDetail", "DeptId") == false){
            AlterTable("PondInspectionDetail", "DeptId");
        }

        if(isColumnExists("UserDetail", "PanchayatName") == false){
            AlterTable("UserDetail", "PanchayatName");
        }

        if(isColumnExists("UserDetail", "FatherName") == false){
            AlterTable("UserDetail", "FatherName");
        }

        if(isColumnExists("UserDetail", "Address") == false){
            AlterTable("UserDetail", "Address");
        }

        if(isColumnExists("UserDetail", "DeptId") == false){
            AlterTable("UserDetail", "DeptId");
        }

        if(isColumnExists("WellInspectionDetail", "structureId") == false){
            AlterTable("WellInspectionDetail", "structureId");
        }

        if(isColumnExists("WellInspectionDetail", "functionalStatus") == false){
            AlterTable("WellInspectionDetail", "functionalStatus");
        }

        if(isColumnExists("PondInspectionDetail", "functionalStatus") == false){
            AlterTable("PondInspectionDetail", "functionalStatus");
        }

        if(isColumnExists("PondInspectionDetail", "structureId") == false){
            AlterTable("PondInspectionDetail", "structureId");
        }

        if(isColumnExists("ExecutionDepartment", "ExecId") == false){
            AlterTable("ExecutionDepartment", "ExecId");
        }

        //Department Table

        if(isColumnExists("DepartmentList", "structure") == false){
            AlterTable("DepartmentList", "structure");
        }

        if(isColumnExists("DepartmentList", "scheme") == false){
            AlterTable("DepartmentList", "scheme");
        }

        if(isColumnExists("DepartmentList", "wellChapakal") == false){
            AlterTable("DepartmentList", "wellChapakal");
        }

        if(isColumnExists("DepartmentList", "building") == false){
            AlterTable("DepartmentList", "building");
        }

        if(isColumnExists("DepartmentList", "nursery") == false){
            AlterTable("DepartmentList", "nursery");
        }

        AlterManregTable();
        AlterOtherDeptTable();
    }

    public void AlterOtherDeptTable(){
        db = databaseHelper.getReadableDatabase();
        String tableName = "OtherDept_Of_Rural_Dev_Dept";

        try{
            if(!isColumnExists("UserDetail", "uid")){
                //AlterTable("Menrega_Rural_Dev_Dept", "Work_Structure_Type_Name");
                db.execSQL("ALTER TABLE UserDetail ADD COLUMN uid TEXT");
                Log.e("ALTER Done","UserDetail - uid");
            }

            if(!isColumnExists(tableName, "YojnaType")){
                //AlterTable("Menrega_Rural_Dev_Dept", "Work_Structure_Type_Name");
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN YojnaType TEXT");
                Log.e("ALTER Done",tableName +"- YojnaType");
            }

            if(!isColumnExists(tableName, "VillageName")){
                //AlterTable("Menrega_Rural_Dev_Dept", "Work_Structure_Type_Name");
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN VillageName TEXT");
                Log.e("ALTER Done",tableName +"- VillageName");
            }

            if(!isColumnExists(tableName, "Work_Structure_Type_Name")){
                //AlterTable("Menrega_Rural_Dev_Dept", "Work_Structure_Type_Name");
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Work_Structure_Type_Name TEXT");
                Log.e("ALTER Done",tableName +"- Work_Structure_Type_Name");
            }

            if(!isColumnExists(tableName, "Other_Name")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Other_Name TEXT");
                Log.e("ALTER Done",tableName +"-"+ "Other_Name");
            }

            if(!isColumnExists(tableName, "Work_Structure_Type_Other_Name")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Work_Structure_Type_Other_Name TEXT");
                Log.e("ALTER Done",tableName +"-"+ "Work_Structure_Type_Other_Name");
            }

            if(!isColumnExists(tableName, "VillageID")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN VillageID TEXT");
                Log.e("ALTER Done",tableName +"-"+ "VillageID");
            }

            if(!isColumnExists(tableName, "isUpdated")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN isUpdated TEXT");
                Log.e("ALTER Done",tableName +" - "+ "isUpdated");
            }

            if(!isColumnExists(tableName, "Types_OfSarchnaId")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Types_OfSarchnaId TEXT");
                Log.e("ALTER Done",tableName +" - "+ "Types_OfSarchnaId");
            }

            if(!isColumnExists(tableName, "Types_OfSarchnaName")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Types_OfSarchnaName TEXT");
                Log.e("ALTER Done",tableName +" - "+ "Types_OfSarchnaName");
            }

            if(!isColumnExists(tableName, "IsPhase1Inspected")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1Inspected TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1Inspected");
            }

            if(!isColumnExists(tableName, "IsPhase1InspBy")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspBy TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspBy");
            }

            if(!isColumnExists(tableName, "IsPhase1InspDate")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspDate TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspDate");
            }

            if(!isColumnExists(tableName, "IsPhase1InspPhoto1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspPhoto1 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspPhoto1");
            }

            if(!isColumnExists(tableName, "IsPhase1InspPhoto2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspPhoto2 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspPhoto2");
            }

            if(!isColumnExists(tableName, "IsPhase1InspLatitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspLatitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspLatitude");
            }

            if(!isColumnExists(tableName, "IsPhase1InspLongitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspLongitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspLongitude");
            }

            if(!isColumnExists(tableName, "IsPhase1InspRemarks")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspRemarks TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspRemarks");
            }

            if(!isColumnExists(tableName, "IsPhase2Inspected")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2Inspected TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2Inspected");
            }

            if(!isColumnExists(tableName, "IsPhase2InspBy")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspBy TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspBy");
            }

            if(!isColumnExists(tableName, "IsPhase2InspDate")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspDate TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspDate");
            }
            if(!isColumnExists(tableName, "IsPhase2InspPhoto1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspPhoto1 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspPhoto1");
            }

            if(!isColumnExists(tableName, "IsPhase2InspPhoto2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspPhoto2 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspPhoto2");
            }

            if(!isColumnExists(tableName, "IsPhase2InspLatitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspLatitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspLatitude");
            }

            if(!isColumnExists(tableName, "IsPhase2InspLongitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspLongitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspLongitude");
            }

            if(!isColumnExists(tableName, "IsPhase2InspRemarks")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspRemarks TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspRemarks");
            }

            if(!isColumnExists(tableName, "IsPhase3Inspected")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3Inspected TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3Inspected");
            }

            if(!isColumnExists(tableName, "IsPhase3InspBy")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspBy TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspBy");
            }

            if(!isColumnExists(tableName, "IsPhase3InspDate")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspDate TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspDate");
            }

            if(!isColumnExists(tableName, "IsPhase3InspPhoto1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspPhoto1 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspPhoto1");
            }

            if(!isColumnExists(tableName, "IsPhase3InspPhoto2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspPhoto2 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspPhoto2");
            }

            if(!isColumnExists(tableName, "IsPhase3InspLatitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspLatitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspLatitude");
            }

            if(!isColumnExists(tableName, "IsPhase3InspLongitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspLongitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspLongitude");
            }

            if(!isColumnExists(tableName, "IsPhase3InspRemarks")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspRemarks TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspRemarks");
            }

            if(!isColumnExists(tableName, "IsFinalInspected")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsFinalInspected TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsFinalInspected");
            }

            if(!isColumnExists(tableName, "AbyabId")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN AbyabId TEXT");
                Log.e("ALTER Done",tableName +" - "+ "AbyabId");
            }

            if(!isColumnExists(tableName, "Khaata_Kheshara_Number")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Khaata_Kheshara_Number TEXT");
                Log.e("ALTER Done",tableName +" - "+ "Khaata_Kheshara_Number");
            }

            if(!isColumnExists(tableName, "Rakba")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Rakba TEXT");
                Log.e("ALTER Done",tableName +" - "+ "Rakba");
            }

            if(!isColumnExists(tableName, "LandOwnerMob")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN LandOwnerMob TEXT");
                Log.e("ALTER Done",tableName +" - "+ "LandOwnerMob");
            }

            if(!isColumnExists(tableName, "LandOwnerName")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN LandOwnerName TEXT");
                Log.e("ALTER Done",tableName +" - "+ "LandOwnerName");
            }

            if(!isColumnExists(tableName, "FencingLatitude1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLatitude1 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLatitude1");
            }

            if(!isColumnExists(tableName, "FencingLatitude2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLatitude2 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLatitude2");
            }

            if(!isColumnExists(tableName, "FencingLatitude3")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLatitude3 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLatitude3");
            }

            if(!isColumnExists(tableName, "FencingLatitude4")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLatitude4 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLatitude4");
            }

            if(!isColumnExists(tableName, "FencingLongitude1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLongitude1 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLongitude1");
            }

            if(!isColumnExists(tableName, "FencingLongitude2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLongitude2 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLongitude2");
            }

            if(!isColumnExists(tableName, "FencingLongitude3")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLongitude3 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLongitude3");
            }

            if(!isColumnExists(tableName, "FencingLongitude4")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLongitude4 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLongitude4");
            }

            if(!isColumnExists(tableName, "IsLandEnterDt")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsLandEnterDt TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsLandEnterDt");
            }

            if(!isColumnExists(tableName, "ConsumerNo")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN ConsumerNo TEXT");
                Log.e("ALTER Done",tableName +" - "+ "ConsumerNo");
            }

            if(!isColumnExists(tableName, "ConsumrBill")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN ConsumrBill TEXT");
                Log.e("ALTER Done",tableName +" - "+ "ConsumrBill");
            }

            if(!isColumnExists(tableName, "TreeType")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN TreeType TEXT");
                Log.e("ALTER Done",tableName +" - "+ "TreeType");
            }

            if(!isColumnExists(tableName, "TreeTypeName")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN TreeTypeName TEXT");
                Log.e("ALTER Done",tableName +" - "+ "TreeTypeName");
            }

            if(!isColumnExists(tableName, "TotalTree")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN TotalTree TEXT");
                Log.e("ALTER Done",tableName +" - "+ "TotalTree");
            }

        }
        catch (Exception e)
        {
            Log.e("ALTER Failed","Menrega_Rural_Dev_Dept");
        }
    }

    public void AlterManregTable(){
        db = databaseHelper.getReadableDatabase();
        String tableName = "Menrega_Rural_Dev_Dept";

        try{
            if(!isColumnExists(tableName, "YojnaType")){
                //AlterTable("Menrega_Rural_Dev_Dept", "Work_Structure_Type_Name");
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN YojnaType TEXT");
                Log.e("ALTER Done",tableName +"- YojnaType");
            }

            if(!isColumnExists(tableName, "Work_Structure_Type_Name")){
                //AlterTable("Menrega_Rural_Dev_Dept", "Work_Structure_Type_Name");
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Work_Structure_Type_Name TEXT");
                Log.e("ALTER Done",tableName +"- Work_Structure_Type_Name");
            }

            if(!isColumnExists(tableName, "Other_Name")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Other_Name TEXT");
                Log.e("ALTER Done",tableName +"-"+ "Other_Name");
            }

            if(!isColumnExists(tableName, "Work_Structure_Type_Other_Name")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Work_Structure_Type_Other_Name TEXT");
                Log.e("ALTER Done",tableName +"-"+ "Work_Structure_Type_Other_Name");
            }

            if(!isColumnExists(tableName, "VillageID")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN VillageID TEXT");
                Log.e("ALTER Done",tableName +"-"+ "VillageID");
            }

            if(!isColumnExists(tableName, "isUpdated")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN isUpdated TEXT");
                Log.e("ALTER Done",tableName +" - "+ "isUpdated");
            }

            if(!isColumnExists(tableName, "Types_OfSarchnaId")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Types_OfSarchnaId TEXT");
                Log.e("ALTER Done",tableName +" - "+ "Types_OfSarchnaId");
            }

            if(!isColumnExists(tableName, "Types_OfSarchnaName")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Types_OfSarchnaName TEXT");
                Log.e("ALTER Done",tableName +" - "+ "Types_OfSarchnaName");
            }

            if(!isColumnExists(tableName, "IsPhase1Inspected")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1Inspected TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1Inspected");
            }

            if(!isColumnExists(tableName, "IsPhase1InspBy")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspBy TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspBy");
            }

            if(!isColumnExists(tableName, "IsPhase1InspDate")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspDate TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspDate");
            }

            if(!isColumnExists(tableName, "IsPhase1InspPhoto1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspPhoto1 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspPhoto1");
            }

            if(!isColumnExists(tableName, "IsPhase1InspPhoto2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspPhoto2 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspPhoto2");
            }

            if(!isColumnExists(tableName, "IsPhase1InspLatitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspLatitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspLatitude");
            }

            if(!isColumnExists(tableName, "IsPhase1InspLongitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspLongitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspLongitude");
            }

            if(!isColumnExists(tableName, "IsPhase1InspRemarks")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase1InspRemarks TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase1InspRemarks");
            }

            if(!isColumnExists(tableName, "IsPhase2Inspected")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2Inspected TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2Inspected");
            }

            if(!isColumnExists(tableName, "IsPhase2InspBy")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspBy TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspBy");
            }

            if(!isColumnExists(tableName, "IsPhase2InspDate")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspDate TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspDate");
            }
            if(!isColumnExists(tableName, "IsPhase2InspPhoto1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspPhoto1 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspPhoto1");
            }

            if(!isColumnExists(tableName, "IsPhase2InspPhoto2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspPhoto2 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspPhoto2");
            }

            if(!isColumnExists(tableName, "IsPhase2InspLatitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspLatitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspLatitude");
            }

            if(!isColumnExists(tableName, "IsPhase2InspLongitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspLongitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspLongitude");
            }

            if(!isColumnExists(tableName, "IsPhase2InspRemarks")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase2InspRemarks TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase2InspRemarks");
            }

            if(!isColumnExists(tableName, "IsPhase3Inspected")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3Inspected TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3Inspected");
            }

            if(!isColumnExists(tableName, "IsPhase3InspBy")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspBy TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspBy");
            }

            if(!isColumnExists(tableName, "IsPhase3InspDate")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspDate TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspDate");
            }

            if(!isColumnExists(tableName, "IsPhase3InspPhoto1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspPhoto1 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspPhoto1");
            }

            if(!isColumnExists(tableName, "IsPhase3InspPhoto2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspPhoto2 BLOB");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspPhoto2");
            }

            if(!isColumnExists(tableName, "IsPhase3InspLatitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspLatitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspLatitude");
            }

            if(!isColumnExists(tableName, "IsPhase3InspLongitude")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspLongitude TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspLongitude");
            }

            if(!isColumnExists(tableName, "IsPhase3InspRemarks")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsPhase3InspRemarks TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsPhase3InspRemarks");
            }

            if(!isColumnExists(tableName, "IsFinalInspected")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsFinalInspected TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsFinalInspected");
            }

            if(!isColumnExists(tableName, "AbyabId")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN AbyabId TEXT");
                Log.e("ALTER Done",tableName +" - "+ "AbyabId");
            }

            if(!isColumnExists(tableName, "Khaata_Kheshara_Number")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Khaata_Kheshara_Number TEXT");
                Log.e("ALTER Done",tableName +" - "+ "Khaata_Kheshara_Number");
            }

            if(!isColumnExists(tableName, "Rakba")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN Rakba TEXT");
                Log.e("ALTER Done",tableName +" - "+ "Rakba");
            }

            if(!isColumnExists(tableName, "LandOwnerMob")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN LandOwnerMob TEXT");
                Log.e("ALTER Done",tableName +" - "+ "LandOwnerMob");
            }

            if(!isColumnExists(tableName, "LandOwnerName")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN LandOwnerName TEXT");
                Log.e("ALTER Done",tableName +" - "+ "LandOwnerName");
            }

            if(!isColumnExists(tableName, "FencingLatitude1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLatitude1 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLatitude1");
            }

            if(!isColumnExists(tableName, "FencingLatitude2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLatitude2 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLatitude2");
            }

            if(!isColumnExists(tableName, "FencingLatitude3")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLatitude3 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLatitude3");
            }

            if(!isColumnExists(tableName, "FencingLatitude4")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLatitude4 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLatitude4");
            }

            if(!isColumnExists(tableName, "FencingLongitude1")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLongitude1 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLongitude1");
            }

            if(!isColumnExists(tableName, "FencingLongitude2")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLongitude2 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLongitude2");
            }

            if(!isColumnExists(tableName, "FencingLongitude3")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLongitude3 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLongitude3");
            }

            if(!isColumnExists(tableName, "FencingLongitude4")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN FencingLongitude4 TEXT");
                Log.e("ALTER Done",tableName +" - "+ "FencingLongitude4");
            }

            if(!isColumnExists(tableName, "IsLandEnterDt")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN IsLandEnterDt TEXT");
                Log.e("ALTER Done",tableName +" - "+ "IsLandEnterDt");
            }

            if(!isColumnExists(tableName, "ConsumerNo")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN ConsumerNo TEXT");
                Log.e("ALTER Done",tableName +" - "+ "ConsumerNo");
            }

            if(!isColumnExists(tableName, "ConsumrBill")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN ConsumrBill TEXT");
                Log.e("ALTER Done",tableName +" - "+ "ConsumrBill");
            }

            if(!isColumnExists(tableName, "TreeType")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN TreeType TEXT");
                Log.e("ALTER Done",tableName +" - "+ "TreeType");
            }

            if(!isColumnExists(tableName, "TreeTypeName")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN TreeTypeName TEXT");
                Log.e("ALTER Done",tableName +" - "+ "TreeTypeName");
            }

            if(!isColumnExists(tableName, "TotalTree")){
                db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN TotalTree TEXT");
                Log.e("ALTER Done",tableName +" - "+ "TotalTree");
            }
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed","Menrega_Rural_Dev_Dept");
        }
    }

    public void AlterTable(String tableName,String columnName){
        db = databaseHelper.getReadableDatabase();

        try{
            db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" TEXT");
            Log.e("ALTER Done",tableName +"-"+ columnName);
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed",tableName +"-"+ columnName);
        }
    }

    public boolean isColumnExists (String table, String column) {
        db = databaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("PRAGMA table_info("+ table +")", null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex("name"));
                if (column.equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        cursor.close();
        return false;
    }


    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        permission = new MarshmallowPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        permission = new MarshmallowPermission(this, Manifest.permission.READ_PHONE_STATE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (permission.result == -1 || permission.result == 0 || permission.result == 1) {
                    try {
                        //imei = getimeinumber();
                        checkOnline();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);


        super.onResume();
    }

//    public String getimeinumber() {
//        String identifier = null;
//        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
//        if (tm != null)
//            identifier = tm.getDeviceId();
//        if (identifier == null || identifier.length() == 0)
//            identifier = Settings.Secure.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
//        return identifier;
//    }

    protected void checkOnline() {
        // TODO Auto-generated method stub
        super.onResume();

        if (Utiilties.isOnline(SplashActivity.this) == false) {

            AlertDialog.Builder ab = new AlertDialog.Builder(
                    SplashActivity.this);
            ab.setTitle("Alert Dialog !!!");
            ab.setMessage(Html.fromHtml("<font color=#000000>Internet Connection is not avaliable... \n Please Turn ON Network Connection \n To Turn ON Network Connection Press Yes Button Else To Exit Press Cancel Button.</font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                                    int whichButton) {
                    GlobalVariables.isOffline = false;
                    Intent I = new Intent(
                            android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });
            ab.setNegativeButton("Go Offline", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog,
                                    int whichButton) {

                    start();
                }
            });

            ab.show();

        } else {

            GlobalVariables.isOffline = false;
            new CheckUpdate().execute();
        }
    }

    private class CheckUpdate extends AsyncTask<Void, Void, Versioninfo> {

        CheckUpdate() {}

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Versioninfo doInBackground(Void... Params) {

//            TelephonyManager tm = null;
//            String imei = null;

//            permission = new MarshmallowPermission(SplashActivity.this, Manifest.permission.READ_PHONE_STATE);
//            if (permission.result == -1 || permission.result == 0) {
//                try {
//                    tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//
//                    if (tm != null) imei = tm.getDeviceId();
//                } catch (Exception e) {
//                }
//            }

            String version = Utiilties.getAppVersion(SplashActivity.this);
//            try {
//                version = getPackageManager().getPackageInfo(getPackageName(),
//                        0).versionName;
//            } catch (PackageManager.NameNotFoundException e) {
//                // TODO Auto-generated catch block
//                e.printStackTrace();
//            }

            Versioninfo versioninfo = WebServiceHelper.CheckVersion(version);

            return versioninfo;

        }

        @Override
        protected void onPostExecute(final Versioninfo versioninfo) {

            final AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setCancelable(false);
            if (versioninfo != null && versioninfo.isValidDevice()) {

                CommonPref.setCheckUpdate(getApplicationContext(), System.currentTimeMillis());

                if (versioninfo.getAdminMsg().trim().length() > 0 && !versioninfo.getAdminMsg().trim().equalsIgnoreCase("anyType{}")) {

                    ab.setTitle(versioninfo.getAdminTitle());
                    ab.setMessage(Html.fromHtml(versioninfo.getAdminMsg()));
                    ab.setPositiveButton("ok",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int whichButton) {
                                    dialog.dismiss();
                                    showDailog(ab, versioninfo);
                                }
                            });
                    ab.show();
                } else {
                    showDailog(ab, versioninfo);
                }
            } else {
                if (versioninfo != null) {
                    Toast.makeText(getApplicationContext(), "ok" ,Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "null response" ,Toast.LENGTH_LONG).show();
                    start();
                }
            }

        }
    }


    private void showDailog(AlertDialog.Builder ab,
                            final Versioninfo versioninfo) {

        if (versioninfo.isVerUpdated()) {

            if (versioninfo.getPriority() == 0) {

                /*
                 * Intent i = new Intent(getBaseContext(), LoginActivity.class);
                 * startActivity(i); finish();
                 */
                dothis();
            } else if (versioninfo.getPriority() == 1) {

                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());

                // ab.setMessage("New version of App is available. Please update the App before proceeding. Do you want to update now?");

                // ab.setMessage(Html
                // .fromHtml("<font color=#000000>New Version of Application is available. Please update the application before proceeding. Do you want to update now?</font>"));
                ab.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                /*
                                 * Intent myWebLink = new Intent(
                                 * android.content.Intent.ACTION_VIEW);
                                 * myWebLink.setData(Uri.parse(versioninfo
                                 * .getAppUrl()));
                                 *
                                 * startActivity(myWebLink);
                                 */

                                Intent launchIntent = getPackageManager()
                                        .getLaunchIntentForPackage(
                                                "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity"); // package
                                // name
                                // and
                                // activity
                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri.parse("market://details?id=" + getApplicationContext().getPackageName()));

                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                            }
                        });
                ab.setNegativeButton("Ignore",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {
                                dialog.dismiss();
                                dothis();
                            }

                        });

                ab.show();

            } else if (versioninfo.getPriority() == 2) {

                ab.setTitle(versioninfo.getUpdateTile());
                ab.setMessage(versioninfo.getUpdateMsg());

                ab.setPositiveButton("Update",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int whichButton) {

                                Intent launchIntent = getPackageManager().getLaunchIntentForPackage(
                                        "com.android.vending");
                                ComponentName comp = new ComponentName(
                                        "com.android.vending",
                                        "com.google.android.finsky.activities.LaunchUrlHandlerActivity");

                                launchIntent.setComponent(comp);
                                launchIntent.setData(Uri.parse("market://details?id="
                                        + getApplicationContext()
                                        .getPackageName()));
                                try {
                                    startActivity(launchIntent);
                                    finish();
                                } catch (android.content.ActivityNotFoundException anfe) {
                                    startActivity(new Intent(
                                            Intent.ACTION_VIEW, Uri
                                            .parse(versioninfo
                                                    .getAppUrl())));
                                    finish();
                                }

                                dialog.dismiss();
                                // finish();
                            }
                        });
                ab.show();
            }
        } else {
            dothis();
        }

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

    }

    private void dothis() {

        if (!Utiilties.isOnline(SplashActivity.this)) {

            AlertDialog.Builder ab = new AlertDialog.Builder(SplashActivity.this);
            ab.setMessage(Html.fromHtml(
                    "<font color=#000000>Internet Connection is not avaliable..Please Turn ON Network Connection </font>"));
            ab.setPositiveButton("Turn On Network Connection", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    Intent I = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(I);
                }
            });

            ab.create();
            ab.show();

        } else {

            start();

        }
    }


//    private void start() {
//        new Handler().postDelayed(new Runnable() {
//
//
//            @Override
//            public void run() {
//                // This method will be executed once the timer is over
//                // Start your app main activity
//                if (username.equals("")) {
//                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
//                    startActivity(i);
//                } else {
//                    //Intent i = new Intent(SplashActivity.this, Home.class);
//                    Intent i = new Intent(SplashActivity.this, DashboardActivity.class);
//                    startActivity(i);
//
//                }
//
//                // close this activity
//                finish();
//            }
//        }, SPLASH_TIME_OUT);
//    }

    private void start() {

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        Intent i;
        if(prefs.getBoolean("username", false) && prefs.getBoolean("password",false)) {
            if(prefs.getString("role", "na").toLowerCase().contains("co")){
                i = new Intent(getApplicationContext(),DashboardCoActivity.class);
                startActivity(i);
                finish();
            }else if(prefs.getString("role", "na").toLowerCase().contains("grievance")){
                i = new Intent(getApplicationContext(), GrievanceHomeActivity.class);
                startActivity(i);
                finish();
            }
//            else if(prefs.getString("role", "na").toLowerCase().contains("Insp")){
//                i = new Intent(getApplicationContext(), DashboardActivity.class);
//                startActivity(i);
//                finish();
//            }
            else{
                i = new Intent(getApplicationContext(),DashboardActivity.class);
                startActivity(i);
                finish();
            }

        }
        else {

            i = new Intent(getApplicationContext(),PreLoginActivity.class);
            startActivity(i);

            finish();
        }
    }


    public static void disableSSLCertificateChecking() {
        TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }

            @Override
            public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }

            @Override
            public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
                // Not implemented
            }
        } };

        try {
            SSLContext sc = SSLContext.getInstance("TLS");

            sc.init(null, trustAllCerts, new java.security.SecureRandom());

            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() { @Override public boolean verify(String hostname, SSLSession session) { return true; } });
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}