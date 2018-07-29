package com.mountainframework.common.queue;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import com.mountainframework.common.constant.DisruptorQueueOption;

/**
 * DefaultDisruptorQueue
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class DefaultDisruptorQueue<T> implements DisruptorService<T> {

	private Disruptor<T> disruptor;

	private DefaultDisruptorQueue(Disruptor<T> disruptor) {
		this.disruptor = disruptor;
	}

	public static <T> DefaultDisruptorQueue<T> create(EventFactory<T> eventFactory, EventHandler<T> handler) {
		Disruptor<T> disruptor = new Disruptor<T>(eventFactory, DisruptorQueueOption.DEFAULT.RING_BUFFER_SIZE,
				DaemonThreadFactory.INSTANCE, DisruptorQueueOption.DEFAULT.PRODUCER,
				DisruptorQueueOption.DEFAULT.WAIT_STRATEGY);
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
