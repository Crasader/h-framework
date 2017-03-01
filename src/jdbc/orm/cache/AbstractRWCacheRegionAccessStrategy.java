package jdbc.orm.cache;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javafx.scene.layout.Region;

/**
 * 封装读写访问策略的基本实现
 * @author huangliy
 */
public abstract class AbstractRWCacheRegionAccessStrategy<K, V> implements CacheRegionAccessStrategy<K, V>{
	/** 缓存存储Region */
	private CacheRegion<K, V> reigon;
	/** 锁 */
	protected Lock lock = new ReentrantLock(false);
	
	/**
	 * 构造函数
	 * @param region
	 */
	public AbstractRWCacheRegionAccessStrategy(CacheRegion<K, V> region) {
		this.reigon = region;
	}
	
	/* 
	 * @see jdbc.orm.cache.Cache#get(java.lang.Object)
	 */
	@Override
	public V get(K key) {
		return reigon.get(key);
	}

	/* 
	 * @see jdbc.orm.cache.Cache#mget(java.lang.Object[])
	 */
	@Override
	public List<V> mget(K... keys) {
		return reigon.mget(keys);
	}

	/* 
	 * @see jdbc.orm.cache.Cache#put(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void put(K key, V value) {
		try {
			lock.lock();
			LockItem<V> lockItem = reigon.getLockItem(key);
			if (null != lockItem) {
				if (!lockItem.isWritable()) {
					return;
				}
				reigon.removeLockItem(key);
			}
			
			reigon.put(key, value);
		} finally {
			lock.unlock();
		}
		
	}

	/* 
	 * @see jdbc.orm.cache.Cache#put(java.lang.Object, jdbc.orm.cache.CacheItem)
	 */
	@Override
	public void put(K key, CacheItem<V> item) {
		try {
			lock.lock();
			LockItem<V> lockItem = reigon.getLockItem(key);
			if (null != lockItem) {
				if (!lockItem.isWritable()) {
					return;
				}
				reigon.removeLockItem(key);
			}
			
			reigon.put(key, item);
		} finally {
			lock.unlock();
		}
	}

	/* 
	 * @see jdbc.orm.cache.Cache#put(java.lang.Object, java.lang.Object[])
	 */
	@Override
	public void put(K key, V... values) {
		reigon.put(key, values);
	}

	/* 
	 * @see jdbc.orm.cache.Cache#remove(java.lang.Object)
	 */
	@Override
	public void remove(K key) {
		try {
			lock.lock();
			LockItem<V> lockItem = reigon.getLockItem(key);
			if (null != lockItem) {
				if (!lockItem.isWritable()) {
					return;
				}
				reigon.removeLockItem(key);
			}
			
			reigon.remove(key);
		} finally {
			lock.unlock();
		}
	}

	/* 
	 * @see jdbc.orm.cache.Cache#clear()
	 */
	@Override
	public void clear() {
		reigon.clear();
	}

	/* 
	 * @see jdbc.orm.cache.Cache#destory()
	 */
	@Override
	public void destory() {
		reigon.destory();
	}

	/* 
	 * @see jdbc.orm.cache.Cache#size()
	 */
	@Override
	public int size() {
		return reigon.size();
	}

	/* 
	 * @see jdbc.orm.cache.CacheRegionAccessStrategy#getCacheRegion()
	 */
	@Override
	public CacheRegion<K, V> getCacheRegion() {
		return reigon;
	}

	/* 
	 * @see jdbc.orm.cache.CacheRegionAccessStrategy#lockItem(java.lang.Object)
	 */
	@Override
	public LockItem<V> lockItem(K key) {
		try {
			lock.lock();
			LockItem<V> lockItem = reigon.getLockItem(key);
			if (null == lockItem) {
				lockItem = new LockItem<V>();
			} else {
				lockItem.lock();
			}
			
			reigon.put(key, lockItem);
			return lockItem;
		} finally {
			lock.unlock();
		}
	}

	/* 
	 * @see jdbc.orm.cache.CacheRegionAccessStrategy#unlockItem(java.lang.Object, jdbc.orm.cache.LockItem)
	 */
	@Override
	public boolean unlockItem(K key, LockItem<V> lockItem) {
		try {
			lock.lock();
			LockItem<V> localLockItem = reigon.getLockItem(key);
			if (null == localLockItem) {
				return false;
			} else if (localLockItem == lockItem) {
				lockItem.unlock();
				return true;
			}
			
			return false;
		} finally {
			lock.unlock();
		}
	}

}
