package com.mountainframework.config;

import java.io.Serializable;
import java.util.Map;

import org.springframework.beans.factory.InitializingBean;

import com.mountainframework.config.container.MountainConfigContainer;

public class ProviderConfig implements InitializingBean, Serializable {

	private static final long serialVersionUID = -2223932846615785011L;

	private Integer timeout;

	private Map<String, RegistryConfig> registryMap;

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		MountainConfigContainer.getInstance().setProvider(this);
	}

}
