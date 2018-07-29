package com.mountainframework.remoting.netty.server;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lmax.disruptor.EventHandler;
import com.mountainframework.common.ReflectionAsms;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;

/**
 * NettyChannelReadEventHandler
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyChannelReadEventHandler implements EventHandler<NettyServerChannelReadEvent> {

	private static final Logger logger = LoggerFactory.getLogger(NettyChannelReadEventHandler.class);

	@Override
	public void onEvent(NettyServerChannelReadEvent event, long sequence, boolean endOfBatch) throws Exception {
		Map<String, Object> handlerBeanMap = event.getHandlerBeanMap();
		RpcMessageResponse response = event.getResponse();
		RpcMessageRequest request = event.getRequest();
		response.setMessageId(request.getMessageId());
		Class<?> classType = request.getClassType();
		Object requestBean = handlerBeanMap.get(classType.getName());
		Object result = ReflectionAsms.getCache(classType).invoke(requestBean, request.getMethodName(),
				request.getParamterVals());
		response.setResult(result);

		ChannelHandlerContext chx = event.getChannelHandlerContext();
		Channel channel = chx.channel();
		channel.eventLoop().execute(() -> channel.writeAndFlush(response).addListener(new ChannelFutureListener() {
			@Override
			public void operationComplete(ChannelFuture channelFuture) throws Exception {
				logger.info("Mountain RPC Server(RequestCount:{}) send message-id respone:{}",
						NettyServerLoader.getAtomicRequestCount().getAndIncrement(), request.getMessageId());
			}
		}));
	}

}
