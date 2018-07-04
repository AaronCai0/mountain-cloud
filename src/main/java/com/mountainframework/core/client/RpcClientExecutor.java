package com.mountainframework.core.client;

import java.util.List;
import java.util.Objects;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.RpcContextConfig;
import com.mountainframework.config.init.InitializingService;
import com.mountainframework.config.init.context.MountainApplicationConfigContext;

/**
 * Rpc客户端调度器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcClientExecutor implements InitializingService {

	private final RpcClientLoader loader = RpcClientLoader.getLoader();

	public RpcClientExecutor() {

	}

	public RpcClientExecutor(String ip, int port) {
		loader.load(ip, port);
	}

	@Override
	public void init(MountainApplicationConfigContext context) {
		RegistryConfig providerRegistry = context.getConsumerRegistry();
		List<String> addressList = Splitter.on(RpcContextConfig.ADDRESS_SIGN)
				.splitToList(Preconditions.checkNotNull(providerRegistry.getAddress(), "Consumer address is null"));
		String ip = addressList.get(0);
		int port = Integer.valueOf(Objects.toString(addressList.get(1), "80"));
		loader.load(ip, port);
	}

	public void stop() {
		loader.unLoad();
	}

}
