package test2;

import com.lmax.disruptor.WorkHandler;

/**
 * 发送短信并写数据库工作任务.
 * 
 * @author JIM
 *
 */
public class SmsParamEventHandler implements WorkHandler<SmsParamEvent> {

	@Override
	public void onEvent(SmsParamEvent event) throws Exception {
		try {
			SmsParam smsParam = event.getValue();
			// System.out.println(smsParam);
			// 使用注入的短信处理器发送短信，此处省略若干字
		} catch (Throwable tr) {
			tr.printStackTrace();
		}
	}

}