package com.mountainframework.remoting.netty;

import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelPipeline;

/**
 * ProtocolSelectorFrame
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface ProtocolSelectorFrame {

	void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline);

}
