package com.mountainframework.config;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.excpeion.ServiceClassNotException;
import com.mountainframework.rpc.proxy.RpcServiceProxyGenerator;

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

	private Integer timeout;

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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		MountainConfigContainer.getContainer().getServiceReferenceConfigs().add(this);
	}

	@Override
	public Object getObject() throws Exception {
		return RpcServiceProxyGenerator.getGenerator().generate(interfaceName);
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
