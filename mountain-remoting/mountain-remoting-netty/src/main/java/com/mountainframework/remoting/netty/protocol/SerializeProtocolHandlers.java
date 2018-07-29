package com.mountainframework.remoting.netty.protocol;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import com.mountainframework.remoting.netty.ChannelPipeLineHandler;

/**
 * SerializeProtocolHandlers
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class SerializeProtocolHandlers {

	private static ClassToInstanceMap<ChannelPipeLineHandler> handlers = MutableClassToInstanceMap.create();

	static {
		handlers.putInstance(JdkNativeProtocolHandler.class, new JdkNativeProtocolHandler());
		handlers.putInstance(KryoProtocolHandler.class, new KryoProtocolHandler());
		handlers.putInstance(HessianProtocolHandler.class, new HessianProtocolHandler());
		handlers.putInstance(ProtostuffProtocolServerHandler.class, new ProtostuffProtocolServerHandler());
		handlers.putInstance(ProtostuffProtocolClientHandler.class, new ProtostuffProtocolClientHandler());
	}

	public static ClassToInstanceMap<ChannelPipeLineHandler> getHandlers() {
		return handlers;
	}

}
