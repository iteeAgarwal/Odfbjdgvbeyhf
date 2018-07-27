package org.dqms.tdu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.dqms.config.SystemSetting;
import org.dqms.util.Print;

public class TokenGeneratorStatic {

	
	
	private Connection connection()
	{
		Connection con = null;
		Statement statement = null;
	try
	{
		 Class.forName(SystemSetting.DB_DRIVER);
		  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
		  
		  
			 
		  }
		  catch(SQLException e)
		  {
			  Print.logException("Exception " ,e);
		  }
	     catch(Exception e)
	     {
	    	 
	    	 Print.logException("Exception " ,e);
		
	     }
	return con;
	
	}
	
	private void insertintotokendetails(int token_groupid,int depart_id,int  patient_id, int token_issue_time,int token_call_time,int token_over_time,  int status,int roomid , int userid)
	{
		int departid;
		if(depart_id!=0)
		{
			departid=depart_id;
		}
		else
		{
			departid=selectFromTable("depart_id", "user_details", "user_id", String.valueOf(userid));
		}
		Connection con = connection();
		String Query = "insert into token_details values(?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setInt(1, 0);
			stmt.setInt(2, tokenMax(token_groupid)+1);
			stmt.setInt(3, token_groupid);
			stmt.setInt(4, patient_id);
			stmt.setInt(5, departid);
			stmt.setInt(6, token_issue_time);
			stmt.setInt(7, roomid);
		    stmt.setInt(8, token_call_time);
			stmt.setInt(9, token_over_time);
			stmt.setInt(10, status);
			stmt.setInt(11, userid);
			
			
			stmt.executeUpdate();
			
			if(tableEmptyCheck("token_doc_summary","user_id", String.valueOf(userid))==0)
					{
				insertDocSummary(userid,token_groupid,1);
					}
			else
			{
				updateDocSummary(selectFromTable("total_token", "token_doc_summary", "user_id",String.valueOf(userid))+1, userid, token_groupid, 1);
			}
			
			
		}
		catch (Exception e) {
			
		}
		
	}

	
	
	
	
	
	
	
	private int tableEmptyCheck(String tablename,String columnname, String columnvalue)
	{
		Connection con = connection();
		 String Query ="SELECT * FROM "+tablename +" where "+columnname+" = "+columnvalue;
		 ResultSet rs = null;
		 int count=0;
		 PreparedStatement ps;
		try {
			ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while(rs.next())
			{
				count++;
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Print.logException("Exception " ,e);
		}	
		
		return count;
	}
	
	
	private int selectFromTable(String getdata,String tablename,String columnname, String columnvalue)
	{
		Connection con = connection();
		 String Query ="SELECT "+getdata+" FROM "+tablename +" where "+columnname+" = "+columnvalue;
		 ResultSet rs = null;
		 int data = 0;
		 
		 PreparedStatement ps;
		try {
			ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while(rs.next())
			{
				data =rs.getInt(getdata);
			}
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Print.logException("Exception " ,e);
		}	
		return data;
	}
	
	private void insertDocSummary(int userid,int token_group_id,int total_token)
	{
		Connection con = connection();
		String Query ="insert into token_doc_summary values(?,?,?,?)"; 
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			ps.setInt(1,userid);
			ps.setInt(2,token_group_id);
			ps.setInt(3, total_token);
			ps.setInt(4, 1);
			
			ps.executeUpdate();
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Print.logException("Exception " ,e);
		}
	}
	
	private void updateDocSummary(int total_token,int user_id,int token_group_id,int current_token)
	{
		Connection con = connection();
		
		String Query ="update token_doc_summary set total_token="+total_token+", token_group_id="+token_group_id+", current_token="+current_token+" where user_id="+user_id;
		
		
			try {
				PreparedStatement ps = con.prepareStatement(Query);
				
				ps.executeUpdate();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				Print.logException("Exception " ,e);
			}
	}
	
	private int getRoomGroup(String room)
	{
		Statement statement = null;
		Connection con = connection();
		String GroupId = null ;
		ResultSet rs = null;
		String Query="select * from token_group_details";
		try {
			statement = con.createStatement(); 
			rs= statement.executeQuery(Query);
			
			while(rs.next())
			{
				String RoomList = rs.getString("room_id");
				
				String roomdata[] = RoomList.split(",");
				
				if(groupFinder(room,roomdata))
				{
					GroupId = rs.getString("token_group_id");
					break;
				}
				
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Print.logException("Exception " ,e);
		}
		
		return Integer.parseInt(GroupId);
	}
	
	private boolean groupFinder(String room,String roomdata[])
	{
		boolean status = false;
		for(int i = 0 ;i<roomdata.length;i++)
		{ 
			if(roomdata[i].equals(room))
			{
				
				status = true;
				break;
			}
			else
			{
				status = false;
			}
		}
		
		return status;
		
	}
	
	private int  tokenMax(int group_id)
	{
		Connection con = connection();
		ResultSet rs = null;
		int token=0;
		String Query = "Select MAX(token_no) from token_details WHERE token_group_id="+group_id;
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while(rs.next())
			{
			token=rs.getInt(1);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Print.logException("Exception " ,e);
		}
		return token;
	}
	public static void main(String[] args) {
		
		TokenGeneratorStatic ts = new TokenGeneratorStatic();
		ts.insertintotokendetails(ts.getRoomGroup("5"), 1, 12, 12345, 12345, 12345, 0, 5, 8);
		/*
		 * 
		 * insertintotokendetails(int token_groupid,int depart_id,int  patient_id, int token_issue_time,int token_call_time,int token_over_time,  int status,int roomid , int userid)
		 * 
		 * all fields cumpulsary except depart_id pass depart_id = 0 if no value otherwise pass value
		 * 
		 * 
		 * 
		 * 
		 * */
	}
}
