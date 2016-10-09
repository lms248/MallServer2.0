package pay.wx;

/**
 * 微信支付常数
 */
public class WxConstant {
	
	/** 接口链接  */
	String UNIFIED_ORDER_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/** 公众账号ID */
	String appid = "wxd842e65051017e61";
	/** 商户号 */
	String mch_id = "1395251602";
	/** 设备号 */
	//String device_info = "";
	/** 随机字符串，不长于32位 */
	String nonce_str = "";
	/** 签名 */
	String sign = "";
	/** 商品描述 */
	String body = "";
	/** 商品详情 */
	String detail = "";
	/** 商户订单号 */
	String out_trade_no = "";
	/** 订单总金额，单位为分 */
	int total_fee;
	/** 终端IP */
	String spbill_create_ip = "";
	/** 通知地址：接收微信支付异步通知回调地址，通知url必须为直接可访问的url，不能携带参数。 */
	String notify_url = "";
	/** 交易类型 */
	String trade_type = "";
	/** 商品ID */
	String product_id = "";
	
}
