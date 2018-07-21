package com.mountainframework.remoting.netty.codec.prostuff;

import com.mountainframework.remoting.netty.codec.NettyMessageDecoder;

public class ProtostuffDecoder extends NettyMessageDecoder {

	public ProtostuffDecoder(ProtostuffCodec codec) {
		super(codec);
	}

}
