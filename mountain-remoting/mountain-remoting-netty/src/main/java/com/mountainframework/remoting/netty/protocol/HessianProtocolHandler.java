package com.mountainframework.remoting.netty.protocol;

import com.mountainframework.remoting.netty.ChannelPipeLineHandler;
import com.mountainframework.remoting.netty.codec.hessian.HessianCodec;
import com.mountainframework.remoting.netty.codec.hessian.HessianDecoder;
import com.mountainframework.remoting.netty.codec.hessian.HessianEncoder;

import io.netty.channel.ChannelPipeline;

/**
 * HessianProtocolHandler
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class HessianProtocolHandler implements ChannelPipeLineHandler {

	@Override
	public void handle(ChannelPipeline pipeline) {
		HessianCodec codec = new HessianCodec();
		pipeline.addLast(new HessianEncoder(codec));
		pipeline.addLast(new HessianDecoder(codec));
	}

}
