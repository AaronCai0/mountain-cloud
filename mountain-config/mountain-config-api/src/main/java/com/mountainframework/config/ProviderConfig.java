
package com.mountainframework.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.google.common.collect.Sets;
import com.mountainframework.common.ObjectUtils;
import com.mountainframework.common.ReflectionAsmCache;
import com.mountainframework.common.ReflectionAsmCache.ReflectionAsmCacheBuilder;
import com.mountainframework.common.ReflectionAsms;
import com.mountainframework.common.StringPatternUtils;
import com.mountainframework.common.bean.AddressSplitResult;
import com.mountainframework.common.constant.Constants;
import com.mountainframework.config.init.MountainRpcBuilderFacotry;
import com.mountainframework.config.init.context.MountainApplicationContext;
import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.registry.ServiceRegistry;
import com.mountainframework.registry.zookeeper.service.ZooKeeperServiceRegistry;

/**
 * 提供者配置信息
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ProviderConfig implements InitializingBean, ApplicationListener<ContextRefreshedEvent>, Serializable {

	private static final Logger logger = LoggerFactory.getLogger(ProviderConfig.class);

	private static final long serialVersionUID = 6994526915703694262L;

	private Integer timeout;

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		MountainConfigContainer.getContainer().setProvider(this);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Map<String, Object> serviceMap = MountainConfigContainer.getContainer().getServiceBeanMap();
		try {
			ReflectionAsmCacheBuilder asmBuilder = ReflectionAsmCache.builder();
			Set<Entry<String, Object>> serviceEntrySet = serviceMap.entrySet();
			for (Entry<String, Object> serviceEntry : serviceEntrySet) {
				asmBuilder.loadCache(Class.forName(serviceEntry.getKey()), serviceEntry.getValue().getClass());
			}
			ReflectionAsms.initCache(asmBuilder.build());
		} catch (ClassNotFoundException e) {
			logger.error("Class not found : " + e.getMessage(), e);
		}

		Set<ServiceRegistry> serviceRegistries = Sets.newConcurrentHashSet();
		Set<RegistryConfig> registryConfigs = MountainConfigContainer.getContainer().getProviderRegistryConfigs();
		for (RegistryConfig registryConfig : registryConfigs) {
			AddressSplitResult splitResult = StringPatternUtils.splitAddress(registryConfig.getAddress(),
					Constants.PROTOCOL_DELIMITER);
			String registryType = ObjectUtils.toStringForDefault(splitResult.getLeft(), Constants.DEFAULT_REGISTRY);
			String registryUrl = splitResult.getRight();
			ServiceRegistry serviceRegistry = null;
			if (registryType.equalsIgnoreCase(Constants.REGISTRY_ZOOKEEPER)) {
				serviceRegistry = new ZooKeeperServiceRegistry(registryUrl);
			}
			serviceRegistries.add(serviceRegistry);
		}

		Set<ProtocolConfig> providerProtocolConfigs = MountainConfigContainer.getContainer()
				.getProviderProtocolConfigs();

		printLog();
		for (ProtocolConfig protocolConfig : providerProtocolConfigs) {
			MountainApplicationContext context = MountainApplicationContext.builder()
					.setProvider(MountainConfigContainer.getContainer().getProvider())
					.setProviderRegistry(serviceRegistries).setProviderProtocol(protocolConfig).build();
			MountainRpcBuilderFacotry.getProviderBuilder().init(context);
		}

	}

	public static final void printLog() {
		Logger logger = LoggerFactory.getLogger("MountainRPC");
		Resource resource = new ClassPathResource("mountain-logo.txt");
		if (!resource.exists()) {
			logger.warn("Mountain logo not Find in classPath!");
		}
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), "UTF-8"))) {
			StringBuilder sb = new StringBuilder();
			sb.append(System.lineSeparator());
			String str = null;
			while ((str = reader.readLine()) != null) {
				sb.append(System.lineSeparator());
				sb.append(str);
			}
			sb.append(System.lineSeparator());
			logger.info(sb.toString());
		} catch (Exception e) {
			logger.info("Read moutain logo fail", e);
		}
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
