package com.mountainframework.config.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceConfig;
import com.mountainframework.config.ServiceConsumerConfig;
import com.mountainframework.config.ServiceProviderConfig;
import com.mountainframework.config.ServiceReferenceConfig;

public class MountainNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("registry", new MountainBeanDefinitionParser(RegistryConfig.class, true));
		registerBeanDefinitionParser("provider", new MountainBeanDefinitionParser(ServiceProviderConfig.class, true));
		registerBeanDefinitionParser("consumer", new MountainBeanDefinitionParser(ServiceConsumerConfig.class, true));
		registerBeanDefinitionParser("service", new MountainBeanDefinitionParser(ServiceConfig.class, true));
		registerBeanDefinitionParser("reference", new MountainBeanDefinitionParser(ServiceReferenceConfig.class, true));
	}

}
