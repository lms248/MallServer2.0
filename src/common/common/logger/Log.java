package common.logger;

import java.time.LocalDateTime;

/**
 * 日志结构体
 */
public class Log {
	private String outputString;
	private LocalDateTime logDateTime;
	
	public Log(String outputString, LocalDateTime logDateTime) {
		this.outputString = outputString;
		this.logDateTime = logDateTime;
	}
	
	public String getOutputString() {
		return outputString;
	}
	public void setOutputString(String outputString) {
		this.outputString = outputString;
	}
	public LocalDateTime getLogDateTime() {
		return logDateTime;
	}
	public void setLogDateTime(LocalDateTime logDateTime) {
		this.logDateTime = logDateTime;
	}

	public void clear() {
		this.outputString=null;
		this.logDateTime=null;
	}
}
