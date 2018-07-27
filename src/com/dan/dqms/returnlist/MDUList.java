package com.dan.dqms.returnlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dqms.db.GroupMDU;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;

public class MDUList {

	
	public List<GroupMDU> getTOGroupList() {
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();

		List<GroupMDU> deptList = new ArrayList<GroupMDU>();
        Query qry = session.createQuery("from GroupMDU p");

        try{
        
		List l = qry.list();
        Iterator it = l.iterator();

		while (it.hasNext()) {

			Object obc = (Object) it.next();

			GroupMDU toGroupBean = (GroupMDU) obc;

			toGroupBean.setMdu_id(toGroupBean.getMdu_id());

			toGroupBean.setMdu_name(toGroupBean.getMdu_name());

			toGroupBean.setRoom_no_list(toGroupBean.getRoom_no_list());

			deptList.add(toGroupBean);

		}
        }catch(Exception e)
        {
        	Print.logException("Exception in MDUList  class", e);
        }
        t.commit();
        session.close();
        DBManager.closeFactory();
		return deptList;

	}

	public List<GroupMDU> getTOGroupListByID(String GroupMDUID) {
       
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();

		List<GroupMDU> deptList = new ArrayList<GroupMDU>();
        Query qry = session.createQuery("from GroupMDU p where mdu_id ='"+ GroupMDUID + "'");

        try{
        
		List l = qry.list();
        Iterator it = l.iterator();

		while (it.hasNext()) {

			Object obc = (Object) it.next();

			GroupMDU toGroupBean = (GroupMDU) obc;

			toGroupBean.setMdu_id(toGroupBean.getMdu_id());

			toGroupBean.setMdu_name(toGroupBean.getMdu_name());

			toGroupBean.setRoom_no_list(toGroupBean.getRoom_no_list());

			deptList.add(toGroupBean);

		}
        }
        catch(Exception e)
        {
        	Print.logException("Exception in MDUList  class", e);
        }
        t.commit();
        session.close();
        DBManager.closeFactory();
		return deptList;

	}

}
