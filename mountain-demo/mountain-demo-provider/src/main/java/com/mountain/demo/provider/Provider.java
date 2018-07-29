package com.mountain.demo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Provider
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class Provider {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		new ClassPathXmlApplicationContext("mountain-demo-provider.xml");
	}

}
