package pay.wx.config;

public class Configuration {

	/** 微信公众平台appid */
	public static String appid = "wxd842e65051017e61";
	/** 微信商户平台商户ID */
	public static String mchId = "1395251602";
	/** 应用密钥AppSecret */
	public static String AppSecret = "873eb6899f5ba9f621fcf51e26e0ccf7";
	// 微信商户平台api密钥
	public static String wechatWxpayKey = "abcdefghijklmnopqrstuvwxyzqwerty";
	//微信商户平台支付结果通知URL
	public static String notifyUrl = "http://test.zishu15.com/web/order/wechatWxPayGetPayResult.htm";
	/** 统一下单URL */
	public static String wechatUnifiedOrderURL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
	/** 查询订单URL */
	public static String wechatOrderQueryURL = "https://api.mch.weixin.qq.com/pay/orderquery";
	
}
