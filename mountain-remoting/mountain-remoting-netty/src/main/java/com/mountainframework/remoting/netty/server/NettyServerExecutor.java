package com.mountainframework.remoting.netty.server;

import java.net.InetSocketAddress;
import java.util.Map;

import com.mountainframework.remoting.RemotingExecutor;
import com.mountainframework.serialization.RpcSerializeProtocol;

/**
 * Netty服务端调度器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyServerExecutor implements RemotingExecutor {

	private NettyServerLoader loader;

	private NettyServerExecutor() {
	}

	public static NettyServerExecutor create() {
		NettyServerExecutor executor = new NettyServerExecutor();
		executor.loader = NettyServerLoader.create();
		return executor;
	}

	public static NettyServerExecutor create(Map<String, Object> handlerBeanMap) {
		NettyServerExecutor executor = new NettyServerExecutor();
		executor.loader = NettyServerLoader.create(handlerBeanMap);
		return executor;
	}

	public NettyServerExecutor initBeanHandler(Map<String, Object> handlerBeanMap) {
		this.loader = NettyServerLoader.create(handlerBeanMap);
		return this;
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
