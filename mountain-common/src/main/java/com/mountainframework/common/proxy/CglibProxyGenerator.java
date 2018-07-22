package com.mountainframework.common.proxy;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * cglib代理生成器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class CglibProxyGenerator implements ProxyGenerator {

	private static final Logger logger = LoggerFactory.getLogger(CglibProxyGenerator.class);

	private final Cache<String, Class<?>> cache = CacheBuilder.newBuilder().maximumSize(1024)
			.expireAfterWrite(1, TimeUnit.HOURS).build();

	private MethodInterceptor interceptor;

	public static CglibProxyGenerator getGenerator(MethodInterceptor interceptor) {
		CglibProxyGenerator generator = CglibProxyGeneratorHolder.INSTANCE;
		generator.interceptor = interceptor;
		return generator;
	}

	private CglibProxyGenerator() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generate(String className) {
		try {
			Class<?> clazz = getCacheClass(className);
			Preconditions.checkNotNull(clazz, "Class not found:{}", className);
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(clazz);
			enhancer.setCallback(interceptor);
			return (T) enhancer.create();
		} catch (IllegalArgumentException e) {
			logger.error("CglibProxyGenerator generate error", e);
			return null;
		}
	}

	private Class<?> getCacheClass(String className) {
		try {
			return cache.get(className, new Callable<Class<?>>() {
				@Override
				public Class<?> call() throws Exception {
					return Class.forName(className);
				}
			});
		} catch (ExecutionException e) {
			logger.error("Get proxy cache class fail", e);
			return null;
		}
	}

	static class CglibProxyGeneratorHolder {
		static final CglibProxyGenerator INSTANCE = new CglibProxyGenerator();
	}

}
