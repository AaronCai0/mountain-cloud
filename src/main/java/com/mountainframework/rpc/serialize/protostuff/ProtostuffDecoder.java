package com.mountainframework.rpc.serialize.protostuff;

import com.mountainframework.rpc.serialize.RpcMessageCodec;
import com.mountainframework.rpc.serialize.RpcMessageDecoder;

public class ProtostuffDecoder extends RpcMessageDecoder {

	public ProtostuffDecoder(RpcMessageCodec codec) {
		super(codec);
	}

}
