package com.mountainframework.test;

import java.util.concurrent.CountDownLatch;

import org.apache.commons.lang3.time.StopWatch;

import com.mountainframework.core.client.RpcClientExecutor;

public class ClientTest {

	public static void main(String[] args) throws InterruptedException {
		RpcClientExecutor executor = new RpcClientExecutor("127.0.0.1", 8989);
		int parallel = 10000;

		StopWatch sw = new StopWatch();
		sw.start();

		CountDownLatch signal = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(parallel);

		for (int i = 1; i <= parallel; i++) {
			CalcParallelRequestThread client = new CalcParallelRequestThread(signal, finish, i);
			new Thread(client).start();
		}

		signal.countDown();
		finish.await();

		sw.stop();

		String tip = String.format("RPC调用总共耗时: [%s] 毫秒", sw.getTime());
		System.out.println(tip);

		executor.stop();

	}

}
