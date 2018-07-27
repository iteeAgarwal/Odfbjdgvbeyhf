<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>
<%@ page import="java.util.*"%>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html>
<head>



<title>Header</title>

<link href="css_dqms/menu.css" type="text/css" rel="stylesheet" />
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />
</head>
<body>






	<%
		int userRoleID = (Integer) session.getAttribute("userRole");

		if (userRoleID == 1) {
	%>

	<!--===================================user role is super user start==========================================-->

	<ul id="menu">

		<li class="menu_right"><a href="#" class="drop">Current
				Status</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./CurrentDisplayList">Current Display</a></li>
						<li><a href="./TokenStatusList">Current Token Status</a></li>


					</ul>

				</div>

			</div></li>



		<li class="menu_right"><a href="#" class="drop">Reports</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./DoctorDaySummary">Doctor Wise</a></li>
						<li><a href="./DoctorLoginLogout">Doctor Login/Logout</a></li>
						<li><a href="./DepartmentDaySummary">Department Wise</a></li>
						<!-- <li><a href="./AllDoctorSummary">All Doctors Reports</a></li> -->
						<li><a href="./HospitalSummary">Todays's Hospital Summary</a>
						</li>
						<!-- <li><a href="#">System Health</a></li> -->

					</ul>

				</div>

			</div></li>


		<li class="menu_right"><a href="#" class="drop">Administration</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./NewUserList">User Admin</a></li>
						<li><a href="admin_change_password.jsp">Change Password</a></li>


					</ul>

				</div>

			</div></li>

		<li class="menu_right"><a href="#" class="drop">System
				Settings</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="#">General Settings</a></li>
						<li><a href="./ImagesListServlet">Advertisement Media</a> </li>
						<li><a href="./ImageUpload">Advertisement Media Upload</a></li>
						<li><a href="ShutDownClient">Device Maintenance</a></li>
						<li><a href="./DeviceDetailsListServlet">Device Settings</a></li>
						<li><a href="./DepartmentListServlet">Department Settings</a></li>
						<li><a href="./RoomNoList">Room Settings</a></li>
						<li><a href="./MDUGroupList">MDU Room Allotment</a></li>
						<li><a href="./TokenGroupList">Room Group</a></li>
						
						
						

						
						

					</ul>

				</div>

			</div></li>


		<li class="menu_right"><a href="./LogoutAction" >Logout</a>



		</li>




	</ul>








	<!--===================================user role is super user end==========================================-->

	<%
		} else if (userRoleID == 2) {
	%>



	<!--===================================user role is doctor start==========================================-->


	<ul id="menu">




		
		<li class="menu_right"><a href="./DCUCallSkip">Doctor Consol Unit</a></li>
		<li class="menu_right"><a href="./Doctor_summary">Doctor
				Summary</a></li>
		<li class="menu_right"><a href="./LogoutAction" >Logout</a>

	</ul>




	<!--===================================user role is super user end==========================================-->

	<%
		} else if (userRoleID == 3) {
	%>



	<!--===================================user role is Token Issuer start==========================================-->

	<ul id="menu">

		<li class="menu_right"><a href="./TokenStatusList">Current
				Token Status</a></li>
		<li class="menu_right"><a href="./TokenInsert">Token Issue</a></li>
		<li class="menu_right"><a href="./PatientListServlet">Patient
				Information</a></li>
		<li class="menu_right"><a href="./LogoutAction" >Logout</a>


	</ul>



	<!--===================================user role is super user end==========================================-->



	<%
		} else if (userRoleID == 4) {
	%>


	<!--===================================user role is ADMIN start==========================================-->

	<ul id="menu">

		<li class="menu_right"><a href="#" class="drop">Current
				Status</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./CurrentDisplayList">Current Display</a></li>
						<li><a href="./TokenStatusList">Current Token Status</a></li>


					</ul>

				</div>

			</div></li>



		<li class="menu_right"><a href="#" class="drop">Reports</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./DoctorDaySummary">Doctor Wise</a></li>
						<li><a href="./DoctorLoginLogout">Doctor Login/Logout</a></li>
						<li><a href="./DepartmentDaySummary">Department Wise</a></li>
						<!-- <li><a href="./AllDoctorSummary">All Doctors Reports</a></li> -->
						<li><a href="./HospitalSummary">Todays's Hospital Summary</a>
						</li>
						<!-- <li><a href="#">System Health</a></li> -->

					</ul>

				</div>

			</div></li>


		<li class="menu_right"><a href="#" class="drop">Administration</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./NewUserList">User Admin</a></li>
						<li><a href="admin_change_password.jsp">Change Password</a></li>


					</ul>

				</div>

			</div></li>

		<li class="menu_right"><a href="#" class="drop">System
				Setting</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="#">General Settings</a></li>
						<li><a href="./ImagesListServlet">Advertisement Media</a> </li>
						<li><a href="./ImageUpload">Advertisement Media Upload</a></li>
						<li><a href="ShutDownClient">Device Maintenance</a></li>
						<li><a href="./DeviceDetailsListServlet">Device Settings</a></li>
						<li><a href="./DepartmentListServlet">Department Settings</a></li>
						<li><a href="./RoomNoList">Room Settings</a></li>
						<li><a href="./MDUGroupList">MDU Room Allotment</a></li>
						<li><a href="./TokenGroupList">Room Group</a></li>

					</ul>

				</div>

			</div></li>


		<li class="menu_right"><a href="./LogoutAction" >Logout</a>



		</li>




	</ul>




	<!--===================================user role is super user end==========================================-->
	<%
		} else if (userRoleID == 5) {
	%>



	<!--===================================user role is HOD start==========================================-->

	<ul id="menu">

		<li class="menu_right"><a href="#">DCU</a>


			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./DCUCallSkip">Doctor Consol Unit</a></li>
						<li><a href="./Doctor_summary">Doctor Summary</a></li>


					</ul>

				</div>

			</div></li>

		<li class="menu_right"><a href="#" class="drop">Current
				Status</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./CurrentDisplayList">Current Display</a></li>
						<li><a href="./TokenStatusList">Current Token Status</a></li>


					</ul>

				</div>

			</div></li>



		<li class="menu_right"><a href="#" class="drop">Reports</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./DoctorDaySummary">Doctor Wise</a></li>
						<li><a href="./DoctorLoginLogout">Doctor Login/Logout</a></li>
						<li><a href="./DepartmentDaySummary">Department Wise</a></li>
						 <!-- <li><a href="./AllDoctorSummary">All Doctors Reports</a></li> -->
						<li><a href="./HospitalSummary">Todays's Hospital Summary</a>
						</li>
						<!-- <li><a href="#">System Health</a></li> -->

					</ul>

				</div>

			</div></li>

		<li class="menu_right"><a href="#" class="drop">System
				Setting</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="#">General Settings</a></li>
						<li><a href="./ImagesListServlet">Advertisement Media</a> </li>
						<li><a href="./ImageUpload">Advertisement Media Upload</a></li>
						<li><a href="ShutDownClient">Device Maintenance</a></li>
						<li><a href="./DeviceDetailsListServlet">Device Settings</a></li>
						<li><a href="./DepartmentListServlet">Department Settings</a></li>
						<li><a href="./RoomNoList">Room Settings</a></li>
						<li><a href="./MDUGroupList">MDU Room Allotment</a></li>
						<li><a href="./TokenGroupList">Room Group</a></li>

					</ul>

				</div>

			</div></li>



		<li class="menu_right"><a href="./LogoutAction" >Logout</a>
	</ul>





	<!--===================================user role is super user end==========================================-->
	<%
		} else {
	%>

	<!--===================================user role is not present start==========================================-->



	<ul id="menu">




		<li class="menu_right"><a href="#">TDU</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">

						<li><a href="./TokenStatusList">Current Token Status</a></li>
						<li><a href="./TokenInsert">Token Issue</a></li>
						<li><a href="./PatientListServlet">Patient Information</a></li>


					</ul>

				</div>

			</div></li>


		<li class="menu_right"><a href="#">DCU</a>


			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./DCUCallSkip">Call / Skip List</a></li>
						<li><a href="./Doctor_summary">Doctors Summary</a></li>


					</ul>

				</div>

			</div></li>

		<li class="menu_right"><a href="#" class="drop">Current
				Status</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./CurrentDisplayList">Current Display</a></li>
						<li><a href="./TokenStatusList">Current Token Status</a></li>


					</ul>

				</div>

			</div></li>



		<li class="menu_right"><a href="#" class="drop">Reports</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="./DoctorDaySummary">Doctor Wise</a></li>
						<li><a href="./DoctorLoginLogout">Doctor Login/Logout</a></li>
						<li><a href="./DepartmentDaySummary">Department Wise</a></li>
						<!-- <li><a href="./AllDoctorSummary">All Doctors Reports</a></li> -->
						<li><a href="./HospitalSummary">Todays's Hospital Summary</a>
						</li>
						<!-- <li><a href="#">System Health</a></li> -->

					</ul>

				</div>

			</div></li>



		<li class="menu_right"><a href="#" class="drop">System
				Setting</a>

			<div class="dropdown_1column align_right">

				<div class="col_1">

					<ul class="simple">
						<li><a href="#">General Settings</a></li>
						<li><a href="./ImagesListServlet">Advertisement Media</a> </li>
						<li><a href="./ImageUpload">Advertisement Media Upload</a></li>
						<li><a href="ShutDownClient">Device Maintenance</a></li>
						<li><a href="./DeviceDetailsListServlet">Device Settings</a></li>
						<li><a href="./DepartmentListServlet">Department Settings</a></li>
						<li><a href="./RoomNoList">Room Settings</a></li>
						<li><a href="./MDUGroupList">MDU Room Allotment</a></li>
						<li><a href="./TokenGroupList">Room Group</a></li>

					</ul>

				</div>

			</div></li>


		<li class="menu_right"><a href="./LogoutAction" >Logout</a>
	</ul>







	<!--===================================user role is  end==========================================-->

	<%
		}
	%>

</body>

</html>

