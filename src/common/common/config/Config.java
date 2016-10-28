package common.config;

import java.io.File;

import javax.servlet.ServletContextEvent;

/**
 * 项目配置
 */
public class Config {
	/** 项目根路径 */
	public static String ROOT_DIR = System.getProperty("user.dir");
	/** 项目URL路径 */
	public static String WEB_BASE = "";
	/** 配置文件根目录 */
	public static String CONFIG_DIR = "config";
	/** 守护线程运行间隔 */
	public static int WATCH_SECOND = 10;
	/** 日志配置文件 */
	public static String LOGGER_CONFIG = "logger.xx";
	/** 日志存储目录 */
	public static String DEFAULT_LOG_PATH = "";
	/** 日志存储目录 */
	public static String DEFAULT_LOG_PATTERN = "";
	/** 数据库配置 */
	public static String DB_CONFIG = "c3p0-config.xml";
	/** MyBatis配置 */
	public static String MYBATIS_CONFIG = "mybatis-config.xml";
	
	/**
	 * 初始化配置
	 * @param sce
	 */
	public static void init(ServletContextEvent sce) {
		ROOT_DIR = sce.getServletContext().getRealPath("") + File.separator;
		WEB_BASE = sce.getServletContext().getContextPath();
		CONFIG_DIR = ROOT_DIR+"WEB-INF" + File.separator + CONFIG_DIR + File.separator;
		LOGGER_CONFIG = CONFIG_DIR + LOGGER_CONFIG;
		DB_CONFIG = CONFIG_DIR + DB_CONFIG;
		MYBATIS_CONFIG = CONFIG_DIR + MYBATIS_CONFIG;
		
		//设置日志参数
		DEFAULT_LOG_PATH = ROOT_DIR+"logs";
		DEFAULT_LOG_PATTERN = "[%level %time %class %method %line] %msg";
	}
	
	public static void main(String[] args) {
		System.out.println("ROOT_DIR=="+ROOT_DIR);
		System.out.println("WEB_BASE=="+WEB_BASE);
		System.out.println("CONFIG_DIR=="+CONFIG_DIR);
		System.out.println("LOGGER_CONFIG=="+LOGGER_CONFIG);
		System.out.println("DB_CONFIG=="+DB_CONFIG);
		System.out.println("MYBATIS_CONFIG=="+MYBATIS_CONFIG);
	}
}
