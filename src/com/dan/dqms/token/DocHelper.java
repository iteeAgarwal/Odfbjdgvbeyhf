package com.dan.dqms.token;

public class DocHelper {

	
	
	public DocHelper(int roomid, int userid) {
		super();
		this.roomid = roomid;
		this.userid = userid;
	}

	private int roomid,userid;

	public int getRoomid() {
		return roomid;
	}

	public void setRoomid(int roomid) {
		this.roomid = roomid;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
}
