package com.dan.dqms.returnlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dqms.db.TokenGroup;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;

public class TokGroupList {

	

	public List<TokenGroup> getTOGroupList() {
		List<TokenGroup> deptList = new ArrayList<TokenGroup>();
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		Query qry = session.createQuery("from TokenGroup p");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				TokenGroup toGroupBean = (TokenGroup) obc;

				toGroupBean.setToken_group_id(toGroupBean.getToken_group_id());

				toGroupBean.setToken_group_name(toGroupBean
						.getToken_group_name());

				toGroupBean.setRoom_id(toGroupBean.getRoom_id());

				toGroupBean.setDepart_id(toGroupBean.getDepart_id());

				deptList.add(toGroupBean);

			}
		} catch (Exception e) {
			
			Print.logException("Exception in token group list class  " , e);
		}
		t.commit();
        session.close();
        DBManager.closeFactory();
		return deptList;

	}

	public List<TokenGroup> getTOGroupListByID(String tokenGroupID) {
		List<TokenGroup> deptList = new ArrayList<TokenGroup>();
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		Query qry = session
				.createQuery("from TokenGroup p where token_group_id ='"
						+ tokenGroupID + "'");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				TokenGroup toGroupBean = (TokenGroup) obc;

				toGroupBean.setToken_group_id(toGroupBean.getToken_group_id());

				toGroupBean.setToken_group_name(toGroupBean
						.getToken_group_name());

				toGroupBean.setRoom_id(toGroupBean.getRoom_id());

				toGroupBean.setDepart_id(toGroupBean.getDepart_id());

				deptList.add(toGroupBean);

			}
		} catch (Exception e) {
		
			Print.logException("Exception in token group list class  " ,e);
		}
		t.commit();
        session.close();
        DBManager.closeFactory();
		return deptList;

	}

	public List<TokenGroup> getTOGroupListdeptID(String depart_id) {
		List<TokenGroup> deptList = new ArrayList<TokenGroup>();
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		Query qry = session.createQuery("from TokenGroup p where depart_id ='"
				+ depart_id + "'");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				TokenGroup toGroupBean = (TokenGroup) obc;

				toGroupBean.setToken_group_id(toGroupBean.getToken_group_id());

				toGroupBean.setToken_group_name(toGroupBean
						.getToken_group_name());

				toGroupBean.setRoom_id(toGroupBean.getRoom_id());

				toGroupBean.setDepart_id(toGroupBean.getDepart_id());

				deptList.add(toGroupBean);

			}

		} catch (Exception e) {
			Print.logException("Exception in token group list class  " , e);
		}
		 t.commit();
		session.close();
		DBManager.closeFactory();

		return deptList;

	}

}
