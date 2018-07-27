package com.mountain.demo.provider;

import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.util.UUID;

import com.mountain.demo.service.CalcService;
import com.mountainframework.remoting.netty.NettyExecutors;
import com.mountainframework.remoting.netty.client.NettyClientChannelHandler;
import com.mountainframework.remoting.netty.client.NettyClientLoader;
import com.mountainframework.remoting.netty.model.NettyRemotingBean;
import com.mountainframework.rpc.model.RpcMessageRequest;
import com.mountainframework.serialization.RpcSerializeProtocol;

public class ClientTest {

	public static void main(String[] args) throws UnknownHostException {
		NettyExecutors.clientExecutor()
				.start(NettyRemotingBean.builder()
						.setSocketAddress(new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 6666))
						.setProtocol(RpcSerializeProtocol.PROTOSTUFF).setThreads(200).build());
		// ReflectionAsms
		// .initCache(ReflectionAsmCache.builder().loadCache(CalcService.class,
		// CalcServiceImpl.class).build());

		Class<?> classType = CalcService.class;
		Method[] ms = classType.getDeclaredMethods();
		Method method = null;
		for (Method m : ms) {
			if (m.getName().equals("add")) {
				method = m;
			}
		}

		for (int i = 0; i < 100000; i++) {
			RpcMessageRequest request = new RpcMessageRequest();
			request.setMessageId(UUID.randomUUID().toString());
			request.setClassType(classType);
			request.setMethodName("add");
			request.setParameterTypes(method.getParameterTypes());
			request.setParamterVals(new Object[] { i, i + 1 });
			NettyClientChannelHandler clientHandler = NettyClientLoader.getInstance().getRpcClientHandler();
			clientHandler.sendRequest2(request);
		}

	}

}
