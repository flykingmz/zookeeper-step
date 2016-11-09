/**
 * 
 */
package com.flykingmz.zookeeper.dSession.json;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;

public class Json {
	private static ObjectMapper mapper = new ObjectMapper(); // can reuse, share
	private static final Log logger = LogFactory.getLog(Json.class);

	/**
	 * 将对象转成json.
	 * 
	 * @param obj
	 *            对象
	 * @return
	 */
	public static String toJson(Object obj) {
		if (obj == null) {
			return null;
		}
		try {
			String str = mapper.writeValueAsString(obj);
			return str;
		}
		catch (JsonGenerationException e) {
			// LOGGER.info(e.getMessage(), e);
			logger.warn(e.getMessage());
			throw new JsonException(e.getMessage(), e);
		}
		catch (JsonMappingException e) {
			// LOGGER.info(e.getMessage(), e);
			logger.warn(e.getMessage());
			throw new JsonException(e.getMessage(), e);
		}
		catch (IOException e) {
			// LOGGER.info(e.getMessage(), e);
			logger.warn(e.getMessage());
			throw new JsonException(e.getMessage(), e);
		}
	}

	/**
	 * json转List.
	 * 
	 * @param content
	 *            json数据
	 * @param valueType
	 *            泛型数据类型
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static <T> List<T> toListObject(String content, Class<T> valueType) {
		if (content == null || content.length() == 0) {
			return null;
		}
		try {
			return mapper.readValue(content, TypeFactory.collectionType(List.class, valueType));
		}
		catch (JsonParseException e) {
			logger.warn("message:" + e.getMessage() + " content:" + content);
			throw new JsonException(e.getMessage(), e);
		}
		catch (JsonMappingException e) {
			logger.warn("message:" + e.getMessage() + " content:" + content);
			throw new JsonException(e.getMessage(), e);
		}
		catch (IOException e) {
			logger.warn("message:" + e.getMessage() + " content:" + content);
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static <T> List<T> toObject(List<String> jsonList, Class<T> valueType) {
		if (jsonList == null || jsonList.isEmpty()) {
			return null;
		}
		List<T> list = new ArrayList<T>();
		for (String json : jsonList) {
			list.add(Json.toObject(json, valueType));
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toMap(String content) {
		return Json.toObject(content, Map.class);
	}

	@SuppressWarnings("unchecked")
	public static Set<Object> toSet(String content) {
		return Json.toObject(content, Set.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> toMap(String json, Class<T> clazz) {
		return Json.toObject(json, Map.class);
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> toSet(String json, Class<T> clazz) {
		return Json.toObject(json, Set.class);
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> toNotNullMap(String json) {
		Map<String, Object> map = Json.toObject(json, Map.class);
		if (map == null) {
			map = new LinkedHashMap<String, Object>();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> toNotNullMap(String json, Class<T> clazz) {
		Map<String, T> map = Json.toObject(json, Map.class);
		if (map == null) {
			map = new LinkedHashMap<String, T>();
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <T> Set<T> toNotNullSet(String json, Class<T> clazz) {
		Set<T> set = Json.toObject(json, Set.class);
		if (set == null) {
			set = new LinkedHashSet<T>();
		}
		return set;
	}

	/**
	 * 类型转换.
	 * 
	 * @param obj
	 * @param clazz
	 * @return
	 */
	public static <T> T convert(Object obj, Class<T> clazz) {
		String json = Json.toJson(obj);
		return toObject(json, clazz);
	}

	/**
	 * 将Json转换成对象.
	 * 
	 * @param json
	 * @param valueType
	 * @return
	 */
	public static <T> T toObject(String json, Class<T> clazz) {
		if (json == null || json.length() == 0) {
			return null;
		}
		try {
			return mapper.readValue(json, clazz);
		}
		catch (JsonParseException e) {
			logger.warn("message:" + e.getMessage() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}
		catch (JsonMappingException e) {
			logger.warn("message:" + e.getMessage() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}
		catch (IOException e) {
			logger.warn("message:" + e.getMessage() + " json:" + json);
			throw new JsonException(e.getMessage(), e);
		}
	}

	public static void print(Object obj) {
		String json = Json.toJson(obj);
		System.out.println("json:" + json);
	}

	public static void print(Object obj, String name) {
		String json = Json.toJson(obj);
		System.out.println("json info " + name + "::" + json);
	}

	public static void main(String[] args) {

	}

}
