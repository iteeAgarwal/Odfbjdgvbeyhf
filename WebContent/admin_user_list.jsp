<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.Room"%>

<%@ page import="org.dqms.db.Roles"%>

<%@ page import="org.dqms.db.User"%>

<%@ page import="org.dqms.db.Department"%>

<%@ page import="java.util.Date"%>

<%@ page import="java.text.SimpleDateFormat"%>


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

<title>Users</title>
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

<script src="js_dqms/token_generate.js" type="text/javascript"></script>
<script src="js_dqms/jquery.min.js" type="text/javascript"></script>

<script>
$(document).ready(function(){
	$('#IP_tableTH').on('click', 'tbody tr', function(event) {
	    $(this).addClass('highlight').siblings().removeClass('highlight');
	});
});
</script>

<script>
	function generateNew() {

		window.location.assign("./NewUserList");

	}

	function onclected() {

		var userRole_id = document.getElementById("userRole_id").value;

		if (userRole_id == 1) {
			document.getElementById("deptID_id").required = false;
			
			document.getElementById("room_id").required = false;

			document.getElementById("viewID").required = false;

		} else if (userRole_id == 2) {

			document.getElementById("deptID_id").required = true;
			
			document.getElementById("room_id").required = true;

			document.getElementById("viewID").required = true;

		} else if (userRole_id == 3) {
			document.getElementById("deptID_id").required = false;
			
			document.getElementById("room_id").required = false;

			document.getElementById("viewID").required = false;

		} else if (userRole_id == 4) {
			document.getElementById("deptID_id").required = false;
			
			document.getElementById("room_id").required = false;

			document.getElementById("viewID").required = false;

		} else if (userRole_id == 5) {
			document.getElementById("deptID_id").required = false;
			
			document.getElementById("room_id").required = false;

			document.getElementById("viewID").required = false;

		}

	}

	</script>
