package com.mountainframework.remoting.netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.Callable;

import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty客户端初始化任务类，多线程任务
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyClientInitializerTask implements Callable<Boolean> {

	private InetSocketAddress inetSocketAddress;
	private EventLoopGroup eventLoopGroup;
	private RpcSerializeProtocol protocol;

	public NettyClientInitializerTask(InetSocketAddress inetSocketAddress, RpcSerializeProtocol protocol,
			EventLoopGroup eventLoopGroup) {
		this.inetSocketAddress = inetSocketAddress;
		this.eventLoopGroup = eventLoopGroup;
		this.protocol = protocol;
	}

	@Override
	public Boolean call() throws Exception {
		Bootstrap bootstrap = new Bootstrap();
		ChannelFuture channelFuture = bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true).handler(NettyClientChannelInitializer.create(protocol))
				.connect(inetSocketAddress);

		channelFuture.addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture future) throws Exception {
				if (future.isSuccess()) {
					NettyClientChannelHandler rpcClientHanlder = future.channel().pipeline()
							.get(NettyClientChannelHandler.class);
					NettyClientLoader.getInstance().setRpcClientHandler(rpcClientHanlder);
				}
			}
		});
		return Boolean.TRUE;
	}

}
