package pay.alipay.config;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：1.0
 *日期：2016-06-06
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
	//APPID
	public static String appid = "2016092301956290";
	
	//合作身份者ID(PID)，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String partner = "2088421932867361";

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = 
			"MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANXK6+axcPNWohZw"+
			"e/gqSAqRjnev+UQt2NgQVAzPTM+ni/jh8wfQNd3TU9QHeh1kYLL5q6NnnsOrDQA/"+
			"ea8qXGaYQOdzTyLT1/0bJf2hRXXPl7tOc9IiGIjZnx7PjaeYMrdlkPiKR2yO5+or"+
			"mTRyC0Ec9Y+j2IPvM9YGxEQjsyhbAgMBAAECgYBLstcGTwvETYeE1H1AqI/rlhBQ"+
			"1rE279jY4CcyhfAXkqagEDkCKjn4ekwzRhZ588G9fWPFrvnrJFStIsyONjySvWF/"+
			"ahLlzYrK9sUqDFhZwFxwdba+srFPyweYWtSGglfX7IKxfLBNuiiMBIv5gCu0U3yd"+
			"GwRCHdOed/DBRNLvYQJBAO4rZa9ZP2w0KTGYYyJbp+rHSkEFq7nTlJDY0ArL3gZE"+
			"6VBxizkbGtiK4n440Lfv38kqXewPGyMaQSQVXrJznzECQQDlzFVMvn9ZjnEljisL"+
			"NxgZUy9gbCu1e6KQ0i9d1CnCqjVWz+ham54VIEvayOVrNs/Br2ipcOW2fa8DrYD/"+
			"7JVLAkEA4M6NuKmhN900VN/mEvQ4118tM5rKsSiJPv+nv8kW1QwOviOuqkRvXp4Z"+
			"k9LFEBmXPiaDaOIJV2Tcv9Z8AC0qwQJAILUkrveuSKDtmdrSZZZB59jfNWowDnS6"+
			"fsJr238HMp3nWaPd/oeHyJoAMfPGM6xphbfhvbxD9eWNs4hOhB64YQJBALZdANt8"+
			"dn2RSHnm2NOXUORFMwkJdSHthTHT1aZ6BYRpe2T2aiHuzHpIhfDHgpLsBHuiYXHD"+
			"elO6e9LOEL8QM+E=";
	//支付宝的公钥，查看地址：https://openhome.alipay.com/platform/keyManage.htm?keyType=partner
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDDI6d306Q8fIfCOaTXyiUeJHkrIvYISRcc73s3vF1ZT7XN8RNPwJxo8pWaJMmvyTn9N4HQ632qJBVHf8sxHi/fEsraprwCtzvzQETrNRwVxLO5jVmRGi60j8Ue1efIlzPXV9je9mkjzOmdssymZkh2QhUrCmZYI/FCEa3/cNMW0QIDAQAB";
	
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path ="";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";

	// 接收通知的接口名
	public static String notify_url = "http://120.24.69.203:8080/pay/alipay/notify";

}

