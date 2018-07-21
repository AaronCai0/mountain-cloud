package com.mountainframework.common.exception;

/**
 * Rpc服务调用超时类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcInvokeErrorException extends RuntimeException {

	private static final long serialVersionUID = 856010443225958171L;

	public RpcInvokeErrorException() {
		super();
	}

	public RpcInvokeErrorException(Throwable cause) {
		super(cause);
	}

	public RpcInvokeErrorException(String message) {
		super(message);
	}

}
