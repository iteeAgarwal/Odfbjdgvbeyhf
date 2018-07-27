package com.dan.dqms.rooms;

import java.io.IOException;
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

import org.dqms.db.TokenGroup;
import org.dqms.db.User;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.MDUList;
import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.SystemDetailsList;
import com.dan.dqms.returnlist.TokGroupList;
import com.dan.dqms.returnlist.UserList;

@WebServlet("/RoomNoList")
public class RoomNoList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public RoomNoList() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		httpsession=request.getSession(false);
		
		UserList users = new UserList();
		MDUList mduList = new MDUList();
		RoomsList roomList = new RoomsList();
		
		TokGroupList groupsListOb = new TokGroupList();

		DepartmentList deptListOb = new DepartmentList();
		List<Department> deptList = deptListOb.getDeptList();

		SystemDetailsList systemDetailsOb = new SystemDetailsList();
		List<Device> deviceList = systemDetailsOb.getWDUDeviceList();
		
		

		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();

		String radioroomID = request.getParameter("radioroomID");

		String viewBtn = request.getParameter("roomView");

		String newBtn = request.getParameter("roomNew");

		String EditBtn = request.getParameter("roomEdit");

		String delBtn = request.getParameter("roomdelete");

		if (viewBtn != null && radioroomID != null) {
			
			request.setAttribute("roomList", roomList.getRoomsByID(radioroomID));
			request.setAttribute("deptList", deptList);
			request.setAttribute("deviceList", deviceList);

			request.getRequestDispatcher("room_number_list.jsp").forward(
					request, response);
			
		} else if (newBtn != null) {
			
			request.setAttribute("roomList", roomList.getRooms());
			request.setAttribute("deptList", deptList);
			request.setAttribute("deviceList", deviceList);

			request.getRequestDispatcher("room_number_list.jsp").forward(
					request, response);
			
		} else if (EditBtn != null && radioroomID != null) {
			
			request.setAttribute("roomList", roomList.getRoomsByID(radioroomID));
			request.setAttribute("deptList", deptList);
			request.setAttribute("deviceList", deviceList);

			request.getRequestDispatcher("room_number_list.jsp").forward(
					request, response);
			
		} else if (delBtn != null && radioroomID != null) {
			
			
			try{

			
			boolean tokenGroup = false;
			boolean mduGroup = false;

			List<User> userList = users.getDoctorsWithRoom(radioroomID);

			List<TokenGroup> groupsList = groupsListOb.getTOGroupList();
			
			List<GroupMDU> MDUList = mduList.getTOGroupList();
			
			
			if(groupsList.size() > 0)
			{
				for(int i= 0; i <groupsList.size() ; i++)
				{
					String groupListStr = groupsList.get(i).getRoom_id();
					
					if(groupListStr.contains(radioroomID))
					{
						tokenGroup  = true;
						break;
					}
					
				}
			}
			
			
			if(MDUList.size() > 0)
			{
				for(int i= 0; i <MDUList.size() ; i++)
				{
					String groupListStr = MDUList.get(i).getRoom_no_list();
					
					if(groupListStr.contains(radioroomID))
					{
						mduGroup  = true;
						break;
					}
					
				}
			}
			
			
			

			if (userList.size() > 0) {

				httpsession.setAttribute("msg",
						"First Delete  doctors this Room");

			} else if (tokenGroup) {

				httpsession.setAttribute("msg",
						"First Delete  token group this Room");
			}
			else if(mduGroup)
			{
				httpsession.setAttribute("msg",
						"First Delete  MDU group this Room");
			}

			else {
				Query qry = session
						.createQuery("delete Room p  where p.room_id ='"
								+ radioroomID + "'");
				int res = qry.executeUpdate();

				if (res > 0) {
					httpsession.setAttribute("msg",
							"Room number delete successfully.....!!");
					System.out.println("room no deleted :"+radioroomID);
				} else {
					httpsession.setAttribute("msg",
							"Room number delete Unsuccessfully.....!!");
				}

				tx.commit();

				session.close();
                DBManager.closeFactory();
			}
			}
			catch(Exception ex)
			{
				Print.logException("Exception in room delete form"
						, ex);
			}
			request.setAttribute("roomList", roomList.getRooms());
			request.setAttribute("deptList", deptList);
			request.setAttribute("deviceList", deviceList);

			request.getRequestDispatcher("room_number_list.jsp").forward(
					request, response);
		} else {
			request.setAttribute("roomList", roomList.getRooms());
			request.setAttribute("deptList", deptList);
			request.setAttribute("deviceList", deviceList);

			request.getRequestDispatcher("room_number_list.jsp").forward(
					request, response);
		}
	}

}
