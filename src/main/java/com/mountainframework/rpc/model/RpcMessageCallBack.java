package com.mountainframework.rpc.model;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.mountainframework.excpeion.RpcInvokeTimeoutException;

/**
 * Rpc服务消息回调类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcMessageCallBack implements Serializable {

	private static final long serialVersionUID = -3938693690009620969L;

	private RpcMessageResponse response;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	public Object start() {
		try {
			lock.lock();
			condition.await(3L, TimeUnit.SECONDS);
			if (response == null) {
				return null;
			}
			return response.getResult();
		} catch (InterruptedException e) {
			throw new RpcInvokeTimeoutException(e);
		} finally {
			lock.unlock();
		}
	}

	public void over(RpcMessageResponse response) {
		try {
			lock.lock();
			this.response = response;
			condition.signal();
		} finally {
			lock.unlock();
		}

	}
}
