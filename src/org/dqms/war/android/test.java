package org.dqms.war.android;

import java.util.ArrayList;

import org.dqms.db.Token;
import org.json.JSONArray;

import com.dan.dqms.returnlist.AppWalkDoctorWise;

public class test {

	public static void main(String args[]){
		
		AppWalkDoctorWise appWalkOb = new AppWalkDoctorWise();
		ArrayList<Token> listTokenApp = appWalkOb.AppTokenDetails_doctorWise(2, 2);
   	 	JSONArray obj = new JSONArray(listTokenApp);
   	 	System.out.println(obj);
	}
}
