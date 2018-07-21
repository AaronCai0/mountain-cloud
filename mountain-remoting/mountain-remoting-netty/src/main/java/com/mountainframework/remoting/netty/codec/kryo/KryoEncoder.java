package com.mountainframework.remoting.netty.codec.kryo;

import com.mountainframework.remoting.netty.codec.NettyMessageEncoder;

public class KryoEncoder extends NettyMessageEncoder {

	public KryoEncoder(KryoCodec codec) {
		super(codec);
	}

}
