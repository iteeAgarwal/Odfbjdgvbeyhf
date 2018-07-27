package com.dan.dqms.user;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.dqms.db.User;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.UserList;
import com.dan.dqms.staticvar.StaticVariables;

@WebServlet("/ChangePassword")
public class ChangePassword extends HttpServlet {
	private static final long serialVersionUID = 1L;
	HttpSession httpsession;
	public ChangePassword() {
		super();

	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		httpsession = request.getSession(false);
		String chapassave = request.getParameter("chapassave");

		String oldPass = request.getParameter("oldPass");

		String newPass = request.getParameter("newPass");

		String rePass = request.getParameter("rePass");

		String responseMess = "";

		String userID = String.valueOf(httpsession.getAttribute("_usrID"));

		Session session = DBManager.getConfiuration();

		Transaction tx = session.beginTransaction();

		if (chapassave != null && oldPass != null && newPass != null
				&& rePass != null) {

			if (newPass.equals(rePass)) {
				UserList userList = new UserList();

				List<User> userListarr = userList.getUsers(userID);

				if (userListarr.size() > 0 && oldPass != null) {
					for (User list : userListarr) {
						if (oldPass.equals(list.getPassword())) {

							try {

								Query qry = session
										.createQuery("update User p set  p.password=? where p.user_id ='"
												+ userID + "'");
								qry.setParameter(0, newPass);

								int res = qry.executeUpdate();

								tx.commit();


								if (res > 0) {
									responseMess = "Password Change Success !!";
									Print.logInfo("Exception in change password successfully---"+ userID);
								} else {
									responseMess = "Password not change success  !!";
									Print.logInfo("Exception in change password Unsuccessfully---"+ userID);

								}

							} catch (Exception e) {
								Print.logException("Exception in change password form---", e);
							}

						}
					}
				}

				else {
					responseMess = "Password does not match !!";
				}

			} else {
				responseMess = "Password does not match !!";

			}

		} else {
			responseMess = "";

		}
		request.setAttribute("responseMess", responseMess);

		request.getRequestDispatcher("admin_change_password.jsp").forward(
				request, response);

		session.close();
		DBManager.closeFactory();
	}

}
