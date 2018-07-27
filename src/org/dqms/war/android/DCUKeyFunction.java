package org.dqms.war.android;

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
import org.dqms.db.DBAdapterApollo;
import org.dqms.db.DocSummary;
import org.dqms.db.Token;
import org.dqms.db.TokenDocSummary;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.dqms.util.StringTools;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

@WebServlet("/DCUKeyFunction")
public class DCUKeyFunction extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String PARM_ROOMNO = "r"; // room ID
	private static final String PARM_TYPE = "t"; // 1=call, 2=skip, 3=treat,
													// 4=cancel
	private static final String PARM_LISTTYPE = "listtype"; // 0=fresh, 1=skip
	private static final String PARM_FUNCTIONTYPE = "f"; // 1=call,skip,treat,cancel
															// 2=summary
															// 3=patient list,
															// 4=logout
	private static final String PARM_TREATTOKENNO = "tokenno"; // treat or
																// cancel token
																// no
	private static final String PARM_DOCTORID = "did"; // login doctor ID
	private static final String PARM_TYPE_user = "w"; // Constants.PARM_USER
														// appointment and
														// walking;

	public DCUKeyFunction() {
		super();

	}

	/* GET request */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		this._doWork_wrapper(false, request, response);
	}

	/* POST request */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		this._doWork_wrapper(true, request, response);
	}

	private void _doWork_wrapper(boolean isPost, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		URL requestURL = null;
		String requestHostName = null;
		String requestUrlPath = null;
		try {
			requestURL = new URL(request.getRequestURL().toString());
			requestHostName = requestURL.getHost();
			requestUrlPath = requestURL.getPath();
			Print.logInfo("Android Login RequestURL " + requestURL);
		} catch (MalformedURLException mfue) {
			// invalid URL? (unlikely to occur)
			Print.logWarn("Invalid URL? " + request.getRequestURL());
		}

		this._doWork(isPost, request, response);
	}

	/* handle POST/GET request */
	private void _doWork(boolean isPost, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String roomNo = DCUKeyFunction.getRequestString(request, PARM_ROOMNO,
				"");
		int functionType = Integer.parseInt(DCUKeyFunction.getRequestString(
				request, PARM_FUNCTIONTYPE, "0"));
		int type = (Integer.parseInt(DCUKeyFunction.getRequestString(request,
				PARM_TYPE, "0")));
		int listType = (Integer.parseInt(DCUKeyFunction.getRequestString(
				request, PARM_LISTTYPE, "0")));
		int lastTokenNo = (Integer.parseInt(DCUKeyFunction.getRequestString(
				request, PARM_TREATTOKENNO, "0")));
		int userID = (Integer.parseInt(DCUKeyFunction.getRequestString(request,
				PARM_DOCTORID, "0")));

		int walkAppID = (Integer.parseInt(ServiceData.getRequestString(request,
				PARM_TYPE_user, "0")));

		walkAppID = 2;

		if (userID <= 0) {
			this.errorResponse(response, "1"); // room no or type is blank
			return;
		}

		DBAdapter db = new DBAdapter();
		DBAdapterApollo dbApollo = new DBAdapterApollo();

		TokenDocSummary tokenDocSummary = null;
		if (walkAppID == 1) {
			tokenDocSummary = db.TokenSummary_UserId(userID);
		}

		else if (walkAppID == 2) {
			tokenDocSummary = dbApollo.TokenSummary_UserId(userID);
		}

		if (tokenDocSummary == null) {

			this.errorResponse(response, "2"); // This room does not have any
												// room group
			return;
		}

		int tokenGroupID = tokenDocSummary.getToken_group_id();
		int CurrentSkippedTokenNo = 0;
		int TotalSkippedTokenNo = 0;

		if (walkAppID == 1) {
			CurrentSkippedTokenNo = db.LatestSkippedToken(userID, lastTokenNo);
			TotalSkippedTokenNo = db.TotalSkippedToken(userID);

		} else if (walkAppID == 2) {
			CurrentSkippedTokenNo = dbApollo.LatestSkippedToken(userID,
					lastTokenNo);
			TotalSkippedTokenNo = dbApollo.TotalSkippedToken(userID);
		}

		int status = 0;
		long token_call_time = 0;
		long token_over_time = 0;
		int currentTokenNo = 0;
		long time = (System.currentTimeMillis() / 1000);

		if (functionType == 1) {
			
			
			if(walkAppID == 1)
			{
				/**************** start walking patients list ******************/
				
				if (listType == 1 & type == 1 & (TotalSkippedTokenNo <= 0)) {
					this.errorResponse(response, "3"); // No token for this room no
														// + skip
					return;
				}
				if (listType == 1
						& type == 1
						& (CurrentSkippedTokenNo > tokenDocSummary.getTotal_token())) {
					this.errorResponse(response, "4"); // All patient checked. No
														// more patient + skip
					return;
				}
				if (listType == 1 & type == 1 & (TotalSkippedTokenNo <= 0)) {
					this.errorResponse(response, "3"); // All patient checked. No
														// more patient + skip
					return;
				}
				if (type == 1 & (tokenDocSummary.getTotal_token() <= 0)) {
					this.errorResponse(response, "5"); // No token for this room no
														// + Fresh
					return;
				}
				if (type == 1
						& type == 0
						& (tokenDocSummary.getCurrent_token() == tokenDocSummary
								.getTotal_token())) {
					this.errorResponse(response, "6"); // All patient checked. No
														// more patient + Fresh
					return;
				}
				if (lastTokenNo > tokenDocSummary.getCurrent_token()) {
					this.errorResponse(response, "8"); // Patient treated does not
														// exists + Treated
					return;
				}
				if ((type == 2 || type == 3 || type == 4) & lastTokenNo <= 0) {
					this.errorResponse(response, "9"); // Patient treated does not
														// exists + Treated
					return;
				}

				if (type == 4) {
					// Canceled
					currentTokenNo = lastTokenNo;
					token_over_time = time;
					status = 4;
				} else if (type == 3) {
					// treated
					currentTokenNo = lastTokenNo;
					token_over_time = time;
					status = 3;
				} else if (type == 2) {
					// update token_details (Skip)
					currentTokenNo = lastTokenNo;
					token_call_time = time;
					status = 2;
					TotalSkippedTokenNo = TotalSkippedTokenNo + 1;
				} else {
					
					
					if (listType == 1) {
						
						// update token_details & token_summary (Skip)
						currentTokenNo = CurrentSkippedTokenNo;
						token_call_time = time;
						status = 1;
					} else {
						// update token_details & token_summary (Fresh)
						token_call_time = time;
						status = 1;
						
					
							currentTokenNo = db.LatestCalledToken(userID,tokenDocSummary.getCurrent_token());
							if (currentTokenNo == 0) {
								this.errorResponse(response, "12"); // No more token to
																	// issue
								return;
							}
						
						
						//currentTokenNo = db.LatestCalledToken(userID,tokenDocSummary.getCurrent_token());
						
						
						
					}
				}

				
				
				int result = db.UpdateCallSkippedToken(tokenGroupID,
						currentTokenNo, token_call_time, token_over_time, status,
						listType, userID);
				
				if (result <= 0) {
					this.errorResponse(response, "7"); // Token issue failed. please
														// try again
					return;
				}
				
				

				

				/*
				 * if (resultApollo <= 0) { this.errorResponse(response, "7"); //
				 * Token issue failed. please // try again return; }
				 */

				JSONObject obj = new JSONObject();
				obj.put("TotalSkippedTokenNo", TotalSkippedTokenNo);
				obj.put("CurrentSkippedTokenNo", CurrentSkippedTokenNo);
				obj.put("current_token",
						new Integer(tokenDocSummary.getCurrent_token()));
				obj.put("total_token",
						new Integer(tokenDocSummary.getTotal_token()));
				if (type == 1) {
					obj.put("token_issue", currentTokenNo);
				}
				PrintWriter out = response.getWriter();
				out.println(obj);
				
				//update token_history table
				db.UpdateCallSkippedTokenHistory(tokenGroupID,
						currentTokenNo, token_call_time, token_over_time, status,
						listType, userID);
				
				
			}
			else if(walkAppID == 2)
			{
				/**************** start Appointment patients list ******************/
				
				
				if (listType == 1 & type == 1 & (TotalSkippedTokenNo <= 0)) {
					this.errorResponse(response, "3"); // No token for this room no
														// + skip
					return;
				}
				if (listType == 1
						& type == 1
						& (CurrentSkippedTokenNo > tokenDocSummary.getTotal_token())) {
					this.errorResponse(response, "4"); // All patient checked. No
														// more patient + skip
					return;
				}
				if (listType == 1 & type == 1 & (TotalSkippedTokenNo <= 0)) {
					this.errorResponse(response, "3"); // All patient checked. No
														// more patient + skip
					return;
				}
				if (type == 1 & (tokenDocSummary.getTotal_token() <= 0)) {
					this.errorResponse(response, "5"); // No token for this room no
														// + Fresh
					return;
				}
				if (type == 1 & type == 0 & (tokenDocSummary.getCurrent_token() == tokenDocSummary.getTotal_token())) {
					this.errorResponse(response, "6"); // All patient checked. No
														// more patient + Fresh
					return;
				}
				/*if (lastTokenNo > tokenDocSummary.getCurrent_token()) {
					
					this.errorResponse(response, "8"); // Patient treated does not
														// exists + Treated
					return;
				}*/
				if ((type == 2 || type == 3 || type == 4) & lastTokenNo <= 0) {
					this.errorResponse(response, "9"); // Patient treated does not
														// exists + Treated
					return;
				}

				if (type == 4) {
					// Canceled
					currentTokenNo = lastTokenNo;
					token_over_time = time;
					status = 4;
				} else if (type == 3) {
					// treated
					currentTokenNo = lastTokenNo;
					token_over_time = time;
					status = 3;
				} else if (type == 2) {
					// update token_details (Skip)
					currentTokenNo = lastTokenNo;
					token_call_time = time;
					status = 2;
					TotalSkippedTokenNo = TotalSkippedTokenNo + 1;
				} else {
					
					
					if (listType == 1) {
						
						// update token_details & token_summary (Skip)
						currentTokenNo = CurrentSkippedTokenNo;
						token_call_time = time;
						status = 1;
					} else {
						// update token_details & token_summary (Fresh)
						token_call_time = time;
						status = 1;
						
						
						
						
							currentTokenNo = dbApollo.LatestCalledToken(userID,tokenDocSummary.getCurrent_token());
							if (currentTokenNo == 0) {
								this.errorResponse(response, "12"); // No more token to
																	// issue
								return;
							
						}
						//currentTokenNo = db.LatestCalledToken(userID,tokenDocSummary.getCurrent_token());
						
						
						
					}
				}

				
					
					int resultApollo = dbApollo.UpdateCallSkippedToken(tokenGroupID,
							currentTokenNo, token_call_time, token_over_time, status,
							listType, userID);
					
					if (resultApollo <= 0) {
						this.errorResponse(response, "7"); // Token issue failed. please
															// try again
						return;
					}
					
				

				

				

				/*
				 * if (resultApollo <= 0) { this.errorResponse(response, "7"); //
				 * Token issue failed. please // try again return; }
				 */

				JSONObject obj = new JSONObject();
				obj.put("TotalSkippedTokenNo", TotalSkippedTokenNo);
				obj.put("CurrentSkippedTokenNo", CurrentSkippedTokenNo);
				obj.put("current_token",
						new Integer(tokenDocSummary.getCurrent_token()));
				obj.put("total_token",
						new Integer(tokenDocSummary.getTotal_token()));
				if (type == 1) {
					obj.put("token_issue", currentTokenNo);
				}
				PrintWriter out = response.getWriter();
				out.println(obj);
				
				
				
				//update token_history table
				dbApollo.UpdateCallSkippedTokenHistory(tokenGroupID,
						currentTokenNo, token_call_time, token_over_time, status,
						listType, userID);
				
			}
			
			} else if (functionType == 2) {
			// get doctor login date time --user_details (doctorID)
			User user = db.UserDetails(userID);
			long login_time = user.getLogin_time();
			// get summary of treated, skipped, canceled token --token_details
			// (roomID,doctorID,tokenGroupID)
			DocSummary docSummary = db.DoctorSummary(userID);

			JSONObject obj = new JSONObject();
			obj.put("TotalSkippedTokenNo", TotalSkippedTokenNo);
			obj.put("CurrentSkippedTokenNo", CurrentSkippedTokenNo);
			obj.put("current_token",
					new Integer(tokenDocSummary.getCurrent_token()));
			obj.put("total_token",
					new Integer(tokenDocSummary.getTotal_token()));
			obj.put("total_token_doctor",
					new Integer(docSummary.getTotal_token_doctorID()));
			obj.put("total_treated_token_doctorID",
					new Integer(docSummary.getTotal_treated_token()));
			obj.put("total_cancel_token",
					new Integer(docSummary.getTotal_cancel_token()));
			obj.put("login_time", new Long(login_time));

			PrintWriter out = response.getWriter();
			out.println(obj);
		} else if (functionType == 3) {
			// send patient List -- token_details (tokenGroupID)
			// tokenNo,patientno,status,issuetime
			ArrayList<Token> token = db.TokenDetails_doctorWise(userID);
			if (token == null || token.size() <= 0) {
				this.errorResponse(response, "10"); // No data available for
													// this group ID
				return;
			}

			JSONObject obj = new JSONObject();
			obj.put("TotalSkippedTokenNo", TotalSkippedTokenNo);
			obj.put("CurrentSkippedTokenNo", CurrentSkippedTokenNo);
			obj.put("current_token",
					new Integer(tokenDocSummary.getCurrent_token()));
			obj.put("total_token",
					new Integer(tokenDocSummary.getTotal_token()));

			JSONArray toklist = new JSONArray();

			for (int i = 0; i < token.size(); i++) {
				JSONObject obj1 = new JSONObject();
				obj1.put("tokenno", token.get(i).getToken_no());
				obj1.put("patientname", token.get(i).getPatient_name());
				obj1.put("status", token.get(i).getStatus());
				toklist.add(obj1);
			}

			obj.put("patients", toklist);
			PrintWriter out = response.getWriter();
			out.println(obj);
		} else if (functionType == 4) {
			long logoutTime = (System.currentTimeMillis() / 1000);
			boolean result = db.DoctorLogoutUpdate(userID, logoutTime);
			if (!result) {
				this.errorResponse(response, "11"); // logout failed
				return;
			}
			JSONObject obj = new JSONObject();
			obj.put("Error", "0");
			PrintWriter out = response.getWriter();
			out.println(obj);
			//update login_history table
			db.DoctorLogoutUpdateHistory(userID, logoutTime);
		}

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

	protected void errorResponse(HttpServletResponse response, String msg)
			throws IOException {
		// CommonServlet.setResponseContentType(response,
		// HTMLTools.CONTENT_TYPE_PLAIN);
		response.setContentType("application/jsonrequest");
		response.setCharacterEncoding("UTF-8");

		/* display error */
		PrintWriter out = response.getWriter();
		out.println("{  \"Error\": \"" + StringTools.escapeJSON(msg) + "\"}");

	}

}
