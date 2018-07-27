package com.dan.dqms.dcu;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.db.DBAdapter;
import org.dqms.db.DocSummary;
import org.dqms.db.User;
import org.dqms.util.Print;

import com.dan.dqms.staticvar.StaticVariables;

@WebServlet("/Doctor_summary")
public class Doctor_summary extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public Doctor_summary() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		httpsession=request.getSession(false);
		DocSummaryBean docSummaryBean = new DocSummaryBean();
		try{

		

		//int docID = StaticVariables.getUserID();

		int docID = (Integer)httpsession.getAttribute("_usrID");

		DBAdapter db = new DBAdapter();

		User user = db.UserDetails(docID);

		long login_time = user.getLogin_time();

		DocSummary docSummary = db.DoctorSummary(docID);

		docSummaryBean.setTotalPatients(new Integer(docSummary
				.getTotal_token_doctorID()));

		//docSummaryBean.setTotalCall(0);

		docSummaryBean.setTotalTreat(new Integer(docSummary
				.getTotal_treated_token()));

		int TotalSkippedTokenNo = db.TotalSkippedToken(docID);

		docSummaryBean.setTotalSkip(TotalSkippedTokenNo);

		docSummaryBean.setTotalCancel(new Integer(docSummary
				.getTotal_cancel_token()));

		Date date = new Date(new Long(login_time) * 1000L);
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		String formattedDate = sdf.format(date);

		if (formattedDate == null) {
			formattedDate = "";
		}
		docSummaryBean.setDateTime(formattedDate);
		}
		catch(Exception e)
		{
			Print.logException("Exception in  Doctor_summary class" , e);
		}

		

		request.setAttribute("docSummaryBean", docSummaryBean);

		request.getRequestDispatcher("dcu_summary.jsp").forward(request,
				response);
		

	}

}
