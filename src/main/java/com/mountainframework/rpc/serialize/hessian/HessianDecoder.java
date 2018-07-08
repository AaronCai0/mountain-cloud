package com.mountainframework.rpc.serialize.hessian;

import com.mountainframework.rpc.serialize.RpcMessageCodec;
import com.mountainframework.rpc.serialize.RpcMessageDecoder;

public class HessianDecoder extends RpcMessageDecoder {

	public HessianDecoder(RpcMessageCodec codec) {
		super(codec);
	}

}
