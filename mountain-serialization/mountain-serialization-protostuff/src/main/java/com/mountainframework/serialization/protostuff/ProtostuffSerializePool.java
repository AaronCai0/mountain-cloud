package com.mountainframework.serialization.protostuff;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

public class ProtostuffSerializePool {

	private GenericObjectPool<ProtostuffSerialize> protostuffPool;

	private ProtostuffSerializePool() {
		protostuffPool = new GenericObjectPool<ProtostuffSerialize>(new ProtostuffSerializeFactory());
	}

	public static ProtostuffSerializePool getProtostuffPoolInstance() {
		return ProtostuffSerializePoolHolder.INSTANCE;
	}

	// private static volatile ProtostuffSerializePool poolFactory;
	// public static ProtostuffSerializePool getProtostuffPoolInstance() {
	// if (poolFactory == null) {
	// synchronized (ProtostuffSerializePool.class) {
	// if (poolFactory == null) {
	// poolFactory = new ProtostuffSerializePool();
	// }
	// }
	// }
	// return poolFactory;
	// }

	public ProtostuffSerializePool(final int maxTotal, final int minIdle, final long maxWaitMillis,
			final long minEvictableIdleTimeMillis) {
		protostuffPool = new GenericObjectPool<ProtostuffSerialize>(new ProtostuffSerializeFactory());

		GenericObjectPoolConfig config = new GenericObjectPoolConfig();

		config.setMaxTotal(maxTotal);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);

		protostuffPool.setConfig(config);
	}

	public ProtostuffSerialize borrow() {
		try {
			return getProtostuffPool().borrowObject();
		} catch (final Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void restore(final ProtostuffSerialize object) {
		getProtostuffPool().returnObject(object);
	}

	public GenericObjectPool<ProtostuffSerialize> getProtostuffPool() {
		return protostuffPool;
	}

	private static class ProtostuffSerializePoolHolder {
		private static final ProtostuffSerializePool INSTANCE = new ProtostuffSerializePool();

	}

}
