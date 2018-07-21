package com.mountainframework.common;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class ReflectionAsmUtils {

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
			return null;
		}
	}

}
