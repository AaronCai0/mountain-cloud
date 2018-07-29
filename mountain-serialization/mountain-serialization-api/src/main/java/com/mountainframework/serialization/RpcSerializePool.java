package com.mountainframework.serialization;

/**
 * RpcSerializePool
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface RpcSerializePool {

	<T> T getPool();

}
