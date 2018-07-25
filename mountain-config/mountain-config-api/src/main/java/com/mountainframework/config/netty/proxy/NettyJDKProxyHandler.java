package com.mountainframework.config.netty.proxy;

import java.lang.reflect.Method;
import java.util.UUID;

import com.google.common.reflect.AbstractInvocationHandler;
import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.remoting.netty.client.NettyClientChannelHandler;
import com.mountainframework.remoting.netty.client.NettyClientLoader;
import com.mountainframework.rpc.model.RpcMessageCallBack;
import com.mountainframework.rpc.model.RpcMessageRequest;

/**
 * Rpc服务代理操作处理类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyJDKProxyHandler extends AbstractInvocationHandler {

	@Override
	public Object handleInvocation(Object proxy, Method method, Object[] args) throws Throwable {
		RpcMessageRequest request = new RpcMessageRequest();
		String className = method.getDeclaringClass().getName();
		request.setMessageId(UUID.randomUUID().toString());
		request.setClassType(request.getClass());
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParamterVals(args);

		NettyClientChannelHandler clientHandler = NettyClientLoader.getInstance().getRpcClientHandler();
		RpcMessageCallBack callBack = clientHandler.sendRequest(request);
		Long consumerTimeout = MountainConfigContainer.getContainer().getConsumer().getTimeout();
		if (consumerTimeout == null || consumerTimeout.longValue() == 0) {
			consumerTimeout = MountainConfigContainer.getContainer().getServiceReferenceConfigMap().get(className)
					.getTimeout();
		}
		return callBack.start(consumerTimeout);
	}

}
