package com.mountainframework.excpeion;

/**
 * Rpc服务调用超时类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcInvokeTimeoutException extends RuntimeException {

	private static final long serialVersionUID = 856010443225958171L;

	public RpcInvokeTimeoutException() {
		super();
	}

	public RpcInvokeTimeoutException(Throwable cause) {
		super(cause);
	}

	public RpcInvokeTimeoutException(String message) {
		super(message);
	}

}
