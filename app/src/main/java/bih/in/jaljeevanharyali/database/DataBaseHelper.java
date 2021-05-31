package bih.in.jaljeevanharyali.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import bih.in.jaljeevanharyali.activity.PanchayatEntity;
import bih.in.jaljeevanharyali.activity.nursery.NurseryEntryActivity;
import bih.in.jaljeevanharyali.adapter.OtherDeptInsptEntity;
import bih.in.jaljeevanharyali.entity.Abyab;
import bih.in.jaljeevanharyali.entity.Block;
import bih.in.jaljeevanharyali.entity.BlockWeb;
import bih.in.jaljeevanharyali.entity.DataProgress;
import bih.in.jaljeevanharyali.entity.DepartmentDetail;
import bih.in.jaljeevanharyali.entity.DepartmentEntity;
import bih.in.jaljeevanharyali.entity.District;
import bih.in.jaljeevanharyali.entity.DistrictData;
import bih.in.jaljeevanharyali.entity.FieldInformation;
import bih.in.jaljeevanharyali.entity.Fyear;
import bih.in.jaljeevanharyali.entity.FyearWeb;
import bih.in.jaljeevanharyali.entity.Krinawayan;
import bih.in.jaljeevanharyali.entity.LocalFieldInfo;
import bih.in.jaljeevanharyali.entity.LocalSpinnerData;
import bih.in.jaljeevanharyali.entity.ManregaSchemeDetail;
import bih.in.jaljeevanharyali.entity.MyGpsDataEntity;
import bih.in.jaljeevanharyali.entity.NurseryEntity;
import bih.in.jaljeevanharyali.entity.PanchayatData;
import bih.in.jaljeevanharyali.entity.PanchayatWeb;
import bih.in.jaljeevanharyali.entity.PondEncroachmentEntity;
import bih.in.jaljeevanharyali.entity.PondEntity;
import bih.in.jaljeevanharyali.entity.PondInspectionDetail;
import bih.in.jaljeevanharyali.entity.PondInspectionEntity;
import bih.in.jaljeevanharyali.entity.PondLakeDepartmentEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaDataEntity;
import bih.in.jaljeevanharyali.entity.SanrachnaTypeEntity;
import bih.in.jaljeevanharyali.entity.SectorWeb;
import bih.in.jaljeevanharyali.entity.SevikaSahaikaEntity;
import bih.in.jaljeevanharyali.entity.SpinnerMaster;
import bih.in.jaljeevanharyali.entity.StructureRemarkEntity;
import bih.in.jaljeevanharyali.entity.StructureType;
import bih.in.jaljeevanharyali.entity.Sub_abyb;
import bih.in.jaljeevanharyali.entity.TreesDetail;
import bih.in.jaljeevanharyali.entity.UserDetails;
import bih.in.jaljeevanharyali.entity.VillageListEntity;
import bih.in.jaljeevanharyali.entity.VoutcherEntity;
import bih.in.jaljeevanharyali.entity.WardList;
import bih.in.jaljeevanharyali.entity.WellInspectionEntity;
import bih.in.jaljeevanharyali.entity.ward;
import bih.in.jaljeevanharyali.utility.Utiilties;

//import bih.in.jaljeevanharyali.activity.NewEnrollmentActivity;


/**
 * Helper to the database, manages versions and creation
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    //private static String DB_PATH = "";
    private static String DB_PATH = "/data/data/app.bih.in.nic.epacsmgmt/databases/";
    //private static String DB_NAME = "eCountingAC.sqlite";
    private static String DB_NAME = "PACSDB1";
    //private static String DB_NAME = "InspAwas.db";
///////private static String DB_NAME = "InspAwasN.db";

    private SQLiteDatabase myDataBase;
    private final Context myContext;

    SQLiteDatabase db;

    public DataBaseHelper(Context context) {

        super(context, DB_NAME, null, 2);
        if (android.os.Build.VERSION.SDK_INT >= 4.2) {


            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        } else {
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        }
        this.myContext = context;
    }



    /**
     * Creates a empty database on the system and rewrites it with your own
     * database.
     */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if (dbExist) {
            // do nothing - database already exist
        } else {
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each
     * time you open the application.
     *
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        SQLiteDatabase checkDB = null;

        try {
            String myPath = DB_PATH + DB_NAME;
            //this.getReadableDatabase();

            checkDB = SQLiteDatabase.openDatabase(myPath, null,
                    SQLiteDatabase.NO_LOCALIZED_COLLATORS | SQLiteDatabase.OPEN_READWRITE);


        } catch (SQLiteException e) {

            // database does't exist yet.

        }

        if (checkDB != null) {

            checkDB.close();

        }

        return checkDB != null ? true : false;

    }

    public boolean databaseExist() {


        File dbFile = new File(DB_PATH + DB_NAME);

        return dbFile.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created
     * empty database in the system folder, from where it can be accessed and
     * handled. This is done by transfering bytestream.
     */
    private void copyDataBase() throws IOException {

        // Open your local db as the input stream
        InputStream myInput = myContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        // Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);
        this.getReadableDatabase().close();
        // transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer, 0, length);
        }

        // Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();


    }

    public void openDataBase() throws SQLException {

        // Open the database
        this.getReadableDatabase();
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null,
                SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if (myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//        db.execSQL("ALTER TABLE Ward ADD COLUMN AreaType TEXT");
//        db.execSQL("ALTER TABLE OtherDept_Of_Rural_Dev_Dept ADD COLUMN VillageID TEXT");
//        db.execSQL("ALTER TABLE Menrega_Rural_Dev_Dept ADD COLUMN Work_Structure_Type_Name TEXT");
//        db.execSQL("ALTER TABLE Menrega_Rural_Dev_Dept ADD COLUMN Work_Structure_Type_Other_Name TEXT");
//        db.execSQL("ALTER TABLE OtherDept_Of_Rural_Dev_Dept ADD COLUMN Work_Structure_Type_Name TEXT");
//        db.execSQL("ALTER TABLE OtherDept_Of_Rural_Dev_Dept ADD COLUMN Work_Structure_Type_Other_Name TEXT");

        //modifyTable();
    }

    public void modifyTable(){

        if(isColumnExists("Ward", "AreaType") == false){
            AlterTable("Ward", "AreaType");
        }

//        if(!isColumnExists("OtherDept_Of_Rural_Dev_Dept", "VillageID")){
//            AlterTable("OtherDept_Of_Rural_Dev_Dept", "VillageID");
//        }
//
//
//        if(!isColumnExists("OtherDept_Of_Rural_Dev_Dept", "Work_Structure_Type_Name")){
//            AlterTable("OtherDept_Of_Rural_Dev_Dept", "Work_Structure_Type_Name");
//        }
//
//        if(!isColumnExists("OtherDept_Of_Rural_Dev_Dept", "Work_Structure_Type_Other_Name")){
//            AlterTable("OtherDept_Of_Rural_Dev_Dept", "Work_Structure_Type_Other_Name");
//        }

//        AlterManregTable("Menrega_Rural_Dev_Dept");
//        AlterManregTable("OtherDept_Of_Rural_Dev_Dept");
    }

    public void AlterManregTable(String tableName){
        db = this.getReadableDatabase();
        //String tableName;

        try{
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
//            db.execSQL("ALTER TABLE "+tableName+" ADD COLUMN "+columnName+" TEXT");
//            Log.e("ALTER Done",tableName +"-"+ columnName);
        }
        catch (Exception e)
        {
            Log.e("ALTER Failed","Menrega_Rural_Dev_Dept");
        }
    }

    public void AlterTable(String tableName,String columnName)
    {
        db = this.getReadableDatabase();

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
        db = this.getReadableDatabase();
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
    public long getUserCount() {

        long x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("Select * from UserLogin", null);

            x = cur.getCount();

            cur.close();
            db.close();
        } catch (Exception e) {
            // TODO: handle exception
        }
        return x;
    }


    public long insertUserDetails(UserDetails result) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();

            values.put("UserID", result.getUserID().toLowerCase());
            values.put("UserName", result.getName());
            values.put("UserPassword", result.getPassword());
            //values.put("IMEI", result.getIMEI());
            values.put("Role", result.getUserrole());
            values.put("DistCode", result.getDistrictCode());
            values.put("DistName", result.getDistName());
            values.put("BlockCode", result.getBlockCode());
            values.put("BlockName", result.getBlockName());
            values.put("PanchayatCode", result.getPanchaayatCode());
            values.put("PanchayatName", result.getPanchayatName());

            values.put("Address", result.getAddress());
            values.put("FatherName", result.getFatherName());
            values.put("MobileNo", result.getMobileNo());
            values.put("DeptId", result.getDeptId());

            String[] whereArgs = new String[]{result.getUserID()};

            c = db.update("UserDetail", values, "UserID=? ", whereArgs);

            if (!(c > 0)) {

                c = db.insert("UserDetail", null, values);
                //c = db.insertWithOnConflict("UserLogin", null, values,SQLiteDatabase.CONFLICT_REPLACE);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;

    }

    public UserDetails getUserDetails(String userId, String pass) {

        UserDetails userInfo = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{userId.trim().toLowerCase(), pass};

            Cursor cur = db.rawQuery("Select * from UserDetail WHERE UserID=? and UserPassword=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                userInfo = new UserDetails();
                userInfo.setUserID(cur.getString(cur.getColumnIndex("UserID")));
                userInfo.setName(cur.getString(cur.getColumnIndex("UserName")));
                userInfo.setPassword(cur.getString(cur.getColumnIndex("UserPassword")));

                userInfo.setUserrole(cur.getString(cur.getColumnIndex("Role")));
                userInfo.setAuthenticated(true);
                userInfo.setDistrictCode(cur.getString(cur.getColumnIndex("DistCode")));
                userInfo.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                userInfo.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
                userInfo.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                userInfo.setPanchaayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                userInfo.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));

                userInfo.setAddress(cur.getString(cur.getColumnIndex("Address")));
                userInfo.setFatherName(cur.getString(cur.getColumnIndex("FatherName")));
                userInfo.setMobileNo(cur.getString(cur.getColumnIndex("MobileNo")));
                userInfo.setDeptId(cur.getString(cur.getColumnIndex("DeptId")));
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            userInfo = null;
        }
        return userInfo;
    }

