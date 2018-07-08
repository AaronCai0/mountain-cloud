package com.mountainframework.rpc.serialize.protostuff;

import java.io.InputStream;
import java.io.OutputStream;

import org.objenesis.Objenesis;
import org.objenesis.ObjenesisStd;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.rpc.model.RpcMessageResponse;
import com.mountainframework.rpc.serialize.RpcSerialize;

public class ProtostuffSerialize implements RpcSerialize {

	private static SchemaCache cachedSchema = SchemaCache.getInstance();
	private static Objenesis objenesis = new ObjenesisStd(true);
	private boolean rpcDirect = false;

	public boolean isRpcDirect() {
		return rpcDirect;
	}

	public void setRpcDirect(boolean rpcDirect) {
		this.rpcDirect = rpcDirect;
	}

	private static <T> Schema<T> getSchema(Class<?> cls) {
		return (Schema<T>) cachedSchema.get(cls);
	}

	@Override
	public Object deserialize(InputStream input) {
		try {
			Class<?> cls = isRpcDirect() ? RpcMessageRequest.class : RpcMessageResponse.class;
			Object message = objenesis.newInstance(cls);
			Schema<Object> schema = getSchema(cls);
			ProtostuffIOUtil.mergeFrom(input, message, schema);
			return message;
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		}
	}

	@Override
	public void serialize(OutputStream output, Object object) {
		Class cls = object.getClass();
		LinkedBuffer buffer = LinkedBuffer.allocate(LinkedBuffer.DEFAULT_BUFFER_SIZE);
		try {
			Schema schema = getSchema(cls);
			ProtostuffIOUtil.writeTo(output, object, schema, buffer);
		} catch (Exception e) {
			throw new IllegalStateException(e.getMessage(), e);
		} finally {
			buffer.clear();
		}
	}

}
