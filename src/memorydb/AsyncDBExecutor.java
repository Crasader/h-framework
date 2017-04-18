package memorydb;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import jdbc.SqlFactory;
import log.InternalLoggerFactory;
import log.Logger;
import thread.StandardRunnable;
import thread.StandardThread;

/**
 * 异步DB处理器
 * @author huangliy
 * @version 1.0.0.0 2017年4月18日下午4:01:28
 */
public class AsyncDBExecutor {
	/** log */
	static final Logger log = InternalLoggerFactory.getLogger("com.h-framework.async");
	
	/** 时间间隔 */
	static final int INTERVAL = 500;
	
	/** 单例 */
	private static final AsyncDBExecutor instance = new AsyncDBExecutor();
	
	/** 消息队列 */
	@SuppressWarnings("unchecked")
	private List<SQLEntry>[] messageArray = new ArrayList[2];
	
	/** 幸存队列 */
	private List<SQLEntry> survivorList = new ArrayList<>();
	
	/** 游标 */
	private volatile int cursor;
	
	/** 锁 */
	private Object lock = new Object();
	
	/** 数据源 */
	private DataSource ds;
	
	/** 初始化标记 */
	private boolean init = false;
	
	/** 执行线程 */
	private StandardThread executor;
	
	/** SqlFactory */
	private SqlFactory sqlFactory;
	
	/** db执行线程数量 */
	private int threadNum = 1;
	
	/** 批量执行size */
	private int batchSize = 400;
	
	/**
	 * 构造函数
	 */
	private AsyncDBExecutor() {};
	
	/**
	 * 获取实例
	 * @return
	 * $Date: 2017年4月18日下午4:05:51
	 */
	public static AsyncDBExecutor getInstance() {
		return instance;
	}
	
	/**
	 * 初始化
	 * @param sqlFactory
	 * @param ds
	 * $Date: 2017年4月18日下午4:06:55
	 */
	public synchronized void init (SqlFactory sqlFactory, DataSource ds) {
		if (init) {
			return;
		}
		
		this.messageArray[0] = new ArrayList<>();
		this.messageArray[1] = new ArrayList<>();
		this.cursor = 0;
		this.ds = ds;
		this.sqlFactory = sqlFactory;
		
		this.executor = new StandardThread("AsyncDBExecutor-thread", new SQLExectorMainThread(), false, INTERVAL);
		init = true;
	}
	
	/**
	 * SQL执行线程
	 * @author huangliy
	 * @version 1.0.0.0 2017年4月18日下午4:24:06
	 */
	private class SQLExectorMainThread extends StandardRunnable{

		/**
		 * 构造函数
		 */
		public SQLExectorMainThread() {
			super(null);
		}
		
		/* 
		 * @see thread.StandardRunnable#execute()
		 */
		@Override
		public void execute() {
			// TODO Auto-generated method stub
		}
	}
}
