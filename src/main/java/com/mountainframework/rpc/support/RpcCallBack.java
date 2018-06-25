package com.mountainframework.rpc.support;

import java.io.Serializable;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RpcCallBack implements Serializable {

	private static final long serialVersionUID = 1L;

	private RpcReseponse response;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	public Object start() {
		try {
			lock.lock();
			condition.await();
			if (response == null) {
				return null;
			}
			return response.getResult();
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		} finally {
			lock.unlock();
		}
	}

	public void over(RpcReseponse response) {
		try {
			lock.lock();
			this.response = response;
			condition.signal();
		} finally {
			lock.unlock();
		}

	}
}
