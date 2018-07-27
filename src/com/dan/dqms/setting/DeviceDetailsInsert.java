package com.dan.dqms.setting;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.db.Device;
import org.dqms.util.Print;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.SystemDetailsList;

@WebServlet("/DeviceDetailsInsert")
public class DeviceDetailsInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public DeviceDetailsInsert() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		httpsession=request.getSession(false);
		boolean flag = false;

		long todayTime = System.currentTimeMillis() / 1000;

		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();

		SystemDetailsList systemDetailsOb = new SystemDetailsList();
		List<Device> deviceList = systemDetailsOb.getSystemDetails();

		String deviceDetailsBtn = request.getParameter("deviceDetails");

		String editDetailsBtn = request.getParameter("editDetails");

		String deviceID = request.getParameter("deviceID");

		String deviceName = request.getParameter("deviceName");

		int deviceType = Integer.parseInt(request.getParameter("deviceType"));

		String ipAddress = request.getParameter("ipAddress");

		String macAddress = request.getParameter("macAddress");

		String address = request.getParameter("address");

		String location = request.getParameter("location");

		if (deviceDetailsBtn != null) {

			try {

				if (deviceList.size() > 0) {
					for (Device list : deviceList) {
						if (ipAddress.equalsIgnoreCase(list.getIp())) {
							flag = true;
							httpsession.setAttribute("msg",
									"Device already exits.....!!");
							request.setAttribute("deviceList", deviceList);
							break;
						}

					}
				}
			
				if(deviceList.size() > 0) {
					for (Device list : deviceList) {
						if (deviceName.equalsIgnoreCase(list.getDevice_name())) {
							flag = true;
							httpsession.setAttribute("msg",
									"Device already  exits with same name.....!!");
							request.setAttribute("deviceList", deviceList);
							break;
						}

					}
				}
			
			
				if (!flag) {
					Device device = new Device();

					device.setDevice_name(deviceName);

					device.setType(deviceType);

					device.setIp(ipAddress);

					device.setMac_address(macAddress);

					device.setAddress(address);

					device.setLast_updated(todayTime);

					device.setLocation(location);

					session.persist(device);

					tx.commit();
			httpsession.setAttribute("msg",
							"Device Details save successfully.....!!");
					
				}

				response.sendRedirect("./DeviceDetailsListServlet");
				
				/*request.getRequestDispatcher("./DeviceDetailsListServlet")
						.forward(request, response);*/

			} catch (Exception e) {
				Print.logException("Exception " ,e);
			}

		} 

		if (editDetailsBtn != null) {

			Query qry = session
					.createQuery("update Device p set  p.device_name=?, p.type=?, p.ip=?, p.mac_address=? ,address=?,location=?,last_updated=? where p.device_id ='"
							+ deviceID + "'");
			qry.setParameter(0, deviceName);
			qry.setParameter(1, deviceType);
			qry.setParameter(2, ipAddress);
			qry.setParameter(3, macAddress);
			qry.setParameter(4, address);
			qry.setParameter(5, location);
			qry.setParameter(6, todayTime);

			int res = qry.executeUpdate();

			// session.update(p);
			tx.commit();

			session.close();
			if (res > 0) {
				httpsession.setAttribute("msg",
						"Device Details Edit successfully.....!!");
			} else {
				httpsession.setAttribute("msg",
						"Device Details Edit Unsuccessfully.....!!");
			}
			DBManager.closeFactory();
			response.sendRedirect("./DeviceDetailsListServlet");
/*			request.getRequestDispatcher("./DeviceDetailsListServlet").forward(
					request, response);*/

		} 

	}

}
