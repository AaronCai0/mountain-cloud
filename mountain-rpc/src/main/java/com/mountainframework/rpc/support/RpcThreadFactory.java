package com.mountainframework.rpc.support;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import com.mountainframework.common.constant.Constants;

/**
 * Rpc线程工厂类
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class RpcThreadFactory implements ThreadFactory {

	private static final AtomicInteger threadCount = new AtomicInteger();

	private final String factoryName;

	private final boolean isDaemon;

	private final ThreadGroup threadGroup;

	public RpcThreadFactory() {
		this(Constants.RPC_THREAD_FACTORY_NAME, false);
	}

	public RpcThreadFactory(String prefixName) {
		this(prefixName, false);
	}

	public RpcThreadFactory(String prefixName, boolean isDaemon) {
		this.factoryName = String.format("%s-Thread-", prefixName, threadCount.getAndIncrement());
		this.isDaemon = isDaemon;
		threadGroup = Thread.currentThread().getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(threadGroup, r, factoryName);
		thread.setDaemon(isDaemon);
		return thread;
	}

}
