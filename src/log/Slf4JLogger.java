package log;

import org.slf4j.Logger;

/**
 * Slf4JLogger
 * @author huangliy
 *
 */
public class Slf4JLogger extends AbstractLogger{
	/** logger */
	private final transient Logger logger;
	
	/**
	 * 构造函数
	 */
	public Slf4JLogger(Logger logger) {
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
		logger.trace(msg, t);
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
		logger.debug(msg, t);
		
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
		logger.info(msg, t);
	}

	@Override
	public boolean isWarnEnable() {
		return InternalLoggerFactory.errorLog.isWarnEnable();
	}

	@Override
	public void warn(String msg) {
		InternalLoggerFactory.errorLog.warn(msg);
	}

	@Override
	public void warn(String format, Object... args) {
		InternalLoggerFactory.errorLog.warn(format, args);
	}

	@Override
	public void warn(String msg, Throwable t) {
		InternalLoggerFactory.errorLog.warn(msg, t);
	}

	@Override
	public boolean isErrorEnable() {
		return InternalLoggerFactory.errorLog.isErrorEnable();
	}

	@Override
	public void error(String msg) {
		InternalLoggerFactory.errorLog.error(msg);
	}

	@Override
	public void error(String format, Object... args) {
		InternalLoggerFactory.errorLog.error(format, args);
	}

	@Override
	public void error(String msg, Throwable t) {
		InternalLoggerFactory.errorLog.error(msg, t);
	}

	@Override
	public void error(String format, Throwable t, Object... args) {
		InternalLoggerFactory.errorLog.error(format, t, args);
	}

	@Override
	public boolean isFatalEnable() {
		return InternalLoggerFactory.errorLog.isErrorEnable();
	}

	@Override
	public void fatal(String msg) {
		InternalLoggerFactory.errorLog.fatal(msg);
	}

	@Override
	public void fatal(String format, Object... args) {
		InternalLoggerFactory.errorLog.fatal(format, args);
	}

	@Override
	public void fatal(String msg, Throwable t) {
		InternalLoggerFactory.errorLog.fatal(msg, t);
	}
	
}
