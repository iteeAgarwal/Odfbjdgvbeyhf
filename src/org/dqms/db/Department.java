package org.dqms.db;

public class Department {

	public  int depart_id;
	public  String depart_name;
	
	public int getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public String getDepart_name() {
		return depart_name;
	}
	public void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}
	public static String getDepartName(int DepartID){
		 DBAdapter db = new DBAdapter();
		String DepartmentName = db.DepartName_DepartID(DepartID);
		return DepartmentName;
	}
	
	
}
