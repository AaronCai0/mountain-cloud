package com.mountainframework.remoting.netty.client;

import com.mountainframework.remoting.netty.protocol.SerializeProtocolSelector;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Netty客户端通道初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	private RpcSerializeProtocol protocol;

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
		SerializeProtocolSelector.selector().initRpcDirect(false).select(protocol, pipeline);
		pipeline.addLast(new NettyClientChannelHandler());
	}

}
