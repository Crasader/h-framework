package thread;

/**
 * @author huangliy
 * 线程执行拦截器
 */
public interface ThreadInterceptor<E extends StandardRunnable> {
	/**
	 * 线程执行前
	 * @param thread
	 */
	void beforExecute(E runnable);
	
	/**
	 * 线程执行后
	 * @param thread
	 */
	void afterExecute(E runnable);
}
