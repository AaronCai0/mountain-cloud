package com.mountainframework.rpc.serialize;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public interface RpcMessageCodec {

	static final int MESSAGE_LENGTH = 4;

	void encode(Object message, ByteBuf buf) throws IOException;

	Object decode(byte[] body) throws IOException;

}
