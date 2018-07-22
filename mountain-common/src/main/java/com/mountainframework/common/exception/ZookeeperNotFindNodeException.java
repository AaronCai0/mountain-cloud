package com.mountainframework.common.exception;

/**
 * zookeeper未找到节点异常类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class ZookeeperNotFindNodeException extends RuntimeException {

	private static final long serialVersionUID = 856010443225958171L;

	public ZookeeperNotFindNodeException() {
		super();
	}

	public ZookeeperNotFindNodeException(Throwable cause) {
		super(cause);
	}

	public ZookeeperNotFindNodeException(String message) {
		super(message);
	}

}
