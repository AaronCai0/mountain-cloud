package com.mountainframework.config.netty.proxy;

import java.lang.reflect.Method;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

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
				RpcMessageCallBack callBack = clientHandler.sendRequest(request);
				// FutureTask<RpcMessageCallBack> future = clientHandler.sendRequest(request);
				Long consumerTimeout = MountainConfigContainer.getContainer().getConsumer().getTimeout();
				if (consumerTimeout == null || consumerTimeout.longValue() == 0) {
					consumerTimeout = MountainConfigContainer.getContainer().getServiceReferenceConfigMap()
							.get(classType.getName()).getTimeout();
				}
				return callBack.start(consumerTimeout);
				// return future.get().start(consumerTimeout);
			}
		});
		NettyClientLoader.getInstance().getThreadPoolExecutor().submit(futureTask);
		return futureTask.get();
	}

}
