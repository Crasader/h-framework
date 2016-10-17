package thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class StandardThreadFactory implements ThreadFactory{
	static final AtomicInteger poolNumber = new AtomicInteger(1);
	final ThreadGroup group;
	final AtomicInteger threadNumber = new AtomicInteger(1);
	final String namePrefix;
	
	/**
	 * 创建一个线程工厂
	 * @return
	 */
	public static ThreadFactory defaultThreadFactory() {
		return new StandardThreadFactory("DefaultPool");
	}
	
	/**
	 * 构造函数
	 * @param poolName
	 */
	public StandardThreadFactory(String poolName) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = poolName + "-" + poolNumber.getAndIncrement() + "-thread-";
	}
	
	/* 
	 * 创建一个新线程
	 * @see java.util.concurrent.ThreadFactory#newThread(java.lang.Runnable)
	 */
	@Override
	public Thread newThread(Runnable r) {
		StandardThread t = new StandardThread(group, namePrefix + threadNumber.getAndIncrement(), r, false);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}

}
