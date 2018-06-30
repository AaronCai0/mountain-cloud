package com.mountainframework.core.factory;

import com.mountainframework.config.init.InitializingService;
import com.mountainframework.core.client.RpcClientExecutor;
import com.mountainframework.core.netty.RpcServerExecutor;

/**
 * Rpc构建工厂
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public interface MountainRpcBuilderFacotry {

	public static InitializingService getProviderBuilder() {
		return new RpcServerExecutor();
	}

	public static InitializingService getConsumerBuilder() {
		return new RpcClientExecutor();
	}

}
