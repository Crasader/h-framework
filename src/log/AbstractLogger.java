package log;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;

/**
 * AbstractLogger
 * @author huangliy
 *
 */
public abstract class AbstractLogger implements Logger{
	/** 日志名称 */
	private final String name;
	
	/**
	 * 构造函数
	 * @param name
	 */
	public AbstractLogger(String name) {
		this.name = name;
	}

	/* 
	 * @see log.Logger#name()
	 */
	@Override
	public String name() {
		return name;
	}
	
	/* 
	 * @see log.Logger#isLogEnable(log.LogLevel)
	 */
	@Override
	public boolean isLogEnable(LogLevel level) {
		switch(level) {
		case TRACE:
			return isTraceEnable();
		case DEBUG:
			return isDebugEnable();
		case WARN:
			return isWarnEnable();
		case INFO:
			return isInfoEnable();
		case ERROR:
			return isErrorEnable();
		case FATAL:
			return isFatalEnable();
		}
		return false;
	}

	/* 
	 * @see log.Logger#log(log.LogLevel, java.lang.String)
	 */
	@Override
	public void log(LogLevel level, String msg) {
		switch(level) {
		case TRACE:
			trace(msg);
			break;
		case DEBUG:
			debug(msg);
			break;
		case WARN:
			warn(msg);
			break;
		case INFO:
			info(msg);
			break;
		case ERROR:
			error(msg);
			break;
		case FATAL:
			fatal(msg);
			break;
		}
	}

	/* 
	 * @see log.Logger#log(log.LogLevel, java.lang.String, java.lang.Object[])
	 */
	@Override
	public void log(LogLevel level, String format, Object... args) {
		switch(level) {
		case TRACE:
			trace(format, args);
			break;
		case DEBUG:
			debug(format, args);
			break;
		case WARN:
			warn(format, args);
			break;
		case INFO:
			info(format, args);
			break;
		case ERROR:
			error(format, args);
			break;
		case FATAL:
			fatal(format, args);
			break;
		}
	}

	/* 
	 * @see log.Logger#log(log.LogLevel, java.lang.String, java.lang.Throwable)
	 */
	@Override
	public void log(LogLevel level, String msg, Throwable t) {
		switch(level) {
		case TRACE:
			trace(msg, t);
			break;
		case DEBUG:
			debug(msg, t);
			break;
		case WARN:
			warn(msg, t);
			break;
		case INFO:
			info(msg, t);
			break;
		case ERROR:
			error(msg, t);
			break;
		case FATAL:
			fatal(msg, t);
			break;
		}
	}

	/* 
	 * @see log.Logger#log(log.LogLevel, java.lang.String, java.lang.Throwable, java.lang.Object[])
	 */
	@Override
	public void log(LogLevel level, String format, Throwable t, Object... args) {
		switch(level) {
		case TRACE:
		case DEBUG:
		case WARN:
		case INFO:
			break;
		case ERROR:
			error(format, t, args);
			break;
		case FATAL:
			break;
		}
	}

	/* 
	 * @see log.Logger#getOriginThrowable(java.lang.Throwable)
	 */
	@Override
	public Throwable getOriginThrowable(Throwable t) {
		if (t instanceof InvocationTargetException) {
			InvocationTargetException e = (InvocationTargetException) t;
			return getOriginThrowable(e.getTargetException());
		} else if (t instanceof UndeclaredThrowableException) {
			UndeclaredThrowableException e = (UndeclaredThrowableException) t;
			return getOriginThrowable(e.getUndeclaredThrowable());
		} else if (t.getClass() == RuntimeException.class) {
			RuntimeException e = (RuntimeException) t;
			if (null != e.getCause() && 
					(e.getCause() instanceof InvocationTargetException || 
							e.getCause() instanceof UndeclaredThrowableException)) {
				return getOriginThrowable(e.getCause());
			}
		}
		return t;
	}
	
	
}
