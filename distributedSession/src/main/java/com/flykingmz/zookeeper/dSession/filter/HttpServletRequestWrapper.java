package com.flykingmz.zookeeper.dSession.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author flyking
 */
public class HttpServletRequestWrapper extends javax.servlet.http.HttpServletRequestWrapper {
	private String sessionId = "";

	public HttpServletRequestWrapper(String sessionId , HttpServletRequest request) {
		super(request);
		this.sessionId = sessionId;
	}

	public HttpSession getSession(boolean create) {
		return new HttpSessionSessionIdWrapper(this.sessionId, super.getSession(create));
	}

	public HttpSession getSession() {
		return new HttpSessionSessionIdWrapper(this.sessionId, super.getSession());
	}

}
