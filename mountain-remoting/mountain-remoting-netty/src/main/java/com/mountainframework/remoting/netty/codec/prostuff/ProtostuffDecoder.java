package com.mountainframework.remoting.netty.codec.prostuff;

import com.mountainframework.remoting.netty.codec.NettyMessageDecoder;

/**
 * ProtostuffDecoder
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ProtostuffDecoder extends NettyMessageDecoder {

	public ProtostuffDecoder(ProtostuffCodec codec) {
		super(codec);
	}

}
