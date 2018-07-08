package com.mountainframework.rpc.serialize.kryo;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.rpc.serialize.RpcSerializePool;

public class KryoSerializePool implements RpcSerializePool {

	private KryoPool pool;

	private KryoSerializePool() {
		pool = new KryoPool.Builder(new KryoFactory() {
			@Override
			public Kryo create() {
				Kryo kryo = new Kryo();
				kryo.register(RpcMessageRequest.class);
				kryo.register(RpcMessageResponse.class);
				kryo.setReferences(false);
				kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
				return kryo;
			}
		}).build();
	}

	public static KryoSerializePool getInstance() {
		return KryoSerializePoolHolder.INSTANCE;
	}

	private static class KryoSerializePoolHolder {
		private static final KryoSerializePool INSTANCE = new KryoSerializePool();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getPool() {
		return (T) pool;
	}

}
