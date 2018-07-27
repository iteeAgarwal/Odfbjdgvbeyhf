<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.Token"%>
<%@ page import="org.dqms.util.Print"%>

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
<SCRIPT src="js_dqms/sorttable.js" type="text/javascript"></script>
<script src="js_dqms/dcu.js" type="text/javascript"></script>
<script src="js_dqms/dcu_walkappoint_list_update.js" type="text/javascript"></script>
</head>
<body onload='getServiceData(); getPatientListData();'>
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
				<TH COLSPAN="5" style="background-color: #007E9C; height: 65px; color: #FFFFFF; font: normal 16px arial;width: 85%">
					<b> <u>Doctor Console Unit</u> </b><br>    <%String name=request.getAttribute("userNameSta").toString();
					                                                         String n[]=name.split(" ",2);%>
					<div align="right" style="margin-top: -1cm;margin-right: 0.5cm;">Welcome, <strong><%=n[0]+"."+n[1]%></strong>(<%=request.getAttribute("userDeptName")%>)<br>Room No: <strong><%=request.getAttribute("userRoomNum")%></strong></div>
				</TH>
				
				<%-- <th>Welcome, <font color="#B4045F">Dr.<%=request.getAttribute("userNameSta")%></font>(<%=request.getAttribute("userDeptName")%>)<br>Room No:<font color="#B4045F"><%=request.getAttribute("userRoomNum")%></font></th> --%>
				
				<%-- <th>Room No: <%=request.getAttribute("userRoomNum")%></th> --%>
				
			</TABLE >
