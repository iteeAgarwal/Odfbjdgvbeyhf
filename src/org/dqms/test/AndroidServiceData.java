package org.dqms.test;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.DBAdapter;
import org.dqms.db.TokenSummary;
import org.dqms.util.StringTools;
import org.dqms.war.android.ServiceData;
import org.json.simple.JSONObject;

public class AndroidServiceData {

	public static void main(String args[]){
		
		String  roomNo  = "1";
        boolean  type     =true;
       
        
        if (StringTools.isBlank(roomNo)) {
        	System.out.print("1"); //room no or type is blank
       	 return;
        }
        
        DBAdapter db = new DBAdapter();
        TokenSummary tokenSummary = db.TokenSummary_RoomId(roomNo);
        if(tokenSummary == null){
        	System.out.print("2"); //This room does not have any room group
       	 return; 
        }
        
        int tokenGroupID = tokenSummary.getToken_group_id();
        int CurrentSkippedTokenNo = 0;
        int TotalSkippedTokenNo =0;
        if(type){
       	 //CurrentSkippedTokenNo = db.LatestSkippedToken(tokenGroupID);
       	 TotalSkippedTokenNo = db.TotalSkippedToken(tokenGroupID);
        }
        
        JSONObject obj = new JSONObject();
    	 obj.put("TotalSkippedTokenNo", TotalSkippedTokenNo  );
    	 obj.put("CurrentSkippedTokenNo", CurrentSkippedTokenNo);
    	 obj.put("token_alloted", new Integer(tokenSummary.getToken_last_issued()));
    	 obj.put("token_current", new Integer(tokenSummary.getToken_last_checked()));
    	 
    	 

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
	        out.println("{");
	        out.println("  \"Error\": \"" + StringTools.escapeJSON(msg) + "\"");
	        out.println("}");
	           
	    }
	
}
