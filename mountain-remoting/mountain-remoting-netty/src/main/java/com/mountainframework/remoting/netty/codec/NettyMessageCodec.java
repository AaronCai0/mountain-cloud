package com.mountainframework.remoting.netty.codec;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

/**
 * NettyMessageCodec
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface NettyMessageCodec {

	static final int MESSAGE_LENGTH = 4;

	void encode(Object message, ByteBuf buf) throws IOException;

	Object decode(byte[] body) throws IOException;

}
