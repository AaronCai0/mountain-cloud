package com.mountainframework.core.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * Rpc客户端通道初始化类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcClientChannelInitializer extends ChannelInitializer<SocketChannel> {

	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		ChannelPipeline pipline = ch.pipeline();
		pipline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
		pipline.addLast(new LengthFieldPrepender(4));
		pipline.addLast(new ObjectEncoder());
		pipline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
				ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
		pipline.addLast(new RpcClientChannelHandler());
	}

}
