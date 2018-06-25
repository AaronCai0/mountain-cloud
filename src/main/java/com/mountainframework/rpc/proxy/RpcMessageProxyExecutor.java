package com.mountainframework.rpc.proxy;

import java.lang.reflect.Proxy;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RpcMessageProxyExecutor {

	private Lock lock = new ReentrantLock();

	private RpcMessageProxyExecutor() {
	}

	public static RpcMessageProxyExecutor getInstance() {
		return RpcMessageProxyExecutorHolder.getInstance();
	}

	public <T> T execute(Class<T> clazz) {
		try {
			lock.lock();
			@SuppressWarnings("unchecked")
			T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] { clazz }, new RpcMessageProxy());
			return t;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
	}

	private static class RpcMessageProxyExecutorHolder {
		private static final RpcMessageProxyExecutor INSTANCE = new RpcMessageProxyExecutor();

		public static RpcMessageProxyExecutor getInstance() {
			return INSTANCE;
		}
	}

}
