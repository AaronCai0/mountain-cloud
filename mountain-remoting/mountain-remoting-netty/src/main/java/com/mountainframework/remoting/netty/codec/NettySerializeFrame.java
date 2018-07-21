package com.mountainframework.remoting.netty.codec;

import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelPipeline;

public interface NettySerializeFrame {

	void select(RpcSerializeProtocol protocol, ChannelPipeline pipline);

}
