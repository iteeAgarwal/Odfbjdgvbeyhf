package org.dqms.war.android.api;

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

import org.dqms.util.Print;
import org.json.JSONArray;

import com.dan.dqms.MS.DataHelper;
import com.dan.dqms.MS.DbAdapterMS;
import com.dan.dqms.MS.DbAdapterMSGraph;

/**
 * Servlet implementation class MSAppDataGraph
 */
@WebServlet("/MSAppDataGraph")
public class MSAppDataGraph extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final String PARM_DEPARTMENT="dept";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MSAppDataGraph() {
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
		this._doWork_wrapper(true, request, response);

	}
	private void _doWork(boolean isPost, HttpServletRequest request, HttpServletResponse response)
	        throws IOException
	    {
	    	
	    	 
	        
	         String Department   = MSAppData.getRequestString (request, PARM_DEPARTMENT , "");
	       
	         
	         
	         
	         
	        DbAdapterMS dbms = new DbAdapterMS();
	        DbAdapterMSGraph dbmsgraph = new DbAdapterMSGraph();
	        ArrayList<DataHelper> ard;
	        ArrayList<Integer> ardg;
	        ArrayList<Integer> ardg1;
	        JSONArray jsa = new JSONArray();
	        JSONArray jsa1 = new JSONArray();
	      if(Department.equals("All"))
	      {
	        ardg=dbmsgraph.barChart(0);
	        ardg1=dbmsgraph.barChart1(0);
	      }
	      else
	      {
	    	  ardg = dbmsgraph.barChart(dbms.findDepartId(Department)); 
	    	  ardg1 = dbmsgraph.barChart1(dbms.findDepartId(Department)); 
	      }
	      
	     
	      for(int i = 0;i<ardg.size();i++)
	      {
	    	  jsa.put(ardg.get(i));
	      }
	      
	     
	      for(int i = 0;i<ardg1.size();i++)
	      {
	    	  jsa1.put(ardg1.get(i));
	      }
	     
	      JSONArray jsmain = new JSONArray();
	      jsmain.put(jsa);
	      jsmain.put(jsa1);
	      
	      
	        
	         PrintWriter out = response.getWriter(); 
			  out.println(jsmain);
	        
	        	 
	         
	         
	    }
 
 public static String getRequestString(HttpServletRequest req, String key,
			String dft) {
		boolean ignoreCase = true;

		/* nothing to lookup? */
		if ((req == null) || key.equals("")) {
			return null;
		}

		/* standard parameters */
		if (ignoreCase) {
			for (Enumeration e = req.getParameterNames(); e.hasMoreElements();) {
				String n = (String) e.nextElement();
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

}
