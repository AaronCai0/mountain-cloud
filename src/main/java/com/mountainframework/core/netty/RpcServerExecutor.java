package com.mountainframework.core.netty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.channels.spi.SelectorProvider;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.init.InitializingService;
import com.mountainframework.config.init.context.MountainApplicationConfigContext;
import com.mountainframework.rpc.support.RpcThreadFactory;
import com.mountainframework.rpc.support.RpcThreadPool;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
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

	@Override
	public void init(MountainApplicationConfigContext context) {
		try {
			RegistryConfig providerRegistry = context.getProviderRegistry();
			List<String> addressList = Splitter.on(":")
					.splitToList(Preconditions.checkNotNull(providerRegistry.getAddress(), "Provider address is null"));
			String ip = addressList.get(0);
			int port = Integer.valueOf(Objects.toString(addressList.get(1), "80"));

			EventLoopGroup bossGroup = new NioEventLoopGroup();
			EventLoopGroup workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
					new RpcThreadFactory(), SelectorProvider.provider());

			ServerBootstrap bootstrap = new ServerBootstrap();
			ChannelFuture channelFuture = bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
					.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_KEEPALIVE, true)
					.childHandler(new RpcServerChannelInitializer()).bind(ip, port).sync();
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

	public static ThreadPoolExecutor getThreadPoolExecutor() {
		return RpcServerExecutorHolder.EXECUTOR;
	}

	private static class RpcServerExecutorHolder {
		static ThreadPoolExecutor EXECUTOR = RpcThreadPool
				.getThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, -1);
	}

}
