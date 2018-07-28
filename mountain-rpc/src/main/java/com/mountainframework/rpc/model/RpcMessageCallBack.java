package com.mountainframework.rpc.model;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mountainframework.common.exception.MountainInvokeTimeoutException;

/**
 * Rpc服务消息回调类
 * 
 * @author yafeng.cai {@link}https://github.com/AaronCai0
 * @date 2018年6月30日
 * @since 1.0
 */
public class RpcMessageCallBack implements Serializable {

	private static final long serialVersionUID = -3938693690009620969L;

	private static final Logger logger = LoggerFactory.getLogger(RpcMessageCallBack.class);

	private volatile RpcMessageResponse response;

	private static Lock lock = new ReentrantLock();

	private static Condition condition = lock.newCondition();

	public Object start(Long timeout) {
		try {
			lock.lock();
			if (timeout == null || timeout.longValue() == 0) {
				condition.await();
			} else {
				condition.await(timeout, TimeUnit.MILLISECONDS);
			}
			if (response == null) {
				// throw new MountainInvokeTimeoutException("Invoke timeout.");
				start(timeout);
			}

			return response.getResult();
		} catch (InterruptedException | MountainInvokeTimeoutException e) {
			logger.error(e.getMessage(), e);
			return null;
		} finally {
			lock.unlock();
		}
	}

	public void over(RpcMessageResponse response) {
		try {
			lock.lock();
			condition.signal();
			this.response = response;
		} finally {
			lock.unlock();
		}
	}

	public RpcMessageResponse getResponse() {
		return response;
	}

	public void setResponse(RpcMessageResponse response) {
		this.response = response;
	}

	public void overAll() {
		try {
			lock.lock();
			condition.signalAll();
		} finally {
			lock.unlock();
		}
	}

}
