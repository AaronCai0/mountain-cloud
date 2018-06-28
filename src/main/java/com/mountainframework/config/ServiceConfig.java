package com.mountainframework.config;

import java.io.Serializable;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mountainframework.config.context.MountainConfigContainer;

public class ServiceConfig implements InitializingBean, Serializable, ApplicationContextAware {

	private static final long serialVersionUID = 4096179256884078139L;

	private String interfaceName;

	private Integer timeout;

	private String ref;

	private Boolean check;

	private ApplicationContext applicationContext;

	@Override
	public void afterPropertiesSet() throws Exception {
		MountainConfigContainer.getContainer().getServiceConfigMap().put(interfaceName, this);
		MountainConfigContainer.getContainer().getServiceBeanMap().put(interfaceName, applicationContext.getBean(ref));
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
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

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

}
