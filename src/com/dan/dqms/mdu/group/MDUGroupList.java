package com.dan.dqms.mdu.group;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.db.Device;
import org.dqms.db.Room;
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

@WebServlet("/MDUGroupList")
public class MDUGroupList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public MDUGroupList() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		MDUList mduGroupList = new MDUList();
		Print.logInfo("MDUGroupList:doPost", null);
		Session session = DBManager.getConfiuration();
        Transaction tx = session.beginTransaction();
        
        RoomsList roomList = new RoomsList();
		List<Room> roomsList = roomList.getRooms();

		SystemDetailsList retDevList=new SystemDetailsList();
		
		List<Device> devList=retDevList.getMDUDeviceList();
		
		String radioroomID = request.getParameter("radioroomID");

		String viewBtn = request.getParameter("roomView");

		String newBtn = request.getParameter("roomNew");

		String EditBtn = request.getParameter("roomEdit");

		String delBtn = request.getParameter("roomdelete");
		
		
		

		if (viewBtn != null && radioroomID != null) {
			request.setAttribute("toGroupList",
					mduGroupList.getTOGroupListByID(radioroomID));

		} else if (newBtn != null) {
			request.setAttribute("toGroupList", mduGroupList.getTOGroupList());

		} else if (EditBtn != null && radioroomID != null) {
			request.setAttribute("toGroupList",
					mduGroupList.getTOGroupListByID(radioroomID));

		} else if (delBtn != null && radioroomID != null) {

			try{
			
			Query qry = session
					.createQuery("delete GroupMDU p  where p.mdu_id ='"
							+ radioroomID + "'");

			int res = qry.executeUpdate();

			if(res > 0)
			{
				request.setAttribute("msg", "MDU Group delete successfully.....!!");
			}
			else
			{
				request.setAttribute("msg", "MDU Group delete Unsuccessfully.....!!");
			}
			
			tx.commit();

			
			}
			catch(Exception e)
			{
				Print.logException("Exception in MDU Group delete form",e);
				
			}
			

			request.setAttribute("toGroupList", mduGroupList.getTOGroupList());

		} else {
			request.setAttribute("toGroupList", mduGroupList.getTOGroupList());

		}
		session.close();
		DBManager.closeFactory();
		request.setAttribute("MDUDeviceList", devList);
		
		request.setAttribute("roomsList", roomsList);
		request.getRequestDispatcher("mdu_group_list.jsp").forward(request,
				response);

	}

}
