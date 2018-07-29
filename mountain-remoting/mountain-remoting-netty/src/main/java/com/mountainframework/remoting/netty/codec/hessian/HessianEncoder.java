package com.mountainframework.remoting.netty.codec.hessian;

import com.mountainframework.remoting.netty.codec.NettyMessageEncoder;

/**
 * HessianEncoder
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class HessianEncoder extends NettyMessageEncoder {

	public HessianEncoder(HessianCodec codec) {
		super(codec);
	}

}
