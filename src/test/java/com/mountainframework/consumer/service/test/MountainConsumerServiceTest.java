package com.mountainframework.consumer.service.test;

import java.util.concurrent.CountDownLatch;

import javax.annotation.Resource;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.mountainframework.service.CalcService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:mountain-consumer.xml" })
public class MountainConsumerServiceTest {

	@Resource(name = "calcService")
	private CalcService calcService;

	@Test
	public void testService() throws Exception {

		int parallel = 8000;

		StopWatch sw = new StopWatch();
		sw.start();

		CountDownLatch signal = new CountDownLatch(1);
		CountDownLatch finish = new CountDownLatch(parallel);

		for (int i = 1; i <= parallel; i++) {
			CalcParallelRequestThread client = new CalcParallelRequestThread(signal, finish, i, calcService);
			new Thread(client).start();
		}

		signal.countDown();
		finish.await();

		sw.stop();

		String tip = String.format("RPC调用总共耗时: [%s] 毫秒", sw.getTime());
		System.out.println(tip);

	}

}
