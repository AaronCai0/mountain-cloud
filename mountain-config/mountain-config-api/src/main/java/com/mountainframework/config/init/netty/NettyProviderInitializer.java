package com.mountainframework.config.init.netty;

import java.util.Set;

import com.mountainframework.common.Constants;
import com.mountainframework.config.ProtocolConfig;
import com.mountainframework.config.init.InitializingService;
import com.mountainframework.config.init.context.MountainApplicationContext;
import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.registry.ServiceRegistry;
import com.mountainframework.registry.model.RegistryUrl;
import com.mountainframework.remoting.netty.NettyExecutors;

public class NettyProviderInitializer implements InitializingService {

	private final Set<String> serviceNameSet = MountainConfigContainer.getContainer().getServiceBeanMap().keySet();

	@Override
	public void init(MountainApplicationContext context) {
		ProtocolConfig protocolConfig = context.getProviderProtocol();
		String protocolName = protocolConfig.getName();
		String host = protocolConfig.getHost();
		Integer port = protocolConfig.getPort();
		String serializeProtocolName = protocolConfig.getSerialize();
		Set<ServiceRegistry> registries = context.getProviderRegistry();
		for (ServiceRegistry serviceRegistry : registries) {
			for (String serviceName : serviceNameSet) {
				String serviceAddress = new StringBuilder().append(protocolName).append(Constants.PROTOCOL_DELIMITER)
						.append(host).append(Constants.ADDRESS_DELIMITER).append(port).toString();
				RegistryUrl url = new RegistryUrl();
				url.setServiceName(serviceName);
				url.setServiceAddress(serviceAddress);
				serviceRegistry.register(url);
			}
		}
		NettyExecutors.serverExecutor().initBeanHandler(MountainConfigContainer.getContainer().getServiceBeanMap())
				.start(host, port, serializeProtocolName);
	}

}
