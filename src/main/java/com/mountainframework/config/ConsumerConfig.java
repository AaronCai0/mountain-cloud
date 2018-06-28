package com.mountainframework.config;

import java.io.Serializable;
import java.util.Set;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.mountainframework.config.context.MountainApplicationConfigContext;
import com.mountainframework.config.context.MountainConfigContainer;
import com.mountainframework.core.factory.MountainRpcBuilderFacotry;

public class ConsumerConfig implements InitializingBean, Serializable, ApplicationListener<ContextRefreshedEvent> {

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
		MountainConfigContainer.getContainer().setConsumer(this);
	}

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Set<RegistryConfig> regitryConfigs = MountainConfigContainer.getContainer().getConsumerRegistryConfigs();
		for (RegistryConfig registryConfig : regitryConfigs) {
			MountainApplicationConfigContext context = MountainApplicationConfigContext.builder()
					.setConsumer(MountainConfigContainer.getContainer().getConsumer())
					.setConsumerRegistry(registryConfig).build();
			MountainRpcBuilderFacotry.getConsumerBuilder().init(context);
		}
	}

}
