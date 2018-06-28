package com.mountainframework.config;

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;

import com.mountainframework.config.context.MountainConfigContainer;

public class ServiceReferenceConfig implements InitializingBean, Serializable {

	private static final long serialVersionUID = 4096179256884078139L;

	private String id;

	private String interfaceName;

	private Integer timeout;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		MountainConfigContainer.getContainer().getServiceReferenceConfigs().add(this);
	}

}
