package common.utils;

import java.io.StringWriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import common.logger.Logger;
import common.logger.LoggerManager;

/**
 * JSON工具类
 */
public class JsonUtils {
	private static Logger log=LoggerManager.getLogger();
	
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final byte[] EMPTY = new byte[0];
	
	public static ObjectNode createObjectNode(){
		ObjectNode node = mapper.createObjectNode(); 
		return node;
	}
	
	public static String jsonFromObject(Object object) {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, object);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {  
            log.error("Unable to serialize to json: " + object);
            return null;
        }
        return writer.toString();  
    }
	
	/**
	 * 对属性中long型数据转String输出
	 * 
	 * @param object
	 * @return
	 * @author lims
	 */
	public static String jsonFromObject_longToString(Object object) {
        StringWriter writer = new StringWriter();
        try {
            mapper.writeValue(writer, object);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {  
            log.error("Unable to serialize to json: " + object);
            return null;
        }
        return writer.toString();  
    }

	public static Object objectFromJson(String json, Class<?> clazz) {  
        Object object;  
        try {  
            object = mapper.readValue(json, clazz);
        } catch (RuntimeException e) {  
            log.error("Runtime exception during deserializing "  
                    + clazz.getSimpleName() + " from "  
                    + StringUtils.abbreviate(json, 80));  
            throw e;  
        } catch (Exception e) {  
            log.error("Exception during deserializing " + clazz.getSimpleName()  
                    + " from " + StringUtils.abbreviate(json, 80));  
            return null;  
        }  
        return object;  
    }
	
	public static byte[] encode(Object obj) {
		try {
			return mapper.writeValueAsBytes(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return EMPTY;
		}
	}
	
	public static String encode2Str(Object obj) {
		try {
			return mapper.writeValueAsString(obj);
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}
	
	public static JsonNode decode(String jsonStr) {
		try {
			return mapper.readTree(jsonStr);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}

	public static ObjectMapper getMapper() {
		return mapper;
	}
	
	public static int getInt(String key,JsonNode json){
		try {
			return json.get(key).asInt();
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	
	public static long getLong(String key,JsonNode json){
		try {
			return json.get(key).asLong();
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}
	
	public static String getString(String key,JsonNode json){
		try {
			return json.get(key).asText();
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
}
