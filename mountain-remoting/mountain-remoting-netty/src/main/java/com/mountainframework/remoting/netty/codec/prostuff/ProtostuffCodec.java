package com.mountainframework.remoting.netty.codec.prostuff;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.common.io.Closer;
import com.mountainframework.remoting.netty.codec.NettyMessageCodec;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.serialization.protostuff.ProtostuffSerialize;
import com.mountainframework.serialization.protostuff.ProtostuffSerializePool;

import io.netty.buffer.ByteBuf;

public class ProtostuffCodec implements NettyMessageCodec {

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
			Object obj = protostuffSerialization.deserialize(byteArrayInputStream,
					rpcDirect ? RpcMessageRequest.class : RpcMessageResponse.class);
			pool.restore(protostuffSerialization);
			return obj;
		} finally {
			closer.close();
		}
	}

}
