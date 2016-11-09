/**
 * 
 */
package com.flykingmz.zookeeper.dSession.dao;

import com.flykingmz.zookeeper.dSession.model.DSessionData;

/**
 * @author flyking
 * 
 */
public interface DSessionDao {
	/**
	 * 添加session数据
	 * @param data
	 */
	void addSession(String sessionId,DSessionData data);

	/**
	 * 获取session数据
	 * @param key
	 * @return
	 */
	DSessionData getSession(String sessionId);

	/**
	 * 删除session数据
	 * @param key
	 */
	void delSession(String sessionId);
}
