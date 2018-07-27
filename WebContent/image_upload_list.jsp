<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" %>

<%@ page import="java.sql.*"%>

<%@ page import="com.dan.dqms.setting.AdvertiseBean"%>
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
<title>Images</title>
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

					<b>Advertisement Media</b>

				</TH>
			</TABLE>


			<div id="cont_shadow">


				<div id="shutdown">
					<form name="doctorForm" method="post"
						action="./ImagesListServlet">
					<table id="IP_tableTH" width="100%" align="center"
						color:#3b485f;="" line-height:1.6em;=""
						border-collapse:collapse;="" class="table-striped">
						<tbody>
				<tr	style="background: #007E9C; height: 40px; font-size: 16px; font-family: Arial, Helvetica, sans-serif;color:#ffffff;text-align: center;font-weight:bold;">


								<th scope="col">Select</th>
								<th scope="col">Department</th>
								<th scope="col">Device Name</th>


								<th scope="col">Image/video Name</th>

								<th scope="col">Name</th>




							</tr>

							<%
								@SuppressWarnings("unchecked")
									List<AdvertiseBean> imageList = (ArrayList<AdvertiseBean>) request
											.getAttribute("imagesList");
								
									if (imageList!=null && imageList.size() > 0) {
													int j=0;
										for (int i = imageList.size() - 1; i >= 0; i--) {
											j++;
							%>
							
							<tr
								id="<%=j%>"	style="height: 25px; font-size: 14px; text-align: center"" onclick="rowSelecter(this.id);">
							<td><input type="radio" name="radioroomID" checked=""
										value="<%=imageList.get(i).getId()%>" id="ra<%=j%>" name="r"></td>
							<td><%=imageList.get(i).getDepart_name()%></td>
							
								<td><%String name=""; 
								if(imageList.get(i).getDevice_type()==1)
								{
									name="MDU";
								}
								else
								{
									name="WDU";
								}
									%><%=name%></td>
								<td><%=imageList.get(i).getImage_name()%></td>
								<td><%=imageList.get(i).getName()%></td>			
							</tr>
							

							<%
								}
									} else {
							%>

							</tbody>
						</table>
						
						<table align="center">
							<tr>

								<td id="response_mess"><%="Image list not founds !!"%></td>


							</tr>
						</table>
						<%
							}
						%>
						
												<table align="center">

							<tr>
								<td><input type="submit" name="cmdDelete" value="Delete"
									id="formButton"></td>
							</tr>

						</table>
						
						</form>
						
						
							<%-- <tr>

								<td></td>
								<td></td>

								<td align="center" style="color: #B20000;"><%="Image video list is Blank !!"%></td>
							</tr>





							<%
								}
							%>




						</tbody>
					</table>
					
					<table align="center">
					<tr>
					<td>
					Using JSP EL to get message attribute value from request scope
						<p id="response_mess">${requestScope.message}</p>
					</td>
					</tr>
					</table>
 --%>


				</div>






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