package com.mountainframework.rpc.support;

import java.io.Serializable;
import java.util.Arrays;

public class RpcReseponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String messageId;

	private String error;

	private Object result;

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	@Override
	public String toString() {
		return "RpcReseponse [messageId=" + messageId + ", error=" + error + ", result=" + result + "]";
	}

}
