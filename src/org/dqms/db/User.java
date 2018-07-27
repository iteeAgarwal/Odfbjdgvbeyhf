package org.dqms.db;

public class User {

	public int user_id;
	public int role_id;
	public String name;
	public String phone_no;
	public String email_id;
	public String username;
	public String password;
	public String designation;
	public int depart_id;
	public int room_id;
	public boolean view;
	public int view1;
	
	public long last_updated_on;
	public long creation_date;
	public long login_time;
	public long logout_time;
	public boolean login;
	
	public String doc_id;
	
	
	
	
	public String getDoc_id() {
		return doc_id;
	}
	public void setDoc_id(String doc_id) {
		this.doc_id = doc_id;
	}
	public int getView1() {
		return view1;
	}
	public void setView1(int view1) {
		this.view1 = view1;
	}
	public long getLogout_time() {
		return logout_time;
	}
	public void setLogout_time(long logout_time) {
		this.logout_time = logout_time;
	}
	public long getLogin_time() {
		return login_time;
	}
	public void setLogin_time(long login_time) {
		this.login_time = login_time;
	}
	public boolean isLogin() {
		return login;
	}
	public void setLogin(boolean login) {
		this.login = login;
	}
	public int getUser_id() {
		return user_id;
	}
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	public int getRole_id() {
		return role_id;
	}
	public void setRole_id(int role_id) {
		this.role_id = role_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public String getEmail_id() {
		return email_id;
	}
	public void setEmail_id(String email_id) {
		this.email_id = email_id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getDesignation() {
		return designation;
	}
	public void setDesignation(String designation) {
		this.designation = designation;
	}
	public int getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public boolean isView() {
		return view;
	}
	public void setView(boolean view) {
		this.view = view;
	}
	public long getLast_updated_on() {
		return last_updated_on;
	}
	public void setLast_updated_on(long last_updated_on) {
		this.last_updated_on = last_updated_on;
	}
	public long getCreation_date() {
		return creation_date;
	}
	public void setCreation_date(long creation_date) {
		this.creation_date = creation_date;
	}

}
