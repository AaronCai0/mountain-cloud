package com.mountainframework.remoting.netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.mountainframework.remoting.RemotingLoaderService;
import com.mountainframework.rpc.support.RpcThreadPoolExecutors;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Netty客户端初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0.3
 */
public class NettyClientLoader implements RemotingLoaderService {

	private static final Logger logger = LoggerFactory.getLogger(NettyClientLoader.class);

	private static final int parallel = Runtime.getRuntime().availableProcessors() * 2;

	private static final ListeningExecutorService threadPoolExecutor = MoreExecutors
			.listeningDecorator(RpcThreadPoolExecutors.newFixedThreadPool(parallel, -1));

	private final EventLoopGroup eventLoopGroup = new NioEventLoopGroup(parallel);

	private NettyClientChannelHandler rpcClientHandler;

	private Lock lock = new ReentrantLock();

	private Condition connectStatus = lock.newCondition();

	private Condition handlerStatus = lock.newCondition();

	public static NettyClientLoader getInstance() {
		return RpcClientLoaderHolder.INSTANCE;
	}

	@Override
	public void load(InetSocketAddress socketAddress, RpcSerializeProtocol serailizeProtocol) {
		ListenableFuture<Boolean> listenableFuture = threadPoolExecutor
				.submit(new NettyClientInitializerTask(socketAddress, serailizeProtocol, eventLoopGroup));

		// 监听线程池异步的执行结果成功与否再决定是否唤醒全部的客户端RPC线程
		Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {
			@Override
			public void onSuccess(Boolean result) {
				try {
					lock.lock();

					if (rpcClientHandler == null) {
						handlerStatus.await();
					}
					// Futures异步回调，唤醒所有rpc等待线程
					if (result == Boolean.TRUE && rpcClientHandler != null) {
						connectStatus.signalAll();
					}
				} catch (InterruptedException e) {
					logger.error("Future method interrupted.", e);
				} finally {
					lock.unlock();
				}
			}

			@Override
			public void onFailure(Throwable error) {
				logger.error("rpc client execute task fail.", error);
			}
		}, threadPoolExecutor);
	}

	@Override
	public void unLoad() {
		rpcClientHandler.close();
		eventLoopGroup.shutdownGracefully();
	}

	public NettyClientChannelHandler getRpcClientHandler() {
		try {
			lock.lock();
			// Netty服务端链路没有建立完毕之前，先挂起等待
			if (rpcClientHandler == null) {
				connectStatus.await();
			}
			return rpcClientHandler;
		} catch (InterruptedException e) {
			logger.error("interrupted.", e);
			return null;
		} finally {
			lock.unlock();
		}

	}

	public void setRpcClientHandler(NettyClientChannelHandler rpcClientHandler) {
		try {
			lock.lock();
			this.rpcClientHandler = rpcClientHandler;
			handlerStatus.signal();
		} finally {
			lock.unlock();
		}

	}

	private static class RpcClientLoaderHolder {
		private static final NettyClientLoader INSTANCE = new NettyClientLoader();
	}
}
