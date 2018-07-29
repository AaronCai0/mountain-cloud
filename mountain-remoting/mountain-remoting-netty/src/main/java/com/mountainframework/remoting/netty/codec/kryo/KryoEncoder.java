package com.mountainframework.remoting.netty.codec.kryo;

import com.mountainframework.remoting.netty.codec.NettyMessageEncoder;

/**
 * KryoEncoder
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class KryoEncoder extends NettyMessageEncoder {

	public KryoEncoder(KryoCodec codec) {
		super(codec);
	}

}
