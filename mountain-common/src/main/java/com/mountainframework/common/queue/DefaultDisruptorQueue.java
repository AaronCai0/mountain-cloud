package com.mountainframework.common.queue;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

public class DefaultDisruptorQueue<T> implements DisruptorService<T> {

	private Disruptor<T> disruptor;

	private DefaultDisruptorQueue(Disruptor<T> disruptor) {
		this.disruptor = disruptor;
	}

	public static <T> DefaultDisruptorQueue<T> create(EventFactory<T> eventFactory, EventHandler<T> handler) {
		int ringBufferSize = 1024 * 1024;
		Disruptor<T> disruptor = new Disruptor<T>(eventFactory, ringBufferSize, DaemonThreadFactory.INSTANCE,
				ProducerType.SINGLE, new YieldingWaitStrategy());
		disruptor.handleEventsWith(handler);
		return new DefaultDisruptorQueue<T>(disruptor);
	}

	@Override
	public RingBuffer<T> start() {
		disruptor.start();
		return disruptor.getRingBuffer();
	}

	@Override
	public void stop() {
		disruptor.shutdown();
	}

}
