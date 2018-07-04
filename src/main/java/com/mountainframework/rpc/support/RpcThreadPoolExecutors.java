package com.mountainframework.rpc.support;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Rpc线程池工厂类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcThreadPoolExecutors {

	public static ExecutorService newFixedThreadPool(int threads, int queues) {
		return new ThreadPoolExecutor(threads, threads, 0, TimeUnit.MILLISECONDS,
				queues == 0 ? new SynchronousQueue<>()
						: (queues < 0 ? new LinkedBlockingQueue<>() : new LinkedBlockingQueue<>(queues)),
				new RpcThreadFactory(), new RpcThreadPoolAbortPolicy());
	}

}
