<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>
<%@ page import="org.dqms.db.Department"%>

<%@ page import="org.dqms.db.Patient"%>

<%@ page import="org.dqms.db.TokenGroup"%>

<%@ page import="org.dqms.db.Room"%>

<%@ page import="org.dqms.db.User"%>

<%@ page import="org.dqms.db.AppToken"%>

<%@ page import="com.dan.dqms.token.TokenStatusBean"%>


<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
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
<META NAME="DESCRIPTION" CONTENT="">
<META NAME="KEYWORDS" CONTENT="">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>Token Issue Form</title>
<!--[if lte IE 8]>
<script src="http://html5shiv.googlecode.com/svn/trunk/html5.js"></script>
<![endif]-->
<link rel="icon" href="./images_dqms/title.gif" type="image/gif" />
<link rel="stylesheet" href="css_dqms/style.css" type="text/css">
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />

<script src="js_dqms/token_generate.js" type="text/javascript"></script>

<script src="js_dqms/jquery.min.js" type="text/javascript"></script>

<script type="text/javascript">
    function checkPrint(){
    	var myPopup = window.open("popupcheck.htm", "", "directories=no,height=150,width=150,menubar=no,resizable=no,scrollbars=no,status=no,titlebar=no,top=0,location=no");
    	if (!myPopup)
    	    alert("Pop Ups NOT enabled for this webpage. Please enable popups.");
    	else {
    		
    	printrecipt();
    	}
    	}
    
    function printrecipt(){
         var divContents = $("#dvContainer").html();
        var printWindow = window.open('', '', 'height=400,width=800');
        printWindow.document.write('<html><head><title>PrintScreen</title>');
        printWindow.document.write('</head><body >');
        printWindow.document.write(divContents);
        printWindow.document.write('</body></html>');
        printWindow.document.close();
        printWindow.print();    
        
    }
    
    function PrintDiv() {
        var contents = document.getElementById("dvContainer").innerHTML;
        var frame1 = document.createElement('iframe');
        frame1.name = "frame1";
        frame1.style.position = "absolute";
        frame1.style.top = "-1000000px";
        document.body.appendChild(frame1);
        var frameDoc = frame1.contentWindow ? frame1.contentWindow : frame1.contentDocument.document ? frame1.contentDocument.document : frame1.contentDocument;
        frameDoc.document.open();
        frameDoc.document.write('<html><head><title></title>');
        frameDoc.document.write('</head><body>');
        frameDoc.document.write(contents);
        frameDoc.document.write('</body></html>');
        frameDoc.document.close();
        setTimeout(function () {
            window.frames["frame1"].focus();
            window.frames["frame1"].print();
            document.body.removeChild(frame1);
        }, 100);
        return false;
    }
    
    
    
    /* 
        $("#btnPrint").live("click", function () {
            var divContents = $("#dvContainer").html();
            var printWindow = window.open('', '', 'height=400,width=800');
            printWindow.document.write('<html><head><title>DIV Contents</title>');
            printWindow.document.write('</head><body >');
            printWindow.document.write(divContents);
            printWindow.document.write('</body></html>');
            printWindow.document.close();
            printWindow.print();
            printWindow.document.close();
        }); */
    </script>

<script>
function checkBrowserName(){
	
	var nAgt = navigator.userAgent;
	var browserName  = navigator.appName;
	
	var nameOffset,verOffset,ix;

	// In MSIE, the true version is after "MSIE" in userAgent
	if ((verOffset=nAgt.indexOf("MSIE"))!=-1) {
		console.log("IE");
	 browserName = "IE";
	 document.getElementById("h1").value=browserName;
	}
	// In Chrome, the true version is after "Chrome" 
	else if ((verOffset=nAgt.indexOf("Chrome"))!=-1) {
		
		browserName = "Chrome";
	 document.getElementById("h1").value=browserName;
	}

	// In Firefox, the true version is after "Firefox" 
	else if ((verOffset=nAgt.indexOf("Firefox"))!=-1) {
		
		browserName = "Firefox";
	 document.getElementById("h1").value=browserName;
	}
}
</script>

