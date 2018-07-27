package com.dan.dqms.dcu;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.config.SystemSetting;
import org.dqms.db.Department;
import org.dqms.db.Room;
import org.dqms.db.Token;
import org.dqms.util.Print;
import org.json.simple.JSONObject;
import com.dan.dqms.returnlist.AppWalkDoctorWise;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.RoomsList;

@WebServlet("/DCUCallSkip")
public class DCUCallSkip extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	int TotalSkippedTokenNo=0;
	int CurrentSkippedTokenNo=0;	
	int current_token =0;
	int total_token = 0;
	int token_issue = 0;
	
	HttpSession httpsession;
	public DCUCallSkip() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		this._doWork(request, response);
		
	}

	private void _doWork( HttpServletRequest request, HttpServletResponse response)
	        throws IOException{
		
		httpsession =request.getSession(false);
		Print.logInfo("DCUCallSkip", "DCUCallSkip:_doWork");
		if (httpsession == null) {
			response.sendRedirect("login_pg.jsp");
			return;
		}
		int userID =(Integer) httpsession.getAttribute("_usrID");
		
		int isAppWalk =1;
		int callType = 0;
		int listTypeBool=0;
		int appointmentTokenNo=0;
		int walkinTokenNo=0;
		
		DCUCallSkipHelper dbhelper = new DCUCallSkipHelper();
		
		String appWalkDropDown = request.getParameter("appWalkDropDown");
		String btncall = request.getParameter("btncall");
		String btnskip = request.getParameter("btnskip");
		String btnlisttype = request.getParameter("btnlisttype");
		String hiddentokenNo = request.getParameter("hiddentokenNo");
		String hiddenlistType = request.getParameter("hiddenlistType");
		String hiddentokenNoWalk = request.getParameter("hiddentokenNoWalk");
		
		/*
		System.out.println("");
		System.out.println("");
		System.out.println("");
		
		System.out.println("appWalkDropDown:" + appWalkDropDown);
		System.out.println("btncall:" + btncall);
		System.out.println("btnskip:" + btnskip);
		System.out.println("btnlisttype:" + btnlisttype);
		System.out.println("hiddenlistType:" + hiddenlistType);
		System.out.println("hiddentokenNo:" + hiddentokenNo);
		System.out.println("hiddentokenNoWalk:" + hiddentokenNoWalk);
		*/
		
		
		if(appWalkDropDown != null){
			if(appWalkDropDown.equalsIgnoreCase("1")){
				request.setAttribute("isAppWalk", true);
				isAppWalk = 1;
			}else{
				request.setAttribute("isAppWalk", false);
				isAppWalk=0;
			}
			
		}
		
		if(hiddentokenNo != null){			
			
			appointmentTokenNo=Integer.parseInt(hiddentokenNo);
		}
		if(hiddentokenNoWalk != null){
			
			walkinTokenNo=Integer.parseInt(hiddentokenNoWalk);
		}
		
		if(hiddenlistType != null){
			if(hiddenlistType.equalsIgnoreCase("true")){
				request.setAttribute("listTypeBool", true);
				listTypeBool = 1;
			}else{
				request.setAttribute("listTypeBool", false);
				listTypeBool=0;
			}
		}
		
		if(btncall != null){			
			
			if(btncall.equalsIgnoreCase("Treat")){
				// update time 
				callType=3;
				
				
				request.setAttribute("callType", 0);
				if((Boolean)request.getAttribute("isAppWalk") == true){
					
					isAppWalk=1;
					request.setAttribute("isAppWalk", true);
					if(hiddentokenNo.equalsIgnoreCase("0")){
						//error msg
						Print.logInfo("Error while treat appointment. Token Number empty. UserID: " + userID);
					}else{
						//update DB appointment
						JSONObject result = dbhelper.DCUApi("", 1, callType, listTypeBool,appointmentTokenNo , userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID
						String res = parseResult(result, 1);
						request.setAttribute("resultError", res);
						if(!res.equalsIgnoreCase("")){
							//should not block here because after treat call will be called
						}
					}
				}else{
					isAppWalk=0;
					
					request.setAttribute("isAppWalk", false);
					if(walkinTokenNo==0){
						//error msg
						Print.logInfo("Error while treat walkin. Token Number empty. UserID: " + userID);
					}else{
						//update DB walkin
						
						
						JSONObject result = dbhelper.DCUApi("", 1, callType, listTypeBool,walkinTokenNo, userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID
						String res=parseResult(result, 1);
						request.setAttribute("resultError", res);
						if(!res.equalsIgnoreCase("")){
							//should not block here because after treat call will be called
						}
					}
				}
				
				request.setAttribute("appointmentTokenNo", 0);
				request.setAttribute("walkinTokenNo", 0);
				
			}else{
				
				callType=1;
				request.setAttribute("callType", 1);
				if((Boolean)request.getAttribute("isAppWalk") == true){
					isAppWalk=1;
					request.setAttribute("isAppWalk", true);
					if(hiddentokenNo.equalsIgnoreCase("0")){ 
						//error msg
						Print.logInfo("Error while call appointment. Token Number empty. UserID: " + userID);
					}else{
						
						request.setAttribute("appointmentTokenNo", Integer.parseInt(hiddentokenNo)); //token number sent by browser
						request.setAttribute("walkinTokenNo", 0);
						JSONObject result = dbhelper.DCUApi("", 1, callType, listTypeBool,Integer.parseInt(hiddentokenNo) , userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID
						
						String res=parseResult(result, 1);
						request.setAttribute("resultError", res);
						if(!(res == "" || res.equalsIgnoreCase("") || res ==null)){
							request.setAttribute("callType", 0);
							callType=0;
						}
					}
				}else{
					isAppWalk=0;
					Print.logInfo("DCUCallSkip-> Call -> Walkin API request : RoomNo= , function Type=1, calltype=" +callType+", listType=" +listTypeBool+ ",hiddentokenno=" + Integer.parseInt(hiddentokenNo)+", userid="+ userID+ ", isAppWalk=" + isAppWalk);
					JSONObject result;
					if(listTypeBool == 1){
						result= dbhelper.DCUApi("", 1, callType, listTypeBool,0, userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID
					}else{
						result = dbhelper.DCUApi("", 1, callType, listTypeBool,Integer.parseInt(hiddentokenNo), userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID						
					}
					Print.logInfo("DCUCallSkip-> Call -> Walkin API response: " + result);
					int current_token_no = 0;
					//if(listTypeBool !=1){
						current_token_no = parseCurrentTokenNo(result);
					//}
					String res = parseResult(result, 1);
					request.setAttribute("resultError", res);
					if(!(res == "" || res.equalsIgnoreCase("") || res ==null)){
						callType=0;
						request.setAttribute("callType", 0);
					}
					request.setAttribute("isAppWalk", false);
					request.setAttribute("appointmentTokenNo", 0); //
					request.setAttribute("walkinTokenNo", current_token_no); //token no issued by db //change the token no with result from db

				}
			}
		}else if(btnskip != null){			
					

					callType=2;
					request.setAttribute("callType", 0);
					if((Boolean)request.getAttribute("isAppWalk") == true){
						
						isAppWalk=1;
						request.setAttribute("isAppWalk", true);
						if(hiddentokenNo.equalsIgnoreCase("0")){
							//error msg
							Print.logInfo("Error while SKIP appointment. Token Number empty. UserID: " + userID);
						}else{
							//update DB appointment
							JSONObject result = dbhelper.DCUApi("", 1, callType, listTypeBool,appointmentTokenNo , userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID
							String res = parseResult(result, 1);
							request.setAttribute("resultError", res);
							if(!res.equalsIgnoreCase("")){
								//should not block here because after treat call will be called
							}
						}
					}else{
						isAppWalk=0;
						
						request.setAttribute("isAppWalk", false);
						if(walkinTokenNo==0){
							//error msg
							Print.logInfo("Error while treat walkin. Token Number empty. UserID: " + userID);
						}else{
							//update DB walkin
							
							
							JSONObject result = dbhelper.DCUApi("", 1, callType, listTypeBool,walkinTokenNo, userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID
							String res=parseResult(result, 1);
							request.setAttribute("resultError", res);
							if(!res.equalsIgnoreCase("")){
								//should not block here because after treat call will be called
							}
						}
					}
					
					request.setAttribute("appointmentTokenNo", 0);
					request.setAttribute("walkinTokenNo", 0);			
				
		}else if(btnlisttype != null){			
			
			if(btnlisttype.equalsIgnoreCase("Cancel")){
				callType=4;
				request.setAttribute("callType", 0);
				if((Boolean)request.getAttribute("isAppWalk") == true){

					isAppWalk=1;
					request.setAttribute("isAppWalk", true);
					if(hiddentokenNo.equalsIgnoreCase("0")){
						//error msg
						Print.logInfo("Error while Cancel appointment. Token Number empty. UserID: " + userID);
					}else{
						//update DB appointment
						JSONObject result = dbhelper.DCUApi("", 1, callType, listTypeBool,appointmentTokenNo , userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID
						String res = parseResult(result, 1);
						request.setAttribute("resultError", res);
						if(!res.equalsIgnoreCase("")){
							//should not block here because after treat call will be called
						}
					}
				}else{
					isAppWalk=0;
					
					request.setAttribute("isAppWalk", false);
					if(walkinTokenNo==0){
						//error msg
						Print.logInfo("Error while treat walkin. Token Number empty. UserID: " + userID);
					}else{
						//update DB walkin
						
						
						JSONObject result = dbhelper.DCUApi("", 1, callType, listTypeBool,walkinTokenNo, userID, isAppWalk); //roomNo, functionType, type, listType, lastTokenNo, userID, walkAppID
						String res=parseResult(result, 1);
						request.setAttribute("resultError", res);
						if(!res.equalsIgnoreCase("")){
							//should not block here because after treat call will be called
						}
					}
				}
				
				request.setAttribute("appointmentTokenNo", 0);
				request.setAttribute("walkinTokenNo", 0);	
			}else if(btnlisttype.equalsIgnoreCase("Skip List")){
				request.setAttribute("listTypeBool", true);
			}else{
				request.setAttribute("listTypeBool", false);
			}
		}
		
		if (request.getAttribute("callType") == null){
			request.setAttribute("callType", 0);
		}
		if (request.getAttribute("isAppWalk") == null){
			request.setAttribute("isAppWalk", true);
		}
		if (request.getAttribute("appointmentTokenNo") == null){
			request.setAttribute("appointmentTokenNo", 0);
		}
		if (request.getAttribute("walkinTokenNo") == null){
			request.setAttribute("walkinTokenNo", 0);
		}
		if (request.getAttribute("listTypeBool") == null){
			request.setAttribute("listTypeBool", false);
		}
		if (request.getAttribute("resultError") == null){
			request.setAttribute("resultError", "");
		}
		
		
		
		
		
		
		/*

		// int calledFlag = 0;

		int freshListFlag = 0;

		int skipListEmpty = 0;

		int skipListOver = 0;


		String call = request.getParameter("Call");

		String skip = request.getParameter("skip");

		String Treat = request.getParameter("Treat");

		String Cancel = request.getParameter("Cancel");

		String listhange = request.getParameter("List Change");

		String walkRadio = request.getParameter("walkRadio");

		String showPatientList = request.getParameter("showPatientList");*/

		try {
	
			/************ Doctor details for the header of DCU *********************/
			String userDeptName = "";
			String userRoomNum = "";
			/* GET user name */
			String userName =(String) httpsession.getAttribute("_usrName");
			int UserID =(Integer) httpsession.getAttribute("_usrID");
			int userDeptID = (Integer)httpsession.getAttribute("_usrDptID");
			int userRoomID = (Integer)httpsession.getAttribute("_RoomID");
			
			DepartmentList deptListOb = new DepartmentList();
			List<Department> deptList = deptListOb.getDeptList();
			for (Department list : deptList) {
				if (userDeptID == list.getDepart_id()) {
					userDeptName = list.getDepart_name();
				}
			}

			RoomsList roomListOb = new RoomsList();
			List<Room> roomsList = roomListOb.getRooms();
			for (Room list : roomsList) {
				if (userRoomID == list.getRoom_id()) {
					userRoomNum = list.getRoom_no();
				}
			}

			request.setAttribute("userIDSta", UserID);
			request.setAttribute("userNameSta", userName);
			request.setAttribute("userDeptName", userDeptName);
			request.setAttribute("userRoomNum", userRoomNum);
			
			/**********************************************************************************/
			
			/************************  Service data for current status  *********************************
			
			int currentToken = 0;
			int totalToken = 0;
			
			DBAdapterApollo bbApo = new DBAdapterApollo();
			TokenDocSummary tokenDocSummary = bbApo.TokenSummary_UserId(UserID);
			if (tokenDocSummary == null) {
				Print.logInfo("Null Exception when get current token and total token from DBAdapter ");
			} else {
				currentToken = new Integer(tokenDocSummary.getCurrent_token());
				totalToken = new Integer(tokenDocSummary.getTotal_token());
			}

			/******************************************************************************************/

			/************************** Get Appointment \ Walkin patients List *****************************/

			AppWalkDoctorWise appWalkOb = new AppWalkDoctorWise();

			ArrayList<Token> listTokenApp = appWalkOb.AppTokenDetails_doctorWise(UserID, 2);
			ArrayList<Token> listTokenWalk = appWalkOb.AppTokenDetails_doctorWise(UserID, 1);

			request.setAttribute("listTokenApp", listTokenApp);
			request.setAttribute("listTokenWalk", listTokenWalk);

			/******************************************************************************************/

			
			/* SET API attributes */
			/*JsonParsingWithCall Jsob = new JsonParsingWithCall();

			URLConnectionOpen urlConnectionOpen = new URLConnectionOpen();

			StringBuffer sb = new StringBuffer();

			sb.append(SystemSetting.apiUrl);
			*/
			/*********************************** call button ***************************************************/

			/*if (call != null) {

				
				// calledFlag = 1;

				// action call button

				// API Call

				sb.append("t=1");

				sb.append("&");

				sb.append("listtype=" + StaticVariables.getListType());

				sb.append("&");

				sb.append("f=1");

				sb.append("&");

				//sb.append("did=" + StaticVariables.getUserID());
				
				sb.append("did=" + httpsession.getAttribute("_usrID"));

				if (StaticVariables.getListType() == 1) {

					StaticVariables.setCurrent_token(0);

					sb.append("&");

					sb.append("tokenno=" + StaticVariables.getSkipHigh());

				} else {

					sb.append("&");

					sb.append("tokenno=" + StaticVariables.getCurrent_token());
				}

				Print.logInfo("Call API URL" + sb.toString());

			//	if (walkRadio != null) {
					String resStr = urlConnectionOpen.openUrl(sb.toString());

					StaticVariables.setCurrent_token(Jsob
							.jsonParserWithCall(resStr));
					Print.logInfo("Call API Response" + resStr);
					
					StaticVariables.setCalledFlag(1);
				//}
				

				Print.logInfo("Current_token"
						+ StaticVariables.getCurrent_token());

			}*/

			/*********************************** skip button ***************************************************/

			/*else if (skip != null) {
				// action skip button
				StaticVariables.setCalledFlag(0);

				// API Call

				sb.append("t=2");

				sb.append("&");

				sb.append("listtype=" + StaticVariables.getListType());

				sb.append("&");

				sb.append("f=1");

				sb.append("&");

				//sb.append("did=" + StaticVariables.getUserID());

				sb.append("did=" + httpsession.getAttribute("_usrID"));
				
				sb.append("&");

				sb.append("tokenno=" + StaticVariables.getCurrent_token());

				Print.logInfo("Skip API URL" + sb.toString());

				String resStr = urlConnectionOpen.openUrl(sb.toString());

				Jsob.jsonParserWithSkip(resStr);

				Print.logInfo("Skip API Response" + resStr);

				// request.setAttribute("msg", " skip success");

			}*/

			/*********************************** listhange button ***************************************************/

			/*else if (listhange != null) {
				// action skip button
				StaticVariables.setCalledFlag(0);

				// listType = 1;

				StringBuffer sb1 = new StringBuffer();

				StaticVariables.setCurrent_token(0);

				if (StaticVariables.getListType() == 0) {
					StaticVariables.setListType(1);
					StaticVariables.setSkipHigh(0);
				} else {
					StaticVariables.setListType(0);
				}
				sb1.append(SystemSetting.apiUrlService);

				sb1.append("d=" + userIDSta);

				sb1.append("&");

				sb1.append("t=" + StaticVariables.getListType());

				if (StaticVariables.getListType() == 1) {
					String resStr = urlConnectionOpen.openUrl(sb1.toString());
					Jsob.jsonParserWithListChange(resStr);
					Print.logInfo("listhange API Response" + resStr);
				}

				// request.setAttribute("msg", " listhange success");

			}*/

			/*********************************** Treat button ***************************************************/

			/*else if (Treat != null) {
				// action Treat button
				StaticVariables.setCalledFlag(0);

				// API Call

				sb.append("t=3");

				sb.append("&");

				sb.append("listtype=" + StaticVariables.getListType());

				sb.append("&");

				sb.append("f=1");

				sb.append("&");

				//sb.append("did=" + StaticVariables.getUserID());

				sb.append("did=" + httpsession.getAttribute("_usrID"));
				
				sb.append("&");

				sb.append("tokenno=" + StaticVariables.getCurrent_token());

				Print.logInfo("Treat API URL" + sb.toString());

				String resStr = urlConnectionOpen.openUrl(sb.toString());

				Jsob.jsonParserWithSkip(resStr);

				Print.logInfo("Treat API Response" + resStr);

				// request.setAttribute("msg", " Treat success");

			}*/

			/*********************************** Cancel button ***************************************************/

			/*else if (Cancel != null) {
				// action logout button
				StaticVariables.setCalledFlag(0);

				// API Call

				sb.append("t=4");

				sb.append("&");

				sb.append("listtype=" + StaticVariables.getListType());

				sb.append("&");

				sb.append("f=1");

				sb.append("&");

				//sb.append("did=" + StaticVariables.getUserID());

				sb.append("did=" + httpsession.getAttribute("_usrID"));
				
				sb.append("&");

				sb.append("tokenno=" + StaticVariables.getCurrent_token());

				Print.logInfo("Cancel API URL" + sb.toString());

				String resStr = urlConnectionOpen.openUrl(sb.toString());

				Jsob.jsonParserWithSkip(resStr);

				Print.logInfo("Cancel API Response" + resStr);

				// request.setAttribute("msg", " Cancel success");

			}*/

			/*********************************** showPatientList button ***************************************************/
			/*else if (showPatientList != null) {
				// action showPatientList button
				// StaticVariables.setCalledFlag(0);
				freshListFlag = 0;

				sb.append("t=4");

				sb.append("&");

				sb.append("listtype=" + StaticVariables.getListType());

				sb.append("&");

				sb.append("f=3");

				sb.append("&");

				//sb.append("did=" + StaticVariables.getUserID());

				sb.append("did=" + httpsession.getAttribute("_usrID"));
				
				Print.logInfo("showPatientList API URL" + sb.toString());

				String resStr = urlConnectionOpen.openUrl(sb.toString());

				try {

					JsonParsing jsonparsing = new JsonParsing();

					ArrayList<PateintDetailHelper> ar = jsonparsing
							.jsonParser(resStr);
					// Collections.sort(ar);

					request.setAttribute("showPatients", ar);

				} catch (Exception ex) {

					Print.logInfo("Exception in DCU showPatients button" + ex);

					ex.printStackTrace();
				}

			} else {

				request.setAttribute("msg", "");

			}*/

			/*********************************** forwars attribute from jsp ***************************************************/

			/*if (StaticVariables.getListType() == 0) {

				request.setAttribute("listType", "Fresh");
			} else {

				request.setAttribute("listType", "Skip");
			}

			app.setAttribute("calledFlag", StaticVariables.getCalledFlag());

			app.setAttribute("freshListFlag", freshListFlag);

			app.setAttribute("skipListEmpty", skipListEmpty);

			app.setAttribute("skipListOver", skipListOver);

			app.setAttribute("CurrentSkippedTokenNo",
					StaticVariables.getCurrentSkippedTokenNo());

			request.setAttribute("listTypeint", StaticVariables.getListType());
			*/
			
		} catch (Exception e) {
			Print.logException("Exception in  DCUCallSkip class",e);
		}

		try {
			request.getRequestDispatcher("dcu_call.jsp").forward(request,
					response);
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			Print.logException("Exception in  DCUCallSkip class",e);
		}
			
		
	}
	
	public String parseResult(JSONObject response, int functionType){
		if(functionType==1){
			JSONObject js;
			try {
				js = new JSONObject(response);
				boolean isError =js.containsKey("Error");
				if(isError == false){
					return "";
				}
				int error = Integer.parseInt((String) js.get("Error"));
				if(error==3){
					return "No more patients.";
				}else if(error==4){
					return "All patients are treated. No more patients.";
				}else if(error==5){
					return "No more patients.";
				}else if(error==6){
					return "All patients are Treated. No more patients.";
				}else if(error==8){
					return "Patient treated does not exists";
				}else if(error==9){
					return "selected patient does not exists";
				}else if(error==12){
					return "All patients are treated. No more patients.";
				}else if(error==7){
					return "Something went wrong!!!";
				}
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Print.logException("Exception while parsing json ", e);
			}
		
		}
		return "";
	}
	
	public int parseCurrentTokenNo(JSONObject response){
			JSONObject js;
			try {
				js = new JSONObject(response);
				int tokenno = (Integer) js.get("token_issue");
				return tokenno;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Print.logException("Exception while parsing json ", e);
			}

		return 0;
	}
	
}
