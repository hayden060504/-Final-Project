package Main;

public class Case { //一個案件
	private int ID; // ID
	private String describe, place; // 地點和物件狀況的描述
	private String type; // 報修類型

	public Case(int ID, String type, String place, String describe) {
		this.ID = ID;
		this.place = place;
		this.describe = describe;
		this.type = type;
	}

	public int getID() {
		return ID;
	}

	public String getPlace() {
		return place;
	}

	public String getDescribe() {
		return describe;
	}

	public String getType() {
		return type;
	}
	
	
}
