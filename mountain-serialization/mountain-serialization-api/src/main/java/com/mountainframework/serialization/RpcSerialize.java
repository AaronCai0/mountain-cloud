package com.mountainframework.serialization;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * RpcSerialize
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface RpcSerialize {

	void serialize(OutputStream output, Object obj) throws IOException;

	Object deserialize(InputStream input, Class<?> cls) throws IOException;
}
