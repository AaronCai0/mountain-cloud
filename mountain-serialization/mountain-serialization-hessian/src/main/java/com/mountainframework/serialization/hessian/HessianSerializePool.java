package com.mountainframework.serialization.hessian;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * HessianSerializePool
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class HessianSerializePool {

	private GenericObjectPool<HessianSerialize> hessianPool;

	private HessianSerializePool() {
		hessianPool = new GenericObjectPool<HessianSerialize>(new HessianSerializeFactory());
	}

	public static HessianSerializePool getHessianPoolInstance() {
		return HessianSerializePoolHolder.INSTANCE;
	}

	public HessianSerializePool(final int maxTotal, final int minIdle, final long maxWaitMillis,
			final long minEvictableIdleTimeMillis) {
		hessianPool = new GenericObjectPool<HessianSerialize>(new HessianSerializeFactory());
		GenericObjectPoolConfig config = new GenericObjectPoolConfig();
		config.setMaxTotal(maxTotal);
		config.setMinIdle(minIdle);
		config.setMaxWaitMillis(maxWaitMillis);
		config.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
		hessianPool.setConfig(config);
	}

	public HessianSerialize borrow() {
		try {
			return getHessianPool().borrowObject();
		} catch (final Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	public void restore(final HessianSerialize object) {
		getHessianPool().returnObject(object);
	}

	public GenericObjectPool<HessianSerialize> getHessianPool() {
		return hessianPool;
	}

	private static class HessianSerializePoolHolder {
		private static final HessianSerializePool INSTANCE = new HessianSerializePool();

	}

}
