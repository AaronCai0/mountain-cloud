package com.mountainframework.core.netty;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang3.reflect.MethodUtils;

import com.mountainframework.config.container.ServiceConfigContainer;
import com.mountainframework.rpc.support.RpcRequest;
import com.mountainframework.rpc.support.RpcReseponse;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

public class RpcInitializerTask implements Runnable {

	private final RpcRequest request;

	private final ChannelHandlerContext ctx;

	public RpcInitializerTask(RpcRequest request, ChannelHandlerContext ctx) {
		this.request = request;
		this.ctx = ctx;
	}

	@Override
	public void run() {
		String className = request.getClassName();
		Object requestBean = ServiceConfigContainer.getInstance().get(className);
		try {
			Object result = MethodUtils.invokeMethod(requestBean, request.getMethodName(), request.getParamterVals(),
					request.getParameterTypes());
			RpcReseponse response = new RpcReseponse();
			response.setMessageId(request.getMessageId());
			response.setResult(result);

			ctx.writeAndFlush(response).addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					System.out.println("PeakServer response message : " + request.getMessageId());
				}
			});
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}

	}

}
