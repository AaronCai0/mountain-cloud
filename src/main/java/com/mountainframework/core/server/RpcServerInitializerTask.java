package com.mountainframework.core.server;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mountainframework.config.init.context.MountainServiceConfigContext;
import com.mountainframework.excpeion.RpcInvokeErrorException;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;

import io.netty.channel.ChannelHandlerContext;

/**
 * Rpc服务端初始化任务类，多线程任务
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcServerInitializerTask implements Callable<Boolean> {

	private static final Logger logger = LoggerFactory.getLogger(RpcServerInitializerTask.class);

	private final RpcMessageRequest request;

	private final RpcMessageResponse response;

	private final ChannelHandlerContext ctx;

	public RpcServerInitializerTask(RpcMessageRequest request, RpcMessageResponse response, ChannelHandlerContext ctx) {
		this.request = request;
		this.response = response;
		this.ctx = ctx;
	}

	@Override
	public Boolean call() throws Exception {
		try {
			String className = request.getClassName();
			Object requestBean = MountainServiceConfigContext.getServiceBeanMap().get(className);
			Object result = MethodUtils.invokeMethod(requestBean, request.getMethodName(), request.getParamterVals(),
					request.getParameterTypes());
			response.setMessageId(request.getMessageId());
			response.setResult(result);

			return Boolean.TRUE;
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			logger.error("Mountain Rpc Server invoke error.", e);
			throw new RpcInvokeErrorException(e);
		}
	}

}
