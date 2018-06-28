package com.mountainframework.config;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.mountainframework.config.context.MountainApplicationConfigContext;
import com.mountainframework.config.context.MountainConfigContainer;
import com.mountainframework.core.factory.MountainRpcBuilderFacotry;

public class ProviderConfig implements InitializingBean, Serializable, ApplicationListener<ContextRefreshedEvent> {

	private static final long serialVersionUID = -2223932846615785011L;

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
		Set<RegistryConfig> regitryConfigs = MountainConfigContainer.getContainer().getProviderRegistryConfigs();
		for (RegistryConfig registryConfig : regitryConfigs) {
			MountainApplicationConfigContext context = MountainApplicationConfigContext.builder()
					.setProvider(MountainConfigContainer.getContainer().getProvider())
					.setProviderRegistry(registryConfig).build();
			MountainRpcBuilderFacotry.getProviderBuilder().init(context);
		}

	}

}
