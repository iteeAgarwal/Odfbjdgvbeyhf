package com.dan.dqms.returnlist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.dqms.db.User;
import org.dqms.util.Print;

import com.dan.dqms.reports.LoginLogoutBean;
import com.dan.dqms.token.TokenGeneratorData;

public class LoginLogoutList {

	

	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

	public List<LoginLogoutBean> getLoginLogoutDetails() {
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		List<LoginLogoutBean> loginList = new ArrayList<LoginLogoutBean>();

		try {

			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("select * from login_history LIMIT 30");

			while (rs.next()) {

				LoginLogoutBean loginLogoutBean = new LoginLogoutBean();

				// user_id, login_time, logout_time, logout_time

				int userID = rs.getInt("user_id");

				UserList userListOb = new UserList();
				List<User> doctorList = userListOb.getUsers(String
						.valueOf(userID));

				if (doctorList.size() > 0) {
					loginLogoutBean.setUserName(doctorList.get(0).getName());

				} else {
					loginLogoutBean.setUserName("");
				}

				int loginTime = rs.getInt("login_time");
				int logoutTime = rs.getInt("logout_time");
				int todayDate = rs.getInt("logout_time");

				if (loginTime == 0) {

					loginLogoutBean.setLogin_time("");

				} else {
					Date loginTime1 = new Date(loginTime * 1000L);

					loginLogoutBean.setLogin_time(sdf.format(loginTime1));

				}

				if (logoutTime == 0) {

					loginLogoutBean.setLogout_time("");

				} else {
					Date LogoutTime1 = new Date(logoutTime * 1000L);

					loginLogoutBean.setLogout_time(sdf.format(LogoutTime1));
				}

				loginList.add(loginLogoutBean);
            con.close();
			}
		} catch (Exception e) {
			Print.logException("Exception in LoginLogoutList class " , e);
		}

		return loginList;

	}

	public List<LoginLogoutBean> getLoginLogoutDetails(int user,
			long fromDateTime, long toDateTime) {

		List<LoginLogoutBean> loginList = new ArrayList<LoginLogoutBean>();
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		try {

			Statement st = con.createStatement();

			ResultSet rs = st
					.executeQuery("select * from login_history where user_id ='"
							+ user
							+ "'  AND today_date > '"
							+ fromDateTime + "' and today_date <'" + toDateTime + "'  LIMIT 30 ");

			while (rs.next()) {

				LoginLogoutBean loginLogoutBean = new LoginLogoutBean();

				// user_id, login_time, logout_time, logout_time

				int userID = rs.getInt("user_id");

				UserList userListOb = new UserList();
				List<User> doctorList = userListOb.getUsers(String
						.valueOf(userID));

				if (doctorList.size() > 0) {
					loginLogoutBean.setUserName(doctorList.get(0).getName());

				} else {
					loginLogoutBean.setUserName("");
				}

				int loginTime = rs.getInt("login_time");
				int logoutTime = rs.getInt("logout_time");
				int todayDate = rs.getInt("logout_time");

				if (loginTime == 0) {

					loginLogoutBean.setLogin_time("");

				} else {
					Date loginTime1 = new Date(loginTime * 1000L);

					loginLogoutBean.setLogin_time(sdf.format(loginTime1));

				}

				if (logoutTime == 0) {

					loginLogoutBean.setLogout_time("");

				} else {
					Date LogoutTime1 = new Date(logoutTime * 1000L);

					loginLogoutBean.setLogout_time(sdf.format(LogoutTime1));
				}

				loginList.add(loginLogoutBean);

			}
			con.close();
		} catch (Exception e) {
			Print.logException("Exception in LoginLogoutList class " , e);
		}

		return loginList;

	}

	
	
	
	
	
	
	
	public List<LoginLogoutBean> getLoginLogoutDetails(
			long fromDateTime, long toDateTime) {

		List<LoginLogoutBean> loginList = new ArrayList<LoginLogoutBean>();
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		try {

			Statement st = con.createStatement();

			ResultSet rs = st
					.executeQuery("select * from login_history where today_date > '"
							+ fromDateTime + "' and today_date <'" + toDateTime + "'  LIMIT 30 ");

			while (rs.next()) {

				LoginLogoutBean loginLogoutBean = new LoginLogoutBean();

				// user_id, login_time, logout_time, logout_time

				int userID = rs.getInt("user_id");

				UserList userListOb = new UserList();
				List<User> doctorList = userListOb.getUsers(String
						.valueOf(userID));

				if (doctorList.size() > 0) {
					loginLogoutBean.setUserName(doctorList.get(0).getName());

				} else {
					loginLogoutBean.setUserName("");
				}

				int loginTime = rs.getInt("login_time");
				int logoutTime = rs.getInt("logout_time");
				int todayDate = rs.getInt("logout_time");

				if (loginTime == 0) {

					loginLogoutBean.setLogin_time("");

				} else {
					Date loginTime1 = new Date(loginTime * 1000L);

					loginLogoutBean.setLogin_time(sdf.format(loginTime1));

				}

				if (logoutTime == 0) {

					loginLogoutBean.setLogout_time("");

				} else {
					Date LogoutTime1 = new Date(logoutTime * 1000L);

					loginLogoutBean.setLogout_time(sdf.format(LogoutTime1));
				}

				loginList.add(loginLogoutBean);

			}
		} catch (Exception e) {
			Print.logException("Exception in LoginLogoutList class " , e);
		}

		return loginList;

	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public List<LoginLogoutBean> getLoginLogoutDetails(List<LoginLogoutBean> loginLogoutDetails, int nxtCtr) {
		List<LoginLogoutBean> loginList = new ArrayList<LoginLogoutBean>();
		
		if(nxtCtr==0){
			return loginLogoutDetails;
		}
		
		for(int i=nxtCtr;i<loginLogoutDetails.size();i++){
			loginList.add(loginLogoutDetails.get(i));
		}
		return loginList;
	}

}
