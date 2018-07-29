package com.mountain.demo.consumer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mountain.demo.service.CalcService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mountain-demo-consumer.xml" })
public class Consumer {

	@Resource(name = "calcService")
	private CalcService calcService;

	@Test
	public void testService() throws Exception {
		int parallel = 1;
		StopWatch sw = new StopWatch();
		sw.start();

		CountDownLatch signal = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(parallel);

		ExecutorService service = Executors.newFixedThreadPool(200);
		for (int i = 1; i <= parallel; i++) {
			service.submit(getTask(signal, finish, i, calcService));
		}
		signal.countDown();
		finish.await();
		sw.stop();
		long time = sw.getTime();
		System.out.println("Mountain RPC调用总共耗时 ：" + (double) time / 1000 + " s");
		System.out.println("Mountain RPC调用平均耗时:" + ((double) time) / parallel + " ms");
		System.out.println("Mountain RPC调用TPS:" + Math.ceil(parallel / ((double) time / 1000)));
	}

	public static Runnable getTask(CountDownLatch signal, CountDownLatch finish, int idex, CalcService calcService) {
		return () -> {
			try {
				signal.await();
				Integer result = calcService.add(idex, idex);
				System.out.println(String.format("calc %s+%s result:[%s]", idex, idex, result));
				finish.countDown();
			} catch (Exception e) {
				e.printStackTrace();
			}
		};
	}

}
