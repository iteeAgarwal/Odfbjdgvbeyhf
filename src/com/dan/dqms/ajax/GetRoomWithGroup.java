package com.dan.dqms.ajax;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Room;
import org.dqms.db.TokenGroup;
import org.dqms.util.Print;

import com.dan.dqms.returnlist.RoomsList;
import com.dan.dqms.returnlist.TokGroupList;
import com.google.gson.Gson;

@WebServlet("/GetRoomWithGroup")
public class GetRoomWithGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetRoomWithGroup() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Print.logInfo("GetRoomWithGroup:doPost", null);
		response.setContentType("application/json");
		response.setContentType("text/html");

		String groupID = request.getParameter("groupID");
		
		try{

		TokGroupList tokenGroupList = new TokGroupList();
		RoomsList roomListOb = new RoomsList();

		List<TokenGroup> groupList = null;
		List<Room> roomsList = new ArrayList<Room>();

		if ("".equals(groupID)) {
			groupList = tokenGroupList.getTOGroupList();

		} else {

			groupList = tokenGroupList.getTOGroupListByID(groupID);

			if (groupList.size() > 0) {

				String RoomList = groupList.get(0).room_id;

				String roomdata[] = RoomList.split(",");

				if (roomdata.length > 0) {
					for (int i = 0; i < roomdata.length; i++) {

						Room rooms = new Room();

						List<Room> roomsNum = roomListOb
								.getRoomsByID(roomdata[i]);
						if (roomsNum.size() > 0) {

							rooms.setRoom_id(roomsNum.get(0).room_id);

							rooms.setRoom_no(roomsNum.get(0).room_no);

						}
						roomsList.add(rooms);

					}
				}

			}

		}

		Gson gson = new Gson();

		String jsonList = gson.toJson(roomsList);

		String jsonListOfInfo = " {\"alert\":" + jsonList + " }";
		response.getWriter().write(jsonListOfInfo);

		}
		catch(Exception e)
		{
			Print.logException("Exception in  GetRoomWithGroup"
					,e);
		}
	}

}
