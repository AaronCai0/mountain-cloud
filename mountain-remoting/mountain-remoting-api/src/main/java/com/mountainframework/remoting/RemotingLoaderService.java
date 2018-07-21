package com.mountainframework.remoting;

import java.net.InetSocketAddress;

import com.mountainframework.serialization.RpcSerializeProtocol;

public interface RemotingLoaderService {

	void load(InetSocketAddress socketAddress, RpcSerializeProtocol protocol);

	void unLoad();

}
