package com.mountainframework.config.init;

import com.mountainframework.config.init.context.MountainApplicationContext;

/**
 * Spring容器加载完成后初始化配置服务接口
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public interface InitializingService {

	/**
	 * 初始化动作
	 */
	void init(MountainApplicationContext context);

}
