package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;

public class Panchayat1 implements Serializable {

    private String distCode;
    private String blockCode;
    private String panchaytCode;
    private String panchayatName;

    public Panchayat1(SoapObject sobj) {
        this.distCode=sobj.getProperty("DistCode").toString();
        this.blockCode=sobj.getProperty("BlockCode").toString();
        this.panchaytCode=sobj.getProperty("PanchayatCode").toString();
        this.panchayatName=sobj.getProperty("PanchayatName").toString();

    }

    public Panchayat1() {

    }

    public String getDistCode() {
        return distCode;
    }

    public void setDistCode(String distCode) {
        this.distCode = distCode;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getPanchaytCode() {
        return panchaytCode;
    }

    public void setPanchaytCode(String panchaytCode) {
        this.panchaytCode = panchaytCode;
    }

    public String getPanchayatName() {
        return panchayatName;
    }

    public void setPanchayatName(String panchayatName) {
        this.panchayatName = panchayatName;
    }
}
