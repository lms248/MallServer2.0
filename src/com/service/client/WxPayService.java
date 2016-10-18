package service.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pay.common.PayWay;
import pay.wx.bean.GetBrandWCPayRequestData;
import pay.wx.bean.MatrixToImageWriter;
import pay.wx.bean.OrderQueryRequestData;
import pay.wx.bean.OrderQueryResponseData;
import pay.wx.bean.UnifiedOrderNotifyRequestData;
import pay.wx.bean.UnifiedOrderNotifyResponseData;
import pay.wx.bean.UnifiedOrderRequestData;
import pay.wx.bean.UnifiedOrderResponseData;
import pay.wx.bean.WxPayRequestData;
import pay.wx.config.WxPayConfig;
import pay.wx.util.Util;
import pay.wx.util.WxPayUtil;
import bean.client.GoodsBean;
import bean.client.OrdersBean;
import bean.client.PayBean;
import bean.client.UserBean;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.thoughtworks.xstream.XStream;

import common.logger.Logger;
import common.utils.Def;
import common.utils.IdGen;
import dao.client.GoodsDao;
import dao.client.OrdersDao;
import dao.client.PayDao;
import dao.client.UserDao;

/**
 * 支付相关逻辑控制器
 */
@Controller
@RequestMapping("/pay/wx")
public class WxPayService {
	
	private static String SUCCESS = "SUCCESS";
	private static String FAIL = "FAIL";
	private static String OK = "OK";
	
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
		
		JSONObject obj = new JSONObject();
		
		log.debug("开始APP下单方法...");
		//1、接收业务参数，生成本地系统订单
		String token = request.getParameter("token");
		String orderIds = request.getParameter("orderIds");
		System.out.println("orderIds===="+orderIds);
		
		UserBean user = UserDao.loadByToken(token);
		if (user == null) {
			obj.put("code", Def.CODE_FAIL);
			obj.put("msg", "用户不存在");
			out.print(obj);
			
			out.flush();
			out.close();
			return;
		}
		
		String payId = IdGen.get().nextId()+"";
		JSONArray orderIdArr = JSON.parseArray(orderIds);
		int total_fee = 0;
		String body = "";
		for (int i = 0; i < orderIdArr.size(); i++) {
			String orderId = orderIdArr.getString(i);
			OrdersBean order = OrdersDao.loadByOrderId(orderId);
			JSONArray goodsArr = JSON.parseArray(order.getGoodsList());
			for (int j = 0; j < goodsArr.size(); j++) {
				JSONObject goodsObj = JSONObject.fromObject(goodsArr.get(j));
				GoodsBean goods = GoodsDao.loadByGoodsId(goodsObj.getString("goodsId"));
				total_fee += goods.getCurPrice()*goodsObj.getInt("amount")*100;
				if (body.equals("")) {
					body = goods.getName();
				} else {
					body += ";"+goods.getName();
				}
			}
			OrdersBean order2 = OrdersDao.loadByOrderId(orderId);
			order2.setPayId(payId);
			OrdersDao.update(order2);
		}
		
		
		Map<String, String> paramMap = new HashMap<>();
		paramMap.put("payId", payId);
		paramMap.put("body", body);
		paramMap.put("total_fee", total_fee+"");
		
		//2、添加本地订单记录
		PayBean pay = new PayBean();
		pay.setPayId(payId);
		pay.setPayWay(PayWay.WECHAT.toString());
		pay.setTotal_fee(total_fee);
		pay.setFee_type(Def.CNY);
		pay.setBody(body);
		pay.setStatus(Def.PAY_STATUS_NO);
		pay.setCreateTime(System.currentTimeMillis());
		PayDao.save(pay);
		
		//3、调用统一下单接口
		UnifiedOrderResponseData responseData = unifiedOrder("APP", paramMap);
		
		//4、生成可用数据
		JSONObject outObj = getJSONObject(responseData);
		
