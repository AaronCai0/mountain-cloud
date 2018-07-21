package com.mountainframework.remoting.netty.protocol;

import com.mountainframework.remoting.netty.ChannelPipeLineHandler;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffCodec;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffDecoder;
import com.mountainframework.remoting.netty.codec.prostuff.ProtostuffEncoder;

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
