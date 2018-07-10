package com.mountainframework.rpc.serialize.protostuff;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.common.io.Closer;
import com.mountainframework.rpc.serialize.RpcMessageCodec;

import io.netty.buffer.ByteBuf;

public class ProtostuffCodec implements RpcMessageCodec {

	private final Closer closer = Closer.create();
	private final ProtostuffSerializePool pool = ProtostuffSerializePool.getProtostuffPoolInstance();
	private boolean rpcDirect;

	public boolean isRpcDirect() {
		return rpcDirect;
	}

	public void setRpcDirect(boolean rpcDirect) {
		this.rpcDirect = rpcDirect;
	}

	@Override
	public void encode(Object message, ByteBuf buf) throws IOException {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			closer.register(byteArrayOutputStream);
			ProtostuffSerialize protostuffSerialization = pool.borrow();
			protostuffSerialization.serialize(byteArrayOutputStream, message);
			byte[] body = byteArrayOutputStream.toByteArray();
			int dataLength = body.length;
			buf.writeInt(dataLength);
			buf.writeBytes(body);
			pool.restore(protostuffSerialization);
		} finally {
			closer.close();
		}
	}

	@Override
	public Object decode(byte[] body) throws IOException {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
			closer.register(byteArrayInputStream);
			ProtostuffSerialize protostuffSerialization = pool.borrow();
			protostuffSerialization.setRpcDirect(rpcDirect);
			Object obj = protostuffSerialization.deserialize(byteArrayInputStream);
			pool.restore(protostuffSerialization);
			return obj;
		} finally {
			closer.close();
		}
	}

}
