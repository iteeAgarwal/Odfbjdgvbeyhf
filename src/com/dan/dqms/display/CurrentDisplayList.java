package com.dan.dqms.display;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.DBAdapter;
import org.dqms.db.Device;
import org.dqms.db.GroupMDU;
import org.dqms.db.MDU;
import org.dqms.util.Print;

import com.dan.dqms.returnlist.MDUList;
import com.dan.dqms.returnlist.SystemDetailsList;

@WebServlet("/CurrentDisplayList")
public class CurrentDisplayList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public CurrentDisplayList() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {
			Print.logInfo("CurrentDisplayList:doPost", null);
			SystemDetailsList systemDetailsOb = new SystemDetailsList();
			List<Device> deviceList = systemDetailsOb.getSystemDetails();

			MDUList mduListOn = new MDUList();

			DBAdapter dbAdapter = new DBAdapter();

			String mduID = request.getParameter("mduID");

			String currentStatusBtn = request.getParameter("currentStatus");

			ArrayList<MDU> listMDU = null;

			if (currentStatusBtn != null && mduID != null) {

				List<GroupMDU> mduGroupList = mduListOn
						.getTOGroupListByID(mduID);

				if (!mduGroupList.isEmpty()) {

					String roomsListStr = mduGroupList.get(0).room_no_list;

					String[] stringArray = roomsListStr.split(",");

					listMDU = dbAdapter.MDUTokenList(stringArray);

					request.setAttribute("listMDU", listMDU);

				} else {

					request.setAttribute("listMDU", listMDU);
				}

			} else {
				request.setAttribute("listMDU", listMDU);

			}

			request.setAttribute("deviceList", deviceList);
			request.getRequestDispatcher("current_display.jsp").forward(
					request, response);

		} catch (Exception e) {
			Print.logException("Exception in current display class ",e);
		}
	}

}
