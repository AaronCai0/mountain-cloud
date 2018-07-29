package com.mountainframework.config;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.google.common.collect.Sets;
import com.mountainframework.common.ObjectUtils;
import com.mountainframework.common.StringPatternUtils;
import com.mountainframework.common.bean.AddressSplitResult;
import com.mountainframework.common.constant.Constants;
import com.mountainframework.config.init.MountainRpcBuilderFacotry;
import com.mountainframework.config.init.context.MountainApplicationContext;
import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.registry.ServiceDiscovery;
import com.mountainframework.registry.zookeeper.service.ZooKeeperServiceDiscovery;

/**
 * 消费者配置信息
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ConsumerConfig implements InitializingBean, ApplicationListener<ContextRefreshedEvent>, Serializable {

	private static final long serialVersionUID = 1822528678724275446L;

	private Long timeout;

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		MountainConfigContainer.getContainer().setConsumer(this);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Set<ProtocolConfig> consumerProtocolConfigs = MountainConfigContainer.getContainer()
				.getConsumerProtocolConfigs();
		Set<ServiceDiscovery> serviceDiscoverySet = Sets.newConcurrentHashSet();
		Set<RegistryConfig> registry = MountainConfigContainer.getContainer().getConsumerRegistryConfigs();
		for (RegistryConfig registryConfig : registry) {
			AddressSplitResult splitResult = StringPatternUtils.splitAddress(registryConfig.getAddress(),
					Constants.PROTOCOL_DELIMITER);
			String registryType = ObjectUtils.toStringForDefault(splitResult.getLeft(), Constants.DEFAULT_REGISTRY);
			String registryUrl = splitResult.getRight();
			ServiceDiscovery serviceDiscovery = null;
			if (registryType.equalsIgnoreCase(Constants.REGISTRY_ZOOKEEPER)) {
				serviceDiscovery = new ZooKeeperServiceDiscovery(registryUrl);
			}
			serviceDiscoverySet.add(serviceDiscovery);
		}
		for (ProtocolConfig protocolConfig : consumerProtocolConfigs) {
			MountainApplicationContext context = MountainApplicationContext.builder()
					.setConsumer(MountainConfigContainer.getContainer().getConsumer())
					.setConsumerRegistry(serviceDiscoverySet).setConsumerProtocol(protocolConfig).build();
			MountainRpcBuilderFacotry.getConsumerBuilder().init(context);
		}
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
