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

	/* 
	 * @see log.InternalLoggerFactory#createErrorLogger(java.lang.String)
	 */
	@Override
	protected Logger createErrorLogger(String name) {
		return new Slf4JErrorLogger(org.slf4j.LoggerFactory.getLogger(name));
	}
	
	/**
	 * tests
	 * @param args
	 */
	public static void main(String[] args) {
		InternalLoggerFactory.setErrorLogger("h.error");
		Logger log = InternalLoggerFactory.getLogger("test");
		
		log.info("你好{}{}{}", 1,2,"da");
	}
}
