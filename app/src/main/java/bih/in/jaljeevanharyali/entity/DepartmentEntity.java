package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class DepartmentEntity implements KvmSerializable, Serializable {

	public static Class<DepartmentEntity> DepartmentEntity_CLASS = DepartmentEntity.class;

	private String DeptId = "";
	private String DeptName = "";
	private String DeptNameHn = "";
	private String isActive = "";

	private String structure = "N";
	private String scheme = "N";
	private String wellChapakal = "N";
	private String building = "N";
	private String nursery = "N";

	public DepartmentEntity(SoapObject obj) {
		this.DeptId = obj.getProperty("Execution_DeptID").toString();
		this.DeptNameHn = obj.getProperty("Execution_DeptName").toString();
		this.DeptName = obj.getProperty("DeptName").toString();
		this.isActive = obj.getProperty("isactive").toString();

		this.scheme = obj.getProperty("SI").toString();
		this.structure = obj.getProperty("WBI").toString();
		this.wellChapakal = obj.getProperty("WCI").toString();
		this.building = obj.getProperty("BI").toString();
		this.nursery = obj.getProperty("NI").toString();
	}

	public DepartmentEntity() {

	}

	@Override
	public Object getProperty(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPropertyCount() {
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setProperty(int arg0, Object arg1) {
		// TODO Auto-generated method stub

	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getScheme() {
		return scheme;
	}

	public void setScheme(String scheme) {
		this.scheme = scheme;
	}

	public String getWellChapakal() {
		return wellChapakal;
	}

	public void setWellChapakal(String wellChapakal) {
		this.wellChapakal = wellChapakal;
	}

	public String getBuilding() {
		return building;
	}

	public void setBuilding(String building) {
		this.building = building;
	}

	public String getNursery() {
		return nursery;
	}

	public void setNursery(String nursery) {
		this.nursery = nursery;
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

	public String getDeptNameHn() {
		return DeptNameHn;
	}

	public void setDeptNameHn(String deptNameHn) {
		DeptNameHn = deptNameHn;
	}

	public String getIsActive() {
		return isActive;
	}

	public void setIsActive(String isActive) {
		this.isActive = isActive;
	}
}
