package test2;

import java.util.HashMap;
import java.util.Map;

/**
 * 短信关键数据.
 * 
 * @author JIM
 *
 */
public class SmsParam {

	/**
	 * 模板参数
	 */
	private Map<String, String> paramMap = new HashMap<String, String>();

	/**
	 * 接收人手机号
	 */
	private String receiverMobile;

	/**
	 * 短信模版
	 */
	private String msgTemplate;

	public String getReceiverMobile() {
		return receiverMobile;
	}

	public void setReceiverMobile(String receiverMobile) {
		this.receiverMobile = receiverMobile;
	}

	public String getMsgTemplate() {
		return msgTemplate;
	}

	public void setMsgTemplate(String msgTemplate) {
		this.msgTemplate = msgTemplate;
	}

	public Map<String, String> getParamMap() {
		return paramMap;
	}

	public void setParamMap(Map<String, String> paramMap) {
		this.paramMap = paramMap;
	}

	@Override
	public String toString() {
		return "SmsParam [paramMap=" + paramMap + ", receiverMobile=" + receiverMobile + ", msgTemplate=" + msgTemplate
				+ "]";
	}

}