package com.mountainframework.config.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProviderConfig;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceConfig;
import com.mountainframework.config.ServiceReferenceConfig;

/**
 * Spring容器xml解析支持类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class MountainNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("registry", new MountainBeanDefinitionParser(RegistryConfig.class, true));
		registerBeanDefinitionParser("provider", new MountainBeanDefinitionParser(ProviderConfig.class, true));
		registerBeanDefinitionParser("consumer", new MountainBeanDefinitionParser(ConsumerConfig.class, true));
		registerBeanDefinitionParser("service", new MountainBeanDefinitionParser(ServiceConfig.class, true));
		registerBeanDefinitionParser("reference", new MountainBeanDefinitionParser(ServiceReferenceConfig.class, true));
	}

}
