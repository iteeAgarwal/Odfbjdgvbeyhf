package org.dqms.test;

import java.util.ArrayList;

import org.dqms.db.DBAdapter;
import org.dqms.db.WDU;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class WDUApiTest {

	public static void main(String args[]){
		
		String DeviceAddress="111";
		DBAdapter db = new DBAdapter();
        ArrayList<WDU> wduList = db.WDUList(DeviceAddress);
        if(wduList == null || wduList.size() <=0){ 
       	 return; 
        }
        
         JSONObject obj = new JSONObject();
    	 JSONArray toklist = new JSONArray();
    			
    	 for(int i=0;i<wduList.size();i++){
    		JSONObject obj1 = new JSONObject();
    		obj1.put("current_token", wduList.get(i).getCurrent_Token());
	       	obj1.put("depart_name", wduList.get(i).getDepart_Name());
	       	obj1.put("doc_name", wduList.get(i).getDoc_Name());
	       	obj1.put("room_no", wduList.get(i).getRoom_No());
	       	toklist.add(obj1);
    	 }
    
    	 obj.put("wdus", toklist);
   	 
		
    	 long hitTime= (System.currentTimeMillis()/1000);
	     db.DeviceHealthUpdate("abcd", DeviceAddress, hitTime, 2);
	     
	}
}
