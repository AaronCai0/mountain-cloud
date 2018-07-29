package com.mountainframework.config.init.netty;

import java.net.InetSocketAddress;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.mountainframework.common.StringPatternUtils;
import com.mountainframework.common.bean.AddressSplitResult;
import com.mountainframework.common.constant.Constants;
import com.mountainframework.config.ProtocolConfig;
import com.mountainframework.config.ServiceReferenceConfig;
import com.mountainframework.config.init.InitializingService;
import com.mountainframework.config.init.context.MountainApplicationContext;
import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.registry.ServiceDiscovery;
import com.mountainframework.registry.model.RegistryUrl;
import com.mountainframework.remoting.netty.NettyExecutors;
import com.mountainframework.remoting.netty.model.NettyRemotingClientBean;
import com.mountainframework.serialization.RpcSerializeProtocol;

/**
 * NettyConsumerInitializer
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyConsumerInitializer implements InitializingService {

	private final Set<ServiceReferenceConfig> referenceConfigs = MountainConfigContainer.getContainer()
			.getServiceReferenceConfigs();

	@Override
	public void init(MountainApplicationContext context) {
		ProtocolConfig protocolConfig = context.getConsumerProtocol();
		String protocolName = protocolConfig.getName();
		String protocolHost = protocolConfig.getHost();
		Integer protocolPort = protocolConfig.getPort();
		Integer threads = protocolConfig.getThreads();
		String serializeProtocolName = protocolConfig.getSerialize();

		Set<String> serviceDiscoveryAddress = Sets.newHashSet();
		Set<ServiceDiscovery> registries = context.getConsumerRegistry();
		for (ServiceDiscovery serviceRegistry : registries) {
			for (ServiceReferenceConfig referenceConfig : referenceConfigs) {
				RegistryUrl url = new RegistryUrl();
				url.setServiceName(referenceConfig.getInterfaceName());
				String serviceAddress = serviceRegistry.substribe(url);
				serviceDiscoveryAddress.add(serviceAddress);
			}
		}
		for (String address : serviceDiscoveryAddress) {
			AddressSplitResult splitResult = StringPatternUtils.splitAddress(address, Constants.PROTOCOL_DELIMITER);
			AddressSplitResult addressResult = StringPatternUtils.splitAddress(splitResult.getRight(),
					Constants.ADDRESS_DELIMITER);
			String serviceProtocol = splitResult.getLeft();
			String host = addressResult.getLeft();
			String port = addressResult.getRight();
			Preconditions.checkNotNull(host, "Registry get host is null");
			Preconditions.checkNotNull(port, "Registry get port is null");
			Integer portInt = Integer.parseInt(port);
			if (host.equals(protocolHost) && portInt.equals(protocolPort) && serviceProtocol.equals(protocolName)) {
				NettyRemotingClientBean nettyRemotingBean = NettyRemotingClientBean.builder()
						.setSocketAddress(new InetSocketAddress(host, Integer.parseInt(port)))
						.setProtocol(RpcSerializeProtocol.findProtocol(serializeProtocolName)).setThreads(threads)
						.build();
				NettyExecutors.clientExecutor().start(nettyRemotingBean);
			}
		}
	}

}
