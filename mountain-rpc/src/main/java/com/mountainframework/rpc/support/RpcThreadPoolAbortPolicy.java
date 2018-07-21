package com.mountainframework.rpc.support;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Rpc线程池拒绝策略类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcThreadPoolAbortPolicy implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		String msg = String.format("[Mountain Registry]%s", executor.getActiveCount());
		System.out.println(msg);
	}

}
