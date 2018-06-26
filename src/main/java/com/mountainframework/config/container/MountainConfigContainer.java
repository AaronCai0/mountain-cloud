package com.mountainframework.config.container;

import java.util.Set;

import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProviderConfig;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceReferenceConfig;

public class MountainConfigContainer {

	private ProviderConfig provider;

	private Set<RegistryConfig> providerRegistryConfigs;

	private ConsumerConfig consumer;

	private Set<RegistryConfig> consumerRegistryConfigs;

	private Set<ServiceReferenceConfig> serviceReferenceConfigs;

	public static MountainConfigContainer getInstance() {
		return MountainConfigContainerHolder.getInstance();
	}

	private MountainConfigContainer() {
	}

	private static class MountainConfigContainerHolder {
		private static final MountainConfigContainer INSTANCE = new MountainConfigContainer();

		public static MountainConfigContainer getInstance() {
			return INSTANCE;
		}
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

	public void setProviderRegistryConfigs(Set<RegistryConfig> providerRegistryConfigs) {
		this.providerRegistryConfigs = providerRegistryConfigs;
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

	public void setConsumerRegistryConfigs(Set<RegistryConfig> consumerRegistryConfigs) {
		this.consumerRegistryConfigs = consumerRegistryConfigs;
	}

	public Set<ServiceReferenceConfig> getServiceReferenceConfigs() {
		return serviceReferenceConfigs;
	}

	public void setServiceReferenceConfigs(Set<ServiceReferenceConfig> serviceReferenceConfigs) {
		this.serviceReferenceConfigs = serviceReferenceConfigs;
	}

}
