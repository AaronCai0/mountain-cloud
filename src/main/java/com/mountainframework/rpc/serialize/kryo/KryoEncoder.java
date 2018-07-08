package com.mountainframework.rpc.serialize.kryo;

import com.mountainframework.rpc.serialize.RpcMessageEncoder;

public class KryoEncoder extends RpcMessageEncoder {

	public KryoEncoder(KryoCodec util) {
		super(util);
	}

}
