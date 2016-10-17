package pay.wx.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.XmlFriendlyNameCoder;

import pay.wx.bean.OrderQueryRequestData;
import pay.wx.bean.OrderQueryResponseData;
import pay.wx.bean.UnifiedOrderNotifyRequestData;
import pay.wx.bean.UnifiedOrderRequestData;
import pay.wx.bean.UnifiedOrderResponseData;
import pay.wx.config.WxPayConfig;
import common.logger.Logger;
import common.logger.LoggerManager;

public class WxPayUtil {
	
	private static Logger logger = LoggerManager.getLogger(WxPayUtil.class);

	/**
	 * TODO:调用统一下单接口
	 * @param data
	 * @return
	 * return:UnifiedOrderResponseData
	 */
	public static UnifiedOrderResponseData unifiedOder(UnifiedOrderRequestData data){
		String requestXMLData = WxPayUtil.castDataToXMLString(data);
		String requestUrl = WxPayConfig.wechatUnifiedOrderURL;
		String requestMethod = "POST";
		String responseString = Util.httpsRequest(requestUrl, requestMethod, requestXMLData);
		UnifiedOrderResponseData responseData = WxPayUtil.castXMLStringToUnifiedOrderResponseData(responseString);
		return responseData;
	}
	
	/**
	 * TODO:调用订单查询接口
	 * @param data
	 * @return
	 * return:OrderQueryResponseData
	 */
	public static OrderQueryResponseData queryOrder(OrderQueryRequestData data){
		String requestXMLData = WxPayUtil.castDataToXMLString(data);
		String requestUrl = WxPayConfig.wechatOrderQueryURL;
		String requestMethod = "POST";
		String responseString = Util.httpsRequest(requestUrl, requestMethod, requestXMLData);
		OrderQueryResponseData responseData = WxPayUtil.castXMLStringToOrderQueryResponseData(responseString);
		return responseData;
	}
	
	/**
	 * TODO:将java对象转换为XML字符串
	 * @param object
	 * @return
	 * return:String
	 */
	private static String castDataToXMLString(Object object){
		//解决XStream对出现双下划线的bug
        XStream xStreamForRequestPostData = new XStream(new DomDriver("UTF-8", new XmlFriendlyNameCoder("-_", "_")));
        //将要提交给API的数据对象转换成XML格式数据Post给API
        return xStreamForRequestPostData.toXML(object);
	}
	
	/**
	 * TODO:把XML字符串转换为统一下单接口返回数据对象
	 * @param responseString
	 * @return
	 * return:UnifiedOrderResponseData
	 */
	private static UnifiedOrderResponseData castXMLStringToUnifiedOrderResponseData(String responseString){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("xml", UnifiedOrderResponseData.class);
		return (UnifiedOrderResponseData) xStream.fromXML(responseString);
	}
	
	/**
	 * TODO:把XML字符串转换为统一下单回调接口请求数据对象
	 * @param requestString
	 * @return
	 * return:UnifiedOrderNotifyRequestData
	 */
	public static UnifiedOrderNotifyRequestData castXMLStringToUnifiedOrderNotifyRequestData(String requestString){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("xml", UnifiedOrderNotifyRequestData.class);
		return (UnifiedOrderNotifyRequestData) xStream.fromXML(requestString);
	}
	
	/**
	 * TODO:把XML字符串转换为订单查询接口返回数据对象
	 * @param responseString
	 * @return
	 * return:OrderQueryResponseData
	 */
	public static OrderQueryResponseData castXMLStringToOrderQueryResponseData(String responseString){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("xml", OrderQueryResponseData.class);
		return (OrderQueryResponseData) xStream.fromXML(responseString);
	}
	
