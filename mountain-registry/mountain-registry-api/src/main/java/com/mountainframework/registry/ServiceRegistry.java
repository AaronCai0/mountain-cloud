package com.mountainframework.registry;

import com.mountainframework.registry.model.RegistryUrl;

public interface ServiceRegistry {

	void register(RegistryUrl url);

	void unregister(RegistryUrl url);

}
