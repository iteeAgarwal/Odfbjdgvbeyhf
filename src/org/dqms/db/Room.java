package org.dqms.db;

public class Room {

	public  int room_id;
	public  String room_no;
	public  int wdu_id;
	public  int depart_id;
	public  String location;
	public  String depart_name;
	
	public  int getRoom_id() {
		return room_id;
	}
	public  void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public  String getRoom_no() {
		return room_no;
	}
	public  void setRoom_no(String room_no) {
		this.room_no = room_no;
	}
	public  int getWdu_id() {
		return wdu_id;
	}
	public  void setWdu_id(int wdu_id) {
		this.wdu_id = wdu_id;
	}
	public  int getDepart_id() {
		return depart_id;
	}
	public  void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public  String getLocation() {
		return location;
	}
	public  void setLocation(String location) {
		this.location = location;
	}
	public  String getDepart_name() {
		return depart_name;
	}
	public  void setDepart_name(String depart_name) {
		this.depart_name = depart_name;
	}

}
