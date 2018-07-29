package com.mountainframework.remoting.netty.codec.prostuff;

import com.mountainframework.remoting.netty.codec.NettyMessageEncoder;

/**
 * ProtostuffEncoder
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ProtostuffEncoder extends NettyMessageEncoder {

	public ProtostuffEncoder(ProtostuffCodec codec) {
		super(codec);
	}

}
