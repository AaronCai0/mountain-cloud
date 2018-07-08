package com.mountainframework.core.protocol;

import com.mountainframework.core.ChannelPipeLineHandler;
import com.mountainframework.rpc.serialize.protostuff.ProtostuffCodec;
import com.mountainframework.rpc.serialize.protostuff.ProtostuffDecoder;
import com.mountainframework.rpc.serialize.protostuff.ProtostuffEncoder;

import io.netty.channel.ChannelPipeline;

public class ProtostuffProtocolHandler implements ChannelPipeLineHandler {

	private boolean rpcDirect;

	public ProtostuffProtocolHandler buildRpcDirect(boolean rpcDirect) {
		this.rpcDirect = rpcDirect;
		return this;
	}

	@Override
	public void handle(ChannelPipeline pipeline) {
		ProtostuffCodec codec = new ProtostuffCodec();
		codec.setRpcDirect(rpcDirect);
		pipeline.addLast(new ProtostuffEncoder(codec));
		pipeline.addLast(new ProtostuffDecoder(codec));
	}

}
