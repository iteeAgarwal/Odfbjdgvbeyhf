package com.dan.dqms.MS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.dqms.config.SystemSetting;
import org.dqms.util.Print;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import com.dan.dqms.token.TokenGeneratorData;

public class DbAdapterMS {

	public Connection connection() {
		Connection con = null;
		Statement statement = null;
		try {
			Class.forName(SystemSetting.DB_DRIVER);
			con = DriverManager.getConnection(SystemSetting.DB_URL,
					SystemSetting.DB_USER, SystemSetting.DB_PASSWORD);

		} catch (SQLException e) {
			e.printStackTrace();
			Print.logInfo("Exception in  TokenGeneratorData class" + e);
		} catch (Exception e) {
			Print.logInfo("Exception in  TokenGeneratorData class" + e);

			e.printStackTrace();

		}
		return con;

	}
	
	public int total_pateints(long date ,int depart)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query = null;
		Connection c = connection();
		
		if(depart==0)
		{
			Query ="select count(slno) from token_history where token_issue_time >="+date ;
		}
		else
		{
			Query ="select count(slno) from token_history where token_issue_time >="+date +"&& depart_id="+depart;
		}
		
		try {
			statement = c.createStatement();
			rs = statement.executeQuery(Query);
			while(rs.next())
			{
			data = rs.getInt(1);
			}
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
		return data;
	}
	public int total_pateints_attended(long date,int depart)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query = null;
		Connection c = connection();
		
		if(depart==0)
		{
			Query ="select count(slno) from token_history where token_issue_time >="+date +"&& status=3";
		}
		else
		{
			Query ="select count(slno) from token_history where token_issue_time >="+date +"&& status=3 && depart_id="+depart;
		}
		try {
			statement = c.createStatement();
			rs = statement.executeQuery(Query);
			while(rs.next())
			{
			data = rs.getInt(1);
			}
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		  
		return data;
	}
	
	public int total_doctor(int depart)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query = null;
		Connection c = connection();
		
		if(depart==0)
		{
			 Query ="select count(user_id) from user_details where role_id=2";
		}
		else
		{
			Query ="select count(user_id) from user_details where role_id=2 && depart_id ="+depart;
		}
		
		
		
		try {
			statement = c.createStatement();
			rs = statement.executeQuery(Query);
			while(rs.next())
			{
			data = rs.getInt(1);
			}
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return data;
	}
	
	public int total_doctor_login(int depart)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query=null;
		Connection c = connection();
		
		if(depart==0)
		{
			 Query ="select count(user_id) from user_details where role_id=2 && login=1 ";
		}
		else
		{
			 Query ="select count(user_id) from user_details where role_id=2 && login=1 && depart_id="+depart;
		}
		try {
			statement = c.createStatement();
			rs = statement.executeQuery(Query);
			while(rs.next())
			{
			data = rs.getInt(1);
			}
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return data;
	}
	
	public int total_token_generator(int depart_id)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query= null;
		Connection c = connection();
		
		if(depart_id==0)
		{
			 Query ="select count(user_id) from user_details where role_id=3";
		}
		else
		{
			 Query ="select count(user_id) from user_details where role_id=3 && depart_id="+depart_id;
		}
		try {
			statement = c.createStatement();
			rs = statement.executeQuery(Query);
			while(rs.next())
			{
			data = rs.getInt(1);
			}
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return data;
	}
	
	public int total_tokengenator_login(int depart)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query=null;
		Connection c = connection();
		
		if(depart==0)
		{
			Query ="select count(user_id) from user_details where role_id=3 && login=1";
		}
		else
		{
			Query ="select count(user_id) from user_details where role_id=3 && login=1 && depart_id ="+depart;
		}
		try {
			statement = c.createStatement();
			rs = statement.executeQuery(Query);
			while(rs.next())
			{
			data = rs.getInt(1);
			c.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return data;
	}
	
	public ArrayList<DataHelper> data_Display(long time , int depart)
	{
		ArrayList<DataHelper> ds = new ArrayList<DataHelper>();
		ds.add(new DataHelper(total_pateints(time, depart), total_pateints_attended(time, depart),total_pateints(time, depart)- total_pateints_attended(time, depart), avgWaitingTime(time,depart), total_doctor(depart), total_doctor_login(depart), total_token_generator(depart), total_tokengenator_login(depart)));
		
		return ds;
		
	}
	
	public int findDepartId(String Deptname)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query=null;
		Connection c = connection();
		
		Query ="select depart_id from department_details where depart_name like "+"'"+Deptname +"%'";
		try {
			statement = c.createStatement();
			rs = statement.executeQuery(Query);
			while(rs.next())
			{
			data = rs.getInt("depart_id");
			}
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		
		return data;
		
	}
	
	public long avgWaitingTime(long fromtime,int depart)
	{
		System.out.println("fromtime"+fromtime);
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		long data = 0;
		String Query;
		SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
		try{
			
			if(depart==0)
			{
				Query="Select * from token_history WHERE today_date>="+fromtime;
				System.out.println("Select * from token_history WHERE today_date>="+fromtime);
			}
			else
			{
				Query="Select * from token_history WHERE today_date>="+fromtime +" && depart_id="+depart;
				System.out.println("Select * from token_history WHERE today_date>="+fromtime +" && depart_id="+depart);
			}
		PreparedStatement pstmt = con
				.prepareStatement(Query);

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
			System.out.println("logouttime!=0");
			System.out.println("value"+value);
			System.out.println(Minutes.minutesBetween(dt1, dt2)
					.getMinutes());
			}
			
			else if(logouttime==0)
			{
				int logouttime1=Utils.currentTime();
				
				count++;
				long jlogintime = logintime * 1000l;
				long jlogouttime = logouttime1 *1000l;
				
				Date dlogin = new Date(jlogintime);
				Date dlogout = new Date(jlogouttime);
				
				
				DateTime dt1 = new DateTime(dlogin);
				DateTime dt2 = new DateTime(dlogout);
				
				value=value+Minutes.minutesBetween(dt1, dt2)
						.getMinutes();
				System.out.println("logouttime==0");
				System.out.println("value"+value);
				System.out.println(Minutes.minutesBetween(dt1, dt2)
						.getMinutes());
			}
		}
		if(count==0)
		{
			
		}
		else
		{
			
		
		 data = value/count;
		 System.out.println("value"+value);
		 System.out.println("count"+count);
		 System.out.println("data"+data);
		}
		con.close();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		//String value = Utils.minIntoHour(data);
		return data;
		
	}
	public static void main(String[] args) {
		DbAdapterMS db = new DbAdapterMS();
		/*System.out.println(db.total_pateints(1433144608,0));
		System.out.println(db.total_pateints_attended(1433144608,0));
		System.out.println(db.total_doctor(0));
		System.out.println(db.total_doctor_login(0));
		System.out.println(db.total_token_generator(0));
		System.out.println(db.total_tokengenator_login(0));*/
		System.out.println(db.findDepartId("Gyn"));
	}
}
