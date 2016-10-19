package pay.alipay.bean;

/**
 * 支付宝下单请求数据
 */
public class AlipayOrderRequestData {
	//参数	类型	是否必填	最大长度	描述	示例值
	/********************************公共参数*************************************/
	//app_id	String	是	32	支付宝分配给开发者的应用ID	2014072300007148
	private static String app_id;
	//method	String	是	128	接口名称	alipay.trade.app.pay
	private static String method;
	//format	String	否	40	仅支持JSON	JSON
	private static String format;
	//charset	String	是	10	请求使用的编码格式，如utf-8,gbk,gb2312等	utf-8
	private static String charset;
	//sign_type	String	是	10	商户生成签名字符串所使用的签名算法类型，目前支持RSA	RSA
	private static String sign_type;
	//sign	String	是	256	商户请求参数的签名串，详见签名	详见示例
	private static String sign;
	//timestamp	String	是	19	发送请求的时间，格式"yyyy-MM-dd HH:mm:ss"	2014-07-24 03:07:50
	private static String timestamp;
	//version	String	是	3	调用的接口版本，固定为：1.0	1.0
	private static String version;
	//notify_url	String	是	256	支付宝服务器主动通知商户服务器里指定的页面http/https路径。建议商户使用https	https://api.xx.com/receive_notify.htm
	private static String notify_url;
	//biz_content	String	是	-	业务请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档	 
	private static String biz_content;
	
	/********************************业务参数*************************************/
	//body	String	否	128	对一笔交易的具体描述信息。如果是多种商品，请将商品描述字符串累加传给body。	Iphone6 16G
	private static String body;
	//subject	String	是	256	商品的标题/交易标题/订单标题/订单关键字等。	大乐透
	private static String subject;
	//out_trade_no	String	是	64	商户网站唯一订单号	70501111111S001111119
	private static String out_trade_no;
	//timeout_express	String	否	6	该笔订单允许的最晚付款时间，逾期将关闭交易。取值范围：1m～15d。m-分钟，h-小时，d-天，1c-当天（1c-当天的情况下，无论交易何时创建，都在0点关闭）。 该参数数值不接受小数点， 如 1.5h，可转换为 90m。	90m
	private static String timeout_express;		
	//total_amount	String	是	9	订单总金额，单位为元，精确到小数点后两位，取值范围[0.01,100000000]	9.00
	private static String total_amount;		
	//seller_id	String	否	16	收款支付宝用户ID。 如果该值为空，则默认为商户签约账号对应的支付宝用户ID	2088102147948060
	private static String seller_id;		
	//product_code	String	是	64	销售产品码，商家和支付宝签约的产品码，为固定值QUICK_MSECURITY_PAY	QUICK_MSECURITY_PAY
	private static String product_code;
	
	public static String getApp_id() {
		return app_id;
	}
	public static void setApp_id(String app_id) {
		AlipayOrderRequestData.app_id = app_id;
	}
	public static String getMethod() {
		return method;
	}
	public static void setMethod(String method) {
		AlipayOrderRequestData.method = method;
	}
	public static String getFormat() {
		return format;
	}
	public static void setFormat(String format) {
		AlipayOrderRequestData.format = format;
	}
	public static String getCharset() {
		return charset;
	}
	public static void setCharset(String charset) {
		AlipayOrderRequestData.charset = charset;
	}
	public static String getSign_type() {
		return sign_type;
	}
	public static void setSign_type(String sign_type) {
		AlipayOrderRequestData.sign_type = sign_type;
	}
	public static String getSign() {
		return sign;
	}
	public static void setSign(String sign) {
		AlipayOrderRequestData.sign = sign;
	}
	public static String getTimestamp() {
		return timestamp;
	}
	public static void setTimestamp(String timestamp) {
		AlipayOrderRequestData.timestamp = timestamp;
	}
	public static String getVersion() {
		return version;
	}
	public static void setVersion(String version) {
		AlipayOrderRequestData.version = version;
	}
	public static String getNotify_url() {
		return notify_url;
	}
	public static void setNotify_url(String notify_url) {
		AlipayOrderRequestData.notify_url = notify_url;
	}
	public static String getBiz_content() {
		return biz_content;
	}
	public static void setBiz_content(String biz_content) {
		AlipayOrderRequestData.biz_content = biz_content;
	}
	public static String getBody() {
		return body;
	}
	public static void setBody(String body) {
		AlipayOrderRequestData.body = body;
	}
	public static String getSubject() {
		return subject;
	}
	public static void setSubject(String subject) {
		AlipayOrderRequestData.subject = subject;
	}
	public static String getOut_trade_no() {
		return out_trade_no;
	}
	public static void setOut_trade_no(String out_trade_no) {
		AlipayOrderRequestData.out_trade_no = out_trade_no;
	}
	public static String getTimeout_express() {
		return timeout_express;
	}
	public static void setTimeout_express(String timeout_express) {
		AlipayOrderRequestData.timeout_express = timeout_express;
	}
	public static String getTotal_amount() {
		return total_amount;
	}
	public static void setTotal_amount(String total_amount) {
		AlipayOrderRequestData.total_amount = total_amount;
	}
	public static String getSeller_id() {
		return seller_id;
	}
	public static void setSeller_id(String seller_id) {
		AlipayOrderRequestData.seller_id = seller_id;
	}
	public static String getProduct_code() {
		return product_code;
	}
	public static void setProduct_code(String product_code) {
		AlipayOrderRequestData.product_code = product_code;
	}	
	
}
