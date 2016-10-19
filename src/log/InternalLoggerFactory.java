package log;

/**
 * @author huangliy
 *	日志工厂类
 */
public abstract class InternalLoggerFactory {
	/** 默认 */
	private static volatile InternalLoggerFactory defaultFactory;
	/** 默认的错误日志 */
	static volatile Logger errorLog;
	
	static {
		final String name = InternalLoggerFactory.class.getName();
		InternalLoggerFactory f;
		try {
			f = new Slf4JLoggerFactory();
			f.createLogger(name).debug("Using SLF4J as the default logging framework");
			defaultFactory = f;
		} catch (Throwable t) {
			throw new RuntimeException("SLF4J not found, logger init error", t);
		}
		
		defaultFactory = f;
		
		setErrorLogger("h.error");
	}
	
	/**
	 * 活动默认日志工厂
	 * @return
	 */
	public static InternalLoggerFactory getDefaultFactory() {
		return defaultFactory;
	}

	/**
	 * 设置默认日志工厂
	 * @param defaultFactory
	 */
	public static void setDefaultFactory(InternalLoggerFactory defaultFactory) {
		if (defaultFactory == null) {
			throw new NullPointerException("defaultFactory");
		}
		InternalLoggerFactory.defaultFactory = defaultFactory;
	}
	
	/**
	 * 获得指定名称日志
	 * @param name
	 * @return
	 */
	public static Logger getLogger(String name) {
		return getDefaultFactory().createLogger(name);
	}
	
	/**
	 * @param cls
	 * @return
	 */
	public static Logger getLogger(Class<?> cls) {
		return getLogger(cls.getName());
	}
	
	/**
	 * 设置统一错误日志
	 * @param name
	 */
	public static void setErrorLogger(String name) {
		errorLog = getDefaultFactory().createErrorLogger(name);
	}
	
	/**
	 * 创建指定名称的Logger
	 * @param name
	 * @return
	 */
	protected abstract Logger createLogger(String name);
	
	/**
	 * 创建专门输出错误的日志
	 * @param name
	 * @return
	 */
	protected abstract Logger createErrorLogger(String name);
}
