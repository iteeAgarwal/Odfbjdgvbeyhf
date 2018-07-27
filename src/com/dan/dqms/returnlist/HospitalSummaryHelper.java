package com.dan.dqms.returnlist;

public class HospitalSummaryHelper {

	
	private int total_pateints;
	private int total_treated;
	private String avrworkingtime;
	private String avrwaitingtime;
	
	
	
	
	public HospitalSummaryHelper(int total_pateints, int total_treated,
			String avrworkingtime, String avrwaitingtime) {
		super();
		this.total_pateints = total_pateints;
		this.total_treated = total_treated;
		this.avrworkingtime = avrworkingtime;
		this.avrwaitingtime = avrwaitingtime;
	}
	public int getTotal_pateints() {
		return total_pateints;
	}
	public void setTotal_pateints(int total_pateints) {
		this.total_pateints = total_pateints;
	}
	public int getTotal_treated() {
		return total_treated;
	}
	public void setTotal_treated(int total_treated) {
		this.total_treated = total_treated;
	}
	public String getAvrworkingtime() {
		return avrworkingtime;
	}
	public void setAvrworkingtime(String avrworkingtime) {
		this.avrworkingtime = avrworkingtime;
	}
	public String getAvrwaitingtime() {
		return avrwaitingtime;
	}
	public void setAvrwaitingtime(String avrwaitingtime) {
		this.avrwaitingtime = avrwaitingtime;
	}
	
	
	
}
