package com.mountainframework.rpc.serialize.protostuff;

import com.mountainframework.rpc.serialize.RpcMessageDecoder;

public class ProtostuffDecoder extends RpcMessageDecoder {

	public ProtostuffDecoder(ProtostuffCodec codec) {
		super(codec);
	}

}
