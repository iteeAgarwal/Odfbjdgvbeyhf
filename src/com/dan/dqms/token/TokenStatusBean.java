package com.dan.dqms.token;

public class TokenStatusBean {

	private String user_id_to;
	private String token_group_id_to;
	
	private String departName;
	private int total_token_to;
	private int current_token_to;
	
	private int total_token_appoint;
	private int current_token_appoint;
	private String display_token;
	
	
	public String getDisplay_token() {
		return display_token;
	}
	public void setDisplay_token(String display_token) {
		this.display_token = display_token;
	}
	public String getUser_id_to() {
		return user_id_to;
	}
	public void setUser_id_to(String user_id_to) {
		this.user_id_to = user_id_to;
	}
	public String getToken_group_id_to() {
		return token_group_id_to;
	}
	public void setToken_group_id_to(String token_group_id_to) {
		this.token_group_id_to = token_group_id_to;
	}
	public int getTotal_token_to() {
		return total_token_to;
	}
	public void setTotal_token_to(int total_token_to) {
		this.total_token_to = total_token_to;
	}
	public int getCurrent_token_to() {
		return current_token_to;
	}
	public void setCurrent_token_to(int current_token_to) {
		this.current_token_to = current_token_to;
	}
	public String getDepartName() {
		return departName;
	}
	public void setDepartName(String departName) {
		this.departName = departName;
	}
	public int getTotal_token_appoint() {
		return total_token_appoint;
	}
	public void setTotal_token_appoint(int total_token_appoint) {
		this.total_token_appoint = total_token_appoint;
	}
	public int getCurrent_token_appoint() {
		return current_token_appoint;
	}
	public void setCurrent_token_appoint(int current_token_appoint) {
		this.current_token_appoint = current_token_appoint;
	}
	
	
	
	

}
