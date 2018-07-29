package com.mountainframework.registry;

import com.mountainframework.registry.model.RegistryUrl;

/**
 * ServiceDiscovery
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public interface ServiceDiscovery {

	String substribe(RegistryUrl url);

}
