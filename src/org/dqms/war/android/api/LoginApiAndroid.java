package org.dqms.war.android.api;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.dqms.db.DBAdapter;
import org.dqms.db.Department;
import org.dqms.db.TokenDocSummary;
import org.dqms.db.TokenSummary;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.dqms.util.StringTools;
import org.json.simple.JSONObject;


@WebServlet("/LoginApiAndroid")
public class LoginApiAndroid extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final int     DOCTOR_ROLE_ID	  =2;
	private static final String  PARM_ROOMNO      ="r";  // Constants.PARM_ACCOUNT;
    private static final String  PARM_USER        ="u";  // Constants.PARM_USER;
    private static final String  PARM_PASSWORD    ="p";  // Constants.PARM_PASSWORD;
    private static final String  PARM_DEPARTMENT    ="d";  // Constants.PARM_PASSWORD;
    private static final String  PARM_LOGOUT    ="l";  // Constants.PARM_PASSWORD;

    
	 /* GET request */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        this._doWork_wrapper(false, request, response);
    }

    /* POST request */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        this._doWork_wrapper(true, request, response);
    }

    private void _doWork_wrapper(boolean isPost, 
        HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {

        URL    requestURL      = null;
        String requestHostName = null;
        String requestUrlPath  = null;
        try {
            requestURL      = new URL(request.getRequestURL().toString());
            requestHostName = requestURL.getHost();
            requestUrlPath  = requestURL.getPath();
            Print.logInfo("Android Login RequestURL " + requestURL);
        } catch (MalformedURLException mfue) {
            // invalid URL? (unlikely to occur)
            Print.logWarn("Invalid URL? " + request.getRequestURL());
        }

            this._doWork(isPost,request, response);
    }

    /* handle POST/GET request */
    private void _doWork(boolean isPost, HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
    	
    	 //String  roomNo  = LoginApi.getRequestString (request, PARM_ROOMNO  , "");
         String  userID     = LoginApiAndroid.getRequestString (request, PARM_USER     , "");
         String  password   = LoginApiAndroid.getRequestString (request, PARM_PASSWORD , "");
         int	 flag_logout=(Integer.parseInt(LoginApiAndroid.getRequestString (request, PARM_LOGOUT     , "0")));
         //String  department   = LoginApi.getRequestString (request, PARM_DEPARTMENT , "");
         
         if ( StringTools.isBlank(userID) || StringTools.isBlank(password)) {
        	 this.errorResponse(response, "Invalid authorization. Please enter Username, Password and RoomNo.");
        	 return;
         }
         
         DBAdapter db = new DBAdapter();
         User user = db.CheckDoctorExists(userID,password, DOCTOR_ROLE_ID);
         if(user == null){
        	 this.errorResponse(response, "Invalid authorization. You have entered incorrect UserName Password.");
        	 return; 
         }
         
         if(flag_logout==1){ //sign out from all accounts
        	 long logoutTime= (System.currentTimeMillis()/1000);
             boolean result = db.DoctorLogoutUpdate(userID,password, logoutTime);
             if(!result){
            	 this.errorResponse(response, "11"); //logout failed
            	 return;
             }
             JSONObject obj = new JSONObject();
         	 obj.put("Error", "0");
         	 PrintWriter out = response.getWriter();
         	 out.println(obj);
         	 
         	 //update login_history table
         	 int user_id = user.getUser_id();
         	 db.DoctorLogoutUpdateHistory(user_id, logoutTime);
         	 return;
         }
         
         if(user.isLogin()){
        	 this.errorResponse(response, "Invalid authorization. User already logged in from other computer.");
        	 return; 
         }
         
         TokenDocSummary tokenDocSummary = db.TokenSummary_UserId(user.getUser_id());
         if(tokenDocSummary == null){
        	 this.errorResponse(response, "Invalid authorization. No token registerd for current Room Number.");
        	 return; 
         }
         
         //update user table about doctor login
         long loginTime= (System.currentTimeMillis()/1000);
         boolean result = db.DoctorLoginUpdate(user.getUser_id(), loginTime);
         if(!result){
        	 this.errorResponse(response, "Invalid authorization. Login Fail.");
        	 return;
         }
         String DepartmentName = Department.getDepartName(user.getDepart_id());
         JSONObject obj = new JSONObject();
         obj.put("doctor_id", new Integer(user.getUser_id()));
     	 obj.put("doctor_name", new String(user.getName()));
     	 obj.put("department", new String(DepartmentName));
     	 obj.put("current_token", new Integer(tokenDocSummary.getCurrent_token()));
     	 obj.put("total_token", new Integer(tokenDocSummary.getTotal_token()));
     	 
     	 PrintWriter out = response.getWriter();
	     out.println(obj);
         
	     //update login_history table
	     db.DoctorLoginUpdateHistory(user.getUser_id(), loginTime);
         
    }
	
	public static String getRequestString(HttpServletRequest req, String key, String dft)
	    {
		 boolean ignoreCase = true;
		 
	        /* nothing to lookup? */
	        if ((req == null) || key.equals("")) {
	            return null;
	        }

	        /* standard parameters */
	        if (ignoreCase) {
	            for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
	                String n = (String)e.nextElement();
	                if (n.equalsIgnoreCase(key)) {
	                    String val = req.getParameter(n);
	                    if (val != null) {
	                        return val;
	                    }
	                }
	            }
	        } else {
	            String val = req.getParameter(key);
	            if (val != null) {
	                return val;
	            }
	        }

	        /* default */
	        return dft;

	    }
	
	protected void errorResponse(HttpServletResponse response, String msg)
	        throws IOException
	    {
	        //CommonServlet.setResponseContentType(response, HTMLTools.CONTENT_TYPE_PLAIN);
			response.setContentType("application/jsonrequest");
			response.setCharacterEncoding("UTF-8");
			
	        /* display error */
	        PrintWriter out = response.getWriter();
	        out.println("{  \"Error\": \"" + StringTools.escapeJSON(msg) + "\"}");
	           
	    }
}
