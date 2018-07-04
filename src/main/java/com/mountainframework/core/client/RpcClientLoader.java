package com.mountainframework.core.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mountainframework.rpc.support.RpcThreadPoolExecutors;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Rpc客户端初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcClientLoader {

	private static final int parallel = Runtime.getRuntime().availableProcessors() * 2;
	private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);
	private final ExecutorService executor = RpcThreadPoolExecutors.newFixedThreadPool(parallel, -1);

	private RpcClientChannelHandler rpcClientHandler;

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	public static RpcClientLoader getLoader() {
		return RpcClientLoaderHolder.getLoader();
	}

	public void load(String ip, int port) {
		InetSocketAddress inetSocketAddress = new InetSocketAddress(ip, port);
		RpcClientInitializerTask task = new RpcClientInitializerTask(inetSocketAddress, eventLoopGroup);
		executor.submit(task);
	}

	public void unLoad() {
		rpcClientHandler.close();
		executor.shutdown();
		eventLoopGroup.shutdownGracefully();
	}

	public RpcClientChannelHandler getRpcClientHandler() {
		try {
			lock.lock();
			if (rpcClientHandler == null) {
				condition.await();
			}
			return rpcClientHandler;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}

	}

	public void setRpcClientHandler(RpcClientChannelHandler rpcClientHandler) {
		try {
			lock.lock();
			this.rpcClientHandler = rpcClientHandler;
			condition.signalAll();
		} finally {
			lock.unlock();
		}

	}

	private static class RpcClientLoaderHolder {

		private static RpcClientLoader loader = new RpcClientLoader();

		public static RpcClientLoader getLoader() {
			return loader;
		}

	}
}
