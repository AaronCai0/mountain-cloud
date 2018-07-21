package com.mountainframework.common;

import org.apache.commons.lang3.StringUtils;

public class ObjectUtils {

	public static String toStringForDefault(Object obj, String defaultValue) {
		return obj == null || StringUtils.isBlank(obj.toString()) ? defaultValue : obj.toString();
	}

}
