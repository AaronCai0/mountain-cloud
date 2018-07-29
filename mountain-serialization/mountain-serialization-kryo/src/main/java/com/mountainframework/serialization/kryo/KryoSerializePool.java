package com.mountainframework.serialization.kryo;

import org.objenesis.strategy.StdInstantiatorStrategy;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.pool.KryoFactory;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.mountainframework.serialization.RpcSerializePool;

/**
 * KryoSerializePool
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class KryoSerializePool implements RpcSerializePool {

	private KryoPool pool;

	private Class<?>[] cacheClasses;

	private KryoSerializePool() {
	}

	public KryoSerializePool cacheClasses(Class<?>[] cacheClasses) {
		this.cacheClasses = cacheClasses;
		return this;
	}

	public KryoPool build() {
		pool = new KryoPool.Builder(new KryoFactory() {
			@Override
			public Kryo create() {
				Kryo kryo = new Kryo();
				if (cacheClasses != null && cacheClasses.length > 0) {
					// 把已知的结构注册到Kryo注册器里面，提高序列化/反序列化效率
					for (Class<?> cacheClass : cacheClasses) {
						kryo.register(cacheClass);
					}
				}
				kryo.setReferences(false);
				kryo.setInstantiatorStrategy(new StdInstantiatorStrategy());
				return kryo;
			}
		}).build();
		return pool;
	}

	public static KryoSerializePool builder() {
		return new KryoSerializePool();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getPool() {
		return (T) pool;
	}

}
