package com.mountainframework.rpc.serialize;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public enum RpcSerializeProtocol {

	JDK("jdk"), KRYO("kryo"), HESSIAN("hessian"), PROTOSTUFF("protostuff");

	private String serializeProtocol;

	private RpcSerializeProtocol(String serializeProtocol) {
		this.serializeProtocol = serializeProtocol;
	}

	public String getSerializeProtocol() {
		return serializeProtocol;
	}

	@Override
	public String toString() {
		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
		return ReflectionToStringBuilder.toString(this);
	}

}
