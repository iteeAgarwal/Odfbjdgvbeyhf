package com.dan.dqms.patient;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.db.Patient;
import org.dqms.db.Room;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.PatientList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.UserList;

@WebServlet("/PatientListServlet")
public class PatientListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	int nxtCtr;
	public PatientListServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Print.logInfo("PatientListServlet:doPost", null);
		RoomsList roomList = new RoomsList();
		List<Room> roomsList = roomList.getRooms();

		UserList userList = new UserList();
		List<User> userListall = userList.getDoctors("2");

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		PatientList patList = new PatientList();
		List<Patient> patintList = patList.getAllPatients();
		
		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();

		String radioroomID = request.getParameter("radioroomID");

		String viewBtn = request.getParameter("roomView");

		String newBtn = request.getParameter("roomNew");

		String EditBtn = request.getParameter("roomEdit");

		String delBtn = request.getParameter("roomdelete");
		
		
		if(request.getParameter("cmdNxt")!=null){
			String s=request.getParameter("nextVal");
			if(s==null){
				nxtCtr=0;
			}
			else{
				nxtCtr = Integer.parseInt(s);	
			}
			
			PatientList patList2 = new PatientList();
			List<Patient> patintList2 = patList2.getAllPatients(nxtCtr);
			
			
			
			RoomsList roomList2 = new RoomsList();
			List<Room> roomsList2 = roomList.getRooms();

			UserList userList2 = new UserList();
			List<User> userListall2 = userList.getDoctors("2");

			DepartmentList deptListOb2 = new DepartmentList();
			List<Department> deptList2 = deptListOb.getDeptList();
			
			request.setAttribute("toGroupList", patintList2);
			request.setAttribute("userList", userListall2);
			request.setAttribute("deptList", deptList2);
			request.setAttribute("roomsList", roomsList2);
			request.setAttribute("toGroupList", patintList2);
			request.getRequestDispatcher("patient_list.jsp").forward(request,
					response);
			return;
		}

		if (viewBtn != null && radioroomID != null) {

			request.setAttribute("toGroupList", patList.getPatient(radioroomID));
			request.setAttribute("userList", userListall);
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);

			request.getRequestDispatcher("patient_list.jsp").forward(request,
					response);

		} else if (newBtn != null) {

			request.setAttribute("toGroupList", patintList);
			request.getRequestDispatcher("patient_list.jsp").forward(request,
					response);

		} else if (EditBtn != null && radioroomID != null) {

			request.setAttribute("toGroupList", patList.getPatient(radioroomID));
			request.setAttribute("userList", userListall);
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);

			request.getRequestDispatcher("patient_list.jsp").forward(request,
					response);

		} else if (delBtn != null && patintList.size() > 0) {

			try {

				Query qry = session
						.createQuery("delete Patient p  where p.patient_id ='"
								+ radioroomID + "'");

				int res = qry.executeUpdate();

				if (res > 0) {
					request.setAttribute("msg",
							"Patient ID delete successfully.....!!");
				} else {
					request.setAttribute("msg",
							"Patient ID delete Unsuccessfully.....!!");
				}

				// session.update(p);
				tx.commit();

				session.close();
                DBManager.closeFactory();
				PatientList patList1 = new PatientList();

				request.setAttribute("toGroupList", patList1.getAllPatients());

				request.setAttribute("userList", userListall);

				request.setAttribute("deptList", deptList);
				request.setAttribute("roomsList", roomsList);

				request.getRequestDispatcher("patient_list.jsp").forward(
						request, response);

			} catch (Exception e) {
				Print.logException("Exception in PatientListServlet edit form ",e);
			}

		} else {

			request.setAttribute("toGroupList", patintList);
			request.setAttribute("userList", userListall);
			request.setAttribute("deptList", deptList);
			request.setAttribute("roomsList", roomsList);
			request.getRequestDispatcher("patient_list.jsp").forward(request,
					response);
		}
	}

}
