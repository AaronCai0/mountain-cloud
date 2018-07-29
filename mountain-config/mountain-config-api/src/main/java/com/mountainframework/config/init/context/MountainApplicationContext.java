package com.mountainframework.config.init.context;

import java.util.Set;

import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProtocolConfig;
import com.mountainframework.config.ProviderConfig;
import com.mountainframework.registry.ServiceDiscovery;
import com.mountainframework.registry.ServiceRegistry;

/**
 * Mountain运行时配置中心
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public final class MountainApplicationContext {

	private ProviderConfig provider;

	private ProtocolConfig providerProtocol;

	private Set<ServiceRegistry> providerRegistry;

	private ConsumerConfig consumer;

	private ProtocolConfig consumerProtocol;

	private Set<ServiceDiscovery> consumerRegistry;

	public MountainApplicationContext(ProviderConfig provider, ProtocolConfig providerProtocol,
			Set<ServiceRegistry> providerRegistry, ConsumerConfig consumer, ProtocolConfig consumerProtocol,
			Set<ServiceDiscovery> consumerRegistry) {
		this.provider = provider;
		this.providerProtocol = providerProtocol;
		this.providerRegistry = providerRegistry;
		this.consumer = consumer;
		this.consumerProtocol = consumerProtocol;
		this.consumerRegistry = consumerRegistry;
	}

	public ProviderConfig getProvider() {
		return provider;
	}

	public ProtocolConfig getProviderProtocol() {
		return providerProtocol;
	}

	public Set<ServiceRegistry> getProviderRegistry() {
		return providerRegistry;
	}

	public ConsumerConfig getConsumer() {
		return consumer;
	}

	public ProtocolConfig getConsumerProtocol() {
		return consumerProtocol;
	}

	public Set<ServiceDiscovery> getConsumerRegistry() {
		return consumerRegistry;
	}

	public static ApplicationConfigContextBuilder builder() {
		return new ApplicationConfigContextBuilder();
	}

	public static class ApplicationConfigContextBuilder {
		private ProviderConfig provider;

		private ProtocolConfig providerProtocol;

		private Set<ServiceRegistry> providerRegistry;

		private ConsumerConfig consumer;

		private ProtocolConfig consumerProtocol;

		private Set<ServiceDiscovery> consumerRegistry;

		public ApplicationConfigContextBuilder setProvider(ProviderConfig provider) {
			this.provider = provider;
			return this;
		}

		public ApplicationConfigContextBuilder setProviderProtocol(ProtocolConfig providerProtocol) {
			this.providerProtocol = providerProtocol;
			return this;
		}

		public ApplicationConfigContextBuilder setProviderRegistry(Set<ServiceRegistry> providerRegistry) {
			this.providerRegistry = providerRegistry;
			return this;
		}

		public ApplicationConfigContextBuilder setConsumer(ConsumerConfig consumer) {
			this.consumer = consumer;
			return this;
		}

		public ApplicationConfigContextBuilder setConsumerProtocol(ProtocolConfig consumerProtocol) {
			this.consumerProtocol = consumerProtocol;
			return this;
		}

		public ApplicationConfigContextBuilder setConsumerRegistry(Set<ServiceDiscovery> consumerRegistry) {
			this.consumerRegistry = consumerRegistry;
			return this;
		}

		public MountainApplicationContext build() {
			return new MountainApplicationContext(provider, providerProtocol, providerRegistry, consumer,
					consumerProtocol, consumerRegistry);
		}

	}

}
