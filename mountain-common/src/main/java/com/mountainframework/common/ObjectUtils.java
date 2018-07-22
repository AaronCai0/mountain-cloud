package com.mountainframework.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Object对象工具类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class ObjectUtils {

	public static String toStringForDefault(Object obj, String defaultValue) {
		return obj == null || StringUtils.isBlank(obj.toString()) ? defaultValue : obj.toString();
	}

}
