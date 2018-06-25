package com.mountainframework.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import com.mountainframework.core.client.RpcClientHandler;
import com.mountainframework.core.client.RpcClientLoader;
import com.mountainframework.rpc.support.RpcCallBack;
import com.mountainframework.rpc.support.RpcRequest;

public class RpcMessageProxy implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		RpcRequest request = new RpcRequest();
		request.setMessageId(UUID.randomUUID().toString());
		request.setClassName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParamterVals(args);

		RpcClientHandler clientHandler = RpcClientLoader.getInstance().getRpcClientHandler();
		RpcCallBack callBack = clientHandler.sendRequest(request);
		return callBack.start();
	}

}
