package com.mountainframework.common.queue;

import com.lmax.disruptor.RingBuffer;

/**
 * DisruptorService
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface DisruptorService<T> {

	RingBuffer<T> start();

	void stop();

}
