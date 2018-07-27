<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="com.dan.dqms.reports.DoctorDaySummaryBean"%>



<%@ page import="org.dqms.db.User"%>






<%
	if (session.getAttribute("user") != null) {
%>
<%
	response.setHeader("Cache-Control",
		"no-cache, no-store, must-revalidate");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
%>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Report</title>
<link rel="icon" href="./images_dqms/title.gif" type="image/gif" />
<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="css_dqms/jquery.datetimepicker.css" />


<style type="text/css">
.custom-date-style {
	background-color: red !important;
}
</style>

<script>
				function setDates(){
				<%
				if(request.getParameter("from")==null || request.getParameter("from").equals(""))
				{
				%>
			 	var today = new Date();
			 	var yyyy = today.getFullYear();
			 	var mm = today.getMonth()+1;
			 	var dd = today.getDate();
			    if(dd<10){
			        dd='0'+dd
			    } 
			    if(mm<10){
			        mm='0'+mm
			    }  
			    var f = yyyy+'/'+mm+'/'+dd+' 00:00';
			    var e1 = document.getElementById("datetimepicker_mask");
			    e1.value = f;
			    var t = yyyy+'/'+mm+'/'+dd+' 23:00';
			    var e2 = document.getElementById("datetimepicker_mask1");
			    e2.value = t;
			    <%
			    }%>
				}
				</script>



</head>
<body onload='setDates();initTable("IP_tableTH");'>
<script src="js_dqms/sortTable.js" type="text/javascript" ></script>
<%!String from="";%>
<%!String to="";%>

<%
from=request.getParameter("from");
to=request.getParameter("to");

%>

	<div id="header">
		<div id="headerlogo">

			<div id="headerleft1">
				<%@include file="topmenu.jsp"%>

			</div>





		</div>
	</div>
	<div id="container">


		<!--  report table start -->
		<div id="report_table1">

			<TABLE BORDER="0" WIDTH="100%">

				<TH COLSPAN="5"
					style="background-color: #007E9C; height: 65px; color: #FFFFFF; font: normal 16px arial;">

					<b> Doctor Report</b>

				</TH>
			</TABLE>


			<div id="cont_shadow">



				<form name="doctorForm" method="post" action="./DoctorDaySummary">

					<table>
						<tr>
							<td align="center"
								style="height: 20px; font-weight: bold; font: normal 14px arial;">Select
								Doctor Name:</td>
							<td><select class="dropdown_conn_class" id="userID"
								name="users" required>
									<option value="">Select Doctor Name</option>




									<%
										@SuppressWarnings("unchecked")
																																																																		List<User> usersList = (ArrayList<User>) request
																																																																	.getAttribute("usersList");

																																																																	String options = request.getParameter("users");

																																																																if (usersList.size() > 0) {

																																																																	for (User listuser : usersList) {
																																																																	if (options != null) {

																																																																			if (Integer.parseInt(options) == listuser
																																																																						.getUser_id()) {
									%>
									<option value="<%=listuser.getUser_id()%>" selected="selected"><%=listuser.getName()%></option>

									<%
										} else {
									%>

									<option value="<%=listuser.getUser_id()%>"><%=listuser.getName()%></option>

									<%
										}

																																																																																			} else {
																																																																																				//else add it as it is.
									%>
									<option value="<%=listuser.getUser_id()%>"><%=listuser.getName()%></option>
									<%
										}
																																																																																		}
																																																																																	}
									%>






							</select></td>
							<td align="center"
								style="height: 20px; font-weight: bold; font: normal 14px arial;">From</td>
							<td><input type="text" value="" name="fromDate"
								id="datetimepicker_mask" required /></td>
							<td align="center"
								style="height: 20px; font-weight: bold; font: normal 14px arial;">To</td>
							<td><input type="text" value="" name="toDate"
								id="datetimepicker_mask1" required /></td>

							<td><input type="submit" name="loginStatus" value="Submit"
								id="formButton"></td>
						</tr>

					</table>
				</form>


				<div id="Doctore_wise_form">
					<table align="center" width="100%" border="0">
						<tbody>
							<tr
								style="background: #A8C7CE; height: 20px; font-size: 14px; color: #00599B; font-weight: normal; font-family: Arial, Helvetica, sans-serif;">


								<th>Doctor Name</th>
								<th>Department Name</th>
								<th>Room No</th>
								<th>Login Status</th>
								<th>Login Time</th>
								<th>Logout Time</th>

							</tr>
							<tr align="center"
								style="height: 20px; font-weight: bold; background-color: #EBF0F6; font: normal 14px arial;">

								<%
									@SuppressWarnings("unchecked")
								DoctorDaySummaryBean patientsListDetails = (DoctorDaySummaryBean) request
									.getAttribute("doctorBean");
								%>


								<td><%if(patientsListDetails.getDocName()==null)out.println("---");else{out.println(patientsListDetails.getDocName());}%></td>
								<td><%if(patientsListDetails.getDeptName()==null)out.println("---");else{out.println(patientsListDetails.getDeptName());}%></td>
								<td><%if(patientsListDetails.getRoomNum()==null)out.println("---");else{out.println(patientsListDetails.getRoomNum());}%></td>
								<td><%if(patientsListDetails.getLoginStatusStr()==null)out.println("---");else{out.println(patientsListDetails.getLoginStatusStr());}%></td>
								<td><%if(patientsListDetails.getLoginTime()==null)out.println("---");else{out.println(patientsListDetails.getLoginTime());}%></td>
								<td><%if(patientsListDetails.getLogoutTime()==null)out.println("---");else{out.println(patientsListDetails.getLogoutTime());}%></td>
							</tr>

							<tr
								style="background-color: #A8C7CE; color: #00599B; height: 20px; font: normal 14px arial;">
								<th>Total Patient in Queue</th>
								<th>Current Token Number</th>
								<th>Total Token Called</th>
								<th>Treated</th>
								<th>Skipped</th>
								<th>Cancelled</th>
							</tr>
							<tr align="center"
								style="height: 20px; font-weight: bold; background-color: #EBF0F6; font: normal 14px arial;">
								<td><%=patientsListDetails.getTotalPatientQueue()%></td>
								<td><%=patientsListDetails.getCurrentTokenNumber()%></td>
								<td><%=patientsListDetails.getTotalTokenCalled()%></td>
								<td><%=patientsListDetails.getTotalTreated()%></td>
								<td><%=patientsListDetails.getTotalSkipped()%></td>
								<td><%=patientsListDetails.getTotalcancelled()%></td>
							</tr>



						</tbody>
					</table>
				</div>

				<div id="Doctore_wise_image">

					<img src="images_dqms/Chandan.png" height="140px" width="143px;">
				</div>
				<div id="patient_wise_tableTH">

					<table id="IP_tableTH" align="center" width="100%" border="0">
						<tbody>
							<tr
								style="background-color: #A8C7CE; color: #00599B; height: 20px; font: normal 14px arial;">
								<th width="200px">Token No</th>
								<th width="200px">Token Issued Date</th>
								<th width="202px">Token Issued Time</th>
								<th width="202px">Token Called Time</th>
								<th width="200px">Token Relieving Time</th>
								<th width="200px">Waiting Time</th>
								<th width="200px">Treatment Time</th>
								<th width="200px">Patient Status</th>
							</tr>


							<%
								@SuppressWarnings("unchecked")
																								ArrayList<DoctorDaySummaryBean> patientsList = (ArrayList<DoctorDaySummaryBean>) request
																										.getAttribute("patientsList");

																								if (patientsList.size() > 0) {

																									for (DoctorDaySummaryBean listPatients : patientsList) {
							%>
							<tr
								style="background: #EBF0F6; height: 25px; font-size: 14px; text-align: center"">

								<td>
								<% if(listPatients.getTokenType()==2)
								{
								%>
								<%="C"+listPatients.getTokenNo()%>
								<%
								}
								else
								{
									%>
									<%=listPatients.getTokenNo()%>
									<%
								}
								%></td>
								<td><%=listPatients.getTokenIssueDate()%></td>
								<td><%=listPatients.getTokenIssueTime()%></td>
								<td><%=listPatients.getTokenCallTime()%></td>
								<td><%=listPatients.getTokenOverTime()%></td>
								<td><%=listPatients.getTotalWaiting()%></td>
								<td><%=listPatients.getTotalConsult()%></td>
								<td><%=listPatients.getStatus()%></td>


							</tr>




							<%
								}
																								} else {
							%>




							<tr>

								<td></td>
								<td></td>
								<td></td>


								<td id="response_mess" align="center"><%="Patients list not found !!"%></td>
							</tr>





							<%
								}
							%>







						</tbody>
					</table>

					<table align="center">
						<tr>
							<td>
								<%-- Using JSP EL to get message attribute value from request scope --%>
								<p id="response_mess">${requestScope.message}</p>
							</td>
						</tr>

					</table>





				</div>










			</div>
		</div>
	</div>
	<%@include file="footer_pg.jsp"%>

<% 
if((from!=null && !from.equals("")) && (to!=null && !to.equals(""))){
%>
<script type="text/javascript">
var elem1 = document.getElementById("datetimepicker_mask");
var elem2 = document.getElementById("datetimepicker_mask1");
elem1.value = '<%=from%>';
elem2.value = '<%=to%>';
</script>
<%	
}
%>



</body>

<script src="js_dqms/jquery.js"></script>
<script src="js_dqms/jquery.datetimepicker.js"></script>
<script>
	$('#datetimepicker_mask').datetimepicker({
		mask : '9999/19/39 29:59'
	});

	$('#datetimepicker_mask1').datetimepicker({
		mask : '9999/19/39 29:59'
	});
</script>
<head>
<meta http-equiv="Cache-Control"
	content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
</head>
</html>
<%
	} else {
		response.sendRedirect("login_pg.jsp");
	}
%>