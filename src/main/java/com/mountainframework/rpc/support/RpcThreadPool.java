package com.mountainframework.rpc.support;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Rpc线程池类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcThreadPool {

	public static ThreadPoolExecutor getThreadPoolExecutor(int threads, int queues) {
		return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
				queues == 0 ? new SynchronousQueue<>()
						: (queues < 0 ? new LinkedBlockingQueue<>() : new LinkedBlockingQueue<>(queues)),
				new RpcThreadFactory(), new RpcThreadPoolAbortPolicy());
	}

}
