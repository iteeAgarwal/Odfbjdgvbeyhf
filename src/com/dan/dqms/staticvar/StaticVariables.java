package com.dan.dqms.staticvar;

public class StaticVariables {
	
	public static String realPath;

	private static int userRoleID;

	private static int userDeptID;

	private static int userID;

	private static int roomID;

	private static int tokenStaticDynamicID;

	private static String userNameStatic;

	private static int listType = 0;

	private static int current_token = 0;
	
	private static int total_token = 0;

	private static int TotalSkippedTokenNo = 0;

	private static int CurrentSkippedTokenNo = 0;

	private static boolean exceptionHigh = false;

	private static int skipHigh = 0;
	
	private static int calledFlag = 0;
	
	
	private static String log_path_name="";
	
	
	
	
	
	

	public static String getLog_path_name() {
		return log_path_name;
	}

	public static void setLog_path_name(String log_path_name) {
		StaticVariables.log_path_name = log_path_name;
	}

	public static int getCalledFlag() {
		return calledFlag;
	}

	public static void setCalledFlag(int calledFlag) {
		StaticVariables.calledFlag = calledFlag;
	}

	public static String getRealPath() {
		return realPath;
	}

	public static void setRealPath(String realPath) {
		StaticVariables.realPath = realPath;
	}

	public static int getSkipHigh() {
		return skipHigh;
	}

	public static void setSkipHigh(int skipHigh) {
		StaticVariables.skipHigh = skipHigh;
	}

	public static int getTotal_token() {
		return total_token;
	}

	public static void setTotal_token(int total_token) {
		StaticVariables.total_token = total_token;
	}

	public static int getTotalSkippedTokenNo() {
		return TotalSkippedTokenNo;
	}

	public static void setTotalSkippedTokenNo(int totalSkippedTokenNo) {
		TotalSkippedTokenNo = totalSkippedTokenNo;
	}

	public static int getCurrentSkippedTokenNo() {
		return CurrentSkippedTokenNo;
	}

	public static void setCurrentSkippedTokenNo(int currentSkippedTokenNo) {
		CurrentSkippedTokenNo = currentSkippedTokenNo;
	}

	

	public static boolean isExceptionHigh() {
		return exceptionHigh;
	}

	public static void setExceptionHigh(boolean exceptionHigh) {
		StaticVariables.exceptionHigh = exceptionHigh;
	}

	public static int getCurrent_token() {
		return current_token;
	}

	public static void setCurrent_token(int current_token) {
		StaticVariables.current_token = current_token;
	}

	public static int getUserID() {
		return userID;
	}

	public static void setUserID(int userID) {
		StaticVariables.userID = userID;
	}

	public static int getUserRoleID() {
		return userRoleID;
	}

	public static void setUserRoleID(int userRoleID) {
		StaticVariables.userRoleID = userRoleID;
	}

	public static int getUserDeptID() {
		return userDeptID;
	}

	public static void setUserDeptID(int userDeptID) {
		StaticVariables.userDeptID = userDeptID;
	}

	public static int getTokenStaticDynamicID() {
		return tokenStaticDynamicID;
	}

	public static void setTokenStaticDynamicID(int tokenStaticDynamicID) {
		StaticVariables.tokenStaticDynamicID = tokenStaticDynamicID;
	}

	public static int getRoomID() {
		return roomID;
	}

	public static void setRoomID(int roomID) {
		StaticVariables.roomID = roomID;
	}

	public static String getUserNameStatic() {
		return userNameStatic;
	}

	public static void setUserNameStatic(String userNameStatic) {
		StaticVariables.userNameStatic = userNameStatic;
	}

	public static int getListType() {
		return listType;
	}

	public static void setListType(int listType) {
		StaticVariables.listType = listType;
	}

}
