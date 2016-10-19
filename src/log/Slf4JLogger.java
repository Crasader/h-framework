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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String format, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void debug(String msg, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isInfoEnable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void info(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(String format, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void info(String msg, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isWarnEnable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void warn(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String format, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void warn(String msg, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isErrorEnable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void error(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String format, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String msg, Throwable t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void error(String format, Throwable t, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isFatalEnable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void fatal(String msg) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fatal(String format, Object... args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fatal(String msg, Throwable t) {
		// TODO Auto-generated method stub
		
	}
	
}
