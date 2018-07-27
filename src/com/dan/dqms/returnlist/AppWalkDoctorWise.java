package com.dan.dqms.returnlist;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.dqms.config.SystemSetting;
import org.dqms.db.Token;
import org.dqms.util.Print;

public class AppWalkDoctorWise {

	public ArrayList<Token> AppTokenDetails_doctorWise(int doctorID,int app_walk_id) {
		ArrayList<Token> listToken = new ArrayList<Token>();

		Connection con = null;
		ResultSet rs = null;
		Statement statement = null;
		try {
			Class.forName(SystemSetting.DB_DRIVER);
			con = DriverManager.getConnection(SystemSetting.DB_URL,
					SystemSetting.DB_USER, SystemSetting.DB_PASSWORD);

			statement = con.createStatement();
			String QueryString = "select token_details.*, patient_details.patient_name from token_details INNER JOIN patient_details on patient_details.patient_id=token_details.patient_id where token_details.app_walk_id ='"+app_walk_id+"' AND token_details.user_id="+ doctorID + " ORDER BY token_details.token_no ASC";
			rs = statement.executeQuery(QueryString);
			while (rs.next()) {
				Token token = new Token();
				token.setDepart_id(rs.getInt("depart_id"));
				token.setPatient_id(rs.getInt("patient_id"));
				token.setPatient_name(rs.getString("patient_name"));
				token.setRoom_id(rs.getInt("room_id"));
				token.setStatus(rs.getInt("status"));
				token.setToken_call_time(rs.getLong("token_call_time"));
				token.setToken_group_id(rs.getInt("token_group_id"));
				token.setToken_issue_time(rs.getLong("token_issue_time"));
				token.setToken_no(rs.getInt("token_no"));
				token.setToken_over_time(rs.getLong("token_over_time"));
				token.setUser_id(rs.getInt("user_id"));
				token.setApp_walk_id(rs.getInt("app_walk_id"));
				token.setApp_walk_value(rs.getString("app_walk_value"));
				listToken.add(token);
			}
		} catch (SQLException s) {
			Print.logException("DBAdapter.TokenDetails_doctorWise", s);
		} catch (Exception e) {
			Print.logException("DBAdapter.TokenDetails_doctorWise", e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (statement != null)
					con.close();
			} catch (SQLException se) {
				Print.logException("DBAdapter.TokenDetails_doctorWise", se);
			}// do nothing
			try {
				if (con != null)
					con.close();
			} catch (SQLException se) {
				Print.logException("DBAdapter.TokenDetails_doctorWise", se);
			}// end finally try
		}// end try

		return listToken;
	}

}
