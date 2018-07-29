package com.mountainframework.remoting.netty.server;

import java.util.Map;

import com.google.common.base.Preconditions;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty服务端通道操作处理类
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyServerChannelHandler extends ChannelInboundHandlerAdapter {

	private Map<String, Object> handlerBeanMap;

	public NettyServerChannelHandler(Map<String, Object> handlerBeanMap) {
		this.handlerBeanMap = handlerBeanMap;
	}

	@Override
	public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
		if (!(msg instanceof RpcMessageRequest)) {
			Preconditions.checkArgument(false, "Mountain server channelRead is not RpcMessageRequest.");
			return;
		}
		channelHandlerContext.channel().eventLoop().execute(() -> {
			RpcMessageRequest request = (RpcMessageRequest) msg;
			RpcMessageResponse response = new RpcMessageResponse();

			// Map<String, Object> handlerBeanMap = event.getHandlerBeanMap();
			// RpcMessageResponse response = event.getResponse();
			// RpcMessageRequest request = event.getRequest();

			// ChannelHandlerContext chx = event.getChannelHandlerContext();
			// NettyServerInitializerTask task = new
			// NettyServerInitializerTask(handlerBeanMap, request, response);
			// NettyServerLoader.submit(task, ctx, request, response);

			NettyServerChannelReadEvent task = new NettyServerChannelReadEvent(channelHandlerContext, handlerBeanMap,
					request, response);
			NettyServerChannelReadEvent.publishReadEvent(NettyServerLoader.getDisruptorProvider(), task);
		});
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
