package com.mountainframework.remoting;

public interface RemotingExecutor {

	void start(String host, Integer port, String protocolName);

	void stop();

}
