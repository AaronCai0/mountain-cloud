package com.mountainframework.remoting.netty.protocol;

import com.mountainframework.remoting.netty.ChannelPipeLineHandler;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffCodec;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffDecoder;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffEncoder;
import com.mountainframework.rpc.model.RpcMessageRequest;

import io.netty.channel.ChannelPipeline;

public class ProtostuffProtocolServerHandler implements ChannelPipeLineHandler {

	@Override
	public void handle(ChannelPipeline pipeline) {
		ProtostuffCodec codec = ProtostuffCodec.create(RpcMessageRequest.class);
		pipeline.addLast(new ProtostuffEncoder(codec));
		pipeline.addLast(new ProtostuffDecoder(codec));
	}

}
