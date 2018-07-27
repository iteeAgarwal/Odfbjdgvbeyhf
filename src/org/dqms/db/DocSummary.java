package org.dqms.db;

public class DocSummary {
	
	public int Total_token_doctorID;
	public int Total_treated_token;
	public int Total_cancel_token;

 	public int getTotal_token_doctorID() {
		return Total_token_doctorID;
	}
	public void setTotal_token_doctorID(int total_token_doctorID) {
		Total_token_doctorID = total_token_doctorID;
	}
	public int getTotal_treated_token() {
		return Total_treated_token;
	}
	public void setTotal_treated_token(int total_treated_token) {
		Total_treated_token = total_treated_token;
	}
	public int getTotal_cancel_token() {
		return Total_cancel_token;
	}
	public void setTotal_cancel_token(int total_cancel_token) {
		Total_cancel_token = total_cancel_token;
	}

}
