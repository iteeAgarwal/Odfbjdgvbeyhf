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
import org.dqms.db.User;
import org.dqms.util.Print;

import com.dan.dqms.returnlist.TokGroupList;
import com.dan.dqms.returnlist.UserList;
import com.google.gson.Gson;

/**
 * Servlet implementation class GetDoctorWithRoom
 */
@WebServlet("/GetDoctorWithRoom")
public class GetDoctorWithRoom extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetDoctorWithRoom() {
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
		Print.logInfo("GetDoctorWithRoom:doPost", null);
		try {
			String roomID = request.getParameter("roomID");

			UserList userListOb = new UserList();

			List<User> doctorList = userListOb.getDoctors("2");

			List<User> docList = new ArrayList<User>();

			if ("".equals(roomID)) {

			} else {

				if (doctorList.size() > 0 && roomID != null) {
					for (User list : doctorList) {

						if (Integer.parseInt(roomID) == list.getRoom_id()) {

							User userOb = new User();

							userOb.setUser_id(list.getUser_id());

							userOb.setName(list.getName());

							docList.add(userOb);

						}

					}
				}

			}

			Gson gson = new Gson();

			String jsonList = gson.toJson(docList);

			String jsonListOfInfo = " {\"alert\":" + jsonList + " }";
			response.getWriter().write(jsonListOfInfo);

		} catch (Exception e) {
			
			Print.logException("Exception in GetDoctorWithRoom class",e);
		}

	}

}
