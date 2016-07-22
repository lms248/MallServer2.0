package common.utils;

/**
 * 字符串工具类
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils{
	/**
	 * 判断是否为空
	 * @param strs
	 * @return
	 */
	public static boolean isBlack(String... strs){
		for(String s : strs){
			if(StringUtils.isBlank(s)){
				return true;
			}
		}
		return false;
	}
}
