package com.mountainframework.core.client;

import java.net.InetSocketAddress;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class RpcClientInitializerTask implements Runnable {

	private InetSocketAddress inetSocketAddress;
	private EventLoopGroup eventLoopGroup;

	public RpcClientInitializerTask(InetSocketAddress inetSocketAddress, EventLoopGroup eventLoopGroup) {
		this.inetSocketAddress = inetSocketAddress;
		this.eventLoopGroup = eventLoopGroup;
	}

	@Override
	public void run() {

		Bootstrap bootstrap = new Bootstrap();
		ChannelFuture channelFuture = bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true).handler(new RpcClientInitializer())
				.connect(inetSocketAddress);

		channelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					RpcClientHandler rpcClientHanlder = future.channel().pipeline().get(RpcClientHandler.class);
					RpcClientLoader.getLoader().setRpcClientHandler(rpcClientHanlder);
				}
			}
		});

	}

}
