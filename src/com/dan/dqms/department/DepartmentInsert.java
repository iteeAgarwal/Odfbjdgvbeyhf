package com.dan.dqms.department;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.db.Department;
import org.dqms.db.GroupMDU;
import org.dqms.db.Room;
import org.dqms.db.TokenGroup;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.MDUList;

@WebServlet("/DepartmentInsert")
public class DepartmentInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public DepartmentInsert() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		httpsession=request.getSession(false);
		DepartmentList departmentList = new DepartmentList();
		List<Department> arrlist = departmentList.getDeptList();

		Print.logInfo("DepartmentInsert", null);
		
		boolean flag = false;

		Session session = DBManager.getConfiuration();
        Transaction tx = session.beginTransaction();

		String departmentID = request.getParameter("departmentID");

		String departmentName = request.getParameter("departmentName");

		String deptSave = request.getParameter("deptSave");

		String deptEdit = request.getParameter("deptEdit");

		if (deptSave != null && departmentName != null) {

			try {

				if (arrlist.size() > 0) {
					for (Department list : arrlist) {
						if (departmentName.equalsIgnoreCase(list
								.getDepart_name())) {
							flag = true;
							httpsession.setAttribute("msg",
									"Department Name already exits.....!!");
							request.setAttribute("toGroupList", arrlist);
							break;
						}

					}
				}
				if (!flag) {
					Department groupDept = new Department();

					groupDept.setDepart_name(departmentName);

					session.persist(groupDept);

					tx.commit();
					
					httpsession.setAttribute("msg",
							"Department save successfully.....!!");

				}

				request.setAttribute("toGroupList", arrlist);

			} catch (Exception e) {
				
				Print.logException("Exception in department inert form",e);
			}

		} else {
			request.setAttribute("toGroupList", arrlist);
		}
		if (deptEdit != null && departmentID != null) {
			
			try{

			Query qry = session
					.createQuery("update Department p set  p.depart_name=? where p.depart_id ='"
							+ departmentID + "'");
			qry.setParameter(0, departmentName);

			int res = qry.executeUpdate();

			if (res > 0) {
				httpsession.setAttribute("msg",
						"Department Name Edit successfully.....!!");
			} else {
				httpsession.setAttribute("msg",
						"Department Name Edit successfully.....!!");
			}

			// session.update(p);
			tx.commit();

			session.close();
			DBManager.closeFactory();
			request.setAttribute("toGroupList", arrlist);
			
			} catch (Exception e) {
				Print.logException("Exception in department edit form",e);
			}

		} else {
			request.setAttribute("toGroupList", arrlist);
		}
		response.sendRedirect("./DepartmentListServlet");
/*		request.getRequestDispatcher("./DepartmentListServlet").forward(
				request, response);*/

	}
}
