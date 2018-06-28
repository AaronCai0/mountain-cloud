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
import com.mountainframework.common.RpcThreadFactory;
import com.mountainframework.common.RpcThreadPool;
import com.mountainframework.config.InitializingConfig;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.context.MountainApplicationConfigContext;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RpcServerExecutor implements InitializingConfig {

	private static final Logger log = LoggerFactory.getLogger(RpcServerExecutor.class);

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
					.childHandler(new RpcChannelInitializer()).bind(ip, port).sync();
			printLog();
			log.info("Mountain RPC server success started! ip:{} , port:{} , version:1.0 ", ip, port);
			channelFuture.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static final void printLog() {
		Resource resource = new ClassPathResource("mountain-logo.txt");
		if (!resource.exists()) {
			log.warn("Mountain logo not Find!");
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
			log.info(sb.toString());
		} catch (Exception e) {
			log.info("Read moutain logo fail", e);
		}
	}

	public static ThreadPoolExecutor getThreadPoolExecutor() {
		return RpcServerExecutorHolder.getExecutor();
	}

	private static class RpcServerExecutorHolder {
		private static ThreadPoolExecutor executor = RpcThreadPool
				.getThreadPool(Runtime.getRuntime().availableProcessors() * 2, -1);

		public static ThreadPoolExecutor getExecutor() {
			return executor;
		}
	}

}
