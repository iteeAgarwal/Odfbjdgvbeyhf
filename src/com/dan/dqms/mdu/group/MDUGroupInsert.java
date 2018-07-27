package com.dan.dqms.mdu.group;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.db.Department;
import org.dqms.db.Device;
import org.dqms.db.GroupMDU;
import org.dqms.db.Room;
import org.dqms.db.TokenGroup;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.MDUList;
import com.dan.dqms.returnlist.SystemDetailsList;

@WebServlet("/MDUGroupInsert")
public class MDUGroupInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public MDUGroupInsert() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		httpsession=request.getSession(false);
		PrintWriter out=response.getWriter();

		Print.logInfo("MDUGroupInsert:doPost", null);
		MDUList toGroupList = new MDUList();
		List<GroupMDU> arrlist = toGroupList.getTOGroupList();
		
		 RoomsList roomList = new RoomsList();
		List<Room> roomsList = roomList.getRooms();

		boolean flag = false;
		//String strFlagCreateOrUpdate="create";
		int checkCounter=0;
        SystemDetailsList systemDetailsList =new SystemDetailsList();   //parul code
        List<Device> systemsDetailsList =systemDetailsList.getSystemDetails();
		Session session = DBManager.getConfiuration();
        Transaction tx = session.beginTransaction();
        
        

		String mduName = request.getParameter("devID");
         int mduID=0;
		String groupName = request.getParameter("mduName");

		String rooms = request.getParameter("rooms");

		String roomSaveBtn = request.getParameter("mduSave");

		String roomEdit = request.getParameter("mduEdit");

		String tokencancelBtn = request.getParameter("mducancel");
  
		
		for ( Device list : systemsDetailsList) {
			if (mduName.equalsIgnoreCase(list.getDevice_name()+"")) {
				
				mduID=list.getDevice_id();
				System.out.println("my id check "+mduID);
				break;
			}
			}
		if (roomSaveBtn != null && mduName!=null && groupName != null) {

			try {

				
				String[] roomsCheckbox = request
						.getParameterValues("roomsCheckbox");
				
				 
				

				StringBuilder builder = new StringBuilder();

				if(roomsCheckbox ==null || roomsCheckbox.length == 0){
					flag=true;
					httpsession.setAttribute("msg","record NOT inserted : NO room selected");
					
				}
				else if (roomsCheckbox.length > 0 && roomsCheckbox !=null) {
					for (String s : roomsCheckbox) {

						if (s.equals(roomsCheckbox[0])) {

						} else {

							builder.append(",");
				 	}
						builder.append(s);
						
					}
					
				}
				
				if (arrlist.size() > 0) {
						
					for (GroupMDU list : arrlist) {
						if (mduID==list.getMdu_id()) {
							
							checkCounter++;
							//strFlagCreateOrUpdate="update";
							request.setAttribute("toGroupList", arrlist);
							//break;
						}
						else{
							//strFlagCreateOrUpdate="create";
							//break;
							  
							
						}

					}
				}
				if (!flag) {
					
					if(checkCounter==0){
						
						GroupMDU groupMDU = new GroupMDU();
						
						
						groupMDU.setMdu_id(mduID);
						groupMDU.setMdu_name(groupName);
						

						if (builder.toString() != null) {
							groupMDU.setRoom_no_list(builder.toString());
						} else {
							groupMDU.setRoom_no_list("");
						}

						session.save(groupMDU);

						tx.commit();
						
						httpsession.setAttribute("msg",
								"MDU Group Name save successfully.....!!");
					}
					else if(checkCounter!=0){

						//GroupMDU groupMDU = new GroupMDU();
						//groupMDU.setMdu_id(Integer.parseInt(mduID));
						//groupMDU.setMdu_name(groupName);
						
						Query qry = session
								.createQuery("update GroupMDU p set  p.mdu_name=?, p.room_no_list=? where p.mdu_id ='"
										+ mduID + "'");
						
						  
					qry.setParameter(0, groupName);
						
						if(builder.toString() != null)
						{
							qry.setParameter(1,builder.toString());
						}
						else
						{
							qry.setParameter(1,"");
						}
						int res = qry.executeUpdate();
						if(res > 0)
						{
							httpsession.setAttribute("msg","MDU Group Name Edit successfully.....!!");
						}
						else
						{
							httpsession.setAttribute("msg","MDU Group Name Edit Unsuccessfully.....!!");
						}
						tx.commit();
						session.close();
						DBManager.closeFactory();
						
						
						
						
						
						
						
						
						
						
						
						
						
						
/*						int iMduID=Integer.parseInt(mduID);
						GroupMDU groupMDU=(GroupMDU)session.get(GroupMDU.class, iMduID);
						groupMDU.setMdu_id(Integer.parseInt(mduID));
						groupMDU.setMdu_name(groupName);
						
						if (builder.toString() != null) {
							groupMDU.setRoom_no_list(builder.toString());
						} else {
							groupMDU.setRoom_no_list("");
						}

						//session.persist(groupMDU);
						session.update(groupMDU);
						

						tx.commit();
						session.close();
						httpsession.setAttribute("msg",
								"MDU Group Name updated successfully.....!!");*/
						
					}
					


				}
				request.setAttribute("roomsList", roomsList);
				
				response.sendRedirect("./MDUGroupList");
				/*request.getRequestDispatcher("./MDUGroupList").forward(request,
						response);*/

			} catch (Exception e) {
				Print.logException("Exception in MDU Group insert  form"+ e,e);
			}

		}
		if (roomEdit != null) {

			  System.out.println("mdu Id in edit .."+mduID);
			try{

			String[] roomsCheckbox = request
					.getParameterValues("roomsCheckbox");

			StringBuilder builder = new StringBuilder();

			if (roomsCheckbox.length > 0 && roomsCheckbox !=null) {
				
				for (String s : roomsCheckbox) {

					if (s.equals(roomsCheckbox[0])) {
                               
						
					} else {

						builder.append(",");
					}
					builder.append(s);
				    
				}
			}
		
			Query qry = session
					.createQuery("update GroupMDU p set  p.mdu_name=?, p.room_no_list=? where p.mdu_id ='"
							+ mduName + "'");
			
			  
			  
		qry.setParameter(0, groupName);
			
			if(builder.toString() != null)
			{
				qry.setParameter(1,builder.toString());
			}
			else
			{
				qry.setParameter(1,"");
			}
			int res = qry.executeUpdate();
			if(res > 0)
			{
				httpsession.setAttribute("msg","MDU Group Name Edit successfully.....!!");
			}
			else
			{
				httpsession.setAttribute("msg","MDU Group Name Edit Unsuccessfully.....!!");
			}
		
			
			tx.commit();
			session.close();
			DBManager.closeFactory();
			request.setAttribute("roomsList", roomsList);
			
			response.sendRedirect("./MDUGroupList");
			/*request.getRequestDispatcher("./MDUGroupList").forward(request,
					response);*/
			
			} catch (Exception e) {
				Print.logException("Exception in MDU Group edit  form",e);
			}


		}

		if (tokencancelBtn != null) {
			
			request.setAttribute("toGroupList", arrlist);
			request.setAttribute("roomsList", roomsList);
			
			response.sendRedirect("./MDUGroupList");
			/*request.getRequestDispatcher("./MDUGroupList").forward(request,
					response);*/

		}

	}
}
