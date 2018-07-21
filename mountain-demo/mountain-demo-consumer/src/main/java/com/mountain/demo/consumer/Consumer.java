package com.mountain.demo.consumer;

import java.util.concurrent.CountDownLatch;

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
		int parallel = 10000;
		StopWatch sw = new StopWatch();
		sw.start();

		CountDownLatch signal = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(parallel);

		for (int i = 1; i <= parallel; i++) {
			new Thread(getTask(signal, finish, i, calcService)).start();
		}
		signal.countDown();
		finish.await();
		sw.stop();
		String tip = String.format("Mountain RPC调用总共耗时: [%s] 毫秒", sw.getTime());
		System.out.println(tip);

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
