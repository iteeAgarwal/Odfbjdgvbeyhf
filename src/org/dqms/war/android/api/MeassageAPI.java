package org.dqms.war.android.api;


import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import org.dqms.db.DBMessage;
/**
 * Servlet implementation class Meassage
 */
@WebServlet("/Meassage")
public class MeassageAPI extends HttpServlet {
	static Connection con=null; 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String message=request.getParameter("mess");
		System.out.println(message);
		int i=DBMessage.insert(message);
		if(i>0)
		{
			request.setAttribute("Status", "Message insert Successfully");
			System.out.println("Message insert Successfully");
			response.setContentType("text/Html");
			response.setHeader("abc", "Sucess");
		}
	}
}
