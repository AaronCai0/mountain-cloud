package com.mountainframework.common.constant;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * VMCacheOption
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public enum VMCacheOption {

	DEFAULT(24, 1024, TimeUnit.HOURS);

	public final long DURATION;

	public final long MAXSIZE;

	public final TimeUnit UNIT;

	private VMCacheOption(long duration, long maxSize, TimeUnit unit) {
		this.DURATION = duration;
		this.UNIT = unit;
		this.MAXSIZE = maxSize;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}

}
