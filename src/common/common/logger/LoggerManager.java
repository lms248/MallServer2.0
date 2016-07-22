package common.logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.ArrayUtils;

import common.utils.StringUtils;
import common.utils.TimerManagerUtils;

/**
 * 日志处理器
 */
public class LoggerManager {
	public static Map<String, Logger> loggerContent = new ConcurrentHashMap<String, Logger>();
	
	/**
	 * 获取用户的Logger
	 * @param username
	 * @return
	 */
	public static Logger getUserLogger(String username) {
		return getLogger(username);
	}
	
	/**
	 * 获取特定名称的logger
	 * @param loggerName
	 * @return
	 */
	public static Logger getLogger(String loggerName) {
		if (LoggerConfig.SYSTEM_LOGGER_NAME.equals(loggerName)) {
			throw new IllegalArgumentException(loggerName + " is system logger name. Place try other logger name.");
		}
		
		Logger logger = loggerContent.get(loggerName);
		if (logger == null) {
			logger = new Logger(loggerName, LoggerConfig.getPATTERN());
			loggerContent.put(loggerName, logger);
		}
		return logger;
	}
	
	/**
	 * 获取带输出格式的logger
	 * @param loggerName
	 * @param pattern
	 * @return
	 */
	public static Logger getLogger(String loggerName, String pattern) {
		if (LoggerConfig.SYSTEM_LOGGER_NAME.equals(loggerName)) {
			throw new IllegalArgumentException(loggerName + " is system logger name. Place try other logger name.");
		}
		
		Logger logger = loggerContent.get(loggerName);
		if (logger == null) {
			logger = new Logger(loggerName, pattern);
			loggerContent.put(loggerName, logger);
		}
		return logger;
	}
	
	/**
	 * 获取class对应的logger
	 * @param clazz
	 * @return
	 */
	public static Logger getLogger(Class<?> clazz) {
		String loggerName = clazz.getName();
		return getLogger(loggerName);
	}
	
	/**
	 * 获取系统默认logger
	 * @return
	 */
	public static Logger getLogger() {
		String loggerName = LoggerConfig.SYSTEM_LOGGER_NAME;
		Logger logger = loggerContent.get(loggerName);
		if (logger == null) {
			logger = new Logger(loggerName, LoggerConfig.getPATTERN());
			loggerContent.put(loggerName, logger);
		}
		return logger;
	}
	
	/**
	 * 获取输出等级字符串
	 * @param level
	 * @return
	 */
	public static CharSequence getLevelString(int level) {
		String l = "";
		switch (level) {
		case 0:
			l = "INFO";
			break;
		case 1:
			l = "DEBUG";
			break;
		case 2:
			l = "ERROR";
			break;
		default:
			break;
		}
		return l;
	}
	
