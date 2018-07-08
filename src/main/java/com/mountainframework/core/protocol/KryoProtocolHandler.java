package com.mountainframework.core.protocol;

import com.mountainframework.core.ChannelPipeLineHandler;
import com.mountainframework.rpc.serialize.kryo.KryoCodec;
import com.mountainframework.rpc.serialize.kryo.KryoDecoder;
import com.mountainframework.rpc.serialize.kryo.KryoEncoder;

import io.netty.channel.ChannelPipeline;

public class KryoProtocolHandler implements ChannelPipeLineHandler {

	@Override
	public void handle(ChannelPipeline pipeline) {
		KryoCodec codec = KryoCodec.create();
		pipeline.addLast(new KryoEncoder(codec));
		pipeline.addLast(new KryoDecoder(codec));
	}

}
