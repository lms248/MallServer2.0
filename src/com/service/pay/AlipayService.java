package service.pay;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import pay.alipay.bean.AlipayOrderRequestBusinessData;
import pay.alipay.bean.AlipayOrderRequestCommonData;
import pay.alipay.config.AlipayConfig;
import pay.alipay.sign.Base64;
import pay.alipay.sign.RSA;
import pay.alipay.sign.RSA2;
import pay.alipay.util.AlipayCore;
import pay.alipay.util.AlipayNotify;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import common.logger.Logger;
import common.utils.Def;
import common.utils.IdGen;
import common.utils.MapUtils;

/**
 * 支付宝支付相关逻辑控制器
 */
@Controller
@RequestMapping("/pay/alipay")
public class AlipayService {
	
	private static String SUCCESS = "SUCCESS";
	private static String FAIL = "FAIL";
	private static String OK = "OK";
	
	private static Logger log = common.logger.LoggerManager.getLogger(AlipayService.class);
	
	/** 获取请求参数签名 */
	@RequestMapping(value ="signatures",method=RequestMethod.POST)
	@ResponseBody
	public void signatures(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException, AlipayApiException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		log.debug("开始APP下单方法...");
		//1、接收业务参数，生成本地系统订单
		String payId = IdGen.get().nextId()+"";
		
		String requestData = getOrderInfo("订单标题", "订单描述", "0.01", payId);
		
		
		//2、添加本地订单记录
		
		//3、原始订单字符串进行签名
		
		//将post接收到的数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串。需要排序。
		
	
		//将待签名字符串使用私钥签名。
		
		
		//4、生成可用数据
		
		//5、返回处理结果
		JSONObject obj = new JSONObject();
		obj.put("code", Def.CODE_SUCCESS);
		obj.put("msg", "支付宝支付APP下单");
		//返回给客户端,建议在客户端使用私钥对应的公钥做一次验签，保证不是他人传输。
		obj.put("data", requestData);
		out.print(obj);
		
		out.flush();
		out.close();
		
		log.debug("结束APP下单方法...");
	}
	
	/**
	 * 获取订单信息
	 * @param subject 商品的标题
	 * @param body 商品描述
	 * @param total_amount 订单总金额
	 * @param out_trade_no 商户网站唯一订单号
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String getOrderInfo(String subject, String body, String total_amount, String out_trade_no) throws UnsupportedEncodingException { 
		String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		//业务参数
		AlipayOrderRequestBusinessData businessData = new AlipayOrderRequestBusinessData();
		businessData.setBody(body);
		businessData.setSubject(subject);
		businessData.setOut_trade_no(out_trade_no);
		businessData.setTimeout_express("30m");
		businessData.setTotal_amount(total_amount);
		businessData.setProduct_code("QUICK_MSECURITY_PAY");
		log.debug("AlipayOrderRequestBusinessData => " + JSONObject.fromObject(businessData));
		//公共参数
		AlipayOrderRequestCommonData commonData = new AlipayOrderRequestCommonData();
		commonData.setApp_id(AlipayConfig.appid);
		commonData.setMethod("alipay.trade.app.pay");
		commonData.setFormat("JSON");
		commonData.setCharset("utf-8");
		commonData.setSign_type("RSA");
		commonData.setTimestamp(timestamp);
		commonData.setVersion("1.0");
		commonData.setNotify_url(AlipayConfig.notify_url);
		commonData.setBiz_content(JSONObject.fromObject(businessData).toString());
		log.debug("AlipayOrderRequestCommonData => " + JSONObject.fromObject(commonData));
		
		Map<String, String> requestParams = (Map<String, String>) MapUtils.beanToMap(commonData);
		String requestData = AlipayCore.createLinkString(requestParams);
		log.debug("requestData => " + requestData);
		String rsa_sign=URLEncoder.encode(RSA.sign(requestData, AlipayConfig.private_key, AlipayConfig.input_charset),AlipayConfig.input_charset);
		log.debug("rsa_sign => " + rsa_sign);
		//把签名得到的sign和签名类型sign_type拼接在待签名字符串后面。
		requestData = requestData + "&sign=" + rsa_sign;
		
		log.debug("requestData + sign => " + requestData);
		return requestData;
	}
	
	/** APP下单 */
	@RequestMapping(value ="signatures2",method=RequestMethod.POST)
	@ResponseBody
	public void signatures2(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			params.put(name, valueStr);
		}
		if(params!=null&&params.size()>0)
		{
			//partner
			String partner=request.getParameter("partner");
			AlipayCore.logResult(partner,"partner");
			//接口名
			String service=request.getParameter("service");
			//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//

			if(partner.replace("\"","").equals(AlipayConfig.partner)&& service.replace("\"","").equals(AlipayConfig.notify_url)){//确认PID和接口名称。
			
				//将post接收到的数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串。需要排序。
				String data=AlipayCore.createLinkString(params);
			
				//打印待签名字符串。工程目录下的log文件夹中。
				AlipayCore.logResult(data,"datashuju");
			
				//将待签名字符串使用私钥签名。
				String rsa_sign=URLEncoder.encode(RSA.sign(data, AlipayConfig.private_key, AlipayConfig.input_charset),AlipayConfig.input_charset);
			
				//把签名得到的sign和签名类型sign_type拼接在待签名字符串后面。
				data=data+"&sign=\""+rsa_sign+"\"&sign_type=\""+AlipayConfig.sign_type+"\"";
			
				//返回给客户端,建议在客户端使用私钥对应的公钥做一次验签，保证不是他人传输。
				out.print(data);
			}
			else
			{
				out.print("客户端信息与服务端配置信息有误！");
			}
		}
		else
		{
			out.print("无客户端信息!");
		}
		
