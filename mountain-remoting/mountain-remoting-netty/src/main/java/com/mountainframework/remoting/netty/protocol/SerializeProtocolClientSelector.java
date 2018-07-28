package com.mountainframework.remoting.netty.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ClassToInstanceMap;
import com.mountainframework.remoting.netty.ChannelPipeLineHandler;
import com.mountainframework.remoting.netty.ProtocolSelectorFrame;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelPipeline;

public class SerializeProtocolClientSelector implements ProtocolSelectorFrame {

	private static Logger logger = LoggerFactory.getLogger(SerializeProtocolClientSelector.class);

	private static final ClassToInstanceMap<ChannelPipeLineHandler> handlers = SerializeProtocolHandlers.getHandlers();

	private SerializeProtocolClientSelector() {
	}

	public static SerializeProtocolClientSelector selector() {
		return new SerializeProtocolClientSelector();
	}

	@Override
	public void select(RpcSerializeProtocol protocol, ChannelPipeline pipeline) {
		ChannelPipeLineHandler handler = null;
		switch (protocol) {
		case JDKNATIVE:
			handler = handlers.getInstance(JdkNativeProtocolHandler.class);
			break;
		case KRYO:
			handler = handlers.getInstance(KryoProtocolHandler.class);
			break;
		case HESSIAN:
			handler = handlers.getInstance(HessianProtocolHandler.class);
			break;
		case PROTOSTUFF:
			handler = handlers.getInstance(ProtostuffProtocolClientHandler.class);
			break;
		default:
			break;
		}
		if (handler == null) {
			logger.warn("No support protocol={}", protocol);
			return;
		}
		handler.handle(pipeline);
	}

}
