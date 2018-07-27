package com.dan.dqms.dbmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DBManager {

	static Session session;
    static SessionFactory factory;
	public static Session getConfiuration() {
		// creating configuration object
		Configuration cfg = new Configuration();
		cfg.configure("hibernate.cfg.xml");// populates the data of the
											// configuration file

		// creating seession factory object
		 factory = cfg.buildSessionFactory();

		return session = factory.openSession();
	}
	public static boolean closeFactory()
	{
		factory.close();
		return true;
	}
}
