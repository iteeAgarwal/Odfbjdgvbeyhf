package com.dan.dqms.listener;

import java.sql.*;
import java.util.Calendar;
import java.util.Date;
import java.text.*;

import org.dqms.db.DBAdapter;
import org.dqms.util.DateTime;
import org.dqms.util.Print;
import org.joda.time.*;
import org.joda.*;

import com.dan.dqms.token.TokenGeneratorData;

public class DeleteChecker {
	 Connection con;
	Statement stmt, stmt2;
	PreparedStatement pstmt, pstmt2,pstmtUpdate;
	ResultSet rs, rs2;
	long lgDate1, lgDate2;
	Date dt1, dt2;
	DeleteChecker(){
	}
		
	

	public void process_for_token_history() {
		try {
			Print.logInfo("DeleteChecker:process_for_token_history", null);
			TokenGeneratorData tk = new TokenGeneratorData();
			con = tk.connection();
			stmt = con.createStatement();
			rs = stmt.executeQuery("SELECT * FROM token_history LIMIT 1;");
			if (rs.next()) {
				lgDate1 = rs.getInt("today_date");

				lgDate1 = lgDate1 * 1000;
			}

			rs = stmt
					.executeQuery("SELECT * FROM token_history ORDER BY slno DESC LIMIT 1");
			if (rs.next()) {
				lgDate2 = rs.getInt("today_date");

				lgDate2 = lgDate2 * 1000;
			}
			dt1 = new Date(lgDate1);
			dt2 = new Date(lgDate2);

			SimpleDateFormat sim = new SimpleDateFormat(
					"MMM dd,yyyy hh:mm:ss:a");

			long t = dt2.getTime() - dt1.getTime();
			int daysInBetween = (int) (t / (24 * 60 * 60 * 1000));
			if (daysInBetween > 30) {

				deleteAllRecs_token_history();
			} else {

			}
		} catch (Exception ex) {
			Print.logException("Exception in  DeleteChecker class" ,ex);
		}
		finally{
			if(stmt!=null){
				try{
				stmt.close();}catch(Exception ex){}
			}
			if(rs!=null){
				try{
				rs.close();}catch(Exception ex2){}
			}
			if(con!=null){
				try{
				con.close();}catch(Exception ex3){}
			}
		}
	}

	public void process_for_token_details() {
		try {
			
			Print.logInfo("DeleteChecker:process_for_token_details", null);
			TokenGeneratorData tk = new TokenGeneratorData();
			con = tk.connection();
			Date todayDate = new Date();
			long db_date = 0;
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


			stmt2 = con.createStatement();

			rs2 = stmt2.executeQuery("SELECT * FROM system_details");

			if (rs2.next()) {

				long tblDate = rs2.getLong("today_date");
				db_date = tblDate;
				long jTblDate = tblDate * 1000l;
				Date dt = new Date(jTblDate);

				Calendar cal=Calendar.getInstance();
				cal.setTime(dt);
				int year1=cal.get(Calendar.YEAR);
				int month1=cal.get(Calendar.MONTH);
				int day1=cal.get(Calendar.DAY_OF_MONTH);
				
				Calendar cal2=Calendar.getInstance();
				cal2.setTime(todayDate);
				int year2=cal2.get(Calendar.YEAR);
				int month2=cal2.get(Calendar.MONTH);
				int day2=cal2.get(Calendar.DAY_OF_MONTH);
				
				if(!(day1 == day2 && month1 == month2 && year1 ==year2)){
					deleteAllRecs_token_details();
					DBAdapter db = new DBAdapter();
					db.UpdateTodaysDate();	
				}

			}
			
			if(db_date == 0){
				deleteAllRecs_token_details();
				DBAdapter db = new DBAdapter();
				db.UpdateTodaysDate();				
			}

		} catch (Exception ex) {
			Print.logException("Exception in  DeleteChecker class process_for_token_details method"
					,ex);
		}
		finally{
			if(stmt2!=null)
				try{stmt2.close();}catch(Exception ex1){}
			
			if(rs2!=null)
				try{rs2.close();}catch(Exception ex1){}
			if(con!=null)
				try{con.close();}catch(Exception ex1){}
			
		}
	}

	void deleteAllRecs_token_details() {
		try {

			Print.logInfo("DeleteChecker:deleteAllRecs_token_details", null);
			TokenGeneratorData tk = new TokenGeneratorData();
			con = tk.connection();
			String deleteAllQuery = "delete from token_details";
			pstmt = con.prepareStatement(deleteAllQuery);
			int i = pstmt.executeUpdate();

			deleteAllQuery = "update token_doc_summary set total_token=0, current_token=0, display_token='0', total_token_walk=0, current_token_walk=0";
			pstmt = con.prepareStatement(deleteAllQuery);
			i = pstmt.executeUpdate();
			
			if (i != 0) {
				
			} else {
				
			}

		} catch (Exception ex) {
			Print.logException("Exception in  DeleteChecker class deleteAllRecs_token_details method"
				,ex);

		}
		finally{
			if(pstmt!=null)
				try{pstmt.close();}catch(Exception ex1){}
			if(con!=null)
				try{con.close();}catch(Exception ex1){}
			
		}
	}

	void deleteAllRecs_token_history() {
		try {
			
			String deleteAllQuery = "delete from token_history";
			stmt = con.createStatement();

			pstmt = con.prepareStatement(deleteAllQuery);
			int i = pstmt.executeUpdate();

			if (i != 0) {
				
			} else {
				
			}

		} catch (Exception ex) {
			Print.logException("Exception in  DeleteChecker class deleteAllRecs_token_history method",ex);
		}
		finally{
			if(pstmt!=null)
				try{pstmt.close();}catch(Exception ex1){}
			if(con!=null)
				try{con.close();}catch(Exception ex1){}
		}
	}
}
