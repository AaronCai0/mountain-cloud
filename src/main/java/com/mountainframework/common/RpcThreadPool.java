package com.mountainframework.common;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RpcThreadPool {

	public static ThreadPoolExecutor getThreadPool(int threads, int queues) {
		return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
				queues == 0 ? new SynchronousQueue<>()
						: (queues < 0 ? new LinkedBlockingQueue<>() : new LinkedBlockingQueue<>(queues)),
				new RpcThreadFactory(), new RpcAbortPolicy());
	}

}
