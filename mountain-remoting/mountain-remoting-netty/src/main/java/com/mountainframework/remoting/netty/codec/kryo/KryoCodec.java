package com.mountainframework.remoting.netty.codec.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.common.io.Closer;
import com.mountainframework.remoting.netty.codec.NettyMessageCodec;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.serialization.kryo.KryoSerialize;

import io.netty.buffer.ByteBuf;

/**
 * KryoCodec
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class KryoCodec implements NettyMessageCodec {

	private static final KryoSerialize serializetor = KryoSerialize
			.getInstance(new Class<?>[] { RpcMessageRequest.class, RpcMessageResponse.class });

	private final Closer closer;

	public static KryoCodec create() {
		return new KryoCodec();
	}

	private KryoCodec() {
		closer = Closer.create();
	}

	@Override
	public void encode(Object message, ByteBuf buf) throws IOException {
		try {
			ByteArrayOutputStream output = new ByteArrayOutputStream();
			closer.register(output);
			serializetor.serialize(output, message);
			byte[] writeData = output.toByteArray();
			buf.writeInt(writeData.length);
			buf.writeBytes(writeData);
		} finally {
			closer.close();
		}

	}

	@Override
	public Object decode(byte[] body) throws IOException {
		try {
			ByteArrayInputStream input = new ByteArrayInputStream(body);
			closer.register(input);
			Object obj = serializetor.deserialize(input, Object.class);
			return obj;
		} finally {
			closer.close();
		}
	}

}
