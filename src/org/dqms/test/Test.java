package org.dqms.test;
import java.io.PrintWriter;

import org.dqms.db.DBAdapter;
import org.dqms.db.Department;
import org.dqms.db.TokenDocSummary;
import org.dqms.db.TokenSummary;
import org.dqms.db.User;
import org.dqms.util.StringTools;
import org.json.simple.JSONObject;


public class Test {

	public static void main(String args[]){
		
		 int  DOCTOR_ROLE_ID  = 2;
         String  userID     = "a1";
         String  password   = "a1";
         
         if ( StringTools.isBlank(userID) || StringTools.isBlank(password)) {
        	 System.out.print("Invalid authorization. Please enter Username, Password and RoomNo.");
        	 return;
         }
         
         DBAdapter db = new DBAdapter();
         User user = db.CheckDoctorExists(userID,password, DOCTOR_ROLE_ID);
         if(user == null){
        	 System.out.print("Invalid authorization. You have entered incorrect UserName Password.");
        	 return; 
         }
         
         if(user.isLogin()){
        	 System.out.print("Invalid authorization. User already logged in from other computer.");
        	 return; 
         }
         
         TokenDocSummary tokenDocSummary = db.TokenSummary_UserId(user.getUser_id());
         if(tokenDocSummary == null){
        	 System.out.print("Invalid authorization. No token registerd for current Room Number.");
        	 return; 
         }
         
         //update user table about doctor login
         long loginTime= (System.currentTimeMillis()/1000);
         boolean result = db.DoctorLoginUpdate(user.getUser_id(), loginTime);
         if(!result){
        	 System.out.print("Invalid authorization. Login Fail.");
        	 return;
         }
         String DepartmentName = Department.getDepartName(user.getDepart_id());
         JSONObject obj = new JSONObject();
         obj.put("doctor_id", new Integer(user.getUser_id()));
     	 obj.put("doctor_name", new String(user.getName()));
     	 obj.put("department", new String(DepartmentName));
     	 obj.put("current_token", new Integer(tokenDocSummary.getCurrent_token()));
     	 obj.put("total_token", new Integer(tokenDocSummary.getTotal_token()));
     	 
     	// PrintWriter out = response.getWriter();
	     //out.println(obj);
         
     	System.out.print(obj);
	}
	
}
