package com.mountainframework.remoting.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * NettyMessageEncoder
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyMessageEncoder extends MessageToByteEncoder<Object> {

	private NettyMessageCodec codec;

	public NettyMessageEncoder(NettyMessageCodec codec) {
		this.codec = codec;
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		codec.encode(msg, out);
	}

}
