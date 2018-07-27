package com.mountainframework.remoting.netty.server;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;

import com.mountainframework.remoting.netty.NettyExecutors;
import com.mountainframework.remoting.netty.model.NettyRemotingBean;
import com.mountainframework.serialization.RpcSerializeProtocol;

public class ClientTest {

	public static void main(String[] args) throws UnknownHostException {
		NettyExecutors.clientExecutor()
				.start(NettyRemotingBean.builder()
						.setSocketAddress(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 6666))
						.setProtocol(RpcSerializeProtocol.PROTOSTUFF).setThreads(200).build());

	}

}
