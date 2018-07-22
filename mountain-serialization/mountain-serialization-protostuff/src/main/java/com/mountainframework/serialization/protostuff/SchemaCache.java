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

public class SchemaCache {

	private static final Logger logger = LoggerFactory.getLogger(SchemaCache.class);

	public static SchemaCache getInstance() {
		return SchemaCacheHolder.INSTANCE;
	}

	private Cache<Class<?>, Schema<?>> cache = CacheBuilder.newBuilder().maximumSize(1024)
			.expireAfterWrite(1, TimeUnit.HOURS).build();

	public Schema<?> get(final Class<?> cls) {
		try {
			return cache.get(cls, new Callable<RuntimeSchema<?>>() {
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

	private static class SchemaCacheHolder {
		private static final SchemaCache INSTANCE = new SchemaCache();
	}
}
