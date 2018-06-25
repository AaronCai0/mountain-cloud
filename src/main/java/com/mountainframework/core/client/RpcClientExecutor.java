package com.mountainframework.core.client;

public class RpcClientExecutor {

	private final RpcClientLoader loader = RpcClientLoader.getInstance();

	public RpcClientExecutor(String ip, int port) {
		loader.load(ip, port);
	}

	public void stop() {
		loader.unLoad();
	}

}
