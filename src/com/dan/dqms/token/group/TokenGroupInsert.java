package com.dan.dqms.token.group;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.db.Department;
import org.dqms.db.Room;
import org.dqms.db.TokenGroup;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.TokGroupList;

@WebServlet("/TokenGroupInsert")
public class TokenGroupInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public TokenGroupInsert() {
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
		
		TokGroupList toGroupList = new TokGroupList();
		List<TokenGroup> arrlist = toGroupList.getTOGroupList();

		

		Session session = DBManager.getConfiuration();
        Transaction tx = session.beginTransaction();

		String groupName = request.getParameter("groupName");

		String groupID = request.getParameter("groupID");

		String deptI = request.getParameter("deptID");

		int deptID = 0;

		if (deptI != null) {
			deptID = Integer.parseInt(deptI);

		}

		

		String roomSaveBtn = request.getParameter("groupSave");

		String roomEdit = request.getParameter("groupEdit");

		String tokencancelBtn = request.getParameter("tokencancel");

		if (roomSaveBtn != null && groupName != null) {
			flag=false;
			try {
			
			String[] roomsCheckbox = request
					.getParameterValues("roomsCheckbox");

			StringBuilder builder = new StringBuilder();

			//================================
			if(roomsCheckbox == null || roomsCheckbox.length == 0){
				flag=true;
				httpsession.setAttribute("msg","record NOT inserted : NO room selected");
			}
			else if (roomsCheckbox.length > 0 && roomsCheckbox !=null) {
				flag=false;
				for (String s : roomsCheckbox) {

					if (s.equals(roomsCheckbox[0])) {

					} else {

						builder.append(",");
					}
					builder.append(s);
				}
			}
			//================================

				if (arrlist.size() > 0) {
					for (TokenGroup list : arrlist) {
						if (groupName.equalsIgnoreCase(list
								.getToken_group_name())) {
							flag = true;
							httpsession.setAttribute("msg",
									"Group Name already exits.....!!");
							request.setAttribute("toGroupList", arrlist);
							break;
						}

					}
				}
				if (!flag) {
					TokenGroup tokenGroup = new TokenGroup();

					tokenGroup.setToken_group_name(groupName);

					tokenGroup.setDepart_id(deptID);
					
					if(builder.toString() != null)
					{
						tokenGroup.setRoom_id(builder.toString());
					}
					else
					{
						tokenGroup.setRoom_id("");
					}

					

					session.persist(tokenGroup);

					tx.commit();
					
					httpsession.setAttribute("msg",
							"Group Name save successfully.....!!");

				}

				response.sendRedirect("./TokenGroupList");
/*				request.getRequestDispatcher("./TokenGroupList").forward(
						request, response);*/

			} catch (Exception e) {
				Print.logException("Exception in token group insert", e);
			}

		}
		if (roomEdit != null) {
			flag=false;
			try{
			
			String[] roomsCheckbox = request
					.getParameterValues("roomsCheckbox");

			StringBuilder builder = new StringBuilder();
			//================================
			if(roomsCheckbox == null || roomsCheckbox.length == 0){
				flag=true;
				httpsession.setAttribute("msg","record NOT inserted : NO room selected");
			}
			else if (roomsCheckbox !=null && roomsCheckbox.length > 0 ) {
				flag=false;
				for (String s : roomsCheckbox) {

					if (s.equals(roomsCheckbox[0])) {

					} else {

						builder.append(",");
					}
					builder.append(s);
				}
			}
			//================================

			if(!flag){
				Query qry = session
						.createQuery("update TokenGroup p set  p.token_group_name=?, p.room_id=?, p.depart_id=? where p.token_group_id ='"
								+ groupID + "'");
				qry.setParameter(0, groupName);
				

				if(builder.toString() != null)
				{
					qry.setParameter(1,builder.toString());
				}
				else
				{
					qry.setParameter(1,"");
				}
				
				
				qry.setParameter(2, deptID);

				int res = qry.executeUpdate();

				// session.update(p);
				tx.commit();

					httpsession.setAttribute("msg", "Group Name Edit successfully.....!!");				
			}
			

			response.sendRedirect("./TokenGroupList");
/*			request.getRequestDispatcher("./TokenGroupList").forward(request,
					response);*/
			
			}
			catch(Exception e)
			{
				Print.logException("Exception in Edit token group form", e);
			}

		}

		if (tokencancelBtn != null) {
			request.setAttribute("toGroupList", arrlist);

			response.sendRedirect("./TokenGroupList");
/*			request.getRequestDispatcher("./TokenGroupList").forward(request,
					response);*/

		}
		session.close();
		DBManager.closeFactory();
	}
}
