package org.dqms.db;

public class Token {

	public  int token_no;
	public  int token_group_id;
	public  int patient_id;
	public  int depart_id;
	public  long token_issue_time;
	public  int last_alloted_token;
	public  boolean checked;
	public  int room_id;
	public  long token_call_time;
	public  long token_over_time;
	public  int status;
	public  int user_id;
	public  String patient_name;
	public int app_walk_id;
	public String app_walk_value;
	
	public int getApp_walk_id() {
		return app_walk_id;
	}
	public void setApp_walk_id(int app_walk_id) {
		this.app_walk_id = app_walk_id;
	}
	public String getApp_walk_value() {
		return app_walk_value;
	}
	public void setApp_walk_value(String app_walk_value) {
		this.app_walk_value = app_walk_value;
	}
	public  int getToken_no() {
		return token_no;
	}
	public  void setToken_no(int token_no) {
		this.token_no = token_no;
	}
	public  int getToken_group_id() {
		return token_group_id;
	}
	public  void setToken_group_id(int token_group_id) {
		this.token_group_id = token_group_id;
	}
	public  int getPatient_id() {
		return patient_id;
	}
	public  void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	public  int getDepart_id() {
		return depart_id;
	}
	public  void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public  long getToken_issue_time() {
		return token_issue_time;
	}
	public  void setToken_issue_time(long token_issue_time) {
		this.token_issue_time = token_issue_time;
	}
	public  int getLast_alloted_token() {
		return last_alloted_token;
	}
	public  void setLast_alloted_token(int last_alloted_token) {
		this.last_alloted_token = last_alloted_token;
	}
	public  boolean isChecked() {
		return checked;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public long getToken_call_time() {
		return token_call_time;
	}
	public void setToken_call_time(long token_call_time) {
		this.token_call_time = token_call_time;
	}
	public long getToken_over_time() {
		return token_over_time;
	}
	public void setToken_over_time(long token_over_time) {
		this.token_over_time = token_over_time;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public  void setChecked(boolean checked) {
		this.checked = checked;
	}

}
