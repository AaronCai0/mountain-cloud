package com.mountainframework.rpc.serialize;

import com.mountainframework.rpc.serialize.kryo.KryoSerializePool;

public class RpcSerializePools {

	public static KryoSerializePool getKryo() {
		return KryoSerializePool.getInstance();
	}

}
