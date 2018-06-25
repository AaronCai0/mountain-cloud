// package com.mountainframework.config.spring;
//
// import org.springframework.beans.factory.config.BeanDefinition;
// import org.springframework.beans.factory.support.RootBeanDefinition;
// import org.springframework.beans.factory.xml.BeanDefinitionParser;
// import org.springframework.beans.factory.xml.ParserContext;
// import org.w3c.dom.Element;
//
// public class RpcRegisteryParser implements BeanDefinitionParser {
// @Override
// public BeanDefinition parse(Element element, ParserContext parserContext) {
// String id = element.getAttribute("id");
// String echoApiPort = element.getAttribute("address");
// String protocolType = element.getAttribute("protocol");
//
// RootBeanDefinition beanDefinition = new RootBeanDefinition();
// beanDefinition.setBeanClass(RpcRegistery.class);
// beanDefinition.getPropertyValues().addPropertyValue("id", id);
// beanDefinition.getPropertyValues().addPropertyValue("address", echoApiPort);
// beanDefinition.getPropertyValues().addPropertyValue("protocol",
// protocolType);
// parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
//
// return beanDefinition;
// }
// }
