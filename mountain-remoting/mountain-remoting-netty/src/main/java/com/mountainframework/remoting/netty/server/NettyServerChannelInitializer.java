package com.mountainframework.remoting.netty.server;

import java.util.Map;

import com.mountainframework.remoting.netty.SerializeProtocolSelectors;
import com.mountainframework.remoting.netty.protocol.SerializeProtocolServerSelector;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Netty服务端通道初始化类
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private Map<String, Object> handlerBeanMap;

	private RpcSerializeProtocol protocol;

	private final SerializeProtocolServerSelector selector = SerializeProtocolSelectors.serverSelector();

	private NettyServerChannelInitializer() {
	}

	private NettyServerChannelInitializer(Map<String, Object> handlerBeanMap, RpcSerializeProtocol protocol) {
		this.handlerBeanMap = handlerBeanMap;
		this.protocol = protocol;
	}

	public static NettyServerChannelInitializer create(Map<String, Object> handlerBeanMap,
			RpcSerializeProtocol protocol) {
		return new NettyServerChannelInitializer(handlerBeanMap, protocol);
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		selector.select(protocol, pipeline);
		pipeline.addLast(new NettyServerChannelHandler(handlerBeanMap));
	}

}
