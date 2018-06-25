package com.mountainframework.core.netty;

import java.util.concurrent.atomic.AtomicInteger;

import com.mountainframework.rpc.support.RpcRequest;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcMessageHandler extends ChannelInboundHandlerAdapter {

	private static final AtomicInteger integer = new AtomicInteger(1);

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if (!(msg instanceof RpcRequest)) {
			System.out.println("PeakRpc server channelRead is not RpcRequest");
			return;
		}
		RpcRequest request = (RpcRequest) msg;
		RpcInitializerTask task = new RpcInitializerTask(request, ctx);
		RpcServerExecutor.getThreadPoolExecutor().submit(task);
		System.out.println(integer.getAndIncrement());
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}

}
