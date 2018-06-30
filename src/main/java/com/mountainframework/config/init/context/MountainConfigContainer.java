package com.mountainframework.config.init.context;

import java.util.Map;
import java.util.Set;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProviderConfig;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceConfig;
import com.mountainframework.config.ServiceReferenceConfig;

/**
 * Mountain配置信息容器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class MountainConfigContainer {

	private ProviderConfig provider;

	private final Set<RegistryConfig> providerRegistryConfigs;

	private ConsumerConfig consumer;

	private final Set<RegistryConfig> consumerRegistryConfigs;

	private final Map<String, ServiceConfig> serviceConfigMap;

	private final Set<ServiceReferenceConfig> serviceReferenceConfigs;

	private final Map<String, Object> serviceBeanMap;

	public static MountainConfigContainer getContainer() {
		return MountainConfigContainerHolder.getInstance();
	}

	private MountainConfigContainer() {
		providerRegistryConfigs = Sets.newConcurrentHashSet();
		serviceConfigMap = Maps.newConcurrentMap();
		consumerRegistryConfigs = Sets.newConcurrentHashSet();
		serviceReferenceConfigs = Sets.newConcurrentHashSet();
		serviceBeanMap = Maps.newConcurrentMap();
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

	public Map<String, Object> getServiceBeanMap() {
		return serviceBeanMap;
	}

	public Map<String, ServiceConfig> getServiceConfigMap() {
		return serviceConfigMap;
	}

}
