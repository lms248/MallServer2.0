package service.client;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pay.wx.bean.GetBrandWCPayRequestData;
import pay.wx.bean.UnifiedOrderRequestData;
import pay.wx.bean.UnifiedOrderResponseData;
import pay.wx.config.Configuration;
import pay.wx.util.Util;
import pay.wx.util.WxPayUtil;
import common.logger.Logger;
import common.utils.Def;
import common.utils.IdGen;

/**
 * 支付相关逻辑控制器
 */
@Controller
@RequestMapping("/pay/wx")
public class WxPayService {
	
	private static Logger log = common.logger.LoggerManager.getLogger(WxPayService.class);
	
	/** APP下单 */
	@RequestMapping(value ="appOrder",method=RequestMethod.POST)
	@ResponseBody
	public void appOrder(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		log.debug("开始APP下单方法...");
		//1、接收业务参数，生成本地系统订单
		String token = request.getParameter("token");
		String payId = request.getParameter("payId");
		
		
		//2、调用统一下单接口
		UnifiedOrderResponseData responseData = unifiedOrder("APP", new Object());
		
		//3、生成可用数据
		JSONObject outObj = getJSONObject(responseData);
		
		//4、返回处理结果
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "微信支付APP下单");
		obj.put("data", outObj);
		out.print(obj);
		
		System.out.println(obj);
		
		log.debug("结束APP下单方法...");
		
		out.flush();
		out.close();
	}
	
	/**
	 * TODO:调用微信统一下单接口
	 * @param payOrder
	 * @return UnifiedOrderResponseData
	 */
	public UnifiedOrderResponseData unifiedOrder(String tradeType,Object object){
		log.debug("开始调用微信统一下单方法...");
		//1、生成请求数据对象
		UnifiedOrderRequestData data = new UnifiedOrderRequestData();
		data.setAppid(Configuration.appid);
		data.setMch_id(Configuration.mchId);
		data.setNonce_str(Util.createRandom(false, 16));
		data.setBody("the body");
		data.setOut_trade_no(IdGen.get().nextId()+"");//本地系统订单号
		data.setTotal_fee(1);
		data.setSpbill_create_ip("127.0.0.1");
		data.setNotify_url(Configuration.notifyUrl);
		data.setTrade_type(tradeType);
		data.setSign(WxPayUtil.getSign(data));
		//2、调用统一下单接口
		log.debug("UnifiedOrderRequestData => " + JSONObject.fromObject(data).toString());
		UnifiedOrderResponseData responseData = WxPayUtil.unifiedOder(data);
		log.debug("UnifiedOrderResponseData => " + JSONObject.fromObject(responseData).toString());
		//3、根据统一下单接口返回数据修改本地订单信息
		
		log.debug("结束调用微信统一下单方法...");
		return responseData;
	}
	
	/**
	 * TODO:根据统一下单接口返回的数据，生成前台APP所需的数据包
	 * @param responseData
	 * @return JSONObject
	 */
	public JSONObject getJSONObject(UnifiedOrderResponseData responseData){
		log.debug("开始使用统一下单接口返回数据生成所需json...");
		JSONObject obj = JSONObject.fromObject(responseData);
		if (responseData.getReturn_code() != null && responseData.getReturn_code().equals("SUCCESS")) {
			if (responseData.getResult_code() != null && responseData.getResult_code().equals("SUCCESS")) {
				//时间戳 timeStamp 是 String(32) 1414561699 当前的时间，其他详见时间戳规则 
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
				String timeStamp = simpleDateFormat.format(new Date());
				obj.put("timeStamp", timeStamp);
				//订单详情扩展字符串 package 是 String(128) prepay_id=123456789 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=*** 
				String _pakage = "prepay_id=" + responseData.getPrepay_id();
				obj.put("pakage", _pakage);
			}
		}
		log.debug("所需json数据  => " + obj.toString());
		log.debug("结束使用统一下单接口返回数据生成所需json...");
		return obj;
	}
	
	/**
	 * TODO:根据统一下单接口返回的数据，生成前台APP所需的数据包
	 * @param responseData
	 * @return JSONObject
	 */
	public JSONObject getJSONObject2(UnifiedOrderResponseData responseData){
		log.debug("开始使用统一下单接口返回数据生成所需json...");
		JSONObject json = new JSONObject();
		String responseSign = WxPayUtil.getSign(responseData);
		if (!responseSign.equals(responseData.getSign())) {
			json.put("code", "1");
			json.put("msg", "签名错误！");
		}else {
			if (responseData.getReturn_code() != null && responseData.getReturn_code().equals("SUCCESS")) {
				if (responseData.getResult_code() != null && responseData.getResult_code().equals("SUCCESS")) {
					//将数据封装成JS-SDK需要的形式返回前台
					//appId 是 String(16) wx8888888888888888 商户注册具有支付权限的公众号成功后即可获得 
					String appId = Configuration.appid;
					//时间戳 timeStamp 是 String(32) 1414561699 当前的时间，其他详见时间戳规则 
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
					String timeStamp = simpleDateFormat.format(new Date());
					//随机字符串 nonceStr 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS 随机字符串，不长于32位。推荐随机数生成算法 
					String nonceStr = Util.createRandom(false, 16);
					//订单详情扩展字符串 package 是 String(128) prepay_id=123456789 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=*** 
					String pcakage = "prepay_id=" + responseData.getPrepay_id();
					//签名方式 signType 是 String(32) MD5 签名算法，暂支持MD5 
					String signType = "MD5";
					//签名 paySign 是 String(64) C380BEC2BFD727A4B6845133519F3AD6 签名，详见签名生成算法 
					GetBrandWCPayRequestData getBrandWCPayRequestData = new GetBrandWCPayRequestData(appId, timeStamp, nonceStr, pcakage, signType);
					getBrandWCPayRequestData.setSignType(WxPayUtil.getSign(getBrandWCPayRequestData));
					json = JSONObject.fromObject(getBrandWCPayRequestData);
				}else {
					//result_code 为 FAIL
					json.put("code", "3");
					json.put("msg",responseData.getErr_code()+":"+responseData.getErr_code_des());
				}
			}else {
				//return_code 为 FAIL
				json.put("code", "2");
				json.put("msg", responseData.getReturn_msg());
			}
		}
		log.debug("所需json数据  => " + json.toString());
		log.debug("结束使用统一下单接口返回数据生成所需json...");
		return json;
	}
}
