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
import com.mountainframework.common.Constants;
import com.mountainframework.remoting.RemotingLoaderService;
import com.mountainframework.remoting.model.RemotingBean;
import com.mountainframework.remoting.netty.model.NettyRemotingBean;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.rpc.support.RpcThreadFactory;
import com.mountainframework.rpc.support.RpcThreadPoolExecutors;
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
 * @since 1.0.5
 */
public class NettyServerLoader implements RemotingLoaderService {

	private static final Logger logger = LoggerFactory.getLogger(NettyServerExecutor.class);

	private static ListeningExecutorService threadPoolExecutor;

	private static AtomicLong atomicRequestCount;

	private int parallel;

	private EventLoopGroup bossEvent;

	private EventLoopGroup workEvent;

	public static NettyServerLoader create() {
		return new NettyServerLoader();
	}

	@Override
	public void load(RemotingBean remotingBean) {
		try {
			if (!(remotingBean instanceof NettyRemotingBean)) {
				Preconditions.checkArgument(false, "RemotingBean must be NettyRemotingBean ");
			}
			NettyRemotingBean nettyRemotingBean = (NettyRemotingBean) remotingBean;
			Map<String, Object> handlerBeanMap = nettyRemotingBean.getHandlerMap();
			InetSocketAddress socketAddress = nettyRemotingBean.getSocketAddress();
			RpcSerializeProtocol serailizeProtocol = nettyRemotingBean.getProtocol();
			parallel = nettyRemotingBean.getThreads().intValue();
			threadPoolExecutor = MoreExecutors
					.listeningDecorator(RpcThreadPoolExecutors.newFixedThreadPool(parallel, -1));
			bossEvent = new NioEventLoopGroup();
			workEvent = new NioEventLoopGroup(parallel, new RpcThreadFactory(), SelectorProvider.provider());
			atomicRequestCount = new AtomicLong(1L);

			ServerBootstrap bootstrap = new ServerBootstrap();
			ChannelFuture channelFuture = bootstrap.group(bossEvent, workEvent).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(NettyServerChannelInitializer.create(handlerBeanMap, serailizeProtocol))
					.bind(socketAddress).sync();

			logger.info("Mountain RPC Server success started! ip:{} , port:{} , version:{} ",
					socketAddress.getHostString(), socketAddress.getPort(), Constants.FRAMEWORK_CURRENT_VERSION);
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
