package com.mountainframework.remoting.netty.client;

import java.net.SocketAddress;
import java.util.Map;
import java.util.concurrent.FutureTask;

import com.google.common.collect.Maps;
import com.mountainframework.rpc.model.RpcMessageCallBack;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.rpc.support.RpcThreadPoolExecutors;

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

	private Map<String, RpcMessageCallBack> callBackMap = Maps.newConcurrentMap();

	private Map<String, RpcMessageAyncCallBack> ayncCallBackMap = Maps.newConcurrentMap();

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

	// @Override
	// public void channelRead(ChannelHandlerContext ctx, Object msg) throws
	// Exception {
	// RpcMessageResponse response = (RpcMessageResponse) msg;
	// String messageId = response.getMessageId();
	// RpcMessageCallBack callBack = callBackMap.get(messageId);
	//
	// if (callBack != null) {
	// callBack.over(response);
	// }
	// }

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		RpcMessageResponse response = (RpcMessageResponse) msg;
		String messageId = response.getMessageId();
		RpcMessageAyncCallBack callBack = ayncCallBackMap.get(messageId);
		callBack.getCallBack().setResponse(response);
		FutureTask<Object> futureTask = callBack.getFutureTask();
		RpcThreadPoolExecutors.newFixedThreadPool(200, -1).submit(futureTask);
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
		channel.writeAndFlush(request);
		return callBack;
	}

	public FutureTask<Object> sendRequest3(RpcMessageRequest request) {
		RpcMessageAyncCallBack ayncCallBack = new RpcMessageAyncCallBack();
		RpcMessageCallBack callBack = new RpcMessageCallBack();
		FutureTask<Object> futureTask = new FutureTask<>(callBack);
		ayncCallBack.setCallBack(callBack);
		ayncCallBack.setFutureTask(futureTask);
		ayncCallBackMap.put(request.getMessageId(), ayncCallBack);
		channel.writeAndFlush(request);
		return futureTask;
	}

	public void sendRequest2(RpcMessageRequest request) {
		RpcMessageCallBack callBack = new RpcMessageCallBack();
		callBackMap.put(request.getMessageId(), callBack);
		channel.writeAndFlush(request);
	}

	static class RpcMessageAyncCallBack {

		private RpcMessageCallBack callBack;

		private FutureTask<Object> futureTask;

		public RpcMessageCallBack getCallBack() {
			return callBack;
		}

		public void setCallBack(RpcMessageCallBack callBack) {
			this.callBack = callBack;
		}

		public FutureTask<Object> getFutureTask() {
			return futureTask;
		}

		public void setFutureTask(FutureTask<Object> futureTask) {
			this.futureTask = futureTask;
		}

	}

}
