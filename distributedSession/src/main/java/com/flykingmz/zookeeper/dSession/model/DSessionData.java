/**
 * 
 */
package com.flykingmz.zookeeper.dSession.model;

import java.io.Serializable;
import java.util.Map;

/**
 * @author flyking
 * 
 */
public class DSessionData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * sessionId
	 */
	private String id;

	/**
	 * session创建时间
	 */
	private Long createTime;

	/**
	 * 最后一次访问时间
	 */
	private Long lastAccessTime;
	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getCreateTime() {
		return createTime;
	}

	public Long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(Long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

	public Map<String, String> getSessionContext() {
		return sessionContext;
	}

	public void setSessionContext(Map<String, String> sessionContext) {
		this.sessionContext = sessionContext;
	}

	/**
	 * session内容体
	 */
	private Map<String, String> sessionContext;

	public DSessionData() {
		this.createTime = System.currentTimeMillis();
		this.lastAccessTime = this.createTime;
	}

}
