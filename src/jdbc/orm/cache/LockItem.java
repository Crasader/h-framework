package jdbc.orm.cache;

/**
 * LockItem
 * @author huangliy
 */
public class LockItem<T> implements CacheItem<T> {
	/** serialVersionUID */
	private static final long serialVersionUID = 3594556426893747240L;
	
	private int lockCount;// 计数器

	/**
	 * 构造函数
	 */
	public LockItem() {
		this.lockCount = 1;
	}
	
	/**
	 *  锁定
	 */
	public void lock() {
		lockCount++;
	}
	
	/**
	 * 解除锁定
	 *  void
	 */
	public void unlock() {
		lockCount--;
	}
	
	/* 
	 * @see jdbc.orm.cache.CacheItem#getValue()
	 */
	@Override
	public T getValue() {
		return null;
	}

	/* 
	 * @see jdbc.orm.cache.CacheItem#isWritable()
	 */
	@Override
	public boolean isWritable() {
		return lockCount <= 0;
	}
	
}
