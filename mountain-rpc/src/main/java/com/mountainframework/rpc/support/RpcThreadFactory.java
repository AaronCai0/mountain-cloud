package com.mountainframework.rpc.support;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Rpc线程工厂
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcThreadFactory implements ThreadFactory {

	private static final String DEFAULT_FACTORY_NAME = "Mountain-Rpc-ThreadFactory";

	private static final AtomicInteger threadCount = new AtomicInteger();

	private final String factoryName;

	private final boolean isDaemon;

	private final ThreadGroup threadGroup;

	public RpcThreadFactory() {
		this(DEFAULT_FACTORY_NAME, false);
	}

	public RpcThreadFactory(String prefixName) {
		this(prefixName, false);
	}

	public RpcThreadFactory(String prefixName, boolean isDaemon) {
		this.factoryName = String.format("%s-Thread-", prefixName, threadCount.getAndIncrement());
		this.isDaemon = isDaemon;
		SecurityManager sm = System.getSecurityManager();
		threadGroup = sm == null ? Thread.currentThread().getThreadGroup() : sm.getThreadGroup();
	}

	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(threadGroup, r, factoryName);
		thread.setDaemon(isDaemon);
		return thread;
	}

}
