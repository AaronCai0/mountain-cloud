package com.mountainframework.config;

import java.io.Serializable;

import org.springframework.beans.factory.InitializingBean;

import com.mountainframework.config.container.MountainConfigContainer;

public class ConsumerConfig implements InitializingBean, Serializable {

	private static final long serialVersionUID = 4096179256884078139L;

	private Integer timeout;

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		MountainConfigContainer.getInstance().setConsumer(this);
	}

}
