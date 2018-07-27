<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.Device"%>
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
<title>Shutdown</title>
<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />
<style>
a:link {
    text-decoration: none;
}

a:visited {
    text-decoration: none;
}

a:hover {
    text-decoration: none;
}

a:active {
    text-decoration: none;
}
</style>

<script language="JavaScript">
	function toggle(source) {
		checkboxes = document.getElementsByName('ipList');
		for (var i = 0, n = checkboxes.length; i < n; i++) {
			checkboxes[i].checked = source.checked;
		}
	}
</script>

<script type="text/javascript">
 function rowSelecter(id) {
	 
	 if (document.getElementById("ch"+id).checked) {
		 document.getElementById("ch"+id).checked = false;
     } else {
    	 document.getElementById("ch"+id).checked = true;
     }
	 
 }
</script>
</head>
<body>
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

					<b>Device Maintenance</b>

				</TH>
			</TABLE>


			<div id="cont_shadow">


				<div id="shutdown">
					<form name="doctorForm" method="post" action="./ShutDownClient">
						<table id="IP_tableTH" width="100%" align="center"
							color:#3b485f;="" line-height:1.6em;=""
							border-collapse:collapse;="">
							<tbody>
								<tr
								style="background: #A8C7CE; height: 40px; color:#006BB4; font-size: 14px; font-family: Arial, Helvetica, sans-serif;">
								<th width="8%"
									style="text-align: center; font-weight: bold;"
									scope="col"><input type="checkbox" onClick="toggle(this)" />
									Toggle All</th>
								<th width="14%"
									style=" text-align: center; font-weight: bold;"
									scope="col">Device Name</th>
								<th width="14%"
									style=" text-align: center; font-weight: bold;"
									scope="col">IP Address</th>
								<th width="12%"
									style=" text-align: center; font-weight: bold;"
									scope="col">MAC Address</th>

								<th width="10%"
									style=" text-align: center; font-weight: bold;"
									scope="col">Is Connected</th>
								<th width="14%"
									style=" text-align: center; font-weight: bold;"
									scope="col">Shutdown</th>

								<th width="14%"
									style=" text-align: center; font-weight: bold;"
									scope="col">Reboot</th>
								
								<th width="12%"
									style=" text-align: center; font-weight: bold;"
									scope="col">Config</th>

							</tr>




								<%
								@SuppressWarnings("unchecked")
									ArrayList<Device> list = (ArrayList<Device>) request
											.getAttribute("listIPData");

									if (list.size() > 0) {
										int j=0;
										for (int i = list.size() - 1; i >= 0; i--) {
											j++;
							%>
							<FORM ACTION="./ShutDownClient" METHOD="POST">
								<tr id="<%=j %>" style="background: #EBF0F6; height: 25px; font-size: 14px;" align="center" onclick="rowSelecter(this.id)">

									<td ><input type="checkbox" name="ipList"
										value="<%=list.get(i).getIp()%>" id="ch<%=j %>"></td>
									<td ><%=list.get(i).getDevice_name()%></td>
									<td ><%=list.get(i).getIp()%></td>

									<td ><%=list.get(i).getMac_address()%></td>
									<td ><%
									boolean healthCheck=list.get(i).isHealth_check();
									if(healthCheck){
										%>
										<img src="images_dqms/connected.png" height="40px" width="43px;">
										<%
									}
									else{
										%>
										<img src="images_dqms/disconnected.png" height="40px" width="43px;">
										<%
									}
									%></td>

									<input type="hidden" name="ipAddress"
										value="<%=list.get(i).getIp()%>" />
									<td ><input type="submit" name="shutdown"
										value="ShutDown" id="formButton"></td>
									<td ><input type="submit" name="reboot"
										value="Reboot" id="formButton"></td>
									<td ><%String str=list.get(i).getIp();%>
									<a id="formButton" href='http://<%=str+"/PISConfiguration/"%>'>&nbsp;&nbsp;&nbsp;link&nbsp;&nbsp;&nbsp;</a></td>
									
								</tr>

							</FORM>



							<%
								}
										for (int i = list.size() - 1; i >= 0; i--) {
							%>
							<FORM ACTION="./ShutDownClient" METHOD="POST">
								<input type="checkbox" name="ipList"
									value="<%=list.get(i).getIp()%>"
									style="opacity: 0; position: absolute; left: 9999px;">


								<%
									}
								%>

								<tr align="center">

									<td></td>
									<td></td>
									<td></td>
									</tr>
									<tr></tr>
									<tr>
									<td></td>
									<td></td>
									<td></td>
									<td><input type="submit" name="allShutdown"
										value="All ShutDown" id="formButton"></td>
									<td><input type="submit" name="allReboot"
										value="All Reboot" id="formButton"></td>

								</tr>

								<tr align="center">
									<td></td>
									<td></td>
									<td width="20%">
										<%-- Using JSP EL to get message attribute value from request scope --%>
										<p id="response_mess">${requestScope.messageShutdown}</p>
									</td>


								</tr>
							</FORM>

							<%
								} else {
							%>

							<tr align="center">

								<td id="response_mess"><%="No Data"%></td>


							</tr>


							<%
								}
							%>

						</tbody>
					</table>


					</form>
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