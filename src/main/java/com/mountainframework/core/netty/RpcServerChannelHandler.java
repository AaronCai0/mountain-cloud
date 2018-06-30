package com.mountainframework.core.netty;

import com.mountainframework.rpc.support.RpcMessageRequest;

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

	// private static final AtomicInteger integer = new AtomicInteger(1);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (!(msg instanceof RpcMessageRequest)) {
			System.out.println("Mountain server channelRead is not RpcRequest");
			return;
		}
		RpcMessageRequest request = (RpcMessageRequest) msg;
		RpcServerInitializerTask task = new RpcServerInitializerTask(request, ctx);
		RpcServerExecutor.getThreadPoolExecutor().submit(task);
		// System.out.println(integer.getAndIncrement());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
