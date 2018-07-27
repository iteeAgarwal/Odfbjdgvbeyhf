package com.dan.dqms.reports;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.db.Room;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import com.dan.dqms.dcu.DocSummaryBean;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.TokenDocSummaryList;
import com.dan.dqms.returnlist.UserList;
import com.dan.dqms.staticvar.StaticVariables;
import com.dan.dqms.token.TokenGeneratorData;

@WebServlet("/DoctorDaySummary")
public class DoctorDaySummary extends HttpServlet {
	private static final long serialVersionUID = 1L;

	long fromDateTime;

	long toDateTime;
	int usetID;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	public DoctorDaySummary() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String fromDate="";
		String toDate="";
		
		Print.logInfo("DoctorDaySummary:doPost", null);
		try {

			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

			DoctorDaySummaryBean doctorBean = new DoctorDaySummaryBean();

			String loginStatusBtn = request.getParameter("loginStatus");

			RoomsList roomList = new RoomsList();
			List<Room> roomsList = roomList.getRooms();

			DepartmentList deptListOb = new DepartmentList();
			List<Department> deptList = deptListOb.getDeptList();

			UserList userListOb = new UserList();
			List<User> usersList = userListOb.getDoctors("2");

			if (loginStatusBtn != null) {

				usetID = (Integer.parseInt(request.getParameter("users")));

				fromDate = request.getParameter("fromDate");

				toDate = request.getParameter("toDate");

				if (isValidDate(fromDate) && isValidDate(toDate)) {

					Date d;
					try {

						d = dateFormat.parse(fromDate);
						fromDateTime = d.getTime() / 1000;

						d = dateFormat.parse(toDate);
						toDateTime = d.getTime() / 1000;

						// request.setAttribute(
						// "allDeptReport",
						// processOne_Department_Limit(deptID,
						// fromDateTime, toDateTime));

					} catch (ParseException e) {
						Print.logException("Exception in  DoctorDaySummary reports class", e);
					}

				} else {
					request.setAttribute("message", "Date format invalid");
					if (usersList.size() > 0) {

						//usetID = usersList.get(0).getUser_id();
					}
				}

			} else {

				if (usersList.size() > 0) {

					usetID = usersList.get(0).getUser_id();
				}

			}

			if (usersList.size() > 0 &&loginStatusBtn != null) {

				// int usetID = usersList.get(0).getUser_id();

				List<User> usersListDetails = userListOb.getUsers(String
						.valueOf(usetID));

				/***************** set doctor details for selected doctor *****************/

				doctorBean.setDocName(usersListDetails.get(0).name);
				int deptID = usersListDetails.get(0).depart_id;

				if (!deptList.isEmpty()) {
					for (Department list : deptList) {
						if (deptID == list.getDepart_id()) {
							doctorBean.setDeptName(list.getDepart_name());
						}
					}

				} else {
					doctorBean.setDeptName("");
				}

				int roomID = usersListDetails.get(0).room_id;

				if (!roomsList.isEmpty()) {
					for (Room list : roomsList) {
						if (roomID == list.getRoom_id()) {
							doctorBean.setRoomNum(list.getRoom_no());
							break;
						}
					}
				} else {
					doctorBean.setRoomNum("");
				}

				boolean loginStatus = usersListDetails.get(0).login;
				if (loginStatus) {

					doctorBean.setLoginStatusStr("Login");

					long loginTime = usersListDetails.get(0).login_time;

					if (loginTime != 0) {
						Date loginDate = new Date(loginTime * 1000L);
						doctorBean.setLoginTime(sdf.format(loginDate));

					} else {
						doctorBean.setLoginTime("");
					}

					doctorBean.setLogoutTime("");

				} else {
					doctorBean.setLoginStatusStr("Logout");
					long loginTime = usersListDetails.get(0).login_time;

					if (loginTime != 0) {
						Date loginDate = new Date(loginTime * 1000L);
						doctorBean.setLoginTime(sdf.format(loginDate));

					} else {
						doctorBean.setLoginTime("");
					}
					long logoutTime = usersListDetails.get(0).logout_time;

					if (logoutTime != 0) {
						Date logoutDate = new Date(logoutTime * 1000L);
						doctorBean.setLogoutTime(sdf.format(logoutDate));
					} else {
						doctorBean.setLogoutTime("");
					}
				}

				/***************** set token status for selected doctor *****************/

				ArrayList<DocSummaryBean> doc_Report_List = processOne_Doctor_Limit(
						usetID, fromDateTime, toDateTime);
				if (!doc_Report_List.isEmpty()) {

					TokenDocSummaryList tokenSummary = new TokenDocSummaryList();

					doctorBean.setTotalPatientQueue(doc_Report_List.get(0)
							.getTotalPatients());

					doctorBean.setCurrentTokenNumber(tokenSummary
							.getCurrentToken(usetID));

					doctorBean.setTotalTokenCalled(doc_Report_List.get(0)
							.getTotalCall());

					doctorBean.setTotalTreated(doc_Report_List.get(0)
							.getTotalTreat());

					doctorBean.setTotalSkipped(doc_Report_List.get(0)
							.getTotalSkip());

					doctorBean.setTotalcancelled(doc_Report_List.get(0)
							.getTotalCancel());

				}

				

			}

			request.setAttribute("usersList", usersList);

			request.setAttribute("doctorBean", doctorBean);
			if(loginStatusBtn != null)
			{
				
				request.setAttribute("patientsList", getPatientsDetails(usetID,fromDateTime,toDateTime));
			}
			else
			{
				
				request.setAttribute("patientsList", getPatientsDetails(0,fromDateTime,toDateTime));
			}

		} catch (Exception e) {
			Print.logException("Exception in  DoctorDaySummary reports class"
					+ e,e);
		}

