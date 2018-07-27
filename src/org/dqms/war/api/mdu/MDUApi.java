package org.dqms.war.api.mdu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.DBAdapter;
import org.dqms.db.MDU;
import org.dqms.db.Room;
import org.dqms.util.Print;
import org.dqms.util.StringTools;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/MDUApi")
public class MDUApi extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final String  PARM_Address      ="a";  // MDU Address 
	
    
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
    	String ipAddr 		   = null;
        URL    requestURL      = null;
        String requestHostName = null;
        String requestUrlPath  = null;
        try {
        	ipAddr			= request.getRemoteAddr();
            requestURL      = new URL(request.getRequestURL().toString());
            requestHostName = requestURL.getHost();
            requestUrlPath  = requestURL.getPath();
            Print.logInfo("Android Login RequestURL " + requestURL);
        } catch (MalformedURLException mfue) {
            // invalid URL? (unlikely to occur)
            Print.logWarn("Invalid URL? " + request.getRequestURL());
        }

            this._doWork(isPost,request, response, ipAddr);
    }

    /* handle POST/GET request */
    private void _doWork(boolean isPost, HttpServletRequest request, HttpServletResponse response, String ipAddr)
        throws IOException
    {
   	 	String  devAddress  = MDUApi.getRequestString (request, PARM_Address  , "");
   	 	
    
   	 	if(devAddress==null || StringTools.isBlank(devAddress.trim())){
   	 		this.errorResponse(response, "1"); //MDU does not have any room configured 
   	 		return;
   	 	}
   	 	
    	 DBAdapter db = new DBAdapter();
         String rooms = db.MDURoomList(devAddress);
         if(rooms == null || StringTools.isBlank(rooms.trim())){
        	 this.errorResponse(response, "2"); //MDU does not have any room configured 
        	 return; 
         }
         
         String room[] = rooms.split(",");  
         
         if(room.length<=0){
        	 this.errorResponse(response, "2"); //MDU does not have any room configured 
        	 return; 
         }
         
         ArrayList<MDU> listMDU = db.MDUTokenList(room);
         if(listMDU == null || listMDU.size() <=0){
        	 this.errorResponse(response, "3"); //No data for WDU 
        	 return; 
         }
         
         JSONObject obj = new JSONObject();
     	 JSONArray toklist = new JSONArray();
     	 
     			
     	 for(int i=0;i<listMDU.size();i++){
     		JSONObject obj1 = new JSONObject();
     		obj1.put("current_token", listMDU.get(i).getCurrent_Token());
        	obj1.put("depart_name", listMDU.get(i).getDepart_Name());
        	obj1.put("doc_name", listMDU.get(i).getDoc_Name());
        	obj1.put("room_no", listMDU.get(i).getRoom_No());
        	toklist.add(obj1);
     	 }
     
     	 obj.put("rooms", toklist);
    	 PrintWriter out = response.getWriter();
	     out.println(obj);
	     
	     long hitTime= (System.currentTimeMillis()/1000);
	     //db.DeviceHealthUpdate(ipAddr, devAddress, hitTime, 1);
	     db.DeviceHealthUpdate(ipAddr, devAddress, hitTime, 1);
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
