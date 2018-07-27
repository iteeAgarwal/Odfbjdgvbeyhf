package org.dqms.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class DBMessage 
{
static Connection con=null;  
	
	public static Connection getConn()
	{
		try
		{  
			Class.forName("com.mysql.jdbc.Driver");  
			con=DriverManager.getConnection("jdbc:mysql://localhost:3306/dqms","root","rootroot");  
			System.out.println(con);
		}
		catch(Exception e)
		{ 
		 	System.out.println(e);
		 } 
		return con;
			}
	public static int insert(String message)
	{
		// TODO Auto-generated method stub
		int p=0;
		try
		{
		  Connection con=getConn();
		  PreparedStatement ps=con.prepareStatement("insert into message(mess) values(?)");
		  ps.setString(1,message);
		   p=ps.executeUpdate();
		   System.out.println(p);
		}
		catch(Exception ex)
		{
			System.out.println(ex);
		}
	 
		return p;
	}
	
}

