package com.mountainframework.remoting.netty.codec;

import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelPipeline;

/**
 * NettySerializeFrame
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface NettySerializeFrame {

	void select(RpcSerializeProtocol protocol, ChannelPipeline pipline);

}
