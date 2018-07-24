package com.mountainframework.remoting;

import com.mountainframework.remoting.model.RemotingBean;

public interface RemotingLoaderService {

	void load(RemotingBean remotingBean);

	void unLoad();

}
