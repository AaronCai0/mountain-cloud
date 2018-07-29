package com.mountainframework.remoting.netty.server;

import java.util.Map;
import java.util.concurrent.Callable;

import com.mountainframework.common.ReflectionAsms;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;

/**
 * Netty服务端初始化任务类，多线程任务
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyServerInitializerTask implements Callable<Boolean> {

	// private static final Logger logger =
	// LoggerFactory.getLogger(RpcServerInitializerTask.class);

	private Map<String, Object> handlerBeanMap;

	private final RpcMessageRequest request;

	private final RpcMessageResponse response;

	public NettyServerInitializerTask(Map<String, Object> handlerBeanMap, RpcMessageRequest request,
			RpcMessageResponse response) {
		this.handlerBeanMap = handlerBeanMap;
		this.request = request;
		this.response = response;
	}

	@Override
	public Boolean call() throws Exception {
		response.setMessageId(request.getMessageId());
		Class<?> classType = request.getClassType();
		Object requestBean = handlerBeanMap.get(classType.getName());
		Object result = ReflectionAsms.getCache(classType).invoke(requestBean, request.getMethodName(),
				request.getParamterVals());
		// Object result = MethodAccess.get(requestBean.getClass()).invoke(requestBean,
		// request.getMethodName(),
		// request.getParamterVals());
		// Object result = MethodUtils.invokeMethod(requestBean,
		// request.getMethodName(), request.getParamterVals());
		response.setResult(result);
		return Boolean.TRUE;
	}

}
