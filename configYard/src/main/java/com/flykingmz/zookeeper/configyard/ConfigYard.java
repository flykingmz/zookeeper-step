/**
 * 
 */
package com.flykingmz.zookeeper.configyard;

import java.util.Map;

/**
 * 配置资源接口
 * @author flyking
 * 
 */
public interface ConfigYard {
	/**
	 * 配置平台根节点名称
	 */
	static String yardRoot = "/yard";
	
	/**
	 * 初始化配置
	 */
	void init();
	
	/**
	 * 重新加载配置资源
	 */
	void reload();
	
	/**
	 * 添加配置
	 * @param key
	 * @param value
	 */
	void add(String key, String value);

	/**
	 * 更新配置
	 * @param key
	 * @param value
	 */
	void update(String key, String value);

	/**
	 * 删除配置
	 * @param key
	 */
	void delete(String key);

	/**
	 * 获取配置
	 * @param key
	 * @return
	 */
	String get(String key);

	/**
	 * 获取所有的配置内容
	 * @return
	 */
	Map<String, String> getAll();

}
