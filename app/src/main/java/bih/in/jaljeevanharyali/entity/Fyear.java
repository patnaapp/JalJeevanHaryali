package bih.in.jaljeevanharyali.entity;

public class Fyear {

	
	private String id;
	private String year ;
	private String isCurrent;
	
	
   public Fyear(String id, String year, String isCurrent)
   {
	   this.id=id;
	   this.year=year;
	   this.isCurrent=isCurrent;
   }
	
	
	
	public String getId() {
	return id;
}



public void setId(String id) {
	this.id = id;
}



	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getIsCurrent() {
		return isCurrent;
	}
	public void setIsCurrent(String isCurrent) {
		this.isCurrent = isCurrent;
	}

	
	
	
}
