package com.mountainframework.registry.zookeeper.service;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mountainframework.common.CollectionUtils;
import com.mountainframework.registry.ServiceDiscovery;
import com.mountainframework.registry.model.RegistryUrl;
import com.mountainframework.registry.zookeeper.bean.ZookeeperConstant;

/**
 * 基于 ZooKeeper 的服务发现接口实现
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class ZooKeeperServiceDiscovery implements ServiceDiscovery {

	private static final Logger LOGGER = LoggerFactory.getLogger(ZooKeeperServiceDiscovery.class);

	private String zkAddress;

	public ZooKeeperServiceDiscovery(String zkAddress) {
		this.zkAddress = zkAddress;
	}

	@Override
	public String substribe(RegistryUrl url) {
		// 创建 ZooKeeper 客户端
		ZkClient zkClient = new ZkClient(zkAddress, ZookeeperConstant.ZK_SESSION_TIMEOUT,
				ZookeeperConstant.ZK_CONNECTION_TIMEOUT);
		LOGGER.debug("connect zookeeper");
		try {
			// 获取 service 节点
			String servicePath = ZookeeperConstant.ZK_REGISTRY_PATH + "/" + url.getServiceName();
			if (!zkClient.exists(servicePath)) {
				throw new RuntimeException(String.format("can not find any service node on path: %s", servicePath));
			}
			List<String> addressList = zkClient.getChildren(servicePath);
			if (CollectionUtils.isEmpty(addressList)) {
				throw new RuntimeException(String.format("can not find any address node on path: %s", servicePath));
			}
			// 获取 address 节点
			String address;
			int size = addressList.size();
			if (size == 1) {
				// 若只有一个地址，则获取该地址
				address = addressList.get(0);
				LOGGER.debug("get only address node: {}", address);
			} else {
				// 若存在多个地址，则随机获取一个地址
				address = addressList.get(ThreadLocalRandom.current().nextInt(size));
				LOGGER.debug("get random address node: {}", address);
			}
			// 获取 address 节点的值
			String addressPath = servicePath + "/" + address;
			return zkClient.readData(addressPath);
		} finally {
			zkClient.close();
		}
	}

	@Override
	public void unsubstribe(RegistryUrl url) {

	}
}