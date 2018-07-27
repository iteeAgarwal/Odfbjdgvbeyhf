package com.dan.dqms.rooms;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.db.Room;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;

import com.dan.dqms.returnlist.RoomsList;

@WebServlet("/RoomNoInsert")
public class RoomNoInsert extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public RoomNoInsert() {
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
		int wduID = 0;
		int deptID = 0;

		RoomsList roomList = new RoomsList();
		List<Room> arrlist = roomList.getRooms();

		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();

		String roomID = request.getParameter("roomIDD");

		String roomNo = request.getParameter("roomNo");

		String deptI = request.getParameter("deptID");
		String wduI = request.getParameter("wduID");
		if (deptI != null) {
			deptID = Integer.parseInt(deptI);
			
		}
       if(wduI!=null)
       {
    	   wduID = Integer.parseInt(wduI);
       }
		String location = request.getParameter("location");

		String roomSaveBtn = request.getParameter("roomSave");

		String roomEdit = request.getParameter("roEdit");

		String roomcancelBtn = request.getParameter("roomcancel");

		if (roomSaveBtn != null && roomNo != null) {

			try {

				if (arrlist.size() > 0) {
					for (Room list : arrlist) {
						if (roomNo.equalsIgnoreCase(list.getRoom_no())) {
							flag = true;
							httpsession.setAttribute("msg",
									"Room number already exits.....!!");
							request.setAttribute("roomList",
									roomList.getRooms());
							break;
						}

					}
				}
				if (!flag) {
					Room room = new Room();

					room.setRoom_no(roomNo);
					room.setDepart_id(deptID);
					room.setLocation(location);
					room.setWdu_id(wduID);

					session.persist(room);

					tx.commit();
					
					httpsession.setAttribute("msg",
							"Room number save successfully.....!!");

				}

				response.sendRedirect("./RoomNoList");
				/*request.getRequestDispatcher("./RoomNoList").forward(request,
						response);*/

			} catch (Exception e) {
				Print.logException("Exception in room insert form" , e);
			}

		}
		if (roomEdit != null) {

			try {

				Query qry = session
						.createQuery("update Room p set  p.room_no=?, p.wdu_id=?, p.depart_id=?, p.location=? where p.room_id ='"
								+ roomID + "'");
				qry.setParameter(0, roomNo);
				qry.setParameter(1, wduID);
				qry.setParameter(2, deptID);
				qry.setParameter(3, location);

				int res = qry.executeUpdate();

				if (res > 0) {
					httpsession.setAttribute("msg",
							"Room number Edit successfully.....!!");
				} else {
					httpsession.setAttribute("msg",
							"Room number Edit Unsuccessfully.....!!");
				}

				tx.commit();

				response.sendRedirect("./RoomNoList");
				/*request.getRequestDispatcher("./RoomNoList").forward(request,
						response);*/
			} catch (Exception e) {
				Print.logException("Exception in room edit form" , e);
			}

		}

		if (roomcancelBtn != null) {

			response.sendRedirect("./RoomNoList");
			/*request.getRequestDispatcher("./RoomNoList").forward(request,
					response);*/

		}
		session.close();
		DBManager.closeFactory();

	}
}