<script type="text/javascript">

function generateNew() {

	window.location.assign("./TokenStatusList");

}

$(document).ready(function(){
    $("#dropdo_walk").on("change", function(){
 if($('#dropdo_walk').val() == '2') {
            $('#appointlist_select').prop('disabled', false);
        } else {
        	$('#appointlist_select').prop('disabled', true); 
        } 
    });
});

</script>

</head>
<body>
<input type="hidden" id="h1"/>
<%String printStatus="n"; %>
<%printStatus="n"; %>

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

					<b> Token Generator </b>

				</TH>
			</TABLE>



			<%
				int staticDynamic = (Integer) request
							.getAttribute("staticDynamic");

					@SuppressWarnings("unchecked")
					List<Patient> patientlist = (ArrayList<Patient>) request
							.getAttribute("patList");

					String ptID1 = "";

					String ptName1 = "";

					String ptPhone1 = "";

					String ptIDCard1 = "";

					int docID1 = 0;

					int depID1 = 0;

					int roomID1 = 0;

					String rfid1 = "";

					String emptymess = "";

					boolean insur1 = false;

					if (patientlist.size() > 0) {

						ptID1 = String.valueOf(patientlist.get(0).patient_id);

						ptName1 = patientlist.get(0).patient_name;

						ptPhone1 = patientlist.get(0).phone_no;

						ptIDCard1 = patientlist.get(0).id_card_no;

						docID1 = patientlist.get(0).doctor_id;

						depID1 = patientlist.get(0).depart_id;

						roomID1 = patientlist.get(0).room_id;

						rfid1 = patientlist.get(0).rfid_no;

						insur1 = patientlist.get(0).insurance;

					} else {

						emptymess = (String) request.getAttribute("emptymess");

					}
			%>



			<div id="token_status_form">
				<p id="span_text1">Token Issue Form</p>

				<form name="drop_list1" method="post" action="./TokenInsert">
					<table align="center">

						<tr>
							<td><input type="text" name="patientID" value=""
								id="doc_td_value"></td>

							<td><input type="submit" name="findtoken" value="Find Token"
								id="formButton"></td>


						</tr>


					</table>
				</form>
				<form name="drop_list" method="post" action="./TokenInsert">
					<table id="doc_td_label_admin">

						<tr>
							<td>Patient ID:</td>
							<td><input type="text" name="ptID" value="<%=ptID1%>"
								id="doc_td_value" placeholder="Auto Generated" disabled></td>
						</tr>

						 <tr>
							<td>Patient Name:</td>
							<td><input type="text" name="ptName" value="<%=ptName1%>"
								required id="doc_td_value"></td>
						</tr> 


						 <tr>
							<td>Phone Number:</td>
							<td><input type="phone" name="phNo" value="<%=ptPhone1%>"
								id="doc_td_value"></td>
						</tr> 
						 <tr>
							<td>ID Card No:</td>
							<td><input type="text" name="idCardNo"
								value="<%=ptIDCard1%>" id="doc_td_value"></td>
						</tr> 

						<%
							if (staticDynamic == 1) {
						%>
						<!--------------------- static token generater start-------------------------------------->

						<tr>
							<td>Department ID:</td>
							<td><select class="dropdown_conn_class" id="deptID_id"
								name="deptID" onchange="GetOptionsStatic()">

									<option value="0" selected disabled style="display: none;">Select
										Department</option>

									<%
										int userDeptID = (Integer) request
														.getAttribute("userDeptID");

												@SuppressWarnings("unchecked")
												List<Department> deptList = (ArrayList<Department>) request
														.getAttribute("deptList");

												if (deptList.size() > 0) {

													for (Department listdept : deptList) {

														if (userDeptID == listdept.getDepart_id()) {
									%>
									<option value="<%=listdept.getDepart_id()%>"
										selected="selected"><%=listdept.getDepart_name()%></option>

									<%
										} else {
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
							<td>Room Number:</td>
							<td><SELECT NAME="rooms_list" class="dropdown_conn_class"
								id="room_id" onchange="GetDoctorOptions()" required>
									<Option value="" selected disabled style="display: none;">Select
										Room Number</option>





									<%
										@SuppressWarnings("unchecked")
												List<Room> roomsList = (ArrayList<Room>) request
														.getAttribute("roomsList");

												if (roomsList.size() > 0) {

													for (Room listdept : roomsList) {

														if (roomID1 == listdept.getRoom_id()) {
									%>
									<option value="<%=listdept.getRoom_id()%>" selected="selected"><%=listdept.getRoom_no()%></option>


									<%
										} else {
									%>

									 <option value="<%=listdept.getRoom_id()%>"><%=listdept.getRoom_no()%></option>

									<%
										}
													}
												}
									%>

							</SELECT></td>
						</tr>

						<tr>
							<td>Doctor Name:</td>
							<td><SELECT NAME="doctor_list" class="dropdown_conn_class"
								id="doc_id"  onchange="GetAppointmentListStatic();" required>
									<Option value="" selected disabled style="display: none;">Select
										Doctor Name</option>


									<%
										@SuppressWarnings("unchecked")
												List<User> doctorList = (ArrayList<User>) request
														.getAttribute("doctorList");

												if (doctorList.size() > 0) {

													for (User listdept : doctorList) {

														if (docID1 == listdept.getUser_id()
																&& listdept.getRole_id() == 2) {
									%>

									<option value="<%=listdept.getUser_id()%>" selected="selected"><%=listdept.getName()%></option>

									<%
										} else {
									%>

									 <option value="<%=listdept.getUser_id()%>"><%=listdept.getName()%></option>
 
									<%
										}
													}
												}
									%>

							</SELECT></td>
						</tr>

						<tr>
							<td>Appointment/Walking:</td>

							<td><select class="dropdown_conn_class" id="dropdo_walk" name="appWalk" required>
									<option value="" selected disabled style="display: none;">Select
										Walk-in/Appointment</option>
									<option value="1" onclick="makeDisable();">Walk-in</option>
									<option value="2" onclick="makeEnable();">Appointment</option> 


							</select></td>
						</tr>

						<tr>
							<td>Select Token:</td>

							<td><select class="dropdown_conn_class" id="appointlist_select" name="select_token" required>
									<option value=""  style="display: none;" >Select
										Token</option>
									
									<%
										for (int i=1; i<51; i++) {
									%>
									<option value="<%=i%>">C<%=i%></option>
									<%
										}
									%>

							</select></td>
						</tr> 
						<!--------------------- static token generator end-------------------------------------->
						<%
							} else {
						%>
						<!--------------------- dynamic token generator start-------------------------------------->
						<tr>
							<td>Department ID:</td>
							
							<td><select class="dropdown_conn_class" id="deptID_id"
								name="deptID" onchange="GetOptions()">

									<option value="0" selected disabled style="display: none;">Select
										Department</option>

									<%
										int userDeptID = (Integer) request
														.getAttribute("userDeptID");

												@SuppressWarnings("unchecked")
												List<Department> deptList = (ArrayList<Department>) request
														.getAttribute("deptList");

												if (deptList.size() > 0) {

													for (Department listdept : deptList) {

														if (userDeptID == listdept.getDepart_id()) {
									%>
									<option value="<%=listdept.getDepart_id()%>"
										selected="selected"><%=listdept.getDepart_name()%></option>

									<%
										} else {
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
							<td>Room Group Name:</td>

							<td><SELECT NAME="room_group_list"
								class="dropdown_conn_class" id="room_group_id" required
								onchange="GetRoomOptions()">
									<Option value="" selected disabled style="display: none;">Select
										Room Group</option>
									<%
										@SuppressWarnings("unchecked")
												List<TokenGroup> groupsList = (ArrayList<TokenGroup>) request
														.getAttribute("groupsList");

												if (groupsList.size() > 0) {

													for (TokenGroup listdept : groupsList) {
									%>

									<option value="<%=listdept.getToken_group_id()%>"><%=listdept.getToken_group_name()%></option>

									<%
										}
												}
									%>

							</SELECT></td>
						</tr>
						<tr>
							<td>Room Number:</td>
							<td><SELECT NAME="rooms_list" class="dropdown_conn_class"
								id="room_id" onchange="GetDoctorOptions()">
									<Option value="0" selected disabled style="display: none;">Select
										Room Number</option>


									<%
										@SuppressWarnings("unchecked")
												List<Room> roomsList = (ArrayList<Room>) request
														.getAttribute("roomsList");

												if (roomsList.size() > 0) {

													for (Room listdept : roomsList) {

														if (roomID1 == listdept.getRoom_id()) {
									%>
									<option value="<%=listdept.getRoom_id()%>" selected="selected"><%=listdept.getRoom_no()%></option>


									<%
										} else {
									%>

									<option value="<%=listdept.getRoom_id()%>"><%=listdept.getRoom_no()%></option>

									<%
										}
													}
												}
									%>

							</SELECT></td>
						</tr>

						<tr>
							<td>Doctor Name:</td>
							<td><SELECT NAME="doctor_list" class="dropdown_conn_class"
								id="doc_id">
									<Option value="" selected disabled style="display: none;">Select
										Doctor Name</option>
									<%
										@SuppressWarnings("unchecked")
												List<User> doctorList = (ArrayList<User>) request
														.getAttribute("doctorList");

												if (doctorList.size() > 0) {

													for (User listdept : doctorList) {

														if (docID1 == listdept.getUser_id()
																&& listdept.getRole_id() == 2) {
									%>

									<option value="<%=listdept.getUser_id()%>" selected="selected"><%=listdept.getName()%></option>

									<%
										} else {
									%>

									<option value="<%=listdept.getUser_id()%>"><%=listdept.getName()%></option>

									<%
										}
													}
												}
									%>

							</SELECT></td>
						</tr>

						<tr>
							<td>Appointment/Walking:</td>

							<td><select class="dropdown_conn_class" id="dropdo_walk" name="appWalk" required>
									<option value="" selected disabled style="display: none;">Select
										Walk-in/Appointment</option>
									<option value="1" onclick="makeDisable();">Walk-in</option>
									<option value="2" onclick="makeEnable();">Appointment</option>


							</select></td>
						</tr>

						<tr>
							<td>Select Token:</td>
							<td><select class="dropdown_conn_class" id="appointlist_select" name="select_token" required>
									<option value=""  style="display: none;" >Select
										Token</option>
									
									<%
										for (int i=1; i<51; i++) {
									%>
									<option value="<%=i%>">C<%=i%></option>
									<%
										}
									%>

							</select></td>
						</tr>
						<%
							}
						%>
						
						
						<!--------------------- dynamic token generator end-------------------------------------->

						<tr>
							<td>RFID:</td>
							<td><input type="text" name="rFid" value="<%=rfid1%>"
								id="doc_td_value"></td>
						</tr>

						<%
							String checked = "";

								if (insur1) {
									checked = "checked";
								} else {
									checked = "unchecked";
								}
						%>
						<tr>
							<td>Insurance</td>
							<td align="center"><input type="checkbox" name="insur"
								value="true" <%=checked + "=''"%>></td>
						</tr>

						<tr>

							<td><input type="submit" name="gettoken" value="Get Token"
								id="formButton"></td>
							<td><input type="button" name="tokenCancel" value="Cancel"
								id="formButton" onclick="generateNew()"></td>
						</tr>

					</table>
					<table align="center" style="color: #B20000;">

						<tr>
							<td><%=emptymess%></td>
						</tr>
					</table>



				</form>
			</div>
			<div id="token_status_display">
				<p id="span_text1">Token Status</p>
				<div id="token_status_display_scroll">

					<table id="IP_tableTH" width="100%" align="center"
						color:#3b485f;="" line-height:1.6em;=""
						border-collapse:collapse;="">
						<tbody>
							<tr
								style="background: #007E9C; height: 40px; font-size: 14px; font-family: Arial, Helvetica, sans-serif;">

								<th style="color: #fff; text-align: center; font-weight: bold;"
									scope="col">Doctor Name</th>

								<th style="color: #fff; text-align: center; font-weight: bold;"
									scope="col">Token Group Name</th>
								<th style="color: #fff; text-align: center; font-weight: bold;"
									scope="col">Total Token Walk-in</th>
								<th style="color: #fff; text-align: center; font-weight: bold;"
									scope="col">Current Token Walk-in</th>
									<th style="color: #fff; text-align: center; font-weight: bold;"
									scope="col">Total Token Appointment</th>
								<th style="color: #fff; text-align: center; font-weight: bold;"
									scope="col">Current Token Appointment</th>



							</tr>
							<%
								@SuppressWarnings("unchecked")
									ArrayList<TokenStatusBean> tokenStatus = (ArrayList<TokenStatusBean>) request
											.getAttribute("tokenStatus");

									if (tokenStatus.size() > 0) {

										for (TokenStatusBean listToken : tokenStatus) {
							%>
							<tr
								style="background: #EFEFEF; height: 25px; font-size: 14px; text-align: center"">


								<td><%=listToken.getUser_id_to()%></td>
								<td><%=listToken.getToken_group_id_to()%></td>
								<td><%=listToken.getTotal_token_to()%></td>
								<td><%=listToken.getCurrent_token_to()%></td>
								
								<td><%=listToken.getTotal_token_appoint()%></td>
								<td><%=listToken.getCurrent_token_appoint()%></td>





							</tr>




							<%
								}
									} else {
							%>
							<tr>
								<td></td>
								<td></td>
								<td></td>

								<td id="response_mess"><%="Token Status list is Blank !!"%></td>


							</tr>


							<%
								}
							%>




						</tbody>
					</table>



<% 
							String browName="";	
							if(emptymess.equals("Token generate successfully..!!")){
								%>
								<script>
								checkBrowserName();
								var x = document.getElementById("h1").value;
								if(x=="IE"){
								</script>
									<div id="dvContainer" style="display:none">
       							<% 
       							String ss=(String)request.getAttribute("p_data");
       							
       							if(ss!=null){
       								
									%> <code><font size="4px"><%
									%> <center>*********************</center> <%
									%> <br><center><b>Lok Nayak Hospital (New Delhi) </b></center>
									 <center>*********************</center> 
									<%
									long l=System.currentTimeMillis();
									java.util.Date d=new java.util.Date(l);
									SimpleDateFormat sim=new SimpleDateFormat("MMM dd,yyyy");
									SimpleDateFormat sim2=new SimpleDateFormat("hh:mm a");
									String s=sim.format(d);
									String s1=sim2.format(d);
									%><center><b><font size="3px"><%=s%>&nbsp;<%=s1%></font></b></center> <br><br> <%
									String ssArr[]=ss.split("~");
									%>
									<table  align="center" border="0" cellpadding="10" cellspacing="2">
									<tr >
									<td height="30" ><font size="3px"><b>Dr.Name-</b></</font></td><td><font size="2px"><strong><%out.println(ssArr[0]);%></strong></font></td>
									</tr>
									<tr  >
									<td height="30" ><font size="3px"><b>Room NO-</b></font></td><td ><font size="2px"><strong><%out.println(ssArr[1]);%></strong></font></td>
									</tr>
									<tr  >
									<td height="30"  ><font size="3px"><b>Department-</b></font></td><td ><font size="2px"><strong><%out.println(ssArr[2]);%></strong></font></td>
									</tr>
									<tr  >
									<td height="30" ><font size="3px"><b>Current Serving Token-</b></font></td><td ><font size="6px"><strong><%out.println(ssArr[3]);%></strong></font></td>
									</tr>
									<tr >
									<td height="30" ><font size="3px"><b>Your Token-</font></td><td align="right"></td>
									</tr>
									<tr  align="center"> <td height="30"   colspan="2"><font size="30px"><strong><%out.println(ssArr[4]);%></strong></font></td></tr>
									</table>
								
									  <center>*********END*********</center> 
									<%
									printStatus="y";
								}
       							%>
       							</font>
       							</code>
    </div>
									
									<script>
								}
								else if(x=="Firefox"){
									</script>
									<div id="dvContainer" style="display:none">
       							<% 
       							ss=(String)request.getAttribute("p_data");
								if(ss!=null){
									%><code><font size="+2"><%
									%><center>*********************************</center><%
									%><br><center>Lok Nayak Hospital (New Delhi)</center><br>
									<center>*********************************</center>
									<%
									long l=System.currentTimeMillis();
									java.util.Date d=new java.util.Date(l);
									SimpleDateFormat sim=new SimpleDateFormat("MMM dd,yyyy");
									SimpleDateFormat sim2=new SimpleDateFormat("hh:mm:a");
									String s=sim.format(d);
									String s1=sim2.format(d);
									%><center><font size="-2"><%=s%>&nbsp;&nbsp;&nbsp;&nbsp;<%=s1%></font></center> <br><br> <%
									String ssArr[]=ss.split("~");
									%>
									<table align="center" border="0" cellpadding="10" cellspacing="2">
									<tr>
									<td height="40" ><strong>Dr.Name-</strong></td><td><strong><%out.println(ssArr[0]);%></strong></td>
									</tr>
									<tr>
									<td height="40" ><strong>Room No-</strong></td><td><strong><%out.println(ssArr[1]);%></strong></td>
									</tr>
									<tr>
									<td height="50" ><strong>Department-</strong></td><td><strong><%out.println(ssArr[2]);%></strong></td>
									</tr>
									<tr>
									<td height="50" ><strong>Current Serving Token-</strong></td><td><strong><%out.println(ssArr[3]);%></strong></td>
									</tr>
									<tr>
									<td height="50" ><strong>Your Token-</strong></td><td ><strong><%out.println(ssArr[4]);%></strong></td>
									</tr>
									</table>
									<br><br>
								<center>	************END************</center>
									<%
									printStatus="y";
								}
       							%></font>
       							</code>
    </div>
									<script>
								}
								else if(x=="Chrome"){
									</script>
									<div id="dvContainer" style="display:none" >
       							<% 
       							ss=(String)request.getAttribute("p_data");
								if(ss!=null){
									
								   %> <code> <%
							       %> <center>*********************</center> <%
									%> <br><center>Lok Nayak Hospital (New Delhi)</center><br>
									 <center>*********************</center> 
									<%
									long l=System.currentTimeMillis();
									java.util.Date d=new java.util.Date(l);
									SimpleDateFormat sim=new SimpleDateFormat("MMM dd,yyyy");
									SimpleDateFormat sim2=new SimpleDateFormat("hh:mm:a");
									String s=sim.format(d);
									String s1=sim2.format(d);
									%><center><font size="-1"><%=s%>&nbsp;&nbsp;&nbsp;&nbsp;<%=s1%></font></center> <br><br> <%
									String ssArr[]=ss.split("~");
									%>
									<table align="center" border="0" cellpadding="10" cellspacing="2">
									<tr>
									<td height="40" ><strong>Dr. Name-</strong></td><td ><strong><%out.println(ssArr[0]);%></strong></td>
									</tr>
									<tr>
									<td height="40" ><strong>Room No-</strong></td><td><strong><%out.println(ssArr[1]);%></strong></td>
									</tr>
									<tr>
									<td height="40" ><strong>Department-</strong></td><td ><strong><%out.println(ssArr[2]);%></strong></td>
									</tr>
									<tr>
									<td height="40" ><strong>Current Serving Token-</strong></td><td><strong><%out.println(ssArr[3]);%></strong></td>
									</tr>
									<tr>
									 <td height="40" ><strong>Your Token-</strong></td><td ><strong><%out.println(ssArr[4]);%></strong></td>
									</tr>
									</table>
									<br><br>
								<!-- <center>	*********END*********</center> -->
									<%
									printStatus="y";
								}
       							%>
       							</code>
    </div>
									<script>
								}
								</script>
								</script>
								<%
								emptymess="";
								request.setAttribute("emptymess", "");
							}
							%>
							 <%if(printStatus.equals("y")){
	 %>
	 <script>
	 PrintDiv();
	 </script>
	 <%
	 printStatus="n";
 } %>




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