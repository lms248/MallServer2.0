package common.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/**
 * 文件处理工具类
 */
public class FileUtils {
	private static String defaultCharset="utf-8";
	
	/**
	 * 过滤文本注释
	 * @param path
	 * @return
	 */
	public static String readFileToJSONString(String path){
		String src=readFileToString(path,defaultCharset);
		src=src.replaceAll("/\\*[^*]*\\*+(?:[^/*][^*]*\\*+)*/", "");
		return src;
	}
	
	public static String readFileToString(String path){
		return readFileToString(path,defaultCharset);
	}
	
	public static String readFileToString(String path,String charset){
		FileInputStream fis=null;
		BufferedReader br=null;
		StringBuilder sb=new StringBuilder();
		try {
			fis=new FileInputStream(path);
			br=new BufferedReader(new InputStreamReader(fis,charset));
			String temp="";
			while((temp=br.readLine())!=null){
				sb.append(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(br!=null)br.close();
				if(fis!=null)fis.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return sb.toString();
	}
	
	public static void writeStringToFile(String path,String content){
		writeStringToFile(path, content, defaultCharset);
	}
	
	
	public static void writeStringToFile(String path,String content,String charset){
		FileOutputStream fos=null;
		BufferedWriter bw=null;
		try {
			fos=new FileOutputStream(path);
			bw=new BufferedWriter(new OutputStreamWriter(fos,charset));
			bw.write(content);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				if(bw!=null)bw.close();
				if(fos!=null)fos.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}
}
