package com.dan.dqms.token.group;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.db.Room;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.TokGroupList;

@WebServlet("/TokenGroupList")
public class TokenGroupList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TokenGroupList() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		TokGroupList toGroupList = new TokGroupList();

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		RoomsList roomList = new RoomsList();
		List<Room> roomsList = roomList.getRooms();

		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();

		String radioroomID = request.getParameter("radioroomID");
		String viewBtn = request.getParameter("roomView");
		String newBtn = request.getParameter("roomNew");
		String EditBtn = request.getParameter("roomEdit");
		String delBtn = request.getParameter("roomdelete");

		if (viewBtn != null && radioroomID != null) {

			request.setAttribute("toGroupList",
					toGroupList.getTOGroupListByID(radioroomID));
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.getRequestDispatcher("token_group_list.jsp").forward(
					request, response);

		} else if (newBtn != null) {

			request.setAttribute("toGroupList", toGroupList.getTOGroupList());
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.getRequestDispatcher("token_group_list.jsp").forward(
					request, response);

		} else if (EditBtn != null && radioroomID != null) {

			request.setAttribute("toGroupList",
					toGroupList.getTOGroupListByID(radioroomID));
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.getRequestDispatcher("token_group_list.jsp").forward(
					request, response);

		} else if (delBtn != null && radioroomID != null) {
			
			try{

			Query qry = session
					.createQuery("delete TokenGroup p  where p.token_group_id ='"
							+ radioroomID + "'");

			int res = qry.executeUpdate();

			if(res > 0)
			{
				request.setAttribute("msg",
						"Token Group delete successfully.....!!");
			}
			else
			{
				request.setAttribute("msg",
						"Token Group delete Unsuccessfully.....!!");
			}
			
			tx.commit();

			

			

			request.setAttribute("toGroupList", toGroupList.getTOGroupList());
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.getRequestDispatcher("token_group_list.jsp").forward(
					request, response);
			
			}catch(Exception e)
			{
				Print.logException("Exception in token group delete form", e);
			}

		} else {

			request.setAttribute("toGroupList", toGroupList.getTOGroupList());
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.getRequestDispatcher("token_group_list.jsp").forward(
					request, response);
		}
		session.close();
		DBManager.closeFactory();
	}

}
