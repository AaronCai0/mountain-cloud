package com.mountainframework.remoting.netty.client;

import com.mountainframework.remoting.netty.SerializeProtocolSelectors;
import com.mountainframework.remoting.netty.protocol.SerializeProtocolClientSelector;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Netty客户端通道初始化类
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	private RpcSerializeProtocol protocol;

	private final SerializeProtocolClientSelector selector = SerializeProtocolSelectors.clientSelector();

	private NettyClientChannelInitializer() {
	}

	private NettyClientChannelInitializer(RpcSerializeProtocol protocol) {
		this.protocol = protocol;
	}

	public static NettyClientChannelInitializer create(RpcSerializeProtocol protocol) {
		return new NettyClientChannelInitializer(protocol);
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		selector.select(protocol, pipeline);
		pipeline.addLast(new NettyClientChannelHandler());
	}

}
