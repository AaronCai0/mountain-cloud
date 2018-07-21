package com.mountainframework.remoting.netty;

import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelPipeline;

public interface ProtocolSelectorFrame {

	void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline);

}
