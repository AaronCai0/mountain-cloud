package com.mountainframework.rpc.serialize.kryo;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.pool.KryoPool;
import com.mountainframework.rpc.serialize.RpcSerialize;

public class KryoSerialize implements RpcSerialize {

	private KryoPool pool;

	public KryoSerialize(KryoPool pool) {
		this.pool = pool;
	}

	@Override
	public void serialize(OutputStream output, Object obj) throws IOException {
		Kryo kryo = pool.borrow();
		Output out = new Output(output);
		kryo.writeClassAndObject(out, obj);
		out.close();
		pool.release(kryo);
	}

	@Override
	public <T> T deserialize(InputStream input, Class<T> clazz) throws IOException {
		Kryo kryo = pool.borrow();
		Input in = new Input(input);
		T t = kryo.readObject(in, clazz);
		in.close();
		input.close();
		pool.release(kryo);
		return t;
	}

}
