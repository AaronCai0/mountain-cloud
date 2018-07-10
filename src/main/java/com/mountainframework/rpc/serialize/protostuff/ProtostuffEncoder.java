package com.mountainframework.rpc.serialize.protostuff;

import com.mountainframework.rpc.serialize.RpcMessageEncoder;

public class ProtostuffEncoder extends RpcMessageEncoder {

	public ProtostuffEncoder(ProtostuffCodec codec) {
		super(codec);
	}

}
