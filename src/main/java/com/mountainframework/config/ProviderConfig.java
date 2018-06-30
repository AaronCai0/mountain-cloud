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
 * 提供者配置信息
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class ProviderConfig implements InitializingBean, ApplicationListener<ContextRefreshedEvent>, Serializable {

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
		Set<RegistryConfig> regitryConfigs = MountainConfigContainer.getContainer().getProviderRegistryConfigs();
		for (RegistryConfig registryConfig : regitryConfigs) {
			MountainApplicationConfigContext context = MountainApplicationConfigContext.builder()
					.setProvider(MountainConfigContainer.getContainer().getProvider())
					.setProviderRegistry(registryConfig).build();
			MountainRpcBuilderFacotry.getProviderBuilder().init(context);
		}

	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
