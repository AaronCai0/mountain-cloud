package com.mountainframework.remoting.netty.server;

import com.mountainframework.remoting.RemotingExecutor;
import com.mountainframework.remoting.model.RemotingBean;

/**
 * Netty服务端调度器
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class NettyServerExecutor implements RemotingExecutor {

	private NettyServerLoader loader;

	private NettyServerExecutor() {
	}

	public static NettyServerExecutor create() {
		NettyServerExecutor executor = new NettyServerExecutor();
		executor.loader = NettyServerLoader.create();
		return executor;
	}

	@Override
	public void start(RemotingBean remotingBean) {
		loader.load(remotingBean);
	}

	@Override
	public void stop() {
		loader.unLoad();
	}

}
