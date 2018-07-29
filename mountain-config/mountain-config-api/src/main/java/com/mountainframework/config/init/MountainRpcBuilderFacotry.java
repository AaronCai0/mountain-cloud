package com.mountainframework.config.init;

import com.mountainframework.config.init.netty.NettyConsumerInitializer;
import com.mountainframework.config.init.netty.NettyProviderInitializer;

/**
 * MountainRpcBuilderFacotry
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class MountainRpcBuilderFacotry {

	public static InitializingService getProviderBuilder() {
		return new NettyProviderInitializer();
	}

	public static InitializingService getConsumerBuilder() {
		return new NettyConsumerInitializer();
	}

}
