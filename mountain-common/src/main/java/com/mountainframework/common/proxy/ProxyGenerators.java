package com.mountainframework.common.proxy;

import com.google.common.reflect.AbstractInvocationHandler;

import net.sf.cglib.proxy.MethodInterceptor;

public abstract class ProxyGenerators {

	public static ProxyGenerator cglibGenerator(MethodInterceptor interceptor) {
		return CglibProxyGenerator.getGenerator(interceptor);
	}

	public static ProxyGenerator jdkGenerator(AbstractInvocationHandler handler) {
		return JDKProxyGenerator.getGenerator(handler);
	}

}
