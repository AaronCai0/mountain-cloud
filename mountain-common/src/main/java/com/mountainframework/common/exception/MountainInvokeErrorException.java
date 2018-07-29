package com.mountainframework.common.exception;

/**
 * MountainInvokeErrorException
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class MountainInvokeErrorException extends RuntimeException {

	private static final long serialVersionUID = 856010443225958171L;

	public MountainInvokeErrorException() {
		super();
	}

	public MountainInvokeErrorException(Throwable cause) {
		super(cause);
	}

	public MountainInvokeErrorException(String message) {
		super(message);
	}

}
