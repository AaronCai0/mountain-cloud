package com.mountainframework.remoting.netty.codec.hessian;

import com.mountainframework.remoting.netty.codec.NettyMessageDecoder;

/**
 * HessianDecoder
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class HessianDecoder extends NettyMessageDecoder {

	public HessianDecoder(HessianCodec codec) {
		super(codec);
	}

}
