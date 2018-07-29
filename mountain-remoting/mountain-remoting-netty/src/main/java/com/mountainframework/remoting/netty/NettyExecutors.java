package com.mountainframework.remoting.netty;

import com.mountainframework.remoting.netty.client.NettyClientExecutor;
import com.mountainframework.remoting.netty.server.NettyServerExecutor;

/**
 * NettyExecutors
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public abstract class NettyExecutors {

	public static NettyServerExecutor serverExecutor() {
		return NettyServerExecutor.create();
	}

	public static NettyClientExecutor clientExecutor() {
		return NettyClientExecutor.create();
	}

}
