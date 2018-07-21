package com.mountainframework.config.init;

import com.mountainframework.config.init.netty.NettyConsumerInitializer;
import com.mountainframework.config.init.netty.NettyProviderInitializer;

public class MountainRpcBuilderFacotry {

	public static InitializingService getProviderBuilder() {
		return new NettyProviderInitializer();
	}

	public static InitializingService getConsumerBuilder() {
		return new NettyConsumerInitializer();
	}

}
