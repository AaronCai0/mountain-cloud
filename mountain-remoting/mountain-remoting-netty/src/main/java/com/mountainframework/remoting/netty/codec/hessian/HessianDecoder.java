package com.mountainframework.remoting.netty.codec.hessian;

import com.mountainframework.remoting.netty.codec.NettyMessageDecoder;

public class HessianDecoder extends NettyMessageDecoder {

	public HessianDecoder(HessianCodec codec) {
		super(codec);
	}

}
