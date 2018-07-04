package com.mountainframework.rpc.serialize;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class RpcMessageDecoder extends ByteToMessageDecoder {

	private Logger logger = LoggerFactory.getLogger(RpcMessageCodeUtil.class);

	private RpcMessageCodeUtil util;

	public RpcMessageDecoder(RpcMessageCodeUtil util) {
		this.util = util;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		// 出现粘包导致消息头长度不对，直接返回
		if (in.readableBytes() < RpcMessageCodeUtil.MESSAGE_LENGTH) {
			return;
		}

		in.markReaderIndex();
		// 读取消息的内容长度
		int messageLength = in.readInt();

		if (messageLength < 0) {
			ctx.close();
		}

		// 读到的消息长度和报文头的已知长度不匹配。那就重置一下ByteBuf读索引的位置
		if (in.readableBytes() < messageLength) {
			in.resetReaderIndex();
			return;
		} else {
			byte[] messageBody = new byte[messageLength];
			in.readBytes(messageBody);
			Object obj = util.decode(messageBody);
			out.add(obj);
		}

	}

}
