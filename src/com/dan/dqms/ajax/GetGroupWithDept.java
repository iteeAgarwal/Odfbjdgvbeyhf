package com.dan.dqms.ajax;

import java.io.IOException;
import java.util.Collections;
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

@WebServlet("/GetRoomsWithDept")
public class GetGroupWithDept extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public GetGroupWithDept() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Print.logInfo("GetRoomsWithDept:doPost", null);
		response.setContentType("application/json");
		response.setContentType("text/html");

		try {

			String deptID = request.getParameter("deptID");

			TokGroupList tokenGroupList = new TokGroupList();

			List<TokenGroup> roomsList = null;

			if ("".equals(deptID)) {

			} else {

				roomsList = tokenGroupList.getTOGroupListdeptID(deptID);
			}

			Gson gson = new Gson();

			String jsonList = gson.toJson(roomsList);

			String jsonListOfInfo = " {\"alert\":" + jsonList + " }";
			response.getWriter().write(jsonListOfInfo);

		} catch (Exception e) {
			Print.logException("Exception in GetGroupWithDept class" ,e);
		}

	}

}
