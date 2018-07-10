package com.mountainframework.rpc.serialize.hessian;

import com.mountainframework.rpc.serialize.RpcMessageEncoder;

public class HessianEncoder extends RpcMessageEncoder {

	public HessianEncoder(HessianCodec codec) {
		super(codec);
	}

}
