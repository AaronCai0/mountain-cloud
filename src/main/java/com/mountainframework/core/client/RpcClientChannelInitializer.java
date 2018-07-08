package com.mountainframework.core.client;

import com.mountainframework.core.protocol.SerializeProtocolSelector;
import com.mountainframework.rpc.serialize.RpcSerializeProtocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Rpc客户端通道初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	private RpcSerializeProtocol protocol;

	private RpcClientChannelInitializer() {
	}

	private RpcClientChannelInitializer(RpcSerializeProtocol protocol) {
		this.protocol = protocol;
	}

	public static RpcClientChannelInitializer create(RpcSerializeProtocol protocol) {
		return new RpcClientChannelInitializer(protocol);
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		SerializeProtocolSelector.selector().initRpcDirect(true).select(protocol, pipeline);
		pipeline.addLast(new RpcClientChannelHandler());
	}

}
