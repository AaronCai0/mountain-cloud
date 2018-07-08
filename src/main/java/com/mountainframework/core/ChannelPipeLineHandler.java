package com.mountainframework.core;

import io.netty.channel.ChannelPipeline;

public interface ChannelPipeLineHandler {

	void handle(ChannelPipeline pipeline);

}
