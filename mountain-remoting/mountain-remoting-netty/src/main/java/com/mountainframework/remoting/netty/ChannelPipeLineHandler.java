package com.mountainframework.remoting.netty;

import io.netty.channel.ChannelPipeline;

public interface ChannelPipeLineHandler {

	void handle(ChannelPipeline pipeline);

}
