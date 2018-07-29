package com.mountainframework.remoting.netty.codec.kryo;

import com.mountainframework.remoting.netty.codec.NettyMessageDecoder;

/**
 * KryoDecoder
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class KryoDecoder extends NettyMessageDecoder {

	public KryoDecoder(KryoCodec codec) {
		super(codec);
	}

}
