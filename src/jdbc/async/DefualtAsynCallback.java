package jdbc.async;

import java.lang.reflect.Method;

import log.Logger;

/**
 * DefualtAsynCallback
 * @author huangliy
 */
public class DefualtAsynCallback implements AsyncCallback {
	private Method method;
	private Object obj;
	private Object[] args;
	
	/**
	 * 构造函数
	 * @param method
	 * @param obj
	 * @param args
	 */
	public DefualtAsynCallback(Method method, Object obj, Object... args) {
		this.method = method;
		this.obj = obj;
		this.args = args;
	}
	
	/* 
	 * @see jdbc.async.AsyncCallback#callback()
	 */
	@Override
	public void callback() {
		try {
			method.invoke(obj, args);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
	}

	/* 
	 * @see jdbc.async.AsyncCallback#doLog(log.Logger, int)
	 */
	@Override
	public void doLog(Logger log, int type) {
		// Ignore
	}

}
