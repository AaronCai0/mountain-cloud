package com.mountainframework.common.proxy;

/**
 * ProxyGenerator
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface ProxyGenerator {

	<T> T generate(String className);

}
