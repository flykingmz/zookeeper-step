/**
 * 
 */
package com.flykingmz.zookeeper.dSession.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flykingmz.zookeeper.dSession.dao.DSessionDao;
import com.flykingmz.zookeeper.dSession.model.DSessionData;

/**
 * @author flyking
 * 
 */
@Service
public class DSessionServiceImpl implements DSessionService {
	@Autowired
	private DSessionDao dSessionDaoImpl;
	
	private Long session_expire_time = 30*60*1000L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flykingmz.zookeeper.dSession.service.DSessionService#getSession(java
	 * .lang.String)
	 */
	public Map<String, String> getSession(String sessionId) {
		DSessionData sessionData = dSessionDaoImpl.getSession(sessionId);
		if((System.currentTimeMillis() - sessionData.getCreateTime())>=session_expire_time){
			dSessionDaoImpl.delSession(sessionId);
			return null;
		}
		sessionData.setLastAccessTime(System.currentTimeMillis());
		dSessionDaoImpl.addSession(sessionId, sessionData);
		return sessionData.getSessionContext();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flykingmz.zookeeper.dSession.service.DSessionService#saveSession(
	 * java.lang.String, java.util.Map)
	 */
	public void saveSession(String sessionId, Map<String, String> session) {
		DSessionData sessionData = new DSessionData();
		sessionData.setSessionContext(session);
		sessionData.setId(sessionId);
		dSessionDaoImpl.addSession(sessionId, sessionData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flykingmz.zookeeper.dSession.service.DSessionService#removeSession
	 * (java.lang.String)
	 */
	public void removeSession(String sessionId) {
		dSessionDaoImpl.delSession(sessionId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flykingmz.zookeeper.dSession.service.DSessionService#setSessionAttribute
	 * (java.lang.String, java.lang.String, java.lang.String)
	 */
	public void setSessionAttribute(String sessionId, String key, String value) {
		DSessionData sessionData = dSessionDaoImpl.getSession(sessionId);
		sessionData.getSessionContext().put(key, value);
		sessionData.setLastAccessTime(System.currentTimeMillis());
		dSessionDaoImpl.addSession(sessionId, sessionData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.flykingmz.zookeeper.dSession.service.DSessionService#
	 * removeSessionAttribute(java.lang.String, java.lang.String)
	 */
	public void removeSessionAttribute(String sessionId, String key) {
		DSessionData sessionData = dSessionDaoImpl.getSession(sessionId);
		sessionData.getSessionContext().remove(key);
		sessionData.setLastAccessTime(System.currentTimeMillis());
		dSessionDaoImpl.addSession(sessionId, sessionData);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flykingmz.zookeeper.dSession.service.DSessionService#getSessionAttribute
	 * (java.lang.String, java.lang.String)
	 */
	public String getSessionAttribute(String sessionId, String key) {
		DSessionData sessionData = dSessionDaoImpl.getSession(sessionId);
		Map<String,String> sessionContext = sessionData.getSessionContext();
		if(sessionContext == null){
			return null;
		}
		sessionData.setLastAccessTime(System.currentTimeMillis());
		dSessionDaoImpl.addSession(sessionId, sessionData);
		return sessionContext.get(key);
	}

}
