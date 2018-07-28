package com.mountainframework.serialization.protostuff;

import java.io.InputStream;
import java.io.OutputStream;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.mountainframework.serialization.RpcSerialize;

public class ProtostuffSerialize implements RpcSerialize {

	private static final Objenesis objenesis = new ObjenesisStd(true);

	@Override
	public Object deserialize(InputStream input, Class<?> cls) {
		try {
			Object message = objenesis.newInstance(cls);
			Schema<Object> schema = SchemaCache.get(cls);
			ProtostuffIOUtil.mergeFrom(input, message, schema);
			return message;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public void serialize(OutputStream output, Object object) {
		Class<?> cls = object.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			Schema<Object> schema = SchemaCache.get(cls);
			ProtostuffIOUtil.writeTo(output, object, schema, buffer);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

}
