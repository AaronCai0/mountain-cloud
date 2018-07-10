package com.mountainframework.rpc.serialize.hessian;

import com.mountainframework.rpc.serialize.RpcMessageDecoder;

public class HessianDecoder extends RpcMessageDecoder {

	public HessianDecoder(HessianCodec codec) {
		super(codec);
	}

}
