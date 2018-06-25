package com.mountainframework.common;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

public class RpcAbortPolicy implements RejectedExecutionHandler {

	@Override
	public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
		String msg = String.format("[PeakRpcServer]%s", executor.getActiveCount());
		System.out.println(msg);
	}

}
