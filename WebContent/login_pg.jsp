<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>


<%@ page import="java.util.*"%>
<%@ page import="org.dqms.db.Room"%>
<%@ page import="org.dqms.db.User"%>
<%@ page import="org.dqms.db.Department"%>

<!-- <!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd"> -->
<!DOCTYPE html>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display Report</title>
<link rel="icon" href="./images_dqms/title.gif" type="image/gif" />
<link href="css_dqms/page.css" type="text/css" rel="stylesheet" />
<link href="css_dqms/style.css" type="text/css" rel="stylesheet" />

</head>
<body>
	<div id="header">
		<div id="headerlogo">

			<!-- <div id="headerleft">
				<a id="hyper_a" href="home_pag.jsp">Home </a>

			</div> -->

		</div>
	</div>



	<!--  report table start -->



	<div id="report_table1">
		<div id="login_form_admin">
			<fieldset>
				<legend></legend>
				<section class="loginform cf">
				<form name="login" action="./LoginAction" method="post"
					accept-charset="utf-8">
					<table align="center">
						<tr
							style="font-family: Arial, Helvetica, sans-serif; color: #920706; font-size: 18px; font-weight: bold;">
							<td>Login</td>
						</tr>
					</table>
					<table align="center">
						<tr>
							<td><label for="usermail">User Name</label></td>
							<td><input type="text" name="username"
								placeholder="yourname" required id="doc_td_value"></td>
						</tr>
						<tr>
							<td><label for="password">Password</label></td>
							<td><input type="password" name="password"
								placeholder="password" required id="doc_td_value"></td>
						</tr>
					</table>
					<table align="center">
						<tr>
							<td ><input type="submit"
								name="login" value="Login"></td>

						</tr>
					</table>
					<table align="center">
						<tr>
							<td>
								<p id="response_mess">${requestScope.loginMess}</p>
							</td>
						</tr>
					</table>





				</form>
				</section>

			</fieldset>


		</div>

		<!--  report table End -->
	</div>


	<%@include file="footer_pg.jsp"%>
</body>
</html>