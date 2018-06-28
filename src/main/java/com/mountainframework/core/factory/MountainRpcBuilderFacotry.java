package com.mountainframework.core.factory;

import com.mountainframework.config.InitializingConfig;
import com.mountainframework.core.client.RpcClientExecutor;
import com.mountainframework.core.netty.RpcServerExecutor;

public interface MountainRpcBuilderFacotry {

	public static InitializingConfig getProviderBuilder() {
		return new RpcServerExecutor();
	}

	public static InitializingConfig getConsumerBuilder() {
		return new RpcClientExecutor();
	}

}
