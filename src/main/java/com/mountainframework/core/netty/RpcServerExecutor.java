package com.mountainframework.core.netty;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.channels.spi.SelectorProvider;
import java.util.concurrent.ThreadPoolExecutor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.base.Preconditions;
import com.mountainframework.common.RpcConstants;
import com.mountainframework.common.RpcThreadFactory;
import com.mountainframework.common.RpcThreadPool;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class RpcServerExecutor implements InitializingBean {

	private static final Logger log = LoggerFactory.getLogger(RpcServerExecutor.class);

	@Value(value = RpcConstants.RPC_SERVER_IP)
	private String ip;

	@Value(value = RpcConstants.RPC_SERVER_PORT)
	private Integer port;

	@Value(value = RpcConstants.RPC_SERVER_VERSION)
	private Float version;

	@Override
	public void afterPropertiesSet() throws Exception {
		Preconditions.checkNotNull(ip);
		Preconditions.checkNotNull(port);
		Preconditions.checkNotNull(version);

		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workGroup = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
				new RpcThreadFactory(), SelectorProvider.provider());

		ServerBootstrap bootstrap = new ServerBootstrap();
		ChannelFuture channelFuture = bootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 1024).option(ChannelOption.SO_KEEPALIVE, true)
				.childHandler(new RpcChannelInitializer()).bind(ip, port).sync();
		printLog();
		log.info("Mountain RPC server success started! ip:{} , port:{} , version:{} ", ip, port, version);
		channelFuture.channel().closeFuture().sync();
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
