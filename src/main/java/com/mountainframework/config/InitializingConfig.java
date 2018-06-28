package com.mountainframework.config;

import com.mountainframework.config.context.MountainApplicationConfigContext;

public interface InitializingConfig {

	/**
	 * Spring容器加载完成后初始化配置
	 */
	void init(MountainApplicationConfigContext context);

}
