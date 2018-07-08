package com.mountainframework.rpc.serialize;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class RpcMessageEncoder extends MessageToByteEncoder<Object> {

	private RpcMessageCodec codec;

	public RpcMessageEncoder(RpcMessageCodec codec) {
		this.codec = codec;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		codec.encode(msg, out);
	}

}