//    public long UpdateInspectionCount(String inspectionCount, String UserId) {
//
//        long c = 0;
//        try {
//
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            ContentValues values = new ContentValues();
//            values.put("TotInspection", inspectionCount);
//
//            String[] whereArgs = new String[]{UserId};
//
//            c = db.update("UserLogin", values, "UserID=? ", whereArgs);
//
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            // TODO: handle exception
//            return 0;
//        }
//        return c;
//
//    }

    public WellInspectionEntity getWellInspectionDetails(String inspId) {

        WellInspectionEntity info = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{inspId};

            Cursor cur = db.rawQuery(
                    "Select * from WellInspectionDetail WHERE id=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                info = new WellInspectionEntity();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setBlockID(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));

                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                //info.setAuthenticated(true);
                info.setVillageID(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setVillageName(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setPanchayat_Code(cur.getString(cur.getColumnIndex("Panchayat_Code")));
                info.setPanchayat_Name(cur.getString(cur.getColumnIndex("Panchayat_Name")));
                info.setKhata_Khesra_No(cur.getString(cur.getColumnIndex("Khata_Khesra_No")));
                info.setPrivate_or_Public(cur.getString(cur.getColumnIndex("Private_or_Public")));
                info.setOutside_Diamter(cur.getString(cur.getColumnIndex("Outside_Diamter")));

                info.setName_of_undertaken(cur.getString(cur.getColumnIndex("Name_Of_Undertaken_Dept")));
                info.setRequirement_Of_Flyer(cur.getString(cur.getColumnIndex("Requirement_Of_Flyer")));
                info.setStatus_of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setTypes_of_Encroachment(cur.getString(cur.getColumnIndex("Types_Of_Encroachment")));
                info.setRequirement_of_Renovation(cur.getString(cur.getColumnIndex("Requirement_Of_Renovation")));
                //info.setre(cur.getString(cur.getColumnIndex("Recommended_Execution_Dept")));
                info.setRequirement_of_machine(cur.getString(cur.getColumnIndex("Requirement_of_Machine")));
                //info.set(cur.getString(cur.getColumnIndex("Name_of_undertaken")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));

                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                //info.setVi(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setLatitude_Mob(cur.getString(cur.getColumnIndex("Latitude_Mob")));
                info.setLongitude_Mob(cur.getString(cur.getColumnIndex("Longitude_Mob")));
                info.setWellAvblValue(cur.getString(cur.getColumnIndex("WellAvblValue")));
                info.setStructureId(cur.getString(cur.getColumnIndex("structureId")));
                info.setFunctional(cur.getString(cur.getColumnIndex("functionalStatus")));

                info.setWellOwnerOtherDeptName(cur.getString(cur.getColumnIndex("Photo3")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg2);
                }

//                if (!cur.isNull(cur.getColumnIndex("Photo3"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo3"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg3 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto3(encodedImg3);
//                }
//
//                if (!cur.isNull(cur.getColumnIndex("Photo4"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo4"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg4 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto4(encodedImg4);
//                }
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            info = null;
        }
        return info;
    }

    public PondEncroachmentEntity getPondEncrhmntDetails(String inspId, String type) {

        String tablename = type.equals("pond") ? "CoPondEncroachmentReport" : "CoWellEncroachmentReport";
        PondEncroachmentEntity info = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{inspId};

            Cursor cur = db.rawQuery(
                    "Select * from "+tablename+" WHERE id=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                info = new PondEncroachmentEntity();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistID")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockID(cur.getString(cur.getColumnIndex("BlockID")));
                info.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayatID(cur.getString(cur.getColumnIndex("PanchayatID")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));

                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("RajswaThanaNumber")));
                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));

                info.setVillageID(cur.getString(cur.getColumnIndex("VillageID")));
                info.setVILLNAME(cur.getString(cur.getColumnIndex("VILLNAME")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setStatus_Of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setType_Of_Encroachment(cur.getString(cur.getColumnIndex("Type_Of_Encroachment")));

                info.setVerified_By(cur.getString(cur.getColumnIndex("Verified_By")));
                info.setIsInspected(cur.getString(cur.getColumnIndex("IsInspected")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));

                info.setEnch_Verified_By(cur.getString(cur.getColumnIndex("Ench_Verified_By")));
                info.setEStatus(cur.getString(cur.getColumnIndex("EStatus")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setEnchrochmentStartDate(cur.getString(cur.getColumnIndex("EnchrochmentStartDate")));
                info.setEnchrochmentEndDate(cur.getString(cur.getColumnIndex("EnchrochmentEndDate")));
                info.setNoticeDate(cur.getString(cur.getColumnIndex("NoticeDate")));
                info.setNoticeNo(cur.getString(cur.getColumnIndex("NoticeNo")));
                info.setAppVersion(cur.getString(cur.getColumnIndex("AppVersion")));
                info.setUploadType(cur.getString(cur.getColumnIndex("UploadType")));

            }
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            info = null;
        }
        return info;
    }


    public PondInspectionDetail getPondInspectionDetails(String inspId) {

        PondInspectionDetail info = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{inspId};

            Cursor cur = db.rawQuery(
                    "Select * from PondInspectionDetail WHERE id=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                info = new PondInspectionDetail();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistrictCode(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setBlockCode(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));

                info.setRajswaThanaNo(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                //info.setAuthenticated(true);
                info.setVillage(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setVillageName(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setPanchayatCode(cur.getString(cur.getColumnIndex("Panchayat_Code")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("Panchayat_Name")));
                info.setKhataKhesraNo(cur.getString(cur.getColumnIndex("Khata_Khesra_No")));
                info.setPrivateOrPublic(cur.getString(cur.getColumnIndex("Private_or_Public")));
                info.setAreaByGovtRecord(cur.getString(cur.getColumnIndex("Area_by_Govt_Record")));

                info.setConnectedWithPine(cur.getString(cur.getColumnIndex("Connected_with_Pine")));
                info.setAvailiablityOfWater(cur.getString(cur.getColumnIndex("Availability_Of_Water")));
                info.setStatusOfEncroachment(cur.getString(cur.getColumnIndex("Status_of_Encroachment")));
                info.setTypesOfEncroachment(cur.getString(cur.getColumnIndex("Types_of_Encroachment")));
                info.setRequirementOfRenovation(cur.getString(cur.getColumnIndex("Requirement_of_Renovation")));
                info.setRecommendedExecutionDept(cur.getString(cur.getColumnIndex("Recommended_Execution_Dept")));
                info.setRequirementOfMachine(cur.getString(cur.getColumnIndex("Requirement_of_machine")));
                info.setOwnerName(cur.getString(cur.getColumnIndex("Name_of_undertaken")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));

                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                //info.setVi(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setLatitude_Mob(cur.getString(cur.getColumnIndex("Latitude_Mob")));
                info.setLongitude_Mob(cur.getString(cur.getColumnIndex("Longitude_Mob")));
                info.setPondAvblValue(cur.getString(cur.getColumnIndex("PondAvblValue")));
                info.setPondCatValue(cur.getString(cur.getColumnIndex("PondCatValue")));
                info.setAbyabID(cur.getString(cur.getColumnIndex("AbyabId")));
                info.setAbyabName(cur.getString(cur.getColumnIndex("AbyabName")));
                info.setDeptId(cur.getString(cur.getColumnIndex("DeptId")));
                info.setFunctional(cur.getString(cur.getColumnIndex("functionalStatus")));

                info.setPondCatName(cur.getString(cur.getColumnIndex("Photo3")));
                info.setPondOwnerOtherDeptName(cur.getString(cur.getColumnIndex("Photo4")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg2);
                }

//                if (!cur.isNull(cur.getColumnIndex("Photo3"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo3"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg3 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto3(encodedImg3);
//                }
//
//                if (!cur.isNull(cur.getColumnIndex("Photo4"))) {
//
//                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photor4"));
//                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);
//
//                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
//                    String encodedImg4 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
//                    info.setPhoto4(encodedImg4);
//                }


            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            info = null;
        }
        return info;
    }

    public ManregaSchemeDetail getManregaDetail(String inspId) {

        ManregaSchemeDetail info = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{inspId};

            Cursor cur = db.rawQuery(
                    "Select * from Menrega_Rural_Dev_Dept WHERE id=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                info = new ManregaSchemeDetail();

                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setExecution_Dept_ID(cur.getString(cur.getColumnIndex("Execution_DeptID")));
                info.setSub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_DivisionID")));
                info.setSub_Sub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_SubDivisionID")));
                //info.setAuthenticated(true);
                info.setSub_Exect_Value(cur.getString(cur.getColumnIndex("Sub_DivisionValue")));
                info.setDistrict_Id(cur.getString(cur.getColumnIndex("DistrictID")));
                info.setDistrict_Name(cur.getString(cur.getColumnIndex("DistrictName")));
                info.setBlock_ID(cur.getString(cur.getColumnIndex("BlockID")));
                info.setBlock_Name(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayat_Id(cur.getString(cur.getColumnIndex("PanchayatID")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
                info.setWard_Id(cur.getString(cur.getColumnIndex("WardID")));
                info.setWard_Name(cur.getString(cur.getColumnIndex("Ward_Name")));
                info.setVillage_Id(cur.getString(cur.getColumnIndex("VillageID")));

                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setWork_Structure_Type(cur.getString(cur.getColumnIndex("Work_Structure_Type")));
                info.setWork_Structure_Type_Name(cur.getString(cur.getColumnIndex("Work_Structure_Type_Name")));
                info.setWork_Structure_Type_Other_Name(cur.getString(cur.getColumnIndex("Work_Structure_Type_Other_Name")));

                info.setMeasurement_Of_Structure(cur.getString(cur.getColumnIndex("Measurement_Of_Structure")));
                info.setEstimated_Amount(cur.getString(cur.getColumnIndex("Estimated_Amount")));
                info.setApproval_Date(cur.getString(cur.getColumnIndex("Approval_Date")));
                info.setAdministrative_Approval_Date(cur.getString(cur.getColumnIndex("Administrative_Approval_Date")));
                info.setScheme_Code(cur.getString(cur.getColumnIndex("MIS_Scheme_Code")));
                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));

                info.setCreatedBy(cur.getString(cur.getColumnIndex("CreatedBy")));
                info.setCreatedDate(cur.getString(cur.getColumnIndex("CreatedDate")));
                info.setApp_Version(cur.getString(cur.getColumnIndex("App_Version")));

                info.setExectDeptName(cur.getString(cur.getColumnIndex("ExectDeptName")));
                info.setSubDivName(cur.getString(cur.getColumnIndex("SubDivName")));
                info.setOther_Name(cur.getString(cur.getColumnIndex("Other_Name")));
                info.setTypes_OfSarchnaName(cur.getString(cur.getColumnIndex("Types_OfSarchnaName")));
                info.setTypes_OfSarchnaId(cur.getString(cur.getColumnIndex("Types_OfSarchnaId")));

                info.setIsPhase1Inspected(cur.getString(cur.getColumnIndex("IsPhase1Inspected")));
                info.setIsPhase1InspBy(cur.getString(cur.getColumnIndex("IsPhase1InspBy")));
                info.setIsPhase1InspDate(cur.getString(cur.getColumnIndex("IsPhase1InspDate")));
                info.setIsPhase1InspLatitude(cur.getString(cur.getColumnIndex("IsPhase1InspLatitude")));
                info.setIsPhase1InspLongitude(cur.getString(cur.getColumnIndex("IsPhase1InspLongitude")));
                info.setIsPhase1InspRemarks(cur.getString(cur.getColumnIndex("IsPhase1InspRemarks")));

                info.setIsPhase2Inspected(cur.getString(cur.getColumnIndex("IsPhase2Inspected")));
                info.setIsPhase2InspBy(cur.getString(cur.getColumnIndex("IsPhase2InspBy")));
                info.setIsPhase2InspDate(cur.getString(cur.getColumnIndex("IsPhase2InspDate")));
                info.setIsPhase2InspLatitude(cur.getString(cur.getColumnIndex("IsPhase2InspLatitude")));
                info.setIsPhase2InspLongitude(cur.getString(cur.getColumnIndex("IsPhase2InspLongitude")));
                info.setIsPhase2InspRemarks(cur.getString(cur.getColumnIndex("IsPhase2InspRemarks")));

                info.setIsPhase3Inspected(cur.getString(cur.getColumnIndex("IsPhase3Inspected")));
                info.setIsPhase3InspBy(cur.getString(cur.getColumnIndex("IsPhase3InspBy")));
                info.setIsPhase3InspDate(cur.getString(cur.getColumnIndex("IsPhase3InspDate")));
                info.setIsPhase3InspLatitude(cur.getString(cur.getColumnIndex("IsPhase3InspLatitude")));
                info.setIsPhase3InspLongitude(cur.getString(cur.getColumnIndex("IsPhase3InspLongitude")));
                info.setIsPhase3InspRemarks(cur.getString(cur.getColumnIndex("IsPhase3InspRemarks")));

                info.setYojnaType(cur.getString(cur.getColumnIndex("YojnaType")));

                info.setAwayabId(cur.getString(cur.getColumnIndex("AbyabId")));
                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
                info.setRakba(cur.getString(cur.getColumnIndex("Rakba")));
                info.setLandOwnerMob(cur.getString(cur.getColumnIndex("LandOwnerMob")));
                info.setLandOwnerName(cur.getString(cur.getColumnIndex("LandOwnerName")));
                info.setFencingLatitude1(cur.getString(cur.getColumnIndex("FencingLatitude1")));
                info.setFencingLatitude2(cur.getString(cur.getColumnIndex("FencingLatitude2")));
                info.setFencingLatitude3(cur.getString(cur.getColumnIndex("FencingLatitude3")));
                info.setFencingLatitude4(cur.getString(cur.getColumnIndex("FencingLatitude4")));
                info.setFencingLongitude1(cur.getString(cur.getColumnIndex("FencingLongitude1")));
                info.setFencingLongitude2(cur.getString(cur.getColumnIndex("FencingLongitude2")));
                info.setFencingLongitude3(cur.getString(cur.getColumnIndex("FencingLongitude3")));
                info.setFencingLongitude4(cur.getString(cur.getColumnIndex("FencingLongitude4")));
                info.setIsLandEnterDt(cur.getString(cur.getColumnIndex("IsLandEnterDt")));

                info.setConsumerNo(cur.getString(cur.getColumnIndex("ConsumerNo")));
                info.setConsumrBill(cur.getString(cur.getColumnIndex("ConsumrBill")));
                info.setTreeType(cur.getString(cur.getColumnIndex("TreeType")));
                info.setTreeTypeName(cur.getString(cur.getColumnIndex("TreeTypeName")));
                info.setTotalTree(cur.getString(cur.getColumnIndex("TotalTree")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg2);
                }


            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            info = null;
        }
        return info;
    }

    public ManregaSchemeDetail getOtherSchemeDetail(String inspId) {

        ManregaSchemeDetail info = null;

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{inspId};

            Cursor cur = db.rawQuery(
                    "Select * from OtherDept_Of_Rural_Dev_Dept WHERE id=?",
                    params);
            int x = cur.getCount();

            while (cur.moveToNext()) {


                info = new ManregaSchemeDetail();

                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setExecution_Dept_ID(cur.getString(cur.getColumnIndex("Execution_DeptID")));
                info.setSub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_Exec_DeptID")));
                info.setSub_Sub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_Sub_Exec_DeptID")));
                info.setSub_Sub_Exec_DeptName(cur.getString(cur.getColumnIndex("Sub_Sub_Exec_DeptName")));
                //info.setAuthenticated(true);
                info.setSub_Exect_Value(cur.getString(cur.getColumnIndex("Sub_ExeValue")));
                info.setDistrict_Id(cur.getString(cur.getColumnIndex("DistrictID")));
                info.setDistrict_Name(cur.getString(cur.getColumnIndex("DistrictName")));
                info.setBlock_ID(cur.getString(cur.getColumnIndex("BlockID")));
                info.setBlock_Name(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayat_Id(cur.getString(cur.getColumnIndex("PanchayatID")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
                info.setWard_Id(cur.getString(cur.getColumnIndex("WardID")));
                info.setWard_Name(cur.getString(cur.getColumnIndex("Ward_Name")));
                info.setVillage_Id(cur.getString(cur.getColumnIndex("VillageID")));
                info.setVillage_Name(cur.getString(cur.getColumnIndex("VillageName")));

                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setWork_Structure_Type(cur.getString(cur.getColumnIndex("Work_Structure_Type")));
                info.setMeasurement_Of_Structure(cur.getString(cur.getColumnIndex("Measurement_Of_Structure")));
                info.setEstimated_Amount(cur.getString(cur.getColumnIndex("Estimated_Amount")));
                //info.setApproval_Date(cur.getString(cur.getColumnIndex("Approval_Date")));
                info.setAdministrative_Approval_Date(cur.getString(cur.getColumnIndex("Administrative_Approval_Date")));
                info.setScheme_Code(cur.getString(cur.getColumnIndex("Scheme_Code")));
                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));

                info.setCreatedBy(cur.getString(cur.getColumnIndex("CreatedBy")));
                info.setCreatedDate(cur.getString(cur.getColumnIndex("CreatedDate")));
                info.setApp_Version(cur.getString(cur.getColumnIndex("App_Version")));
                info.setExectDeptName(cur.getString(cur.getColumnIndex("ExectDeptName")));
                info.setSubDivName(cur.getString(cur.getColumnIndex("SubDivName")));
                info.setSahariNikayeId(cur.getString(cur.getColumnIndex("SahariNikayeId")));

                //info.setOther_Name(cur.getString(cur.getColumnIndex("Other_Name")));
                info.setTypes_OfSarchnaName(cur.getString(cur.getColumnIndex("Types_OfSarchnaName")));
                info.setTypes_OfSarchnaId(cur.getString(cur.getColumnIndex("Types_OfSarchnaId")));

                info.setIsPhase1Inspected(cur.getString(cur.getColumnIndex("IsPhase1Inspected")));
                info.setIsPhase1InspBy(cur.getString(cur.getColumnIndex("IsPhase1InspBy")));
                info.setIsPhase1InspDate(cur.getString(cur.getColumnIndex("IsPhase1InspDate")));
                info.setIsPhase1InspLatitude(cur.getString(cur.getColumnIndex("IsPhase1InspLatitude")));
                info.setIsPhase1InspLongitude(cur.getString(cur.getColumnIndex("IsPhase1InspLongitude")));
                info.setIsPhase1InspRemarks(cur.getString(cur.getColumnIndex("IsPhase1InspRemarks")));

                info.setIsPhase2Inspected(cur.getString(cur.getColumnIndex("IsPhase2Inspected")));
                info.setIsPhase2InspBy(cur.getString(cur.getColumnIndex("IsPhase2InspBy")));
                info.setIsPhase2InspDate(cur.getString(cur.getColumnIndex("IsPhase2InspDate")));
                info.setIsPhase2InspLatitude(cur.getString(cur.getColumnIndex("IsPhase2InspLatitude")));
                info.setIsPhase2InspLongitude(cur.getString(cur.getColumnIndex("IsPhase2InspLongitude")));
                info.setIsPhase2InspRemarks(cur.getString(cur.getColumnIndex("IsPhase2InspRemarks")));

                info.setIsPhase3Inspected(cur.getString(cur.getColumnIndex("IsPhase3Inspected")));
                info.setIsPhase3InspBy(cur.getString(cur.getColumnIndex("IsPhase3InspBy")));
                info.setIsPhase3InspDate(cur.getString(cur.getColumnIndex("IsPhase3InspDate")));
                info.setIsPhase3InspLatitude(cur.getString(cur.getColumnIndex("IsPhase3InspLatitude")));
                info.setIsPhase3InspLongitude(cur.getString(cur.getColumnIndex("IsPhase3InspLongitude")));
                info.setIsPhase3InspRemarks(cur.getString(cur.getColumnIndex("IsPhase3InspRemarks")));

                info.setYojnaType(cur.getString(cur.getColumnIndex("YojnaType")));

                info.setAwayabId(cur.getString(cur.getColumnIndex("AbyabId")));
                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
                info.setRakba(cur.getString(cur.getColumnIndex("Rakba")));
                info.setLandOwnerMob(cur.getString(cur.getColumnIndex("LandOwnerMob")));
                info.setLandOwnerName(cur.getString(cur.getColumnIndex("LandOwnerName")));
                info.setFencingLatitude1(cur.getString(cur.getColumnIndex("FencingLatitude1")));
                info.setFencingLatitude2(cur.getString(cur.getColumnIndex("FencingLatitude2")));
                info.setFencingLatitude3(cur.getString(cur.getColumnIndex("FencingLatitude3")));
                info.setFencingLatitude4(cur.getString(cur.getColumnIndex("FencingLatitude4")));
                info.setFencingLongitude1(cur.getString(cur.getColumnIndex("FencingLongitude1")));
                info.setFencingLongitude2(cur.getString(cur.getColumnIndex("FencingLongitude2")));
                info.setFencingLongitude3(cur.getString(cur.getColumnIndex("FencingLongitude3")));
                info.setFencingLongitude4(cur.getString(cur.getColumnIndex("FencingLongitude4")));
                info.setIsLandEnterDt(cur.getString(cur.getColumnIndex("IsLandEnterDt")));

                info.setConsumerNo(cur.getString(cur.getColumnIndex("ConsumerNo")));
                info.setConsumrBill(cur.getString(cur.getColumnIndex("ConsumrBill")));
                info.setTreeType(cur.getString(cur.getColumnIndex("TreeType")));
                info.setTreeTypeName(cur.getString(cur.getColumnIndex("TreeTypeName")));
                info.setTotalTree(cur.getString(cur.getColumnIndex("TotalTree")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg2);
                }


            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            info = null;
        }
        return info;
    }

    public long UpdateWellInspectionDetail(WellInspectionEntity entity) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put("Block_Code", entity.getBlockID());
            values.put("Block_Name", entity.getBlockName());
            values.put("DistrictCode", entity.getDistID());
            values.put("DistName", entity.getDistName());

            values.put("Panchayat_Code", entity.getPanchayat_Code());
            values.put("Panchayat_Name", entity.getPanchayat_Name());
            values.put("Village_Id", entity.getVillageID());
            values.put("Village_Name", entity.getVillageName());

            values.put("Khata_Khesra_No", entity.getKhata_Khesra_No());
            values.put("Private_or_Public", entity.getPrivate_or_Public());
            values.put("Outside_Diamter", entity.getOutside_Diamter());
            values.put("Name_Of_Undertaken_Dept", entity.getName_of_undertaken());
            values.put("Requirement_Of_Flyer", entity.getRequirement_Of_Flyer());
            values.put("WellAvblValue", entity.getWellAvblValue());
            values.put("Status_of_Encroachment", entity.getStatus_of_Encroachment());
            values.put("Types_of_Encroachment", entity.getTypes_of_Encroachment());
            values.put("Requirement_of_Renovation", entity.getRequirement_of_Renovation());
            values.put("WellAvblValue", entity.getWellAvblValue());
            values.put("Requirement_of_machine", entity.getRequirement_of_machine());
            values.put("Remarks", entity.getRemarks());
            values.put("isUpdated", entity.getIsUpdated());
            values.put("DistrictCode", entity.getDistID());
            values.put("Photo3", entity.getWellOwnerOtherDeptName());
            values.put("Verified_Date", entity.getVerified_Date());
            values.put("structureId", entity.getStructureId());
            values.put("functionalStatus", entity.getFunctional());

            String[] whereArgs = new String[]{String.valueOf(entity.getId())};

            if (entity.getId() == 0){
                c = db.insert("WellInspectionDetail", null, values);
            }else{
                c = db.update("WellInspectionDetail", values, "id=? ", whereArgs);
            }

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public long UpdateEncrhmntDetail(PondEncroachmentEntity entity, String type) {
        String tableName = type.equals("pond") ? "CoPondEncroachmentReport" : "CoWellEncroachmentReport";
        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values = new ContentValues();

            if(entity.getUploadType().equals("R")){
                values.put("EnchrochmentStartDate", entity.getEnchrochmentStartDate());
                values.put("EnchrochmentEndDate", entity.getEnchrochmentEndDate());
                values.put("NoticeDate", entity.getNoticeDate());
                values.put("NoticeNo", entity.getNoticeNo());
            }else{
                values.put("Status_Of_Encroachment", entity.getStatus_Of_Encroachment());
                values.put("Type_Of_Encroachment", entity.getType_Of_Encroachment());
            }

            values.put("Verified_By", entity.getVerified_By());
            values.put("Verified_Date", entity.getVerified_Date());
            values.put("AppVersion", entity.getAppVersion());
            values.put("UploadType", entity.getUploadType());
            values.put("isUpdated", entity.getIsUpdated());

            String[] whereArgs = new String[]{String.valueOf(entity.getId())};

            c = db.update(tableName, values, "id=? ", whereArgs);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public long UpdatePondMarkedEncrhmntDetail(PondEncroachmentEntity entity) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put("Status_Of_Encroachment", entity.getStatus_Of_Encroachment());
            values.put("Type_Of_Encroachment", entity.getType_Of_Encroachment());
            values.put("Verified_By", entity.getVerified_By());
            values.put("Verified_Date", entity.getVerified_Date());
            values.put("AppVersion", entity.getAppVersion());
            values.put("UploadType", entity.getUploadType());
            values.put("isUpdated", entity.getIsUpdated());

            String[] whereArgs = new String[]{String.valueOf(entity.getId())};

                c = db.update("CoPondEncroachmentReport", values, "id=? ", whereArgs);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public long UpdatePondInspectionDetail(PondInspectionEntity entity) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put("Block_Code", entity.getBlockID());
            values.put("Block_Name", entity.getBlockName());
            values.put("DistrictCode", entity.getDistID());
            values.put("DistName", entity.getDistName());

            values.put("Panchayat_Code", entity.getPanchayat_Code());
            values.put("Panchayat_Name", entity.getPanchayat_Name());
            values.put("Village_Id", entity.getVillageID());
            values.put("Village_Name", entity.getVillageName());

            values.put("Khata_Khesra_No", entity.getKhata_Khesra_No());
            values.put("Private_or_Public", entity.getPrivate_or_Public());
            values.put("Area_by_Govt_Record", entity.getArea_by_Govt_Record());
            values.put("Name_of_undertaken", entity.getName_of_undertaken());
            values.put("Connected_with_Pine", entity.getConnectedWithPine());
            values.put("Availability_Of_Water", entity.getAvailability_Of_Water());
            values.put("Status_of_Encroachment", entity.getStatus_of_Encroachment());
            values.put("Types_of_Encroachment", entity.getTypes_of_Encroachment());
            values.put("Requirement_of_Renovation", entity.getRequirement_of_Renovation());
            values.put("Recommended_Execution_Dept", entity.getRecommended_Execution_Dept());
            values.put("Requirement_of_machine", entity.getRequirement_of_machine());
            values.put("Remarks", entity.getRemarks());
            values.put("isUpdated", entity.getIsUpdated());

            values.put("PondAvblValue", entity.getPondAvblValue());
            values.put("PondCatValue", entity.getPondCatValue());
            values.put("AbyabId", entity.getAbyabID());
            values.put("AbyabName", entity.getAbyabName());
            values.put("Verified_Date", entity.getVerified_Date());
            values.put("Photo3", entity.getPondCatName());
            values.put("Photo4", entity.getPondOwnerOtherDeptName());
            values.put("DeptId", entity.getDeptId());
            values.put("functionalStatus", entity.getFunctionalStatus());

            String[] whereArgs = new String[]{String.valueOf(entity.getId())};

            if (entity.getId() == 0){
                c = db.insert("PondInspectionDetail", null, values);
            }else{
                c = db.update("PondInspectionDetail", values, "id=? ", whereArgs);
            }



            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public long InsertManreagaSchemeDetail(ManregaSchemeDetail entity) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put("Execution_DeptID", entity.getExecution_Dept_ID());
            values.put("ExectDeptName", entity.getExectDeptName());
            values.put("Sub_DivisionID", entity.getSub_Exect_Dept_ID());
            values.put("SubDivName", entity.getSubDivName());
            values.put("Sub_SubDivisionID", entity.getSub_Sub_Exect_Dept_ID());
            values.put("Sub_DivisionValue", entity.getSub_Exect_Value());

            values.put("DistrictID", entity.getDistrict_Id());
            values.put("DistrictName", entity.getDistrict_Name());
            values.put("BlockID", entity.getBlock_ID());
            values.put("BlockName", entity.getBlock_Name());
            values.put("PanchayatID", entity.getPanchayat_Id());
            values.put("PanchayatName", entity.getPanchayatName());
            values.put("WardID", entity.getWard_Id());
            values.put("Ward_Name", entity.getWard_Name());
            values.put("Work_Structure_Type", entity.getWork_Structure_Type());
            values.put("Work_Structure_Type_Name", entity.getWork_Structure_Type_Name());
            values.put("Work_Structure_Type_Other_Name", entity.getWork_Structure_Type_Other_Name());
            values.put("Measurement_Of_Structure", entity.getMeasurement_Of_Structure());
            values.put("Estimated_Amount", entity.getEstimated_Amount());
            values.put("Approval_Date", entity.getApproval_Date());
            values.put("Administrative_Approval_Date", entity.getAdministrative_Approval_Date());
            values.put("MIS_Scheme_Code", entity.getScheme_Code());
            values.put("Remarks", entity.getRemarks());
            values.put("CreatedBy", entity.getCreatedBy());
            values.put("CreatedDate", entity.getCreatedDate());
            values.put("App_Version", entity.getApp_Version());
            values.put("VillageID", entity.getVillage_Id());
            values.put("Other_Name", entity.getOther_Name());


            String[] whereArgs = new String[]{String.valueOf(entity.getId())};


            if(entity.getId() > 0){
                c = db.update("Menrega_Rural_Dev_Dept", values, "id=? ", whereArgs);
            }else {
                c = db.insert("Menrega_Rural_Dev_Dept", null, values);
            }


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public long InsertOtherSchemeDetail(ManregaSchemeDetail entity) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put("Execution_DeptID", entity.getExecution_Dept_ID());
            values.put("ExectDeptName", entity.getExectDeptName());
            values.put("Sub_Exec_DeptID", entity.getSub_Exect_Dept_ID());
            values.put("SubDivName", entity.getSubDivName());
            values.put("Sub_Sub_Exec_DeptID", entity.getSub_Sub_Exect_Dept_ID());
            values.put("Sub_Sub_Exec_DeptName", entity.getSub_Sub_Exec_DeptName());
            values.put("Sub_ExeValue", entity.getSub_Exect_Value());

            values.put("DistrictID", entity.getDistrict_Id());
            values.put("DistrictName", entity.getDistrict_Name());
            values.put("BlockID", entity.getBlock_ID());
            values.put("BlockName", entity.getBlock_Name());
            values.put("PanchayatID", entity.getPanchayat_Id());
            values.put("PanchayatName", entity.getPanchayatName());
            values.put("Urban_AreaID", entity.getUrban_Area_Id());
            values.put("Urban_AreaName", entity.getUrban_AreaName());
            values.put("WardID", entity.getWard_Id());
            values.put("Ward_Name", entity.getWard_Name());
            values.put("Work_Structure_Type", entity.getWork_Structure_Type());
            values.put("Work_Structure_Type_Name", entity.getWork_Structure_Type_Name());
            values.put("Work_Structure_Type_Other_Name", entity.getWork_Structure_Type_Other_Name());
            values.put("Measurement_Of_Structure", entity.getMeasurement_Of_Structure());
            values.put("Estimated_Amount", entity.getEstimated_Amount());
            //values.put("Approval_Date", entity.getApproval_Date());
            values.put("Administrative_Approval_Date", entity.getAdministrative_Approval_Date());
            values.put("Scheme_Code", entity.getScheme_Code());
            values.put("Remarks", entity.getRemarks());
            values.put("CreatedBy", entity.getCreatedBy());
            values.put("CreatedDate", entity.getCreatedDate());
            values.put("App_Version", entity.getApp_Version());
            values.put("SahariNikayeId", entity.getSahariNikayeId());
            values.put("VillageID", entity.getVillage_Id());

            String[] whereArgs = new String[]{String.valueOf(entity.getId())};


            if(entity.getId() > 0){
                c = db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=? ", whereArgs);
            }else {
                c = db.insert("OtherDept_Of_Rural_Dev_Dept", null, values);
            }


            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public long UpdateSanrachnaInspectionDetail(SanrachnaDataEntity entity) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put("Photo1", entity.getImage1());
            values.put("Photo2", entity.getImage2());
            values.put("RemarksCompl", entity.getRemarksCompl());
            values.put("Verified_By", entity.getVerified_By());
            values.put("Verified_Date", entity.getVerified_Date());
            values.put("Longitude_Mob", entity.getLongitude_Mob());
            values.put("Latitude_Mob", entity.getLatitude_Mob());
            values.put("AppVersion", entity.getAppversion());
            values.put("isUpdated", entity.getIsUpdated());


            String[] whereArgs = new String[]{String.valueOf(entity.getId())};

            c = db.update("SanrachnaProgressData", values, "id=? ", whereArgs);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public long UpdateGPSCount(String gpsCount, String UserId) {

        long c = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            ContentValues values = new ContentValues();
            values.put("TotBuilPic", gpsCount);

            String[] whereArgs = new String[]{UserId};

            c = db.update("UserLogin", values, "UserID=? ", whereArgs);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
            return 0;
        }
        return c;

    }

    public long resetNursuryBuildingUpdatedData(String insepectionId){
        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(insepectionId)};
            c = db.delete("NursuryBuildingDetail", "id=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long deleteRemarkUpdatedData(String insepectionId){
        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(insepectionId)};
            c = db.delete("StructureRemarkUpdateDetail", "InspectionID=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long resetWellInspectionUpdatedData(String insepectionId){
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(insepectionId)};
            c = db.delete("WellInspectionDetail", "id=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long resetEncrhmntUpdatedData(String id, String type){
        String tableName = type.equals("pond") ? "CoPondEncroachmentReport" : "CoWellEncroachmentReport";
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(id)};
            c = db.delete(tableName, "id=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long resetPondInspectionUpdatedData(String insepectionId){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(insepectionId)};
            c = db.delete("PondInspectionDetail", "id=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long resetSanrachnaUpdatedData(String insepectionId){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(insepectionId)};
            c = db.delete("SanrachnaProgressData", "id=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long deleteManregadData(String insepectionId, int phaseType){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("isUpdated", "0");
            switch (phaseType){
                case 1:
                    values.put("IsPhase1Inspected", "N");
                    break;
                case 2:
                    values.put("IsPhase2Inspected", "N");
                    break;
                case 3:
                    values.put("IsPhase3Inspected", "N");
                    break;
            }

            String[] whereArgs = {String.valueOf(insepectionId)};

            //c = db.update("Menrega_Rural_Dev_Dept", values, "id=?", whereArgs);

            c = db.delete("Menrega_Rural_Dev_Dept", "id=?", whereArgs);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long resetManregadData(String insepectionId, int phaseType){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("isUpdated", "0");

            String[] whereArgs = {String.valueOf(insepectionId)};

            //if (phaseType == 3){
                c = db.delete("Menrega_Rural_Dev_Dept", "id=?", whereArgs);
//            }else{
//                c = db.update("Menrega_Rural_Dev_Dept", values, "id=?", whereArgs);
//            }


            //c = db.delete("Menrega_Rural_Dev_Dept", "id=?", whereArgs);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long deleteOtherSchemeData(String insepectionId, int phaseType){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("isUpdated", "0");
            switch (phaseType){
                case 1:
                    values.put("IsPhase1Inspected", "N");
                    break;
                case 2:
                    values.put("IsPhase2Inspected", "N");
                    break;
                case 3:
                    values.put("IsPhase3Inspected", "N");
                    break;
            }

            String[] whereArgs = {String.valueOf(insepectionId)};
            c = db.delete("OtherDept_Of_Rural_Dev_Dept", "id=?", whereArgs);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long resetOtherDeptData(String insepectionId, int phaseType){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("isUpdated", "0");

            String[] whereArgs = {String.valueOf(insepectionId)};

            //if (phaseType == 3){
                c = db.delete("OtherDept_Of_Rural_Dev_Dept", "id=?", whereArgs);
//            }else{
//                c = db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=?", whereArgs);
//            }


            //c = db.delete("Menrega_Rural_Dev_Dept", "id=?", whereArgs);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long setPondLakeWellEncrhmntDataToLocal(ArrayList<PondEncroachmentEntity> list, String type) {
        String tableName = type == "pond" ? "CoPondEncroachmentReport" : "CoWellEncroachmentReport";

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<PondEncroachmentEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("InspectionID", info.get(i).getInspectionID());
                    values.put("DistID", info.get(i).getDistID());
                    values.put("DistName", info.get(i).getDistName());
                    values.put("BlockID", info.get(i).getBlockID());
                    values.put("BlockName", info.get(i).getBlockName());
                    values.put("RajswaThanaNumber", info.get(i).getRajswaThanaNumber());
                    values.put("VillageID", info.get(i).getVillageID());
                    values.put("VILLNAME", info.get(i).getVILLNAME());
                    values.put("Latitude", info.get(i).getLatitude());
                    values.put("Longitude", info.get(i).getLongitude());
                    values.put("PanchayatID", info.get(i).getPanchayatID());
                    values.put("PanchayatName", info.get(i).getPanchayatName());
                    values.put("Khaata_Kheshara_Number", info.get(i).getKhaata_Kheshara_Number());
                    values.put("Status_Of_Encroachment", info.get(i).getStatus_Of_Encroachment());
                    values.put("Type_Of_Encroachment", info.get(i).getType_Of_Encroachment());
                    values.put("Verified_By", info.get(i).getVerified_By());
                    values.put("IsInspected", info.get(i).getIsInspected());
                    values.put("Ench_Verified_By", info.get(i).getEnch_Verified_By());
                    values.put("EStatus", info.get(i).getEStatus());
                    values.put("isUpdated", "0");

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getInspectionID())};

                    c = db.update(tableName, values, "InspectionID=?", whereArgs);

                    if(c < 1){
                        c = db.insert(tableName, null, values);
                    }


                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }
    public long setPondMWRDataToLocal(ArrayList<PondInspectionEntity> list, String type) {
        String tableName = "PondInspectionDetail";

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<PondInspectionEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("InspectionID", info.get(i).getInspectionID());
                    values.put("DistrictCode", info.get(i).getDistID());
                    values.put("DistName", info.get(i).getDistName());
                    values.put("Block_Code", info.get(i).getBlockID());
                    values.put("Block_Name", info.get(i).getBlockName());
                    values.put("Rajswa_Thana_No", info.get(i).getRajswaThanaNumber());
                    values.put("Village_Id", info.get(i).getVillageID());
                    values.put("Village_Name", info.get(i).getVillageName());
                    values.put("Latitude", info.get(i).getLatitude());
                    values.put("Longitude", info.get(i).getLongitude());
                    values.put("Panchayat_Code", info.get(i).getPanchayat_Code());
                    values.put("Panchayat_Name", info.get(i).getPanchayat_Name());
                    values.put("Khata_Khesra_No", info.get(i).getKhata_Khesra_No());
                    values.put("PondCatValue", info.get(i).getPondCatValue());
                    values.put("Area_by_Govt_Record", info.get(i).getArea_by_Govt_Record());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getInspectionID())};

                    c = db.update(tableName, values, "InspectionID=?", whereArgs);

                    if(c < 1){
                        values.put("isUpdated", "0");
                        c = db.insert(tableName, null, values);
                    }


                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setPondLakeWellDataToLocal(ArrayList<PondEntity> list, String type, String structureId) {
        String tableName = type == "pond" ? "PondInspectionDetail" : "WellInspectionDetail";

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<PondEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("InspectionID", info.get(i).getInspectionID());
                    values.put("DistrictCode", info.get(i).getDistID());
                    values.put("DistName", info.get(i).getDistName());
                    values.put("Block_Code", info.get(i).getBlockID());
                    values.put("Block_Name", info.get(i).getBlockName());
                    values.put("Rajswa_Thana_No", info.get(i).getRajswaThanaNumber());
                    values.put("Village_Id", info.get(i).getVillageID());
                    values.put("Village_Name", info.get(i).getVillageName());
                    values.put("Latitude", info.get(i).getLatitude());
                    values.put("Longitude", info.get(i).getLongitude());
                    values.put("Panchayat_Code", info.get(i).getPanchayatID());
                    values.put("Panchayat_Name", info.get(i).getPanchayatName());
                    values.put("Khata_Khesra_No", info.get(i).getKhataKhesraNo());
                    values.put("structureId", structureId);
                    values.put("isUpdated", "0");

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getInspectionID())};

                    c = db.update(tableName, values, "InspectionID=?", whereArgs);

                    if(c < 1){
                        c = db.insert(tableName, null, values);
                    }


                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setWardDataToLocal(ArrayList<ward> list) {
       // String tableName = type == "pond" ? "PondInspectionDetail" : "WellInspectionDetail";

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<ward> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("WardName", info.get(i).getWardname().trim());
                    values.put("WardCode", info.get(i).getWardCode().trim());
                    values.put("PanchayatCode", info.get(i).getPanchayatCode().trim());
                    values.put("AreaType", info.get(i).getAreaType());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getWardCode())};

                    c = db.update("Ward", values, "WardCode=?", whereArgs);

                    if(c != 1){
                        c = db.insert("Ward", null, values);
                    }
                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setVillageDataToLocal(ArrayList<VillageListEntity> list) {
        // String tableName = type == "pond" ? "PondInspectionDetail" : "WellInspectionDetail";

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<VillageListEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("VillageCode", info.get(i).getVillCode());
                    values.put("VillageName", info.get(i).getVillName());
                    values.put("PanchayatCode", info.get(i).getPanchayatCode());
                    values.put("BLOCKCODE", info.get(i).getBlockCode());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getVillCode()),String.valueOf(info.get(i).getPanchayatCode())};

                    c = db.update("VillageList", values, "VillageCode=? AND PanchayatCode=?", whereArgs);

                    if(c != 1){
                        c = db.insert("VillageList", null, values);
                    }

                    Log.e("Panchayat", info.get(i).getPanchayatCode());
                    Log.e("VillageCode", info.get(i).getVillCode());
                    Log.e("C", String.valueOf(c));

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setExecDeptDataToLocal(ArrayList<DepartmentEntity> list) {

        long c = -1;

        if (list != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < list.size(); i++) {

                    values.put("DeptId", list.get(i).getDeptId());
                    values.put("DeptName", list.get(i).getDeptName());
                    values.put("DeptNameHn", list.get(i).getDeptNameHn());
                    values.put("isActive", list.get(i).getIsActive());

                    values.put("structure", list.get(i).getStructure());
                    values.put("scheme", list.get(i).getScheme());
                    values.put("wellChapakal", list.get(i).getWellChapakal());
                    values.put("building", list.get(i).getBuilding());
                    values.put("nursery", list.get(i).getNursery());

                    String[] whereArgs = new String[]{String.valueOf(list.get(i).getDeptId())};

                    c = db.update("DepartmentList", values, "DeptId=?", whereArgs);

                    if(c != 1){
                        c = db.insert("DepartmentList", null, values);
                    }
                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                //return c;
            }
        }
        return c;
    }

    public long setDepartmentDataToLocal(ArrayList<DepartmentDetail> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<DepartmentDetail> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("id", info.get(i).getId());
                    values.put("_DepatmentName", info.get(i).getDepartmentName());
                    values.put("_DepatmentHNd", info.get(i).getDepartmentNameHnd());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getId())};

                    c = db.update("PondLakeDept", values, "id=?", whereArgs);

                    if(c != 1){
                        c = db.insert("PondLakeDept", null, values);
                    }

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setTreeDetailDataToLocal(ArrayList<TreesDetail> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<TreesDetail> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("TreeId", info.get(i).getTreeId());
                    values.put("TreeNameEng", info.get(i).getTreeEng());
                    values.put("TreeNameHnd", info.get(i).getTreeHnd());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getTreeId())};

                    c = db.update("TreesDetail", values, "TreeId=?", whereArgs);

                    if(c != 1){
                        c = db.insert("TreesDetail", null, values);
                    }

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setAbyabPublicDataToLocal(ArrayList<Abyab> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Abyab> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                db.delete("AbyabPublic",  null, null);

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("abyabId", info.get(i).getAbyab_Code());
                    values.put("abyabName", info.get(i).getAbyab_name());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getAbyab_Code())};

                    c = db.update("AbyabPublic", values, "abyabId=?", whereArgs);

                    if(c != 1){
                        c = db.insert("AbyabPublic", null, values);
                    }

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setAbyabDataToLocal(ArrayList<Abyab> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<Abyab> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                db.delete("SubExecutionDept",  null, null);

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("SubExecutionDept_Id", info.get(i).getAbyab_Code());
                    values.put("SubExecutionDept_Name", info.get(i).getAbyab_name());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getAbyab_Code())};

                    c = db.update("SubExecutionDept", values, "SubExecutionDept_Id=?", whereArgs);

                    if(c != 1){
                        c = db.insert("SubExecutionDept", null, values);
                    }

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setSanrachnaTypeDataToLocal(ArrayList<SanrachnaTypeEntity> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<SanrachnaTypeEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("PacsID", info.get(i).getSancrachnaId());
                    values.put("PacsName", info.get(i).getSancrachnaName());
                    values.put("BlockCode", info.get(i).getSub_Execution_DeptID());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getSancrachnaId())};

                    c = db.update("PACS", values, "PacsID=?", whereArgs);

                    if(c < 1){
                        c = db.insert("PACS", null, values);
                    }



                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setStructureTypeDataToLocal(ArrayList<StructureType> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<StructureType> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("StrID", info.get(i).getStrID());
                    values.put("StrName", info.get(i).getStrName());
                    values.put("StrCode", info.get(i).getStrCode());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getStrID())};

                    c = db.update("StructureType", values, "StrID=?", whereArgs);

                    if(c < 1){
                        c = db.insert("StructureType", null, values);
                    }



                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setManregaInspDataToLocal(ArrayList<ManregaSchemeDetail> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<ManregaSchemeDetail> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {
                    String[] dateArray = info.get(i).getAdministrative_Approval_Date().split(" ");
                    String[] approvalDateArray = info.get(i).getApproval_Date().split(" ");

                    String date = info.get(i).getAdministrative_Approval_Date();
                    String approvalDate = info.get(i).getApproval_Date();

                    if(dateArray.length > 0){
                        date = dateArray[0];
                    }

                    if(approvalDateArray.length > 0){
                        approvalDate = approvalDateArray[0];
                        if(approvalDate.equals("01/01/1900")){
                            approvalDate = "NA";
                        }
                    }

                    values.put("id", info.get(i).getId());
                    values.put("Execution_DeptID", info.get(i).getExecution_Dept_ID());
                    values.put("Sub_SubDivisionID", info.get(i).getSub_Sub_Exect_Dept_ID());
                    values.put("Sub_DivisionValue", info.get(i).getSub_Exect_Value());
                    values.put("DistrictID", info.get(i).getDistrict_Id());
                    values.put("DistrictName", info.get(i).getDistrict_Name());
                    values.put("BlockID", info.get(i).getBlock_ID());
                    values.put("BlockName", info.get(i).getBlock_Name());
                    values.put("PanchayatID", info.get(i).getPanchayat_Id());
                    values.put("PanchayatName", info.get(i).getPanchayatName());
                    values.put("WardID", info.get(i).getWard_Id());
                    values.put("Latitude", info.get(i).getLatitude());
                    values.put("Longitude", info.get(i).getLongitude());
                    values.put("Work_Structure_Type", info.get(i).getWork_Structure_Type());
                    values.put("Measurement_Of_Structure", info.get(i).getMeasurement_Of_Structure());
                    values.put("Estimated_Amount", info.get(i).getEstimated_Amount());
                    values.put("Approval_Date", approvalDate);
                    values.put("Administrative_Approval_Date", date);
                    values.put("MIS_Scheme_Code", info.get(i).getScheme_Code());
                    values.put("Remarks", info.get(i).getRemarks());
                    values.put("CreatedBy", info.get(i).getCreatedBy());
                    values.put("CreatedDate", info.get(i).getCreatedDate());
                    values.put("App_Version", info.get(i).getApp_Version());
                    values.put("ExectDeptName", info.get(i).getExectDeptName());
                    values.put("SubDivName", info.get(i).getSubDivName());
                    values.put("Ward_Name", info.get(i).getWard_Name());
                    values.put("Sub_SubDivisionName", info.get(i).getSub_Sub_Exec_DeptName());
                    values.put("Work_Structure_Type_Name", info.get(i).getWork_Structure_Type_Name());
                    values.put("Other_Name", info.get(i).getOther_Name());
                    values.put("Work_Structure_Type_Other_Name", info.get(i).getWork_Structure_Type_Other_Name());
                    values.put("VillageID", info.get(i).getVillage_Id());
                    values.put("isUpdated", "0");
                    values.put("Types_OfSarchnaId", info.get(i).getTypes_OfSarchnaId());
                    values.put("Types_OfSarchnaName", info.get(i).getTypes_OfSarchnaName());
                    values.put("IsPhase1Inspected", info.get(i).getIsPhase1Inspected());
                    values.put("IsPhase1InspBy", info.get(i).getIsPhase1InspBy());
                    values.put("IsPhase1InspDate", info.get(i).getIsPhase1InspDate());
                    //values.put("IsPhase1InspPhoto1", info.get(i).getSub_Execution_DeptID());
                    //values.put("IsPhase1InspPhoto2", info.get(i).getSub_Execution_DeptID());
//                    values.put("IsPhase1InspLatitude", info.get(i).getIsPhase1InspLatitude());
//                    values.put("IsPhase1InspLongitude", info.get(i).getIsPhase1InspLongitude());
//                    values.put("IsPhase1InspRemarks", info.get(i).getIsPhase1InspRemarks());
                    values.put("IsPhase2Inspected", info.get(i).getIsPhase2Inspected());
                    values.put("IsPhase2InspBy", info.get(i).getIsPhase2InspBy());
                    values.put("IsPhase2InspDate", info.get(i).getIsPhase2InspDate());
                    //values.put("IsPhase2InspPhoto1", info.get(i).getIsPhase2Ins());
                    //values.put("IsPhase2InspPhoto2", info.get(i).getSub_Execution_DeptID());
//                    values.put("IsPhase2InspLatitude", info.get(i).getIsPhase2InspLatitude());
//                    values.put("IsPhase2InspLongitude", info.get(i).getIsPhase2InspLongitude());
//                    values.put("IsPhase2InspRemarks", info.get(i).getIsPhase2InspRemarks());
                    values.put("IsPhase3Inspected", info.get(i).getIsPhase3Inspected());
                    values.put("IsPhase3InspBy", info.get(i).getIsPhase3InspBy());
                    values.put("IsPhase3InspDate", info.get(i).getIsPhase3InspDate());
                    //values.put("IsPhase3InspPhoto1", info.get(i).getSub_Execution_DeptID());
                    //values.put("IsPhase3InspPhoto2", info.get(i).getSub_Execution_DeptID());
//                    values.put("IsPhase3InspLatitude", info.get(i).getIsPhase3InspLatitude());
//                    values.put("IsPhase3InspLongitude", info.get(i).getIsPhase3InspLongitude());
//                    values.put("IsPhase3InspRemarks", info.get(i).getIsPhase3InspRemarks());
                    values.put("IsFinalInspected", info.get(i).getIsFinalInspected());
                    values.put("YojnaType", info.get(i).getYojnaType());
                    values.put("AbyabId", info.get(i).getAwayabId());

                    values.put("Khaata_Kheshara_Number", info.get(i).getKhaata_Kheshara_Number());
                    values.put("Rakba", info.get(i).getRakba());
                    values.put("LandOwnerMob", info.get(i).getLandOwnerMob());
                    values.put("LandOwnerName", info.get(i).getLandOwnerName());
                    values.put("FencingLatitude1", info.get(i).getFencingLatitude1());
                    values.put("FencingLatitude2", info.get(i).getFencingLatitude2());
                    values.put("FencingLatitude3", info.get(i).getFencingLatitude3());
                    values.put("FencingLatitude4", info.get(i).getFencingLatitude4());
                    values.put("FencingLongitude1", info.get(i).getFencingLongitude1());
                    values.put("FencingLongitude2", info.get(i).getFencingLongitude2());
                    values.put("FencingLongitude3", info.get(i).getFencingLongitude3());
                    values.put("FencingLongitude4", info.get(i).getFencingLongitude4());
                    values.put("IsLandEnterDt", info.get(i).getIsLandEnterDt());

                    values.put("ConsumerNo", info.get(i).getConsumerNo());
                    values.put("ConsumrBill", info.get(i).getConsumrBill());
                    values.put("TreeType", info.get(i).getTreeType());

                    if(info.get(i).getYojnaType().equals("U")){
                        values.put("IsPhase1Inspected", "Y");
                        values.put("IsPhase2Inspected", "Y");
                    }

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getId())};

                    c = db.update("Menrega_Rural_Dev_Dept", values, "id=?", whereArgs);

                    if(c < 1){
                        c = db.insert("Menrega_Rural_Dev_Dept", null, values);
                    }



                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setRemarkDataToLocal(ArrayList<StructureRemarkEntity> list, String strType) {

        long c = -1;

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            ContentValues values = new ContentValues();

            for(StructureRemarkEntity info: list){

                values.put("InspectionID", info.getInspectionID());
                values.put("DistrictCode", info.getDist_Code());
                values.put("DistName", info.getDist_Name());
                values.put("BlockCode", info.getBlock_Code());
                values.put("BlockName", info.getBlock_Name());
                values.put("PanchayatCode", info.getPanchayat_Code());
                values.put("PanchayatName", info.getPanchayat_Name());
                values.put("VILLNAME", info.getVillage_Name());
                values.put("VILLCODE", info.getVillage_Code());
                values.put("RajswaThanaNumber", info.getRajswaThanaNumber());
                values.put("Khaata_Kheshara_Number", info.getKhaata_Kheshara_Number());
                values.put("Latitude", info.getLatitude());
                values.put("Longitude", info.getLongitude());
                values.put("StrID", info.getStrID());
                values.put("TypesOfSarchna", info.getTypesOfSarchna());
                values.put("Remarks", info.getRemark());
                values.put("StrType", strType);

                String[] whereArgs = new String[]{String.valueOf(info.getInspectionID())};

                c = db.update("StructureRemarkUpdateDetail", values, "InspectionID=?", whereArgs);

                if(c < 1){
                    values.put("IsUpdate", "0");
                    c = db.insert("StructureRemarkUpdateDetail", null, values);
                }
            }
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            return -2;
        }
        return c;
    }

    public long setOtherInspDataToLocal(ArrayList<OtherDeptInsptEntity> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<OtherDeptInsptEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {
                    String[] dateArray = info.get(i).getAdministrative_Approval_Date().split(" ");
                    String date = info.get(i).getAdministrative_Approval_Date();

                    if(dateArray.length > 0){
                        date = dateArray[0];
                    }

                    values.put("id", info.get(i).getId());
                    values.put("Execution_DeptID", info.get(i).getExecution_Dept_ID());
                    values.put("Sub_Exec_DeptID", info.get(i).getSub_Exect_Dept_ID());
                    values.put("Sub_Sub_Exec_DeptID", info.get(i).getSub_Sub_Exect_Dept_ID());
                    values.put("Sub_ExeValue", info.get(i).getSub_Exect_Value());
                    values.put("DistrictID", info.get(i).getDistrict_Id());
                    values.put("DistrictName", info.get(i).getDistrict_Name());
                    values.put("BlockID", info.get(i).getBlock_ID());
                    values.put("BlockName", info.get(i).getBlock_Name());
                    values.put("PanchayatID", info.get(i).getPanchayat_Id());
                    values.put("PanchayatName", info.get(i).getPanchayatName());
                    values.put("Urban_AreaID", info.get(i).getUrban_Area_Id());
                    values.put("Urban_AreaName", info.get(i).getUrban_AreaName());
                    values.put("WardID", info.get(i).getWard_Id());
                    values.put("Latitude", info.get(i).getLatitude());
                    values.put("Longitude", info.get(i).getLongitude());
                    values.put("Work_Structure_Type", info.get(i).getWork_Structure_Type());
                    values.put("Measurement_Of_Structure", info.get(i).getMeasurement_Of_Structure());
                    values.put("Estimated_Amount", info.get(i).getEstimated_Amount());
                    //values.put("Approval_Date", info.get(i).getApproval_Date());

                    values.put("Administrative_Approval_Date", date);
                    values.put("Scheme_Code", info.get(i).getScheme_Code());
                    values.put("Remarks", info.get(i).getRemarks());
                    values.put("CreatedBy", info.get(i).getCreatedBy());
                    values.put("CreatedDate", info.get(i).getCreatedDate());
                    values.put("App_Version", info.get(i).getApp_Version());
                    values.put("ExectDeptName", info.get(i).getExectDeptName());
                    values.put("SubDivName", info.get(i).getSubDivName());
                    values.put("Ward_Name", info.get(i).getWard_Name());
                    values.put("Sub_Sub_Exec_DeptName", info.get(i).getSub_Sub_Exec_DeptName());
                    values.put("Work_Structure_Type_Name", info.get(i).getWork_Structure_Type_Name());
                    values.put("Other_Name", info.get(i).getOther_Name());
                    values.put("Work_Structure_Type_Other_Name", info.get(i).getWork_Structure_Type_Other_Name());
                    values.put("VillageID", info.get(i).getVillage_Id());
                    values.put("VillageName", info.get(i).getVillage_Name());
                    values.put("SahariNikayeId", info.get(i).getSahariNikayeId());

                    values.put("isUpdated", "0");
                    values.put("Types_OfSarchnaId", info.get(i).getTypes_OfSarchnaId());
                    values.put("Types_OfSarchnaName", info.get(i).getTypes_OfSarchnaName());
                    values.put("IsPhase1Inspected", info.get(i).getIsPhase1Inspected());
                    values.put("IsPhase1InspBy", info.get(i).getIsPhase1InspBy());
                    values.put("IsPhase1InspDate", info.get(i).getIsPhase1InspDate());
                    //values.put("IsPhase1InspPhoto1", info.get(i).getSub_Execution_DeptID());
                    //values.put("IsPhase1InspPhoto2", info.get(i).getSub_Execution_DeptID());
//                    values.put("IsPhase1InspLatitude", info.get(i).getIsPhase1InspLatitude());
//                    values.put("IsPhase1InspLongitude", info.get(i).getIsPhase1InspLongitude());
//                    values.put("IsPhase1InspRemarks", info.get(i).getIsPhase1InspRemarks());
                    values.put("IsPhase2Inspected", info.get(i).getIsPhase2Inspected());
                    values.put("IsPhase2InspBy", info.get(i).getIsPhase2InspBy());
                    values.put("IsPhase2InspDate", info.get(i).getIsPhase2InspDate());
                    //values.put("IsPhase2InspPhoto1", info.get(i).getIsPhase2Ins());
                    //values.put("IsPhase2InspPhoto2", info.get(i).getSub_Execution_DeptID());
//                    values.put("IsPhase2InspLatitude", info.get(i).getIsPhase2InspLatitude());
//                    values.put("IsPhase2InspLongitude", info.get(i).getIsPhase2InspLongitude());
//                    values.put("IsPhase2InspRemarks", info.get(i).getIsPhase2InspRemarks());
                    values.put("IsPhase3Inspected", info.get(i).getIsPhase3Inspected());
                    values.put("IsPhase3InspBy", info.get(i).getIsPhase3InspBy());
                    values.put("IsPhase3InspDate", info.get(i).getIsPhase3InspDate());
                    //values.put("IsPhase3InspPhoto1", info.get(i).getSub_Execution_DeptID());
                    //values.put("IsPhase3InspPhoto2", info.get(i).getSub_Execution_DeptID());
//                    values.put("IsPhase3InspLatitude", info.get(i).getIsPhase3InspLatitude());
//                    values.put("IsPhase3InspLongitude", info.get(i).getIsPhase3InspLongitude());
//                    values.put("IsPhase3InspRemarks", info.get(i).getIsPhase3InspRemarks());
                    values.put("IsFinalInspected", info.get(i).getIsFinalInspected());
                    values.put("YojnaType", info.get(i).getYojnaType());
                    values.put("AbyabId", info.get(i).getAwayabId());

                    values.put("Khaata_Kheshara_Number", info.get(i).getKhaata_Kheshara_Number());
                    values.put("Rakba", info.get(i).getRakba());
                    values.put("LandOwnerMob", info.get(i).getLandOwnerMob());
                    values.put("LandOwnerName", info.get(i).getLandOwnerName());
                    values.put("FencingLatitude1", info.get(i).getFencingLatitude1());
                    values.put("FencingLatitude2", info.get(i).getFencingLatitude2());
                    values.put("FencingLatitude3", info.get(i).getFencingLatitude3());
                    values.put("FencingLatitude4", info.get(i).getFencingLatitude4());
                    values.put("FencingLongitude1", info.get(i).getFencingLongitude1());
                    values.put("FencingLongitude2", info.get(i).getFencingLongitude2());
                    values.put("FencingLongitude3", info.get(i).getFencingLongitude3());
                    values.put("FencingLongitude4", info.get(i).getFencingLongitude4());
                    values.put("IsLandEnterDt", info.get(i).getIsLandEnterDt());

                    values.put("ConsumerNo", info.get(i).getConsumerNo());
                    values.put("ConsumrBill", info.get(i).getConsumrBill());
                    values.put("TreeType", info.get(i).getTreeType());

                    if(info.get(i).getYojnaType().equals("U")){
                        values.put("IsPhase1Inspected", "Y");
                        values.put("IsPhase2Inspected", "Y");
                    }

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getId())};

                    c = db.update("OtherDept_Of_Rural_Dev_Dept", values, "id=?", whereArgs);

                    if(c < 1){
                        c = db.insert("OtherDept_Of_Rural_Dev_Dept", null, values);
                    }



                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setSanrachnaProgressDataToLocal(ArrayList<SanrachnaDataEntity> list) {

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<SanrachnaDataEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    //values.put("id", info.get(i).getId());
                    values.put("InspectionID", info.get(i).getInspectionID());
                    values.put("Distcode", info.get(i).getDistcode());
                    values.put("DistName", info.get(i).getDistName());
                    values.put("BlockCode", info.get(i).getBlockCode());
                    values.put("BlockName", info.get(i).getBlockName());
                    values.put("PanchayatCode", info.get(i).getPanchayatCode());
                    values.put("PanchayatName", info.get(i).getPanchayatName());
                    values.put("RajswaThanaNumber", info.get(i).getRajswaThanaNumber());
                    values.put("DepatmentName", info.get(i).getDepatmentName());
                    values.put("Khaata_Kheshara_Number", info.get(i).getKhaata_Kheshara_Number());
                    values.put("VILLCODE", info.get(i).getVILLCODE());
                    values.put("VILLNAME", info.get(i).getVILLNAME());
                    values.put("Latitude", info.get(i).getLatitude());
                    values.put("Longitude", info.get(i).getLongitude());
                    values.put("Commercial_Public", info.get(i).getCommercial_Public());
                    values.put("TypesOfSarchna", info.get(i).getTypesOfSarchna());
                    values.put("Swamitw_Dep", info.get(i).getSwamitw_Dep());
                    values.put("Remarks", info.get(i).getRemarks());
                    values.put("IsNewEntryPond", info.get(i).getIsNewEntryPond());
                    values.put("SwamitwDep_Name", info.get(i).getSwamitwDep_Name());
                    values.put("Area_By_Govt_Record", info.get(i).getArea_By_Govt_Record());
                    values.put("Connected_With_Pine", info.get(i).getConnected_With_Pine());
                    values.put("Availability_Of_Water", info.get(i).getAvailability_Of_Water());
                    values.put("Status_Of_Encroachment", info.get(i).getStatus_of_Encroachment());
                    values.put("Type_Of_Encroachment", info.get(i).getTypes_of_Encroachment());
                    values.put("Requirement_of_Renovation", info.get(i).getRequirement_of_Renovation());
                    values.put("Requirement_of_machine", info.get(i).getRequirement_of_machine());
                    values.put("isUpdated", "0");
                    //values.put("Approval_Date", info.get(i).getApproval_Date());


                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getId())};

                    c = db.update("SanrachnaProgressData", values, "id=?", whereArgs);

                    if(c < 1){
                        c = db.insert("SanrachnaProgressData", null, values);
                    }



                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setPanchayatDataToLocal(UserDetails userInfo, ArrayList<PanchayatEntity> list) {
        // String tableName = type == "pond" ? "PondInspectionDetail" : "WellInspectionDetail";

        long c = -1;

        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<PanchayatEntity> info = list;


        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("PanchayatCode", info.get(i).getPcode());
                    values.put("PanchayatName", info.get(i).getPname());
                    values.put("PACName", info.get(i).getAreaType());

                    values.put("BlockCode", userInfo.getBlockCode());
                    //values.put("Block Name", userInfo.getBlockName());
                    values.put("DistrictCode", userInfo.getDistrictCode());
                    values.put("DistrictName", userInfo.getDistName());
                    values.put("PartNo", "2");

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getPcode())};

                    c = db.update("Panchayat", values, "PanchayatCode=?", whereArgs);

                    if(c != 1){
                        c = db.insert("Panchayat", null, values);
                    }



                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }
    public long insertPanchayatist(ArrayList<PanchayatData> result,String distCode,String blockCode) {

        long c = -1;
        try {


            SQLiteDatabase db = this.getWritableDatabase();
            //db.execSQL("Delete from Panchayat");
            // "SHG" ( `SHG_ID` TEXT, `SHG_NAME` TEXT, `PG_ID` TEXT, `PG_NAME` TEXT, `ID` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `Village_Code` TEXT, `Village_Name` TEXT )
            for (PanchayatData SHGType : result) {


                ContentValues values = new ContentValues();
                values.put("PanchayatCode", SHGType.getPcode().trim());
                values.put("PanchayatName", SHGType.getPname().trim());
                values.put("PACName", SHGType.getAreaType().trim());
                values.put("DistrictCode", distCode);
                values.put("BlockCode", blockCode);
                values.put("PartNo", SHGType.getAreaType());
                values.put("AreaType", SHGType.getAreaType());

                String[] whereArgs = new String[]{SHGType.getPcode()};
                c = db.update("Panchayat", values, "PanchayatCode=?", whereArgs);
                if (!(c > 0)) {
                    c = db.insert("Panchayat", null, values);
                }

            }
            db.close();
            getWritableDatabase().close();

        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }
        return c;
    }

    public long setFieldInfoLocal(ArrayList<FieldInformation> list) {


        long c = -1;
        //WebServiceHelper webservice=new WebServiceHelper();
        //ArrayList<FieldInformation> info=webservice.getFieldInformation();


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<FieldInformation> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (info != null) {
            try {
                for (int i = 0; i < info.size(); i++) {

                    values.put("Field", info.get(i).getField());
                    values.put("isActive", info.get(i).getisActive());
                    values.put("Label", info.get(i).getLabel());
                    values.put("Type", info.get(i).getType());
                    values.put("Sequence", Integer.parseInt(info.get(i).getSequence()));
                    c = db.insert("FieldMaster", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public String getWellEncrhmntUpdatedDataCount(){
        String pondCount = "0", wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curPond = db.rawQuery("Select * from CoWellEncroachmentReport WHERE isUpdated=?", params);

            pondCount = String.valueOf(curPond.getCount());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return pondCount;
    }

    public String getPondEncrhmntUpdatedDataCount(){
        String pondCount = "0", wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curPond = db.rawQuery("Select * from CoPondEncroachmentReport WHERE isUpdated=?", params);
            pondCount = String.valueOf(curPond.getCount());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return pondCount;
    }

    public String getPondLakeUpdatedDataCount(){
        //ArrayList<String> List = new ArrayList<String>();
        String pondCount = "0", wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor curPond = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            //Cursor curWell = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            pondCount = String.valueOf(curPond.getCount());
            //wellCount = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return pondCount;
    }

    public String getWellUpdatedDataCount(){
        String wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor curPond = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            Cursor curWell = db.rawQuery("Select * from WellInspectionDetail WHERE isUpdated=?", params);


            wellCount = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return wellCount;
    }

    public String getManregadDataCount(){
        String wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor curPond = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            Cursor curWell = db.rawQuery("Select * from Menrega_Rural_Dev_Dept WHERE isUpdated=?", params);


            wellCount = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return wellCount;
    }

    public String getOtherSchemeDataCount(){
        String wellCount = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor curPond = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            Cursor curWell = db.rawQuery("Select * from OtherDept_Of_Rural_Dev_Dept WHERE isUpdated=?", params);


            wellCount = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return wellCount;
    }

    public String getNursuryBuildingDataCount(String type){
        String wellCount = "0";
        String[] params = new String[]{"1", type};
        try{
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor curWell = db.rawQuery("Select * from NursuryBuildingDetail WHERE IsUpdate=? AND StrType=?", params);

            wellCount = String.valueOf(curWell.getCount());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return wellCount;
    }

    public String getUpdateRemarkDataCount(){
        String count = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db.rawQuery("Select id from StructureRemarkUpdateDetail WHERE IsUpdate=?", params);

            count = String.valueOf(cur.getCount());

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return count;
    }

    public String getSanrachnaProgressDataCount(){
        String count = "0";
        String[] params = new String[]{"1"};
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            //Cursor curPond = db.rawQuery("Select * from PondInspectionDetail WHERE isUpdated=?", params);

            Cursor curWell = db.rawQuery("Select * from SanrachnaProgressData WHERE isUpdated=?", params);


            count = String.valueOf(curWell.getCount());


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }

        return count;
    }


    public ArrayList<LocalSpinnerData> getAwcList(String sectorCode) {

        // PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
        ArrayList<LocalSpinnerData> awcList = new ArrayList<LocalSpinnerData>();
        Cursor cur;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            if(sectorCode == null || sectorCode.equalsIgnoreCase("")|| sectorCode.equalsIgnoreCase("NA") || sectorCode.equalsIgnoreCase("0")){
                 cur = db.rawQuery("Select * from Panchayat", null);
            }else {
                String[] params = new String[]{sectorCode};
                 cur = db.rawQuery("Select * from Panchayat WHERE Sector_Code=?", params);
            }

            int x = cur.getCount();

            while (cur.moveToNext()) {

                LocalSpinnerData localSpinnerData = new LocalSpinnerData();
                localSpinnerData.setCode((cur.getString(cur.getColumnIndex("slno"))));
                localSpinnerData.setValue(cur.getString(cur.getColumnIndex("Name")));

                awcList.add(localSpinnerData);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return awcList;

    }


    public ArrayList<LocalSpinnerData> getSectorList(String projectCode) {

        // PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
        ArrayList<LocalSpinnerData> sectorList = new ArrayList<LocalSpinnerData>();
        Cursor cur;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
               String[] params = new String[]{projectCode};
                cur = db.rawQuery("Select * from Sector WHERE Block_Code=?", params);


            int x = cur.getCount();

            while (cur.moveToNext()) {

                LocalSpinnerData localSpinnerData = new LocalSpinnerData();
                localSpinnerData.setCode((cur.getString(cur.getColumnIndex("Code"))));
                localSpinnerData.setValue(cur.getString(cur.getColumnIndex("Name")));

                sectorList.add(localSpinnerData);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return sectorList;

    }


    public long setSpinnerMasterDataLocal(ArrayList<SpinnerMaster> list) {


        long c = -1;


        DataBaseHelper dh = new DataBaseHelper(myContext);
        try {
            dh.createDataBase();


        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return -1;
        }

        ArrayList<SpinnerMaster> info = list;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (info != null) {
            try {


                for (int i = 0; i < info.size(); i++) {

                    values.put("Code", info.get(i).getCode());
                    values.put("Value", info.get(i).getValue());
                    values.put("Field", info.get(i).getField());

                    c = db.insert("SpinnerMaster", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;


    }

    public ArrayList<PondEntity> getWellsInspectionUpdatedDetail(){
        //PondInspectionDetail info = null;

        ArrayList<PondEntity> infoList = new ArrayList<PondEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from WellInspectionDetail WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                PondEntity info = new PondEntity();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));

                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockID(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));
                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                info.setVillageID(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setVillageName(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setStructureId(cur.getString(cur.getColumnIndex("structureId")));
                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<PondEntity> getWellsInspectionDetail(String panchayatId, String structureId){
        //PondInspectionDetail info = null;

        ArrayList<PondEntity> infoList = new ArrayList<PondEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"0",panchayatId, structureId};

            Cursor cur = db.rawQuery(
                    "Select * from WellInspectionDetail WHERE isUpdated=? AND Panchayat_Code=? AND structureId=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                PondEntity info = new PondEntity();
                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));

                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockID(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));
                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                info.setVillageID(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setVillageName(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<StructureRemarkEntity> getStructureRemarkDetail(String panchayatId, String structureId){
        //PondInspectionDetail info = null;

        ArrayList<StructureRemarkEntity> infoList = new ArrayList();

        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur;

            if(panchayatId.equals("0") && structureId.equals("0")){
                String[] params = new String[]{"1"};
                cur = db.rawQuery("Select * from StructureRemarkUpdateDetail WHERE IsUpdate=?",
                        params);
            }else{
                String[] params = new String[]{"0",panchayatId, structureId};
                cur = db.rawQuery("Select * from StructureRemarkUpdateDetail WHERE IsUpdate=? AND PanchayatCode=? AND StrType=?",
                        params);
            }

            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                infoList.add(getRemarkDetail(cur));
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public StructureRemarkEntity getRemarkDetail(Cursor cur){
        StructureRemarkEntity info = new StructureRemarkEntity();

        info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));

        info.setDist_Code(cur.getString(cur.getColumnIndex("DistrictCode")));
        info.setDist_Name(cur.getString(cur.getColumnIndex("DistName")));
        info.setBlock_Code(cur.getString(cur.getColumnIndex("BlockCode")));
        info.setBlock_Name(cur.getString(cur.getColumnIndex("BlockName")));
        info.setPanchayat_Code(cur.getString(cur.getColumnIndex("PanchayatCode")));
        info.setPanchayat_Name(cur.getString(cur.getColumnIndex("PanchayatName")));
        info.setVillage_Code(cur.getString(cur.getColumnIndex("VILLCODE")));
        info.setVillage_Name(cur.getString(cur.getColumnIndex("VILLNAME")));

        info.setWard_Code(cur.getString(cur.getColumnIndex("WARDCODE")));
        info.setWard_Name(cur.getString(cur.getColumnIndex("WARDNAME")));
        info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
        info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
        info.setUrban_AreaID(cur.getString(cur.getColumnIndex("Urban_AreaID")));
        info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("RajswaThanaNumber")));
        info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
        info.setStrID(cur.getString(cur.getColumnIndex("StrID")));
        info.setTypesOfSarchna(cur.getString(cur.getColumnIndex("TypesOfSarchna")));

        info.setRemark(cur.getString(cur.getColumnIndex("Remarks")));
        info.setDeptId(cur.getString(cur.getColumnIndex("DeptId")));
        info.setDeptName(cur.getString(cur.getColumnIndex("DeptName")));

        info.setIsStrAvailable(cur.getString(cur.getColumnIndex("IsStrAvailable")));
        info.setIsWorking(cur.getString(cur.getColumnIndex("IsWorking")));
        info.setNewRemark(cur.getString(cur.getColumnIndex("newRemark")));
        info.setAppVersion(cur.getString(cur.getColumnIndex("AppVersion")));
        info.setEntryDate(cur.getString(cur.getColumnIndex("EntryDate")));
        info.setEntryBy(cur.getString(cur.getColumnIndex("EntryBy")));
        info.setStrType(cur.getString(cur.getColumnIndex("StrType")));

        info.setIsUpdate(cur.getString(cur.getColumnIndex("IsUpdate")));
        info.setMobile(cur.getString(cur.getColumnIndex("Mobile")));


        if (!cur.isNull(cur.getColumnIndex("Photo"))) {

            byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

            info.setPhoto(encodedImg2);
            info.setImg(imgData);
        }

        return info;
    }

    public ArrayList<PondEntity> getPondsInspectionUpdatedDetail(){
        //PondInspectionDetail info = null;

        ArrayList<PondEntity> infoList = new ArrayList<PondEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from PondInspectionDetail WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                PondEntity info = new PondEntity();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));

                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockID(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));
                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                info.setVillageID(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setVillageName(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));

                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }
    public ArrayList<NurseryEntity> getNurseryInspectionUpdatedDetail(){
        //PondInspectionDetail info = null;

        ArrayList<NurseryEntity> infoList = new ArrayList<NurseryEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from NurseryInspectionDetail WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                NurseryEntity info = new NurseryEntity();

                //info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));

                info.setDist_Code(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setDist_Name(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlock_Code(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlock_Name(cur.getString(cur.getColumnIndex("Block_Name")));
                info.setArea_Code(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                info.setArea_Name(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setPanchayat_Code(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setPanchayat_Name(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setWard_Code(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setWard_Name(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setVillage_Code(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setVillage_Name(cur.getString(cur.getColumnIndex("Village_Name")));
                //info.setPlantation_Name(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setArea(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setTree(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setMobile(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));

                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<SanrachnaDataEntity> getSanrachnaInspectionUpdatedDetail(){
        //PondInspectionDetail info = null;

        ArrayList<SanrachnaDataEntity> infoList = new ArrayList<SanrachnaDataEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from SanrachnaProgressData WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                SanrachnaDataEntity info = new SanrachnaDataEntity();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistcode(cur.getString(cur.getColumnIndex("Distcode")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
                info.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("RajswaThanaNumber")));
                info.setDepatmentName(cur.getString(cur.getColumnIndex("DepatmentName")));
                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
                info.setVILLCODE(cur.getString(cur.getColumnIndex("VILLCODE")));
                info.setVILLNAME(cur.getString(cur.getColumnIndex("VILLNAME")));

                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setCommercial_Public(cur.getString(cur.getColumnIndex("Commercial_Public")));
                info.setTypesOfSarchna(cur.getString(cur.getColumnIndex("TypesOfSarchna")));
                info.setSwamitw_Dep(cur.getString(cur.getColumnIndex("Swamitw_Dep")));
                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setVerified_By(cur.getString(cur.getColumnIndex("Verified_By")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setLongitude_Mob(cur.getString(cur.getColumnIndex("Longitude_Mob")));
                info.setLatitude_Mob(cur.getString(cur.getColumnIndex("Latitude_Mob")));
                info.setIsNewEntryPond(cur.getString(cur.getColumnIndex("IsNewEntryPond")));
                info.setSwamitwDep_Name(cur.getString(cur.getColumnIndex("SwamitwDep_Name")));
                info.setArea_By_Govt_Record(cur.getString(cur.getColumnIndex("Area_By_Govt_Record")));
                info.setConnected_With_Pine(cur.getString(cur.getColumnIndex("Connected_With_Pine")));
                info.setAvailability_Of_Water(cur.getString(cur.getColumnIndex("Availability_Of_Water")));
                info.setStatus_of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setTypes_of_Encroachment(cur.getString(cur.getColumnIndex("Type_Of_Encroachment")));
                info.setRequirement_of_Renovation(cur.getString(cur.getColumnIndex("Requirement_of_Renovation")));
                info.setRemarksCompl(cur.getString(cur.getColumnIndex("RemarksCompl")));
                info.setAppversion(cur.getString(cur.getColumnIndex("AppVersion")));
                info.setRequirement_of_machine(cur.getString(cur.getColumnIndex("Requirement_of_machine")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));




                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                    info.setImage1(cur.getBlob(cur.getColumnIndex("Photo1")));
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

                    info.setPhoto2(encodedImg2);
                    info.setImage2(cur.getBlob(cur.getColumnIndex("Photo2")));
                }

                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<ManregaSchemeDetail> getManregaDetailList(){
        //PondInspectionDetail info = null;

        ArrayList<ManregaSchemeDetail> infoList = new ArrayList<ManregaSchemeDetail>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from Menrega_Rural_Dev_Dept WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                infoList.add(getManregaDetailFromDB(cur));
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<ManregaSchemeDetail> getManregaDetailListPanchayatWise(String panCode, String abyabId){
        //PondInspectionDetail info = null;

        ArrayList<ManregaSchemeDetail> infoList = new ArrayList<ManregaSchemeDetail>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur;

            if(abyabId.equals("")){
                String[] params = new String[]{panCode,"0"};

                cur = db.rawQuery("Select * from Menrega_Rural_Dev_Dept WHERE PanchayatID=? AND isUpdated=?", params);
            }else{
                String[] params = new String[]{panCode,"0", abyabId};

                cur = db.rawQuery("Select * from Menrega_Rural_Dev_Dept WHERE PanchayatID=? AND isUpdated=? AND AbyabId=?", params);
            }



            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                infoList.add(getManregaDetailFromDB(cur));
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<ManregaSchemeDetail> getOtherDeptDetailListPanchayatWise(String panCode, String abyabId){

        ArrayList<ManregaSchemeDetail> infoList = new ArrayList<ManregaSchemeDetail>();
        String filter = "";

        if(!panCode.equals("NA")){
            filter += "AND PanchayatID="+panCode;
        }

        if(!abyabId.equals("")){
            filter += "AND AbyabId="+abyabId;
        }

        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur;
            String[] params = new String[]{"0"};

            cur = db.rawQuery("Select * from OtherDept_Of_Rural_Dev_Dept WHERE isUpdated=? "+filter, params);
//            if(panCode.equals("NA") && abyabId.equals("")){
//                String[] params = new String[]{"0"};
//
//                cur = db.rawQuery("Select * from OtherDept_Of_Rural_Dev_Dept WHERE isUpdated=?", params);
//            }else{
//                if(abyabId.equals("")){
//                    String[] params = new String[]{panCode,"0"};
//
//                    cur = db.rawQuery("Select * from OtherDept_Of_Rural_Dev_Dept WHERE PanchayatID=? AND isUpdated=?",params);
//                }else{
//                    String[] params = new String[]{panCode,"0", abyabId};
//
//                    cur = db.rawQuery("Select * from OtherDept_Of_Rural_Dev_Dept WHERE PanchayatID=? AND isUpdated=? AND AbyabId=?",params);
//                }
//
//            }
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {

                ManregaSchemeDetail info = new ManregaSchemeDetail();

                info.setId(cur.getInt(cur.getColumnIndex("id")));

                info.setExecution_Dept_ID(cur.getString(cur.getColumnIndex("Execution_DeptID")));
                info.setSub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_Exec_DeptID")));
                info.setSub_Sub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_Sub_Exec_DeptID")));
                //info.setAuthenticated(true);
                info.setSub_Exect_Value(cur.getString(cur.getColumnIndex("Sub_ExeValue")));
                info.setDistrict_Id(cur.getString(cur.getColumnIndex("DistrictID")));
                info.setDistrict_Name(cur.getString(cur.getColumnIndex("DistrictName")));
                info.setBlock_ID(cur.getString(cur.getColumnIndex("BlockID")));
                info.setBlock_Name(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayat_Id(cur.getString(cur.getColumnIndex("PanchayatID")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
                info.setUrban_Area_Id(cur.getString(cur.getColumnIndex("Urban_AreaID")));
                info.setUrban_AreaName(cur.getString(cur.getColumnIndex("Urban_AreaName")));
                info.setWard_Id(cur.getString(cur.getColumnIndex("WardID")));
                info.setVillage_Id(cur.getString(cur.getColumnIndex("VillageID")));
                info.setVillage_Name(cur.getString(cur.getColumnIndex("VillageName")));

                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setWork_Structure_Type(cur.getString(cur.getColumnIndex("Work_Structure_Type")));
                info.setWork_Structure_Type_Name(cur.getString(cur.getColumnIndex("Work_Structure_Type_Name")));
                info.setWork_Structure_Type_Other_Name(cur.getString(cur.getColumnIndex("Work_Structure_Type_Other_Name")));
                info.setMeasurement_Of_Structure(cur.getString(cur.getColumnIndex("Measurement_Of_Structure")));
                info.setEstimated_Amount(cur.getString(cur.getColumnIndex("Estimated_Amount")));
                //info.setApproval_Date(cur.getString(cur.getColumnIndex("Approval_Date")));
                info.setAdministrative_Approval_Date(cur.getString(cur.getColumnIndex("Administrative_Approval_Date")));
                info.setScheme_Code(cur.getString(cur.getColumnIndex("Scheme_Code")));
                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));

                info.setCreatedBy(cur.getString(cur.getColumnIndex("CreatedBy")));
                info.setCreatedDate(cur.getString(cur.getColumnIndex("CreatedDate")));
                info.setApp_Version(cur.getString(cur.getColumnIndex("App_Version")));
                info.setExectDeptName(cur.getString(cur.getColumnIndex("ExectDeptName")));
                info.setSubDivName(cur.getString(cur.getColumnIndex("SubDivName")));
                //info.setOther_Name(cur.getString(cur.getColumnIndex("Other_Name")));
                info.setWard_Name(cur.getString(cur.getColumnIndex("Ward_Name")));

                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setTypes_OfSarchnaId(cur.getString(cur.getColumnIndex("Types_OfSarchnaId")));
                info.setTypes_OfSarchnaName(cur.getString(cur.getColumnIndex("Types_OfSarchnaName")));

                info.setIsPhase1Inspected(cur.getString(cur.getColumnIndex("IsPhase1Inspected")));
                info.setIsPhase1InspBy(cur.getString(cur.getColumnIndex("IsPhase1InspBy")));
                info.setIsPhase1InspDate(cur.getString(cur.getColumnIndex("IsPhase1InspDate")));
                info.setIsPhase1InspLatitude(cur.getString(cur.getColumnIndex("IsPhase1InspLatitude")));
                info.setIsPhase1InspLongitude(cur.getString(cur.getColumnIndex("IsPhase1InspLongitude")));
                info.setIsPhase1InspRemarks(cur.getString(cur.getColumnIndex("IsPhase1InspRemarks")));

                info.setIsPhase2Inspected(cur.getString(cur.getColumnIndex("IsPhase2Inspected")));
                info.setIsPhase2InspBy(cur.getString(cur.getColumnIndex("IsPhase2InspBy")));
                info.setIsPhase2InspDate(cur.getString(cur.getColumnIndex("IsPhase2InspDate")));
                info.setIsPhase2InspLatitude(cur.getString(cur.getColumnIndex("IsPhase2InspLatitude")));
                info.setIsPhase2InspLongitude(cur.getString(cur.getColumnIndex("IsPhase2InspLongitude")));
                info.setIsPhase2InspRemarks(cur.getString(cur.getColumnIndex("IsPhase2InspRemarks")));

                info.setIsPhase3Inspected(cur.getString(cur.getColumnIndex("IsPhase3Inspected")));
                info.setIsPhase3InspBy(cur.getString(cur.getColumnIndex("IsPhase3InspBy")));
                info.setIsPhase3InspDate(cur.getString(cur.getColumnIndex("IsPhase3InspDate")));
                info.setIsPhase3InspLatitude(cur.getString(cur.getColumnIndex("IsPhase3InspLatitude")));
                info.setIsPhase3InspLongitude(cur.getString(cur.getColumnIndex("IsPhase3InspLongitude")));
                info.setIsPhase3InspRemarks(cur.getString(cur.getColumnIndex("IsPhase3InspRemarks")));

                info.setYojnaType(cur.getString(cur.getColumnIndex("YojnaType")));



                if (!cur.isNull(cur.getColumnIndex("IsPhase1InspPhoto1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase1InspPhoto1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase1InspPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase1InspPhoto2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase1InspPhoto2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase1InspPhoto2(encodedImg2);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase2InspPhoto1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase2InspPhoto1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase2InspPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase2InspPhoto2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase2InspPhoto2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase2InspPhoto2(encodedImg2);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase3InspPhoto1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase3InspPhoto1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase3InspPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase3InspPhoto2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase3InspPhoto2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase3InspPhoto2(encodedImg2);
                }

                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ManregaSchemeDetail getManregaDetailFromDB(Cursor cur){
        ManregaSchemeDetail info = new ManregaSchemeDetail();

        info.setId(cur.getInt(cur.getColumnIndex("id")));

        info.setExecution_Dept_ID(cur.getString(cur.getColumnIndex("Execution_DeptID")));
        info.setSub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_DivisionID")));
        info.setSub_Sub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_SubDivisionID")));
        //info.setAuthenticated(true);
        info.setSub_Exect_Value(cur.getString(cur.getColumnIndex("Sub_DivisionValue")));
        info.setDistrict_Id(cur.getString(cur.getColumnIndex("DistrictID")));
        info.setDistrict_Name(cur.getString(cur.getColumnIndex("DistrictName")));
        info.setBlock_ID(cur.getString(cur.getColumnIndex("BlockID")));
        info.setBlock_Name(cur.getString(cur.getColumnIndex("BlockName")));
        info.setPanchayat_Id(cur.getString(cur.getColumnIndex("PanchayatID")));
        info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
        info.setWard_Id(cur.getString(cur.getColumnIndex("WardID")));
        info.setVillage_Id(cur.getString(cur.getColumnIndex("VillageID")));

        info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
        info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
        info.setWork_Structure_Type(cur.getString(cur.getColumnIndex("Work_Structure_Type")));
        info.setWork_Structure_Type_Name(cur.getString(cur.getColumnIndex("Work_Structure_Type_Name")));
        info.setWork_Structure_Type_Other_Name(cur.getString(cur.getColumnIndex("Work_Structure_Type_Other_Name")));
        info.setMeasurement_Of_Structure(cur.getString(cur.getColumnIndex("Measurement_Of_Structure")));
        info.setEstimated_Amount(cur.getString(cur.getColumnIndex("Estimated_Amount")));
        info.setApproval_Date(cur.getString(cur.getColumnIndex("Approval_Date")));
        info.setAdministrative_Approval_Date(cur.getString(cur.getColumnIndex("Administrative_Approval_Date")));
        info.setScheme_Code(cur.getString(cur.getColumnIndex("MIS_Scheme_Code")));
        info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));

        info.setCreatedBy(cur.getString(cur.getColumnIndex("CreatedBy")));
        info.setCreatedDate(cur.getString(cur.getColumnIndex("CreatedDate")));
        info.setApp_Version(cur.getString(cur.getColumnIndex("App_Version")));
        info.setExectDeptName(cur.getString(cur.getColumnIndex("ExectDeptName")));
        info.setSubDivName(cur.getString(cur.getColumnIndex("SubDivName")));
        info.setOther_Name(cur.getString(cur.getColumnIndex("Other_Name")));
        info.setWard_Name(cur.getString(cur.getColumnIndex("Ward_Name")));

        info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
        info.setTypes_OfSarchnaId(cur.getString(cur.getColumnIndex("Types_OfSarchnaId")));
        info.setTypes_OfSarchnaName(cur.getString(cur.getColumnIndex("Types_OfSarchnaName")));

        info.setIsPhase1Inspected(cur.getString(cur.getColumnIndex("IsPhase1Inspected")));
        info.setIsPhase1InspBy(cur.getString(cur.getColumnIndex("IsPhase1InspBy")));
        info.setIsPhase1InspDate(cur.getString(cur.getColumnIndex("IsPhase1InspDate")));
        info.setIsPhase1InspLatitude(cur.getString(cur.getColumnIndex("IsPhase1InspLatitude")));
        info.setIsPhase1InspLongitude(cur.getString(cur.getColumnIndex("IsPhase1InspLongitude")));
        info.setIsPhase1InspRemarks(cur.getString(cur.getColumnIndex("IsPhase1InspRemarks")));

        info.setIsPhase2Inspected(cur.getString(cur.getColumnIndex("IsPhase2Inspected")));
        info.setIsPhase2InspBy(cur.getString(cur.getColumnIndex("IsPhase2InspBy")));
        info.setIsPhase2InspDate(cur.getString(cur.getColumnIndex("IsPhase2InspDate")));
        info.setIsPhase2InspLatitude(cur.getString(cur.getColumnIndex("IsPhase2InspLatitude")));
        info.setIsPhase2InspLongitude(cur.getString(cur.getColumnIndex("IsPhase2InspLongitude")));
        info.setIsPhase2InspRemarks(cur.getString(cur.getColumnIndex("IsPhase2InspRemarks")));

        info.setIsPhase3Inspected(cur.getString(cur.getColumnIndex("IsPhase3Inspected")));
        info.setIsPhase3InspBy(cur.getString(cur.getColumnIndex("IsPhase3InspBy")));
        info.setIsPhase3InspDate(cur.getString(cur.getColumnIndex("IsPhase3InspDate")));
        info.setIsPhase3InspLatitude(cur.getString(cur.getColumnIndex("IsPhase3InspLatitude")));
        info.setIsPhase3InspLongitude(cur.getString(cur.getColumnIndex("IsPhase3InspLongitude")));
        info.setIsPhase3InspRemarks(cur.getString(cur.getColumnIndex("IsPhase3InspRemarks")));
        info.setYojnaType(cur.getString(cur.getColumnIndex("YojnaType")));
        info.setAwayabId(cur.getString(cur.getColumnIndex("AbyabId")));

        info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
        info.setRakba(cur.getString(cur.getColumnIndex("Rakba")));
        info.setLandOwnerMob(cur.getString(cur.getColumnIndex("LandOwnerMob")));
        info.setLandOwnerName(cur.getString(cur.getColumnIndex("LandOwnerName")));
        info.setFencingLatitude1(cur.getString(cur.getColumnIndex("FencingLatitude1")));
        info.setFencingLatitude2(cur.getString(cur.getColumnIndex("FencingLatitude2")));
        info.setFencingLatitude3(cur.getString(cur.getColumnIndex("FencingLatitude3")));
        info.setFencingLatitude4(cur.getString(cur.getColumnIndex("FencingLatitude4")));
        info.setFencingLongitude1(cur.getString(cur.getColumnIndex("FencingLongitude1")));
        info.setFencingLongitude2(cur.getString(cur.getColumnIndex("FencingLongitude2")));
        info.setFencingLongitude3(cur.getString(cur.getColumnIndex("FencingLongitude3")));
        info.setFencingLongitude4(cur.getString(cur.getColumnIndex("FencingLongitude4")));
        info.setIsLandEnterDt(cur.getString(cur.getColumnIndex("IsLandEnterDt")));

        info.setConsumerNo(cur.getString(cur.getColumnIndex("ConsumerNo")));
        info.setConsumrBill(cur.getString(cur.getColumnIndex("ConsumrBill")));
        info.setTreeType(cur.getString(cur.getColumnIndex("TreeType")));
        info.setTreeTypeName(cur.getString(cur.getColumnIndex("TreeTypeName")));
        info.setTotalTree(cur.getString(cur.getColumnIndex("TotalTree")));

        if (!cur.isNull(cur.getColumnIndex("IsPhase1InspPhoto1"))) {

            byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase1InspPhoto1"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            info.setIsPhase1InspPhoto1(encodedImg1);
        }

        if (!cur.isNull(cur.getColumnIndex("IsPhase1InspPhoto2"))) {

            byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase1InspPhoto2"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            info.setIsPhase1InspPhoto2(encodedImg2);
        }

        if (!cur.isNull(cur.getColumnIndex("IsPhase2InspPhoto1"))) {

            byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase2InspPhoto1"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            info.setIsPhase2InspPhoto1(encodedImg1);
        }

        if (!cur.isNull(cur.getColumnIndex("IsPhase2InspPhoto2"))) {

            byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase2InspPhoto2"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            info.setIsPhase2InspPhoto2(encodedImg2);
        }

        if (!cur.isNull(cur.getColumnIndex("IsPhase3InspPhoto1"))) {

            byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase3InspPhoto1"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            info.setIsPhase3InspPhoto1(encodedImg1);
        }

        if (!cur.isNull(cur.getColumnIndex("IsPhase3InspPhoto2"))) {

            byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase3InspPhoto2"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            info.setIsPhase3InspPhoto2(encodedImg2);
        }

        return info;
    }

    public ArrayList<ManregaSchemeDetail> getOtherSchemeDetailList(){
        //PondInspectionDetail info = null;

        ArrayList<ManregaSchemeDetail> infoList = new ArrayList<ManregaSchemeDetail>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from OtherDept_Of_Rural_Dev_Dept WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                ManregaSchemeDetail info = new ManregaSchemeDetail();

                info.setId(cur.getInt(cur.getColumnIndex("id")));

                info.setExecution_Dept_ID(cur.getString(cur.getColumnIndex("Execution_DeptID")));
                info.setSub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_Exec_DeptID")));
                info.setSub_Sub_Exect_Dept_ID(cur.getString(cur.getColumnIndex("Sub_Sub_Exec_DeptID")));
                //info.setAuthenticated(true);
                info.setSub_Exect_Value(cur.getString(cur.getColumnIndex("Sub_ExeValue")));
                info.setDistrict_Id(cur.getString(cur.getColumnIndex("DistrictID")));
                info.setDistrict_Name(cur.getString(cur.getColumnIndex("DistrictName")));
                info.setBlock_ID(cur.getString(cur.getColumnIndex("BlockID")));
                info.setBlock_Name(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayat_Id(cur.getString(cur.getColumnIndex("PanchayatID")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
                info.setUrban_Area_Id(cur.getString(cur.getColumnIndex("Urban_AreaID")));
                info.setUrban_AreaName(cur.getString(cur.getColumnIndex("Urban_AreaName")));
                info.setWard_Id(cur.getString(cur.getColumnIndex("WardID")));
                info.setVillage_Id(cur.getString(cur.getColumnIndex("VillageID")));
                info.setVillage_Name(cur.getString(cur.getColumnIndex("VillageName")));

                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setWork_Structure_Type(cur.getString(cur.getColumnIndex("Work_Structure_Type")));
                info.setWork_Structure_Type_Name(cur.getString(cur.getColumnIndex("Work_Structure_Type_Name")));
                info.setWork_Structure_Type_Other_Name(cur.getString(cur.getColumnIndex("Work_Structure_Type_Other_Name")));
                info.setMeasurement_Of_Structure(cur.getString(cur.getColumnIndex("Measurement_Of_Structure")));
                info.setEstimated_Amount(cur.getString(cur.getColumnIndex("Estimated_Amount")));
                //info.setApproval_Date(cur.getString(cur.getColumnIndex("Approval_Date")));
                info.setAdministrative_Approval_Date(cur.getString(cur.getColumnIndex("Administrative_Approval_Date")));
                info.setScheme_Code(cur.getString(cur.getColumnIndex("Scheme_Code")));
                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));

                info.setCreatedBy(cur.getString(cur.getColumnIndex("CreatedBy")));
                info.setCreatedDate(cur.getString(cur.getColumnIndex("CreatedDate")));
                info.setApp_Version(cur.getString(cur.getColumnIndex("App_Version")));
                info.setExectDeptName(cur.getString(cur.getColumnIndex("ExectDeptName")));
                info.setSubDivName(cur.getString(cur.getColumnIndex("SubDivName")));
                //info.setOther_Name(cur.getString(cur.getColumnIndex("Other_Name")));
                info.setWard_Name(cur.getString(cur.getColumnIndex("Ward_Name")));

                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setTypes_OfSarchnaId(cur.getString(cur.getColumnIndex("Types_OfSarchnaId")));
                info.setTypes_OfSarchnaName(cur.getString(cur.getColumnIndex("Types_OfSarchnaName")));

                info.setIsPhase1Inspected(cur.getString(cur.getColumnIndex("IsPhase1Inspected")));
                info.setIsPhase1InspBy(cur.getString(cur.getColumnIndex("IsPhase1InspBy")));
                info.setIsPhase1InspDate(cur.getString(cur.getColumnIndex("IsPhase1InspDate")));
                info.setIsPhase1InspLatitude(cur.getString(cur.getColumnIndex("IsPhase1InspLatitude")));
                info.setIsPhase1InspLongitude(cur.getString(cur.getColumnIndex("IsPhase1InspLongitude")));
                info.setIsPhase1InspRemarks(cur.getString(cur.getColumnIndex("IsPhase1InspRemarks")));

                info.setIsPhase2Inspected(cur.getString(cur.getColumnIndex("IsPhase2Inspected")));
                info.setIsPhase2InspBy(cur.getString(cur.getColumnIndex("IsPhase2InspBy")));
                info.setIsPhase2InspDate(cur.getString(cur.getColumnIndex("IsPhase2InspDate")));
                info.setIsPhase2InspLatitude(cur.getString(cur.getColumnIndex("IsPhase2InspLatitude")));
                info.setIsPhase2InspLongitude(cur.getString(cur.getColumnIndex("IsPhase2InspLongitude")));
                info.setIsPhase2InspRemarks(cur.getString(cur.getColumnIndex("IsPhase2InspRemarks")));

                info.setIsPhase3Inspected(cur.getString(cur.getColumnIndex("IsPhase3Inspected")));
                info.setIsPhase3InspBy(cur.getString(cur.getColumnIndex("IsPhase3InspBy")));
                info.setIsPhase3InspDate(cur.getString(cur.getColumnIndex("IsPhase3InspDate")));
                info.setIsPhase3InspLatitude(cur.getString(cur.getColumnIndex("IsPhase3InspLatitude")));
                info.setIsPhase3InspLongitude(cur.getString(cur.getColumnIndex("IsPhase3InspLongitude")));
                info.setIsPhase3InspRemarks(cur.getString(cur.getColumnIndex("IsPhase3InspRemarks")));

                info.setYojnaType(cur.getString(cur.getColumnIndex("YojnaType")));
                info.setAwayabId(cur.getString(cur.getColumnIndex("AbyabId")));

                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
                info.setRakba(cur.getString(cur.getColumnIndex("Rakba")));
                info.setLandOwnerMob(cur.getString(cur.getColumnIndex("LandOwnerMob")));
                info.setLandOwnerName(cur.getString(cur.getColumnIndex("LandOwnerName")));
                info.setFencingLatitude1(cur.getString(cur.getColumnIndex("FencingLatitude1")));
                info.setFencingLatitude2(cur.getString(cur.getColumnIndex("FencingLatitude2")));
                info.setFencingLatitude3(cur.getString(cur.getColumnIndex("FencingLatitude3")));
                info.setFencingLatitude4(cur.getString(cur.getColumnIndex("FencingLatitude4")));
                info.setFencingLongitude1(cur.getString(cur.getColumnIndex("FencingLongitude1")));
                info.setFencingLongitude2(cur.getString(cur.getColumnIndex("FencingLongitude2")));
                info.setFencingLongitude3(cur.getString(cur.getColumnIndex("FencingLongitude3")));
                info.setFencingLongitude4(cur.getString(cur.getColumnIndex("FencingLongitude4")));
                info.setIsLandEnterDt(cur.getString(cur.getColumnIndex("IsLandEnterDt")));

                info.setConsumerNo(cur.getString(cur.getColumnIndex("ConsumerNo")));
                info.setConsumrBill(cur.getString(cur.getColumnIndex("ConsumrBill")));
                info.setTreeType(cur.getString(cur.getColumnIndex("TreeType")));
                info.setTreeTypeName(cur.getString(cur.getColumnIndex("TreeTypeName")));
                info.setTotalTree(cur.getString(cur.getColumnIndex("TotalTree")));

                if (!cur.isNull(cur.getColumnIndex("IsPhase1InspPhoto1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase1InspPhoto1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase1InspPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase1InspPhoto2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase1InspPhoto2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase1InspPhoto2(encodedImg2);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase2InspPhoto1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase2InspPhoto1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase2InspPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase2InspPhoto2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase2InspPhoto2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase2InspPhoto2(encodedImg2);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase3InspPhoto1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase3InspPhoto1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase3InspPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("IsPhase3InspPhoto2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("IsPhase3InspPhoto2"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setIsPhase3InspPhoto2(encodedImg2);
                }

                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<PondEncroachmentEntity> getPondsEncrhmntDetail(String panchayatId, String type){
        //PondInspectionDetail info = null;
        String tableName = type.equals("pond") ? "CoPondEncroachmentReport" : "CoWellEncroachmentReport";

        ArrayList<PondEncroachmentEntity> infoList = new ArrayList<PondEncroachmentEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"0",panchayatId};

            Cursor cur = db.rawQuery(
                    "Select * from "+ tableName +" WHERE isUpdated=? AND PanchayatID=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                PondEncroachmentEntity info = new PondEncroachmentEntity();
                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistID")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockID(cur.getString(cur.getColumnIndex("BlockID")));
                info.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayatID(cur.getString(cur.getColumnIndex("PanchayatID")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("RajswaThanaNumber")));
                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
                info.setVillageID(cur.getString(cur.getColumnIndex("VillageID")));
                info.setVILLNAME(cur.getString(cur.getColumnIndex("VILLNAME")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setStatus_Of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setType_Of_Encroachment(cur.getString(cur.getColumnIndex("Type_Of_Encroachment")));
                info.setVerified_By(cur.getString(cur.getColumnIndex("Verified_By")));
                info.setIsInspected(cur.getString(cur.getColumnIndex("IsInspected")));

                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<PondEncroachmentEntity> getPondsEncrhmntUpdatedDetail(String type){
        //PondInspectionDetail info = null;

        String tableName = type.equals("pond") ? "CoPondEncroachmentReport" : "CoWellEncroachmentReport";
        ArrayList<PondEncroachmentEntity> infoList = new ArrayList<PondEncroachmentEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from "+ tableName +" WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                PondEncroachmentEntity info = new PondEncroachmentEntity();
                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistID")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockID(cur.getString(cur.getColumnIndex("BlockID")));
                info.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayatID(cur.getString(cur.getColumnIndex("PanchayatID")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("RajswaThanaNumber")));
                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
                info.setVillageID(cur.getString(cur.getColumnIndex("VillageID")));
                info.setVILLNAME(cur.getString(cur.getColumnIndex("VILLNAME")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setStatus_Of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setType_Of_Encroachment(cur.getString(cur.getColumnIndex("Type_Of_Encroachment")));
                info.setVerified_By(cur.getString(cur.getColumnIndex("Verified_By")));
                info.setIsInspected(cur.getString(cur.getColumnIndex("IsInspected")));

                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setEnch_Verified_By(cur.getString(cur.getColumnIndex("Ench_Verified_By")));
                info.setEStatus(cur.getString(cur.getColumnIndex("EStatus")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setEnchrochmentStartDate(cur.getString(cur.getColumnIndex("EnchrochmentStartDate")));
                info.setEnchrochmentEndDate(cur.getString(cur.getColumnIndex("EnchrochmentEndDate")));
                info.setNoticeDate(cur.getString(cur.getColumnIndex("NoticeDate")));
                info.setNoticeNo(cur.getString(cur.getColumnIndex("NoticeNo")));
                info.setAppVersion(cur.getString(cur.getColumnIndex("AppVersion")));
                info.setUploadType(cur.getString(cur.getColumnIndex("UploadType")));

                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<PondInspectionEntity> getPondsInspectionDetail(String panchayatId){
        //PondInspectionDetail info = null;

        ArrayList<PondInspectionEntity> infoList = new ArrayList<PondInspectionEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"0",panchayatId};

            Cursor cur = db.rawQuery(
                    "Select * from PondInspectionDetail WHERE isUpdated=? AND Panchayat_Code=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                PondInspectionEntity info = new PondInspectionEntity();
                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockID(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));
                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                info.setVillageID(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setVillageName(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setPanchayat_Code(cur.getString(cur.getColumnIndex("Panchayat_Code")));
                info.setPanchayat_Name(cur.getString(cur.getColumnIndex("Panchayat_Name")));
                info.setPondCatValue(cur.getString(cur.getColumnIndex("PondCatValue")));
                info.setArea_by_Govt_Record(cur.getString(cur.getColumnIndex("Area_by_Govt_Record")));

                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }



    public ArrayList<SanrachnaDataEntity> getSanrachnaDetail(String panchayatId){
        //PondInspectionDetail info = null;

        ArrayList<SanrachnaDataEntity> infoList = new ArrayList<SanrachnaDataEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"0",panchayatId};

            Cursor cur = db.rawQuery(
                    "Select * from SanrachnaProgressData WHERE isUpdated=? AND PanchayatCode=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                SanrachnaDataEntity info = new SanrachnaDataEntity();
                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setDistcode(cur.getString(cur.getColumnIndex("Distcode")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));
                info.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
                info.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                info.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("PanchayatName")));
                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("RajswaThanaNumber")));
                info.setDepatmentName(cur.getString(cur.getColumnIndex("DepatmentName")));
                info.setKhaata_Kheshara_Number(cur.getString(cur.getColumnIndex("Khaata_Kheshara_Number")));
                info.setVILLCODE(cur.getString(cur.getColumnIndex("VILLCODE")));
                info.setVILLNAME(cur.getString(cur.getColumnIndex("VILLNAME")));

                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setCommercial_Public(cur.getString(cur.getColumnIndex("Commercial_Public")));
                info.setTypesOfSarchna(cur.getString(cur.getColumnIndex("TypesOfSarchna")));
                info.setSwamitw_Dep(cur.getString(cur.getColumnIndex("Swamitw_Dep")));
                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setVerified_By(cur.getString(cur.getColumnIndex("Verified_By")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setLongitude_Mob(cur.getString(cur.getColumnIndex("Longitude_Mob")));
                info.setLatitude_Mob(cur.getString(cur.getColumnIndex("Latitude_Mob")));
                info.setIsNewEntryPond(cur.getString(cur.getColumnIndex("IsNewEntryPond")));
                info.setSwamitwDep_Name(cur.getString(cur.getColumnIndex("SwamitwDep_Name")));
                info.setArea_By_Govt_Record(cur.getString(cur.getColumnIndex("Area_By_Govt_Record")));
                info.setConnected_With_Pine(cur.getString(cur.getColumnIndex("Connected_With_Pine")));
                info.setAvailability_Of_Water(cur.getString(cur.getColumnIndex("Availability_Of_Water")));
                info.setStatus_of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setTypes_of_Encroachment(cur.getString(cur.getColumnIndex("Type_Of_Encroachment")));
                info.setRequirement_of_Renovation(cur.getString(cur.getColumnIndex("Requirement_of_Renovation")));
                info.setRemarksCompl(cur.getString(cur.getColumnIndex("RemarksCompl")));
                info.setAppversion(cur.getString(cur.getColumnIndex("AppVersion")));
                info.setRequirement_of_machine(cur.getString(cur.getColumnIndex("Requirement_of_machine")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));

                infoList.add(info);
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

    public ArrayList<PondInspectionDetail> getInspectedPondList(){
        //PondInspectionDetail info = null;

        ArrayList<PondInspectionDetail> infoList = new ArrayList<PondInspectionDetail>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from PondInspectionDetail WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                PondInspectionDetail info = new PondInspectionDetail();
                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setBlockCode(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));

                info.setRajswaThanaNo(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                //info.setAuthenticated(true);
                info.setVillage(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setPanchayatCode(cur.getString(cur.getColumnIndex("Panchayat_Code")));
                info.setPanchayatName(cur.getString(cur.getColumnIndex("Panchayat_Name")));
                info.setKhataKhesraNo(cur.getString(cur.getColumnIndex("Khata_Khesra_No")));
                info.setPrivateOrPublic(cur.getString(cur.getColumnIndex("Private_or_Public")));
                info.setAreaByGovtRecord(cur.getString(cur.getColumnIndex("Area_by_Govt_Record")));

                info.setConnectedWithPine(cur.getString(cur.getColumnIndex("Connected_with_Pine")));
                info.setAvailiablityOfWater(cur.getString(cur.getColumnIndex("Availability_Of_Water")));
                info.setStatusOfEncroachment(cur.getString(cur.getColumnIndex("Status_of_Encroachment")));
                info.setTypesOfEncroachment(cur.getString(cur.getColumnIndex("Types_of_Encroachment")));
                info.setRequirementOfRenovation(cur.getString(cur.getColumnIndex("Requirement_of_Renovation")));
                info.setRecommendedExecutionDept(cur.getString(cur.getColumnIndex("Recommended_Execution_Dept")));
                info.setRequirementOfMachine(cur.getString(cur.getColumnIndex("Requirement_of_machine")));
                info.setOwnerName(cur.getString(cur.getColumnIndex("Name_of_undertaken")));
                info.setDistName(cur.getString(cur.getColumnIndex("DistName")));

                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                //info.setVi(cur.getString(cur.getColumnIndex("Village_Name")));
                //info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setLatitude_Mob(cur.getString(cur.getColumnIndex("Latitude_Mob")));
                info.setLongitude_Mob(cur.getString(cur.getColumnIndex("Longitude_Mob")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg2);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo3"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg3 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto3(encodedImg3);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo4"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg4 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto4(encodedImg4);
                }


                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            //info = null;
        }
        return infoList;
    }

    public ArrayList<WellInspectionEntity> getInspectedWellList(){
        //PondInspectionDetail info = null;

        ArrayList<WellInspectionEntity> infoList = new ArrayList<WellInspectionEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"1"};

            Cursor cur = db.rawQuery(
                    "Select * from WellInspectionDetail WHERE isUpdated=?",
                    params);
            int x = cur.getCount();
            // db1.execSQL("Delete from UserDetail");

            while (cur.moveToNext()) {


                WellInspectionEntity info = new WellInspectionEntity();

                info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
                info.setId(cur.getInt(cur.getColumnIndex("id")));
                info.setBlockID(cur.getString(cur.getColumnIndex("Block_Code")));
                info.setBlockName(cur.getString(cur.getColumnIndex("Block_Name")));

                info.setRajswaThanaNumber(cur.getString(cur.getColumnIndex("Rajswa_Thana_No")));
                //info.setAuthenticated(true);
                info.setVillageID(cur.getString(cur.getColumnIndex("Village_Id")));
                info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
                info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
                info.setPanchayat_Code(cur.getString(cur.getColumnIndex("Panchayat_Code")));
                info.setPanchayat_Name(cur.getString(cur.getColumnIndex("Panchayat_Name")));
                info.setKhata_Khesra_No(cur.getString(cur.getColumnIndex("Khata_Khesra_No")));
                info.setPrivate_or_Public(cur.getString(cur.getColumnIndex("Private_or_Public")));
                info.setOutside_Diamter(cur.getString(cur.getColumnIndex("Outside_Diamter")));

                info.setName_of_undertaken(cur.getString(cur.getColumnIndex("Name_Of_Undertaken_Dept")));
                info.setRequirement_Of_Flyer(cur.getString(cur.getColumnIndex("Requirement_Of_Flyer")));
                info.setStatus_of_Encroachment(cur.getString(cur.getColumnIndex("Status_Of_Encroachment")));
                info.setTypes_of_Encroachment(cur.getString(cur.getColumnIndex("Types_Of_Encroachment")));
                info.setRequirement_of_Renovation(cur.getString(cur.getColumnIndex("Requirement_Of_Renovation")));
                //info.setre(cur.getString(cur.getColumnIndex("Recommended_Execution_Dept")));
                info.setRequirement_of_machine(cur.getString(cur.getColumnIndex("Requirement_of_Machine")));
                //info.set(cur.getString(cur.getColumnIndex("Name_of_undertaken")));
                //info.setDi(cur.getString(cur.getColumnIndex("DistName")));
                info.setDistID(cur.getString(cur.getColumnIndex("DistrictCode")));

                info.setRemarks(cur.getString(cur.getColumnIndex("Remarks")));
                info.setIsUpdated(cur.getString(cur.getColumnIndex("isUpdated")));
                //info.setVi(cur.getString(cur.getColumnIndex("Village_Name")));
                info.setVerified_Date(cur.getString(cur.getColumnIndex("Verified_Date")));
                info.setLatitude_Mob(cur.getString(cur.getColumnIndex("Latitude_Mob")));
                info.setLongitude_Mob(cur.getString(cur.getColumnIndex("Longitude_Mob")));

                if (!cur.isNull(cur.getColumnIndex("Photo1"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg1 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto1(encodedImg1);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo2"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto2(encodedImg2);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo3"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg3 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto3(encodedImg3);
                }

                if (!cur.isNull(cur.getColumnIndex("Photo4"))) {

                    byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo1"));
                    Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                    String encodedImg4 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                    info.setPhoto4(encodedImg4);
                }

                infoList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
        }
        return infoList;
    }

//
//    public UserDetails getUserDetails(String userId, String pass) {
//
//        UserDetails userInfo = null;
//
//        try {
//
//            SQLiteDatabase db = this.getReadableDatabase();
//
//            String[] params = new String[]{userId.trim(), pass};
//
//            Cursor cur = db.rawQuery(
//                    "Select * from UserLogin WHERE UserID=? and Password=?",
//                    params);
//            int x = cur.getCount();
//            // db1.execSQL("Delete from UserDetail");
//
//            while (cur.moveToNext()) {
//
//
//                userInfo = new UserDetails();
//                userInfo.setUserID(cur.getString(cur.getColumnIndex("UserID")));
//                userInfo.setName(cur.getString(cur.getColumnIndex("UserName")));
//                userInfo.setPassword(cur.getString(cur.getColumnIndex("Password")));
//               // userInfo.setMobile(cur.getString(cur.getColumnIndex("ContactNo")));
//                userInfo.setUserrole(cur.getString(cur.getColumnIndex("Userrole")));
//                //userInfo.setIMEI(cur.getString(cur.getColumnIndex("IMEI")));
//                userInfo.setAuthenticated(true);
//                userInfo.setDistrictCode(cur.getString(cur.getColumnIndex("DistCode")));
//                userInfo.setDistName(cur.getString(cur.getColumnIndex("DistName")));
//                userInfo.setBlockCode(cur.getString(cur.getColumnIndex("BlockCode")));
//                userInfo.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
//                userInfo.setDegignation(cur.getString(cur.getColumnIndex("Degignation")));
//                userInfo.setMobileNo(cur.getString(cur.getColumnIndex("MobileNo")));
//            }
//
//            cur.close();
//            db.close();
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            userInfo = null;
//        }
//        return userInfo;
//    }


    public ArrayList<LocalFieldInfo> getLocalFieldInfo() {


        ArrayList<LocalFieldInfo> fieldList = new ArrayList<LocalFieldInfo>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{"Y"};
            Cursor cur = db.rawQuery("Select * from FieldMaster where isActive=? order by Sequence", params);


            while (cur.moveToNext()) {

                String slno = cur.getString(0);
                String Field = cur.getString(1);
                String Label = cur.getString(3);
                String Type = cur.getString(4);
                String Sequence = String.valueOf(cur.getInt(5));


                fieldList.add(new LocalFieldInfo(slno, Field, Label, "Y", Type, Sequence));


            }

            cur.close();
            db.close();


        } catch (Exception e) {
            // TODO: handle exception
            return fieldList;

        }

        return fieldList;


    }


    public ArrayList<LocalSpinnerData> getLocalSpinnerData(String F) {


        ArrayList<LocalSpinnerData> fieldList = new ArrayList<LocalSpinnerData>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[]{F};
            Cursor cur = db.rawQuery("Select * from SpinnerMaster where Field=? order by Code ", params);


            while (cur.moveToNext()) {


                String Code = cur.getString(1);
                String Value = cur.getString(2);
                String Field = cur.getString(3);


                fieldList.add(new LocalSpinnerData(Code, Value, Field, ""));


            }

            cur.close();
            db.close();


        } catch (Exception e) {
// TODO: handle exception
            return fieldList;

        }

        return fieldList;


    }


    public ArrayList<Fyear> getFyearLocalData() {

        ArrayList<Fyear> fieldList = new ArrayList<Fyear>();

        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int i = 0;

            Cursor cur = db.rawQuery("Select * from Fyear ", null);
            while (cur.moveToNext()) {
                i++;
                fieldList.add(new Fyear(cur.getString(0), cur.getString(1), cur.getString(2)));


            }

        } catch (Exception e) {
        }

        return fieldList;
    }

    public ArrayList<Fyear> getFyearLocalDataFromScheme(String schemeid) {

        ArrayList<Fyear> fieldList = new ArrayList<Fyear>();
//CREATE TABLE "Scheme" ( `slno` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `schemeId` TEXT,
// `schemeName` TEXT, `schemeYearId` TEXT, `schemeYearName` TEXT )
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            int i = 0;

            Cursor cur = db.rawQuery("Select  distinct schemeYearId,schemeYearName from Scheme WHERE schemeId='"+schemeid+"' ORDER BY schemeYearId,schemeYearName ASC", null);
            while (cur.moveToNext()) {
                i++;
                fieldList.add(new Fyear(cur.getString(0), cur.getString(1), "N"));


            }

        } catch (Exception e) {
        }

        return fieldList;
    }
    //TODO Check this
//    public long inserToDatabase(DataProgress dataProgress) {
//        long c = -1;
//        try {
//            SQLiteDatabase db = this.getWritableDatabase();
//            ContentValues values = new ContentValues();
//            values.put("District_Code", dataProgress.getDistrict_code());
//            values.put("Block_Code", dataProgress.getBlock_code());
//            values.put("Sector_Code", dataProgress.getSector_code());
//            values.put("Panchayat_Code", dataProgress.getPanchayat_code());
//            values.put("Fyear", dataProgress.getFyear());
//            values.put("F11", dataProgress.getF11());
//            values.put("F12", dataProgress.getF12());
//            values.put("F13", dataProgress.getSpMakeFoodData());
//            values.put("F14", dataProgress.getRegisteredChildren());
//            if (NewEnrollmentActivity.s1)
//                values.put(NewEnrollmentActivity.s1_field, dataProgress.getSpinner1_Data());
//            if (NewEnrollmentActivity.s2)
//                values.put(NewEnrollmentActivity.s2_field, dataProgress.getSpinner2_Data());
//            if (NewEnrollmentActivity.s3)
//                values.put(NewEnrollmentActivity.s3_field, dataProgress.getSpinner3_Data());
//            if (NewEnrollmentActivity.s4)
//                values.put(NewEnrollmentActivity.s4_field, dataProgress.getSpinner4_Data());
//            if (NewEnrollmentActivity.s5)
//                values.put(NewEnrollmentActivity.s5_field, dataProgress.getSpinner5_Data());
//            if (NewEnrollmentActivity.t1)
//                values.put(NewEnrollmentActivity.t1_field, dataProgress.getText1_Data());
//            if (NewEnrollmentActivity.t2)
//                values.put(NewEnrollmentActivity.t2_field, dataProgress.getText2_Data());
//            if (NewEnrollmentActivity.t3)
//                values.put(NewEnrollmentActivity.t3_field, dataProgress.getText3_Data());
//            if (NewEnrollmentActivity.t4)
//                values.put(NewEnrollmentActivity.t4_field, dataProgress.getText4_Data());
//            if (NewEnrollmentActivity.t5)
//                values.put(NewEnrollmentActivity.t5_field, dataProgress.getText5_Data());
//            values.put("Remarks", dataProgress.getRemark_Data());
//            values.put("Photo1", dataProgress.getPhoto1());
//            values.put("Photo2", dataProgress.getPhoto2());
//            values.put("Photo3", dataProgress.getPhoto3());
//            values.put("Photo4", dataProgress.getPhoto4());
//            values.put("Photo5", dataProgress.getPhoto5());
//            values.put("EntryBy", dataProgress.getUpload_by());
//            c = db.insert("UploadData", null, values);
//            db.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//            return c;
//        }
//        return c;
//
//    }

    public long inserAnganbariDetails(MyGpsDataEntity myGpsDataEntity) {

        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("District_Code", myGpsDataEntity.getDistId());
            values.put("Project_Code", myGpsDataEntity.getProjectId());
            values.put("Sector_Code", myGpsDataEntity.getSectorId());
            values.put("AWC_Code", myGpsDataEntity.getAwcid());
            values.put("Fyear", myGpsDataEntity.getYearId());
            values.put("latitude", myGpsDataEntity.getLatitude());
            values.put("longitude", myGpsDataEntity.getLongitude());
            values.put("updateedDate", myGpsDataEntity.getUpdatedDate());
            values.put("userId", myGpsDataEntity.getUserId());
            values.put("Photo1", myGpsDataEntity.getPhoto1());
            values.put("Photo2", myGpsDataEntity.getPhoto2());
            values.put("isWaterRes", myGpsDataEntity.getIsWaterRes());
            values.put("isBuilding", myGpsDataEntity.getIsBuilding());
            c = db.insert("UploadDataforGps", null, values);
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }
    public long updateAnganbariDetails(MyGpsDataEntity myGpsDataEntity) {

        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("District_Code", myGpsDataEntity.getDistId());
            values.put("Project_Code", myGpsDataEntity.getProjectId());
            values.put("Sector_Code", myGpsDataEntity.getSectorId());
            values.put("AWC_Code", myGpsDataEntity.getAwcid());
            values.put("Fyear", myGpsDataEntity.getYearId());
            values.put("latitude", myGpsDataEntity.getLatitude());
            values.put("longitude", myGpsDataEntity.getLongitude());
            values.put("updateedDate", myGpsDataEntity.getUpdatedDate());
            values.put("userId", myGpsDataEntity.getUserId());
            values.put("Photo1", myGpsDataEntity.getPhoto1());
            values.put("Photo2", myGpsDataEntity.getPhoto2());
            values.put("isWaterRes", myGpsDataEntity.getIsWaterRes());
            values.put("isBuilding", myGpsDataEntity.getIsBuilding());

            c = db.update("UploadDataforGps",  values,"sl_no=?",new String[]{myGpsDataEntity.getSl_no()});
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }
    public ArrayList<MyGpsDataEntity> getAllGpsList(String userId, String from) {
        Cursor cur=null;
        ArrayList<MyGpsDataEntity> progressList = new ArrayList<MyGpsDataEntity>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] param = {userId};
        //Cursor cur = db.rawQuery("select sl_no,District_Code,Project_Code,Sector_Code,AWC_Code,Fyear,longitude,latitude,updateedDate,userId,isWaterRes,isBuilding from UploadDataforGps where userId = ?", param);
        if(from.equalsIgnoreCase("upload")) {
            cur = db.rawQuery("select * from UploadDataforGps where Latitude IS NOT NULL and Longitude IS NOT NULL and userId = ?", param);
        }else{
            cur = db.rawQuery("select * from UploadDataforGps where userId = ?", param);
        }
        try {
            int count = cur.getCount();
            while (cur.moveToNext()) {

                MyGpsDataEntity progress = new MyGpsDataEntity();
                progress.setSl_no(cur.getString(cur.getColumnIndex("sl_no")));
                progress.setDistId(cur.isNull(cur.getColumnIndex("District_Code")) == false ? cur.getString(cur.getColumnIndex("District_Code")) : "");
                progress.setProjectId(cur.isNull(cur.getColumnIndex("Project_Code")) == false ? cur.getString(cur.getColumnIndex("Project_Code")) : "");
                progress.setSectorId(cur.isNull(cur.getColumnIndex("Sector_Code")) == false ? cur.getString(cur.getColumnIndex("Sector_Code")) : "");


                progress.setAwcid(cur.isNull(cur.getColumnIndex("AWC_Code")) == false ? cur.getString(cur.getColumnIndex("AWC_Code")) : "");
                progress.setYearId(cur.isNull(cur.getColumnIndex("Fyear")) == false ? cur.getString(cur.getColumnIndex("Fyear")) : "");

                progress.setLongitude(cur.isNull(cur.getColumnIndex("longitude")) == false ? cur.getString(cur.getColumnIndex("longitude")) : "");
                progress.setLatitude(cur.isNull(cur.getColumnIndex("latitude")) == false ? cur.getString(cur.getColumnIndex("latitude")) : "");
                progress.setUpdatedDate(cur.isNull(cur.getColumnIndex("updateedDate")) == false ? cur.getString(cur.getColumnIndex("updateedDate")) : "");
                progress.setUserId(cur.isNull(cur.getColumnIndex("userId")) == false ? cur.getString(cur.getColumnIndex("userId")) : "");

                progress.setPhoto1(cur.isNull(cur.getColumnIndex("Photo1")) == false ? cur.getBlob(cur.getColumnIndex("Photo1")) : null);
                progress.setPhoto2(cur.isNull(cur.getColumnIndex("Photo2")) == false ? cur.getBlob(cur.getColumnIndex("Photo2")) : null);

                progress.setIsWaterRes(cur.isNull(cur.getColumnIndex("isWaterRes")) == false ? cur.getString(cur.getColumnIndex("isWaterRes")) : "");
                progress.setIsBuilding(cur.isNull(cur.getColumnIndex("isBuilding")) == false ? cur.getString(cur.getColumnIndex("isBuilding")) : "");

                progressList.add(progress);
            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            cur.close();
            db.close();
        }
        return progressList;

    }

    public MyGpsDataEntity getGpsDetails(String SlNo) {
        Cursor cur=null;
        MyGpsDataEntity progress = new MyGpsDataEntity();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] param = {SlNo};
        //Cursor cur = db.rawQuery("select sl_no,District_Code,Project_Code,Sector_Code,AWC_Code,Fyear,longitude,latitude,updateedDate,userId,isWaterRes,isBuilding from UploadDataforGps where userId = ?", param);

            cur = db.rawQuery("select * from UploadDataforGps where sl_no = ?", param);

        try {
            int count = cur.getCount();
            while (cur.moveToNext()) {

                progress.setSl_no(cur.getString(cur.getColumnIndex("sl_no")));
                progress.setDistId(cur.isNull(cur.getColumnIndex("District_Code")) == false ? cur.getString(cur.getColumnIndex("District_Code")) : "");
                progress.setProjectId(cur.isNull(cur.getColumnIndex("Project_Code")) == false ? cur.getString(cur.getColumnIndex("Project_Code")) : "");
                progress.setSectorId(cur.isNull(cur.getColumnIndex("Sector_Code")) == false ? cur.getString(cur.getColumnIndex("Sector_Code")) : "");


                progress.setAwcid(cur.isNull(cur.getColumnIndex("AWC_Code")) == false ? cur.getString(cur.getColumnIndex("AWC_Code")) : "");
                progress.setYearId(cur.isNull(cur.getColumnIndex("Fyear")) == false ? cur.getString(cur.getColumnIndex("Fyear")) : "");

                progress.setLongitude(cur.isNull(cur.getColumnIndex("longitude")) == false ? cur.getString(cur.getColumnIndex("longitude")) : "");
                progress.setLatitude(cur.isNull(cur.getColumnIndex("latitude")) == false ? cur.getString(cur.getColumnIndex("latitude")) : "");
                progress.setUpdatedDate(cur.isNull(cur.getColumnIndex("updateedDate")) == false ? cur.getString(cur.getColumnIndex("updateedDate")) : "");
                progress.setUserId(cur.isNull(cur.getColumnIndex("userId")) == false ? cur.getString(cur.getColumnIndex("userId")) : "");

                progress.setPhoto1(cur.isNull(cur.getColumnIndex("Photo1")) == false ? cur.getBlob(cur.getColumnIndex("Photo1")) : null);
                progress.setPhoto2(cur.isNull(cur.getColumnIndex("Photo2")) == false ? cur.getBlob(cur.getColumnIndex("Photo2")) : null);

                progress.setIsWaterRes(cur.isNull(cur.getColumnIndex("isWaterRes")) == false ? cur.getString(cur.getColumnIndex("isWaterRes")) : "");
                progress.setIsBuilding(cur.isNull(cur.getColumnIndex("isBuilding")) == false ? cur.getString(cur.getColumnIndex("isBuilding")) : "");

            }


        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            cur.close();
            db.close();
        }
        return progress;

    }


    public long insertVoucher(VoutcherEntity voutcherEntity) {

        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("chaval_detail", voutcherEntity.getChaval_detail());
            values.put("masur_daal_detail", voutcherEntity.getMasur_daal_detail());
            values.put("chana_detail", voutcherEntity.getChana_detail());
            values.put("mungfali_detail", voutcherEntity.getMungfali_detail());
            values.put("gur_detail", voutcherEntity.getGur_detail());
            values.put("suji_detail", voutcherEntity.getSuji_detail());
            values.put("chini_detail", voutcherEntity.getChini_detail());
            values.put("refine_tel_detail", voutcherEntity.getRefine_tel_detail());
            values.put("iyodin_namak_detail", voutcherEntity.getIyodin_namak_detail());
            values.put("masala_detail", voutcherEntity.getMasala_detail());
            values.put("nasta_detail", voutcherEntity.getNasta_detail());
            values.put("hari_sabji_detail", voutcherEntity.getHari_sabji_detail());
            values.put("jalavan_detail", voutcherEntity.getJalavan_detail());
            values.put("anda_detail", voutcherEntity.getAnda_detail());
            values.put("parivahan_detail", voutcherEntity.getParivahan_detail());
            values.put("total_detail", voutcherEntity.getTotal_detail());
            values.put("ls_id", voutcherEntity.getLs_id());
            values.put("voucher_no", voutcherEntity.getVoucher_no());
            values.put("vouter_img", voutcherEntity.getVouter_img());

            c = db.insert("myVoutcher", null, values);
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }

    public ArrayList<VoutcherEntity> getAllVoucher() {

        ArrayList<VoutcherEntity> progressList = new ArrayList<VoutcherEntity>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db
                    .rawQuery(
                            "select * from myVoutcher",
                            null);

            int count = cur.getCount();

            while (cur.moveToNext()) {

                VoutcherEntity progress = new VoutcherEntity();
                progress.setSlno(cur.getString(cur.getColumnIndex("slno")));
                progress.setChana_detail(cur.isNull(cur.getColumnIndex("chaval_detail")) == false ? cur.getString(cur.getColumnIndex("chaval_detail")) : "");
                progress.setMasur_daal_detail(cur.isNull(cur.getColumnIndex("masur_daal_detail")) == false ? cur.getString(cur.getColumnIndex("masur_daal_detail")) : "");
                progress.setChana_detail(cur.isNull(cur.getColumnIndex("chana_detail")) == false ? cur.getString(cur.getColumnIndex("chana_detail")) : "");
                progress.setMungfali_detail(cur.isNull(cur.getColumnIndex("mungfali_detail")) == false ? cur.getString(cur.getColumnIndex("mungfali_detail")) : "");
                progress.setGur_detail(cur.isNull(cur.getColumnIndex("gur_detail")) == false ? cur.getString(cur.getColumnIndex("gur_detail")) : "");
                progress.setSuji_detail(cur.isNull(cur.getColumnIndex("suji_detail")) == false ? cur.getString(cur.getColumnIndex("suji_detail")) : "");
                progress.setChini_detail(cur.isNull(cur.getColumnIndex("chini_detail")) == false ? cur.getString(cur.getColumnIndex("chini_detail")) : "");
                progress.setRefine_tel_detail(cur.isNull(cur.getColumnIndex("refine_tel_detail")) == false ? cur.getString(cur.getColumnIndex("refine_tel_detail")) : "");
                progress.setIyodin_namak_detail(cur.isNull(cur.getColumnIndex("iyodin_namak_detail")) == false ? cur.getString(cur.getColumnIndex("iyodin_namak_detail")) : "");
                progress.setMasala_detail(cur.isNull(cur.getColumnIndex("masala_detail")) == false ? cur.getString(cur.getColumnIndex("masala_detail")) : "");
                progress.setNasta_detail(cur.isNull(cur.getColumnIndex("nasta_detail")) == false ? cur.getString(cur.getColumnIndex("nasta_detail")) : "");
                progress.setHari_sabji_detail(cur.isNull(cur.getColumnIndex("hari_sabji_detail")) == false ? cur.getString(cur.getColumnIndex("hari_sabji_detail")) : "");
                progress.setJalavan_detail(cur.isNull(cur.getColumnIndex("jalavan_detail")) == false ? cur.getString(cur.getColumnIndex("jalavan_detail")) : "");
                progress.setAnda_detail(cur.isNull(cur.getColumnIndex("anda_detail")) == false ? cur.getString(cur.getColumnIndex("anda_detail")) : "");
                progress.setParivahan_detail(cur.isNull(cur.getColumnIndex("parivahan_detail")) == false ? cur.getString(cur.getColumnIndex("parivahan_detail")) : "");
                progress.setTotal_detail(cur.isNull(cur.getColumnIndex("total_detail")) == false ? cur.getString(cur.getColumnIndex("total_detail")) : "");
                progress.setLs_id(cur.isNull(cur.getColumnIndex("ls_id")) == false ? cur.getString(cur.getColumnIndex("ls_id")) : "");
                progress.setVoucher_no(cur.isNull(cur.getColumnIndex("voucher_no")) == false ? cur.getString(cur.getColumnIndex("voucher_no")) : "");
                progress.setVouter_img(cur.isNull(cur.getColumnIndex("vouter_img")) == false ?
                        cur.getBlob(cur.getColumnIndex("vouter_img")) : null);


                progressList.add(progress);
            }
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception

        }
        return progressList;

    }


    public static String getCurrentDate() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        int h = cal.get(Calendar.HOUR);
        int m = cal.get(Calendar.MINUTE);
        int s = cal.get(Calendar.SECOND);

        String date = day + "/" + month + "/" + year + "  " + h + ":" + m + ":" + s;
        return date;

    }


    public ArrayList<DataProgress> getAllProgressList(String userId) {

        ArrayList<DataProgress> progressList = new ArrayList<DataProgress>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] param = {userId};
        Cursor cur = db.rawQuery("select slno,District_Code,Block_Code,Sector_Code,Panchayat_Code,F1,F2,F3,F4,F5,F6,F7,F8,F9,F10,F11,F12,F13,F14,Fyear,Remarks,EntryDate,Latitude,Longitude,UploadDate,EntryBy from UploadData where Latitude IS NOT NULL and Longitude IS NOT NULL and EntryBy = ? LIMIT 3", param);

        try {

            while (cur.moveToNext()) {

                DataProgress progress = new DataProgress();

                progress.setSlno(cur.getString(cur.getColumnIndex("slno")));


                progress.setDistrict_code(cur.isNull(cur.getColumnIndex("District_Code")) == false ? cur.getString(cur.getColumnIndex("District_Code")) : "");
                progress.setBlock_code(cur.isNull(cur.getColumnIndex("Block_Code")) == false ? cur.getString(cur.getColumnIndex("Block_Code")) : "");
                progress.setSector_code(cur.isNull(cur.getColumnIndex("Sector_Code")) == false ? cur.getString(cur.getColumnIndex("Sector_Code")) : "");
                progress.setPanchayat_code(cur.isNull(cur.getColumnIndex("Panchayat_Code")) == false ? cur.getString(cur.getColumnIndex("Panchayat_Code")) : "");


                progress.setF11(cur.isNull(cur.getColumnIndex("F11")) == false ? cur.getString(cur.getColumnIndex("F11")) : "");
                progress.setF12(cur.isNull(cur.getColumnIndex("F12")) == false ? cur.getString(cur.getColumnIndex("F12")) : "");
                progress.setSpMakeFoodData(cur.isNull(cur.getColumnIndex("F13")) == false ? cur.getString(cur.getColumnIndex("F13")) : "");
                progress.setRegisteredChildren(cur.isNull(cur.getColumnIndex("F14")) == false ? cur.getString(cur.getColumnIndex("F14")) : "");
                progress.setFyear(cur.isNull(cur.getColumnIndex("Fyear")) == false ? cur.getString(cur.getColumnIndex("Fyear")) : "");

                progress.setSpinner1_Data(cur.isNull(cur.getColumnIndex("F1")) == false ? cur.getString(cur.getColumnIndex("F1")) : "");
                progress.setSpinner2_Data(cur.isNull(cur.getColumnIndex("F2")) == false ? cur.getString(cur.getColumnIndex("F2")) : "");
                progress.setSpinner3_Data(cur.isNull(cur.getColumnIndex("F3")) == false ? cur.getString(cur.getColumnIndex("F3")) : "");
                progress.setSpinner4_Data(cur.isNull(cur.getColumnIndex("F4")) == false ? cur.getString(cur.getColumnIndex("F4")) : "");
                progress.setSpinner5_Data(cur.isNull(cur.getColumnIndex("F5")) == false ? cur.getString(cur.getColumnIndex("F5")) : "");

                progress.setText1_Data(cur.isNull(cur.getColumnIndex("F6")) == false ? cur.getString(cur.getColumnIndex("F6")) : "");
                progress.setText2_Data(cur.isNull(cur.getColumnIndex("F7")) == false ? cur.getString(cur.getColumnIndex("F7")) : "");
                progress.setText3_Data(cur.isNull(cur.getColumnIndex("F8")) == false ? cur.getString(cur.getColumnIndex("F8")) : "");
                progress.setText4_Data(cur.isNull(cur.getColumnIndex("F9")) == false ? cur.getString(cur.getColumnIndex("F9")) : "");
                progress.setText5_Data(cur.isNull(cur.getColumnIndex("F10")) == false ? cur.getString(cur.getColumnIndex("F10")) : "");
                progress.setRemark_Data(cur.isNull(cur.getColumnIndex("Remarks")) == false ? cur.getString(cur.getColumnIndex("Remarks")) : "");


                progress.setEntry_Date(cur.isNull(cur.getColumnIndex("EntryDate")) == false ? cur.getString(cur.getColumnIndex("EntryDate")) : "");
                progress.setLatitude(cur.isNull(cur.getColumnIndex("Latitude")) == false ? cur.getString(cur.getColumnIndex("Latitude")) : "");
                progress.setLongitude(cur.isNull(cur.getColumnIndex("Longitude")) == false ? cur.getString(cur.getColumnIndex("Longitude")) : "");
                progress.setGps_date(cur.isNull(cur.getColumnIndex("UploadDate")) == false ? cur.getString(cur.getColumnIndex("UploadDate")) : "");


                progress.setUpload_by(cur.getString(cur.getColumnIndex("EntryBy")));

                String[] param2 = {userId, cur.getString(cur.getColumnIndex("slno"))};
                Cursor cur1 = db.rawQuery("select Photo1 from UploadData where Latitude IS NOT NULL and Longitude IS NOT NULL and EntryBy = ? and slno = ? ", param2);
                Cursor cur2 = db.rawQuery("select Photo2 from UploadData where Latitude IS NOT NULL and Longitude IS NOT NULL and EntryBy = ? and slno = ? ", param2);
                Cursor cur3 = db.rawQuery("select Photo3 from UploadData where Latitude IS NOT NULL and Longitude IS NOT NULL and EntryBy = ? and slno = ? ", param2);
                Cursor cur4 = db.rawQuery("select Photo4 from UploadData where Latitude IS NOT NULL and Longitude IS NOT NULL and EntryBy = ? and slno = ? ", param2);
                Cursor cur5 = db.rawQuery("select Photo5 from UploadData where Latitude IS NOT NULL and Longitude IS NOT NULL and EntryBy = ? and slno = ? ", param2);
                try {

                    while (cur1.moveToNext()) {
                        progress.setPhoto1(cur1.isNull(cur1.getColumnIndex("Photo1")) == false ? Base64
                                .encodeToString(
                                        cur1.getBlob(cur1.getColumnIndex("Photo1")),
                                        Base64.NO_WRAP) : "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cur1.close();
                }
                try {
                    while (cur2.moveToNext()) {
                        progress.setPhoto2(cur2.isNull(cur2.getColumnIndex("Photo2")) == false ? Base64
                                .encodeToString(cur2.getBlob(cur2.getColumnIndex("Photo2")), Base64.NO_WRAP) : "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cur2.close();
                }
                try {
                    while (cur3.moveToNext()) {
                        progress.setPhoto3(cur3.isNull(cur3.getColumnIndex("Photo3")) == false ? Base64
                                .encodeToString(
                                        cur3.getBlob(cur3.getColumnIndex("Photo3")),
                                        Base64.NO_WRAP) : "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cur3.close();
                }
                try {
                    while (cur4.moveToNext()) {
                        progress.setPhoto4(cur4.isNull(cur4.getColumnIndex("Photo4")) == false ? Base64
                                .encodeToString(
                                        cur4.getBlob(cur4.getColumnIndex("Photo4")),
                                        Base64.NO_WRAP) : "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cur4.close();
                }
                try {
                    while (cur5.moveToNext()) {
                        progress.setPhoto5(cur5.isNull(cur5.getColumnIndex("Photo5")) == false ? Base64
                                .encodeToString(
                                        cur5.getBlob(cur5.getColumnIndex("Photo5")),
                                        Base64.NO_WRAP) : "");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    cur5.close();
                }
                progressList.add(progress);

            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        } finally {
            cur.close();
            db.close();
        }
        return progressList;

    }

    public long deletePendingUpload(String pid, String userId) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(pid), userId};
            c = db.delete("UploadData", "slno=? and EntryBy =?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long deletePendingUpload2(String pid, String userId) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(pid), userId};
            c = db.delete("UploadDataforGps", "sl_no=? and userId=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long deletePendingUpload3(String pid, String userId) {
        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            String[] DeleteWhere = {String.valueOf(pid), userId};
            c = db.delete("SevikaSahaika", "slno=? and userId=?", DeleteWhere);

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }


    public int getNumberOfPendingData(String userId) {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select slno from Inspection where Latitude IS NOT NULL and Longitude IS NOT NULL and UploadBy =?", whereArgs);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }

    public int getNumberTotalOfPendingData(String userId) {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select slno from UploadData where EntryBy =?", whereArgs);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }

    public int getNumberOfPendingData2(String userId) {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select sl_no from UploadDataforGps where Latitude IS NOT NULL and Longitude IS NOT NULL and userId=? ", whereArgs);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }
    public int getNumberOfPendingData2GPS(String userId) {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};
            Cursor cur = db.rawQuery("Select sl_no from UploadDataforGps where userId=? ", whereArgs);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }

    public int getNumberOfPendingData3() {

        int x = 0;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {null, null};
            Cursor cur = db.rawQuery("Select * from myVoutcher", null);
            x = cur.getCount();
            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return x;

    }


    public ArrayList<DataProgress> getProgressList(String userId) {


        ArrayList<DataProgress> progressList = new ArrayList<DataProgress>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] whereArgs = {userId};


            Cursor cur = db
                    .rawQuery("Select * from UploadData where EntryBy=?", whereArgs);

            while (cur.moveToNext()) {

                DataProgress progress = new DataProgress();
                progress.setSlno(cur.getString(cur.getColumnIndex("slno")));
                progress.setLatitude(cur.isNull(cur.getColumnIndex("Latitude")) == false ? cur.getString(cur.getColumnIndex("Latitude")) : "NULL");
                progress.setLongitude(cur.isNull(cur.getColumnIndex("Longitude")) == false ? cur.getString(cur.getColumnIndex("Longitude")) : "NULL");

                progress.setText1_Data(cur.isNull(cur.getColumnIndex("F6")) == false ? cur.getString(cur.getColumnIndex("F6")) : "");
                progress.setText5_Data(cur.isNull(cur.getColumnIndex("F10")) == false ? cur.getString(cur.getColumnIndex("F10")) : "");
                progress.setRemark_Data(cur.isNull(cur.getColumnIndex("Remarks")) == false ? cur.getString(cur.getColumnIndex("Remarks")) : "");
                String pcode = (cur.isNull(cur.getColumnIndex("Panchayat_Code")) == false ? cur.getString(cur.getColumnIndex("Panchayat_Code")) : "");
                String kname = "";

                progress.setEntry_Date(cur.isNull(cur.getColumnIndex("EntryDate")) == false ? cur.getString(cur.getColumnIndex("EntryDate")) : "");


                whereArgs = new String[]{pcode};
                Cursor c = db.rawQuery(
                        "select * from Panchayat where Code=?",
                        whereArgs);

                if (c.getCount() > 0) {
                    c.moveToNext();
                    kname = c.getString(c.getColumnIndex("Name"));

                }
                c.close();
                progress.setPanchayatName(kname);
                progressList.add(progress);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception

        }
        return progressList;

    }

    //TODO enrolment activity edit data
//    public DataProgress getEditData(String slno) {
//
//
//        DataProgress progress = new DataProgress();
//
//
//        try {
//
//            SQLiteDatabase db = this.getReadableDatabase();
//            String[] sl = {slno};
//            Cursor cur = db
//                    .rawQuery(
//                            "select * from UploadData where slno=?",
//                            sl);
//
//
//            String s1, s2, s3, s4, s5, s34;
//
//            while (cur.moveToNext()) {
//
//
//                progress.setSlno(cur.getString(cur.getColumnIndex("slno")));
//
//
//                progress.setDistrict_code(!(cur.getString(cur.getColumnIndex("District_Code"))).equals("") ? cur.getString(cur.getColumnIndex("District_Code")) : "");
//                progress.setBlock_code(!cur.getString(cur.getColumnIndex("Block_Code")).equals("") ? cur.getString(cur.getColumnIndex("Block_Code")) : "");
//                progress.setSector_code(!cur.getString(cur.getColumnIndex("Sector_Code")).equals("") ? cur.getString(cur.getColumnIndex("Sector_Code")) : "");
//                progress.setPanchayat_code(!cur.getString(cur.getColumnIndex("Panchayat_Code")).equals("") ? cur.getString(cur.getColumnIndex("Panchayat_Code")) : "");
//
//
//                String fyearcode = (!cur.getString(cur.getColumnIndex("Fyear")).equals("") ? cur.getString(cur.getColumnIndex("Fyear")) : "");
//
//                progress.setF11(!cur.getString(cur.getColumnIndex("F11")).equals("") ? cur.getString(cur.getColumnIndex("F11")) : "");
//                progress.setF12(!cur.getString(cur.getColumnIndex("F12")).equals("") ? cur.getString(cur.getColumnIndex("F12")) : "");
//                //progress.setSpMakeFoodData(!cur.getString(cur.getColumnIndex("F13")).equals("") ? cur.getString(cur.getColumnIndex("F13")) : "");
//                progress.setRegisteredChildren(!cur.getString(cur.getColumnIndex("F14")).equals("") ? cur.getString(cur.getColumnIndex("F14")) : "");
//
//
//                s1 = (cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.s1_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.s1_field)) : "");
//                s2 = (cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.s2_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.s2_field)) : "");
//                s3 = (cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.s3_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.s3_field)) : "");
//                s4 = (cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.s4_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.s4_field)) : "");
//                s5 = (cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.s5_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.s5_field)) : "");
//                s34 = (cur.isNull(cur.getColumnIndex("F13")) == false ? cur.getString(cur.getColumnIndex("F13")) : "");
//
//
//                progress.setText1_Data(cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.t1_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.t1_field)) : "");
//                progress.setText2_Data(cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.t2_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.t2_field)) : "");
//                progress.setText3_Data(cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.t3_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.t3_field)) : "");
//                progress.setText4_Data(cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.t4_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.t4_field)) : "");
//                progress.setText5_Data(cur.isNull(cur.getColumnIndex(NewEnrollmentActivity.t5_field)) == false ? cur.getString(cur.getColumnIndex(NewEnrollmentActivity.t5_field)) : "");
//                progress.setRemark_Data(cur.isNull(cur.getColumnIndex(feedEntry.Remark)) == false ? cur.getString(cur.getColumnIndex(feedEntry.Remark)) : "");
//
//
//                progress.setEntry_Date(cur.isNull(cur.getColumnIndex("EntryDate")) == false ? cur.getString(cur.getColumnIndex("EntryDate")) : "");
//                progress.setLatitude(cur.isNull(cur.getColumnIndex("Latitude")) == false ? cur.getString(cur.getColumnIndex("Latitude")) : "");
//                progress.setLongitude(cur.isNull(cur.getColumnIndex("Longitude")) == false ? cur.getString(cur.getColumnIndex("Longitude")) : "");
//                progress.setSpMakeFoodData(s34);
//
//                cur.close();
//                String[] whereArgs = new String[]{NewEnrollmentActivity.s1_field, s1};
//                cur = db.rawQuery(
//                        "select * from SpinnerMaster where Field=? and Code=?",
//                        whereArgs);
//
//                if (cur.getCount() > 0) {
//                    cur.moveToNext();
//                    progress.setSpinner1_Data(cur.getString(2));
//                } else progress.setSpinner1_Data("");
//                cur.close();
//
//
//                whereArgs = new String[]{fyearcode};
//                cur = db.rawQuery(
//                        "select * from Fyear where slno=?",
//                        whereArgs);
//
//                if (cur.getCount() > 0) {
//                    cur.moveToNext();
//
//                    progress.setFyear(cur.getString(1));
//                    ;
//                } else progress.setFyear("");
//                ;
//                cur.close();
//
//
//                whereArgs = new String[]{NewEnrollmentActivity.s2_field, s2};
//                cur = db.rawQuery(
//                        "select * from SpinnerMaster where Field=? and Code=?",
//                        whereArgs);
//
//                if (cur.getCount() > 0) {
//                    cur.moveToNext();
//
//                    progress.setSpinner2_Data(cur.getString(2));
//                } else progress.setSpinner2_Data("");
//                cur.close();
//
//
//                whereArgs = new String[]{NewEnrollmentActivity.s3_field, s3};
//                cur = db.rawQuery(
//                        "select * from SpinnerMaster where Field=? and Code=?",
//                        whereArgs);
//                if (cur.getCount() > 0) {
//                    cur.moveToNext();
//
//                    progress.setSpinner3_Data(cur.getString(2));
//                } else progress.setSpinner3_Data("");
//                cur.close();
//
//
//                whereArgs = new String[]{NewEnrollmentActivity.s4_field, s4};
//                cur = db.rawQuery(
//                        "select * from SpinnerMaster where Field=? and Code=?",
//                        whereArgs);
//                if (cur.getCount() > 0) {
//                    cur.moveToNext();
//
//                    progress.setSpinner4_Data(cur.getString(2));
//                } else progress.setSpinner4_Data("");
//                cur.close();
//
//
//                whereArgs = new String[]{NewEnrollmentActivity.s5_field, s5};
//                cur = db.rawQuery(
//                        "select * from SpinnerMaster where Field=? and Code=?",
//                        whereArgs);
//
//                if (cur.getCount() > 0) {
//                    cur.moveToNext();
//
//                    progress.setSpinner5_Data(cur.getString(2));
//                } else progress.setSpinner5_Data("");
//                cur.close();
//
//               /* whereArgs = new String[]{"F13", s34};
//                cur = db.rawQuery(
//                        "select * from SpinnerMaster where Field=? and Code=?",
//                        whereArgs);
//
//                if (cur.getCount() > 0) {
//                    cur.moveToNext();
//
//                    progress.setSpMakeFoodData(cur.getString(2));
//                } else progress.setSpMakeFoodData("");
//                cur.close();*/
//
//
//            }
//
//            cur.close();
//            db.close();
//
//        } catch (Exception e) {
//            // TODO: handle exception
//            e.printStackTrace();
//
//        }
//        return progress;
//    }


    public boolean deleteFromDB(String slno) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] sl = {slno};


        long c = db.delete("UploadData", "slno=?", sl);
        if (c > 0) return true;
        else return false;
    }


    public ArrayList<LocalSpinnerData> getLocalDependentSpinnerData(String tableName, String dependentField, String param) {


        ArrayList<LocalSpinnerData> fieldList = new ArrayList<LocalSpinnerData>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = null;
            String depField = dependentField;
            if ((dependentField != null) && (param != null) && (tableName != null)) {

                String[] params = new String[]{param};


                cur = db.rawQuery("Select * from " + tableName + " where " + depField + "=?", params);


            } else if (tableName != null) {
                cur = db.rawQuery("Select * from " + tableName, null);
            }


            if (cur != null) {
                while (cur.moveToNext()) {
                    String Code = cur.getString(cur.getColumnIndex("Code"));
                    String Name = cur.getString(cur.getColumnIndex("Name"));
                    String AwcCode = "";
                    if (tableName.equalsIgnoreCase("Panchayat")) {
                        AwcCode = cur.getString(cur.getColumnIndex("slno"));
                    }


                    fieldList.add(new LocalSpinnerData(Code, Name, "", AwcCode));
                }
            }

            cur.close();
            db.close();


        } catch (Exception e) {
            // TODO: handle exception
            return fieldList;

        }

        return fieldList;


    }
    public long setFyearDataLocal(ArrayList<FyearWeb> yearlist) {

        long c = -1;
        ArrayList<FyearWeb> fyear = yearlist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if (fyear != null) {
            try {


                for (int i = 0; i < fyear.size(); i++) {

                    values.put("Year", fyear.get(i).getYear());
                    values.put("yearId", fyear.get(i).getFyearId());

                    values.put("isCurrent", fyear.get(i).getIsCurrent());

                    c = db.insert("Fyear", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setDistrictDataLocal(ArrayList<DistrictData> distlist) {

        long c = -1;
        ArrayList<DistrictData> dist = distlist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (dist != null) {
            try {
                for (int i = 0; i < dist.size(); i++) {
                    values.put("Code", dist.get(i).getCode());
                    values.put("Name", dist.get(i).getValue());
                    String[] param = {dist.get(i).getCode()};
                   // long update = db.update("DistDetail", values, "Code = ?", param);
                  //  if (!(update > 0))
                        c = db.insert("DistDetail", null, values);
                }
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setDistrictDataLocalUserWise(ArrayList<District> distlist, String userid) {

        long c = -1;
        ArrayList<District> dist = distlist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (dist != null) {
            try {
                for (int i = 0; i < dist.size(); i++) {
                    values.put("Code", dist.get(i).get_DistCode());
                    values.put("Name", dist.get(i).get_DistName());
                    values.put("userid", userid);
                    String[] param = {dist.get(i).get_DistCode()};
                    //long update = db.update("DistDetail", values, "Code = ?", param);
                  //  if (!(update > 0))
                        c = db.insert("DistDetailUserBy", null, values);
                }
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setBlockDataLocal(ArrayList<BlockWeb> blocklist, String distCode) {

        long c = -1;
        ArrayList<BlockWeb> block = blocklist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Block");
        if (block.size() > 0) {
            try {


                for (int i = 0; i < block.size(); i++) {

                    values.put("Code", block.get(i).getCode());
                    values.put("Name", block.get(i).getValue());
                    values.put("District_Code", block.get(i).getDistrictcode());
                  /*  String[] param={block.get(i).getCode()};
                    long update = db.update("Block", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Block", null, values);
                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }
    public long setBlockDataLocaluSERbY(ArrayList<BlockWeb> sectorWebArrayList, String userid) {

        long c = -1;
        ArrayList<BlockWeb> sectorWebs = sectorWebArrayList;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from BlockUserBy");
        if (sectorWebs.size() > 0) {
            try {


                for (int i = 0; i < sectorWebs.size(); i++) {
                    values.put("BlockCode", sectorWebs.get(i).getBlockCode());
                    values.put("BlockName", sectorWebs.get(i).getBlockName());
                    values.put("DistCode", sectorWebs.get(i).getDistCode());
                    values.put("UserId",sectorWebs.get(i).getUserId().toLowerCase());
                    /*String[] param={sectorWebs.get(i).getCode()};
                    long update = db.update("Sector", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("BlockUserBy", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public ArrayList<BlockWeb> getUserWiseBlockList(String projectCode) {

        // PlaceDataSQL placeData = new PlaceDataSQL(MainActivity.this);
        ArrayList<BlockWeb> sectorList = new ArrayList<BlockWeb>();
        Cursor cur;
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[]{projectCode.toLowerCase()};
            cur = db.rawQuery("Select * from BlockUserBy WHERE UserId=?", params);


            int x = cur.getCount();

            while (cur.moveToNext()) {

                BlockWeb localSpinnerData = new BlockWeb();
                localSpinnerData.setBlockCode((cur.getString(cur.getColumnIndex("BlockCode"))));
                localSpinnerData.setBlockName(cur.getString(cur.getColumnIndex("BlockName")));
                localSpinnerData.setDistCode(cur.getString(cur.getColumnIndex("DistCode")));
                localSpinnerData.setUserId(cur.getString(cur.getColumnIndex("UserId")));


                sectorList.add(localSpinnerData);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return sectorList;

    }
    public long setBlockDataForDist(ArrayList<Block> blocklist, String distCode) {

        long c = -1;
        ArrayList<Block> block = blocklist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Block");
        if (block.size() > 0) {
            try {


                for (int i = 0; i < block.size(); i++) {

                    values.put("Code", block.get(i).getBlockCode());
                    values.put("Name", block.get(i).getBlockName());
                    values.put("District_Code", distCode);
                  /*  String[] param={block.get(i).getCode()};
                    long update = db.update("Block", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Block", null, values);
                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }
    public long setSectorDataLocal(ArrayList<SectorWeb> sectorWebArrayList, String blockCode) {

        long c = -1;
        ArrayList<SectorWeb> sectorWebs = sectorWebArrayList;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Sector");
        if (sectorWebs.size() > 0) {
            try {


                for (int i = 0; i < sectorWebs.size(); i++) {
                    values.put("Code", sectorWebs.get(i).getCode());
                    values.put("Name", sectorWebs.get(i).getValue());
                    values.put("Block_Code", blockCode);
                    /*String[] param={sectorWebs.get(i).getCode()};
                    long update = db.update("Sector", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Sector", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public long setPanchayatDataLocal(ArrayList<PanchayatWeb> panchayatlist, String sectorCode) {

        long c = -1;
        ArrayList<PanchayatWeb> panchayat = panchayatlist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Panchayat");
        if (panchayat.size() > 0) {
            try {


                for (int i = 0; i < panchayat.size(); i++) {

                    values.put("Code", panchayat.get(i).getCode());
                    values.put("slno", panchayat.get(i).getAWC_Code());
                    values.put("Name", panchayat.get(i).getValue());
                    values.put("Sector_Code", panchayat.get(i).getSectorCode());
                   /* String[] param={panchayat.get(i).getCode()};
                    long update = db.update("Panchayat", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Panchayat", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }
    public long setPanchayatDataForDistBlock(ArrayList<PanchayatData> panchayatlist, String distcode, String blkcode) {

        long c = -1;
        //CREATE TABLE `Panchayat1` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `DistCode` TEXT,
        // `BlockCode` TEXT, `PanchayatCode` TEXT, `PanchayatName` TEXT )
        ArrayList<PanchayatData> panchayat = panchayatlist;
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        db.execSQL("Delete from Panchayat1");
        if (panchayat.size() > 0) {
            try {


                for (int i = 0; i < panchayat.size(); i++) {

                    values.put("PanchayatCode", panchayat.get(i).getPcode());
                    values.put("PanchayatName", panchayat.get(i).getPname());
                    values.put("DistCode", distcode);
                    values.put("BlockCode", blkcode);
                   // values.put("Sector_Code", panchayat.get(i).getSectorCode());
                   /* String[] param={panchayat.get(i).getCode()};
                    long update = db.update("Panchayat", values, "Code = ?", param);
                    if (!(update>0))*/
                    c = db.insert("Panchayat1", null, values);

                }
                db.close();


            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public DataProgress getCredentialData() {
        DataProgress progress = new DataProgress();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
                            "select * from Credential where slno = '1'",
                            null);
            if (cur.getCount() > 0) {

                while (cur.moveToNext()) {
                    progress.setDistrict_code(null != (cur.getString(cur.getColumnIndex("District_Code"))) ? cur.getString(cur.getColumnIndex("District_Code")) : "");
                    progress.setBlock_code(null != (cur.getString(cur.getColumnIndex("Block_Code"))) ? cur.getString(cur.getColumnIndex("Block_Code")) : "");
                    progress.setPanchayat_code(null != (cur.getString(cur.getColumnIndex("Panchayat_Code"))) ? cur.getString(cur.getColumnIndex("Panchayat_Code")) : "");
                    progress.setSector_code(null != (cur.getString(cur.getColumnIndex("Ward_Code"))) ? cur.getString(cur.getColumnIndex("Ward_Code")) : "");

                    String fyearcode = (null != cur.getString(cur.getColumnIndex("Fyear")) ? cur.getString(cur.getColumnIndex("Fyear")) : "");

                    progress.setFyear(fyearcode);

                    progress.setF11(null != cur.getString(cur.getColumnIndex("F11")) ? cur.getString(cur.getColumnIndex("F11")) : "");
                    progress.setF12(null != cur.getString(cur.getColumnIndex("F12")) ? cur.getString(cur.getColumnIndex("F12")) : "");


                    progress.setDistrictName(null != cur.getString(cur.getColumnIndex("DistrictName")) ? cur.getString(cur.getColumnIndex("DistrictName")) : "");

                    progress.setBlockName(null != cur.getString(cur.getColumnIndex("BlockName")) ? cur.getString(cur.getColumnIndex("BlockName")) : "");
                    progress.setSectorName(null != cur.getString(cur.getColumnIndex("WardName")) ? cur.getString(cur.getColumnIndex("WardName")) : "");

                    progress.setPanchayatName(null != cur.getString(cur.getColumnIndex("PanchayatName")) ? cur.getString(cur.getColumnIndex("PanchayatName")) : "");

                    progress.setFyearName(null != cur.getString(cur.getColumnIndex("FyearName")) ? cur.getString(cur.getColumnIndex("FyearName")) : "");


                    String[] whereArgs = new String[]{fyearcode};
                    cur = db.rawQuery(
                            "select * from Fyear where slno=?",
                            whereArgs);

                    if (cur.getCount() > 0) {
                        cur.moveToNext();

                        progress.setFyearName(cur.getString(1));
                        progress.setFyear(cur.getString(3));
                    } else progress.setFyear("");
                    cur.close();

                }
            } else {
                progress = null;
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            return null;

        }
        return progress;
    }


    public String getPanchayatName(String pcode, Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String kname = "";

        String[] whereArgs = new String[]{pcode};
        Cursor c = db.rawQuery(
                "select * from Panchayat where Code=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            kname = c.getString(c.getColumnIndex("Name"));

        }
        c.close();


        return kname;

    }


    public String getDistrictName(String dcode, Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String name = "";

        String[] whereArgs = new String[]{dcode};
        Cursor c = db.rawQuery(
                "select * from District where Code=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            name = c.getString(c.getColumnIndex("Name"));

        }
        c.close();
        return name;

    }


    public String getBlockName(String bcode, Context context) {
        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String name = "";

        String[] whereArgs = new String[]{bcode};
        Cursor c = db.rawQuery(
                "select * from Block where Code=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            name = c.getString(c.getColumnIndex("Name"));

        }
        c.close();
        return name;

    }


    public String getSpinnerValue(Context context, String fieldName, String code) {

        DataBaseHelper helper = new DataBaseHelper(context);
        SQLiteDatabase db = helper.getReadableDatabase();

        String name = "";

        String[] whereArgs = new String[]{fieldName, code};
        Cursor c = db.rawQuery(
                "select Value from SpinnerMaster where Field=? and Code=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            name = c.getString(c.getColumnIndex("Value"));

        }
        c.close();
        return name;
    }

    public long deleteAllInspectionRecord(){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            //String[] DeleteWhere = {String.valueOf(insepectionId)};
            //c = db.delete("PlantationDetail", "InspectionID=?", DeleteWhere);
            db.execSQL("delete from PondInspectionDetail");
            db.execSQL("delete from WellInspectionDetail");
            db.execSQL("delete from Menrega_Rural_Dev_Dept");
            db.execSQL("delete from OtherDept_Of_Rural_Dev_Dept");
            db.execSQL("delete from NursuryBuildingDetail");

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }

    public long deleteAllCoInspectionRecord(){

        long c = -1;

        try {

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("delete from CoPondEncroachmentReport");
            db.execSQL("delete from CoWellEncroachmentReport");

            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            return c;
        }
        return c;
    }


    public long deleteCredential(Context context, String pid) {

        DataBaseHelper placeData = new DataBaseHelper(context);
        SQLiteDatabase db = placeData.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("Block_Code", "");
        values.put("BlockName", "");
        values.put("District_Code", "");
        values.put("DistrictName", "");
        values.put("Panchayat_Code", "");
        values.put("PanchayatName", "");
        values.put("WardName", "");
        values.put("Ward_Code", "");
        values.put("Fyear", "");
        values.put("FyearName", "");
        String[] param = {"1"};

        long update = db.update("Credential", values, "slno = ?", param);
        if (!(update > 0)) {
            update = db.insert("Credential", null, values);
        }
        db.close();
        return update;
    }


    public long insertSevikaSahaikaImage(Activity activity, SevikaSahaikaEntity sevikaSahaikaEntity) {
        long c=-1;
        try {
            DataBaseHelper placeData = new DataBaseHelper(activity);
            SQLiteDatabase db = placeData.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("UserId",sevikaSahaikaEntity.getUserId());
            contentValues.put("DistrictCode",sevikaSahaikaEntity.getDistrictCode());
            contentValues.put("ProjectCode",sevikaSahaikaEntity.getProjectCode());
            contentValues.put("SectorCode",sevikaSahaikaEntity.getSectorCode());
            contentValues.put("aanganvariCode",sevikaSahaikaEntity.getAanganvariCode());
            if (sevikaSahaikaEntity.getSevikaPhoto()!=null)contentValues.put("SevikaPhoto", Utiilties.bitmaptoByte(Utiilties.StringToBitMap(sevikaSahaikaEntity.getSevikaPhoto())));
            if (sevikaSahaikaEntity.getSahaikaPhoto()!=null)contentValues.put("SahaikaPhoto", Utiilties.bitmaptoByte(Utiilties.StringToBitMap(sevikaSahaikaEntity.getSahaikaPhoto())));
            c = db.insert("SevikaSahaika", null, contentValues);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return  c;
    }
    public ArrayList<SevikaSahaikaEntity> getAllSevikaSahaikaDetails(String userid){
        ArrayList<SevikaSahaikaEntity> sevikaSahaikaEntities=new ArrayList<SevikaSahaikaEntity>();
        try {
            DataBaseHelper dataBaseHelper=new DataBaseHelper(myContext);
            SQLiteDatabase sqLiteDatabase=dataBaseHelper.getReadableDatabase();
            Cursor cursor=sqLiteDatabase.rawQuery("select * from SevikaSahaika where UserId=?",new String[]{userid});
            while (cursor.moveToNext()){
                SevikaSahaikaEntity sevikaSahaikaEntity=new SevikaSahaikaEntity();
                sevikaSahaikaEntity.setId(Integer.toString(cursor.getInt(cursor.getColumnIndex("slno"))));
                sevikaSahaikaEntity.setUserId(cursor.getString(cursor.getColumnIndex("UserId")));
                sevikaSahaikaEntity.setDistrictCode(cursor.getString(cursor.getColumnIndex("DistrictCode")));
                sevikaSahaikaEntity.setProjectCode(cursor.getString(cursor.getColumnIndex("ProjectCode")));
                sevikaSahaikaEntity.setSectorCode(cursor.getString(cursor.getColumnIndex("SectorCode")));
                sevikaSahaikaEntity.setAanganvariCode(cursor.getString(cursor.getColumnIndex("aanganvariCode")));
                sevikaSahaikaEntity.setSevikaPhoto(cursor.isNull(cursor.getColumnIndex("SevikaPhoto")) == false ? Base64
                        .encodeToString(cursor.getBlob(cursor.getColumnIndex("SevikaPhoto")), Base64.NO_WRAP) : "");
                sevikaSahaikaEntity.setSahaikaPhoto(cursor.isNull(cursor.getColumnIndex("SahaikaPhoto")) == false ? Base64
                        .encodeToString(cursor.getBlob(cursor.getColumnIndex("SahaikaPhoto")), Base64.NO_WRAP) : "");
                sevikaSahaikaEntities.add(sevikaSahaikaEntity);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return sevikaSahaikaEntities;
    }
    public  int countSevikaSahaikaDetail(Activity mContext, String userid){
        Cursor cursor;
        try {
            DataBaseHelper dataBaseHelper=new DataBaseHelper(mContext);
            SQLiteDatabase sqLiteDatabase=dataBaseHelper.getReadableDatabase();
            cursor=sqLiteDatabase.rawQuery("select slno from SevikaSahaika where UserId=?",new String[]{userid});
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return cursor.getCount();
    }

    public long checkIfExistAwc(String panchayatcode) {
        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor=db.rawQuery("select sl_no from UploadDataforGps where AWC_Code=?",new String[]{panchayatcode});
            c=cursor.getCount();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;
    }

    public boolean deleteBenFromMasterWardRec(String benid) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] sl = {benid};


        long c = db.delete("BeneficiaryForWard", "BenfiId=?", sl);
        if (c > 0) return true;
        else return false;
    }
    public boolean deleteBenWardRec(String sid) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] sl = {sid};


        long c = db.delete("Ben_Ward_Data", "id=?", sl);
        if (c > 0) return true;
        else return false;
    }
    public boolean deleteBenWardRecByBenID(String benid) {

        SQLiteDatabase db = this.getWritableDatabase();
        String[] sl = {benid};


        long c = db.delete("Ben_Ward_Data", "BenID=?", sl);
        if (c > 0) return true;
        else return false;
    }
    public String getBenID(String id) {
        SQLiteDatabase db = this.getWritableDatabase();


        String name = "";

        String[] whereArgs = new String[]{id};
        Cursor c = db.rawQuery(
                "select * from Ben_Ward_Data where id=?",
                whereArgs);

        if (c.getCount() > 0) {
            c.moveToNext();
            name = c.getString(c.getColumnIndex("BenID"));

        }
        c.close();
        return name;

    }

    public  int countBenWardData(Activity mContext, String userid){
        Cursor cursor;
        try {
            DataBaseHelper dataBaseHelper=new DataBaseHelper(mContext);
            SQLiteDatabase sqLiteDatabase=dataBaseHelper.getReadableDatabase();
            cursor=sqLiteDatabase.rawQuery("select * from Ben_Ward_Data where UserID=?",new String[]{userid});
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
        return cursor.getCount();
    }

    public ArrayList<PondLakeDepartmentEntity> getPondLakeDeptList() {
        ArrayList<PondLakeDepartmentEntity> deptList = new ArrayList<PondLakeDepartmentEntity>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            //String[] params = new String[] { blockCode };

            Cursor cur = db
                    .rawQuery(
                            "SELECT * from PondLakeDept",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                PondLakeDepartmentEntity dept = new PondLakeDepartmentEntity();
                dept.set_DepatmentName(cur.getString(cur.getColumnIndex("_DepatmentName")));
                dept.set_DepatmentHNd(cur.getString(cur.getColumnIndex("_DepatmentHNd")));
                dept.setId(cur.getInt(cur.getColumnIndex("id")));

                deptList.add(dept);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return deptList;
    }

    public ArrayList<StructureType> getAllSanrachnaTypeList() {
        ArrayList<StructureType> deptList = new ArrayList<StructureType>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            //String[] params = new String[] { subExecID };

            Cursor cur = db
                    .rawQuery(
                            "SELECT * from StructureType", null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                StructureType dept = new StructureType();
                dept.setStrID(cur.getString(cur.getColumnIndex("StrID")));
                dept.setStrName(cur.getString(cur.getColumnIndex("StrName")));
                dept.setStrCode(cur.getString(cur.getColumnIndex("StrCode")));

                deptList.add(dept);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return deptList;
    }

    public ArrayList<SanrachnaTypeEntity> getSanrachnaTypeList(String subExecID) {
        ArrayList<SanrachnaTypeEntity> deptList = new ArrayList<SanrachnaTypeEntity>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { subExecID };

            Cursor cur = db
                    .rawQuery(
                            "SELECT * from PACS WHERE BlockCode= ?", params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                SanrachnaTypeEntity dept = new SanrachnaTypeEntity();
                dept.setSancrachnaId(cur.getString(cur.getColumnIndex("PacsID")));
                dept.setSancrachnaName(cur.getString(cur.getColumnIndex("PacsName")));
                dept.setSub_Execution_DeptID(cur.getString(cur.getColumnIndex("BlockCode")));

                deptList.add(dept);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return deptList;
    }


    public ArrayList<ward> getWardList(String Pan_Code) {
        ArrayList<ward> deptList = new ArrayList<ward>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { Pan_Code };

            Cursor cur = db
                    .rawQuery(
                            "SELECT * from Ward WHERE PanchayatCode= ?", params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                ward dept = new ward();
                dept.setWardCode(cur.getString(cur.getColumnIndex("WardCode")));
                dept.setWardname(cur.getString(cur.getColumnIndex("WardName")));
                dept.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                dept.setAreaType(cur.getString(cur.getColumnIndex("AreaType")));

                deptList.add(dept);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return deptList;
    }
    public ArrayList<ward> getWardListPanchayatwise(String areaCode,String Pan_Code) {
        ArrayList<ward> deptList = new ArrayList<ward>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { areaCode,Pan_Code };

            Cursor cur = db
                    .rawQuery(
                            "SELECT * from Ward WHERE AreaType= ? AND PanchayatCode= ?", params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                ward dept = new ward();
                dept.setWardCode(cur.getString(cur.getColumnIndex("WardCode")));
                dept.setWardname(cur.getString(cur.getColumnIndex("WardName")));
                dept.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                dept.setAreaType(cur.getString(cur.getColumnIndex("AreaType")));

                deptList.add(dept);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return deptList;
    }

    public ArrayList<VillageListEntity> getVillageList(String Pan_Code) {
        ArrayList<VillageListEntity> deptList = new ArrayList<VillageListEntity>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { Pan_Code };

            Cursor cur = db
                    .rawQuery(
                            "SELECT * from VillageList WHERE PanchayatCode= ?", params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                VillageListEntity dept = new VillageListEntity();
                dept.setVillCode(cur.getString(cur.getColumnIndex("VillageCode")));
                dept.setVillName(cur.getString(cur.getColumnIndex("VillageName")));
                dept.setPanchayatCode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                dept.setBlockCode(cur.getString(cur.getColumnIndex("BLOCKCODE")));

                deptList.add(dept);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return deptList;
    }

    public ArrayList<PanchayatData> getPanchayt(String blockCode) {
        ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { blockCode };

            Cursor cur = db
                    .rawQuery(
                            "SELECT PanchayatCode,PanchayatName,DistrictCode,BlockCode,PACName from Panchayat WHERE BlockCode = ? ORDER BY PanchayatName",
                            params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                PanchayatData panchayat = new PanchayatData();
                panchayat.setPcode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                panchayat.setPname(cur.getString(cur.getColumnIndex("PanchayatName")));
                panchayat.setBcode(cur.getString(cur.getColumnIndex("BlockCode")));
                panchayat.setDcode(cur.getString(cur.getColumnIndex("DistrictCode")));
                panchayat.setAreaType(cur.getString(cur.getColumnIndex("PACName")));

                panchayatList.add(panchayat);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return panchayatList;
    }

    public ArrayList<PanchayatData> getPanchaytAreawise(String blockCode, String areaType) {
        ArrayList<PanchayatData> panchayatList = new ArrayList<PanchayatData>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] {blockCode,areaType};

            Cursor cur = db
                    .rawQuery(
                            "SELECT PanchayatCode,PanchayatName,DistrictCode,BlockCode,PACName from Panchayat WHERE BlockCode = ? AND PACName = ? ORDER BY PanchayatName",
                            params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                PanchayatData panchayat = new PanchayatData();
                panchayat.setPcode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                panchayat.setPname(cur.getString(cur.getColumnIndex("PanchayatName")));
                panchayat.setBcode(cur.getString(cur.getColumnIndex("BlockCode")));
                panchayat.setDcode(cur.getString(cur.getColumnIndex("DistrictCode")));
                panchayat.setAreaType(cur.getString(cur.getColumnIndex("PACName")));

                panchayatList.add(panchayat);
            }

            cur.close();
            db.close();

        }
        catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return panchayatList;
    }

    public ArrayList<Krinawayan> getKriwayan() {
        ArrayList<Krinawayan> panchayatList = new ArrayList<Krinawayan>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            //String[] params = new String[] { blockCode };

            Cursor cur = db.rawQuery("SELECT * from ExecutionDepartment", null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Krinawayan panchayat = new Krinawayan();
                panchayat.setKrinawayan_Code(cur.getString(cur.getColumnIndex("ExecutionDepartment_Id")));
                panchayat.setKrinawayan_name(cur.getString(cur.getColumnIndex("ExecutionDepartment_Name")));
                //panchayat.setKrinawayan_name(cur.getString(cur.getColumnIndex("Krinawayan_name")));


                panchayatList.add(panchayat);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return panchayatList;

    }

    public ArrayList<TreesDetail> getTreelist() {
        ArrayList<TreesDetail> list = new ArrayList<TreesDetail>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            //String[] params = new String[] { Krinawayan_Code };

            Cursor cur = db.rawQuery("SELECT * from TreesDetail ", null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                TreesDetail info = new TreesDetail();
                info.setTreeId(cur.getString(cur.getColumnIndex("TreeId")));
                info.setTreeEng(cur.getString(cur.getColumnIndex("TreeNameEng")));
                info.setTreeHnd(cur.getString(cur.getColumnIndex("TreeNameHnd")));
                list.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return list;
    }

    public ArrayList<Abyab> getAbyab(String Krinawayan_Code) {
        ArrayList<Abyab> panchayatList = new ArrayList<Abyab>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            String[] params = new String[] { Krinawayan_Code };

            Cursor cur = db.rawQuery("SELECT * from SubExecutionDept ", null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Abyab panchayat = new Abyab();
                panchayat.setAbyab_Code(cur.getString(cur.getColumnIndex("SubExecutionDept_Id")));
                panchayat.setAbyab_name(cur.getString(cur.getColumnIndex("SubExecutionDept_Name")));
                panchayat.setKrinawayan_Code(cur.getString(cur.getColumnIndex("ExecutionDepartment_Id")));


                panchayatList.add(panchayat);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return panchayatList;
    }
    public ArrayList<Sub_abyb> getSubAbyab(String Krinawayan_Code) {
        ArrayList<Sub_abyb> panchayatList = new ArrayList<Sub_abyb>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { Krinawayan_Code };

            Cursor cur = db.rawQuery("SELECT * from SubSubExectDept WHERE SubExectDeptId=?", params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Sub_abyb panchayat = new Sub_abyb();
                panchayat.setSub_abyb_Code(cur.getString(cur.getColumnIndex("SubSubExectDept_Id")));
                panchayat.setSub_abyb_name(cur.getString(cur.getColumnIndex("SubSubExectDept_Name")));
                panchayat.setAbyb_Code(cur.getString(cur.getColumnIndex("SubExectDeptId")));


                panchayatList.add(panchayat);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return panchayatList;
    }

    public long setAbyabCitizenToLocal(ArrayList<Abyab> list) {

        long c = -1;
        ArrayList<Abyab> info = list;

        if (info != null) {
            try {
                SQLiteDatabase db = this.getReadableDatabase();
                db.delete("Category",  null, null);

                ContentValues values = new ContentValues();

                for (int i = 0; i < info.size(); i++) {

                    values.put("CategoryID", info.get(i).getAbyab_Code());
                    values.put("CategoryName", info.get(i).getAbyab_name());

                    String[] whereArgs = new String[]{String.valueOf(info.get(i).getAbyab_Code())};

                    c = db.update("Category", values, "CategoryID=?", whereArgs);

                    if(c != 1){
                        c = db.insert("Category", null, values);
                    }

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public ArrayList<DepartmentEntity> getExecDepartmentList() {
        ArrayList<DepartmentEntity> deptList = new ArrayList<DepartmentEntity>();
        try {
            String[] params = new String[] { "Y" };
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db
                    .rawQuery(
                            "SELECT * from DepartmentList WHERE isActive=?",
                            params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                DepartmentEntity info = new DepartmentEntity();
                info.setDeptId(cur.getString(cur.getColumnIndex("DeptId")));
                info.setDeptName(cur.getString(cur.getColumnIndex("DeptName")));
                info.setDeptNameHn(cur.getString(cur.getColumnIndex("DeptNameHn")));
                info.setIsActive(cur.getString(cur.getColumnIndex("isActive")));

                info.setStructure(cur.getString(cur.getColumnIndex("structure")));
                info.setScheme(cur.getString(cur.getColumnIndex("scheme")));
                info.setWellChapakal(cur.getString(cur.getColumnIndex("wellChapakal")));
                info.setBuilding(cur.getString(cur.getColumnIndex("building")));
                info.setNursery(cur.getString(cur.getColumnIndex("nursery")));

                deptList.add(info);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception
        }

        return deptList;
    }

    public ArrayList<Abyab> getAbyabList() {
        ArrayList<Abyab> districtList = new ArrayList<Abyab>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db
                    .rawQuery(
                            "SELECT abyabId,abyabName from AbyabPublic ORDER BY abyabId",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Abyab district = new Abyab();
                district.setAbyab_Code(cur.getString(cur
                        .getColumnIndex("abyabId")));
                district.setAbyab_name(cur.getString(cur
                        .getColumnIndex("abyabName")));

                districtList.add(district);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return districtList;
    }

    public ArrayList<District> getDistrict() {
        //CREATE TABLE "DistDetail" ( `Code` TEXT NOT NULL, `Name` TEXT, `slno`
        // INTEGER, PRIMARY KEY(`Code`) )
        ArrayList<District> districtList = new ArrayList<District>();
        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
                            "SELECT DistCode,DistName from Districts ORDER BY DistName",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                District district = new District();
                district.set_DistCode(cur.getString(cur
                        .getColumnIndex("DistCode")));
                district.set_DistName(cur.getString(cur
                        .getColumnIndex("DistName")));

                districtList.add(district);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            e.printStackTrace();
            // TODO: handle exception

        }
        return districtList;
    }

    public ArrayList<District> getDistrictUserBy() {
        //CREATE TABLE "DistDetail" ( `Code` TEXT NOT NULL, `Name` TEXT, `slno`
        // INTEGER, PRIMARY KEY(`Code`) )
        ArrayList<District> districtList = new ArrayList<District>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cur = db
                    .rawQuery(
                            "SELECT Code,Name from DistDetailUserBy ORDER BY Name",
                            null);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                District district = new District();
                district.set_DistCode(cur.getString(cur
                        .getColumnIndex("Code")));
                district.set_DistName(cur.getString(cur
                        .getColumnIndex("Name")));

                districtList.add(district);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception

        }
        return districtList;

    }

    public ArrayList<Block> getBlock(String distName) {

        ArrayList<Block> blockList = new ArrayList<Block>();
//CREATE TABLE `Block` ( `slno` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT UNIQUE,
// `District_Code` TEXT, `Code` TEXT, `Name` TEXT )
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String[] params = new String[] { distName };
            Cursor cur = db
                    .rawQuery(
                            "SELECT BlockCode,DistCode,BlockName from Blocks WHERE DistCode = ? ORDER BY BlockName ",
                            params);
            int x = cur.getCount();

            while (cur.moveToNext()) {

                Block block = new Block();
                block.setBlockCode(cur.getString(cur
                        .getColumnIndex("BlockCode")));
                block.setBlockName(cur.getString(cur
                        .getColumnIndex("BlockName")));
                block.setDistCode(cur.getString(cur
                        .getColumnIndex("DistCode")));

                blockList.add(block);
            }

            cur.close();
            db.close();

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();

        }
        return blockList;

    }
    public ArrayList<PanchayatData> getPanchayatLocal(String blkId) {
        //CREATE TABLE `Panchayat1` ( `id` INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, `DistCode` TEXT,
        // `BlockCode` TEXT, `PanchayatCode` TEXT, `PanchayatName` TEXT )
        ArrayList<PanchayatData> pdetail = new ArrayList<PanchayatData>();
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur = db.rawQuery("SELECT * FROM  Panchayat where BlockCode='" + blkId + "' order by PanchayatName", null);
            int x = cur.getCount();
            while (cur.moveToNext()) {
                PanchayatData panchayat = new PanchayatData();
                panchayat.setPcode(cur.getString(cur.getColumnIndex("PanchayatCode")));
                panchayat.setPname((cur.getString(cur.getColumnIndex("PanchayatName"))));
                panchayat.setAreaType((cur.getString(cur.getColumnIndex("PACName"))));
                pdetail.add(panchayat);
            }
            cur.close();
            db.close();
        }
        catch (Exception e) {
        }
        return pdetail;
    }

    public long updateNursery(NurseryEntryActivity phasuLevelActivity, NurseryEntity result) {

        long c = -1;
        try {
            DataBaseHelper placeData = new DataBaseHelper(phasuLevelActivity);
            SQLiteDatabase db = placeData.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("Sl_no",result.getSl_no());
            values.put("Entry_By",result.getEntry_By());
            values.put("Dist_Code",result.getDist_Code());
            values.put("Dist_Name",result.getDist_Name());
            values.put("Block_Code",result.getBlock_Code());
            values.put("Block_Name",result.getBlock_Name());
            values.put("Area_Code",result.getArea_Code());
            values.put("Area_Name",result.getArea_Name());
            values.put("Panchayat_Code", result.getPanchayat_Code());
            values.put("Panchayat_Name", result.getPanchayat_Name());
            values.put("Ward_Code", result.getWard_Code());
            values.put("Ward_Name", result.getWard_Name());
            values.put("Village_Code", result.getVillage_Code());
            values.put("Village_Name",result.getVillage_Name());
            //values.put("Plantation_Name",result.getPlantation_Name());
            values.put("Area",result.getArea());
            values.put("Tree",result.getTree());
            values.put("Mobile",result.getMobile());
            values.put("Image",result.getImage());
            values.put("Latitude",result.getLatitude());
            values.put("Longitude",result.getLongitude());
            String[] whereArgs = new String[]{(String.valueOf(result.getSl_no()))};
            c = db.update("NurseryEntry", values, "Sl_no=?", whereArgs);
            // c = db.insert("InsertDeworming", null, values);

            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }


    public long InsertUpdateNurseryBuildingData(NurseryEntity obj) {

        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("InspectionID", obj.getInspectionID());
            values.put("DistrictCode", obj.getDist_Code());
            values.put("DistName", obj.getDist_Name());
            values.put("BlockCode", obj.getBlock_Code());
            values.put("BlockName", obj.getBlock_Name());
            values.put("PanchayatCode", obj.getPanchayat_Code());
            values.put("PanchayatName", obj.getPanchayat_Name());
            values.put("VILLCODE", obj.getVillage_Code());
            values.put("VILLNAME", obj.getVillage_Name());
            values.put("WARDCODE", obj.getWard_Code());
            values.put("WARDNAME", obj.getWard_Name());

            values.put("Urban_AreaID", obj.getArea_Code());
            values.put("NurseryName", obj.getNursury_Name());
            values.put("TotalArea", obj.getArea());
            values.put("TotalTree", obj.getTree());
            values.put("BuildingType", obj.getBuildingType());
            values.put("BuildingName", obj.getBuildingName());
            values.put("Execution_DeptID", obj.getExecution_DeptID());
            values.put("Execution_DeptName", obj.getExecution_DeptName());
            values.put("OtherDept", obj.getOtherDept());
            values.put("ConsumerNo", obj.getConsumerNo());
            values.put("BCode", obj.getBCode());
            values.put("PreAmount", obj.getPreAmount());

            values.put("StrType", obj.getStrType());
            values.put("Latitude", obj.getLatitude());
            values.put("Longitude", obj.getLongitude());
            values.put("Photo", obj.getImg());
            values.put("AppVersion", obj.getAppVersion());
            values.put("EntryDate", obj.getEntryDate());
            values.put("EntryBy", obj.getEntry_By());
            values.put("IsUpdate", obj.getIsUpdate());
            values.put("Remark", obj.getRemark());
            values.put("Mobile", obj.getMobile());
            values.put("DeptId", obj.getDeptId());


            String[] whereArgs = new String[]{String.valueOf(obj.getId()), obj.getStrType()};

            if (obj.getId()== 0){
                c = db.insert("NursuryBuildingDetail", null, values);
            }else{
                c = db.update("NursuryBuildingDetail", values, "id=? AND StrType=?", whereArgs);
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }

    public long insertNursuryBuildingDataToLocal(ArrayList<NurseryEntity> list, String type) {

        long c = -1;

        if (list != null) {

            try {
                SQLiteDatabase db = this.getReadableDatabase();

                ContentValues values = new ContentValues();

                for (int i = 0; i < list.size(); i++) {

                    //values.put("id", info.get(i).getId());
                    values.put("InspectionID", list.get(i).getInspectionID());
                    values.put("DistrictCode", list.get(i).getDist_Code());
                    values.put("DistName", list.get(i).getDist_Name());
                    values.put("BlockCode", list.get(i).getBlock_Code());
                    values.put("BlockName", list.get(i).getBlock_Name());
                    values.put("PanchayatCode", list.get(i).getPanchayat_Code());
                    values.put("PanchayatName", list.get(i).getPanchayat_Name());
                    values.put("VILLCODE", list.get(i).getVillage_Code());
                    values.put("VILLNAME", list.get(i).getVillage_Name());
                    values.put("WARDCODE", list.get(i).getWard_Code());
                    values.put("WARDNAME", list.get(i).getWard_Name());
                    values.put("Urban_AreaID", list.get(i).getArea_Code());
                    values.put("NurseryName", list.get(i).getNursury_Name());
                    values.put("TotalArea", list.get(i).getArea());
                    values.put("TotalTree", list.get(i).getTree());
                    values.put("BuildingType", list.get(i).getBuildingType());
                    values.put("BuildingName", list.get(i).getBuildingName());
                    values.put("Execution_DeptID", list.get(i).getExecution_DeptID());
                    values.put("Execution_DeptName", list.get(i).getExecution_DeptName());
                    values.put("OtherDept", list.get(i).getOtherDept());
                    values.put("ConsumerNo", list.get(i).getConsumerNo());
                    values.put("BCode", list.get(i).getBCode());
                    values.put("PreAmount", list.get(i).getPreAmount());
                    values.put("StrType", type);
                    values.put("IsUpdate", "0");


                    String[] whereArgs = new String[]{String.valueOf(list.get(i).getInspectionID())};

                    c = db.update("NursuryBuildingDetail", values, "InspectionID=?", whereArgs);

                    if(c < 1){
                        c = db.insert("NursuryBuildingDetail", null, values);
                    }

                }
                db.close();

            } catch (Exception e) {
                e.printStackTrace();
                return c;
            }
        }
        return c;
    }

    public ArrayList<NurseryEntity> getNurseryInspectionDetail(String blockcode, String type, String isUpdate){
        //PondInspectionDetail info = null;

        ArrayList<NurseryEntity> infoList = new ArrayList<NurseryEntity>();

        try {

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cur;

            if(blockcode.equals("")){
                String[] params = new String[]{isUpdate, type};
                cur = db.rawQuery("Select * from NursuryBuildingDetail WHERE IsUpdate=? AND StrType=?",params);
            }else{
                String[] params = new String[]{isUpdate,blockcode, type};
                cur = db.rawQuery("Select * from NursuryBuildingDetail WHERE IsUpdate=? AND BlockCode=? AND StrType=?",params);
            }

            while (cur.moveToNext()) {

                infoList.add(getNurseryObject(cur));
            }

            cur.close();
            db.close();
            this.getReadableDatabase().close();
        } catch (Exception e) {
            // TODO: handle exception
            //info = null;
            e.printStackTrace();
        }
        return infoList;
    }

    public NurseryEntity getNurseryObject(Cursor cur){
        NurseryEntity info = new NurseryEntity();
        info.setInspectionID(cur.getString(cur.getColumnIndex("InspectionID")));
        info.setId(cur.getInt(cur.getColumnIndex("id")));
        info.setDist_Code(cur.getString(cur.getColumnIndex("DistrictCode")));
        info.setDist_Name(cur.getString(cur.getColumnIndex("DistName")));
        info.setBlock_Code(cur.getString(cur.getColumnIndex("BlockCode")));
        info.setBlock_Name(cur.getString(cur.getColumnIndex("BlockName")));
        info.setPanchayat_Code(cur.getString(cur.getColumnIndex("PanchayatCode")));
        info.setPanchayat_Name(cur.getString(cur.getColumnIndex("PanchayatName")));
        info.setVillage_Name(cur.getString(cur.getColumnIndex("VILLNAME")));
        info.setVillage_Code(cur.getString(cur.getColumnIndex("VILLCODE")));
        info.setWard_Code(cur.getString(cur.getColumnIndex("WARDCODE")));
        info.setWard_Name(cur.getString(cur.getColumnIndex("WARDNAME")));
        info.setLatitude(cur.getString(cur.getColumnIndex("Latitude")));
        info.setLongitude(cur.getString(cur.getColumnIndex("Longitude")));
        info.setArea_Code(cur.getString(cur.getColumnIndex("Urban_AreaID")));
        info.setNursury_Name(cur.getString(cur.getColumnIndex("NurseryName")));
        info.setArea(cur.getString(cur.getColumnIndex("TotalArea")));
        info.setTree(cur.getString(cur.getColumnIndex("TotalTree")));
        info.setBuildingType(cur.getString(cur.getColumnIndex("BuildingType")));
        info.setBuildingName(cur.getString(cur.getColumnIndex("BuildingName")));
        info.setExecution_DeptID(cur.getString(cur.getColumnIndex("Execution_DeptID")));
        info.setExecution_DeptName(cur.getString(cur.getColumnIndex("Execution_DeptName")));
        info.setOtherDept(cur.getString(cur.getColumnIndex("OtherDept")));
        info.setConsumerNo(cur.getString(cur.getColumnIndex("ConsumerNo")));
        info.setBCode(cur.getString(cur.getColumnIndex("BCode")));
        info.setPreAmount(cur.getString(cur.getColumnIndex("PreAmount")));
        info.setAppVersion(cur.getString(cur.getColumnIndex("AppVersion")));
        info.setStrType(cur.getString(cur.getColumnIndex("StrType")));
        info.setEntryDate(cur.getString(cur.getColumnIndex("EntryDate")));
        info.setEntry_By(cur.getString(cur.getColumnIndex("EntryBy")));
        info.setIsUpdate(cur.getString(cur.getColumnIndex("IsUpdate")));
        info.setRemark(cur.getString(cur.getColumnIndex("Remark")));
        info.setMobile(cur.getString(cur.getColumnIndex("Mobile")));
        info.setDeptId(cur.getString(cur.getColumnIndex("DeptId")));

        if (!cur.isNull(cur.getColumnIndex("Photo"))) {

            byte[] imgData = cur.getBlob(cur.getColumnIndex("Photo"));
            Bitmap bmp = BitmapFactory.decodeByteArray(imgData, 0,imgData.length);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
            String encodedImg2 = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);

            info.setPhoto(encodedImg2);
            info.setImg(imgData);
        }

        return info;
    }

    public long InsertUpdateRemarkData(StructureRemarkEntity obj) {

        long c = -1;
        try {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();

            values.put("newRemark", obj.getNewRemark());
            values.put("IsStrAvailable", obj.getIsStrAvailable());
            values.put("IsWorking", obj.getIsWorking());

            values.put("Latitude", obj.getLatitude());
            values.put("Longitude", obj.getLongitude());
            values.put("Photo", obj.getImg());
            values.put("AppVersion", obj.getAppVersion());
            values.put("EntryDate", obj.getEntryDate());
            values.put("EntryBy", obj.getEntryBy());
            values.put("IsUpdate", obj.getIsUpdate());


            String[] whereArgs = new String[]{String.valueOf(obj.getInspectionID())};

//            if (obj.getId()== 0){
//                c = db.insert("StructureRemarkUpdateDetail", null, values);
//            }else{
                c = db.update("StructureRemarkUpdateDetail", values, "InspectionID=?", whereArgs);
           // }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
            return c;
        }

        return c;

    }
}