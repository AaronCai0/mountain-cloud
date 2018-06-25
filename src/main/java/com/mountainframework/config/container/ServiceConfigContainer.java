package com.mountainframework.config.container;

import java.util.Map;

import com.google.common.collect.Maps;

public class ServiceConfigContainer {

	private final Map<String, Object> serviceContainer = Maps.newConcurrentMap();

	public static ServiceConfigContainer getInstance() {
		return ServiceBeanContainerHolder.INSTANCE;
	}

	private ServiceConfigContainer() {
	}

	public void put(String name, Object bean) {
		serviceContainer.put(name, bean);
	}

	public void remove(String name) {
		serviceContainer.remove(name);
	}

	public Object get(String name) {
		return serviceContainer.get(name);
	}

	private static class ServiceBeanContainerHolder {
		public static final ServiceConfigContainer INSTANCE = new ServiceConfigContainer();
	}

}
