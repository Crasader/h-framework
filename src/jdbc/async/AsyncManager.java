package jdbc.async;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import log.InternalLoggerFactory;
import log.Logger;
import thread.StandardRunnable;
import thread.StandardThread;

/**
 * 异步sql执行管理
 * @author huangliy
 */
public class AsyncManager {
	/** instance */
	private static final AsyncManager instance = new AsyncManager();
	/** log */
	private static final Logger log = InternalLoggerFactory.getLogger("async");
	/** 间隔（毫秒） */
	private static final long INTERVAL = 300;
	
	/** 异步回调队列 */
	private BlockingQueue<AsyncCallback> queue = new LinkedBlockingQueue<>();
	
	/**
	 * 构造函数
	 */
	private AsyncManager() {
		StandardThread thread = new StandardThread("async-sql-thread", new AysncExector(), false, INTERVAL);
		thread.start();
	}
	
	/**
	 * 获得实例
	 * @return AsyncManager
	 */
	public static AsyncManager getInstance() {
		return instance;
	}
	
	/**
	 * 添加异步回调
	 * @param callback void
	 */
	public void addAsyncCallback(AsyncCallback callback) {
		queue.add(callback);
		callback.doLog(log, 1);
	}
	
	/**
	 * 异步执行线程
	 * @author huangliy
	 */
	private class AysncExector extends StandardRunnable {
		public AysncExector() {
			super(null);
		}

		@Override
		public void execute() {
			AsyncCallback callBack = null;
			while ((callBack = queue.poll()) != null) {
				try {
					callBack.callback();
					callBack.doLog(log, 2);
				} catch (Throwable t) {
					callBack.doLog(log, 3);
				}
			}
		}
	}
}
