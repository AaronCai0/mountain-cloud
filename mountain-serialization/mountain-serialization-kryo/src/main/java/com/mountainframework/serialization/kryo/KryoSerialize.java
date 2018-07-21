package com.mountainframework.serialization.kryo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.google.common.io.Closer;
import com.mountainframework.serialization.RpcSerialize;

public class KryoSerialize implements RpcSerialize {

	private static KryoPool pool;

	private final Closer closer = Closer.create();

	public static KryoSerialize create() {
		return new KryoSerialize();
	}

	public static KryoSerialize getInstance() {
		pool = KryoSerializePool.builder().build();
		return KryoSerializeHolder.INSTANCE;
	}

	public static KryoSerialize getInstance(Class<?>[] cahceClasses) {
		pool = KryoSerializePool.builder().cacheClasses(cahceClasses).build();
		return KryoSerializeHolder.INSTANCE;
	}

	@Override
	public void serialize(OutputStream output, Object obj) throws IOException {
		try {
			Kryo kryo = pool.borrow();
			Output out = new Output(output);
			closer.register(out);
			kryo.writeClassAndObject(out, obj);
			pool.release(kryo);
		} finally {
			closer.close();
		}
	}

	@Override
	public Object deserialize(InputStream input, Class<?> cls) throws IOException {
		try {
			Kryo kryo = pool.borrow();
			Input in = new Input(input);
			closer.register(in);
			Object obj = kryo.readClassAndObject(in);
			pool.release(kryo);
			return obj;
		} finally {
			closer.close();
		}
	}

	private static class KryoSerializeHolder {
		private static final KryoSerialize INSTANCE = new KryoSerialize();
	}

}
