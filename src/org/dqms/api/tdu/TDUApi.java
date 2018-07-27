package org.dqms.api.tdu;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Patient;
import org.dqms.util.Print;
import org.dqms.util.StringTools;
import org.json.JSONObject;

import com.dan.dqms.token.TokenGeneratorData;


/**
 * Servlet implementation class TDUApi
 */
@WebServlet("/TDUApi")
public class TDUApi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TDUApi() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		 PrintWriter out=response.getWriter();
		
		
	this._doWork(true, request, response);
	
	}
	private void _doWork(boolean isPost, HttpServletRequest request, HttpServletResponse response)
	        throws IOException
	    {
		
		 PrintWriter out=response.getWriter();
		
		 String id="";
		 String name="";
		 String department="";
		 String room="";
		 String Doctor="";
		 int Token=0;
		 int type=0;
		 String contact="";
		
		 
		
		 
		 
		 TDUData td=new TDUData();
try
{
	
	String data=request.getParameter("a");
	
	if(data==null)
	{
		out.println("No data Found !!");
	}
	else{
	  JSONObject json = new JSONObject(data); 
    
	  
     

      if(json.isNull("Id")) 
      {
    	 out.println("1");
    	 return ;
     }
     else
     {
    	 id=json.getString("Id");
     }
	
	 if(json.isNull("Name"))
	 {
		 out.println("2");
	//	this.errorResponse(response, "2");
		return;
	 }
	 else
	 {
		 name=json.getString("Name");
	 }
	 if(json.isNull("Department"))
	 {
		 out.println("3");
		//this.errorResponse(response, "3");
		return;
	 }
	 else
	 {
		 department=json.getString("Department");
	 }
	 
	 if(json.isNull("Room"))
	 {
		 out.println("4");
		//this.errorResponse(response, "4");
		return;
	 }
	 else
	 {
		 room=json.getString("Room");
	 }
	 if(json.isNull("Doctor"))
	 {
		 out.println("5");
		//this.errorResponse(response, "5");
		return;
	 }
	 else
	 {
		 Doctor=json.getString("Doctor");
	 }
	 if(json.isNull("Token"))
	 {
		 out.println("6");
		//this.errorResponse(response, "6");
		return;
	 }
	 else
	 {
		 Token=json.getInt("Token");
	 }
	 if(json.isNull("Type"))
	 {
		 out.println("7");
		//this.errorResponse(response, "7");
		return;
	 }
	 else
	 {
		 type=json.getInt("Type");
	 }
	 if(json.isNull("Contact"))
	 {
		 
	 }
	 else
	 {
		 contact=json.getString("Contact");
	 }
	
	
	 out.println("0");
	 	
	 TokenInsertData tk=new TokenInsertData();
//	 if(department.contains(" and"))
//	 {
//		
//		 department=department.replace(" and"," &");
//	 }
//	 else if(department.contains(" AND"))
//	 {
//		
//		 department=department.replace(" AND"," &");
//	 }
	 
	 out.println("hiii");
     tk.setPatientDetails(id,name,contact,room,department,Doctor,Token,type);
    

	}
	 
  
}

	catch (Exception mfue) {
        // invalid URL? (unlikely to occur)
		mfue.printStackTrace();
        Print.logWarn("Invalid URL? " + request.getRequestURL());
        out.print("-1");
        System.out.print(mfue);
        
    } 


	    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		  
		this._doWork(false, request, response);
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
