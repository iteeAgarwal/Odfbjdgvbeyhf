package com.dan.dqms.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Room;
import org.dqms.util.Print;

import com.dan.dqms.returnlist.RoomsList;
import com.google.gson.Gson;

@WebServlet("/GetRoomWithDept")
public class GetRoomWithDept extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetRoomWithDept() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		doPost(request, response);

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("application/json");
		response.setContentType("text/html");
		Print.logInfo("GetRoomWithDept:doPost", null);
		String deptID = request.getParameter("deptID");
		
		try{

		RoomsList roomlist = new RoomsList();

		List<Room> roomsList = null;

		if ("".equals(deptID)) {
			roomsList = roomlist.getRooms();

		} else {
			StringBuffer sb=request.getRequestURL();
			roomsList = roomlist.getRoomsBydepID(deptID);
			//roomsList = roomlist.getUniqeRoomsBydepID(deptID);

			// roomsList = tokenGroupList.getTOGroupListdeptID(deptID);
		}

		Gson gson = new Gson();

		String jsonList = gson.toJson(roomsList);

		String jsonListOfInfo = " {\"alert\":" + jsonList + " }";
		response.getWriter().write(jsonListOfInfo);
		
		}
		catch(Exception e)
		{
			Print.logException("Exception in  GetRoomWithDept class",e);
		}

	}

}
