package com.mountainframework.common.exception;

/**
 * ServiceClassNotException
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class ServiceClassNotException extends RuntimeException {

	private static final long serialVersionUID = 856010443225958171L;

	public ServiceClassNotException() {
		super();
	}

	public ServiceClassNotException(Throwable cause) {
		super(cause);
	}

	public ServiceClassNotException(String message) {
		super(message);
	}

}
