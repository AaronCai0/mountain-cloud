package com.mountain.demo.provider.service;

import com.mountain.demo.service.CalcService;

/**
 * CalcServiceImpl
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class CalcServiceImpl implements CalcService {

	@Override
	public Integer add(int a, int b) {
		return a + b;
	}

}
