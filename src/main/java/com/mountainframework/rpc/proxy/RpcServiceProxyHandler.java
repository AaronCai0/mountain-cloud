package com.mountainframework.rpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import com.mountainframework.core.client.RpcClientChannelHandler;
import com.mountainframework.core.client.RpcClientLoader;
import com.mountainframework.rpc.support.RpcMessageCallBack;
import com.mountainframework.rpc.support.RpcMessageRequest;

/**
 * Rpc服务代理操作处理类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcServiceProxyHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		RpcMessageRequest request = new RpcMessageRequest();
		request.setMessageId(UUID.randomUUID().toString());
		request.setClassName(method.getDeclaringClass().getName());
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParamterVals(args);

		RpcClientChannelHandler clientHandler = RpcClientLoader.getLoader().getRpcClientHandler();
		RpcMessageCallBack callBack = clientHandler.sendRequest(request);
		return callBack.start();
	}

}
