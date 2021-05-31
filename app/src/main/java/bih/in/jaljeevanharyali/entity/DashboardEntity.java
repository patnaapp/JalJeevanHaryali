package bih.in.jaljeevanharyali.entity;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;

import java.io.Serializable;
import java.util.Hashtable;

public class DashboardEntity implements KvmSerializable, Serializable {

	private static final long serialVersionUID = 1L;

	public static Class<DashboardEntity> DASHBOARD_CLASS = DashboardEntity.class;
	private String Startedongoing = "";
	private String StartedongoingAmount = "";
	private String Completed = "";
	private String CompletedAmount = "";
	private String StructureType = "";
	private String welltotal = "";
	private String PondTotal = "";
	private String HandPumbTotal = "";
	private String Ahartotal = "";
	private String NaharTotal = "";
	private String PainTotal = "";

	private String TotalNursery = "";
	private String TotalNurseryInspected = "";
	private String TotalBuilding = "";
	private String TotalBuildingInspected = "";




	public DashboardEntity(SoapObject obj)
	{
		this.Startedongoing = obj.getProperty("Startedongoing").toString();
		this.StartedongoingAmount = obj.getProperty("StartedongoingAmount").toString();
		this.Completed = obj.getProperty("Completed").toString();
		this.CompletedAmount = obj.getProperty("CompletedAmount").toString();
		//this.StructureType = obj.getProperty("StructureType").toString();
		this.welltotal = obj.getProperty("welltotal").toString();
		this.PondTotal = obj.getProperty("PondTotal").toString();
		this.HandPumbTotal = obj.getProperty("HandPumbTotal").toString();
		this.Ahartotal = obj.getProperty("Ahartotal").toString();
		this.NaharTotal = obj.getProperty("NaharTotal").toString();
		this.PainTotal = obj.getProperty("PainTotal").toString();

		this.TotalNursery = obj.getProperty("TotalNursery").toString();
		this.TotalNurseryInspected = obj.getProperty("TotalNurseryInspected").toString();
		this.TotalBuilding = obj.getProperty("TotalBuilding").toString();
		this.TotalBuildingInspected = obj.getProperty("TotalBuildingInspected").toString();

	}

	public DashboardEntity()
	{

	}

	public String getTotalNursery() {
		return TotalNursery;
	}

	public void setTotalNursery(String totalNursery) {
		TotalNursery = totalNursery;
	}

	public String getTotalNurseryInspected() {
		return TotalNurseryInspected;
	}

	public void setTotalNurseryInspected(String totalNurseryInspected) {
		TotalNurseryInspected = totalNurseryInspected;
	}

	public String getTotalBuilding() {
		return TotalBuilding;
	}

	public void setTotalBuilding(String totalBuilding) {
		TotalBuilding = totalBuilding;
	}

	public String getTotalBuildingInspected() {
		return TotalBuildingInspected;
	}

	public void setTotalBuildingInspected(String totalBuildingInspected) {
		TotalBuildingInspected = totalBuildingInspected;
	}

	public String getAhartotal() {
		return Ahartotal;
	}

	public void setAhartotal(String ahartotal) {
		Ahartotal = ahartotal;
	}

	public String getNaharTotal() {
		return NaharTotal;
	}

	public void setNaharTotal(String naharTotal) {
		NaharTotal = naharTotal;
	}

	public String getPainTotal() {
		return PainTotal;
	}

	public void setPainTotal(String painTotal) {
		PainTotal = painTotal;
	}

	@Override
	public Object getProperty(int arg0)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getPropertyCount()
	{
		// TODO Auto-generated method stub
		return 3;
	}

	@Override
	public void getPropertyInfo(int arg0, Hashtable arg1, PropertyInfo arg2)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setProperty(int arg0, Object arg1)
	{
		// TODO Auto-generated method stub
	}


	public String getStartedongoing()
	{
		return Startedongoing;
	}

	public void setStartedongoing(String startedongoing)
	{
		Startedongoing = startedongoing;
	}

	public String getStartedongoingAmount()
	{
		return StartedongoingAmount;
	}

	public void setStartedongoingAmount(String startedongoingAmount) {
		StartedongoingAmount = startedongoingAmount;
	}

	public String getCompleted() {
		return Completed;
	}

	public void setCompleted(String completed) {
		Completed = completed;
	}

	public String getCompletedAmount() {
		return CompletedAmount;
	}

	public void setCompletedAmount(String completedAmount) {
		CompletedAmount = completedAmount;
	}

	public String getStructureType() {
		return StructureType;
	}

	public void setStructureType(String structureType) {
		StructureType = structureType;
	}

	public String getWelltotal() {
		return welltotal;
	}

	public void setWelltotal(String welltotal) {
		this.welltotal = welltotal;
	}

	public String getPondTotal() {
		return PondTotal;
	}

	public void setPondTotal(String pondTotal) {
		PondTotal = pondTotal;
	}

	public String getHandPumbTotal() {
		return HandPumbTotal;
	}

	public void setHandPumbTotal(String handPumbTotal) {
		HandPumbTotal = handPumbTotal;
	}
}
