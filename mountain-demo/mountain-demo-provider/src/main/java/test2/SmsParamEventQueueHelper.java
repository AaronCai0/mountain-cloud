package test2;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * 短信队列.
 * 
 * @author JIM
 *
 */
@Component
public class SmsParamEventQueueHelper extends BaseQueueHelper<SmsParam, SmsParamEvent, SmsParamEventHandler>
		implements InitializingBean {

	private static final int QUEUE_SIZE = 4096 * 2;

	private int threadNum;

	// @Value("${app.sms.smsMsgEventQueue.threadNum:1}")
	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

	@Override
	protected int getThreadNum() {
		return threadNum;
	}

	@Override
	protected int getQueueSize() {
		return QUEUE_SIZE;
	}

	@Override
	protected Class<SmsParamEvent> eventClass() {
		return SmsParamEvent.class;
	}

	@Override
	protected Class<SmsParamEventHandler> eventHandlerClass() {
		return SmsParamEventHandler.class;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		this.init();
	}
}