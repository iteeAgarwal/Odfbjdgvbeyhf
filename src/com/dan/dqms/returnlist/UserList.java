package com.dan.dqms.returnlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dqms.db.User;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;

public class UserList {

	public List<User> getAllUsers() {
		ArrayList<User> user = new ArrayList<User>();
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();

		Query qry = session.createQuery("from User p");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();
				User userBean = (User) obc;
				userBean.setRole_id(userBean.getRole_id());
				userBean.setUser_id(userBean.getUser_id());
				userBean.setName(userBean.getName());
				userBean.setPhone_no(userBean.getPhone_no());
				userBean.setEmail_id(userBean.getEmail_id());
				userBean.setUsername(userBean.getUsername());
				userBean.setPassword(userBean.getPassword());
				userBean.setDesignation(userBean.getDesignation());
				userBean.setDepart_id(userBean.getDepart_id());
				userBean.setRoom_id(userBean.getRoom_id());
				userBean.setView(userBean.isView());

				userBean.setLast_updated_on(userBean.getLast_updated_on());

				userBean.setCreation_date(userBean.getCreation_date());
                userBean.setDoc_id(userBean.getDoc_id());
				user.add(userBean);
                 
			}
		} catch (Exception e) {
			Print.logException("Exception in user  list class  " , e);
			
		}
		 t.commit();
         session.close();
         DBManager.closeFactory();
		return user;

	}

	public List<User> getUsers(String userID) {
		ArrayList<User> user = new ArrayList<User>();
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();

		Query qry = session.createQuery("from User p where user_id ='" + userID
				+ "'  ");
		try {
			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				User userBean = (User) obc;

				userBean.setRole_id(userBean.getRole_id());

				userBean.setUser_id(userBean.getUser_id());

				userBean.setName(userBean.getName());

				userBean.setPhone_no(userBean.getPhone_no());

				userBean.setEmail_id(userBean.getEmail_id());

				userBean.setUsername(userBean.getUsername());

				userBean.setPassword(userBean.getPassword());

				userBean.setDesignation(userBean.getDesignation());

				userBean.setDepart_id(userBean.getDepart_id());

				userBean.setRoom_id(userBean.getRoom_id());

				userBean.setView(userBean.isView());

				userBean.setLast_updated_on(userBean.getLast_updated_on());

				userBean.setCreation_date(userBean.getCreation_date());
				
				userBean.setLogin(userBean.isLogin());
				
				userBean.setLogin_time(userBean.getLogin_time());
				
				userBean.setLogout_time(userBean.getLogout_time());

				user.add(userBean);

			}
		} catch (Exception e) {
			Print.logException("Exception in user list class  " , e);
		}
		t.commit();
		session.close();
		DBManager.closeFactory();
		return user;

	}

	public List<User> getDoctors(String roleID) {
		ArrayList<User> user = new ArrayList<User>();
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();

		Query qry = session.createQuery("from User p where role_id ='" + roleID
				+ "'  ");

		try {
			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				User userBean = (User) obc;

				userBean.setRole_id(userBean.getRole_id());

				userBean.setUser_id(userBean.getUser_id());

				userBean.setName(userBean.getName());

				userBean.setPhone_no(userBean.getPhone_no());

				userBean.setEmail_id(userBean.getEmail_id());

				userBean.setUsername(userBean.getUsername());

				userBean.setPassword(userBean.getPassword());

				userBean.setDesignation(userBean.getDesignation());

				userBean.setDepart_id(userBean.getDepart_id());

				userBean.setRoom_id(userBean.getRoom_id());

				userBean.setView(userBean.isView());

				userBean.setLast_updated_on(userBean.getLast_updated_on());

				userBean.setCreation_date(userBean.getCreation_date());

				user.add(userBean);

			}
		} catch (Exception e) {
			Print.logException("Exception in  user list class  " , e);
		}
		t.commit();
		session.close();
		DBManager.closeFactory();
		return user;

	}

	public List<User> getDoctorsWithRoom(String roomID) {
		ArrayList<User> user = new ArrayList<User>();
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();

		Query qry = session
				.createQuery("from User p where role_id ='2' AND room_id ='"
						+ roomID + "' ");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				User userBean = (User) obc;

				userBean.setRole_id(userBean.getRole_id());

				userBean.setUser_id(userBean.getUser_id());

				userBean.setName(userBean.getName());

				userBean.setPhone_no(userBean.getPhone_no());

				userBean.setEmail_id(userBean.getEmail_id());

				userBean.setUsername(userBean.getUsername());

				userBean.setPassword(userBean.getPassword());

				userBean.setDesignation(userBean.getDesignation());

				userBean.setDepart_id(userBean.getDepart_id());

				userBean.setRoom_id(userBean.getRoom_id());

				userBean.setView(userBean.isView());

				userBean.setLast_updated_on(userBean.getLast_updated_on());

				userBean.setCreation_date(userBean.getCreation_date());

				user.add(userBean);

			}
		} catch (Exception e) {
			Print.logException("Exception in  user list class  " , e);
		}
		t.commit();
		session.close();
		DBManager.closeFactory();
		return user;

	}

}
