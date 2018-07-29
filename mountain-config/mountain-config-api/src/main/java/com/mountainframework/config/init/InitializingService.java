package com.mountainframework.config.init;

import com.mountainframework.config.init.context.MountainApplicationContext;

/**
 * Spring容器加载完成后初始化配置接口
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface InitializingService {

	void init(MountainApplicationContext context);

}
