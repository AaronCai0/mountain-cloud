package com.mountainframework.remoting.netty;

import com.mountainframework.remoting.netty.protocol.SerializeProtocolClientSelector;
import com.mountainframework.remoting.netty.protocol.SerializeProtocolServerSelector;

public class SerializeProtocolSelectors {

	public static SerializeProtocolServerSelector serverSelector() {
		return SerializeProtocolServerSelector.selector();
	}

	public static SerializeProtocolClientSelector clientSelector() {
		return SerializeProtocolClientSelector.selector();
	}

}
