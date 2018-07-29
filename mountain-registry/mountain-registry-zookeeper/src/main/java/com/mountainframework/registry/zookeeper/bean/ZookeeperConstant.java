package com.mountainframework.registry.zookeeper.bean;

/**
 * ZookeeperConstant
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface ZookeeperConstant {

	int ZK_SESSION_TIMEOUT = 5000;

	int ZK_CONNECTION_TIMEOUT = 1000;

	String ZK_REGISTRY_PATH = "/mountain";

	String SERVICE_TEMP_NODE_PREFIX = "/address-";

}
