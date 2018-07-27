package com.mountainframework.remoting.netty.client;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.mountainframework.remoting.RemotingLoaderService;
import com.mountainframework.remoting.model.RemotingBean;
import com.mountainframework.remoting.netty.model.NettyRemotingBean;
import com.mountainframework.rpc.support.RpcThreadPoolExecutors;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Netty客户端初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0.3
 */
public class NettyClientLoader implements RemotingLoaderService {

	private static final Logger logger = LoggerFactory.getLogger(NettyClientLoader.class);

	private int parallel;

	// private ListeningExecutorService threadPoolExecutor;

	private ExecutorService threadPoolExecutor;

	private EventLoopGroup eventLoopGroup;

	private NettyClientChannelHandler rpcClientHandler;

	private Lock lock = new ReentrantLock();

	private final Condition connectStatus = lock.newCondition();

	// private Condition handlerStatus = lock.newCondition();

	public static NettyClientLoader getInstance() {
		return RpcClientLoaderHolder.INSTANCE;
	}

	public ExecutorService getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	@Override
	public void load(RemotingBean remotingBean) {
		if (!(remotingBean instanceof NettyRemotingBean)) {
			Preconditions.checkArgument(false, "RemotingBean must be NettyRemotingBean.");
		}
		NettyRemotingBean nettyRemotingBean = (NettyRemotingBean) remotingBean;
		InetSocketAddress socketAddress = nettyRemotingBean.getSocketAddress();
		RpcSerializeProtocol serailizeProtocol = nettyRemotingBean.getProtocol();
		parallel = nettyRemotingBean.getThreads().intValue();
		threadPoolExecutor = RpcThreadPoolExecutors.newFixedThreadPool(parallel, -1);
		eventLoopGroup = new NioEventLoopGroup(parallel);

		Bootstrap bootstrap = new Bootstrap();
		ChannelFuture channelFuture = bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
				.option(ChannelOption.SO_KEEPALIVE, true)
				.handler(NettyClientChannelInitializer.create(serailizeProtocol)).connect(socketAddress);

		try {
			lock.lock();
			channelFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					if (future.isSuccess()) {
						try {
							lock.lock();
							NettyClientChannelHandler rpcClientHanlder = future.channel().pipeline()
									.get(NettyClientChannelHandler.class);
							NettyClientLoader.getInstance().setRpcClientHandler(rpcClientHanlder);
							connectStatus.signal();
						} finally {
							lock.unlock();
						}
					}
				}
			});
			connectStatus.await();
		} catch (InterruptedException e) {
			logger.error("Client server channel interrupt", e);
		} finally {
			lock.unlock();
		}
		// ListenableFuture<Boolean> listenableFuture = threadPoolExecutor
		// .submit(new NettyClientInitializerTask(socketAddress, serailizeProtocol,
		// eventLoopGroup));

		// 监听线程池异步的执行结果成功与否再决定是否唤醒全部的客户端RPC线程
		// Futures.getChecked(future, exceptionClass, timeout, unit)(future, timeout,
		// unit, exceptionClass);

		// Futures.addCallback(listenableFuture, new FutureCallback<Boolean>() {
		// @Override
		// public void onSuccess(Boolean result) {
		// try {
		// lock.lock();
		//
		// if (rpcClientHandler == null) {
		// handlerStatus.await();
		// }
		// // Futures异步回调，唤醒所有rpc等待线程
		// if (result == Boolean.TRUE && rpcClientHandler != null) {
		// connectStatus.signalAll();
		// }
		// } catch (InterruptedException e) {
		// logger.error("Future method interrupted.", e);
		// } finally {
		// lock.unlock();
		// }
		// }
		//
		// @Override
		// public void onFailure(Throwable error) {
		// logger.error("rpc client execute task fail.", error);
		// }
		// }, threadPoolExecutor);
	}

	@Override
	public void unLoad() {
		rpcClientHandler.close();
		eventLoopGroup.shutdownGracefully();
	}

	// public NettyClientChannelHandler getRpcClientHandler() {
	// try {
	// lock.lock();
	// // Netty服务端链路没有建立完毕之前，先挂起等待
	// if (rpcClientHandler == null) {
	// handlerStatus.await();
	// // connectStatus.await();
	// }
	// return rpcClientHandler;
	// } catch (InterruptedException e) {
	// logger.error("interrupted.", e);
	// return null;
	// } finally {
	// lock.unlock();
	// }
	// }
	//
	// public void setRpcClientHandler(NettyClientChannelHandler rpcClientHandler) {
	// try {
	// lock.lock();
	// this.rpcClientHandler = rpcClientHandler;
	// handlerStatus.signalAll();
	// } finally {
	// lock.unlock();
	// }
	// }

	public NettyClientChannelHandler getRpcClientHandler() {
		return this.rpcClientHandler;
	}

	public void setRpcClientHandler(NettyClientChannelHandler rpcClientHandler) {
		this.rpcClientHandler = rpcClientHandler;
	}

	private static class RpcClientLoaderHolder {
		private static final NettyClientLoader INSTANCE = new NettyClientLoader();
	}
}
