<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.Token"%>


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

<title>DCU</title>

<link rel="icon" href="./images_dqms/title.gif" type="image/gif" />

<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />

<script src="js_dqms/serviceAPI.js" type="text/javascript"></script>
<script src="js_dqms/validation.js" type="text/javascript"></script>





</head>
<body onload="getServiceData()">
	<div id="header">
		<div id="headerlogo">

			<div id="headerleft1">
				<%@include file="topmenu.jsp"%>

			</div>





		</div>
	</div>


	<div id="dialogoverlay"></div>
	<div id="dialogbox">
		<div>

			<div id="dialogboxbody"></div>
			<div id="dialogboxfoot"></div>
		</div>
	</div>
	<div id="container">


		<!--  report table start -->
		<div id="report_table1">

			<TABLE BORDER="0" WIDTH="100%">

				<TH COLSPAN="5"
					style="background-color: #007E9C; height: 65px; color: #FFFFFF; font: normal 16px arial;">

					<b> Doctor Consol Unit </b>

				</TH>
			</TABLE>


			<form name="token" method="post" action="./DCUCallSkip">

				<div id="cont_shadow_apo">


					<input type="hidden" name="hidden_doc_id" id="hidden_doc_idID"
						value="<%=request.getAttribute("userIDSta")%>"> <input
						type="hidden" name="hidden_list_type" id="hidden_list_typeID"
						value="<%=request.getAttribute("listTypeint")%>">



					<div id="token_status_form_apo">
						<p id="span_text1">Walk-in Patients List</p>

						<div id="walk_patients_form">

							<!------------------   walk_patients_form start--------------- -->



							<table id="IP_tableTH" width="100%" align="center"
								color:#3b485f;="" line-height:1.6em;=""
								border-collapse:collapse;="">
								<thead>
									<tr
										style="background: #007E9C; height: 40px; font-size: 14px; font-family: Arial, Helvetica, sans-serif;">





										<th id="tokenNoID"
											style="color: #fff; text-align: center; font-weight: bold; width: 20%;"
											scope="col">Token No</th>

										<th id="patientNoID"
											style="color: #fff; text-align: center; font-weight: bold; width: 40%;"
											scope="col">Patient Name</th>

										<th id="statusID"
											style="color: #fff; text-align: center; font-weight: bold; width: 20%;"
											scope="col">Status</th>
									</tr>


									<%
										@SuppressWarnings("unchecked")
											ArrayList<Token> tokenStatus = (ArrayList<Token>) request
													.getAttribute("listTokenWalk");

											if (tokenStatus.size() > 0) {

												for (Token listToken : tokenStatus) {
									%>
									<tr
										style="background: #EFEFEF; height: 25px; font-size: 14px; text-align: center"">



										<td><%=listToken.getToken_no()%></td>
										<td><%=listToken.getPatient_name()%></td>


										<%
											String tokenStatusName = "";

														if (listToken.getStatus() == 1) {
															tokenStatusName = "Called";

														} else if (listToken.getStatus() == 2) {
															tokenStatusName = "Skipped";
														} else if (listToken.getStatus() == 3) {
															tokenStatusName = "Treated";
														} else if (listToken.getStatus() == 4) {
															tokenStatusName = "cancelled";
														}
										%>



										<td><%=tokenStatusName%></td>


									</tr>
									<%
										}
											} else {
									%>
									<tr>

										<td></td>
										<td></td>

										<td align="center" style="color: #B20000;"><%="Walk-in Patients List is Blank !!"%></td>
									</tr>





									<%
										}
									%>

								</thead>
							</table>


							<!------------------   walk_patients_form end--------------- -->
						</div>


					</div>

					<div id="token_status_form_apo" style="margin-top: 295px;">
						<p id="span_text1">Appointment Patients List</p>

						<div id="walk_patients_form">



							<!------------------   Appointment_patients_form start--------------- -->

							<table id="IP_tableTH" width="100%" align="center"
								color:#3b485f;="" line-height:1.6em;=""
								border-collapse:collapse;="">
								<thead>
									<tr
										style="background: #007E9C; height: 40px; font-size: 14px; font-family: Arial, Helvetica, sans-serif;">


										<th id="statusID"
											style="color: #fff; text-align: center; font-weight: bold; width: 20%;"
											scope="col">Select</th>

										<th id="tokenNoID"
											style="color: #fff; text-align: center; font-weight: bold; width: 20%;"
											scope="col">Token No</th>

										<th id="patientNoID"
											style="color: #fff; text-align: center; font-weight: bold; width: 40%;"
											scope="col">Patient Name</th>

										<th id="statusID"
											style="color: #fff; text-align: center; font-weight: bold; width: 20%;"
											scope="col">Status</th>

									</tr>




									<%
										@SuppressWarnings("unchecked")
											ArrayList<Token> listTokenApp = (ArrayList<Token>) request
													.getAttribute("listTokenApp");

											if (tokenStatus.size() > 0) {

												for (Token listToken : listTokenApp) {
									%>
									<tr
										style="background: #EFEFEF; height: 25px; font-size: 14px; text-align: center"">

										<td><input type="radio" name="walkRadio" id="app_walk_id"
											value="<%= listToken.getToken_no()%>"></td>
										<td><%=listToken.getToken_no()%></td>
										<td><%=listToken.getPatient_name()%></td>


										<%
											String tokenStatusName = "";

														if (listToken.getStatus() == 1) {
															tokenStatusName = "Called";

														} else if (listToken.getStatus() == 2) {
															tokenStatusName = "Skipped";
														} else if (listToken.getStatus() == 3) {
															tokenStatusName = "Treated";
														} else if (listToken.getStatus() == 4) {
															tokenStatusName = "cancelled";
														}
										%>



										<td><%=tokenStatusName%></td>

									</tr>
									<%
										}
											} else {
									%>
									<tr>

										<td></td>
										<td></td>

										<td align="center" style="color: #B20000;"><%=" Appointment patients list is Blank !!"%></td>
									</tr>

									<%
										}
									%>
								</thead>
							</table>


							<!------------------   Appointment_patients_form end--------------- -->

						</div>

					</div>
				</div>
				<div id="doc_status_display_apo">
					<p id="span_text1">Calling Unit</p>


					<!------------------   Calling Unit  start--------------- -->

					<%
						String callTreatBtn = "";

							String listType = (String) request.getAttribute("listType");

							int calledFlag = (Integer) application
									.getAttribute("calledFlag");
					%>

					<%
						if ("Fresh".equalsIgnoreCase(listType)) {
								String callBtn = request.getParameter("Call");

								if (calledFlag == 0) {
									callTreatBtn = "Call";
								} else {
									callTreatBtn = "Treat";
								}
					%>

					<!--     Fresh list start                   -->




					<table id="right_menu_table" align="center" style="margin-top: 1%;">
						<tr>
							<td>Select Appointment/Walking Patients:</td>

							<td><select class="dropdown_conn_class" id="dropdo"
								name="appWalk" required>

									<option value="1">Walk-in</option>
									<option value="2" >Appointment</option>


							</select></td>
						</tr>
					</table>

					<table id="right_menu_table" align="center" style="margin-top: 1%;padding:10px;">


						<tr>

							<%
								if ((Integer) application.getAttribute("freshListFlag") == 1) {
							%>
							<td><input type="button" name="<%=callTreatBtn%>"
								value="<%=callTreatBtn%>" class="msg_btn"
								onclick="noPatients();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>
							<%
								} else {
							%>
							<td><input type="submit" name="<%=callTreatBtn%>"
								value="<%=callTreatBtn%>" class="msg_btn"
								style="font-size: 16px; height: 80px; width: 100px;"></td>
							<%
								}
							%>




							<%
								if (calledFlag == 0) {
							%>

							<td><input type="button" name="skip" value="Skip"
								class="msg_btn" onclick="beforcall();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>


							<td><input type="submit" name="List Change"
								value="List Change" class="msg_btn"
								style="font-size: 16px; height: 80px; width: 100px;"></td>

							<%
								}

										else {
							%>

							<td><input type="submit" name="skip" value="Skip"
								class="msg_btn" id="scan"
								style="font-size: 16px; height: 80px; width: 100px;"></td>


							<td><input type="button" name="List Change"
								value="List Change" class="msg_btn" onclick="cantListChnage();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>
							<%
								}
							%>
						</tr>
						<tr>


							<%
								if (calledFlag == 0) {
							%>
							<td><input type="button" class="msg_btn" name="summary"
								value="Summary" onclick="getSummary();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>




							<td><input type="button" name="logout" value="Logout"
								class="msg_btn" onclick="getLogout();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>

							<!-- <td><input type="submit" name="showPatientList"
								value="Show Patients" class="msg_btn"
								style="font-size: 18px; height: 100px; width: 150px;"></td> -->

							<%
								}

										else {
							%>

							<td><input type="button" class="msg_btn" name="summary"
								value="Summary" onclick="getSummary();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>




							<td><input type="button" name="logout" value="Logout"
								class="msg_btn" onclick="cantListChnage();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>

							<!-- <td><input type="submit" name="showPatientList"
								value="Show Patients" class="msg_btn"
								style="font-size: 18px; height: 100px; width: 150px;"></td> -->

							<%
								}
							%>

						</tr>



					</table>


					<%
						}

							else {

								String callBtn = request.getParameter("Call");

								if (calledFlag == 0) {
									callTreatBtn = "Call";
								} else {
									callTreatBtn = "Treat";
								}
					%>
					<!--     skip list start                   -->



					<table id="right_menu_table" align="center" style="margin-top: 1%;">
						<tr>
							<td>Select Appointment/Walking Patients:</td>

							<td><select class="dropdown_conn_class" id="dropdo"
								name="appWalk" required>

									<option value="1">Walk-in</option>
									<option value="2">Appointment</option>


							</select></td>
						</tr>
					</table>

					<table id="right_menu_table" align="center" style="margin-top: 1%;padding:10px;">


						<tr>




							<%
								if ((Integer) application.getAttribute("skipListEmpty") == 1) {
							%>
							<td><input type="button" name="<%=callTreatBtn%>"
								value="<%=callTreatBtn%>" class="msg_btn"
								onclick="skipListEmpty();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>
							<%
								} else if ((Integer) application
												.getAttribute("skipListOver") == 1) {
							%>
							<td><input type="button" name="<%=callTreatBtn%>"
								value="<%=callTreatBtn%>" class="msg_btn"
								onclick="noPatients();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>
							<%
								}

										else

										{
							%>
							<td><input type="submit" name="<%=callTreatBtn%>"
								value="<%=callTreatBtn%>" class="msg_btn"
								style="font-size: 16px; height: 80px; width: 100px;"></td>
							<%
								}
							%>





							<%
								if (calledFlag == 0) {
							%>

							<td><input type="button" name="skip" value="Skip"
								class="msg_btn" onclick="beforcall();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>


							<td><input type="button" name="cancel" value="Cancel"
								class="msg_btn" onclick="beforcall();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>

							<%
								}

										else {
							%>

							<td><input type="submit" name="skip" value="Skip"
								class="msg_btn" id="scan"
								style="font-size: 16px; height: 80px; width: 100px;"></td>


							<td><input type="submit" name="Cancel" value="Cancel"
								class="msg_btn"
								style="font-size: 16px; height: 80px; width: 100px;"></td>
							<%
								}
							%>


						</tr>
						<tr>





							<%
								if (calledFlag == 0) {
							%>

							<td><input type="submit" name="List Change"
								value="List Change" class="msg_btn"
								style="font-size: 16px; height: 80px; width: 100px;"></td>


							<td><input type="button" class="msg_btn" name="summary"
								value="Summary" onclick="getSummary();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>




							<td><input type="button" name="logout" value="Logout"
								class="msg_btn" onclick="getLogout();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>

							<!-- <td><input type="submit" name="showPatientList"
								value="Show Patients" class="msg_btn"
								style="font-size: 18px; height: 100px; width: 150px;"></td> -->


							<%
								}

										else {
							%>

							<td><input type="button" name="List Change"
								value="List Change" class="msg_btn" onclick="cantListChnage();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>

							<td><input type="button" class="msg_btn" name="summary"
								value="Summary" onclick="getSummary();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>


							<!-- <td><input type="submit" name="showPatientList"
								value="Show Patients" class="msg_btn"
								style="font-size: 18px; height: 100px; width: 150px;"></td> -->

							<td><input type="button" name="logout" value="Logout"
								class="msg_btn" onclick="cantListChnage();"
								style="font-size: 16px; height: 80px; width: 100px;"></td>




							<%
								}
							%>

						</tr>




					</table>




					<!--     skip list end                   -->


					<%
						}
					%>



					<table align="center">
						<tr>
							<td style="font-size: 15px; color: #920706;">
								<%-- Using JSP EL to get message attribute value from request scope --%>
								${requestScope.mess}
							</td>
						</tr>
					</table>


					<!------------------   Calling Unit end--------------- -->



				</div>
				<div id="doc_status_display_apo" style="margin-top: 295px;">
					<p id="span_text1">Doctor Details</p>

					<!------------------   Doctor  Details start--------------- -->






					<table id="doc_td_label" align="center" style="width: 100%;">

						<tr id="table_head_apollo">
							<td style="padding-left: 30px;">Dr. Name:</td>
							<td style="padding-left: 30px;">Department Name:</td>
							<td style="padding-left: 30px;">Room Number:</td>
							<td style="padding-left: 30px;">List Type:</td>

						</tr>

						<tr id="table_head_apollo">
							<td id="dcu_td_apo"><%=application.getAttribute("userNameSta")%></td>
							<td id="dcu_td_apo"><%=application.getAttribute("userDeptName")%></td>
							<td id="dcu_td_apo"><%=application.getAttribute("userRoomNum")%></td>
							<td id="dcu_td_apo"><%=request.getAttribute("listType")%></td>
						</tr>


					</table>
					<table id="doc_td_label" align="center" style="width: 100%;">





						<%
							if (request.getAttribute("listType").equals("Fresh")) {
						%>

						<tr id="table_head_apollo">
							<td style="padding-left: 30px;">Last Called Token:</td>
							<td style="padding-left: 30px;">Last Alloted Token:</td>

						</tr>

						<tr id="table_head_apollo">
							<td class="dcu_td_apo" id="current_token"></td>
							<td class="dcu_td_apo" id="total_token"></td>
						</tr>


						<%
							} else {
						%>

						<tr id="table_head_apollo">
							<td style="padding-left: 30px;">Current Token:</td>
							<td style="padding-left: 30px;">Last Alloted Token:</td>
							<td style="padding-left: 30px;">Total Skipped Token:</td>

						</tr>

						<tr id="table_head_apollo">
							<td class="dcu_td_apo" id="current_token"><%=application.getAttribute("CurrentSkippedTokenNo")%></td>
							<td class="dcu_td_apo" id="total_token"></td>
							<td class="dcu_td_apo" id="skip_token"></td>
						</tr>




						<%
							}
						%>






					</table>

					<!------------------  Doctor  Details  end--------------- -->




				</div>
			</form>
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