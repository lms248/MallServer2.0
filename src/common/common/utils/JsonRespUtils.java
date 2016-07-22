package common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * JSON返回数据
 */
public class JsonRespUtils {
	
	public static String response(int code,String msg){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", code);
		map.put("data", msg);
		String data=JsonUtils.encode2Str(map);
		return data;
	}
	
	public static String fail(String msg){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", Def.CODE_FAIL);
		map.put("data", msg);
		String data=JsonUtils.encode2Str(map);
		return data;
	}
	
	public static String success(Object obj){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", Def.CODE_SUCCESS);
		map.put("data", obj);
		String data=JsonUtils.encode2Str(map);
		return data;
	}
	
	public static String exception(Object obj){
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("code", Def.CODE_EXCEPTION);
		map.put("data", obj);
		String data=JsonUtils.encode2Str(map);
		return data;
	}
}
