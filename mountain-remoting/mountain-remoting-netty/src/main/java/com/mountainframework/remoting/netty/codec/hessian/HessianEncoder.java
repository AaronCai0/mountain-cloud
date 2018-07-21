package com.mountainframework.remoting.netty.codec.hessian;

import com.mountainframework.remoting.netty.codec.NettyMessageEncoder;

public class HessianEncoder extends NettyMessageEncoder {

	public HessianEncoder(HessianCodec codec) {
		super(codec);
	}

}
