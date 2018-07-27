package com.dan.dqms.ajax;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.dqms.util.Print;

public class URLConnectionOpen {

	public String openUrl(String urlString) {

		StringBuilder content = new StringBuilder();
		Print.logInfo("URLConnectionOpen:openUrl", null);
		try {
			String reply1 = "";

			URL url = new URL(urlString);

			HttpURLConnection connection = null;

			InputStream is = null;

			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");

			connection.connect();

			is = connection.getInputStream();

			BufferedReader theReader = new BufferedReader(
					new InputStreamReader(is, "UTF-8"));

			while ((reply1 = theReader.readLine()) != null) {
				content.append(reply1);

			}

			theReader.close();

		} catch (IOException ex) {
			Print.logException("Exception in  URLConnectionOpen class"
					,ex);
		}
		return content.toString();
	}
}
