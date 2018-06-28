package com.mountainframework.provider.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MountainProviderStartor {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("mountain-provider.xml");
	}

}
