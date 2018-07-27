package com.dan.dqms.token;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.dqms.db.AppToken;
import org.dqms.db.Department;
import org.dqms.db.Patient;
import org.dqms.db.Room;
import org.dqms.db.TokenGroup;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.AppTokenList;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.PatientList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.TokGroupList;
import com.dan.dqms.returnlist.UserList;
import com.dan.dqms.staticvar.StaticVariables;

@WebServlet("/TokenInsert")
public class TokenInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	HttpServletRequest request;
	HttpServletResponse response;
	public TokenInsert() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		httpsession=request.getSession(false);
		boolean flag = false;
		this.request=request;
		this.response=response;
		String[] tokenWalkArray = { "","C1", "C2", "C3", "C4", "C5", "C6", "C7",
				"C8", "C9", "C10", "C11", "C12", "C13", "C14", "C15", "C16", "C17",
				"C18", "C19", "C20", "C21", "C22", "C23", "C24", "C25", "C26", "C27",
				"C28", "C29", "C30", "C31", "C32", "C33", "C34", "C35", "C36", "C37","C38",
				"C39", "C40", "C41", "C42", "C43", "C44", "C45", "C46", "C47","C48","C49","C50"};

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		TokGroupList groupsListOb = new TokGroupList();
		List<TokenGroup> groupsList = groupsListOb.getTOGroupList();
		

		RoomsList roomListOb = new RoomsList();
		List<Room> roomsList = roomListOb.getRoomsBydepID(String.valueOf(httpsession.getAttribute("_usrDptID")));
		
		UserList userListOb = new UserList();
		List<User> doctorList = userListOb.getDoctors("2");

		String patientID = request.getParameter("patientID");

		PatientList patientlist = new PatientList();
		List<Patient> allPatList = patientlist.getAllPatients();
		List<Patient> patList = patientlist.getPatient(patientID);
		
		try {

			Session session = DBManager.getConfiuration();
			Transaction tx = session.beginTransaction();

			String gettoken = request.getParameter("gettoken");

			String findtoken = request.getParameter("findtoken");

			if (gettoken != null) {

				String ptName = request.getParameter("ptName");

				String phNo = request.getParameter("phNo");

				String idCardNo = request.getParameter("idCardNo");

				String appWalk = request.getParameter("appWalk");

				String select_tokenID = request.getParameter("select_token");

				
				String select_tokenValue=null;
				int value = 0;
				if(select_tokenID!=null){
				select_tokenValue = tokenWalkArray[Integer.parseInt(select_tokenID)];
				value = Integer.parseInt(select_tokenID);//----------------------
				}
				
				
				


				int deptID = Integer.parseInt(request.getParameter("deptID"));

				int room_group_list = 0;

				if ((Integer)httpsession.getAttribute("_usrTokStaDyn") != 1) {
					room_group_list = Integer.parseInt(request
							.getParameter("room_group_list"));
				}

				int rooms_list = Integer.parseInt(request
						.getParameter("rooms_list"));

				String docIDStr = request.getParameter("doctor_list");

				int doctor_list = 0;

				if ("".equals(docIDStr)) {
					doctor_list = 0;
				} else {
					doctor_list = Integer.parseInt(docIDStr);
				}

				String rFid = request.getParameter("rFid");

				String insur = request.getParameter("insur");

				boolean insurance = false;

				if ("true".equals(insur)) {
					insurance = true;
				}
				/*----------create object TokenGeneratorDynamic  --------*/

				/*----------insert patient details-------------------*/

/*				if (allPatList.size() > 0) {
					for (Patient list : allPatList) {
						if (ptName.equalsIgnoreCase(list.getPatient_name())
								&& idCardNo.equalsIgnoreCase(list
										.getId_card_no())) {
							flag = true;
							request.setAttribute("emptymess",
									"Patients Already Exits..!!");

							break;
						}

					}
				}*/
				if (!flag) {

					long todaytTime = System.currentTimeMillis() / 1000;

					Patient patientBean = new Patient();

					patientBean.setPatient_name(ptName);
					patientBean.setId_card_no(idCardNo);
					patientBean.setDoctor_id(doctor_list);
					patientBean.setDepart_id(deptID);
					patientBean.setPhone_no(phNo);
					patientBean.setRoom_id(rooms_list);
					patientBean.setRfid_no(rFid);
					patientBean.setLast_check_time(0);
					patientBean.setInsurance(insurance);

					session.persist(patientBean);

					tx.commit();
					
					PatientList newpatientlist = new PatientList();

					List<Patient> newallPatList = newpatientlist
							.getAllPatients();

					Collections.reverse(newallPatList);

					int patIDNew = newallPatList.get(0).getPatient_id();

					if ((Integer)httpsession.getAttribute("_usrTokStaDyn") == 1) {
						TokenGeneratorStatic ts = new TokenGeneratorStatic();
						
						if(ts.getRoomGroup(String.valueOf(rooms_list))!=0){
							ts.insertintotokendetails(ts.getRoomGroup(String.valueOf(rooms_list)),
									deptID, patIDNew, todaytTime, 0, rooms_list,
									doctor_list,Integer.parseInt(appWalk),value);							
							request.setAttribute("emptymess",
									"Token generate successfully..!!");
							
							String Print_Token;
							if(Integer.parseInt(appWalk) ==1){
								Print_Token = "" + value;
								//System.out.println("value: " + value);
							}else{
								Print_Token = select_tokenValue;
								//System.out.println("select_tokenValue: " + select_tokenValue);
							}
							String dep_name="";
							int curr_tok=0;
							int allo_tok=0;
							String room_no="";
							String doc_name="";
							printProcess(deptID,rooms_list,doctor_list,Print_Token);
						}
						else{
							request.setAttribute("emptymess",
									"Room No is NOT registered with any Room Group !!");
							//return;
						}
						

					} else {
						TokenGeneratorDynamic tokenGeneratorDynamic = new TokenGeneratorDynamic();

						tokenGeneratorDynamic.roomfromGroup(room_group_list,
								room_group_list, doctor_list, todaytTime,
								patIDNew);

						request.setAttribute("emptymess",
								"Token generate successfully..!!");
					}



				}

				
			} else if (findtoken != null) {

				if (patList.size() > 0) {
					request.setAttribute("emptymess", "Patient ID Founds..!!");
				} else {
					request.setAttribute("emptymess",
							"Patient ID Not Founds..!!");
				}

			}

			else {

				request.setAttribute("emptymess", "");

			}

			/**********remaining tokens********************/
			AppTokenList appToken = new AppTokenList();
			List<AppToken> app_tokenListArr = appToken.getRemainTokenAppoint();
			
			
			ArrayList<TokenStatusBean> tokenStatus = (ArrayList<TokenStatusBean>) getTokenStatus();

			request.setAttribute("staticDynamic",
					(Integer)httpsession.getAttribute("_usrTokStaDyn"));

			request.setAttribute("userDeptID", httpsession.getAttribute("_usrDptID"));

			request.setAttribute("deptList", deptList);

			request.setAttribute("groupsList", groupsList);

			request.setAttribute("roomsList", roomsList);

			request.setAttribute("doctorList", doctorList);

			request.setAttribute("patList", patList);

			request.setAttribute("tokenStatus", tokenStatus);
			
			request.setAttribute("app_tokenListArr", app_tokenListArr);
			
			
		//	response.sendRedirect("./token_status_form.jsp");
			request.getRequestDispatcher("./token_status_form.jsp").forward(
					request, response);
			session.close();
			DBManager.closeFactory();
		} catch (Exception e) {
			Print.logException("Exception in  TokenInsert class" , e);
		}

	}

	// ========get Token Status===========//

	public List<TokenStatusBean> getTokenStatus() {
		ArrayList<TokenStatusBean> tokenStatus = new ArrayList<TokenStatusBean>();

		TokenGeneratorData tg = new TokenGeneratorData();

		Connection con = tg.connection();

		TokGroupList tk = new TokGroupList();

		UserList userOb = new UserList();

		try {
			Statement st = con.createStatement();

			ResultSet rs = st.executeQuery("select * from token_doc_summary");

			while (rs.next()) {

				String docName = "";

				String groupName = "";

				TokenStatusBean bean = new TokenStatusBean();

				int userID = rs.getInt("user_id");

				ArrayList<User> userListArr = (ArrayList<User>) userOb
						.getUsers(String.valueOf(userID));

				if (userListArr.size() > 0) {
					docName = userListArr.get(0).getName();
				}

				bean.setUser_id_to(docName);

				int tokenID = rs.getInt("token_group_id");

				List<TokenGroup> tokenGroupList = (ArrayList<TokenGroup>) tk
						.getTOGroupListByID(String.valueOf(tokenID));

				if (tokenGroupList.size() > 0) {
					groupName = tokenGroupList.get(0).getToken_group_name();
				}

				bean.setToken_group_id_to(groupName);

				bean.setTotal_token_to(rs.getInt("total_token"));

				bean.setCurrent_token_to(rs.getInt("current_token"));
				
				
				bean.setTotal_token_appoint(rs.getInt("total_token_walk"));
				
				bean.setCurrent_token_appoint(rs.getInt("current_token_walk"));

				tokenStatus.add(bean);
			}
        
		} catch (Exception e) {
			Print.logException("Exception in  TokenInsert class getTokenStatus method"
					, e);
		}
            try
            {
            	  con.close();
            }
            catch(Exception e)
            {
            	e.printStackTrace();
            }
		return tokenStatus;

	}
	
	void printProcess(int depart_id,int room_id,int doc_id,String Print_Token){
		String dep_name="";
		String curr_tok="__";
		String room_no="";
		String doc_name="";
		int allo_tok=0;
		TokenGeneratorData tg = new TokenGeneratorData();
		//System.out.println("printToken:" + Print_Token);
		Connection con = tg.connection();
		
		try{
			PreparedStatement pstRoomName=con.prepareStatement("select * from room_details where room_id=?");
			pstRoomName.setInt(1, room_id);
			ResultSet rsRoomName=pstRoomName.executeQuery();
			while(rsRoomName.next()){
				room_no=rsRoomName.getString("room_no");
			}
			
			PreparedStatement pstDoctorName=con.prepareStatement("select * from user_details where user_id=?");
			pstDoctorName.setInt(1, doc_id);
			ResultSet rsDoctorName=pstDoctorName.executeQuery();
			while(rsDoctorName.next()){
				doc_name=rsDoctorName.getString("name");
			}
			
			PreparedStatement pstmt2=con.prepareStatement("select * from department_details where depart_id=?");
			pstmt2.setInt(1, depart_id);
			ResultSet rs2=pstmt2.executeQuery();
			while(rs2.next()){
				dep_name=rs2.getString("depart_name");
				
			}PreparedStatement pstmt_last=con.prepareStatement("select * from token_doc_summary where user_id=?");
			pstmt_last.setInt(1, doc_id);
			ResultSet rs_last=pstmt_last.executeQuery();
			while(rs_last.next()){
				curr_tok=rs_last.getString("display_token");
				allo_tok=rs_last.getInt("total_token");
			}
		  con.close();
		if(Print_Token.equalsIgnoreCase("0") || Print_Token == "0"){
			Print_Token = "" + allo_tok;
		}
		String ss=doc_name+"~"+room_no+"~"+dep_name+"~"+curr_tok+"~"+Print_Token;
		//System.out.println("print data:" + ss);
		request.setAttribute("p_data", ss);
		
		//PrinterOptions p=new PrinterOptions();
		//p.SET_VALUES(dep_name,curr_tok , allo_tok,request,response);
		
		}catch(Exception ex){}
	}
	
	
}
