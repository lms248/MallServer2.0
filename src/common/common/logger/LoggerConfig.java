package common.logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import common.config.Config;

/**
 * 日志配置
 */
public class LoggerConfig {
	/**************************日志等级定义********************************/
	public static final int INFO = 0;
	public static final int DEBUG = 1;
	public static final int ERROR = 2;
	
	/**************************日志输出定义********************************/
	public static final int APPENDER_CONTROLLER = 0;
	public static final int APPENDER_FILE = 1;
	
	/**************************日志参数配置********************************/
	public static long LAST_MODIFY = 0;
	public static final String SYSTEM_LOGGER_NAME = "syslog";
	
	/** 日志回写时间频率（秒） */
	public static int DEFAULT_FLUSH_TIME = 30;
	public static String DEFAULT_LOG_PATH = Config.DEFAULT_LOG_PATH;
	public static String DEFAULT_PATTERN = Config.DEFAULT_LOG_PATTERN;
	public static int[] DEFAULT_LEVELS=new int[]{INFO,DEBUG,ERROR};
	public static int[] DEFAULT_APPENDERS=new int[]{APPENDER_CONTROLLER,APPENDER_FILE};
	
	public static Map<String, Object> configFile = new ConcurrentHashMap<String, Object>();
	
	public static int getFLUSH_TIME() {
		Integer time = (Integer) configFile.get("FLUSH_TIME");
		time = time == null ? DEFAULT_FLUSH_TIME : time;
		return time;
	}
	
	public static String getLOG_PATH() {
		String path = (String) configFile.get("LOG_PATH");
		path = path == null ? DEFAULT_LOG_PATH : path;
		return path;
	}
	
	public static String getPATTERN() {
		String pattern = (String) configFile.get("PATTERN");
		pattern = pattern == null ? DEFAULT_PATTERN : pattern;
		return pattern;
	}
	
	public static int[] getLEVELS(){
		int[] levels = (int[]) configFile.get("LEVELS");
		levels = levels == null ? DEFAULT_LEVELS : levels;
		return levels;
	}
	
	public static int[] getAPPENDERS(){
		int[] appenders = (int[]) configFile.get("APPENDERS");
		appenders = appenders == null ? DEFAULT_LEVELS : appenders;
		return appenders;
	}
}
