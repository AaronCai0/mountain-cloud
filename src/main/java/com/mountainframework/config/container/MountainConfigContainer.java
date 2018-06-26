package com.mountainframework.config.container;

import java.util.Set;

import com.google.common.collect.Sets;
import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProviderConfig;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceConfig;
import com.mountainframework.config.ServiceReferenceConfig;

public class MountainConfigContainer {

	private ProviderConfig provider;

	private final Set<RegistryConfig> providerRegistryConfigs;

	private ConsumerConfig consumer;

	private final Set<RegistryConfig> consumerRegistryConfigs;

	private final Set<ServiceConfig> serviceConfigs;

	private final Set<ServiceReferenceConfig> serviceReferenceConfigs;

	public static MountainConfigContainer getInstance() {
		return MountainConfigContainerHolder.getInstance();
	}

	private MountainConfigContainer() {
		providerRegistryConfigs = Sets.newConcurrentHashSet();
		serviceConfigs = Sets.newConcurrentHashSet();
		consumerRegistryConfigs = Sets.newConcurrentHashSet();
		serviceReferenceConfigs = Sets.newConcurrentHashSet();
	}

	private static class MountainConfigContainerHolder {
		private static final MountainConfigContainer INSTANCE = new MountainConfigContainer();

		public static MountainConfigContainer getInstance() {
			return INSTANCE;
		}
	}

	public Set<ServiceConfig> getServiceConfigs() {
		return serviceConfigs;
	}

	public ProviderConfig getProvider() {
		return provider;
	}

	public void setProvider(ProviderConfig provider) {
		this.provider = provider;
	}

	public Set<RegistryConfig> getProviderRegistryConfigs() {
		return providerRegistryConfigs;
	}

	public ConsumerConfig getConsumer() {
		return consumer;
	}

	public void setConsumer(ConsumerConfig consumer) {
		this.consumer = consumer;
	}

	public Set<RegistryConfig> getConsumerRegistryConfigs() {
		return consumerRegistryConfigs;
	}

	public Set<ServiceReferenceConfig> getServiceReferenceConfigs() {
		return serviceReferenceConfigs;
	}

}
