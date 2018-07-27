<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.Patient"%>

<%@ page import="org.dqms.db.Department"%>

<%@ page import="java.util.Date"%>

<%@ page import="java.text.SimpleDateFormat"%>


<%@ page import="org.dqms.db.Room"%>

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




<meta content="text/html; charset=UTF-8" http-equiv="content-type">
<meta content="no-cache" http-equiv="cache-control">
<meta content="no-cache" http-equiv="pragma">
<meta content="0" http-equiv="expires">
<meta content="Copyright© DAN Electronic System Pvt Ltd." name="copyright">
<meta content="none" name="robots">



<title>Patient</title>
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
<script src="js_dqms/sorttable.js?seq=6" type="text/javascript"></script>

<script>
	function generateNew() {

		window.location.assign("./PatientListServlet");

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
					style="background-color: #007E9C; height: 65px; color: #FFFFFF; font: normal 16px arial;">

					<b>Patient Information</b>

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
						<p id="span_text1">Patient View</p>

						<%
							@SuppressWarnings("unchecked")
									List<Patient> list = (ArrayList<Patient>) request
											.getAttribute("toGroupList");

									if (list.size() > 0) {

										for (Patient listpat : list) {
						%>

						<table id="doc_td_label_admin">
							<tr>
								<td>Patient ID:</td>
								<td><input type="text" name="ptID"
									value="<%=listpat.getPatient_id()%>" id="doc_td_value" disabled></td>
							</tr>

							<tr>
								<td>Patient Name:</td>
								<td><input type="text" name="ptName"
									value="<%=listpat.getPatient_name()%>" required
									id="doc_td_value"></td>
							</tr>

							<tr>
								<td>ID Card No:</td>
								<td><input type="text" name="idCardNo"
									value="<%=listpat.getId_card_no()%>" required id="doc_td_value"></td>
							</tr>

							<tr>
								<td>Doctor Name:</td>


								<%
									String docName = "";

													@SuppressWarnings("unchecked")
													List<User> userList = (ArrayList<User>) request
															.getAttribute("userList");

													if (userList.size() > 0) {

														for (User listdept : userList) {

															if (list.get(0).getDoctor_id() == listdept
																	.getUser_id()
																	&& listdept.getRole_id() == 2) {
																docName = listdept.getName();
															}

														}
													}
								%>
								<td><input type="text" name="docID" value="<%=docName%>"
									required id="doc_td_value"></td>
							</tr>




							<tr>
								<td>Phone Number:</td>
								<td><input type="text" name="phNo"
									value="<%=listpat.getPhone_no()%>" required id="doc_td_value"></td>
							</tr>
							<tr>
								<td>Department ID:</td>


								<%
									String deptName = "";
													@SuppressWarnings("unchecked")
													List<Department> deptList = (ArrayList<Department>) request
															.getAttribute("deptList");

													if (deptList.size() > 0) {

														for (Department listdept : deptList) {

															if (list.get(0).getDepart_id() == listdept
																	.getDepart_id()) {
																deptName = listdept.getDepart_name();
															}

														}
													}
								%>


								<td><input type="text" name="deptID" value="<%=deptName%>"
									required id="doc_td_value"></td>
							</tr>
							<tr>
								<td>Room Number:</td>

								<%
									String roomNum = "";
													@SuppressWarnings("unchecked")
													List<Room> roomsList = (ArrayList<Room>) request
															.getAttribute("roomsList");

													if (roomsList.size() > 0) {

														for (Room listdept : roomsList) {

															if (list.get(0).getRoom_id() == listdept
																	.getRoom_id()) {
																roomNum = listdept.getRoom_no();
															}

														}
													}
								%>
								<td><input type="text" name="roomNo" value="<%=roomNum%>"
									required id="doc_td_value"></td>
							</tr>
							<tr>
								<td>RFID:</td>
								<td><input type="text" name="rFid"
									value="<%=listpat.rfid_no%>" required id="doc_td_value"></td>
							</tr>

							<%
								String checked = "";

												boolean insur = listpat.isInsurance();

												if (insur) {
													checked = "checked";
												} else {
													checked = "unchecked";
												}
							%>
							<tr>
								<td>Insurance</td>
								<td align="center"><input type='checkbox' name="insur"
									VALUE="true" <%=checked + "=''"%> /></td>
							</tr>



							<tr>


								<td><input type="button" name="userDetails" value="Cancel"
									id="formButton" onclick="generateNew()"></td>
							</tr>



							<%
								}
										} else {
							%>
							<tr align="center">

								<td id="response_mess"><%="Patients  not founds !!"%></td>


							</tr>


							<%
								response.sendRedirect("./PatientListServlet");
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
				<form name="doctorForm" method="post" action="#">
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

							<td><input type="button" name="userDetails" value="Cancel"
									id="formButton" onclick="generateNew()"></td>
						</tr>
					</table>
				</form>
				<!--===================================END==========================================-->
				<%
					} else if (EditBtn != null) {
				%>

				<!--===================================Edit room number==========================================-->
				<form name="editpatient_Form" method="post" action="./PatientForm">
					<div id="doc_form_admin">
						<p id="span_text1">Patient Edit Form</p>


						<%
							@SuppressWarnings("unchecked")
									List<Patient> listusers = (ArrayList<Patient>) request
											.getAttribute("toGroupList");

									if (listusers.size() > 0) {

										for (int i = 0; i < listusers.size(); i++) {
						%>

						<table id="doc_td_label_admin">

							<tr>
								<td>Patient ID:</td>
								<td><input type="text" name="ptID"
									value="<%=listusers.get(0).getPatient_id()%>" id="doc_td_value"
									readonly="readonly"></td>
							</tr>

							<tr>
								<td>Patient Name:</td>
								<td><input type="text" name="ptName"
									value="<%=listusers.get(0).getPatient_name()%>"
									id="doc_td_value"></td>
							</tr>

							<tr>
								<td>Phone Number:</td>
								<td><input type="text" name="phNo"
									value="<%=listusers.get(0).getPhone_no()%>" id="doc_td_value"></td>
							</tr>




							<tr>
								<td>ID Card No:</td>
								<td><input type="text" name="idCardNo"
									value="<%=listusers.get(0).getId_card_no()%>" id="doc_td_value"></td>
							</tr>
							<tr>
								<td>RFID ID:</td>
								<td><input type="text" name="rFid"
									value="<%=listusers.get(0).getRfid_no()%>" id="doc_td_value"></td>
							</tr>


							<tr>
								<td>Department:</td>
								<td><select class="dropdown_conn_class" id="deptID"
									name="depart" required>
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
								<td><select class="dropdown_conn_class" id="roomNo_id"
									name="rooms_list">
										<option value="0" selected disabled style="display: none;">select room</option>

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
								<td>Doctor Name:</td>
								<td><select class="dropdown_conn_class" id="roomNo_id"
									name="doctor_list">
										<option value="0" selected disabled style="display: none;">select Doctor Name</option>

										<%
											@SuppressWarnings("unchecked")
															List<User> userList = (ArrayList<User>) request
																	.getAttribute("userList");

															if (userList.size() > 0) {

																for (User listdept : userList) {
										%>



										<%
											if (listusers.get(0).getDoctor_id() == listdept
																			.getUser_id()) {
										%>

										<option value="<%=listdept.getUser_id()%>"
											selected="selected"><%=listdept.getName()%></option>

										<%
											} else {
																		//else add it as it is.
										%>
										<option value="<%=listdept.getUser_id()%>"><%=listdept.getName()%></option>
										<%
											}
																}
															}
										%>


								</select></td>
							</tr>



							<tr>

								<td><input type="submit" name="patientEdit" value="Save"
									id="formButton"></td>

								<td><input type="button" name="userDetails" value="Cancel"
									id="formButton" onclick="generateNew()"></td>
							</tr>






							<%
								}
										} else {
							%>
							<tr align="center">



								<td id="response_mess"><%="patients list not founds !!"%></td>


							</tr>


							<%
								response.sendRedirect("./PatientListServlet");
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
					<form name="doctorForm" method="post" action="./PatientListServlet">
						<table id="IP_tableTH" width="100%" align="center"
							color:#3b485f;="" line-height:1.6em;=""
							border-collapse:collapse;="" class="table-striped">
							<tbody>
								<tr	style="background: #007E9C; height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">
									<th scope="col">Select</th>
									<th scope="col">Patient ID</th>

									<th scope="col">Patient Name</th>
									<th scope="col">RFID No</th>
									<th scope="col">ID Card No</th>
									<th scope="col">Phone NO</th>
									<th scope="col">Room No</th>
									<th scope="col">Doctor ID</th>
									<th scope="col">Department ID</th>
									<th scope="col">Last Check Time</th>



								</tr>

								<%!int nxt=0; %>
								<%
									@SuppressWarnings("unchecked")
											List<Patient> list = (ArrayList<Patient>) request
													.getAttribute("toGroupList");

											SimpleDateFormat sdf = new SimpleDateFormat(
													"dd/MM/yyyy HH:mm:ss");
										if(list!=null){
											if (list.size() > 0) {
												int j=0;
												
												for (int i = list.size() - 1; i >= 0; i--) {
													j++;
													String Last_updated = "";
													if(list.get(i).getLast_check_time() != 0 )
													{
														Date createDate = new Date(list.get(i)
																.getLast_check_time() * 1000L);

														 Last_updated = sdf.format(createDate);
													}
													else
													{
														Last_updated ="";
													}

													
								%>
								<tr id="<%=j%>" 
									style="height: 25px; font-size: 14px; text-align: center"" onclick="rowSelecter(this.id);">

									<td><input type="radio" name="radioroomID" checked=""
										value="<%=list.get(i).getPatient_id()%>"  id="ra<%=j%>" name="r" /></td>

									
									<td><%=list.get(i).getPatient_id()%></td>
									<td><%=list.get(i).getPatient_name()%></td>
									<td><%=list.get(i).getRfid_no()%></td>
									<td><%=list.get(i).getId_card_no()%></td>
									<td><%=list.get(i).getPhone_no()%></td>
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


									<%
										String docName = "";

														@SuppressWarnings("unchecked")
														List<User> userList = (ArrayList<User>) request
																.getAttribute("userList");

														if (userList.size() > 0) {

															for (User listdept : userList) {

																if (list.get(i).getDoctor_id() == listdept
																		.getUser_id()
																		&& listdept.getRole_id() == 2) {
																	docName = listdept.getName();
																}

															}
														}
									%>
									<td><%=docName%></td>

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
									<td><%=Last_updated%></td>


								</tr>
									
									



								<%
								
								if(j>=10){
									nxt=list.get(i).getPatient_id();
									%>
									<input type="hidden" name="nextVal" value="<%=nxt%>" />
									<%
									break;
								}
									}
											}//if ends 
											
											else {
								%>

							</tbody>
						</table>
						<table align="center">
							<tr>

								<td id="response_mess"><%="Patients list not founds !!"%></td>


							</tr>
						</table>



						<%
							}
										}
										else{
											%>
											
											<table align="center">
							<tr>

								<td id="response_mess"><%="Patients list not founds !!"%></td>


							</tr>
						</table>
											<%
										}
						%>






						<table align="center">

							<tr>
								<td><input type="submit" name="roomView" value="View"
									id="formButton"></td>

								<td><input type="submit" name="roomEdit" value="Edit"
									id="formButton"></td>
								<td><input type="submit" name="roomdelete" value="Delete"
									id="formButton"></td>
								<td><input type="submit" value="Next>>" name="cmdNxt" id="formButton"/></td>

							</tr>

						</table>
						<table align="center">
							<%
								String msg = (String) request.getAttribute("msg");
										if (msg == null) {
											msg = "";
										}
							%>
							<tr>


								<td style="color: #B60000"><%=msg%></td>
							</tr>
						</table>


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