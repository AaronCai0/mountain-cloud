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
			Class<?> clazz = getClass(className);
			Preconditions.checkNotNull(clazz, "Class not found:{}", className);
			Enhancer enhancer = new Enhancer(); // 创建加强器，用来创建动态代理类
			enhancer.setSuperclass(clazz); // 为加强器指定要代理的业务类（即：为下面生成的代理类指定父类）
			// 设置回调：对于代理类上所有方法的调用，都会调用CallBack，而Callback则需要实现intercept()方法进行拦
			enhancer.setCallback(interceptor);
			// 创建动态代理类对象并返回
			return (T) enhancer.create();
		} catch (IllegalArgumentException e) {
			logger.error("RpcServiceProxyGenerator generate error.", e);
			return null;
		}
	}

	private Class<?> getClass(String className) {
		try {
			return cache.get(className, new Callable<Class<?>>() {
				@Override
				public Class<?> call() throws Exception {
					return Class.forName(className);
				}
			});
		} catch (ExecutionException e) {
			return null;
		}
	}

	static class CglibProxyGeneratorHolder {
		static final CglibProxyGenerator INSTANCE = new CglibProxyGenerator();
	}

}
