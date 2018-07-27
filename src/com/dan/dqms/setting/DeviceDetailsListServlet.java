package com.dan.dqms.setting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Device;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.SystemDetailsList;

@WebServlet("/DeviceDetailsListServlet")
public class DeviceDetailsListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public DeviceDetailsListServlet() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		SystemDetailsList systemDetailsOb = new SystemDetailsList();

		List<Device> deviceList = systemDetailsOb.getSystemDetails();

		Session session = DBManager.getConfiuration();

		Transaction tx = session.beginTransaction();

		String radioroomID = request.getParameter("radioroomID");

		String viewBtn = request.getParameter("roomView");

		String newBtn = request.getParameter("roomNew");

		String EditBtn = request.getParameter("roomEdit");

		String delBtn = request.getParameter("roomdelete");

		List<Device> deviceListByID = systemDetailsOb
				.getSystemByID(radioroomID);

		if (viewBtn != null && radioroomID != null && deviceListByID.size() > 0) {
			request.setAttribute("deviceList", deviceListByID);

		} else if (newBtn != null) {

			request.setAttribute("deviceList", deviceList);

		} else if (EditBtn != null && radioroomID != null
				&& deviceListByID.size() > 0) {
			request.setAttribute("deviceList", deviceListByID);

		} else if (delBtn != null && deviceListByID.size() > 0) {

			Query qry = session
					.createQuery("delete Device p  where p.device_id ='"
							+ radioroomID + "'");

			int res = qry.executeUpdate();

			if (res > 0) {
				request.setAttribute("msg", "Device delete successfully.....!!");
			}

			else {
				request.setAttribute("msg",
						"Device delete Unsuccessfully.....!!");
			}

			// session.update(p);
			tx.commit();

			session.close();

			SystemDetailsList systemDetailsOb1 = new SystemDetailsList();

			List<Device> deviceList1 = systemDetailsOb1.getSystemDetails();

			request.setAttribute("deviceList", deviceList1);

		} else {
			request.setAttribute("deviceList", deviceList);

		}

		request.getRequestDispatcher("system_settings_list.jsp").forward(
				request, response);
	}

}
