package com.mountainframework.config;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

/**
 * 应用程序信息
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @since 1.0
 */
public class ApplicationConfig implements Serializable {

	private static final long serialVersionUID = -6415421022879552869L;

	// 应用名称
	private String name;

	// 模块版本
	private String version;

	// 应用负责人
	private String owner;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	@Override
	public String toString() {
		return new ReflectionToStringBuilder(this).toString();
	}
}
