package com.mountainframework.remoting.netty.codec.prostuff;

import com.mountainframework.remoting.netty.codec.NettyMessageEncoder;

public class ProtostuffEncoder extends NettyMessageEncoder {

	public ProtostuffEncoder(ProtostuffCodec codec) {
		super(codec);
	}

}
