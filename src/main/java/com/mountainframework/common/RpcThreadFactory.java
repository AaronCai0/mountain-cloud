package com.mountainframework.common;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class RpcThreadFactory implements ThreadFactory {

	private static final String DEFAULT_FACTORY_NAME = "RpcThreadFactory";

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
