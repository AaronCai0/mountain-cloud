package com.mountainframework.core.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mountainframework.rpc.model.RpcMessageRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Rpc服务端通道操作处理类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcServerChannelHandler extends ChannelInboundHandlerAdapter {

	private static final Logger logger = LoggerFactory.getLogger(RpcServerChannelHandler.class);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (!(msg instanceof RpcMessageRequest)) {
			logger.warn("Mountain server channelRead is not RpcMessageRequest");
			return;
		}
		RpcMessageRequest request = (RpcMessageRequest) msg;
		RpcServerInitializerTask task = new RpcServerInitializerTask(request, ctx);
		RpcServerExecutor.getThreadPoolExecutor().submit(task);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}