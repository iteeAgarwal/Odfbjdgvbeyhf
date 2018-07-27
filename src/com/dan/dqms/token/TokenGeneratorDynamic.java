package com.dan.dqms.token;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.dqms.config.SystemSetting;
import org.dqms.util.Print;

public class TokenGeneratorDynamic {
	private Connection connection() {
		Connection con = null;
		Statement statement = null;
		try {
			Class.forName(SystemSetting.DB_DRIVER);
			con = DriverManager.getConnection(SystemSetting.DB_URL,
					SystemSetting.DB_USER, SystemSetting.DB_PASSWORD);

		} catch (SQLException e) {
			Print.logInfo("Exception in connection method TokenGeneratorDynamic class"
					, e);
		} catch (Exception e) {
			Print.logInfo("Exception in  connection method TokenGeneratorDynamic class"
					, e);

		}
		return con;

	}

	private int tokenMax(int group_id) {
		Connection con = connection();
		ResultSet rs = null;
		int token = 0;
		String Query = "Select MAX(token_no) from token_details WHERE token_group_id="
				+ group_id;
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while (rs.next()) {
				token = rs.getInt(1);
			}
             con.close();
		} catch (SQLException e) {
			Print.logException("Exception in  tokenMax method TokenGeneratorDynamic class"
					, e);
		}
		return token;
	}

	public void roomfromGroup(int groupid, int roomid, int userid, long time,
			int patientid) {
		Connection con = connection();
		ResultSet rs = null;
		String roomId = null;
		int department = 0;
		int token = 0;
		String Query = "select room_id ,Department_id"
				+ " from token_group_details where token_group_id=" + groupid;

		try {
			PreparedStatement ps = con.prepareStatement(Query);
			rs = ps.executeQuery();

			while (rs.next()) {
				roomId = rs.getString("room_id");
				department = rs.getInt("Department_id");
			}


			if (roomid != 0 && userid != 0) {
				inserttokenDetailDynamicWithRoomID(groupid, patientid,
						department, time, 1234, 1234, 0, roomid, userid);
			} else {
				ArrayList<DocHelper> docdata = docFromRoomid(roomId);
				inserttokenDetailDynamic(groupid, patientid, department, time,
						1234, 1234, 0, docdata);
			}
              con.close();
		} catch (SQLException e) {
			Print.logInfo("Exception in  roomfromGroup method TokenGeneratorDynamic class"
					, e); 
		}

	}

	private void inserttokenDetailDynamic(int token_groupid, int patient_id,
			int depart_id, long token_issue_time, int token_call_time,
			int token_over_time, int status, ArrayList<DocHelper> docid) {
		Connection con = connection();
		if (tableEmptyCheck("token_group_summary", "token_group_id",
				String.valueOf(token_groupid)) == 0) {

			/********************** insert data in token details table ****************/

			String Query = "insert into token_details values(?,?,?,?,?,?,?,?,?,?,?)";

			String QueryHis = "insert into token_history values(?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				PreparedStatement stmt = con.prepareStatement(Query);
				stmt.setInt(1, 0);
				stmt.setInt(2, tokenMax(token_groupid) + 1);
				stmt.setInt(3, token_groupid);
				stmt.setInt(4, patient_id);
				stmt.setInt(5, depart_id);
				stmt.setLong(6, token_issue_time);
				stmt.setInt(7, docid.get(0).getRoomid());
				stmt.setInt(8, 0);
				stmt.setInt(9, 0);
				stmt.setInt(10, status);
				stmt.setInt(11, docid.get(0).getUserid());

				stmt.executeUpdate();

				/********************** insert data in token History table ****************/

				PreparedStatement stmtHis = con.prepareStatement(QueryHis);
				stmtHis.setInt(1, 0);
				stmtHis.setInt(2, tokenMax(token_groupid));
				stmtHis.setInt(3, token_groupid);
				stmtHis.setInt(4, patient_id);
				stmtHis.setInt(5, depart_id);
				stmtHis.setLong(6, token_issue_time);
				stmtHis.setInt(7, docid.get(0).getRoomid());
				stmtHis.setInt(8, 0);
				stmtHis.setInt(9, 0);
				stmtHis.setInt(10, status);
				stmtHis.setInt(11, docid.get(0).getUserid());
				stmtHis.setLong(12, token_issue_time);

				stmtHis.executeUpdate();

				insertDocSummary(docid.get(0).getUserid(), token_groupid, 1);
				insertGroupSummary(token_groupid, 1, docid.get(0).getUserid());

			} catch (Exception e) {
				Print.logException("Exception in  inserttokenDetailDynamic method TokenGeneratorDynamic class"
						, e);
			}

		} else {
			int userid = selectFromTable("current_user_id",
					"token_group_summary", "token_group_id",
					String.valueOf(token_groupid));

			int index = arrayListindex(docid, userid);

			String Query = "insert into token_details values(?,?,?,?,?,?,?,?,?,?,?)";

			String QueryHis = "insert into token_history values(?,?,?,?,?,?,?,?,?,?,?,?)";

			try {
				PreparedStatement stmt = con.prepareStatement(Query);
				stmt.setInt(1, 0);
				stmt.setInt(2, tokenMax(token_groupid) + 1);
				stmt.setInt(3, token_groupid);
				stmt.setInt(4, patient_id);
				stmt.setInt(5, depart_id);
				stmt.setLong(6, token_issue_time);
				stmt.setInt(7, docid.get(index + 1).getRoomid());
				stmt.setInt(8, 0);
				stmt.setInt(9, 0);
				stmt.setInt(10, status);
				stmt.setInt(11, docid.get(index + 1).getUserid());

				stmt.executeUpdate();

				/********************** insert data in token History table ****************/

				PreparedStatement stmtHis = con.prepareStatement(QueryHis);
				stmtHis.setInt(1, 0);
				stmtHis.setInt(2, tokenMax(token_groupid));
				stmtHis.setInt(3, token_groupid);
				stmtHis.setInt(4, patient_id);
				stmtHis.setInt(5, depart_id);
				stmtHis.setLong(6, token_issue_time);
				stmtHis.setInt(7, docid.get(0).getRoomid());
				stmtHis.setInt(8, 0);
				stmtHis.setInt(9, 0);
				stmtHis.setInt(10, status);
				stmtHis.setInt(11, docid.get(0).getUserid());
				stmtHis.setLong(12, token_issue_time);

				stmtHis.executeUpdate();

				updateGroupSummary(
						selectFromTable("total_token", "token_group_summary",
								"token_group_id", String.valueOf(token_groupid)) + 1,
						docid.get(index + 1).getUserid(), token_groupid);
				if (tableEmptyCheck("token_doc_summary", "user_id",
						String.valueOf(docid.get(index + 1).getUserid())) == 0) {
					insertDocSummary(docid.get(index + 1).getUserid(),
							token_groupid, tokenMax(token_groupid));
				} else {
					updateDocSummary(tokenMax(token_groupid),
							docid.get(index + 1).getUserid(), token_groupid);
				}
				
			} catch (Exception e) {
				Print.logException("Exception in  inserttokenDetailDynamic method TokenGeneratorDynamic class"
						, e);
			}
		}

		
		try
		{
			con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}

	private void inserttokenDetailDynamicWithRoomID(int token_groupid,
			int patient_id, int depart_id, long token_issue_time,
			int token_call_time, int token_over_time, int status, int roomid,
			int userid) {
		Connection con = connection();
		
		if (tableEmptyCheck("token_group_summary", "token_group_id",
				String.valueOf(token_groupid)) == 0) {
			
			String Query = "insert into token_details values(?,?,?,?,?,?,?,?,?,?,?)";
			String QueryHis = "insert into token_history values(?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				PreparedStatement stmt = con.prepareStatement(Query);
				stmt.setInt(1, 0);
				stmt.setInt(2, tokenMax(token_groupid) + 1);
				stmt.setInt(3, token_groupid);
				stmt.setInt(4, patient_id);
				stmt.setInt(5, depart_id);
				stmt.setLong(6, token_issue_time);
				stmt.setInt(7, roomid);
				stmt.setInt(8, 0);
				stmt.setInt(9, 0);
				stmt.setInt(10, status);
				stmt.setInt(11, userid);

				 stmt.executeUpdate();
				 

					/********************** insert data in token History table ****************/

					PreparedStatement stmtHis = con.prepareStatement(QueryHis);
					stmtHis.setInt(1, 0);
					stmtHis.setInt(2, tokenMax(token_groupid));
					stmtHis.setInt(3, token_groupid);
					stmtHis.setInt(4, patient_id);
					stmtHis.setInt(5, depart_id);
					stmtHis.setLong(6, token_issue_time);
					stmtHis.setInt(7, roomid);
					stmtHis.setInt(8, 0);
					stmtHis.setInt(9, 0);
					stmtHis.setInt(10, status);
					stmtHis.setInt(11, userid);
					stmtHis.setLong(12, token_issue_time);

					stmtHis.executeUpdate();
				 

				insertDocSummary(userid, token_groupid, 1);
				insertGroupSummary(token_groupid, 1, userid);
				
				
			} catch (Exception e) {
				Print.logException("Exception in  inserttokenDetailDynamicWithRoomID method TokenGeneratorDynamic class"
						, e);

			}

		} else {

			String Query = "insert into token_details values(?,?,?,?,?,?,?,?,?,?,?)";
			String QueryHis = "insert into token_history values(?,?,?,?,?,?,?,?,?,?,?,?)";
			try {
				PreparedStatement stmt = con.prepareStatement(Query);
				stmt.setInt(1, 0);
				stmt.setInt(2, tokenMax(token_groupid) + 1);
				stmt.setInt(3, token_groupid);
				stmt.setInt(4, patient_id);
				stmt.setInt(5, depart_id);
				stmt.setLong(6, token_issue_time);
				stmt.setInt(7, roomid);
				stmt.setInt(8, 0);
				stmt.setInt(9, 0);
				stmt.setInt(10, status);
				stmt.setInt(11, userid);

				stmt.executeUpdate();
				
				/********************** insert data in token History table ****************/
				
				PreparedStatement stmtHis = con.prepareStatement(QueryHis);
				stmtHis.setInt(1, 0);
				stmtHis.setInt(2, tokenMax(token_groupid));
				stmtHis.setInt(3, token_groupid);
				stmtHis.setInt(4, patient_id);
				stmtHis.setInt(5, depart_id);
				stmtHis.setLong(6, token_issue_time);
				stmtHis.setInt(7, roomid);
				stmtHis.setInt(8, 0);
				stmtHis.setInt(9, 0);
				stmtHis.setInt(10, status);
				stmtHis.setInt(11, userid);
				stmtHis.setLong(12, token_issue_time);

				stmtHis.executeUpdate();

				updateGroupSummarywithroom(
						selectFromTable("total_token", "token_group_summary",
								"token_group_id", String.valueOf(token_groupid)) + 1,
						userid, token_groupid);
				
				
				if (tableEmptyCheck("token_doc_summary", "user_id",
						String.valueOf(userid)) == 0) {
					insertDocSummary(userid, token_groupid,
							tokenMax(token_groupid));
					
					
				} else {
					updateDocSummary(tokenMax(token_groupid), userid,
							token_groupid);
				}
				
			} catch (Exception e) {
				Print.logException("Exception in  inserttokenDetailDynamicWithRoomID method TokenGeneratorDynamic class"
						,e);
			}
			
			
		}
		try{
		con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	private ArrayList<DocHelper> docFromRoomid(String roomId) {

		String room[] = roomId.split(",");
		int user_id;
		ArrayList<DocHelper> docId = new ArrayList<DocHelper>();
		Connection con = connection();
		ResultSet rs = null;

		for (int i = 0; i < room.length; i++) {
			String Query = "select user_id from user_details where room_id="
					+ room[i];
			PreparedStatement ps;
			try {
				ps = con.prepareStatement(Query);
				rs = ps.executeQuery();
				while (rs.next()) {
					user_id = rs.getInt("user_id");
					docId.add(new DocHelper(Integer.parseInt(room[i]), user_id));
				}
				

			} catch (SQLException e) {

				Print.logException("Exception in  docFromRoomid method TokenGeneratorDynamic class"
						, e);

			}
     
		}
		try{
		con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return docId;

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
			Print.logException("Exception in  tableEmptyCheck method TokenGeneratorDynamic class"
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
			Print.logException("Exception in  selectFromTable method TokenGeneratorDynamic class"
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
			int total_token) {
		Connection con = connection();
		String Query = "insert into token_doc_summary values(?,?,?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			ps.setInt(1, userid);
			ps.setInt(2, token_group_id);
			ps.setInt(3, total_token);
			ps.setInt(4, 0);
			ps.setInt(5, 0);
			ps.executeUpdate();
		} catch (SQLException e) {
			Print.logException("Exception in  insertDocSummary method TokenGeneratorDynamic class"
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

	private void updateGroupSummary(int total_token, int current_user_id,
			int token_group_id) {
		Connection con = connection();

		String Query = "update token_group_summary set total_token="
				+ total_token + ", current_user_id=" + current_user_id
				+ " where token_group_id=" + token_group_id;

		try {
			PreparedStatement ps = con.prepareStatement(Query);

			ps.executeUpdate();
		} catch (SQLException e) {
			Print.logException("Exception in  updateGroupSummary method TokenGeneratorDynamic class"
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

	private void updateGroupSummarywithroom(int total_token,
			int current_user_id, int token_group_id) {
		Connection con = connection();

		String Query = "update token_group_summary set total_token="
				+ total_token + "  where token_group_id=" + token_group_id;

		try {
			PreparedStatement ps = con.prepareStatement(Query);

			ps.executeUpdate();
		} catch (SQLException e) {
			Print.logException("Exception in  updateGroupSummarywithroom method TokenGeneratorDynamic class"
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

	private void updateDocSummary(int total_token, int user_id,
			int token_group_id) {
		Connection con = connection();

		String Query = "update token_doc_summary set total_token="
				+ total_token + ", token_group_id=" + token_group_id
				+ " where user_id=" + user_id;

		try {
			PreparedStatement ps = con.prepareStatement(Query);

			ps.executeUpdate();
		} catch (SQLException e) {
			Print.logException("Exception in  updateDocSummary method TokenGeneratorDynamic class"
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

	private void insertGroupSummary(int token_group_id, int total_token,
			int currentuserid) {
		Connection con = connection();
		String Query = "insert into token_group_summary values(?,?,?)";
		try {
			PreparedStatement ps = con.prepareStatement(Query);

			ps.setInt(1, token_group_id);
			ps.setInt(2, total_token);
			ps.setInt(3, currentuserid);
			ps.executeUpdate();
		} catch (SQLException e) {
			Print.logException("Exception in  insertGroupSummary method TokenGeneratorDynamic class"
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

	private int arrayListindex(ArrayList<DocHelper> docdata, int userID) {
		int index = 0;
		for (int i = 0; i < docdata.size(); i++) {
			if (userID == docdata.get(i).getUserid()) {
				index = i;
				if (docdata.size() - 1 != i) {

				} else {
					index = i - docdata.size();
				}

				break;
			}
		}

		return index;

	}

	public static void main(String ar[]) {

		new TokenGeneratorDynamic().roomfromGroup(3, 0, 0, 1234, 12);
		/*
		 * roomfromGroup(int groupid,int roomid,int userid ,int time,int
		 * patientid)
		 * 
		 * 
		 * groupid , tokenissuetime ,patientid (compulsary):
		 * 
		 * roomid and userid =0 if no value otherwise pass value
		 */

	}

}
