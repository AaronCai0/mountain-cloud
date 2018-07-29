package com.mountainframework.serialization.protostuff;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * SchemaCache
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class SchemaCache {

	private static final Logger logger = LoggerFactory.getLogger(SchemaCache.class);

	private static Cache<Class<?>, Schema<?>> cache = CacheBuilder.newBuilder().maximumSize(1024)
			.expireAfterWrite(24, TimeUnit.HOURS).build();

	@SuppressWarnings("unchecked")
	public static <T> Schema<T> get(final Class<?> cls) {
		try {
			return (Schema<T>) cache.get(cls, new Callable<RuntimeSchema<?>>() {
				@Override
				public RuntimeSchema<?> call() throws Exception {
					return RuntimeSchema.createFrom(cls);
				}
			});
		} catch (ExecutionException e) {
			logger.error("Get schema cache fail", e);
			return null;
		}
	}

	public static void putCache(Class<?> key) {
		cache.put(key, RuntimeSchema.createFrom(key));
	}

}
