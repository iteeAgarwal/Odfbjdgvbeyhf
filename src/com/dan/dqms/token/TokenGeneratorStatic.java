package com.dan.dqms.token;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dqms.config.SystemSetting;
import org.dqms.util.Print;

public class TokenGeneratorStatic {

	private Connection connection() {
		Connection con = null;
		Statement statement = null;
		try {
			Class.forName(SystemSetting.DB_DRIVER);
			con = DriverManager.getConnection(SystemSetting.DB_URL,
					SystemSetting.DB_USER, SystemSetting.DB_PASSWORD);

		} catch (SQLException e) {
			Print.logException("Exception in  connection method TokenGeneratorStatic"
					, e);
		} catch (Exception e) {
			Print.logException("Exception in  connection method TokenGeneratorStatic"
					, e);

		}
		return con;

	}

	public void insertintotokendetails(int token_groupid, int depart_id,
			int patient_id, long token_issue_time, int status, int roomid,
			int userid, int appWalk, int token) {

		int departid;

		int totalTokenAll = 0;

		if (depart_id != 0) {
			departid = depart_id;
		} else {
			departid = selectFromTable("depart_id", "user_details", "user_id",
					String.valueOf(userid));
		}

		Connection con = connection();
System.out.println("--------------- token insert details---------");
		/* ********************** insert data in token Details table ****************/
		String Query = "insert into token_details values(?,?,?,?,?,?,?,?,?,?,?,?,?)";

		String QueryHis = "insert into token_history values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setInt(1, 0);

		/*	if (appWalk == 1) {
				stmt.setInt(2, tokenMax(token_groupid) + 1);
			} else {
				stmt.setInt(2, select_tokenID);
			}
*/
			stmt.setInt(2, token);
			stmt.setInt(3, token_groupid);
			stmt.setInt(4, patient_id);
			stmt.setInt(5, departid);
			stmt.setLong(6, token_issue_time);
			stmt.setInt(7, roomid);
			stmt.setInt(8, 0);
			stmt.setInt(9, 0);
			stmt.setInt(10, status);
			stmt.setInt(11, userid);
			stmt.setInt(12, appWalk);
		/*	if (appWalk == 1) {
				stmt.setString(13, "");

				totalTokenAll = tokenMax(token_groupid);
			} else {
				stmt.setString(13, select_tokenValue);
				totalTokenAll = tokenMaxAppoint(token_groupid);

			}*/
			stmt.setString(13,"");

			int i = stmt.executeUpdate();

			/********************** insert data in token History table ****************/

			PreparedStatement stmtHis = con.prepareStatement(QueryHis);
			stmtHis.setInt(1, 0);
		/*	if (appWalk == 1) {
				stmtHis.setInt(2, tokenMax(token_groupid));
			} else {
				stmtHis.setInt(2, select_tokenID);
			}
			*/
			stmtHis.setInt(2, token);
			
			stmtHis.setInt(3, token_groupid);
			stmtHis.setInt(4, patient_id);
			stmtHis.setInt(5, departid);
			stmtHis.setLong(6, token_issue_time);
			stmtHis.setInt(7, roomid);
			stmtHis.setInt(8, 0);
			stmtHis.setInt(9, 0);
			stmtHis.setInt(10, status);
			stmtHis.setInt(11, userid);
			stmtHis.setLong(12, token_issue_time);
			stmtHis.setInt(13, appWalk);
		/*	if (appWalk == 1) {
				stmtHis.setString(14, "");
			} else {
				stmtHis.setString(14, select_tokenValue);
			}
*/
			stmtHis.setString(14, "");
			
			stmtHis.executeUpdate();
			
			/****************get total token MAX*******************/

			if (appWalk == 1) {

				totalTokenAll = tokenMax(token_groupid);
			} else {

				totalTokenAll = tokenMaxAppoint(token_groupid);


			}

			if (tableEmptyCheck("token_doc_summary", "user_id",
					String.valueOf(userid)) == 0) {

				insertDocSummary(userid, token_groupid, totalTokenAll, appWalk, roomid);

			} else {

				updateDocSummary(totalTokenAll, userid, token_groupid, appWalk);

			}
			

		} catch (Exception e) {
			Print.logException("Exception in  insertintotokendetails method TokenGeneratorStatic"
					, e);
		}
		try{
			con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

	}

