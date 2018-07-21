package com.mountainframework.config.init.context;

import java.util.Map;
import java.util.Set;

import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceConfig;
import com.mountainframework.config.ServiceReferenceConfig;

/**
 * Mountain服务配置中心
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
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
