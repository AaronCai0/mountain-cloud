package com.mountainframework.rpc.serialize.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.common.io.Closer;
import com.mountainframework.rpc.serialize.RpcMessageCodec;

import io.netty.buffer.ByteBuf;

public class HessianCodec implements RpcMessageCodec {

	HessianSerializePool pool = HessianSerializePool.getHessianPoolInstance();
	private static Closer closer = Closer.create();

	public HessianCodec() {

	}

	@Override
	public void encode(Object message, ByteBuf out) throws IOException {
		try {
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			closer.register(byteArrayOutputStream);
			HessianSerialize hessianSerialization = pool.borrow();
			hessianSerialization.serialize(byteArrayOutputStream, message);
			byte[] body = byteArrayOutputStream.toByteArray();
			int dataLength = body.length;
			out.writeInt(dataLength);
			out.writeBytes(body);
			pool.restore(hessianSerialization);
		} finally {
			closer.close();
		}
	}

	@Override
	public Object decode(byte[] body) throws IOException {
		try {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
			closer.register(byteArrayInputStream);
			HessianSerialize hessianSerialization = pool.borrow();
			Object object = hessianSerialization.deserialize(byteArrayInputStream);
			pool.restore(hessianSerialization);
			return object;
		} finally {
			closer.close();
		}
	}

}
