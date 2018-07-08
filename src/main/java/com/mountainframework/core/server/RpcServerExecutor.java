package com.mountainframework.core.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.mountainframework.common.Constants;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.init.InitializingService;
import com.mountainframework.config.init.context.MountainApplicationConfigContext;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.rpc.serialize.RpcSerializeProtocol;
import com.mountainframework.rpc.support.RpcThreadFactory;
import com.mountainframework.rpc.support.RpcThreadPoolExecutors;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Rpc服务端调度器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcServerExecutor implements InitializingService {

	private static final Logger logger = LoggerFactory.getLogger(RpcServerExecutor.class);

	private static final RpcSerializeProtocol PROTOCOL = RpcSerializeProtocol.PROTOSTUFF;

	private static final int parallel = Runtime.getRuntime().availableProcessors() * 2;

	private static final ListeningExecutorService threadPoolExecutor = MoreExecutors
			.listeningDecorator(RpcThreadPoolExecutors.newFixedThreadPool(parallel, -1));;

	@Override
	public void init(MountainApplicationConfigContext context) {
		try {
			RegistryConfig providerRegistry = context.getProviderRegistry();
			List<String> addressList = Splitter.on(Constants.ADDRESS_DELIMITER)
					.splitToList(Preconditions.checkNotNull(providerRegistry.getAddress(), "Provider address is null"));
			String ip = addressList.get(0);
			int port = Integer.valueOf(Objects.toString(addressList.get(1), "80"));

			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workGroup = new NioEventLoopGroup(parallel, new RpcThreadFactory(),
					SelectorProvider.provider());

			ServerBootstrap bootstrap = new ServerBootstrap();
			ChannelFuture channelFuture = bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(RpcServerChannelInitializer.create(PROTOCOL)).bind(ip, port).sync();
			printLog();
			logger.info("Mountain RPC server success started! ip:{} , port:{} , version:1.0 ", ip, port);
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			logger.error("RpcServerExecutor.init() interrupted error.", e);
		}
	}

	public static final void printLog() {
		Resource resource = new ClassPathResource("mountain-logo.txt");
		if (!resource.exists()) {
			logger.warn("Mountain logo not Find!");
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"))) {
			StringBuilder sb = new StringBuilder();
			sb.append(System.lineSeparator());
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(System.lineSeparator());
				sb.append(str);
			}
			sb.append(System.lineSeparator());
			logger.info(sb.toString());
		} catch (Exception e) {
			logger.info("Read moutain logo fail", e);
		}
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
						System.out.println("Mountain RPC Server Send message-id respone:" + request.getMessageId());
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
