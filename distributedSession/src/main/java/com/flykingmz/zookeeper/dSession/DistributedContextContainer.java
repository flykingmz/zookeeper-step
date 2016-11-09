package com.flykingmz.zookeeper.dSession;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.flykingmz.zookeeper.dSession.service.DSessionService;
import com.flykingmz.zookeeper.dSession.service.DSessionServiceImpl;


/**
 * @author flyking
 */
public class DistributedContextContainer implements ApplicationContextAware{
	private static ApplicationContext			applicationcontext;	

	private DistributedContextContainer() {
	}
	
	public static final DSessionService getSessionService() {
		return applicationcontext.getBean("dSessionServiceImpl", DSessionServiceImpl.class);
	}

	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationcontext = applicationContext;		
	}

}
