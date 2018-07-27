package com.dan.dqms.reports;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.config.SystemSetting;
import org.dqms.util.Print;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.Minutes;

import beans.DoctorSummaryBean;

import com.dan.dqms.token.TokenGeneratorData;



@WebServlet("/AllDoctorSummary")
public class AllDoctorSummary extends HttpServlet {
	private static final long serialVersionUID = 1L;

	Connection con;
	PreparedStatement pstmt,pstmt2,pstmt3,pstmt4,pstmt5;
	PreparedStatement pstmt_log_his;
	Statement stmt;
	ResultSet rs,rs2,rs3,rs4,rs5;
	ResultSet rs_log_his;
	long v_wait,v_treat;
	String depart_name;
	int num_treat_patients=0,tot_wait=0,tot_treat=0,wait_time=0,treat_time=0;



	long fromDateTime;
	long toDateTime;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	public AllDoctorSummary() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Print.logInfo("AllDoctorSummary:doPost", null);
		
		String fromDate = request.getParameter("fromDate");

		String toDate = request.getParameter("toDate");
		
		if(request.getParameter("loginStatus")!=null && fromDate != null && toDate != null ){

			if (isValidDate(fromDate) && isValidDate(toDate)) {

				Date d;
				try {

					d = dateFormat.parse(fromDate);
					fromDateTime = d.getTime() / 1000;

					d = dateFormat.parse(toDate);
					toDateTime = d.getTime() / 1000;



					
					request.setAttribute("doctor_summary",process2(fromDateTime,toDateTime));

				} catch (ParseException e) {
					Print.logException("Exception in  AllDoctorSummary reports class"+ e,e);
				}

			} else {
				request.setAttribute("message", "Date format invalid");
				ArrayList<DoctorSummaryBean> al=process();				
				request.setAttribute("doctor_summary", al);
			}
			
			request.getRequestDispatcher("report_all_doctor_summary.jsp?from="+fromDate+"&to="+toDate).forward(request, response);
		}
		else
		{

			ArrayList<DoctorSummaryBean> al=process();
			
			request.setAttribute("doctor_summary", al);
			request.getRequestDispatcher("report_all_doctor_summary.jsp").forward(request, response);
		}

		

	}


	public Connection getConnection(){

		try{
			Class.forName(SystemSetting.DB_DRIVER);

			con=DriverManager.getConnection(SystemSetting.DB_URL, SystemSetting.DB_USER, SystemSetting.DB_PASSWORD);

			if(con==null){}
				
		}
		catch(Exception ex){
			Print.logException("Exception in getting jdbc connection", ex);
		}

		return con;
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



	public ArrayList<DoctorSummaryBean> process(){

		ArrayList<DoctorSummaryBean> al=new ArrayList<DoctorSummaryBean>();

		try{

			Print.logInfo("AllDoctorSummary:process", null);	
			con=getConnection();

			pstmt=con.prepareStatement("select * from user_details where role_id=2");
			rs=pstmt.executeQuery();
			while(rs.next()){
				int num_treat_patients_l=0,tot_wait_l=0,tot_treat_l=0,wait_time_l=0,treat_time_l=0;
				int doc_id=rs.getInt("user_id");
				pstmt5=con.prepareStatement("select * from token_history where user_id=?");
				pstmt5.setInt(1, doc_id);
				rs5=pstmt5.executeQuery();
				if(!rs5.next()){
					continue;
				}
				String doc_name=rs.getString("name");
				int dept_id=rs.getInt("depart_id");
				pstmt2=con.prepareStatement("SELECT depart_name FROM department_details where depart_id=?");
				pstmt2.setInt(1, dept_id);
				rs2=pstmt2.executeQuery();
				while(rs2.next()){
					depart_name=rs2.getString("depart_name");
				}
				pstmt_log_his=con.prepareStatement("select * from login_history where user_id=?");
				pstmt_log_his.setInt(1, doc_id);
				rs_log_his=pstmt_log_his.executeQuery();
				int total_working_mins=0,wH=0,wM=0;
				while(rs_log_his.next()){
					int login_time=rs_log_his.getInt("login_time");
					int logout_time=rs_log_his.getInt("logout_time");
					if(login_time==0||logout_time==0){
						continue;
					}
					long jLogin_time=login_time*1000;
					long jLogout_time=logout_time*1000;
					Date dtLogin=new Date(jLogin_time);
					Date dtLogout=new Date(jLogout_time);
					DateTime dtIn=new DateTime(dtLogin);
					DateTime dtOut=new DateTime(dtLogout);
					/*int total_working_hours=Hours.hoursBetween(dtIn, dtOut).getHours();*/
					total_working_mins=total_working_mins+Minutes.minutesBetween(dtIn, dtOut).getMinutes();
					
				/*	wH=wH+(total_working_mins/60);
					wM=wM+(total_working_mins%60);*/	
				}
				wH=total_working_mins/60;
				wM=total_working_mins%60;
				
				
				pstmt3=con.prepareStatement("select * from token_history where user_id=? and status=3");
				pstmt3.setInt(1, doc_id);
				rs3=pstmt3.executeQuery();
				while(rs3.next()){
					int token_call_time=rs3.getInt("token_call_time");
					int token_over_time=rs3.getInt("token_over_time");
					if(token_call_time==0 || token_over_time==0){
						continue;
					}
					long jToken_call_time=token_call_time*1000l;
					long jToken_over_time=token_over_time*1000l;
					Date dCall_time=new Date(jToken_call_time);
					Date dOver_time=new Date(jToken_over_time);
					DateTime dt2 = new DateTime(dCall_time);
					DateTime dt3 = new DateTime(dOver_time);
					num_treat_patients_l++;
					treat_time_l=treat_time_l+Minutes.minutesBetween(dt2, dt3).getMinutes();
					
				}
				
				pstmt4=con.prepareStatement("select * from token_history where user_id=? and status=1");
				pstmt4.setInt(1, doc_id);
				rs4=pstmt4.executeQuery();
				while(rs4.next()){
					int token_issue_time=rs4.getInt("token_issue_time");
					int token_call_time=rs4.getInt("token_call_time");
					if(token_issue_time==0 || token_call_time==0){
						continue;
					}
					long jToken_issue_time=token_issue_time*1000l;
					long jToken_call_time=token_call_time*1000l;				
					Date dIssue_time=new Date(jToken_issue_time);
					Date dCall_time=new Date(jToken_call_time);
					DateTime dt1 = new DateTime(dIssue_time);
					DateTime dt2 = new DateTime(dCall_time);
					tot_wait_l++;
					wait_time_l=wait_time_l+Minutes.minutesBetween(dt1, dt2).getMinutes();
				}
				int avgWait=0,avgTreat=0;
				int aWH=0,aWM=0,aTH=0,aTM=0;
				String strAvgWait="00 hrs : 00 mins",strAvgTreat="00 hrs : 00 mins";
				if(tot_wait_l!=0){
					avgWait=wait_time_l/tot_wait_l;
					aWH=avgWait/60;
					aWM=avgWait%60;
					strAvgWait=aWH+" hrs : "+aWM+" mins";
				}
					if(num_treat_patients_l!=0){
						avgTreat=treat_time_l/num_treat_patients_l;
						aTH=avgTreat/60;
						aTM=avgTreat%60;
						strAvgTreat=aTH+" hrs : "+aTM+" mins";
					}
					DoctorSummaryBean dsb=new DoctorSummaryBean();

				/*	dsb.setDoc_id(doc_id);dsb.setDoc_name(doc_name);dsb.setDepart_name(depart_name);
					dsb.setTotal_working_hours(total_working_hours);dsb.setNum_treat_patients(num_treat_patients_l);
					dsb.setAvgWait(avgWait);dsb.setAvgTreat(avgTreat);*/
					dsb.setDoc_id(doc_id);dsb.setDoc_name(doc_name);dsb.setDepart_name(depart_name);
					dsb.setNum_treat_patients(num_treat_patients_l);
					dsb.setStrTotal_working_hours(wH+" hrs : "+wM+" mins");
					dsb.setStrAvgWait(strAvgWait);dsb.setStrAvgTreat(strAvgTreat);
					

					al.add(dsb);

			}//first while ends
          con.close();
		}catch(Exception ex){

			Print.logException("exception process method", ex);

		}

		return al;

	}



	public ArrayList<DoctorSummaryBean> process2(long fromDate,long toDate){

		ArrayList<DoctorSummaryBean> al=new ArrayList<DoctorSummaryBean>();

		try{
			Print.logInfo("AllDoctorSummary:process2", null);
			con=getConnection();

			pstmt=con.prepareStatement("select * from user_details where role_id=2");
			rs=pstmt.executeQuery();
			while(rs.next()){
				int num_treat_patients_l=0,tot_wait_l=0,tot_treat_l=0,wait_time_l=0,treat_time_l=0;
				int doc_id=rs.getInt("user_id");
				pstmt5=con.prepareStatement("select * from token_history where user_id=?");
				pstmt5.setInt(1, doc_id);
				rs5=pstmt5.executeQuery();
				if(!rs5.next()){
					continue;
				}
				String doc_name=rs.getString("name");
				int dept_id=rs.getInt("depart_id");
				pstmt2=con.prepareStatement("SELECT depart_name FROM department_details where depart_id=?");
				pstmt2.setInt(1, dept_id);
				rs2=pstmt2.executeQuery();
				while(rs2.next()){
					depart_name=rs2.getString("depart_name");
				}
				
				pstmt_log_his=con.prepareStatement("select * from login_history where user_id=?");
				pstmt_log_his.setInt(1, doc_id);
				rs_log_his=pstmt_log_his.executeQuery();
				int total_working_mins=0,wH=0,wM=0;
				while(rs_log_his.next()){
					int login_time=rs_log_his.getInt("login_time");
					int logout_time=rs_log_his.getInt("logout_time");
					if(login_time==0 || logout_time==0){
						continue;
					}
					long jLogin_time=login_time*1000;
					long jLogout_time=logout_time*1000;
					Date dtLogin=new Date(jLogin_time);
					Date dtLogout=new Date(jLogout_time);
					DateTime dtIn=new DateTime(dtLogin);
					DateTime dtOut=new DateTime(dtLogout);
					/*int total_working_hours=Hours.hoursBetween(dtIn, dtOut).getHours();*/
					total_working_mins=total_working_mins+Minutes.minutesBetween(dtIn, dtOut).getMinutes();
					
						
				}
				wH=total_working_mins/60;
				wM=total_working_mins%60;
				
				
				/*Minutes mm=Minutes.minutesBetween(dtIn, dtOut);
				Hours hh=Hours.hoursBetween(dtIn, dtOut);
				int mmi=mm.getMinutes();
				int hhi=hh.getHours();*/
				pstmt3=con.prepareStatement("select * from token_history where user_id=? and status=3 and (today_date > ? and today_date < ?)");
				pstmt3.setInt(1, doc_id);
				pstmt3.setLong(2, fromDate);
				pstmt3.setLong(3, toDate);
				rs3=pstmt3.executeQuery();
				while(rs3.next()){
					int token_call_time=rs3.getInt("token_call_time");
					int token_over_time=rs3.getInt("token_over_time");
					if(token_call_time==0 || token_over_time==0){
						continue;
					}
					long jToken_call_time=token_call_time*1000l;
					long jToken_over_time=token_over_time*1000l;
					Date dCall_time=new Date(jToken_call_time);
					Date dOver_time=new Date(jToken_over_time);
					DateTime dt2 = new DateTime(dCall_time);
					DateTime dt3 = new DateTime(dOver_time);
					num_treat_patients_l++;
					
					treat_time_l=treat_time_l+Minutes.minutesBetween(dt2, dt3).getMinutes();
					
					
					
				}
				
				pstmt4=con.prepareStatement("select * from token_history where user_id=? and status=1 and (today_date > ? and today_date < ?)");
				pstmt4.setInt(1, doc_id);
				pstmt4.setLong(2, fromDate);
				pstmt4.setLong(3, toDate);
				rs4=pstmt4.executeQuery();
				while(rs4.next()){
					int token_issue_time=rs4.getInt("token_issue_time");
					int token_call_time=rs4.getInt("token_call_time");
					//syso
					if(token_issue_time==0 || token_call_time==0){
						continue;
					}
					long jToken_issue_time=token_issue_time*1000l;
					long jToken_call_time=token_call_time*1000l;				
					Date dIssue_time=new Date(jToken_issue_time);
					Date dCall_time=new Date(jToken_call_time);
					DateTime dt1 = new DateTime(dIssue_time);
					DateTime dt2 = new DateTime(dCall_time);
					tot_wait_l++;
					wait_time_l=wait_time_l+Minutes.minutesBetween(dt1, dt2).getMinutes();
					/*Minutes ml=Minutes.minutesBetween(dt1, dt2);
					iMM_wait_time_l=iMM_wait_time_l+ml.getMinutes();
					Hours hl=Hours.hoursBetween(dt1, dt2);
					iHH_wait_time_l=iHH_wait_time_l+hl.getHours();*/
					/*waitMinutes.minutesBetween(dt1, dt2);*/
				}
				int avgWait=0,avgTreat=0;
				int aWH=0,aWM=0,aTH=0,aTM=0;
				String strAvgWait="00 hrs : 00 mins",strAvgTreat="00 hrs : 00 mins";
				if(tot_wait_l==0){
				}
				
				if(tot_wait_l!=0){
					avgWait=wait_time_l/tot_wait_l;
					aWH=avgWait/60;
					aWM=avgWait%60;
					strAvgWait=aWH+" hrs : "+aWM+" mins";
				}
					if(num_treat_patients_l!=0){
						avgTreat=treat_time_l/num_treat_patients_l;
						aTH=avgTreat/60;
						aTM=avgTreat%60;
						strAvgTreat=aTH+" hrs : "+aTM+" mins";
					}
					DoctorSummaryBean dsb=new DoctorSummaryBean();


					dsb.setDoc_id(doc_id);dsb.setDoc_name(doc_name);dsb.setDepart_name(depart_name);
					dsb.setNum_treat_patients(num_treat_patients_l);
					dsb.setStrTotal_working_hours(wH+" hrs : "+wM+" mins");
					dsb.setStrAvgWait(strAvgWait);dsb.setStrAvgTreat(strAvgTreat);
					
					al.add(dsb);

			}//first while ends

		}catch(Exception ex){

			Print.logException("exception process2 method", ex);
		}

		return al;

	}


}
