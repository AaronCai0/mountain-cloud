package com.mountainframework.rpc.serialize.kryo;

import com.mountainframework.rpc.serialize.RpcMessageDecoder;

public class KryoDecoder extends RpcMessageDecoder {

	public KryoDecoder(KryoCodec util) {
		super(util);
	}

}
