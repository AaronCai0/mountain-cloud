package com.mountainframework.config;

import java.io.Serializable;
import java.util.Set;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import com.mountainframework.config.init.context.MountainApplicationConfigContext;
import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.core.factory.MountainRpcBuilderFacotry;

/**
 * 消费者配置信息
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class ConsumerConfig implements InitializingBean, ApplicationListener<ContextRefreshedEvent>, Serializable {

	private static final long serialVersionUID = 1822528678724275446L;

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

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
