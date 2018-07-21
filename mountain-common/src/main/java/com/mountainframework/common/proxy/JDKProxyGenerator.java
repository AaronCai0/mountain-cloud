package com.mountainframework.common.proxy;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.reflect.AbstractInvocationHandler;
import com.google.common.reflect.Reflection;

/**
 * Rpc服务代理生成器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class JDKProxyGenerator implements ProxyGenerator {

	private static final Logger logger = LoggerFactory.getLogger(JDKProxyGenerator.class);

	private Lock lock = new ReentrantLock();

	private AbstractInvocationHandler handler;

	public static JDKProxyGenerator getGenerator(AbstractInvocationHandler handler) {
		JDKProxyGenerator generator = RpcMessageProxyExecutorHolder.INSTANCE;
		generator.handler = handler;
		return generator;
	}

	private JDKProxyGenerator() {
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T generate(String className) {
		try {
			lock.lock();
			Class<?> clazz = Class.forName(className);
			return (T) Reflection.newProxy(clazz, handler);
		} catch (IllegalArgumentException | ClassNotFoundException e) {
			logger.error("RpcServiceProxyGenerator generate error.", e);
			return null;
		} finally {
			lock.unlock();
		}
	}

	private static class RpcMessageProxyExecutorHolder {
		static final JDKProxyGenerator INSTANCE = new JDKProxyGenerator();
	}

}
