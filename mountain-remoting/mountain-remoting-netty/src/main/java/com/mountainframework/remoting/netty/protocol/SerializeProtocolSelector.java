package com.mountainframework.remoting.netty.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.mountainframework.remoting.netty.ChannelPipeLineHandler;
import com.mountainframework.remoting.netty.ProtocolSelectorFrame;
import com.mountainframework.serialization.RpcSerializeProtocol;

import io.netty.channel.ChannelPipeline;

public class SerializeProtocolSelector implements ProtocolSelectorFrame {

	private static Logger logger = LoggerFactory.getLogger(SerializeProtocolSelector.class);

	private static ClassToInstanceMap<ChannelPipeLineHandler> handlers = MutableClassToInstanceMap.create();

	private boolean rpcDirect;

	static {
		handlers.putInstance(JdkNativeProtocolHandler.class, new JdkNativeProtocolHandler());
		handlers.putInstance(KryoProtocolHandler.class, new KryoProtocolHandler());
		handlers.putInstance(HessianProtocolHandler.class, new HessianProtocolHandler());
		handlers.putInstance(ProtostuffProtocolHandler.class, new ProtostuffProtocolHandler());
	}

	public static SerializeProtocolSelector selector() {
		return new SerializeProtocolSelector();
	}

	public SerializeProtocolSelector initRpcDirect(boolean rpcDirect) {
		this.rpcDirect = rpcDirect;
		return this;
	}

	private SerializeProtocolSelector() {
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
			handler = handlers.getInstance(ProtostuffProtocolHandler.class).buildRpcDirect(rpcDirect);
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
