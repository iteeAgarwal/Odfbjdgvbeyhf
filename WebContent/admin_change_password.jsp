<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>
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

<title>Change Password</title>

<link rel="icon" href="./images_dqms/title.gif" type="image/gif" />
<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />

<script>
	function generateNew() {

		window.location.assign("./NewUserList");

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

					<b> Admin </b>

				</TH>
			</TABLE>





			<div id="doc_form_admin">
				<p id="span_text1">Change Password</p>
				<form name="doctorForm" method="post" action="./ChangePassword">
					<table id="doc_td_label_admin">


						<tr>
							<td>Old Password:</td>
							<td><input type="password" name="oldPass" value=""
								id="doc_td_value" required></td>
						</tr>

						<tr>
							<td>New Password:</td>
							<td><input type="password" name="newPass" value=""
								id="doc_td_value" required></td>
						</tr>

						<tr>
							<td>Retype Password:</td>
							<td><input type="password" name="rePass" value=""
								id="doc_td_value" required></td>
						</tr>






						<tr>
							<td></td>
						</tr>
						<tr>
							<td><input type="submit" name="chapassave" value="Save"
								id="formButton"></td>

							<td><input type="button" name="userDetails" value="Cancel"
								id="formButton" onclick="generateNew()"></td>
						</tr>
					</table>

					<table align="center">
						<tr>
							<td style="color: #B60000">
								<%
									String responseMess = (String) request
												.getAttribute("responseMess");
										if (responseMess == null) {
											responseMess = "";
										}
								%> <%=responseMess%>

							</td>
						</tr>

					</table>
				</form>

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