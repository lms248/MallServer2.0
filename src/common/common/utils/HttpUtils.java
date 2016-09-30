package common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletInputStream;

import net.sf.json.JSONObject;

/**
 * http请求工具类
 */
public class HttpUtils {
	
	public static String doGet(String url) {
		return doGet(url,null);
	}
	
	public static String doGet(String url, Map<String,String> params) {
		StringBuffer result = new StringBuffer();
		BufferedReader in = null;
		try {
			url=linkParams(url, params);
			System.out.println(url);
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			//conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("user-agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; WOW64)");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			conn.setReadTimeout(2000);
			conn.connect();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result.toString();
	}

	public static String doPost(String url, Map<String,String> params) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(url);
			URLConnection conn = realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			//conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("user-agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; WOW64)");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setReadTimeout(2000);
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
			out.print(getParamsStr(params));
			out.flush();
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}
	
	public static String doPost(String url,Map<String,String> requestProperty, String body) {
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuffer result = new StringBuffer();
		try {
			URL realUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection)realUrl.openConnection();
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			//conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("user-agent","Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0; WOW64)");
			conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded; charset=UTF-8");
			if(requestProperty!=null)
			for(Entry<String,String> entry:requestProperty.entrySet()){
				conn.setRequestProperty(entry.getKey(), entry.getValue());
			}
			conn.setDoOutput(true);
			conn.setDoInput(true);
//			conn.setReadTimeout(5000);
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
			out.print(body);
			out.flush();
			if(conn.getResponseCode()==200){
				in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
				String line;
				while ((line = in.readLine()) != null) {
					result.append(line);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}
	
	/**
	 * 创建带参数的URL
	 * @param url
	 * @param params
	 * @return
	 */
	public static String linkParams(String url,Map<String,String> params){
		StringBuffer sb=new StringBuffer();
		if(params==null)return url;
		if(params.size()<=0)return url;
		sb.append(url).append("?");
		for(Entry<String,String> entry:params.entrySet()){
			String value=entry.getValue();
			if(StringUtils.isEmpty(value))value="";
			sb.append(entry.getKey()).append("=").append(value).append("&");
		}
		return StringUtils.removeEnd(sb.toString(), "&");
	}
	
	/**
	 * 获取参数字符串
	 * @param params
	 * @return
	 */
	public static String getParamsStr(Map<String,String> params){
		StringBuffer sb=new StringBuffer();
		if(params==null)return "";
		if(params.size()<=0)return "";
		for(Entry<String,String> entry:params.entrySet()){
			String value=entry.getValue();
			if(StringUtils.isEmpty(value))value="";
			sb.append(entry.getKey()).append("=").append(value).append("&");
		}
		return StringUtils.removeEnd(sb.toString(), "&");
	}
	
	/**
	 * 判断链接是否有效
	 * @param url
	 * @return
	 */
	public static boolean exists(String url) {
		try {
	    	//设置此类是否应该自动执行 HTTP 重定向（响应代码为 3xx 的请求）。
	        HttpURLConnection.setFollowRedirects(false);
	        //到 URL 所引用的远程对象的连接
	        HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
	        /* 设置 URL 请求的方法， GET POST HEAD OPTIONS PUT DELETE TRACE 以上方法之一是合法的，具体取决于协议的限制。*/
	        con.setRequestMethod("HEAD");
	        //从 HTTP 响应消息获取状态码
	        return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return false;
	    }
	}
	
	/**
	 * 根据输入流转化成JSONObject格式数据
	 * @param inputStream
	 * @return
	 */
	public static JSONObject getJson4Stream(InputStream inputStream) {
		StringBuffer sb = new StringBuffer("");
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader((ServletInputStream)inputStream,"utf-8"));
			String line;
			while((line = reader.readLine())!=null) {
				System.out.println(line);
				sb.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return JSONObject.fromObject(sb.toString());
	}
}
