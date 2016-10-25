/**
 * 
 */
package com.flykingmz.zookeeper.configyard;

import java.util.List;

import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flyking
 * 
 */
public class ConfigYardWatcher {
	private final static Logger logger = LoggerFactory
			.getLogger(ConfigYardWatcher.class);
	
	private ZkClient client;
	
	private ConfigYardListener configYardListener;
	
	private ConfigYard configYard;

	public ConfigYardWatcher(ZkClient client,ConfigYard configYard) {
		this.client = client;
		this.configYard = configYard;
		this.initConfigYard();
	}
	
	private void initConfigYard(){
		configYardListener = new ConfigYardListener();
	}  
	
	public void watcher(String key){
		client.subscribeDataChanges(key, configYardListener);
		client.subscribeChildChanges(key, configYardListener);
	}

	/**
	 * 配置监听器
	 * @author wenhui
	 *
	 */
	private class ConfigYardListener implements IZkDataListener,IZkChildListener{
		public void handleDataChange(String dataPath, Object data)
				throws Exception {
			logger.info("data {} change,start reload configProperties",dataPath);
			configYard.reload();
		}

		public void handleDataDeleted(String dataPath) throws Exception {
			logger.info("data {} delete,start reload configProperties",dataPath);
			configYard.reload();
		}

		public void handleChildChange(String parentPath,
				List<String> currentChilds) throws Exception {
			logger.info("data {} ChildChange,start reload configProperties",parentPath);
			configYard.reload();
		}
		
	}
}
