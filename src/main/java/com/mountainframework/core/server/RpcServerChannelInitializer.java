package com.mountainframework.core.server;

import com.mountainframework.core.protocol.SerializeProtocolSelector;
import com.mountainframework.rpc.serialize.RpcSerializeProtocol;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * Rpc服务端通道初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcServerChannelInitializer extends ChannelInitializer<SocketChannel> {

	private RpcSerializeProtocol protocol;

	private RpcServerChannelInitializer() {
	}

	private RpcServerChannelInitializer(RpcSerializeProtocol protocol) {
		this.protocol = protocol;
	}

	public static RpcServerChannelInitializer create(RpcSerializeProtocol protocol) {
		return new RpcServerChannelInitializer(protocol);
	}

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipeline = ch.pipeline();
		SerializeProtocolSelector.selector().initRpcDirect(false).select(protocol, pipeline);
		// pipeline.addLast(new RpcServerChannelHandler());
		// pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0,
		// 4));
		// pipeline.addLast(new LengthFieldPrepender(4));
		// pipeline.addLast(new ObjectEncoder());
		// pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
		// ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
		pipeline.addLast(new RpcServerChannelHandler());
	}

}