		request.getRequestDispatcher("report_doc_day_summary.jsp?from="+fromDate+"&to="+toDate).forward(
				request, response);

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

	public ArrayList<DoctorDaySummaryBean> getPatientsDetails(int userid ,long fromDate,long toDate ) {
		ArrayList<DoctorDaySummaryBean> doc_Report_List = new ArrayList<DoctorDaySummaryBean>();
		try {
			Print.logInfo("DoctorDaySummary", null);
			TokenGeneratorData tk = new TokenGeneratorData();
			Connection con = tk.connection();

			SimpleDateFormat format = new SimpleDateFormat("hh:mm:ss");
			SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
			PreparedStatement pstmt = con
					.prepareStatement("select * from token_history where user_id='"
							+ userid + "'   and today_date >= '"+fromDate+"' and today_date <= '"+toDate+"' limit 100");

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {

				DoctorDaySummaryBean docBean = new DoctorDaySummaryBean();

				int token_no = rs.getInt("token_no");
				int status = rs.getInt("status");
				int token_issue_time = rs.getInt("token_issue_time");
				int token_call_time = rs.getInt("token_call_time");
				int token_over_time = rs.getInt("token_over_time");
				int token_type = rs.getInt("app_walk_id");

				long jToken_issue_time = token_issue_time * 1000l;
				long jToken_call_time = token_call_time * 1000l;
				long jToken_over_time = token_over_time * 1000l;

				Date dIssue_time = new Date(jToken_issue_time);
				Date dCall_time = new Date(jToken_call_time);
				Date dOver_time = new Date(jToken_over_time);

				String strIssue = format.format(dIssue_time);
				String strCall = format.format(dCall_time);
				String strOver = format.format(dOver_time);

				String strIssue_date = format2.format(dIssue_time);

				DateTime dt1 = new DateTime(dIssue_time);
				DateTime dt2 = new DateTime(dCall_time);
				DateTime dt3 = new DateTime(dOver_time);

				docBean.setTokenNo(token_no);
				
                docBean.setTokenType(token_type);
                 
				docBean.setTokenIssueDate(strIssue_date);

				docBean.setTokenIssueTime(strIssue);

				if(status==0 && token_call_time==0)
				{
					docBean.setTokenCallTime("NA");

					
					docBean.setTokenOverTime("NA");

					docBean.setTotalWaiting("NA");

					docBean.setTotalConsult("NA");
				}
				else if(status==1 && token_call_time!=0)
				{
				docBean.setTokenCallTime(strCall);

				docBean.setTokenOverTime("NA");

				docBean.setTotalWaiting("NA");

				docBean.setTotalConsult("NA");
				
				}
				else if(status==2)
				{
                    docBean.setTokenCallTime(strCall);
                    
                    docBean.setTokenOverTime(strOver);

					docBean.setTotalWaiting(minIntoHour(Minutes.minutesBetween(dt1, dt2)
							.getMinutes()));
					docBean.setTotalConsult("NA");
				}
				else
				{
					docBean.setTokenCallTime(strCall);

					
					docBean.setTokenOverTime(strOver);

					docBean.setTotalWaiting(minIntoHour(Minutes.minutesBetween(dt1, dt2)
							.getMinutes()));

					docBean.setTotalConsult(minIntoHour(Minutes.minutesBetween(dt2, dt3)
							.getMinutes()));
				}

				String tokenStatusName = "";

				if (status == 1) {
					tokenStatusName = "Called";

				} else if (status == 2) {
					tokenStatusName = "Skipped";
				} else if (status == 3) {
					tokenStatusName = "Treated";
				} else if (status == 4) {
					tokenStatusName = "cancelled";
				}

				docBean.setStatus(tokenStatusName);

				doc_Report_List.add(docBean);

			}
		} catch (Exception ex) {
			Print.logException("Exception in  DoctorDaysSummary class" + ex,ex);

		}
		return doc_Report_List;
	}

