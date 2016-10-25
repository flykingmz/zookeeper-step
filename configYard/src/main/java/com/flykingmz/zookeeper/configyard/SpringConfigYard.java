/**
 * 
 */
package com.flykingmz.zookeeper.configyard;

import java.util.Map;

import org.apache.zookeeper.WatchedEvent;

/**
 * @author flyking
 *
 */
public class SpringConfigYard implements ConfigYard {

	/* (non-Javadoc)
	 * @see com.flykingmz.zookeeper.configyard.ConfigYard#add(java.lang.String, java.lang.String)
	 */
	public void add(String key, String value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.flykingmz.zookeeper.configyard.ConfigYard#update(java.lang.String, java.lang.String)
	 */
	public void update(String key, String value) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.flykingmz.zookeeper.configyard.ConfigYard#delete(java.lang.String)
	 */
	public void delete(String key) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.flykingmz.zookeeper.configyard.ConfigYard#get(java.lang.String)
	 */
	public String get(String key) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.flykingmz.zookeeper.configyard.ConfigYard#getAll()
	 */
	public Map<String, String> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	public void init() {
		// TODO Auto-generated method stub
		
	}

	public void reload() {
		// TODO Auto-generated method stub
		
	}

}
