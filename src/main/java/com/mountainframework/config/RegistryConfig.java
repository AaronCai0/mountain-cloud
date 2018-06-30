package com.mountainframework.config;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.config.spring.extension.SpringExtensionFactory;

/**
 * 注册中心配置信息
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RegistryConfig implements InitializingBean, ApplicationContextAware, Serializable {

	private static final long serialVersionUID = -6321800544373306404L;

	// 注册中心地址
	private String address;

	// 注册中心缺省端口
	private Integer port;

	// 注册中心协议
	private String protocol;

	// 注册中心请求超时时间(毫秒)
	private Integer timeout;

	// 启动时检查注册中心是否存在
	private Boolean check;

	// 在该注册中心上服务是否暴露
	private Boolean register;

	// 是否为缺省
	private Boolean isDefault;

	private ApplicationContext applicationContext;

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public Integer getTimeout() {
		return timeout;
	}

	public void setTimeout(Integer timeout) {
		this.timeout = timeout;
	}

	public Boolean getCheck() {
		return check;
	}

	public void setCheck(Boolean check) {
		this.check = check;
	}

	public Boolean getRegister() {
		return register;
	}

	public void setRegister(Boolean register) {
		this.register = register;
	}

	public Boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(Boolean isDefault) {
		this.isDefault = isDefault;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (applicationContext.containsBean("provider")) {
			MountainConfigContainer.getContainer().getProviderRegistryConfigs().add(this);
		} else if (applicationContext.containsBean("consumer")) {
			MountainConfigContainer.getContainer().getConsumerRegistryConfigs().add(this);
		}
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		SpringExtensionFactory.addApplicationContext(applicationContext);
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
