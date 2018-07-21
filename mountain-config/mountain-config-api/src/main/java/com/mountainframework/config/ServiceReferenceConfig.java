package com.mountainframework.config;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.mountainframework.common.exception.ServiceClassNotException;
import com.mountainframework.common.proxy.ProxyGenerators;
import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.config.netty.proxy.NettyCglibProxyInterceptor;

/**
 * 服务消费配置信息
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class ServiceReferenceConfig implements InitializingBean, FactoryBean<Object>, Serializable {

	private static final long serialVersionUID = -7347028772696099784L;

	private String id;

	private String interfaceName;

	private Long timeout;

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		MountainConfigContainer.getContainer().getServiceReferenceConfigs().add(this);
		MountainConfigContainer.getContainer().getServiceReferenceConfigMap().put(interfaceName, this);
	}

	@Override
	public Object getObject() throws Exception {
		return ProxyGenerators.cglibGenerator(new NettyCglibProxyInterceptor()).generate(interfaceName);
	}

	@Override
	public Class<?> getObjectType() {
		try {
			return Class.forName(interfaceName);
		} catch (ClassNotFoundException e) {
			throw new ServiceClassNotException(e);
		}
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
