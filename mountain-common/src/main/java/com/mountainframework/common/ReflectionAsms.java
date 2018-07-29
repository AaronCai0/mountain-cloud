package com.mountainframework.common;

import java.util.concurrent.TimeUnit;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.base.Preconditions;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

/**
 * 反射工具类
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ReflectionAsms {

	private static final LoadingCache<Class<?>, MethodAccess> cachedMap = CacheBuilder.newBuilder().maximumSize(1024)
			.expireAfterWrite(24, TimeUnit.HOURS).build(new CacheLoader<Class<?>, MethodAccess>() {
				@Override
				public MethodAccess load(Class<?> key) throws Exception {
					return MethodAccess.get(key);
				}
			});

	private static ReflectionAsmCache cacheAsm;

	public static void initCache(ReflectionAsmCache cacheAsmVar) {
		cacheAsm = cacheAsmVar;
	}

	public static MethodAccess getCache(Class<?> type) {
		return cacheAsm.get(type);
	}

	public static MethodAccess getUncheckedCache(Class<?> type) {
		return cacheAsm.getUnchecked(type);
	}

	public static MethodAccess get(Class<?> type) {
		return Preconditions.checkNotNull(cachedMap.getUnchecked(type), "Get cache methodAccess is null.");
	}

	public static MethodAccess getUnchecked(Class<?> type) {
		return cachedMap.getUnchecked(type);
	}

}
