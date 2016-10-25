/**
 * 
 */
package com.flykingmz.zookeeper.configyard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flyking
 * 
 */
public class ProgrammaticallyConfigYard implements ConfigYard {
	private final static Logger logger = LoggerFactory
			.getLogger(ProgrammaticallyConfigYard.class);
	/**
	 * 存储配置内容
	 */
	private volatile Map<String, String> yardProperties = new HashMap<String, String>();

	private ZkClient client;
	
	private ConfigYardWatcher configYardWatcher;

	public ProgrammaticallyConfigYard(String serverstring) {
		this.client = new ZkClient(serverstring);
		configYardWatcher = new ConfigYardWatcher(client,this);
		this.init();
	}

	/**
	 * 初始化加载配置到内存
	 */
	public void init() {
		if(!client.exists(yardRoot)){
			client.createPersistent(yardRoot);
		}
		if (yardProperties == null) {
			logger.info("start to init yardProperties");
			yardProperties = this.getAll();
			logger.info("init yardProperties over");
		}
	}

	private String contactKey(String key){
		return yardRoot.concat("/").concat(key);
	}
	
	public void add(String key, String value) {
		String contactKey = this.contactKey(key);
		this.client.createPersistent(contactKey, value);
		configYardWatcher.watcher(contactKey);
	}

	public void update(String key, String value) {
		String contactKey = this.contactKey(key);
		this.client.writeData(contactKey, value);
		configYardWatcher.watcher(contactKey);
	}

	public void delete(String key) {
		String contactKey = this.contactKey(key);
		this.client.delete(contactKey);
	}

	public String get(String key) {
		String contactKey = this.contactKey(key);
		return this.client.readData(contactKey);
	}

	public Map<String, String> getAll() {
		List<String> yardList = this.client.getChildren(yardRoot);
		Map<String, String> currentYardProperties = new HashMap<String, String>();
		for(String yard : yardList){
			String value = this.client.readData(yard);
			String key = yard.substring(yard.indexOf("/")+1);
			currentYardProperties.put(key, value);
		}
		return yardProperties;
	}

	public void reload() {
		yardProperties = this.getAll();
	}

}
