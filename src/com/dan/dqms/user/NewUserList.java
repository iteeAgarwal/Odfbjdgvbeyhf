package com.dan.dqms.user;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.db.Roles;
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
import com.dan.dqms.returnlist.UserRoleList;

@WebServlet("/NewUserList")
public class NewUserList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NewUserList() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
      
		UserList userList = new UserList();

		RoomsList roomList = new RoomsList();
		List<Room> roomsList = roomList.getRooms();

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		UserRoleList userListOb = new UserRoleList();
		List<Roles> roleList = userListOb.getUserRoles();

		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();

		String radioroomID = request.getParameter("radioroomID");

		String viewBtn = request.getParameter("roomView");

		String newBtn = request.getParameter("roomNew");

		String EditBtn = request.getParameter("roomEdit");

		String delBtn = request.getParameter("roomdelete");

		List<User> userListarr = userList.getUsers(radioroomID);
		List<User> userListall = userList.getAllUsers();

		if (viewBtn != null && radioroomID != null && userListarr.size() > 0) {

			request.setAttribute("userList", userListarr);
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.setAttribute("roleList", roleList);

			request.getRequestDispatcher("admin_user_list.jsp").forward(
					request, response);

		} else if (newBtn != null) {

			request.setAttribute("userList", userListall);
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.setAttribute("roleList", roleList);

			request.getRequestDispatcher("admin_user_list.jsp").forward(
					request, response);

		} else if (EditBtn != null && radioroomID != null
				&& userListarr.size() > 0) {

			request.setAttribute("userList", userListarr);
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.setAttribute("roleList", roleList);

			request.getRequestDispatcher("admin_user_list.jsp").forward(
					request, response);

		} else if (delBtn != null && userListall.size() > 0) {

			try {

				Query qry = session
						.createQuery("delete User p  where p.user_id ='"
								+ radioroomID + "'");

				int res = qry.executeUpdate();

				if (res > 0) {
					request.setAttribute("msg",
							"User delete successfully.....!!");
				}

				else {
					request.setAttribute("msg",
							"User delete Unsuccessfully.....!!");
				}

				tx.commit();

			
			} catch (Exception e) {
				Print.logException("Exception in delete user  form " , e);
			}

			UserList userList1 = new UserList();

			request.setAttribute("userList", userList1.getAllUsers());
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.setAttribute("roleList", roleList);

			request.getRequestDispatcher("admin_user_list.jsp").forward(
					request, response);
		} else {
			request.setAttribute("userList", userListall);
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.setAttribute("roleList", roleList);

			request.getRequestDispatcher("admin_user_list.jsp").forward(
					request, response);
		}
		session.close();
		DBManager.closeFactory();
	}

}
