package com.mountainframework.core;

import com.mountainframework.rpc.serialize.RpcSerializeProtocol;

import io.netty.channel.ChannelPipeline;

public interface ProtocolSelectorFrame {

	void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline);

}
