<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="org.dqms.db.TokenGroup"%>

<%@ page import="org.dqms.db.Room"%>

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

<title>Token Group </title>
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
	function generateNewGroup() {

		window.location.assign("./TokenGroupList");

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

					<b>Room Group</b>

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
				<form name="newRoomForm" method="post" action="#">
					<div id="doc_form_admin">
						<p id="span_text1">Token Group View</p>

						<table id="doc_td_label_admin">

							<%
								@SuppressWarnings("unchecked")
										List<TokenGroup> list = (ArrayList<TokenGroup>) request
												.getAttribute("toGroupList");

										if (list.size() > 0) {

											for (TokenGroup listrooms : list) {
							%>


							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>Group ID:</td>
								<td><input type="text" name="roomID"
									value="<%=listrooms.getToken_group_id()%>" id="doc_td_value"
									disabled></td>
							</tr>

							<tr>
								<td>Group Name:</td>
								<td><input type="text" name="roomNo"
									value="<%=listrooms.getToken_group_name()%>" disabled
									id="doc_td_value"></td>
							</tr>

							<%
								String deptName = "";
												@SuppressWarnings("unchecked")
												List<Department> deptList = (ArrayList<Department>) request
														.getAttribute("deptList");

												if (deptList.size() > 0) {

													for (Department listdept : deptList) {

														if (listrooms.getDepart_id() == listdept
																.getDepart_id()) {
															deptName = listdept.getDepart_name();
														}

													}
												}
							%>

							<tr>
								<td>Department ID:</td>
								<td><input type="text" name="deptID" value="<%=deptName%>"
									disabled id="doc_td_value"></td>
							</tr>







							<tr>
								<td>Room Numbers:</td>
								
								<%

									 String strRooms  = listrooms.getRoom_id();
										
									 String[] splitRooms = strRooms.split(",");
									 
									 String roomNo = "";
									 
									 
									 @SuppressWarnings("unchecked")
										List<Room> roomsList = (ArrayList<Room>) request
												.getAttribute("roomsList");

	                                	
									
									
									StringBuilder builder = new StringBuilder();
									
									for(int e= 0; e<splitRooms.length;e++)
									{
										for(int f= 0; f<roomsList.size();f++ )
	                                	{
	                                		
	                                		if((Integer.parseInt(splitRooms[e])) == roomsList.get(f).getRoom_id())
	                                		{
	                                			roomNo = roomsList.get(f).getRoom_no();
	                                			
	                                			builder.append(roomNo);
	                                			builder.append(",");
	                                			break;
	                                		}
	                                		
	                                	}
										
									}
									 
	                                	
										
										
										
									
									
									%>
									
									
									

									
								
								
								
								<td><input type="text" name="location"
									value="<%=builder.toString()%>" disabled id="doc_td_value"></td>
							</tr>



							<tr>
								<td></td>
								<td></td>
							</tr>

							<tr>


								<td><input type="button" name="tokencancel" value="cancel"
									id="formButton" onclick="generateNewGroup()"></td>
							</tr>

						</table>




						<%
							}
									} else {
						%>
						<tr align="center">

							<td id="response_mess"><%="Token Group list not founds !!"%></td>


						</tr>


						<%
							response.sendRedirect("./TokenGroupList");
									}
						%>







					</div>
				</form>

				<!--===================================END==========================================-->
				<%
					} else if (newBtn != null) {
				%>


				<!--===================================Add new room number==========================================-->
				<form name="drop_list" method="post" action="./TokenGroupInsert">

					<div id="doc_form_admin">
						<p id="span_text1">Token Group Insert</p>

						<table id="doc_td_label_admin">
							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>Group ID:</td>
								<td><input type="text" name="groupID" value=""
									id="doc_td_value" placeholder="Auto Generated" disabled></td>
							</tr>

							<tr>
								<td>Group Name:</td>
								<td><input type="text" name="groupName" value="" required
									id="doc_td_value"></td>
							</tr>



							<tr>
								<td>Department :</td>
								
										
										<td><select class="dropdown_conn_class" id="deptID_id"
								name="deptID"  onchange="GetOptionsStatic22()" required >
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
								<td>Select Room Numbers:</td>
								<td></td>
							</tr>
						</table>
						<table align="center" id="slct2">


							



						</table>

						<table align="center">

							<tr>

								<td><input type="submit" name="groupSave" value="Save"
									id="formButton"></td>
								<td><input type="button" name="tokencancel" value="cancel"
									id="formButton" onclick="generateNewGroup()"></td>
							</tr>

						</table>





					</div>
					<div ></div>
					
				</form>

				<!--===================================END==========================================-->
				<%
					} else if (EditBtn != null) {
				%>

				<!--===================================Edit room number==========================================-->
				<form name="editRoomForm" method="post" action="./TokenGroupInsert">

					<div id="doc_form_admin">
						<p id="span_text1">Token Group Edit</p>

						<table id="doc_td_label_admin">

							<%
								@SuppressWarnings("unchecked")
										List<TokenGroup> list = (ArrayList<TokenGroup>) request
												.getAttribute("toGroupList");

										if (list.size() > 0) {

											for (int i = 0; i < list.size(); i++) {
							%>

							<tr>
								<td></td>
								<td></td>
							</tr>
							<tr>
								<td>Group ID:</td>
								<td><input type="text" name="groupID"
									value="<%=list.get(0).getToken_group_id()%>" id="doc_td_value"
									readonly="readonly"></td>
							</tr>

							<tr>
								<td>Group Number:</td>
								<td><input type="text" name="groupName"
									value="<%=list.get(0).getToken_group_name()%>" required
									id="doc_td_value"></td>
							</tr>



							<tr>

								<td>DDepartment :</td>
								<td><select id="dropdown_conn" name="deptID" onchange="GetOptionsStatic33()" onclick="GetOptionsStatic33()">

									<option value="" selected disabled style="display: none;">select department</option>

										<%
											@SuppressWarnings("unchecked")
															List<Department> deptList = (ArrayList<Department>) request
																	.getAttribute("deptList");

															if (deptList.size() > 0) {

																for (Department listdept : deptList) {

																	if (list.get(0).getDepart_id() == listdept
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
								<td>Select Room Numbers:</td>
								<td></td>
							</tr>

						</table>
												<br>
						<center><table id="tb_roomList_edit" >
						
						</table></center>
						<br>
						<%-- <table id="doc_td_label_admin">
						


							<%
								@SuppressWarnings("unchecked")
										List<Room> roomsList = (ArrayList<Room>) request
												.getAttribute("roomsList");

										if (roomsList.size() > 0) {
											
											if(roomsList.size() > 10){

											int rowsize = roomsList.size() / 10;
											
											 String strRooms  =  list.get(0).getRoom_id();
												
											 String[] splitRooms = strRooms.split(",");
											 
											 String checked = "";
											 
											 
											 int roo1 = roomsList.get(0).getRoom_id();
		 	                                	
		 	                                	boolean flag1 = false;
												
												if(splitRooms.length > 0)
												{
													for(int v = 0; v<splitRooms.length;v++){
													
													if(splitRooms[v].equals(String.valueOf(roo1)))
													{
														
														flag1 = true;
														break;
													}
													
												}
												}
												if(flag1)
												{
													checked = "checked";
												}
												else
												{
													checked = "unchecked";
												}
											 
											 
							%>

							<tr>

								<td><input type="checkbox" name="roomsCheckbox" required 
									value="<%=roomsList.get(0).getRoom_id()%>"  <%=checked + "=''"%> /> <label><%=roomsList.get(0).getRoom_no()%></label>
									<%
										for (int j = 0; j <= rowsize; j++) {
									
 	                                 for (int m = 10 * j + 1; m < 10 * (j + 1); m++) {
 	                                	 
 	                                	int roo = roomsList.get(m).getRoom_id();
 	                                	
 	                                	boolean flag = false;
										
										if(splitRooms.length > 0)
										{
											for(int v = 0; v<splitRooms.length;v++){
											
											if(splitRooms[v].equals(String.valueOf(roo)))
											{
												
												flag = true;
												break;
											}
											
										}
										}
										if(flag)
										{
											checked = "checked";
										}
										else
										{
											checked = "unchecked";
										}
 	                                	 
 	                                	 
 	                                	 
                                         %>
								<td><input type="checkbox" name="roomsCheckbox"
									value="<%=roomsList.get(m).getRoom_id()%>"  <%=checked + "=''"%>/> <label><%=roomsList.get(m).getRoom_no()%></label>
									<%
										if (m == roomsList.size() - 1) {
																break;
															}
									%></td>
								<%
									}
								%>
							</tr>
							<%
								}
										}
											
											else
											{
												%>
												<tr>
													<td>
												<%
												
												 String strRooms  =  list.get(0).getRoom_id();
												
												
												
												 String[] splitRooms = strRooms.split(",");
												 
												 
												
												 
												
												for (int n = 0; n < roomsList.size(); n++) {
													boolean flag = false;
													 String checked = "";
													
													int roo = roomsList.get(n).getRoom_id();
													
													if(splitRooms.length > 0)
													{
														for(int v = 0; v<splitRooms.length;v++){
															
															//out.println(splitRooms[v]);
														
														if(splitRooms[v].equals(String.valueOf(roo)))
														{
															
															
															flag = true;
															break;
														}
														
													}
													}
													if(flag)
													{
														checked = "checked";
													}
													else
													{
														checked = "unchecked";
													}
													
													%>
									<input type="checkbox" name="roomsCheckbox"
									value="<%=roomsList.get(n).getRoom_id()%>"  <%=checked + "=''"%>/> 
									<label><%=roomsList.get(n).getRoom_no()%></label>
													
													
													
												   <%
												
											
												}
												%>
												<tr>
													<td>
												<%
												
											}
											
										}

										else {

											String mess = "Room no not founds..!!";
							%>
							<tr>
								<td>

									<p id="response_mess"><%=mess%></p>
								</td>
							</tr>
							<%
								}
							%>




						</table> --%>
						
						
						
						
						<table align="center">



							<tr>


								<td><input type="submit" name="groupEdit" value="Save"
									id="formButton"></td>

								<td><input type="button" name="tokencancel" value="cancel"
									id="formButton" onclick="generateNewGroup()"></td>
							</tr>

						</table>




						<%
							}
									} else {
						%>
						<tr align="center">

							<td id="response_mess"><%="Token Group list not founds !!"%></td>


						</tr>


						<%
							response.sendRedirect("./TokenGroupList");
									}
						%>







					</div>
				</form>


				<!--===================================END==========================================-->

				<%
					} else {
				%>
				<div id="shutdown">
					<form name="roomForm" method="post" action="./TokenGroupList">

						<table id="IP_tableTH" width="100%" align="center"
							color:#3b485f;="" line-height:1.6em;=""
							border-collapse:collapse;="" class="table-striped">
							<tbody>
								<tr	style="background: #007E9C; text-align: center; height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">

									<th scope="col">Select</th>
									<th scope="col">Token Group ID</th>
									<th scope="col">Group Name</th>
									<th scope="col">Room No</th>
									<th scope="col">Department</th>
								</tr>
								<%
									@SuppressWarnings("unchecked")
											List<TokenGroup> list = (ArrayList<TokenGroup>) request
													.getAttribute("toGroupList");

											if (list.size() > 0) {
											int j=0;
												for (int i = list.size() - 1; i >= 0; i--) {
												j++;
								%>
								<tr id="<%=j%>"
									style="height: 25px; font-size: 14px; text-align: center"" onclick="rowSelecter(this.id);">

									<td><input type="radio" name="radioroomID" checked=""
										value="<%=list.get(i).getToken_group_id()%>" id="ra<%=j%>" name="r"></td>

									<td><%=list.get(i).getToken_group_id()%></td>

									<td><%=list.get(i).getToken_group_name()%></td>
									
									<%

									 String strRooms  =  list.get(i).getRoom_id();
										
									 String[] splitRooms = strRooms.split(",");
									 
									 String roomNo = "";
									 
									 
									 @SuppressWarnings("unchecked")
										List<Room> roomsList = (ArrayList<Room>) request
												.getAttribute("roomsList");

	                                	
									
									
									StringBuilder builder = new StringBuilder();
									
									for(int e= 0; e<splitRooms.length;e++)
									{
										for(int f= 0; f<roomsList.size();f++ )
	                                	{
	                                		
	                                		if((Integer.parseInt(splitRooms[e])) == roomsList.get(f).getRoom_id())
	                                		{
	                                			roomNo = roomsList.get(f).getRoom_no();
	                                			
	                                			builder.append(roomNo);
	                                			builder.append(",");
	                                			break;
	                                		}
	                                		
	                                	}
										
									}
									 
	                                	
										
										
										
									
									
									%>
									
									
									

									<td><%=builder.toString()%></td>

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






								</tr>




								<%
									}
											} else {
								%>

							</tbody>
						</table>
						<table align="center">
							<tr>

								<td id="response_mess"><%="Token Group list not founds !!"%></td>


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
								<input type="submit" name="roomdelete" id="formButton" value="Delete" onclick="if (confirm('Are you sure you want to delete?')) { form.action='./TokenGroupList'; } else { return false; }" />
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