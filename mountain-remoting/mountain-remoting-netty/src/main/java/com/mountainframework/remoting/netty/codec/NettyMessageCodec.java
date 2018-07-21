package com.mountainframework.remoting.netty.codec;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public interface NettyMessageCodec {

	static final int MESSAGE_LENGTH = 4;

	void encode(Object message, ByteBuf buf) throws IOException;

	Object decode(byte[] body) throws IOException;

}
