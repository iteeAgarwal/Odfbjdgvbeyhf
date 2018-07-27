package com.dan.dqms.returnlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dqms.db.Device;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;

public class SystemDetailsList {

	

	public List<Device> getSystemDetails() {
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		List<Device> deviceList = new ArrayList<Device>();
		Query qry = session.createQuery("from Device p");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				Device deviceOb = (Device) obc;

				deviceOb.setDevice_id(deviceOb.getDevice_id());

				deviceOb.setDevice_name(deviceOb.getDevice_name());

				deviceOb.setType(deviceOb.getType());

				deviceOb.setIp(deviceOb.getIp());

				deviceOb.setMac_address(deviceOb.getMac_address());

				deviceOb.setAddress(deviceOb.getAddress());

				deviceOb.setLocation(deviceOb.getLocation());

				deviceOb.setLast_updated(deviceOb.getLast_updated());

				deviceOb.setHealth_check(deviceOb.isHealth_check());

				deviceOb.setLast_hit_time(deviceOb.getLast_hit_time());

				deviceList.add(deviceOb);

			}

		}

		catch (Exception e) {
			Print.logException("Exception in SystemDetailsList class  " , e);
		}
		
		session.close();
		DBManager.closeFactory();
		return deviceList;

	}

	public List<Device> getWDUDeviceList() {
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		List<Device> deviceList = new ArrayList<Device>();
		Query qry = session.createQuery("from Device p where p.type=2");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				Device deviceOb = (Device) obc;

				deviceOb.setDevice_id(deviceOb.getDevice_id());

				deviceOb.setDevice_name(deviceOb.getDevice_name());

				deviceOb.setType(deviceOb.getType());

				deviceOb.setIp(deviceOb.getIp());

				deviceOb.setMac_address(deviceOb.getMac_address());

				deviceOb.setAddress(deviceOb.getAddress());

				deviceOb.setLocation(deviceOb.getLocation());

				deviceOb.setLast_updated(deviceOb.getLast_updated());

				deviceOb.setHealth_check(deviceOb.isHealth_check());

				deviceOb.setLast_hit_time(deviceOb.getLast_hit_time());

				deviceList.add(deviceOb);

			}

		}

		catch (Exception e) {
			Print.logException("Exception in SystemDetailsList class  " , e);
		}
		
		session.close();
		DBManager.closeFactory();
		return deviceList;

	}
	
	public List<Device> getMDUDeviceList(){
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();

		List<Device> deviceList = new ArrayList<Device>();
		Query qry = session.createQuery("from Device p where p.type=1");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				Device deviceOb = (Device) obc;

				deviceOb.setDevice_id(deviceOb.getDevice_id());

				deviceOb.setDevice_name(deviceOb.getDevice_name());

				deviceOb.setType(deviceOb.getType());

				deviceOb.setIp(deviceOb.getIp());

				deviceOb.setMac_address(deviceOb.getMac_address());

				deviceOb.setAddress(deviceOb.getAddress());

				deviceOb.setLocation(deviceOb.getLocation());

				deviceOb.setLast_updated(deviceOb.getLast_updated());

				deviceOb.setHealth_check(deviceOb.isHealth_check());

				deviceOb.setLast_hit_time(deviceOb.getLast_hit_time());

				deviceList.add(deviceOb);

			}

		}

		catch (Exception e) {
			Print.logException("Exception in SystemDetailsList class  " , e);
		}
		
		session.close();
		DBManager.closeFactory();
   		return deviceList;
	}
	
	
	public List<Device> getSystemByID(String deviceID) {
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		List<Device> deviceList = new ArrayList<Device>();

		Query qry = session.createQuery("from Device p where device_id ='"
				+ deviceID + "'");

		try {

			List l = qry.list();

			Iterator it = l.iterator();

			while (it.hasNext()) {

				Object obc = (Object) it.next();

				Device deviceOb = (Device) obc;

				deviceOb.setDevice_id(deviceOb.getDevice_id());

				deviceOb.setDevice_name(deviceOb.getDevice_name());

				deviceOb.setType(deviceOb.getType());

				deviceOb.setIp(deviceOb.getIp());

				deviceOb.setMac_address(deviceOb.getMac_address());

				deviceOb.setAddress(deviceOb.getAddress());

				deviceOb.setLocation(deviceOb.getLocation());

				deviceOb.setLast_updated(deviceOb.getLast_updated());

				deviceOb.setHealth_check(deviceOb.isHealth_check());

				deviceOb.setLast_hit_time(deviceOb.getLast_hit_time());

				deviceList.add(deviceOb);

			}
		}

		catch (Exception e) {
			Print.logException("Exception in SystemDetailsList class  " , e);
		}
	       
        session.close();
        DBManager.closeFactory();
		return deviceList;

	}

}