<script type="text/javascript">
 function rowSelecter(id) {
	 document.getElementById("ra"+id).checked = true;
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
					style="background-color: #007E9C; height: 65px; color: #FFFFFF; font-weight: normal; font-size: 16px; font-family: Arial, Helvetica, sans-serif;">

					<b>Users</b>

				</TH>
			</TABLE>





			<%
				String viewBtn = request.getParameter("roomView");

					String newBtn = request.getParameter("roomNew");

					String EditBtn = request.getParameter("roomEdit");

					String delBtn = request.getParameter("roomdelete");

					if (viewBtn != null) {
			%>
			<!--===================================View room number==========================================-->


			<form name="viewuserForm" method="post" action="#">
				<div id="doc_form_admin">
					<p id="span_text1">User View</p>

					<%
						String userRoleName = "";

								String departName = "";

								String roomNum = "";

								@SuppressWarnings("unchecked")
								List<User> list = (ArrayList<User>) request
										.getAttribute("userList");

								@SuppressWarnings("unchecked")
								List<Department> deptList = (ArrayList<Department>) request
										.getAttribute("deptList");

								@SuppressWarnings("unchecked")
								List<Room> roomsList = (ArrayList<Room>) request
										.getAttribute("roomsList");

								int view = list.get(0).getView1();

								if (deptList.size() > 0 && list.size() > 0) {
									for (Department deptOb : deptList) {
										if (deptOb.getDepart_id() == list.get(0)
												.getDepart_id()) {
											departName = deptOb.getDepart_name();
										}
									}
								}

								if (roomsList.size() > 0 && list.size() > 0) {
									for (Room roomOb : roomsList) {
										if (roomOb.getRoom_id() == list.get(0).room_id) {
											roomNum = roomOb.getRoom_no();
										}
									}
								}

								if (list.size() > 0) {

									for (User listusers : list) {

										if (listusers.getRole_id() == 1) {

											userRoleName = "Super User";

										} else if (listusers.getRole_id() == 2) {
											userRoleName = "doctor";

										} else if (listusers.getRole_id() == 3) {
											userRoleName = "Token Issuer";
										} else if (listusers.getRole_id() == 4) {
											userRoleName = "Admin";

										} else if (listusers.getRole_id() == 5) {
											userRoleName = "HOD";

										}
					%>

					<table id="doc_td_label_admin">
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>Select Role:</td>
							<td><input type="text" name="roomID"
								value="<%=userRoleName%>" id="doc_td_value" readonly="readonly"></td>
						</tr>

						<tr>
							<td>Unique ID:</td>
							<td><input type="text" name="userID"
								value="<%=listusers.getUser_id()%>" id="doc_td_value"
								readonly="readonly"></td>
						</tr>
							<tr>
							<td>Doctor ID:</td>
							<td><input type="text" name="DocID"
								value="<%=listusers.getDoc_id()%>" id="doc_td_value"
								readonly="readonly"></td>
						</tr>

						<tr>
							<td>Name:</td>
							<td><input type="text" name="userName"
								value="<%=listusers.getName()%>" readonly="readonly"
								id="doc_td_value"></td>
						</tr>

						<tr>
							<td>Phone Number:</td>
							<td><input type="text" name="userPhNo"
								value="<%=listusers.getPhone_no()%>" readonly="readonly"
								id="doc_td_value"></td>
						</tr>




						<tr>
							<td>Email ID:</td>
							<td><input type="email" name="userEmail"
								value="<%=listusers.getEmail_id()%>" id="doc_td_value"
								readonly="readonly"></td>
						</tr>
						<tr>
							<td>User Name:</td>
							<td><input type="text" name="uName"
								value="<%=listusers.getUsername()%>" readonly="readonly"
								id="doc_td_value"></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input type="text" name="uPass"
								value="<%=listusers.getPassword()%>" readonly="readonly"
								id="doc_td_value"></td>
						</tr>
						<tr>
							<td>Designation:</td>
							<td><input type="text" name="desig"
								value="<%=listusers.getDesignation()%>" id="doc_td_value"
								readonly="readonly"></td>
						</tr>

						<tr>
							<td>Department:</td>
							<td><input type="text" name="roomNo" value="<%=departName%>"
								readonly="readonly" id="doc_td_value"></td>
						</tr>
						<tr>
							<td>Room No:</td>
							<td><input type="text" name="roomNo" value="<%=roomNum%>"
								readonly="readonly" id="doc_td_value"></td>
						</tr>
						<tr>


							<%
								String checked = "";

												String checked1 = "";

												if (view == 1) {
													checked = "checked";
												} else if (view == 2) {
													checked1 = "checked";
												}

												else {
													checked = "unchecked";

													checked1 = "unchecked";
												}
							%>


							<td>View:</td>
							<td align="center"><input type="radio" name="view" value="1"
								id="viewID" <%=checked + "=''"%>>Summary
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="radio" name="view"
								value="2" id="dcu" <%=checked1 + "=''"%>>DCU</td>
						</tr>

						<tr>


							<td><input type="button" name="userDetails" value="Cancel"
								id="formButton" onclick="generateNew()"></td>
						</tr>

					</table>




					<%
						}
								} else {
					%>

					<table align="center">
						<tr>

							<td id="response_mess"><%="Users list not founds !!"%></td>


						</tr>
					</table>

					<%
						response.sendRedirect("./NewUserList");
								}
					%>







				</div>
			</form>

			<!--===================================END==========================================-->
			<%
				} else if (newBtn != null) {
			%>


			<!--===================================Add new room number==========================================-->
			<div id="doc_form_admin">
				<p id="span_text1">User Admin Insert Form</p>
				<form  name="drop_list" name="doctorForm" method="post" action="./NewUserInsert">
					<table id="doc_td_label_admin">

						<tr>
							<td>Unique ID:</td>
							<td><input type="text" name="userID" value=""
								class="doc_td_value_class" id="userID_id"
								placeholder="Auto Generated" readonly="readonly"></td>
						</tr>
						<tr>
							<td>Doctor ID:</td>
							<td><input type="text" name="DocID" value=""
								class="doc_td_value_class" id="userID_id"></td>
						</tr>


						<tr>
							<td>Select Role:</td>




							<td><select class="dropdown_conn_class" id="userRole_id"
								name="userRole" required onchange="onclected();">


									<option value="" selected disabled style="display: none;">Select User Role</option>

									<%
										@SuppressWarnings("unchecked")
												List<Roles> roleList = (ArrayList<Roles>) request
														.getAttribute("roleList");

												String options = request.getParameter("userRole");

												if (roleList.size() > 0) {

													for (Roles listrole : roleList) {
														if (options != null) {
															if (Integer.parseInt(options) == listrole.getRole_id()) {
									%>
									<option value="<%=listrole.getRole_id()%>" selected="selected"><%=listrole.getRole_name()%></option>

									<%
										} else {
									%>

									<option value="<%=listrole.getRole_id()%>"><%=listrole.getRole_name()%></option>

									<%
										}

														} else {
															//else add it as it is.
									%>
									<option value="<%=listrole.getRole_id()%>"><%=listrole.getRole_name()%></option>
									<%
										}
													}
												}
									%>



							</select></td>




						</tr>

						<tr>
							<td>Name:</td>
							<td><input type="text" name="userName" value="" required
								id="userName_id" class="doc_td_value_class"></td>
						</tr>

						<tr>
							<td>Phone Number:</td>
							<td><input type="text" name="userPhNo" value="" 
								id="userPhNo_id" class="doc_td_value_class"></td>
						</tr>




						<tr>
							<td>Email ID:</td>
							<td><input type="email" name="userEmail" value=""
								id="userEmail_id" class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>User Name:</td>
							<td><input type="text" name="uName" value="" required
								id="uName_id" class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input type="password" name="uPass" value="" required
								id="uPass_id" class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Designation:</td>
							<td><input type="text" name="desig" value="" id="desig_id"
								class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Department:</td>
							<td><select class="dropdown_conn_class" id="deptID_id"
								name="depart"  onchange="GetOptionsStatic1()"  >
									<option value="" selected disabled style="display: none;">select department</option>

									<%
										@SuppressWarnings("unchecked")
												List<Department> deptList = (ArrayList<Department>) request
														.getAttribute("deptList");

												if (deptList.size() > 0) {

													for (Department listdept : deptList) {
									%>

									<option value="<%=listdept.getDepart_id()%>"><%=listdept.getDepart_name()%></option>

									<%
										}
												}
									%>
							</select></td>
						</tr>

						<tr>
							<td>Room No:</td>
							<td><select class="dropdown_conn_class" id="room_id"
								name="rooms_list">
									<option value="" selected disabled style="display: none;">select room</option>

									<%
										@SuppressWarnings("unchecked")
												List<Room> roomsList = (ArrayList<Room>) request
														.getAttribute("roomsList");

												if (roomsList.size() > 0) {

													for (Room listdept : roomsList) {
									%>

									<option value="<%=listdept.getRoom_id()%>"><%=listdept.getRoom_no()%></option>

									<%
										}
												}
									%>
							</select></td>
						</tr>

						<tr>
							<td>View:</td>
							<td align="center"><input type="radio" name="view" value="1"
								id="viewID">Summary &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input
								type="radio" name="view" value="2" id="viewID">DCU</td>
						</tr>




						<tr>
							<td><input type="submit" name="userDetails" value="Save"
								id="formButton"></td>

							<td><input type="button" name="userDetails" value="Cancel"
								id="formButton" onclick="generateNew()"></td>
						</tr>
					</table>
				</form>
			</div>
			<!--===================================END==========================================-->
			<%
				} else if (EditBtn != null) {
			%>

			<!--===================================Edit room number==========================================-->
			<form name="drop_list" method="post" action="./NewUserInsert">
				<div id="doc_form_admin">
					<p id="span_text1">User Admin Edit Form</p>



					<%
						@SuppressWarnings("unchecked")
								List<User> listusers = (ArrayList<User>) request
										.getAttribute("userList");

								if (listusers.size() > 0) {

									for (int i = 0; i < listusers.size(); i++) {
					%>

					<table id="doc_td_label_admin">
						<tr>
							<td></td>
							<td></td>
						</tr>
						<tr>
							<td>Select Role:</td>
							<td><select class="dropdown_conn_class" id="userRole_id"
								name="userRole" required onchange="onclected()">




									<%
										if (listusers.get(0).role_id == 1) {
									%>
									<option value="1" selected="selected">Super User</option>
									<option value="2">doctor</option>
									<option value="3">Token Issuer</option>
									<option value="4">Admin</option>
									<option value="5">HOD</option>
									<%
										} else if (listusers.get(0).role_id == 2) {
									%>


									<option value="1">Super User</option>
									<option value="2" selected="selected">doctor</option>
									<option value="3">Token Issuer</option>
									<option value="4">Admin</option>
									<option value="5">HOD</option>
									<%
										} else if (listusers.get(0).role_id == 3) {
									%>


									<option value="1">Super User</option>
									<option value="2">doctor</option>
									<option value="3" selected="selected">Token Issuer</option>
									<option value="4">Admin</option>
									<option value="5">HOD</option>
									<%
										} else if (listusers.get(0).role_id == 4) {
									%>


									<option value="1">Super User</option>
									<option value="2">doctor</option>
									<option value="3">Token Issuer</option>
									<option value="4" selected="selected">Admin</option>
									<option value="5">HOD</option>
									<%
										} else if (listusers.get(0).role_id == 5) {
									%>


									<option value="1">Super User</option>
									<option value="2">doctor</option>
									<option value="3">Token Issuer</option>
									<option value="4">Admin</option>
									<option value="5" selected="selected">HOD</option>
									<%
										} else {
									%>
									<option value="0" selected disabled style="display: none;">Select User Role</option>
									<option value="1">Super User</option>
									<option value="2">doctor</option>
									<option value="3">Token Issuer</option>
									<option value="4">Admin</option>
									<option value="5">HOD</option>
									<%
										}
									%>



							</select></td>
						</tr>

						<tr>
							<td>Unique ID:</td>
							<td><input type="text" name="userID"
								value="<%=listusers.get(0).getUser_id()%>" id="doc_td_value"
								readonly="readonly"></td>
						</tr>
						
						<tr>
							<td>Doctor ID:</td>
							<td><input type="text" name="DocID"
								value="<%=listusers.get(0).getDoc_id()%>" id="doc_td_value"></td>
						</tr>

						<tr>
							<td>Name:</td>
							<td><input type="text" name="userName"
								value="<%=listusers.get(0).getName()%>" id="doc_td_value"></td>
						</tr>

						<tr>
							<td>Phone Number:</td>
							<td><input type="text" name="userPhNo"
								value="<%=listusers.get(0).getPhone_no()%>" id="doc_td_value"></td>
						</tr>




						<tr>
							<td>Email ID:</td>
							<td><input type="email" name="userEmail"
								value="<%=listusers.get(0).getEmail_id()%>" id="doc_td_value"></td>
						</tr>
						<tr>
							<td>User Name:</td>
							<td><input type="text" name="uName"
								value="<%=listusers.get(0).getUsername()%>" id="doc_td_value"></td>
						</tr>
						<tr>
							<td>Password:</td>
							<td><input type="password" name="uPass"
								value="<%=listusers.get(0).getPassword()%>" id="doc_td_value"></td>
						</tr>
						<tr>
							<td>Designation:</td>
							<td><input type="text" name="desig"
								value="<%=listusers.get(0).getDesignation()%>" id="doc_td_value"></td>
						</tr>

						<tr>
							<td>Department:</td>
							<td><select class="dropdown_conn_class" id="deptID_id"
								name="depart"  onchange="GetOptionsStatic1()" >
									<option value="" selected disabled style="display: none;">select department</option>

									<%
										@SuppressWarnings("unchecked")
														List<Department> deptList = (ArrayList<Department>) request
																.getAttribute("deptList");

														if (deptList.size() > 0) {

															for (Department listdept : deptList) {

																if (listusers.get(0).getDepart_id() == listdept
																		.getDepart_id()) {
									%>
									<option value="<%=listdept.getDepart_id()%>"
										selected="selected"><%=listdept.getDepart_name()%></option>

									<%
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
						</tr>
						<tr>
							<td>Room No:</td>
							<td><select class="dropdown_conn_class" id="room_id"
								name="rooms_list">
									<option value="" selected disabled style="display: none;">select room</option>

									<%
										@SuppressWarnings("unchecked")
														List<Room> roomsList = (ArrayList<Room>) request
																.getAttribute("roomsList");

														if (roomsList.size() > 0) {

															for (Room listdept : roomsList) {
									%>



									<%
										if (listusers.get(0).getRoom_id() == listdept
																		.getRoom_id()) {
									%>

									<option value="<%=listdept.getRoom_id()%>" selected="selected"><%=listdept.getRoom_no()%></option>

									<%
										} else {
																	//else add it as it is.
									%>
									<option value="<%=listdept.getRoom_id()%>"><%=listdept.getRoom_no()%></option>
									<%
										}
															}
														}
									%>


							</select></td>
						</tr>
						<tr>


							<%
								int view = listusers.get(0).getView1();

												String checked = "";

												String checked1 = "";

												if (view == 1) {
													checked = "checked";
												} else if (view == 2) {
													checked1 = "checked";
												}

												else {
													checked = "unchecked";

													checked1 = "unchecked";
												}
							%>


							<td>View:</td>
							<td align="center"><input type="radio" name="view" value="1"
								id="viewID" <%=checked + "=''"%>>Summary
								&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp<input type="radio" name="view"
								value="2" id="dcu" <%=checked1 + "=''"%>>DCU</td>
						</tr>

						<tr>

							<td><input type="submit" name="userEdit" value="Save"
								id="formButton"></td>

							<td><input type="button" name="userDetails" value="Cancel"
								id="formButton" onclick="generateNew()"></td>
						</tr>






						<%
							}
									} else {
						%>
						<tr align="center">



							<td id="response_mess"><%="Users list not founds !!"%></td>


						</tr>


						<%
							response.sendRedirect("./NewUserList");
									}
						%>
					</table>


				</div>

			</form>


			<!--===================================END==========================================-->

			<%
				} else {
			%>
			<div id="shutdown">
				<form name="roomForm" method="post" action="./NewUserList">

					<table id="IP_tableTH" width="100%" align="center"
						color:#3b485f;="" line-height:1.6em;=""
						border-collapse:collapse;="" class="table-striped">
						<tbody>
							<tr	style="height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">

								<th scope="col">Select</th>

								<th scope="col">User ID</th>

								<th scope="col">Role ID</th>
								<th scope="col">Name</th>
								<th scope="col">Phone No</th>
								<th scope="col">Email ID</th>
								<th scope="col">User Name</th>
								
								<th scope="col">Designation</th>
								<th scope="col">Department</th>
								<th scope="col">Room</th>
								<th scope="col">Last Update On</th>
								<th scope="col">Created On</th>
								<th scope="col">View</th>
							</tr>
							<%
								@SuppressWarnings("unchecked")
										List<User> list = (ArrayList<User>) request
												.getAttribute("userList");

										if (list.size() > 0) {
												int j=0;
											for (int i = list.size() - 1; i >= 0; i--) {
												j++;
												Date updatedDate = new Date(list.get(i)
														.getLast_updated_on() * 1000);

												Date createDate = new Date(list.get(i)
														.getCreation_date() * 1000);

												SimpleDateFormat sdf = new SimpleDateFormat(
														"dd/MM/yyyy HH:mm:ss");

												String Last_updated = sdf.format(updatedDate);

												String Last_create_time = sdf.format(createDate);
							%>
							<tr name="t1" id="<%=j%>" 
								style="height: 25px; font-weight: normal; font-size: 14px; font-family: Arial, Helvetica, sans-serif; text-align: center"" onclick="rowSelecter(this.id);">
							
							
							

								<td><input type="radio" name="radioroomID" checked=""
									value="<%=list.get(i).getUser_id()%>" id="ra<%=j%>" name="r"></td>

								<%
									String userRoleName = "";
													if (list.get(i).role_id == 1) {
														userRoleName = "Super User";

													} else if (list.get(i).role_id == 2) {
														userRoleName = "doctor";
													} else if (list.get(i).role_id == 3) {
														userRoleName = "Token Issuer";
													} else if (list.get(i).role_id == 4) {
														userRoleName = "Admin";
													} else if (list.get(i).role_id == 5) {
														userRoleName = "HOD";
													}
								%>

								<td><%=list.get(i).getUser_id()%></td>
								<td><%=userRoleName%></td>
								<td><%=list.get(i).getName()%></td>
								<td><%=list.get(i).getPhone_no()%></td>
								<td><%=list.get(i).getEmail_id()%></td>

								<td><%=list.get(i).getUsername()%></td>
								
								<td><%=list.get(i).getDesignation()%></td>


								<%
									String deptName = "";
													@SuppressWarnings("unchecked")
													List<Department> deptList = (ArrayList<Department>) request
															.getAttribute("deptList");

													if (deptList.size() > 0) {

														for (Department listdept : deptList) {

															if (list.get(i).getDepart_id() == listdept
																	.getDepart_id()) {
																deptName = listdept.getDepart_name();
															}

														}
													}
								%>

								<td><%=deptName%></td>

								<%
									String roomNum = "";
													@SuppressWarnings("unchecked")
													List<Room> roomsList = (ArrayList<Room>) request
															.getAttribute("roomsList");

													if (roomsList.size() > 0) {

														for (Room listdept : roomsList) {

															if (list.get(i).getRoom_id() == listdept
																	.getRoom_id()) {
																roomNum = listdept.getRoom_no();
															}

														}
													}
								%>


								<td><%=roomNum%></td>

								<td><%=Last_updated%></td>
								<td><%=Last_create_time%></td>
								<%
									String viewStr = "";

													int view = list.get(i).getView1();

													if (view == 1) {
														viewStr = "summary";
													} else if (view == 2) {
														viewStr = "DCU";
													} else {
														viewStr = "";
													}
								%>

								<td><%=viewStr%></td>





							</tr>




							<%
								}
										} else {
							%>



							<tr>

								<td id="response_mess"><%="users list not founds !!"%></td>


							</tr>


							<%
								}
							%>






						</tbody>
					</table>
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
								<input type="submit" name="roomdelete" id="formButton" value="Delete" onclick="if (confirm('Are you sure you want to delete?')) { form.action='./NewUserList'; } else { return false; }" />
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