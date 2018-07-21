package com.mountainframework.remoting.netty.protocol;

import com.mountainframework.remoting.netty.ChannelPipeLineHandler;

import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class JdkNativeProtocolHandler implements ChannelPipeLineHandler {

	@Override
	public void handle(ChannelPipeline pipeline) {
		pipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
		pipeline.addLast(new LengthFieldPrepender(4));
		pipeline.addLast(new ObjectEncoder());
		pipeline.addLast(new ObjectDecoder(Integer.MAX_VALUE,
				ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
	}

}
