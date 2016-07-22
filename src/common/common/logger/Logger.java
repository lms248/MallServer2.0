package common.logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentLinkedDeque;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 日志记录调用方法
 */
public class Logger {
	private String loggerName;//日志名
	private String pattern;//日志模式
	ConcurrentLinkedDeque<Log> clq = new ConcurrentLinkedDeque<Log>();
	
	public Logger(String loggerName, String pattern) {
		this.loggerName = loggerName;
		this.pattern = pattern;
	}
	
	public void info(Object... msg) {
		if(ArrayUtils.contains(LoggerConfig.getLEVELS(), LoggerConfig.INFO)) {
			StringBuilder sb = new StringBuilder();
			for(Object obj : msg) {
				sb.append(obj.toString()).append(" | ");
			}
			log(StringUtils.remove(sb.toString(), " | "), LoggerConfig.INFO);
		}
	}
	
	public void debug(Object... msg) {
		if(ArrayUtils.contains(LoggerConfig.getLEVELS(), LoggerConfig.DEBUG)) {
			StringBuilder sb = new StringBuilder();
			for(Object obj : msg) {
				sb.append(obj.toString()).append(" | ");
			}
			log(StringUtils.remove(sb.toString(), " | "), LoggerConfig.DEBUG);
		}
	}
	
	public void error(Object... msg) {
		if(ArrayUtils.contains(LoggerConfig.getLEVELS(), LoggerConfig.ERROR)) {
			StringBuilder sb = new StringBuilder();
			for(Object obj : msg) {
				sb.append(obj.toString()).append(" | ");
			}
			log(StringUtils.remove(sb.toString(), " | "), LoggerConfig.ERROR);
		}
	}
	
	public void log(String msg, int level) {
		StackTraceElement stack[] = (new Throwable()).getStackTrace();
		StackTraceElement s = stack[2];
		String className = s.getClassName();
		String methodName = s.getMethodName();
		String fileName = s.getFileName();
		int lineNum = s.getLineNumber();
		LocalDateTime logDateTime = LocalDateTime.now();
		String content = pattern.replace("%class", className)
				.replace("%line", lineNum + "")
				.replace("%time", logDateTime.toString())
				.replace("%msg", msg)
				.replace("%level", LoggerManager.getLevelString(level))
				.replace("%method", methodName)
				.replace("%file", fileName);
		output(content,level,logDateTime);
	}
	
	public void output(String content, int level, LocalDateTime logDateTime){
		if(ArrayUtils.contains(LoggerConfig.getAPPENDERS(), LoggerConfig.APPENDER_CONTROLLER)) {
			if(level==LoggerConfig.INFO) {
				System.out.println(content);
			} else if(level==LoggerConfig.DEBUG) {
				System.out.println(content);
			} else if(level==LoggerConfig.ERROR) {
				System.err.println(content);
			}
		}
		if(ArrayUtils.contains(LoggerConfig.getAPPENDERS(), LoggerConfig.APPENDER_FILE)){
			Log log = new Log(content,logDateTime);
			clq.offer(log);
		}
	}
	
	public void flush() {
		File f = null;
		BufferedWriter bw = null;
		try {
			LocalDate tempDate = null;
			int t = 0;
			Log log = null;
			while ((log = clq.poll()) != null) {
				if (tempDate == null
						|| !log.getLogDateTime().toLocalDate().isEqual(tempDate)) {
					f = new File(LoggerConfig.getLOG_PATH() + File.separator
							+ log.getLogDateTime().toLocalDate()
							+ File.separator + loggerName + ".log");
					if (!f.exists()) {
						f.getParentFile().mkdirs();
					}
					bw = new BufferedWriter(new FileWriter(f, true));
					tempDate = log.getLogDateTime().toLocalDate();
				}
				bw.write(log.getOutputString()+"\r\n");
				log.clear();
				log = null;
				t++;
				if (t % 50 == 0) {
					bw.flush();
				}
			}
			if (bw != null) {
				bw.flush();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
