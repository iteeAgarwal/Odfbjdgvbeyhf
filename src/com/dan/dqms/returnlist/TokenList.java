package com.dan.dqms.returnlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dqms.db.Token;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;

public class TokenList {
	
	public List<Token> getAllTokens() {
		List<Token> tokenList = new ArrayList<Token>();
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();

		Query qry = session.createQuery("from Token p");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {
				Object obc = (Object) it.next();
				Token tokenBean = (Token) obc;

				tokenBean.setDepart_id(tokenBean.getDepart_id());
				tokenBean.setToken_group_id(tokenBean.getToken_group_id());
				tokenBean.setToken_no(tokenBean.getToken_no());
				tokenBean.setPatient_id(tokenBean.getPatient_id());
				tokenBean.setLast_alloted_token(tokenBean
						.getLast_alloted_token());
				tokenBean.setToken_issue_time(tokenBean.getToken_issue_time());
				tokenBean.setChecked(tokenBean.isChecked());

				tokenList.add(tokenBean);

			}
		}

		catch (Exception e) {
			Print.logException("Exception in TokenList class  " , e);
		}
		session.close();
		DBManager.closeFactory();
		return tokenList;

	}

}
