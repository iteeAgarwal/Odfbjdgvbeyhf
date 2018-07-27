package com.dan.dqms.reports;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.db.User;
import org.dqms.util.Print;

import com.dan.dqms.returnlist.LoginLogoutList;
import com.dan.dqms.returnlist.UserList;

@WebServlet("/DoctorLoginLogout")
public class DoctorLoginLogout extends HttpServlet {
	private static final long serialVersionUID = 1L;

	long fromDateTime;

	long toDateTime;
	static int nxtCtr;
	int usersID;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	HttpSession httpsession;
	

	
	
	public DoctorLoginLogout() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Print.logInfo("DoctorLoginLogout", null);
		
		String fromDate="";
		String toDate="";
		LoginLogoutList loginLogoutList = new LoginLogoutList();

		UserList userListOb = new UserList();
		List<User> usersList = userListOb.getAllUsers();

		String loginStatusBtn = request.getParameter("loginStatus");

		
		if(request.getParameter("cmdNxt")!=null){
			String s=request.getParameter("hid_nextVal");
			if(s==null){
				nxtCtr=0;
			}
			else{
				nxtCtr = nxtCtr+Integer.parseInt(s);	
			}
			
			
			
			
			if(request.getParameter("hid_uid")!=null && request.getParameter("hid_from")!=null && request.getParameter("hid_to")!=null){
				

				if (usersID == -1) {
					
					request.setAttribute("usersList", usersList);
					request.setAttribute("loginLogoutList",
							loginLogoutList.getLoginLogoutDetails(loginLogoutList.getLoginLogoutDetails(fromDateTime, toDateTime),nxtCtr));
					request.getRequestDispatcher("report_login_logout.jsp?from="+fromDate+"&to="+toDate+"&uid"+usersID).forward(
							request, response);
					return;

				}

				//,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,
				usersID = (Integer.parseInt(request.getParameter("hid_uid")));

				fromDate = request.getParameter("hid_from");

				toDate = request.getParameter("hid_to");
		
				

				
				
			//	else{
					if (isValidDate(fromDate) && isValidDate(toDate)) {

					Date d;
					try {

						d = dateFormat.parse(fromDate);
						fromDateTime = d.getTime() / 1000;

						d = dateFormat.parse(toDate);
						toDateTime = d.getTime() / 1000;

						request.setAttribute("usersList", usersList);
						request.setAttribute("loginLogoutList",
								loginLogoutList.getLoginLogoutDetails(loginLogoutList.getLoginLogoutDetails(usersID, fromDateTime, toDateTime),nxtCtr));
						
						request.getRequestDispatcher("report_login_logout.jsp?from="+fromDate+"&to="+toDate+"&uid="+usersID).forward(
								request, response);
						return;

					} catch (ParseException e) {
						Print.logException("Exception in  DoctorLoginLogout reports class", e);
					}

				}
			else {
					request.setAttribute("message", "Date format invalid");
					request.setAttribute("loginLogoutList",
							loginLogoutList.getLoginLogoutDetails());
				}
				//}	
				
				
				request.setAttribute("usersList", usersList);
				request.setAttribute("loginLogoutList",
						loginLogoutList.getLoginLogoutDetails(loginLogoutList.getLoginLogoutDetails(usersID, fromDateTime, toDateTime),nxtCtr));
				
				request.getRequestDispatcher("report_login_logout.jsp?from="+fromDate+"&to="+toDate+"&uid="+usersID).forward(
						request, response);
				return;
			}
			else{
				/*request.setAttribute("loginLogoutList", loginLogoutList
						.getLoginLogoutDetails(usersID, fromDateTime,
								toDateTime));*/
				
				request.setAttribute("usersList", usersList);
				request.getRequestDispatcher("report_login_logout.jsp").forward(
						request, response);return;
			}

			
		}
		
		
		
		
		
		if (loginStatusBtn != null) {

			usersID = (Integer.parseInt(request.getParameter("users")));

			fromDate = request.getParameter("fromDate");

			toDate = request.getParameter("toDate");
			
			

			if (usersID == -1) {
				try{
				Date d2;
				d2 = dateFormat.parse(fromDate);
				fromDateTime = d2.getTime() / 1000;

				d2 = dateFormat.parse(toDate);
				toDateTime = d2.getTime() / 1000;
				}catch(Exception ex){}
				
				
				request.setAttribute("usersList", usersList);
				request.setAttribute("loginLogoutList",
						loginLogoutList.getLoginLogoutDetails(fromDateTime,toDateTime));
				request.getRequestDispatcher("report_login_logout.jsp?from="+fromDate+"&to="+toDate+"&uid="+usersID).forward(
						request, response);
				return;

			} else {

				if (isValidDate(fromDate) && isValidDate(toDate)) {

					Date d;
					try {

						d = dateFormat.parse(fromDate);
						fromDateTime = d.getTime() / 1000;

						d = dateFormat.parse(toDate);
						toDateTime = d.getTime() / 1000;

						request.setAttribute("loginLogoutList", loginLogoutList
								.getLoginLogoutDetails(usersID, fromDateTime,
										toDateTime));
						
						
						
						
						request.setAttribute("usersList", usersList);

						request.getRequestDispatcher("report_login_logout.jsp?from="+fromDate+"&to="+toDate+"&uid="+usersID).forward(
								request, response);
						
						
						
						
						

					} catch (ParseException e) {
						Print.logException("Exception in  DoctorLoginLogout reports class" + e,e);
					}

				} else {
					request.setAttribute("message", "Date format invalid");
					request.setAttribute("loginLogoutList",
							loginLogoutList.getLoginLogoutDetails());
				}

			}

		} else {
			

			request.setAttribute("usersList", usersList);
			/*request.setAttribute("loginLogoutList",
					loginLogoutList.getLoginLogoutDetails());*/
			
			request.getRequestDispatcher("report_login_logout.jsp").forward(
					request, response);
		}

/*		request.setAttribute("usersList", usersList);

		request.getRequestDispatcher("report_login_logout.jsp").forward(
				request, response);*/
	}

	public boolean isValidDate(String inDate) {

		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

}
