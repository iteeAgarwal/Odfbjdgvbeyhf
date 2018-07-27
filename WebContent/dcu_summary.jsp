<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="com.dan.dqms.dcu.DocSummaryBean"%>
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
<title>Doctor Summary</title>
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

					<b>Doctor Summary</b>

				</TH>
			</TABLE>


			<div id="cont_shadow">


				<div id="shutdown">
					
						<table id="IP_tableTH" width="100%" align="center"
							color:#3b485f;="" line-height:1.6em;=""
							border-collapse:collapse;="">
							<tbody>
								<tr
									style="background: #007E9C; height: 40px; font-size: 14px; font-family: Arial, Helvetica, sans-serif;">



									<th style="color: #fff; text-align: center; font-weight: bold;"
										scope="col">Login Date/Time</th>
									<th style="color: #fff; text-align: center; font-weight: bold;"
										scope="col">Total Patients</th>


									<th style="color: #fff; text-align: center; font-weight: bold;"
										scope="col">Patient Treated</th>

									<th style="color: #fff; text-align: center; font-weight: bold;"
										scope="col">Patient Skipped</th>

									<th style="color: #fff; text-align: center; font-weight: bold;"
										scope="col">Patient Cancelled</th>





								</tr>

								<%
									@SuppressWarnings("unchecked")
										DocSummaryBean docSummaryBean = (DocSummaryBean) request
												.getAttribute("docSummaryBean");
								%>
								<tr
									style="background: #EFEFEF; height: 25px; font-size: 14px; text-align: center"">


									<td><%=docSummaryBean.getDateTime()%></td>
									<td><%=docSummaryBean.getTotalPatients()%></td>

									<td><%=docSummaryBean.getTotalTreat()%></td>

									<td><%=docSummaryBean.getTotalSkip()%></td>

									<td><%=docSummaryBean.getTotalCancel()%></td>


								</tr>





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