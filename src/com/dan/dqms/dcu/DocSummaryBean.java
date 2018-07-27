package com.dan.dqms.dcu;

public class DocSummaryBean {

	private int totalPatients;

	private int totalCall;

	private int totalTreat;

	private int totalSkip;

	private int totalCancel;
	
	private  String dateTime;
	
	private  String deptName;
	
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getTotalPatients() {
		return totalPatients;
	}

	public void setTotalPatients(int totalPatients) {
		this.totalPatients = totalPatients;
	}

	public int getTotalCall() {
		return totalCall;
	}

	public void setTotalCall(int totalCall) {
		this.totalCall = totalCall;
	}

	public int getTotalTreat() {
		return totalTreat;
	}

	public void setTotalTreat(int totalTreat) {
		this.totalTreat = totalTreat;
	}

	public int getTotalSkip() {
		return totalSkip;
	}

	public void setTotalSkip(int totalSkip) {
		this.totalSkip = totalSkip;
	}

	public int getTotalCancel() {
		return totalCancel;
	}

	public void setTotalCancel(int totalCancel) {
		this.totalCancel = totalCancel;
	}

	public String getDateTime() {
		return dateTime;
	}

	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	
	
	

}
