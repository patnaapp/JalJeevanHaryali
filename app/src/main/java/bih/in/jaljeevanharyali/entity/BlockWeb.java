package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.SoapObject;

public class BlockWeb {

	
	
	private String code;
	private String value;
	private String districtcode;
	private String DistCode = "";
	private String BlockCode = "";
	private String BlockName = "";
	private String PacsBankId = "";
	private String UserId;

	
	public static Class<BlockWeb> BlockWeb_CLASS= BlockWeb.class;
	
	public BlockWeb(SoapObject sobj)
	{
		
		this.code=sobj.getProperty(1).toString();
		this.value=sobj.getProperty(2).toString();
		this.districtcode=sobj.getProperty(0).toString();

		
	}

	public BlockWeb() {

	}

	public BlockWeb(SoapObject sobj, String s) {
		this.BlockCode=sobj.getProperty("BlockCode").toString();
		this.BlockName=sobj.getProperty("BlockName").toString();
		this.DistCode=sobj.getProperty("DistCode").toString();
		this.UserId=sobj.getProperty("UserId").toString();

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getDistrictcode() {
		return districtcode;
	}

	public void setDistrictcode(String districtcode) {
		this.districtcode = districtcode;
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

	public String getBlockName() {
		return BlockName;
	}

	public void setBlockName(String blockName) {
		BlockName = blockName;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}
}
