package com.dan.dqms.login;

import java.io.IOException;
import java.sql.Connection;

import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.staticvar.StaticVariables;
import com.dan.dqms.token.TokenGeneratorData;

@WebServlet("/LogoutAction")
public class LogoutAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public LogoutAction() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Print.logInfo("LogoutAction:doPost", "");
		httpsession = request.getSession(false);
		if (httpsession == null) {
			response.sendRedirect("login_pg.jsp");
			return;
		}
		/*String user = null;
		user = (String) httpsession.getAttribute("user");*/
		if (httpsession != null && httpsession.getAttribute("user") != null) {

			updateLogoutTime();
			updateLoginHistory();
			httpsession.removeAttribute("user");
			httpsession.removeAttribute("_usrDptID");
			httpsession.removeAttribute("_usrRole");
			httpsession.removeAttribute("_usrTokStaDyn");
			httpsession.removeAttribute("_usrID");
			httpsession.removeAttribute("_usrName");
			httpsession.removeAttribute("_RoomID");
			if(httpsession.getAttribute("userRole")!=null)
			httpsession.removeAttribute("userRole");
			if(httpsession.getAttribute("msg")!=null)
			httpsession.removeAttribute("msg");
			httpsession.invalidate();
			
			

			response.sendRedirect("login_pg.jsp");
			/*
			request.getRequestDispatcher("/login_pg.jsp").forward(request,
					response);*/

		} else {
			response.sendRedirect("login_pg.jsp");
			/*request.getRequestDispatcher("/login_pg.jsp").forward(request,
					response);*/

		}

	}

	public void updateLogoutTime() {

		try {
			Print.logInfo("LogoutAction:updateLogoutTime", null);
			Session session = DBManager.getConfiuration();
			Transaction tx = session.beginTransaction();

			long todayTime = System.currentTimeMillis() / 1000;

			Query qry = session.createQuery("update User set login = false ,logout_time = '"
					+ todayTime + "' where user_id ='"
					+ httpsession.getAttribute("_usrID") + "'");

			int res = qry.executeUpdate();

			if (res > 0) {
				
				

			} else {
				
			}

			tx.commit();
			session.close();
			DBManager.closeFactory();
		} catch (Exception ex) {
			Print.logException("Exception in  updateLogoutTime" ,ex);

		}

	}

	public void updateLoginHistory() {

		Print.logInfo("LogoutAction:updateLoginHistory", null);
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();

		long todayTime = System.currentTimeMillis() / 1000;

		

		String Query = "update login_history set logout_time = '" + todayTime + "' where user_id ='" + httpsession.getAttribute("_usrID") + "'" +"&& login_time="+loginTime();

		try {
			Statement st = con.createStatement();

			int i = st.executeUpdate(Query);

			if (i > 0) {

			} else {

			}

		} catch (Exception ex) {
			Print.logException("Exception in  insertLoginHistory",ex);
		} finally {
			try {
				con.close();
			} catch (SQLException e) {
			}
		}

	}
	
	
	
	
	
	
	public String loginTime()

    {
		Print.logInfo("LogoutAction:loginTime", null);
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		ResultSet rs;
		String logintime = null;
		String Query="Select MAX(login_time) from login_history WHERE user_id="+httpsession.getAttribute("_usrID");
		try {
			PreparedStatement ps = con.prepareStatement(Query);
			rs = ps.executeQuery();
			while (rs.next()) {
				logintime = rs.getString(1);
			}
			
			con.close();

		} catch (SQLException e) {
			Print.logException("Exception in  tokenMax method TokenGeneratorStatic"
				,e);
		}
		
		return logintime;
		
	}
	
	
	
	

}
