package com.mountainframework.common.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * cglib代理生成器
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class CglibProxyGenerator extends DynamicProxySupport implements ProxyGenerator {

	private static final Logger logger = LoggerFactory.getLogger(CglibProxyGenerator.class);

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
			Preconditions.checkNotNull(clazz, "Class not found:{}.", className);
			Enhancer enhancer = new Enhancer();
			enhancer.setSuperclass(clazz);
			enhancer.setCallback(interceptor);
			return (T) enhancer.create();
		} catch (IllegalArgumentException e) {
			logger.error("CglibProxyGenerator generate error.", e);
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	static class CglibProxyGeneratorHolder {
		static final CglibProxyGenerator INSTANCE = new CglibProxyGenerator();
	}

	public static Class<?> loadCache(String key, Class<?> value) {
		return null;
	}

}
