package com.mountainframework.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 日志工具类
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class Loggers {

	private static final Logger logger = LoggerFactory.getLogger(Loggers.class);

	public static Logger getLogger() {
		return logger;
	}

}
