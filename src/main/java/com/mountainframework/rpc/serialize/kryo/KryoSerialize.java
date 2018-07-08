package com.mountainframework.rpc.serialize.kryo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.google.common.io.Closer;
import com.mountainframework.rpc.serialize.RpcSerialize;
import com.mountainframework.rpc.serialize.RpcSerializePools;

public class KryoSerialize implements RpcSerialize {

	private final KryoPool pool;

	private final Closer closer;

	private KryoSerialize() {
		pool = RpcSerializePools.getKryo().getPool();
		closer = Closer.create();
	}

	public static KryoSerialize create() {
		return new KryoSerialize();
	}

	@Override
	public void serialize(OutputStream output, Object obj) throws IOException {
		try {
			closer.register(output);
			Kryo kryo = pool.borrow();
			Output out = new Output(output);
			kryo.writeClassAndObject(out, obj);
			pool.release(kryo);
		} finally {
			closer.close();
		}
	}

	@Override
	public Object deserialize(InputStream input) throws IOException {
		try {
			closer.register(input);
			Kryo kryo = pool.borrow();
			Input in = new Input(input);
			Object obj = kryo.readClassAndObject(in);
			input.close();
			pool.release(kryo);
			return obj;
		} finally {
			closer.close();
		}

	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T deserialize(InputStream input, Class<T> clazz) throws IOException {
		try {
			closer.register(input);
			Kryo kryo = pool.borrow();
			Input in = new Input(input);
			Object obj = kryo.readClassAndObject(in);
			if (obj == null) {
				return null;
			}
			input.close();
			pool.release(kryo);
			return (T) obj;
		} finally {
			closer.close();
		}

	}

}
