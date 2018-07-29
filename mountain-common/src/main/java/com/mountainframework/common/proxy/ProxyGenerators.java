package com.mountainframework.common.proxy;

import com.google.common.reflect.AbstractInvocationHandler;

import net.sf.cglib.proxy.MethodInterceptor;

/**
 * ProxyGenerators
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public abstract class ProxyGenerators {

	public static ProxyGenerator cglibGenerator(MethodInterceptor interceptor) {
		return CglibProxyGenerator.getGenerator(interceptor);
	}

	public static ProxyGenerator jdkGenerator(AbstractInvocationHandler handler) {
		return JDKProxyGenerator.getGenerator(handler);
	}

}
