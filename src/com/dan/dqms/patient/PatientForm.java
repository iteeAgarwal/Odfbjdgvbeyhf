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

/**
 * Servlet implementation class PatientForm
 */
@WebServlet("/PatientForm")
public class PatientForm extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public PatientForm() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		
		Print.logInfo("PatientForm:doPost", null);
		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();

		String patientEdit = request.getParameter("patientEdit");
		
		

		try {
			if (patientEdit != null) {

				String ptID = request.getParameter("ptID");

				String ptName = request.getParameter("ptName");

				String phNo = request.getParameter("phNo");

				String idCardNo = request.getParameter("idCardNo");

				String rFid = request.getParameter("rFid");

				String appWalk = request.getParameter("appWalk");

				int deptID = Integer.parseInt(request.getParameter("depart"));

				int rooms_list = Integer.parseInt(request
						.getParameter("rooms_list"));

				String docIDStr = request.getParameter("doctor_list");
				

				int doctor_list = 0;

				if ("0".equals(docIDStr)) {
					doctor_list = 0;
				} else {
					doctor_list = Integer.parseInt(docIDStr);
				}

				
				

				/*
				 * String insur = request.getParameter("insur");
				 * 
				 * boolean insurance = false;
				 * 
				 * if ("1".equals(insur)) { insurance = true; }
				 */

				Query qry = session
						.createQuery("update Patient p set  p.patient_name=?,phone_no=?,id_card_no=?,rfid_no=?,depart_id=?,room_id=?,doctor_id=? where p.patient_id ='"
								+ ptID + "'");
				qry.setParameter(0, ptName);

				qry.setParameter(1, phNo);

				qry.setParameter(2, idCardNo);

				qry.setParameter(3, rFid);

				qry.setParameter(4, deptID);

				qry.setParameter(5, rooms_list);

				qry.setParameter(6, doctor_list);

				int res = qry.executeUpdate();

				tx.commit();

				session.close();
				DBManager.closeFactory();

				if (res > 0) {
					
					request.setAttribute("msg", "Patient Edit successfully.....!!");
				} else {
					
					
					request.setAttribute("msg",
							"Patient Edit Unsuccessfully.....!!");

				}

				request.getRequestDispatcher("./PatientListServlet").forward(request,
						response);

			}

		} catch (Exception e) {

			 Print.logException("Exception in patient entry form ",e);

		}

	}
}
