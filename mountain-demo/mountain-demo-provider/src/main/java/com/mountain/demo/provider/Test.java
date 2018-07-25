package com.mountain.demo.provider;

import com.mountain.demo.provider.service.CalcServiceImpl;
import com.mountain.demo.service.CalcService;
import com.mountainframework.common.ReflectionAsms;

public class Test {

	public static void main(String[] args) throws Exception {
		CalcService calc = new CalcServiceImpl();
		// calc.add(1,2);

		// MethodAccess md = MethodAccess.get(CalcServiceImpl.class);
		ReflectionAsms.get(CalcServiceImpl.class);

		Class<?> cls = CalcServiceImpl.class;
		long ti = System.currentTimeMillis();
		for (int i = 0; i < 100; i++) {
			ReflectionAsms.getUnchecked(cls).invoke(calc, "add", i, i + 1);
			// md.invoke(calc, "add", i, i + 1);
			// calc.add(i, i + 1);
			// MethodUtils.invokeMethod(calc, "add", i, i + 1);
		}
		System.out.println(System.currentTimeMillis() - ti);

	}

}
