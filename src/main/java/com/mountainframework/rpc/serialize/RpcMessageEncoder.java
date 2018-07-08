package com.mountainframework.rpc.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcMessageEncoder extends MessageToByteEncoder<Object> {

	private RpcMessageCodec util;

	public RpcMessageEncoder(RpcMessageCodec util) {
		this.util = util;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		util.encode(msg, out);
	}

}
