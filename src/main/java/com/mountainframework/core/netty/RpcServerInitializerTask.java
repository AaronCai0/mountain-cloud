package com.mountainframework.core.netty;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mountainframework.config.init.context.MountainServiceConfigContext;
import com.mountainframework.rpc.support.RpcMessageRequest;
import com.mountainframework.rpc.support.RpcMessageReseponse;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * Rpc服务端初始化任务类，多线程任务
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcServerInitializerTask implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(RpcServerInitializerTask.class);

	private final RpcMessageRequest request;

	private final ChannelHandlerContext ctx;

	public RpcServerInitializerTask(RpcMessageRequest request, ChannelHandlerContext ctx) {
		this.request = request;
		this.ctx = ctx;
	}

	@Override
	public void run() {
		String className = request.getClassName();
		Object requestBean = MountainServiceConfigContext.getServiceBeanMap().get(className);
		try {
			Object result = MethodUtils.invokeMethod(requestBean, request.getMethodName(), request.getParamterVals(),
					request.getParameterTypes());
			RpcMessageReseponse response = new RpcMessageReseponse();
			response.setMessageId(request.getMessageId());
			response.setResult(result);

			ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					logger.info("Mountain Rpc Server response message : {}", request.getMessageId());
				}
			});
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			logger.error("RpcServerInitializerTask run error.", e);
		}
	}

}
