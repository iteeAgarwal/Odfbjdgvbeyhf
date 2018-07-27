package com.dan.dqms.MS;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.dqms.config.SystemSetting;
import org.dqms.util.Print;

public class DbAdapterMSGraph {

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
	
	
	public ArrayList<Integer> barChart(int depart)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query = null;
		Connection c = connection();
		ArrayList<Integer> ad = new ArrayList<Integer>(); 
		ArrayList<Long> al = Utils.date_ten();
		System.out.println(Utils.date_ten());
		
		for(int i=0 ;i<al.size()-1;i++)
		{
			if(depart==0)
			{
				Query ="select count(slno) from token_history where token_issue_time >="+al.get(i+1)+"&& token_issue_time <="+al.get(i);
			}
			else
			{
				Query ="select count(slno) from token_history where token_issue_time >="+al.get(i+1)+"&& token_issue_time <="+al.get(i) +" && depart_id="+depart;
			}
			try {
				statement = c.createStatement();
				rs = statement.executeQuery(Query);
				while(rs.next())
				{
				data = rs.getInt(1);
				ad.add(data);
				}
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  
		}
		
		
		return ad;
		
	}
	public ArrayList<Integer> barChart1(int depart)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query = null;
		Connection c = connection();
		ArrayList<Integer> ad = new ArrayList<Integer>(); 
		ArrayList<Long> al = Utils.date_ten();
		System.out.println(Utils.date_ten());
		
		for(int i=0 ;i<al.size()-1;i++)
		{
			if(depart==0)
			{
				Query ="select count(slno) from token_history where token_issue_time >="+al.get(i+1)+"&& token_issue_time <="+al.get(i)+" && status!=0";
			}
			else
			{
				Query ="select count(slno) from token_history where token_issue_time >="+al.get(i+1)+"&& token_issue_time <="+al.get(i) +" && depart_id="+depart+" && status!=0";
			}
			try {
				statement = c.createStatement();
				rs = statement.executeQuery(Query);
				while(rs.next())
				{
				data = rs.getInt(1);
				ad.add(data);
				}
				c.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			  
		}
		
		
		return ad;
		
	}
	public ArrayList<Integer> pieChart(long date,int depart)
	{
		Statement statement = null;
		ResultSet rs = null;
		int data = 0;
		String Query = null;
		ArrayList<Integer> ar = new ArrayList<Integer>();
		
		Connection c = connection();
		for(int i= 0;i<5;i++)
		{
		if(depart==0)
			Query="select count(slno) from token_history where token_issue_time >="+date +" && status ="+i;
		else
			Query="select count(slno) from token_history where token_issue_time >="+date+" && depart_id="+depart+" && status ="+i;
		try {
			statement = c.createStatement();
			rs = statement.executeQuery(Query);
			while(rs.next())
			{
			data = rs.getInt(1);
			ar.add(data);
			}
			c.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		}
		
		
		return ar;
		
	}
	
	
	public static void main(String[] args) {
		//System.out.println(new DbAdapterMSGraph().barChart(0));
		System.out.println(new DbAdapterMSGraph().pieChart(1432964861, 2));
		System.out.println(new DbAdapterMSGraph().barChart1(0));
	}
}
