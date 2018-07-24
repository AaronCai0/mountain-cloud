package com.mountainframework.config.spring.schema;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.google.common.base.Preconditions;
import com.mountainframework.common.Constants;
import com.mountainframework.common.ObjectUtils;
import com.mountainframework.config.ApplicationConfig;
import com.mountainframework.config.ConsumerConfig;
import com.mountainframework.config.ProtocolConfig;
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

	private static final Logger logger = LoggerFactory.getLogger(MountainBeanDefinitionParser.class);

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

		MutablePropertyValues beanProperties = beanDefinition.getPropertyValues();
		String id = element.getAttribute("id");
		if (beanClass.equals(ApplicationConfig.class)) {
			beanProperties.addPropertyValue("name", element.getAttribute("name"));
			beanProperties.addPropertyValue("version", element.getAttribute("version"));
			beanProperties.addPropertyValue("owner", element.getAttribute("owner"));

			parserContext.getRegistry().registerBeanDefinition("application", beanDefinition);
		} else if (beanClass.equals(ProtocolConfig.class)) {
			String name = element.getAttribute("name");
			name = StringUtils.isBlank(name) ? "mountain" : name;
			beanProperties.addPropertyValue("name", name);
			beanProperties.addPropertyValue("threads",
					ObjectUtils.toStringForDefault(element.getAttribute("threads"), Constants.DEFAULT_THREADS));
			try {
				beanProperties.addPropertyValue("host", ObjectUtils.toStringForDefault(element.getAttribute("host"),
						InetAddress.getLocalHost().getHostAddress()));
			} catch (UnknownHostException e) {
				logger.error("Not get protocol host.", e);
			}
			beanProperties.addPropertyValue("port", element.getAttribute("port"));
			beanProperties.addPropertyValue("serialize",
					ObjectUtils.toStringForDefault(element.getAttribute("serialize"), Constants.DEFAULT_SERIALIZE));

			parserContext.getRegistry().registerBeanDefinition(name, beanDefinition);
		} else if (beanClass.equals(RegistryConfig.class)) {
			beanProperties.addPropertyValue("address", element.getAttribute("address"));
			beanProperties.addPropertyValue("port", element.getAttribute("port"));
			beanProperties.addPropertyValue("protocol", element.getAttribute("protocol"));
			beanProperties.addPropertyValue("timeout", element.getAttribute("timeout"));
			beanProperties.addPropertyValue("check", element.getAttribute("check"));
			beanProperties.addPropertyValue("register", element.getAttribute("register"));
			beanProperties.addPropertyValue("isDefault", element.getAttribute("isDefault"));

			if (StringUtils.isBlank(id)) {
				parserContext.getRegistry().registerBeanDefinition(UUID.randomUUID().toString(), beanDefinition);
			}
		} else if (beanClass.equals(ProviderConfig.class)) {
			beanProperties.addPropertyValue("timeout", element.getAttribute("timeout"));
			parserContext.getRegistry().registerBeanDefinition("provider", beanDefinition);

		} else if (beanClass.equals(ConsumerConfig.class)) {
			beanProperties.addPropertyValue("timeout", element.getAttribute("timeout"));
			parserContext.getRegistry().registerBeanDefinition("consumer", beanDefinition);

		} else if (beanClass.equals(ServiceConfig.class)) {
			String interfaceName = element.getAttribute("interface");
			beanProperties.addPropertyValue("interfaceName", interfaceName);
			beanProperties.addPropertyValue("timeout", element.getAttribute("timeout"));
			beanProperties.addPropertyValue("ref", element.getAttribute("ref"));
			beanProperties.addPropertyValue("check", element.getAttribute("check"));
			parserContext.getRegistry().registerBeanDefinition(interfaceName, beanDefinition);
		} else if (beanClass.equals(ServiceReferenceConfig.class)) {
			String interfaceName = element.getAttribute("interface");
			beanProperties.addPropertyValue("id", id);
			beanProperties.addPropertyValue("interfaceName", interfaceName);
			beanProperties.addPropertyValue("timeout", element.getAttribute("timeout"));

			parserContext.getRegistry().registerBeanDefinition(interfaceName, beanDefinition);
		}
		if (StringUtils.isNotBlank(id)) {
			Preconditions.checkState(!parserContext.getRegistry().containsBeanDefinition(id),
					"Duplicate spring bean id " + id);
			parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
			beanProperties.addPropertyValue("id", id);
		}

		return beanDefinition;
	}

}
