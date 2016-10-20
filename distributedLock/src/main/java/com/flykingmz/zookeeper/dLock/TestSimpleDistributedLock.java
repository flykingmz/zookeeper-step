package com.flykingmz.zookeeper.dLock;

import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestSimpleDistributedLock {
	private final static Logger logger = LoggerFactory
			.getLogger(TestSimpleDistributedLock.class);
	
	private static final int THREAD_NUM = 100; 
	private static final CountDownLatch threadSemaphore = new CountDownLatch(THREAD_NUM);  

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		final DistributedLock dc = new SimpleDistributedLock("host:port","/rootLock");  
		 for(int i=0; i < THREAD_NUM; i++){  
	            new Thread(){  
	                @Override  
	                public void run() {  
	                    try{  
	                        logger.info("thread start no "+Thread.currentThread().getId());
	                        dc.lock();
	                        dc.unLock();
	                        threadSemaphore.countDown();
	                    } catch (Exception e){  
	                        e.printStackTrace();  
	                    }  
	                }  
	            }.start();  
	        }  
	        try {  
	            threadSemaphore.await();  
				dc.release();
	            logger.info("所有线程运行结束!");  
	        } catch (InterruptedException e) {  
	            e.printStackTrace();  
	        } catch (Exception e) {
				e.printStackTrace();
			} 
	}

}
