<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="java.util.Date"%>

<%@ page import="java.text.SimpleDateFormat"%>



<%@ page import="org.dqms.db.Room"%>

<%@ page import="org.dqms.db.User"%>

<%@ page import="org.dqms.db.Device"%>


<%
	if (session.getAttribute("user") != null) {
%>
<%	response.setHeader("Cache-Control",
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

<title>Device</title>
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

		window.location.assign("./DeviceDetailsListServlet");

	}
</script>


<script type="text/javascript">
 function rowSelecter(id) {
	// document.getElementById("ra"+id).checked = true;
	 document.getElementById(id).radio.checked = true;
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

					<b>Device Settings </b>

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

			<%
				@SuppressWarnings("unchecked")
						List<Device> deviceList = (ArrayList<Device>) request
								.getAttribute("deviceList");

						if (deviceList.size() > 0) {

							for (int i = deviceList.size() - 1; i >= 0; i--) {
			%>
			<div id="doc_form_admin">
				<p id="span_text1">Device View Form</p>
				<form name="doctorForm" method="post" action="#">
					<table id="doc_td_label_admin">


						<tr>
							<td>Device Type:</td>
							<td><select class="dropdown_conn_class" id="userRole_id"
								name="deviceType" required>

									<%
										if (deviceList.get(0).type == 1) {
									%>
									<option value="">Select Device Type</option>


									<option value="1" selected="selected">MDU</option>
									<option value="2">WDU</option>
									<%
										} else if (deviceList.get(0).type == 2) {
									%>


									<option value="">Select Device Type</option>


									<option value="1">MDU</option>
									<option value="2" selected="selected">WDU</option>
									<%
										} else {
									%>

									<option value="">Select Device Type</option>
									<option value="1">MDU</option>
									<option value="2">WDU</option>
									<%
										}
									%>






							</select></td>
						</tr>
						<tr>
							<td>Device ID:</td>
							<td><input type="text" name="deviceID"
								value="<%=deviceList.get(0).device_id%>"
								class="doc_td_value_class" id="deviceID_id"
								readonly="readonly"></td>
						</tr>

						<tr>
							<td>Device Name:</td>
							<td><input type="text" name="deviceName"
								value="<%=deviceList.get(0).device_name%>" readonly="readonly"
								id="deviceName_id" class="doc_td_value_class"></td>
						</tr>

						<tr>
							<td>IP Address:</td>
							<td><input type="text" name="ipAddress"
								value="<%=deviceList.get(0).ip%>" readonly="readonly" id="ipAddress_id"
								class="doc_td_value_class"></td>
						</tr>




						<tr>
							<td>MAC Address:</td>
							<td><input type="text" name="macAddress" readonly="readonly"
								value="<%=deviceList.get(0).mac_address%>" id="macAddress_id"
								max="4" class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><input type="text" name="address" readonly="readonly"
								value="<%=deviceList.get(0).address%>"  id="address_id"
								class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Location:</td>
							<td><input type="text" name="location"
								value="<%=deviceList.get(0).location%>" readonly="readonly"
								id="location_id" class="doc_td_value_class"></td>
						</tr>



						<tr>

							<td><input type="button" name="userDetails" value="Cancel"
								id="formButton" onclick="generateNew()"></td>
						</tr>
					</table>
				</form>
			</div>

			<%
				}
						} else {
			%>



			<tr>

				<td id="response_mess"><%="Device list not founds !!"%></td>


			</tr>


			<%
				response.sendRedirect("./DeviceDetailsListServlet");
						}
			%>


			<!--===================================END==========================================-->
			<%
				} else if (newBtn != null) {
			%>


			<!--===================================Add new room number==========================================-->
			<div id="doc_form_admin">
				<p id="span_text1">Device Insert Form</p>
				<form name="doctorForm" method="post" action="./DeviceDetailsInsert">
					<table id="doc_td_label_admin">


						<tr>
							<td>Device Type:</td>
							<td><select class="dropdown_conn_class" id="userRole_id"
								name="deviceType" required>


									<option value="" selected disabled style="display: none;">Select Device Type</option>
									<option value="1">MDU</option>
									<option value="2">WDU</option>




							</select></td>
						</tr>
						<tr>
							<td>Device ID:</td>
							<td><input type="text" name="deviceID" value=""
								class="doc_td_value_class" id="deviceID_id"
								placeholder="Auto Generated" disabled="disabled"></td>
						</tr>

						<tr>
							<td>Device Name:</td>
							<td><input type="text" name="deviceName" value="" required
								id="deviceName_id" class="doc_td_value_class"></td>
						</tr>

						<tr>
							<td>IP Address:</td>
							<td><input type="text" name="ipAddress" value=""
								id="ipAddress_id" class="doc_td_value_class"></td>
						</tr>




						<tr>
							<td>MAC Address:</td>
							<td><input type="text" name="macAddress" value=""
								id="macAddress_id" class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><input type="text" name="address" value="" required
								id="address_id" maxlength="4" class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Location:</td>
							<td><input type="text" name="location" value="" required
								id="location_id" class="doc_td_value_class"></td>
						</tr>



						<tr>
							<td><input type="submit" name="deviceDetails" value="Save"
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



			<%
				@SuppressWarnings("unchecked")
						List<Device> deviceList = (ArrayList<Device>) request
								.getAttribute("deviceList");

						if (deviceList.size() > 0) {

							for (int i = deviceList.size() - 1; i >= 0; i--) {
			%>
			<div id="doc_form_admin">
				<p id="span_text1">Device Edit Form</p>
				<form name="doctorForm" method="post" action="./DeviceDetailsInsert">
					<table id="doc_td_label_admin">


						<tr>
							<td>Device Type:</td>
							<td><select class="dropdown_conn_class" id="userRole_id"
								name="deviceType" required>

									<%
										if (deviceList.get(0).type == 1) {
									%>
									<option value="" selected disabled style="display: none;">Select Device Type</option>


									<option value="1" selected="selected">MDU</option>
									<option value="2">WDU</option>
									<%
										} else if (deviceList.get(0).type == 2) {
									%>


									<option value="" selected disabled style="display: none;">Select Device Type</option>


									<option value="1">MDU</option>
									<option value="2" selected="selected">WDU</option>
									<%
										} else {
									%>

									<option value="">Select Device Type</option>
									<option value="1">MDU</option>
									<option value="2">WDU</option>
									<%
										}
									%>






							</select></td>
						</tr>
						<tr>
							<td>Device ID:</td>
							<td><input type="text" name="deviceID"
								value="<%=deviceList.get(0).device_id%>"
								class="doc_td_value_class" id="deviceID_id"
								readonly="readonly"></td>
						</tr>

						<tr>
							<td>Device Name:</td>
							<td><input type="text" name="deviceName"
								value="<%=deviceList.get(0).device_name%>" required
								id="deviceName_id" class="doc_td_value_class"></td>
						</tr>

						<tr>
							<td>IP Address:</td>
							<td><input type="text" name="ipAddress"
								value="<%=deviceList.get(0).ip%>"  id="ipAddress_id"
								class="doc_td_value_class"></td>
						</tr>




						<tr>
							<td>MAC Address:</td>
							<td><input type="text" name="macAddress"
								value="<%=deviceList.get(0).mac_address%>" id="macAddress_id"
								class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Address</td>
							<td><input type="text" name="address"
								value="<%=deviceList.get(0).address%>" maxlength="4" required
								id="address_id" class="doc_td_value_class"></td>
						</tr>
						<tr>
							<td>Location:</td>
							<td><input type="text" name="location"
								value="<%=deviceList.get(0).location%>" required
								id="location_id" class="doc_td_value_class"></td>
						</tr>



						<tr>
							<td><input type="submit" name="editDetails" value="Save"
								id="formButton"></td>
							<td><input type="button" name="userDetails" value="Cancel"
								id="formButton" onclick="generateNew()"></td>
						</tr>
					</table>
				</form>
			</div>

			<%
				}
						} else {
			%>



			<tr>

				<td id="response_mess"><%="Device list not founds !!"%></td>


			</tr>


			<%
				response.sendRedirect("./DeviceDetailsListServlet");
						}
			%>

			<!--===================================END==========================================-->

			<%
				} else {
			%>
			<div id="shutdown">
				<form name="roomForm" method="post"
					action="./DeviceDetailsListServlet">

					<table id="IP_tableTH" width="100%" align="center"
						color:#3b485f;="" line-height:1.6em;=""
						border-collapse:collapse;=""  class="table-striped" >
						<tbody>
							<tr	style="background: #007E9C; height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">


								<th scope="col">Select</th>

								<th scope="col">Device ID</th>

								<th scope="col">Device Name</th>
								<th scope="col">Device Type</th>
								<th scope="col">IP Address</th>

								<th scope="col">MAC Address</th>
								<th scope="col">Address</th>
								<th scope="col">Location</th>
								<th scope="col">Last Updated</th>

								<th scope="col">Last Hit Time</th>
								<th scope="col">Health</th>






							</tr>
							<%
								@SuppressWarnings("unchecked")
										List<Device> deviceList = (ArrayList<Device>) request
												.getAttribute("deviceList");

										if (deviceList.size() > 0) {
											int j=0;
											for (int i = deviceList.size() - 1; i >= 0; i--) {
												j++;
												Date updatedDate = new Date(deviceList.get(i).getLast_updated()*1000);
												
												Date LastHitDate = new Date(deviceList.get(i).getLast_updated()*1000);
												
												SimpleDateFormat sdf = new SimpleDateFormat(
														"dd/MM/yyyy HH:mm:ss");

												String Last_updated = sdf.format(updatedDate);
												
												String Last_hit_time = sdf.format(LastHitDate);
							%>
							<tr id="<%=j%>" 
								style="height: 25px; font-size: 14px; text-align: center"  onclick="rowSelecter(this.id);">

								<td><input type="radio" name="radioroomID"
									value="<%=deviceList.get(i).getDevice_id()%>" id="ra<%=j%>"></td>


								<td><%=deviceList.get(i).getDevice_id()%></td>
								<td><%=deviceList.get(i).getDevice_name()%></td>
								<td><%String name=""; 
								if(deviceList.get(i).getType()==1)
								{
									name="MDU";
								}
								else
								{
									name="WDU";
								}
									%><%=name%></td>
								<td><%=deviceList.get(i).getIp()%></td>
								<td><%=deviceList.get(i).getMac_address()%></td>

								<td><%=deviceList.get(i).getAddress()%></td>
								<td><%=deviceList.get(i).getLocation()%></td>

								<td><%=Last_updated%></td>

								<td><%=Last_hit_time%></td>

								
								
								
								<%
									if (deviceList.get(i).isHealth_check()) {
								%>

								<td><img src="./images_dqms/accept.png" width="30px"
									height="30px" /></td>
								<%
									} else {
								%>

								<td><img src="./images_dqms/error.png" width="30px"
									height="30px" /></td>
								<%
									}
								%>













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
								<input type="submit" name="roomdelete" id="formButton" value="Delete" onclick="if (confirm('Are you sure you want to delete?')) { form.action='./DeviceDetailsListServlet'; } else { return false; }" />
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
							<%if(msg!=null && !msg.equals("")){%>
								<script>
								alert('<%=msg%>');
								</script>
							<%}%>

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