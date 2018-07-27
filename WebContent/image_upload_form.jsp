<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.Department"%>
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

<title>Image Upload</title>

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

					<b> Advertisement Media Upload</b>

				</TH>
			</TABLE>





			<div id="doc_form_admin">
				<p id="span_text1">Image Upload</p>
				<form name="doctorForm" method="post" action="./FullImageUpload"
					enctype="multipart/form-data">
					<table id="doc_td_label_admin">

						<tr>
							<td>Image Name:</td>

							<td><input type="text" name="imageName" value=""   required/></td>
						</tr>

						<tr>
							<td id="upload_text">Image/Video Upload:</td>

							<td><input type="file" name="file" id="browse_btn" /></td>
						</tr>




					</table>

					<hr>
					<table id="doc_td_label_admin" align="left">
						<tr>
							<td>Select Department</td>

						</tr>

						<%
							@SuppressWarnings("unchecked")
								List<Department> deptList = (ArrayList<Department>) request
										.getAttribute("deptList");

								

								if (deptList.size() > 0) {

									for (Department listdept : deptList) {
						%>
						<tr>
							<td><input type="checkbox" name="deptCheckbox"
								value="<%=listdept.getDepart_id()%>" /> <label><%=listdept.getDepart_name()%></label>

							</td>
						</tr>
						<%
							}
								}
						%>




					</table>
					<table id="doc_td_label_admin" align="center">
						<tr>


							<td>Select WDU/MDU</td>
						</tr>


						<tr>
							<td><input type="radio" name="deviceCheckbox" value="1"    required/>
								<label>MDU</label></td>

						</tr>
						<tr>

							<td><input type="radio" name="deviceCheckbox" value="2" required />
								<label>WDU</label></td>
						</tr>




					</table>
					<table align="center">


						<tr>

							<td></td>
						</tr>
						
						<tr>

							<td></td>
						</tr>



					</table>

					<table align="center">


						<tr>

							<td><input type="submit" name="image_upload" value="Upload"
								id="formButton"></td>
						</tr>
						
						<tr>

							<td></td>
						</tr>



					</table>
					
					<table align="center">
					<tr>
					<td>
					<%-- Using JSP EL to get message attribute value from request scope --%>
					<!--  	<p id="response_mess">${requestScope.message}</p> -->
				<% 
				if(request.getAttribute("message")!=null){ out.println("<SCRIPT>alert('"+request.getAttribute("message")+"')</SCRIPT>");  
				}
				%>
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