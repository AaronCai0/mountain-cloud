package com.mountainframework.config.spring.schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

import com.mountainframework.config.ApplicationConfig;
import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProtocolConfig;
import com.mountainframework.config.ProviderConfig;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceConfig;
import com.mountainframework.config.ServiceReferenceConfig;

/**
 * Spring容器xml解析支持类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class MountainNamespaceHandler extends NamespaceHandlerSupport {

	@Override
	public void init() {
		registerBeanDefinitionParser("application", new MountainBeanDefinitionParser(ApplicationConfig.class, true));
		registerBeanDefinitionParser("protocol", new MountainBeanDefinitionParser(ProtocolConfig.class, true));
		registerBeanDefinitionParser("registry", new MountainBeanDefinitionParser(RegistryConfig.class, true));
		registerBeanDefinitionParser("provider", new MountainBeanDefinitionParser(ProviderConfig.class, true));
		registerBeanDefinitionParser("consumer", new MountainBeanDefinitionParser(ConsumerConfig.class, true));
		registerBeanDefinitionParser("service", new MountainBeanDefinitionParser(ServiceConfig.class, true));
		registerBeanDefinitionParser("reference", new MountainBeanDefinitionParser(ServiceReferenceConfig.class, true));
	}

}
