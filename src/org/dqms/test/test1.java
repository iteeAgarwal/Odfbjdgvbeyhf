package org.dqms.test;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.dqms.db.DBAdapter;
import org.dqms.db.DocSummary;
import org.dqms.db.Token;
import org.dqms.db.TokenDocSummary;
import org.dqms.db.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class test1 {

	public static void main(String args[]){
		
		/*	private static final long serialVersionUID = 1L;
	private static final String  PARM_ROOMNO      ="r";  // room ID 
    private static final String  PARM_TYPE        ="t";  // 1=call, 2=skip, 3=treat, 4=cancel
    private static final String  PARM_LISTTYPE        ="listtype";  // 0=fresh, 1=skip
    private static final String  PARM_FUNCTIONTYPE    ="f";  // 1=call,skip,treat,cancel 2=summary 3=patient list
    private static final String  PARM_TREATTOKENNO    ="traettoken";  //treat or cancel token no
    private static final String  PARM_DOCTORID   ="did";  // login doctor ID*/
		
		String  roomNo  = "1";
   	 	int  functionType  = 1;
        int  type     = 1;
        int  listType     = 1;
        int  lastTokenNo     = 0;
        int  userID     = 2;
        if (userID <=0) {
       	 return;
        }
        
        DBAdapter db = new DBAdapter();
        TokenDocSummary tokenDocSummary = db.TokenSummary_UserId(userID);
        if(tokenDocSummary == null){
       	 return; 
        }

        int tokenGroupID = tokenDocSummary.getToken_group_id();
        int CurrentSkippedTokenNo = 0;
        int TotalSkippedTokenNo =0;
        if(listType ==1 && functionType==2){
       	 CurrentSkippedTokenNo = db.LatestSkippedToken(userID, lastTokenNo);
       	 TotalSkippedTokenNo = db.TotalSkippedToken(userID);
        }
        
        int status = 0;
        long token_call_time =0;
        long token_over_time= 0;
        int currentTokenNo;
        long time= (System.currentTimeMillis()/1000);
        if(functionType == 1){
       	 if(listType == 1 & type==1  & (TotalSkippedTokenNo<=0)){
           	 return; 
       	 }
       	 if(listType == 1 & type==1 & (CurrentSkippedTokenNo == TotalSkippedTokenNo)){
           	 return; 
       	 }
       	 if(type == 1 & (tokenDocSummary.getTotal_token()<=0)){

           	 return;
       	 }
       	 if(type == 1 & (tokenDocSummary.getCurrent_token() == tokenDocSummary.getTotal_token())){
           	 return;
       	 }
       	 if(lastTokenNo > tokenDocSummary.getCurrent_token()){
           	 return;
       	 }
       	 if((type == 2 || type == 3 || type == 4) & lastTokenNo<=0){
           	 return;
       	 }
       	
       	 if(type == 4){
       		 //Canceled
       		 currentTokenNo = lastTokenNo;
       		 token_over_time = time;
       		 status=4;
       	 } else if(type == 3){
       		 //treated
       		 currentTokenNo = lastTokenNo;
       		 token_over_time = time;
       		 status=3;
       	 }else if(type == 2){
       		//update token_details (Skip)
       		 currentTokenNo = lastTokenNo;
       		 token_call_time = time;
       		 status=2;
       	 }else{
       		 if(listType == 1){
       			//update token_details & token_summary (Skip)
       			currentTokenNo = CurrentSkippedTokenNo;
   	        	token_call_time = time;
   	        	status=1;
       		 }else{
	        		//update token_details & token_summary (Fresh)
	        		 currentTokenNo = db.LatestCalledToken(userID, tokenDocSummary.getCurrent_token());
	        		 token_call_time = time;
	        		 status=1;
	        		 if(currentTokenNo == 0){

	                   	 return;
	        		 }
       		 }
       	 }

       	 int result = db.UpdateCallSkippedToken(tokenGroupID, currentTokenNo, token_call_time, token_over_time, status, listType, userID);
   		 if(result<=0){
   			
           	 return; 
   		 }
   		 
   		 JSONObject obj = new JSONObject();
        	 obj.put("TotalSkippedTokenNo", TotalSkippedTokenNo  );
        	 obj.put("CurrentSkippedTokenNo", CurrentSkippedTokenNo);
        	 obj.put("current_token", new Integer(tokenDocSummary.getCurrent_token()));
       	 obj.put("total_token", new Integer(tokenDocSummary.getTotal_token()));
       	 if(type == 1){
       		 obj.put("token_issue", currentTokenNo);
       	 }
        	 //PrintWriter out = response.getWriter();
   	     
   	     
        }else if(functionType == 2){
       	 //get doctor login date time --user_details (doctorID)
       	 User user = db.UserDetails(userID);
       	 long login_time = user.getLogin_time();
       	 //get summary of treated, skipped, canceled token --token_details (roomID,doctorID,tokenGroupID)
       	 DocSummary docSummary = db.DoctorSummary(userID);
       	 
       	 JSONObject obj = new JSONObject();
        	 obj.put("TotalSkippedTokenNo", TotalSkippedTokenNo  );
        	 obj.put("CurrentSkippedTokenNo", CurrentSkippedTokenNo);
        	 obj.put("current_token", new Integer(tokenDocSummary.getCurrent_token()));
        	 obj.put("total_token", new Integer(tokenDocSummary.getTotal_token()));
        	 obj.put("total_token_doctor", new Integer(docSummary.getTotal_token_doctorID()));
        	 obj.put("total_treated_token_doctorID", new Integer(docSummary.getTotal_treated_token()));
        	 obj.put("total_cancel_token", new Integer(docSummary.getTotal_cancel_token()));
        	 obj.put("login_time", new Long(login_time));
       	 
        	 //PrintWriter out = response.getWriter();
   	     
        }else if(functionType == 3){
       	 //send patient List -- token_details (tokenGroupID) tokenNo,patientno,status,issuetime
       	 ArrayList<Token> token = db.TokenDetails_doctorWise(userID);
       	 if(token == null || token.size()<=0){
       		
           	 return; 
       	 }
       	 
       	 JSONObject obj = new JSONObject();
        	 obj.put("TotalSkippedTokenNo", TotalSkippedTokenNo  );
        	 obj.put("CurrentSkippedTokenNo", CurrentSkippedTokenNo);
        	 obj.put("current_token", new Integer(tokenDocSummary.getCurrent_token()));
       	 obj.put("total_token", new Integer(tokenDocSummary.getTotal_token()));
        	 
        	JSONArray toklist = new JSONArray();
        			
        	for(int i=0;i<token.size();i++){
        		JSONObject obj1 = new JSONObject();
        		obj1.put("tokenno", token.get(i).getToken_no()  );
           	obj1.put("patientname", token.get(i).getPatient_name());
           	obj1.put("status", token.get(i).getStatus());
           	toklist.add(obj1);
        	}
        
        	 obj.put("patients", toklist);
       	 //PrintWriter out = response.getWriter();
   	     
        }else if(functionType == 4){
       	 long logoutTime= (System.currentTimeMillis()/1000);
            boolean result = db.DoctorLogoutUpdate(userID, logoutTime);
            if(!result){
           	
           	 return;
            }
            JSONObject obj = new JSONObject();
        	 obj.put("Error", "0");
        	 //PrintWriter out = response.getWriter();
        }
   }
}
