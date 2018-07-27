package org.dqms.war.android;

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
import org.dqms.db.DBAdapterApollo;
import org.dqms.db.TokenDocSummary;
import org.dqms.db.TokenSummary;
import org.dqms.util.Print;
import org.dqms.util.StringTools;
import org.json.simple.JSONObject;


@WebServlet("/ServiceData")
public class ServiceData extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final String  PARM_DOCID      ="d";  // Constants.PARM_ACCOUNT;
    private static final String  PARM_TYPE        ="t";  // Constants.PARM_USER;
    private static final String  PARM_TYPE_user        ="w";  // Constants.PARM_USER appointment and walking;
    
    public ServiceData() {
		super();

	}
	
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
    	
    	 int  userID  = (Integer.parseInt(ServiceData.getRequestString (request, PARM_DOCID  , "0")));
         String typeStr = ServiceData.getRequestString (request, PARM_TYPE     , "0");
         if(typeStr.equalsIgnoreCase("true")){
        	 typeStr="1";
         }else if(typeStr.equalsIgnoreCase("false")){
        	 typeStr="0";
         }
    	 boolean  type     = (Integer.parseInt(typeStr)) >0?true:false;
         
         	 if (userID<=0) {
            	 this.errorResponse(response, "1"); //room no or type is blank
            	 return;
             }
             
         	DBAdapterApollo dbApollo = new DBAdapterApollo();
             DBAdapter db = new DBAdapter();
             TokenDocSummary tokenDocSummary = db.TokenSummary_UserId(userID);
             if(tokenDocSummary == null){
            	 this.errorResponse(response, "2"); //This room does not have any room group
            	 return; 
             }
             
             int tokenGroupID = tokenDocSummary.getToken_group_id();
            
             if(type){
            	 int CurrentSkippedTokenNo_app = 0;
                 int TotalSkippedTokenNo_app =0;
                 int CurrentSkippedTokenNo_walk = 0;
                 int TotalSkippedTokenNo_walk =0;
                 
                 CurrentSkippedTokenNo_app = dbApollo.LatestSkippedTokenApp(userID,0);
                 CurrentSkippedTokenNo_walk = dbApollo.LatestSkippedTokenWalk(userID,0);
                 TotalSkippedTokenNo_app = dbApollo.TotalSkippedTokenApp(userID);
                 TotalSkippedTokenNo_walk = dbApollo.TotalSkippedTokenWalk(userID);
            	 
                 JSONObject obj = new JSONObject();
            	 obj.put("total_token",  TotalSkippedTokenNo_walk + TotalSkippedTokenNo_app);
             	 obj.put("current_token_app", CurrentSkippedTokenNo_app);
             	 obj.put("total_token_app", TotalSkippedTokenNo_app);
             	 obj.put("current_token_walk", CurrentSkippedTokenNo_walk);
            	 obj.put("total_token_walk", TotalSkippedTokenNo_walk);
            	 PrintWriter out = response.getWriter();
        	     out.println(obj);  
             }else{
            	 JSONObject obj = new JSONObject();
            	 obj.put("total_token",  new Integer(tokenDocSummary.getTotal_token_walk()) +  new Integer(tokenDocSummary.getTotal_token()));
             	 obj.put("current_token_app", new Integer(tokenDocSummary.getCurrent_token_walk()) );
             	 obj.put("total_token_app", new Integer(tokenDocSummary.getTotal_token_walk()));
             	 obj.put("current_token_walk", new Integer(tokenDocSummary.getCurrent_token()));
            	 obj.put("total_token_walk", new Integer(tokenDocSummary.getTotal_token()));
            	 PrintWriter out = response.getWriter();
        	     out.println(obj);   
             }            
        
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
