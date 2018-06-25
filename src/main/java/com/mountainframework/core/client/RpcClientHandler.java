package com.mountainframework.core.client;

import java.net.SocketAddress;
import java.util.Map;

import com.google.common.collect.Maps;
import com.mountainframework.rpc.support.RpcCallBack;
import com.mountainframework.rpc.support.RpcRequest;
import com.mountainframework.rpc.support.RpcReseponse;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcClientHandler extends ChannelInboundHandlerAdapter {

	private Map<String, RpcCallBack> callBackMap = Maps.newConcurrentMap();

	private volatile Channel channel;

	private SocketAddress remoteAddress;

	public Channel getChannel() {
		return channel;
	}

	public SocketAddress getRemoteAddress() {
		return remoteAddress;
	}

	@Override
	public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
		super.channelRegistered(ctx);
		this.channel = ctx.channel();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		this.remoteAddress = this.channel.remoteAddress();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		RpcReseponse response = (RpcReseponse) msg;
		String messageId = response.getMessageId();
		RpcCallBack callBack = callBackMap.get(messageId);
		if (callBack != null) {
			callBackMap.remove(messageId);
			callBack.over(response);
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	public void close() {
		channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	public RpcCallBack sendRequest(RpcRequest request) {
		RpcCallBack callBack = new RpcCallBack();
		callBackMap.put(request.getMessageId(), callBack);
		channel.writeAndFlush(request);
		return callBack;
	}

}