	/* selected doctor reports */

	public ArrayList<DocSummaryBean> processOne_Doctor_Limit(int d_ID,
			long fromDateTime, long toDateTime) {

		Print.logInfo("DoctorDaySummary:processOne_Doctor_Limit", null);
		
		ArrayList<DocSummaryBean> dept_Report_List = new ArrayList<DocSummaryBean>();

		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();

		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs, rs2;

		int tot_tok = 0, no_mean = 0, tot_called = 0, tot_skip = 0, tot_treat = 0, tot_cancel = 0;
		DocSummaryBean doctorBean = new DocSummaryBean();

		try {

			for (int status = 0; status <= 4; status++) {
				pstmt = con
						.prepareStatement("select * from token_history where user_id=? and status=? and today_date >= ? and today_date <= ? limit 100");

				pstmt.setInt(1, d_ID);
				pstmt.setInt(2, status);
				pstmt.setLong(3, fromDateTime);
				pstmt.setLong(4, toDateTime);

				rs2 = pstmt.executeQuery();

				while (rs2.next()) {
					tot_tok++;
					if (rs2.getInt("status") == 0) {
						no_mean++;
					} else if (rs2.getInt("status") == 1) {
						tot_called++;
					} else if (rs2.getInt("status") == 2) {
						tot_skip++;
					} else if (rs2.getInt("status") == 3) {
						tot_treat++;
					} else if (rs2.getInt("status") == 4) {
						tot_cancel++;
					}
				}

			}

			doctorBean.setTotalPatients(tot_tok);

			doctorBean.setTotalCall(tot_called);

			doctorBean.setTotalTreat(tot_treat);

			doctorBean.setTotalSkip(tot_skip);

			doctorBean.setTotalCancel(tot_cancel);

			dept_Report_List.add(doctorBean);
              con.close();
		} catch (Exception ex) {

			Print.logException("Exception in  DepartmentDaySummary class processOne_Department_Limit methos"
					+ ex,ex);
		}
		return dept_Report_List;
	}
	public String minIntoHour(long data)
	{
		int hours = (int) (data / 60); //since both are ints, you get an int
		int minutes = (int) (data % 60);
		String time = null;
		if(hours!=0)
		{
			time= String.valueOf(hours) + " hours " + String.valueOf(minutes) +" min " ;
		}
		 
		else
		{
			time = String.valueOf(minutes) +" min ";
		}
		return time;
	}
}
