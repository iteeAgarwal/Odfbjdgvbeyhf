package com.dan.dqms.dcu;

import java.util.ArrayList;

import org.dqms.util.Print;
import org.json.JSONException;
import org.json.JSONObject;

import com.dan.dqms.staticvar.StaticVariables;

public class JsonParsingWithCall {

	public int jsonParserWithCall(String response) {

		int current_token = 0;

		ArrayList<PateintDetailHelper> ar = new ArrayList<PateintDetailHelper>();
		try {
			JSONObject js = new JSONObject(response);

			if (StaticVariables.getListType() == 1) {
				current_token = js.getInt("token_issue");
				
				StaticVariables.setSkipHigh(current_token);
			} else {
				current_token = js.getInt("token_issue");
			}

		} catch (JSONException ex) {
			StaticVariables.setListType(0);
			StaticVariables.setCalledFlag(0);

			Print.logException("JSONException in DCU showPatients button" , ex);
		}
		return current_token;
	}

	public void jsonParserWithSkip(String response) {

		try {
			JSONObject js = new JSONObject(response);

			StaticVariables.setCurrentSkippedTokenNo(js
					.getInt("CurrentSkippedTokenNo"));

			StaticVariables.setTotalSkippedTokenNo(js
					.getInt("TotalSkippedTokenNo"));

			if (StaticVariables.getListType() == 1) {
				StaticVariables.setCurrent_token(js.getInt("current_token"));
			} else {
				StaticVariables.setCurrent_token(js.getInt("current_token"));

			}

		} catch (JSONException ex) {

			Print.logException("JSONException in DCU jsonParserWithSkip button" , ex);

			StaticVariables.setListType(0);
			StaticVariables.setCalledFlag(0);

		}

	}

	public void jsonParserWithListChange(String response) {

		try {
			JSONObject js = new JSONObject(response);

			

			
			
			
			if (StaticVariables.getListType() == 1) {
				StaticVariables.setCurrentSkippedTokenNo(js
						.getInt("CurrentSkippedTokenNo"));
			} else {
				StaticVariables.setTotalSkippedTokenNo(js
						.getInt("TotalSkippedTokenNo"));

			}


		} catch (JSONException ex) {

			Print.logException("JSONException in jsonParserWithListChange  button"
					, ex);

			StaticVariables.setListType(0);
			StaticVariables.setCalledFlag(0);
		}

	}

}
