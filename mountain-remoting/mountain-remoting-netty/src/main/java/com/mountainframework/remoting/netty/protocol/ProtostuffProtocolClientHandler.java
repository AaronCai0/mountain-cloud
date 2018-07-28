package com.mountainframework.remoting.netty.protocol;

import com.mountainframework.remoting.netty.ChannelPipeLineHandler;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffCodec;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffDecoder;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffEncoder;
import com.mountainframework.rpc.model.RpcMessageResponse;

import io.netty.channel.ChannelPipeline;

public class ProtostuffProtocolClientHandler implements ChannelPipeLineHandler {

	@Override
	public void handle(ChannelPipeline pipeline) {
		ProtostuffCodec codec = ProtostuffCodec.create(RpcMessageResponse.class);
		pipeline.addLast(new ProtostuffEncoder(codec));
		pipeline.addLast(new ProtostuffDecoder(codec));
	}

}
