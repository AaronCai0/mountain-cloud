package com.mountainframework.serialization;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * RpcSerializeProtocol
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public enum RpcSerializeProtocol {

	JDKNATIVE("jdk"), KRYO("kryo"), HESSIAN("hessian"), PROTOSTUFF("protostuff");

	private String serializeProtocol;

	private RpcSerializeProtocol(String serializeProtocol) {
		this.serializeProtocol = serializeProtocol;
	}

	public String getSerializeProtocol() {
		return serializeProtocol;
	}

	public static RpcSerializeProtocol findProtocol(final String protocolName) {
		RpcSerializeProtocol[] protocols = values();
		for (RpcSerializeProtocol protocol : protocols) {
			if (protocol.getSerializeProtocol().equalsIgnoreCase(protocolName)) {
				return protocol;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		ReflectionToStringBuilder.setDefaultStyle(ToStringStyle.SHORT_PREFIX_STYLE);
		return ReflectionToStringBuilder.toString(this);
	}
}
