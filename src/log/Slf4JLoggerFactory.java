package log;

/**
 * Slf4JLoggerFactory
 * @author huangliy
 *
 */
public class Slf4JLoggerFactory extends InternalLoggerFactory{

	/* 
	 * @see log.InternalLoggerFactory#createLogger(java.lang.String)
	 */
	@Override
	protected Logger createLogger(String name) {
		return new Slf4JLogger(org.slf4j.LoggerFactory.getLogger(name));
	}

	@Override
	protected Logger createErrorLogger(String name) {
		// TODO Auto-generated method stub
		return null;
	}

}
