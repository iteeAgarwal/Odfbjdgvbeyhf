package org.dqms.db;

public class TokenSummary {

	public  int token_group_id;
	public  int token_last_issued;
	public  int token_last_checked;
	public  long token_issue_time;
	
	public int total_skipped_token;
	public int total_treated_token;
	public int total_treated_token_doctorID;
	public int total_cancel_token;
	
	
	public  int getToken_group_id() {
		return token_group_id;
	}
	public  void setToken_group_id(int token_group_id) {
		this.token_group_id = token_group_id;
	}
	public  int getToken_last_issued() {
		return token_last_issued;
	}
	public  void setToken_last_issued(int token_last_issued) {
		this.token_last_issued = token_last_issued;
	}
	public  int getToken_last_checked() {
		return token_last_checked;
	}
	public  void setToken_last_checked(int token_last_checked) {
		this.token_last_checked = token_last_checked;
	}
	public  long getToken_issue_time() {
		return token_issue_time;
	}
	public  void setToken_issue_time(long token_issue_time) {
		this.token_issue_time = token_issue_time;
	}
	
	public int getTotal_skipped_token() {
		return total_skipped_token;
	}
	public void setTotal_skipped_token(int total_skipped_token) {
		this.total_skipped_token = total_skipped_token;
	}
	public int getTotal_treated_token() {
		return total_treated_token;
	}
	public void setTotal_treated_token(int total_treated_token) {
		this.total_treated_token = total_treated_token;
	}
	public int getTotal_treated_token_doctorID() {
		return total_treated_token_doctorID;
	}
	public void setTotal_treated_token_doctorID(int total_treated_token_doctorID) {
		this.total_treated_token_doctorID = total_treated_token_doctorID;
	}
	public int getTotal_cancel_token() {
		return total_cancel_token;
	}
	public void setTotal_cancel_token(int total_cancel_token) {
		this.total_cancel_token = total_cancel_token;
	}
	
}
