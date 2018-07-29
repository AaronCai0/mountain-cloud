package com.mountainframework.remoting.netty;

import io.netty.channel.ChannelPipeline;

/**
 * ChannelPipeLineHandler
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface ChannelPipeLineHandler {

	void handle(ChannelPipeline pipeline);

}
