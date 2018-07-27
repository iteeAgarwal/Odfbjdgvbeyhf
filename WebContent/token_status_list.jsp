<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.Token"%>

<%@ page import="org.dqms.db.Department"%>

<%@ page import="com.dan.dqms.token.TokenStatusBean"%>
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
<title>Token List</title>
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

					<b> Token Status List</b>

				</TH>
			</TABLE>


			<div id="cont_shadow">



				<form name="doctorForm" method="post" action="./TokenStatusList">

					<table>
						<tr>
							<td>Select Department:</td>
							<td><select class="dropdown_conn_class" id="deptID"
								name="depart" required>
									<option value="" selected disabled style="display: none;">Select Department</option>
									<option value="-1">All Department</option>

									<%
										@SuppressWarnings("unchecked")
											List<Department> deptList = (ArrayList<Department>) request
													.getAttribute("deptList");

											String options = request.getParameter("depart");

											if (deptList.size() > 0) {

												for (Department listdept : deptList) {
													if (options != null) {

														if (Integer.parseInt(options) == listdept
																.getDepart_id()) {
									%>
									<option value="<%=listdept.getDepart_id()%>"
										selected="selected"><%=listdept.getDepart_name()%></option>

									<%
										} else {
									%>

									<option value="<%=listdept.getDepart_id()%>"><%=listdept.getDepart_name()%></option>

									<%
										}

													} else {
														//else add it as it is.
									%>
									<option value="<%=listdept.getDepart_id()%>"><%=listdept.getDepart_name()%></option>
									<%
										}
												}
											}
									%>



							</select></td>
							<td></td>
							<td></td>
							<td></td>

							<td><input type="submit" name="tokenStatus" value="Submit"
								id="formButton"></td>
						</tr>

					</table>
				</form>
				<div id="status">
					<table id="IP_tableTH" width="100%" align="center"
						color:#3b485f;="" line-height:1.6em;=""
						border-collapse:collapse;="" class="table-striped">
						<tbody>
							<tr	style="background: #007E9C; height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">

								<th scope="col">Department Name</th>
								<th scope="col">Doctor Name</th>

								<th scope="col">Token Group Name</th>
								<th scope="col">Total Token Issue</th>
								<th scope="col">Current Token</th>



							</tr>
							<%
								@SuppressWarnings("unchecked")
									ArrayList<TokenStatusBean> tokenStatus = (ArrayList<TokenStatusBean>) request
											.getAttribute("tokenStatus");

									if (tokenStatus.size() > 0) {

										for (TokenStatusBean listToken : tokenStatus) {
							%>
							<tr
								style="height: 25px; font-size: 14px; text-align: center"">

								<td><%=listToken.getDepartName()%></td>
								<td><%=listToken.getUser_id_to()%></td>
								<td><%=listToken.getToken_group_id_to()%></td>
								<td><%=listToken.getTotal_token_to()%></td>
								<td><%=listToken.getDisplay_token()%></td>

							</tr>

							<%
								}
									} else {
							%>




							<tr>

								<td></td>
								<td></td>

								<td align="center" style="color: #B20000;"><%="Token Status list is Blank !!"%></td>
							</tr>





							<%
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