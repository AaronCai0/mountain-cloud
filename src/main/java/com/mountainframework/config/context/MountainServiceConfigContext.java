package com.mountainframework.config.context;

import java.util.Map;
import java.util.Set;

import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceConfig;
import com.mountainframework.config.ServiceReferenceConfig;

public final class MountainServiceConfigContext {

	private static final MountainConfigContainer container = MountainConfigContainer.getContainer();

	public static Set<RegistryConfig> getConsumerRegistryConfigs() {
		return container.getConsumerRegistryConfigs();
	}

	public static Map<String, ServiceConfig> getServiceConfigMap() {
		return container.getServiceConfigMap();
	}

	public static Set<ServiceReferenceConfig> getServiceReferenceConfigs() {
		return container.getServiceReferenceConfigs();
	}

	public static Map<String, Object> getServiceBeanMap() {
		return container.getServiceBeanMap();
	}

}
