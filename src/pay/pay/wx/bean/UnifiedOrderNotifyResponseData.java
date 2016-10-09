package pay.wx.bean;

/**
 * 
 * TODO:商户接收回调数据并且处理后同步返回给微信的数据
 * @type_name:UnifiedOrderNotifyResponseData
 * @time:下午6:23:14
 * @author:Frankhbu
 */
public class UnifiedOrderNotifyResponseData {

	//返回状态码	return_code	是	String(16)	SUCCESS	SUCCESS/FAIL	SUCCESS表示商户接收通知成功并校验成功	
	private String return_code;
	//返回信息	return_msg	否	String(128)	OK	返回信息，如非空，为错误原因：签名失败、参数格式校验错误
	private String return_msg;
	
	public String getReturnCode() {
		return return_code;
	}
	public void setReturnCode(String return_code) {
		this.return_code = return_code;
	}
	public String getReturnMsg() {
		return return_msg;
	}
	public void setReturnMsg(String return_msg) {
		this.return_msg = return_msg;
	}
	
	
}