		out.flush();
		out.close();
	}
	
	/** 通知 */
	@RequestMapping(value ="notify",method=RequestMethod.POST)
	@ResponseBody
	public void notify(HttpServletRequest request,HttpServletResponse response) 
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		PrintWriter out = response.getWriter();
		
		log.debug("开始接收支付结果通知（回调）...");
		
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map<String, String[]> requestParams = request.getParameterMap();
		for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
			params.put(name, valueStr);
		}
		
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以下仅供参考)//
		//商户订单号	
		String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//支付宝交易号	
		String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

		//交易状态
		String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"),"UTF-8");
		
		//异步通知ID
		String notify_id=request.getParameter("notify_id");
		
		//sign
		String sign=request.getParameter("sign");
		//获取支付宝的通知返回参数，可参考技术文档中页面跳转同步通知参数列表(以上仅供参考)//
		
		if(notify_id!=""&&notify_id!=null){////判断接受的post通知中有无notify_id，如果有则是异步通知。
			if(AlipayNotify.verifyResponse(notify_id).equals("true"))//判断成功之后使用getResponse方法判断是否是支付宝发来的异步通知。
			{
				if(AlipayNotify.getSignVeryfy(params, sign))//使用支付宝公钥验签
				{
					//——请根据您的业务逻辑来编写程序（以下代码仅作参考）——
					if(trade_status.equals("TRADE_FINISHED")){
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						//注意：
						//退款日期超过可退款期限后（如三个月可退款），支付宝系统发送该交易状态通知
						//请务必判断请求时的out_trade_no、total_fee、seller_id与通知时获取的out_trade_no、total_fee、seller_id为一致的
						
					} else if (trade_status.equals("TRADE_SUCCESS")){
						//判断该笔订单是否在商户网站中已经做过处理
							//如果没有做过处理，根据订单号（out_trade_no）在商户网站的订单系统中查到该笔订单的详细，并执行商户的业务程序
							//如果有做过处理，不执行商户的业务程序
						//注意：
						//付款完成后，支付宝系统发送该交易状态通知
						//请务必判断请求时的out_trade_no、total_fee、seller_id与通知时获取的out_trade_no、total_fee、seller_id为一致的
					}
					//——请根据您的业务逻辑来编写程序（以上代码仅作参考）——
					out.print("success");//请不要修改或删除
					
					//调试打印log
					//AlipayCore.logResult("notify_url success!","notify_url");
				}
				else//验证签名失败
				{
					out.print("sign fail");
				}
			}
			else//验证是否来自支付宝的通知失败
			{
				out.print("response fail");
			}
		}
		else{
			out.print("no notify message");
		}
		
		out.flush();
		out.close();
		
		log.debug("结束接收支付结果通知（回调）...");
	}
	
	
	
	
}
