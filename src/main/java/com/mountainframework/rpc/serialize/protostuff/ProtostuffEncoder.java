package com.mountainframework.rpc.serialize.protostuff;

import com.mountainframework.rpc.serialize.RpcMessageCodec;
import com.mountainframework.rpc.serialize.RpcMessageEncoder;

public class ProtostuffEncoder extends RpcMessageEncoder {

	public ProtostuffEncoder(RpcMessageCodec codec) {
		super(codec);
	}

}
