package com.mountainframework.rpc.serialize.protostuff;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

public class ProtostuffSerializeFactory extends BasePooledObjectFactory<ProtostuffSerialize> {

	public ProtostuffSerialize create() throws Exception {
		return createProtostuff();
	}

	public PooledObject<ProtostuffSerialize> wrap(ProtostuffSerialize hessian) {
		return new DefaultPooledObject<ProtostuffSerialize>(hessian);
	}

	private ProtostuffSerialize createProtostuff() {
		return new ProtostuffSerialize();
	}

}
