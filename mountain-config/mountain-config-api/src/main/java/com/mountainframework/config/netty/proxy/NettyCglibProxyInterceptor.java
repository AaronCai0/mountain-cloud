package com.mountainframework.config.netty.proxy;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

import com.mountainframework.config.init.context.MountainConfigContainer;
import com.mountainframework.remoting.netty.client.NettyClientChannelHandler;
import com.mountainframework.remoting.netty.client.NettyClientLoader;
import com.mountainframework.rpc.model.RpcMessageRequest;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class NettyCglibProxyInterceptor implements MethodInterceptor {

	@Override
	public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
		FutureTask<Object> futureTask = new FutureTask<>(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				RpcMessageRequest request = new RpcMessageRequest();
				Class<?> classType = method.getDeclaringClass();
				request.setMessageId(UUID.randomUUID().toString());
				request.setClassType(classType);
				request.setMethodName(method.getName());
				request.setParameterTypes(method.getParameterTypes());
				request.setParamterVals(args);

				NettyClientChannelHandler clientHandler = NettyClientLoader.getInstance().getRpcClientHandler();
				// RpcMessageCallBack callBack = clientHandler.sendRequest(request);

				FutureTask<Object> futureTask = clientHandler.sendRequest3(request);
				Long consumerTimeout = MountainConfigContainer.getContainer().getConsumer().getTimeout();
				if (consumerTimeout == null || consumerTimeout.longValue() == 0) {
					consumerTimeout = MountainConfigContainer.getContainer().getServiceReferenceConfigMap()
							.get(classType.getName()).getTimeout();
				}
				return futureTask.get();
			}
		});
		NettyClientLoader.getInstance().getThreadPoolExecutor().submit(futureTask);
		return futureTask.get();
	}

	public static void main(String[] args) throws Exception {
		FutureTask<Object> f = new FutureTask<>(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				return 1;
			}
		});
		System.out.println(f.get());
	}

}
