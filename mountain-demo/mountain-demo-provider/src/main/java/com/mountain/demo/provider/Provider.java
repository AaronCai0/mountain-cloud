package com.mountain.demo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Provider {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("mountain-demo-provider.xml");
	}

}