	/**
	 * TODO:利用反射获取Java对象的字段并进行加密，过滤掉sign字段
	 * @param data
	 * @return
	 * return:String
	 */
	public static String getSign(Object data) {
		StringBuffer stringA = new StringBuffer();
		Map<String, String> map = new HashMap<String, String>();
		Field[] fields = data.getClass().getDeclaredFields();
		Method[] methods = data.getClass().getDeclaredMethods();
		
		for (Field field : fields) {
			String fieldName = field.getName();
			if (fieldName.substring(0,1).equals("_")) {
				fieldName = fieldName.substring(1);
			}
			if (field != null && !fieldName.equals("sign")) {
				String getMethodName = "get" + fieldName.substring(0,1).toUpperCase() + fieldName.substring(1);
				for (Method method : methods) {
					if (method.getName().equals(getMethodName)) {
						try {
							if (method.invoke(data, new Object[]{}) != null && method.invoke(data, new Object[]{}).toString().length() != 0) {
								map.put(fieldName, method.invoke(data, new Object[]{}).toString());
							}
						} catch (IllegalAccessException | IllegalArgumentException
								| InvocationTargetException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
		List<Map.Entry<String,String>> mappingList = null; 
    	//通过ArrayList构造函数把map.entrySet()转换成list 
    	mappingList = new ArrayList<Map.Entry<String,String>>(map.entrySet()); 
    	//通过比较器实现比较排序 
    	Collections.sort(
			mappingList, 
			new Comparator<Map.Entry<String,String>>(){ 
				public int compare(Map.Entry<String,String> mapping1,Map.Entry<String,String> mapping2){ 
					return mapping1.getKey().compareTo(mapping2.getKey()); 
				} 
	  		}
    	);
		for (Map.Entry<String, String> mapping : mappingList) {
			logger.debug(mapping.getKey() + "=> " + mapping.getValue());
			stringA.append("&"+mapping.getKey()+"="+mapping.getValue());
		}
		String stringSignTemp = stringA.toString().substring(1) + "&key=" + WxPayConfig.wxpayKey;
		logger.debug("stringA => " + stringA.toString().substring(1));
		logger.debug("stringSignTemp => " + stringSignTemp);
		logger.debug("sign => " + Util.MD5(stringSignTemp).toUpperCase());
		return Util.MD5(stringSignTemp).toUpperCase();
	}

	/**
	 * TODO:主函数，没什么用的
	 * @param args
	 * return:void
	 */
	public static void main(String[] args){

//		String responseString = "<xml><return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg><appid><![CDATA[wx2421b1c4370ec43b]]></appid><mch_id><![CDATA[10000100]]></mch_id><nonce_str><![CDATA[IITRi8Iabbblz1Jc]]></nonce_str><sign><![CDATA[7921E432F65EB8ED0CE9755F0E86D72F]]></sign><result_code><![CDATA[SUCCESS]]></result_code><prepay_id><![CDATA[wx201411101639507cbf6ffd8b0779950874]]></prepay_id><trade_type><![CDATA[JSAPI]]></trade_type></xml>";
		
//		String notifyString = "<xml><appid><![CDATA[wxa724f52bb5f2adba]]></appid><bank_type><![CDATA[CFT]]></bank_type><cash_fee><![CDATA[1]]></cash_fee><fee_type><![CDATA[CNY]]></fee_type><is_subscribe><![CDATA[Y]]></is_subscribe><mch_id><![CDATA[1229355602]]></mch_id><nonce_str><![CDATA[yt530qid6am805w4]]></nonce_str><openid><![CDATA[owaDHjnHucCHBJEfYmc0exraJsZA]]></openid><out_trade_no><![CDATA[0578772477877]]></out_trade_no><result_code><![CDATA[SUCCESS]]></result_code><return_code><![CDATA[SUCCESS]]></return_code><sign><![CDATA[5577CC49C305587BD004B4AB7F9E880C]]></sign><time_end><![CDATA[20150826164911]]></time_end><total_fee>1</total_fee><trade_type><![CDATA[NATIVE]]></trade_type><transaction_id><![CDATA[1009720069201508260714072542]]></transaction_id></xml>";
		
		
		
	}
}
