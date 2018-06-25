package com.mountainframework.rpc.boot;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MountainRpcStartor {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("mountain-provider.xml");
	}

}
