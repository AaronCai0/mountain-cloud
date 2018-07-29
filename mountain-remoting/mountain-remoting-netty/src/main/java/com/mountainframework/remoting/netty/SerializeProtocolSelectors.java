package com.mountainframework.remoting.netty;

import com.mountainframework.remoting.netty.protocol.SerializeProtocolClientSelector;
import com.mountainframework.remoting.netty.protocol.SerializeProtocolServerSelector;

/**
 * SerializeProtocolSelectors
 * 
 * @author yafeng.cai<https://github.com/AaronCai0>
 * @since 1.0
 */
public class SerializeProtocolSelectors {

	public static SerializeProtocolServerSelector serverSelector() {
		return SerializeProtocolServerSelector.selector();
	}

	public static SerializeProtocolClientSelector clientSelector() {
		return SerializeProtocolClientSelector.selector();
	}

}
