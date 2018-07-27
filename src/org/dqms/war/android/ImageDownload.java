package org.dqms.war.android;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.dqms.config.SystemSetting;
import org.dqms.db.DBAdapter;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.dqms.util.StringTools;
import org.json.simple.JSONObject;


@WebServlet("/ImageDownload")
public class ImageDownload extends HttpServlet{

	private static final long serialVersionUID = 1L;
	private static final int     DOCTOR_ROLE_ID	  =2;
	private static final String ServerPath = SystemSetting.ANDROID_IMAGE_PATH;
	private static final boolean    IGNORE_CASE             = true; 
	private static final String  PARM_USER        ="u";  // Constants.PARM_USER;
    private static final String  PARM_PASSWORD    ="p";  // Constants.PARM_PASSWORD;

    
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
           Print.logInfo("ImageDownload RequestURL " + requestURL);
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

	   String  username     = LoginApi.getRequestString (request, PARM_USER     , "");
       String  password   = LoginApi.getRequestString (request, PARM_PASSWORD , "");
       
       if ( StringTools.isBlank(username) || StringTools.isBlank(password)) {
      	 this.errorResponse(response, "Invalid authorization. Please enter Username, Password and RoomNo.");
      	 return;
       }
       
       DBAdapter db = new DBAdapter();
       int user = db.CheckDoctorExistsUserID(username,password, DOCTOR_ROLE_ID);
       if(user == 0){
      	 this.errorResponse(response, "Invalid authorization. You have entered incorrect UserName Password.");
      	 return; 
       }
       
       String userID = "" + user;
       
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        
		String project_path = getServletContext().getRealPath("/") + ServerPath;
		Print.logInfo("project-path: " + project_path);
		
		if (!isMultipart) {
			this.errorResponse(response, "1"); //("File Not Uploaded");
       	 	return; 
		} else {
			
			File file=new File(project_path + userID);
			if (!file.exists()){
				boolean success = (new File(project_path + userID)).mkdir();
				if (!success) {
					Print.logInfo(userID +  "  Error while creating directory for imei.");
					this.errorResponse(response, "2"); //Error while creating directory for imei.
		       	 	return; 
				}
			}

			String ServerPathImei = project_path + userID + "/";
			
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			List items = null;

			try {
			  items = upload.parseRequest(request);
			} catch (FileUploadException e) {
				Print.logException("Exception " ,e);
			}
			  Iterator itr = items.iterator();
			  while (itr.hasNext()) {
				  FileItem item = (FileItem) itr.next();
				  if (item.isFormField()){
					  String name = item.getFieldName();
					  String value = item.getString();
				  } else {
					  try {
						  String itemName = item.getName();
						  File f = new File(ServerPathImei + itemName);
						  if (f.exists()){
							f.delete();  
						  }
						  
						  String finalimage = itemName;
						  File savedFile = new File(ServerPathImei + finalimage);
						  item.write(savedFile);
						  
						  JSONObject obj = new JSONObject();
				          obj.put("Error", "0");
						  PrintWriter out = response.getWriter(); 
						  out.println(obj);
					  } catch (Exception e) {
						  Print.logException("ImageDownload ", e);
					  }	
				  	}
				  }
			}
		}

	    public static String getRequestString(HttpServletRequest req, String key, String dft)
	    {

	        /* nothing to lookup? */
	        if ((req == null) || key.equals("")) {
	            return null;
	        }

	        /* standard parameters */
	        if (IGNORE_CASE) {
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


