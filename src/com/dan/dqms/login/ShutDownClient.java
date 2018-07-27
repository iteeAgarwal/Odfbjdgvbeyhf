package com.dan.dqms.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Device;
import org.dqms.util.Print;

import com.dan.dqms.ajax.URLConnectionOpen;
import com.dan.dqms.returnlist.SystemDetailsList;



/**
 * Servlet implementation class ShutDownClient
 */
@WebServlet("/ShutDownClient")
public class ShutDownClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
	StringBuilder content;

	public ShutDownClient() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Print.logInfo("ShutDownClient:doPost", null);
		
		URLConnectionOpen urlConnection = new URLConnectionOpen();

		String allShutdown = request.getParameter("allShutdown");

		String allReboot = request.getParameter("allReboot");

		String shutdown = request.getParameter("shutdown");

		String reboot = request.getParameter("reboot");

		String ipAddress = request.getParameter("ipAddress");
		

		
		if (shutdown != null && ipAddress != null) {	 
			
			String url_String = ("http://" + ipAddress + ":8080/ServerGpioCommand/ServerGpioCommand?command=Shutdown");
			urlConnection.openUrl(url_String);

		}
		if (reboot != null && ipAddress != null) {
			String url_String = ("http://" + ipAddress + ":8080/ServerGpioCommand/ServerGpioCommand?command=Reboot");

			urlConnection.openUrl(url_String);

		}

		if (allShutdown != null) {
			String ipList[] = request.getParameterValues("ipList");

			if (ipList != null) {
				for (int i = 0; i < ipList.length; i++) {
					String url_String = ("http://" + ipList[i] + ":8080/ServerGpioCommand/ServerGpioCommand?command=Shutdown");
					urlConnection.openUrl(url_String);
				}
			} else {
				request.setAttribute("messageShutdown",
						"Please select toggle all");
			}
		}
		if (allReboot != null) {
			String ipList[] = request.getParameterValues("ipList");

			if (ipList != null) {
				for (int i = 0; i < ipList.length; i++) {
					String url_String = ("http://" + ipList[i] + ":8080/ServerGpioCommand/ServerGpioCommand?command=Reboot");
					urlConnection.openUrl(url_String);

				}
			} else {
				request.setAttribute("messageShutdown",
						"Please select toggle all");
			}
		}

		SystemDetailsList systemDetailsOb = new SystemDetailsList();
		List<Device> deviceList = systemDetailsOb.getSystemDetails();

		request.setAttribute("listIPData", deviceList);
		request.getRequestDispatcher("/setting_shutdown.jsp").forward(request,
				response);

	}

	
}
