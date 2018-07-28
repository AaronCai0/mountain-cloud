package com.mountainframework.remoting.netty.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Netty服务端通道操作处理类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyServerChannelHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(NettyServerChannelHandler.class);

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
		RpcMessageRequest request = (RpcMessageRequest) msg;
		RpcMessageResponse response = new RpcMessageResponse();
		// NettyServerInitializerTask task = new
		// NettyServerInitializerTask(handlerBeanMap, request, response);
		// NettyServerLoader.submit(task, ctx, request, response);

		NettyServerChannelReadEvent task = new NettyServerChannelReadEvent(channelHandlerContext, handlerBeanMap,
				request, response);
		NettyServerChannelReadEvent.publishReadEvent(NettyServerLoader.getDisruptorProvider(), task);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
