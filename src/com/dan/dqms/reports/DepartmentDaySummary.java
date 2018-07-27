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
import org.dqms.db.User;
import org.dqms.util.Print;

import com.dan.dqms.dcu.DocSummaryBean;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.LoginLogoutList;
import com.dan.dqms.returnlist.UserList;
import com.dan.dqms.token.TokenGeneratorData;

@WebServlet("/DepartmentDaySummary")
public class DepartmentDaySummary extends HttpServlet {
	private static final long serialVersionUID = 1L;

	long fromDateTime;

	long toDateTime;

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	public DepartmentDaySummary() {
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
		Print.logInfo("DepartmentDaySummary:doPost", null);
		LoginLogoutList loginLogoutList = new LoginLogoutList();

		UserList userListOb = new UserList();
		List<User> usersList = userListOb.getAllUsers();

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		String tokenStatusBtn = request.getParameter("tokenStatus");

		if (tokenStatusBtn != null) {

			int deptID = (Integer.parseInt(request.getParameter("depart")));

			fromDate = request.getParameter("fromDate");

			toDate = request.getParameter("toDate");

			if (deptID == -1 && isValidDate(fromDate) && isValidDate(toDate)) {

				Date d;
				try {
					

					d = dateFormat.parse(fromDate);
					fromDateTime = d.getTime() / 1000;

					d = dateFormat.parse(toDate);
					toDateTime = d.getTime() / 1000;

					request.setAttribute(
							"allDeptReport",
							processAll_Department_Limit(fromDateTime,
									toDateTime));

				} catch (ParseException e) {
					Print.logException("Exception in  DepartmentDaySummary reports class"
							+ e,e);

				}

			} else {

				if (isValidDate(fromDate) && isValidDate(toDate)) {

					Date d;
					try {

						d = dateFormat.parse(fromDate);
						fromDateTime = d.getTime() / 1000;

						d = dateFormat.parse(toDate);
						toDateTime = d.getTime() / 1000;

						request.setAttribute(
								"allDeptReport",
								processOne_Department_Limit(deptID,
										fromDateTime, toDateTime));

					} catch (ParseException e) {
						Print.logException("Exception in  DepartmentDaySummary reports class"
								+ e,e);
					}

				} else {
					request.setAttribute("message", "Date format invalid");
					request.setAttribute("allDeptReport",
							processAll_Department());
				}

			}

		} else {

			request.setAttribute("allDeptReport", processAll_Department());
			// processOne_Department_Limit(1);
		}

		request.setAttribute("deptList", deptList);

		request.getRequestDispatcher("report_dept_day_summary.jsp?from="+fromDate+"&to="+toDate).forward(
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

	/* All department reports with range */

	public ArrayList<DocSummaryBean> processAll_Department_Limit(
			long fromDateTime, long toDateTime) {
		Print.logInfo("DepartmentDaySummary:processAll", null);
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();

		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs, rs2;

		ArrayList<DocSummaryBean> dept_Report_List = new ArrayList<DocSummaryBean>();

		try {

			pstmt = con.prepareStatement("select * from department_details");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int dept_id = rs.getInt("depart_id");
				String dept_name = rs.getString("depart_name");

				DocSummaryBean doctorBean = new DocSummaryBean();

				int tot_tok = 0, no_mean = 0, tot_called = 0, tot_skip = 0, tot_treat = 0, tot_cancel = 0;

				for (int status = 0; status <= 4; status++) {

					pstmt = con
							.prepareStatement("select * from token_history where depart_id=? and status=? and today_date >= ? and today_date <= ? limit 100");

					pstmt.setInt(1, dept_id);
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

				doctorBean.setDeptName(dept_name);

				doctorBean.setTotalPatients(tot_tok);

				doctorBean.setTotalCall(tot_called);

				doctorBean.setTotalTreat(tot_treat);

				doctorBean.setTotalSkip(tot_skip);

				doctorBean.setTotalCancel(tot_cancel);

				dept_Report_List.add(doctorBean);

			}
			con.close();
		} catch (Exception ex) {
			Print.logException("Exception in  DepartmentDaySummary class processAll_Department_Limit methos"
					+ ex,ex);
		}
		return dept_Report_List;
	}

	/* All department reports */

	public ArrayList<DocSummaryBean> processAll_Department() {
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();
		Print.logInfo("DepartmentDaySummary:processAll_Department", null);
		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs, rs2;

		ArrayList<DocSummaryBean> dept_Report_List = new ArrayList<DocSummaryBean>();

		try {

			pstmt = con.prepareStatement("select * from department_details");
			rs = pstmt.executeQuery();

			while (rs.next()) {
				int dept_id = rs.getInt("depart_id");
				String dept_name = rs.getString("depart_name");

				DocSummaryBean doctorBean = new DocSummaryBean();

				int tot_tok = 0, no_mean = 0, tot_called = 0, tot_skip = 0, tot_treat = 0, tot_cancel = 0;

				for (int status = 0; status <= 4; status++) {

					pstmt = con
							.prepareStatement("select * from token_history where depart_id=? and status=?  limit 100");

					pstmt.setInt(1, dept_id);
					pstmt.setInt(2, status);

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

				doctorBean.setDeptName(dept_name);

				doctorBean.setTotalPatients(tot_tok);

				doctorBean.setTotalCall(tot_called);

				doctorBean.setTotalTreat(tot_treat);

				doctorBean.setTotalSkip(tot_skip);

				doctorBean.setTotalCancel(tot_cancel);

				dept_Report_List.add(doctorBean);

			}
			con.close();
		} catch (Exception ex) {
			Print.logException("Exception in  DepartmentDaySummary class processAll_Department_Limit methos"
					+ ex,ex);
			
		}
		return dept_Report_List;
	}

	/* selected department reports */

	public ArrayList<DocSummaryBean> processOne_Department_Limit(int d_ID,
			long fromDateTime, long toDateTime) {

		ArrayList<DocSummaryBean> dept_Report_List = new ArrayList<DocSummaryBean>();
		Print.logInfo("DepartmentDaySummary:processOne_Department_limit", null);
		TokenGeneratorData tk = new TokenGeneratorData();
		Connection con = tk.connection();

		Statement stmt = null;
		PreparedStatement pstmt = null;
		ResultSet rs, rs2;
		String dept_name = null;

		int tot_tok = 0, no_mean = 0, tot_called = 0, tot_skip = 0, tot_treat = 0, tot_cancel = 0;
		DocSummaryBean doctorBean = new DocSummaryBean();

		try {

			pstmt = con
					.prepareStatement("select * from department_details where depart_id ='"
							+ d_ID + "' ");
			rs = pstmt.executeQuery();

			while (rs.next()) {

				dept_name = rs.getString("depart_name");
			}

			for (int status = 0; status <= 4; status++) {
				pstmt = con
						.prepareStatement("select * from token_history where depart_id=? and status=? and today_date >= ? and today_date <= ? limit 100");

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

			doctorBean.setDeptName(dept_name);

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

}
