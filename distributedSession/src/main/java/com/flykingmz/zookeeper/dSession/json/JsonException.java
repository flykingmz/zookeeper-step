/**
 * 
 */
package com.flykingmz.zookeeper.dSession.json;

/**
 * @author flyking
 *
 */
public class JsonException extends RuntimeException {
	public JsonException(String msg , Throwable e) {
		super(msg,e);
	}
}
