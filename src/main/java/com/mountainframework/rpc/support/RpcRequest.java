package com.mountainframework.rpc.support;

import java.io.Serializable;
import java.util.Arrays;

public class RpcRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String messageId;

	private String methodName;

	private String className;

	private Class<?>[] parameterTypes;

	private Object[] paramterVals;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public Class<?>[] getParameterTypes() {
		return parameterTypes;
	}

	public void setParameterTypes(Class<?>[] parameterTypes) {
		this.parameterTypes = parameterTypes;
	}

	public Object[] getParamterVals() {
		return paramterVals;
	}

	public void setParamterVals(Object[] paramterVals) {
		this.paramterVals = paramterVals;
	}

	@Override
	public String toString() {
		return "RpcRequest [messageId=" + messageId + ", methodName=" + methodName + ", className=" + className
				+ ", parameterTypes=" + Arrays.toString(parameterTypes) + ", paramterVals="
				+ Arrays.toString(paramterVals) + "]";
	}

}