<!-- </div> -->
			<form name="dcu_form" id="dcu_form" method="post" action="./DCUCallSkip">
				<div id="cont_shadow_apo">
					 
					<input type="hidden" name="hidden_list_type" id="hidden_list_typeID"
						value="<%=request.getAttribute("listTypeint")%>">

					<div id="token_status_form_apo" >
						<p id="span_text1">Appointment Patient List</p>
						<div id="walk_patients_form">
							<!------------------   Appointment_patients_form start--------------- -->
							<table id="table_app" width="100%" align="center"
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
								</thead>
								<TBODY>
									<%
										@SuppressWarnings("unchecked")
											ArrayList<Token> listTokenApp = (ArrayList<Token>) request.getAttribute("listTokenApp");
											if (listTokenApp.size() > 0) {
												int i =0;
												for (Token listToken : listTokenApp) {
									%>
									<tr
										style="background: #EFEFEF; height: 25px; font-size: 14px; text-align: center">
										<%if(i==0){ %>
										<td><input type="radio" name="appRadio" id="app_walk_id" value="<%= listToken.getToken_no()%>" checked="checked"></td>
										<%}else{ %>
										<td><input type="radio" name="appRadio" id="app_walk_id" value="<%= listToken.getToken_no()%>" ></td>
										<%} %>
										<td><%=listToken.getApp_walk_value()%></td>
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
												tokenStatusName = "Cancelled";
											}
										%>
										<td><%=tokenStatusName%></td>
									</tr>
									<%	i++;
										}
											} else {
									%>
									<tr>
										<td></td>
										<td></td>
										<td align="center" style="color: #B20000;"><%=" No Appointment patients !!"%></td>
									</tr>
									<%
										}
									%>
							</table>
							<!------------------   Appointment_patients_form end--------------- -->
						</div>
					</div>

					<div id="token_status_form_apo" style="margin-top: 295px;">
						<p id="span_text1">Walk-in Patient List</p>
						<div id="walk_patients_form">
							<!------------------   walk_patients_form start--------------- -->

							<table id="table_walk" width="100%" align="center"
								color:#3b485f;="" line-height:1.6em;=""
								border-collapse:collapse;="">
								<thead>
									<tr style="background: #007E9C; height: 40px; font-size: 14px; font-family: Arial, Helvetica, sans-serif;">
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
								</thead>
								<tbody>
									<%
										@SuppressWarnings("unchecked")
											ArrayList<Token> tokenStatus = (ArrayList<Token>) request.getAttribute("listTokenWalk");
											if (tokenStatus.size() > 0) {

												for (Token listToken : tokenStatus) {
									%>
										<tr style="background: #EFEFEF; height: 25px; font-size: 14px; text-align: center"">	
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
														tokenStatusName = "Cancelled";
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
											<td align="center" style="color: #B20000;"><%="No walk-in patient !!"%></td>
											<td></td>
										</tr>
									<%
											}
									%>
								</tbody>
							</table>
							<!------------------   walk_patients_form end--------------- -->
						</div>
					</div>

				</div>
				
				<div id="doc_status_display_apo" >
					<p id="span_text1">Doctor Details</p>
					<!------------------   Doctor  Details start--------------- -->

					<table id="doc_td_label" align="center" style="width: 100%;">

						<%
							if ((Boolean)(request.getAttribute("listTypeBool"))==false) {
						%>
						
<!-- 						<tr  id="table_head_apollo">
							<td align="center">Total Patient:</td>
							<td align="center">Appointment<table><tr><td align="center">Total Patient:</td><td align="center">Last Called Patient:</td></tr></table></td>
							<td align="center">Walk-in<table><tr><td align="center">Total Patient:</td><td align="center">Last Called Patient:</td></tr></table></td>
							<td align="center" style="padding-left: 10px;">List Type:</td>
						</tr>
						<tr id="table_head_apollo">
							<td id="total_token"></td>
							<td ><table><tr><td  id="total_token_app"></td><td id="current_token_app"></td></tr></table></td>
							<td ><table><tr><td id="total_token_walk"></td><td id="current_token_walk"></td></tr></table></td>
							<td id="dcu_td_apo">Fresh List</td>
						</tr> -->
						
						
						<tr align="center" id="table_head_apollo">
						<td rowspan="2">Total Patient</td><td colspan="2"><strong>Appointment</strong></td><td colspan="2"><strong>Walk-in</strong></td><td rowspan="2">List Type</td>
						</tr>
						<tr id="table_head_apollo" align="center">
						<td>Total Patient</td><td>Last Called Patient</td>
						<td>Total Patient</td><td>Last Called Patient</td>
						</tr>
						<tr align="center" id="table_head_apollo">
						<td id="total_token"></td><td id="total_token_app"></td><td id="current_token_app"></td><td id="total_token_walk"></td><td id="current_token_walk"></td><td id="dcu_td_apo">Fresh List</td>
						<tr>
						

						
						
						
						
						<%
							} else {
						%>

						<!-- <tr id="table_head_apollo">
							
							<td >Total Patient:</td>
							<td >Appointment<table><tr><td >Total Patient:</td><td >Last Called Patient:</td></tr></table></td>
							<td >Walk-in<table><tr><td >Total Patient:</td><td >Last Called Patient:</td></tr></table></td>
							<td style="padding-left: 10px;">List Type:</td>
						</tr>


						<tr id="table_head_apollo">
							<td id="skip_total_token"></td>
							<td ><table><tr><td id="skip_total_token_app"></td><td id="skip_current_token_app"></td></tr></table></td>
							<td ><table><tr><td id="skip_total_token_walk"></td><td id="skip_current_token_walk"></td></tr></table></td>
							<td id="dcu_td_apo">Skip List</td>
						</tr> -->



						<tr align="center" id="table_head_apollo">
						<td rowspan="2">Total Patient</td><td colspan="2"><strong>Appointment</strong></td><td colspan="2"><strong>Walk-in</strong></td><td rowspan="2">List Type</td>
						</tr>
						<tr id="table_head_apollo" align="center">
						<td>Total Patient</td><td>Last Called Patient</td>
						<td>Total Patient</td><td>Last Called Patient</td>
						</tr>
						<tr align="center" id="table_head_apollo">
						<td id="skip_total_token"></td><td id="skip_total_token_app"></td><td id="skip_current_token_app"></td><td id="skip_total_token_walk"></td><td id="skip_current_token_walk"></td><td id="dcu_td_apo">Skip List</td>
						<tr>

						<%
							}
						%>

					</table>

					<!------------------  Doctor  Details  end--------------- -->

				</div>
				
				<div id="callSkip_table_div" style="margin-top: 180px;">
					<p id="span_text1">Calling Unit</p>
					
					<!------------------   Calling Unit  start--------------- -->
					<%  boolean isAppWalk =(Boolean)(request.getAttribute("isAppWalk"));
						int callType = (Integer)(request.getAttribute("callType"));
						boolean listTypeBool=(Boolean)(request.getAttribute("listTypeBool"));
						int appointmentTokenNo=(Integer)(request.getAttribute("appointmentTokenNo"));
						int walkinTokenNo=(Integer)(request.getAttribute("walkinTokenNo")); 
					%>
					<input type="hidden" name="hiddentokenNo" id="hidden_tokenNo" value="<%=appointmentTokenNo%>"> 
					<input type="hidden" name="hiddenlistType" id="hidden_listType" value="<%=listTypeBool%>">
					<input type="hidden" name="hiddentokenNoWalk" id="hidden_tokenNo" value="<%=walkinTokenNo%>">
					
					<%if(appointmentTokenNo>0){ %>
						<script type="text/javascript">updateRowNoAppointmentPatient("<%=appointmentTokenNo %>");</script>
					<%} %>
					<%if(!((request.getAttribute("resultError")) == "" || (request.getAttribute("resultError")).equals("") || (request.getAttribute("resultError")) ==null)){ %>
						<script type="text/javascript">ShowErrorAlertDCU('<%=request.getAttribute("resultError")%>');</script>
					<%} %>
					<table id="right_menu_table" align="center" style="margin-top: 1%;">
					<tr>
							<td>Select Appointment/Walking Patients:</td>	
					<% if((Boolean)(request.getAttribute("isAppWalk"))==true )	{%>
							
							<td><select class="dropdown_conn_class" id="appWalkDropDown"
								name="appWalkDropDown" required>
									<option value="1" selected="selected">Appointment</option>
									<option value="2">Walk-in</option>
							</select></td>
						</tr>
					<%	}	else{ %>
							<td><select class="dropdown_conn_class" id="appWalkDropDown"
								name="appWalkDropDown" required>
									<option value="1" >Appointment</option>
									<option value="2" selected="selected">Walk-in</option>	
							</select></td>
						
					<%	}	%>
					</tr>
					
					<table id="right_menu_table" align="center" style="margin-top: 1%;padding:10px;">
						<tr>
							
					<% 
					if((Boolean)(request.getAttribute("listTypeBool"))==true && (Integer)(request.getAttribute("callType")) == 1) { %>
						<script type="text/javascript">disableAppWalkDropdown();</script>
						<% if((Boolean)(request.getAttribute("isAppWalk"))==true) { %>
							<td><input type="submit" name="btncall" value="Treat" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnskip" value="Skip" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnlisttype" value="Cancel" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
						<% } else { %>
							<td><input type="submit" name="btncall" value="Treat" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnskip" value="Skip" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnlisttype" value="Cancel" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
						<% } %>
						
					<% } else if((Boolean)(request.getAttribute("listTypeBool"))==true && (Integer)(request.getAttribute("callType")) != 1) { %>	
						<script type="text/javascript">enableAppWalkDropdown();</script> 
						<% if((Boolean)(request.getAttribute("isAppWalk"))==true) { %>
							<td><input type="submit" name="btncall" value="Call" class="msg_btn"
									onclick="checkTokenSelectedAppointment();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="button" name="btnskip" value="Skip" class="msg_btn"
									onclick="noSkipBeforeCall();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnlisttype" value="Fresh List" class="msg_btn"
									onclick="ChangeListType();" style="font-size: 16px; height: 80px; width: 100px;"></td>
						<% } else { %> 
							<td><input type="submit" name="btncall" value="Call" class="msg_btn"
									onclick="checkTokenSelectedAppointment();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="button" name="btnskip" value="Skip" class="msg_btn"
									onclick="noSkipBeforeCall();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnlisttype" value="Fresh List" class="msg_btn"
									onclick="ChangeListType();" style="font-size: 16px; height: 80px; width: 100px;"></td>
						<% } %>	
					<% } else if((Boolean)(request.getAttribute("listTypeBool"))==false && (Integer)(request.getAttribute("callType")) == 1) { %>
						 <script type="text/javascript">disableAppWalkDropdown();</script>
						<% if((Boolean)(request.getAttribute("isAppWalk"))==true) { %>
							<td><input type="submit" name="btncall" value="Treat" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnskip" value="Skip" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnlisttype" value="Cancel" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
						<% } else { %>
							<td><input type="submit" name="btncall" value="Treat" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnskip" value="Skip" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnlisttype" value="Cancel" class="msg_btn"
									onclick="enableAppWalkDropdown();" style="font-size: 16px; height: 80px; width: 100px;"></td>
						<% } %>
					<% } else { %>
						 <script type="text/javascript">enableAppWalkDropdown();</script>
						<% if((Boolean)(request.getAttribute("isAppWalk"))==true) { %>
							<td><input type="submit" name="btncall" value="Call" class="msg_btn"
									onclick="checkTokenSelectedAppointment();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="button" name="btnskip" value="Skip" class="msg_btn"
									onclick="noSkipBeforeCall();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnlisttype" value="Skip List" class="msg_btn"
									onclick="ChangeListType();" style="font-size: 16px; height: 80px; width: 100px;"></td>
						<% } else { %> 
							<td><input type=submit name="btncall" value="Call" class="msg_btn"
									onclick="checkTokenSelectedAppointment();" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="button" name="btnskip" value="Skip" class="msg_btn"
									onclick="noSkipBeforeCall;" style="font-size: 16px; height: 80px; width: 100px;"></td>
							<td><input type="submit" name="btnlisttype" value="Skip List" class="msg_btn"
									onclick="ChangeListType();" style="font-size: 16px; height: 80px; width: 100px;"></td>
						<% } %>
					<% } %>
					
					
						</tr>
					</table>



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
				
				
			</form>
		</div>
	</div>

	
	
	<%@include file="footer_pg.jsp"%>
</table></div><input type="hidden" name="hidden_doc_id" id="hidden_doc_idID" value='<%=request.getAttribute("userIDSta")%>'></body>
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