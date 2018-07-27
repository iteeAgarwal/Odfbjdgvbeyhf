package com.dan.dqms.MS;

public class DataHelper {

	private int total_pateints;
	private int pateints_attended;
	private int patients_unattended;
	private long Avg_Waiting_time;
	private int total_doctor;
	private int total_doctor_login;
	private int token_generator;
	private int token_generator_login;
	
	
	

	

	public DataHelper(int total_pateints, int pateints_attended,
			int patients_unattended, long avg_Waiting_time, int total_doctor,
			int total_doctor_login, int token_generator,
			int token_generator_login) {
		super();
		this.total_pateints = total_pateints;
		this.pateints_attended = pateints_attended;
		this.patients_unattended = patients_unattended;
		Avg_Waiting_time = avg_Waiting_time;
		this.total_doctor = total_doctor;
		this.total_doctor_login = total_doctor_login;
		this.token_generator = token_generator;
		this.token_generator_login = token_generator_login;
	}

	public long getAvg_Waiting_time() {
		return Avg_Waiting_time;
	}

	public void setAvg_Waiting_time(long avg_Waiting_time) {
		Avg_Waiting_time = avg_Waiting_time;
	}

	public int getTotal_pateints() {
		return total_pateints;
	}
	
	public void setTotal_pateints(int total_pateints) {
		this.total_pateints = total_pateints;
	}
	public int getPateints_attended() {
		return pateints_attended;
	}
	public void setPateints_attended(int pateints_attended) {
		this.pateints_attended = pateints_attended;
	}
	public int getPatients_unattended() {
		return patients_unattended;
	}
	public void setPatients_unattended(int patients_unattended) {
		this.patients_unattended = patients_unattended;
	}
	public int getTotal_doctor() {
		return total_doctor;
	}
	public void setTotal_doctor(int total_doctor) {
		this.total_doctor = total_doctor;
	}
	public int getTotal_doctor_login() {
		return total_doctor_login;
	}
	public void setTotal_doctor_login(int total_doctor_login) {
		this.total_doctor_login = total_doctor_login;
	}
	public int getToken_generator() {
		return token_generator;
	}
	public void setToken_generator(int token_generator) {
		this.token_generator = token_generator;
	}
	public int getToken_generator_login() {
		return token_generator_login;
	}
	public void setToken_generator_login(int token_generator_login) {
		this.token_generator_login = token_generator_login;
	}
	
	
}
