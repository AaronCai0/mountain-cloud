package com.mountainframework.remoting.model;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.google.common.base.Preconditions;
import com.mountainframework.serialization.RpcSerializeProtocol;

public abstract class RemotingBean implements Serializable {

	private static final long serialVersionUID = 6089541980781500342L;

	private RpcSerializeProtocol protocol;

	private Map<String, Object> handlerMap;

	private Integer threads;

	public RpcSerializeProtocol getProtocol() {
		return protocol;
	}

	public Map<String, Object> getHandlerMap() {
		return handlerMap;
	}

	public Integer getThreads() {
		return threads;
	}

	public RemotingBean() {
	}

	public RemotingBean(RpcSerializeProtocol protocol, Map<String, Object> handlerMap, Integer threads) {
		this.protocol = protocol;
		this.handlerMap = handlerMap;
		this.threads = threads;
	}

	public RemotingBean(RpcSerializeProtocol protocol, Integer threads) {
		this.protocol = protocol;
		this.threads = threads;
	}

	protected void checkObjValues() {
		Preconditions.checkNotNull(protocol, "RemotingBean protocol can not be null.");
		Preconditions.checkNotNull(handlerMap, "RemotingBean handlerMap can not be null.");
		Preconditions.checkNotNull(threads, "RemotingBean threads can not be null.");
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

}
