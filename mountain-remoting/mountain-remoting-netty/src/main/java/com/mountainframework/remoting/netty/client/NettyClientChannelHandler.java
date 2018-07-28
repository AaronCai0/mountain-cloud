package com.mountainframework.remoting.netty.client;

import java.net.SocketAddress;
import java.util.Map;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.mountainframework.rpc.model.RpcMessageCallBack;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty客户端通道操作处理类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyClientChannelHandler extends ChannelInboundHandlerAdapter {

	private static final Map<String, RpcMessageCallBack> callBackMap = Maps.newConcurrentMap();

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
		ctx.channel().eventLoop().execute(() -> {
			if (!(msg instanceof RpcMessageResponse)) {
				Preconditions.checkArgument(false, "Mountain client channelRead is not RpcMessageResponse.");
				return;
			}
			RpcMessageResponse response = (RpcMessageResponse) msg;
			String messageId = response.getMessageId();
			RpcMessageCallBack callBack = callBackMap.get(messageId);
			callBack.setResponse(response);
			callBackMap.remove(messageId);
			if (callBackMap.isEmpty()) {
				callBack.overAll();
				// callBack.over(response);
			}
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

	public void close() {
		channel.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
	}

	public RpcMessageCallBack sendRequest(RpcMessageRequest request) {
		RpcMessageCallBack callBack = new RpcMessageCallBack();
		callBackMap.put(request.getMessageId(), callBack);
		Channel channel = this.channel;
		channel.eventLoop().execute(() -> channel.writeAndFlush(request));
		return callBack;
	}

	// public FutureTask<RpcMessageCallBack> sendRequest(RpcMessageRequest request)
	// {
	// FutureTask<RpcMessageCallBack> future = new FutureTask<>(new
	// Callable<RpcMessageCallBack>() {
	// @Override
	// public RpcMessageCallBack call() throws Exception {
	// RpcMessageCallBack callBack = new RpcMessageCallBack();
	// callBackMap.put(request.getMessageId(), callBack);
	// channel.writeAndFlush(request);
	// return callBack;
	// }
	// });
	// NettyClientLoader.getInstance().getThreadPoolExecutor().submit(future);
	// return future;
	// }

}
