package bih.in.jaljeevanharyali.entity;

import java.io.Serializable;

/**
 * Created by nisci on 28-Jun-2017.
 */

public class MyGpsDataEntity implements Serializable {

    private String sl_no;
    private String yearId;
    private String distId;
    private String projectId;
    private String sectorId;
    private String awcid;
    private byte[] photo1;
    private byte[] photo2;
    private String latitude;
    private String longitude;
    private String isWaterRes;
    private String isBuilding;
    private String userId;
    private String updatedDate;

    public String getYearId() {
        return yearId;
    }

    public void setYearId(String yearId) {
        this.yearId = yearId;
    }

    public String getDistId() {
        return distId;
    }

    public void setDistId(String distId) {
        this.distId = distId;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getSectorId() {
        return sectorId;
    }

    public void setSectorId(String sectorId) {
        this.sectorId = sectorId;
    }

    public String getAwcid() {
        return awcid;
    }

    public void setAwcid(String awcid) {
        this.awcid = awcid;
    }

    public byte[] getPhoto1() {
        return photo1;
    }

    public void setPhoto1(byte[] photo1) {
        this.photo1 = photo1;
    }

    public byte[] getPhoto2() {
        return photo2;
    }

    public void setPhoto2(byte[] photo2) {
        this.photo2 = photo2;
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

    public String getIsWaterRes() {
        return isWaterRes;
    }

    public void setIsWaterRes(String isWaterRes) {
        this.isWaterRes = isWaterRes;
    }

    public String getSl_no() {
        return sl_no;
    }

    public void setSl_no(String sl_no) {
        this.sl_no = sl_no;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getIsBuilding() {
        return isBuilding;
    }

    public void setIsBuilding(String isBuilding) {
        this.isBuilding = isBuilding;
    }
}
