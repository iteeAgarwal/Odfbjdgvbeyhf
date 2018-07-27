package org.dqms.test;

import java.io.PrintWriter;
import java.util.ArrayList;

import org.dqms.db.DBAdapter;
import org.dqms.db.MDU;
import org.dqms.util.StringTools;
import org.dqms.war.api.mdu.MDUApi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class MDUApiTest {

	public static void main(String args[]){
		
		String  devAddress  = "113";
	    
   	 DBAdapter db = new DBAdapter();
        String rooms = db.MDURoomList(devAddress);
        if(rooms == null || StringTools.isBlank(rooms.trim())){ 
       	 return; 
        }
        
        String room[] = rooms.split(",");
        if(room.length<=0){ 
       	 return; 
        }
        
        ArrayList<MDU> listMDU = db.MDUTokenList(room);
        
        JSONObject obj = new JSONObject();
    	 JSONArray toklist = new JSONArray();
    			
    	 for(int i=0;i<listMDU.size();i++){
    		JSONObject obj1 = new JSONObject();
    		obj1.put("current_token", listMDU.get(i).getCurrent_Token());
       	obj1.put("depart_name", listMDU.get(i).getDepart_Name());
       	obj1.put("doc_name", listMDU.get(i).getDoc_Name());
       	obj1.put("room_no", listMDU.get(i).getRoom_No());
       	toklist.add(obj1);
    	 }
    
    	 obj.put("rooms", toklist);
   	 //PrintWriter out = response.getWriter();

	}
}
