package thread;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangliy
 * StandardRunnable
 */
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class StandardRunnable implements Runnable{
	/** 拦截器 */
	protected List<ThreadInterceptor> interceptors;
	/** 开始时间 */
	protected long startTime;
	/** 是否发生错误 */
	protected boolean error;
	
	/**
	 * 构造方法
	 * @param interceptors
	 */
	public StandardRunnable(List<ThreadInterceptor> interceptors) {
		this.interceptors = interceptors;
		if (this.interceptors == null) {
			this.interceptors = new ArrayList<ThreadInterceptor>();
		}
	}
	
	/* 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		executeRunBefore();
		execute();
		executeRunAfter();
	}
	
	/**
	 * 执行后
	 */
	private void executeRunAfter() {
		for (ThreadInterceptor threadInterceptor : interceptors) {
			threadInterceptor.afterExecute(this);
		}
	}

	/**
	 * 执行前
	 */
	private void executeRunBefore() {
		for (ThreadInterceptor threadInterceptor : interceptors) {
			threadInterceptor.beforExecute(this);
		}
	}

	/**
	 * 添加拦截器
	 * @param interceptor
	 */
	public void addInterceptor(ThreadInterceptor interceptor) {
		this.interceptors.add(interceptor);
	}
	
	/**
	 * 需要执行的方法
	 */
	public abstract void execute();
}
