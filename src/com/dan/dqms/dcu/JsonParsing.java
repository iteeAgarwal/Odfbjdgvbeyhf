package com.dan.dqms.dcu;

import java.util.ArrayList;

import org.dqms.util.Print;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.dan.dqms.staticvar.StaticVariables;

public class JsonParsing {

	public ArrayList<PateintDetailHelper> jsonParser(String response) {
		
		
		ArrayList<PateintDetailHelper> ar = new ArrayList<PateintDetailHelper>();
		try {
			JSONObject js = new JSONObject(response);
			JSONArray ja = js.getJSONArray("patients");
			for (int i = 0; i < ja.length(); i++) {
				JSONObject js1 = (JSONObject) ja.get(i);
				ar.add(new PateintDetailHelper(js1.getString("patientname"),
						js1.getInt("status"), js1.getInt("tokenno")));
			}

		} catch (JSONException ex) {

			Print.logException("JSONException in DCU showPatients button" , ex);
			
		}
		return ar;
	}

}
