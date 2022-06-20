package com.kh.mvc.common.listener;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @WebListener
 * - web.xml에 listener등록처리
 *
 */
@WebListener
public class SessionCountListener implements HttpSessionListener {

	private static int activeSessions;
	
    /**
     * Default constructor. 
     */
    public SessionCountListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see HttpSessionListener#sessionCreated(HttpSessionEvent)
     */
    public void sessionCreated(HttpSessionEvent se)  { 
    	activeSessions++;
    	System.out.println("[sessionCreated] 현재 세션 수 : " + activeSessions);
    }

	/**
     * @see HttpSessionListener#sessionDestroyed(HttpSessionEvent)
     */
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	if(activeSessions > 0) {
    		activeSessions--;
    	}
    	System.out.println("[sessionDestroyed] 현재 세션 수 : " + activeSessions);
    	
    }
	
}
