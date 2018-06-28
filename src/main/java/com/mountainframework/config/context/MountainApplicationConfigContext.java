package com.mountainframework.config.context;

import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProviderConfig;
import com.mountainframework.config.RegistryConfig;

public final class MountainApplicationConfigContext {

	private ProviderConfig provider;

	private RegistryConfig providerRegistry;

	private ConsumerConfig consumer;

	private RegistryConfig consumerRegistry;

	private MountainApplicationConfigContext(ProviderConfig provider, RegistryConfig providerRegistry,
			ConsumerConfig consumer, RegistryConfig consumerRegistry) {
		this.provider = provider;
		this.providerRegistry = providerRegistry;
		this.consumer = consumer;
		this.consumerRegistry = consumerRegistry;
	}

	public ProviderConfig getProvider() {
		return provider;
	}

	public RegistryConfig getProviderRegistry() {
		return providerRegistry;
	}

	public ConsumerConfig getConsumer() {
		return consumer;
	}

	public RegistryConfig getConsumerRegistry() {
		return consumerRegistry;
	}

	public static ApplicationConfigContextBuilder builder() {
		return new ApplicationConfigContextBuilder();
	}

	public static class ApplicationConfigContextBuilder {

		private ProviderConfig provider;

		private RegistryConfig providerRegistry;

		private ConsumerConfig consumer;

		private RegistryConfig consumerRegistry;

		public ApplicationConfigContextBuilder setProvider(ProviderConfig provider) {
			this.provider = provider;
			return this;
		}

		public ApplicationConfigContextBuilder setProviderRegistry(RegistryConfig providerRegistry) {
			this.providerRegistry = providerRegistry;
			return this;
		}

		public ApplicationConfigContextBuilder setConsumer(ConsumerConfig consumer) {
			this.consumer = consumer;
			return this;
		}

		public ApplicationConfigContextBuilder setConsumerRegistry(RegistryConfig consumerRegistry) {
			this.consumerRegistry = consumerRegistry;
			return this;
		}

		public MountainApplicationConfigContext build() {
			return new MountainApplicationConfigContext(provider, providerRegistry, consumer, consumerRegistry);
		}
	}

}
