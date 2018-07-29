package com.mountainframework.rpc.support;

import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Rpc线程池拒绝策略类
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class RpcThreadPoolAbortPolicy implements RejectedExecutionHandler {

	private static final Logger logger = LoggerFactory.getLogger(RpcThreadPoolAbortPolicy.class);

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
		String msg = String.format(
				"Mountain RPC thread pool rejected!"
						+ " Pool Size: %d (active: %d, core: %d, max: %d, largest: %d),Task: %d (completed: %d),"
						+ "Executor status:(isShutdown:%s, isTerminated:%s, isTerminating:%s)",
				e.getPoolSize(), e.getActiveCount(), e.getCorePoolSize(), e.getMaximumPoolSize(),
				e.getLargestPoolSize(), e.getTaskCount(), e.getCompletedTaskCount(), e.isShutdown(), e.isTerminated(),
				e.isTerminating());
		logger.warn(msg);
		throw new RejectedExecutionException(msg);
	}

}
