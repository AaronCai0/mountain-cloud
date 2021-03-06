package com.mountainframework.remoting.netty.codec.hessian;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.common.io.Closer;
import com.mountainframework.remoting.netty.codec.NettyMessageCodec;
import com.mountainframework.serialization.hessian.HessianSerialize;
import com.mountainframework.serialization.hessian.HessianSerializePool;

import io.netty.buffer.ByteBuf;

/**
 * HessianCodec
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class HessianCodec implements NettyMessageCodec {

	private HessianSerializePool pool = HessianSerializePool.getHessianPoolInstance();
	private static Closer closer = Closer.create();

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
			Object object = hessianSerialization.deserialize(byteArrayInputStream, Object.class);
			pool.restore(hessianSerialization);
			return object;
		} finally {
			closer.close();
		}
	}

}
