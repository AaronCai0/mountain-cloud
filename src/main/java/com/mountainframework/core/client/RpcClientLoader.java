package com.mountainframework.core.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mountainframework.common.RpcThreadPool;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

public class RpcClientLoader {

	private static final int parallel = Runtime.getRuntime().availableProcessors() * 2;
	private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);
	private final ThreadPoolExecutor executor = RpcThreadPool.getThreadPool(parallel, -1);

	private RpcClientHandler rpcClientHandler;

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

	public RpcClientHandler getRpcClientHandler() {
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

	public void setRpcClientHandler(RpcClientHandler rpcClientHandler) {
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
