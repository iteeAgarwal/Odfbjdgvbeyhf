package com.dan.dqms.reports;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.util.Print;

import com.dan.dqms.returnlist.HospitalSummayReport;
import com.dan.dqms.returnlist.HospitalSummaryHelper;
import java.util.*;
@WebServlet("/HospitalSummary")
public class HospitalSummary extends HttpServlet {
	private static final long serialVersionUID = 1L;

	long fromDateTime;
	long toDateTime;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	public HospitalSummary() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		String fromDate = request.getParameter("fromDate");
        String toDate = request.getParameter("toDate");
        String subBut = request.getParameter("loginStatus");
        if(subBut!=null){
		if (isValidDate(fromDate) && isValidDate(toDate)) {

			Date d;
			try {

				d = dateFormat.parse(fromDate);
				fromDateTime = d.getTime() / 1000;

				d = dateFormat.parse(toDate);
				toDateTime = d.getTime() / 1000;

			} catch (ParseException e) {
				Print.logException("Exception in  AllDoctorSummary reports class", e);
			}


        	HospitalSummayReport hsr=new HospitalSummayReport();
        	
        	try{
        	Date dt_from_dt=dateFormat.parse(fromDate);
        	Date dt_to_dt=dateFormat.parse(toDate);
        	long ln_from_dt=dt_from_dt.getTime();
        	long ln_to_dt=dt_to_dt.getTime();
        	ln_from_dt=ln_from_dt/1000;
        	ln_to_dt=ln_to_dt/1000;
        	ArrayList<HospitalSummaryHelper> list_hos_summ=hsr.dataOutput(ln_from_dt, ln_to_dt);
        	request.setAttribute("hos_summ", list_hos_summ);
        	request.getRequestDispatcher("report_hospital_summary.jsp?from="+fromDate+"&to="+toDate).forward(
    				request, response);
        	}catch(Exception ex){
        		Print.logException("Exception in  date formating in Hospital Summary report class", ex);
        	}
        
		
		
		
		} else {
			request.setAttribute("message", "Date format invalid");
			// request.setAttribute("allDeptReport", processAll_Department());
		}
       
        }
        else
        {
        	request.getRequestDispatcher("report_hospital_summary.jsp").forward(
    				request, response);
        }

	}

	public boolean isValidDate(String inDate) {

		dateFormat.setLenient(false);
		try {
			dateFormat.parse(inDate.trim());
		} catch (ParseException pe) {
			return false;
		}
		return true;
	}

}
