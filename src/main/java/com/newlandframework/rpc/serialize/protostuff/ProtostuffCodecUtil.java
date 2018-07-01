package com.newlandframework.rpc.serialize.protostuff;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.common.io.Closer;
import com.newlandframework.rpc.serialize.MessageCodecUtil;

import io.netty.buffer.ByteBuf;

public class ProtostuffCodecUtil implements MessageCodecUtil {
	private static Closer closer = Closer.create();
	private ProtostuffSerializePool pool = ProtostuffSerializePool.getProtostuffPoolInstance();
	private boolean rpcDirect = false;

	public boolean isRpcDirect() {
		return rpcDirect;
	}

	public void setRpcDirect(boolean rpcDirect) {
		this.rpcDirect = rpcDirect;
	}

	@Override
	public void encode(final ByteBuf out, final Object message) throws IOException {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			closer.register(byteArrayOutputStream);
			ProtostuffSerialize protostuffSerialization = pool.borrow();
			protostuffSerialization.serialize(byteArrayOutputStream, message);
			byte[] body = byteArrayOutputStream.toByteArray();
			int dataLength = body.length;
			out.writeInt(dataLength);
			out.writeBytes(body);
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
