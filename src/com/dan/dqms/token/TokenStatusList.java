package com.dan.dqms.token;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dqms.db.Department;
import org.dqms.db.TokenGroup;
import org.dqms.util.Print;

import com.dan.dqms.returnlist.DepartmentList;
import com.dan.dqms.returnlist.TokGroupList;
import com.dan.dqms.returnlist.TokenStaList;

@WebServlet("/TokenStatusList")
public class TokenStatusList extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public TokenStatusList() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			List<Integer> tokenGroupList = new ArrayList<Integer>();

			List<TokenStatusBean> allTokenGroupList = new ArrayList<TokenStatusBean>();

			DepartmentList deptListOb = new DepartmentList();
			List<Department> deptList = deptListOb.getDeptList();

			TokGroupList groupsListOb = new TokGroupList();
			List<TokenGroup> groupsList = groupsListOb.getTOGroupList();

			TokenStaList tokenStatusob = new TokenStaList();
			ArrayList<TokenStatusBean> tokenStatus = (ArrayList<TokenStatusBean>) tokenStatusob
					.getTokenStatus();

			String tokenStatusBtn = request.getParameter("tokenStatus");

			if (tokenStatusBtn != null) {
				int depart = (Integer.parseInt(request.getParameter("depart")));

				if (depart == -1) {
					request.setAttribute("tokenStatus", tokenStatus);
				} else {

					if (groupsList.size() > 0) {

						for (TokenGroup list : groupsList) {

							if (depart == list.getDepart_id()) {

								tokenGroupList.add(list.getToken_group_id());
							}

						}

					} else {
						request.setAttribute("tokenStatus", tokenStatus);
					}

					if (tokenGroupList.size() > 0) {
						for (int i = 0; i < tokenGroupList.size(); i++) {
							allTokenGroupList.addAll(tokenStatusob
									.getTokenStatusByGroup(tokenGroupList
											.get(i)));
						}

						request.setAttribute("tokenStatus", allTokenGroupList);
					} else {
						request.setAttribute("tokenStatus", tokenStatus);
					}

				}

			}

			else {
				request.setAttribute("tokenStatus", tokenStatus);
			}

			request.setAttribute("deptList", deptList);

			request.getRequestDispatcher("token_status_list.jsp").forward(
					request, response);

		} catch (Exception e) {
			Print.logException("Exception in  TokenStatusList class" , e);
		}
	}

}
