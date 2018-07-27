package org.dqms.db;

public class Device {

	public  int device_id;
	public  String device_name;
	public  int type;
	public  String ip;
	public  String mac_address;
	public  String address;
	public  String location;
	public  long last_updated;
	public  boolean health_check;
	public  long last_hit_time;
	
	
	public int getDevice_id() {
		return device_id;
	}
	public void setDevice_id(int device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMac_address() {
		return mac_address;
	}
	public void setMac_address(String mac_address) {
		this.mac_address = mac_address;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getLast_updated() {
		return last_updated;
	}
	public void setLast_updated(long last_updated) {
		this.last_updated = last_updated;
	}
	public boolean isHealth_check() {
		return health_check;
	}
	public void setHealth_check(boolean health_check) {
		this.health_check = health_check;
	}
	public long getLast_hit_time() {
		return last_hit_time;
	}
	public void setLast_hit_time(long last_hit_time) {
		this.last_hit_time = last_hit_time;
	}
	
	
	
	
}
