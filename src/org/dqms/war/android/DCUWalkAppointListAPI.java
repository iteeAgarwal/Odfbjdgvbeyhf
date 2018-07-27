package org.dqms.war.android;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.DBAdapter;
import org.dqms.db.DBAdapterApollo;
import org.dqms.db.Token;
import org.dqms.db.TokenDocSummary;
import org.dqms.util.Print;
import org.dqms.util.StringTools;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dan.dqms.returnlist.AppWalkDoctorWise;

/**
 * Servlet implementation class DCUWalkAppointListAPI
 */
@WebServlet("/DCUWalkAppointListAPI")
public class DCUWalkAppointListAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String  PARM_DOCID      ="d";  // Constants.PARM_ACCOUNT;
    private static final String  PARM_TYPE        ="t";  // Constants.PARM_USER;
    private static final String  PARM_TYPE_user        ="w";  // Constants.PARM_USER appointment and walking;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DCUWalkAppointListAPI() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this._doWork_wrapper(false, request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this._doWork_wrapper(false, request, response);
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
                Print.logInfo("DCUWalkAppointListAPI RequestURL " + requestURL);
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
        	
        	 int  userID  = (Integer.parseInt(DCUWalkAppointListAPI.getRequestString (request, PARM_DOCID  , "0")));
 
             	 if (userID<=0) {
                	 this.errorResponse(response, "1"); //room no or type is blank
                	 return;
                 }
                 
             	 DBAdapter db = new DBAdapter();
                 TokenDocSummary tokenDocSummary = db.TokenSummary_UserId(userID);
                 if(tokenDocSummary == null){
                	 this.errorResponse(response, "2"); //This room does not have any room group
                	 return; 
                 }
                 
                 int tokenGroupID = tokenDocSummary.getToken_group_id();
                 
                 AppWalkDoctorWise appWalkOb = new AppWalkDoctorWise();
                 JSONObject finalList = new JSONObject();
                 
                	 ArrayList<Token> listTokenApp = appWalkOb.AppTokenDetails_doctorWise(userID, 2);
                	 JSONArray appoint = new JSONArray();
                	 try{
                		 for(int i=0;i< listTokenApp.size();i++){
	                		 JSONObject jo = new JSONObject();
	                		 jo.put("token_no", listTokenApp.get(i).getToken_no());
	                		 jo.put("app_walk_value", listTokenApp.get(i).getApp_walk_value());
	                		 jo.put("patient_name", listTokenApp.get(i).getPatient_name());
	                		 jo.put("status", listTokenApp.get(i).getStatus());
	                		 appoint.put(jo);
                		 }
                		// if(listTokenApp.size()>0){
                			 finalList.put("appointlist", appoint);
                		// }
                	 }catch(JSONException e){
            			 Print.logException("DCUWalkAppointListAPI", e);
            		 }
                	
                	 
                	 ArrayList<Token> listTokenWalk = appWalkOb.AppTokenDetails_doctorWise(userID, 1);
                	 JSONArray walk = new JSONArray();
                	 try{
                		 for(int i=0;i< listTokenWalk.size();i++){
	                		 JSONObject jo = new JSONObject();
	                		 jo.put("token_no", listTokenWalk.get(i).getToken_no());
	                		 jo.put("patient_name", listTokenWalk.get(i).getPatient_name());
	                		 jo.put("status", listTokenWalk.get(i).getStatus());
	                		 walk.put(jo);
                		 }
                		 finalList.put("walklist", walk);
                	 }catch(JSONException e){
            			 Print.logException("DCUWalkAppointListAPI", e);
            		 }
                	 
                	 PrintWriter out = response.getWriter();
            	     out.println(finalList); 
            	               
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
