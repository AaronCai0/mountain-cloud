package com.mountainframework.common.queue;

import com.lmax.disruptor.RingBuffer;

public interface DisruptorService<T> {

	RingBuffer<T> start();

	void stop();

}
