package com.mountainframework.rpc.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RpcServiceProxyExecutor {

	private Lock lock = new ReentrantLock();

	private RpcServiceProxyExecutor() {
	}

	public static RpcServiceProxyExecutor getExecutor() {
		return RpcMessageProxyExecutorHolder.getInstance();
	}

	public <T> T newProxyInstance(String className) {
		try {
			lock.lock();
			Class<?> clazz = Class.forName(className);
			@SuppressWarnings("unchecked")
			T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
					new RpcServiceProxyHandler());
			return t;
		} catch (IllegalArgumentException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
	}

	public <T> T newProxyInstance(Class<T> clazz) {
		try {
			lock.lock();
			@SuppressWarnings("unchecked")
			T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz },
					new RpcServiceProxyHandler());
			return t;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
	}

	private static class RpcMessageProxyExecutorHolder {
		private static final RpcServiceProxyExecutor INSTANCE = new RpcServiceProxyExecutor();

		public static RpcServiceProxyExecutor getInstance() {
			return INSTANCE;
		}
	}

}
