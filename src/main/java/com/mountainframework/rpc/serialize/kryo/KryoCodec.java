package com.mountainframework.rpc.serialize.kryo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.google.common.io.Closer;
import com.mountainframework.rpc.serialize.RpcMessageCodec;

import io.netty.buffer.ByteBuf;

public class KryoCodec implements RpcMessageCodec {

	private KryoSerialize serializetor = KryoSerialize.getInstance();
	private Closer closer;

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
			Object obj = serializetor.deserialize(input);
			return obj;
		} finally {
			closer.close();
		}
	}

}
