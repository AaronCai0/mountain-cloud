package com.mountainframework.config.netty.proxy;

import java.lang.reflect.Method;
import java.util.UUID;

import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.remoting.netty.client.NettyClientChannelHandler;
import com.mountainframework.remoting.netty.client.NettyClientLoader;
import com.mountainframework.rpc.model.RpcMessageCallBack;
import com.mountainframework.rpc.model.RpcMessageRequest;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class NettyCglibProxyInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		// FutureTask<Object> futureTask = new FutureTask<>(new Callable<Object>() {
		// @Override
		// public Object call() throws Exception {
		// RpcMessageRequest request = new RpcMessageRequest();
		// Class<?> classType = method.getDeclaringClass();
		// request.setMessageId(UUID.randomUUID().toString());
		// request.setClassType(classType);
		// request.setMethodName(method.getName());
		// request.setParameterTypes(method.getParameterTypes());
		// request.setParamterVals(args);
		//
		// NettyClientChannelHandler clientHandler =
		// NettyClientLoader.getInstance().getRpcClientHandler();
		// RpcMessageCallBack callBack = clientHandler.sendRequest(request);
		// Long consumerTimeout =
		// MountainConfigContainer.getContainer().getConsumer().getTimeout();
		// if (consumerTimeout == null || consumerTimeout.longValue() == 0) {
		// consumerTimeout =
		// MountainConfigContainer.getContainer().getServiceReferenceConfigMap()
		// .get(classType.getName()).getTimeout();
		// }
		// // return callBack.start(consumerTimeout);
		//
		// try {
		// Thread.sleep(300000);
		// } catch (Exception e) {
		// }
		//
		// Integer a = 222;
		// return a;
		// }
		// });
		// NettyClientLoader.getInstance().getThreadPoolExecutor().submit(futureTask);
		// return futureTask.get();

		RpcMessageRequest request = new RpcMessageRequest();
		Class<?> classType = method.getDeclaringClass();
		request.setMessageId(UUID.randomUUID().toString());
		request.setClassType(classType);
		request.setMethodName(method.getName());
		request.setParameterTypes(method.getParameterTypes());
		request.setParamterVals(args);

		NettyClientChannelHandler clientHandler = NettyClientLoader.getInstance().getRpcClientHandler();
		RpcMessageCallBack callBack = clientHandler.sendRequest(request);
		Long consumerTimeout = MountainConfigContainer.getContainer().getConsumer().getTimeout();
		if (consumerTimeout == null || consumerTimeout.longValue() == 0) {
			consumerTimeout = MountainConfigContainer.getContainer().getServiceReferenceConfigMap()
					.get(classType.getName()).getTimeout();
		}
		// return callBack.start(consumerTimeout);

		try {
			Thread.sleep(300000);
		} catch (Exception e) {
		}

		Integer a = 222;
		return a;
	}

}
