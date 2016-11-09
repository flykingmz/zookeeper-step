/**
 * 
 */
package com.flykingmz.zookeeper.dSession.service;

import java.util.Map;

/**
 * @author flyking
 * 
 */
public interface DSessionService {
	Map<String, String> getSession(String sessionId);

	void saveSession(String sessionId, Map<String, String> session);

	void removeSession(String sessionId);

	void setSessionAttribute(String sessionId, String key, String value);

	void removeSessionAttribute(String sessionId, String key);

	String getSessionAttribute(String sessionId, String key);
}
