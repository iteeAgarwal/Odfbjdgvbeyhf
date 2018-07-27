<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="com.dan.dqms.reports.LoginLogoutBean"%>

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
<style type="text/css">
.custom-date-style {
	background-color: red !important;
}
.table-striped tbody tr:nth-child(odd) td {
    background-color: #81DAF5;
}

.table-striped tbody tr:nth-child(even) td {
    background-color: #CEECF5;
}

.table-striped tbody tr.highlight td {
    background-color: #F2F5A9;
}
.table-striped tbody tr:first-child td {
    background-color: #007E9C;
}

</style>
<link rel="icon" href="./images_dqms/title.gif" type="image/gif" />
<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css"
	href="css_dqms/jquery.datetimepicker.css" />




<script src="js_dqms/sortTable.js" type="text/javascript" ></script>
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
				}
				else{
					%>abc();<%
				}
				%>
				initTable("IP_tableTH");
				}
 				function abc(){
					var node = document.createElement("i");
					var e = document.getElementById("userID");
					var strUser = e.options[e.selectedIndex].text;
					var frDt = document.getElementById("datetimepicker_mask").value;
					var toDt = document.getElementById("datetimepicker_mask1").value;
				    var textnode = document.createTextNode('User: '+strUser+' From: '+frDt+' To: '+toDt);
				    node.appendChild(textnode);
				    document.getElementById("R_SUM").appendChild(node);
				} 
				</script>
<script src="js_dqms/jquery.min.js" type="text/javascript"></script>
<script>
$(document).ready(function(){
	$('#IP_tableTH').on('click', 'tbody tr', function(event) {
	    $(this).addClass('highlight').siblings().removeClass('highlight');
	});
});
</script>

</head>
<body onload="setDates()">
<%!String from="";%>
<%!String to="";%>
<%!String uid="";%>
<%
from=request.getParameter("from");
to=request.getParameter("to");
uid=request.getParameter("uid");


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

				<TH COLSPAN="5" style="background-color: #007E9C; height: 65px; color: #FFFFFF; font: normal 16px arial;">
					<b> Doctor Login/Logout Time Status </b><div id="R_SUM"></div>
				</TH>
				
			</TABLE>


			<div id="cont_shadow">
				<form name="doctorForm" method="post" action="./DoctorLoginLogout">

					<table>
						<tr>
							<td>Select User Name:</td>
							<td><select class="dropdown_conn_class" id="userID"
								name="users" required>
									<option value="">Select User Name</option>
									<option value="-1">All Users</option>



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
							<td>From</td>
							<td><input type="text" value="" name="fromDate"
								id="datetimepicker_mask" required /></td>
							<td>To</td>
							<td><input type="text" value="" name="toDate"
								id="datetimepicker_mask1" required /></td>
							<td><input type="submit" name="loginStatus" value="Submit"
								id="formButton"></td>
						</tr>

					</table>
				</form>
				<div id="status">
				<form name="doctorForm" method="post" action="./DoctorLoginLogout">
					<table id="IP_tableTH" width="100%" align="center"
						color:#3b485f;="" line-height:1.6em;=""
						border-collapse:collapse;="" class="table-striped">
						<tbody>
							<tr	style="background: #007E9C; height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">


								<th>User Name</th>
								<th>Login Time</th>
								<th>Logout Time</th>



							</tr>
							<%!int nxt=0; %>
							<%
								@SuppressWarnings("unchecked")
														ArrayList<LoginLogoutBean> loginLogoutList = (ArrayList<LoginLogoutBean>) request
																.getAttribute("loginLogoutList");

														
							
							if(loginLogoutList==null){
								%>
								<tr>

								<td></td>


								<td id="response_mess" align="center"><%="..."%></td>
							</tr>
								<%
							}
							
							
							else if (loginLogoutList.size() > 0) {
								int j=0;
								for (LoginLogoutBean listLogin : loginLogoutList) {
									j++;
							%>
							<tr
								style="height: 25px; font-size: 14px; text-align: center"">

								<td><%=listLogin.getUserName()%></td>
								<td><%=listLogin.getLogin_time()%></td>
								<td><%=listLogin.getLogout_time()%></td>

							</tr>
								<%if(j>=10){
									nxt=loginLogoutList.indexOf(listLogin);
									%>
									<input type="hidden" name="hid_nextVal" value="<%=nxt%>" />
									<input type="hidden" name="hid_uid" value="<%=uid%>" />
									<input type="hidden" name="hid_from" value="<%=from%>" />
									<input type="hidden" name="hid_to" value="<%=to%>" />
									<%
									break;
								}	
								%>
							<%
								}
														} else {
							%>




							<tr>

								<td></td>


								<td id="response_mess" align="center"><%="User list not found !!"%></td>
							</tr>





							<%
								}
							%>






						</tbody>
					</table>
					
					
					<table align="center">

							<tr>
								<td><input type="submit" value="Next>>" name="cmdNxt" id="formButton" /></td>
							</tr>

						</table>
					
					
						
					<table align="center">
					<tr>
					<td>
					<%-- Using JSP EL to get message attribute value from request scope --%>
										<p id="response_mess">${requestScope.message}</p>
					</td>
					</tr>
					
					</table>
				</form>

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
var elem3 = document.getElementById("userID");
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