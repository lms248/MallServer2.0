package common.utils;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtilsBean;

public class MapUtils {
	
	/**
	 * 将javabean实体类转为map类型，然后返回一个map类型的值
	 * @param obj
	 * @return
	 */
    public static Map<?, ?> beanToMap(Object obj) { 
            Map<String, Object> params = new HashMap<String, Object>(0); 
            try { 
                PropertyUtilsBean propertyUtilsBean = new PropertyUtilsBean(); 
                PropertyDescriptor[] descriptors = propertyUtilsBean.getPropertyDescriptors(obj); 
                for (int i = 0; i < descriptors.length; i++) { 
                    String name = descriptors[i].getName(); 
                    if (!"class".equals(name)) { 
                        params.put(name, propertyUtilsBean.getNestedProperty(obj, name)); 
                    } 
                } 
            } catch (Exception e) { 
                e.printStackTrace(); 
            } 
            return params; 
    }
    
}
