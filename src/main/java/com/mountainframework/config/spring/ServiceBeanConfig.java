package com.mountainframework.config.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import com.mountainframework.config.container.ServiceConfigContainer;
import com.mountainframework.config.spring.extension.SpringExtensionFactory;

public class ServiceBeanConfig implements ApplicationContextAware, ApplicationListener<ApplicationEvent> {

	private String interfaceName;
	private String ref;
	private ApplicationContext applicationContext;

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		ServiceConfigContainer.getInstance().put(interfaceName, applicationContext.getBean(ref));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		SpringExtensionFactory.addApplicationContext(applicationContext);
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

}
