package GUI;

public class Business {
	private int businessNum;
	private String businessName;
	private int beltCounter;
	private int scarfCounter;
	private int sunGlassCounter;
	
	public Business(int businessNum, String businessName) {
		this.businessNum = businessNum;
		this.businessName = businessName;
		beltCounter = 0;
		scarfCounter = 0;
		sunGlassCounter = 0;
	}

	public int getBusinessNum() {
		return businessNum;
	}

	public void setBusinessNum(int businessNum) {
		this.businessNum = businessNum;
	}

	public String getBusinessName() {
		return businessName;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public int getBeltCounter() {
		return beltCounter;
	}

	public void setBeltCounter(int beltCounter) {
		this.beltCounter = beltCounter;
	}

	public int getScarfCounter() {
		return scarfCounter;
	}

	public void setScarfCounter(int scarfCounter) {
		this.scarfCounter = scarfCounter;
	}

	public int getSunGlassCounter() {
		return sunGlassCounter;
	}

	public void setSunGlassCounter(int sunGlassCounter) {
		this.sunGlassCounter = sunGlassCounter;
	}
}
