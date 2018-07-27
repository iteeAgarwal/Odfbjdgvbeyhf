package org.dqms.db;

public class Patient {

	public  int patient_id;
	public  String patient_aid;
	public  String patient_name;
	public  String id_card_no;
	public  String rfid_no;
	public  String phone_no;
	public  int room_id;
	public  int doctor_id;
	public  int depart_id;
	public  long last_check_time;
	public  boolean insurance;
	
	
	public int getPatient_id() {
		return patient_id;
	}
	public void setPatient_id(int patient_id) {
		this.patient_id = patient_id;
	}
	public String getPatient_aid() {
		return patient_aid;
	}
	public void setPatient_aid(String patient_aid) {
		this.patient_aid = patient_aid;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getId_card_no() {
		return id_card_no;
	}
	public void setId_card_no(String id_card_no) {
		this.id_card_no = id_card_no;
	}
	public String getRfid_no() {
		return rfid_no;
	}
	public void setRfid_no(String rfid_no) {
		this.rfid_no = rfid_no;
	}
	public String getPhone_no() {
		return phone_no;
	}
	public void setPhone_no(String phone_no) {
		this.phone_no = phone_no;
	}
	public int getRoom_id() {
		return room_id;
	}
	public void setRoom_id(int room_id) {
		this.room_id = room_id;
	}
	public int getDoctor_id() {
		return doctor_id;
	}
	public void setDoctor_id(int doctor_id) {
		this.doctor_id = doctor_id;
	}
	public int getDepart_id() {
		return depart_id;
	}
	public void setDepart_id(int depart_id) {
		this.depart_id = depart_id;
	}
	public long getLast_check_time() {
		return last_check_time;
	}
	public void setLast_check_time(long last_check_time) {
		this.last_check_time = last_check_time;
	}
	public boolean isInsurance() {
		return insurance;
	}
	public void setInsurance(boolean insurance) {
		this.insurance = insurance;
	}
	
	

}
