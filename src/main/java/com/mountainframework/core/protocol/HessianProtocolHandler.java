package com.mountainframework.core.protocol;

import com.mountainframework.core.ChannelPipeLineHandler;
import com.mountainframework.rpc.serialize.hessian.HessianCodec;
import com.mountainframework.rpc.serialize.hessian.HessianDecoder;
import com.mountainframework.rpc.serialize.hessian.HessianEncoder;

import io.netty.channel.ChannelPipeline;

public class HessianProtocolHandler implements ChannelPipeLineHandler {

	@Override
	public void handle(ChannelPipeline pipeline) {
		HessianCodec codec = new HessianCodec();
		pipeline.addLast(new HessianEncoder(codec));
		pipeline.addLast(new HessianDecoder(codec));
	}

}