	/**
	 * 加载日志配置文件
	 * @param filePath
	 */
	public static void loadLogConfig(String filePath) {
		File f = new File(filePath);
		if (f.exists()) {
			FileInputStream fis = null;
			BufferedReader br = null;
			try {
				System.out.println("[System INFO] start loadLogConfig.");
				fis = new FileInputStream(f);
				br = new BufferedReader(new InputStreamReader(fis, "utf-8"));
				String temp = "";
				LoggerConfig.configFile.clear();
				while ((temp = br.readLine()) != null) {
					temp = temp.trim();
					if (!temp.startsWith("#") && StringUtils.isNoneBlank(temp)) {
						String[] attr = temp.split("=");
						if (attr[0].equalsIgnoreCase("LOG_PATH")) {
							LoggerConfig.configFile.put("LOG_PATH", attr[1]);
						} else if (attr[0].equalsIgnoreCase("PATTERN")) {
							LoggerConfig.configFile.put("PATTERN", attr[1]);
						} else if (attr[0].equalsIgnoreCase("LEVELS")) {
							String[] levels = attr[1].split(",");
							List<Integer> list = new ArrayList<Integer>();
							for (String l : levels) {
								if ("INFO".equalsIgnoreCase(l)) {
									list.add(LoggerConfig.INFO);
								} else if ("DEBUG".equalsIgnoreCase(l)) {
									list.add(LoggerConfig.DEBUG);
								} else if ("ERROR".equalsIgnoreCase(l)) {
									list.add(LoggerConfig.ERROR);
								}
							}
							int[] lvs = new int[list.size()];
							for (int i = 0; i < list.size(); i++) {
								lvs[i] = list.get(i);
							}
							LoggerConfig.configFile.put("LEVELS", lvs);
						} else if (attr[0].equalsIgnoreCase("APPENDERS")) {
							String[] appenders = attr[1].split(",");
							List<Integer> list = new ArrayList<Integer>();
							for (String a : appenders) {
								if("APPENDER_CONTROLLER".equalsIgnoreCase(a)){
									list.add(LoggerConfig.APPENDER_CONTROLLER);
								}else if("APPENDER_FILE".equalsIgnoreCase(a)){
									list.add(LoggerConfig.APPENDER_FILE);
								}
							}
							int[] aps = new int[list.size()];
							for (int i = 0; i < list.size(); i++) {
								aps[i] = list.get(i);
							}
							LoggerConfig.configFile.put("APPENDERS", aps);
						} else if (attr[0].equalsIgnoreCase("FLUSH_TIME")) {
							int time = Integer.valueOf(attr[1]);
							if (time > 0) {
								LoggerConfig.configFile.put("FLUSH_TIME", time);
							}
						}
					}
					
				}
				System.out.println("[System INFO] LoadLogConfig completed.");
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("[System ERROR] LoadLogConfig meet exception.");
			} finally {
				try {
					if (br != null) {
						br.close();
					}
					if (fis != null) {
						fis.close();
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		} else {
			throw new IllegalArgumentException("Can not find "+filePath);
		}
	}
	
	/**
	 * 监控日志配置文件
	 * @param filePath
	 */
	public static void watchLogConfig(String filePath) {
		File f = new File(filePath);
		if (f.exists()) {
			if (f.lastModified() != LoggerConfig.LAST_MODIFY) {
				LoggerConfig.LAST_MODIFY = f.lastModified();
				loadLogConfig(filePath);
			}
		} else {
			throw new IllegalArgumentException("Can not find "+filePath);
		}
	}
	
	/**
	 * 初始化logger配置
	 * @param filePath
	 */
	public static void initLoggerConfig(String filePath) {
		watchLogConfig(filePath);
		startFileWriter();
	}
	
	/**
	 * 初始化logger配置
	 * @param filePath
	 */
	public static void initLoggerConfig(){
		startFileWriter();
	}
	
	/** 定时回写文本日志 */
	private static ScheduledFuture<?> rewriteFileFuture;
	
	/**
	 * 启动文件日志定时回写器
	 */
	public static void startFileWriter() {
		if (rewriteFileFuture != null) {
			rewriteFileFuture.cancel(true);
			rewriteFileFuture = null;
		}
		if (ArrayUtils.contains(LoggerConfig.getAPPENDERS(), LoggerConfig.APPENDER_FILE)) {
			rewriteFileFuture = TimerManagerUtils.schedule(new Runnable() {
				@Override
				public void run() {
					for (Entry<String, Logger> entry : loggerContent.entrySet()) {
						entry.getValue().flush();
					}
				}
			}, LoggerConfig.getFLUSH_TIME(), TimeUnit.SECONDS);
		}
	}
	
	/**
	 * 停止日志回写
	 */
	public static void stopFileWriter() {
		if (rewriteFileFuture != null) {
			rewriteFileFuture.cancel(true);
			rewriteFileFuture = null;
		}
		for (Entry<String, Logger> entry : loggerContent.entrySet()) {
			entry.getValue().flush();
		}
	}
}


