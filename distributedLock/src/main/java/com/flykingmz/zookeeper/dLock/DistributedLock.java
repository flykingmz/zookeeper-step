/**
 * 
 */
package com.flykingmz.zookeeper.dLock;

import java.util.concurrent.TimeUnit;

/**
 * @author flyking
 * 
 */
public interface DistributedLock {
	/**
	 * 获取锁，如果没有得到就一直等待
	 * @throws Exception
	 */
	void lock() throws Exception;

	/**
	 * 获取锁，直到指定时间time超时返回
	 * @param time
	 * @param unit
	 * @return
	 * @throws Exception
	 */
	boolean lock(long time, TimeUnit unit) throws Exception;

	/**
	 * 释放锁
	 * @throws Exception
	 */
	void unLock() throws Exception;
	
	/**
	 * 释放根节点锁
	 * @throws Exception
	 */
	void release() throws Exception;
}
