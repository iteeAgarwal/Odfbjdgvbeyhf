package com.dan.dqms.returnlist;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.dqms.util.Print;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import com.dan.dqms.token.TokenGeneratorData;

public class HospitalSummayReport {

	
	

	
	public ArrayList<HospitalSummaryHelper> dataOutput(long fromtime,long totime)
	{
		ArrayList<HospitalSummaryHelper>  ah = new ArrayList<HospitalSummaryHelper>();
		ah.add(new HospitalSummaryHelper(totalPatients(fromtime, totime),totalPatientsTreated(fromtime, totime), avgWorkingTime(fromtime, totime), avgWaitingTime(fromtime, totime)));
		
		return ah;
	}
	
	
	public int totalPatients(long fromtime,long totime)
	{
		
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con=tk.connection();
		
		ResultSet rs = null;
		int totalpatients = 0 ;
		String Query="SELECT COUNT(slno) FROM token_history WHERE today_date>="+fromtime+" && today_date<="+totime;
		try{
		Statement statement = con.createStatement();
		rs = statement.executeQuery(Query);
		while (rs.next()) {
			
			totalpatients = rs.getInt(1);

		}
		 
		
		con.close();
		}
		catch(Exception e)
		{
			Print.logException("Exception in reports class" ,e);
		}
		return totalpatients;
		
	}
	
	
	public int totalPatientsTreated(long fromtime,long totime)
	{
		
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con=tk.connection();
		
		ResultSet rs = null;
		int totalpatients = 0 ;
		String Query="SELECT COUNT(slno) FROM token_history WHERE today_date>="+fromtime+" && today_date<="+totime+" && status=3";
		try{
		Statement statement = con.createStatement();
		 rs = statement.executeQuery(Query);
		 while (rs.next()) {
				
				totalpatients = rs.getInt(1);

			}
		 con.close();
		}
		catch(Exception e)
		{
			Print.logException("Exception in reports class" ,e);
		}
		return totalpatients;
		
	}
	  
	public String avgWorkingTime(long fromtime,long totime)
	{
		
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		long data = 0;
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
		try{
		PreparedStatement pstmt = con
				.prepareStatement("SELECT user_details.user_id, login_history.login_time, login_history.logout_time FROM user_details INNER JOIN login_history on login_history.user_id = user_details.user_id where role_id=2 &&  today_date>="+fromtime + " && today_date<="+totime );

		
		long value=0;
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
           
			int logintime= rs.getInt("login_time");
			int logouttime = rs.getInt("logout_time");
			
			if(logouttime!=0)
			{
				
			long jlogintime = logintime * 1000l;
			long jlogouttime = logouttime *1000l;
			
			Date dlogin = new Date(jlogintime);
			Date dlogout = new Date(jlogouttime);
			
			
			DateTime dt1 = new DateTime(dlogin);
			DateTime dt2 = new DateTime(dlogout);
			
			value=value+Minutes.minutesBetween(dt1, dt2)
					.getMinutes();
			
			}
		}
		int count = countDocNum();
		 data = value/count;
		con.close();
		}
		catch(Exception e)
		{
			Print.logException("Exception in reports class" ,e);
		}
		
		String value= minIntoHour(data);
		return value;
		
	}
	public String avgWaitingTime(long fromtime,long totime)
	{
		
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		long data = 0;
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
		try{
		PreparedStatement pstmt = con
				.prepareStatement("Select * from token_history WHERE today_date>="+fromtime+" && today_date<="+totime);

		int count =0;
		long value=0;
		ResultSet rs = pstmt.executeQuery();
		while (rs.next()) {
           
			int logintime= rs.getInt("token_issue_time");
			int logouttime = rs.getInt("token_call_time");
			
			if(logouttime!=0)
			{
				count++;
			long jlogintime = logintime * 1000l;
			long jlogouttime = logouttime *1000l;
			
			Date dlogin = new Date(jlogintime);
			Date dlogout = new Date(jlogouttime);
			
			
			DateTime dt1 = new DateTime(dlogin);
			DateTime dt2 = new DateTime(dlogout);
			
			value=value+Minutes.minutesBetween(dt1, dt2)
					.getMinutes();
			
			}
		}
		if(count==0)
		{
			
		}
		else
		{
			
		
		 data = value/count;
		}
		con.close();
		}
		catch(Exception e)
		{
			Print.logException("Exception in reports class" ,e);
		}
		
		String value = minIntoHour(data);
		return value;
		
	}
	
	public int countDocNum()
	{
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con=tk.connection();
		
		ResultSet rs = null;
		int totaldoc = 0 ;
		String Query="SELECT COUNT(user_id) FROM user_details WHERE role_id=2";
		try{
		Statement statement = con.createStatement();
		 rs = statement.executeQuery(Query);
		 while (rs.next()) {
				
				totaldoc = rs.getInt(1);

			}
		 con.close();
		}
		catch(Exception e)
		{
			Print.logException("Exception in reports class" ,e);
		}
		return totaldoc;
		
		
		
	}
	
	public String minIntoHour(long data)
	{
		int hours = (int) (data / 60); //since both are ints, you get an int
		int minutes = (int) (data % 60);
		String time = null;
		if(hours!=0)
		{
			time= String.valueOf(hours) + " hours " + String.valueOf(minutes) +" min " ;
		}
		 
		else
		{
			time = String.valueOf(minutes) +" min ";
		}
		return time;
	}
	public static void main(String[] args) {
		HospitalSummayReport hs = new HospitalSummayReport();
		ArrayList<HospitalSummaryHelper> ar =hs.dataOutput(1432636195,1432638708);
		
		HospitalSummaryHelper h=ar.get(0);
		
	}
}
