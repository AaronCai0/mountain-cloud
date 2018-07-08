package com.mountainframework.rpc.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mountainframework.core.server.RpcServerInitializerTask;

/**
 * Rpc服务代理生成器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcServiceProxyGenerator {

	private static final Logger logger = LoggerFactory.getLogger(RpcServerInitializerTask.class);

	private Lock lock = new ReentrantLock();

	public static RpcServiceProxyGenerator getGenerator() {
		return RpcMessageProxyExecutorHolder.INSTANCE;
	}

	private RpcServiceProxyGenerator() {
	}

	public <T> T generate(String className) {
		try {
			lock.lock();
			Class<?> clazz = Class.forName(className);
			@SuppressWarnings("unchecked")
			T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
					new RpcServiceProxyHandler());
			return t;
		} catch (IllegalArgumentException | ClassNotFoundException e) {
			logger.error("RpcServiceProxyGenerator generate error.", e);
			return null;
		} finally {
			lock.unlock();
		}
	}

	public <T> T generate(Class<T> clazz) {
		try {
			lock.lock();
			@SuppressWarnings("unchecked")
			T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
					new RpcServiceProxyHandler());
			return t;
		} catch (IllegalArgumentException e) {
			logger.error("RpcServiceProxyGenerator generate error.", e);
			return null;
		} finally {
			lock.unlock();
		}
	}

	private static class RpcMessageProxyExecutorHolder {
		static final RpcServiceProxyGenerator INSTANCE = new RpcServiceProxyGenerator();
	}

}
