package com.rocoinfo.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * <dl>
 * <dd>Description:</dd>
 * <dd>Company: 大城若谷信息技术有限公司</dd>
 * <dd>@date：2016/6/28 13:42</dd>
 * <dd>@author：Aaron</dd>
 * </dl>
 */
public class MapUtils {

	private MapUtils() {
	}

	/**
	 * 向map中传入参数 需判断空值
	 * 
	 * @param map map
	 * @param key key
	 * @param value value
	 */
	public static void putNotNull(Map<String, Object> map, String key, Object value) {
		if (map == null || value == null)
			return;
		if (value instanceof String) {
			if (StringUtils.isNotBlank((String) value))
				map.put(key, ((String) value).trim());
		} else {
			map.put(key, value);
		}
	}

	/**
	 * 向map中传入参数 需判断空值,如果是空值 则放入默认值
	 * 
	 * @param map map
	 * @param key key
	 * @param value value
	 */
	public static void putOrElse(Map<String, Object> map, String key, Object value, Object defaultVal) {
		if (map == null || StringUtils.isBlank(key))
			return;
		if (value == null) {
			map.put(key, defaultVal);
			return;
		}
		if (value instanceof String) {
			if (StringUtils.isNotBlank((String) value))
				map.put(key, ((String) value).trim());
			else
				map.put(key, defaultVal);
		} else {
			map.put(key, value);
		}
	}
}
