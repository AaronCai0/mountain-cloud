package com.mountainframework.common.proxy;

public interface ProxyGenerator {

	<T> T generate(String className);

}
