package org.dqms.db;

public class SystemDetails {

	public  long today_date;
	public  boolean token_allocation;
	public  long last_online_update;
	public  boolean printer_setting;
	
	public long getToday_date() {
		return today_date;
	}
	public void setToday_date(long today_date) {
		this.today_date = today_date;
	}
	public boolean isToken_allocation() {
		return token_allocation;
	}
	public void setToken_allocation(boolean token_allocation) {
		this.token_allocation = token_allocation;
	}
	public long getLast_online_update() {
		return last_online_update;
	}
	public void setLast_online_update(long last_online_update) {
		this.last_online_update = last_online_update;
	}
	public boolean isPrinter_setting() {
		return printer_setting;
	}
	public void setPrinter_setting(boolean printer_setting) {
		this.printer_setting = printer_setting;
	}
	
	
}
