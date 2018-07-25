package test2;

public class Test {

	public static void main(String[] args) {
		SmsParamEventQueueHelper helper = new SmsParamEventQueueHelper();
		helper.setThreadNum(1);
		helper.init();

		long ti = System.currentTimeMillis();
		for (int i = 0; i < 1048576; i++) {
			SmsParam smsParam = new SmsParam();
			// smsParam.getParamMap().put("code", "1234");
			helper.publishEvent(smsParam);
		}
		System.out.println(System.currentTimeMillis() - ti);

	}

}
