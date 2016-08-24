package com.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;

import common.utils.MD5;

/**
 * 测试
 * @author my
 *
 */
public class SMSUtil {
	
	public static int sendSMS_ChinaNet1(String mobile,String content) throws Exception{
		
		Map<String, String> paramentMap = new LinkedHashMap<String, String>();
        paramentMap.put("username", "shangpin666");//用户名
		String strtime = new SimpleDateFormat("yyyyMMddHHmmss").format(System.currentTimeMillis());
        String pass = MD5.encode(MD5.encode("dbWwq5FX")+strtime);
		paramentMap.put("tkey",  strtime);
        paramentMap.put("password", pass);//加密后密码
        paramentMap.put("productid", "123123");//产品id
        paramentMap.put("mobile", mobile);//号码
        paramentMap.put("content",  content);//内容
        paramentMap.put("xh",  "");
        
        String status = "";
		//http://www.ztsms.cn:8800/sendManyNSms.do  多号码，多内容
		//http://www.ztsms.cn:8800/sendNSms.do      同内容，不同号码
        status = sendHttpRequest16("http://www.ztsms.cn/sendNSms.do", paramentMap, "UTF-8", "POST");
		System.out.println(status);
		return 0;
		
	}
	
	public static String sendHttpRequest16(String strUrl, Map<String, String> paramentMap, String anaycle, String presendway) {
		StringBuffer sb = new StringBuffer();
		try {
			URL url = new URL(strUrl);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(true);
			httpConn.setRequestMethod(presendway);
			httpConn.setConnectTimeout(60000);
			httpConn.setReadTimeout(60000);

			StringBuffer parament = new StringBuffer();
			for (Map.Entry entry : paramentMap.entrySet()) {
				if (("".equals(entry.getKey())) || (entry.getKey() == null)) {
					parament.append(URLEncoder.encode((String) entry.getValue(), anaycle) + "&");
				} else if ((((String) entry.getKey()).equalsIgnoreCase("MOBILE")) || (((String) entry.getKey()).equalsIgnoreCase("phone")) || 
						(((String) entry.getKey()).equalsIgnoreCase("mb")) || (((String) entry.getKey()).equalsIgnoreCase("tele"))) {
					if (((String) entry.getValue()).endsWith(",")) {// 手机号以,结尾
						parament.append((String) entry.getKey() + "=" + ((String) entry.getValue()).substring(0, ((String) entry.getValue()).length() - 1) + "&");
					} else {
						parament.append((String) entry.getKey() + "=" + ((String) entry.getValue()) + "&");
					}
				} else if ((((String) entry.getKey()).equalsIgnoreCase("Content")) || (((String) entry.getKey()).equalsIgnoreCase("Message")) || (((String) entry.getKey()).equalsIgnoreCase("ms"))
						|| (((String) entry.getKey()).equalsIgnoreCase("msg_content")) || (((String) entry.getKey()).equalsIgnoreCase("msg")) || (((String) entry.getKey()).equalsIgnoreCase("sms"))
						|| (((String) entry.getKey()).equalsIgnoreCase("smscontent"))|| (((String) entry.getKey()).equalsIgnoreCase("smsg")))
					parament.append((String) entry.getKey() + "=" + URLEncoder.encode((String) entry.getValue(), anaycle) + "&");
				else {
					parament.append((String) entry.getKey() + "=" + (String) entry.getValue() + "&");
				}
			}
			//log.info(url+"?"+parament.toString());
			if (("".equals(parament.toString())) || (parament.toString() == null))
				return "9999";
			if (parament.toString().endsWith("&")) {
				parament.setLength(parament.length() - 1);
			}

			//httpConn.setRequestProperty("Content-Length", String.valueOf(parament.toString().getBytes().length));
			httpConn.setRequestProperty("Content-Length", String.valueOf(parament.toString().getBytes().length));

			httpConn.getOutputStream().write(parament.toString().getBytes());
			httpConn.getOutputStream().flush();
			httpConn.getOutputStream().close();
			InputStream is = httpConn.getInputStream();
			BufferedReader bf = new BufferedReader(new InputStreamReader(is,anaycle));
			String tr = "";
			while ((tr = bf.readLine()) != null) {
				sb.append("\n").append(tr);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString().replaceFirst("\n", "");
	}
	
}
