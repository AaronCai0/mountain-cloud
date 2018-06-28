package com.mountainframework.core.client;

import java.util.List;
import java.util.Objects;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.mountainframework.config.InitializingConfig;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.context.MountainApplicationConfigContext;

public class RpcClientExecutor implements InitializingConfig {

	private final RpcClientLoader loader = RpcClientLoader.getLoader();

	public RpcClientExecutor() {

	}

	public RpcClientExecutor(String ip, int port) {
		loader.load(ip, port);
	}

	@Override
	public void init(MountainApplicationConfigContext context) {
		RegistryConfig providerRegistry = context.getConsumerRegistry();
		List<String> addressList = Splitter.on(":")
				.splitToList(Preconditions.checkNotNull(providerRegistry.getAddress(), "Consumer address is null"));
		String ip = addressList.get(0);
		int port = Integer.valueOf(Objects.toString(addressList.get(1), "80"));
		loader.load(ip, port);
	}

	public void stop() {
		loader.unLoad();
	}

}
