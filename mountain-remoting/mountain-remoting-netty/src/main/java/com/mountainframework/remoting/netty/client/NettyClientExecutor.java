package com.mountainframework.remoting.netty.client;

import java.net.InetSocketAddress;

import com.mountainframework.remoting.RemotingExecutor;
import com.mountainframework.serialization.RpcSerializeProtocol;

/**
 * Netty客户端调度器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyClientExecutor implements RemotingExecutor {

	private NettyClientLoader loader;

	private NettyClientExecutor() {
	}

	public static NettyClientExecutor create() {
		NettyClientExecutor executor = new NettyClientExecutor();
		executor.loader = NettyClientLoader.getInstance();
		return executor;
	}

	@Override
	public void start(String host, Integer port, String protocolName) {
		InetSocketAddress socketAddress = new InetSocketAddress(host, port);
		RpcSerializeProtocol protocol = RpcSerializeProtocol.findProtocol(protocolName);
		loader.load(socketAddress, protocol);
	}

	@Override
	public void stop() {
		loader.unLoad();
	}

}
