package thread;

/**
 * @author huangliy
 * StandardThread
 */
public class StandardThread extends Thread{
	/** 默认运行间隔 */
	public static final long DEFUALT_INTERVAL = 60 * 1000;
	
	private Runnable runnable; //需要运行逻辑
	private boolean runOnce; //是否只执行一次
	private volatile boolean stop; //是否停止运行
	private volatile long interval; //运行时间间隔
	
	
	/**
	 * 构造函数
	 * @param name
	 * @param runnable
	 * @param runOnce
	 * @param interval
	 */
	public StandardThread(String name, Runnable runnable, boolean runOnce, long interval) {
		super(name);
		this.runnable = runnable;
		this.runOnce = runOnce;
		this.stop = false;
		this.interval = interval;
	}
	
	/**
	 * 构造函数
	 * @param group
	 * @param name
	 * @param runnable
	 * @param runOnce
	 */
	public StandardThread(ThreadGroup group, String name, Runnable runnable, boolean runOnce) {
		super(group, name);
		this.runnable = runnable;
		this.runOnce = runOnce;
	}
	
	/**
	 * 停止线程
	 */
	public void stopThread() {
		try {
			this.interrupt();
		} catch (Exception e) {
			// Ignore this Exception
		}
		
		this.stop = true;
	}
	
	/**
	 * 修改运行间隔
	 * @param interval
	 */
	public void changeInterval(long interval) {
		this.interval = interval;
	}

	/* 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		if (runOnce) {
			runOnce();
			return;
		}
		
		while(!stop) {
			// 运行逻辑
			runOnce();
			
			try {
				sleep(interval);
			} catch (InterruptedException e) {
				// Ignore this Exception
			}
		}
	}

	/**
	 * 运行一次
	 */
	private void runOnce() {
		runnable.run();
	}
	
	
}
