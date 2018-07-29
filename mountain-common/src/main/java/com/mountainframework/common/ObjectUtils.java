package com.mountainframework.common;

import org.apache.commons.lang3.StringUtils;

/**
 * Object对象工具类
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ObjectUtils {

	public static String toStringIfAbsent(Object obj, String defaultValue) {
		return objectIsAbsent(obj) ? defaultValue : obj.toString();
	}

	public static Boolean toBooleanIfAbsent(Object obj, Boolean defaultValue) {
		return objectIsAbsent(obj) || StringUtils.isBlank(obj.toString()) ? defaultValue
				: Boolean.valueOf(obj.toString());
	}

	public static Integer toIntegerIfAbsent(Object obj, Integer defaultValue) {
		return objectIsAbsent(obj) ? defaultValue : Integer.valueOf(obj.toString());
	}

	public static Long toLongIfAbsent(Object obj, Long defaultValue) {
		return objectIsAbsent(obj) ? defaultValue : Long.valueOf(obj.toString());
	}

	public static Float toFloatIfAbsent(Object obj, Float defaultValue) {
		return objectIsAbsent(obj) ? defaultValue : Float.valueOf(obj.toString());
	}

	public static Double toDoubleIfAbsent(Object obj, Double defaultValue) {
		return objectIsAbsent(obj) ? defaultValue : Double.valueOf(obj.toString());
	}

	private static boolean objectIsAbsent(Object obj) {
		return obj == null || StringUtils.isBlank(obj.toString());
	}
}
