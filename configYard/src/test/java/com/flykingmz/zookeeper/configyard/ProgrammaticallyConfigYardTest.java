/**
 * 
 */
package com.flykingmz.zookeeper.configyard;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author flyking
 * 
 */
public class ProgrammaticallyConfigYardTest {
	private final static Logger logger = LoggerFactory
			.getLogger(ProgrammaticallyConfigYard.class);

	public static void main(String[] args) {
		ProgrammaticallyConfigYard yard = new ProgrammaticallyConfigYard("host:port");
		yard.add("testKey1", "1");
		yard.add("testKey2", "2");
		yard.add("testKey3", "3");
		yard.add("testKey4", "4");
		yard.add("testKey5", "5");
		yard.add("testKey6", "6");
		logger.info("value is===>"+yard.get("testKey1"));
		logger.info("value is===>"+yard.get("testKey2"));
		logger.info("value is===>"+yard.get("testKey3"));
		logger.info("value is===>"+yard.get("testKey4"));
		logger.info("value is===>"+yard.get("testKey5"));
		logger.info("value is===>"+yard.get("testKey6"));
		yard.update("testKey6", "testKey6");
		logger.info("update testKey6 value is===>"+yard.get("testKey6"));
		yard.delete("testKey1");
		yard.delete("testKey2");
		yard.delete("testKey3");
		yard.delete("testKey4");
		yard.delete("testKey5");
		yard.delete("testKey6");
	}
}

