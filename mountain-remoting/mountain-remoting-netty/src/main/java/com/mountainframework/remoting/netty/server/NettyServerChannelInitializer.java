package com.mountainframework.remoting.netty.server;

import java.util.Map;

import com.mountainframework.remoting.netty.protocol.SerializeProtocolSelector;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Netty服务端通道初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private Map<String, Object> handlerBeanMap;

	private RpcSerializeProtocol protocol;

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
		SerializeProtocolSelector.selector().initRpcDirect(true).select(protocol, pipeline);
		pipeline.addLast(new NettyServerChannelHandler(handlerBeanMap));
	}

}
