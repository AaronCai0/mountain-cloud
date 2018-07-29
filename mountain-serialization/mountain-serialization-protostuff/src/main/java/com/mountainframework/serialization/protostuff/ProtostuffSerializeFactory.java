package com.mountainframework.serialization.protostuff;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

/**
 * ProtostuffSerializeFactory
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ProtostuffSerializeFactory extends BasePooledObjectFactory<ProtostuffSerialize> {

	@Override
	public ProtostuffSerialize create() throws Exception {
		return createProtostuff();
	}

	@Override
	public PooledObject<ProtostuffSerialize> wrap(ProtostuffSerialize hessian) {
		return new DefaultPooledObject<ProtostuffSerialize>(hessian);
	}

	private ProtostuffSerialize createProtostuff() {
		return new ProtostuffSerialize();
	}

}
