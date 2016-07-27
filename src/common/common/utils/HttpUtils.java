package common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.ServletInputStream;

import net.sf.json.JSONObject;

/**
 * http请求工具类
 */
public class HttpUtils {
	
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
