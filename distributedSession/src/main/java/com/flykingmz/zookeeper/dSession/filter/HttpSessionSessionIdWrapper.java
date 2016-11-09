package com.flykingmz.zookeeper.dSession.filter;

import java.util.Enumeration;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flykingmz.zookeeper.dSession.DistributedContextContainer;
import com.flykingmz.zookeeper.dSession.Enumerator;


/**
 * @author flyking
 */
public class HttpSessionSessionIdWrapper extends HttpSessionWrapper {
	private final static Logger logger = LoggerFactory
			.getLogger(HttpSessionSessionIdWrapper.class);
	
	private String sessionId;

	public HttpSessionSessionIdWrapper(String sessionId, HttpSession session) {
		super(session);
		this.sessionId = sessionId;
	}

	public Object getAttribute(String key) {
		return DistributedContextContainer.getSessionService().getSessionAttribute(
				sessionId, key);
	}

	public Enumeration getAttributeNames() {
		Map<String, String> session = DistributedContextContainer.getSessionService()
				.getSession(sessionId);
		return (new Enumerator(session.keySet(), true));
	}

	public void invalidate() {
		DistributedContextContainer.getSessionService().removeSession(sessionId);
	}

	public void removeAttribute(String key) {
		DistributedContextContainer.getSessionService().removeSessionAttribute(
				sessionId, key);
	}

	@SuppressWarnings("unchecked")
	public void setAttribute(String key, Object value) {
		if (value instanceof String) {
			DistributedContextContainer.getSessionService().setSessionAttribute(
					sessionId, key, (String) value);
		} else {
			logger.warn("session unsupport not serializable string." + "[key="
					+ key + "]" + "[value=" + value + "]");
		}
	}
	
	@Override
	public String getId() {
		return sessionId;
	}
	
}
