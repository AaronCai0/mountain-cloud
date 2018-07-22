package com.mountain.demo.provider.service;

import com.mountain.demo.service.CalcService;

public class CalcServiceImpl implements CalcService {

	@Override
	public Integer add(int a, int b) {
		return a + b;
	}

}
