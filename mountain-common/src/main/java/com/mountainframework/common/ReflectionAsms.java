package com.mountainframework.common;

import java.util.Map;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

/**
 * 反射工具类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class ReflectionAsms {

	// private static final Logger logger =
	// LoggerFactory.getLogger(ReflectionAsmUtils.class);

	// private static final Cache<Class<?>, MethodAccess> cahce =
	// CacheBuilder.newBuilder().maximumSize(1024)
	// .expireAfterWrite(24, TimeUnit.HOURS).build(new CacheLoader<K, V1>() {
	// @Override
	// public V1 load(K key) throws Exception {
	// return null;
	// }
	// });

	// public static MethodAccess get(final Class<?> type) {
	// try {
	// return cahce.get(type, new Callable<MethodAccess>() {
	// @Override
	// public MethodAccess call() throws Exception {
	// return MethodAccess.get(type);
	// }
	// });
	// } catch (ExecutionException e) {
	// logger.error("Get cache error", e);
	// return null;
	// }
	// }

	private static final Map<Class<?>, MethodAccess> cacheMap = Maps.newConcurrentMap();

	public static MethodAccess get(final Class<?> type) {
		return Preconditions.checkNotNull(cacheMap.get(type), "Get cache methodAccess is null");
	}

	public static MethodAccess getUnchecked(final Class<?> type) {
		return cacheMap.get(type);
	}

	// public static Map<Class<?>, MethodAccess> getCacheMap() {
	// return cacheMap;
	// }

	public static void loadClassCache(Class<?> cls) {
		cacheMap.put(cls, MethodAccess.get(cls));
	}

	public static void loadClassCache(Class<?>[] clses) {
		for (Class<?> cls : clses) {
			cacheMap.put(cls, MethodAccess.get(cls));
		}
	}

}
