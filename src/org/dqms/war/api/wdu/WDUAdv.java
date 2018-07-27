package org.dqms.war.api.wdu;

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

import org.dqms.db.Advertisement;
import org.dqms.db.DBAdapter;
import org.dqms.db.WDU;
import org.dqms.util.Print;
import org.dqms.util.StringTools;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;


@WebServlet("/WDUAdv")
public class WDUAdv extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	private static final String  PARM_Address      ="a";  // room ID 
    
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
    	
    	String  DeviceAddress  = WDUApi.getRequestString (request, PARM_Address  , "");
   	 	
    	 DBAdapter db = new DBAdapter();
         ArrayList<Integer> wduDepartID = db.WDUDepartList(DeviceAddress);
         if(wduDepartID == null || wduDepartID.size() <=0){
        	 this.errorResponse(response, "1"); //No data for WDU 
        	 return; 
         }
         
         ArrayList<Advertisement> listAdvertisement = db.AdvertisementList(wduDepartID,2);
         if(listAdvertisement == null || listAdvertisement.size() <=0){
        	 this.errorResponse(response, "1"); //No data for WDU 
        	 return; 
         }
         
         JSONObject obj = new JSONObject();
     	 JSONArray toklist = new JSONArray();
     			
     	 for(int i=0;i<listAdvertisement.size();i++){
     		JSONObject obj1 = new JSONObject();
     		obj1.put("name", listAdvertisement.get(i).getImage_name());
        	obj1.put("path", listAdvertisement.get(i).getImage_path());
        	toklist.add(obj1);
     	 }
     
     	 obj.put("ads", toklist);
    	 PrintWriter out = response.getWriter();
	     out.println(obj);
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
