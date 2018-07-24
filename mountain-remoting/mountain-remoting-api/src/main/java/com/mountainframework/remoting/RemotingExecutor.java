package com.mountainframework.remoting;

import com.mountainframework.remoting.model.RemotingBean;

public interface RemotingExecutor {

	void start(RemotingBean remotingBean);

	void stop();

}
