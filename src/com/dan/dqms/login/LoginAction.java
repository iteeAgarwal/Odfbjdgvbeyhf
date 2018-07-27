package com.dan.dqms.login;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.config.SystemSetting;
import org.dqms.db.Department;
import org.dqms.db.Room;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.UserList;
import com.dan.dqms.staticvar.StaticVariables;
import com.dan.dqms.token.TokenGeneratorData;

@WebServlet("/LoginAction")
public class LoginAction extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	
	
	File update_log = null;
	@Override 
	public void init() throws ServletException { 
	    super.init(); 

	}	
	
	
	
	public LoginAction() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		httpsession=request.getSession(true);
		System.out.println("session check "+httpsession.getId());
		Print.logInfo("LoginAction:doPost", null);
		String root = getServletContext().getRealPath("/");
		StaticVariables.setRealPath(root);
		
		RoomsList roomList = new RoomsList();
		List<Room> roomsList = roomList.getRooms();

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		UserList userList = new UserList();
		List<User> userListArr = userList.getAllUsers();

		String login = request.getParameter("login");
		String uname = request.getParameter("username");
		String upass = request.getParameter("password");
		ServletContext app = getServletContext();
		if (login != null && userListArr.size() > 0) {
			try {
				boolean loginsuccess = false;
				int userRole = 0;
				for (User list : userListArr) {
					if (uname.equalsIgnoreCase(list.getUsername())
							&& upass.equalsIgnoreCase(list.getPassword())) {
						userRole = list.getRole_id();
						httpsession.setAttribute("_usrDptID", list.getDepart_id());
						httpsession.setAttribute("_usrRole", userRole);
						httpsession.setAttribute("_usrTokStaDyn", 1);
						httpsession.setAttribute("_usrID", list.getUser_id());
						httpsession.setAttribute("_usrName", list.getName());
						httpsession.setAttribute("_RoomID", list.getRoom_id());
						
						
				/*		StaticVariables.setUserDeptID(list.getDepart_id());

						StaticVariables.setUserRoleID(userRole);

						StaticVariables.setTokenStaticDynamicID(1);

						StaticVariables.setUserID(list.getUser_id());

						StaticVariables.setUserNameStatic(list.getName());

						StaticVariables.setRoomID(list.getRoom_id());*/

						loginsuccess = true;

					}

				}

				if (loginsuccess) {
					httpsession.setAttribute("userRole", userRole);

					httpsession.setAttribute("user", uname);

					/* update login time */

					updateLoginTime();

					/* insert login history value */
					insertLoginHistory();
					if (userRole == 3) {
						request.setAttribute("userDeptID",
								httpsession.getAttribute("_usrDptID"));
						response.sendRedirect("./TokenInsert");
					}else if (userRole == 2) {
						response.sendRedirect("./DCUCallSkip");
					}else if (userRole == 1) {
							response.sendRedirect("./CurrentDisplayList");
					} else {
						response.sendRedirect("./PatientListServlet");
					}
				} else {
					request.setAttribute("deptList", deptList);
					request.setAttribute("roomsList", roomsList);
					request.setAttribute("loginMess",
							"User Name and Password Invalid..!!");
					request.getRequestDispatcher("login_pg.jsp").forward(
							request, response);
					}
			} catch (Exception e) {
				Print.logInfo("Exception in  Login Time " + e);
			}

		} else {
		request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.getRequestDispatcher("login_pg.jsp").forward(request,
					response);
		}
	}

	public void updateLoginTime() {
		try {
			Print.logInfo("LoginAction:updateLoginTime()", null);
			Session session = DBManager.getConfiuration();
			Transaction tx = session.beginTransaction();

			long todayTime = System.currentTimeMillis() / 1000;

			Query qry = session.createQuery("update User set  login = 1 ,login_time = '"
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
			Print.logException("Exception in  updateLoginTime" ,ex);
		}
	}

	public void insertLoginHistory() throws SQLException {

		Print.logInfo("LoginAction:insertLoginHistory", null);
		TokenGeneratorData tk = new TokenGeneratorData();

		long todayTime = System.currentTimeMillis() / 1000;

		Connection con = tk.connection();

		String Query = "insert into login_history(user_id, login_time, logout_time, today_Date) values(?,?,?,?)";

		try {
			PreparedStatement stmt = con.prepareStatement(Query);
			stmt.setInt(1, (Integer)httpsession.getAttribute("_usrID"));
			stmt.setLong(2, todayTime);
			stmt.setInt(3, 0);
			stmt.setLong(4, todayTime);

			int i = stmt.executeUpdate();

			if (i > 0) {

			} else {

			}

		} catch (Exception ex) {
			Print.logException("Exception in  insertLoginHistory"  , ex);
		} finally {
			con.close();
		}

	}

}
