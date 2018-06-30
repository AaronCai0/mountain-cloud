package com.mountainframework.config.spring;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;
import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProviderConfig;
import com.mountainframework.config.RegistryConfig;
import com.mountainframework.config.ServiceConfig;
import com.mountainframework.config.ServiceReferenceConfig;

/**
 * 解析Spring配置的xml节点
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class MountainBeanDefinitionParser implements BeanDefinitionParser {

	// private final Logger logger =
	// LoggerFactory.getLogger(MountainBeanDefinitionParser.class);

	private final Class<?> beanClass;

	private final boolean isRequired;

	public MountainBeanDefinitionParser(Class<?> beanClass, boolean isRequired) {
		Preconditions.checkNotNull(beanClass, "Resolve xml node beanClass cannot be null");
		this.beanClass = beanClass;
		this.isRequired = isRequired;
	}

	@Override
	public BeanDefinition parse(Element element, ParserContext parserContext) {
		return parseXmlElement(element, parserContext, beanClass, isRequired);
	}

	/**
	 * 解析xml节点
	 * 
	 * @param element
	 * @param parserContext
	 * @param beanClass
	 * @param isRequired
	 * @return
	 */
	private static BeanDefinition parseXmlElement(Element element, ParserContext parserContext, Class<?> beanClass,
			boolean isRequired) {
		RootBeanDefinition beanDefinition = new RootBeanDefinition();
		beanDefinition.setBeanClass(beanClass);
		beanDefinition.setLazyInit(false);

		String id = element.getAttribute("id");
		if (beanClass.equals(RegistryConfig.class)) {
			beanDefinition.getPropertyValues().addPropertyValue("address", element.getAttribute("address"));
			beanDefinition.getPropertyValues().addPropertyValue("port", element.getAttribute("port"));
			beanDefinition.getPropertyValues().addPropertyValue("protocol", element.getAttribute("protocol"));
			beanDefinition.getPropertyValues().addPropertyValue("timeout", element.getAttribute("timeout"));
			beanDefinition.getPropertyValues().addPropertyValue("check", element.getAttribute("check"));
			beanDefinition.getPropertyValues().addPropertyValue("register", element.getAttribute("register"));
			beanDefinition.getPropertyValues().addPropertyValue("isDefault", element.getAttribute("isDefault"));

			if (StringUtils.isBlank(id)) {
				parserContext.getRegistry().registerBeanDefinition(UUID.randomUUID().toString(), beanDefinition);
			}
		} else if (beanClass.equals(ProviderConfig.class)) {
			beanDefinition.getPropertyValues().addPropertyValue("timeout", element.getAttribute("timeout"));
			parserContext.getRegistry().registerBeanDefinition("provider", beanDefinition);

		} else if (beanClass.equals(ConsumerConfig.class)) {
			beanDefinition.getPropertyValues().addPropertyValue("timeout", element.getAttribute("timeout"));
			parserContext.getRegistry().registerBeanDefinition("consumer", beanDefinition);

		} else if (beanClass.equals(ServiceConfig.class)) {
			String interfaceName = element.getAttribute("interface");
			beanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);
			beanDefinition.getPropertyValues().addPropertyValue("timeout", element.getAttribute("timeout"));
			beanDefinition.getPropertyValues().addPropertyValue("ref", element.getAttribute("ref"));
			beanDefinition.getPropertyValues().addPropertyValue("check", element.getAttribute("check"));
			parserContext.getRegistry().registerBeanDefinition(interfaceName, beanDefinition);
		} else if (beanClass.equals(ServiceReferenceConfig.class)) {
			String interfaceName = element.getAttribute("interface");
			beanDefinition.getPropertyValues().addPropertyValue("id", id);
			beanDefinition.getPropertyValues().addPropertyValue("interfaceName", interfaceName);
			beanDefinition.getPropertyValues().addPropertyValue("timeout", element.getAttribute("timeout"));

			parserContext.getRegistry().registerBeanDefinition(interfaceName, beanDefinition);
		}
		if (StringUtils.isNotBlank(id)) {
			Preconditions.checkState(!parserContext.getRegistry().containsBeanDefinition(id),
					"Duplicate spring bean id " + id);
			parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
			beanDefinition.getPropertyValues().addPropertyValue("id", id);
		}

		return beanDefinition;
	}

}
