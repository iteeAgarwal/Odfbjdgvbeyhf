package com.dan.dqms.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class MyListener implements HttpSessionListener {

	public MyListener() {
		
	}

	public void sessionCreated(HttpSessionEvent arg0) {
		DeleteChecker ch = new DeleteChecker();
		ch.process_for_token_history();
		ch.process_for_token_details();
	}

	public void sessionDestroyed(HttpSessionEvent arg0) {

	}

}
