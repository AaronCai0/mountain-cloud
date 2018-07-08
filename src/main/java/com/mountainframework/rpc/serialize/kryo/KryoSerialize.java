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
	public Object deserialize(InputStream input) throws IOException {
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

}