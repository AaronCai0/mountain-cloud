package com.mountainframework.remoting.netty.client;

import com.mountainframework.remoting.RemotingExecutor;
import com.mountainframework.remoting.model.RemotingBean;

/**
 * Netty客户端调度器
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class NettyClientExecutor implements RemotingExecutor {

	private NettyClientLoader loader;

	private NettyClientExecutor() {
	}

	public static NettyClientExecutor create() {
		NettyClientExecutor executor = new NettyClientExecutor();
		executor.loader = NettyClientLoader.getInstance();
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
