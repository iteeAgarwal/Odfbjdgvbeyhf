package com.dan.dqms.reports;

public class DoctorDaySummaryBean {

	private int tokenNo;

	private String tokenIssueDate;
	
    private int tokenType;
    
	private String tokenIssueTime;

	private String tokenCallTime;

	private String tokenOverTime;

	private String totalWaiting;

	private String totalConsult;

	private String status;

	private String docName;
	private String deptName;
	private String roomNum;
	private String loginStatusStr;
	private String loginTime;
	private String logoutTime;

	private int totalPatientQueue;
	private int currentTokenNumber;
	private int totalTokenCalled;
	private int totalTreated;
	private int totalSkipped;
	private int totalcancelled;

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public int getTokenType() {
		return tokenType;
	}

	public void setTokenType(int tokenType) {
		this.tokenType = tokenType;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getLoginStatusStr() {
		return loginStatusStr;
	}

	public void setLoginStatusStr(String loginStatusStr) {
		this.loginStatusStr = loginStatusStr;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

	public String getLogoutTime() {
		return logoutTime;
	}

	public void setLogoutTime(String logoutTime) {
		this.logoutTime = logoutTime;
	}

	public int getTotalPatientQueue() {
		return totalPatientQueue;
	}

	public void setTotalPatientQueue(int totalPatientQueue) {
		this.totalPatientQueue = totalPatientQueue;
	}

	public int getCurrentTokenNumber() {
		return currentTokenNumber;
	}

	public void setCurrentTokenNumber(int currentTokenNumber) {
		this.currentTokenNumber = currentTokenNumber;
	}

	public int getTotalTokenCalled() {
		return totalTokenCalled;
	}

	public void setTotalTokenCalled(int totalTokenCalled) {
		this.totalTokenCalled = totalTokenCalled;
	}

	public int getTotalTreated() {
		return totalTreated;
	}

	public void setTotalTreated(int totalTreated) {
		this.totalTreated = totalTreated;
	}

	public int getTotalSkipped() {
		return totalSkipped;
	}

	public void setTotalSkipped(int totalSkipped) {
		this.totalSkipped = totalSkipped;
	}

	public int getTotalcancelled() {
		return totalcancelled;
	}

	public void setTotalcancelled(int totalcancelled) {
		this.totalcancelled = totalcancelled;
	}

	public int getTokenNo() {
		return tokenNo;
	}

	public void setTokenNo(int tokenNo) {
		this.tokenNo = tokenNo;
	}

	public String getTokenIssueDate() {
		return tokenIssueDate;
	}

	public void setTokenIssueDate(String tokenIssueDate) {
		this.tokenIssueDate = tokenIssueDate;
	}

	public String getTokenIssueTime() {
		return tokenIssueTime;
	}

	public void setTokenIssueTime(String tokenIssueTime) {
		this.tokenIssueTime = tokenIssueTime;
	}

	public String getTokenCallTime() {
		return tokenCallTime;
	}

	public void setTokenCallTime(String tokenCallTime) {
		this.tokenCallTime = tokenCallTime;
	}

	public String getTokenOverTime() {
		return tokenOverTime;
	}

	public void setTokenOverTime(String tokenOverTime) {
		this.tokenOverTime = tokenOverTime;
	}

	

	public String getTotalWaiting() {
		return totalWaiting;
	}

	public void setTotalWaiting(String totalWaiting) {
		this.totalWaiting = totalWaiting;
	}

	public String getTotalConsult() {
		return totalConsult;
	}

	public void setTotalConsult(String totalConsult) {
		this.totalConsult = totalConsult;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