		//5、返回处理结果
		out.print(outObj);
		System.out.println(outObj);
		/*JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "微信支付APP下单");
		obj.put("data", outObj);
		out.print(obj);*/
		
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
		Map<String, String> paramMap = (Map<String, String>) object;
		log.debug("开始调用微信统一下单方法...");
		//1、生成请求数据对象
		UnifiedOrderRequestData data = new UnifiedOrderRequestData();
		data.setAppid(WxPayConfig.appid);
		data.setMch_id(WxPayConfig.mchId);
		data.setNonce_str(Util.createRandom(false, 16));
		data.setBody(paramMap.get("body"));
		data.setOut_trade_no(paramMap.get("payId"));//本地系统订单号
		data.setTotal_fee(Integer.parseInt(paramMap.get("total_fee")));
		data.setSpbill_create_ip("127.0.0.1");
		data.setNotify_url(WxPayConfig.notifyUrl);
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
		JSONObject outObj = new JSONObject();
		if (responseData.getReturn_code() != null && responseData.getReturn_code().equals(SUCCESS)) {
			if (responseData.getResult_code() != null && responseData.getResult_code().equals(SUCCESS)) {
				//将数据封装成APP需要的形式返回前台
				//appId 是 String(16) wx8888888888888888 商户注册具有支付权限的公众号成功后即可获得 
				String appid = WxPayConfig.appid;
				//时间戳 timeStamp 是 String(32) 1414561699 当前的时间，其他详见时间戳规则 
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddHHmmss");
				String timestamp = simpleDateFormat.format(new Date());
				//随机字符串 noncestr 是 String(32) 5K8264ILTKCH16CQ2502SI8ZNMTM67VS 随机字符串，不长于32位。推荐随机数生成算法 
				String noncestr = Util.createRandom(false, 16);
				//订单详情扩展字符串 package 是 String(128) 暂填写固定值Sign=WXPay
				String packagestr = "Sign=WXPay";
				//预支付交易会话标识 prepay_id 是 String(64) wx201410272009395522657a690389285100 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时 
				String prepayid = obj.getString("prepay_id");
				
				WxPayRequestData data = new WxPayRequestData(appid, WxPayConfig.mchId, prepayid, packagestr, noncestr, timestamp);
				data.setSign(WxPayUtil.getSign(data));
				
				outObj.put("code", Def.CODE_SUCCESS);
				outObj.put("msg", responseData.getReturn_msg());
				outObj.put("data", JSONObject.fromObject(data));
			}
		} else {
			outObj.put("code", Def.CODE_FAIL);
			outObj.put("msg", responseData.getReturn_msg());
		}
		log.debug("所需json数据  => " + obj.toString());
		log.debug("结束使用统一下单接口返回数据生成所需json...");
		return outObj;
	}
	
	/**
	 * TODO:根据统一下单接口返回的数据，生成前台APP所需的数据包
	 * @param responseData
	 * @return JSONObject
	 * @deprecated
	 */
	public JSONObject getJSONObject2(UnifiedOrderResponseData responseData){
		log.debug("开始使用统一下单接口返回数据生成所需json...");
		JSONObject json = new JSONObject();
		String responseSign = WxPayUtil.getSign(responseData);
		if (!responseSign.equals(responseData.getSign())) {
			json.put("code", "1");
			json.put("msg", "签名错误！");
		}else {
			if (responseData.getReturn_code() != null && responseData.getReturn_code().equals(SUCCESS)) {
				if (responseData.getResult_code() != null && responseData.getResult_code().equals(SUCCESS)) {
					//将数据封装成JS-SDK需要的形式返回前台
					//appId 是 String(16) wx8888888888888888 商户注册具有支付权限的公众号成功后即可获得 
					String appId = WxPayConfig.appid;
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
	
	/**
	 * TODO:根据统一下单返回结果生成二维码
	 * @param responseData
	 * @param response
	 * return:void
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void getQRCode(UnifiedOrderResponseData responseData,HttpServletResponse response) {
		log.debug("开始根据统一下单返回结果生成二维码...");
		log.debug("code_url => " + responseData.getCode_url());
		response.setContentType("image/jpeg");
		try {
		     MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
		     Map hints = new HashMap();
		     hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		     BitMatrix bitMatrix = multiFormatWriter.encode(responseData.getCode_url(),BarcodeFormat.QR_CODE,400,400,hints);
		     MatrixToImageWriter.writeToStream(bitMatrix, "jpg", response.getOutputStream());
		 } catch (Exception e) {
		     e.printStackTrace();
		 }
		log.debug("结束根据统一下单返回结果生成二维码...");
	}
	/**
	 * 
	 * TODO:接收微信服务器の支付结果回调
	 * @param request
	 * @param response
	 * return:void
	 */
	@RequestMapping(value ="wxPayResult",method=RequestMethod.POST)
	@ResponseBody
	public void getPayResult(HttpServletRequest request,HttpServletResponse response){
		log.debug("开始接收支付结果通知（回调）...");
		UnifiedOrderNotifyResponseData responseData = new UnifiedOrderNotifyResponseData();
		//1、获取回调数据
		BufferedReader br;
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			while((line = br.readLine())!=null){
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
			responseData.setReturnCode(FAIL);
			responseData.setReturnMsg("invalid data block");
		}
		log.debug("received string in notifyRequest => " + sb.toString());
		//2、把接收到的数据转换成UnifiedOrderNotifyRequestData对象
		UnifiedOrderNotifyRequestData payResult = WxPayUtil.castXMLStringToUnifiedOrderNotifyRequestData(sb.toString());
		log.debug("UnifiedOrderNotifyRequestData => " + JSONObject.fromObject(payResult).toString());
		System.out.println("#payResult.getResult_code()===="+payResult.getResult_code());
		String responseSign = WxPayUtil.getSign(payResult);
		if (responseSign != null && responseSign.equals(payResult.getSign())) {
			if (	payResult.getReturn_code() != null &&
					payResult.getResult_code() != null &&
					payResult.getBank_type() != null &&
					payResult.getOut_trade_no() != null &&
					payResult.getTotal_fee() != null &&
					payResult.getTransaction_id() != null
					) {
				responseData.setReturnCode(SUCCESS);
				responseData.setReturnMsg(OK);
				//根据out_trade_no获取订单信息
				
				//更新本地订单信息
				PayBean pay = PayDao.loadByPayId(payResult.getOut_trade_no());
				System.out.println("payResult.getOut_trade_no()===="+payResult.getOut_trade_no());
				System.out.println("pay===="+pay);
				System.out.println("##payResult.getResult_code()===="+payResult.getResult_code());
				pay.setResult_code(payResult.getResult_code());
				
				//判断本地订单支付状态
				//如果支付成功，直接返回
				//如果未支付成功，根据返回信息修改支付状态
				if (payResult.getResult_code().equals(SUCCESS)) {
					pay.setStatus(Def.PAY_STATUS_YES);
					List<OrdersBean> orderList = OrdersDao.loadByPayId(payResult.getOut_trade_no());
					for (OrdersBean order : orderList) {
						order.setStatus(Def.ORDER_STATUS_NOTDELIVER);
						OrdersDao.update(order);
					}
				}else {
					//本地订单状态修改为支付错误
					pay.setErr_code(payResult.getErr_code());
					pay.setErr_code_des(payResult.getErr_code_des());
					pay.setStatus(Def.PAY_STATUS_NO);
				}
				pay.setPayTime(System.currentTimeMillis());
				PayDao.update(pay);
				
			}else {
				responseData.setReturnCode(FAIL);
				responseData.setReturnMsg("important param is null");
			}
		}else {
			responseData.setReturnCode(FAIL);
			responseData.setReturnMsg("invalid sign");
		}
		//3、将要返回的数据转为XML字符串
		XStream xStream = new XStream();
		xStream.alias("xml", UnifiedOrderNotifyResponseData.class);
		String responseString = xStream.toXML(responseData);
		responseString = responseString.replaceAll("__", "_");
		log.debug("UnifiedOrderNotifyResponseData => " + responseString);
		//4、返回处理结果
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.print(responseString);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		log.debug("结束接收支付结果通知（回调）...");
	}
	
	/**
	 * TODO:调用微信查询订单接口
	 * @param request
	 * @param response
	 * return:void
	 */
//	@RequestMapping("order/wechatWxPayQueryOrder.php")
	public void queryOrder(HttpServletRequest request,HttpServletResponse response){
		log.debug("开始调用微信订单查询接口...");
		Map<String, String> result = new HashMap<String, String>();
		//0、获取参数
		String out_trade_no = request.getParameter("outTradeNo");
		out_trade_no = (String) request.getSession().getAttribute("measureNumber");
		String transaction_id = request.getParameter("transaction_id");
		//1、生成查询订单的请求数据对象
		OrderQueryRequestData orderQueryRequestData = new OrderQueryRequestData();
		orderQueryRequestData.setAppid(WxPayConfig.appid);
		orderQueryRequestData.setMch_id(WxPayConfig.mchId);
		//优先使用out_trade_no进行订单查询
		if (out_trade_no != null && out_trade_no.length() != 0) {
			orderQueryRequestData.setOut_trade_no(out_trade_no);
		}else {
			orderQueryRequestData.setTransaction_id(transaction_id);
		}
		orderQueryRequestData.setNonce_str(Util.createRandom(false, 16));
		orderQueryRequestData.setSign(WxPayUtil.getSign(orderQueryRequestData));
		log.debug("orderQueryRequestData => " + JSONObject.fromObject(orderQueryRequestData).toString());
		//2、调用查询订单接口
		OrderQueryResponseData orderQueryResponseData = WxPayUtil.queryOrder(orderQueryRequestData);
		log.debug("orderQueryResponseData => " + JSONObject.fromObject(orderQueryResponseData).toString());
		//3、根据接口返回结果
		//判断returnCode
		if (orderQueryResponseData.getReturn_code() != null && orderQueryResponseData.getReturn_code().equals(SUCCESS)) {
			//判断resultCode
			if (orderQueryResponseData.getResult_code() != null && orderQueryResponseData.getResult_code().equals(SUCCESS)) {
				//判断签名
				String returnedSign = orderQueryResponseData.getSign();
				if (returnedSign != null && returnedSign.equals(WxPayUtil.getSign(orderQueryResponseData))) {
					String tradeState = orderQueryResponseData.getTrade_state();
					if (tradeState != null && tradeState.equals(SUCCESS)) {
						result.put("code", "0");
//						result.put("msg", "订单支付成功！");
					}else if (tradeState == null) {
						result.put("code", "4");
//						result.put("msg", "获取订单状态失败！");
					}else if(tradeState.equals("REFUND")){
						result.put("code", "5");
//						result.put("msg", "转入退款！");
					}else if(tradeState.equals("NOTPAY")) {
						result.put("code", "6");
//						result.put("msg", "未支付！");
					}else if(tradeState.equals("CLOSED")) {
						result.put("code", "7");
//						result.put("msg", "已关闭！");
					}else if(tradeState.equals("REVOKED")) {
						result.put("code", "8");
//						result.put("msg", "已撤销（刷卡支付）！");
					}else if(tradeState.equals("USERPAYING")) {
						result.put("code", "9");
//						result.put("msg", "用户支付中！");
					}else if (tradeState.equals("PAYERROR")) {
						result.put("code", "10");
//						result.put("msg", "支付失败！");
					}else{
						result.put("code", "11");
//						result.put("msg", "未知的失败状态！");
					}
					//根据out_trade_no查询本地订单，判断本地订单支付状态
					//如果支付成功，直接返回
					
					//如果未支付成功，根据返回信息修改支付状态
					
				}else {
					result.put("code", "3");
//					result.put("msg", "签名错误！");
				}
			}else {
				result.put("code", "2");
//				result.put("msg", orderQueryResponseData.getErr_code()+"="+orderQueryResponseData.getErr_code_des());
			}
		}else {
			result.put("code", "1");
//			result.put("msg", orderQueryResponseData.getReturn_msg());
		}
		//4、返回处理结果
		PrintWriter out = null;
		try {
			JSONObject json = JSONObject.fromObject(result);
			log.debug("orderQueryResult =>" + json.toString());
			out = response.getWriter();
			out.print(json.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		log.debug("结束调用微信订单查询接口...");
	}
	
}
