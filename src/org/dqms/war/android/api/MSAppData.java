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
import org.json.simple.JSONObject;

import com.dan.dqms.MS.DataHelper;
import com.dan.dqms.MS.DbAdapterMS;
import com.dan.dqms.MS.DbAdapterMSGraph;
import com.dan.dqms.MS.Utils;

/**
 * Servlet implementation class MSAppData
 */
@WebServlet("/MSAppData")
public class MSAppData extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    private static final String DEPARTMENT_PARM = "dep";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MSAppData() {
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
	 
	 private void _doWork(boolean isPost, HttpServletRequest request, HttpServletResponse response)
		        throws IOException
		    {
		    	
		         String Department   = MSAppData.getRequestString (request, DEPARTMENT_PARM , "");
		       
		         
		         
		         
		         
		        DbAdapterMS dbms = new DbAdapterMS();
		        DbAdapterMSGraph dbmsgraph = new DbAdapterMSGraph();
		        ArrayList<DataHelper> ard;
		        ArrayList<Integer> ardg;
		      if(Department.equals("All"))
		      {
		         ard= dbms.data_Display(Utils.getTodayDateString(), 0);
		         ardg = dbmsgraph.pieChart(Utils.getTodayDateString(), 0);
		      }
		      else
		      {
		    	   ard= dbms.data_Display(Utils.getTodayDateString(), dbms.findDepartId(Department));
			       ardg = dbmsgraph.pieChart(Utils.getTodayDateString(),dbms.findDepartId(Department) );
		      }
		      
		      
		         JSONObject jo1 = new JSONObject();
		         DataHelper datahelper= ard.get(0);
		         jo1.put("total_patients", datahelper.getTotal_pateints());
		         jo1.put("total_patients_attended", datahelper.getPateints_attended());
		         jo1.put("total_patients_unattended", datahelper.getPatients_unattended());
		         jo1.put("avr_waiting_time", datahelper.getAvg_Waiting_time());
		         jo1.put("total_doctors", datahelper.getTotal_doctor());
		         jo1.put("total_doctors_login", datahelper.getTotal_doctor_login());
		         jo1.put("total_generator", datahelper.getToken_generator());
		         jo1.put("total_generator_login", datahelper.getToken_generator_login());
		         jo1.put("token_not_called", ardg.get(0));
		         jo1.put("token_called", ardg.get(1));
		         jo1.put("token_skip", ardg.get(2));
		         jo1.put("token_treat", ardg.get(3));
		         jo1.put("token_cancel", ardg.get(4));
		         
		         PrintWriter out = response.getWriter(); 
				  out.println(jo1);
		        
		        	 
		         
		         
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
}
