package com.mountainframework.remoting.netty.codec.kryo;

import com.mountainframework.remoting.netty.codec.NettyMessageDecoder;

public class KryoDecoder extends NettyMessageDecoder {

	public KryoDecoder(KryoCodec codec) {
		super(codec);
	}

}
