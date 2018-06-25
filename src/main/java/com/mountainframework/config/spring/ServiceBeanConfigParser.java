package com.mountainframework.config.spring;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

public class ServiceBeanConfigParser implements BeanDefinitionParser {

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		String interfaceName = element.getAttribute("interface");
		String ref = element.getAttribute("ref");

		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClass(ServiceBeanConfig.class);
		beanDefinition.setLazyInit(false);
		beanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);
		beanDefinition.getPropertyValues().addPropertyValue("ref", ref);

		parserContext.getRegistry().registerBeanDefinition(interfaceName, beanDefinition);

		return beanDefinition;
	}
}
