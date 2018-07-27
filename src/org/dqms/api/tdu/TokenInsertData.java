package org.dqms.api.tdu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.List;

import org.dqms.db.Patient;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.dan.dqms.dbmanager.DBManager;
import com.dan.dqms.returnlist.PatientList;
import com.dan.dqms.token.TokenGeneratorData;
import com.dan.dqms.token.TokenGeneratorStatic;

public class TokenInsertData {
	
	public void setPatientDetails(String PtID,String PtName,String contact, String room,String department,String doctor, int token,int type)
	{
		Session session = DBManager.getConfiuration();
		Transaction tx = session.beginTransaction();
		
		int room_id=0;
	    int department_id=0;
	    int doctor_id=0;
	    int tokenGrpID=0;
	    int PatientID=0;
	
		TokenGeneratorData tg = new TokenGeneratorData();
		//System.out.println("printToken:" + Print_Token);
		Connection con = tg.connection();
		
		System.out.println("room "+room);
		System.out.println("===department "+department);
		System.out.println(" doctor "+doctor);
		
		try{
			// Find Department ID by Department Name
			PreparedStatement pstDepartmentID=con.prepareStatement("select * from department_details where depart_name=?");
			pstDepartmentID.setString(1, department);
			ResultSet rsDepartmentID=pstDepartmentID.executeQuery();
			
			while(rsDepartmentID.next()){
				department_id=rsDepartmentID.getInt("depart_id");
				
			}
			
			
			// Find room ID by Room No and Department ID;
			PreparedStatement pstRoomID=con.prepareStatement("select * from room_details where room_no=? and depart_id=?");
			
			pstRoomID.setString(1, room);
			pstRoomID.setInt(2,department_id );
			ResultSet rsRoomID=pstRoomID.executeQuery();
			
			while(rsRoomID.next()){
				room_id=rsRoomID.getInt("room_id");
				
				
			}
			 
			// Find User Id by Department Id and User Name
			
			PreparedStatement pstDoctorID=con.prepareStatement("select * from user_details where depart_id=? and doc_id=? and room_id=? ");
			
			pstDoctorID.setInt(1, department_id);
			pstDoctorID.setString(2, doctor);
			pstDoctorID.setInt(3, room_id);
			ResultSet rsDoctorID=pstDoctorID.executeQuery();
			while(rsDoctorID.next()){
				doctor_id=rsDoctorID.getInt("user_id");
				
			}
			
			
			//Find tokenGrpId by room id and department ID
			PreparedStatement psttokenGrpID=con.prepareStatement("select * from token_group_details where room_id=? and Department_id=?");
			psttokenGrpID.setInt(1, room_id);
			psttokenGrpID.setInt(2, department_id);
			System.out.println(room_id + "     "+department_id);
			ResultSet rstokenGrpID=psttokenGrpID.executeQuery();
			
			while(rstokenGrpID.next()){
				
				tokenGrpID=rstokenGrpID.getInt("token_group_id");
				System.out.println("hi.... tttkkkppp"+tokenGrpID);
				
				
				
			}
			
			
			
			long todaytTime = System.currentTimeMillis() / 1000;

			Patient patientBean = new Patient();
			
		
			
			patientBean.setPatient_name("");
			patientBean.setId_card_no(null);
			patientBean.setDoctor_id(doctor_id);
			patientBean.setDepart_id(department_id);
			patientBean.setPhone_no(contact);
			patientBean.setRoom_id(room_id);
			patientBean.setRfid_no(null);
			patientBean.setLast_check_time(0);
			patientBean.setInsurance(false);
			patientBean.setPatient_aid(PtID);
			session.persist(patientBean);

			tx.commit();
			session.close();
			DBManager.closeFactory();
			PatientList newpatientlist = new PatientList();

			List<Patient> newallPatList = newpatientlist
					.getAllPatients();

			Collections.reverse(newallPatList);

			int patIDNew = newallPatList.get(0).getPatient_id();
	
			TokenGeneratorStatic ts = new TokenGeneratorStatic();
			
			ts.insertintotokendetails(tokenGrpID, department_id, patIDNew, todaytTime, 0, room_id, doctor_id, 1, token);
			con.close();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
		}
	}
	public static void main(String ar[])
	{
		
	}
}
