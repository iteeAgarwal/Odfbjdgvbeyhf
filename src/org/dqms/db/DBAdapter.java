package org.dqms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.dqms.config.SystemSetting;
import org.dqms.util.Print;

public class DBAdapter 
{

	public User CheckDoctorExists(String Username, String Password, int role_id){
		User user = null; 
		Connection con = null;
		ResultSet rs = null;
		Statement statement = null;
		try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
			  statement = con.createStatement(); 
			  String QueryString = "SELECT * from user_details where username='" + Username + "' and password='" + Password + "' and role_id=" + role_id;
			  rs = statement.executeQuery(QueryString);
			  while (rs.next()) {
					  user = new User();
					  user.setCreation_date(rs.getLong("creation_date"));
					  user.setDepart_id(rs.getInt("depart_id"));
					  user.setDesignation(rs.getString("designation"));
					  user.setEmail_id(rs.getString("email_id"));
					  user.setLast_updated_on(rs.getLong("last_updated_on"));
					  user.setName(rs.getString("name"));
					  user.setPassword(rs.getString("password"));
					  user.setPhone_no(rs.getString("phone_no"));
					  user.setRole_id(rs.getInt("role_id"));
					  user.setRoom_id(rs.getInt("room_id"));
					  user.setUser_id(rs.getInt("user_id"));
					  user.setUsername(rs.getString("username"));
					  user.setView(rs.getBoolean("view"));
					  user.setLogin(rs.getBoolean("login"));
					  user.setLogin_time(rs.getLong("login_time"));
					  user.setLogout_time(rs.getLong("logout_time"));
			  }
		}catch (SQLException s){
				  Print.logException("DBAdapter.CheckDoctorExists", s);
		}catch(Exception e){
			Print.logException("DBAdapter.CheckDoctorExists", e);
		}finally{
			try{
				if (rs != null) {
                 rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.CheckDoctorExists", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.CheckDoctorExists", se);
			}//end finally try
		}//end try
		return user;
	}
	
	public int CheckDoctorExistsUserID(String Username, String Password, int role_id){
		int UserID=0;
		Connection con = null;
		ResultSet rs = null;
		Statement statement = null;
		try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
			  statement = con.createStatement(); 
			  String QueryString = "SELECT user_id from user_details where username='" + Username + "' and password='" + Password + "' and role_id=" + role_id;
			  rs = statement.executeQuery(QueryString);
			  while (rs.next()) {
					  UserID = rs.getInt("user_id");
			  }
		}catch (SQLException s){
				  Print.logException("DBAdapter.CheckDoctorExistsUserID", s);
		}catch(Exception e){
			Print.logException("DBAdapter.CheckDoctorExistsUserID", e);
		}finally{
			try{
				if (rs != null) {
                 rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.CheckDoctorExistsUserID", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.CheckDoctorExistsUserID", se);
			}//end finally try
		}//end try
		return UserID;
	}
	
	public TokenSummary TokenSummary_RoomId(String RoomID){
		TokenSummary tokensummary = null; 
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select token_summary.token_last_issued, token_summary.token_last_checked, token_summary.token_group_id from token_summary INNER JOIN token_group_details on token_summary.token_group_id = token_group_details.token_group_id where token_group_details.room_id REGEXP '[,]" + RoomID + "[,]' | '^" + RoomID + "[,]' | '[,]" + RoomID + "$' OR token_group_details.room_id=" + RoomID;
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  tokensummary = new TokenSummary();
					  tokensummary.setToken_last_checked(rs.getInt("token_last_checked"));
					  tokensummary.setToken_last_issued(rs.getInt("token_last_issued"));
					  tokensummary.setToken_group_id(rs.getInt("token_group_id"));
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.TokenSummary_RoomId", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.TokenSummary_RoomId", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TokenSummary_RoomId", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TokenSummary_RoomId", se);
			}//end finally try
		  }//end try
		
		return tokensummary;
	}
	
	public TokenDocSummary TokenSummary_UserId(int UserID){
		TokenDocSummary tokenDocSummary = null; 
		Connection con = null;
		  ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);

				  statement = con.createStatement(); 
				  String QueryString = "select total_token,total_token_walk, current_token, current_token_walk,token_group_id from token_doc_summary where user_id=" + UserID;
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  tokenDocSummary = new TokenDocSummary();
					  tokenDocSummary.setCurrent_token(rs.getInt("current_token"));
					  tokenDocSummary.setToken_group_id(rs.getInt("token_group_id"));
					  tokenDocSummary.setTotal_token(rs.getInt("total_token"));
					  tokenDocSummary.setCurrent_token_walk(rs.getInt("current_token_walk"));
					  tokenDocSummary.setTotal_token_walk(rs.getInt("total_token_walk"));
					  tokenDocSummary.setUser_id(UserID);
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.TokenSummary_UserId", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.TokenSummary_UserId", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TokenSummary_UserId", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TokenSummary_UserId", se);
			}//end finally try
		  }//end try
		
		
		return tokenDocSummary;
	}
	
	public int LatestSkippedToken(int userID, int lastSkippedToken){
		int token_no=0;
		Connection con = null;
		ResultSet rs = null;
		Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString="";
				  if(lastSkippedToken>0){
					  QueryString = "select token_no from token_details where user_id=" + userID + " and token_no>" +lastSkippedToken + " and status=2 AND app_walk_id=1 ORDER BY token_no LIMIT 1";
				  }else{
					  QueryString = "select token_no from token_details where user_id=" + userID + " and status=2 AND app_walk_id=1 ORDER BY token_no LIMIT 1";
				  }
				  
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  token_no = rs.getInt("token_no");
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.LatestSkippedToken", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.LatestSkippedToken", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.LatestSkippedToken", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.LatestSkippedToken", se);
			}//end finally try
		  }//end try
		
		
		return token_no;
	}
	
	public int LatestCalledToken(int userID, int lastCalledToken){
		int token_no=0;
		Connection con = null;
		ResultSet rs = null;
		Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString="";
				  if(lastCalledToken>0){
					  QueryString = "select token_no from token_details where user_id=" + userID + " and token_no>" +lastCalledToken + " and app_walk_id=1 and status=0 ORDER BY token_no LIMIT 1";
				  }else{
					  QueryString = "select token_no from token_details where user_id=" + userID + " and app_walk_id=1 and status=0 ORDER BY token_no LIMIT 1";
				  }
				  
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  token_no = rs.getInt("token_no");
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.LatestCalledToken", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.LatestCalledToken", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.LatestCalledToken", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.LatestCalledToken", se);
			}//end finally try
		  }//end try
		
		
		return token_no;
	}
	
	public int TotalSkippedToken(int userID){
		int TotalToken=0;
		Connection con = null;
		ResultSet rs = null;
		Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select COUNT(token_no) from token_details where user_id=" + userID + " and status=2 AND app_walk_id=1;";
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  TotalToken = rs.getInt(1);
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.TotalSkippedToken", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.TotalSkippedToken", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TotalSkippedToken", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TotalSkippedToken", se);
			}//end finally try
		  }//end try
		
		return TotalToken;
	}
	
	public int UpdateCallSkippedToken(int tokenGroupId, int currentTokenNo, long token_call_time, long token_over_time, int status, int listType, int doctorID){
		int result=0;
		Connection con = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  String QueryString = "update token_details set token_call_time=" + token_call_time + ", status=" + status + " where token_no=" + currentTokenNo + " AND app_walk_id=1 and user_id=" + doctorID;
				  if((status==3 | status ==4) & token_over_time !=0){
					  QueryString="update token_details set token_over_time=" + token_over_time + ", status=" + status + " where token_no=" + currentTokenNo + " AND app_walk_id=1  and user_id=" + doctorID;
				  }
				  PreparedStatement updateEXP = con.prepareStatement(QueryString);
				  result = updateEXP.executeUpdate();
				   
				  if(status==1 & listType ==0){
					  QueryString = "update token_doc_summary set current_token=" + currentTokenNo + ", display_token=" + currentTokenNo + " where  user_id=" + doctorID;
					  updateEXP = con.prepareStatement(QueryString);
					  int result1 = updateEXP.executeUpdate();
				  }else if(status==1 & listType ==1){
					  QueryString = "update token_doc_summary set display_token=" + currentTokenNo + " where  user_id=" + doctorID;
					  updateEXP = con.prepareStatement(QueryString);
					  int result1 = updateEXP.executeUpdate();
				  }
				  
				  /*QueryString = "update user_details set current_token=" + currentTokenNo + " where user_id=" + doctorID;
				  updateEXP = con.prepareStatement(QueryString);
				  int result1 = updateEXP.executeUpdate();
				  */
		  }catch (SQLException s){
			  Print.logException("DBAdapter.UpdateCallSkippedToken", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.UpdateCallSkippedToken", e);
		  }finally{
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.UpdateCallSkippedToken", se);
			}//end finally try
		  }//end try
		
		return result;
	}
	
	public int UpdateCallSkippedTokenHistory(int tokenGroupId, int currentTokenNo, long token_call_time, long token_over_time, int status, int listType, int doctorID){
		int result=0;
		Connection con = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  String QueryString = "update token_history set token_call_time=" + token_call_time + ", status=" + status + " where token_no=" + currentTokenNo + " AND app_walk_id=1 and user_id=" + doctorID + " ORDER BY slno DESC LIMIT 1";
				  if((status==3 | status ==4) & token_over_time !=0){
					  QueryString="update token_history set token_over_time=" + token_over_time + ", status=" + status + " where token_no=" + currentTokenNo + " AND app_walk_id=1  and user_id=" + doctorID + " ORDER BY slno DESC LIMIT 1";
				  }
				  PreparedStatement updateEXP = con.prepareStatement(QueryString);
				  result = updateEXP.executeUpdate();

		  }catch (SQLException s){
			  Print.logException("DBAdapter.UpdateCallSkippedToken", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.UpdateCallSkippedToken", e);
		  }finally{
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.UpdateCallSkippedToken", se);
			}//end finally try
		  }//end try
		
		return result;
	}
	
	public User UserDetails(int userID){
		User user = new User();
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select * from user_details where user_id=" + userID ;
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  user.setCreation_date(rs.getLong("creation_date"));
					  user.setDepart_id(rs.getInt("depart_id"));
					  user.setDesignation(rs.getString("designation"));
					  user.setEmail_id(rs.getString("email_id"));
					  user.setLast_updated_on(rs.getLong("last_updated_on"));
					  user.setName(rs.getString("name"));
					  user.setPassword(rs.getString("password"));
					  user.setPhone_no(rs.getString("phone_no"));
					  user.setRole_id(rs.getInt("role_id"));
					  user.setRoom_id(rs.getInt("room_id"));
					  user.setUsername(rs.getString("username"));
					  user.setView(rs.getBoolean("view"));
					  user.setUser_id(rs.getInt("user_id"));
					  user.setLogin(rs.getBoolean("login"));
					  user.setLogin_time(rs.getLong("login_time"));
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.UserDetails", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.UserDetails", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.UserDetails", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.UserDetails", se);
			}//end finally try
		  }//end try
		
		return user;
	}
	
	public String DepartName_DepartID(int DepartID){
		String Depart_Name = "";
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select depart_name from department_details where depart_id=" + DepartID ;
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  Depart_Name=rs.getString("depart_name");
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.DepartName_DepartID", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.DepartName_DepartID", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DepartName_DepartID", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DepartName_DepartID", se);
			}//end finally try
		  }//end try
		
		return Depart_Name;
	}
	
	public DocSummary DoctorSummary(int doctorID){
		DocSummary docSummary= new DocSummary();
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  statement = con.createStatement(); 
				  String QueryString = "select COUNT(token_no) from token_details where user_id=" + doctorID + " and status=3";
				  rs = statement.executeQuery(QueryString);
				  rs.next();
				  docSummary.setTotal_treated_token(rs.getInt(1));
				  
				  QueryString = "select COUNT(token_no) from token_details where user_id=" + doctorID;
				  rs = statement.executeQuery(QueryString);
				  rs.next();
				  docSummary.setTotal_token_doctorID(rs.getInt(1));
				  
				  QueryString = "select COUNT(token_no) from token_details where user_id=" + doctorID + " and status=4";
				  rs = statement.executeQuery(QueryString);
				  rs.next();
				  docSummary.setTotal_cancel_token(rs.getInt(1));
				  
		  }catch (SQLException s){
			  Print.logException("DBAdapter.DoctorSummary", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.DoctorSummary", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DoctorSummary", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DoctorSummary", se);
			}//end finally try
		  }//end try
		
		return docSummary;
	}
	
	
	public ArrayList<Token> TokenDetails_tokenGroupWise(int tokenGroupID,int doctorID){
		ArrayList<Token> listToken= new ArrayList<Token>();
		
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select token_details.*, patient_details.patient_name from token_details INNER JOIN patient_details on patient_details.patient_id=token_details.patient_id where token_details.token_group_id=" + tokenGroupID ;
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
					  listToken.add(token);
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.TokenDetails_tokenGroupWise", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.TokenDetails_tokenGroupWise", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TokenDetails_tokenGroupWise", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TokenDetails_tokenGroupWise", se);
			}//end finally try
		  }//end try
		
		return listToken;
	}
	
	public ArrayList<Token> TokenDetails_doctorWise(int doctorID){
		ArrayList<Token> listToken= new ArrayList<Token>();
		
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select token_details.*, patient_details.patient_name from token_details INNER JOIN patient_details on patient_details.patient_id=token_details.patient_id where token_details.user_id=" + doctorID ;
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
					  listToken.add(token);
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.TokenDetails_doctorWise", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.TokenDetails_doctorWise", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TokenDetails_doctorWise", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.TokenDetails_doctorWise", se);
			}//end finally try
		  }//end try
		
		
		return listToken;
	}
	
	public boolean DoctorLoginUpdate(int userID, long loginTime){
		boolean result =false;
		Connection con = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  String QueryString = "update user_details set login=1, login_time=" + loginTime + " where user_id=" + userID;
				  PreparedStatement updateEXP = con.prepareStatement(QueryString);
				  int res = updateEXP.executeUpdate();
				  result = res>0?true:false;
				  
				  QueryString = "insert into login_history (user_id, login_time, today_date) values(" + userID + ", " + loginTime + ", " + loginTime + ")";
				  updateEXP = con.prepareStatement(QueryString);
				  res = updateEXP.executeUpdate();
				  
		  }catch (SQLException s){
			  Print.logException("DBAdapter.DoctorLoginUpdate", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.DoctorLoginUpdate", e);
		  }finally{
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DoctorLoginUpdate", se);
			}//end finally try
		  }//end try
		
		return result;
	}
	
	public boolean DoctorLoginUpdateHistory(int userID, long loginTime){
		boolean result =false;
		Connection con = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  String QueryString = "insert into login_history (user_id, login_time, today_date) values(" + userID + ", " + loginTime + ", " + loginTime + ")";
				  PreparedStatement updateEXP = con.prepareStatement(QueryString);
				  int res = updateEXP.executeUpdate();
				  
		  }catch (SQLException s){
			  Print.logException("DBAdapter.DoctorLoginUpdateHistory", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.DoctorLoginUpdateHistory", e);
		  }finally{
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DoctorLoginUpdateHistory", se);
			}//end finally try
		  }//end try
		
		return result;
	}
	public boolean DoctorLogoutUpdate(int userID, long logoutTime){
		boolean result =false;
		Connection con = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  String QueryString = "update user_details set login=0, logout_time=" + logoutTime + " where user_id=" + userID;
				  PreparedStatement updateEXP = con.prepareStatement(QueryString);
				  int res = updateEXP.executeUpdate();
				  result = res>0?true:false;
		  }catch (SQLException s){
			  Print.logException("DBAdapter.DoctorLogoutUpdate", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.DoctorLogoutUpdate", e);
		  }finally{
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DoctorLogoutUpdate", se);
			}//end finally try
		  }//end try
		
		return result;
	}
	
	public boolean DoctorLogoutUpdate(String username, String password, long logoutTime){
		boolean result =false;
		Connection con = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  String QueryString = "update user_details set login=0, logout_time=" + logoutTime + " where username='" + username + "' and password='" + password + "'";
				  PreparedStatement updateEXP = con.prepareStatement(QueryString);
				  int res = updateEXP.executeUpdate();
				  result = res>0?true:false;
				  
		  }catch (SQLException s){
			  Print.logException("DBAdapter.DoctorLogoutUpdate", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.DoctorLogoutUpdate", e);
		  }finally{
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DoctorLogoutUpdate", se);
			}//end finally try
		  }//end try
		
		return result;
	}
	
	public boolean DoctorLogoutUpdateHistory(int userID, long logoutTime){
		boolean result =false;
		Connection con = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  String QueryString = "update login_history set logout_time=" + logoutTime + " where user_id=" + userID + " ORDER BY slno DESC  LIMIT 1";
				  PreparedStatement updateEXP = con.prepareStatement(QueryString);
				  int res = updateEXP.executeUpdate();
				  
		  }catch (SQLException s){
			  Print.logException("DBAdapter.DoctorLogoutUpdateHistory", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.DoctorLogoutUpdateHistory", e);
		  }finally{
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DoctorLogoutUpdateHistory", se);
			}//end finally try
		  }//end try
		
		return result;
	}
	
	public ArrayList<Room> RoomList(){
		ArrayList<Room> listRoom= new ArrayList<Room>();
		
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select room_details.*, department_details.depart_name from room_details INNER JOIN department_details on department_details.depart_id=room_details.depart_id";
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  Room room = new Room();
					  room.setDepart_id(rs.getInt("depart_id"));
					  room.setDepart_name(rs.getString("depart_name"));
					  room.setLocation(rs.getString("location"));
					  room.setRoom_id(rs.getInt("room_id"));
					  room.setRoom_no(rs.getString("room_no"));
					  room.setWdu_id(rs.getInt("wdu_id"));
					  listRoom.add(room);
				  }
		  }catch (SQLException s){
			  Print.logException("DBAdapter.RoomList", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.RoomList", e);
		  }finally{
			try{
				if (rs != null) {
	             rs.close();
				}
				if(statement!=null)
					con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.RoomList", se);
			}// do nothing
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.RoomList", se);
			}//end finally try
		  }//end try
		
		return listRoom;
	}
	
	public ArrayList<WDU> WDUList(String Address){
		ArrayList<WDU> listWDU= new ArrayList<WDU>();
		
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select room_details.room_no, user_details.name, token_doc_summary.display_token, department_details.depart_name  from user_details INNER JOIN token_doc_summary on token_doc_summary.user_id = user_details.user_id INNER JOIN room_details on room_details.room_id = user_details.room_id INNER JOIN department_details on department_details.depart_id=user_details.depart_id	where user_details.login=1 and room_details.wdu_id=(select device_id from device_details where address='"+ Address +"' and type=2)";
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  WDU wdu = new WDU();
					  wdu.setCurrent_Token(rs.getString("display_token"));
					  wdu.setDepart_Name(rs.getString("depart_Name"));
					  wdu.setDoc_Name(rs.getString("name"));
					  wdu.setRoom_No(rs.getString("room_No"));
					  listWDU.add(wdu);
				  }
			  }catch (SQLException s){
				  Print.logException("DBAdapter.WDUList", s);
			  }catch(Exception e){
				Print.logException("DBAdapter.WDUList", e);
			  }finally{
				try{
					if (rs != null) {
		             rs.close();
					}
					if(statement!=null)
						con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.WDUList", se);
				}// do nothing
				try{
		        if(con!=null)
		           con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.WDUList", se);
				}//end finally try
			  }//end try	
		return listWDU;
	}
	
	public String MDURoomList(String Address){
		String rooms="";
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select group_mdu_details.room_no_list from group_mdu_details INNER JOIN device_details on device_details.device_id=group_mdu_details.mdu_id where device_details.address='"+ Address +"' and type=1";
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  rooms=(rs.getString("room_no_list"));
				  }
			  }catch (SQLException s){
				  Print.logException("DBAdapter.MDURoomList", s);
			  }catch(Exception e){
				Print.logException("DBAdapter.MDURoomList", e);
			  }finally{
				try{
					if (rs != null) {
		             rs.close();
					}
					if(statement!=null)
						con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.MDURoomList", se);
				}// do nothing
				try{
		        if(con!=null)
		           con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.MDURoomList", se);
				}//end finally try
			  }//end try	
		return rooms;
	}
	
	public ArrayList<MDU> MDUTokenList(String Rooms[]){
		ArrayList<MDU> listMDU= new ArrayList<MDU>();
		StringBuffer query = new StringBuffer();
		
		for(int i=0; i<Rooms.length; i++){
			query.append("user_details.room_id=");
			query.append(Rooms[i]);
			if(Rooms.length>i+1){
				query.append(" OR ");
			}
		}
		
		  Connection con = null;
		  ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select room_details.room_no, user_details.name, token_doc_summary.display_token, department_details.depart_name from user_details INNER JOIN token_doc_summary on token_doc_summary.user_id = user_details.user_id INNER JOIN room_details on room_details.room_id = user_details.room_id INNER JOIN department_details on department_details.depart_id=user_details.depart_id	where (user_details.login=1) and ("+ query +")";
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  MDU mdu = new MDU();
					  mdu.setCurrent_Token(rs.getString("display_token"));
					  mdu.setDepart_Name(rs.getString("depart_Name"));
					  mdu.setDoc_Name(rs.getString("name"));
					  mdu.setRoom_No(rs.getString("room_No"));
					  listMDU.add(mdu);
				  }
			  }catch (SQLException s){
				  Print.logException("DBAdapter.MDUTokenList", s);
			  }catch(Exception e){
				Print.logException("DBAdapter.MDUTokenList", e);
			  }finally{
				try{
					if (rs != null) {
		             rs.close();
					}
					if(statement!=null)
						con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.MDUTokenList", se);
				}// do nothing
				try{
		        if(con!=null)
		           con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.MDUTokenList", se);
				}//end finally try
			  }//end try	
		return listMDU;
	}
	
	public boolean DeviceHealthUpdate(String ipAddr, String address, long hitTime, int devType){
		boolean result =false;
		Connection con = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  String QueryString = "update device_details set ip='" + ipAddr + "', last_hit_time=" + hitTime + ", health_check=1 where address='" + address + "' and type=" + devType;
				  PreparedStatement updateEXP = con.prepareStatement(QueryString);
				  int res = updateEXP.executeUpdate();
				  result = res>0?true:false;
		  }catch (SQLException s){
			  Print.logException("DBAdapter.DeviceHealthUpdate", s);
		  }catch(Exception e){
			Print.logException("DBAdapter.DeviceHealthUpdate", e);
		  }finally{
			try{
	        if(con!=null)
	           con.close();
			}catch(SQLException se){
				Print.logException("DBAdapter.DeviceHealthUpdate", se);
			}//end finally try
		  }//end try
		
		return result;
	}
	
	public ArrayList<Integer> MDUDepartIDList(String Rooms[]){
		ArrayList<Integer> listDepartID= new ArrayList<Integer>();
		StringBuffer query = new StringBuffer();
		
		for(int i=0; i<Rooms.length; i++){
			query.append("room_id=");
			query.append(Rooms[i]);
			if(Rooms.length>i+1){
				query.append(" OR ");
			}
		}
		
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select distinct depart_id from room_details where "+ query;
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  listDepartID.add(rs.getInt("depart_id"));
				  }
			  }catch (SQLException s){
				  Print.logException("DBAdapter.MDUDepartIDList", s);
			  }catch(Exception e){
				Print.logException("DBAdapter.MDUDepartIDList", e);
			  }finally{
				try{
					if (rs != null) {
		             rs.close();
					}
					if(statement!=null)
						con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.MDUDepartIDList", se);
				}// do nothing
				try{
		        if(con!=null)
		           con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.MDUDepartIDList", se);
				}//end finally try
			  }//end try	
		return listDepartID;
	}
	
	public ArrayList<Advertisement> AdvertisementList(ArrayList<Integer> listDepartID, int devType){
		ArrayList<Advertisement> advertList= new ArrayList<Advertisement>();
		StringBuffer query = new StringBuffer();
		
		for(int i=0; i<listDepartID.size(); i++){
			query.append("depart_id=");
			query.append(listDepartID.get(i));
			if(listDepartID.size()>i+1){
				query.append(" OR ");
			}
		}
		
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select image_name, image_path from advertisement where device_type=" + devType + " and " + query;
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  Advertisement advertisement =new Advertisement();
					  advertisement.setImage_name(rs.getString("image_name"));
					  advertisement.setImage_path(rs.getString("image_path"));
					  advertList.add(advertisement);
				  }
			  }catch (SQLException s){
				  Print.logException("DBAdapter.AdvertisementList", s);
			  }catch(Exception e){
				Print.logException("DBAdapter.AdvertisementList", e);
			  }finally{
				try{
					if (rs != null) {
		             rs.close();
					}
					if(statement!=null)
						con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.AdvertisementList", se);
				}// do nothing
				try{
		        if(con!=null)
		           con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.AdvertisementList", se);
				}//end finally try
			  }//end try	
		return advertList;
	}
	
	public ArrayList<Integer> WDUDepartList(String Address){
		ArrayList<Integer> listDepartID= new ArrayList<Integer>();
		
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select room_details.depart_id from room_details INNER JOIN device_details on room_details.wdu_id = device_details.device_id where device_details.address='"+ Address +"' and device_details.type=2";
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  listDepartID.add(rs.getInt("depart_id"));
				  }
			  }catch (SQLException s){
				  Print.logException("DBAdapter.WDUDepartList", s);
			  }catch(Exception e){
				Print.logException("DBAdapter.WDUDepartList", e);
			  }finally{
				try{
					if (rs != null) {
		             rs.close();
					}
					if(statement!=null)
						con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.WDUDepartList", se);
				}// do nothing
				try{
		        if(con!=null)
		           con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.WDUDepartList", se);
				}//end finally try
			  }//end try	
		return listDepartID;
	}
	
	
	public void UpdateTodaysDate(){
		long todaysdate = System.currentTimeMillis()/1000;
		Connection con = null;
		ResultSet rs = null;
		  Statement statement = null;
		  try{
			  Class.forName(SystemSetting.DB_DRIVER);
			  con = DriverManager.getConnection(SystemSetting.DB_URL,SystemSetting.DB_USER,SystemSetting.DB_PASSWORD);
				  
				  statement = con.createStatement(); 
				  String QueryString = "select COUNT(*) from system_details";
				  rs = statement.executeQuery(QueryString);
				  while (rs.next()) {
					  int rowCount = rs.getInt(1);
					  if(rowCount == 0){
						  QueryString = "insert into system_details (today_date) values(" + todaysdate + ")";
						  PreparedStatement updateEXP = con.prepareStatement(QueryString);
						  int res = updateEXP.executeUpdate();
					  }else{
						  QueryString = "update system_details set today_date=" + todaysdate ;
						  PreparedStatement updateEXP = con.prepareStatement(QueryString);
						  int res = updateEXP.executeUpdate();
					  }
				  }
			  }catch (SQLException s){
				  Print.logException("DBAdapter.WDUDepartList", s);
			  }catch(Exception e){
				Print.logException("DBAdapter.WDUDepartList", e);
			  }finally{
				try{
					if (rs != null) {
		             rs.close();
					}
					if(statement!=null)
						con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.WDUDepartList", se);
				}// do nothing
				try{
		        if(con!=null)
		           con.close();
				}catch(SQLException se){
					Print.logException("DBAdapter.WDUDepartList", se);
				}//end finally try
			  }//end try	
	}
	
	
}
