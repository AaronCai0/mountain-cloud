package com.mountainframework.rpc.serialize;

import io.netty.buffer.ByteBuf;

public interface RpcMessageCodeUtil {

	static final int MESSAGE_LENGTH = 4;

	void encode(Object message, ByteBuf buf);

	Object decode(byte[] body);

}
