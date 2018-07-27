<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.Device"%>

<%@ page import="org.dqms.db.MDU"%>


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
<title>Display</title>
<style>
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





<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />
<script src="js_dqms/jquery.min.js" type="text/javascript"></script>
<script>
$(document).ready(function(){
	$('#IP_tableTH').on('click', 'tbody tr', function(event) {
	    $(this).addClass('highlight').siblings().removeClass('highlight');
	});
});
</script>
</head>
<body onLoad='initTable("IP_tableTH");'>
<script src="js_dqms/sortTable.js" type="text/javascript" ></script>
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

					<b> Display Token</b>

				</TH>
			</TABLE>


			<div id="cont_shadow">



				<form name="doctorForm" method="post" action="./CurrentDisplayList">

					<table>
						<tr>
							<td>Select Department:</td>
							<td><select class="dropdown_conn_class" id="deptID"
								name="mduID" required>
									<option value="" selected disabled style="display: none;">Select Department</option>
									<!-- <option value="-1">All Department</option> -->

									<%
										@SuppressWarnings("unchecked")
											List<Device> deviceList = (ArrayList<Device>) request
													.getAttribute("deviceList");

											String options = request.getParameter("mduID");

											if (deviceList.size() > 0) {

												for (Device listdept : deviceList) {
													if (options != null) {

														if (Integer.parseInt(options) == listdept
																.getDevice_id()) {
									%>
									<option value="<%=listdept.getDevice_id()%>"
										selected="selected"><%=listdept.getDevice_name()%></option>

									<%
										} else {
									%>

									<option value="<%=listdept.getDevice_id()%>"><%=listdept.getDevice_name()%></option>

									<%
										}

													} else {
														//else add it as it is.
									%>
									<option value="<%=listdept.getDevice_id()%>"><%=listdept.getDevice_name()%></option>
									<%
										}
												}
											}
									%>



							</select></td>
							<td></td>
							<td></td>
							<td></td>

							<td><input type="submit" name="currentStatus" value="Submit"
								id="formButton"></td>
						</tr>

					</table>
				</form>
				<div id="status">
					<table id="IP_tableTH" width="60%" align="center" color:#3b485f;=""
						line-height:1.6em;="" border-collapse:collapse;="" class="table-striped">
						<tbody>
							<tr	style="background: #007E9C; height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">


								<th scope="col">Room Number</th>
								<th scope="col">Doctor Name</th>
								<th scope="col">Current Token</th>



							</tr>
							<%
							
							String currentStatusBtn = request.getParameter("currentStatus");
							
							if(currentStatusBtn != null){
								@SuppressWarnings("unchecked")
									ArrayList<MDU> listMDU = (ArrayList<MDU>) request
											.getAttribute("listMDU");

								if(listMDU==null){
									%>
																<tr>

								<td></td>
								

								<td align="center" style="color: #B20000;"><%="Token Status list is Blank !!!"%></td>
							</tr>
									<%
								}	
								
								else if (listMDU.size() > 0) {
                                 
										for (MDU list : listMDU) {
							%>
							<tr
								style="height: 25px; font-size: 14px; text-align: center"">

								<td><%=list.getRoom_No()%></td>
								<td><%=list.getDoc_Name()%></td>
								<td><%=list.getCurrent_Token()%></td>
							</tr>

							<%
								}
									} else {
							%>
							<tr>

								<td></td>
								

								<td align="center" style="color: #B20000;"><%="Token Status list is Blank !!!"%></td>
							</tr>

							<%
								}
							}
							%>

						</tbody>
					</table>
				</div>

			</div>
		</div>
	</div>
	<%@include file="footer_pg.jsp"%>

</body>
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