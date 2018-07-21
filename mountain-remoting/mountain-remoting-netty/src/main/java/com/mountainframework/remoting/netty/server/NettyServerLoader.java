package com.mountainframework.remoting.netty.server;

import java.net.InetSocketAddress;
import java.nio.channels.spi.SelectorProvider;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.mountainframework.remoting.RemotingLoaderService;
import com.mountainframework.remoting.thread.RpcThreadFactory;
import com.mountainframework.remoting.thread.RpcThreadPoolExecutors;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty服务端初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyServerLoader implements RemotingLoaderService {

	private static final Logger logger = LoggerFactory.getLogger(NettyServerExecutor.class);

	private static final int parallel = Runtime.getRuntime().availableProcessors() * 2;

	private static final ListeningExecutorService threadPoolExecutor = MoreExecutors
			.listeningDecorator(RpcThreadPoolExecutors.newFixedThreadPool(parallel, -1));

	private final EventLoopGroup bossEvent = new NioEventLoopGroup();
	private final EventLoopGroup workEvent = new NioEventLoopGroup(parallel, new RpcThreadFactory(),
			SelectorProvider.provider());

	private Map<String, Object> handlerBeanMap;

	private static AtomicLong atomicRequestCount = new AtomicLong(1L);

	private NettyServerLoader() {
	}

	private NettyServerLoader(Map<String, Object> handlerBeanMap) {
		this.handlerBeanMap = handlerBeanMap;
	}

	public static NettyServerLoader create() {
		return new NettyServerLoader();
	}

	public static NettyServerLoader create(Map<String, Object> handlerBeanMap) {
		return new NettyServerLoader(handlerBeanMap);
	}

	@Override
	public void load(InetSocketAddress socketAddress, RpcSerializeProtocol protocol) {
		try {
			Preconditions.checkNotNull(handlerBeanMap, "init handlerBeanMap is null.");
			ServerBootstrap bootstrap = new ServerBootstrap();
			ChannelFuture channelFuture = bootstrap.group(bossEvent, workEvent).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(NettyServerChannelInitializer.create(handlerBeanMap, protocol)).bind(socketAddress)
					.sync();

			// printLog();
			logger.info("Mountain RPC Server success started! ip:{} , port:{} , version:1.0 ",
					socketAddress.getHostString(), socketAddress.getPort());
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("Mountain RPC Server interrupted error.", e);
		}
	}

	@Override
	public void unLoad() {
		workEvent.shutdownGracefully();
		bossEvent.shutdownGracefully();
	}

	public static void submit(Callable<Boolean> task, ChannelHandlerContext ctx, RpcMessageRequest request,
			RpcMessageResponse response) {
		ListenableFuture<Boolean> listenableFuture = threadPoolExecutor.submit(task);
		// Netty服务端把计算结果异步返回
		Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
					@Override
					public void operationComplete(ChannelFuture channelFuture) throws Exception {
						logger.info("Mountain RPC Server(RequestCount:{}) send message-id respone:{}",
								atomicRequestCount.getAndIncrement(), request.getMessageId());
					}
				});
			}

			@Override
			public void onFailure(Throwable t) {
				logger.error("Invoke fail.", t);
			}
		}, threadPoolExecutor);
	}

}
