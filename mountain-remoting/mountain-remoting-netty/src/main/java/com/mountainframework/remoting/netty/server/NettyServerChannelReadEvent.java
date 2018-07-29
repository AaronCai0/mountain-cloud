package com.mountainframework.remoting.netty.server;

import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.lmax.disruptor.RingBuffer;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;

import io.netty.channel.ChannelHandlerContext;

/**
 * Netty服务端初始化事件
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyServerChannelReadEvent {

	private Map<String, Object> handlerBeanMap;

	private RpcMessageRequest request;

	private RpcMessageResponse response;

	private ChannelHandlerContext channelHandlerContext;

	public NettyServerChannelReadEvent() {
	}

	public NettyServerChannelReadEvent(ChannelHandlerContext chx, Map<String, Object> handlerBeanMap,
			RpcMessageRequest request, RpcMessageResponse response) {
		this.channelHandlerContext = chx;
		this.handlerBeanMap = handlerBeanMap;
		this.request = request;
		this.response = response;
	}

	public ChannelHandlerContext getChannelHandlerContext() {
		return channelHandlerContext;
	}

	public void setChannelHandlerContext(ChannelHandlerContext channelHandlerContext) {
		this.channelHandlerContext = channelHandlerContext;
	}

	public Map<String, Object> getHandlerBeanMap() {
		return handlerBeanMap;
	}

	public void setHandlerBeanMap(Map<String, Object> handlerBeanMap) {
		this.handlerBeanMap = handlerBeanMap;
	}

	public RpcMessageRequest getRequest() {
		return request;
	}

	public void setRequest(RpcMessageRequest request) {
		this.request = request;
	}

	public RpcMessageResponse getResponse() {
		return response;
	}

	public void setResponse(RpcMessageResponse response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

	public static void publishReadEvent(RingBuffer<NettyServerChannelReadEvent> ringBuffer,
			NettyServerChannelReadEvent task) {
		long sequence = ringBuffer.next();
		try {
			NettyServerChannelReadEvent event = ringBuffer.get(sequence);
			event.setChannelHandlerContext(task.getChannelHandlerContext());
			event.setHandlerBeanMap(task.getHandlerBeanMap());
			event.setRequest(task.getRequest());
			event.setResponse(task.getResponse());
		} finally {
			ringBuffer.publish(sequence);
		}
	}

}
