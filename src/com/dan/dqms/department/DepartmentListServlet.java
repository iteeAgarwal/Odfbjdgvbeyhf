package com.dan.dqms.department;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.db.Room;
import org.dqms.db.TokenGroup;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.MDUList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.TokGroupList;
import com.dan.dqms.returnlist.UserList;

@WebServlet("/DepartmentListServlet")
public class DepartmentListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DepartmentListServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		Print.logInfo("DepartmentListServlet:doPost", null);
		
		
		RoomsList roomList = new RoomsList();
        DepartmentList departmentList = new DepartmentList();

		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();

		String radioroomID = request.getParameter("radioroomID");

		String viewBtn = request.getParameter("roomView");

		String newBtn = request.getParameter("roomNew");

		String EditBtn = request.getParameter("roomEdit");

		String delBtn = request.getParameter("roomdelete");

		if (viewBtn != null && radioroomID != null) {
			request.setAttribute("toGroupList",
					departmentList.getDepart(radioroomID));

		} else if (newBtn != null) {
			request.setAttribute("toGroupList", departmentList.getDeptList());

		} else if (EditBtn != null && radioroomID != null) {
			request.setAttribute("toGroupList",
					departmentList.getDepart(radioroomID));

		} else if (delBtn != null && radioroomID != null) {

			try {

				List<Room> roomsList = roomList.getRoomsBydepID(radioroomID);

				/*----get room list with department---*/
				if (roomsList.size() > 0) {

					request.setAttribute("msg",
							"First Delete  rooms this department");

				} else {

					Query qryDept = session
							.createQuery("delete Department p  where p.depart_id ='"
									+ radioroomID + "'");

					int resDept = qryDept.executeUpdate();

					if (resDept > 0) {
						request.setAttribute("msg",
								"Department delete successfully.....!!");

						Print.logInfo("Department delete success this department  "
								+ radioroomID);

					} else {
						request.setAttribute("msg",
								"Department delete Unsuccessfully.....!!");

						Print.logInfo("Department delete Unsuccess this department  "
								+ radioroomID);
					}

					tx.commit();

					session.close();
                    DBManager.closeFactory();
				}

			} catch (Exception e) {
				Print.logException("Exception in department  delete form", e);
			}

			request.setAttribute("toGroupList", departmentList.getDeptList());

		} else {
			request.setAttribute("toGroupList", departmentList.getDeptList());

		}

		request.getRequestDispatcher("department_list.jsp").forward(request,
				response);
	}

}
