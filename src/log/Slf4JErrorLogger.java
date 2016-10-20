package log;

import org.slf4j.Logger;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

import string.SymbolConstants;

/**
 * Slf4JErrorLogger
 * @author huangliy
 *
 */
public class Slf4JErrorLogger extends AbstractLogger{
	/** logger */
	private final transient Logger logger;
	
	private static Marker FATAL = MarkerFactory.getMarker("FATAL");
	
	/**
	 * 构造函数
	 */
	public Slf4JErrorLogger(Logger logger) {
		super(logger.getName());
		this.logger = logger;
	}

	@Override
	public boolean isTraceEnable() {
		return logger.isTraceEnabled();
	}

	@Override
	public void trace(String msg) {
		logger.trace(msg);
	}

	@Override
	public void trace(String format, Object... args) {
		logger.trace(format, args);
	}

	@Override
	public void trace(String msg, Throwable t) {
		logger.trace(getThrowableTrace(msg, t));
	}

	@Override
	public boolean isDebugEnable() {
		return logger.isDebugEnabled();
	}

	@Override
	public void debug(String msg) {
		logger.debug(msg);
	}

	@Override
	public void debug(String format, Object... args) {
		logger.debug(format, args);
	}

	@Override
	public void debug(String msg, Throwable t) {
		logger.debug(getThrowableTrace(msg, t));
		
	}

	@Override
	public boolean isInfoEnable() {
		return logger.isInfoEnabled();
	}

	@Override
	public void info(String msg) {
		logger.info(msg);
	}

	@Override
	public void info(String format, Object... args) {
		logger.info(format, args);
	}

	@Override
	public void info(String msg, Throwable t) {
		logger.info(getThrowableTrace(msg, t));
	}

	@Override
	public boolean isWarnEnable() {
		return logger.isWarnEnabled();
	}

	@Override
	public void warn(String msg) {
		logger.warn(msg);
	}

	@Override
	public void warn(String format, Object... args) {
		logger.warn(format, args);
	}

	@Override
	public void warn(String msg, Throwable t) {
		logger.warn(getThrowableTrace(msg, t));
	}

	@Override
	public boolean isErrorEnable() {
		return logger.isErrorEnabled();
	}

	@Override
	public void error(String msg) {
		logger.error(msg);
	}

	@Override
	public void error(String format, Object... args) {
		logger.error(format, args);
	}

	@Override
	public void error(String msg, Throwable t) {
		logger.error(getThrowableTrace(msg, t));
	}

	@Override
	public void error(String format, Throwable t, Object... args) {
		FormattingTuple ft = MessageFormatter.arrayFormat(format, args);
		String fStr = ft.getMessage();
		InternalLoggerFactory.errorLog.error(getThrowableTrace(fStr, t));
	}

	@Override
	public boolean isFatalEnable() {
		return logger.isErrorEnabled(FATAL);
	}

	@Override
	public void fatal(String msg) {
		logger.error(FATAL, msg);
	}

	@Override
	public void fatal(String format, Object... args) {
		logger.error(FATAL, format, args);
	}

	@Override
	public void fatal(String msg, Throwable t) {
		logger.error(FATAL, getThrowableTrace(msg, t));
	}
	
	/**
	 * 获取一次Trace信息
	 * @param msg
	 * @param t
	 * @return
	 */
	private String getThrowableTrace(String msg, Throwable t) {
		t = getOriginThrowable(t);
		StringBuilder sb = new StringBuilder();
		int num = InternalLoggerFactory.LINES;
		StackTraceElement[] stacks = t.getStackTrace();
		sb.append(t.toString());
		sb.append("#");
		
		int index = 1;
		for (StackTraceElement element : stacks) {
			String value = element.toString();
			sb.append(SymbolConstants.NEW_LINE).append(SymbolConstants.TAB).append(value);
			
			index++;
			if (index > num) {
				break;
			}
		}
		return sb.toString();
	}
}
