package com.mountainframework.serialization.protostuff.constant;

/**
 * ProtostuffPoolConstants
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface ProtostuffPoolConstants {

	int SERIALIZE_POOL_MAX_TOTAL = 500;

	int SERIALIZE_POOL_MIN_IDLE = 10;

	int SERIALIZE_POOL_MAX_WAIT_MILLIS = 5000;

	int SERIALIZE_POOL_MIN_EVICTABLE_IDLE_TIME_MILLIS = 600000;

}
