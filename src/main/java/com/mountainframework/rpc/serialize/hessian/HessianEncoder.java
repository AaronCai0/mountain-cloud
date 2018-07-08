package com.mountainframework.rpc.serialize.hessian;

import com.mountainframework.rpc.serialize.RpcMessageCodec;
import com.mountainframework.rpc.serialize.RpcMessageEncoder;

public class HessianEncoder extends RpcMessageEncoder {

	public HessianEncoder(RpcMessageCodec codec) {
		super(codec);
	}

}
