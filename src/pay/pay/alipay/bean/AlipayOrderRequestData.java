package pay.alipay.bean;

/**
 * 支付宝下单请求数据
 */
public class AlipayOrderRequestData {
	//参数	类型	是否必填	最大长度	描述	示例值
	/********************************公共参数*************************************/
	//app_id	String	是	32	支付宝分配给开发者的应用ID	2014072300007148
	private String app_id;
	//method	String	是	128	接口名称	alipay.trade.app.pay
	private String method;
	//format	String	否	40	仅支持JSON	JSON
	private String format;
	//charset	String	是	10	请求使用的编码格式，如utf-8,gbk,gb2312等	utf-8
	private String charset;
	//sign_type	String	是	10	商户生成签名字符串所使用的签名算法类型，目前支持RSA	RSA
	private String sign_type;
	//sign	String	是	256	商户请求参数的签名串，详见签名	详见示例
	private String sign;
	//timestamp	String	是	19	发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"	2014-07-24 03:07:50
	private String timestamp;
	//version	String	是	3	调用的接口版本，固定为：1.0	1.0
	private String version;
	//notify_url	String	是	256	支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https	https://api.xx.com/receive_notify.htm
	private String notify_url;
	//biz_content	String	是	-	业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档	 
	private String biz_content;
	
	/********************************业务参数*************************************/
	//body	String	否	128	对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。	Iphone6 16G
	private String body;
	//subject	String	是	256	商品的标题/交易标题/订单标题/订单关键字等。	大乐透
	private String subject;
	//out_trade_no	String	是	64	商户网站唯一订单号	70501111111S001111119
	private String out_trade_no;
	//timeout_express	String	否	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。	90m
	private String timeout_express;		
	//total_amount	String	是	9	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]	9.00
	private String total_amount;		
	//seller_id	String	否	16	收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID	2088102147948060
	private String seller_id;		
	//product_code	String	是	64	销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY	QUICK_MSECURITY_PAY
	private String product_code;
	
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getCharset() {
		return charset;
	}
	public void setCharset(String charset) {
		this.charset = charset;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getNotify_url() {
		return notify_url;
	}
	public void setNotify_url(String notify_url) {
		this.notify_url = notify_url;
	}
	public String getBiz_content() {
		return biz_content;
	}
	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getOut_trade_no() {
		return out_trade_no;
	}
	public void setOut_trade_no(String out_trade_no) {
		this.out_trade_no = out_trade_no;
	}
	public String getTimeout_express() {
		return timeout_express;
	}
	public void setTimeout_express(String timeout_express) {
		this.timeout_express = timeout_express;
	}
	public String getTotal_amount() {
		return total_amount;
	}
	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}
	public String getSeller_id() {
		return seller_id;
	}
	public void setSeller_id(String seller_id) {
		this.seller_id = seller_id;
	}
	public String getProduct_code() {
		return product_code;
	}
	public void setProduct_code(String product_code) {
		this.product_code = product_code;
	}
	
}
