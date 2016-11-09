/**
 * 
 */
package com.flykingmz.zookeeper.dSession.dao;

import org.I0Itec.zkclient.ZkClient;
import com.flykingmz.zookeeper.dSession.json.Json;
import com.flykingmz.zookeeper.dSession.model.DSessionData;

/**
 * @author flyking
 * 
 */
public class DSessionDaoImpl implements DSessionDao {

	private ZkClient client;

	private String zookeeperURL;

	public void setZookeeperURL(String zookeeperURL) {
		this.zookeeperURL = zookeeperURL;
	}

	public void init() {
		this.client = new ZkClient(zookeeperURL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flykingmz.zookeeper.dSession.dao.DSessionDao#addSession(java.lang
	 * .String, com.flykingmz.zookeeper.dSession.model.DSessionData)
	 */
	public void addSession(String sessionId, DSessionData data) {
		this.client.createPersistent(sessionId, Json.toJson(data));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flykingmz.zookeeper.dSession.dao.DSessionDao#getSession(java.lang
	 * .String)
	 */
	public DSessionData getSession(String sessionId) {
		String sessionData = this.client.readData(sessionId);
		return Json.toObject(sessionData, DSessionData.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.flykingmz.zookeeper.dSession.dao.DSessionDao#delSession(java.lang
	 * .String)
	 */
	public void delSession(String sessionId) {
		this.client.delete(sessionId);
	}

}
