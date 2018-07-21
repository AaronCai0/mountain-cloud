package com.mountainframework.common.bean;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class AddressSplitResult {

	private String left;

	private String right;

	public AddressSplitResult() {
	}

	public AddressSplitResult(String left, String right) {
		this.left = left;
		this.right = right;
	}

	public String getLeft() {
		return left;
	}

	public void setLeft(String left) {
		this.left = left;
	}

	public String getRight() {
		return right;
	}

	public void setRight(String right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

}
