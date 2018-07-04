package com.mountainframework.rpc.serialize;

import java.io.InputStream;
import java.io.OutputStream;

public interface RpcSerialize {

	void serialize(OutputStream output, Object obj);

	<T> void deserialize(InputStream input, Class<T> clazz);

}
