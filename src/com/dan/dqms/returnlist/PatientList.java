package com.dan.dqms.returnlist;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.dqms.db.Patient;
import org.dqms.util.Print;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;

public class PatientList {

	

	public List<Patient> getAllPatients() {
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		
		List<Patient> patList = new ArrayList<Patient>();
		Query qry = session.createQuery("from Patient p");

		try{
		
		List l = qry.list();
		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object obc = (Object) it.next();
			Patient patientBean = (Patient) obc;

			patientBean.setPatient_id(patientBean.getPatient_id());

			patientBean.setPatient_name(patientBean.getPatient_name());

			patientBean.setId_card_no(patientBean.getId_card_no());

			patientBean.setRfid_no(patientBean.getRfid_no());

			patientBean.setPhone_no(patientBean.getPhone_no());

			patientBean.setRoom_id(patientBean.getRoom_id());

			patientBean.setDoctor_id(patientBean.getDoctor_id());

			patientBean.setDepart_id(patientBean.getDepart_id());

			patientBean.setLast_check_time(patientBean.getLast_check_time());

			patientBean.setInsurance(patientBean.isInsurance());

			patList.add(patientBean);

		}
		}
		catch(Exception e)
		{
			Print.logException("Exception in PatientList class", e);
			
		}
		t.commit();
		session.close();
	//	DBManager.closeFactory();
		return patList;

	}

	
	
	
	public List<Patient> getAllPatients(int id) {
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		List<Patient> patList = new ArrayList<Patient>();
		
		if(id==0){
			return getAllPatients();
		}
		
		Query qry = session.createQuery("from Patient p where p.patient_id < "+id);

		try{
		
		List l = qry.list();
		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object obc = (Object) it.next();
			Patient patientBean = (Patient) obc;

			patientBean.setPatient_id(patientBean.getPatient_id());

			patientBean.setPatient_name(patientBean.getPatient_name());

			patientBean.setId_card_no(patientBean.getId_card_no());

			patientBean.setRfid_no(patientBean.getRfid_no());

			patientBean.setPhone_no(patientBean.getPhone_no());

			patientBean.setRoom_id(patientBean.getRoom_id());

			patientBean.setDoctor_id(patientBean.getDoctor_id());

			patientBean.setDepart_id(patientBean.getDepart_id());

			patientBean.setLast_check_time(patientBean.getLast_check_time());

			patientBean.setInsurance(patientBean.isInsurance());

			patList.add(patientBean);

		}
		}
		catch(Exception e)
		{
			Print.logException("Exception in PatientList class", e);
			
		}
		t.commit();
		session.close();
		//DBManager.closeFactory();
		return patList;

	}
	
	
	
	
	
	
	
	
	public List<Patient> getPatient(String patientID) {
		Session session = DBManager.getConfiuration();
		Transaction t = session.beginTransaction();
		List<Patient> patList = new ArrayList<Patient>();
        Query qry = session.createQuery("from Patient d where patient_id = '"
						+ patientID + "'");

        try{
        
		List l = qry.list();
		Iterator it = l.iterator();

		while (it.hasNext()) {
			Object obc = (Object) it.next();
			Patient patientBean = (Patient) obc;

			patientBean.setPatient_id(patientBean.getPatient_id());

			patientBean.setPatient_name(patientBean.getPatient_name());

			patientBean.setId_card_no(patientBean.getId_card_no());

			patientBean.setRfid_no(patientBean.getRfid_no());

			patientBean.setPhone_no(patientBean.getPhone_no());

			patientBean.setRoom_id(patientBean.getRoom_id());

			patientBean.setDoctor_id(patientBean.getDoctor_id());

			patientBean.setDepart_id(patientBean.getDepart_id());

			patientBean.setLast_check_time(patientBean.getLast_check_time());
			
			patientBean.setInsurance(patientBean.isInsurance());

			patList.add(patientBean);

		}
        }
		catch(Exception e)
		{
			Print.logException("Exception in PatientList class", e);
			
		}
        t.commit();
        session.close();
       // DBManager.closeFactory();
		return patList;

	}

}
