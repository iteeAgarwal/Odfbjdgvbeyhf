package org.dqms.db;

public class TokenDocSummary {

    public int User_id;
	public int Token_group_id;
	public int Total_token;
	public int Current_token;
	public int Total_token_walk;
	public int Current_token_walk;
	
	public int getTotal_token_walk() {
		return Total_token_walk;
	}
	public void setTotal_token_walk(int total_token_walk) {
		Total_token_walk = total_token_walk;
	}
	public int getCurrent_token_walk() {
		return Current_token_walk;
	}
	public void setCurrent_token_walk(int current_token_walk) {
		Current_token_walk = current_token_walk;
	}
	
	
	public int getUser_id() {
		return User_id;
	}
	public void setUser_id(int user_id) {
		User_id = user_id;
	}
	public int getToken_group_id() {
		return Token_group_id;
	}
	
	public void setToken_group_id(int token_group_id) {
		Token_group_id = token_group_id;
	}
	
	public int getTotal_token() {
		return Total_token;
	}
	
	public void setTotal_token(int total_token) {
		Total_token = total_token;
	}
	public int getCurrent_token() {
		return Current_token;
	}
	public void setCurrent_token(int current_token) {
		Current_token = current_token;
	}
	
}
