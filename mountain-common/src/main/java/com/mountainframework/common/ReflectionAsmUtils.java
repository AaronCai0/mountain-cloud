package com.mountainframework.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 反射工具类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class ReflectionAsmUtils {

	private static final Logger logger = LoggerFactory.getLogger(ReflectionAsmUtils.class);

	private static final Cache<Class<?>, MethodAccess> cahce = CacheBuilder.newBuilder().maximumSize(1024)
			.expireAfterWrite(1, TimeUnit.HOURS).build();

	public static MethodAccess get(final Class<?> type) {
		try {
			return cahce.get(type, new Callable<MethodAccess>() {
				@Override
				public MethodAccess call() throws Exception {
					return MethodAccess.get(type);
				}
			});
		} catch (ExecutionException e) {
			logger.error("Get cache error", e);
			return null;
		}
	}

}