	private int tableEmptyCheck(String tablename, String columnname,
			String columnvalue) {
		Connection con = connection();
		String Query = "SELECT * FROM " + tablename + " where " + columnname
				+ " = " + columnvalue;

		ResultSet rs = null;
		int count = 0;
		PreparedStatement ps;
		try {
			ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while (rs.next()) {
				count++;
			}

		} catch (SQLException e) {
			Print.logException("Exception in  tableEmptyCheck method TokenGeneratorStatic"
					, e);
		}

		try{
			con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		return count;
	}

	private int selectFromTable(String getdata, String tablename,
			String columnname, String columnvalue) {
		Connection con = connection();
		String Query = "SELECT " + getdata + " FROM " + tablename + " where "
				+ columnname + " = " + columnvalue;

		ResultSet rs = null;
		int data = 0;

		PreparedStatement ps;
		try {
			ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while (rs.next()) {
				data = rs.getInt(getdata);
			}

		} catch (SQLException e) {
			Print.logException("Exception in  selectFromTable method TokenGeneratorStatic"
					, e);
		}
		try{
			con.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}

		return data;
	}

	private void insertDocSummary(int userid, int token_group_id,
			int total_token, int appWalk,int roomid) {
		Connection con = connection();
		String Query = "insert into token_doc_summary values(?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			ps.setInt(1, userid);
			ps.setInt(2, token_group_id);
 
			ps.setInt(4, 0);
			ps.setInt(5, 0);

			ps.setInt(7, 0);
			ps.setInt(8, roomid);

			if (appWalk == 1) {
				ps.setInt(3, total_token);
				ps.setInt(6, 0);
			} else {
				ps.setInt(3, 0);
				ps.setInt(6, total_token);
			}

			ps.executeUpdate();
            con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  insertDocSummary method TokenGeneratorStatic"
					, e);
		}
	}

	private void updateDocSummary(int total_token, int user_id,
			int token_group_id, int appWalk) {
		Connection con = connection();
		String Query = null;

		if (appWalk == 1) {
			Query = "update token_doc_summary set total_token=" + total_token
					+ ", token_group_id=" + token_group_id + " where user_id="
					+ user_id;
		} else {
			Query = "update token_doc_summary set total_token_walk="
					+ total_token + ", token_group_id=" + token_group_id
					+ " where user_id=" + user_id;
		}

		try {
			PreparedStatement ps = con.prepareStatement(Query);

			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  updateDocSummary method TokenGeneratorStatic"
					, e);
		}
	}

	public int getRoomGroup(String room) {
		Statement statement = null;
		Connection con = connection();
		String GroupId = null;
		ResultSet rs = null;
		String Query = "select * from token_group_details";
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(Query);

			while (rs.next()) {
				String RoomList = rs.getString("room_id");

				String roomdata[] = RoomList.split(",");

				if (groupFinder(room, roomdata)) {
					GroupId = rs.getString("token_group_id");
					break;
				}

			}
            con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  getRoomGroup method TokenGeneratorStatic"
					, e);
		}

		if(GroupId!=null)
		return Integer.parseInt(GroupId);
		else
			return 0;
	}

	private boolean groupFinder(String room, String roomdata[]) {
		boolean status = false;
		for (int i = 0; i < roomdata.length; i++) {
			if (roomdata[i].equals(room)) {

				status = true;
				break;
			} else {
				status = false;
			}
		}

		return status;

	}

	private int tokenMax(int group_id) {
		Connection con = connection();
		ResultSet rs = null;
		int token = 0;
		String Query = "Select MAX(token_no) from token_details WHERE token_group_id='"
				+ group_id + "' AND app_walk_id=1";
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while (rs.next()) {
				token = rs.getInt(1);
			}
            con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  tokenMax method TokenGeneratorStatic"
					, e);
		}
		return token;
	}

	private int tokenMaxAppoint(int group_id) {
		Connection con = connection();
		ResultSet rs = null;
		int token = 0;
		String Query = "Select * from token_details WHERE token_group_id='"
				+ group_id + "' AND app_walk_id=2";
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while (rs.next()) {
				token++;
			}
             con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  tokenMax method TokenGeneratorStatic"
					, e);
		}
		return token;
	}

	public static void main(String[] args) {

		// TokenGeneratorStatic ts = new TokenGeneratorStatic();
		// ts.insertintotokendetails(ts.getRoomGroup("2"), 3, 12, 12345, 0, 2,
		// 4);
		/*
		 * 
		 * insertintotokendetails(int token_groupid,int depart_id,int
		 * patient_id, int token_issue_time, int status,int roomid , int userid)
		 * 
		 * all fields cumpulsary except depart_id pass depart_id = 0 if no value
		 * otherwise pass value
		 */
	}
}
