package com.mountainframework.common.exception;

/**
 * MountainInvokeTimeoutException
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class MountainInvokeTimeoutException extends RuntimeException {

	private static final long serialVersionUID = 856010443225958171L;

	public MountainInvokeTimeoutException() {
		super();
	}

	public MountainInvokeTimeoutException(Throwable cause) {
		super(cause);
	}

	public MountainInvokeTimeoutException(String message) {
		super(message);
	}

}
