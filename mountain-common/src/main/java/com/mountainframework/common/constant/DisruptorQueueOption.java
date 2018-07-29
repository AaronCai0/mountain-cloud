package com.mountainframework.common.constant;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.lmax.disruptor.WaitStrategy;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.ProducerType;

/**
 * DisruptorQueueOption
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public enum DisruptorQueueOption {

	DEFAULT(1024 * 1024, ProducerType.SINGLE, new YieldingWaitStrategy());

	public final int RING_BUFFER_SIZE;

	public final ProducerType PRODUCER;

	public final WaitStrategy WAIT_STRATEGY;

	private DisruptorQueueOption(int rING_BUFFER_SIZE, ProducerType pRODUCER, WaitStrategy wAIT_STRATEGY) {
		RING_BUFFER_SIZE = rING_BUFFER_SIZE;
		PRODUCER = pRODUCER;
		WAIT_STRATEGY = wAIT_STRATEGY;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

}
