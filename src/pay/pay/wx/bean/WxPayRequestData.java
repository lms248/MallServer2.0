package pay.wx.bean;
/**
 * APP端调起支付的参数列表
 */
public class WxPayRequestData {

	//应用ID appid String(32) 是 wx8888888888888888 微信开放平台审核通过的应用APPID 
	private String appid;
	//商户号 partnerid String(32) 是 1900000109 微信支付分配的商户号 
	private String partnerid;
	//预支付交易会话ID prepayid String(32) 是 WX1217752501201407033233368018 微信返回的支付交易会话ID 
	private String prepayid;
	//扩展字段 package String(128) 是 Sign=WXPay 暂填写固定值Sign=WXPay 
	private String packagestr;
	//随机字符串 noncestr String(32) 是 5K8264ILTKCH16CQ2502SI8ZNMTM67VS 随机字符串，不长于32位。推荐随机数生成算法 
	private String noncestr;
	//时间戳 timestamp String(10) 是 1412000000 时间戳，请见接口规则-参数规定 
	private String timestamp;
	//签名 sign String(32) 是 C380BEC2BFD727A4B6845133519F3AD6 签名，详见签名生成算法 
	private String sign;
	
	public WxPayRequestData(String appid, String partnerid, String prepayid,
			String packagestr, String noncestr, String timestamp) {
		super();
		this.appid = appid;
		this.partnerid = partnerid;
		this.prepayid = prepayid;
		this.packagestr = packagestr;
		this.noncestr = noncestr;
		this.timestamp = timestamp;
	}

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPartnerid() {
		return partnerid;
	}

	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}

	public String getPrepayid() {
		return prepayid;
	}

	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}

	public String getPackage() {
		return packagestr;
	}

	public void setPackage(String packagestr) {
		this.packagestr = packagestr;
	}

	public String getNoncestr() {
		return noncestr;
	}

	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
