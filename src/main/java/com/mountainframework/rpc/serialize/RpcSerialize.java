package com.mountainframework.rpc.serialize;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface RpcSerialize {

	void serialize(OutputStream output, Object obj) throws IOException;

	Object deserialize(InputStream input) throws IOException;

	<T> T deserialize(InputStream input, Class<T> clazz) throws IOException;

}
