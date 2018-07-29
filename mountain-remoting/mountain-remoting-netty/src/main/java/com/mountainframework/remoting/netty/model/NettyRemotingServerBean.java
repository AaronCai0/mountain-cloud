package com.mountainframework.remoting.netty.model;

import java.io.Serializable;
import java.net.InetSocketAddress;
import java.util.Map;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.google.common.base.Preconditions;
import com.mountainframework.remoting.model.RemotingBean;
import com.mountainframework.serialization.RpcSerializeProtocol;

/**
 * NettyRemotingBean
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyRemotingServerBean extends RemotingBean implements Serializable {

	private static final long serialVersionUID = 2980461534885064406L;

	private InetSocketAddress socketAddress;

	private Integer backLog;

	private Boolean keepAlive;

	private NettyRemotingServerBean() {
	}

	private NettyRemotingServerBean(RpcSerializeProtocol protocol, Map<String, Object> handlerMap, Integer threads,
			InetSocketAddress socketAddress, Integer backLog, Boolean keepAlive) {
		super(protocol, handlerMap, threads);
		this.socketAddress = socketAddress;
		this.backLog = backLog;
		this.keepAlive = keepAlive;
	}

	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}

	public Integer getBackLog() {
		return backLog;
	}

	public Boolean getKeepAlive() {
		return keepAlive;
	}

	public static NettyRemotingBeanBuilder builder() {
		return new NettyRemotingBeanBuilder();
	}

	public static class NettyRemotingBeanBuilder {

		private RpcSerializeProtocol protocol;

		private Map<String, Object> handlerMap;

		private Integer threads;

		private InetSocketAddress socketAddress;

		private Integer backLog;

		private Boolean keepAlive;

		public NettyRemotingBeanBuilder setProtocol(RpcSerializeProtocol protocol) {
			this.protocol = protocol;
			return this;
		}

		public NettyRemotingBeanBuilder setHandlerMap(Map<String, Object> handlerMap) {
			this.handlerMap = handlerMap;
			return this;
		}

		public NettyRemotingBeanBuilder setThreads(Integer threads) {
			this.threads = threads;
			return this;
		}

		public NettyRemotingBeanBuilder setSocketAddress(InetSocketAddress socketAddress) {
			this.socketAddress = socketAddress;
			return this;
		}

		public NettyRemotingBeanBuilder setBackLog(Integer backLog) {
			this.backLog = backLog;
			return this;
		}

		public NettyRemotingBeanBuilder setKeepAlive(Boolean keepAlive) {
			this.keepAlive = keepAlive;
			return this;
		}

		public NettyRemotingServerBean build() {
			Preconditions.checkNotNull(protocol, "RemotingBean protocol can not be null.");
			Preconditions.checkNotNull(handlerMap, "RemotingBean handlerMap can not be null.");
			Preconditions.checkNotNull(threads, "RemotingBean threads can not be null.");
			Preconditions.checkNotNull(socketAddress, "RemotingBean socketAddress can not be null.");
			return new NettyRemotingServerBean(protocol, handlerMap, threads, socketAddress, backLog, keepAlive);
		}

	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

}
