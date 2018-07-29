package com.mountainframework.remoting.netty.codec.prostuff;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.mountainframework.remoting.netty.codec.NettyMessageCodec;
import com.mountainframework.rpc.model.RpcMessageCallBack;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.serialization.protostuff.ProtostuffSerialize;
import com.mountainframework.serialization.protostuff.ProtostuffSerializePool;
import com.mountainframework.serialization.protostuff.SchemaCache;

import io.netty.buffer.ByteBuf;

/**
 * ProtostuffCodec
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ProtostuffCodec implements NettyMessageCodec {

	private static final ProtostuffSerializePool pool = ProtostuffSerializePool.getProtostuffPoolInstance();

	static {
		SchemaCache.putCache(RpcMessageRequest.class);
		SchemaCache.putCache(RpcMessageResponse.class);
		SchemaCache.putCache(RpcMessageCallBack.class);
	}

	private Class<?> deserializeClass;

	private ProtostuffCodec() {
	}

	private ProtostuffCodec(Class<?> deserializeClass) {
		this.deserializeClass = deserializeClass;
	}

	public static ProtostuffCodec create(Class<?> deserializeClass) {
		return new ProtostuffCodec(deserializeClass);
	}

	@Override
	public void encode(Object message, ByteBuf buf) throws IOException {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		ProtostuffSerialize protostuffSerialization = pool.borrow();
		protostuffSerialization.serialize(byteArrayOutputStream, message);
		byte[] body = byteArrayOutputStream.toByteArray();
		int dataLength = body.length;
		buf.writeInt(dataLength);
		buf.writeBytes(body);
		pool.restore(protostuffSerialization);
	}

	@Override
	public Object decode(byte[] body) throws IOException {
		ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(body);
		ProtostuffSerialize protostuffSerialization = pool.borrow();
		Object obj = protostuffSerialization.deserialize(byteArrayInputStream, deserializeClass);
		pool.restore(protostuffSerialization);
		return obj;
	}

}
