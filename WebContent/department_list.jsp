<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.GroupMDU"%>

<%@ page import="org.dqms.db.Department"%>


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
<title>Department</title>
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
<link rel="icon" href="./images_dqms/title.gif" type="image/gif" />
<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />
<script>
	function generateNew() {

		window.location.assign("./DepartmentListServlet");

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
<script type="text/javascript">
 function rowSelecter(id) {
	 document.getElementById(id).checked = true;
 }
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

					<b>Department Settings</b>

				</TH>
			</TABLE>


			<div id="cont_shadow">


				<%
					String viewBtn = request.getParameter("roomView");

						String newBtn = request.getParameter("roomNew");

						String EditBtn = request.getParameter("roomEdit");

						String delBtn = request.getParameter("roomdelete");

						if (viewBtn != null) {
				%>
				<!--===================================View room number==========================================-->

				<form name="doctorForm" method="post" action="#">
					<div id="doc_form_admin">
						<p id="span_text1">Department view</p>

						<table id="doc_td_label_admin">


							<%
								@SuppressWarnings("unchecked")
										List<Department> list = (ArrayList<Department>) request
												.getAttribute("toGroupList");

										if (list.size() > 0) {

											for (Department listrooms : list) {
							%>


							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>MDU ID:</td>
								<td><input type="text" name="deptID1"
									value="<%=listrooms.getDepart_id()%>" id="doc_td_value"
									disabled></td>
							</tr>

							<tr>
								<td>MDU Name:</td>
								<td><input type="text" name="deptName"
									value="<%=listrooms.getDepart_name()%>" disabled
									id="doc_td_value"></td>
							</tr>




							<tr>
								<td></td>
								<td></td>
							</tr>

							<tr>


								<td><input type="button" name="deptcancel" value="cancel"
									id="formButton" onclick="generateNew()"></td>
							</tr>





							<%
								}
										} else {
							%>
							<tr align="center">

								<td id="response_mess"><%="Department list not founds !!"%></td>


							</tr>


							<%
								response.sendRedirect("./DepartmentListServlet");
										}
							%>
						</table>
					</div>

				</form>

				<!--===================================END==========================================-->
				<%
					} else if (newBtn != null) {
				%>


				<!--===================================Add new room number==========================================-->

				<form name="newRoomForm" method="post" action="./DepartmentInsert">
					<div id="doc_form_admin">
						<p id="span_text1">Department Insert</p>

						<table id="doc_td_label_admin">

							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>Department ID:</td>
								<td><input type="text" name="departmentID" value=""
									id="doc_td_value" placeholder="Auto Generated" disabled></td>
							</tr>

							<tr>
								<td>Department Name:</td>
								<td><input type="text" name="departmentName" value=""
									required id="doc_td_value"></td>
							</tr>

							<tr>
								<td></td>
								<td></td>
							</tr>

							<tr>

								<td><input type="submit" name="deptSave" value="Save"
									id="formButton"></td>
								<td><input type="button" name="deptcancel" value="cancel"
									id="formButton" onclick="generateNew()"></td>
							</tr>

						</table>





					</div>
				</form>

				<!--===================================END==========================================-->
				<%
					} else if (EditBtn != null) {
				%>

				<!--===================================Edit room number==========================================-->
				<form name="newRoomForm" method="post" action="./DepartmentInsert">
					<div id="doc_form_admin">
						<p id="span_text1">Department Edit</p>

						<table id="doc_td_label_admin">

						<%
							@SuppressWarnings("unchecked")
									List<Department> list = (ArrayList<Department>) request
											.getAttribute("toGroupList");

									if (list.size() > 0) {

										for (int i = 0; i < list.size(); i++) {
						%>

						
							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>Department ID:</td>
								<td><input type="text" name="departmentID"
									value="<%=list.get(0).getDepart_id()%>" id="doc_td_value"
									readonly="readonly"></td>
							</tr>

							<tr>
								<td>Department Number:</td>
								<td><input type="text" name="departmentName"
									value="<%=list.get(0).getDepart_name()%>" required
									id="doc_td_value"></td>
							</tr>








							<tr>
								<td></td>
								<td></td>
							</tr>

							<tr>


								<td><input type="submit" name="deptEdit" value="Save"
									id="formButton"></td>
								<td><input type="button" name="deptcancel" value="cancel"
									id="formButton" onclick="generateNew()"></td>
							</tr>

						</table>




						<%
							}
									} else {
						%>
						<tr align="center">

							<td id="response_mess"><%="MDU Group list not founds !!"%></td>


						</tr>


						<%
							response.sendRedirect("./DepartmentListServlet");
									}
						%>







					</div>
				</form>


				<!--===================================END==========================================-->

				<%
					} else {
				%>
				<div id="shutdown">
					<form name="roomForm" method="post"
						action="./DepartmentListServlet">

						<table id="IP_tableTH" width="100%" align="center"
							color:#3b485f;="" line-height:1.6em;=""
							border-collapse:collapse;="" class="table-striped">
							<tbody>
								<tr	style="height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">

									<th scope="col">select</th>
									<th scope="col">Department ID</th>
									<th scope="col">Department Name</th>



								</tr>
								<%
									@SuppressWarnings("unchecked")
											List<Department> list = (ArrayList<Department>) request
													.getAttribute("toGroupList");

											if (list.size() > 0) {
												int j=0;
												for (int i = list.size() - 1; i >= 0; i--) {
													j++;								
								%>		
								<tr id="<%=j%>"
									style="height: 25px; font-size: 14px; text-align: center"" onclick="rowSelecter(this.id);">

									<td><input type="radio" name="radioroomID" checked=""
										value="<%=list.get(i).getDepart_id()%>" id="ra<%=j%>" name="r"></td>

									<td><%=list.get(i).getDepart_id()%></td>

									<td><%=list.get(i).getDepart_name()%></td>

								</tr>

								<%
									}
											} else {
								%>

							</tbody>
						</table>
						<table align="center">
							<tr>

								<td id="response_mess"><%="Department list not founds !!"%></td>


							</tr>
						</table>



						<%
							}
						%>






						<table align="center">

							<tr>
								<td><input type="submit" name="roomView" value="View"
									id="formButton"></td>
								<td><input type="submit" name="roomNew" value="New"
									id="formButton"></td>
								<td><input type="submit" name="roomEdit" value="Edit"
									id="formButton"></td>
								<!-- <td><input type="submit" name="roomdelete" value="Delete"
									id="formButton"></td> -->
								<td>
								<input type="submit" name="roomdelete" id="formButton" value="Delete" onclick="if (confirm('Are you sure you want to delete?')) { form.action='./DepartmentListServlet'; } else { return false; }" />
								</td>

							</tr>

						</table>
											<!-- <table align="center"> -->
						<%
							String msg = (String) session.getAttribute("msg");
									if (msg == null) {
										msg = "";
									}
						%>
						<!-- <tr> -->


							<!-- <td style="color: #B60000"> -->
							<%-- <%=msg%> --%>
							<%if(msg!=null && !msg.equals("")){
							%>
								<script>
								alert('<%=msg%>');
								</script>
							<%								
							} %>

							<!-- </td> -->
							<%session.setAttribute("msg", ""); %>
<!-- 						</tr>
					</table> -->


					</form>
				</div>

				<%
					}
				%>








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