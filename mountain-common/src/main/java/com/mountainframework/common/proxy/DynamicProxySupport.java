package com.mountainframework.common.proxy;

import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.mountainframework.common.constant.VMCacheOption;

/**
 * DynamicProxySupport
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public abstract class DynamicProxySupport {

	private static final Logger logger = LoggerFactory.getLogger(DynamicProxySupport.class);

	private final LoadingCache<String, Class<?>> cache = CacheBuilder.newBuilder()
			.maximumSize(VMCacheOption.DEFAULT.MAXSIZE)
			.expireAfterWrite(VMCacheOption.DEFAULT.DURATION, VMCacheOption.DEFAULT.UNIT)
			.build(new CacheLoader<String, Class<?>>() {
				@Override
				public Class<?> load(String key) throws Exception {
					return Class.forName(key);
				}
			});

	public Class<?> getCacheClass(String className) {
		try {
			return cache.get(className);
		} catch (ExecutionException e) {
			logger.error("Get cglib proxy cache class fail.", e);
			throw new UncheckedExecutionException(e.getMessage(), e);
		}
	}

}
