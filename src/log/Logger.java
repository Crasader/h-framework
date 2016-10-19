package log;

/**
 * 通用标准类接口
 * @author huangliy
 *
 */
public interface Logger {
	/**
	 * 获取日志名称
	 * @return
	 */
	String name();
	
	boolean isTraceEnable();
	
	void trace(String msg);
	
	void trace(String format, Object... args);
	
	void trace(String msg, Throwable t);
	
	
	boolean isDebugEnable();
	
	void debug(String msg);
	
	void debug(String format, Object... args);
	
	void debug(String msg, Throwable t);
	
		
	boolean isInfoEnable();
	
	void info(String msg);
	
	void info(String format, Object... args);
	
	void info(String msg, Throwable t);
	
	
	boolean isWarnEnable();
	
	void warn(String msg);
	
	void warn(String format, Object... args);
	
	void warn(String msg, Throwable t);
	
	
	boolean isErrorEnable();
	
	void error(String msg);
	
	void error(String format, Object... args);
	
	void error(String msg, Throwable t);
	
	void error(String format, Throwable t, Object...args);
	
	
	boolean isFatalEnable();
	
	void fatal(String msg);
	
	void fatal(String format, Object... args);
	
	void fatal(String msg, Throwable t);
	
	/**
	 * 是否支持输出指定级别日志
	 * @param level
	 * @return
	 */
	boolean isLogEnable(LogLevel level);
	
	/**
	 * 输出指定级别日志
	 * @param level
	 * @param msg
	 */
	void log(LogLevel level, String msg);
	
	/**
	 * 输出指定级别日志
	 * @param level
	 * @param format
	 * @param args
	 */
	void log(LogLevel level, String format, Object... args);
	
	/**
	 * 输出指定级别日志
	 * @param level
	 * @param msg
	 * @param t
	 */
	void log(LogLevel level, String msg, Throwable t);
	
	/**
	 * 输出指定级别日志
	 * @param level
	 * @param format
	 * @param t
	 * @param args
	 */
	void log(LogLevel level, String format, Throwable t, Object... args);
	
	/**
	 * 获取错误的原始原因
	 * @param t
	 * @return
	 */
	Throwable getOriginThrowable(Throwable t);
}
