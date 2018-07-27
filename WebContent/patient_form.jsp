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

<title>Patient Form</title>
<link rel="icon" href="./images_dqms/title.gif" type="image/gif" />
<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />
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

					<b> Patient Form </b>

				</TH>
			</TABLE>




			<div id="cont_shadow">
				<div id="doc_form">
					<form name="doctorForm" method="post" action="./PatientForm">
						<table id="doc_td_label">
							<tr>
								<td>Patient ID:</td>
								<td><input type="text" name="ptID" value=""
									id="doc_td_value" placeholder="Auto Generated" disabled></td>
							</tr>

							<tr>
								<td>Patient Name:</td>
								<td><input type="text" name="ptName" value="" required
									id="doc_td_value"></td>
							</tr>

							<tr>
								<td>ID Card No:</td>
								<td><input type="text" name="idCardNo" value="" required
									id="doc_td_value"></td>
							</tr>

							<tr>
								<td>Doctor ID:</td>
								<td><input type="number" name="docID" min="1" step="1"
									value="" required id="doc_td_value"></td>
							</tr>




							<tr>
								<td>Phone Number:</td>
								<td><input type="number" name="phNo" min="1" step="1"
									value="" required id="doc_td_value"></td>
							</tr>
							<tr>
								<td>Department ID:</td>
								<td><input type="number" name="deptID" min="1" step="1"
									value="" required id="doc_td_value"></td>
							</tr>
							<tr>
								<td>Room Number:</td>
								<td><input type="number" min="1" step="1" name="roomNo"
									value="" required id="doc_td_value"></td>
							</tr>
							<tr>
								<td>RFID:</td>
								<td><input type="text" name="rFid" value="" required
									id="doc_td_value"></td>
							</tr>
							<tr>
								<td>Insurance</td>
								<td align="center"><input type="checkbox" name="insur"
									value="1"></td>
							</tr>


							<tr>
								<td></td>
							</tr>
							<tr>
								<td><input type="submit" name="doctorDetails" value="Save"
									id="formButton"></td>

								<td><input type="submit" name="doctorDetails"
									value="Cancel" id="formButton"></td>
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