package com.mountain.demo.provider;

import com.mountain.demo.provider.service.CalcServiceImpl;
import com.mountain.demo.service.CalcService;
import com.mountainframework.common.ReflectionAsmUtils;

public class Test {

	public static void main(String[] args) {
		CalcService calc = new CalcServiceImpl();
		// calc.add(1,2);

		long ti = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			ReflectionAsmUtils.get(CalcServiceImpl.class).invoke(calc, "add", i, i + 1);
		}
		System.out.println(System.currentTimeMillis() - ti);

	}

}
