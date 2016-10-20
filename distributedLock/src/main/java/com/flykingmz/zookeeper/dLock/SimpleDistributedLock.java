/**
 * 
 */
package com.flykingmz.zookeeper.dLock;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkNoNodeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实现的一个基于zookeeper的简单分布式锁实现
 * 初始化需要传递zookeeper的host+port
 * @author flyking
 * 
 */
public class SimpleDistributedLock implements DistributedLock {
	private final static Logger logger = LoggerFactory
			.getLogger(SimpleDistributedLock.class);

	private ZkClient client;
	private String rootLockerName;
	private String SUB_LOCK_NAME_PREFIX = "sublock-";
	private ThreadLocal<String> currentLockPath = new ThreadLocal<String>();

	public SimpleDistributedLock(String serverstring, String rootLockerName) {
		this.client = new ZkClient(serverstring);
		this.rootLockerName = rootLockerName;
		this.createRootLock();
	}

	/**
	 * 基于临时序列节点创建的一个锁
	 */
	private void createLock() {
		String selfPath = client.createEphemeralSequential(rootLockerName.concat("/").concat(SUB_LOCK_NAME_PREFIX), 1);
		logger.info("thread {},create Lock and path is {}",Thread.currentThread().getId(),selfPath);
		currentLockPath.set(selfPath.substring(selfPath.lastIndexOf("/")+1));
	}

	/**
	 * 基于持久节点创建的锁的根节点
	 */
	private void createRootLock() {
		this.client.createPersistent(rootLockerName);
	}

	/**
	 * 删除锁
	 */
	private void deleteLock() {
		String selfPath = currentLockPath.get();
		client.delete(rootLockerName.concat("/").concat(
				selfPath));
		logger.info("thread {},delete Lock and path is {} and time {}",Thread.currentThread().getId(),rootLockerName.concat("/").concat(
				selfPath),System.currentTimeMillis());
	}

	/**
	 * 尝试获取锁，如果有返回已获取，如果锁被占用就返回未获取
	 * @return
	 */
	private boolean tryLock() {
		List<String> sortedLocks = this.getSortedChildren();
		String lockPath = currentLockPath.get();
		if (sortedLocks.indexOf(lockPath) == 0) {
			logger.info("thread {} try get Lock ",Thread.currentThread().getId());
			return true;
		}
		return false;
	}

	/**
	 * 获取排序好的子节点
	 * @return
	 */
	private List<String> getSortedChildren() {
		try {
			List<String> children = client.getChildren(rootLockerName);
			Collections.sort(children, new Comparator<String>() {
				public int compare(String lhs, String rhs) {
					return getLockNodeNumber(lhs).compareTo(
							getLockNodeNumber(rhs));
				}
			});
			return children;

		} catch (ZkNoNodeException e) {
			this.createRootLock();
			return getSortedChildren();
		}
	}

	/**
	 * 截取锁编号
	 * @param str zk的临时序列节点名称
	 * @return
	 */
	private String getLockNodeNumber(String str) {
		int index = str.lastIndexOf(SUB_LOCK_NAME_PREFIX);
		if (index >= 0) {
			return index <= str.length() ? str.substring(index) : "";
		}
		return str;
	}

	/**
	 * 阻塞获取锁,存在锁释放和锁监听的并发竞争问题
	 * 需要优化
	 * @param timeToWait
	 * 阻塞最大等待的时间(ms)
	 * @param usePollMode
	 * 是否采用简单轮询模式，还是阻塞等待模式
	 * @return
	 */
	@Deprecated
	private boolean blockLock(long timeToWait, boolean usePollMode) {
		logger.info("thread {} enter block Lock ",Thread.currentThread().getId());
		if(usePollMode){
			return this.blockLock(timeToWait);
		}
		long starTime = System.currentTimeMillis();
		while ((System.currentTimeMillis() - starTime) < timeToWait) {
			boolean getLock = this.tryLock();
			if(getLock){
				logger.info("thread {} block get Lock ",Thread.currentThread().getId());
				return true;
			}
			if (!getLock) {
				List<String> sortedLocks = this.getSortedChildren();
				String lockPath = currentLockPath.get();
				if(sortedLocks.indexOf(lockPath) == 0){
					logger.info("thread {} block get Lock ",Thread.currentThread().getId());
					return true;
				}
				int preIndex = sortedLocks.indexOf(lockPath) - 1;
				String preIndexPath = sortedLocks.get(preIndex);
				String preSequencePath = rootLockerName.concat("/").concat(
						preIndexPath);
				logger.info("thread {} start listen {} and time {} ",Thread.currentThread().getId(),preSequencePath,System.currentTimeMillis());
				final CountDownLatch latch = new CountDownLatch(1);
				IZkDataListener dataListener = new IZkDataListener() {
					
					public void handleDataDeleted(String dataPath) throws Exception {
						logger.info("data path {} handleDataDeleted ",dataPath);									
					}
					
					public void handleDataChange(String dataPath, Object data) throws Exception {
						logger.info("data path {} handleDataChange ",dataPath);						
					}
				};
				
				try {
					client.subscribeDataChanges(preSequencePath,dataListener);
					logger.info("thread {} start wait {} and time {}",Thread.currentThread().getId(),rootLockerName,System.currentTimeMillis());
					latch.await();
				} catch (ZkNoNodeException e){
					logger.info("zk client ZkNoNodeException error!", e);
					client.subscribeDataChanges(preSequencePath, dataListener);
				}catch (Exception e) {
				}

			} else {
				logger.info("thread {} block get Lock ",Thread.currentThread().getId());
				return true;
			}

		}
		return false;
	}
	
	/**
	 * 阻塞等待获取锁，简单的轮询实现
	 * @param timeToWait
	 * 阻塞最大等待的时间(ms)
	 * @return
	 */
	private boolean blockLock(long timeToWait) {
		logger.info("thread {} enter block Lock ",Thread.currentThread().getId());
		long starTime = System.currentTimeMillis();
		while ((System.currentTimeMillis() - starTime) < timeToWait) {
			boolean getLock = this.tryLock();
			if(getLock){
				logger.info("thread {} block get Lock ",Thread.currentThread().getId());
				return true;
			}
		}
		return false;
	}

	public void lock() throws Exception {
		this.createLock();
		boolean isLock = this.tryLock();
		if (isLock) {
			return;
		}
		this.blockLock(Long.MAX_VALUE);
	}

	public boolean lock(long time, TimeUnit unit) throws Exception {
		this.createLock();
		boolean isLock = this.tryLock();
		if (isLock) {
			return true;
		}
		isLock = this.blockLock(unit.toMillis(time));
		if (isLock) {
			return true;
		}
		return false;
	}

	public void unLock() throws Exception {
		this.deleteLock();
	}

	public void release() throws Exception {
		this.client.delete(rootLockerName);
		this.client.close();
	}

}
