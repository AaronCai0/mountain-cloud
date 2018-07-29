package com.mountainframework.remoting.netty.model;

import java.io.Serializable;
import java.net.InetSocketAddress;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.google.common.base.Preconditions;
import com.mountainframework.remoting.model.RemotingBean;
import com.mountainframework.serialization.RpcSerializeProtocol;

/**
 * NettyRemotingClientBean
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyRemotingClientBean extends RemotingBean implements Serializable {

	private static final long serialVersionUID = 2980461534885064406L;

	private InetSocketAddress socketAddress;

	private Boolean keepAlive;

	private NettyRemotingClientBean() {
	}

	private NettyRemotingClientBean(RpcSerializeProtocol protocol, Integer threads, InetSocketAddress socketAddress,
			Boolean keepAlive) {
		super(protocol, threads);
		this.socketAddress = socketAddress;
		this.keepAlive = keepAlive;
	}

	public InetSocketAddress getSocketAddress() {
		return socketAddress;
	}

	public Boolean getKeepAlive() {
		return keepAlive;
	}

	public static NettyRemotingBeanBuilder builder() {
		return new NettyRemotingBeanBuilder();
	}

	public static class NettyRemotingBeanBuilder {

		private RpcSerializeProtocol protocol;

		private Integer threads;

		private InetSocketAddress socketAddress;

		private Boolean keepAlive;

		public NettyRemotingBeanBuilder setProtocol(RpcSerializeProtocol protocol) {
			this.protocol = protocol;
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

		public NettyRemotingBeanBuilder setKeepAlive(Boolean keepAlive) {
			this.keepAlive = keepAlive;
			return this;
		}

		public NettyRemotingClientBean build() {
			Preconditions.checkNotNull(protocol, "RemotingBean protocol can not be null.");
			Preconditions.checkNotNull(threads, "RemotingBean threads can not be null.");
			Preconditions.checkNotNull(socketAddress, "RemotingBean socketAddress can not be null.");
			return new NettyRemotingClientBean(protocol, threads, socketAddress, keepAlive);
		}

	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

}
