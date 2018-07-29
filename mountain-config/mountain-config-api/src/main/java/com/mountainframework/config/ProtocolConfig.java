package com.mountainframework.config;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mountainframework.config.init.context.MountainConfigContainer;

/**
 * 协议配置信息
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ProtocolConfig implements InitializingBean, ApplicationContextAware, Serializable {

	private static final long serialVersionUID = -1709327230693127992L;

	// 服务协议
	private String name;

	// 服务IP地址
	private String host;

	// 服务端口
	private Integer port;

	private String serialize;

	private Integer threads;

	private ApplicationContext applicationContext;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getSerialize() {
		return serialize;
	}

	public void setSerialize(String serialize) {
		this.serialize = serialize;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (applicationContext.containsBean("provider")) {
			MountainConfigContainer.getContainer().getProviderProtocolConfigs().add(this);
		} else if (applicationContext.containsBean("consumer")) {
			MountainConfigContainer.getContainer().getConsumerProtocolConfigs().add(this);
		}
	}

	public Integer getThreads() {
		return threads;
	}

	public void setThreads(Integer threads) {
		this.threads = threads;
	}

	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

}
